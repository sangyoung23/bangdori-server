package bangdori.api.domain.product.dto;

import bangdori.api.domain.product.entity.ProductInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductDTO {

    private Long prodNo;
    private Long corpNo;
    private String tradeType;
    private String title;
    private String type;
    private String statusCd;
    private String entrancePwd;
    private String unitPwd;
    private String phoneNo1;
    private String phoneNo2;
    private String unitNo;
    private String etc;
    private String deposit;
    private String monthlyRent;
    private String depositAndMonthlyRent;
    private String salePricdAndDepoAndRent;
    private String depositAndMonthlyRentAndPreFee;
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
    private String propertyX;
    private String propertyY;
    private String prodRoadAddr;
    private String prodDtlAddr;
    private Long prodMngUser;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime newDtm;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDtm;

    private Long regUserId;
    private Long chgUserId;

    private List<String> remarkCd;


    public static ProductDTO fromEntity(ProductInfo productInfo, List<String> remarkCd) {
        String deposit = productInfo.getDeposit() != null ? productInfo.getDeposit() : "0";
        String monthlyRent = productInfo.getMonthlyRent() != null ? productInfo.getMonthlyRent() : "0";
        String salePrice = productInfo.getSalePrice() != null ? productInfo.getSalePrice() : "0";
        String depositTotal = productInfo.getDepositTotal() != null ? productInfo.getDepositTotal() : "0";
        String rentTotal = productInfo.getRentTotal() != null ? productInfo.getRentTotal() : "0";
        String premiumFee = productInfo.getPremiumFee() != null ? productInfo.getPremiumFee() : "0";

        return new ProductDTO(
                productInfo.getProdNo(),
                productInfo.getCorpNo(),
                productInfo.getTradeType(),
                productInfo.getTitle(),
                productInfo.getType(),
                productInfo.getStatusCd(),
                productInfo.getEntrancePwd(),
                productInfo.getUnitPwd(),
                productInfo.getPhoneNo1(),
                productInfo.getPhoneNo2(),
                productInfo.getUnitNo(),
                productInfo.getEtc(),
                deposit,
                monthlyRent,
                "보증" + deposit + "만" + " / " +  "월" + monthlyRent + "만",
                "매매" + salePrice + "만" + " / " + "보증" + depositTotal + "만" + " / " + "임대" + rentTotal + "만",
                "보증" + deposit + "만" + " / " +  "월" + monthlyRent + "만" + " / " + "권리" + premiumFee + "만",
                salePrice,
                depositTotal,
                rentTotal,
                premiumFee,
                productInfo.getPremiumYn(),
                productInfo.getDirectionCd(),
                productInfo.getRcmCd(),
                productInfo.getRoomCd(),
                productInfo.getBathCd(),
                productInfo.getMoveInCd(),
                productInfo.getProdAddr(),
                productInfo.getPropertyX(),
                productInfo.getPropertyY(),
                productInfo.getProdRoadAddr(),
                productInfo.getProdDtlAddr(),
                productInfo.getProdMngUser(),
                productInfo.getNewDtm(),
                productInfo.getRegDtm(),
                productInfo.getRegUserId(),
                productInfo.getChgUserId(),
                remarkCd
        );
    }
}
