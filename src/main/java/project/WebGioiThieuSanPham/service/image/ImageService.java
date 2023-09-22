package project.WebGioiThieuSanPham.service.image;

import project.WebGioiThieuSanPham.dto.image.request.ImageRequest;
import project.WebGioiThieuSanPham.dto.image.response.ImageResponse;

public interface ImageService {
    ImageResponse add (ImageRequest imageRequest);
}
