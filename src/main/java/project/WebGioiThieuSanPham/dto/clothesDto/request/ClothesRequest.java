package project.WebGioiThieuSanPham.dto.clothesDto.request;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.enums.Sex;
import project.WebGioiThieuSanPham.enums.Size;
import project.WebGioiThieuSanPham.enums.Status;
import project.WebGioiThieuSanPham.models.Category;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClothesRequest {
    private String name;
    private Sex sex;
    private Map<Size, Integer> sizesStock;
    private BigDecimal price;
    private String description;
    private Date releaseDate;
    private String status;
    private String mainPath;
    private CategoryRequest categoryRequest;
    List<String> MediaPath;
}
