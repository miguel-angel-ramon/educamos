package es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAlumno;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_PARDIA_ALUPLAN")
public class PardiaAluplan extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_PARDIA_ALUPLAN")
    @SequenceGenerator(name = "SQ_FCT_PARDIA_ALUPLAN", sequenceName = "SQ_FCT_PARDIA_ALUPLAN", allocationSize = 1)
    @Column(name = "ID_PARDIA_ALUPLAN")
    private Long idPardiaAluplan;

    @Column(name = "ID_CONVPROY_ALU", nullable = false)
    private Long idConvProyAlu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PARSEM_ALUPLAN")
    private ParsemAluplan parsemAluplan;

    @Column(name = "F_DIA", nullable = false)
    private Date fDia;

    @Column(name = "TX_OBSERVACIONES")
    private String txObservaciones;

    @Column(name = "NU_HORAS")
    private Integer nuHoras;
}
