package bangdori.api.domain.code.dto;

import bangdori.api.domain.code.entity.CodeInfo;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class CodeDTO {
    // DTO로 변환 할 컬럼 정의
    private String commCd;
    private String dtlCd;
    private String cdNm;

    // 실제로 엔티티를 DTO로 변환하는 메서드
    public static CodeDTO fromEntity(CodeInfo codeInfo) {
        return new CodeDTO(
                codeInfo.getCommCd(),
                codeInfo.getDtlCd(),
                codeInfo.getCdNm()
        );
    }


}