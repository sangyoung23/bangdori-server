package bangdori.api.domain.code.repository;

import bangdori.api.domain.code.entity.CodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeInfoRepository extends JpaRepository<CodeInfo, Long> {
    List<CodeInfo> findAllByUseYnOrderByOrdAsc(String useYn);
}
