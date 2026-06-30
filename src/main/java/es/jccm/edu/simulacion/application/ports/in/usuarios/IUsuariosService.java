package es.jccm.edu.simulacion.application.ports.in.usuarios;

import java.util.List;

import es.jccm.edu.simulacion.adapter.in.rest.usuarios.model.DocenteSustitutoDTO;
import es.jccm.edu.simulacion.adapter.in.rest.usuarios.model.PerfilDefectoUsuarioDTO;
import es.jccm.edu.simulacion.application.domain.usuarios.*;
import es.jccm.edu.simulacion.application.domain.usuarios.projection.UsuarioSimuSh;


public interface IUsuariosService {

	List<UsuarioDto> getListadoUsuarios(Long codCentro, Long codPerfil);
	
	List<UsuarioList> getListadoUsuariosDelphos(Long codCentro, Long codPerfil);

	DatosUsuario getDatosUsuario(String usuarioDelphos, String usurioComunica, Long idEmpledado);
	
	List<PerfilUsuarioList> getPerfilesUsuario(String idUsuario);
	
	List<PerfilUsuarioList> getPerfilesDelphosUsuario(String idUsuario);
	
	List<CentroUsuarioList> getCentrosUsuario(String idUsuario);
	
	List<CentroUsuarioList> getCentrosDelphosUsuario(String idUsuario);

	List<CentroUsuarioList> getCentrosDelphosUsuarioByXEmpleado(Long xEmpleado);
	
	DatosPersonalesUsuario getDatosPersonalesUsuario(Long idUsuario, Long oid);

	CursoAcademico getAnnoActual();

	Long getAnnoAacademico();
	
    DatosUsuario getDatosUsuarioDelphos(Long oidUsuario);
	
	List<PerfilUsuarioList> getPerfilesDelphosUsuarioFCTDGC(String idUsuario);

	DatosUsuario getDatosUsuarioModAcc(String xPerfil, Long oid);
	
	List<PerfilUsuarioList> getPerfilesModAccUsuarioFCTDGC(String xPerfil, String xCentro, Long oid);
	
	String getPosicionComponentesEscritorio(String dni);
	
	
	void setPosicionComponentesEscritorio(String dni, String posicion);
	
	void setConfigUsuario(String oid, String options);
	
	void setTourUsuario(String oid);
	
	void setTourEvaluacionUsuario(String oid);
	
	Boolean isDocenteSustituto(Long idEmpleado, Integer anno);
	
	List<DocenteSustitutoDTO> getDocentesSustitutos(Long idEmpleado, Integer anno);

	List<DocenteSustitutoDTO> getDocentesSustituye(Long idEmpleado, Integer anno);

	PerfilDefectoUsuarioDTO getPerfilDefecto(Long oidUsuario);

	void resetConfigUsuario(Long oidUsuario);

	Boolean isCoordinadorCicloOrFP(Long idEmpleado, String fechaTomaPosesion);

	Boolean isJefeDepartamento(Long idEmpleado, String fechaTomaPosesion);

	Long isInspector(Long idEmpleado);

	Boolean getAccesoShellProfesorYDirector(Long idUsuarioModAcc);

	Long getDirectorReport(Long idCentro);
	
	List<UsuarioSimuSh> getListadoUsuariosSimulacionSh(Long codCentro, String codRol);

	List<Long> centrosAnteriores(Long idEmpleado);

	List<Long> anyosAnteriores();


}
