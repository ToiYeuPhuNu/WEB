package project.WebGioiThieuSanPham.service.clothes;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.mapper.ClothesMapper;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.repository.CategoryRepository;
import project.WebGioiThieuSanPham.repository.ClothesRepository;

import java.util.List;
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
    public ClothesDetailView updateClothes(UUID id, ClothesDetailView clothesDetailView) {

        Clothes existingClothes = clothesRepository.findById(id).orElseThrow(()-> new RuntimeException("Clothes not found"));
        existingClothes = clothesMapper.updateClothesFromClothesDetail(clothesDetailView, clothesDetailView.getCategory());
        existingClothes = clothesRepository.save(existingClothes);
        return clothesMapper.ClothesToClothesDetail(existingClothes);
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
    public Page<ClothesAvatarView> getAllClothes(int page) {
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page,size);
        //lấy ds sp theo trang
        Page<Clothes> clothesPage = clothesRepository.findAll(pageRequest);
        //chuyển đổi trang sp clothes thành clothesAvatrView
        Page<ClothesAvatarView> clothesAvatarsPage = clothesPage.map(clothesMapper::ClothesToClothesAvatar);
        return clothesAvatarsPage;
    }

    @Override
    public Page<ClothesAvatarView> getlothesByCategory(UUID category, int page) {
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        //lấy ds sp theo trang và thể loại
        Page<Clothes> clothesPage = clothesRepository.findByCategory(category, pageRequest);
        //chuyển đổi trang sp clothes thành clothesAvatrView
        Page<ClothesAvatarView> clothesAvatarViewPage = clothesPage.map(clothesMapper::ClothesToClothesAvatar);
        return clothesAvatarViewPage;
    }

    @Override
    public ClothesDetailView createClothes(ClothesRequest clothesRequest) {
        String clothesName = clothesRequest.getName();
        if (clothesRepository.existsByName(clothesName)) {
            throw new RuntimeException("Tên sản phẩm đã tồn tại.");
        }
        // Kiểm tra tính hợp lệ của tên sản phẩm
        if (clothesName == null || clothesName.trim().isEmpty()) {
            throw new RuntimeException("Tên sản phẩm không hợp lệ.");
        }
        Category category = categoryRepository.findByName(clothesRequest.getCategories().toString())
                .orElseGet(()-> {
                    Category newCategory = new Category();
                    newCategory.setName(clothesRequest.getCategories().toString());
                    return categoryRepository.save(newCategory);
                });
        Clothes clothes = clothesMapper.clothesRepuestToClothes(clothesRequest);
        clothes.setCategories((List<Category>) category);
        clothes = clothesRepository.save(clothes);
        return clothesMapper.ClothesToClothesDetail(clothes);

    }

    @Override
    public List<Clothes> getlothesByCategory(UUID categoryId) {
        return clothesRepository.findByCategoryId(categoryId);
    }
}




