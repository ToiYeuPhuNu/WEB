package project.WebGioiThieuSanPham.dto.clothesDto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.enums.Sex;
import project.WebGioiThieuSanPham.enums.Size;
import project.WebGioiThieuSanPham.enums.Status;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClothesDetailView extends BaseDto {
    private UUID id;
    private Sex sex;
    private String name;
    private String color;
    private BigDecimal price;
    private String category;
    private String description;
    private Date releaseDate;
    private Status status;
    private Map<Size, Integer> size;

}