package es.jccm.edu.proyectosfct.application.ports.in.empresas;

import java.util.List;
import org.springframework.data.domain.Page;
import es.jccm.edu.proyectosfct.application.domain.empresas.Empresa;
import es.jccm.edu.proyectosfct.application.domain.empresas.EmpresaEmpleadosList;
import es.jccm.edu.proyectosfct.application.domain.empresas.EmpresaList;
import es.jccm.edu.proyectosfct.application.domain.empresas.TipoEmpresa;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;


public interface IEmpresasService {
	
	// Create
	
	Empresa createEmpresa(Empresa empresaDto);
	
	// Read
	
	List<Empresa> getAllEmpresas();	
	
	Page<Empresa> getAllEmpresas(Integer page, Integer numItems);
	
	Page<Empresa> getPageEmpresasByName(String name, Integer page);
	
	Long countEmpresas();
	
	Empresa getEmpresaById(Long idEmpresa);
	
	List<TipoEmpresa> getTiposEmpresa(Long idEmpresa);
	
	// Update
	
	Empresa updateEmpresa(Empresa empresa);
	
	// Delete
	
	void deleteEmpresa(Long idEmpresa);

	List<EmpresaList> getEmpresas();
	
	List<EmpresaList> getEmpresasFamiliaProvincia(Long idFamilia,Long idProvincia,String dPais,Integer conConvenio);

	List<Familia> getFamiliasEmpresa(Long idEmpresa);
	
	List<EmpresaEmpleadosList> getEmpleadosEmpresa(Long idEmpresa);

	List<EmpresaList> getListadoEmpresasByEmpleadoCentro(Long id_tutorfctdual, 
														 Long idCentro, 
														 Integer cAnno, 
														 Integer tipoEmpresa,
														 Long idPerfil,
														 Long idCentroCombo,
														 Long idProvincia,
														 Long idUsuario, 
														 Long idEmpleadoComunica);

	List<EmpresaList> getListadoEmpresas();

}
