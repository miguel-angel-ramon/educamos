package es.jccm.edu.alumnos.application.domain.conductasContrarias;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLALUMNOS")
public class CCAlumno implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="X_ALUMNO")
	private long id;
	
	@Column(name="T_NOMBRE")
	private String nombre;
	
	@Column(name="T_APELLIDO1")
	private String apellido1;
	
	@Column(name="T_APELLIDO2")
	private String apellido2;
	
	public String getNombreCompleto() {
		return this.getNombre() + " " + this.apellido1 +  " "  + this.apellido2;
	}

}
