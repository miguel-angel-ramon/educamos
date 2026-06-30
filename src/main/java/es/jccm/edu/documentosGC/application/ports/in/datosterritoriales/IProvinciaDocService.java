package es.jccm.edu.documentosGC.application.ports.in.datosterritoriales;

import java.util.List;

import es.jccm.edu.documentosGC.application.domain.datosterritoriales.ProvinciaDoc;

public interface IProvinciaDocService {

	// Read
	ProvinciaDoc findProvinciaById(Long id);
	
	// Read
	List<ProvinciaDoc> getListadoProvincias();

	List<ProvinciaDoc> getListadoProvinciasZona(Long idPerfil, Long idUsuario);

	List<ProvinciaDoc> getListadoProvinciasCentro(Long xEmpleado, String fTomaPos);

	List<ProvinciaDoc> getListadoProvinciasProvincial(Long idPerfil, Long idUsuario, Long idCentroProvincia);

}
