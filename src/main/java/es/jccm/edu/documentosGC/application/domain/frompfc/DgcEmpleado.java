package es.jccm.edu.documentosGC.application.domain.frompfc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name="TLEMPLEADOS")
public class DgcEmpleado implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_EMPLEADO")
	private Long id;

	@NotBlank
	private String apellido1;

	@NotBlank
	private String apellido2;

	@NotBlank
	private String nombre;

	@NotBlank
	@Column(name="C_NUMIDE")
	private String dniEmpleado;

	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;

	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;

	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;

	@Column(name="F_CREACION")
	private Date fechaCreacion;

	@Column(name="L_ACTIVO")
	private String esActivo;

	// ---------- Relationships -----------
	
}