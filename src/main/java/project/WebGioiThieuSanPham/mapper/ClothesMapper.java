package project.WebGioiThieuSanPham.mapper;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesResponse;
import project.WebGioiThieuSanPham.models.Clothes;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
@Component
public interface ClothesMapper {


     List<ClothesAvatarView> toListDao(List<Clothes> clothes);
     ClothesDetailView ClothesToClothesDetail(Clothes clothes);


     ClothesResponse clothesToClothesResponse(Clothes clothes);

     Clothes toEntity(ClothesRequest clothesRequest);


}
