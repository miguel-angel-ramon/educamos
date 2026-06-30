package es.jccm.edu.documentosGC.application.ports.in.centrodoc;
import java.util.List;

import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.CentroDocInspeccion;

public interface ICentroDocInspeccionService {
	
	List<CentroDocInspeccion> getCentroMunicipio(Long c_provincia,Long c_municipio);

	List<CentroDocInspeccion> getListadoCentrosMunicipioZona(Long c_provincia, Long c_municipio, Long idPerfil,	Long idUsuario);

	List<CentroDocInspeccion> getListadoCentrosMunicipioInspectorCentro(Long c_provincia, Long c_municipio, Long xEmpleado, String fTomaPos);

	List<CentroDocInspeccion> getListadoCentrosMunicipioInspectorProvincial(Long c_provincia, Long c_municipio,	Long idPerfil, Long idUsuario);

	List<CentroDocInspeccion> getListadoCentrosConsejeria(Long c_provincia, Long c_municipio);

}
