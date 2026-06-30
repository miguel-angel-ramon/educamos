package es.jccm.edu.documentosGC.application.ports.in.rodal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;

public interface RodalClientDgc {
	
	ByteArrayOutputStream recuperaDoc(String idDoc, String modulo) throws RodalExceptionDgc;
	
	Boolean borrarDocumento(String idRodal, String modulo, String entidad) throws RodalExceptionDgc;

	RespuestaInsertarDoc insertaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad, Long cAnno, Long idCentro) throws RodalExceptionDgc, InsertarDocFault, IOException;

	Boolean actualizaDoc(MultipartFile file, String modulo, String entidad, Long idEntidad) throws RodalExceptionDgc, IOException;

	Boolean actualizaDoc(MultipartFile file, String idRodal) throws RodalExceptionDgc, IOException;


}
