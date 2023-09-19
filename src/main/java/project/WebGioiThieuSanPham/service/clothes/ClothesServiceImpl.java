package project.WebGioiThieuSanPham.service.clothes;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;
    private final ClothesMapper clothesMapper;
    private final CategoryRepository categoryRepository;
    public ClothesServiceImpl(ClothesMapper clothesMapper, ClothesRepository clothesRepository, CategoryRepository categoryRepository){
        this.clothesRepository =  clothesRepository;
        this.clothesMapper = clothesMapper;
        this.categoryRepository = categoryRepository;
    }


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
        try {
            List<Object> nonEmptyFields = Arrays.asList(
                    clothesRequest.getName(),
                    clothesRequest.getSize(),
                    clothesRequest.getPrice(),
                    clothesRequest.getCategories(),
                    clothesRequest.getDescription(),
                    clothesRequest.getReleaseDate(),
                    clothesRequest.getStatus(),
                    clothesRequest.getMainPath(),
                    clothesRequest.getMediaPath()
            );
            if (nonEmptyFields.stream().anyMatch(field -> field == null || field.toString().trim().isEmpty())) {
                throw new RuntimeException("Các trường không được để trống");
            }
            Category category = categoryRepository.findByName(clothesRequest.getCategories().toString())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(clothesRequest.getCategories().toString());
                        return categoryRepository.save(newCategory);
                    });
            Clothes clothes = clothesMapper.clothesRepuestToClothes(clothesRequest);
            clothes.setCategories((List<Category>) category);
            clothes.setName(clothesRequest.getName());
            clothes.setSizesStock(clothesRequest.getSizesStock());
            clothes.setPrice(clothesRequest.getPrice());
            clothes.setDescription(clothesRequest.getDescription());
            clothes.setReleaseDate(clothesRequest.getReleaseDate());
            clothes.setStatus(Status.valueOf(clothesRequest.getStatus()));
            clothes.setMainPath(clothesRequest.getMainPath());
            clothes.setMediaPath(clothesRequest.getMediaPath());

            clothes = clothesRepository.save(clothes);

            return clothesMapper.ClothesToClothesResponse(clothes);
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public ClothesResponse updateClothes(UUID id,ClothesRequest clothesRequest) {
        try {
            List<Object> nonEmptyFields = Arrays.asList(
                    clothesRequest.getName(),
                    clothesRequest.getSize(),
                    clothesRequest.getPrice(),
                    clothesRequest.getCategories(),
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
            Category category = categoryRepository.findByName(clothesRequest.getCategories().toString())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(clothesRequest.getCategories().toString());
                        return categoryRepository.save(newCategory);
                    });
            existingClothes = clothesMapper.clothesRepuestToClothes(clothesRequest);
            existingClothes.setCategories((List<Category>) category);
            existingClothes.setName(clothesRequest.getName());
            existingClothes.setSizesStock(clothesRequest.getSizesStock());
            existingClothes.setPrice(clothesRequest.getPrice());
            existingClothes.setDescription(clothesRequest.getDescription());
            existingClothes.setReleaseDate(clothesRequest.getReleaseDate());
            existingClothes.setStatus(Status.valueOf(clothesRequest.getStatus()));
            existingClothes.setMainPath(clothesRequest.getMainPath());
            existingClothes.setMediaPath(clothesRequest.getMediaPath());

            existingClothes = clothesRepository.save(existingClothes);

            return clothesMapper.ClothesToClothesResponse(existingClothes);
        }catch (Exception e){
            throw e;
        }
    }


}




