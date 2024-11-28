package bangdori.api.user.controller;

import bangdori.api.comm.ApiResponse;
import bangdori.api.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import bangdori.api.user.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public ApiResponse getUsers() {
        System.out.println("악 이제 됨!!!!!!!!");
        Map<String, Object> members = userService.getUsers();
        return new ApiResponse().addResult(members);
    }


    @PostMapping("/id")
    public ApiResponse getUserById(@RequestBody HashMap<String, Object> params) {
        System.out.println(" 되는지?    :::   "+params.get("id"));
        Map<String, Object> result = new HashMap<>();
        UserInfoDto userDTO = userService.getUserById((String) params.get("id"));
        result.put("userDto",userDTO);
        return new ApiResponse().addResult(result);
    }
}
