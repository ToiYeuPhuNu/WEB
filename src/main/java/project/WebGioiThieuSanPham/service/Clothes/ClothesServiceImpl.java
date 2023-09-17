package project.WebGioiThieuSanPham.service.Clothes;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import project.WebGioiThieuSanPham.mapper.ClothesMapper;
import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.repository.ClothesRepository;

import java.util.UUID;
@RequiredArgsConstructor

@AllArgsConstructor
@Service
public class ClothesServiceImpl implements ClothesService {
    ClothesRepository clothesRepository;
    private ClothesMapper mapper;

    @Override
    public Clothes add(Clothes clothes) {
        return clothesRepository.save(clothes);
    }
    @Override
    public Clothes getById(UUID id) {
        return clothesRepository.findById(id).orElse(null);
    }

//    public Page<ClothesAvatarView> filterClothes(FilterRequest filterRequest, Pageable pageable) {
//        String category = filterRequest.getCategory();
//        String color = filterRequest.getColor();
//        Sex sex = filterRequest.getSex();
//        BigDecimal minPrice = filterRequest.getMinPrice();
//        BigDecimal maxPrice = filterRequest.getMaxPrice();
//
//        Page<Clothes> clothesPage = clothesRepository.filterClothes(
//                category,
//                color,
//                sex,
//                minPrice,
//                maxPrice,
//                pageable
//        );
//
//        List<ClothesAvatarView> avatarViews = new ArrayList<>();
//        for (Clothes clothes : clothesPage.getContent()) {
//            ClothesAvatarView avatarView = new ClothesAvatarView();
//            avatarView.setId(clothes.getId());
//            avatarView.setName(clothes.getName());
//            avatarView.setPrice(clothes.getPrice());
//            avatarView.setStatus(clothes.getStatus());
//            avatarViews.add(avatarView);
//        }
//
//        return new PageImpl<>(avatarViews, pageable, clothesPage.getTotalElements());
//    }


}




