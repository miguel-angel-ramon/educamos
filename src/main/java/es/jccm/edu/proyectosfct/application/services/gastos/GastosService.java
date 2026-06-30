package es.jccm.edu.proyectosfct.application.services.gastos;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.BASE64DecodedMultipartFile;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.TicketAlumnadoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.TicketTutorDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.GastoAlumnadoHistorialRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.GastoAlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.GastoAnexoHistorialRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.GastoAnexoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.GastoFlujoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.GastoTutorHistorialRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.GastoTutorRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.ImpuestoTipoServicioRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.PeriodoGastoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.TicketAlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.TicketTutorRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.gastos.TipoGastoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual.TutoresFctDualRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.CabeceraAnexoVIII;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAlumnadoHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAnexo;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAnexoHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoEstadoFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoFlujo;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoTutor;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoTutorHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.HistoricoFlujoGastos;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ImpuestoTipoServicio;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoAnexoVIII;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoGastoAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoGastoAnexo;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoGastoTutores;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.PeriodoGasto;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketTutor;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TipoGasto;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.CabeceraAnexoVIIIProjection;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.DatosCabeceraAnexoVIAlumnadoProjection;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.DatosCabeceraAnexoVITutorProjection;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.GastoEstadoFlujoSiguienteProjection;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.HistoricoFlujoGastosProjection;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.ListadoAnexoVIIIProjection;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.ListadoGastoAlumnadoProjection;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.ListadoGastoAnexoProjection;
import es.jccm.edu.proyectosfct.application.domain.gastos.projection.ListadoGastoTutoresProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.ports.in.gastos.IGastosService;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@Service
public class GastosService implements IGastosService {
	
	private static final Long ANEXO_VI_TUTORES = 3L;
	private static final Long ANEXO_VI_ALUMNADO = 4L;
	private static final Long ANEXO_VIII = 5L;
	
	private static final Long ID_PERFIL_ALUMNADO = 5000L;	
	private static final Long ID_PERFIL_CENTRO = 161L;
	private static final Long ID_PERFIL_PROFESORADO = 2L;
	private static final Long ID_PERFIL_GESTOR = 11207L;
	
	private static final String ABREV_TIPO_TUTORES = "GAS_TUT";
	private static final String ABREV_TIPO_ALUMNADO = "GAS_ALU";
	private static final String ABREV_ANEXO_VIBIS = "ANE_VI_bis";
	private static final String ABREV_ANEXO_VI = "ANE_VI";
	private static final String ABREV_ANEXO_VIII = "ANE_VIII";
	
	@Autowired
	private PeriodoGastoRepository periodoGastoRepository;
	
	@Autowired
	private ConveniosFctRepository conveniosFctRepository;
	
	@Autowired
	private TipoGastoRepository tipoGastoRepository;
	
	@Autowired
	private GastoFlujoRepository gastoFlujoRepository;
	
	@Autowired
	private GastoTutorHistorialRepository gastoTutorHistorialRepository;
	
	@Autowired
	private GastoTutorRepository gastoTutorRepository;
	
	@Autowired
	private GastoAlumnadoHistorialRepository gastoAlumnadoHistorialRepository;
	
	@Autowired
	private GastoAlumnadoRepository gastoAlumnadoRepository;
	
	@Autowired
	private GastoAnexoRepository gastoAnexoRepository;
	
	@Autowired
	private GastoAnexoHistorialRepository gastoAnexoHistorialRepository;
	
	@Autowired
	private TutoresFctDualRepository tutoresFctDualRepository;
	
	@Autowired
	private AlumnadoRepository alumnadoRepository;
	
	@Autowired
	private TicketTutorRepository ticketTutorRepository;
	
	@Autowired
	private TicketAlumnadoRepository ticketAlumnadoRepository;
	
	@Autowired
	ImpuestoTipoServicioRepository impuestoTipoRepository;
	
    @Autowired
    private IRodalClient rodalClient;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void generarAnexoVIII(Long idPerfil, Long idCentro, Long xUsuarioDelphos, Long idPeriodoGasto,
			String abrevTipoGasto, Integer cAnno) throws Exception {
		
		Centro centro = new Centro();
		centro.setId(idCentro);
		PeriodoGasto periodo = new PeriodoGasto();
		periodo.setId(idPeriodoGasto);
		
		Long idProvincia = gastoAnexoRepository.getIdProvinciaByAnexo(idPerfil, xUsuarioDelphos, idCentro);
		
		generaAnexo(xUsuarioDelphos, idPerfil, abrevTipoGasto, -1L, cAnno, centro, periodo, -1L,  -1L, -1L, idProvincia);
		
//		Long idUsuario, Long idPerfil, String abrevTipoGasto, Long idFlujo, Integer cAnno, Centro centro, PeriodoGasto periodo, Long idTutor,
//		Long idFamilia, Long idCurso, Long idModalidad) throws Exception  
	}
	
	@Override
	public void generarAnexosVI(Long idPerfil, 
								Long idCentro, 
								Long xUsuarioDelphos, 
								Long idPeriodoGasto,
								String abrevTipoGasto, 
								Integer cAnno,
								Long idTutor,
								Long idCurso,
								Long idUnidad) throws Exception {
		
		Centro centro = new Centro();
		centro.setId(idCentro);
		PeriodoGasto periodo = new PeriodoGasto();
		periodo.setId(idPeriodoGasto);
		
		generaAnexo(xUsuarioDelphos, 
					idPerfil, 
					abrevTipoGasto, 
					-1L, 
					cAnno, 
					centro, 
					periodo, 
					idTutor,  
					idCurso, 
					idUnidad, 
					-1L);

	}
	
	@Override
	public GastoTutorHistorial createSiguienteEstadoFlujoTutor(Long idUsuario, 
															   GastoTutorHistorial gastoTutorHistorialIn, 
															   Long idPerfil,
															   String abrevTipoGasto) throws Exception  {
		
		GastoTutorHistorial historial = new GastoTutorHistorial();				
		historial.setFechaRegistro(new Date());
		historial.setGastoFlujo(gastoTutorHistorialIn.getGastoFlujo());
		historial.setGastoTutor(gastoTutorHistorialIn.getGastoTutor());
		historial.setIdUsuario(idUsuario);
		historial.setObservaciones(gastoTutorHistorialIn.getObservaciones());		
		
		gastoTutorHistorialRepository.save(historial);
		
		/*this.generaAnexo(idUsuario, 
				         idPerfil, 
				         abrevTipoGasto,
				         gastoTutorHistorialIn.getGastoFlujo().getId(),
				         gastoTutorHistorialIn.getGastoTutor().getPeriodoGasto().getAnnoPeriodo(),
				         gastoTutorHistorialIn.getGastoTutor().getCentro(),
				         gastoTutorHistorialIn.getGastoTutor().getPeriodoGasto(),
				         -1L,
				         -1L,
				         -1L,
				         -1L);*/
		
		return historial;
	}
	
	@Override
	public GastoAlumnadoHistorial createSiguienteEstadoFlujoAlumnado(Long idUsuario, 
															   		 GastoAlumnadoHistorial gastoAlumnadoHistorialIn, 
															   		 Long idPerfil,
															   		 String abrevTipoGasto,
															   		 Long idTutor,			
															   		 Long idCurso,
															   		 Long idUnidad) throws Exception  {
		
		GastoAlumnadoHistorial historial = new GastoAlumnadoHistorial();				
		historial.setFechaRegistro(new Date());
		historial.setGastoFlujo(gastoAlumnadoHistorialIn.getGastoFlujo());
		historial.setGastoAlumno(gastoAlumnadoHistorialIn.getGastoAlumno());
		historial.setIdUsuario(idUsuario);
		historial.setObservaciones(gastoAlumnadoHistorialIn.getObservaciones());	
		
		gastoAlumnadoHistorialRepository.save(historial);
		
		//this.generaAnexo(idUsuario, 
		//		         idPerfil, 
		//		         abrevTipoGasto,
		//		         gastoAlumnadoHistorialIn.getGastoFlujo().getId(),
		//		         gastoAlumnadoHistorialIn.getGastoAlumno().getPeriodoGasto().getAnnoPeriodo(),
		//		         gastoAlumnadoHistorialIn.getGastoAlumno().getCentro(),
		//		         gastoAlumnadoHistorialIn.getGastoAlumno().getPeriodoGasto(),
		//		         idTutor,
		//		         idCurso,
		//		         idUnidad,
		//		         -1L);				
		
		return historial;
	}	
	
	@Override
	public GastoAnexoHistorial createSiguienteEstadoFlujoAnexo(GastoAnexoHistorial gastoHistorialIn, 
															   Long idUsuario) throws Exception  {
		
		GastoAnexoHistorial historial = new GastoAnexoHistorial();				
		historial.setFechaRegistro(new Date());
		historial.setGastoAnexo(gastoHistorialIn.getGastoAnexo());		
		historial.setGastoFlujo(gastoHistorialIn.getGastoFlujo());
		historial.setIdUsuario(idUsuario);
		historial.setObservaciones(gastoHistorialIn.getObservaciones());	
		
		gastoAnexoHistorialRepository.save(historial);			
		
		return historial;
	}

	private void generaAnexo (Long idUsuario, 
							  Long idPerfil, 
							  String abrevTipoGasto,
							  Long idFlujo,
							  Integer cAnno,
							  Centro centro,
							  PeriodoGasto periodo,
							  Long idTutor,
							  Long idCurso,
							  Long idUnidad,
							  Long idProvincia) throws Exception  {
		
		if (ID_PERFIL_CENTRO.equals(idPerfil) || ID_PERFIL_PROFESORADO.equals(idPerfil)) {			
			
			//Optional<GastoFlujo> flujo = gastoFlujoRepository.findById(idFlujo); 
			
			//if(flujo.isPresent() && flujo.get().getEstadoDestino().getAbrev().equals("VC") ) {				
				
				byte[] fileAnexo = null;
				//Integer numeroVal = -1;
				
				//if (abrevTipoGasto.equals(ABREV_TIPO_TUTORES)) numeroVal = gastoTutorRepository.getListadoGastoTutoresPendientes(cAnno,centro.getId(),-1L,periodo.getId());
			    //else numeroVal = gastoAlumnadoRepository.getListadoGastoAlumnadoPendientes(cAnno,centro.getId(),idTutor,periodo.getId(),idCurso,idUnidad);					
					
				//if  (numeroVal == 0) 
				//{
					
					if (abrevTipoGasto.equals(ABREV_TIPO_TUTORES)) 	fileAnexo = exportReportAnexoGastoTipo(cAnno,
																				                           centro.getId(), 
																				                           periodo.getId(),
																				                           ANEXO_VI_TUTORES,
																				                           -1L,-1L,-1L,-1L,-1L,-1L);
					else fileAnexo = exportReportAnexoGastoTipo(cAnno, 
																centro.getId(), 
																periodo.getId(), 
																ANEXO_VI_ALUMNADO,
																idTutor,
																idCurso,
																idUnidad,
																-1L,-1L,-1L);							
				
					TipoGasto tipo = null;
				
					if (abrevTipoGasto.equals(ABREV_TIPO_TUTORES)) tipo = tipoGastoRepository.findByAbrev(ABREV_ANEXO_VIBIS);
				    else tipo = tipoGastoRepository.findByAbrev(ABREV_ANEXO_VI);
					
					GastoAnexo anexo = new GastoAnexo();
					GastoAnexoHistorial historico =  new GastoAnexoHistorial();
					
					if (abrevTipoGasto.equals(ABREV_TIPO_TUTORES)) {
						anexo = gastoAnexoRepository.findByPeriodoGastoIdAndTipoGastoIdAndCentroId(periodo.getId(),
																								   tipo.getId(),
																								   centro.getId());						
					} else {
						anexo = gastoAnexoRepository.findByPeriodoGastoIdAndTipoGastoIdAndCentroIdAndTutorFctIdAndIdCursoAndIdUnidad(periodo.getId(),
																																	 tipo.getId(),
																																	 centro.getId(),
																																	 idTutor,
																																	 idCurso,
																																	 idUnidad);
					}
					
					
					GastoFlujo flujoAnexo = null;
					if (anexo == null) {
						
						anexo = new GastoAnexo();	
						flujoAnexo = gastoFlujoRepository.findByEstadoOrigenIsNullAndIdPerfilAndTipoGastoId(idPerfil,tipo.getId());
						
					} else {					
						
						String estado = gastoFlujoRepository.getEstadoAnexo(anexo.getId());
						
						if (abrevTipoGasto.equals(ABREV_TIPO_TUTORES)) {
							if (estado.equals("PFC")) flujoAnexo = gastoFlujoRepository.findByEstadoOrigenIsNullAndIdPerfilAndTipoGastoId(idPerfil,tipo.getId());
							else flujoAnexo = gastoFlujoRepository.findByIdPerfilAndTipoGastoIdAndEstadoOrigenIdAndEstadoDestinoId(idPerfil,tipo.getId(),10L,2L);					
						} else {
							if (estado.equals("PVP") || estado.equals("PSUBA")) flujoAnexo = gastoFlujoRepository.findByEstadoOrigenIsNullAndIdPerfilAndTipoGastoId(idPerfil,tipo.getId());
							else flujoAnexo = gastoFlujoRepository.findByIdPerfilAndTipoGastoIdAndEstadoOrigenIdAndEstadoDestinoId(idPerfil,tipo.getId(),10L,2L);
							
						}
						
						
					}
					
					//if (anexo != null) historico = gastoAnexoHistorialRepository.findByGastoAnexoId(anexo.getId());
					//else anexo = new GastoAnexo();					
					
					//if (historico.getId() == null)  {
						
						anexo.setCentro(centro);
						anexo.setPeriodoGasto(periodo);		
						anexo.setTipoGasto(tipo);
						if (abrevTipoGasto.equals(ABREV_TIPO_ALUMNADO)) {
							Optional<TutorFctDual> tutor =  tutoresFctDualRepository.findById(idTutor);
							if (tutor.isPresent()) {
								anexo.setTutorFct(tutor.get());							
							}	
						anexo.setIdCurso(idCurso==-1L?null:idCurso);
						anexo.setIdUnidad(idUnidad==-1L?null:idUnidad);	
						
						}
						gastoAnexoRepository.save(anexo);
											
						historico.setFechaRegistro(new Date());
						historico.setIdUsuario(idUsuario);
						historico.setGastoAnexo(anexo);
						historico.setGastoFlujo(flujoAnexo);
						if (abrevTipoGasto.equals(ABREV_TIPO_TUTORES))  historico.setNombreFichero("AnexoVI-bis.pdf");
						else historico.setNombreFichero("AnexoVI.pdf"); 
						gastoAnexoHistorialRepository.save(historico);						
					//}		
					
					
					BASE64DecodedMultipartFile file = null;
					if (abrevTipoGasto.equals(ABREV_TIPO_TUTORES))  file = new BASE64DecodedMultipartFile(fileAnexo,"AnexoVI-bis.pdf");
					else file = new BASE64DecodedMultipartFile(fileAnexo,"AnexoVI.pdf");
				
					RespuestaInsertarDoc respuestaRodal = null ;
					Boolean actualizado = false;
					String entidad = abrevTipoGasto.equals(ABREV_TIPO_TUTORES)?"ANE_VIB_":"ANE_VI_";
					
					if (historico.getIdAneHisRodal() == null) {
						
						respuestaRodal = rodalClient.insertaDoc(file, 
												                "MFCT", 
												                entidad, 
												                historico.getId(),
												                cAnno.longValue(),
												                centro.getId(),
												                -1L,"-1",-1L,-1L);
					} else {
						
						actualizado = rodalClient.actualizaDoc(file, 
												               "MFCT", 
												               entidad, 
												               historico.getId(),"-1",-1L,-1L);
						
					}					
				//}			
			//}	
		}else {
			if (abrevTipoGasto.equals(ABREV_ANEXO_VIII)) {			
//				Optional<GastoFlujo> flujo = gastoFlujoRepository.findById(idFlujo); 
//				
//				if(flujo.isPresent()) {		
					byte[] fileAnexo = null;
					
					fileAnexo = exportReportAnexoGastoTipo(cAnno, 
														   centro.getId(), 
														   periodo.getId(),
														   ANEXO_VIII,
														   -1L,
														   -1L,
														   -1L, 
														   idProvincia, 
														   idUsuario, 
														   idPerfil);
					
					TipoGasto tipo = tipoGastoRepository.findByAbrev(abrevTipoGasto);
					
					
					GastoAnexo gastoAnexo = new GastoAnexo();
					GastoAnexoHistorial historial =  new GastoAnexoHistorial();
					
					gastoAnexo = gastoAnexoRepository.findByPeriodoGastoIdAndTipoGastoIdAndIdProvincia(periodo.getId(),
																								       tipo.getId(),
																								       idProvincia);
															
					GastoFlujo flujoAnexo = null;
					
					if (gastoAnexo == null) {
						flujoAnexo = gastoFlujoRepository.findByEstadoOrigenIsNullAndIdPerfilAndTipoGastoId(idPerfil,tipo.getId());						
					} else 
					{
						String estado = gastoFlujoRepository.getEstadoAnexo(gastoAnexo.getId());						
						if (estado.equals("ANEG")) { 
						  flujoAnexo = gastoFlujoRepository.findByEstadoOrigenIsNullAndIdPerfilAndTipoGastoId(idPerfil,tipo.getId());
						} else {
						  flujoAnexo = gastoFlujoRepository.findByIdPerfilAndTipoGastoIdAndEstadoOrigenIdAndEstadoDestinoId(idPerfil,tipo.getId(),14L,11L);
						}						
					}
					
					if (gastoAnexo == null) 
					{						
						gastoAnexo = new GastoAnexo();
						gastoAnexo.setPeriodoGasto(periodo);
						gastoAnexo.setTipoGasto(tipo);
						gastoAnexo.setIdProvincia(idProvincia);
						gastoAnexoRepository.save(gastoAnexo);
					}					
				
					//CREAR DATOS EN FCT_ANEXOS_HISTORIAL
	
					historial.setFechaRegistro(new Date());
					historial.setGastoAnexo(gastoAnexo);
					historial.setGastoFlujo(flujoAnexo);
					historial.setIdUsuario(idUsuario);
					historial.setNombreFichero("AnexoVIII.pdf");
					
					gastoAnexoHistorialRepository.save(historial);
										
					
					RespuestaInsertarDoc respuestaRodal = null;
					Boolean actualizado = false;
					BASE64DecodedMultipartFile file = new BASE64DecodedMultipartFile(fileAnexo,"AnexoVIII.pdf");
					
					if (historial.getIdAneHisRodal() == null) {
						respuestaRodal = rodalClient.insertaDoc(file, 
						   									    "MFCT", 
															    "ANEVIII_", 
															    historial.getId(),
															    cAnno.longValue(),
																-1L,
																idProvincia,"-1",-1L,-1L);
					} else {
						actualizado = rodalClient.actualizaDoc(file, 
					                  historial.getIdAneHisRodal());					
						
					}
//				}
			}
		}
	}		

	@Override
	public GastoTutor createGastoTutor(Long idUsuario, Long idPerfil, GastoTutor gastoTutorIn) throws Exception  {
		
		Optional<GastoTutor> gastoTutorCreado = gastoTutorRepository.findById(gastoTutorIn.getId());
		
		boolean fechasSolapadas = isFechaGastoSolapadoTutor(gastoTutorIn);
		
		if(!fechasSolapadas) {
			
					if(!gastoTutorCreado.isPresent()) {
						GastoTutor gastoTutorSave = gastoTutorRepository.save(gastoTutorIn);
						
						GastoFlujo flujo = gastoFlujoRepository.findByEstadoOrigenIsNullAndIdPerfilAndTipoGastoId(idPerfil, tipoGastoRepository.findByAbrev("GAS_TUT").getId());
						
						GastoTutorHistorial historial = new GastoTutorHistorial();				
						historial.setFechaRegistro(new Date());
						historial.setGastoFlujo(flujo);
						historial.setGastoTutor(gastoTutorSave);
						historial.setIdUsuario(idUsuario);
						historial.setObservaciones("Gasto tutor generado");
						
						gastoTutorHistorialRepository.save(historial);
						
						return gastoTutorSave;
					}else {
						return gastoTutorRepository.save(gastoTutorIn);
					}			
		}else 
		{	 
			throw new Exception("Las fechas del gasto se solapan con otro gasto registrado.");			
		}
	}		
	
	private boolean isFechaGastoSolapadoTutor(GastoTutor gastoTutorIn){
		List<GastoTutor> listGastosTutor = gastoTutorRepository.findAllByTutorFctId(gastoTutorIn.getTutorFct().getId());
		for (GastoTutor gastoTutor : listGastosTutor) {			
			
		    /*if ((gastoTutorIn.getId() != gastoTutor.getId() || gastoTutorIn.getId().equals(-1L))  &&  
		    	!(gastoTutorIn.getFechaInicio().compareTo(gastoTutor.getFechaFin()) >= 0 
		    	  || gastoTutorIn.getFechaFin().compareTo(gastoTutor.getFechaInicio()) <= 0)) {
		    	return true;
		    } */
			
			if (gastoTutorIn.getId() != gastoTutor.getId() && gastoTutorRepository.tlf_intersecper(gastoTutorIn.getFechaInicio(),
																								   gastoTutorIn.getFechaFin(),
																								   gastoTutor.getFechaInicio(),
																								   gastoTutor.getFechaFin()) > 0)   
			{
				return true;
			}
		    
		    
		}
		return false;
	}
	
	@Override
	public GastoAlumnado createGastoAlumnado(Long idUsuario, Long idPerfil, GastoAlumnado gastoAlumnoIn) throws Exception  {
		
		Optional<GastoAlumnado> gastoAlumnoCreado = gastoAlumnadoRepository.findById(gastoAlumnoIn.getId());

//		boolean fechasSolapadas = isFechaGastoSolapadoAlumnado(gastoAlumnoIn);
		
//		if(!fechasSolapadas) {
			if(!gastoAlumnoCreado.isPresent()) {
				GastoAlumnado gastoAlumnoSave = gastoAlumnadoRepository.save(gastoAlumnoIn);
				
				GastoFlujo flujo = gastoFlujoRepository.findByEstadoOrigenIsNullAndIdPerfilAndTipoGastoId(idPerfil, tipoGastoRepository.findByAbrev("GAS_ALU").getId());
				

				GastoAlumnadoHistorial historial = new GastoAlumnadoHistorial();
				historial.setFechaRegistro(new Date());
				historial.setGastoFlujo(flujo);
				historial.setGastoAlumno(gastoAlumnoSave);
				historial.setIdUsuario(idUsuario);
				historial.setObservaciones("Gasto alumno generado");

				gastoAlumnadoHistorialRepository.save(historial);

				Long idFlujoValProf = 33L;
				GastoFlujo siguienteFlujo = gastoFlujoRepository.findAllById(idFlujoValProf);
				if( idPerfil == 2) {
					Date ahora = new Date();
					GastoAlumnadoHistorial hisValProf = new GastoAlumnadoHistorial();
					hisValProf.setFechaRegistro(new Date(ahora.getTime() + 1000));
					hisValProf.setGastoFlujo(siguienteFlujo);
					hisValProf.setGastoAlumno(gastoAlumnoSave);
					hisValProf.setIdUsuario(idUsuario);
					gastoAlumnadoHistorialRepository.save(hisValProf);
				}
				
				return gastoAlumnoSave;
			}else {
				return gastoAlumnadoRepository.save(gastoAlumnoIn);
			}
//		}else {
//			throw new Exception("Las fechas del gasto se solapan con otro gastro registrado.");
//		}
		
	}	
	
//	private boolean isFechaGastoSolapadoAlumnado(GastoAlumnado gastoAlumnoIn){
//		List<GastoAlumnado> listGastosAlumno = gastoAlumnadoRepository.findAllByIdMatricula(gastoAlumnoIn.getIdMatricula());
//		for (GastoAlumnado gastoAlumno : listGastosAlumno) {
//
//			if (gastoAlumno.getId() != gastoAlumnoIn.getId() && gastoTutorRepository.tlf_intersecper(gastoAlumnoIn.getFechaInicio(),
//																								     gastoAlumnoIn.getFechaFin(),
//																								     gastoAlumno.getFechaInicio(),
//																								     gastoAlumno.getFechaFin()) > 0)
//			{
//				return true;
//			}
//
//			/*if (gastoAlumnoIn.getId() != gastoAlumno.getId() &&
//		    	!(gastoAlumnoIn.getFechaInicio().compareTo(gastoAlumno.getFechaFin()) >= 0 || gastoAlumnoIn.getFechaFin().compareTo(gastoAlumno.getFechaInicio()) <= 0) ) {
//		    	return true;
//		    } */
//		}
//		return false;
//	}
	
	@Override
	public GastoAnexo createGastoAnexo(Long idUsuario, GastoAnexo gastoAnexoIn) throws Exception {
		return gastoAnexoRepository.save(gastoAnexoIn);
	}	
	
	@Override
	public List<GastoEstadoFlujoSiguiente> getSiguienteGastoFlujo(Long idPerfil, Long idGasto, String abrevTipoGasto) {
		Long idTipoGasto = tipoGastoRepository.findByAbrev(abrevTipoGasto).getId();
		
		List<GastoEstadoFlujoSiguienteProjection> estados = gastoFlujoRepository.findSiguienteGastoFlujo(idPerfil, idGasto, idTipoGasto);
		
		return estados.stream().map(entity -> modelMapper.map(entity, GastoEstadoFlujoSiguiente.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ListadoGastoTutores> getListadoGastoTutores(Integer annoPeriodo, 
														    Long idCentro, 
														    Long idTutor, 
														    Long idPeriodoGasto,
														    Long idPerfil,
														    Long idEstado) {


		List<ListadoGastoTutoresProjection> listadoGastoTutores = gastoTutorRepository.getListadoGastoTutores(annoPeriodo, 
																											  idCentro, 
																											  idTutor, 
																											  idPeriodoGasto,
																											  ID_PERFIL_GESTOR.equals(idPerfil)?0:1,
																											  idEstado,
																											  ID_PERFIL_PROFESORADO.equals(idPerfil)?0:1);

		return listadoGastoTutores.stream().map(entity -> modelMapper.map(entity, ListadoGastoTutores.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ListadoGastoTutores> getComboTutores(Integer annoPeriodo, 
													 Long idCentro,
													 Long idUsuario) {
		
		List<ListadoGastoTutoresProjection> listadoGastoTutores = gastoTutorRepository.getComboTutores(annoPeriodo, 
																									   idCentro,
																									   idUsuario
																									   );
		
		return listadoGastoTutores.stream().map(entity -> modelMapper.map(entity, ListadoGastoTutores.class)).collect(Collectors.toList());
	}
	
	
	public List<ListadoGastoTutores> getGastosListadoVI_bis(Integer annoPeriodo, 
															Long idCentro, Long idTutor, Long idPeriodoGasto) {
		
		List<ListadoGastoTutoresProjection> listadoGastoTutores = gastoTutorRepository.getGastosListadoVI_bis(idCentro, idTutor, idPeriodoGasto);
		
		return listadoGastoTutores.stream().map(entity -> modelMapper.map(entity, ListadoGastoTutores.class)).collect(Collectors.toList());
	}
	

	public List<ListadoGastoAlumnado> getGastosListadoVI(Integer annoPeriodo, 
														 Long idCentro, 
														 Long idPeriodoGasto,
														 Long idTutor,
														 Long idCurso,
														 Long idUnidad) {
		
		
		List<ListadoGastoAlumnadoProjection> listadoGastoAlumnado = gastoAlumnadoRepository.getGastosListadoVI(annoPeriodo, 
																											   idCentro, 
																											   idPeriodoGasto,
																											   //idTutor,
																											   idCurso,
																											   idUnidad);
		
		return listadoGastoAlumnado.stream().map(entity -> modelMapper.map(entity, ListadoGastoAlumnado.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ListadoGastoAlumnado> getListadoGastoAlumnado(Integer annoPeriodo, 
															  Long idCentro, 
															  Long idTutor, 
															  Long idPeriodoGasto,
															  Long idPerfil,
															  Long idCurso,
															  Long idUnidad,															  
															  Long idUsuarioComunica) {
		
		Long idMatricula = -1L;
		if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {
			
			idMatricula = gastoAlumnadoRepository.getMatriculaAlumnado(annoPeriodo, 
																	   idCentro,																   
																	   idUsuarioComunica);			
		}
		
		
		List<ListadoGastoAlumnadoProjection> listadoGastoAlumnado = null;
		
		if (ID_PERFIL_ALUMNADO.equals(idPerfil) || ID_PERFIL_PROFESORADO.equals(idPerfil)) {
		
		
		 listadoGastoAlumnado = gastoAlumnadoRepository.getListadoGastoAlumnado(annoPeriodo, 
																		        idCentro, 
																			    idTutor, 
																				idPeriodoGasto,
																				idPerfil,	
																			    idCurso,
																				idUnidad,
																				idMatricula,
																			    1,
																			    Objects.equals(ID_PERFIL_ALUMNADO, idPerfil) ? 0 : 1,																				
																				idUsuarioComunica);
		} else {
			
			 listadoGastoAlumnado = gastoAlumnadoRepository.getListadoGastoAlumnadoCentro(annoPeriodo, 
						                                                                  idCentro, 
																						  idTutor, 
																						  idPeriodoGasto,
																						  idPerfil,	
																						  idCurso,
																						  idUnidad,
																						  idMatricula,
																						  Objects.equals(ID_PERFIL_GESTOR, idPerfil) ? 0 : 1,		
																						  1);
			
			
			
			
		}
		
		return listadoGastoAlumnado.stream().map(entity -> modelMapper.map(entity, ListadoGastoAlumnado.class)).collect(Collectors.toList());
	}
	
	@Override
	public Alumno getDatosAlumnoGastoNuevo(Integer annoPeriodo, Long idCentro, Long idUsuarioComunica) {
		Long idMatricula = gastoAlumnadoRepository.getMatriculaAlumnado(annoPeriodo, idCentro, idUsuarioComunica);	
		return modelMapper.map(alumnadoRepository.findAlumnoByIdMatricula(idMatricula), Alumno.class);
	}
	
	@Override
	public List<ListadoGastoAnexo> getListadoGastoAnexo(Integer annoPeriodo, 
														Long idCentro, 
														String abrevTipoGasto, 
														Long idPeriodoGasto, 
														Long idTutor,
														Long IdCurso,
														Long idUnidad,
														Long idPerfil,
														Long idProvinciaDelegacion,
														Long idUsuario) {
		
		Long idTipoGasto = tipoGastoRepository.findByAbrev(abrevTipoGasto).getId();
		
		List<ListadoGastoAnexoProjection> listadoGastoAnexo = null;
		
		if (abrevTipoGasto.equals(ABREV_ANEXO_VIBIS))
		{			
			
			listadoGastoAnexo = gastoAnexoRepository.getListadoGastoAnexoVIbis(annoPeriodo, 
																			   idCentro, 
																			   idTipoGasto, 
																			   idPeriodoGasto,
																			   idPerfil,
																			   idUsuario);
			
		} else if(abrevTipoGasto.equals(ABREV_ANEXO_VI)) {
			
			listadoGastoAnexo = gastoAnexoRepository.getListadoGastoAnexoVI(annoPeriodo, 
																			idCentro, 
																			idTipoGasto, 
																			idPeriodoGasto, 
																			idTutor,
																			IdCurso,
																			idUnidad,
																			idPerfil,
																			idUsuario);
		}else {
			
			Long idProvincia = -1L;
			if (!idProvinciaDelegacion.equals(-1L)) {
				idProvincia = idProvinciaDelegacion; 
				
			} else {
				idProvincia = gastoAnexoRepository.getIdProvinciaByAnexo(idPerfil, idUsuario, idCentro); 
			}				

			listadoGastoAnexo = gastoAnexoRepository.getListadoGastoAnexoVIII(annoPeriodo, idProvincia, idTipoGasto, idPeriodoGasto,idPerfil);
		}		 

		
		return listadoGastoAnexo.stream().map(entity -> modelMapper.map(entity, ListadoGastoAnexo.class)).collect(Collectors.toList());
	}

	@Override
	public List<PeriodoGasto> getPeriodosGasto(Integer annoPeriodo, 
											   Long idCentro, 
											   Long idPerfil, 
											   Long idTutor,
											   Long idUsuarioComunica) {
		
		return periodoGastoRepository.findByAnnoPeriodo(annoPeriodo);	
		
	}
	
	@Override
	public List<HistoricoFlujoGastos> getHistoricoFlujoGastos(Long id, String abrevTipoGasto) {			
		Long idTipoGasto = tipoGastoRepository.findByAbrev(abrevTipoGasto).getId();
		
		List<HistoricoFlujoGastosProjection> listadohistorico = gastoTutorHistorialRepository.getHistoricoFlujoGastos(id, idTipoGasto);
		
		return listadohistorico.stream().map(entity -> modelMapper.map(entity, HistoricoFlujoGastos.class)).collect(Collectors.toList());
	}
	
	

	@Override
	public byte[] exportReportAnexoGastoTipo(Integer cAnno,
											 Long idCentro,
			                                 Long idPeriodo, 
											 Long idTipo,
											 Long idTutor,
											 Long idCurso,
											 Long idUnidad,
											 Long idProvincia,
											 Long idUsuario,
											 Long idPerfil) throws IOException, JRException {
		
		byte[] output = null; 
	    InputStream dbAsStream = null; 
	    Resource resource = null;
	     
	    if (ANEXO_VI_TUTORES == idTipo) {
	    	resource = resourceLoader.getResource("classpath:reports/anexo6Tutor.jrxml");
	    } else if(ANEXO_VI_ALUMNADO == idTipo) {
	    	resource = resourceLoader.getResource("classpath:reports/anexo6.jrxml");
	    }else {
	    	resource = resourceLoader.getResource("classpath:reports/anexo8.jrxml");
	    }
	  
        dbAsStream = resource.getInputStream();
        
        Map<String, Object> parameters = new HashMap<>();              
    	String tutor = "";    
    	String email = ""; 
    	String curso = "";
    	String fecha = "";
        
        JRBeanCollectionDataSource datasource = null;
        if (ANEXO_VI_TUTORES == idTipo) 
        {        	
        	datasource = getParamVIbis(cAnno, idCentro, idPeriodo, parameters, tutor, email, curso, fecha);

        } else if(ANEXO_VI_ALUMNADO == idTipo) { 
        	
        	datasource = getParamAnexoVI(cAnno, idCentro, idPeriodo, idTutor, idCurso, idUnidad, parameters);

        	
        }else {
        	
        	datasource = getParamAnexoVII(idCentro, idPeriodo, idProvincia, idUsuario, idPerfil, parameters);
        }
        
        parameters.put("ds", datasource);        
        
        JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);      	 
    	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
        
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.exportReport();
        output = outputStream.toByteArray();  
       
        return output;
	}

	private JRBeanCollectionDataSource getParamVIbis(Integer cAnno, Long idCentro, Long idPeriodo,
			Map<String, Object> parameters, String tutor, String email, String curso,
			String fecha) {
		DatosCabeceraAnexoVITutorProjection parametrostutor;
		List<ListadoGastoTutores> listadoGastosTutor;
		String centro;
		String localidad;
		String codigo;
		String periodo;
		String director;
		Double totalGastos = 0.0;
		
		JRBeanCollectionDataSource datasource;
		parametrostutor = gastoTutorRepository.getDatosCabeceraAnexoVITutores(idCentro, idPeriodo);  
		listadoGastosTutor = getGastosListadoVI_bis(cAnno,idCentro,-1L,idPeriodo);
		datasource = new JRBeanCollectionDataSource(listadoGastosTutor);
		for (ListadoGastoTutores gasto : listadoGastosTutor) {
			totalGastos += gasto.getTotal();
		}
		BigDecimal bd = BigDecimal.valueOf(totalGastos).setScale(2, RoundingMode.CEILING);
		totalGastos = bd.doubleValue();
				
		centro = parametrostutor.getCentro();
		localidad = parametrostutor.getLocalidad();
		codigo = parametrostutor.getCodigo();
		periodo = parametrostutor.getPeriodo();
		director = parametrostutor.getDirector();
		
		parameters.put("centro", centro);
		parameters.put("localidad", localidad);   
		parameters.put("codigo", codigo);
		parameters.put("periodo", periodo);
		parameters.put("director", director);		
		parameters.put("tutor", tutor);
		parameters.put("email", email);
		parameters.put("curso", curso);
		parameters.put("fecha", fecha);
		parameters.put("totalGastos", totalGastos);
		return datasource;
	}

	private JRBeanCollectionDataSource getParamAnexoVI(Integer cAnno, Long idCentro, Long idPeriodo, Long idTutor,
			Long idCurso, Long idUnidad, Map<String, Object> parameters) {
		DatosCabeceraAnexoVIAlumnadoProjection parametrosAlu;
		List<ListadoGastoAlumnado> listadoGastosAlumnado;
		String centro;
		String localidad;
		String codigo;
		String periodo;
		String director;
		String tutor;
		String email;
		String curso;
		String fecha;
		String horas;
		Double totalGastos = 0.0;
		
		JRBeanCollectionDataSource datasource;
		parametrosAlu = gastoAlumnadoRepository.getDatosCabeceraAnexoVIAlumnado(idCentro, idPeriodo,idTutor,idCurso,idUnidad); 
		listadoGastosAlumnado = getGastosListadoVI(cAnno,idCentro,idPeriodo,idTutor,idCurso,idUnidad);
		datasource = new JRBeanCollectionDataSource(listadoGastosAlumnado);
		for (ListadoGastoAlumnado gasto : listadoGastosAlumnado) {
			totalGastos += gasto.getTotal();
		}
		
		centro = parametrosAlu.getCentro();
		localidad = parametrosAlu.getLocalidad();
		codigo = parametrosAlu.getCodigo();
		periodo = parametrosAlu.getPeriodo();
		director = parametrosAlu.getDirector();
		tutor = parametrosAlu.getTutor(); 
		email = parametrosAlu.getMail(); 
		curso = parametrosAlu.getCurso();
		fecha = parametrosAlu.getFecha();
		horas = parametrosAlu.getHoras();
		
		parameters.put("centro", centro);
		parameters.put("localidad", localidad);   
		parameters.put("codigo", codigo);
		parameters.put("periodo", periodo);
		parameters.put("director", director);		
		parameters.put("tutor", tutor);
		parameters.put("email", email);
		parameters.put("curso", curso);
		parameters.put("fecha", fecha);
		parameters.put("totalGastos", totalGastos);
		return datasource;
	}

	private JRBeanCollectionDataSource getParamAnexoVII(Long idCentro, Long idPeriodo, Long idProvincia, Long idUsuario,
			Long idPerfil, Map<String, Object> parameters) {
		JRBeanCollectionDataSource datasource;
		CabeceraAnexoVIIIProjection cabeceraProjection = gastoAnexoRepository.getCabeceraListadoGastoAnexoVIII(idPeriodo, idUsuario, idPerfil, idCentro, idProvincia);       
		CabeceraAnexoVIII cabecera =  modelMapper.map(cabeceraProjection, CabeceraAnexoVIII.class);
		
		List<ListadoAnexoVIIIProjection> listadoProjection = gastoAnexoRepository.getListadoGastoAnexoVIII(idPeriodo, idProvincia);
		List<ListadoAnexoVIII> listado = listadoProjection.stream().map(entity -> modelMapper.map(entity, ListadoAnexoVIII.class)).collect(Collectors.toList());
		datasource = new JRBeanCollectionDataSource(listado);
		
		String liquidacion = cabecera.getLiquidacion();
		String provincia = cabecera.getProvincia();
		String numCentros = !listado.isEmpty()?listado.size()+"":"0";
		String totalTutores = cabecera.getTotalTutores() == null?"":(cabecera.getTotalTutores() % 1 == 0 ? cabecera.getTotalTutores().toString().substring(0, cabecera.getTotalTutores().toString().indexOf('.')) : cabecera.getTotalTutores().toString());
		String totalAlu = cabecera.getTotalAlumnado() == null?"":(cabecera.getTotalAlumnado() % 1 == 0 ? cabecera.getTotalAlumnado().toString().substring(0, cabecera.getTotalAlumnado().toString().indexOf('.')) : cabecera.getTotalAlumnado().toString());
        //String total = cabecera.getTotal().toString(); // Devolver total como número
		String total = convertNumberToLetter(cabecera.getTotal()); //Devolver total  con letras
		String nombre = cabecera.getNombre();
		String firma = "En " + provincia + " " + conveniosFctRepository.getFechaActual();
		String centroIni = "";
		String centroFin = "";
		
		if(!listado.isEmpty()) {
		centroIni =listado.get(0).getDenominacion();
		centroFin =listado.get(listado.size()-1).getDenominacion();
		}
		
		parameters.put("liquidacion", liquidacion);
		parameters.put("provincia", provincia);
		parameters.put("numCentros", numCentros);
		parameters.put("totalTutores", totalTutores);
		parameters.put("totalAlumnado", totalAlu);
		parameters.put("total", total);
		parameters.put("nombreP",nombre);
		parameters.put("firmaP",firma);
		parameters.put("centroIni",centroIni);
		parameters.put("centroFin", centroFin);
		return datasource;
	}

	@Override
	public GastoTutor getGastoTutor(Long idGastoTutor) {
		Optional<GastoTutor> gasto = gastoTutorRepository.findById(idGastoTutor);
		return gasto.orElse(null);
	}
	
	@Override
	public GastoAlumnado getGastoAlumnado(Long idGastoAlumnado) {
		Optional<GastoAlumnado> gasto = gastoAlumnadoRepository.findById(idGastoAlumnado);
		return gasto.orElse(null);
	}
	
	@Override
	public PeriodoGasto getPeriodoGastoById(Long idGastoPeriodo) {
		Optional<PeriodoGasto> periodo = periodoGastoRepository.findById(idGastoPeriodo);
		
		return periodo.orElse(null);
	}

	@Override
	public GastoAnexoHistorial getAnexoHistoriaById(Long idEntidad) {				
		Optional<GastoAnexoHistorial> gasto = gastoAnexoHistorialRepository.findById(idEntidad);
		return gasto.orElse(null);				
	}

	@Override
	public void updateAnexoHistorial(GastoAnexoHistorial anexohistorial) {
		gastoAnexoHistorialRepository.save(anexohistorial);
		
	}

	@Override
	public List<ElementoSelect> getComboCursosGastos(Integer annoPeriodo, 
													 Long idCentro, 
													 String abrevTipoGasto,
													 Long idPeriodoGasto, 
													 Long idTutor,
													 Long idUsuarioComunica,
													 Long idPerfil) {		
		
		Long idMatricula = -1L;
		
		if (ID_PERFIL_ALUMNADO.equals(idPerfil)) { 
			idMatricula = gastoAlumnadoRepository.getMatriculaAlumnado(annoPeriodo, 
																	   idCentro,																   
																	   idUsuarioComunica);
		}
		
		List<ElementoSelectProjection> cursos = gastoAlumnadoRepository.getComboCursosGastos(annoPeriodo, 
																							 idCentro,																	
																							 abrevTipoGasto,
																							 idPeriodoGasto,
																							 idTutor,
																							 idMatricula,
																							 idUsuarioComunica);

        return cursos.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ElementoSelect> getComboUnidadGastos(Integer annoPeriodo, 
													 Long idCentro, 
												     String abrevTipoGasto,
												     Long idPeriodoGasto, 
													 Long idTutor,
													 Long idCurso,
													 Long idUsuarioComunica,
													 Long idPerfil) 
	{
		
		Long idMatricula = -1L;
		
		if (ID_PERFIL_ALUMNADO.equals(idPerfil)) { 
			idMatricula = gastoAlumnadoRepository.getMatriculaAlumnado(annoPeriodo, 
																	   idCentro,																   
																	   idUsuarioComunica);
		}
		
		List<ElementoSelectProjection> modalidad = gastoAlumnadoRepository.getComboUnidadGastos(annoPeriodo, 
																							    idCentro,	 
																								abrevTipoGasto,
																								idPeriodoGasto,
																								idTutor,
																								idCurso,
																								idMatricula,
																								idUsuarioComunica);

		return modalidad.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ElementoSelect> getComboTutoresGastosAlumno(Integer annoPeriodo, 
			                                                Long idCentro, 
			                                                String abrevTipoGasto,
			                                                Long idPeriodoGasto,
			                                                Long idUsuarioComunica,
			                                                Long idPerfil) {
		
		Long idMatricula = -1L;
		
		if (ID_PERFIL_ALUMNADO.equals(idPerfil)) { 
			idMatricula = gastoAlumnadoRepository.getMatriculaAlumnado(annoPeriodo, 
																	   idCentro,																   
																	   idUsuarioComunica);
		}
		
		List<ElementoSelectProjection> tutores = gastoAlumnadoRepository.getComboTutoresGastosAlumno(annoPeriodo, 
			     																				     idCentro,																							   
																							         abrevTipoGasto,
																								     idPeriodoGasto,
																								     idMatricula);

        return tutores.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
		
	}
	
	
	@Override
	public void updateFirmaAnexo(Long idHistorial, 
								 Long xUsuario, 
								 Integer posFirma,
								 Long idPerfil) {
		
		    Optional<GastoAnexoHistorial> gasto = gastoAnexoHistorialRepository.findById(idHistorial);
		    
			if(gasto.isPresent()) 
			{
				
				GastoAnexoHistorial gastoEncontrado= gasto.get();
				
				if(posFirma==1) 
				{				
					gastoEncontrado.setFhRegistro1(new Date());
					gastoEncontrado.setIdUsuario1_firma(xUsuario);
					
				} else {
					gastoEncontrado.setFhRegistro2(new Date());
					gastoEncontrado.setIdUsuario2_firma(xUsuario);					
				}
				
				gastoAnexoHistorialRepository.save(gasto.get());
				
				GastoAnexoHistorial gastoNew = new GastoAnexoHistorial();
				
				List<GastoEstadoFlujoSiguiente> lista = this.getSiguienteGastoFlujo(idPerfil,
																			        gasto.get().getGastoAnexo().getId(),
																			        gasto.get().getGastoAnexo().getTipoGasto().getAbrev()); 
				
				
				Optional<GastoFlujo> flujo = gastoFlujoRepository.findById(lista.get(0).getId());
				
				
				gastoNew.setFechaRegistro(new Date());
				gastoNew.setGastoAnexo(gasto.get().getGastoAnexo());
				gastoNew.setGastoFlujo(flujo.get());
				gastoNew.setIdUsuario(xUsuario);
				gastoAnexoHistorialRepository.save(gastoNew);	
			}		
	}

	@Override
	public List<TicketAlumnado> getTicketsAlumno(Long idGastoAlumnado) {
		return ticketAlumnadoRepository.findAllByGastoAlumnadoId(idGastoAlumnado);
	}

	@Override
	public List<TicketTutor> getTicketsTutor(Long idGastoTutor) {
		return ticketTutorRepository.findAllByGastoTutorId(idGastoTutor);
	}

	@Override
	public List<TicketTutor> createTicketTutor(Long idGasto, 
			                                   Long cAnno,
			                                   Long idCentro,
											   List<MultipartFile> files, 
											   Long idUsuario) throws RodalExceptionService, InsertarDocFault, IOException {
		List<TicketTutor> listTicketTutor = new ArrayList<>();
		Optional<GastoTutor> gasto = gastoTutorRepository.findById(idGasto);
		
		for (MultipartFile file : files) {
			TicketTutor ticketSave = new TicketTutor();
			ticketSave.setFechaRegistro(new Date());
			ticketSave.setIdUsuario(idUsuario);
			ticketSave.setGastoTutor(gasto.orElse(new GastoTutor()));	
			
			ticketTutorRepository.save(ticketSave);
			
			rodalClient.insertaDoc(file, "MFCT", "TICKET_TUT_", ticketSave.getId(), cAnno, idCentro, -1L,"-1",-1L,-1L);
			
			listTicketTutor.add(ticketSave);
		}
		
		return listTicketTutor; 
	}

	public ImpuestoTipoServicio getImpuestoTipoServicio(Integer cAnno, String tipServicio) {
		return impuestoTipoRepository.findByTipoServicioCentroAnnoAnnoAndTipoServicioCentroAnnoTipoServicioCentroCodigo(cAnno, tipServicio);
	}
	
	@Override
	public void deleteTicketTutor(List<TicketTutorDto> listTicketsTutorDto) throws RodalExceptionService {		
		for (TicketTutorDto ticketDelt : listTicketsTutorDto) {
			rodalClient.borrarDocumento(ticketDelt.getIdTicketTutRodal(), "MFCT", "TICKET_TUT_");
			ticketTutorRepository.deleteById(ticketDelt.getId());
		}
	}
	
	@Override
	public List<TicketAlumnado> createTicketAlumno(Long idGasto, 
												   Long cAnno,
									               Long idCentro,
												   List<MultipartFile> files, 
												   Long idUsuario) throws RodalExceptionService, InsertarDocFault, IOException {
		List<TicketAlumnado> listTicketAlumno = new ArrayList<>();
		Optional<GastoAlumnado> gasto = gastoAlumnadoRepository.findById(idGasto);
		
		for (MultipartFile file : files) {
			TicketAlumnado ticketSave = new TicketAlumnado();
			ticketSave.setFechaRegistro(new Date());
			ticketSave.setIdUsuario(idUsuario);
			ticketSave.setGastoAlumnado(gasto.orElse(new GastoAlumnado()));	
			
			ticketAlumnadoRepository.save(ticketSave);
			
			rodalClient.insertaDoc(file, "MFCT", "TICKET_ALU_", ticketSave.getId(), cAnno, idCentro, -1L,"-1",-1L,-1L);
			
			listTicketAlumno.add(ticketSave);
		}
		
		return listTicketAlumno;
	}
	
	@Override
	public void deleteTicketAlumno(List<TicketAlumnadoDto> listTicketsAlumnoDto) throws RodalExceptionService {
		for (TicketAlumnadoDto ticketDelt : listTicketsAlumnoDto) {
			rodalClient.borrarDocumento(ticketDelt.getIdTicketAluRodal(), "MFCT", "TICKET_ALU_");
			ticketAlumnadoRepository.deleteById(ticketDelt.getId());
		}
	}
	
	@Override
	public void updateTicketTutor(TicketTutor ticketUpdate) {
		ticketTutorRepository.save(ticketUpdate);
	}
	
	@Override
	public void updateTicketAlumno(TicketAlumnado ticketUpdate) {
		ticketAlumnadoRepository.save(ticketUpdate);
	}
	
	@Override
	public TicketAlumnado getTicketAlumno(Long idTicketTutor) {
		return ticketAlumnadoRepository.findById(idTicketTutor).orElse(null);
	}

	public List<TicketAlumnado> getTicketAlumnoGatos(Long idGasto) {
		
		return ticketAlumnadoRepository.findAllByGastoAlumnadoId(idGasto);
	}
	
	@Override
	public TicketTutor getTicketTutor(Long idTicketAlumno) {
		return ticketTutorRepository.findById(idTicketAlumno).orElse(null);
	}

	@Override
	public TutorFctDual getNombreTutorById(Long idTutorFct) {
		Optional<TutorFctDual> tutor =  tutoresFctDualRepository.findById(idTutorFct);
		return tutor.orElse(new TutorFctDual());
	}

	@Override
	public List<ElementoSelect> getEstadosGastosDirector() {
		
		List<ElementoSelectProjection> estados = gastoAlumnadoRepository.getEstadosGastosDirector();

        return estados.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	
	}

	@Override
	public GastoAnexo getGastoAnexoById(Long idAnexo) {
		
		Optional<GastoAnexo> gasto =  gastoAnexoRepository.findById(idAnexo);
		
		return gasto.orElse(new GastoAnexo());		
	
	}

	@Override
	@Transactional
	public void deleteGastoTutor(Long idGastoTutor) throws RodalExceptionService {

		GastoTutor gastosTutor = gastoTutorRepository.findById(idGastoTutor).get();
		gastoTutorHistorialRepository.deleteByGastoTutor(gastosTutor);
		List<TicketTutor> tickets = ticketTutorRepository.findAllByGastoTutorId(idGastoTutor);
		List<TicketTutorDto> ticketsDto = tickets.stream().map(entity -> modelMapper.map(entity, TicketTutorDto.class)).collect(Collectors.toList()); 
		deleteTicketTutor(ticketsDto);
		gastoTutorRepository.deleteById(idGastoTutor);
	}

	@Override
	@Transactional
	public void deleteGastoAlumno(Long idGastoAlumno) {
		
		List<TicketAlumnado> tickets = getTicketAlumnoGatos(idGastoAlumno);
		for (TicketAlumnado tickect : tickets) {
			ticketAlumnadoRepository.delete(tickect);
		}
		
		GastoAlumnado gastoAlumnado = gastoAlumnadoRepository.findById(idGastoAlumno).get();
		gastoAlumnadoHistorialRepository.deleteByGastoAlumno(gastoAlumnado);
		gastoAlumnadoRepository.deleteById(idGastoAlumno);
	}

	public String convertNumberToLetter(double numero) {
		String[] UNIDADES = {"", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve" };
		String[] DECENAS = {"", "diez", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta","noventa" };
		String[] ESPECIALES = {"once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho","diecinueve" };
		String[] VEINTENAS = {"veintiuno", "veintidós", "veintitrés", "veinticuatro", "veinticinco", "veintiséis","veintisiete", "veintiocho", "veintinueve" };

		int parteEntera = (int) numero;
		int parteDecimal = (int) Math.round((numero - parteEntera) * 100);

		String resultado = convertInteger(parteEntera, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);

		if (parteDecimal > 0) {
			resultado += " con " + convertInteger(parteDecimal, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		}

		return capitalizeFirstLetter(resultado);
	}

	private String convertInteger(int numero, String[] UNIDADES, String[] DECENAS, String[] ESPECIALES,
			String[] VEINTENAS) {
		if (numero == 0) {
			return "";
		} else if (numero >= 1 && numero <= 9) {
			return UNIDADES[numero];
		} else if (numero >= 11 && numero <= 19) {
			return ESPECIALES[numero - 11];
		} else if (numero == 10) {
			return "diez";
		} else if (numero == 20) {
			return "veinte";
		} else if (numero >= 21 && numero <= 29) {
			return VEINTENAS[numero - 21];
		} else if (numero >= 30 && numero <= 99) {			
			return getMaxDecenas(numero, UNIDADES, DECENAS);			
		} else if (numero >= 100 && numero <= 999) {			
			return getMaxCentenas(numero, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else {
			return getMaxNumber(numero, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);			
		}		
	}

	private String getMaxDecenas(int numero, String[] UNIDADES, String[] DECENAS) {
		int unidad = numero % 10;
		int decena = numero / 10;
		if (unidad == 0) {
			return DECENAS[decena];
		} else {
			return DECENAS[decena] + " y " + UNIDADES[unidad];
		}
	}

	private String getMaxCentenas(int numero, String[] UNIDADES, String[] DECENAS, String[] ESPECIALES,
			String[] VEINTENAS) {
		int centena = numero / 100;
		int resto = numero % 100;
		if (centena == 1 && resto == 0) {
			return "cien";
		} else if (centena == 1) {
			return "ciento " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else if (centena == 2) {
			return "doscientos " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else if (centena == 3) {
			return "trescientos " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else if (centena == 4) {
			return "cuatrocientos " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else if (centena == 5) {
			return "quinientos " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else if (centena == 6) {
			return "seiscientos " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else if (centena == 7) {
			return "setecientos " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else if (centena == 8) {
			return "ochocientos " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else if (centena == 9) {
			return "novecientos " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else {
			String centenaStr = UNIDADES[centena] + "cientos";
			if (resto == 0) {
				return centenaStr;
			} else {
				return centenaStr + " " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
			}
		}
	}	
	
	private String getMaxNumber(int numero, String[] UNIDADES, String[] DECENAS, String[] ESPECIALES,
			String[] VEINTENAS) {
		if (numero >= 1000 && numero <= 999999) {			
			return getMaxMillares(numero, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);			
		} else if (numero >= 1000000 && numero <= 999999999) {
			return getMaxMillones(numero, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		} else {
			// si el número es mayor de 999999999
			return "Número fuera de rango";
		}
	}

	private String getMaxMillones(int numero, String[] UNIDADES, String[] DECENAS, String[] ESPECIALES,
			String[] VEINTENAS) {
		int millones = numero / 1000000;
		int resto = numero % 1000000;
		String millonesStr = convertInteger(millones, UNIDADES, DECENAS, ESPECIALES, VEINTENAS) + " millón";
		if (millones != 1) {
			millonesStr += "es";
		}
		if (resto == 0) {
			return millonesStr;
		} else {
			return millonesStr + " " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		}
	}

	private String getMaxMillares(int numero, String[] UNIDADES, String[] DECENAS, String[] ESPECIALES,
			String[] VEINTENAS) {
		int millar = numero / 1000;
		int resto = numero % 1000;
		String millarStr;
		if (millar == 1) {
			millarStr = "mil";
		} else {
			millarStr = convertInteger(millar, UNIDADES, DECENAS, ESPECIALES, VEINTENAS) + " mil";
		}
		if (resto == 0) {
			return millarStr;
		} else {
			return millarStr + " " + convertInteger(resto, UNIDADES, DECENAS, ESPECIALES, VEINTENAS);
		}
	}

	private String capitalizeFirstLetter(String str) {
		if (str != null && !"".equals(str)) {
			str=str.substring(0, 1).toUpperCase() + str.substring(1);
		} else {
			str = "";
		}
		return str;
	}

	@Override
	public Integer getNoModificableAnexoVI(Long idCentro, Long idPeriodo, Long idTutor) {
		return gastoAlumnadoRepository.getNoModificableAnexoVI(idCentro,idPeriodo,idTutor);
	}

	@Override
	public Integer getNoModificableAnexoVIbis(Long idCentro, Long idPeriodo) {
		return gastoAlumnadoRepository.getNoModificableAnexoVIbis(idCentro,idPeriodo);
	}
	@Override
	public List<ElementoSelect> getComboGastosAlumnos(Integer anno, Long idCentro, Long idTutor, Long idCurso, Long idUnidad, Long idUsuario, Long idPeriodo) {

		List<ElementoSelectProjection> listadoAlumnadoSinGasto = gastoAlumnadoRepository.getElementsGastosAlumnos(anno, idCentro, idTutor, idCurso, idUnidad , idUsuario, idPeriodo);

		return listadoAlumnadoSinGasto.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());

	}

	@Override
	public String getEstadoAnexoGasto(Long idCentro, Long idPeriodoGasto, Long idTipoGasto, Long idTutorFct, Long idUnidad, Long idCurso) {
		String estadoActual;

		if(idTipoGasto == 4){
			estadoActual = gastoAnexoRepository.getEstadoAnexoGastoAlumnos(idCentro, idPeriodoGasto, idTipoGasto, idTutorFct, idUnidad, idCurso);
		} else {
			estadoActual = gastoAnexoRepository.getEstadoAnexoGastoTutores(idCentro, idPeriodoGasto, idTipoGasto);
		}
		return estadoActual;
	}

}
