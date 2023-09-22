package project.WebGioiThieuSanPham.mapper;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.dto.image.request.ImageRequest;
import project.WebGioiThieuSanPham.dto.image.response.ImageResponse;
import project.WebGioiThieuSanPham.models.Image;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface ImageMapper {
    Image toEntityForImage(ImageRequest imageRequest);
    ImageResponse toDaoForImage(Image image);
}
