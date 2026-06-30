package es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_RESULTADOSA_ACTIVIDADES")
public class ResultadosAprendizajeActividades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_RESULTADOSA_ACTIVIDADES")
    @SequenceGenerator(name = "SQ_FCT_RESULTADOSA_ACTIVIDADES", sequenceName = "SQ_FCT_RESULTADOSA_ACTIVIDADES", allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_RESULTADOA_ACTIVIDAD")
    private Long idResultadoaActividad;

    @Column(name = "ID_ACTIVIDAD_MODULO")
    private Long idActividadModulo;

    @Column(name = "ID_RESULTADOA_MODULO")
    private Long idResultadoaModulo;

    @Column(name = "NU_ORDEN")
    private Integer nuOrden;

    @Column(name = "C_USUCREACION")
    private Long cUsuCreacion;

    @Column(name = "F_CREACION")
    private Date fCreacion;

    @Column(name = "C_USUACTUALIZA")
    private Long cUsuActualiza;

    @Column(name = "F_ACTUALIZA")
    private Date fActualiza;
}
