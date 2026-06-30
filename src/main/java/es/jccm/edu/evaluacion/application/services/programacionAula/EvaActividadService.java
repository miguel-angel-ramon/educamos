package es.jccm.edu.evaluacion.application.services.programacionAula;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.CriterioPesoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaActividadRepository;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaActividadService;

@Service
public class EvaActividadService implements IEvaActividadService {

    @Autowired
    private EvaActividadRepository actividadRepository;

    @Autowired
    private ModelMapper modelMapper;

	@Override
	public EvaActividad getActividadById(Long idActividad) {
		Optional<EvaActividad> actividad = actividadRepository.findById(idActividad);
		if(actividad.isPresent()) {
			return actividad.get();
		} else {
			return null;
		}
	}

	@Override
	public List<ActividadDTO> findActividadesByUnidadProgramacionAndConvocatoria(Long idUnidadProgramacion,	Long idConvOmc) {
		List<ActividadDTO> actividadesDTO = new ArrayList<>();
		List<CriterioPesoDTO> criteriosPesos = new ArrayList<>();
		List<EvaActividad> actividades = actividadRepository.findAllByUnidadProgramacionIdAndConvCentroOmcId(idUnidadProgramacion, idConvOmc);
		
		for(EvaActividad actividad : actividades) {
			ActividadDTO act = modelMapper.map(actividad, ActividadDTO.class);
			if(actividad.getRelacionesActividadCriterios() != null && !actividad.getRelacionesActividadCriterios().isEmpty()) {
				List<CriterioEvaluacionDTO> criteriosActividad = new ArrayList<>();
				for(EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion : actividad.getRelacionesActividadCriterios()) {
					CriterioEvaluacionDTO criterio = (modelMapper.map(relacionActividadCriterioEvaluacion.getCriterioEvaluacion(),CriterioEvaluacionDTO.class));
					/*Para Calificación de actividades*/
					criterio.setPeso(relacionActividadCriterioEvaluacion.getPeso());
					criteriosActividad.add(criterio);
					
					/*Procedimiento habitual*/
					CriterioPesoDTO critPeso = new CriterioPesoDTO();
					critPeso.setIdCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getId());
					critPeso.setAbrevCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getAbreviatura());
					critPeso.setPeso(relacionActividadCriterioEvaluacion.getPeso());
					criteriosPesos.add(critPeso);
				}
				criteriosActividad.sort(Comparator.comparing(CriterioEvaluacionDTO::getOrden));
				act.setCriteriosEvaluacion(criteriosActividad);
				act.setCriteriosEvaluacionIds(criteriosActividad.stream().map(CriterioEvaluacionDTO::getId).collect(Collectors.toList()));
			} else {
				act.setCriteriosEvaluacion(new ArrayList<>());
				act.setCriteriosEvaluacionIds(new ArrayList<>());
			}
			act.setCriteriosPesos(criteriosPesos);
			actividadesDTO.add(act);
		}
		return actividadesDTO;
	}
	
	@Override
	public List<ActividadDTO> findActividadesByUnidadProgramacionAndConvocatoriaAndProgramacionAula(Long idUnidadProgramacion,	Long idConvCentroOmc, Long idProgramacionAula) {
		List<ActividadDTO> actividadesDTO = new ArrayList<>();
		List<CriterioPesoDTO> criteriosPesos = new ArrayList<>();
		List<EvaActividad> actividades = new ArrayList<>();

		if(idUnidadProgramacion == -1) {
			actividades = actividadRepository.findActividadesByConvCentroAndidProgAula(idProgramacionAula, idConvCentroOmc);
		} else if (idConvCentroOmc == -1) {
			actividades = actividadRepository.findAllByUnidadProgramacionIdAndProgramacionAulaId(idUnidadProgramacion, idProgramacionAula);
		} else {
			actividades = actividadRepository.findAllByUnidadProgramacionIdAndConvCentroOmcIdAndProgramacionAulaId(idUnidadProgramacion, idConvCentroOmc, idProgramacionAula);
		}


		for(EvaActividad actividad : actividades) {
			ActividadDTO act = modelMapper.map(actividad, ActividadDTO.class);
			if(actividad.getConvCentroOmc() != null && actividad.getConvCentroOmc().getConvocatoriaCentro() != null) {
				act.setDescripcionConvocatoria(actividad.getConvCentroOmc().getConvocatoriaCentro().getDescripcionConvocatoria());
			}
			if(actividad.getRelacionesActividadCriterios() != null && !actividad.getRelacionesActividadCriterios().isEmpty()) {
				List<CriterioEvaluacionDTO> criteriosActividad = new ArrayList<>();
				for(EvaRelacionActividadCriterioEvaluacion relacionActividadCriterioEvaluacion : actividad.getRelacionesActividadCriterios()) {
					CriterioEvaluacionDTO criterio = (modelMapper.map(relacionActividadCriterioEvaluacion.getCriterioEvaluacion(),CriterioEvaluacionDTO.class));
					/*Para Calificación de actividades*/
					criterio.setPeso(relacionActividadCriterioEvaluacion.getPeso());
					criteriosActividad.add(criterio);
					
					/*Procedimiento habitual*/
					CriterioPesoDTO critPeso = new CriterioPesoDTO();
					critPeso.setIdCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getId());
					critPeso.setAbrevCriterio(relacionActividadCriterioEvaluacion.getCriterioEvaluacion().getAbreviatura());
					critPeso.setPeso(relacionActividadCriterioEvaluacion.getPeso());
					criteriosPesos.add(critPeso);
				}
				criteriosActividad.sort(Comparator.comparing(CriterioEvaluacionDTO::getIdCompetenciaEspecifica, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(CriterioEvaluacionDTO::getOrden, Comparator.nullsLast(Comparator.naturalOrder())));
				act.setCriteriosEvaluacion(criteriosActividad);
				act.setCriteriosEvaluacionIds(criteriosActividad.stream().map(CriterioEvaluacionDTO::getId).collect(Collectors.toList()));
			} else {
				act.setCriteriosEvaluacion(new ArrayList<>());
				act.setCriteriosEvaluacionIds(new ArrayList<>());
			}
			act.setCriteriosPesos(criteriosPesos);
			actividadesDTO.add(act);
		}
		return actividadesDTO;
	}

	@Transactional
	@Override
	public void deleteActividadById(Long idActividad) {
		actividadRepository.deleteById(idActividad);
	}
}