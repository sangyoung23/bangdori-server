package bangdori.api.domain.user.controller;

import bangdori.api.comm.response.ApiResponse;
import bangdori.api.comm.jwt.TokenProvider;
import bangdori.api.domain.user.dto.UserInfoDTO;
import bangdori.api.domain.user.dto.UserRequestDTO;
import bangdori.api.domain.user.dto.UserUpdateDTO;
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
    public ApiResponse Login(@RequestBody UserRequestDTO request) {
        String id = request.getId();
        byte[] decodedBytes = Base64.getDecoder().decode(request.getPassword());
        String password = new String(decodedBytes);

        UserInfoDTO userInfoDto = userService.authenticate(id, password);

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
    public ApiResponse updateUserInfo(@PathVariable Long userNo ,@RequestBody UserUpdateDTO userUpdateDto) {
            userUpdateDto.setUserNo(userNo);
            userService.updateUserForm(userUpdateDto);
            return new ApiResponse().success();
    }
}
