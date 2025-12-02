package bangdori.api.domain.product.dto;

import bangdori.api.domain.product.entity.ProductImageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ProductImageInfoDTO {

    private Long seqNo;
    private Long prodNo;
    private String managementFileName;
    private String realFileName;
    private String useYn;
    private LocalDateTime regDtm;

    public static ProductImageInfoDTO fromEntity(ProductImageInfo productImageInfo) {
        return new ProductImageInfoDTO(
                productImageInfo.getSeqNo(),
                productImageInfo.getProductInfo().getProdNo(),
                productImageInfo.getManagementFileName(),
                productImageInfo.getRealFileName(),
                productImageInfo.getUseYn(),
                productImageInfo.getRegDtm()
        );
    }
}