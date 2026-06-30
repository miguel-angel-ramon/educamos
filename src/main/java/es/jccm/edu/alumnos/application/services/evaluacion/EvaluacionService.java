package es.jccm.edu.alumnos.application.services.evaluacion;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.CursoCentroDTO;
import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.materiaLlaveDTO;
import es.jccm.edu.alumnos.adapter.out.repository.evaluacion.EvaluacionRepository;
import es.jccm.edu.alumnos.adapter.out.repository.evaluacion.RegistrosTemporalesRepository;
import es.jccm.edu.alumnos.application.domain.evaluacion.AlumnoEvalInd;
import es.jccm.edu.alumnos.application.domain.evaluacion.Calificacion;
import es.jccm.edu.alumnos.application.domain.evaluacion.ConvUnidad;
import es.jccm.edu.alumnos.application.domain.evaluacion.Convocatoria;
import es.jccm.edu.alumnos.application.domain.evaluacion.EstadosPromocion;
import es.jccm.edu.alumnos.application.domain.evaluacion.Evaluacion;
import es.jccm.edu.alumnos.application.domain.evaluacion.GrupoActividadConvocatoria;
import es.jccm.edu.alumnos.application.domain.evaluacion.ListCalificaciones;
import es.jccm.edu.alumnos.application.domain.evaluacion.MateriaUnidad;
import es.jccm.edu.alumnos.application.domain.evaluacion.Promocion;
import es.jccm.edu.alumnos.application.domain.evaluacion.RegistroTemporal;
import es.jccm.edu.alumnos.application.domain.evaluacion.SistemaCalificacion;
import es.jccm.edu.alumnos.application.domain.evaluacion.UnidadConv;
import es.jccm.edu.alumnos.application.domain.evaluacion.projection.ListCalificacionesProjection;
import es.jccm.edu.alumnos.application.domain.evaluacion.projection.PromocionProjection;
import es.jccm.edu.alumnos.application.ports.in.evaluacion.IEvaluacionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaluacionService implements IEvaluacionService {
	
	private static final String ERROR = "Error";

	@PersistenceContext
	EntityManager em;

	@Autowired
	private EvaluacionRepository evaluacionRepository;
	
	@Autowired
	private RegistrosTemporalesRepository registrosTemporalesRepository;

	@Autowired
	private ModelMapper modelMapper;

	private static final Logger LOG = LogManager.getLogger(EvaluacionService.class);

	@Override
	public List<Evaluacion> getNotasByGrupoActividad(String idGrupoAct, Long idEmpleado, Long idConvocatoria) {

		Long[] idsGruposAct = (Long[]) ConvertUtils.convert(idGrupoAct.split(","), Long[].class);

		try {
			List<Evaluacion> listaEvaluacion = evaluacionRepository.getNotasByGrupoActividad(idsGruposAct, idEmpleado, idConvocatoria).stream().map(x -> modelMapper.map(x, Evaluacion.class)).collect(Collectors.toList());

			// Eliminamos los alumnos duplicados
			Set<Long> ids = new HashSet<>();
			List<Evaluacion> listaFiltrada = listaEvaluacion.stream()
					.filter(objeto -> ids.add(objeto.getIdMatricula()))
					.collect(Collectors.toList());

			for (Evaluacion evaluacion : listaFiltrada) {
				setFoto(evaluacion);

				List<Calificacion> calificaciones = new ArrayList<Calificacion>();

				calificaciones.addAll(evaluacionRepository.getCalificacionesByMatricula(idsGruposAct, evaluacion.getIdMatricula(), idConvocatoria).stream().map(x -> modelMapper.map(x, Calificacion.class)).collect(Collectors.toList()));
				evaluacion.setCalificaciones(calificaciones);

			}

			return listaFiltrada;

		} catch (Exception e) {
			log.error(ERROR,e);
			throw e;
		}
	}

	public Evaluacion setFoto(Evaluacion evaluacion) {
		Blob img = evaluacionRepository.getFotoAlumno(evaluacion.getIdAlumno());

		try {
			if (img != null) {
				evaluacion.setFoto(img.getBytes(1, (int) img.length()));
			}
		} catch (SQLException e) {
			log.error(ERROR,e);
		}
		return evaluacion;
	}
	
	@Override
	public SistemaCalificacion getTipoSistemaCalificacion(Long idGrupoAct, Integer anno) {
		
		SistemaCalificacion sistemaCalificacion = new SistemaCalificacion();
		sistemaCalificacion.setIdGrupoActividad(idGrupoAct);
		sistemaCalificacion.setNumSisCal(evaluacionRepository.getNumeroSistemasCalificacion(idGrupoAct, anno));
		
		if(sistemaCalificacion.getNumSisCal() == 1) {
			List<ListCalificacionesProjection> sisCal = evaluacionRepository.getTipoSistemaCalificacion(idGrupoAct, anno);
			sistemaCalificacion.setListaCalificaciones(sisCal.stream().map(x -> modelMapper.map(x, ListCalificaciones.class)).collect(Collectors.toList()));
		}

		return sistemaCalificacion;
	}
	
	@Override
	public List<ListCalificaciones> getTipoSistemaCalificacionPorAlumno(Long idMatMatricula, Integer anno) {
		
			List<ListCalificacionesProjection> sisCal = evaluacionRepository.getTipoSistemaCalificacionPorAlumno(idMatMatricula, anno);
			return	sisCal.stream().map(x -> modelMapper.map(x, ListCalificaciones.class)).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public void setNotaAlumnoParaGrupoActividad(Long idConvCentroOmc, Long idMatMatricula, Long idCalifica, Long idConvocatoria, Integer accion) {

		try {
			if(accion == -1) {
				evaluacionRepository.eliminarNota(idConvCentroOmc, idMatMatricula);
			} else if(accion == 0){
				Integer resultado = insertarNotaBBDD(idConvCentroOmc, idMatMatricula, idCalifica);
				String mensaje = String.format("Resultado de insertar nota con idConvCentroOmc: %d, idMatMatricula: %d, idCalifica: %d, idConvocatoria: %d es %d", 
						idConvCentroOmc, idMatMatricula, idCalifica, idConvocatoria, resultado);
				LOG.info(mensaje);
			} else {
				actualizarNotaBBDD(idConvCentroOmc, idMatMatricula, idCalifica, idConvocatoria);
			}
		} catch (Exception e) {
			log.error(ERROR,e);
			throw e;
		}
		
	}
	
	private Integer insertarNotaBBDD(Long idConvCentroOmc, Long idMatMatricula, Long idCalifica) {
	    Session session = em.unwrap(Session.class);
 
	    try {
	        return session.doReturningWork(new ReturningWork<Integer>() {
	            @Override
	            public Integer execute(Connection connection) throws SQLException {
	                String sql = "{ ? = call DELPHOS.TLPQ_ACTNOTEVA.insertar(?,?,?,?,?,?,?)}";
	                try (CallableStatement function = connection.prepareCall(sql)) {
	                    function.registerOutParameter(1, Types.INTEGER);
	                    function.setLong(2, idMatMatricula);
	                    function.setLong(3, idConvCentroOmc);
	                    function.setLong(4, idCalifica);
	                    function.setString(5, null);
	                    function.setString(6, null);
	                    function.setString(7, null);
	                    function.setString(8, "S");
 
	                    function.execute();
	                    return function.getInt(1);
	                }
	            }
	        });
	    } catch (Exception e) {
	        log.error("Error al insertar nota en BBDD", e);
	        return null;
	    }
	}
	
	private void actualizarNotaBBDD(Long idConvCentroOmc, Long idMatMatricula, Long idCalifica, Long idConvocatoria) {
	       
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement = null;
		try {
			callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						     
				   CallableStatement function = connection.prepareCall("{ call DELPHOS.TLPQ_ACTNOTEVA.modificar(?,?,?,?,?,?,?) }");
				   					 function.setLong(1, idMatMatricula);
						             function.setLong(2, idConvCentroOmc);
						             function.setLong(3, idCalifica);
						             function.setLong(4, idConvocatoria);
						             function.setString(5, null);
						             function.setString(6, null);
							         function.setString(7, "S");
				   	                 function.execute();
				   	               
							        return function;
							    }
			});
           	callableStatement.getResultSet();
           } catch (SQLException e) {
	            log.error(ERROR,e);
	       } finally {
			try {
				if(callableStatement!=null) {
					callableStatement.close();
				}
			} catch (SQLException e) {
				log.error(ERROR,e);
			}
		}
	}
	
	@Override
	public Integer setNotaConvocatoriaFinalAlumno(Long idConvCentroOmc, Long idMatMatricula, Long idMatricula, Long idCalifica, 
			String apruebaMateria, Long idConvUnidad, String fechaSesion, Integer accion) {
		
			Long idEjecucion = this.registrosTemporalesRepository.getIdEjecucion();
			
			RegistroTemporal registroTemporal = new RegistroTemporal();
			registroTemporal.setIdEntreg(this.registrosTemporalesRepository.getIdEntreg());
			registroTemporal.setIdEjecucion(idEjecucion);
			registroTemporal.setNombreEntreg("--");
			registroTemporal.setEstado("N");
			registroTemporal.setCodPromas("EVALUACION");
			registroTemporal.setIdClave1(idMatMatricula);
			registroTemporal.setIdClave3(idMatricula);
			//Sistema de calificación cerrado, cambiar en un futuro para añadir casuistica de sistemas abiertos NVEvalCompl
			registroTemporal.setCodigoClave4("S");
			registroTemporal.setCodigoClave6(idConvUnidad.toString());
			registroTemporal.setFechaActualizacion(new Date());	
			
			Integer resultado = -1;
				
			if(accion != -1) {
				registroTemporal.setCodigoClave3(idCalifica.toString());
				registroTemporal.setCodigoClave5(apruebaMateria);
			}
				
			try {
				this.registrosTemporalesRepository.save(registroTemporal);
				
				resultado = setNotaConvocatoriaFinalYResultadoPromocionBBDD(idEjecucion, idConvCentroOmc, fechaSesion);
			} catch (Exception e) {
				log.info(e.getMessage());
				throw e;
			}
			
			return resultado;
	}
	
	@Override
	public Integer setResultadoPromocionAlumno(Long idConvCentroOmc, Long idMatricula, Long resultado, 
			Long idEstGenMatr, Long idConvUnidad, String fechaSesion, Integer accion) {
		
			Long idEjecucion = this.registrosTemporalesRepository.getIdEjecucion();
			
			RegistroTemporal registroTemporal = new RegistroTemporal();
			registroTemporal.setIdEntreg(this.registrosTemporalesRepository.getIdEntreg());
			registroTemporal.setIdEjecucion(idEjecucion);
			registroTemporal.setNombreEntreg("--");
			registroTemporal.setEstado("N");
			registroTemporal.setCodPromas("EVALUACION");
			registroTemporal.setIdClave2(idMatricula);
			registroTemporal.setCodigoClave6(idConvUnidad.toString());
			registroTemporal.setFechaActualizacion(new Date());	
			
			Integer resultadoEjecucion = -1;
			
			//TLPQ_EVALUACION - 2213
			if(accion != -1) {
				registroTemporal.setCodigoClave2(resultado.toString());
				registroTemporal.setCodigoClave3(idEstGenMatr.toString());
			}
				
			try {
				this.registrosTemporalesRepository.save(registroTemporal);
				
				resultadoEjecucion = setNotaConvocatoriaFinalYResultadoPromocionBBDD(idEjecucion, idConvCentroOmc, fechaSesion);
			} catch (Exception e) {
				log.info(e.getMessage());
				throw e;
			}
			
			return resultadoEjecucion;
	}
	
	private Integer setNotaConvocatoriaFinalYResultadoPromocionBBDD(Long idEjecucion, Long idConvCentroOmc, String fechaSesion) {
	       
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement = null;
		try {
			callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						     
				   CallableStatement function = connection.prepareCall("{ call ? := TLPQ_EVALUACION.TLF_PMEVACON(?, ?, ?, ?) }");
				   					 function.setLong(2, idEjecucion);
						             function.setLong(3, idConvCentroOmc);
						             function.setString(4, fechaSesion);
						             function.setString(5, "S");
							         function.registerOutParameter(1, Types.INTEGER);
				   	                 function.execute();
				   	               
							        return function;
							    }
			});
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            log.error(ERROR,e);
	            return null;
	       } finally {
			try {
				if(callableStatement!=null) {
					callableStatement.close();
				}
			} catch (SQLException e) {
				log.error(ERROR,e);
			}
		}
	}
	
	@Override
	public List<Evaluacion> getNotasByUnidad(Long idUnidad, Long idConvocatoria, Long idOfertaMatrig) {
		
		try {
		List<Evaluacion> listaEvaluacion = evaluacionRepository.getNotasByUnidad(idUnidad, idConvocatoria, idOfertaMatrig).stream().map(x -> modelMapper.map(x, Evaluacion.class)).collect(Collectors.toList());

		for(Evaluacion evaluacion: listaEvaluacion) {

			List<Calificacion> calificaciones = new ArrayList<Calificacion>();

			calificaciones.addAll(evaluacionRepository.getCalificacionesByUnidadMatricula(idUnidad, evaluacion.getIdMatricula(), idConvocatoria).stream().map(x -> modelMapper.map(x, Calificacion.class)).collect(Collectors.toList()));

			evaluacion.setCalificaciones(calificaciones);

			//Saco la lista de materias llave
			List<materiaLlaveDTO> materiasConLlaveEnMatricula = evaluacionRepository.getMateriasLlaveByMatricula(evaluacion.getIdMatricula() ).stream().map(x -> modelMapper.map(x, materiaLlaveDTO.class)).collect(Collectors.toList());

			for(Calificacion cali: calificaciones) {

				for(materiaLlaveDTO materiaLlave: materiasConLlaveEnMatricula){

					if (materiaLlave.getIdMateriaOmg().equals(cali.getIdMateriaOmg()))
					{
						cali.setIdMateriaOmgLlave(materiaLlave.getIdMateriaOmgLlave());
					}

					if(materiaLlave.getIdMateriaOmgLlave().equals(cali.getIdMateriaOmg()))
					{
						cali.setIsMateriaLlave(true);
					}

				}

			}


		}

		return listaEvaluacion;
		
		} catch (Exception e) {
			log.error(ERROR,e);
			throw e;
		}
	}
	
	@Override
	public List<MateriaUnidad> getMateriaByUnidad(Long idUnidad, Integer anno, Long idOferMatring) {
		
		try {
			List<MateriaUnidad> materias = evaluacionRepository.getMateriaByUnidad(idUnidad).stream().map(x -> modelMapper.map(x, MateriaUnidad.class)).collect(Collectors.toList());
			
			for(MateriaUnidad materia: materias) {
				
				List<ListCalificaciones> sistemaCalificacion = evaluacionRepository.getSistemaCalificacionByIdMateria(materia.getIdMateria(), anno).stream().map(x -> modelMapper.map(x, ListCalificaciones.class)).collect(Collectors.toList());
			
				if (sistemaCalificacion != null) {
					materia.setSistemaCalificacion(sistemaCalificacion);
				}
			}
			
			return	materias;
		} catch(Exception e) {
			log.error(ERROR,e);
			throw e;
		}
	}
	
	public List<Convocatoria> getConvocatorias(Long idCentro, Integer anno) {
		
		try {
			
			List<Convocatoria> convocatorias = evaluacionRepository.getConvocatorias(idCentro, anno).stream().map(x -> modelMapper.map(x, Convocatoria.class)).collect(Collectors.toList());
			
			return convocatorias;
			
		} catch (Exception e) {
			log.error(ERROR,e);
			throw e;
		}
		
	}
	
	public List<UnidadConv> getUnidadesConvocatoria(Long idConvocatoria, Long idOfertamatrig, String idUnidad) {
		
		List<UnidadConv> unidadesConvocatoria = new ArrayList<UnidadConv>();
		
		try {
		
			if(idUnidad != null) {
				
				Long[] idsUnidades = (Long[]) ConvertUtils.convert(idUnidad.split(","), Long[].class);
				unidadesConvocatoria = evaluacionRepository.getUnidadesConvocatoria(idConvocatoria, idsUnidades).stream().map(x -> modelMapper.map(x, UnidadConv.class)).collect(Collectors.toList());
				
			}else {
				unidadesConvocatoria = evaluacionRepository.getAllUnidadesByCurso(idConvocatoria, idOfertamatrig).stream().map(x -> modelMapper.map(x, UnidadConv.class)).collect(Collectors.toList());
			}
			
			
			return unidadesConvocatoria;
			
		} catch (Exception e) {
			log.error(ERROR,e);
			throw e;
		}
		
	}
	
	public List<GrupoActividadConvocatoria> getGruposActividadConvocatoria(Long idConvocatoria, Long idEmpleado) {
		
		try {
			
			List<GrupoActividadConvocatoria> gruposActividadConvocatoria = evaluacionRepository.getGruposActividadConvocatoria(idConvocatoria, idEmpleado).stream().map(x -> modelMapper.map(x, GrupoActividadConvocatoria.class)).collect(Collectors.toList());
			
			return gruposActividadConvocatoria;
			
		} catch (Exception e) {
			log.error(ERROR,e);
			throw e;
		}
		
	}
	
	
	public List<GrupoActividadConvocatoria> getGruposActividadUnidades(Long idConvocatoria, Long idEmpleado) {
			
			try {
				
				List<GrupoActividadConvocatoria> gruposActividadConvocatoria = evaluacionRepository.getGruposActividadUnidades(idConvocatoria, idEmpleado).stream().map(x -> modelMapper.map(x, GrupoActividadConvocatoria.class)).collect(Collectors.toList());
				
				return gruposActividadConvocatoria;
				
			} catch (Exception e) {
				log.error(ERROR,e);
				throw e;
			}
			
		}
	
	public List<UnidadConv> getUnidadesByGrupo(Long idConvocatoria, Long idGrupoAct) {
			
			
			try {
				
				List<UnidadConv> UnidadesGrupoActividad = evaluacionRepository.getUnidadesGrupoActividad(idConvocatoria, idGrupoAct).stream().map(x -> modelMapper.map(x, UnidadConv.class)).collect(Collectors.toList());
				
				return UnidadesGrupoActividad;
				
			} catch (Exception e) {
				log.error(ERROR,e);
				throw e;
			}
			
		}
	
	@Override
	public List<ConvUnidad> getFechaSesion(Long unidad, Long convocatoria) {
		
		
		try {
			
			List<ConvUnidad> convUnidad = evaluacionRepository.getFechaSesion(unidad, convocatoria).stream().map(x -> modelMapper.map(x, ConvUnidad.class)).collect(Collectors.toList());
			
			return convUnidad;
			
		} catch (Exception e) {
			log.error(ERROR,e);
			throw e;
		}
		
	}

@Override
public Promocion getPromotion(Long idMatMatricula, Long idConvocatoria) {
	
	try {
		PromocionProjection project = evaluacionRepository.getPromotion(idMatMatricula, idConvocatoria);
		
		/*Parche hasta definir definitivamente la query*/
		if(project == null) {
			project = new PromocionProjection() {
				
				@Override
				public Long getIdPromocion() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Long getIdEstado() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getFechaSesion() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getEstado() {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}
		Promocion p =  modelMapper.map(project, Promocion.class);
		
		return p;
	} catch (Exception e) {
		log.error(ERROR,e);
		throw e;
	}
	}



@Override
public List<EstadosPromocion> getStatesPromocion(Long idOfermatrig) {
	
	
	try {
		
		List<EstadosPromocion> states = evaluacionRepository.getEstadosPromocion(idOfermatrig).stream().map(x -> modelMapper.map(x, EstadosPromocion.class)).collect(Collectors.toList());
		
		return states;
		
	} catch (Exception e) {
		log.error(ERROR,e);
		throw e;
	}
	
}

@Override
public Long getPromotionStates(Long idOfermatrig, Long action) {
	
	Long states = null;
	if(action == 0) {
		states = evaluacionRepository.getNumberHours(idOfermatrig);
	} else if(action == 1) {
		states = evaluacionRepository.getIsCicle(idOfermatrig);
	}
	return states;
}
	
public List<AlumnoEvalInd> getAlumnosByGruposActividad(String idGrupoAct, Long idEmpleado, Long idConvocatoria) {
	try {
		Long[] idsGruposAct = (Long[]) ConvertUtils.convert(idGrupoAct.split(","), Long[].class);
		List<AlumnoEvalInd> alumnos = evaluacionRepository.getAlumnosByGruposActividad(idsGruposAct, idEmpleado, idConvocatoria).stream().map(x -> modelMapper.map(x, AlumnoEvalInd.class)).collect(Collectors.toList());
		
		return alumnos;
	} catch (Exception e) {
		log.error(ERROR,e);
		throw e;
	}
}

public List<AlumnoEvalInd> getAlumnosByUnidad(Long idUnidad, Long idConvocatoria, Long idOfertaMatrig ) {
	try {
		List<AlumnoEvalInd> alumnos = evaluacionRepository.getAlumnosByUnidad(idUnidad, idConvocatoria, idOfertaMatrig).stream().map(x -> modelMapper.map(x, AlumnoEvalInd.class)).collect(Collectors.toList());
		return alumnos;
	} catch (Exception e) {
		log.error(ERROR,e);
		throw e;
	}
}

@Override
public HashMap<String, Long> getReprobedHours(Long idMatricula, Long idConvOmc) {
	
	List<Long> array = evaluacionRepository.getInfoReprobedFCT(idMatricula, idConvOmc).get(0);
	
	HashMap<String, Long> json = new HashMap<>();
	
	json.put("suspensas", array.get(0));
	json.put("nulos", array.get(1));
	
	return json;
}

@Override
public String getMediaEvaluacion(Long idAlumno,  Long idOfertMatrig, String notas, Long decimales) {
	
			String notas_definitive = notas.replace('_', '|');
			Session session = em.unwrap(Session.class);
			CallableStatement callableStatement = null;
			try {
			callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
	
		    @Override
			public CallableStatement execute(Connection connection) throws SQLException {
					     
		    	
		    //TODO obtener la media de las notas del curso
			   CallableStatement function = connection.prepareCall("{ ? = call DELPHOS.TLF_PUEDETITULAR_BACH_CON_SUSP(?,?,?,?)}");
			   					 function.setLong(2, idAlumno);
					             function.setLong(3, idOfertMatrig);
					             function.setString(4, notas_definitive);
					             function.setLong(5, decimales);
					             function.registerOutParameter(1, Types.CHAR);
			   	                 function.execute();
			   	               
			   	                 return function;
						    }
							});
       return callableStatement.getString(1);
       } catch (SQLException e) {
            log.error(ERROR,e);
            return null;
       } finally {
				try {
					if(callableStatement!=null) {
						callableStatement.close();
					}
				} catch (SQLException e) {
					log.error(ERROR,e);
				}
			}
}

	@Override
	public List<CursoCentroDTO> getCursosByCentroAndAnno(Long idCentro, Long anno) {
		return evaluacionRepository.getCursosByCentroAndAnno(idCentro, anno).stream().map(entity -> modelMapper.map(entity, CursoCentroDTO.class)).collect(Collectors.toList());
	}


}


