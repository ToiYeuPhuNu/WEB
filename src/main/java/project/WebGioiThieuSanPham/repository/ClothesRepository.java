    package project.WebGioiThieuSanPham.repository;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
    import project.WebGioiThieuSanPham.enums.Sex;
    import project.WebGioiThieuSanPham.models.Clothes;

    import java.math.BigDecimal;
    import java.util.UUID;
    @Repository
    public interface ClothesRepository extends JpaRepository<Clothes, UUID>, JpaSpecificationExecutor<Clothes> {
        Page<Clothes> findClothesBySex(Sex sex, Pageable pageable);
        @Query("SELECT c FROM Clothes c WHERE (:sex IS NULL OR c.sex = :sex) " +
                "AND (:category IS NULL OR c.category.id = :categoryId) " +
                "AND (:minPrice IS NULL OR c.price >= :minPrice) " +
                "AND (:maxPrice IS NULL OR c.price <= :maxPrice)")
        Page<Clothes> filter(Sex sex, UUID categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    }