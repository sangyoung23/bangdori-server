package bangdori.api.domain.user.dto;

import bangdori.api.domain.user.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class    UserInfoDto {

    private Long userNo;
    private Long corpNo;  // 외래키 'CORP_NO'만 필요
    private String corpNm;
    private String roleCd;
    private String statusCd;
    private String id;
    private String pwd;
    private String name;
    private String phoneNo;

    /**
     * Authentication 객체를 생성하는 메소드
     * UserInfoDto에서 권한 정보(roleCd)를 기반으로 Authentication 객체 생성
     */
    public Authentication toAuthentication() {
        // roleCd를 권한 리스트로 변환
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleCd));

        // Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(id, pwd, authorities);
    }

    /**
     * UserInfo 엔티티를 UserInfoDto로 변환하는 메소드
     * @param userInfo 변환할 UserInfo 엔티티
     * @return UserInfoDto 객체
     */
    public static UserInfoDto fromEntity(UserInfo userInfo) {
        UserInfoDto dto = new UserInfoDto();
        dto.setUserNo(userInfo.getUserNo());
        dto.setCorpNo(userInfo.getCorpInfo().getCorpNo());
        dto.setCorpNm(userInfo.getCorpInfo().getCorpNm());
        dto.setRoleCd(userInfo.getRoleCd());
        dto.setStatusCd(userInfo.getStatusCd());
        dto.setId(userInfo.getId());
        dto.setPwd(userInfo.getPwd());
        dto.setName(userInfo.getName());
        dto.setPhoneNo(userInfo.getPhoneNo());
        return dto;
    }
}
