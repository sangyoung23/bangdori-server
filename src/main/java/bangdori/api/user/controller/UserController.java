package bangdori.api.user.controller;

import bangdori.api.comm.ApiResponse;
import bangdori.api.comm.TokenProvider;
import bangdori.api.user.dto.UserInfoDto;
import bangdori.api.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import bangdori.api.user.service.UserService;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    // 로그인 요청 처리
    @PostMapping("/login")
    public ApiResponse Login(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        byte[] decodedBytes = Base64.getDecoder().decode(params.get("password"));
        String password = new String(decodedBytes);;
        // 사용자 인중
        UserInfoDto userInfoDto = userService.authenticate(id, password);


        // JWT 토근 생성
        String token = tokenProvider.createToken(userInfoDto.toAuthentication(),userInfoDto.getUserNo());
 
        return new ApiResponse().addResult(Map.of(
                "token", token,
                "userNo", userInfoDto.getUserNo(),
                "userId", userInfoDto.getId(),
                "username", userInfoDto.getName(),
                "corpNo", userInfoDto.getCorpNo(),
                "corpNm", userInfoDto.getCorpNm()
        ));
    }

    // 회원 정보 수정
    @PostMapping("/update/userInfo")
    public ApiResponse updateUserForm(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.updateUserForm(userUpdateDto);
            return new ApiResponse().success();
        } catch (Exception e) {
            return new ApiResponse().error();
        }
    }



    @GetMapping("/list")
        public ApiResponse getUsers() {
            Map<String, Object> members = userService.getUsers();
            return new ApiResponse().addResult(members);
        }


        @PostMapping("/id")
        public ApiResponse getUserById(@RequestBody HashMap<String, Object> params) {
            Map<String, Object> result = new HashMap<>();
            UserInfoDto userDTO = userService.getUserById((String) params.get("id"));
            result.put("userDto",userDTO);
            return new ApiResponse().addResult(result);
        }
}
