package es.jccm.edu.proyectosfct.application.services.programasPFE.modulosPFE;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.modulosPFE.ModulosFPERepository;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ModulosProgPFE;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.modulosPFE.IModulosFPE;

@Service
public class ModulosFPEService implements IModulosFPE {

	@Autowired
	private ModelMapper modelMapper;
	
    @Autowired
    private ModulosFPERepository modulosFPERepository;
	
	@Override
	public List<ElementoSelect> getModulosDisponibles(Long idProgramaFPE, Long idCurso, Long idCentro, Integer cAnno) {
		
		List<ElementoSelectProjection> modulos = modulosFPERepository.getModulosDisponibles(idProgramaFPE,idCurso,idCentro,cAnno);

		return modulos.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ElementoSelect> getModulosSeleccionados(Long idProgramaFPE) {
		
		List<ElementoSelectProjection> modulos = modulosFPERepository.getModulosSeleccionados(idProgramaFPE);

		return modulos.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	
	@Override
	public void saveModulosFPE(Long idProgramaFPE, List<Long> modulos) {
		
		List<ModulosProgPFE> modulosAnt =  modulosFPERepository.findByIdProgramaFPE(idProgramaFPE);
		
		if (!modulos.isEmpty()) {
			 modulosFPERepository.deleteAll(modulosAnt);
		} 
		
		
	   if (modulos.size()>0) {
		   List<ModulosProgPFE> entidades = modulos.stream()
		            .map(idMod -> {
		                ModulosProgPFE m = new ModulosProgPFE();
		                m.setIdProgramaFPE(idProgramaFPE);
		                m.setIdModulo(idMod);
		                return m;
		            })
		            .collect(Collectors.toList());        
		        
		        modulosFPERepository.saveAll(entidades);		   
	   }	
		
	}
	

}
