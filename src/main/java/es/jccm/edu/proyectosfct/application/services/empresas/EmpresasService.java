package es.jccm.edu.proyectosfct.application.services.empresas;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import es.jccm.edu.proyectosfct.adapter.out.repositories.empresas.EmpresaRepository;
import es.jccm.edu.proyectosfct.application.domain.empresas.Empresa;
import es.jccm.edu.proyectosfct.application.domain.empresas.EmpresaEmpleadosList;
import es.jccm.edu.proyectosfct.application.domain.empresas.EmpresaList;
import es.jccm.edu.proyectosfct.application.domain.empresas.TipoEmpresa;
import es.jccm.edu.proyectosfct.application.domain.empresas.projection.EmpresaEmpleadosProjection;
import es.jccm.edu.proyectosfct.application.domain.empresas.projection.EmpresaProjection;
import es.jccm.edu.proyectosfct.application.domain.empresas.projection.TipoEmpresaProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.FamiliaProjection;
import es.jccm.edu.proyectosfct.application.ports.in.empresas.IEmpresasService;
import es.jccm.edu.proyectosfct.application.services.Utiles;

@Service
public class EmpresasService implements IEmpresasService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final Long ID_PERFIL_GESTOR = 11207L;
	private static final Long ID_PERFIL_DELEGACION_FCT = 13207L;
	private static final Long ID_PERFIL_DELEGACION_FCT_1 = 15207L;
	private static final Long ID_PERFIL_ALUMNADO = 5000L;

	@Autowired
	ModelMapper modelMapper;

	// Create

	public Empresa createEmpresa(Empresa empresa) {
		//Validations.validationObject(empresa);
		//Validations.validationLondId(empresa.getId());
		return empresaRepository.save(empresa);
	}

	// Read

	public List<EmpresaList> getEmpresas() {
		List<EmpresaProjection> empresas = empresaRepository.findAllEmpresas();
		if (empresas == null || empresas.isEmpty()) {
			String mes = crearMessage(EmpresaProjection.class.getSimpleName(), "Todo Empresa");
			throw new NotFoundException(mes);
		}

		return empresas.stream().map(entity -> modelMapper.map(entity, EmpresaList.class)).collect(Collectors.toList());
	}

	public List<EmpresaList> getEmpresasFamiliaProvincia(Long idFamilia, Long idProvincia,String dPais,Integer conConvenio) {
		//Validations.validationLondId(idFamilia);
		List<EmpresaProjection> empresas = empresaRepository.findAllEmpresasProvincia(idFamilia, idProvincia,dPais, conConvenio);
		//if (empresas == null || empresas.isEmpty()) {
		//	//String mes = crearMessage(EmpresaList.class.getSimpleName(), idFamilia.toString());
			//throw new NotFoundException(mes);
		//}
		return empresas.stream().map(entity -> modelMapper.map(entity, EmpresaList.class)).collect(Collectors.toList());
	}
	
	public List<EmpresaList> getListadoEmpresasByEmpleadoCentro(Long id_tutorfctdual, 
																Long idCentro, 
																Integer cAnno, 
																Integer tipoEmpresa,
																Long idPerfil,
																Long idCentroCombo,
																Long idProvincia,
																Long idUsuario,
																Long idEmpleadoComunica
																) {
		List<EmpresaProjection> empresas = null ;
		
		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_DELEGACION_FCT.equals(idPerfil) || ID_PERFIL_DELEGACION_FCT_1.equals(idPerfil)) {
			
			
			if (ID_PERFIL_GESTOR.equals(idPerfil)) {
				
				empresas = empresaRepository.findAllEmpresasEmpleadoDelegacion(id_tutorfctdual, 
																			   idCentro, 
																			   cAnno, 
																			   tipoEmpresa,
																			   idCentroCombo,
																			   idUsuario,
																			   idPerfil);				
			} else {
				
				empresas = empresaRepository.findAllEmpresasEmpleadoDelegacionProvincias(id_tutorfctdual, 
																					     idCentro, 
																					     cAnno, 
																					     tipoEmpresa,
																					     idCentroCombo,
																					     idProvincia);
			}  

			
		} else {
			
			
			if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {
				
				empresas = empresaRepository.findAllEmpresasEmpleadoCentroAlumno(idCentro, cAnno, tipoEmpresa, idEmpleadoComunica);
				
			} else {
				
				empresas = empresaRepository.findAllEmpresasEmpleadoCentro(id_tutorfctdual, idCentro, cAnno, tipoEmpresa, idUsuario);
				
			}
			
			
		}
		
		
		
		return empresas.stream().map(entity -> modelMapper.map(entity, EmpresaList.class)).collect(Collectors.toList());
	}

	
	
	public List<Empresa> getAllEmpresas() {
		List<Empresa> empresas = empresaRepository.findByEmpresaActiva("S");
		if (empresas == null || empresas.isEmpty()) {
			String mes = crearMessage(Empresa.class.getSimpleName(), "Empresa Activa");
			throw new NotFoundException(mes);
		}
		return empresas;
	}

	public Page<Empresa> getAllEmpresas(Integer page, Integer numItems) {
		Pageable paging = PageRequest.of(page, numItems, Sort.by("nombreEmpresa"));
		// return empresaRepository.findByEmpresaActiva("S", paging);
		return null;
	}

	public Page<Empresa> getPageEmpresasByName(String name, Integer page) {
		Pageable paging = PageRequest.of(page, 10, Sort.by("nombreEmpresa"));
		Page<Empresa> empresa = empresaRepository.findByNombreEmpresaContainingIgnoreCaseAndEmpresaActiva(name, "S",
				paging);
		if (empresa == null || empresa.isEmpty()) {
			String mes = crearMessage(Empresa.class.getSimpleName(), name);
			throw new NotFoundException(mes);
		}
		return empresa;
	}

	public Long countEmpresas() {
		return empresaRepository.countByEmpresas();
	}

	public Empresa getEmpresaById(Long idEmpresa) {
		//Validations.validationLondId(idEmpresa);
		Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);

		if (!empresa.isPresent()) {
			String mes = crearMessage(Empresa.class.getSimpleName(), idEmpresa.toString());
			throw new NotFoundException(mes);
		}

		return empresa.get();
	}

	@Override
	public List<TipoEmpresa> getTiposEmpresa(Long idEmpresa) {
		//Validations.validationLondId(idEmpresa);
		List<TipoEmpresaProjection> tiposEmpresaP = empresaRepository.getTiposEmpresas(idEmpresa);
		if (tiposEmpresaP == null || tiposEmpresaP.isEmpty()) {
			String mes = crearMessage(TipoEmpresa.class.getSimpleName(), idEmpresa.toString());
			throw new NotFoundException(mes);
		}
		return tiposEmpresaP.stream().map(entity -> modelMapper.map(entity, TipoEmpresa.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<Familia> getFamiliasEmpresa(Long idEmpresa) {
		//Validations.validationLondId(idEmpresa);
		List<FamiliaProjection> familias = empresaRepository.getFamiliasEmpresa(idEmpresa);
		if (familias == null || familias.isEmpty()) {
			String mes = crearMessage(Familia.class.getSimpleName(), idEmpresa.toString());
			throw new NotFoundException(mes);
		}
		return familias.stream().map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());
	}

	// Update

	public Empresa updateEmpresa(Empresa empresa) {
		//Validations.validationObject(empresa);

		Empresa empresaOrigen = getEmpresaById(empresa.getId());
		if (empresaOrigen == null) {
			String mes = crearMessage(Familia.class.getSimpleName(), empresa.getId().toString());
			throw new NotFoundException(mes);
		}
		BeanUtils.copyProperties(empresa, empresaOrigen, Utiles.getNullPropertyNames(empresa));
		empresaOrigen = empresaRepository.save(empresaOrigen);

		return empresaOrigen;
	}

	// Delete

	@Transactional
	public void deleteEmpresa(Long idEmpresa) {
		//Validations.validationLondId(idEmpresa);
		empresaRepository.deleteEmpresaById(idEmpresa);
	}

	@Override
	public List<EmpresaEmpleadosList> getEmpleadosEmpresa(Long idEmpresa) {
		
		List<EmpresaEmpleadosProjection> empresasEmpleados =  empresaRepository.findAllEmpleadosEmpresa(idEmpresa);
		
		return empresasEmpleados.stream().map(entity -> modelMapper.map(entity, EmpresaEmpleadosList.class)).collect(Collectors.toList());
	}
	
	public List<EmpresaList> getListadoEmpresas(){
		List<EmpresaProjection> empresas = empresaRepository.FindAllEmpresasListado();
		 return empresas.stream().map(entity -> modelMapper.map(entity, EmpresaList.class)).collect(Collectors.toList());
	}
	
	// Other methods
	private String crearMessage(String nameClass, String id) {
		return "No se ha encontrado el objeto relacionado con " + nameClass + " para el parámetro (" + id + ")\"";
	}

}
