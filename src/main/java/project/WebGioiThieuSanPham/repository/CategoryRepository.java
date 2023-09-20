package project.WebGioiThieuSanPham.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.WebGioiThieuSanPham.models.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByName(String categoryName);
}
