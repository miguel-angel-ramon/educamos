package es.jccm.edu.alumnos.adapter.out.repository.historicoDomiciliosAlumno;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno.HistoricoDomicilioAlumnoDTO;

public interface HistDomiciliosAlumnoRepository {
	
	List <HistoricoDomicilioAlumnoDTO> findDomiciliosAlumnoById (Long idAlumno);

}
