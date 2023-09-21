package project.WebGioiThieuSanPham.service.clothes;


import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import project.WebGioiThieuSanPham.dto.ApiListBaseRequest;
import project.WebGioiThieuSanPham.dto.SearchByKeyword;
import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.BasePage;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;
import project.WebGioiThieuSanPham.enums.Sex;
import project.WebGioiThieuSanPham.mapper.CategoryMapper;
import project.WebGioiThieuSanPham.mapper.ClothesMapper;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.repository.CategoryRepository;
import project.WebGioiThieuSanPham.repository.ClothesRepository;
import project.WebGioiThieuSanPham.utils.FilterDataUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;
    private final ClothesMapper clothesMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final SearchUtil searchUtil= new SearchUtil();



    @Override
    public void deleteClothes(UUID id) {
        Objects.requireNonNull(id, "ID của Clothes không được null");
        Clothes clothes = clothesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Clothes với ID"));
        clothesRepository.delete(clothes);
    }

    @Override
    public ClothesDetailView getClothesById(UUID id) {
        Objects.requireNonNull(id, "ID của Clothes không được null");
        Clothes clothes = clothesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Clothes với ID"));
        return clothesMapper.ClothesToClothesDetail(clothes);
    }


    @Override
    public ClothesResponse createClothes(ClothesRequest clothesRequest) {

        if (isAnyFieldEmpty(clothesRequest)) {
            throw new RuntimeException("Các trường không được để trống");
        }
        Category existingCategory = handleCategory(clothesRequest);

        Clothes clothes = clothesMapper.toEntity(clothesRequest);
        clothes.setCategory(existingCategory);
        clothes = clothesRepository.save(clothes);
        return clothesMapper.clothesToClothesResponse(clothes);
    }
    @Override
    public ClothesResponse updateClothes(UUID id,ClothesRequest clothesRequest) {
        Objects.requireNonNull(id, "ID của sản phẩm không được null.");
        if (isAnyFieldEmpty(clothesRequest)) {
            throw new RuntimeException("Các trường không được để trống");
        }
            Clothes existingClothes = clothesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm quần áo với ID"));
        Category existingCategory = handleCategory(clothesRequest);

        existingClothes = clothesMapper.toEntity(clothesRequest);
        existingClothes.setCategory(existingCategory);
        existingClothes = clothesRepository.save(existingClothes);
        return clothesMapper.clothesToClothesResponse(existingClothes);
    }

    private Category handleCategory(ClothesRequest clothesRequest) {
        Optional<Category> existingCategory = Optional.of(Optional.ofNullable(clothesRequest.getCategoryRequest())
                .map(categoryRequest -> categoryRepository.findByName(categoryRequest.getName()))
                .orElseGet(() -> {
                    Category newCategory = categoryMapper.categoryRequestToCategory(clothesRequest.getCategoryRequest());
                    newCategory.setName(clothesRequest.getCategoryRequest().getName());
                    return categoryRepository.save(newCategory);
                }));
        return existingCategory.orElse(null);
    }

    private boolean isAnyFieldEmpty(ClothesRequest clothesRequest) {
        return Stream.of(
                clothesRequest.getName(),
                clothesRequest.getSizesStock(),
                clothesRequest.getPrice(),
                clothesRequest.getCategoryRequest(),
                clothesRequest.getDescription(),
                clothesRequest.getReleaseDate(),
                clothesRequest.getStatus(),
                clothesRequest.getMainPath(),
                clothesRequest.getMediaPath()
        ).anyMatch(field -> field == null || field.toString().trim().isEmpty());
    }

    public BasePage<ClothesAvatarView> getClothesBySex(ApiListBaseRequest apiListBaseRequest, Sex sex){
        Pageable pageable = FilterDataUtil.buildPageRequest(apiListBaseRequest);
        Page<Clothes> page = clothesRepository.findClothesBySex(sex, pageable);
        return this.map(page);

    }

    public BasePage<ClothesAvatarView> search(ApiListBaseRequest apiListBaseRequest, SearchByKeyword searchRequest){
        String keyword = searchRequest.getKeyword();
        return this.map(searchUtil
                .ilike(keyword, "name")
                .build(searchRequest));
    }

    public BasePage<ClothesAvatarView> getAll(ApiListBaseRequest apiListBaseRequest){
        Page<Clothes> page = clothesRepository.findAll(FilterDataUtil.buildPageRequest(apiListBaseRequest));
        return this.map(page);

    }

    public BasePage<ClothesAvatarView> filterUtil(ApiListBaseRequest apiListBaseRequest, Sex sex, UUID categoryId, BigDecimal minPrice, BigDecimal maxPrice){
        Pageable pageable = FilterDataUtil.buildPageRequest(apiListBaseRequest);
        Page<Clothes> page = clothesRepository.filter(sex, categoryId, minPrice, maxPrice, pageable);
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




