package project.WebGioiThieuSanPham.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "category")
public class Category extends MDMAEntity{
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<CategoryChild> categoryChildren;
}
