package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AlumnoEvalConv implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idAlumno;
	
	private Long idMatricula;
	
	private Long idGrupoActividad;
	
	private Long idOfertaMatrig;
	
	private Long anno;
	
	private String numEscolar;

	private String nombre;

	private String apellidos;
	
	private String nombreCurso;
	
	private byte[] foto;
	
	private Integer numMaterias;
	
	private Long idPromocion;
	
	private Long idEstado;
	
	private String descripcionEstado;
	
	private String fechaSesion;
	
	private Long cResultado;
	
	private Long idEtapa;
	
	private String descripcionEtapa;

	private Integer acnee;

	private String nivelCurricular;

}
