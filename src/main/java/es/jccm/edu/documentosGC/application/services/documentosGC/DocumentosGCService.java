package es.jccm.edu.documentosGC.application.services.documentosGC;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.documentosGC.adapter.out.repositories.adjuntosdocumento.AdjuntosDocumentoRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.convocatoriacentros.ConvocatoriaCentrosRepository;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ListNombreAdjuntosDto;
import es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacion.TlDocumentoRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.convocatoriareunion.ConvocatoriaReunionRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.convocatoriaunidad.ConvocatoriaUnidadRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.documentosGC.DocumentoGCRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.etapa.EtapaRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.ofertacursogenerico.OfertaCursoGenericoRepository;
import es.jccm.edu.documentosGC.adapter.out.repositories.ofertamateriagenerica.OfertaMateriaGenericaRepository;
import es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities.AdjuntosDocumento;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.TlDocumento;
import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.CentroDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.cursoacademicodoc.entities.CursoAcademicoDoc;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.AdjuntosListDetalle;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ContadoresInicio;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ConvocatoriaCentros;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ConvocatoriaReunion;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ConvocatoriaReunionList;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ConvocatoriaUnidad;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.CursoProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DetalleAdjuntos;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DetalleAdjuntosRequest;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentoGCNuevo;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGCList;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.Etapa;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.HistDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.OfertaMateriaGenerica;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.NuevoParte;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.OfertaCursoGenerico;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ParteGeneradoDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.PartesMensualesDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.PlazosEntregaDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.TipoAdjuntosList;
import es.jccm.edu.documentosGC.application.ports.in.centrodoc.ICentroDocGCService;
import es.jccm.edu.documentosGC.application.ports.in.cursoacademicodoc.ICursoAcademicoDocService;
import es.jccm.edu.documentosGC.application.ports.in.documentosGC.IDocumentosGCService;
import es.jccm.edu.documentosGC.application.ports.in.firmantesdigital.IFirmantesDigitalService;
import es.jccm.edu.documentosGC.application.ports.in.flujodoc.IFlujoDocumentosGCService;
import es.jccm.edu.documentosGC.application.ports.in.historicodoc.IHistoricoDocumentoGCService;
import es.jccm.edu.documentosGC.application.ports.in.rodal.RodalClientDgc;
import es.jccm.edu.documentosGC.application.ports.in.rodal.RodalExceptionDgc;
import es.jccm.edu.documentosGC.application.ports.in.tipdocadjunto.ITipoDocumentoAdjuntoGCService;
import es.jccm.edu.documentosGC.application.ports.in.tipodoc.ITipoDocumentosGCService;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.AdjuntosListDetalleProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.ContadoresInicioProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.ConvocatoriaReunionesProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.DocumentosGCListProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.HistDocumentosGCListProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.InformacionEstadoPojection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.ParteGeneradoDocumentosProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.PartesMensualesDocumentosProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.PlazosEntregaDocumentosProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.TipoAdjuntosListProjection;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities.AdjuntosFirmantes;
import es.jccm.edu.documentosGC.application.domain.flujodoc.entities.FlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.historicodoc.entities.HistoricoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.tipodocadjunto.entities.TipoDocumentoAdjuntoGC;

@Service
public class DocumentosGCService implements IDocumentosGCService {

	private static final Long ID_CONSEJO_ESCOLAR = 13L;	
	
    @PersistenceContext
	EntityManager em;
	
	@Autowired
	private DocumentoGCRepository documentosGCRepository;
	
	@Autowired
	private AdjuntosDocumentoRepository adjuntosDocumentoRepository;
	
	@Autowired
	private ConvocatoriaReunionRepository convocatoriaReunionRepository;	
	
	@Autowired
	private TlDocumentoRepository tlDocumentoRepository;	
	
	@Autowired
	private ICursoAcademicoDocService cursoService;

	@Autowired 
	private ICentroDocGCService centroService;
	
	@Autowired
	private ITipoDocumentosGCService tipoService;	
	
	@Autowired
	private IFlujoDocumentosGCService flujoService;
	
	@Autowired
	private IHistoricoDocumentoGCService historicoService;
	
	@Autowired
	ICentroDocGCService centroDocService;
	
	@Autowired
	private RodalClientDgc rodalclient;
	
	@Autowired
	ITipoDocumentoAdjuntoGCService tipoAdjuntoService;	
	
	@Autowired	
	private ConvocatoriaUnidadRepository convocatoriaUnidadRepository;
	
	@Autowired	
	private OfertaCursoGenericoRepository cursoRepository;
	
	@Autowired	
	private OfertaMateriaGenericaRepository materiaRepository;
	
	@Autowired	
	private ConvocatoriaCentrosRepository convocatoriacRepository;
	
	@Autowired	
	private EtapaRepository etapaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	IFirmantesDigitalService firmantesDigitalService;	
	
	private static final SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy"); 
	
	@Override
	public List<ContadoresInicio> getContadoresInicio(Long idCentro, 
											          Integer anno, 
												      Long idPerfil,
												      Long xEmpleado,
												      String fTomapos) 
	{
		List<ContadoresInicioProjection> contadoresP = null;
		
		if (idPerfil == 161L) {
			contadoresP = documentosGCRepository.getContadoresDirectores(idCentro,anno,idPerfil,xEmpleado);			
		} else {
			contadoresP = documentosGCRepository.getContadoresInspectores(anno,idPerfil,xEmpleado,fTomapos);
			
		}
		
		return contadoresP.stream().map(entity -> modelMapper.map(entity, ContadoresInicio.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<DocumentosGCList> getAllDocumentosGC(Long idCentro, 
													 Integer anno, 
													 Long idTipo, 
													 Long idEstado, 
													 Long idProvincia, 
													 Long idMunicipio, 
													 String abrev, 
													 Long idPerfil,
													 Long xEmpleado) {
		
		List<DocumentosGCListProjection> documentosGCListProjection = documentosGCRepository.getAllDocumentosGC(idCentro,
																												anno,
																												idTipo,
																												idEstado,
																												idProvincia,
																												idMunicipio,
																												abrev,
																												idPerfil,
																												xEmpleado); 
		
		return documentosGCListProjection.stream()
						.map(entity -> modelMapper.map(entity, DocumentosGCList.class)).collect(Collectors.toList());

	}
	
	@Override
	public List<DocumentosGCList> getAllDocumentosGCZona(Long idCentro, 
														 Integer anno, 
														 Long idTipo, 
														 Long idEstado,
														 Long idProvincia, 
														 Long idMunicipio, 
														 Long idPerfil, 
														 Long idUsuario, 
														 String abrev,
														 Long xEmpleado) {
		List<DocumentosGCListProjection> documentosGCListProjection = documentosGCRepository.getAllDocumentosGCZona(idCentro,
																													anno,
																													idTipo,
																													idEstado,
																													idProvincia,
																													idMunicipio,
																													idPerfil,
																													idUsuario,
																													abrev,
																													xEmpleado); 
		
		return documentosGCListProjection.stream()
						.map(entity -> modelMapper.map(entity, DocumentosGCList.class)).collect(Collectors.toList());
	}
	
	
	@Override

	public List<DocumentosGCList> getAllDocumentosGCInspectorCentro(Long idCentro, 
																	Integer anno, 
																	Long idTipo, 
																	Long idEstado, 
																	Long idProvincia, 
																	Long idMunicipio, 
																	Long xEmpleado, 
																	String fTomapos, 
																	String abrev) {
		
		try {
			Date d_fTomapos = formato.parse(fTomapos);
			
			List<DocumentosGCListProjection> documentosGCListProjection = documentosGCRepository.getAllDocumentosGCInspectorCentro(idCentro,
																																   anno,
																																   idTipo,
																																   idEstado,
																																   idProvincia,
																																   idMunicipio,  
																																   xEmpleado, 
																																   d_fTomapos,
																																   abrev); 
			
			
			return documentosGCListProjection.stream()
							.map(entity -> modelMapper.map(entity, DocumentosGCList.class)).collect(Collectors.toList());
		} catch (ParseException e) {

			e.printStackTrace();
		} 
		
		return null;	

	}
	
	@Override
	public List<DocumentosGCList> getAllDocumentosAR(Long idCentro, 
													 Integer anno, 
													 Long idTipo, 
													 Long idEstado,
													 Long xEmpleado) {
		
		List<DocumentosGCListProjection> documentosGCListProjection = documentosGCRepository.getAllDocumentosAR(idCentro,
																												anno,
																												idTipo,
																												idEstado,
																												xEmpleado); 
		
		return documentosGCListProjection.stream().map(entity -> modelMapper.map(entity, DocumentosGCList.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<DocumentosGCList> getAllDocumentosGCProvincial(Long idCentro, 
															   Integer anno, 
															   Long idTipo, 
															   Long idEstado,
															   Long idProvincia, 
															   Long idMunicipio, 
															   Long idPerfil, 
															   Long idUsuario, 
															   String abrev, 
															   Long idCentroProvincia,
															   Long xEmpleado) {
		
		List<DocumentosGCListProjection> documentosGCListProjection = documentosGCRepository.getAllDocumentosGCProvincial(idCentro,
																										       			  anno,
																													      idTipo,
																													      idEstado,
																													      idProvincia,
																													      idMunicipio,
																													      idPerfil,
																													      idUsuario,
																													      abrev,
																													      idCentroProvincia,
																													      xEmpleado); 

		return documentosGCListProjection.stream()
					.map(entity -> modelMapper.map(entity, DocumentosGCList.class)).collect(Collectors.toList());
	}

	@Override
	public DocumentosGC createDocumentoGC(DocumentoGCNuevo documento, List<MultipartFile> file, List<Long> listAdjuntos) throws RodalExceptionDgc, Exception  {		
		DocumentosGC newdocumento = null;
		HistoricoDocumentoGC newhistorico = null;
		RespuestaInsertarDoc docUp = null;
		Integer result = 0;
		
		try {			
			
			
			TipoDocumentosGC tipo = tipoService.findById(documento.getIdTipo());	
			
			// guardamos la entidad Documento 
			if (documento.getIdDocumento() == -1) {
				newdocumento = saveDocumentoGC(documento, tipo);
			} else {
				newdocumento = this.getDocumentoId(documento.getIdDocumento());				
			}						
		
			// guardamos la entidad Histórico documento
					
			newhistorico = saveHistoricoDocGC(documento, tipo, newdocumento);			
			
			if (file != null && file.size()>0) { 
				result = insertaAdjuntos(documento, file, newdocumento, newhistorico, docUp, listAdjuntos);			
			}
			
			if (result == -1) {				
				
				if (newhistorico != null && file != null && file.size() > 0) historicoService.delete(newhistorico);
				if (documento.getIdDocumento() == -1 && newdocumento != null && file != null && file.size() > 0) documentosGCRepository.delete(newdocumento);			
								
				throw new Exception("Error al adjuntar documentos");
			}
			
			return newdocumento;			
			
			} catch (RodalExceptionDgc e) {		
			
				historicoService.delete(newhistorico);
				if (documento.getIdDocumento() == -1) documentosGCRepository.delete(newdocumento);			
				
				throw new RodalExceptionDgc(e);		
		    
			} catch (Exception e) {		
			
				if (newhistorico != null && file != null)	historicoService.delete(newhistorico);
				if (documento.getIdDocumento() == -1 && newdocumento != null && file != null)   documentosGCRepository.delete(newdocumento);			
				
				throw new Exception();		
		    }	
				
	}

	private Integer insertaAdjuntos(DocumentoGCNuevo documento, List<MultipartFile> files,
			DocumentosGC newdocumento, HistoricoDocumentoGC newhistorico, RespuestaInsertarDoc docUp, List<Long> listAdjuntos)
			throws Exception {
		
		List<Long> idAdjuntos = new ArrayList<Long>();
		try {
			int i = 0;
			List<String> adjInsRodal = new ArrayList<String>();	
			
			for (MultipartFile file : files) {				
				
				TipoDocumentoAdjuntoGC tipoAdjunto = tipoAdjuntoService.getTipoDocumentoAdjuntosGCById(documento.getTipoAdjuntos().get(i));
				  
				AdjuntosDocumento adjuntoDoc = new AdjuntosDocumento();
				adjuntoDoc.setHistorial(newhistorico);
				adjuntoDoc.setTipo(tipoAdjunto);
				adjuntoDoc.setFirmado(0);		
				
				adjuntosDocumentoRepository.save(adjuntoDoc);
				idAdjuntos.add(adjuntoDoc.getId());
				listAdjuntos.add(adjuntoDoc.getId());
				
				docUp = rodalclient.insertaDoc(file, "MDGC", "DGC", adjuntoDoc.getId(), documento.getIdAnno(), documento.getIdCentro());
				
				adjInsRodal.add(docUp.getIdDoc());
				
				if(docUp.getIdDoc() != null) 
				{				
					adjuntoDoc = updateAdjuntoDocGC(adjuntoDoc, docUp.getIdDoc(), file.getOriginalFilename() );			
				
				} else {
					
					
					for (String rodal : adjInsRodal) {
						
						rodalclient.borrarDocumento(rodal, "MDGC",  "DGC");
					}	
					
					adjuntosDocumentoRepository.deleteAdjuntosDocumentoByHistorialId(newhistorico.getId());
					return -1;					
				}
				i++;
			}
				
				
		} catch (RodalExceptionDgc e) {			
			
			adjuntosDocumentoRepository.deleteAdjuntosDocumentoByHistorialId(newhistorico.getId());
			
			throw new RodalExceptionDgc(e);		
	    
		} catch (Exception e) {	
			
			adjuntosDocumentoRepository.deleteAdjuntosDocumentoByHistorialId(newhistorico.getId());
			
			throw new Exception();		
	    }		
		
		return 0;
	}

	private AdjuntosDocumento updateAdjuntoDocGC(AdjuntosDocumento adjuntoDoc, 
													String idRodal, 
													String nombreFichero) 
	{
		
        adjuntoDoc.setIdRodal(idRodal);
		adjuntoDoc.setTxFicheroRodal(nombreFichero);		
		adjuntosDocumentoRepository.save(adjuntoDoc);
		
		return adjuntoDoc; 
	}
	
	
	private HistoricoDocumentoGC saveHistoricoDocGC(DocumentoGCNuevo documento, 
												    TipoDocumentosGC tipo, 
													DocumentosGC newdocumento) 
	{
	
		HistoricoDocumentoGC newHistoricodocumento = new HistoricoDocumentoGC();
		
		//EstadoDocumentoGC estadoSiguiente = estadoService.findById(documento.getIdEstado());
		
		//EstadoDocumentoGC estadoActual = estadoService.estadoActualDocumentosGC(newdocumento.getId());			
		
		
		FlujoDocumentoGC flujo = flujoService.findById(documento.getIdFlujo());
		
		Calendar calendar = Calendar.getInstance();
		Date sysdate = calendar.getTime();
		
		newHistoricodocumento.setDocumento(newdocumento);
		newHistoricodocumento.setFlujo(flujo);
		newHistoricodocumento.setRegistro(sysdate);
		newHistoricodocumento.setObservaciones(documento.getTxObservaciones());
		newHistoricodocumento.setUsuario(documento.getIdUsuario());	
		
		historicoService.save(newHistoricodocumento);
		
		return newHistoricodocumento; 
	}
	
	
	

	private DocumentosGC saveDocumentoGC(DocumentoGCNuevo documento, TipoDocumentosGC tipo) {
		
		CursoAcademicoDoc anno = cursoService.findById(documento.getIdAnno());
		
		CentroDocumentosGC centro = centroService.findById(documento.getIdCentro());	
		
		TlDocumento tldocumento = null;
		if(documento.getIdTlDocumento() != null) {
			Optional<TlDocumento> optionalTldocumento = tlDocumentoRepository.findById(documento.getIdTlDocumento());
			tldocumento = optionalTldocumento.orElse(null);
		}
		
		ConvocatoriaReunion convocatoriaReunion = null;
		if(documento.getIdConvreunion() != null) {
			Optional<ConvocatoriaReunion> optionalConvReu = convocatoriaReunionRepository.findById(documento.getIdConvreunion());
			convocatoriaReunion = optionalConvReu.orElse(null);
		}
		
		ConvocatoriaUnidad convocatoriaUnidad = null;
		if(documento.getIdConvunidad() != null) {
			Optional<ConvocatoriaUnidad> optionalConvU = convocatoriaUnidadRepository.findById(documento.getIdConvunidad());
			convocatoriaUnidad = optionalConvU.orElse(null);
		}
		
		OfertaCursoGenerico ofertamatrig = null;
		if(documento.getIdOfertamatrig() != null) {
			Optional<OfertaCursoGenerico> optionalOfertamatrig = cursoRepository.findById(documento.getIdOfertamatrig());
			ofertamatrig = optionalOfertamatrig.orElse(null);
		}
		
		OfertaMateriaGenerica materia = null;
		if(documento.getIdMateriac() != null) {
			Optional<OfertaMateriaGenerica> optionalMateriac = materiaRepository.findById(documento.getIdMateriac());
			materia = optionalMateriac.orElse(null);
		}		
		
		ConvocatoriaCentros convocatoriaC = null;
		if(documento.getIdConvCentro()!= null) {
			Optional<ConvocatoriaCentros> optionalConvocatoriac = convocatoriacRepository.findById(documento.getIdConvCentro());
			convocatoriaC = optionalConvocatoriac.orElse(null);
		}
		
		Etapa etapa  = null;
		if(documento.getIdEtapa()!= null) {
			Optional<Etapa> etapac = etapaRepository.findById(documento.getIdEtapa());
			etapa = etapac.orElse(null);
		}
		
		DocumentosGC newDocumento = new DocumentosGC();
		
		if  (anno != null && centro != null) {
			
    		newDocumento.setAnno(anno);
			newDocumento.setCentro(centro);
			newDocumento.setIdTipoDocumento(tipo);
			newDocumento.setDescripcion(documento.getDsDescripcion());
			newDocumento.setObservaciones(documento.getTxObservaciones());	
			newDocumento.setDsParaus(documento.getDsParaus());
			newDocumento.setConvocatoriaReunion(convocatoriaReunion);	
			newDocumento.setConvocatoriaUnidad(convocatoriaUnidad);
			newDocumento.setTldocumento(tldocumento);
			newDocumento.setOfertamatrig(ofertamatrig);
			newDocumento.setMateriac(materia);
			newDocumento.setConvocatoriacentro(convocatoriaC);
			newDocumento.setEtapa(etapa);
			if (documento.getFsesion() != null && !documento.getFsesion().equals("") ) 
			{
				
				Date fecha;
				try {
					String fSesion = documento.getFsesion();
					fecha = formato.parse(fSesion);
					newDocumento.setFsesion(fecha);	
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}	
			
			
			DocumentosGC newdocumento =  documentosGCRepository.save(newDocumento);
			
			return newdocumento;			
		}
		
		return null;
	}

	@Override
	public DocumentosGC getDocumentoId(Long idDocumento) {
		
		Optional<DocumentosGC> documento =  documentosGCRepository.findById(idDocumento) ;
		
		return documento.orElse(null);
		
	}

	@Override
	public List<InformacionEstadoPojection> getInformacionEstadoDocumento(Long idDocumento, Long idHistorial) {
		
		return documentosGCRepository.getInformacionEstadoDocumento(idDocumento,idHistorial);				

	}
	
	@Override
	public List<HistDocumentosGC> getHistDocumentosGC(Long idDocumento) {
		
		List<HistDocumentosGCListProjection> histDocumentosListProjection = documentosGCRepository.getHistDocumentosGC(idDocumento);
		
		return histDocumentosListProjection.stream()
				.map(entity -> modelMapper.map(entity,HistDocumentosGC.class)).collect(Collectors.toList());
		
	}
	
	@Transactional
	public int deleteDocumento(Long idDocumento) throws RodalExceptionDgc, Exception {	
		
		Integer succes = 0;
		
		Optional<DocumentosGC> documento = documentosGCRepository.findById(idDocumento); 
			
		List<HistoricoDocumentoGC> listHistoricos = historicoService.getHistoricoDocumentoId(documento.get());
		
		if (listHistoricos != null && listHistoricos.size() == 1) {			
					
			List<AdjuntosDocumento> adjuntos = adjuntosDocumentoRepository.findAdjuntosDocumentoByHistorialId(listHistoricos.get(0).getId());
				
				try {					
					
					for (AdjuntosDocumento adjunto : adjuntos) {
						
						rodalclient.borrarDocumento(adjunto.getIdRodal(),"DGC","DGC");
						
						firmantesDigitalService.deleteByIdAdjunto(adjunto.getId());						
						
					}				
					
					adjuntosDocumentoRepository.deleteAdjuntosDocumentoByHistorialId(listHistoricos.get(0).getId());			
				
				} catch (RodalExceptionDgc e) {	
					
					for (AdjuntosDocumento adjunto : adjuntos) {
						
						firmantesDigitalService.deleteByIdAdjunto(adjunto.getId());			
						
					}
					
					// aunque falle el borrado en Rodal borramos las tablas relacionadas
					adjuntosDocumentoRepository.deleteAdjuntosDocumentoByHistorialId(listHistoricos.get(0).getId());
					
					historicoService.delete(listHistoricos.get(0));
					
					documentosGCRepository.deleteById(idDocumento);
					
					succes = -1;					
					
		            //throw new RodalExceptionMfct("ERROR Borrando de Rodal:" + e.getMessage());
		        }
			
			
			historicoService.delete(listHistoricos.get(0));	
			
			
		}
		
		documentosGCRepository.deleteById(idDocumento);
		
		return succes;
	}

	@Override
	public List<ConvocatoriaReunionList> getConvocatorias(Long idCentro, Long anno, Long idTipo) {
		
		List<ConvocatoriaReunionesProjection> convocatoriaReunionesProjection = null;
		
		if (idTipo == ID_CONSEJO_ESCOLAR) {
			convocatoriaReunionesProjection = documentosGCRepository.getConvocatoriasConsejoEscolar(idCentro,anno,idTipo); 
		} else {
			convocatoriaReunionesProjection = documentosGCRepository.getConvocatorias(idCentro,anno,idTipo); 
		}		 
		
		return convocatoriaReunionesProjection.stream().map(entity -> modelMapper.map(entity, ConvocatoriaReunionList.class)).collect(Collectors.toList());	
	}
	
	
	@Override
	public Integer getNumeroDocumentosSinCrear(Long idCentro, Long anno, String pdsAbrev) {
		return documentosGCRepository.getNumeroDocumentosSinCrear(idCentro, anno, pdsAbrev);
	}
	
	@Override
	public List<PlazosEntregaDocumentosGC> getDocumentosNoCreados(Long idCentro, Long anno, String pdsAbrev) {
        List<PlazosEntregaDocumentosProjection> convocatoriaReunionesProjection = documentosGCRepository.getDocumentosNoCreados(idCentro,anno,pdsAbrev); 
		
		return convocatoriaReunionesProjection.stream().map(entity -> modelMapper.map(entity, PlazosEntregaDocumentosGC.class)).collect(Collectors.toList());
	}

	@Override
	public List<PlazosEntregaDocumentosGC> getMensajesPlazosEntregaFinalizado(Long idCentro, Long anno, String pdsAbrev) {
		List<PlazosEntregaDocumentosProjection> convocatoriaReunionesProjection = documentosGCRepository.getMensajesPlazosEntregaFinalizado(idCentro,anno,pdsAbrev); 
		
		return convocatoriaReunionesProjection.stream().map(entity -> modelMapper.map(entity, PlazosEntregaDocumentosGC.class)).collect(Collectors.toList());
	}

	@Override
	public List<PartesMensualesDocumentosGC> getPartesMensuales(Long idCentro, Long anno) {

		List<PartesMensualesDocumentosProjection> partesMensualesProjection = documentosGCRepository.getPartesMensuales(idCentro,anno); 
		
		return partesMensualesProjection.stream().map(entity -> modelMapper.map(entity, PartesMensualesDocumentosGC.class)).collect(Collectors.toList());
	}

	@Override
	
	public List<ParteGeneradoDocumentosGC> getParteGenerados(Long cAnno, Long xCentro){
		
		List<ParteGeneradoDocumentosProjection> partesGeneradosProjection = documentosGCRepository.getParteGenerados(cAnno,xCentro); 
		
		return partesGeneradosProjection.stream().map(entity -> modelMapper.map(entity, ParteGeneradoDocumentosGC.class)).collect(Collectors.toList());
		
	}
	
@Override
	
	public List<CursoProjection> getMesCurso(Long cAnno){
		
		List<CursoProjection> mesCursoProjection = documentosGCRepository.getMesCurso(cAnno); 
		
		return mesCursoProjection.stream().map(entity -> modelMapper.map(entity, CursoProjection.class)).collect(Collectors.toList());
		
	}

	@Override
	public Integer getNumeroPartesCreados(Long idCentro, Long anno, Long mes) {
		return documentosGCRepository.getNumeroPartesCreados(idCentro, anno, mes);
}

	

	@Override
	public List<TipoAdjuntosList> getInformacionTiposAdjuntosDoc(Long anno, Long idTipDoc, Long idPerfil, Long idFlujo) {
		
		
		List<TipoAdjuntosListProjection> tiposP = documentosGCRepository.getInformacionTiposAdjuntosDoc(anno,idTipDoc,idPerfil,idFlujo); 
		
		return tiposP.stream().map(entity -> modelMapper.map(entity, TipoAdjuntosList.class)).collect(Collectors.toList());
		
		
		
	}

	@Override
	public List<AdjuntosListDetalle> getInformacionAdjuntosDetalle(Long idDocumento, Long idPerfil) {
		
		List<AdjuntosListDetalleProjection> adjuntosP = documentosGCRepository.getInformacionTiposAdjuntosDoc(idDocumento,idPerfil); 
		
		return adjuntosP.stream().map(entity -> modelMapper.map(entity, AdjuntosListDetalle.class)).collect(Collectors.toList());
	}

	@Override
	public Integer updateAdjuntosDocumentoGC(DetalleAdjuntos detalleAdjuntos, List<MultipartFile> files, Long idEmpleado) throws RodalExceptionDgc {		
		
		int i=0;
		for (DetalleAdjuntosRequest adjunto : detalleAdjuntos.getAdjuntos()) {
			
			try {
			
				if (adjunto.getOperacion().equals("I")) {
					
					addAdjuntoDetalle(detalleAdjuntos, files.get(i), adjunto, idEmpleado);	
					i++;
					
				} else if (adjunto.getOperacion().equals("U")) {
					
					
					rodalclient.actualizaDoc(files.get(i), adjunto.getIdDocHisRodal());
					
					Optional<AdjuntosDocumento> adjuntoD = adjuntosDocumentoRepository.findById(adjunto.getIdAdjunto());
					
					updateAdjuntoDocGC(adjuntoD.get(),adjunto.getIdDocHisRodal(),files.get(i).getOriginalFilename());					
	
					i++; 
										
					
				} else if (adjunto.getOperacion().equals("D") && adjunto.getIdDocHisRodal() != null){
					
						rodalclient.borrarDocumento(adjunto.getIdDocHisRodal(),"DGC","DGC");
						
						Optional<AdjuntosFirmantes> firmante = firmantesDigitalService.findByAdjuntoIdAndEmpleadoId(adjunto.getIdAdjunto(),idEmpleado);
						
						if(firmante.isPresent()) {
							
							firmantesDigitalService.deleteById(firmante.get().getId());
							
						}					
						
						Optional<AdjuntosDocumento> adjuntoD = adjuntosDocumentoRepository.findById(adjunto.getIdAdjunto());
						
						if(adjuntoD.isPresent()) {
							adjuntosDocumentoRepository.delete(adjuntoD.get());
						}					
				}	
			} catch (RodalExceptionDgc e) {				
	            throw new RodalExceptionDgc("ERROR operación sobre adjunto:" + e.getMessage());
	        } catch (InsertarDocFault e) {
	        	throw new RodalExceptionDgc("ERROR operación sobre adjunto:" + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
			
			
		}
		
		
		return 0;
		
	}

	private void addAdjuntoDetalle(DetalleAdjuntos detalleAdjuntos, 
								  MultipartFile files,
								  DetalleAdjuntosRequest adjunto,
								  Long idEmpleado) throws RodalExceptionDgc, InsertarDocFault, IOException {
		HistoricoDocumentoGC historico = historicoService.findById(detalleAdjuntos.getIdHistorial());		
		
		TipoDocumentoAdjuntoGC tipo = tipoAdjuntoService.getTipoDocumentoAdjuntosGCById(adjunto.getIdTipoAdjunto());
		
		AdjuntosDocumento adjuntoDoc = new AdjuntosDocumento();
		adjuntoDoc.setHistorial(historico);
		adjuntoDoc.setTipo(tipo);
		adjuntoDoc.setFirmado(0);		
		
		AdjuntosDocumento newAdjunto = adjuntosDocumentoRepository.save(adjuntoDoc);
		
		RespuestaInsertarDoc docUp = rodalclient.insertaDoc(files, 
									   					    "MDGC", "DGC", 
									   					    adjuntoDoc.getId(), 
									   					    detalleAdjuntos.getIdAnno(), 
									   					    detalleAdjuntos.getIdCentro());
		
		
		if(docUp.getIdDoc() != null) 
		{	
			adjuntoDoc = updateAdjuntoDocGC(adjuntoDoc, docUp.getIdDoc(), files.getOriginalFilename() );
			
			if (adjunto.getFirmable() != null && adjunto.getFirmable().equals("1") ) {			
				firmantesDigitalService.createAdjuntosFirmante(adjuntoDoc.getId(), idEmpleado);
			}			
			
		} else 
		{
			adjuntosDocumentoRepository.delete(newAdjunto);	
			throw new RodalExceptionDgc("ERROR al adjuntar fichero");
		}
	}
	
	
	@Override
	public Integer createParte(NuevoParte parte) throws SQLException {
	
	   return createParteBBDD(parte);
	}	

	private Integer createParteBBDD(NuevoParte parte) throws SQLException {
		
		java.util.Date utilDate = parte.getFremision();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		       
		Session session = em.unwrap(Session.class);
		
		
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						     
				   CallableStatement function = connection.prepareCall("{ ? = call DELPHOS.TLF_INSERTAPARTAUS(?,?,?,?,?,?)}");
				   					 function.setInt(2, parte.getCentro());
						             function.setInt(3, parte.getAnno());
						             function.setInt(4, parte.getMes());
						             function.setDate(5, sqlDate);
							         function.setInt(6, parte.getAnnonatural());
							         function.setInt(7, parte.getUsuario());
							         function.registerOutParameter(1, Types.INTEGER);
				   	                 function.execute();
				   	               
							        return function;
							    }
			});

       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }  finally {
	    	   callableStatement.close();
		}
	}

	@Override
	public String getAdjuntoFirmable(Long idAjunto, Long idEmpleado) {
		return documentosGCRepository.getAdjuntoFirmable(idAjunto, idEmpleado);
	}

	@Override
	public List<Integer> getNombreDocumentoEnUso(ListNombreAdjuntosDto lista) {
		List<Integer> result = new ArrayList<Integer>(lista.getLsNames().size());
		List<String> lNames = lista.getLsNames();
		for (String name : lNames) {			
			if (name != null) {
				Integer nUsa = documentosGCRepository.getNombreDocumentoEnUso(lista.getIdTipoDocumento(),
																			  lista.getIdCentro(),
																			  name);
				
				if (nUsa>0) {
					result.add(4);
				} else {
					result.add(-1);
				}
				
			} else break; 
					
		}
		
		return result;
	}	
}
