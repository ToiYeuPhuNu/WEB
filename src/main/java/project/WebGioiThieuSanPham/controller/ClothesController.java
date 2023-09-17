package project.WebGioiThieuSanPham.controller;


import lombok.AllArgsConstructor;


import org.springframework.web.bind.annotation.*;

import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.service.Clothes.ClothesServiceImpl;


import java.util.UUID;


@RestController
@RequestMapping("/clothes")
@AllArgsConstructor
public class ClothesController {

    ClothesServiceImpl clothesService;

    @PostMapping("/")
    public Clothes add(@RequestBody Clothes clothes) {
        return clothesService.add(clothes);
    }

    @GetMapping("/{id}")
    public Clothes getDetail(@PathVariable(name = "id") UUID id)
    {
        return clothesService.getById(id);
    }

//    @GetMapping("/filter")
//    public ResponseEntity<ApiBaseObjectResponse<Page<ClothesAvatarView>>> filterClothes(
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) String color,
//            @RequestParam(required = false) Sex sex,
//            @RequestParam(required = false) BigDecimal minPrice,
//            @RequestParam(required = false) BigDecimal maxPrice,
//            @PageableDefault(sort = "releaseDate") Pageable pageable) {
//        try {
//            Page<ClothesAvatarView> filteredClothes = clothesService.filterClothes(
//                    category,
//                    color,
//                    sex,
//                    minPrice,
//                    maxPrice,
//                    pageable
//            );
//            return new ResponseEntity<>(new ApiBaseObjectResponse<>("MDS-000", "Success", filteredClothes), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiBaseObjectResponse<>("MDS-004", "Lỗi trong quá trình tìm kiếm sản phẩm", null), HttpStatus.BAD_REQUEST);
//        }
//    }
}

