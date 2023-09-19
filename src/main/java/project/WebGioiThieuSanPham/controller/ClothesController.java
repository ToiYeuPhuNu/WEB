package project.WebGioiThieuSanPham.controller;


import lombok.AllArgsConstructor;


import org.springframework.web.bind.annotation.*;

import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;
import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.service.clothes.ClothesServiceImpl;


import java.util.UUID;


@RestController
@RequestMapping("/clothes")
@AllArgsConstructor
public class ClothesController {

    ClothesServiceImpl clothesService;

    @PostMapping("/")
    public ClothesResponse createClothes(@RequestBody ClothesRequest clothesRequest) {
        return clothesService.createClothes(clothesRequest);
    }

    @GetMapping("/{id}")
    public ClothesDetailView getClothesById(@PathVariable(name = "id") UUID id){
        return clothesService.getClothesById(id);
    }

    @PutMapping("/{id}")
    public ClothesResponse updateClothes(@PathVariable(name = "id") UUID id, @RequestBody ClothesRequest clothesRequest){
        return clothesService.updateClothes(id,clothesRequest);
    }
    @DeleteMapping("/{id}")
    public void deleteColthes(@PathVariable(name = "id") UUID id){
        clothesService.deleteClothes(id);
    }
}

