package project.WebGioiThieuSanPham.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.WebGioiThieuSanPham.models.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByCategoryChildren(Category categoryChild);

    Optional<Category> findByName(String name);
}
