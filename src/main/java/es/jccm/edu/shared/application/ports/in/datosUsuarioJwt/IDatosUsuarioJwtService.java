package es.jccm.edu.shared.application.ports.in.datosUsuarioJwt;

import es.jccm.edu.shared.application.domain.datosUsuarioJwt.AplicacionUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosFreshServiceJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.NombreUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.RolUsuarioJwt;

import java.util.List;


public interface IDatosUsuarioJwtService {

	DatosUsuarioJwt getDatosUsuarioByJwt(String jwt);

	DatosUsuarioJwt getDatosUsuarioByJwtUsingRepository(String jwt);

	DatosUsuarioJwt getDatosUsuarioByOidUsuario(String oidUsuario);

	List<AplicacionUsuarioJwt> getAplicacionesUsuario(String oidUsuario);

	List<RolUsuarioJwt> getRolesUsuario(String oidUsuario, String nif);

	boolean hasSimulacionRole(String jwt);

	NombreUsuarioJwt getUsuarioNombre(String oidUsuario);
	
	List<DatosFreshServiceJwt> getDatosFreshService(Long oidUsuario);
	
	DatosUsuarioJwt getDatosUsuarioByOidUsuarioUsingRepository(Long oidUsuario);
    
	Long getSecurityLevel(String oidUsuario);
	
	Long getSecurityLevelExcepcion(String oidUsuario);
	
	Long getSecurityLevelEmpleado(String oidEmpleado);
	
}
