package bangdori.api.domain.product.repository;

import bangdori.api.domain.product.entity.ProductImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageInfoRepository extends JpaRepository<ProductImageInfo, Long> {
    List<ProductImageInfo> findByProductInfoProdNoAndUseYn(Long prodNo, String useYn);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ProductImageInfo pi SET pi.useYn = :useYn WHERE pi.managementFileName = :managementFileName")
    int updateUseYnByRealFileName(@Param("managementFileName") String managementFileName, @Param("useYn") String useYn);
}
