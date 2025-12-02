package bangdori.api.domain.user.dto;

import bangdori.api.domain.user.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class  UserPublicInfoDTO {
    private Long userNo;
    private String name;

    public static UserPublicInfoDTO fromEntity(UserInfo userInfo) {
        return new UserPublicInfoDTO(userInfo.getUserNo(), userInfo.getName());
    }
}
