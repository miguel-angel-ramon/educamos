package es.jccm.edu.alumnos.application.domain.evaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Convocatoria implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idConvocatoria;
	
	private String nombre;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private String tipoConvocatoria;
	
	private String estado;
	
}
