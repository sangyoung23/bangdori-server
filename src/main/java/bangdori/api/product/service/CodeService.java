package bangdori.api.product.service;

import bangdori.api.product.dto.CodeInfoDto;
import bangdori.api.product.entity.CodeInfo;
import bangdori.api.product.repository.CodeInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeInfoRepository codeInfoRepository;

    public List<Map<String, Object>> getGroupedCommCodeList() {
        List<CodeInfo> codeInfos = codeInfoRepository.findAll();

        // 그룹별로 코드 분류
        Map<String, List<CodeInfoDto>> groupedCodes = codeInfos.stream()
                .collect(Collectors.groupingBy(
                        CodeInfo::getCommCd,  // 그룹화할 키 (XX110, AL001 등)
                        Collectors.mapping(
                                codeInfo -> new CodeInfoDto(
                                        codeInfo.getCommCd(),
                                        codeInfo.getDtlCd(),
                                        codeInfo.getCdNm()
                                ),
                                Collectors.toList()
                        )
                ));

        // Map<String, List<CodeInfoDto>>를 List<Map<String, Object>>로 변환
        return groupedCodes.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put(entry.getKey(), entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
