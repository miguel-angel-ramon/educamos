package es.jccm.edu.proyectosfct.application.ports.in.rodal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;

public interface RodalClientMfct {
	
	ByteArrayOutputStream recuperaDoc(String idDoc, String modulo) throws RodalExceptionMfct;
	
	Boolean borrarDocumento(String idRodal, String modulo, String entidad) throws RodalExceptionMfct;

	RespuestaInsertarDoc insertaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad, Long cAnno, Long idCentro) throws RodalExceptionMfct, InsertarDocFault, IOException;

	Boolean actualizaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad) throws RodalExceptionMfct, IOException;

	Boolean actualizaDoc(MultipartFile file, String idRodal) throws RodalExceptionMfct, IOException;


}
