package bangdori.api.product.entity;

import bangdori.api.user.entity.CorpInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TB_PRODUCT_REMARKS_INFO")
@NoArgsConstructor
@AllArgsConstructor
public class ProductRemarksInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO")
    private Long seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROD_NO", nullable = false)
    @JsonBackReference
    private ProductInfo productInfo; // FK → TB_PRODUCT_INFO

    @Column(name = "REMARK_CD", length = 50, nullable = false)
    private String remarkCd;

    @Column(name = "USE_YN", length = 1, nullable = false)
    private String useYn;

    @Column(name = "REG_DTM", nullable = false)
    private LocalDateTime regDtm;

    @Builder
    public ProductRemarksInfo(ProductInfo productInfo, String remarkCd, String useYn, LocalDateTime regDtm) {
        this.productInfo = productInfo;
        this.remarkCd = remarkCd;
        this.useYn = useYn;
        this.regDtm = regDtm;
    }
}
