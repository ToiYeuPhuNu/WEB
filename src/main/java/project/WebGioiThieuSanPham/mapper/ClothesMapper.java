package project.WebGioiThieuSanPham.mapper;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.dto.clothesDto.request.ClothesRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.request.FilterRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.models.Clothes;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface ClothesMapper {

     ClothesDetailView toDao(Clothes clothes);
     List<ClothesAvatarView> toListDao(Clothes clothes);
     Clothes updateClothesFromClothesDetail(ClothesDetailView clothesDetailView, String category);
     Clothes clothesRepuestToClothes(ClothesRequest clothesRequest);
     ClothesDetailView ClothesToClothesDetail(Clothes clothes);
     ClothesAvatarView ClothesToClothesAvatar(Clothes clothes);
     Clothes clothesAvatarToClothes(ClothesAvatarView clothesAvatarView);

}
