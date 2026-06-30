package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class MateriaAlumnoEvaluacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idMateria;
	
	private String nombreMateria;
	
	@OneToMany(mappedBy = "idConvCentroOmc")
	private List<CalificacionMateriaAlumnoEvaluacion> calificaciones;
	
	private Long idMatMatriAlu;
	
	private Long idEstado;
	
	private String nombreEstado;

	private Long acnee;

	private String nivelCurricular;

	private String materiaAdap;
	
	private Boolean estaPonderada;
	
	private Boolean existenCompetencias;
	
}
