package bangdori.api.domain.code.controller;


import bangdori.api.comm.response.ApiResponse;
import bangdori.api.domain.code.dto.CodeDTO;
import bangdori.api.domain.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bangdori.api.comm.Constants;


import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/codes")
public class CodeController {

    private final CodeService codeService;

    // 공통 코드 조회
    @GetMapping("/grouped")
    public ApiResponse getGroupedCodes() {
        Map<String, List<CodeDTO>> codeList = codeService.getGroupedCommCodeList();
        return new ApiResponse()
                .success()
                .addResult(Constants.KEY_LIST, codeList);
    }
}
