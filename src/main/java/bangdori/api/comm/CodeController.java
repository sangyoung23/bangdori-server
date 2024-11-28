package bangdori.api.comm;

import bangdori.api.product.dto.CodeInfoDto;
import bangdori.api.comm.ApiResponse;
import bangdori.api.product.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("comm/code")
public class CodeController {

    private final CodeService codeService;

    @GetMapping("/comms")
    public ApiResponse getCommCodeList(@RequestParam HashMap<String, Object> params) {
        List<Map<String, Object>> groupedCodeList = codeService.getGroupedCommCodeList();

        return new ApiResponse().addResult(groupedCodeList);
    }
}
