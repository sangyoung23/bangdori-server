package bangdori.api.comm.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

//import javax.persistence.*;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity  // 객체와 테이블 매핑
@Table(name = "TB_CODE_INFO")
public class CodeInfo {

    @Id  // Primary Key 지정
    @Column(name = "COMM_CD")
    private int commCd;

    @Id  // Primary Key 지정
    @Column(name = "DTL_CD")
    private int dtlCd;

    @NotNull
    @Column(name = "CD_NM")
    private String cdNM;

    @Column(name = "ORD")
    private String ord;

    @NotNull
    @Column(name = "USE_YN")
    @ColumnDefault("1")
    private String useYn;

}
