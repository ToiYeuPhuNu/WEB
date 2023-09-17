package project.WebGioiThieuSanPham.service.Clothes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.WebGioiThieuSanPham.dto.clothesDto.request.FilterRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.enums.Sex;
import project.WebGioiThieuSanPham.models.Clothes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ClothesService {

    Clothes add(Clothes clothes);

    Clothes getById(UUID id);

//    public List<Clothes> getAllClothes(Clothes clothes);

//    Page<ClothesAvatarView> filterClothes(FilterRequest filterRequest, Pageable pageable);
}
