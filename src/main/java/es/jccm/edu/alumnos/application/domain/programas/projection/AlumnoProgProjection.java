package es.jccm.edu.alumnos.application.domain.programas.projection;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.programas.MateriaPrograma;

public interface AlumnoProgProjection {

	Long getId();
	Long getIdMatricula();
	String getNombre();
	String getApellido1();
	String getApellido2();
	String getUnidad();
	String getEstado();
	int getResultado();
	//List <MateriaPrograma> getMaterias();
}
