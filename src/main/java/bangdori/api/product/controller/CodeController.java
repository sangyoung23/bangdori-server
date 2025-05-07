package bangdori.api.product.controller;


import bangdori.api.comm.ApiResponse;
import bangdori.api.product.dto.CodeDTO;
import bangdori.api.product.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import bangdori.api.comm.Constants;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/codes")
public class CodeController {

    private final CodeService codeService;
    private final ApiResponse apiResponse;

    // 공통 코드 조회
    @GetMapping("/grouped")
    public ApiResponse getGroupedCodes() {
        Map<String, List<CodeDTO>> codeList = codeService.getGroupedCommCodeList();
        return apiResponse.addResult(Constants.KEY_LIST, codeList);
    }
}
