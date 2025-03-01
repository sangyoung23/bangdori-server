package bangdori.api.product.entity;

import bangdori.api.product.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "TB_PRODUCT_INFO")
@NoArgsConstructor
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

    @Column(name = "PROPERTY_X", length = 100)
    private String propertyX;

    @Column(name = "PROPERTY_Y", length = 100)
    private String propertyY;

    @Column(name = "PROD_ROAD_ADDR", length = 100)
    private String prodRoadAddr;

    @Column(name = "PROD_DTL_ADDR", length = 100)
    private String prodDtlAddr;

    @Column(name = "PROD_MNG_USER", nullable = false)
    private Long prodMngUser;

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

    @OneToMany(mappedBy = "productInfo", fetch = FetchType.LAZY)
    @JsonBackReference  // 순환 참조 방지
    private List<ProductRemarksInfo> productRemarksInfos;

    // ProductDto로부터 ProductInfo 엔티티를 생성하는 @Builder를 사용한 fromDto 메서드
    public static ProductInfo fromDto(ProductDTO dto) {
        return ProductInfo.builder()
                //.prodNo(dto.getProdNo()) //insert 할때 오류나는데...
                .tradeType(dto.getTradeType())
                .title(dto.getTitle())
                .type(dto.getType())
                .statusCd(dto.getStatusCd())
                .entrancePwd(dto.getEntrancePwd())
                .unitPwd(dto.getUnitPwd())
                .phoneNo1(dto.getPhoneNo1())
                .phoneNo2(dto.getPhoneNo2())
                .unitNo(dto.getUnitNo())
                .etc(dto.getEtc())
                .deposit(dto.getDeposit())
                .monthlyRent(dto.getMonthlyRent())
                .salePrice(dto.getSalePrice())
                .depositTotal(dto.getDepositTotal())
                .rentTotal(dto.getRentTotal())
                .premiumFee(dto.getPremiumFee())
                .premiumYn(dto.getPremiumYn())
                .directionCd(dto.getDirectionCd())
                .rcmCd(dto.getRcmCd())
                .roomCd(dto.getRoomCd())
                .bathCd(dto.getBathCd())
                .moveInCd(dto.getMoveInCd())
                .prodAddr(dto.getProdAddr())
                .propertyX(dto.getPropertyX())
                .propertyY(dto.getPropertyY())
                .prodRoadAddr(dto.getProdRoadAddr())
                .prodDtlAddr(dto.getProdDtlAddr())
                .prodMngUser(dto.getProdMngUser())
                .newDtm(LocalDateTime.now())
                .useYn("1") // 기본값 설정
                .regDtm(LocalDateTime.now())
                .chgUserId(dto.getChgUserId())
                .regUserId(dto.getRegUserId())// 등록일시 설정
                .build();
    }

    @Builder
    public ProductInfo(Long prodNo, String tradeType, String title, String type, String statusCd,
                       String entrancePwd, String unitPwd, String phoneNo1, String phoneNo2,
                       String unitNo, String etc, String deposit, String monthlyRent,
                       String salePrice, String depositTotal, String rentTotal, String premiumFee,
                       String premiumYn, String directionCd, String rcmCd, String roomCd,
                       String bathCd, String moveInCd, String prodAddr, String propertyX, String propertyY, String prodRoadAddr,
                       String prodDtlAddr,Long prodMngUser , LocalDateTime newDtm, String useYn, String rmk,
                       Long regUserId, LocalDateTime regDtm, Long chgUserId, LocalDateTime chgDtm) {
        this.prodNo = prodNo;
        this.tradeType = tradeType;
        this.title = title;
        this.type = type;
        this.statusCd = statusCd;
        this.entrancePwd = entrancePwd;
        this.unitPwd = unitPwd;
        this.phoneNo1 = phoneNo1;
        this.phoneNo2 = phoneNo2;
        this.unitNo = unitNo;
        this.etc = etc;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.salePrice = salePrice;
        this.depositTotal = depositTotal;
        this.rentTotal = rentTotal;
        this.premiumFee = premiumFee;
        this.premiumYn = premiumYn;
        this.directionCd = directionCd;
        this.rcmCd = rcmCd;
        this.roomCd = roomCd;
        this.bathCd = bathCd;
        this.moveInCd = moveInCd;
        this.prodAddr = prodAddr;
        this.propertyX = propertyX;
        this.propertyY = propertyY;
        this.prodRoadAddr = prodRoadAddr;
        this.prodDtlAddr = prodDtlAddr;
        this.prodMngUser = prodMngUser;
        this.newDtm = newDtm;
        this.useYn = useYn;
        this.rmk = rmk;
        this.regUserId = regUserId;
        this.regDtm = regDtm;
        this.chgUserId = chgUserId;
        this.chgDtm = chgDtm;
    }

    public void updateNewDtm() {
        this.newDtm = LocalDateTime.now();
    }

    public void updateChgUserId(Long userNo) {
        this.chgUserId = userNo;
    }

    public  void updateUseYn(String useYn) {
        this.useYn = useYn;
        this.chgDtm = LocalDateTime.now();
    }

    public void updateFromDto(ProductDTO dto) {
        this.tradeType = dto.getTradeType();
        this.title = dto.getTitle();
        this.type = dto.getType();
        this.statusCd = dto.getStatusCd();
        this.entrancePwd = dto.getEntrancePwd();
        this.unitPwd = dto.getUnitPwd();
        this.phoneNo1 = dto.getPhoneNo1();
        this.phoneNo2 = dto.getPhoneNo2();
        this.unitNo = dto.getUnitNo();
        this.etc = dto.getEtc();
        this.deposit = dto.getDeposit();
        this.monthlyRent = dto.getMonthlyRent();
        this.salePrice = dto.getSalePrice();
        this.depositTotal = dto.getDepositTotal();
        this.rentTotal = dto.getRentTotal();
        this.premiumFee = dto.getPremiumFee();
        this.premiumYn = dto.getPremiumYn();
        this.directionCd = dto.getDirectionCd();
        this.rcmCd = dto.getRcmCd();
        this.roomCd = dto.getRoomCd();
        this.bathCd = dto.getBathCd();
        this.moveInCd = dto.getMoveInCd();
        this.prodAddr = dto.getProdAddr();
        this.propertyX = dto.getPropertyX();
        this.propertyY = dto.getPropertyY();
        this.prodRoadAddr = dto.getProdRoadAddr();
        this.prodDtlAddr = dto.getProdDtlAddr();
        this.prodMngUser = dto.getProdMngUser();
        this.newDtm = LocalDateTime.now(); // 변경 시간 업데이트
        this.chgDtm = LocalDateTime.now(); // 변경 시간 업데이트
    }

}