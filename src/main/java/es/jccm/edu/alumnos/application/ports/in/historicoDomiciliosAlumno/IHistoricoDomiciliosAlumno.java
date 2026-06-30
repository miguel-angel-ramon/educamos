package es.jccm.edu.alumnos.application.ports.in.historicoDomiciliosAlumno;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno.HistoricoDomicilioAlumnoDTO;

public interface IHistoricoDomiciliosAlumno {
	
	List <HistoricoDomicilioAlumnoDTO> getDomiciliosAlumnosById (Long idAlumno);
	

}
