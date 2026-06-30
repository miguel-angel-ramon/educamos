package es.jccm.edu.proyectosfct.application.domain.datosalumnosplan.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_DATOSALU_PLAN")
public class DatosAlumnoPlan extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_DATOSALU_PLAN")
    @SequenceGenerator(name = "SQ_FCT_DATOSALU_PLAN", sequenceName = "SQ_FCT_DATOSALU_PLAN", allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_DATOSALU_PLAN")
    private Long idDatosAluPlan;

    @Column(name = "X_MATRICULA")
    private Long xMatricula;

    @Column(name = "LG_PRL")
    private Integer lgPrl;

    @Column(name = "LG_CERTIFICACION")
    private Integer lgCertificacion;

    @Column(name = "DS_CERTIFICACION")
    private String dsCertificacion;

    @Column(name = "LG_ADAPTACIONES")
    private Integer lgAdaptaciones;

    @Column(name = "DS_ADAPTACIONES")
    private String dsAdaptaciones;

    @Column(name = "LG_AUTORIZACIONES")
    private Integer lgAutorizaciones;

    @Column(name = "DS_AUTORIZACIONES")
    private String dsAutorizaciones;

    @Column(name = "DS_OBSERVACIONES")
    private String dsObservaciones;
}
