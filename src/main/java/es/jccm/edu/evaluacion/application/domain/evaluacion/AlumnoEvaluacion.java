package es.jccm.edu.evaluacion.application.domain.evaluacion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

@Entity
@Data
public class AlumnoEvaluacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idAlumno;
	
	private Long idMatricula;
	
	private Long idMatMatriAlu;

	private String numEscolar;

	private String nombre;

	private String apellidos;

	private byte[] foto;

	private Long idNotAlu;

	private Long idCalifica;

	private Float nota;

	private String descCal;

	private String aprueba;
	
	private Long disabled;

	private Boolean materiaLlavePendiente = false;

	private Long idMateriaOmg;

	@OneToMany(mappedBy = "idCompetencia")
	private List<CompetenciaAlumno> competencias;
}
