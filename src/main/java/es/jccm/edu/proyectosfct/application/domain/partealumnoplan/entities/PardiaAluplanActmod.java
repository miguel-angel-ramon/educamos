package es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities;

import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ActividadesModulos;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "FCT_PARDIA_ALUPLAN_ACTMOD")
public class PardiaAluplanActmod extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_PARDIA_ALUPLAN_ACTMOD")
    @SequenceGenerator(name = "SQ_FCT_PARDIA_ALUPLAN_ACTMOD", sequenceName = "SQ_FCT_PARDIA_ALUPLAN_ACTMOD", allocationSize = 1)
    @Column(name = "ID_PARDIA_ALUPLAN_ACTMOD")
    private Long idPardiaAluplanActmod;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PARDIA_ALUPLAN", nullable = false)
    private PardiaAluplan pardiaAluplan;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ACTIVIDAD_MODULO", nullable = false)
    private ActividadesModulos actividadModulo;
}
