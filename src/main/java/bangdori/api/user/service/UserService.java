package bangdori.api.user.service;

import bangdori.api.comm.Constants;
import bangdori.api.comm.ErrorCode;
import bangdori.api.comm.exception.InvalidException;
import bangdori.api.user.dto.UserInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
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
    public UserInfoDTO getUserById(String id) {
        Optional<UserInfo> userInfo = userRepository.findById(id);

        if(userInfo.isPresent()){

            return new ObjectMapper().convertValue(userInfo.getClass(), UserInfoDTO.class);

        }else{
            // null 처리를 이렇게 하는게 맞는가..확인이 필요..
            return new UserInfoDTO();
        }

    }
}
