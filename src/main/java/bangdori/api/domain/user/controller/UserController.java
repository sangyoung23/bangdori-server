package bangdori.api.domain.user.controller;

import bangdori.api.comm.response.ApiResponse;
import bangdori.api.comm.jwt.TokenProvider;
import bangdori.api.domain.user.dto.UserInfoDto;
import bangdori.api.domain.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import bangdori.api.domain.user.service.UserService;

import java.util.Base64;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;


    // 로그인
    @PostMapping("/auth/login")
    public ApiResponse Login(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        byte[] decodedBytes = Base64.getDecoder().decode(params.get("password"));
        String password = new String(decodedBytes);
        // 사용자 인중
        UserInfoDto userInfoDto = userService.authenticate(id, password);


        // JWT 토근 생성
        String token = tokenProvider.createToken(userInfoDto.toAuthentication(),userInfoDto.getUserNo());

        return new ApiResponse()
                .success()
                .addResult(Map.of(
                        "token", token,
                        "userNo", userInfoDto.getUserNo(),
                        "userId", userInfoDto.getId(),
                        "username", userInfoDto.getName(),
                        "corpNo", userInfoDto.getCorpNo(),
                        "corpNm", userInfoDto.getCorpNm()
                ));
    }

    // 회원 정보 수정
    @PutMapping("/{userNo}")
    public ApiResponse updateUserInfo(@PathVariable Long userNo ,@RequestBody UserUpdateDto userUpdateDto) {
            userUpdateDto.setUserNo(userNo);
            userService.updateUserForm(userUpdateDto);
            return new ApiResponse().success();
    }
}
