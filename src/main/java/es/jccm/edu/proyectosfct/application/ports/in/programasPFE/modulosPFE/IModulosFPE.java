package es.jccm.edu.proyectosfct.application.ports.in.programasPFE.modulosPFE;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;

public interface IModulosFPE {

	List<ElementoSelect> getModulosDisponibles(Long idProgramaFPE, Long idCurso, Long idCentro, Integer cAnno);

	List<ElementoSelect> getModulosSeleccionados(Long idProgramaFPE);

	void saveModulosFPE(Long idProgramaFPE, List<Long> modulos);

}
