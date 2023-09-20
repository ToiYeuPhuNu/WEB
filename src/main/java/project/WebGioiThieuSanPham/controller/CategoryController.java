package project.WebGioiThieuSanPham.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.WebGioiThieuSanPham.dto.ApiListBaseRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.dto.clothesDto.response.BasePage;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.service.category.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    CategoryService categoryService;
    @PostMapping("/add")
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable(name = "id") UUID categoryId, CategoryRequest updateCategoryRequest){
        return categoryService.updateCategory(categoryId, updateCategoryRequest);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable(name = "id") UUID categoryId){
        categoryService.deleteCategory(categoryId);
    }
    @GetMapping("/")
    public List<CategoryResponse> getAllCategory(){
        return categoryService.getAllCategories();
    }
    @GetMapping("/{id}")
    public BasePage<ClothesAvatarView> getClothesFromCategory(@RequestBody ApiListBaseRequest apiListBaseRequest,@PathVariable(name = "id") UUID id ){
        return categoryService.getClothesByCategory(apiListBaseRequest, id);
    }
}
