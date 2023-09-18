package project.WebGioiThieuSanPham.dto.clothesDto.request;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.enums.Sex;


import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {
    private String name;
    private Sex sex;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;


}