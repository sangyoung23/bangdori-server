package bangdori.api.product.repository;

import bangdori.api.product.entity.CodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeInfoRepository extends JpaRepository<CodeInfo, Long> {
    List<CodeInfo> findAllByUseYn(String useYn);
}
