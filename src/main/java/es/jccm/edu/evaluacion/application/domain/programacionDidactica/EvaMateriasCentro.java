package es.jccm.edu.evaluacion.application.domain.programacionDidactica;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class EvaMateriasCentro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idMateriaOmg;
   
	private String nombreMateria;
	
	private String descMateria;
	
	private String nombreCurso;
	
	private String abrevMateria;
	
	
}