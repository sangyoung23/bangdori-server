package bangdori.api.product.repository;

import bangdori.api.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductInfo, Long> {
    List<ProductInfo> findAllByUseYn(String useYn);
}
