package es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLTUTORES")
public class TutorHistDom implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="X_TUTOR")
	private Long id;
	
	@Column(name="T_NOMBRE")
	private String nombre;
	
	@Column(name="T_APELLIDO1")
	private String apellido1;
	
	@Column(name="T_APELLIDO2")
	private String apellido2;
	

	

}
