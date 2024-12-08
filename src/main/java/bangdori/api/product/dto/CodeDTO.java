package bangdori.api.product.dto;

import bangdori.api.product.entity.CodeInfo;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Getter
    @Setter
    public static class ProductImageInfoDTO {

        private Long seqNo; // 일련번호
        private Long prodNo; // 매물 고유번호 (외래키)
        private String managementFileName; // 관리 파일명
        private String realFileName; // 실제 파일명
        private String useYn; // 사용 여부
        private LocalDateTime regDtm; // 등록 일시
    }
}