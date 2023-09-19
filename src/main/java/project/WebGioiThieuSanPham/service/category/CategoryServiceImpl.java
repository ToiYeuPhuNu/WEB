package project.WebGioiThieuSanPham.service.category;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.repository.CategoryRepository;
import project.WebGioiThieuSanPham.repository.ClothesRepository;
import project.WebGioiThieuSanPham.service.clothes.ClothesService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ClothesRepository clothesRepository;
    private ClothesService clothesService;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ClothesService clothesService, ClothesRepository clothesRepository){
        this.categoryRepository = categoryRepository;
        this.clothesService = clothesService;
        this.clothesRepository = clothesRepository;
    }
    @Override
    public Category createCategory(Category category) {
        // Kiểm tra tính hợp lệ của dữ liệu trước khi lưu danh mục
        Objects.requireNonNull(category, "Danh mục không được null");
        String categoryName = Objects.requireNonNull(category.getName(), "Tên danh mục không hợp lệ");
        // Kiểm tra xem danh mục đã tồn tại trong cơ sở dữ liệu hay chưa
        Optional<Category> existingCategory = categoryRepository.findByName(categoryName);
        if (existingCategory.isPresent()){
            throw new RuntimeException("Danh mục đã tồn tại");
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(UUID categoryId, Category updateCategory) {
        Objects.requireNonNull(categoryId, "ID của danh mục không được null.");
        //kiểm tra xem danh mục có tồn tại hay không
        Optional<Category> existingCategoryOptional = categoryRepository.findById(categoryId);
        Category existingCategory = existingCategoryOptional.orElseThrow(()-> new RuntimeException("Danh mục không tồn tại "));
        // Cập nhật thông tin danh mục
        existingCategory.setName(updateCategory.getName());
        existingCategory.setClothes(updateCategory.getClothes());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Objects.requireNonNull(categoryId, "ID của danh mục không được null!");
        Optional<Category> existingCategoryOptional = categoryRepository.findById(categoryId);
        Category existingCategory = existingCategoryOptional.orElseThrow(()-> new RuntimeException("Danh mục không tồn tại "));
        List<Clothes> clothesDelete = clothesRepository.findByCategoryId(categoryId);
        for (Clothes clothes : clothesDelete){
            clothes.setCategories(null);
        }
        categoryRepository.deleteById(categoryId);
    }
}
