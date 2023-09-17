package project.WebGioiThieuSanPham.dto.clothesDto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.enums.Status;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClothesAvatarView extends BaseDto{
    private UUID id;
    private String name;
    private BigDecimal price;
    private Status status;
}
