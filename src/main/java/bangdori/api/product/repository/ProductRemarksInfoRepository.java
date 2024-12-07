package bangdori.api.product.repository;

import bangdori.api.product.entity.ProductRemarksInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRemarksInfoRepository extends JpaRepository<ProductRemarksInfo, Long> {
    List<ProductRemarksInfo> findByProductInfoProdNo(Long prodNo);
}