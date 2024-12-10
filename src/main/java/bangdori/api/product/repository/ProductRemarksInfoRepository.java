package bangdori.api.product.repository;

import bangdori.api.product.entity.ProductRemarksInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRemarksInfoRepository extends JpaRepository<ProductRemarksInfo, Long> {
    List<ProductRemarksInfo> findByProductInfoProdNo(Long prodNo);

    @Modifying
    @Query("UPDATE ProductRemarksInfo r SET r.useYn = :useYn WHERE r.productInfo.prodNo = :prodNo")
    int updateUseYnByProdNo(@Param("prodNo") Long prodNo, @Param("useYn") String useYn);

}