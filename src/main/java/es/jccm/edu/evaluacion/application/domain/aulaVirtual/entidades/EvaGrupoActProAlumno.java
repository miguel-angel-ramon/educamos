package es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaEmpleado;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TLGRUACTPROALU", schema = "DELPHOS_SEGEDU")
public class EvaGrupoActProAlumno extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "X_GRUACTPROALU")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Size(max = 200, message = "No puede superar los 200 caracteres")
	@Column(name="D_GRUACTPROALU")
	private String descripcion;
	
	@NotBlank
	@Size(max = 40, message = "No puede superar los 40 caracteres")
	@Column(name="S_GRUACTPROALU")
	private String descripcionCorta;
	
	@NotBlank
	@Size(max = 10, message = "No puede superar los 10 caracteres")
	@Column(name="C_GRUACTPROALU")
	private String codigo;
	
	@NotNull
    @Column(name = "F_TOMAPOS")
    private Date tomaPos;
	
	@NotNull
	@Column (name="C_ANNO")
	private Integer  anno;
	
	// Esta actividad es de TLACTIVIDADES (no de EVA_ACTIVIDAD)
	@NotNull
	@Column (name="X_ACTIVIDAD")
	private Long idActividad;

	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPLEADO")
	private EvaEmpleado empleado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CENTRO")
	private EvaCentro centro;
	
	// Esta actividad es de TLACTIVIDADES
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="X_ACTIVIDAD")
//	private EvaActividad actividad;
}