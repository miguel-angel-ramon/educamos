package es.jccm.edu.ausencias.application.ports.in.profesores;

import java.util.List;

import es.jccm.edu.ausencias.application.domain.profesores.AusenciasProfesores;

public interface IAusenciasProfesores {

	List<AusenciasProfesores> getAusenciasProfesores(Long codCentro, Integer anno, String fecha);
}
