package project.WebGioiThieuSanPham.dto.image.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ImageResponse {
    private UUID id;
    private String path;
    private ClothesResponse clothesResponse;
}
