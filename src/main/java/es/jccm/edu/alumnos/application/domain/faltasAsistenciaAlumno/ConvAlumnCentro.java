package es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ConvAlumnCentro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long indice;
	
	private String descripcion;
	
	private String fecinicon;
	
	private String fecfincon;
}
