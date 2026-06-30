package es.jccm.edu.proyectosfct.application.services.rodal;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import es.jccm.edu.proyectosfct.application.configuration.rodal.RodalConfig;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.SecuenciacionProyectos;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IConveniosFctService;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.ISecuenciacionProyectosService;
import es.jccm.edu.proyectosfct.application.ports.in.rodal.RodalClientMfct;
import es.jccm.edu.proyectosfct.application.ports.in.rodal.RodalExceptionMfct;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.ICentroService;
import es.jccm.edu.shared.configuration.rodal.Rodal2Inicialize;
import lombok.extern.slf4j.Slf4j;

@Service("rodalClientServiceMfct")
@Slf4j
public class RodalClientServiceMfct  implements RodalClientMfct {

    private static final String ERROR_BORRADO_RODAL = "ERROR Borrando de Rodal";
    private static final String ERROR_RECUPERADO_RODAL = "ERROR Borrando de Rodal";
	
	@Autowired
    private Rodal2Inicialize rodal2Inicialize;
	
    @Autowired
    private RodalConfig rodalConfig;
    
	@Autowired
	private IConveniosFctService conveniosFctService;
	
	@Autowired
	private ISecuenciacionProyectosService secProyectosService;
	
	@Autowired
	private ICentroService centroService;
    
    /**
     * Método que retorna un documento de Rodal
     * 
     * @param idRodal
     * @return
     * @throws RodalExceptionMfct
     */
    public ByteArrayOutputStream recuperaDoc(String idRodal, String modulo) throws RodalExceptionMfct {
        try {
            ParametrosRecuperarDoc parametrosRecuperarDoc = new ParametrosRecuperarDoc();
            parametrosRecuperarDoc.setIdDoc(idRodal);
            
            RespuestaRecuperarDoc respuestaRec;
            
            if(modulo.equals("MFCT")) {
            	Rodal2 rodal2 = rodal2Inicialize.getRodal2FCT(rodalConfig);            
                respuestaRec = rodal2.recuperarDoc(parametrosRecuperarDoc);
            } else {
            	throw new IllegalArgumentException("Operación solo implementada para módulo mfct");
            }

            if(respuestaRec.getContenido() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // El cliente lo decodifica automáticamente
                baos.write(respuestaRec.getContenido());
                return baos;
            }else {
            	String message = MessageFormat.format("ERROR Recuperando de Rodal. El documento {0} no retorna contenido.", idRodal);
                log.error(message);
                throw new RodalExceptionMfct(message);
            }

        }catch (RecuperarDocFault|IOException e) {
            log.error(ERROR_RECUPERADO_RODAL,e);
            throw new RodalExceptionMfct( ERROR_BORRADO_RODAL +": " + e.getMessage());
        }

    }   
    

	@Override
	public Boolean borrarDocumento(String idRodal, String modulo, String entidad) throws RodalExceptionMfct {
		try {
		        ParametrosBorrarDoc parametos = new ParametrosBorrarDoc();
		        parametos.setIdDoc(idRodal);
		        ResultadoOperacion resultado = new ResultadoOperacion();
		        
		        if(modulo.equals("MFCT")) {
		        	if (entidad.equals("CF_") || entidad.equals("CP_") || entidad.equals("SP_") ) { 
		        	  Rodal2 rodal2 = rodal2Inicialize.getRodal2FCT(rodalConfig);            
		              resultado = rodal2.borrarDoc(parametos);		        		
		        	}
		        	if (entidad.equals("CF_")) {            	
		            	ConveniosFct convenioUpdate =  conveniosFctService.getConvenioByIdConfirRodal(idRodal); 
		                convenioUpdate.setIdConfirRodal(null);
		                convenioUpdate.setTxConfirFichero(null);
		                convenioUpdate.setFechaFirma(null);
		                convenioUpdate.setFechaProrroga(null);
		                conveniosFctService.updateConvenio(convenioUpdate);    
		                return resultado.getResultado() == 0;
		            } else if (entidad.equals("CP_")) {
		              	ConveniosFct convenioUpdate =  conveniosFctService.getConvenioByIdConproRodal(idRodal); 
		                convenioUpdate.setIdConproRodal(null);
		                convenioUpdate.setTxConproFichero(null);
		                convenioUpdate.setFechaFirmaProrroga(null);
		                conveniosFctService.updateConvenio(convenioUpdate);  
		                return resultado.getResultado() == 0;
		            	
		            } else if (entidad.equals("SP_")) {
		              	SecuenciacionProyectos secProyUpdate =  secProyectosService.getSecuenciacionByIdSecficRodal(idRodal); 
		              	secProyUpdate.setIdSecficRodal(null);
		              	secProyUpdate.setTxSecficFichero(null);
		                
		              	secProyectosService.updateSecuenciacionProyecto(secProyUpdate);  
		                return resultado.getResultado() == 0;
		            	
		            } 
		        	return false;          	
	            	
	            } else {
	            	throw new IllegalArgumentException("Solo para módulo mfct");  
	            }        
		      	
	
		        
		        
		}catch (BorrarDocFault e) {
        	String message = MessageFormat.format(ERROR_BORRADO_RODAL + " {0}", idRodal);
        	
        	log.error(ERROR_RECUPERADO_RODAL, message);
        	
            throw new RodalExceptionMfct(ERROR_BORRADO_RODAL+": " + message);
        }
	
	}

	
	
	@Override
	public RespuestaInsertarDoc insertaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad, Long cAnno, Long idCentro)
												throws RodalExceptionMfct, InsertarDocFault, IOException {		
		
		RespuestaInsertarDoc respuesta = null;
		ParametrosInsertarDoc paramInsertaDoc = null;
    	if (modulo.equals("MFCT")) {
    		
    		
    		Centro centro = centroService.getCentroById(idCentro);
    		
    		String nombreFichero =  file.getOriginalFilename();
	        paramInsertaDoc = getParametrosDocNew(file,entidad,idEntidad,cAnno,modulo,
											      rodalConfig.getUnidadFuncional(),
											      rodalConfig.getSistema(),
											      rodalConfig.getExpediente(),
											      centro.getCodigoCentro());
			
		    Rodal2 rodal2 = rodal2Inicialize.getRodal2FCT(rodalConfig);	
		    respuesta = rodal2.insertarDoc(paramInsertaDoc);
		    	
	    	if ((entidad.equals("CF_")  || entidad.equals("CP_")) && respuesta.getIdDoc() != null ) {                	
            	ConveniosFct convenioUpdate = conveniosFctService.getConvenioById(idEntidad);
            	
            	if (entidad.equals("CF_")) { 
                	convenioUpdate.setIdConfirRodal(respuesta.getIdDoc());
                	convenioUpdate.setTxConfirFichero(nombreFichero);            	
                	
            	} else {
            		
            		Date fechaFinVigencia = convenioUpdate.getFechaFinVigencia();	
            		Calendar calendar=Calendar.getInstance();
                	calendar.setTime(fechaFinVigencia);
                	calendar.add(Calendar.YEAR, 2);
                	Date fechaProrroga = calendar.getTime();
                	convenioUpdate.setFechaProrroga(fechaProrroga);
            		
            		convenioUpdate.setIdConproRodal(respuesta.getIdDoc());
            		convenioUpdate.setTxConproFichero(nombreFichero);                		
            	}
            	
            	conveniosFctService.updateConvenio(convenioUpdate);
            }  else if(entidad.equals("SP_") && respuesta.getIdDoc() != null) {
        		SecuenciacionProyectos secProyectosUpdate = secProyectosService.getSecuenciacionById(idEntidad);
        		secProyectosUpdate.setIdSecficRodal(respuesta.getIdDoc());
        		secProyectosUpdate.setTxSecficFichero(nombreFichero);
        		
        		secProyectosService.updateSecuenciacionProyecto(secProyectosUpdate);
            }				    
				
		} else {
			
			throw new IllegalArgumentException("Solo para módulo mfct");
				
		} 
					
		return respuesta;	
	}
	
	
	public ParametrosInsertarDoc getParametrosDocNew(MultipartFile file, String entidad, Long idEntidad, Long cAnno, String modulo,
													 String unidadFuncional, String sistema, String expediente, Long cCodigoCentro) throws IOException {
		
		String sAnno = cAnno + "";	
		DocJCCM propiedadesDoc = new DocJCCM();
		propiedadesDoc.setTipoDoc(FilenameUtils.getExtension(file.getOriginalFilename()));
		
	  	ParametrosInsertarDoc paramDoc = new ParametrosInsertarDoc();
		paramDoc.setUnidadFuncional(unidadFuncional);
		paramDoc.setSistema(sistema);  
		paramDoc.setExpediente(expediente);
		paramDoc.setNombre(file.getOriginalFilename());
		paramDoc.setMetadatosRodal(propiedadesDoc);
		paramDoc.setNombre(entidad + idEntidad + "_" + file.getOriginalFilename());
		paramDoc.setContenido(file.getBytes());    	
		paramDoc.setSubsistema(modulo + sAnno.substring(2,sAnno.length()) + cCodigoCentro);
		
		return paramDoc; 
		
	}
	
	
	

	@Override
	public Boolean actualizaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad) throws RodalExceptionMfct, IOException {
		
		String nombreFichero = file.getOriginalFilename(); 		
		ParametrosActualizarDoc paramActualizarDoc = null;
		
		if (modulo.equals("MFCT")) {
			
			ConveniosFct convenioUpdate = new ConveniosFct();
			SecuenciacionProyectos secProyectos = new SecuenciacionProyectos();			
			if (entidad.equals("CF_")) {
				
	        	convenioUpdate = conveniosFctService.getConvenioById(idEntidad);
	           	paramActualizarDoc = this.getParametrosDocUpdate(file,entidad,idEntidad, convenioUpdate.getIdConfirRodal()); 	

	      
	        } else if (entidad.equals("CP_")) { 		
				
	        	convenioUpdate = conveniosFctService.getConvenioById(idEntidad);
	        	paramActualizarDoc = this.getParametrosDocUpdate(file,entidad,idEntidad, convenioUpdate.getIdConproRodal());
	        	
	        } else if (entidad.equals("SP_")) { 		
			
				secProyectos = secProyectosService.getSecuenciacionById(idEntidad);
				paramActualizarDoc = this.getParametrosDocUpdate(file,entidad,idEntidad, secProyectos.getIdSecficRodal());      
			} 	
			//TODO: y en otro caso que parametros usamos?
	        
	        try {
	        	
	        	Rodal2 rodal2 = rodal2Inicialize.getRodal2FCT(rodalConfig);            
	        	rodal2.actualizarDoc(paramActualizarDoc);	        	
	        	
	        	if (entidad.equals("CF_") && convenioUpdate != null) { 
	        		
	            	convenioUpdate.setTxConfirFichero(nombreFichero);     
	            	conveniosFctService.updateConvenio(convenioUpdate);
	        		
	        	} else if (entidad.equals("CP_") && convenioUpdate != null) {
	        	
	            	convenioUpdate.setTxConproFichero(nombreFichero);
	            	conveniosFctService.updateConvenio(convenioUpdate);
	            	
	        		
	        	} else if (entidad.equals("SP_") && secProyectos != null) {
	        	
	        		secProyectos.setTxSecficFichero(nombreFichero);     
	            	secProyectosService.updateSecuenciacionProyecto(secProyectos);
	        	}
	        	

	        }catch (ActualizarDocFault e) {
	            log.error("ERROR Actualizando Doc en Rodal",
	                         e);
	            throw new RodalExceptionMfct("ERROR Actualizando Doc en Rodal:" + e.getMessage());
	        }			
			
		}	 return true ;	
	}
	
	public ParametrosActualizarDoc getParametrosDocUpdate(MultipartFile file,String entidad, Long idEntidad, String idDocRodal) throws IOException {
		
    	DocJCCM propiedadesDoc = new DocJCCM();
    	propiedadesDoc.setTipoDoc(FilenameUtils.getExtension(file.getOriginalFilename()));
    	
    	ParametrosActualizarDoc paramDoc = new ParametrosActualizarDoc();
    	paramDoc.setNombre(file.getOriginalFilename());
    	paramDoc.setMetadatosRodal(propiedadesDoc);
    	paramDoc.setNombre(entidad + idEntidad + "_" + file.getOriginalFilename());  
    	paramDoc.setContenido(file.getBytes());
    	paramDoc.setIdDoc(idDocRodal);
    	
    	return paramDoc; 
		
	}

	@Override
	public Boolean actualizaDoc(MultipartFile file, String idRodal) throws RodalExceptionMfct, IOException {
		throw new IllegalStateException("esta funcion solo la llama dgc");
	}
}