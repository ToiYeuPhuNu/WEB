package project.WebGioiThieuSanPham.mapper;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.dto.clothesDto.request.FilterRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesDetailView;
import project.WebGioiThieuSanPham.models.Clothes;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public interface ClothesMapper {

     Clothes toEntity(FilterRequest filterRequest);
     ClothesDetailView toDao(Clothes clothes);
     List<ClothesAvatarView> toListDao(Clothes clothes);

}
