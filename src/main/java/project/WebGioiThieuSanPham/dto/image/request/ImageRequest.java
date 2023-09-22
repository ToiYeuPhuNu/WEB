package project.WebGioiThieuSanPham.dto.image.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ImageRequest {
    private String path;
    private ClothesRequest clothesRequest;
}
