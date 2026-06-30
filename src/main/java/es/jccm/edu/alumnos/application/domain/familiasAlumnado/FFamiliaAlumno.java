package es.jccm.edu.alumnos.application.domain.familiasAlumnado;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLFAMILIASALU")
public class FFamiliaAlumno  implements Serializable{
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="X_FAMILIA")
	Long id;
	
	@Column(name="X_TUTOR1")
	Long idTutor1;
	
	@Column(name="X_TUTOR2")
	Long idTutor2;
	
	
	
	

}
