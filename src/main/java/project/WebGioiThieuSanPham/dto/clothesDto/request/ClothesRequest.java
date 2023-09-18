package project.WebGioiThieuSanPham.dto.clothesDto.request;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Size size;
    private Map<Size, Integer> sizesStock = new HashMap<>();
    private BigDecimal price;
    private String description;
    private Date releaseDate;
    private Status status;
    private String mainPath;
    private List<Category> categories;
    List<String> MediaPath = new ArrayList<>();
}
