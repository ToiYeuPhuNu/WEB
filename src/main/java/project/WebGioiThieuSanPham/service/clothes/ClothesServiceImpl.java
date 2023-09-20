package project.WebGioiThieuSanPham.service.clothes;


import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import project.WebGioiThieuSanPham.dto.ApiListBaseRequest;
import project.WebGioiThieuSanPham.dto.SearchByKeyword;
import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.BasePage;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;
import project.WebGioiThieuSanPham.mapper.ClothesMapper;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.repository.CategoryRepository;
import project.WebGioiThieuSanPham.repository.ClothesRepository;
import project.WebGioiThieuSanPham.service.category.CategoryServiceImpl;
import project.WebGioiThieuSanPham.utils.FilterDataUtil;

import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;
    private final ClothesMapper clothesMapper;
    private final CategoryRepository categoryRepository;
    private final SearchUtil searchUtil= new SearchUtil();



    @Override
    public void deleteClothes(UUID id) {
        Clothes clothes = clothesRepository.findById(id).orElseThrow(() -> new RuntimeException("Clothes not found"));
        clothesRepository.delete(clothes);
    }

    @Override
    public ClothesDetailView getClothesById(UUID id) {
        Clothes clothes = clothesRepository.findById(id).orElseThrow(()-> new RuntimeException("Clothes not found"));
        return clothesMapper.ClothesToClothesDetail(clothes);
    }


    @Override
    public ClothesResponse createClothes(ClothesRequest clothesRequest) {

            List<Object> nonEmptyFields = Arrays.asList(
                    clothesRequest.getName(),
                    clothesRequest.getSize(),
                    clothesRequest.getPrice(),
                    clothesRequest.getCategoryRequest(),
                    clothesRequest.getCategoryRequest(),
                    clothesRequest.getDescription(),
                    clothesRequest.getReleaseDate(),
                    clothesRequest.getStatus(),
                    clothesRequest.getMainPath(),
                    clothesRequest.getMediaPath()
            );
            if (nonEmptyFields.stream().anyMatch(field -> field == null || field.toString().trim().isEmpty())) {
                throw new RuntimeException("Các trường không được để trống");
            }
            Category existingCategory = categoryRepository.findByName(clothesRequest.getCategoryRequest().getName());
            if (clothesRequest.getCategoryRequest() != null){
                Category category = categoryRepository.findByName(clothesRequest.getName());
                if (category == null){
                    category = new Category();
                    category.setName(clothesRequest.getName());
                    categoryRepository.save(existingCategory);
                }
            }

            Clothes clothes = new Clothes();
            BeanUtils.copyProperties(clothesRequest, clothes);
            clothes.setCategory(existingCategory);
            clothes = clothesRepository.save(clothes);
            return clothesMapper.clothesToClothesResponse(clothes);
    }
    @Override
    public ClothesResponse updateClothes(UUID id,ClothesRequest clothesRequest) {

        List<Object> nonEmptyFields = Arrays.asList(
                clothesRequest.getName(),
                clothesRequest.getSize(),
                clothesRequest.getPrice(),
                clothesRequest.getCategoryRequest(),
                clothesRequest.getCategoryRequest(),
                clothesRequest.getDescription(),
                clothesRequest.getReleaseDate(),
                clothesRequest.getStatus(),
                clothesRequest.getMainPath(),
                clothesRequest.getMediaPath()
        );
        if (nonEmptyFields.stream().anyMatch(field -> field == null || field.toString().trim().isEmpty())) {
            throw new RuntimeException("Các trường không được để trống");
        }
            Clothes existingClothes = clothesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm quần áo"));
        Category existingCategory = categoryRepository.findByName(clothesRequest.getCategoryRequest().getName());
        if (clothesRequest.getCategoryRequest() != null){
            Category category = categoryRepository.findByName(clothesRequest.getName());
            if (category == null){
                category = new Category();
                category.setName(clothesRequest.getName());
                categoryRepository.save(existingCategory);
            }
        }

        BeanUtils.copyProperties(clothesRequest, existingClothes);
        existingClothes.setCategory(existingCategory);
        existingClothes = clothesRepository.save(existingClothes);
        return clothesMapper.clothesToClothesResponse(existingClothes);
    }

    public BasePage<ClothesAvatarView> search(ApiListBaseRequest apiListBaseRequest, SearchByKeyword searchRequest){
        String keyword = searchRequest.getKeyword();
        return this.map(searchUtil
                .ilike(keyword, "name")
                .build(searchRequest));
    };

    public BasePage<ClothesAvatarView> getAll(ApiListBaseRequest apiListBaseRequest){
        Page<Clothes> page = clothesRepository.findAll(FilterDataUtil.buildPageRequest(apiListBaseRequest));

        return this.map(page);

    }
    protected BasePage<ClothesAvatarView> map(Page<Clothes> page) {
        BasePage<ClothesAvatarView> rPage = new BasePage<>();
        rPage.setData(clothesMapper.toListDao(page.getContent()));
        rPage.setTotalPage(page.getTotalPages());
        rPage.setTotalRecord( page.getTotalElements());
        rPage.setPage(page.getPageable().getPageNumber());
        return rPage;
    }
    public class SearchUtil {
        private final List<Specification<Clothes>> specifications = new ArrayList<>();
        private final List<Predicate> tempPredicates = new ArrayList<>();
        ClothesRepository repository = ClothesServiceImpl.this.clothesRepository;

        public SearchUtil ilike(String keyword, String... fields) {
            if (StringUtils.isNotBlank(keyword)) {
                String finalKeyword = "%" + keyword.trim().toUpperCase() + "%";
                specifications.add(
                        (Specification<Clothes>) (root, query, criteriaBuilder) -> {
                            for (String field : fields) {
                                tempPredicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), finalKeyword));
                            }
                            return criteriaBuilder.or(tempPredicates.toArray(Predicate[]::new));
                        });
                tempPredicates.clear();
            }
            return this;
        }


        public Page<Clothes> build(ApiListBaseRequest filter) {
            Page<Clothes> data;
            if (!specifications.isEmpty()) {
                data = repository.findAll(specifications.stream().reduce(Specification::and).orElseThrow(),
                        FilterDataUtil.buildPageRequest(filter));
                specifications.clear();
            } else {
                data = repository.findAll(FilterDataUtil.buildPageRequest(filter));
            }
            return data;
        }
    }
}




