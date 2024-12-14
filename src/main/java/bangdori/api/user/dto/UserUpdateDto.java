package bangdori.api.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    private Long userNo;     // 필수, 어떤 사용자를 수정할지 지정
    private String name;     // 수정 가능: 이름
    private String phoneNo;  // 수정 가능: 핸드폰 번호
    private String pwd;      // 수정 가능: 비밀번호
}
