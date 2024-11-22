package bangdori.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private Long userNo;
    private Long corpNo;  // 외래키 'CORP_NO'만 필요
    private String roleCd;
    private String statusCd;
    private String id;
    private String pwd;
    private String name;
    private String phoneNo;
    private Date regDtm;
    private Date chgDtm;

    // 필요하다면 추가적인 필드를 만들거나 조정 가능
    // 예: corpName 같은 것을 포함시킬 수 있습니다. (그 경우 CorpInfo를 참조하는 대신 String이나 다른 형태로)

}