package project.WebGioiThieuSanPham.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id")
    UUID id;
    @Column(name ="path")
    String path;
    @ManyToOne
    @JoinColumn(name ="clothes_id")
    private Clothes clothes;

}
