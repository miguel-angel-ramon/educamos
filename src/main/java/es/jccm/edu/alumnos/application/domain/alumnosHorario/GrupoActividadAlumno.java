package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class GrupoActividadAlumno implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idGrupoActividad;
	
	private String nombre;

	private Long idMateriaOmg;
	
	private String observacion;
}
