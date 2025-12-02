package bangdori.api.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    private Long userNo;
    private String name;
    private String phoneNo;
    private String pwd;
}
