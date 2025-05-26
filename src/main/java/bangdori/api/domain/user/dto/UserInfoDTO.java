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
public class UserInfoDTO {

    private Long userNo;
    private Long corpNo;
    private String corpNm;
    private String roleCd;
    private String statusCd;
    private String id;
    private String pwd;
    private String name;
    private String phoneNo;

    public Authentication toAuthentication() {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleCd));

        return new UsernamePasswordAuthenticationToken(id, pwd, authorities);
    }

    public static UserInfoDTO fromEntity(UserInfo userInfo) {
        UserInfoDTO dto = new UserInfoDTO();
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
