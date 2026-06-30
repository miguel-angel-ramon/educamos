package es.jccm.edu.evaluacion.application.domain.ponderacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class MateriasUnidad implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idMateria;

	private Long idGrupoActividad;
	
	private String txMateria;
	
	private Long idUnidad;
	
	private String txUnidad;
	
	private Long idModalidad;
	
	private String txModalidad;

	private Long idCurso;

	private String txCurso;

	private Long idCiclo;

	private String txCiclo;

	private Long idEtapa;

	private String txEtapa;
	
	private Boolean estaPonderada;
	
	private Boolean existenCompetencias;
}
