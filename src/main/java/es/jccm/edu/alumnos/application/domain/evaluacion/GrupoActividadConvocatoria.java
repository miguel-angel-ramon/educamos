package es.jccm.edu.alumnos.application.domain.evaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class GrupoActividadConvocatoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idGrupoActividad;
	
	private String nombre;
	
	private String abreviatura;
	
	private String estado;
	
	private Long idUnidad;
	
	private Long idEtapa;
	
	private String unidad;
	
	private Long idMateriaOmg;
	
	private Long idOfertaMatrig;
	
	private Long lomloe;
	
	private Long idConvUnidad;

	private String curso;

	private Long cra;
}
