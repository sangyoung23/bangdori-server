package bangdori.api.domain.code.dto;

import bangdori.api.domain.code.entity.CodeInfo;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class CodeDTO {
    private String commCd;
    private String dtlCd;
    private String cdNm;

    public static CodeDTO fromEntity(CodeInfo codeInfo) {
        return new CodeDTO(
                codeInfo.getCommCd(),
                codeInfo.getDtlCd(),
                codeInfo.getCdNm()
        );
    }


}