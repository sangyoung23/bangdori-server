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
    private ProductInfo productInfo;

    @Column(name = "MNG_FILE_NM", length = 255, nullable = false)
    private String managementFileName;

    @Column(name = "REAL_FILE_NM", length = 255, nullable = false)
    private String realFileName;

    @Column(name = "USE_YN", length = 1, nullable = false)
    private String useYn;

    @Column(name = "REG_DTM", nullable = false)
    private LocalDateTime regDtm;

    public static ProductImageInfo fromDto(ProductImageInfo dto, ProductInfo productInfo) {
        return ProductImageInfo.builder()
                .productInfo(productInfo)
                .managementFileName(dto.getManagementFileName())
                .realFileName(dto.getRealFileName())
                .useYn(dto.getUseYn())
                .regDtm(LocalDateTime.now())
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
