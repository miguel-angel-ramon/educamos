package es.jccm.edu.documentosGC.application.ports.in.datosterritoriales;

import es.jccm.edu.documentosGC.application.domain.datosterritoriales.MunicipioDoc;
import java.util.List;

public interface IMunicipioDocService {
	
	List<MunicipioDoc> findMunicipioByProvincia(Long idProvincia);

	List<MunicipioDoc> getMunicipioProvinciaZona(Long idProvincia, Long idPerfil, Long idUsuario);

	List<MunicipioDoc> getMunicipioProvinciaCentro(Long idProvincia,Long xEmpleado, String fTomaPos);

	List<MunicipioDoc> getMunicipioInspectorProvincial(Long idProvincia, Long idPerfil, Long idUsuario);

	List<MunicipioDoc> getMunicipioConsejeria(Long idProvincia);

}
