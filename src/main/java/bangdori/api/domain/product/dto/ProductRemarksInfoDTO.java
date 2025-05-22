package bangdori.api.domain.product.dto;

import bangdori.api.domain.product.entity.ProductRemarksInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ProductRemarksInfoDTO {

    private Long seqNo;
    private Long prodNo;
    private String remarkCd;
    private String useYn;
    private LocalDateTime regDtm;

    public static ProductRemarksInfoDTO fromEntity(ProductRemarksInfo productRemarksInfo) {
        return new ProductRemarksInfoDTO(
                productRemarksInfo.getSeqNo(),
                productRemarksInfo.getProductInfo().getProdNo(),
                productRemarksInfo.getRemarkCd(),
                productRemarksInfo.getUseYn(),
                productRemarksInfo.getRegDtm()
        );
    }
}
