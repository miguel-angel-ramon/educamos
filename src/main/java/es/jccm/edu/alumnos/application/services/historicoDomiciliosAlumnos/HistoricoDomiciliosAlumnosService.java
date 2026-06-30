package es.jccm.edu.alumnos.application.services.historicoDomiciliosAlumnos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.historicoDomiciliosAlumno.HistDomiciliosAlumnoRepository;
import es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno.HistoricoDomicilioAlumnoDTO;
import es.jccm.edu.alumnos.application.ports.in.historicoDomiciliosAlumno.IHistoricoDomiciliosAlumno;

@Service
public class HistoricoDomiciliosAlumnosService implements IHistoricoDomiciliosAlumno{
	
	@Autowired 
	private HistDomiciliosAlumnoRepository domiciliosRepository;

	@Override
	public List<HistoricoDomicilioAlumnoDTO> getDomiciliosAlumnosById(Long idAlumno) {
		
		return domiciliosRepository.findDomiciliosAlumnoById(idAlumno);
	}
	

}
