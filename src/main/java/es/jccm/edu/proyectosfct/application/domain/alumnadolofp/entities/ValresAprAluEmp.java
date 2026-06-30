package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_VALRESAPRALUEMP")
public class ValresAprAluEmp extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_VALRESAPRALUEMP")
    @SequenceGenerator(name = "SQ_FCT_VALRESAPRALUEMP", sequenceName = "SQ_FCT_VALRESAPRALUEMP", allocationSize = 1)
    @Column(name = "ID_VALRESAPRALUEMP", nullable = false)
    private Long idValresAprAluEmp;

    @Column(name = "X_EMPRESA", nullable = false)
    private Long xEmpresa;

    @Column(name = "X_COMESP", nullable = false)
    private Long xComesp;

    @Column(name = "X_CALIFICA", nullable = false)
    private Long xCalifica;

    @Column(name = "X_MATMATRICULA", nullable = false)
    private Long xMatMatricula;

    @Column(name = "TX_MOTIVACION", length = 4000, nullable = false)
    private String txMotivacion;
}
