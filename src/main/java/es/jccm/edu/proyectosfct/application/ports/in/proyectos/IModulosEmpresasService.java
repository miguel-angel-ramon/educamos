package es.jccm.edu.proyectosfct.application.ports.in.proyectos;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosEmpresas;

public interface IModulosEmpresasService {
	
	// Create

	ModulosEmpresas createM(ModulosEmpresas moduloEmpresaIn);

	Boolean getIsCheckedModuloEmpresa(Long idModCurso, Long idConvProy);

	List<ModulosEmpresas> createModuloEmpresa(List<ModulosEmpresas> moduloempresas, Long idConvProy);
	
	

}
