package es.jccm.edu.shared.application.ports.in.rodal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;

public interface IRodalClient {
	
	ByteArrayOutputStream recuperaDoc(String idDoc, String modulo) throws RodalExceptionService;
	
	Boolean borrarDocumento(String idRodal, String modulo, String entidad) throws RodalExceptionService;
	
	Boolean borrarDocumento(String idRodal) throws RodalExceptionService;

	RespuestaInsertarDoc insertaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad, Long cAnno, Long idCentro, Long idProvincia, String cPerfil, Long usuarioActual, Long usuarioActualComunica) throws RodalExceptionService, InsertarDocFault, IOException;

	Boolean actualizaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad, String cPerfil, Long usuarioActual, Long usuarioActualComunica) throws RodalExceptionService, IOException;

	Boolean actualizaDoc(MultipartFile file, String idRodal) throws RodalExceptionService, IOException;


}
