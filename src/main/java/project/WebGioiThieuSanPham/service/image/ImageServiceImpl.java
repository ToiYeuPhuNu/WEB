package project.WebGioiThieuSanPham.service.image;

import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.dto.image.request.ImageRequest;
import project.WebGioiThieuSanPham.dto.image.response.ImageResponse;
import project.WebGioiThieuSanPham.mapper.ImageMapper;
import project.WebGioiThieuSanPham.models.Image;
import project.WebGioiThieuSanPham.repository.ImageRepository;

@Service

public class ImageServiceImpl implements ImageService{
    ImageMapper imageMapper;
    ImageRepository imageRepository;
    public ImageResponse add(ImageRequest imageRequest){
        Image image = imageRepository.save(imageMapper.toEntityForImage(imageRequest));
        ImageResponse imageResponse = imageMapper.toDaoForImage(image);
        return imageResponse;

    }
}
