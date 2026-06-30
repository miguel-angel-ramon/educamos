package es.jccm.edu.ausencias.application.ports.in.guardias;

import java.util.List;

import es.jccm.edu.ausencias.application.domain.guardias.DatosProfesoresGuardias;
import es.jccm.edu.ausencias.application.domain.guardias.GuardiasProfesores;

public interface IGuardiasProfesores {

	List<GuardiasProfesores> getGuardiasProfesores(Long codCentro, Integer anno);
	
	List<DatosProfesoresGuardias> getDatosProfesoresGuardias(Long codCentro, Long idTramo, Integer diaSemana);
	
}
