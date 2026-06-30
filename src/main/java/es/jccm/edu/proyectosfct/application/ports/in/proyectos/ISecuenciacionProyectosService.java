package es.jccm.edu.proyectosfct.application.ports.in.proyectos;

import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.SecuenciacionProyectos;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;

public interface ISecuenciacionProyectosService {
	
	// Create

	SecuenciacionProyectos createSecuenciacionProyectos(SecuenciacionProyectos secuenciacionProyectosIn);

	Optional<SecuenciacionProyectos> getSecuenciacionProyectoId(Long idProyecto);

	SecuenciacionProyectos updateSecuenciacionProyecto(SecuenciacionProyectos secProyecto);

	SecuenciacionProyectos getSecuenciacionById(Long idSecuenciacion);
	
	void deleteSecuenciacionProyectos(Long idSecProy);

	SecuenciacionProyectos createDocumentoGC(SecuenciacionProyectos documento, MultipartFile file, Long cAnno, Long idCentro)
			throws RodalExceptionService, Exception;
	

	SecuenciacionProyectos getSecuenciacionByIdSecficRodal(String idRodal);
	
	

}
