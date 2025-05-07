package bangdori.api.domain.user.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TB_CORP_INFO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CorpInfo {

    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 설정 (id값이 null일 경우 자동 생성)
    @Column(name = "CORP_NO")  // 컬럼 지정
    private Long corpNo;

    @NotNull
    @Column(name = "CORP_NM")
    private String corpNm;

    @NotNull
    @Column(name = "CORP_ADDR")
    private String corpAddr;

    @Column(name = "CORP_MNG_NM")
    private String corpMngNm;

    @Column(name = "CORP_MNG_HPHN_NO")
    private String corpMngHphnNo;

    @NotNull
    @Column(name = "REG_DTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDtm;

    @NotNull
    @Column(name = "CHG_DTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date chgDtm;

    @OneToMany(mappedBy = "corpInfo", fetch = FetchType.LAZY)
    @JsonBackReference  // 순환 참조 방지
    private List<UserInfo> userInfos;


}
