package es.jccm.edu.proyectosfct.application.services.datosproyectos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.datosproyectos.DatosProyectosFctRepository;
import es.jccm.edu.proyectosfct.application.domain.datosproyecto.DatosProyectosFct;
import es.jccm.edu.proyectosfct.application.ports.in.datosproyectos.IDatosProyectosFctService;
import es.jccm.edu.proyectosfct.application.services.proyectos.ProyectosService;

@Service
public class DatosProyectosFctService implements IDatosProyectosFctService  {
	
	@Autowired
	private DatosProyectosFctRepository datosProyectoFctRepository;	
	
	@Autowired
	private ProyectosService proyectosService;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<DatosProyectosFct> createDatosProyecto(List<DatosProyectosFct> listDatosProyectoIn) {
		List<DatosProyectosFct> datosProyectoSave = new ArrayList<>();
		
		listDatosProyectoIn.forEach(datosProyectoIn ->{
			Optional<DatosProyectosFct> datosPrograma = datosProyectoFctRepository.findById(datosProyectoIn.getId());
			if(datosPrograma.isPresent()){
				datosPrograma.get().setActividadFormativo(datosProyectoIn.getActividadFormativo());
				datosPrograma.get().setActividadEvaluacion(datosProyectoIn.getActividadEvaluacion());
				datosPrograma.get().setResultado(datosProyectoIn.getResultado());
				datosPrograma.get().setCriterios(datosProyectoIn.getCriterios());
				
				datosProyectoSave.add(datosProyectoFctRepository.save(datosPrograma.get()));
			}else {
				if(datosProyectoIn.getProyecto() != null && datosProyectoIn.getProyecto().getId() != 0) {
					datosProyectoIn.setProyecto(proyectosService.getProyectoId(datosProyectoIn.getProyecto().getId()));
					datosProyectoSave.add(datosProyectoFctRepository.save(datosProyectoIn));
				}
			}
		});
		
		return datosProyectoSave;	
	}

	@Override
	public void deleteDatosProyecto(Long id) {
		Optional<DatosProyectosFct> datosProyectos = datosProyectoFctRepository.findById(id);
		datosProyectoFctRepository.deleteById(id);
		actualizarOrden(datosProyectos.get().getProyecto().getId());
	}
	
	private void actualizarOrden(Long idProyecto){
		List<DatosProyectosFct> datosProyectosList = datosProyectoFctRepository.findAllByProyectoIdOrderByOrden(idProyecto);
		
		List<DatosProyectosFct> datosProyectosListAux = datosProyectosList.stream().collect(Collectors.toList());
		
		for(int i = 0; i < datosProyectosList.size(); i++) {
			DatosProyectosFct datosPrograma = datosProyectosList.get(i);
			datosPrograma.setOrden(i+1);
			datosProyectosListAux.add(datosPrograma);
		}
		
		datosProyectoFctRepository.saveAll(datosProyectosListAux);
	}

	@Override
	public List<DatosProyectosFct> getActividadesProyecto(Long idProyecto) {
		return datosProyectoFctRepository.findAllByProyectoIdOrderByOrden(idProyecto);
	}

	@Override
	public Integer getCountDatosProyecto(Long idProyecto) {
		return datosProyectoFctRepository.countByProyectoId(idProyecto);
	}	
	
}
