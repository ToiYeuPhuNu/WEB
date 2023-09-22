package project.WebGioiThieuSanPham.dto.clothesDto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.dto.image.response.ImageResponse;
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
public class ClothesDetailView extends BaseDto {
    private UUID id;
    private Sex sex;
    private Map<Size, Integer> size;
    private String name;
    private BigDecimal price;
    private CategoryResponse categoryResponse;
    private String description;
    private Date releaseDate;
    private Status status;
    private List<ImageResponse> imageResponses = new ArrayList<>();

}