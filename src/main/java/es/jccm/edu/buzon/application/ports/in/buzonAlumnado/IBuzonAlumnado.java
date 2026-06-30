package es.jccm.edu.buzon.application.ports.in.buzonAlumnado;

import java.util.List;

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.AlumnoUnidadDto;
import es.jccm.edu.buzon.adapter.in.rest.buzon.model.CursoSolicitudDto;
import es.jccm.edu.buzon.adapter.in.rest.buzon.model.UsuariotBuzonDto;
import es.jccm.edu.buzon.application.domain.buzon.BuzonAlumnado;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.simulacion.adapter.in.rest.centros.model.UsuariotDto;

public interface IBuzonAlumnado {
	
	Boolean isEquipoDirectivo(DatosUsuarioJwt datosUsuario);
	
	Boolean postSeleccionadosAlumnado(List<UsuariotBuzonDto> seleccionados, DatosUsuarioJwt datosUsuario);
	
	List<BuzonAlumnado> getFechasVigenciaAlumnado(List<Long> oidPersonasAccesoBuzonAlumnado);
	
	List<UsuariotDto> getPersonalAccesoBuzonAlumnado(String oidCentro);
	
	Boolean updateFechaVigencia(Long oidPersona,String fechaBaja,String fechaAlta);
	
	Boolean deleteSeleccionados(List<UsuariotDto> seleccionados);
	
	List<UsuariotDto> getAlumnado(String oidCentro);
	
	List<AlumnoUnidadDto> getAlumnosUnidades(String oidCentro);
	
	List<CursoSolicitudDto> getCursoEtapaAlumnosCentro(String oidCentro);
}
