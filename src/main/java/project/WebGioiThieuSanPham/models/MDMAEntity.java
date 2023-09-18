package project.WebGioiThieuSanPham.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class MDMAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    protected UUID id;
    @Pattern(regexp = "^[A-Z]+$", message = "Tên không hợp lệ")
    @Column(unique = true)
    private String name;
}
