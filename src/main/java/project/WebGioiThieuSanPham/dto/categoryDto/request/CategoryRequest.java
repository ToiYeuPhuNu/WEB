package project.WebGioiThieuSanPham.dto.categoryDto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryRequest {
    private String name;
}
