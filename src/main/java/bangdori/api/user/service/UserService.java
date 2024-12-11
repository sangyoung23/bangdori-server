package bangdori.api.user.service;

import bangdori.api.comm.Constants;
import bangdori.api.user.dto.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import bangdori.api.user.entity.UserInfo;
import bangdori.api.user.repository.UserRepository;

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

    /**
     * 클라이언트에서 넘어온 사용자 검증 로직
     */
    public UserInfoDto authenticate(String id, String password) {
        // 사용자 조회
        UserInfo userInfo = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        System.out.println(passwordEncoder.matches(password, userInfo.getPwd()));
        System.out.println(!passwordEncoder.matches(password, userInfo.getPwd()));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, userInfo.getPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // DTO로 변환 후 반환
        return UserInfoDto.fromEntity(userInfo);

    }

    /**
     * userInfo List 조회
     *
     * @return
     */
    public Map<String, Object> getUsers() {
        Map<String, Object> result = new HashMap<>();
        List<UserInfo> userInfo = userRepository.findAll();
        result.put(Constants.KEY_LIST, userInfo);// JpaRepository에서 제공하는 findAll() 함수
        return result;

    }


    /**
     * userInfo List 조회
     *
     * @return
     */
    public UserInfoDto getUserById(String id) {
        Optional<UserInfo> userInfo = userRepository.findById(id);

        if(userInfo.isPresent()){

            return new ObjectMapper().convertValue(userInfo.getClass(), UserInfoDto.class);

        }else{
            // null 처리를 이렇게 하는게 맞는가..확인이 필요..
            return new UserInfoDto();
        }

    }
}
