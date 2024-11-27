package bangdori.api.comm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comm/code")
public class CodeController {

    @GetMapping("/comms")
    public ApiResponse getCommCodeList(RequestParam HashMap<String, Object> params) {
        
    }
}
