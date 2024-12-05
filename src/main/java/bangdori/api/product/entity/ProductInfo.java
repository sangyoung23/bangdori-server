package bangdori.api.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TB_PRODUCT_INFO")
public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROD_NO")
    private Long prodNo;

    @Column(name = "TRADE_TYPE", length = 50, nullable = false)
    private String tradeType;

    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    @Column(name = "TYPE", length = 10, nullable = false)
    private String type;

    @Column(name = "STATUS_CD", length = 50, nullable = false)
    private String statusCd;

    @Column(name = "ENTRANCE_PWD", length = 10, nullable = false)
    private String entrancePwd;

    @Column(name = "UNIT_PWD", length = 10, nullable = false)
    private String unitPwd;

    @Column(name = "PHONE_NO1", length = 100)
    private String phoneNo1;

    @Column(name = "PHONE_NO2", length = 100)
    private String phoneNo2;

    @Column(name = "UNIT_NO", length = 10, nullable = false)
    private String unitNo;

    @Column(name = "ETC", columnDefinition = "TEXT")
    private String etc;

    @Column(name = "DEPOSIT", length = 100)
    private String deposit;

    @Column(name = "MONTHLY_RENT", length = 100)
    private String monthlyRent;

    @Column(name = "SALE_PRICE", length = 100)
    private String salePrice;

    @Column(name = "DEPOSIT_TOTAL", length = 100)
    private String depositTotal;

    @Column(name = "RENT_TOTAL", length = 100)
    private String rentTotal;

    @Column(name = "PREMIUM_FEE", length = 100)
    private String premiumFee;

    @Column(name = "PREMIUM_YN", length = 100)
    private String premiumYn;

    @Column(name = "DIRECTION_CD", length = 10)
    private String directionCd;

    @Column(name = "RCM_CD", length = 10)
    private String rcmCd;

    @Column(name = "ROOM_CD", length = 10)
    private String roomCd;

    @Column(name = "BATH_CD", length = 10)
    private String bathCd;

    @Column(name = "MOVE_IN_CD", length = 10)
    private String moveInCd;

    @Column(name = "PROD_ADDR", length = 100)
    private String prodAddr;

    @Column(name = "PROD_ROAD_ADDR", length = 100)
    private String prodRoadAddr;

    @Column(name = "PROD_DTL_ADDR", length = 100)
    private String prodDtlAddr;

    @Column(name = "NEW_DTM", nullable = false)
    private LocalDateTime newDtm;

    @Column(name = "USE_YN", length = 10, nullable = false)
    private String useYn;

    @Column(name = "RMK", columnDefinition = "TEXT")
    private String rmk;

    @Column(name = "REG_USER_ID", nullable = false)
    private Long regUserId;

    @Column(name = "REG_DTM", nullable = false)
    private LocalDateTime regDtm;

    @Column(name = "CHG_USER_ID", nullable = false)
    private Long chgUserId;

    @Column(name = "CHG_DTM")
    private LocalDateTime chgDtm;
}