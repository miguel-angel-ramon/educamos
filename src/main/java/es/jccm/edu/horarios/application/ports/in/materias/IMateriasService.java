package es.jccm.edu.horarios.application.ports.in.materias;

import java.util.List;

import es.jccm.edu.horarios.application.domain.materias.MateriaList;


public interface IMateriasService {
	
	List<MateriaList> getMaterias(String idUsuario, Integer anno);

}
