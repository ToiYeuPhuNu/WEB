package project.WebGioiThieuSanPham.service.clothes;


import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;
import project.WebGioiThieuSanPham.enums.Status;
import project.WebGioiThieuSanPham.mapper.ClothesMapper;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.repository.CategoryRepository;
import project.WebGioiThieuSanPham.repository.ClothesRepository;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@AllArgsConstructor
@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;
    private final ClothesMapper clothesMapper;
    private final CategoryRepository categoryRepository;

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
    public Page<ClothesAvatarView> getAllClothes(Optional<Integer> pageOptional) {
        int size = 10;
        int page = pageOptional.orElse(0);
        Pageable pageable = PageRequest.of(page,size);
        //lấy ds sp theo trang
        Page<Clothes> clothesPage = clothesRepository.findAll(pageable);
        //chuyển đổi trang sp clothes thành clothesAvatrView
        Page<ClothesAvatarView> clothesAvatarsPage = clothesPage.map(clothesMapper::ClothesToClothesAvatar);
        return clothesAvatarsPage;
    }

    @Override
    public Page<ClothesAvatarView> getClothesByCategory(UUID categoryId, Optional<Integer> pageOptional) {
        int size = 10;
        int page = pageOptional.orElse(0);
        Pageable pageable = PageRequest.of(page, size);
        //lấy ds sp theo trang và thể loại
        Page<Clothes> clothesPage = clothesRepository.findByCategory(categoryId, pageable);
        //chuyển đổi trang sp clothes thành clothesAvatrView
        Page<ClothesAvatarView> clothesAvatarViewPage = clothesPage.map(clothesMapper::ClothesToClothesAvatar);
        return clothesAvatarViewPage;
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


}




