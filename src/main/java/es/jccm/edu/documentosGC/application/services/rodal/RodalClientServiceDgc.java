package es.jccm.edu.documentosGC.application.services.rodal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

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
import es.jccm.edu.documentosGC.application.configuration.rodal.RodalConfigDGC;
import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.CentroDocumentosGC;
import es.jccm.edu.documentosGC.application.ports.in.centrodoc.ICentroDocGCService;
import es.jccm.edu.documentosGC.application.ports.in.rodal.RodalClientDgc;
import es.jccm.edu.documentosGC.application.ports.in.rodal.RodalExceptionDgc;
import es.jccm.edu.shared.configuration.rodal.Rodal2Inicialize;
import lombok.extern.slf4j.Slf4j;

@Service("rodalClientServiceDgc")
@Slf4j
public class RodalClientServiceDgc implements RodalClientDgc {

    private static final String ERROR_BORRADO_RODAL = "ERROR Borrando de Rodal";
    private static final String ERROR_RECUPERADO_RODAL = "ERROR Borrando de Rodal";
	
	@Autowired
    private Rodal2Inicialize rodal2Inicialize;

    
    @Autowired
    private RodalConfigDGC rodalConfigDGC;
    
	
	@Autowired
	private ICentroDocGCService centroDocService;
	
    
    
    
    /**
     * Método que retorna un documento de Rodal
     * 
     * @param idRodal
     * @return
     * @throws RodalExceptionMfct
     */
    public ByteArrayOutputStream recuperaDoc(String idRodal, String modulo) throws RodalExceptionDgc {
        /*try {
            ParametrosRecuperarDoc parametrosRecuperarDoc = new ParametrosRecuperarDoc();
            parametrosRecuperarDoc.setIdDoc(idRodal);
            
            RespuestaRecuperarDoc respuestaRec;
            
            if(modulo.equals("MFCT")) {
    			throw new IllegalArgumentException("Solo para módulo dgc");
            } else {
            	Rodal2 rodal2DGC = rodal2Inicialize.getRodal2DGC(rodalConfigDGC);            
                respuestaRec = rodal2DGC.recuperarDoc(parametrosRecuperarDoc);
            }

            if(respuestaRec.getContenido() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // El cliente lo decodifica automáticamente
                baos.write(respuestaRec.getContenido());
                return baos;
            }else {
            	String message = MessageFormat.format("ERROR Recuperando de Rodal. El documento {0} no retorna contenido.", idRodal);
                log.error(message);
                throw new RodalExceptionDgc(message);
            }

        }catch (RecuperarDocFault|IOException e) {
            log.error(ERROR_RECUPERADO_RODAL,e);
            throw new RodalExceptionDgc( ERROR_BORRADO_RODAL +": " + e.getMessage());
        } */
    	return null;

    }   
    

	@Override
	public Boolean borrarDocumento(String idRodal, String modulo, String entidad) throws RodalExceptionDgc {
		return null;
		/*try {
		        ParametrosBorrarDoc parametos = new ParametrosBorrarDoc();
		        parametos.setIdDoc(idRodal);
		        ResultadoOperacion resultado = new ResultadoOperacion();
		        
		        if(modulo.equals("MFCT")) {
	    			throw new IllegalArgumentException("Solo para módulo dgc");
   	
	            	
	            } else {
	            	Rodal2 rodal2DGC = rodal2Inicialize.getRodal2DGC(rodalConfigDGC);            
	            	resultado = rodal2DGC.borrarDoc(parametos);   
	            }        
		      	
	        	return resultado.getResultado() == 0;
		
		        
		        
		}catch (BorrarDocFault e) {
        	String message = MessageFormat.format(ERROR_BORRADO_RODAL + " {0}", idRodal);
        	
        	log.error(ERROR_RECUPERADO_RODAL, message);
        	
            throw new RodalExceptionDgc(ERROR_BORRADO_RODAL+": " + message);
        } */
	
	}

	
	
	@Override
	public RespuestaInsertarDoc insertaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad, Long cAnno, Long idCentro)
												throws RodalExceptionDgc, InsertarDocFault, IOException {		
		
		return null;
		
		/*RespuestaInsertarDoc respuesta = null;
		ParametrosInsertarDoc paramInsertaDoc = null;
    	if (modulo.equals("MFCT")) {
			throw new IllegalArgumentException("Solo para módulo dgc");

		} else {
			
			CentroDocumentosGC centro = centroDocService.findById(idCentro);
			
		    paramInsertaDoc = getParametrosDocNew(file,entidad,idEntidad,cAnno, modulo,
											      rodalConfigDGC.getUnidadFuncional(),
											      rodalConfigDGC.getSistema(),
											      rodalConfigDGC.getExpediente(),
											      centro.getCodigoCentro());
									
			Rodal2 rodal2DGC = rodal2Inicialize.getRodal2DGC(rodalConfigDGC); 
			respuesta = rodal2DGC.insertarDoc(paramInsertaDoc); 
				
		} 
					
		return respuesta;	 */
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
	public Boolean actualizaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad) throws RodalExceptionDgc, IOException {
		
		String nombreFichero = file.getOriginalFilename(); 		
		ParametrosActualizarDoc paramActualizarDoc = null;
		
		if (modulo.equals("MFCT")) {
			throw new IllegalArgumentException("Solo para módulo dgc");
	
			
		}
		throw new IllegalStateException("funcion no implementada");
		//return true ;	
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
	public Boolean actualizaDoc(MultipartFile file, String idRodal) throws RodalExceptionDgc, IOException {
		
		return null;
		
		/*Rodal2 rodal2DGC = rodal2Inicialize.getRodal2DGC(rodalConfigDGC);    
		ParametrosActualizarDoc paramActualizarDoc = new ParametrosActualizarDoc();
		paramActualizarDoc.setIdDoc(idRodal);
		try {
			
		    paramActualizarDoc.setContenido(file.getBytes());		
			rodal2DGC.actualizarDoc(paramActualizarDoc);
			
		} catch (ActualizarDocFault e) {
            log.error("ERROR Actualizando Doc en Rodal",e);
             throw new RodalExceptionDgc("ERROR Actualizando Doc en Rodal:" + e.getMessage());
		}
		return true;*/
		
	} 
}