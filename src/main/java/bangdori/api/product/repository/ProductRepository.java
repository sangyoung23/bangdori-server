package bangdori.api.product.repository;

import bangdori.api.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductInfo, Long> {
    List<ProductInfo> findAllByUseYnAndCorpNoOrderByNewDtmDesc(String useYn , Long corpNo);

    Optional<ProductInfo> findByProdNo(Long prodNo);


}
