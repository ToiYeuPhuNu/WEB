package project.WebGioiThieuSanPham.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.WebGioiThieuSanPham.dto.image.request.ImageRequest;
import project.WebGioiThieuSanPham.dto.image.response.ImageResponse;
import project.WebGioiThieuSanPham.service.image.ImageService;

@RestController
@RequestMapping("/image")
@AllArgsConstructor

public class ImageController {

    private final ImageService imageService;

    @PostMapping("/")
    public ImageResponse creat(@RequestBody ImageRequest imageRequest){
        return imageService.add(imageRequest);
    }
}
