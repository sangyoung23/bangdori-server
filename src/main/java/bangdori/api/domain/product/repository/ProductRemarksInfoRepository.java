package bangdori.api.domain.product.repository;

import bangdori.api.domain.product.entity.ProductRemarksInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRemarksInfoRepository extends JpaRepository<ProductRemarksInfo, Long> {
    List<ProductRemarksInfo> findByProductInfoProdNo(Long prodNo);

    @Modifying
    @Query("UPDATE ProductRemarksInfo r SET r.useYn = :useYn WHERE r.productInfo.prodNo = :prodNo")
    int updateUseYnByProdNo(@Param("prodNo") Long prodNo, @Param("useYn") String useYn);

}