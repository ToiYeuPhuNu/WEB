package project.WebGioiThieuSanPham.dto.clothesDto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.enums.Sex;
import project.WebGioiThieuSanPham.enums.Size;
import project.WebGioiThieuSanPham.enums.Status;
import project.WebGioiThieuSanPham.models.Category;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClothesResponse {
    private UUID id;
    private String name;
    private Sex sex;
    private Map<Size, Integer> sizesStock = new HashMap<>();
    private BigDecimal price;
    private String description;
    private Date releaseDate;
    private Status status;
    private String mainPath;
    private CategoryResponse categoryResponse;
    List<String> MediaPath = new ArrayList<>();
}
