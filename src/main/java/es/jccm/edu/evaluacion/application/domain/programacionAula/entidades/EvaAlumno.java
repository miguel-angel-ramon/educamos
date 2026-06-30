package es.jccm.edu.evaluacion.application.domain.programacionAula.entidades;

import es.jccm.edu.evaluacion.application.domain.Constantes;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TLALUMNOS", schema = "DELPHOS")
public class EvaAlumno extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_ALUMNO")
	private Long id;
	
	@NotBlank
	@Size(max=25, message = "No puede superar los 25 caracteres")
	@Column(name="T_NOMBRE")
	private String nombre;
	
	@NotBlank
	@Size(max=25, message = "No puede superar los 25 caracteres")
	@Column(name="T_APELLIDO1")
	private String apellido1;
	
	@Size(max=25, message = "No puede superar los 25 caracteres")
	@Column(name="T_APELLIDO2")
	private String apellido2;
	
	@NotBlank
	@Size(max=12, message = "No puede superar los 12 caracteres")
	@Column(name="C_NUMESCOLAR")
	private String numEscolar;
	
	@Transient
	private String nombreCompleto;
	
	public String getNombreCompleto() {
		this.nombreCompleto = this.getNombre().concat(Constantes.ESPACIO).concat(this.getApellido1());
		if(StringUtils.isNotBlank(this.getApellido2())) {
			this.nombreCompleto.concat(Constantes.ESPACIO).concat(this.getApellido2());
		}
		return this.nombreCompleto;
	}

}
