    package project.WebGioiThieuSanPham.repository;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;
    import project.WebGioiThieuSanPham.enums.Sex;
    import project.WebGioiThieuSanPham.models.Clothes;

    import java.math.BigDecimal;
    import java.util.UUID;
    @Repository
    public interface ClothesRepository extends JpaRepository<Clothes, UUID>, JpaSpecificationExecutor<Clothes> {
        Clothes findClothesByName(String name);


//        @Query("SELECT c FROM Clothes c " +
//                "WHERE (:category IS NULL OR c.category = :category) " +
//                "AND (:color IS NULL OR c.color = :color) " +
//                "AND (:sex IS NULL OR c.sex = :sex) " +
//                "AND (:minPrice IS NULL OR c.price >= :minPrice) " +
//                "AND (:maxPrice IS NULL OR c.price <= :maxPrice) " +
//                "AND c.status = 'Stock'")
//        Page<Clothes> filterClothes(@Param("category") String category,
//                                    @Param("color") String color,
//                                    @Param("sex") Sex sex,
//                                    @Param("minPrice") BigDecimal minPrice,
//                                    @Param("maxPrice") BigDecimal maxPrice,
//                                    Pageable pageable);
  }