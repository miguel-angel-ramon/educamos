package es.jccm.edu.proyectosfct.application.domain.tareasSincronizadas.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "FCT_TAREAS_SINCRONIZADAS")
public class TareaSincronizada extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_TAREAS_SINCRONIZADAS")
    @SequenceGenerator(
            name = "SQ_FCT_TAREAS_SINCRONIZADAS",
            sequenceName = "SQ_FCT_TAREAS_SINCRONIZADAS",
            allocationSize = 1
    )
    @Column(name = "ID_TAREA")
    private Long idTarea;

    @Column(name = "DS_NOMBRE_TAREA", nullable = false)
    private String nombreTarea;

    @Column(name = "LG_EN_EJECUCION")
    private Integer enEjecucion;

    @Column(name = "F_INICIO_EJECUCION")
    private Date inicioEjecucion;

    @Column(name = "F_FIN_EJECUCION")
    private Date finEjecucion;

}
