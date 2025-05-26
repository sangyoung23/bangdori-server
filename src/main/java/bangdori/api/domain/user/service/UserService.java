package bangdori.api.domain.user.service;

import bangdori.api.comm.Constants;
import bangdori.api.domain.user.repository.UserRepository;
import bangdori.api.domain.user.dto.UserInfoDTO;
import bangdori.api.domain.user.dto.UserUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import bangdori.api.domain.user.entity.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoDTO authenticate(String id, String password) {
        UserInfo userInfo = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, userInfo.getPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return UserInfoDTO.fromEntity(userInfo);

    }

    @Transactional
    public void updateUserForm(UserUpdateDTO userUpdateDto) {
        UserInfo userInfo = userRepository.findById(userUpdateDto.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 번호입니다."));

        if (userUpdateDto.getName() != null && !userUpdateDto.getName().isEmpty()) {
            userInfo.updateUserName(userUpdateDto.getName());
        }
        if (userUpdateDto.getPhoneNo() != null && !userUpdateDto.getPhoneNo().isEmpty()) {
            userInfo.updateUserPhoneNo(userUpdateDto.getPhoneNo());
        }
        if (userUpdateDto.getPwd() != null && !userUpdateDto.getPwd().isEmpty()) {
            userInfo.updateUserPwd(passwordEncoder.encode(userUpdateDto.getPwd()));
        }

        userRepository.save(userInfo);
    }

    public Map<String, Object> getUsers() {
        Map<String, Object> result = new HashMap<>();
        List<UserInfo> userInfo = userRepository.findAll();
        result.put(Constants.KEY_LIST, userInfo);
        return result;

    }

    public UserInfoDTO getUserById(String id) {
        Optional<UserInfo> userInfo = userRepository.findById(id);

        if(userInfo.isPresent()){

            return new ObjectMapper().convertValue(userInfo.getClass(), UserInfoDTO.class);

        } else {
            return new UserInfoDTO();
        }

    }
}
