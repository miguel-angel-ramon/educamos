package es.jccm.edu.proyectosfct.application.ports.in.usuarios;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.usuarios.entities.ParamsFCT;

public interface IParamsFCTService {
	
	List<ParamsFCT> paramsFCT(String vista, Long idCentro, Long xUsuario, Long xUsuarioComunica, Long oid);	
	
	Long getXusuarioComunicaByOid(Long oid, Long xUsuarioComunica);
	
	Long getXempleadoComunicaByOid(Long oid, Long xEmpleadoComunica);

}
