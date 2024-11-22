package bangdori.api.user.controller;

import bangdori.api.comm.ApiResponse;
import bangdori.api.user.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import bangdori.api.user.entity.UserInfo;
import bangdori.api.user.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
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
        UserInfoDTO userDTO = userService.getUserById((String) params.get("id"));
        result.put("userDto",userDTO);
        return new ApiResponse().addResult(result);
    }
}
