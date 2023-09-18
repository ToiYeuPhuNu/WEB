package project.WebGioiThieuSanPham.service.clothes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.request.FilterRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;
import project.WebGioiThieuSanPham.enums.Sex;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ClothesService {

    public ClothesResponse updateClothes(UUID id,ClothesRequest clothesRequest);
    public void deleteClothes(UUID id);
    public ClothesDetailView getClothesById(UUID id);
    public Page<ClothesAvatarView> getAllClothes(int page);
    public Page<ClothesAvatarView> getlothesByCategory(UUID categoryId, int page);
    public ClothesResponse createClothes(ClothesRequest clothesRequest);


}
