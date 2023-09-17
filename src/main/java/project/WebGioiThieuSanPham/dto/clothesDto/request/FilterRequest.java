package project.WebGioiThieuSanPham.dto.clothesDto.request;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.enums.Sex;
import project.WebGioiThieuSanPham.enums.Status;


import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {
    private String name;
    private Sex sex;
    private String color;
    private String Category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    @Override
    public String toString() {
        return "FilterRequest{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", color='" + color + '\'' +
                ", Category='" + Category + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}