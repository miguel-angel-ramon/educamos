package es.jccm.edu.simulacion.application.domain.usuarios.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TLPTOTRAEMP", schema = "DELPHOS")
@Data
@EqualsAndHashCode(callSuper = true)
public class PuestoTrabajoEmpleadoGenerico extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "SEQ_TLPTOTRAEMP", sequenceName = "SEQ_TLPTOTRAEMP", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TLPTOTRAEMP")
    @EmbeddedId
    private PuestoTrabajoEmpleadoGenericoPK id;

    @Column(name = "X_CENTRO", nullable = false)
    private Long idCentro;

    @Column(name = "X_PUESTO", nullable = false)
    private Long idPuesto;

    @Column(name = "X_TIPOPER", nullable = false)
    private Long idTipoPersonal;

    @Column(name = "F_CESE")
    private Date fechaCese;

    @Column(name = "T_ABREV")
    private String abrev;

    @Column(name = "X_EMPLEADO_SUSTITUYE")
    private Long idEmpleadoSustituye;

    @Column(name = "F_TOMAPOS_SUSTITUYE")
    private Date fechaTomaPosesionSustituye;

    @Column(name = "L_PROSIR", nullable = false)
    private String prosir;

    @Column(name = "F_TOMAPOSREA", nullable = false)
    private Date fechaTomaPosesionReal;

    @Column(name = "X_ORGDEPPUE")
    private Long idOrgDepPue;

    @Column(name = "X_EMPSIR")
    private Long idEmpSIR;

    @Column(name = "L_USUARIO")
    private String crearUsuario;

    @Column(name = "X_LOCALIDAD")
    private Long idLocalidad;

    @Column(name = "C_TIPOPUESTO", nullable = false)
    private String tipoPuesto;
    
}
