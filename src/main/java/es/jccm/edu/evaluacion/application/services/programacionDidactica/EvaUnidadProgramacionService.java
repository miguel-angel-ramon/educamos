package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.ConvocatoriasDto;
import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.CriterioListDto;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CompetenciaEspecificaDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadProgramacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadesProgramacionCriterioDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ProgramacionAulaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.*;
import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaValoracionCriterioActividadAlumnoRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.ponderacion.PonderacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionActividadCriterioEvaluacionRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.*;
import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.ConvocatoriaProjection;
import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.CompetenciasEspecificasPonderacionProjection;
import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.CriteriosPonderacionProjection;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.*;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaUnidadesProgramacionCriterioProjection;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaUnidadProgramacionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvaUnidadProgramacionService implements IEvaUnidadProgramacionService {

    @Autowired
    private EvaUnidadProgramacionRepository unidadProgramacionRepository;
    
    @Autowired
    private EvaRelacionUnidadProgramacionCriteriosEvaluacionRepository relacionUnidadProgramacionCriteriosEvaluacionRepository;
    
    @Autowired
    private EvaCriterioEvaluacionRepository criterioEvaluacionRepository;
    
    @Autowired
    private EvaProgramacionDidacticaRepository programacionDidacticaRepository;
    
    @Autowired
    private EvaRelacionProgramacionDidacticaUnidadProgramacionRepository relacionProgramacionDidacticaUnidadProgramacionRepository;
    
    @Autowired
    private EvaRelacionCompetenciaEspecificaMateriaRepository relacionCompetenciaEspecificaMateriaRepository;
    
    @Autowired
    private EvaCompetenciaEspecificaDidacticaRepository competenciaEspecificaDidacticaRepository;
    
    @Autowired
	private EvaConvocatoriaCentrosOMCRepository convocatoriaCentrosOmcRepository;

	@Autowired
	private PonderacionRepository ponderacionRepository;
    
    @Autowired
	private EvaRelacionPonderacionCriteriosEvaluacionRepository relacionPonderacionCriteriosEvaluacionRepository;

	@Autowired
	private EvaRelacionUnidadProgramacionSaberBasicoRepository relacionUnidadProgramacionSaberBasicoRepository;
    
    @Autowired
    private ModelMapper modelMapper;

	@Autowired
	EvaRelacionActividadCriterioEvaluacionRepository relacionActividadCriterioEvaluacionRepository;

	@Autowired
	EvaValoracionCriterioActividadAlumnoRepository valoracionCriterioActividadAlumnoRepository;
	@Autowired
	EvaRelacionBloqueSaberBasicoMateriaRepository relacionBloqueMateriaRepository;

	@Autowired
	EvaSaberBasicoRepository saberBasicoRepository;
    
    @Override
	public List<UnidadProgramacionDTO> getUnidadesProgramacion(Long idProgramacionDidactica) {
    	
    	List<UnidadProgramacionDTO> unidadesProgramacionOut = new ArrayList<>();
    	
    	EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findById(idProgramacionDidactica).orElse(null);
	    
	    if (programacionDidactica != null) {
	    	List<EvaRelacionProgramacionDidacticaUnidadProgramacion> relProgDidacUnidadProg = relacionProgramacionDidacticaUnidadProgramacionRepository.findByProgramacionDidactica(programacionDidactica);
	    	List<EvaUnidadProgramacion> unidadesProgramacion = relProgDidacUnidadProg.stream()
	    			.map(x -> x.getUnidadProgramacion())
	    			.sorted(Comparator.comparing(EvaUnidadProgramacion::getOrden)
                    .thenComparing(EvaUnidadProgramacion::getAbreviatura)
                    .thenComparing(EvaUnidadProgramacion::getNombre))
	    			.collect(Collectors.toList());
	    	
	    	unidadesProgramacionOut = unidadesProgramacion.stream().map(x -> modelMapper.map(x, UnidadProgramacionDTO.class)).collect(Collectors.toList());
	    	
	    	for (UnidadProgramacionDTO unidadProgramacion : unidadesProgramacionOut) {
	    		List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> criteriosEvaluacion =
	    				relacionUnidadProgramacionCriteriosEvaluacionRepository.findByUnidadProgramacionId(unidadProgramacion.getId());
	    		List<EvaSaberBasico> saberesBasicos = saberBasicoRepository.findAllByIdUnidadProgramacion(unidadProgramacion.getId());
	    		unidadProgramacion.setCriteriosEvaluacionIds(criteriosEvaluacion.stream().map(x -> x.getCriterioEvaluacion().getId()).collect(Collectors.toList()));
				unidadProgramacion.setCriteriosEvaluacionAbrev(criteriosEvaluacion.stream().map(x -> x.getCriterioEvaluacion().getAbreviatura()).collect(Collectors.toList()));
				unidadProgramacion.setSaberesBasicosIds(saberesBasicos.stream().map(x -> x.getId()).collect(Collectors.toList()));
				unidadProgramacion.setSaberesBasicosAbrev(saberesBasicos.stream().map(x -> x.getAbreviatura()).collect(Collectors.toList()));
	    	}
	    }
	    
	    return unidadesProgramacionOut;
	    
	}
    
	@Override
	@Transactional
	public EvaUnidadProgramacion insertUnidadProgramacion(Long idProgramacionDidactica, Long idConvCentroOmc, UnidadProgramacionDTO unidadProgramacion) {
			
		EvaUnidadProgramacion nuevaUnidadProgramacion = modelMapper.map(unidadProgramacion, EvaUnidadProgramacion.class);
		
		if(idConvCentroOmc != null) {
			EvaConvocatoriaCentrosOMC convocatoria = convocatoriaCentrosOmcRepository.findById(idConvCentroOmc).orElse(null);
			
			if(convocatoria != null) {
				nuevaUnidadProgramacion.setConvCentroOmc(convocatoria);
			}
		}
		
		// Creo la Unidad de programación
		EvaUnidadProgramacion unidadProgramacionGuardada = unidadProgramacionRepository.save(nuevaUnidadProgramacion);
		
		// Recorro el listado de los Ids de los criterios de evaluación que llegan
	    for (Long criterioId : unidadProgramacion.getCriteriosEvaluacionIds()) {
	    	// Por cada Id de criterio de evaluación lo rescato para comprobar que exista dicho Id
	        EvaCriterioEvaluacion criterio = criterioEvaluacionRepository.findById(criterioId).orElse(null);
	        
	        // Si existe el criterio creo la relación entre este y la unidad de programación creada
	        if (criterio != null) {
	        	EvaRelacionUnidadProgramacionCriteriosEvaluacion relUniprogCrieva = new EvaRelacionUnidadProgramacionCriteriosEvaluacion();
	        	relUniprogCrieva.setUnidadProgramacion(unidadProgramacionGuardada);
	            relUniprogCrieva.setCriterioEvaluacion(criterio);

	            relacionUnidadProgramacionCriteriosEvaluacionRepository.save(relUniprogCrieva);
	        }
	    }
	    
	    // Rescato la programación didáctica mediante el id que recibo
	    EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findById(idProgramacionDidactica).orElse(null);
	    
	    // Si existe la programación didáctica creo la relación entre esta y la unidad de programación creada
	    if (programacionDidactica != null) {
	    	EvaRelacionProgramacionDidacticaUnidadProgramacion relProgDidacUniProg = new EvaRelacionProgramacionDidacticaUnidadProgramacion();
        	relProgDidacUniProg.setUnidadProgramacion(unidadProgramacionGuardada);
        	relProgDidacUniProg.setProgramacionDidactica(programacionDidactica);

        	relacionProgramacionDidacticaUnidadProgramacionRepository.save(relProgDidacUniProg);
        }

		// Guardar los saberes de la unidad
		unidadProgramacion.getSaberesBasicos().stream().forEach( saberes -> {
			EvaRelacionUnidadProgramacionSaberBasico relacion = new EvaRelacionUnidadProgramacionSaberBasico();
			EvaSaberBasico saberBasico = modelMapper.map(saberes, EvaSaberBasico.class);
			relacion.setSaberBasico(saberBasico);
			relacion.setUnidadProgramacion(unidadProgramacionGuardada);
			relacionUnidadProgramacionSaberBasicoRepository.save(relacion);
		});
        log.info("se ha insertado exitosamente la unidad de programacion con id= " + unidadProgramacionGuardada.getId()
                + " a la programación didáctica con id= " + idProgramacionDidactica);

        return unidadProgramacionGuardada;
    }
	
	@Override
	@Transactional
	public void updateUnidadProgramacion(Long idConvCentroOmc, UnidadProgramacionDTO unidadProgramacion) {
			
		EvaUnidadProgramacion actualizarUnidadProgramacion = unidadProgramacionRepository.findById(unidadProgramacion.getId()).orElse(null);
			
		if(actualizarUnidadProgramacion != null) {	
		
			if(idConvCentroOmc != null) {
				EvaConvocatoriaCentrosOMC convocatoria = convocatoriaCentrosOmcRepository.findById(idConvCentroOmc).orElse(null);
				
				if(convocatoria != null) {
					actualizarUnidadProgramacion.setConvCentroOmc(convocatoria);
				}
			} else {
				actualizarUnidadProgramacion.setConvCentroOmc(null);
			}
			
			actualizarUnidadProgramacion.setNombre(unidadProgramacion.getNombre());
			actualizarUnidadProgramacion.setAbreviatura(unidadProgramacion.getAbreviatura());
			actualizarUnidadProgramacion.setDescripcion(unidadProgramacion.getDescripcion());
			actualizarUnidadProgramacion.setOrden(unidadProgramacion.getOrden());
			
			// Actualizo la Unidad de programación
			EvaUnidadProgramacion unidadProgramacionActualizada = unidadProgramacionRepository.save(actualizarUnidadProgramacion);
			
			List<Long> criteriosEvaluacionIds = unidadProgramacion.getCriteriosEvaluacionIds();
	
		    // Obtenengo todos los criterios que tiene asociada la unidad de programación antes del update
		    List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> criteriosEvaluacionActuales = 
		    		relacionUnidadProgramacionCriteriosEvaluacionRepository.findByUnidadProgramacionId(unidadProgramacionActualizada.getId());
	
		    // Recorro el listado de los Ids de los criterios de evaluación que llegan
		    for (Long criterioEvaluacionId : criteriosEvaluacionIds) {
		    	// Compruebo si cada Id está ya creado, si no lo encuentra lanzo una excepcion
		    	EvaRelacionUnidadProgramacionCriteriosEvaluacion relacionUnidadProgramacionCriteriosEvaluacion = relacionUnidadProgramacionCriteriosEvaluacionRepository
		    			.findByCriterioEvaluacionIdAndUnidadProgramacionId(criterioEvaluacionId, unidadProgramacionActualizada.getId());
		    	
		    	// Si está creado lo borro del listado de los ya actuales. En caso contrario lo creo
		        if (relacionUnidadProgramacionCriteriosEvaluacion != null) {
		        	criteriosEvaluacionActuales.remove(relacionUnidadProgramacionCriteriosEvaluacion);
		        } else {
		        	EvaCriterioEvaluacion criterio = criterioEvaluacionRepository.findById(criterioEvaluacionId).orElse(null);
			        
			        if (criterio != null) {
			        	EvaRelacionUnidadProgramacionCriteriosEvaluacion relUniprogCrieva = new EvaRelacionUnidadProgramacionCriteriosEvaluacion();
			        	relUniprogCrieva.setUnidadProgramacion(unidadProgramacionActualizada);
			            relUniprogCrieva.setCriterioEvaluacion(criterio);
	
			            relacionUnidadProgramacionCriteriosEvaluacionRepository.save(relUniprogCrieva);
			        }
		        }
		    }
	
		    // Elimino los criterios de evaluación que no han llegado, los que entendemos que ya no están relacionados
		    relacionUnidadProgramacionCriteriosEvaluacionRepository.deleteAll(criteriosEvaluacionActuales);

			//Actializar saberes basicos

			List<SaberBasicoDTO> saberesActuales = getSaberesBasicosByUnidadProgramacion(unidadProgramacion.getId());
			List<EvaSaberBasico> lista;

			if(!unidadProgramacion.getSaberesBasicos().isEmpty()){
				lista = unidadProgramacion.getSaberesBasicos().stream().map(x -> modelMapper.map(x, EvaSaberBasico.class)).collect(Collectors.toList());
				log.info("Saberes: " + lista);
			}

			unidadProgramacionActualizada.setRelacionProgramacionDidacticaUnidadProgramacion(null);

			unidadProgramacion.getSaberesBasicos().stream().forEach(sb -> {
				EvaSaberBasico saber = modelMapper.map(sb, EvaSaberBasico.class);
			//vemos si exite o no
				EvaRelacionUnidadProgramacionSaberBasico relacion = relacionUnidadProgramacionSaberBasicoRepository.findByUnidadProgramacionAndSaberBasico(unidadProgramacionActualizada, saber);
			// si no existe
				if(relacion == null) {
					//insertamos
					EvaRelacionUnidadProgramacionSaberBasico relacionAdd = new EvaRelacionUnidadProgramacionSaberBasico();
					relacionAdd.setUnidadProgramacion(unidadProgramacionActualizada);
					relacionAdd.setSaberBasico(saber);
					relacionUnidadProgramacionSaberBasicoRepository.save(relacionAdd);
				}else{
						SaberBasicoDTO saberDTO = saberesActuales.stream().filter(s -> s.getId().equals(saber.getId())).findFirst().orElse(null);
						saberesActuales.remove(saberDTO);

				}
			});
		//eliminamos los que no estan en la lista
			if(!saberesActuales.isEmpty()) {
				saberesActuales.stream().forEach(saber -> {
					relacionUnidadProgramacionSaberBasicoRepository.
							deleteByUnidadProgramacionAndSaberBasico(unidadProgramacionActualizada, modelMapper.map(saber, EvaSaberBasico.class));
				});
			}
		    
		    log.info("Se ha actualizado exitosamente la unidad de programacion con id= " + unidadProgramacionActualizada.getId());

		} else {
			throw new RuntimeException("Error al actualizar la unidad de programación con id= " + unidadProgramacion.getId());
		}
	}
	
	@Override
	@Transactional
	public boolean deleteUnidadProgramacion(Long idUnidadProgramacion) {
		// Busco si existe la Unidad de programación mediante el id recibido y la rescato
		EvaUnidadProgramacion borrarUnidadProgramacion = unidadProgramacionRepository.findById(idUnidadProgramacion).orElse(null);
		
		if(borrarUnidadProgramacion != null) {	    		
			// Borro todas las relaciones con los criterios de evaluación
	    	relacionUnidadProgramacionCriteriosEvaluacionRepository.deleteAllByUnidadProgramacion(borrarUnidadProgramacion);
	    	
	    	// Borro todas las relaciones con los saberes básicos
	    	relacionUnidadProgramacionSaberBasicoRepository.deleteAllByUnidadProgramacion(borrarUnidadProgramacion);
	    	
	    	// Borro la relación la Programación didáctica
	    	relacionProgramacionDidacticaUnidadProgramacionRepository.deleteById(borrarUnidadProgramacion.getRelacionProgramacionDidacticaUnidadProgramacion().getId());
	    	
			// Borro la Unidad de programación mediante el id recibido
			unidadProgramacionRepository.deleteById(borrarUnidadProgramacion.getId());
    
    		log.info("Se ha borrado exitosamente la unidad de programacion con id= " + idUnidadProgramacion);
    		
    		return true;
		} else {
			log.error("Error al borrar la unidad de programación con id= " + idUnidadProgramacion);
			
			return false;
		}
	}
	
	@Override
	public List<UnidadesProgramacionCriterioDTO> getNumUnidadesProgramacionCriterios(List<CriterioListDto> criterios, Long idProgramacionDidactica) {
		List<UnidadesProgramacionCriterioDTO> criteriosUnidadesProgramacion = new ArrayList<>();
		
		if(criterios != null && !criterios.isEmpty()) {
			List<Long> criteriosIds = criterios.stream().map(CriterioListDto :: getIdCriterio).collect(Collectors.toList());
			List<EvaUnidadesProgramacionCriterioProjection> criteriosUP = unidadProgramacionRepository.getNumUnidadesProgramacionCriterios(criteriosIds, idProgramacionDidactica);
			criteriosUnidadesProgramacion = criteriosUP.stream().map(x -> modelMapper.map(x, UnidadesProgramacionCriterioDTO.class)).collect(Collectors.toList());
		}
		
		return criteriosUnidadesProgramacion;
	}

   @Override
   public List<CompetenciaEspecificaDidacticaDTO> getCompetenciasEspecificas(Long idMateriaOmg) {
	   
	   
	   List<EvaRelacionCompetenciaEspecificaMateria> relaciones = relacionCompetenciaEspecificaMateriaRepository.findAllByIdMateriaOmg(idMateriaOmg);
   
	   List<CompetenciaEspecificaDidacticaDTO> competenciasEspecificas = new ArrayList<>();
	   
	   for (EvaRelacionCompetenciaEspecificaMateria relacion : relaciones) {
		   EvaCompetenciaEspecificaDidactica competenciaRescatada = competenciaEspecificaDidacticaRepository.findById(relacion.getCompetenciaEspecificaDidactica().getId()).orElse(null);
	   
		   if(competenciaRescatada != null) {
			   CompetenciaEspecificaDidacticaDTO competencia =  modelMapper.map(competenciaRescatada, CompetenciaEspecificaDidacticaDTO.class);


			   List<EvaCriterioEvaluacion> criterios = criterioEvaluacionRepository.findAllByCompetenciaEspecificaId(competencia.getId());

			   competencia.setCriterios(criterios.stream().sorted(Comparator.comparing(EvaCriterioEvaluacion::getOrden)).map(x -> {
				   CriterioEvaluacionDTO credto = modelMapper.map(x, CriterioEvaluacionDTO.class);
				   Long numUnidades = relacionUnidadProgramacionCriteriosEvaluacionRepository.countByCriterioEvaluacion(x.getId());
				   credto.setNumUnidadesProgramacion(numUnidades);
				   return credto;
			   }).collect(Collectors.toList()));


			   competencia.setCriterios(this.getCriteriosEvaluacionDTO(competencia));


			   competenciasEspecificas.add(competencia);   
		   }
	   }

	   Collections.sort(competenciasEspecificas, Comparator.comparing(CompetenciaEspecificaDidacticaDTO::getNOrdenPres, Comparator.nullsLast(Comparator.naturalOrder())));
	    
	   return competenciasEspecificas;
	}

	private List<CriterioEvaluacionDTO> getCriteriosEvaluacionDTO(CompetenciaEspecificaDidacticaDTO competencia) {
		List<EvaCriterioEvaluacion> criterios = criterioEvaluacionRepository.findAllByCompetenciaEspecificaId(competencia.getId());

		List<CriterioEvaluacionDTO> criteriosDTO = criterios.stream().map(x ->
				modelMapper.map(x, CriterioEvaluacionDTO.class)).collect(Collectors.toList());

		criteriosDTO.forEach(criterio-> criterio.setNumUnidadesProgramacion(relacionUnidadProgramacionCriteriosEvaluacionRepository.countByCriterioEvaluacion(criterio.getId())));

		return criteriosDTO;
	}

	public List<CompetenciaEspecificaDidacticaDTO> getCriteriosEvaluacionAcnee(Long idPonderacion) {
		List<CompetenciasEspecificasPonderacionProjection> competenciasEspecificasProjection = ponderacionRepository.getCompetenciasByPonderacion(idPonderacion);
		List<CompetenciaEspecificaDidacticaDTO> competenciasEspecificas = competenciasEspecificasProjection.stream().map(x -> modelMapper.map(x, CompetenciaEspecificaDidacticaDTO.class)).collect(Collectors.toList());
		for(CompetenciaEspecificaDidacticaDTO competencia : competenciasEspecificas) {
			List<CriteriosPonderacionProjection> criteriosProjection = ponderacionRepository.getCriteriosByPonderacionyCompetencia(idPonderacion, competencia.getId());
			List<CriterioEvaluacionDTO> criterios = criteriosProjection.stream().map(x -> modelMapper.map(x, CriterioEvaluacionDTO.class)).collect(Collectors.toList());
			Collections.sort(criterios, Comparator.comparing(CriterioEvaluacionDTO::getOrden, Comparator.nullsLast(Comparator.naturalOrder())));
			competencia.setCriterios(criterios);
		}

		Collections.sort(competenciasEspecificas, Comparator.comparing(CompetenciaEspecificaDidacticaDTO::getNOrdenPres, Comparator.nullsLast(Comparator.naturalOrder())));

		return competenciasEspecificas;
	}
	@Override
	public List<ConvocatoriasDto> getConvocatorias(Long anno, Long codigoCentro, Long idMateria) {
		
		List<ConvocatoriaProjection> convocatoriaProjection = unidadProgramacionRepository.getConvocatorias(anno, codigoCentro, idMateria);
		return convocatoriaProjection.stream().map(convocatoria -> modelMapper.map(convocatoria, ConvocatoriasDto.class)).collect(Collectors.toList());
		
	}

	@Override
	public List<CompetenciaEspecificaDidacticaDTO> getCompetenciasEspecificasUnidad(Long idUnidadProgramacion, Long idActividad) throws Exception {
		EvaUnidadProgramacion unidadProgramacion = unidadProgramacionRepository.findById(idUnidadProgramacion).orElse(null);

		if (unidadProgramacion != null) {
			List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> relacionesUnidadProgramacionCriteriosEvaluacion = relacionUnidadProgramacionCriteriosEvaluacionRepository.findAllByUnidadProgramacion(unidadProgramacion);
			List<CompetenciaEspecificaDidacticaDTO> competenciasEspecificas = new ArrayList<>();
			List<EvaCriterioEvaluacion> criteriosUnidad = new ArrayList<>();
			List<Long> idsCompetenciasEspecificasUnidad = new ArrayList<>();
			for (EvaRelacionUnidadProgramacionCriteriosEvaluacion relUniProgCriEva : relacionesUnidadProgramacionCriteriosEvaluacion) {
				EvaCriterioEvaluacion criterio = relUniProgCriEva.getCriterioEvaluacion();
				Long idCompetenciaEspecifica = criterio.getCompetenciaEspecifica().getId();
				criteriosUnidad.add(criterio);
				if (!idsCompetenciasEspecificasUnidad.contains(idCompetenciaEspecifica)) {
					idsCompetenciasEspecificasUnidad.add(idCompetenciaEspecifica);
				}
			}
			for (Long idComEsp : idsCompetenciasEspecificasUnidad) {
				EvaCompetenciaEspecificaDidactica competenciaRescatada = competenciaEspecificaDidacticaRepository.findById(idComEsp).orElse(null);

				if (competenciaRescatada != null) {
					CompetenciaEspecificaDidacticaDTO competencia = modelMapper.map(competenciaRescatada, CompetenciaEspecificaDidacticaDTO.class);

					List<EvaCriterioEvaluacion> criteriosCompetencia = criteriosUnidad.stream().filter(cu -> cu.getCompetenciaEspecifica().equals(competenciaRescatada)).collect(Collectors.toList());
					List<CriterioEvaluacionDTO> criteriosEvaluacionDto = criteriosCompetencia.stream().map(ce -> {
								CriterioEvaluacionDTO crevdto = modelMapper.map(ce, CriterioEvaluacionDTO.class);
								EvaRelacionPonderacionCriteriosEvaluacion relPonCriEva = relacionPonderacionCriteriosEvaluacionRepository.findByUnidadProgramacionAndCriterioEvaluacion(unidadProgramacion, ce);

								if (relPonCriEva != null) {
									crevdto.setIdTipoOperacion(relPonCriEva.getIdOpeCalCriEva());
									if (relPonCriEva.getIdOpeCalCriEva() == 2) {
										if (idActividad != null) {
											EvaRelacionActividadCriterioEvaluacion relActCri = relacionActividadCriterioEvaluacionRepository.findByActividadIdAndCriterioEvaluacionId(idActividad, crevdto.getId());
											if (relActCri != null) {
												crevdto.setPeso(relActCri.getPeso());

												Long numeroCalificaciones = valoracionCriterioActividadAlumnoRepository.countByRelacionActividadCriterioEvaluacionId(relActCri.getId());
												if (numeroCalificaciones != null && numeroCalificaciones > 0) {
													crevdto.setTieneValoraciones(true);
												}
											} else {
												crevdto.setPeso(1);
											}
										} else {
											crevdto.setPeso(1);
										}
									}
								}
								return crevdto;
							}
					).collect(Collectors.toList());
					Collections.sort(criteriosEvaluacionDto, Comparator.comparing(CriterioEvaluacionDTO::getAbreviatura));
					competencia.setCriterios(criteriosEvaluacionDto);

					competenciasEspecificas.add(competencia);
				}
			}
			Collections.sort(competenciasEspecificas, Comparator.comparing(CompetenciaEspecificaDidacticaDTO::getNOrdenPres, Comparator.nullsLast(Comparator.naturalOrder())));

			return competenciasEspecificas;
		} else {
			throw new Exception("No se ha podido encontrar la unidad de programación");
		}
	}
	public List<RelacionBloqueSaberBasicoMateriaDTO> getBloquesSaberes(Long idMateria){

		List<EvaRelacionBloqueSaberBasicoMateria> bloques = relacionBloqueMateriaRepository.findAllByIdMateriaOmg(idMateria);

		List<RelacionBloqueSaberBasicoMateriaDTO> bloquesOut = bloques.stream().map( rel -> {
			RelacionBloqueSaberBasicoMateriaDTO relOut = modelMapper.map(rel, RelacionBloqueSaberBasicoMateriaDTO.class);
			
			List<SaberBasicoDTO> saberesBasicos = saberBasicoRepository.findAllByIdBloque(rel.getBloqueSaberBasico().getId()).stream().map(sb -> modelMapper.map(sb, SaberBasicoDTO.class)).sorted(Comparator.comparingLong(SaberBasicoDTO::getOrden)).collect(Collectors.toList());
			
			relOut.getBloqueSaberBasico().setSaberesBasicos(saberesBasicos);
			
			return relOut;
		}).sorted(Comparator.comparingLong(relOut -> relOut.getBloqueSaberBasico().getOrden())).collect(Collectors.toList());

		return bloquesOut;
	}

	@Override
	public List<SaberBasicoDTO> getSaberesBasicosByUnidadProgramacion(Long idUnidadProgramacion) {
		return saberBasicoRepository.findAllByIdUnidadProgramacion(idUnidadProgramacion).stream().map(sabb -> modelMapper.map(sabb, SaberBasicoDTO.class)).collect(Collectors.toList());
	}

	@Override
	public EvaConvocatoriaCentrosOMC obtenerConvocatoriaCentroOMC(EvaConvocatoriaCentrosOMC convCentroOMC, Integer anyoOriginal, Integer anyoNuevo) {
		/* Se comenta porque no se va a asignar convocatoria a programaciones didácticas copiadas de años anteriores
		if (convCentroOMC != null && !anyoOriginal.equals(anyoNuevo)) { // Si es de distinto año, rescata la convocatoria de centro OMC equivalente del curso académico nuevo
			return convocatoriaCentrosOmcRepository.getConvocatoriaCentroOMCEquivalenteCurso(convCentroOMC.getId(), anyoNuevo);
		} else {
			return convCentroOMC;
		}
		*/
		return anyoOriginal.equals(anyoNuevo) ? convCentroOMC : null;
	}

}