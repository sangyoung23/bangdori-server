package bangdori.api.product.dto;

import bangdori.api.product.entity.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ProductDTO {

    private Long prodNo;
    private String tradeType;
    private String title;
    private String type;
    private String entrancePwd;
    private String unitPwd;
    private String phoneNo1;
    private String phoneNo2;
    private String unitNo;
    private String etc;
    private String deposit;
    private String monthlyRent;
    private String depositAndMonthlyRent;
    private String salePrice;
    private String depositTotal;
    private String rentTotal;
    private String premiumFee;
    private String premiumYn;
    private String directionCd;
    private String rcmCd;
    private String roomCd;
    private String bathCd;
    private String moveInCd;
    private String prodAddr;
    private String prodRoadAddr;
    private String prodDtlAddr;
    private LocalDateTime chgDtm;

    public static ProductDTO fromEntity(ProductInfo productInfo) {
        return new ProductDTO(
                productInfo.getProdNo(),
                productInfo.getTradeType(),
                productInfo.getTitle(),
                productInfo.getType(),
                productInfo.getEntrancePwd(),
                productInfo.getUnitPwd(),
                productInfo.getPhoneNo1(),
                productInfo.getPhoneNo2(),
                productInfo.getUnitNo(),
                productInfo.getEtc(),
                productInfo.getDeposit(),
                productInfo.getMonthlyRent(),
                "보" + productInfo.getDeposit() + "만" + " / " +  "월" + productInfo.getMonthlyRent() + "만",
                productInfo.getSalePrice(),
                productInfo.getDepositTotal(),
                productInfo.getRentTotal(),
                productInfo.getPremiumFee(),
                productInfo.getPremiumYn(),
                productInfo.getDirectionCd(),
                productInfo.getRcmCd(),
                productInfo.getRoomCd(),
                productInfo.getBathCd(),
                productInfo.getMoveInCd(),
                productInfo.getProdAddr(),
                productInfo.getProdRoadAddr(),
                productInfo.getProdDtlAddr(),
                productInfo.getChgDtm()
        );
    }
}