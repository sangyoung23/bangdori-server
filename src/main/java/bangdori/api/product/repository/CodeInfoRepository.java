package bangdori.api.product.repository;

import bangdori.api.product.entity.CodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeInfoRepository extends JpaRepository<CodeInfo, String> {
}
