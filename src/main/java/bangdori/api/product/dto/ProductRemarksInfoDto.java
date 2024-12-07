package bangdori.api.product.dto;

import bangdori.api.product.entity.ProductRemarksInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ProductRemarksInfoDto {

    private Long seqNo;        // 일련번호
    private Long prodNo;       // 매물 정보 고유 번호
    private String remarkCd;   // 특이사항 코드
    private String useYn;      // 사용여부
    private LocalDateTime regDtm; // 등록일시

    // 엔티티에서 DTO로 변환하는 메서드
    public static ProductRemarksInfoDto fromEntity(ProductRemarksInfo productRemarksInfo) {
        return new ProductRemarksInfoDto(
                productRemarksInfo.getSeqNo(),
                productRemarksInfo.getProductInfo().getProdNo(),  // ProductInfo에서 prodNo를 가져옴
                productRemarksInfo.getRemarkCd(),
                productRemarksInfo.getUseYn(),
                productRemarksInfo.getRegDtm()
        );
    }
}
