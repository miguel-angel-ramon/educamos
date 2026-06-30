package es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_ACTIVIDADES_MODULOS")
public class ActividadesModulos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_ACTIVIDADES_MODULOS")
    @SequenceGenerator(name = "SQ_FCT_ACTIVIDADES_MODULOS", sequenceName = "SQ_FCT_ACTIVIDADES_MODULOS", allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_ACTIVIDAD_MODULO")
    private Long idActividadModulo;

    @Column(name = "ID_MODULO_CURSO")
    private Long idModuloCurso;

    @Column(name = "TX_NOMBRE")
    private String txNombre;

    @Column(name = "DS_ACTIVIDAD")
    private String dsActividad;

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

    @Column(name = "TX_ABREV")
    private String txAbrev;

}