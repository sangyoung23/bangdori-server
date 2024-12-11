package bangdori.api.user.entity;

import bangdori.api.product.entity.ProductInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;

//import javax.persistence.*;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "TB_USER_INFO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CORP_NO", nullable = false)
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

    // username, password, authorities를 받는 생성자 추가
    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = username;
        this.pwd = password;
        // authorities에서 첫 번째 역할만 가져옴
        this.roleCd = authorities.stream()
                .map(GrantedAuthority::getAuthority)  // 권한 이름을 가져옵니다.
                .filter(role -> role.equals("ADMIN") || role.equals("OWNER") || role.equals("MEMBER")) // 유효한 역할만 필터링
                .findFirst()  // 첫 번째 유효한 권한을 선택
                .orElse("MEMBER");  // 없으면 "MEMBER" 역할을 기본값으로 설정
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // roleCd를 기반으로 권한 설정 (ROLE_ 접두사 추가)
        return List.of(() -> "ROLE_" + this.roleCd);
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "ACTIVE".equals(this.statusCd);
    }
}

