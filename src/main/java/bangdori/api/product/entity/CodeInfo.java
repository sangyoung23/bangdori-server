package bangdori.api.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_code_info")
public class CodeInfo {

    @Id
    @Column(name = "COMM_CD", length = 10)
    private String commCd;

    @Column(name = "DTL_CD", length = 10)
    private String dtlCd;

    @Column(name = "CD_NM", length = 50)
    private String cdNm;

    @Column(name = "ORD", length = 10)
    private String ord;

    @Column(name = "USE_YN", length = 1)
    private String useYn;
}
