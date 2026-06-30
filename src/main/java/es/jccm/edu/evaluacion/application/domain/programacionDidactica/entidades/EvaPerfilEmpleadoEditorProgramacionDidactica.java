package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "EVA_EMPEDITPROGDID", schema = "DELPHOS")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EvaPerfilEmpleadoEditorProgramacionDidactica extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 345L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_EMPEDITPROGDID")
	@SequenceGenerator(name = "SQ_EVA_EMPEDITPROGDID", sequenceName = "SQ_EVA_EMPEDITPROGDID", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_EMPEDITPROGDID")
    private Long id;

    @NotNull
    @Column(name = "X_EMPLEADO")
    private Long idEmpleado;

    @NotNull
    @Column(name = "X_OFERTAMATRIG")
    private Long idOfertaMatrig;

    @NotNull
    @Column(name = "X_MATERIAOMG")
    private Long idMateriaOmg;

    @NotNull
    @Column(name = "X_CENTRO")
    private Long idCentro;

    @NotNull
    @Column(name = "NU_ANNO")
    private Integer anno;

    @NotNull
    @Column(name = "F_INICIO")
    private Date fechaInicio;

    @Column(name = "F_FIN")
    private Date fechaFin;

    @NotNull
    @Column(name = "LG_ACTIVO", columnDefinition = "NUMBER(1,0) DEFAULT 1")
    private Integer activo = 1;

    @Column(name = "X_NIVEADAP")
    private Long niveAdap;

    @Column(name = "X_MATERIAOMGADAP")
    private Long idMateriaOmgAdap;
}

