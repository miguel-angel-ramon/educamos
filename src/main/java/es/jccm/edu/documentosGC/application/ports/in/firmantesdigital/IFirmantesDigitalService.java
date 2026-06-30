package es.jccm.edu.documentosGC.application.ports.in.firmantesdigital;

import java.util.List;
import java.util.Optional;


import es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities.DetalleFirmante;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities.AdjuntosFirmantes;
import es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities.FirmanteRequest;

public interface IFirmantesDigitalService {

	List<AdjuntosFirmantes> createAdjuntosFirmantes(List<FirmanteRequest> profesoresActaEvaluacionIdsDto, Long idAdjunto, String conTutor);
	
	Optional<AdjuntosFirmantes> findByAdjuntoIdAndEmpleadoId(Long idAdjunto, Long idEmpleado);	

	List<DetalleFirmante> getDetalleFirmantes(Long idAdjunto);

	
	void deleteByIdAdjunto(Long idAdjunto);
	
	void deleteById(Long idFirmante);


	Boolean actualizarDocumentoFirmado(Long idAdjunto, Long idPerfil, Long idEmpleado, Long idUsuario, String tipoFirma);
	List<String> getEntornoFirma();
	
	AdjuntosFirmantes createAdjuntosFirmante(Long idAdjunto, Long idEmpleado);

	List<AdjuntosFirmantes> createAdjuntosFirmantesPartes(List<FirmanteRequest> firmantes,
            Long idAdjunto);

	Integer getNumeroFirmados(Long idAdjunto);

}
