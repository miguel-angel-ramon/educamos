package es.jccm.edu.proyectosfct.application.services.datosprograma;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.datosprograma.DatosProgramaFctRepository;
import es.jccm.edu.proyectosfct.application.domain.datosprograma.DatosProgramaFct;
import es.jccm.edu.proyectosfct.application.ports.in.datosprograma.IDatosProgramaFctService;
import es.jccm.edu.proyectosfct.application.services.programas.ProgramaFctService;

@Service
public class DatosProgramaFctService implements IDatosProgramaFctService  {
	
	@Autowired
	private DatosProgramaFctRepository datosProgramaFctRepository;	
	
	@Autowired
	private ProgramaFctService programaService;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<DatosProgramaFct> createDatosPrograma(List<DatosProgramaFct> listDatosProgramaIn, Long idPrograma ) {
		List<DatosProgramaFct> datosProgramaSave = new ArrayList<>();
				
		deleteActividades(idPrograma);
		
		listDatosProgramaIn.forEach(datosProgramaIn ->{
			
			Optional<DatosProgramaFct> datosPrograma = datosProgramaFctRepository.findById(datosProgramaIn.getId());
			if(datosPrograma.isPresent()){
				datosPrograma.get().setActividadFormativo(datosProgramaIn.getActividadFormativo());
				datosPrograma.get().setActividadEvaluacion(datosProgramaIn.getActividadEvaluacion());
				datosPrograma.get().setResultado(datosProgramaIn.getResultado());
				datosPrograma.get().setCriterios(datosProgramaIn.getCriterios());
				
				datosProgramaSave.add(datosProgramaFctRepository.save(datosPrograma.get()));
			}else {
				
				if(datosProgramaIn.getPrograma() != null && datosProgramaIn.getPrograma().getId() != 0) {
					datosProgramaIn.setPrograma(programaService.getProgramaId(datosProgramaIn.getPrograma().getId()));
					List<DatosProgramaFct> programas = datosProgramaFctRepository.findAllByProgramaIdOrderByOrden(listDatosProgramaIn.get(0).getPrograma().getId());
					datosProgramaIn.setOrden(programas.size()+1);
					datosProgramaSave.add(datosProgramaFctRepository.save(datosProgramaIn));
				}
			}
		});
		
		return datosProgramaSave;	
	}

	private void deleteActividades(Long idPrograma) {
		
		List<DatosProgramaFct> datosProgramaant = datosProgramaFctRepository.findAllByProgramaIdOrderByOrden(idPrograma);		
		
		datosProgramaant.forEach(datosPrograma ->{
			datosProgramaFctRepository.deleteById(datosPrograma.getId());
		});
	}

	@Override
	public void deleteDatosPrograma(Long id) {
		
		Optional<DatosProgramaFct> datosPrograma = datosProgramaFctRepository.findById(id);
		datosProgramaFctRepository.deleteById(id);
		actualizarOrden(datosPrograma.get().getPrograma().getId());
	}
	
	private void actualizarOrden(Long idPrograma){
		List<DatosProgramaFct> datosProgramaList = datosProgramaFctRepository.findAllByProgramaIdOrderByOrden(idPrograma);
		
		List<DatosProgramaFct> datosProgramaListAux = datosProgramaList.stream().collect(Collectors.toList());
		
		for(int i = 0; i < datosProgramaList.size(); i++) {
			DatosProgramaFct datosPrograma = datosProgramaList.get(i);
			datosPrograma.setOrden(i+1);
			datosProgramaListAux.add(datosPrograma);
		}
		
		datosProgramaFctRepository.saveAll(datosProgramaListAux);
	}

	@Override
	public List<DatosProgramaFct> getActividadesPrograma(Long idPrograma) {
		return datosProgramaFctRepository.findAllByProgramaIdOrderByOrden(idPrograma);
	}

	@Override
	public Integer getCountDatosPrograma(Long idPrograma) {
		return datosProgramaFctRepository.countByProgramaId(idPrograma);
	}	
	
}
