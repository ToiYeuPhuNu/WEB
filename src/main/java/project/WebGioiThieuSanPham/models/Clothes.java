package project.WebGioiThieuSanPham.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.enums.Sex;
import project.WebGioiThieuSanPham.enums.Size;
import project.WebGioiThieuSanPham.enums.Status;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clothes")
public class Clothes extends MDMAEntity{

    //đánh dấu kiểu dữ liệu của trường Sex là một enum với tên được lưu trữ trong db
    @Enumerated(EnumType.STRING)
    private Sex sex;
    //tạo bảng ràng buộc size(chỉ là kiểu dữ liệu nguyên thủy, không phải đối tươợng)
    @ElementCollection
    @CollectionTable(name = "clothes_detail_info", joinColumns = @JoinColumn(name = "clothes_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "size")
    //tạo ràng buộc Size và Quantity
    @Column(name = "quantity")
    private Map<Size, Integer> sizesStock = new HashMap<>();
    @Column(name = "price")
    private BigDecimal price;
    @Column(name="description")
    private String description;
    @Column(name="releaseDate")
    private Date releaseDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ElementCollection
    @CollectionTable(name = "media_path", joinColumns = @JoinColumn(name = "clothes_id"))
    @MapKeyColumn(name = "media_path")
    List<String> MediaPath = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryChild categoryChild;
        public void updateStatus() {
            // Kiểm tra xem tất cả các kích cỡ có bằng 0 hoặc null không
            boolean allSizesZeroOrNull = sizesStock.values().stream()
                    .allMatch(quantity -> quantity == null || quantity == 0);

            // Nếu tất cả các kích cỡ đều bằng 0 hoặc null, đặt trạng thái là "Out_of_stock"
            if (allSizesZeroOrNull) {
                this.status = Status.Out_Of_Stock;
            } else {
                // Nếu có ít nhất một kích cỡ có số lượng lớn hơn 0, đặt trạng thái là khác "Stock"
                this.status = Status.Stock;
            }
        }
    }


