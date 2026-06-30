package es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosCurso;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_RESULTADOSA_MODULOS")
public class ResultadosAsociadosPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_RESULTADOSA_MODULOS")
    @SequenceGenerator(name = "SQ_FCT_RESULTADOSA_MODULOS", sequenceName = "SQ_FCT_RESULTADOSA_MODULOS", allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_RESULTADOA_MODULO")
    private Long idResultadoaModulo;

    @Column(name = "LG_CENTRO")
    private Integer lg_centro;

    @Column(name = "LG_EMPRESA")
    private Integer lg_empresa;

    @Column(name = "C_USUCREACION")
    private Long cUsuCreacion;

    @Column(name = "F_CREACION")
    private Date fCreacion;

    @Column(name = "C_USUACTUALIZA")
    private Long cUsuActualiza;

    @Column(name = "F_ACTUALIZA")
    private Date fActualiza;

    @Column(name = "X_COMESP")
    private Long x_comesp;

    // ---------- Relaciones -----------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MODULO_CURSO", referencedColumnName = "ID_MODULO_CURSO")
    private ModulosCurso modulosCurso;
}