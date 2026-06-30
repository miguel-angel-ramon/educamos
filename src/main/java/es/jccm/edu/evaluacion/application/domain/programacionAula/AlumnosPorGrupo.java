package es.jccm.edu.evaluacion.application.domain.programacionAula;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AlumnosPorGrupo implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    private Long idGrupo;
   
	private String nombreGrupo;
	
	private Integer numeroAlumnos;

}
