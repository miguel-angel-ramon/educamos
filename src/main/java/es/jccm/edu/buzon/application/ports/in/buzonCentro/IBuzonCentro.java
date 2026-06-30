package es.jccm.edu.buzon.application.ports.in.buzonCentro;

import java.util.List;

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.UsuariotBuzonDto;
import es.jccm.edu.buzon.application.domain.buzonCentro.BuzonCentro;
import es.jccm.edu.buzon.application.domain.buzonCentro.UnidadBuzonCentro;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.simulacion.adapter.in.rest.centros.model.UsuariotDto;

public interface IBuzonCentro {

	List<UsuariotDto> getPersonalCentro(String oidCentro);
	
	List<UsuariotDto> getAdministradoresBuzonCentro(String oidCentro);
	
	List<UsuariotDto> getDocentes(String oidCentro);
	
	List<UsuariotDto> getNoDocentes(String oidCentro);
	
	Boolean isDirector(DatosUsuarioJwt datosUsuario);
	
	List<UsuariotDto> getCandidatosCentro(String oidCentro);
	
	Boolean postSeleccionadosCentro(List<UsuariotBuzonDto> seleccionados, DatosUsuarioJwt datosUsuario);
	
	Boolean deleteSeleccionadosCentro(List<UsuariotDto> seleccionados);
	
	Boolean updateFechaVigencia(Long oidPersona, String fechaBaja, String fechaAlta);
	
	List<BuzonCentro> getFechasVigencia(List<Long> oidPersonasAccesoBuzon);
	
	Boolean updateFechaVigencia(BuzonCentro buzonCentroActualizado);

}
