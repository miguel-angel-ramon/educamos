package es.jccm.edu.alumnos.application.domain.familiasAlumnado;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class FamiliaAlumnado implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idFamilia;
	
	private String numideTutor1;
	
	private String nombreTutor1;
	
	private Long idTutor1;
	
	private String numideTutor2;
	
	private String nombreTutor2;
	
	private Long idTutor2;
	
	private String direccion;
	
	private String telefono;
	
	private String tfnoUrgencias;
	
	private String domicilioDe;
	
	
	
	
	
	
	
	

}
