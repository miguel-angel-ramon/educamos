package es.jccm.edu.alumnos.application.ports.in.familiasAlumnado;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.familiasAlumnado.FamiliaAlumnado;

public interface IFamiliasCentroService {
	
	
	List<FamiliaAlumnado>getFamiliasAlumnadoCentro(Long idCentro, int annio);
	
}
