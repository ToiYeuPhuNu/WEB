package project.WebGioiThieuSanPham.service.category;

import project.WebGioiThieuSanPham.dto.ApiListBaseRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.dto.clothesDto.response.BasePage;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    public CategoryResponse createCategory(CategoryRequest categoryRequest);
    public List<CategoryResponse> getAllCategories();
    public CategoryResponse updateCategory(UUID categoryId, CategoryRequest updateCategoryRequest);
    public void deleteCategory(UUID categoryId);

    public BasePage<ClothesAvatarView> getClothesByCategory(ApiListBaseRequest apiListBaseRequest, UUID id);

}
