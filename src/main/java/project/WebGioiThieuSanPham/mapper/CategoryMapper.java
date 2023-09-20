package project.WebGioiThieuSanPham.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.models.Category;

@Mapper(componentModel = "spring")
@Service
public interface CategoryMapper {
    CategoryResponse categoryToCategoryResponse(Category category);
    Category categoryRequestToCategory(CategoryRequest categoryRequest);
}
