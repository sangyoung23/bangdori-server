package bangdori.api.product.repository;

import bangdori.api.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductInfo, Long> {
    List<ProductInfo> findAllByUseYnOrderByNewDtmDesc(String useYn);

    Optional<ProductInfo> findByProdNo(Long prodNo);

}
