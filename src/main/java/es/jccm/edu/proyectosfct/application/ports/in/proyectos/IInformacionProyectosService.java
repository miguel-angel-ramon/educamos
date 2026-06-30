package es.jccm.edu.proyectosfct.application.ports.in.proyectos;

import java.io.IOException;
import java.util.Optional;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.InformacionProyectos;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import org.springframework.web.multipart.MultipartFile;


public interface IInformacionProyectosService {
	
	// Create

	InformacionProyectos createInfoProyecto(InformacionProyectos infoProyectoIn, MultipartFile file, Long anno) throws Exception;
	
	// Get
	
	Optional<InformacionProyectos> getInfoProyectoId(Long idProyecto);
	
	void deleteInformacionProyecto(Long id);

	Integer countActividadesProyecto(Long idProyecto);	

}
