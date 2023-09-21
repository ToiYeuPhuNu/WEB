package project.WebGioiThieuSanPham.controller;


import lombok.AllArgsConstructor;


import org.springframework.web.bind.annotation.*;

import project.WebGioiThieuSanPham.dto.ApiListBaseRequest;
import project.WebGioiThieuSanPham.dto.SearchByKeyword;
import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.BasePage;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;
import project.WebGioiThieuSanPham.enums.Sex;
import project.WebGioiThieuSanPham.service.clothes.ClothesServiceImpl;


import java.math.BigDecimal;
import java.util.UUID;


@RestController
@RequestMapping("/clothes")
@AllArgsConstructor
public class ClothesController {

    ClothesServiceImpl clothesService;

    @PostMapping("/add")
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
    @GetMapping("/")
    public BasePage<ClothesAvatarView> getAllClothes(@RequestBody ApiListBaseRequest apiListBaseRequest){
        return clothesService.getAll((apiListBaseRequest));
    }

    @GetMapping("/gender")
    public BasePage<ClothesAvatarView> findClothesByGender(@RequestBody ApiListBaseRequest apiListBaseRequest, Sex sex){
        return clothesService.getClothesBySex(apiListBaseRequest, sex);
    }

    @GetMapping("/search")
    public BasePage<ClothesAvatarView> search(@RequestBody ApiListBaseRequest apiListBaseRequest, SearchByKeyword searchByKeyword){
        return clothesService.search(apiListBaseRequest, searchByKeyword);
    }

    @GetMapping("/filter")
    public BasePage<ClothesAvatarView> filterClothes(@RequestBody ApiListBaseRequest apiListBaseRequest,
                                                     @RequestParam(required = false) UUID categoryId,
                                                     @RequestParam(required = false) Sex sex,
                                                     @RequestParam(required = false) BigDecimal minPrice,
                                                     @RequestParam(required = false) BigDecimal maxPrice){
        return clothesService.filterUtil(apiListBaseRequest,sex,categoryId,minPrice,maxPrice);
    }
}

