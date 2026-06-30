package es.jccm.edu.pdc.application.services.cuestionarios;




import es.jccm.edu.pdc.adapter.out.repositories.planActuacion.CenObjCenRepository;
import es.jccm.edu.pdc.adapter.out.repositories.planActuacion.CenObjLineaActuacionRepository;
import es.jccm.edu.pdc.adapter.out.repositories.planActuacion.CenObjLineaSeguimientoRepository;
import es.jccm.edu.pdc.adapter.out.repositories.planActuacion.PlanActuacionRepository;
import es.jccm.edu.pdc.adapter.out.repositories.planActuacion.InformacionImpactoPDCRepository;
import es.jccm.edu.pdc.adapter.out.repositories.planActuacion.InformacionSeguimientoPDCRepository;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.*;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.InformeProjection;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.SugerenciasMejorasProjection;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.AmbitoCompleto;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.DetalleAmbito;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.LineaActuacionPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.ObjetivoEspecificoPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.projection.AmbitosCompletosProjection;
import es.jccm.edu.pdc.application.domain.planActuacion.projection.ObjetivoEspecificoActualizadoProjection;
import es.jccm.edu.pdc.application.domain.planActuacion.tablas.InformacionImpactoPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.tablas.InformacionSeguimientoPDC;
import es.jccm.edu.pdc.application.ports.in.cuestionarios.IPlanActuacionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PlanActuacionService implements IPlanActuacionService {
	
	@Autowired
	private PlanActuacionRepository planActuacionRepository;
	
	
	@Autowired
	private CenObjLineaActuacionRepository cenObjLineaActuacionRepository;
	
	@Autowired
	private CenObjCenRepository cenObjCenRepository;
	
	@Autowired
	private InformacionSeguimientoPDCRepository TLPDCINFSEGRepository;
	
	@Autowired
	private InformacionImpactoPDCRepository TLPDCINFIMPACRepository;

	@Autowired
	private CenObjLineaSeguimientoRepository cenObjLineaSeguimientoRepository;
	
	@Autowired
	private CuestionarioService cuestionarioService;
	@Autowired
	private EvaluacionHomeService evaluacionHomeService;
	

	@Autowired
	private ModelMapper modelMapper;
	
	private static final Logger LOG = LogManager.getLogger(PlanActuacionService.class);

	@Override
	public List<Informe> getAmbitosPlanActuacion(Long idSector, Long idAnno, Long idCentro) {
		String mensaje = String.format("Obteniendo el informe generado con idCentro = %s", idCentro);

		LOG.info(mensaje);

		try {
			List<InformeProjection> informeOut = planActuacionRepository.getAmbitosPlanActuacion(idSector, idAnno, idCentro);
			if (informeOut.isEmpty()) return new ArrayList<Informe>();
			Map<Long, Informe> mapaAmbitos = new HashMap<>();
			for (InformeProjection informe : informeOut) {
				Long cod = informe.getIdCompetencia();
				if(mapaAmbitos.containsKey(cod)){
					mapaAmbitos.get(cod).getDescripcionesObjetivo().add(informe.getDesObjetivo());
				}
				else {
					Informe i = new Informe();
					i.setAnno(informe.getAnno());
					i.setCodCentro(informe.getCodCentro());
					i.setCodCompetencia(informe.getCodCompetencia());
					i.setDescCompetencia(informe.getDescCompetencia());
					i.setIdCompetencia(informe.getIdCompetencia());
					i.setNivel(informe.getNivel());
					i.setRespuestas(informe.getRespuestas());		
					i.setSector(informe.getSector());
					i.setValor(informe.getValor());
					i.setDescripcionesObjetivo(new ArrayList<String>());
					i.getDescripcionesObjetivo().add(informe.getDesObjetivo());
					mapaAmbitos.put(cod,i); 
				}
			}

			return  new ArrayList<>(mapaAmbitos.values());

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<AmbitoCompleto> getAmbitosCompletosCentro(Long idSector, Long idAnno, Long xCentro, Long x_cuepub) {
		List<AmbitosCompletosProjection> ambitosCompletosProjection = planActuacionRepository.getAmbitosCompletosCentro(x_cuepub,idAnno,xCentro,idSector);
		List<SugerenciasMejorasProjection> mejoras = cuestionarioService.getSugerenciasMejorasCuestionarioCentro(xCentro, x_cuepub);
		Map<Long, SugerenciasMejorasProjection> mapaMejorasPorOpcion = new HashMap<>();
		if(mejoras!= null) {
			for(SugerenciasMejorasProjection mejora : mejoras) {
				if(!mapaMejorasPorOpcion.containsKey(mejora.getIdCueopc())) {
					mapaMejorasPorOpcion.put(mejora.getIdCueopc(), mejora);
				}
			}
		}
				
		Map<Long, AmbitoCompleto> mapaAmbitoCompleto = new HashMap<>();
		// x_compentencia, listado ids de objetivosGenerales
		Map<Long, Set<Long>> mapaAmbitoobjetivoGeneral = new HashMap<>();
		Map<Long, ObjetivoGeneral> mapaobjetivosGenerales = new HashMap<>();
		
		// x_compentencia, listado ids de sugerencias
		Map<Long, Set<Long>> mapaAmbitoSugerencia = new HashMap<>();
		Map<Long, Sugerencia> mapaSugerencias = new HashMap<>();
		
		// x_compentencia, listado ids de punto partidas
		Map<Long, Set<Long>> mapaAmbitoPuntoPartida = new HashMap<>();
		Map<Long, PuntoPartida> mapaPuntoPartidas = new HashMap<>();
		
		// x_compentencia, listado ids de objetivos especificos
		Map<Long, Set<Long>> mapaAmbitoObjetivoEspecifico = new HashMap<>();
		Map<Long, ObjetivoEspecifico> mapaObjetivosEspecificos = new HashMap<>();
		
		// x_objetivoEspecifico, listado ids de lineas de actuacion
		Map<Long, Set<Long>> mapaObjetivoEspecificoLineaActuacion = new HashMap<>();
		Map<Long, LineaActuacion> mapaLineasActuaciones = new HashMap<>();
		
		
		for(AmbitosCompletosProjection fila : ambitosCompletosProjection) {
			if(!mapaAmbitoCompleto.containsKey(fila.getX_COMPETENCIA())){
				AmbitoCompleto a = new AmbitoCompleto();
				a.setXCentro(xCentro);
				a.setIdCompetencia(fila.getX_COMPETENCIA());
				a.setSector(fila.getD_SECTOR());
				a.setNivel(fila.getC_NIVEL());
				a.setCodCompetencia(fila.getC_PDCCOMPETENCIA());
				a.setDescCompetencia(fila.getD_PDCCOMPETENCIA());
				a.setValor(fila.getN_VALOR());
				a.setRespuestas(fila.getN_RESPUESTAS());
				a.setAnno(idAnno);
				
				DetalleAmbito detalle = new DetalleAmbito();
				detalle.setIdCompetencia(fila.getX_COMPETENCIA());
				detalle.setObjetivoGeneral(new ArrayList<>());
				detalle.setPuntoPartida(new ArrayList<>());
				detalle.setSugerencia(new ArrayList<>());
				a.setDetalleAmbitos(detalle);
				a.setObjetivosEspecificos(new ArrayList<>());
				
				mapaAmbitoCompleto.put(fila.getX_COMPETENCIA(),a );
			}
			
			// Objetivos generales
			if(fila.getX_OBJETIVO()!= null && !mapaobjetivosGenerales.containsKey(fila.getX_OBJETIVO())) {
				ObjetivoGeneral objGeneral = new ObjetivoGeneral();
				objGeneral.setActivo(fila.getL_ACTIVO());
				objGeneral.setCodCompetencia(fila.getC_PDCCOMPETENCIA());
				objGeneral.setDesCompetencia(fila.getD_PDCCOMPETENCIA());
				objGeneral.setDesObjetivo(fila.getD_OBJETIVO());
				objGeneral.setIdCompetencia(fila.getX_COMPETENCIA());
				objGeneral.setIdGrupoObjetivo(fila.getX_OBJGRPCOM());
				objGeneral.setIdObjetivo(fila.getX_OBJETIVO());
				objGeneral.setIdSubDimension(fila.getX_SUBDIMENSION());
				objGeneral.setTipObjetivo(fila.getX_TIPOBJ());
				objGeneral.setTituloCompetencia(fila.getD_PDCCOMPETENCIA());
				
				mapaobjetivosGenerales.put(fila.getX_OBJETIVO(), objGeneral);
				
				if(mapaAmbitoobjetivoGeneral.containsKey(fila.getX_COMPETENCIA())) {
					Set<Long> listado = mapaAmbitoobjetivoGeneral.get(fila.getX_COMPETENCIA());
					listado.add(fila.getX_OBJETIVO());
				}
				else {
					Set<Long> s = new HashSet<>();
					s.add(fila.getX_OBJETIVO());
					mapaAmbitoobjetivoGeneral.put(fila.getX_COMPETENCIA(), s);
				}
			}
			
			// sugerencias
			if(fila.getX_SUGERENCIA()!= null && !mapaSugerencias.containsKey(fila.getX_SUGERENCIA())) {
				Sugerencia suger = new Sugerencia();
				suger.setCodCompetencia(fila.getC_PDCCOMPETENCIA()+"");
				suger.setDesCompetencia(fila.getD_PDCCOMPETENCIA());
				suger.setIdCompetencia(fila.getX_COMPETENCIA());
				suger.setIdSubDimension(fila.getX_SUBDIMENSION());
				suger.setTituloCompetencia(fila.getD_PDCCOMPETENCIA());
				//suger.setComoMejorar(fila.getD_COMOMEJORAR());
				suger.setDesSugerencia(fila.getD_SUGERENCIA());
				suger.setIdSector(idSector);
				suger.setIdSugNiv(fila.getX_PDCSUGNIV());
				suger.setIdSugerencia(fila.getX_SUGERENCIA());
				
				mapaSugerencias.put(fila.getX_SUGERENCIA(), suger);
				
				if(mapaAmbitoSugerencia.containsKey(fila.getX_COMPETENCIA())) {
					Set<Long> listado = mapaAmbitoSugerencia.get(fila.getX_COMPETENCIA());
					listado.add(fila.getX_SUGERENCIA());
				}
				else {
					Set<Long> s = new HashSet<>();
					s.add(fila.getX_SUGERENCIA());
					mapaAmbitoSugerencia.put(fila.getX_COMPETENCIA(), s);
				}
			}
			
			// puntoPartida
			if(fila.getX_CUEOPC()!= null && !mapaPuntoPartidas.containsKey(fila.getX_CUEOPC())) {
				PuntoPartida puntoPartida = new PuntoPartida();
				puntoPartida.setDesCompetencia(fila.getD_PDCCOMPETENCIA());
				puntoPartida.setIdOpcion(fila.getX_CUEOPC());
				puntoPartida.setDesPregunta(fila.getD_CUEPRE());
				puntoPartida.setDesOpcion(fila.getD_CUEOPC());
				puntoPartida.setX_cuepre(fila.getX_CUEPRE());
				SugerenciasMejorasProjection comoMejorar = mapaMejorasPorOpcion.get(fila.getX_CUEOPC());
				if(comoMejorar != null) {
					puntoPartida.setComoMejorar(comoMejorar.getDescSugerencia());
				}
				mapaPuntoPartidas.put(fila.getX_CUEOPC(), puntoPartida);
				
				if(mapaAmbitoPuntoPartida.containsKey(fila.getX_COMPETENCIA())) {
					Set<Long> listado = mapaAmbitoPuntoPartida.get(fila.getX_COMPETENCIA());
					listado.add(fila.getX_CUEOPC());
				}
				else {
					Set<Long> s = new HashSet<>();
					s.add(fila.getX_CUEOPC());
					mapaAmbitoPuntoPartida.put(fila.getX_COMPETENCIA(), s);
				}
			}
			
			// objetivos especificos
			if(fila.getX_PDCENOBJCEN()!= null && !mapaObjetivosEspecificos.containsKey(fila.getX_PDCENOBJCEN())) {
				ObjetivoEspecifico objetivoEspecifico = new ObjetivoEspecifico();
				objetivoEspecifico.setActivo("S");
				objetivoEspecifico.setDescripcion(fila.getD_OBJESPECIFICO());
				objetivoEspecifico.setIdObjEsp(fila.getX_PDCENOBJCEN());
				objetivoEspecifico.setIdObjetivo(fila.getX_OBJETIVO());
				objetivoEspecifico.setLineasActuacion(new ArrayList<>());
				mapaObjetivosEspecificos.put(fila.getX_PDCENOBJCEN(), objetivoEspecifico);
				
				if(mapaAmbitoObjetivoEspecifico.containsKey(fila.getX_COMPETENCIA())) {
					Set<Long> listado = mapaAmbitoObjetivoEspecifico.get(fila.getX_COMPETENCIA());
					listado.add(fila.getX_PDCENOBJCEN());
				}
				else {
					Set<Long> s = new HashSet<>();
					s.add(fila.getX_PDCENOBJCEN());
					mapaAmbitoObjetivoEspecifico.put(fila.getX_COMPETENCIA(), s);
				}
			}
			
			// lineas de actuacion
			if(fila.getX_PDCCENOBJLINACT()!= null && !mapaLineasActuaciones.containsKey(fila.getX_PDCCENOBJLINACT())) {
				LineaActuacion lineaAct = new LineaActuacion();
				lineaAct.setActivo("S");
				lineaAct.setDescripcion(fila.getD_LINEAACT());
				lineaAct.setIdObjEsp(fila.getX_PDCENOBJCEN());
				lineaAct.setFechaCreacion(fila.getF_CREACION());
				lineaAct.setFechaFin(fila.getF_FIN());
				lineaAct.setFechaInicio(fila.getF_INICIO());
				lineaAct.setIdLinAct(fila.getX_PDCCENOBJLINACT());
				lineaAct.setInstrumentos(fila.getD_INSTRUMENTOS());
				lineaAct.setLogro(fila.getD_INDLOGRO());
				lineaAct.setPorcentaje(fila.getPORC_EJEC());
				lineaAct.setResponsable(fila.getT_RESPONSABLE());
				lineaAct.setTitulo(fila.getT_LINEAACT());
				mapaLineasActuaciones.put(fila.getX_PDCCENOBJLINACT(), lineaAct);
				
				if(mapaObjetivoEspecificoLineaActuacion.containsKey(fila.getX_PDCENOBJCEN())) {
					Set<Long> listado = mapaObjetivoEspecificoLineaActuacion.get(fila.getX_PDCENOBJCEN());
					listado.add(fila.getX_PDCCENOBJLINACT());
				}
				else {
					Set<Long> s = new HashSet<>();
					s.add(fila.getX_PDCCENOBJLINACT());
					mapaObjetivoEspecificoLineaActuacion.put(fila.getX_PDCENOBJCEN(), s);
				}
			}
				
			
		}
		
		for(Long idObjEsp : mapaObjetivoEspecificoLineaActuacion.keySet()) {
			Set<Long> conjuntoLA = mapaObjetivoEspecificoLineaActuacion.get(idObjEsp);
			List<Long> listaOrdenada = new ArrayList<>(conjuntoLA);
	        Collections.sort(listaOrdenada);
			for(Long idLineaAct : listaOrdenada) {
				LineaActuacion actuacion = mapaLineasActuaciones.get(idLineaAct);
				ObjetivoEspecifico objEsp = mapaObjetivosEspecificos.get(idObjEsp);
				if(objEsp.getLineasActuacion()== null){
					objEsp.setLineasActuacion(new ArrayList<>());
				}
				objEsp.getLineasActuacion().add(actuacion);
			}
		}
		for(Long idAmbito : mapaAmbitoObjetivoEspecifico.keySet()) {
			Set<Long> conjuntoO = mapaAmbitoObjetivoEspecifico.get(idAmbito);
			List<Long> listaOrdenada = new ArrayList<>(conjuntoO);
	        Collections.sort(listaOrdenada);
			for(Long idObjetivosEspecificos : listaOrdenada) {
				ObjetivoEspecifico objEsp = mapaObjetivosEspecificos.get(idObjetivosEspecificos);
				AmbitoCompleto ambito = mapaAmbitoCompleto.get(idAmbito);
				ambito.getObjetivosEspecificos().add(objEsp);
			}
		}
		for(Long idAmbito : mapaAmbitoobjetivoGeneral.keySet()) {
			Set<Long> conjuntoG = mapaAmbitoobjetivoGeneral.get(idAmbito);
			List<Long> listaOrdenada = new ArrayList<>(conjuntoG);
	        Collections.sort(listaOrdenada);
			for(Long idObj : listaOrdenada) {
				ObjetivoGeneral objEsp = mapaobjetivosGenerales.get(idObj);
				AmbitoCompleto ambito = mapaAmbitoCompleto.get(idAmbito);
				ambito.getDetalleAmbitos().getObjetivoGeneral().add(objEsp);
			}
		}
		for(Long idAmbito : mapaAmbitoSugerencia.keySet()) {
			Set<Long> conjuntoSug = mapaAmbitoSugerencia.get(idAmbito);
			List<Long> listaOrdenada = new ArrayList<>(conjuntoSug);
	        Collections.sort(listaOrdenada);
			for(Long idSug : listaOrdenada) {
				Sugerencia sug = mapaSugerencias.get(idSug);
				AmbitoCompleto ambito = mapaAmbitoCompleto.get(idAmbito);
				ambito.getDetalleAmbitos().getSugerencia().add(sug);
			}
		}
		for(Long idAmbito : mapaAmbitoPuntoPartida.keySet()) {
			Set<Long> conjuntoPP = mapaAmbitoPuntoPartida.get(idAmbito);
			List<Long> listaOrdenada = new ArrayList<>(conjuntoPP);
	        Collections.sort(listaOrdenada);
	        
			for(Long idPunto : listaOrdenada) {
				PuntoPartida p = mapaPuntoPartidas.get(idPunto);
				AmbitoCompleto ambito = mapaAmbitoCompleto.get(idAmbito);
				ambito.getDetalleAmbitos().getPuntoPartida().add(p);
			}
		}
		
		return new ArrayList<>(mapaAmbitoCompleto.values());
	}

	public DetalleAmbito getDetalleAmbitoCentro(Long idCompetencia, Long idCentro, Long anio) {
		String mensaje = String.format("Obteniendo el detalle del ambito con idCompetencia = %s", idCompetencia);
		LOG.info(mensaje);

		Cuestionario cuestionario = cuestionarioService.getCuestionarioCentro(idCentro,anio);

		try {

			DetalleAmbito detalleAmbito = new DetalleAmbito();

			List<ObjetivoGeneral> objetivoGeneral = planActuacionRepository.getObjetivoGeneral(idCompetencia).stream().map(x -> modelMapper.map(x, ObjetivoGeneral.class)).collect(Collectors.toList());
			List<PuntoPartida> puntoPartida = planActuacionRepository.getPuntoPartida(idCompetencia,cuestionario.getIdCuePubUsu()).stream().map(x -> modelMapper.map(x, PuntoPartida.class)).collect(Collectors.toList());
			List<Sugerencia> sugerencia = planActuacionRepository.getSugerencia(idCompetencia).stream().map(x -> modelMapper.map(x, Sugerencia.class)).collect(Collectors.toList());

			detalleAmbito.setObjetivoGeneral(objetivoGeneral);
			detalleAmbito.setPuntoPartida(puntoPartida);
			detalleAmbito.setSugerencia(sugerencia);

			return detalleAmbito;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<ObjetivoEspecifico> getObjetivosEspecificos(Long codCentro, Long idObjetivo, Long idAnno) {
		String mensaje = String.format("Obteniendo los objetivos especificos con codCentro = %s", codCentro);

		LOG.info(mensaje);

		try {
			Long idCentro = planActuacionRepository.getIdCentroByCod(codCentro);
			List<ObjetivoEspecifico> objetivosEspecificos = planActuacionRepository.getObjetivosEspecificos(idCentro, idObjetivo,idAnno).stream().map(x -> modelMapper.map(x, ObjetivoEspecifico.class)).collect(Collectors.toList());

			for(ObjetivoEspecifico objetivo: objetivosEspecificos) {
				List<LineaActuacion> lineasActuacion = planActuacionRepository.getLineasActuacion(objetivo.getIdObjEsp(), idCentro).stream().map(x -> modelMapper.map(x, LineaActuacion.class)).collect(Collectors.toList());
				objetivo.setLineasActuacion(lineasActuacion);
			}

			return objetivosEspecificos;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	public void setObjetivoEspecifico(Long codCentro, Long anno, Long idObjetivo, String descripcionObjetivo) {
		String mensaje = String.format("Insertando el objetivo especifico del objetivo ", idObjetivo);

		LOG.info(mensaje);

		try {
			Long idCentro = planActuacionRepository.getIdCentroByCod(codCentro);

			planActuacionRepository.setObjetivoEspecifico(idCentro, anno, idObjetivo, descripcionObjetivo);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Modifying
	@Transactional
	public void setLineasDeActuacion(Long idObjEsp, String titulo, String descripcion, Date fechaInicio,
									 Date fechaFin, String responsable, String logro, String instrumentos, String estado,Long idUsuComunica) {
		String mensaje = String.format("Insertando el lineas de actuación en el obejtivo específico ", idObjEsp);

		LOG.info(mensaje);
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = dt1.format(new Date());

		String fechaInicioFormat = null;
		String fechaFinFormat = null;


		if(fechaInicio != null) {
			fechaInicioFormat = dt1.format(fechaInicio);
		}
		if (fechaFin != null) {
			fechaFinFormat = dt1.format(fechaFin);
		}
		try {
			if(descripcion != null && responsable != null && logro != null && instrumentos != null) {
				 estado = "D";
			} else {
				estado = "B";
			}

			Optional<CenObjCen> obj = cenObjCenRepository.findById(idObjEsp);
			CenObjCen cenObjCen = obj.isPresent()?obj.get():null;
			CenObjLineaActuacion cenObjLineaActuacion = CenObjLineaActuacion.builder()
					.idCenOjCen(cenObjCen)
					.tituloLineaActuacion(titulo)
					.descripcionLineaActuacion(descripcion)
					.fechaInicioEjecucion(fechaInicio)
					.fechaFinEjecucion(fechaFin)
					.responsable(responsable)
					.indLogro(logro)
					.instrumentos(instrumentos)
					.estadoLineaActuacion(estado)
					.activo("S")
					.build();
			cenObjLineaActuacion.setFechaCreacion(new Date());
			cenObjLineaActuacion.setIdUsuarioCreacion(idUsuComunica);
			cenObjLineaActuacion.setFechaModificacion(new Date());
			cenObjLineaActuacion.setIdUsuarioModificacion(idUsuComunica);

			CenObjLineaActuacion nuevoCenObjLineaActuacion = cenObjLineaActuacionRepository.save(cenObjLineaActuacion);
			Long idCenObjLineaActuacion = nuevoCenObjLineaActuacion.getId();
			
			if(estado == "D") {
				CenObjLineaSeguimiento cenObjLineaSeguimiento = CenObjLineaSeguimiento.builder()
						.idCenObjLineaActuacion(idCenObjLineaActuacion)
						.porcentajeEjecucion(0.0)
						.build();

				cenObjLineaSeguimiento.setFechaCreacion(new Date());
				cenObjLineaSeguimiento.setIdUsuarioCreacion(idUsuComunica);
				cenObjLineaSeguimiento.setFechaModificacion(new Date());
				cenObjLineaSeguimiento.setIdUsuarioModificacion(idUsuComunica);

				cenObjLineaSeguimientoRepository.save(cenObjLineaSeguimiento);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	public void deleteObjetivoEspecifico(ObjetivoEspecifico objetivoEspecifico) {
		String mensaje = String.format("Desactivando objetivo específico ", objetivoEspecifico.getIdObjEsp());

		LOG.info(mensaje);

		try {
			List<LineaActuacion> lineasActuacion = objetivoEspecifico.getLineasActuacion();

			planActuacionRepository.deleteObjetivoEspecifico(objetivoEspecifico.getIdObjEsp());

			for (LineaActuacion linea: lineasActuacion) {
				deleteLineaActuacion(linea.getIdLinAct());
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	public void deleteLineaActuacion(Long idLinAct) {
		String mensaje = String.format("Desactivando línea de actuación ", idLinAct);

		LOG.info(mensaje);

		try {
			planActuacionRepository.deleteLineaActuacion(idLinAct);
			planActuacionRepository.deleteLineasDeSeguimiento(idLinAct);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void editObjetivosEspecificos(Long idObjEsp, String descripcion) {
		String mensaje = String.format("Update objetivo especifico ", idObjEsp);

		LOG.info(mensaje);

		try {
			planActuacionRepository.editObjetivosEspecificos(idObjEsp, descripcion);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	@Transactional
	public void editLineasActuacion(Long idObjEsp,  List<LineaActuacion> listaLineas, Long idUsuComunica) {
		String mensaje = String.format("Editando el lineas de actuación en el obejtivo específico ", idObjEsp);
		LOG.info(mensaje);

		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			

			for (LineaActuacion lineaActuacion : listaLineas) {
				String fechaInicioFormat = null;
				String fechaFinFormat = null;
				String fechaActual = dt1.format(new Date());
				Long idUsuario = idUsuComunica;

				if (lineaActuacion.getFechaInicio() != null) {
					fechaInicioFormat = dt1.format(lineaActuacion.getFechaInicio());
				}
				if (lineaActuacion.getFechaFin() != null) {
					fechaFinFormat = dt1.format(lineaActuacion.getFechaFin());
				}
				
				if (lineaActuacion.getDescripcion() != null && !lineaActuacion.getDescripcion().isEmpty()
					    && lineaActuacion.getResponsable() != null && !lineaActuacion.getResponsable().isEmpty()
					    && lineaActuacion.getLogro() != null && !lineaActuacion.getLogro().isEmpty()
					    && lineaActuacion.getInstrumentos() != null && !lineaActuacion.getInstrumentos().isEmpty()) 
				{
					
					CenObjLineaSeguimiento cenObjLineaSeguimiento = CenObjLineaSeguimiento.builder()
							.idCenObjLineaActuacion(lineaActuacion.getIdLinAct())
							.porcentajeEjecucion(0.0)
							.build();

					cenObjLineaSeguimiento.setFechaCreacion(new Date());
					cenObjLineaSeguimiento.setIdUsuarioCreacion(idUsuComunica);
					cenObjLineaSeguimiento.setFechaModificacion(new Date());
					cenObjLineaSeguimiento.setIdUsuarioModificacion(idUsuComunica);

					cenObjLineaSeguimientoRepository.save(cenObjLineaSeguimiento);
					
					planActuacionRepository.editLineasActuacion(
							lineaActuacion.getIdLinAct(), 
							lineaActuacion.getTitulo(), 
							lineaActuacion.getDescripcion(),
							fechaInicioFormat, 
							fechaFinFormat, 
							lineaActuacion.getResponsable(), 
							lineaActuacion.getLogro(),
							lineaActuacion.getInstrumentos(),
							"D",
							idUsuario,
							fechaActual
					);
					
				} else {
					planActuacionRepository.editLineasActuacion(
							lineaActuacion.getIdLinAct(), 
							lineaActuacion.getTitulo(), 
							lineaActuacion.getDescripcion(),
							fechaInicioFormat, 
							fechaFinFormat, 
							lineaActuacion.getResponsable(), 
							lineaActuacion.getLogro(),
							lineaActuacion.getInstrumentos(),
							"B",
							idUsuario,
							fechaActual
					);			
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<Informe> getValoresCentro(Long idSector, Long idAnno) {
		String mensaje = String.format("Obteniendo el informe de valores" );

		LOG.info(mensaje);

		try {
			List<InformeProjection> informeOut = planActuacionRepository.getValoresCentro(idSector, idAnno);

			if (informeOut.isEmpty()) return new ArrayList<Informe>();
			Map<Long, Informe> mapaAmbitos = new HashMap<>();
			for (InformeProjection informe : informeOut) {
				Long cod = informe.getIdCompetencia();
				if(mapaAmbitos.containsKey(cod)){
					mapaAmbitos.get(cod).getDescripcionesObjetivo().add(informe.getDesObjetivo());
				}
				else {
					Informe i = new Informe();
					i.setAnno(informe.getAnno());
					i.setCodCentro(informe.getCodCentro());
					i.setCodCompetencia(informe.getCodCompetencia());
					i.setDescCompetencia(informe.getDescCompetencia());
					i.setIdCompetencia(informe.getIdCompetencia());
					i.setNivel(informe.getNivel());
					i.setRespuestas(informe.getRespuestas());		
					i.setSector(informe.getSector());
					i.setValor(informe.getValor());
					i.setDescripcionesObjetivo(new ArrayList<String>());
					i.getDescripcionesObjetivo().add(informe.getDesObjetivo());				
					mapaAmbitos.put(cod, i);
				}
			}
			
			return new ArrayList<>(mapaAmbitos.values());

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public Double getValorAmbitoCinco(Long codCentro, Long Anno) {
		String mensaje = String.format("Obteniendo el informe de valores" );

		LOG.info(mensaje);

		try {
			Double informeOut = planActuacionRepository.getValorAmbitoCinco(codCentro, Anno);

			return  informeOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public Double getValorAmbitoCincoGlobal(Long Anno) {
		String mensaje = String.format("Obteniendo el informe de valores" );

		LOG.info(mensaje);

		try {
			Double informeOut = planActuacionRepository.getValorAmbitoCincoGlobal(Anno);

			return  informeOut;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Boolean visualizaPlan(Long codCentro) {
		String mensaje = String.format("Obtener si el plan de actuacion se puede visualizar" );
		Long idCentro = planActuacionRepository.getIdCentroByCod(codCentro);
		LOG.info(mensaje);

		try {
			Long esVisible = planActuacionRepository.visualizaPlan(idCentro);

			return  esVisible > 0;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	public List<ObjetivoEspecificoPDC> getObjetivosEspecificosPorAmbito(String anio, String x_competencia, String x_centro){
		String mensaje = String.format("Obtener los objetivos especificos para el año , competencia y centro: "+ anio+ " "+ x_competencia+" "+x_centro);
		LOG.info(mensaje);

		try {
			List<ObjetivoEspecificoActualizadoProjection> resultados = planActuacionRepository.getObjetivosEspecificos(anio,x_competencia,x_centro);
	
			if (resultados.isEmpty()) return new ArrayList<>();
			
			Map<Integer, ObjetivoEspecificoPDC> mapaObjetivos = new HashMap<>();
			
			for(ObjetivoEspecificoActualizadoProjection resultado : resultados){
				LineaActuacionPDC l = new LineaActuacionPDC();
				l.setDescripcion(resultado.getDescipcionActuacion());
				l.setFechaFin(resultado.getFechaFinActuacion());	
				l.setFechaInicio(resultado.getFechaInicioActuacion());
				l.setFechaCreacion(resultado.getFechaCreacionActuacion());
				l.setId(resultado.getIdLinAct());
				l.setInstrumentos(resultado.getInstrumentosActuacion());
				l.setLogro(resultado.getLogroActuacion());
				l.setPorcentaje(resultado.getPorcActuacion());
				l.setResponsable(resultado.getResponsableActuacion());
				l.setTitulo(resultado.getTituloActuacion());
				l.setFechaInicioEjecucion(resultado.getFechaInicioEjecucionAct());
				l.setFechaFinEjecucion(resultado.getFechaFinEjecucionAct());
				l.setTareas(resultado.getTareasActuacion());
				l.setValoracion(resultado.getValoracionActuacion());
				l.setDificultades(resultado.getDificultadesActuacion());
				l.setComentarios(resultado.getComentariosActuacion());
				
				
				Integer idObj = resultado.getIdObjetivoEsp();
				if(mapaObjetivos.containsKey(idObj)) {
					mapaObjetivos.get(idObj).getActuaciones().add(l);
					Integer p = mapaObjetivos.get(idObj).getPorcentajeTotal();
					p+=l.getPorcentaje();
					mapaObjetivos.get(idObj).setPorcentajeTotal(p);
				}
				else {
					ObjetivoEspecificoPDC objetivoEsp = new ObjetivoEspecificoPDC();
					objetivoEsp.setId(idObj);
					objetivoEsp.setDescripcion(resultado.getDescripcionObj());
					objetivoEsp.setIdAmbito(resultado.getIdAmbito());
					objetivoEsp.setActuaciones(new ArrayList<LineaActuacionPDC>());;
					objetivoEsp.getActuaciones().add(l);
					objetivoEsp.setPorcentajeTotal(l.getPorcentaje());
					mapaObjetivos.put(idObj,objetivoEsp);
				}
			}
		
			 List<ObjetivoEspecificoPDC> lista = new ArrayList<>(mapaObjetivos.values());
			 // Calcular media
			 for (ObjetivoEspecificoPDC obj : lista) {
				 obj.setPorcentajeTotal(obj.getPorcentajeTotal()/obj.getActuaciones().size());
			 }
			 return lista;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<LineaActuacionPDC> getHistoricoLineasActuacion(Integer idActuacion) {
		String mensaje = String.format("Obteniendo el historico de las lineas de actuacion de = %s", idActuacion);

		LOG.info(mensaje);

		try {

			List<LineaActuacionPDC> lineasActuacion = planActuacionRepository.getHistoricoLineasActuacion(idActuacion).stream().map(x -> modelMapper.map(x, LineaActuacionPDC.class)).collect(Collectors.toList());
				
			return lineasActuacion;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void insertarPropuestasYMejorasPorAmbito(Integer x_centro, Integer x_cuepub, Integer x_competencia, Integer anno, String observacion, String propuesta) {
		InformacionSeguimientoPDC infSeg = TLPDCINFSEGRepository.findByXCompetencia(x_competencia,x_centro,x_cuepub,anno);
      
		if(infSeg!= null) {
			//Update
			if(observacion != null)
				infSeg.setObservacion(observacion);
			if(propuesta != null)
				infSeg.setPropuesta(propuesta);
			TLPDCINFSEGRepository.save(infSeg);
		}
		else {	
			infSeg = new InformacionSeguimientoPDC();
			infSeg.setCentro(x_centro);
			infSeg.setCuepub(x_cuepub);
			infSeg.setCompetencia(x_competencia);
			infSeg.setAnno(anno);
			infSeg.setObservacion(observacion);
			infSeg.setPropuesta(propuesta);
			TLPDCINFSEGRepository.save(infSeg);
		}
		
	}


	public void eliminarPropuestasYMejorasPorAmbito(Integer x_competencia, Integer x_centro, Integer x_cuepub, Integer anno ) {
		InformacionSeguimientoPDC infSeg = TLPDCINFSEGRepository.findByXCompetencia(x_competencia,x_centro,x_cuepub,anno);
		if(infSeg!= null) {
			TLPDCINFSEGRepository.delete(infSeg);
		}
	
	}
	
	public void insertarPropuestasYMejorasGlobal(Integer x_centro, Integer x_cuepub, Integer anno, String mejoraGestion, String mejoraApren, String otrastareas) {
		InformacionImpactoPDC infSeg = TLPDCINFIMPACRepository.findMejorasGlobal(x_centro,x_cuepub,anno);
      
		if(infSeg!= null) {
			//Update
			if(mejoraGestion != null) 
				infSeg.setMejoragestion(mejoraGestion);
			
			if(mejoraApren != null) 
				infSeg.setMejoraaprendizaje(mejoraApren);
			
			if(otrastareas != null) 
				infSeg.setOtrastareas(otrastareas);
			
			TLPDCINFIMPACRepository.save(infSeg);
		}
		else {	
			infSeg = new InformacionImpactoPDC();
			infSeg.setCentro(x_centro);
			infSeg.setCuepub(x_cuepub);
			infSeg.setAnno(anno);
			infSeg.setMejoragestion(mejoraGestion);
			infSeg.setMejoraaprendizaje(mejoraApren);
			infSeg.setOtrastareas(otrastareas);
			
			TLPDCINFIMPACRepository.save(infSeg);
		}
		
	}


	public void eliminarPropuestasYMejorasGlobal(Integer x_centro, Integer x_cuepub, Integer anno ) {
		InformacionImpactoPDC infSeg = TLPDCINFIMPACRepository.findMejorasGlobal(x_centro,x_cuepub,anno);
		if(infSeg!= null) {
			TLPDCINFIMPACRepository.delete(infSeg);
		}
	
	}
	public InformacionImpactoPDC obtenerPropuestasYMejorasGlobal(Integer x_centro, Integer x_cuepub, Integer anno ) {
		InformacionImpactoPDC a = TLPDCINFIMPACRepository.findMejorasGlobal(x_centro,x_cuepub,anno);
		if (a == null) {
			return new InformacionImpactoPDC();
		}
		return a;
	
	}
	public InformacionSeguimientoPDC obtenerPropuestasYMejorasPorAmbito(Integer x_competencia, Integer x_centro, Integer x_cuepub, Integer anno ) {
		InformacionSeguimientoPDC a =  TLPDCINFSEGRepository.findByXCompetencia(x_competencia,x_centro,x_cuepub,anno);
		if (a == null) {
			return new InformacionSeguimientoPDC();
		}
		return a;
	
	}

	@Override
	public List<Informe> getAmbitosConObjetivosEspecificos(String anno, String x_centro) {
		String mensaje = String.format("Obteniendo el informe generado con x_centro = %s", x_centro);

		LOG.info(mensaje);

		try {
			List<InformeProjection> informeOut = planActuacionRepository.getAmbitosConObjetivosEspecificos(anno,x_centro);
			if (informeOut.isEmpty()) return new ArrayList<Informe>();
			Map<Long, Informe> mapaAmbitos = new HashMap<>();
			for (InformeProjection informe : informeOut) {
				Long cod = informe.getIdCompetencia();
				if(mapaAmbitos.containsKey(cod)){
					mapaAmbitos.get(cod).getDescripcionesObjetivo().add(informe.getDesObjetivo());
				}
				else {
					Informe i = new Informe();
					i.setAnno(informe.getAnno());
					i.setCodCentro(informe.getCodCentro());
					i.setCodCompetencia(informe.getCodCompetencia());
					i.setDescCompetencia(informe.getDescCompetencia());
					i.setIdCompetencia(informe.getIdCompetencia());
					i.setNivel(informe.getNivel());
					i.setRespuestas(informe.getRespuestas());		
					i.setSector(informe.getSector());
					i.setValor(informe.getValor());
					i.setDescripcionesObjetivo(new ArrayList<String>());
					i.getDescripcionesObjetivo().add(informe.getDesObjetivo());
					mapaAmbitos.put(cod,i); 
				}
			}

			return  new ArrayList<>(mapaAmbitos.values());

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Integer> getHistoricoAniosConObjEspYLineasActuacion(String x_centro){
		String mensaje = String.format("Obteniendo los años con objetivos especificos con lineas de actuacion para el centro", x_centro);

		LOG.info(mensaje);

		try {

			List<Integer> historico = planActuacionRepository.getHistoricoAniosConObjEspYLineasActuacion(x_centro);
			if(historico == null) {
				return new ArrayList<Integer>();
			}
				
			return historico;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

}
