package es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Table(name = "TLCALIFICACIONES", schema = "DELPHOS")
public class EvaCalificacion extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "X_CALIFICA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLS_CALXCALIFICA")
	@SequenceGenerator(name = "TLS_CALXCALIFICA", sequenceName = "TLS_CALXCALIFICA", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	private Long id;

	@Size(max = 1, message = "No puede superar 1 caracter")
	@Column(name="C_CODCALSENU")
	private String codigoSenu;

	@NotBlank
	@Size(max = 80, message = "No puede superar los 80 caracteres")
	@Column(name="D_CALIFICA")
	private String descripcion;
	
	@NotBlank
	@Size(max = 1, message = "No puede superar 1 caracter")
	@Column(name = "L_APRUEBA", columnDefinition = "VARCHAR2(1) DEFAULT 'S'")
	private String lAprueba;
	
	@NotBlank
	@Size(max = 1, message = "No puede superar 1 caracter")
	@Column(name = "L_MATCERRADA", columnDefinition = "VARCHAR2(1) DEFAULT 'N'")
	private String lMatriculaCerrada;
	
	@NotBlank
	@Size(max = 1, message = "No puede superar 1 caracter")
	@Column(name = "L_RENUNCIA", columnDefinition = "VARCHAR2(1) DEFAULT 'N'")
	private String lRenuncia;
	
	@Column(name = "N_NUMERO")
	private Integer numero;
	
	@NotNull
	@Column(name = "N_ORDEN")
	private Integer orden;

	@NotBlank
	@Size(max = 30, message = "No puede superar los 30 caracteres")
	@Column(name="S_CALIFICA")
	private String descripcionCorta;
	
	@NotBlank
	@Size(max = 5, message = "No puede superar los 5 caracteres")
	@Column(name="T_ABREV")
	private String abreviatura;

	// ---------- Relationships -----------

	@Column(name="X_SISTCAL")
	private Long sistema;
}