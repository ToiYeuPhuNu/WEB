package project.WebGioiThieuSanPham.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.service.category.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    CategoryService categoryService;
    @PostMapping("/add")
    public CategoryResponse createCategory(@PathVariable(name = "categoryRequest") CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(UUID categoryId, CategoryRequest updateCategoryRequest){
        return categoryService.updateCategory(categoryId, updateCategoryRequest);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(UUID categoryId){
        categoryService.deleteCategory(categoryId);
    }
    @GetMapping("/")
    public List<CategoryResponse> getAllCategory(){
        return categoryService.getAllCategories();
    }
}
