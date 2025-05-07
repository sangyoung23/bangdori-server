package bangdori.api.domain.user.repository;

import bangdori.api.domain.user.dto.UserPulicInfoDTO;
import bangdori.api.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findById(String id);

    @Query("SELECT new bangdori.api.user.dto.UserPulicInfoDTO(u.userNo, u.name) " +
            "FROM UserInfo u " +
            "WHERE u.corpInfo = (SELECT u2.corpInfo FROM UserInfo u2 WHERE u2.userNo = :userNo) " +
            "AND u.statusCd = '20'")
    List<UserPulicInfoDTO> findByCorpInfoOfUserNo(@Param("userNo") Long userNo);



}
