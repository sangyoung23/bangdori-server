package bangdori.api.product.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_code_info")
public class CodeInfo {

    @Id // 유니크 키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가(옵션 선택 가능)
    @Column(name = "SEQ_NO")
    private Long seqNo;

    @Column(name = "COMM_CD", length = 10)
    private String commCd;

    @Column(name = "DTL_CD", length = 10)
    private String dtlCd;

    @Column(name = "CD_NM", length = 50)
    private String cdNm;

    @Column(name = "ORD", length = 10)
    private String ord;

    @Column(name = "USE_YN", length = 1, nullable = false)
    private String useYn;
}
