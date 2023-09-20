    package project.WebGioiThieuSanPham.repository;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
    import org.springframework.stereotype.Repository;
    import project.WebGioiThieuSanPham.enums.Sex;
    import project.WebGioiThieuSanPham.models.Clothes;

    import java.util.List;
    import java.util.UUID;
    @Repository
    public interface ClothesRepository extends JpaRepository<Clothes, UUID>, JpaSpecificationExecutor<Clothes> {
        Page<Clothes> findClothesBySex(Sex sex, Pageable pageable);
  }