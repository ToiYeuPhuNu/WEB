package project.WebGioiThieuSanPham.service.category;

import project.WebGioiThieuSanPham.dto.ApiListBaseRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.dto.clothesDto.response.BasePage;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
     CategoryResponse createCategory(CategoryRequest categoryRequest);
     List<CategoryResponse> getAllCategories();
     CategoryResponse updateCategory(UUID categoryId, CategoryRequest updateCategoryRequest);
     void deleteCategory(UUID categoryId);

     BasePage<ClothesAvatarView> getClothesByCategory(ApiListBaseRequest apiListBaseRequest, UUID id);

}
