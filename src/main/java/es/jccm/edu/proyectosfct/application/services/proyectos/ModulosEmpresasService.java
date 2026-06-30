package es.jccm.edu.proyectosfct.application.services.proyectos;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Optional;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.ModulosEmpresasRepository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosEmpresas;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IModulosEmpresasService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModulosEmpresasService implements IModulosEmpresasService {

	@Autowired
	private ModulosEmpresasRepository modulosEmpresasRepository;
	
	@Override
	public ModulosEmpresas createM(ModulosEmpresas moduloEmpresaIn) {
		return modulosEmpresasRepository.save(moduloEmpresaIn);
	}

	@Override
	@Transactional
	public List<ModulosEmpresas> createModuloEmpresa(List<ModulosEmpresas> modulosEmpresa, Long idConvProy) {

		List<ModulosEmpresas> actual = modulosEmpresasRepository.findAllByConvenioProyectoId(idConvProy);

		borrarModulos(modulosEmpresa, actual);

		añadirModulos(modulosEmpresa, actual);

		return modulosEmpresa;
	}

	private void añadirModulos(List<ModulosEmpresas> modulosEmpresa, List<ModulosEmpresas> actual) {
		// Lista de módulos a añadir
		List<ModulosEmpresas> modulosAnadir = new ArrayList<>();
		for (ModulosEmpresas moduloNuevo : modulosEmpresa) {
			boolean encontrado = false;
			for (ModulosEmpresas moduloActual : actual) {
				if (moduloNuevo.getModuloCurso().getId().equals(moduloActual.getModuloCurso().getId())) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado) {
				modulosAnadir.add(moduloNuevo);
			}
		}

		//Si hay módulos para añadir
		if (!modulosAnadir.isEmpty()) {
			modulosEmpresasRepository.saveAll(modulosAnadir);
		}
	}

	private void borrarModulos(List<ModulosEmpresas> modulosEmpresa, List<ModulosEmpresas> actual) {
		// Lista de módulos a eliminar
		List<ModulosEmpresas> modulosEliminar = new ArrayList<>();
		for (ModulosEmpresas moduloActual : actual) {
			boolean encontrado = false;
			for (ModulosEmpresas nuevoModulo : modulosEmpresa) {
				if (moduloActual.getModuloCurso().getId().equals(nuevoModulo.getModuloCurso().getId())) {
					encontrado = true;
					break;
				}
			}

			if (!encontrado) {
				modulosEliminar.add(moduloActual);
			}
		}

		//Si hay módulos para eliminar
		if (!modulosEliminar.isEmpty()) {
			modulosEmpresasRepository.deleteAll(modulosEliminar);
		}
	}


	@Override
	public Boolean getIsCheckedModuloEmpresa(Long idModCurso, Long idConvProy) {
		Optional<ModulosEmpresas> moduloActividad = modulosEmpresasRepository.findByModuloCursoIdAndConvenioProyectoId(idModCurso, idConvProy);
		
		return  moduloActividad.isPresent() ? true : false;
	}

}
