package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "FCT_INFO_ADICIONAL_PLAN")
public class InfoAdicionalPlan extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_INFO_ADICIONAL_PLAN")
    @SequenceGenerator(
            name = "SQ_FCT_INFO_ADICIONAL_PLAN",
            sequenceName = "SQ_FCT_INFO_ADICIONAL_PLAN",
            initialValue = 1,
            allocationSize = 1,
            schema = "DELPHOS"
    )
    @Column(name = "ID_INFO_ADICIONAL_PLAN")
    private Long id;

    @Column(name = "ID_PROYECTO", nullable = false)
    private Long idProyecto;

    @Column(name = "DS_DESCRIPCION", length = 4000)
    private String descripcion;

    @Column(name = "TX_CALENDARIO_HORARIO", length = 4000)
    private String calendarioHorario;

    @Column(name = "TX_RESULTADO_PREVISTO", length = 4000)
    private String resultadoPrevisto;

    @Column(name = "TX_CONTENIDOS_DESARROLLAR", length = 4000)
    private String contenidosDesarrollar;

    @Column(name = "TX_ACTIVIDADES_FORMATIVAS", length = 4000)
    private String actividadesFormativas;

    @Lob
    @Column(name = "TX_MECANISMO_COORDINACION_SEGUIMIENTO", length = 4000)
    private String mecanismoCoordinacionSeguimiento;
}