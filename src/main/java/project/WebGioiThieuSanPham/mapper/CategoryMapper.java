package project.WebGioiThieuSanPham.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;

@Mapper(componentModel = "spring")
@Service
public interface CategoryMapper {
    CategoryResponse categoryToCategoryResponse(Category category);
    Category categoyRequestToCategory(CategoryRequest categoryRequest);
}
