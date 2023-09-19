package project.WebGioiThieuSanPham.service.category;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.mapper.CategoryMapper;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.repository.CategoryRepository;
import project.WebGioiThieuSanPham.repository.ClothesRepository;
import project.WebGioiThieuSanPham.service.clothes.ClothesService;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ClothesRepository clothesRepository;
    private final CategoryMapper categoryMapper;
    private final ClothesService clothesService;
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        String categoryName = categoryRequest.getName();
        Category existingCategory = categoryRepository.findByName(categoryName);
        if (existingCategory != null){
            throw new RuntimeException("Danh mục đã tồn tại!");
        } else if (StringUtils.isBlank(categoryName)) {
            throw new RuntimeException("Tên không hợp lệ!");
        } else {
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            categoryRepository.save(newCategory);
            return categoryMapper.categoryToCategoryResponse(newCategory);
        }
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::categoryToCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(UUID categoryId, CategoryRequest updateCategoryRequest) {
        Objects.requireNonNull(categoryId, "ID của danh mục không được null.");
        //kiểm tra xem danh mục có tồn tại hay không
        Optional<Category> existingCategoryOptional = categoryRepository.findById(categoryId);
        Category existingCategory = existingCategoryOptional.orElseThrow(()-> new RuntimeException("Danh mục không tồn tại!"));
        String newName = updateCategoryRequest.getName();
        if (newName!=null && StringUtils.isBlank(newName)){
            throw new RuntimeException("Tên không hợp lệ!");
        }
        // Cập nhật thông tin danh mục
        existingCategory.setName(updateCategoryRequest.getName());
        categoryRepository.save(existingCategory);
        return categoryMapper.categoryToCategoryResponse(existingCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Objects.requireNonNull(categoryId, "ID của danh mục không được null!");
        Optional<Category> existingCategoryOptional = categoryRepository.findById(categoryId);
        Category existingCategory = existingCategoryOptional.orElseThrow(()-> new RuntimeException("Danh mục không tồn tại "));
        categoryRepository.deleteById(categoryId);
    }
}
