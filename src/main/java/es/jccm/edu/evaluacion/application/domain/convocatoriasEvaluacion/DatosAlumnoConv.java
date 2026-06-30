package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class DatosAlumnoConv implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idAlumno;
	
	private Long idMatricula;
	
	private Long idMatMatriAlu;

	private Long idGrupoActividad;

	private Long idOfertaMatrig;

	private String numEscolar;

	private String nombre;

	private String apellidos;

	private String nota;

	private String aprueba;

	private byte[] foto;

	private Long anno;

	private Long idUnidad;

	private Long idMateria;

	private String materia;

	private String nombreCurso;

	private Long idConvCentroOmc;

	private String convocatoria;

	private String estadoConv;

}
