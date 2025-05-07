package bangdori.api.domain.product.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TB_PRODUCT_IMG_INFO")
@NoArgsConstructor
public class ProductImageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO")
    private Long seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROD_NO", nullable = false)
    private ProductInfo productInfo; // Foreign Key - 매물 정보

    @Column(name = "MNG_FILE_NM", length = 255, nullable = false)
    private String managementFileName; // 관리 파일명

    @Column(name = "REAL_FILE_NM", length = 255, nullable = false)
    private String realFileName; // 실제 파일명

    @Column(name = "USE_YN", length = 1, nullable = false)
    private String useYn; // 사용 여부

    @Column(name = "REG_DTM", nullable = false)
    private LocalDateTime regDtm; // 등록 일시

    // DTO로부터 엔티티를 생성하는 fromDto 메서드
    public static ProductImageInfo fromDto(ProductImageInfo dto, ProductInfo productInfo) {
        return ProductImageInfo.builder()
                .productInfo(productInfo)
                .managementFileName(dto.getManagementFileName())
                .realFileName(dto.getRealFileName())
                .useYn(dto.getUseYn())
                .regDtm(LocalDateTime.now()) // 등록 일시 설정
                .build();
    }

    @Builder
    public ProductImageInfo(Long seqNo, ProductInfo productInfo, String managementFileName,
                            String realFileName, String useYn, LocalDateTime regDtm) {
        this.seqNo = seqNo;
        this.productInfo = productInfo;
        this.managementFileName = managementFileName;
        this.realFileName = realFileName;
        this.useYn = useYn;
        this.regDtm = regDtm;
    }

    public void updateUseYn(String useYn) {
        this.useYn = useYn;
    }
}
