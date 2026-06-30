package es.jccm.edu.shared.application.services.rodal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.ParsemAluplanRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.anexosPFERepository.AnexosFPERepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.historialPFERepository.HistorialPFERepository;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.EvalProyAnexo;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.ParsemAluplan;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.AnexosProgPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.HistorialProgramaPFE;
import es.jccm.edu.proyectosfct.application.ports.in.evaluaciones.IEvaluacionesService;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.ActualizarDocFault;
import com.jccm.cstic.clientesws.clienterodal.BorrarDocFault;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import com.jccm.cstic.clientesws.clienterodal.RecuperarDocFault;
import com.jccm.cstic.clientesws.clienterodal.Rodal2;
import es.jccm.cstic.dm.rodal.documento.DocJCCM;
import es.jccm.cstic.dm.rodal.documento.ParametrosActualizarDoc;
import es.jccm.cstic.dm.rodal.documento.ParametrosBorrarDoc;
import es.jccm.cstic.dm.rodal.documento.ParametrosInsertarDoc;
import es.jccm.cstic.dm.rodal.documento.ParametrosRecuperarDoc;
import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.cstic.dm.rodal.documento.RespuestaRecuperarDoc;
import es.jccm.cstic.dm.rodal.documento.ResultadoOperacion;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.ParsemAluProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.ParsemAluProyRepository;
import es.jccm.edu.proyectosfct.application.configuration.rodal.RodalConfig;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProy;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamiento;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexosHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAnexoHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketTutor;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAlumno;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.SecuenciacionProyectos;
import es.jccm.edu.proyectosfct.application.ports.in.alumnoprograma.IAlumnoProgramaService;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IConveniosFctService;
import es.jccm.edu.proyectosfct.application.ports.in.conveniosprogramas.IConveniosProgramasService;
import es.jccm.edu.proyectosfct.application.ports.in.desplazamiento.IDesplazamientoService;
import es.jccm.edu.proyectosfct.application.ports.in.gastos.IGastosService;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IProyectosService;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.ISecuenciacionProyectosService;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.ICentroService;
import es.jccm.edu.shared.application.domain.rodal.CustomMultipartFile;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.configuration.rodal.Rodal2Inicialize;

@Service
public class RodalClientService implements IRodalClient  {

    private static final Logger LOGGER = LogManager.getLogger(RodalClientService.class);
    private static final String ERROR_BORRADO_RODAL = "ERROR Borrando de Rodal";
    private static final String ERROR_RECUPERADO_RODAL = "ERROR Borrando de Rodal";
    private SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss"); 
    private static final String ANE_VIB = "ANE_VIB_";
    private static final String ANE_VII = "ANE_VII_";
    private static final String PAR_MENP = "PAR_MENP_";
    private static final String PAR_MENY = "PAR_MENY_";
    private static final String PAR_SEML = "PAR_SEMPL_";
	private static final String EVAL_PROY_ANEXO = "EVAL_PROY_ANEXO";
    private static final String ANE_FPE = "ANE_FPE_";
    private static final String ANEV = "ANEV";
    
	@Autowired
    private Rodal2Inicialize rodal2Inicialize;
	
    @Autowired
    private RodalConfig rodalConfig;
    
    @Autowired
    private IRodalClient rodalClient;
    
	@Autowired
	private IConveniosFctService conveniosFctService;
	
	@Autowired
	private ISecuenciacionProyectosService secProyectosService;
	
	@Autowired
	private ICentroService centroService;

	
	@Autowired
	IAlumnoProgramaService alumnoProgramaService;
	
	@Autowired
	IProyectosService proyectosService;
	
	@Autowired
	IGastosService gastosService;
	
	@Autowired
	IDesplazamientoService desplazamientoService;
	
	@Autowired
	IConveniosProgramasService conveniosProgramasService;
	
	@Autowired
	private ParsemAluProgRepository parsemAluProgRepository;
	
	@Autowired
	private ParsemAluProyRepository parsemAluProyRepository;

	@Autowired
	private ParsemAluplanRepository parsemAluplanRepository;

	@Autowired
	private IEvaluacionesService evaluacionesService;
    
    @Autowired
    private AnexosFPERepository anexosFPERepository;
    
	@Autowired
	private HistorialPFERepository  historialPFERepository;
	
    /**
     * Método que retorna un documento de Rodal
     * 
     * @param idRodal
     * @return
     * @throws RodalExceptionService
     */
    public ByteArrayOutputStream recuperaDoc(String idRodal, String modulo) throws RodalExceptionService {
        try {
            ParametrosRecuperarDoc parametrosRecuperarDoc = new ParametrosRecuperarDoc();
            parametrosRecuperarDoc.setIdDoc(idRodal);
            
            RespuestaRecuperarDoc respuestaRec;
            

            	Rodal2 rodal2 = rodal2Inicialize.getRodal2FCT(rodalConfig);            
                respuestaRec = rodal2.recuperarDoc(parametrosRecuperarDoc);


            if(respuestaRec.getContenido() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // El cliente lo decodifica automáticamente
                baos.write(respuestaRec.getContenido());
                return baos;
            }else {
            	String message = MessageFormat.format("ERROR Recuperando de Rodal. El documento {0} no retorna contenido.", idRodal);
                LOGGER.fatal(message);
                throw new RodalExceptionService(message);
            }

        }catch (RecuperarDocFault|IOException e) {
            LOGGER.fatal(ERROR_RECUPERADO_RODAL,e);
            throw new RodalExceptionService( ERROR_BORRADO_RODAL +": " + e.getMessage());
        }

    }   
    
//    private static boolean exceededTimeOut(Date ini, Date fin, long timeout) {
//        
//        return calcTimeOut(ini, fin) >= timeout;
//
//    }

//    private static long calcTimeOut(Date ini, Date fin) {
//        return (fin.getTime() - ini.getTime());
//    }


	@Override
	public Boolean borrarDocumento(String idRodalDel, String moduloDel, String entidadDel) throws RodalExceptionService {
		try {
		        ParametrosBorrarDoc parametosDel = new ParametrosBorrarDoc();
		        parametosDel.setIdDoc(idRodalDel);
		        ResultadoOperacion resultadoDel = new ResultadoOperacion();
		        
		        if(moduloDel.equals("MFCT")) {
		        	if (entidadDel.equals("CF_") || entidadDel.equals("CP_") || entidadDel.equals("SP_") || entidadDel.equals(EVAL_PROY_ANEXO)) {
		        	  Rodal2 rodal2 = rodal2Inicialize.getRodal2FCT(rodalConfig);            
		        	  resultadoDel = rodal2.borrarDoc(parametosDel);		        		
		        	}
		        	if (entidadDel.equals("CF_")) {            	
		            	ConveniosFct convenioUpdateDel =  conveniosFctService.getConvenioByIdConfirRodal(idRodalDel); 
		            	convenioUpdateDel.setIdConfirRodal(null);
		            	convenioUpdateDel.setTxConfirFichero(null);
		            	convenioUpdateDel.setFechaFirma(null);
		            	convenioUpdateDel.setFechaProrroga(null);
		                conveniosFctService.updateConvenio(convenioUpdateDel);    
		                return resultadoDel.getResultado() == 0;
		            } else if (entidadDel.equals("CP_")) {
		              	ConveniosFct convenioUpdateDel =  conveniosFctService.getConvenioByIdConproRodal(idRodalDel); 
		              	convenioUpdateDel.setIdConproRodal(null);
		              	convenioUpdateDel.setTxConproFichero(null);
		              	convenioUpdateDel.setFechaFirmaProrroga(null);
		                conveniosFctService.updateConvenio(convenioUpdateDel);  
		                return resultadoDel.getResultado() == 0;		                
		                
		            } else if (entidadDel.equals("EVA_PROG_")) {		            	
		        		AlumnoPrograma alumnoProgramaDel = alumnoProgramaService.getAlumnoProgramaByIdRodal(idRodalDel);
		        		alumnoProgramaDel.setIdEvaRodal(null);
		        		alumnoProgramaDel.setTxEvafirFichero(null);
			    		alumnoProgramaService.updateAlumnoConvenioPrograma(alumnoProgramaDel);
		            	
		            } else if (entidadDel.equals("EVA_PROY_")) {
		            	ConveniosProyectoAlumno proyectoAlumnoDel =  proyectosService.getConvenioProyectoAlumnoByIdRodal(idRodalDel);
		            	proyectoAlumnoDel.setIdEvaRodal(null);
		            	proyectoAlumnoDel.setTxEvafirFichero(null);
		                proyectosService.updateAlumnoConvenioProyecto(proyectoAlumnoDel);
		            	
		            } else if (entidadDel.equals("SP_")) {
		              	SecuenciacionProyectos secProyUpdateDel =  secProyectosService.getSecuenciacionByIdSecficRodal(idRodalDel); 
		              	secProyUpdateDel.setIdSecficRodal(null);
		              	secProyUpdateDel.setTxSecficFichero(null);
		                
		              	secProyectosService.updateSecuenciacionProyecto(secProyUpdateDel);  
		                return resultadoDel.getResultado() == 0;
		            	
		            } else if (entidadDel.equals("AUTDES_")) {
		              	AutorizacionDesplazamiento autDesDel =  desplazamientoService.getAutorizacionDesplazamientoByIdRodal(idRodalDel); 
		              	autDesDel.setIdAutTutRodal(null);
		              	autDesDel.setNombreFichero(null);		                
		              	desplazamientoService.updateAutorizacionDesplazamiento(autDesDel);
		              	return resultadoDel.getResultado() == 0;		            	
		            }
		        	
		        	return false;          	
	            	
	            }
		      	
	        	return resultadoDel.getResultado() == 0;
		
		        
		        
		}catch (BorrarDocFault e) {
        	String message = MessageFormat.format(ERROR_BORRADO_RODAL + " {0}", idRodalDel);
        	
        	LOGGER.fatal(ERROR_RECUPERADO_RODAL, message);
        	
            throw new RodalExceptionService(ERROR_BORRADO_RODAL+": " + message);
        }
	
	}

	@Override
	public Boolean borrarDocumento(String idRodal) throws RodalExceptionService {
		try {
		        ParametrosBorrarDoc parametos = new ParametrosBorrarDoc();
		        parametos.setIdDoc(idRodal);
		        ResultadoOperacion resultado = new ResultadoOperacion();
		        
		        Rodal2 rodal2 = rodal2Inicialize.getRodal2FCT(rodalConfig);            
	            resultado = rodal2.borrarDoc(parametos);		
		        
	            return resultado.getResultado() == 0;       
		        
		}catch (BorrarDocFault e) {
        	String message = MessageFormat.format(ERROR_BORRADO_RODAL + " {0}", idRodal);
        	
        	LOGGER.fatal(ERROR_RECUPERADO_RODAL, message);
        	
            throw new RodalExceptionService(ERROR_BORRADO_RODAL+": " + message);
        }
	
	}
	
	/*@Override
	public RespuestaInsertarDoc insertaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad, Long cAnno, Long idCentro, Long idProvincia, String cPerfil, Long usuarioActual, Long usuarioActualComunica)
										   throws RodalExceptionService, InsertarDocFault, IOException {		
		
		RespuestaInsertarDoc respuesta = null;
		ParametrosInsertarDoc paramInsertaDoc = null;
    	if (modulo.equals("MFCT")) {
    		
    		
    		Long idCodigo = idCentro != -1L?centroService.getCentroById(idCentro).getCodigoCentro():idProvincia;    		
    		   		
    		String nombreFichero =  file.getOriginalFilename();   
    		
            MultipartFile renamedFile = renameMultipartFile(file, entidad, nombreFichero);   		 		
    		
	        paramInsertaDoc = getParametrosDocNew(renamedFile,entidad,idEntidad,cAnno,modulo,
											      rodalConfig.getUnidadFuncional(),
											      rodalConfig.getSistema(),
											      rodalConfig.getExpediente(),
											      idCodigo);
			
		    respuesta = insertUpdate(paramInsertaDoc,entidad,idEntidad);
		    	
	    	if ((entidad.equals("CF_")  || entidad.equals("CP_")) && respuesta.getIdDoc() != null ) {                	
            	ConveniosFct convenioUpdate = conveniosFctService.getConvenioById(idEntidad);
            	
            	if (entidad.equals("CF_")) { 
                	convenioUpdate.setIdConfirRodal(respuesta.getIdDoc());
                	convenioUpdate.setTxConfirFichero(nombreFichero);            	
                	
            	} else {
            		
            		//Date fechaFinVigencia = convenioUpdate.getFechaFinVigencia();
            		Date fechaFinVigencia = new Date();
            		Calendar calendar=Calendar.getInstance();
                	calendar.setTime(fechaFinVigencia);
                	calendar.add(Calendar.YEAR, 4);
                	Date fechaProrroga = calendar.getTime();
                	convenioUpdate.setFechaProrroga(fechaProrroga);
            		
            		convenioUpdate.setIdConproRodal(respuesta.getIdDoc());
            		convenioUpdate.setTxConproFichero(nombreFichero);                		
            	}
            	
            	conveniosFctService.updateConvenio(convenioUpdate);
            	
	    	}  else if(entidad.equals("EVA_PROG_") && respuesta.getIdDoc() != null) {
	    		AlumnoPrograma alumnoPrograma = alumnoProgramaService.getAlumnoProgramaById(idEntidad);
	    		alumnoPrograma.setIdEvaRodal(respuesta.getIdDoc());
	    		alumnoPrograma.setTxEvafirFichero(nombreFichero);
	    		alumnoPrograma.setFechaFirma(new Date());
	    		alumnoProgramaService.updateAlumnoConvenioPrograma(alumnoPrograma);
	    		
	    	}  else if(entidad.equals("EVA_PROY_") && respuesta.getIdDoc() != null) {	
               ConveniosProyectoAlumno proyectoAlumno =  proyectosService.getConvenioProyectoAlumno(idEntidad);
               proyectoAlumno.setIdEvaRodal(respuesta.getIdDoc());
               proyectoAlumno.setTxEvafirFichero(nombreFichero);
               proyectoAlumno.setFechaFirma(new Date());
               proyectosService.updateAlumnoConvenioProyecto(proyectoAlumno);
//
//
//	    	}  else if(entidad.equals("SP_") && respuesta.getIdDoc() != null) {
//        		SecuenciacionProyectos secProyectosUpdate = secProyectosService.getSecuenciacionById(idEntidad);
//        		secProyectosUpdate.setIdSecficRodal(respuesta.getIdDoc());
//        		secProyectosUpdate.setTxSecficFichero(nombreFichero);
//
//        		secProyectosService.updateSecuenciacionProyecto(secProyectosUpdate);
            
	    	}  else if(entidad.equals("ANE_VIB_") || entidad.equals("ANE_VI_") || entidad.equals("ANEVIII_") && respuesta.getIdDoc() != null) {
	    		GastoAnexoHistorial  anexohistorial = gastosService.getAnexoHistoriaById(idEntidad); 
	    		anexohistorial.setIdAneHisRodal(respuesta.getIdDoc());
	    		anexohistorial.setNombreFichero(nombreFichero);	    		
	    		gastosService.updateAnexoHistorial(anexohistorial);
	    	}  else if(entidad.equals("TICKET_TUT_")) {
	    		TicketTutor ticketUpdate = gastosService.getTicketTutor(idEntidad);
	    		ticketUpdate.setIdTicketTutRodal(respuesta.getIdDoc());
	    		ticketUpdate.setNombreFichero(nombreFichero);
	    		gastosService.updateTicketTutor(ticketUpdate);
	    	} else if(entidad.equals("TICKET_ALU_")){
	    		TicketAlumnado ticketUpdate = gastosService.getTicketAlumno(idEntidad);
	    		ticketUpdate.setIdTicketAluRodal(respuesta.getIdDoc());
	    		ticketUpdate.setNombreFichero(nombreFichero);
	    		gastosService.updateTicketAlumno(ticketUpdate);
	    	} else if(entidad.equals("AUTDES_")){
	    		AutorizacionDesplazamiento autDesUpdate = desplazamientoService.getAutorizacionDesplazamiento(idEntidad);
	    		autDesUpdate.setIdAutTutRodal(respuesta.getIdDoc());
	    		autDesUpdate.setNombreFichero(nombreFichero);
	    		desplazamientoService.updateAutorizacionDesplazamiento(autDesUpdate);
	    	} else if(entidad.equals("ANE_VII_") || entidad.equals("ANE_XI_") ){
	    		AutorizacionesAnexosHistorial autDesUpdate = desplazamientoService.getAutorizacionAnexoHistorial(idEntidad);
	    		autDesUpdate.setIdAneHisRodal(respuesta.getIdDoc());
	    		autDesUpdate.setNombreFichero(nombreFichero);
	    		desplazamientoService.updateAutorizacionAnexoHistorial(autDesUpdate);
	    	} else if(entidad.equals("PAR_MENP_")){ 	    		
	    		Optional<ParsemAluProg> parsemO = parsemAluProgRepository.findById(idEntidad);
	    		ParsemAluProg parsem= parsemO.orElse(null);	
	    		parsem.setIdrodal(respuesta.getIdDoc());
	    		parsem.setFichero(nombreFichero);
	    		parsemAluProgRepository.save(parsem);
	    	} else if(entidad.equals("PAR_MENY_")){ 	    		
	    		Optional<ParsemAluProy> parsemO = parsemAluProyRepository.findById(idEntidad);
	    		ParsemAluProy parsem= parsemO.orElse(null);	
	    		parsem.setIdrodal(respuesta.getIdDoc());
	    		parsem.setFichero(nombreFichero);
	    		parsemAluProyRepository.save(parsem);
	    	} else if(entidad.equals("PAR_SEMPL_")){
				Optional<ParsemAluplan> parsemO = parsemAluplanRepository.findById(idEntidad);
				ParsemAluplan parsem= parsemO.orElse(null);
				parsem.setIdParsemRodal(respuesta.getIdDoc());
				parsem.setTxParsemFichero(nombreFichero);
				parsem.setXUsuarioCreacion(cPerfil.equals("ALU")?usuarioActualComunica:usuarioActual);
				parsem.setCdVista(cPerfil);
				parsem.setFRegistro( new Date());
				parsemAluplanRepository.save(parsem);
			}
	
				
		}
					
		return respuesta;	
	} */
										   
	@Override
	public RespuestaInsertarDoc insertaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad, Long cAnno, Long idCentro, Long idProvincia, String cPerfil, Long usuarioActual, Long usuarioActualComunica)
	        throws RodalExceptionService, InsertarDocFault, IOException {

	    RespuestaInsertarDoc respuesta = null;
	    ParametrosInsertarDoc paramInsertaDoc = null;

	    if (!modulo.equals("MFCT")) {
	        return respuesta;  // Early exit if modulo is not "MFCT"
	    }

	    Long idCodigo = getIdCodigo(idCentro, idProvincia);
	    String nombreFichero = file.getOriginalFilename();
	    MultipartFile renamedFile = renameMultipartFile(file, entidad, nombreFichero);
	    paramInsertaDoc = getParametrosDocNew(renamedFile, entidad, idEntidad, cAnno, modulo, rodalConfig.getUnidadFuncional(),
	            rodalConfig.getSistema(), rodalConfig.getExpediente(), idCodigo);

	    respuesta = insertUpdate(paramInsertaDoc, entidad, idEntidad);

	    if (respuesta.getIdDoc() != null) {
	        processEntityResponse(entidad, idEntidad, nombreFichero, respuesta, cPerfil, usuarioActual, usuarioActualComunica);
	    }

	    return respuesta;
	}

	private Long getIdCodigo(Long idCentro, Long idProvincia) {
	    return idCentro != -1L ? centroService.getCentroById(idCentro).getCodigoCentro() : idProvincia;
	}

	private void processEntityResponse(String entidad, Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta,
			                           String cPerfil, Long usuarioActual, Long usuarioActualComunica) {
	    switch (entidad) {
	        case "CF_":
	        case "CP_":
	            updateConvenio(entidad, idEntidad, nombreFichero, respuesta);
	            break;

	        case "EVA_PROG_":
	            updateAlumnoPrograma(idEntidad, nombreFichero, respuesta);
	            break;

	        case "EVA_PROY_":
	            updateProyectoAlumno(idEntidad, nombreFichero, respuesta);
	            break;

	        case ANE_VIB:
	        case "ANE_VI_":
	        case "ANEVIII_":
	            updateAnexoHistorial(idEntidad, nombreFichero, respuesta);
	            break;

	        case "TICKET_TUT_":
	            updateTicketTutor(idEntidad, nombreFichero, respuesta);
	            break;

	        case "TICKET_ALU_":
	            updateTicketAlumno(idEntidad, nombreFichero, respuesta);
	            break;

	        case "AUTDES_":
	            updateAutorizacionDesplazamiento(idEntidad, nombreFichero, respuesta);
	            break;

	        case ANE_VII:
	        case "ANE_XI_":
	            updateAutorizacionAnexoHistorial(idEntidad, nombreFichero, respuesta);
	            break;

	        case PAR_MENP:
	            updateParsemAluProg(idEntidad, nombreFichero, respuesta);
	            break;

	        case PAR_MENY:
	            updateParsemAluProy(idEntidad, nombreFichero, respuesta);
	            break;

	        case PAR_SEML:
	            updateParsemAluplan(idEntidad, nombreFichero, respuesta, cPerfil, usuarioActual, usuarioActualComunica);
	            break;

			case EVAL_PROY_ANEXO:
				updateEvalProyAnexo(idEntidad,nombreFichero,respuesta);
				break;		
				
			 case ANE_FPE:
		        updateAneFPE(idEntidad, nombreFichero, respuesta, cPerfil, usuarioActual, usuarioActualComunica);
		        break;	
			 case ANEV:
			        updateAneV(idEntidad, nombreFichero, respuesta);
			        break;		        
				
	        default:
	            // Handle unknown entity types
	            break;
	    }
	}
	
	private void updateAneV(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
		Optional<HistorialProgramaPFE> historialProgramaPFE = historialPFERepository.findById(idEntidad);
		HistorialProgramaPFE historial = historialProgramaPFE.orElse(null);
		if (historial != null) {
			historial.setIdAneRodal(respuesta.getIdDoc());
			historial.setTxAneFichero(nombreFichero);
			historialPFERepository.save(historial);
		}		
	}

	private void updateEvalProyAnexo(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
		EvalProyAnexo evalProyAnexo = evaluacionesService.getEvalProyAnexoById(idEntidad);
		if (evalProyAnexo != null) {
			evalProyAnexo.setIdEvaFirRodal(respuesta.getIdDoc());
			evalProyAnexo.setDsEvaFirFichero(nombreFichero);
			evalProyAnexo.setLgActualizado(1);
			evaluacionesService.updateEvalProyAnexo(evalProyAnexo);
		}
	}

	private void updateConvenio(String entidad, Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    ConveniosFct convenioUpdate = conveniosFctService.getConvenioById(idEntidad);
	    if ("CF_".equals(entidad)) {
	        convenioUpdate.setIdConfirRodal(respuesta.getIdDoc());
	        convenioUpdate.setTxConfirFichero(nombreFichero);
	    } else {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(new Date());
	        calendar.add(Calendar.YEAR, 4);
	        convenioUpdate.setFechaProrroga(calendar.getTime());
	        convenioUpdate.setIdConproRodal(respuesta.getIdDoc());
	        convenioUpdate.setTxConproFichero(nombreFichero);
	    }
	    conveniosFctService.updateConvenio(convenioUpdate);
	}

	private void updateAlumnoPrograma(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    AlumnoPrograma alumnoPrograma = alumnoProgramaService.getAlumnoProgramaById(idEntidad);
	    alumnoPrograma.setIdEvaRodal(respuesta.getIdDoc());
	    alumnoPrograma.setTxEvafirFichero(nombreFichero);
	    alumnoPrograma.setFechaFirma(new Date());
	    alumnoProgramaService.updateAlumnoConvenioPrograma(alumnoPrograma);
	}

	private void updateProyectoAlumno(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    ConveniosProyectoAlumno proyectoAlumno = proyectosService.getConvenioProyectoAlumno(idEntidad);
	    proyectoAlumno.setIdEvaRodal(respuesta.getIdDoc());
	    proyectoAlumno.setTxEvafirFichero(nombreFichero);
	    proyectoAlumno.setFechaFirma(new Date());
	    proyectosService.updateAlumnoConvenioProyecto(proyectoAlumno);
	}

	private void updateAnexoHistorial(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    GastoAnexoHistorial anexohistorial = gastosService.getAnexoHistoriaById(idEntidad);
	    anexohistorial.setIdAneHisRodal(respuesta.getIdDoc());
	    anexohistorial.setNombreFichero(nombreFichero);
	    gastosService.updateAnexoHistorial(anexohistorial);
	}

	private void updateTicketTutor(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    TicketTutor ticketUpdate = gastosService.getTicketTutor(idEntidad);
	    ticketUpdate.setIdTicketTutRodal(respuesta.getIdDoc());
	    ticketUpdate.setNombreFichero(nombreFichero);
	    gastosService.updateTicketTutor(ticketUpdate);
	}

	private void updateTicketAlumno(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    TicketAlumnado ticketUpdate = gastosService.getTicketAlumno(idEntidad);
	    ticketUpdate.setIdTicketAluRodal(respuesta.getIdDoc());
	    ticketUpdate.setNombreFichero(nombreFichero);
	    gastosService.updateTicketAlumno(ticketUpdate);
	}

	private void updateAutorizacionDesplazamiento(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    AutorizacionDesplazamiento autDesUpdate = desplazamientoService.getAutorizacionDesplazamiento(idEntidad);
	    autDesUpdate.setIdAutTutRodal(respuesta.getIdDoc());
	    autDesUpdate.setNombreFichero(nombreFichero);
	    desplazamientoService.updateAutorizacionDesplazamiento(autDesUpdate);
	}

	private void updateAutorizacionAnexoHistorial(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    AutorizacionesAnexosHistorial autDesUpdate = desplazamientoService.getAutorizacionAnexoHistorial(idEntidad,-1);
	    autDesUpdate.setIdAneHisRodal(respuesta.getIdDoc());
	    autDesUpdate.setNombreFichero(nombreFichero);
	    desplazamientoService.updateAutorizacionAnexoHistorial(autDesUpdate);
	}

	private void updateParsemAluProg(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    Optional<ParsemAluProg> parsemO = parsemAluProgRepository.findById(idEntidad);
	    ParsemAluProg parsem = parsemO.orElse(null);
	    parsem.setIdrodal(respuesta.getIdDoc());
	    parsem.setFichero(nombreFichero);
	    parsemAluProgRepository.save(parsem);
	}

	private void updateParsemAluProy(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta) {
	    Optional<ParsemAluProy> parsemO = parsemAluProyRepository.findById(idEntidad);
	    ParsemAluProy parsem = parsemO.orElse(null);
	    parsem.setIdrodal(respuesta.getIdDoc());
	    parsem.setFichero(nombreFichero);
	    parsemAluProyRepository.save(parsem);
	}

	private void updateParsemAluplan(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta,
			                         String cPerfil, Long usuarioActual , Long usuarioActualComunica) {
	    Optional<ParsemAluplan> parsemO = parsemAluplanRepository.findById(idEntidad);
	    ParsemAluplan parsem = parsemO.orElse(null);
	    if (parsem != null) {
	        parsem.setIdParsemRodal(respuesta.getIdDoc());
	        parsem.setTxParsemFichero(nombreFichero);
	        parsem.setXUsuarioCreacion(cPerfil.equals("ALU") ? usuarioActualComunica : usuarioActual);
	        parsem.setCdVista(cPerfil);
	        parsem.setFRegistro(new Date());
			parsem.setLgActualizado(1);
	        parsemAluplanRepository.save(parsem);
	    } else {
	        // Handle the case where the entity is not found, either log or throw an exception
	        // For example:
	        throw new IllegalArgumentException("ParsemAluplan with id " + idEntidad + " not found.");
	    }
	}
	
	private void updateAneFPE(Long idEntidad, String nombreFichero, RespuestaInsertarDoc respuesta,
            String cPerfil, Long usuarioActual , Long usuarioActualComunica) {
		Optional<AnexosProgPFE> anexoO = anexosFPERepository.findById(idEntidad);
		AnexosProgPFE anexo = anexoO.orElse(null);
		if (anexo != null) {
		anexo.setIdRodal(respuesta.getIdDoc());
		anexo.setTxFicheroRodal(nombreFichero);	
		anexosFPERepository.save(anexo);
		} else {
		// Handle the case where the entity is not found, either log or throw an exception
		// For example:
		throw new IllegalArgumentException("AnexosProgPFE with id " + idEntidad + " not found.");
		}
}		
										   
										   

	   private MultipartFile renameMultipartFile(MultipartFile file, String entidad, String nombre) throws IOException {
		   
		   if (entidad.equals("EVA_PROG_") || entidad.equals("EVA_PROY_") || entidad.equals("ANE_FCT_") || entidad.equals(ANE_FPE) || entidad.equals("ANEV") ) {
			   
			   String formattedTime = formatoHora.format(new Date());
			   
			   int dotIndex = nombre.indexOf('.');
			   String nuevo = nombre.substring(0, dotIndex) + formattedTime.replace(':','_') + ".pdf" ;			   
			   
			   return new CustomMultipartFile(file.getBytes(), nuevo);
			   
		   } else {
			   return file;
		   }
		   
	        
	    }
	
	private RespuestaInsertarDoc insertUpdate(ParametrosInsertarDoc paramInsertaDoc, String entidad, Long idEntidad) throws InsertarDocFault, RodalExceptionService {
		
		RespuestaInsertarDoc respuesta;		
		Rodal2 rodal2 = rodal2Inicialize.getRodal2FCT(rodalConfig);
		Boolean borrado;
	    if (entidad.equals("EVA_PROG_"))  {
	    	AlumnoPrograma alumnoPrograma = alumnoProgramaService.getAlumnoProgramaById(idEntidad);
	    	if (alumnoPrograma.getIdEvaRodal() != null) {
	    		borrado = rodalClient.borrarDocumento(alumnoPrograma.getIdEvaRodal());
	    	} 
	    	
	    } else if (entidad.equals("EVA_PROY_")) {
	    	 ConveniosProyectoAlumno proyectoAlumno =  proyectosService.getConvenioProyectoAlumno(idEntidad);
	    	 if (proyectoAlumno.getIdEvaRodal() != null) {
		    		borrado = rodalClient.borrarDocumento(proyectoAlumno.getIdEvaRodal());
		    	} 
	    }

		respuesta = rodal2.insertarDoc(paramInsertaDoc);
		
		
		return respuesta;
	}
	
	
	public ParametrosInsertarDoc getParametrosDocNew(MultipartFile file, String entidad, Long idEntidad, Long cAnno, String modulo,
													 String unidadFuncional, String sistema, String expediente, Long cCodigoCentro) throws IOException {
		
		String sAnnoNew = cAnno + "";	
		DocJCCM propiedadesDocNew = new DocJCCM();
		propiedadesDocNew.setTipoDoc(FilenameUtils.getExtension(file.getOriginalFilename()));
		
	  	ParametrosInsertarDoc paramDocNew = new ParametrosInsertarDoc();
	  	paramDocNew.setUnidadFuncional(unidadFuncional);
	  	paramDocNew.setSistema(sistema);  
	  	paramDocNew.setExpediente(expediente);
	  	paramDocNew.setNombre(file.getOriginalFilename());
	  	paramDocNew.setMetadatosRodal(propiedadesDocNew);
	  	paramDocNew.setNombre(entidad + idEntidad + "_" + file.getOriginalFilename());
	  	paramDocNew.setContenido(file.getBytes());    	
	  	paramDocNew.setSubsistema(modulo + sAnnoNew.substring(2,sAnnoNew.length()) + cCodigoCentro);
		
		return paramDocNew; 
		
	}	
	
	@Override
	public Boolean actualizaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad, String cPerfil, Long usuarioActual, Long usuarioActualComunica) throws RodalExceptionService, IOException {
	    
	    String nombreFichero = file.getOriginalFilename();
	    ParametrosActualizarDoc paramActualizarDoc = getParametrosDoc(file, entidad, idEntidad);
	    Rodal2 rodal2 = rodal2Inicialize.getRodal2FCT(rodalConfig);

	    try {
	        rodal2.actualizarDoc(paramActualizarDoc);
	        actualizarEntidad(entidad, idEntidad, nombreFichero, cPerfil, usuarioActual, usuarioActualComunica);
	    } catch (ActualizarDocFault e) {
	        LOGGER.fatal("ERROR Actualizando Doc en Rodal", e);
	        throw new RodalExceptionService("ERROR Actualizando Doc en Rodal:" + e.getMessage());
	    }

	    return true;
	}

	private ParametrosActualizarDoc getParametrosDoc(MultipartFile file, String entidad, Long idEntidad) throws IOException {
	    switch (entidad) {
	        case ANE_VIB:
	        case "ANE_VI_":
	        case "ANE_VIII_":
	            return getParametrosDocForGasto(file, idEntidad);
	        case "CF_":
	        case "CP_":
	            return getParametrosDocForConvenio(file, idEntidad, entidad);
	        case "EVA_PROG_":
	        case "EVA_PROY_":
	            return getParametrosDocForAlumno(file, idEntidad, entidad);
	        case ANE_VII:
	        case "ANE_VII":
	        case "ANE_XI_":
	        case "ANE_XI":
	            return getParametrosDocForAutorizacion(file, idEntidad);
	        case "AUTDES_":
	            return getParametrosDocForDesplazamiento(file, idEntidad);
	        case PAR_MENP:
	        case PAR_MENY:
	        case PAR_SEML:
	            return getParametrosDocForParsem(file, idEntidad, entidad);
			case EVAL_PROY_ANEXO:
				return getParametrosDocForEvalProyAnexo(file, idEntidad, entidad);
			case ANE_FPE:
				return getParametrosDocAnexoFPE(file, idEntidad, entidad);	
				
	        default:
	            throw new IllegalArgumentException("Entidad no reconocida");
	    }
	}
	
	private ParametrosActualizarDoc getParametrosDocAnexoFPE(MultipartFile file, Long idEntidad, String entidad) throws IOException{
		AnexosProgPFE anexo = anexosFPERepository.findById(idEntidad).orElse(null);
		if (anexo != null) {
			return this.getParametrosDocUpdate(file, ANE_FPE, idEntidad, anexo.getIdRodal());
		} 
		 throw new IllegalArgumentException("Error al adjuntar fichero");
		
	}
	private ParametrosActualizarDoc getParametrosDocForEvalProyAnexo(MultipartFile file, Long idEntidad, String entidad) throws IOException{
		EvalProyAnexo evalProyAnexo = evaluacionesService.getEvalProyAnexoById(idEntidad);
		return this.getParametrosDocUpdate(file, ANE_VIB, idEntidad, evalProyAnexo.getIdEvaFirRodal());
	}
	private ParametrosActualizarDoc getParametrosDocForGasto(MultipartFile file, Long idEntidad) throws IOException {
	    GastoAnexoHistorial gasto = gastosService.getAnexoHistoriaById(idEntidad);
	    return this.getParametrosDocUpdate(file, ANE_VIB, idEntidad, gasto.getIdAneHisRodal());
	}

	private ParametrosActualizarDoc getParametrosDocForConvenio(MultipartFile file, Long idEntidad, String entidad) throws IOException {
	    ConveniosFct convenioUpdate = conveniosFctService.getConvenioById(idEntidad);
	    if ("CF_".equals(entidad)) {
	        return this.getParametrosDocUpdate(file, entidad, idEntidad, convenioUpdate.getIdConfirRodal());
	    } else if ("CP_".equals(entidad)) {
	        return this.getParametrosDocUpdate(file, entidad, idEntidad, convenioUpdate.getIdConproRodal());
	    }
	    throw new IllegalArgumentException("Entidad no reconocida para convenio");
	}

	private ParametrosActualizarDoc getParametrosDocForAlumno(MultipartFile file, Long idEntidad, String entidad) throws IOException {
	    if ("EVA_PROG_".equals(entidad)) {
	        AlumnoPrograma alumnoPrograma = alumnoProgramaService.getAlumnoProgramaById(idEntidad);
	        return this.getParametrosDocUpdate(file, entidad, idEntidad, alumnoPrograma.getIdEvaRodal());
	    } else if ("EVA_PROY_".equals(entidad)) {
	        ConveniosProyectoAlumno proyectoAlumno = proyectosService.getConvenioProyectoAlumno(idEntidad);
	        return this.getParametrosDocUpdate(file, entidad, idEntidad, proyectoAlumno.getIdEvaRodal());
	    }
	    throw new IllegalArgumentException("Entidad no reconocida para alumno");
	}

	private ParametrosActualizarDoc getParametrosDocForAutorizacion(MultipartFile file, Long idEntidad) throws IOException {
	    AutorizacionesAnexosHistorial autorizacion = desplazamientoService.getAutorizacionAnexoHistorial(idEntidad,-1);
	    return this.getParametrosDocUpdate(file, ANE_VII, idEntidad, autorizacion.getIdAneHisRodal());
	}

	private ParametrosActualizarDoc getParametrosDocForDesplazamiento(MultipartFile file, Long idEntidad) throws IOException {
	    AutorizacionDesplazamiento desplazamiento = desplazamientoService.getAutorizacionDesplazamiento(idEntidad);
	    return this.getParametrosDocUpdate(file, "AUTDES_", idEntidad, desplazamiento.getIdAutTutRodal());
	}

	private ParametrosActualizarDoc getParametrosDocForParsem(MultipartFile file, Long idEntidad, String entidad) throws IOException {
	    if (PAR_MENP.equals(entidad)) {
	        return returnParMenp(file, idEntidad, entidad);
	        
	    } else if (PAR_MENY.equals(entidad)) {
	        return returnParMeny(file, idEntidad, entidad);
	        
	    } else if (PAR_SEML.equals(entidad)) {
	        return returnParSempl(file, idEntidad, entidad);	        
	    }
	    throw new IllegalArgumentException("Entidad no reconocida para Parsem");
	}

	private ParametrosActualizarDoc returnParSempl(MultipartFile file, Long idEntidad, String entidad)
			throws IOException {
		Optional<ParsemAluplan> parsemO = parsemAluplanRepository.findById(idEntidad);
		ParsemAluplan parsemAluplanUpdate = parsemO.orElse(null);
		
		if (parsemAluplanUpdate != null) {
		    return this.getParametrosDocUpdate(file, entidad, idEntidad, parsemAluplanUpdate.getIdParsemRodal());
		} else {
		    // Log the warning instead of throwing an exception
		    LOGGER.warn("ParsemAluplan with id {} not found, skipping document update.", idEntidad);
		    return null; // or some default value
		}
	}

	private ParametrosActualizarDoc returnParMeny(MultipartFile file, Long idEntidad, String entidad)
			throws IOException {
		Optional<ParsemAluProy> parsemO = parsemAluProyRepository.findById(idEntidad);
		ParsemAluProy parsemYUpdate = parsemO.orElse(null);
		
		if (parsemYUpdate != null) {
		    return this.getParametrosDocUpdate(file, entidad, idEntidad, parsemYUpdate.getIdrodal());
		} else {
		    // Log the warning instead of throwing an exception.
		    LOGGER.warn("ParsemAluProy with id {} not found, skipping document update.", idEntidad);
		    return null; // or some default value
		}
	}

	private ParametrosActualizarDoc returnParMenp(MultipartFile file, Long idEntidad, String entidad)
			throws IOException {
		Optional<ParsemAluProg> parsemO = parsemAluProgRepository.findById(idEntidad);
		ParsemAluProg parsemUpdate = parsemO.orElse(null);
		
		if (parsemUpdate != null) {
		    return this.getParametrosDocUpdate(file, entidad, idEntidad, parsemUpdate.getIdrodal());
		} else {
		    // Log the error without throwing an exception.
		    LOGGER.warn("ParsemAluProg with id {} not found, skipping document update.", idEntidad);
		    return null; // or some default value
		}
	}

	
	private void actualizarEntidad(String entidad, Long idEntidad, String nombreFichero, String cPerfil, Long usuarioActual, Long usuarioActualComunica) {
	    switch (entidad) {
	        case "CF_":
	        case "CP_":
	            actualizarConvenio(idEntidad, nombreFichero, entidad);
	            break;
	        case "EVA_PROG_":
	        case "EVA_PROY_":
	            actualizarAlumno(idEntidad, nombreFichero, entidad);
	            break;
	        case "AUTDES_":
	            actualizarDesplazamiento(idEntidad, nombreFichero);
	            break;
	        case PAR_MENP:
	        case PAR_MENY:
	        case PAR_SEML:
	            actualizarParsem(idEntidad, nombreFichero, entidad, cPerfil, usuarioActual, usuarioActualComunica);
	            break;
			case EVAL_PROY_ANEXO:
				actualizarEvalProyAnexo(idEntidad, nombreFichero,usuarioActual);
				break;
			case ANE_FPE:
				actualizarAneFPE(idEntidad, nombreFichero,usuarioActual);
				break;				
	        default:
	        	break;
	    }
	}
	
	private void actualizarAneFPE(Long idEntidad, String nombreFichero, Long usuarioActual){
		AnexosProgPFE anexo = anexosFPERepository.findById(idEntidad).orElse(null);
		if (anexo != null) {
			anexo.setTxFicheroRodal(nombreFichero);
			anexosFPERepository.save(anexo);
		} else {
	        // Handle not-found: throw exception or return a status
	        throw new ResourceNotFoundException("Error al actualizar anexo " + idEntidad);
	    }
		
	}

	private void actualizarEvalProyAnexo(Long idEntidad, String nombreFichero, Long usuarioActual){
		EvalProyAnexo evalProyAnexo = evaluacionesService.getEvalProyAnexoById(idEntidad);
		evalProyAnexo.setDsEvaFirFichero(nombreFichero);
		evalProyAnexo.setLgActualizado(1);
		evalProyAnexo.setCUsuFirma(usuarioActual);
		evalProyAnexo.setFFirma(new Date());
		evaluacionesService.updateEvalProyAnexo(evalProyAnexo);
	}

	private void actualizarConvenio(Long idEntidad, String nombreFichero, String entidad) {
	    ConveniosFct convenioUpdate = conveniosFctService.getConvenioById(idEntidad);
	    if ("CF_".equals(entidad)) {
	        convenioUpdate.setTxConfirFichero(nombreFichero);
			//convenioUpdate.setFechaFirma(null);
	    } else if ("CP_".equals(entidad)) {
	        convenioUpdate.setTxConproFichero(nombreFichero);
			//convenioUpdate.setFechaFirmaProrroga(null);
	    }
	    conveniosFctService.updateConvenio(convenioUpdate);
	}

	private void actualizarAlumno(Long idEntidad, String nombreFichero, String entidad) {
	    if ("EVA_PROG_".equals(entidad)) {
	        AlumnoPrograma alumnoPrograma = alumnoProgramaService.getAlumnoProgramaById(idEntidad);
	        alumnoPrograma.setTxEvafirFichero(nombreFichero);
	        alumnoProgramaService.updateAlumnoConvenioPrograma(alumnoPrograma);
	    } else if ("EVA_PROY_".equals(entidad)) {
	        ConveniosProyectoAlumno proyectoAlumno = proyectosService.getConvenioProyectoAlumno(idEntidad);
	        proyectoAlumno.setTxEvafirFichero(nombreFichero);
	        proyectosService.updateAlumnoConvenioProyecto(proyectoAlumno);
	    }
	}

	private void actualizarDesplazamiento(Long idEntidad, String nombreFichero) {
	    AutorizacionDesplazamiento desplazamiento = desplazamientoService.getAutorizacionDesplazamiento(idEntidad);
	    desplazamiento.setNombreFichero(nombreFichero);
	    desplazamientoService.updateAutorizacionDesplazamiento(desplazamiento);
	}

	private void actualizarParsem(Long idEntidad, String nombreFichero, String entidad, String cPerfil, Long usuarioActual, Long usuarioActualComunica) {
	    if (PAR_MENP.equals(entidad)) {
	        ParsemAluProg parsemUpdate = parsemAluProgRepository.findById(idEntidad).orElse(null);
	        if (parsemUpdate != null) {
	            parsemUpdate.setFichero(nombreFichero);
	            parsemAluProgRepository.save(parsemUpdate);  // Save the updated entity
	        } 
	    } else if (PAR_MENY.equals(entidad)) {
	        ParsemAluProy parsemYUpdate = parsemAluProyRepository.findById(idEntidad).orElse(null);
	        if (parsemYUpdate != null) {
	        	parsemYUpdate.setFichero(nombreFichero);
	        	parsemAluProyRepository.save(parsemYUpdate);
	        }
	        
	    } else if (PAR_SEML.equals(entidad)) {
	        ParsemAluplan parsemAluplanUpdate = parsemAluplanRepository.findById(idEntidad).orElse(null);
	        if (parsemAluplanUpdate != null) {
		        parsemAluplanUpdate.setTxParsemFichero(nombreFichero);
		        parsemAluplanUpdate.setXUsuarioCreacion(cPerfil.equals("ALU") ? usuarioActualComunica : usuarioActual);
		        parsemAluplanUpdate.setCdVista(cPerfil);
		        parsemAluplanUpdate.setFRegistro(new Date());
				parsemAluplanUpdate.setLgActualizado(1);
		        parsemAluplanRepository.save(parsemAluplanUpdate);
		        }
	    }
	}
	
	
	public ParametrosActualizarDoc getParametrosDocUpdate(MultipartFile file,String entidad, Long idEntidad, String idDocRodal) throws IOException {
		
    	DocJCCM propiedadesDocUpdate = new DocJCCM();
    	propiedadesDocUpdate.setTipoDoc(FilenameUtils.getExtension(file.getOriginalFilename()));
    	
    	ParametrosActualizarDoc paramDocUpdate = new ParametrosActualizarDoc();
    	paramDocUpdate.setNombre(file.getOriginalFilename());
    	paramDocUpdate.setMetadatosRodal(propiedadesDocUpdate);
    	paramDocUpdate.setNombre(entidad + idEntidad + "_" + file.getOriginalFilename());  
    	paramDocUpdate.setContenido(file.getBytes());
    	paramDocUpdate.setIdDoc(idDocRodal);
    	
    	return paramDocUpdate; 
		
	}

	@Override
	public Boolean actualizaDoc(MultipartFile file, String idRodal) throws RodalExceptionService, IOException {
		
		Rodal2 rodal2DGC = rodal2Inicialize.getRodal2FCT(rodalConfig);
		ParametrosActualizarDoc paramActualizarDoc = new ParametrosActualizarDoc();
		paramActualizarDoc.setIdDoc(idRodal);
		try {
			
		    paramActualizarDoc.setContenido(file.getBytes());		
			rodal2DGC.actualizarDoc(paramActualizarDoc);
			
		} catch (ActualizarDocFault e) {
            LOGGER.fatal("ERROR Actualizando Doc en Rodal",e);
             throw new RodalExceptionService("ERROR Actualizando Doc en Rodal:" + e.getMessage());
		}
		return true;
		
	}
}