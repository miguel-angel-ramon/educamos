package es.jccm.edu.evaluacion.application.domain.materiasProfesor;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class MateriasProfesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idMateria;/////

   
	private String abreviatura;
	
	
	private String descripcion;
}