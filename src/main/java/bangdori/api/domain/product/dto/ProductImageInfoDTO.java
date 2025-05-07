package bangdori.api.domain.product.dto;

import bangdori.api.domain.product.entity.ProductImageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ProductImageInfoDTO {

    private Long seqNo; // 일련번호
    private Long prodNo; // 매물 고유번호 (외래키)
    private String managementFileName; // 관리 파일명
    private String realFileName; // 실제 파일명
    private String useYn; // 사용 여부
    private LocalDateTime regDtm; // 등록 일시

    public static ProductImageInfoDTO fromEntity(ProductImageInfo productImageInfo) {
        return new ProductImageInfoDTO(
                productImageInfo.getSeqNo(),
                productImageInfo.getProductInfo().getProdNo(), // ProductInfo에서 prodNo를 가져옴
                productImageInfo.getManagementFileName(),
                productImageInfo.getRealFileName(),
                productImageInfo.getUseYn(),
                productImageInfo.getRegDtm()
        );
    }
}