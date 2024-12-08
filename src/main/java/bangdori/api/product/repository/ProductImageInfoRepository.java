package bangdori.api.product.repository;

import bangdori.api.product.entity.ProductImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageInfoRepository extends JpaRepository<ProductImageInfo, Long> {
    // 필요한 경우 추가 쿼리 메서드를 정의할 수 있습니다.
}
