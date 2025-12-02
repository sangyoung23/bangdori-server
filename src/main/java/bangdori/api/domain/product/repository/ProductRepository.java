package bangdori.api.domain.product.repository;

import bangdori.api.domain.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductInfo, Long> {
    @Query("SELECT p FROM ProductInfo p LEFT JOIN FETCH p.productRemarksInfos r WHERE p.useYn = :useYn AND p.corpNo = :corpNo ORDER BY p.newDtm DESC")
    List<ProductInfo> findAllWithRemarksByUseYnAndCorpNo(@Param("useYn") String useYn, @Param("corpNo") Long corpNo);

    Optional<ProductInfo> findByProdNo(Long prodNo);


}
