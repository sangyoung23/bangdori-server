package bangdori.api.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;

//import javax.persistence.*;
import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "TB_USER_INFO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 설정 (id값이 null일 경우 자동 생성)
    @Column(name = "USER_NO")  // 컬럼 지정
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CORP_NO", referencedColumnName = "CORP_NO")// 외래키 corp_no
    @JsonBackReference
    private CorpInfo corpInfo;

    @NotNull
    @Column(name = "ROLE_CD")
    private String roleCd;

    @NotNull
    @Column(name = "STATUS_CD")
    private String statusCd;

    @NotNull
    @Column(name = "ID")
    private String id;

    @NotNull
    @Column(name = "PWD")
    private String pwd;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "PHONE_NO")
    private String phoneNo;

    @NotNull
    @Column(name = "REG_DTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDtm;

    @NotNull
    @Column(name = "CHG_DTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date chgDtm;
}
