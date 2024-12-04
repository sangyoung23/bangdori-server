package bangdori.api.product.service;

import bangdori.api.product.dto.CodeDTO;
import bangdori.api.product.repository.CodeInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeInfoRepository codeInfoRepository;

    public Map<String, List<CodeDTO>> getGroupedCommCodeList() {
        return codeInfoRepository.findAllByUseYnOrderByOrdAsc("1").stream()
                .map(CodeDTO::fromEntity)
                .collect(Collectors.groupingBy(CodeDTO::getCommCd));
    }
}
