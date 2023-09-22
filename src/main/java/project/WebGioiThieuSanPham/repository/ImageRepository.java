package project.WebGioiThieuSanPham.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import project.WebGioiThieuSanPham.models.Image;


import java.util.UUID;

@Repository

public interface ImageRepository extends JpaRepository<Image, UUID>, JpaSpecificationExecutor<Image> {
}
