package project.WebGioiThieuSanPham.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="category")
public class Category extends MDMAEntity{
    @ManyToMany( mappedBy = "categories", cascade = CascadeType.MERGE)
    private List<Clothes> clothes;
}
