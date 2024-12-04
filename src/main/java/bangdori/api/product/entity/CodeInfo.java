package bangdori.api.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_code_info")
public class CodeInfo {

    @Id // 유니크 키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가(옵션 선택 가능)
    @Column(name = "SEQ_NO", nullable = false)
    private Long seqNo;

    @Column(name = "COMM_CD", length = 10, nullable = false)
    private String commCd;

    @Column(name = "DTL_CD", length = 10, nullable = false)
    private String dtlCd;

    @Column(name = "CD_NM", length = 50, nullable = false)
    private String cdNm;

    @Column(name = "ORD")
    private Integer ord;

    @Column(name = "USE_YN", length = 1, nullable = false)
    private String useYn;
}
