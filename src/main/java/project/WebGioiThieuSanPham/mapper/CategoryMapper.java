package project.WebGioiThieuSanPham.mapper;

import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;

public interface CategoryMapper {
    CategoryResponse categoryToCategoryResponse(Category category);
    Category categoyRequestToCategory(CategoryRequest categoryRequest);
}
