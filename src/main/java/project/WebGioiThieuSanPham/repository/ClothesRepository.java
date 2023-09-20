    package project.WebGioiThieuSanPham.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
    import org.springframework.stereotype.Repository;
    import project.WebGioiThieuSanPham.models.Clothes;

    import java.util.UUID;
    @Repository
    public interface ClothesRepository extends JpaRepository<Clothes, UUID>, JpaSpecificationExecutor<Clothes> {

  }