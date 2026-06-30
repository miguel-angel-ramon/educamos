package es.jccm.edu.documentosGC.application.services.actasevaluacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacion.ActasEvaluacionRepository;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.ActaEvaluacionList;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.ConvocatoriaCentro;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.ConvocatoriasCorrespondencia;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.CursoConvocatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.DatosCentroActa;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.DocumentoActa;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.FechasConvocatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.FirmantesPartes;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.OtrosFirmantes;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.TlDocumento;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.UnidadConvocatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.VistaPartesActas;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.ActaEvaluacionListProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.ConvocatoriaCentroProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.ConvocatoriasCorrespondenciaProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.CursoConvocatoriaProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.DatosCentroActaProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.DocumentoActaProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.FechasConvocatoriaProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.FirmantesPartesProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.OtrosFirmantesProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.TldocumentoListProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.UnidadConvocatoriaProjection;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.projection.VistaPartesActasProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ConvocatoriaUnidadList;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.ports.in.actasevaluacion.IActasEvaluacionService;
import es.jccm.edu.documentosGC.application.ports.in.estadodoc.IEstadosDocumentosGCService;

@Service
public class ActasEvaluacionService implements IActasEvaluacionService {
	
	@Autowired
	private ActasEvaluacionRepository actasEvaluacionRepository;
	
	@Autowired
	private IEstadosDocumentosGCService estadosDocumentosGCService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ConvocatoriaCentro> getConvocatoriasCentro(Long idCentro, 
														   Long cAnno, 
														   Long idTipo ) {
		
		List<ConvocatoriaCentroProjection> convocatoriaP = actasEvaluacionRepository.getConvocatoriasCentro(idCentro, 
																											cAnno, 
																											idTipo==null?-1:idTipo); 
		
		return convocatoriaP.stream().map(entity -> modelMapper.map(entity, ConvocatoriaCentro.class)).collect(Collectors.toList());
	}

	@Override
	public List<CursoConvocatoria> getCursosConvocatoria(Long idCentro, 
													     Long cAnno, 
													     Long idTipo, 
													     Long idConvCentro) {
		
		List<CursoConvocatoriaProjection> cursoP = actasEvaluacionRepository.getCursosConvocatoria(idCentro, 
																								   cAnno, 
																								   idTipo==null?-1:idTipo,
																								   idConvCentro); 
		
		return cursoP.stream().map(entity -> modelMapper.map(entity, CursoConvocatoria.class)).collect(Collectors.toList());
	}

	@Override
	public List<UnidadConvocatoria> getUnidadesConvocatoria(Long idCentro, 
														    Long cAnno, 
														    Long idTipo,
														    Long idConvCentro, 
														    Long idOfertamatric) {
		
	
		List<UnidadConvocatoriaProjection> unidadP = actasEvaluacionRepository.getUnidadesConvocatoria(idCentro, 
																								       cAnno, 
																								       idTipo==null?-1:idTipo,
																								       idConvCentro,
																								       idOfertamatric); 

		return unidadP.stream().map(entity -> modelMapper.map(entity, UnidadConvocatoria.class)).collect(Collectors.toList());
	}
	
	
	@Override
	public List<DocumentoActa> getDocumentosCurso(Long idCentro, Long cAnno, Long idOfertamatric) {
		
		List<DocumentoActaProjection> documentoP = actasEvaluacionRepository.getDocumentosCurso(idCentro,cAnno,idOfertamatric); 

		return documentoP.stream().map(entity -> modelMapper.map(entity, DocumentoActa.class)).collect(Collectors.toList());
	}
	
	
	@Override
	public List<EstadoDocumentoGC> getEstadosActasEvaluacion(Long idPerfil,
															 Long cAnno, 
															 String dsAbrevPadre) {
		
		EstadoDocumentoGC estadoPendiente = new EstadoDocumentoGC();
		
		estadoPendiente.setId(0L);
		estadoPendiente.setDsAbrev("PEN");
		estadoPendiente.setDsNombre("Pendiente");		
		estadoPendiente.setFhInicio(null);
		estadoPendiente.setFhFin(null);
		estadoPendiente.setLgFinal(false);
		estadoPendiente.setTxAviso(null);
		
		List<EstadoDocumentoGC> estados = estadosDocumentosGCService.estadosDocumentosGC(idPerfil, cAnno, dsAbrevPadre);
		estados.add(0, estadoPendiente);
		
		return estados;		

	}	
	
	@Override
	public List<ActaEvaluacionList> getAllActasEvaluacion(Long idCentro, 
														  Long cAnno, 
														  Long idTipo,
														  Long idConvCentro, 
														  Long idOfertamatric, 
														  Long idUnidad, 
														  Long idEstado,
														  Long idDocumento,
														  Long idMateriac,
														  Long idEtapa,
														  Long xEmpleado) {
		
		
		
		List<ActaEvaluacionListProjection> actaP = actasEvaluacionRepository.getAllActasEvaluacion(idCentro, 
																								   cAnno, 
																								   idTipo==null?-1:idTipo,
																								   idConvCentro, 
																								   idOfertamatric, 
																								   idUnidad, 
																								   idEstado,
																								   idDocumento,
																								   idMateriac,
																								   idEtapa,
																								   xEmpleado); 
		
		List<ActaEvaluacionList> acta = actaP.stream().map(x -> modelMapper.map(x,ActaEvaluacionList.class))
													  .collect(Collectors.toList());
		
		return acta;
	}
	
	@Override
	public List<ActaEvaluacionList> getAllActasEvaluacionInspCentro(Long idCentro, 
																    Long cAnno, 
																	Long idTipo,
																	Long idConvCentro, 
																	Long idOfertamatric, 
																	Long idUnidad, 
																	Long idEstado,
																	Long idProvincia, 
																	Long idMunicipio,
																	Long xEmpleado,
																	String fTomapos,
																	Long idDocumento) {
		
		
		
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");		
		
		try {
			
			Date d_fTomapos = formato.parse(fTomapos);
			
			List<ActaEvaluacionListProjection> actaP = actasEvaluacionRepository.getAllActasEvaluacionInspCentro(idCentro, 
																												 cAnno, 
																												 idTipo==null?-1:idTipo,
																												 idConvCentro, 
																												 idOfertamatric, 
																												 idUnidad, 
																												 idEstado,
																												 idProvincia,
																												 idMunicipio,
																												 xEmpleado,
																												 d_fTomapos,
																												 idDocumento); 
	
			return actaP.stream().map(entity -> modelMapper.map(entity, ActaEvaluacionList.class)).collect(Collectors.toList());
		} catch (ParseException e) {

			e.printStackTrace();
		} 
		
		return null;
	}
	
	@Override
	public List<ActaEvaluacionList> getAllActasEvaluacionZona(Long idCentro, 
															  Long cAnno, 
															  Long idTipo,
															  Long idConvCentro, 
															  Long idOfertamatric, 
															  Long idUnidad, 
															  Long idEstado,
												              Long idPerfil,
												              Long idProvincia,
												              Long idMunicipio,
															  Long idUsuario,
															  Long idDocumento) {
		
		List<ActaEvaluacionListProjection> actaP =  actasEvaluacionRepository.getAllActasEvaluacionZona(idCentro,
																										cAnno,
																										idTipo,
																										idConvCentro,
																										idOfertamatric,
																										idUnidad,
																										idEstado,
																										idPerfil,
																										idProvincia,
																										idMunicipio,
																										idUsuario,
																										idDocumento); 
		
		return actaP.stream().map(entity -> modelMapper.map(entity, ActaEvaluacionList.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ActaEvaluacionList> getAllActasEvaluacionProvincial(Long idCentro, 
																    Long cAnno, 
																	Long idTipo,
																	Long idConvCentro, 
																	Long idOfertamatric, 
																    Long idUnidad, 
																	Long idEstado,
																	Long idProvincia,
																	Long idMunicipio,
																	Long idPerfil,
																	Long idUsuario,
																	Long idCentroProvincia,
																	Long idDocumento) {
		
		List<ActaEvaluacionListProjection> actaP =  actasEvaluacionRepository.getAllActasEvaluacionProvincial(idCentro,
																											  cAnno,
																											  idTipo,
																											  idConvCentro,
																											  idOfertamatric,
																											  idUnidad,
																											  idEstado,
																											  idProvincia,
																											  idMunicipio,
																											  idPerfil,
																											  idUsuario,
																											  idCentroProvincia,
																											  idDocumento); 
		
		return actaP.stream().map(entity -> modelMapper.map(entity, ActaEvaluacionList.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ActaEvaluacionList> getAllActasEvaluacionConsejeria(Long idCentro, 
																    Long cAnno, 
																    Long idTipo,
																    Long idConvCentro, 
																    Long idOfertamatric, 
																    Long idUnidad, 
																    Long idEstado,
																    Long idProvincia,
																    Long idMunicipio,
																    Long idDocumento) {
		
		List<ActaEvaluacionListProjection> actaP =  actasEvaluacionRepository.getAllActasEvaluacionConsejeria(idCentro,
																											  cAnno,
																											  idTipo,
																											  idConvCentro,
																											  idOfertamatric,
																											  idUnidad,
																											  idEstado,
																											  idProvincia,
																											  idMunicipio,
																											  idDocumento); 
		
		return actaP.stream().map(entity -> modelMapper.map(entity, ActaEvaluacionList.class)).collect(Collectors.toList());
	}
	
	
	
	
	
	

	@Override
	public List<ConvocatoriaUnidadList> getConvocatoriasEvaluacion(Long idCentro, Long cAnno, Long idTipo) {
		
		return actasEvaluacionRepository.getConvocatoriasEvaluacion(idCentro, cAnno, idTipo==null?-1:idTipo).stream().map(entity -> modelMapper.map(entity, ConvocatoriaUnidadList.class)).collect(Collectors.toList());
	}



	@Override
	public DatosCentroActa getLocalidadCentro(Long idCentro, Long cAnno) {
		
		DatosCentroActaProjection datosP = actasEvaluacionRepository.getLocalidadCentro(idCentro); 
		
		return modelMapper.map(datosP, DatosCentroActa.class);	

	}

	@Override
	public String getNombreReport(Long idDocumento) {
		return actasEvaluacionRepository.getNombreReport(idDocumento);
	}

	@Override
	public FechasConvocatoria getFechasConvocatoria(Long idConvUnidad,
													Long idConvocatoria,
													Long idEtapa) {
		
		FechasConvocatoriaProjection fechaP = null;
		
		if (idConvUnidad == -1L) {
			
			fechaP = actasEvaluacionRepository.getFechasConvocatoriaEtapa(idConvocatoria, idEtapa);
			
		} else {
			
			fechaP = actasEvaluacionRepository.getFechasConvocatoria(idConvUnidad);
			
		}
		
		 
		
		return modelMapper.map(fechaP, FechasConvocatoria.class);  
	}	
	
	@Override
	public List<TlDocumento> getDocumentosByIdCentroAndCAnnoAndIdConvunidad(Long idCentro, Integer cAnno, Long idConvunidad){
		List<TldocumentoListProjection> documentoProjection = actasEvaluacionRepository.getDocumentosByIdCentroAndCAnnoAndIdConvunidad(idCentro, cAnno, idConvunidad);
		
		return documentoProjection.stream().map(entity -> modelMapper.map(entity, TlDocumento.class)).collect(Collectors.toList());
	}

	@Override
	public List<OtrosFirmantes> getOtrosFirmantes(Long idCentro, Long idUnidad, Integer dia, String mes, Integer anno, Long idDirector, Integer annoAca) {
		
		List<OtrosFirmantesProjection> otrosProjection = null;
		
		if (idUnidad == -1L) {
			
			otrosProjection = actasEvaluacionRepository.getOtrosFirmantesSinTutor(idCentro, idDirector);
			
		} else {
			
			otrosProjection = actasEvaluacionRepository.getOtrosFirmantes(idCentro, idUnidad, dia, mes, anno, idDirector, annoAca);
			
		}		
		
		return otrosProjection.stream().map(entity -> modelMapper.map(entity, OtrosFirmantes.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<OtrosFirmantes> getOtrosFirmantesNoFormales(Long idCentro, Long idUnidad, Long idDirector, Integer annoAca) {
		
		List<OtrosFirmantesProjection> otrosProjection = null;
			
		otrosProjection = actasEvaluacionRepository.getOtrosFirmantesNoFormales(idCentro, idUnidad, idDirector, annoAca);
			
		return otrosProjection.stream().map(entity -> modelMapper.map(entity, OtrosFirmantes.class)).collect(Collectors.toList());
	}
	
	public Integer getActaEvaluacionGenerada(Integer cAnno, 
										     Long idCentro, 
										     Long idTipoDocumento, 
										     Long idConvUnidad,
										     Long idDocumento,
										     Long idCurso,
										     Long idMateria,
										     Long idConvCentro) {
		
		if (idCurso != -1L && idMateria != -1L ) {
			
			return actasEvaluacionRepository.getActaEvaluacionGeneradaMateria(cAnno,
																			  idCentro,
																			  idTipoDocumento,																			  
																			  idDocumento,
																			  idCurso,
																			  idMateria,
																			  idConvCentro);
			
			
		} else if (idCurso != -1L) {
			
			return actasEvaluacionRepository.getActaEvaluacionGeneradaCurso(cAnno,
																		    idCentro,
																		    idTipoDocumento,																	
																		    idDocumento,
																		    idCurso,
																		    idConvCentro);
			
		} else if (idConvUnidad == -1L) {
				
				return actasEvaluacionRepository.getActaEvaluacionGeneradaEtapa(cAnno,
																			    idCentro,
																			    idTipoDocumento,																	
																			    idDocumento,
																			    idCurso,
																			    idConvCentro);	
		
		} else {
			
			return actasEvaluacionRepository.getActaEvaluacionGenerada(cAnno,
																	   idCentro,
																	   idTipoDocumento,
																	   idConvUnidad,
																	   idDocumento);			
		}
		

	}	
	
	@Override
	public FechasConvocatoria getFechasConvocatoriaCurso(Long idConvCentro,
														 Long idOfertamatrig,	
														 Long idCentro) {
		FechasConvocatoriaProjection fechaP = actasEvaluacionRepository.getFechasConvocatoriaCurso(idConvCentro,
																							       idOfertamatrig,
																							       idCentro);
		
		return modelMapper.map(fechaP, FechasConvocatoria.class);  
	}	
	
	@Override
	public List<ConvocatoriasCorrespondencia> getConvocatoriasCorrespondencia(Long idCentro, Long cAnno, Long idOfertamatrig, Long idConvunidad) {
		
		List<ConvocatoriasCorrespondenciaProjection> convocatoriasP = null;
		
		if (idConvunidad == -1L) {
			
			convocatoriasP = actasEvaluacionRepository.getConvocatoriasCorrespondenciaCurso(idCentro,cAnno,idOfertamatrig);
			
		} else {
			
			convocatoriasP = actasEvaluacionRepository.getConvocatoriasCorrespondencia(idCentro,cAnno,idOfertamatrig, idConvunidad);
		}
		 
		
		return convocatoriasP.stream().map(entity -> modelMapper.map(entity, ConvocatoriasCorrespondencia.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<DocumentoActa> getMateriasCurso(Long idOfertamatric, String tipo) {
		
		List<DocumentoActaProjection> documentoP = actasEvaluacionRepository.getMateriasCurso(idOfertamatric); 
		
		List<DocumentoActa> documento = documentoP.stream().map(entity -> modelMapper.map(entity, DocumentoActa.class)).collect(Collectors.toList());
		
		if (tipo.equals("FORM")) {
			
			if (documento.size()>0) {
				DocumentoActa doc = new DocumentoActa();
				doc.setId(99999L);
				doc.setDescripcion("Global");
				documento.add(doc);
			}
		}
		
		return documento;
		
	}

	@Override
	public Integer getNumOfertasDocumento(Long idCentro, Long cAnno, Long idDocumento) {
		return actasEvaluacionRepository.getNumOfertasDocumento(idCentro,cAnno,idDocumento);
	}

	@Override
	public List<DocumentoActa> getEtapasActas(Long idCentro, Long cAnno, Long idDocumento, Long idCurso) {
		
		List<DocumentoActaProjection> documentoP = actasEvaluacionRepository.getEtapasActas(idCentro,cAnno,idDocumento, idCurso); 

		return documentoP.stream().map(entity -> modelMapper.map(entity, DocumentoActa.class)).collect(Collectors.toList());
	}

	@Override
	public VistaPartesActas getCentroVistaPartesActas(Long idCentro) {
		
		VistaPartesActasProjection vistaP = actasEvaluacionRepository.getCentroVistaPartesActas(idCentro); 
		
		return modelMapper.map(vistaP, VistaPartesActas.class);	
	}

	@Override
	public List<FirmantesPartes> getFirmantesPartes(Long idCentro) {
		
		List<FirmantesPartesProjection> otrosProjection = actasEvaluacionRepository.getFirmantesPartes(idCentro);
		
		return otrosProjection.stream().map(entity -> modelMapper.map(entity, FirmantesPartes.class)).collect(Collectors.toList());
	}

}
