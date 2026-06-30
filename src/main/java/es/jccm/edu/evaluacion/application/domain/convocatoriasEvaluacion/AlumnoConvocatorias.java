package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class AlumnoConvocatorias implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idAlumno;
	
	private Long idMatricula;
	
	private Long idMatMatriAlu;

	private String numEscolar;

	private String nombre;

	private String apellidos;
	
	private Long idPromocion;
	
	private Long idEstado;
	
	private String descripcionEstado;
	
	private String fechaSesion;

}
