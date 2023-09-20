    package project.WebGioiThieuSanPham.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
    import org.springframework.stereotype.Repository;
    import project.WebGioiThieuSanPham.models.Clothes;

    import java.util.UUID;
    @Repository
    public interface ClothesRepository extends JpaRepository<Clothes, UUID>, JpaSpecificationExecutor<Clothes> {



//        <Clothes> Page<Clothes> findAll(Specification<Clothes> clothesSpecification, PageRequest pageRequest);

        //  <Clothes> Page<Clothes> findAll(Specification<Clothes> clothesSpecification, PageRequest pageRequest);


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