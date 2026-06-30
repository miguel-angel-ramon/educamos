package es.jccm.edu.simulacion.application.services.usuarios;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.simulacion.adapter.in.rest.usuarios.model.PerfilDefectoUsuarioDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.simulacion.adapter.in.rest.usuarios.model.DocenteSustitutoDTO;
import es.jccm.edu.simulacion.adapter.out.repositories.usuarios.UsuarioRepository;
import es.jccm.edu.simulacion.application.domain.usuarios.CentroUsuarioList;
import es.jccm.edu.simulacion.application.domain.usuarios.CursoAcademico;
import es.jccm.edu.simulacion.application.domain.usuarios.DatosPersonalesUsuario;
import es.jccm.edu.simulacion.application.domain.usuarios.DatosUsuario;
import es.jccm.edu.simulacion.application.domain.usuarios.PerfilUsuarioList;
import es.jccm.edu.simulacion.application.domain.usuarios.UsuarioList;
import es.jccm.edu.simulacion.application.domain.usuarios.projection.CentroUsuarioProjection;
import es.jccm.edu.simulacion.application.domain.usuarios.projection.DatosPersonalesUsuarioProjection;
import es.jccm.edu.simulacion.application.domain.usuarios.projection.PerfilUsuarioProjection;
import es.jccm.edu.simulacion.application.domain.usuarios.projection.UsuarioProjection;
import es.jccm.edu.simulacion.application.domain.usuarios.projection.UsuarioSimuSh;
import es.jccm.edu.simulacion.application.ports.in.usuarios.IUsuariosService;
import es.jccm.edu.simulacion.application.ports.in.usuarios.UsuarioDto;

import javax.transaction.Transactional;

@Service
public class UsuariosService implements IUsuariosService {

	private static final Logger LOG = LogManager.getLogger(UsuariosService.class);

	private final String PERFIL_ALUMNADO = "5000";
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<UsuarioDto> getListadoUsuarios(Long codCentro, Long codPerfil) {
		String mensaje = String.format("Obteniendo del listado de usuarios centro con código =%d y perfil con código = %d de la BBDD de Delphos",codCentro,codPerfil);
	
		LOG.info(mensaje);

		List<UsuarioProjection> usuariosList = usuarioRepository.getAllUsuarios(codCentro, codPerfil);

		return usuariosList.stream().map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
				.collect(Collectors.toList());
	}

	public List<UsuarioList> getListadoUsuariosDelphos(Long codCentro, Long codPerfil) {
		String mensaje = String.format("Obteniendo del listado de usuarios centro con código =%d y perfil con código = %d de la BBDD de Delphos",codCentro,codPerfil);
		LOG.info(mensaje);

		List<UsuarioProjection> usuariosList = usuarioRepository.getAllUsuariosDelphos(codCentro, codPerfil);

		return usuariosList.stream().map(usuario -> modelMapper.map(usuario, UsuarioList.class))
				.collect(Collectors.toList());
	}

	public DatosUsuario getDatosUsuario(String usuarioDelphos, String usurioComunica, Long idEmpleado) {
		String mensaje = String.format("Obteniendo datos del usuario con idUsuario = %s", usuarioDelphos);
		
		LOG.info(mensaje);

		DatosUsuario user = modelMapper.map(usuarioRepository.getDatosUsuario(usuarioDelphos), DatosUsuario.class);
		if (user == null){
			user = modelMapper.map(usuarioRepository.getDatosUsuarioComunica(usurioComunica), DatosUsuario.class);
		}

		user.setCentroAnterior(usuarioRepository.getCentroAnterior(idEmpleado));
		user.setAnyosAnteriores(usuarioRepository.getAnyosAnteriores());

		Blob img = usuarioRepository.getfotoUsuario(user.getNumide());
		try {
			if (img != null) {
				user.setFoto(img.getBytes(1, (int) img.length()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;

	}

	public List<PerfilUsuarioList> getPerfilesUsuario(String idUsuario) {
		String mensaje = String.format("Obteniendo los perfiles del usuario con idUsuario = %s", idUsuario);
		
		LOG.info(mensaje);

		List<PerfilUsuarioProjection> perfilesList = usuarioRepository.getPerfilesUsuario(idUsuario);

		return perfilesList.stream().map(perfil -> modelMapper.map(perfil, PerfilUsuarioList.class))
				.collect(Collectors.toList());
	}

	public List<PerfilUsuarioList> getPerfilesDelphosUsuario(String idUsuario) {
		String mensaje = String.format("Obteniendo los perfiles de Delphos del usuario con idUsuario = %s", idUsuario);
		
		LOG.info(mensaje);

		List<PerfilUsuarioProjection> perfilesList = usuarioRepository.getPerfilesDelphosUsuario(idUsuario);

		return perfilesList.stream().map(perfil -> modelMapper.map(perfil, PerfilUsuarioList.class))
				.collect(Collectors.toList());
	}

	public List<CentroUsuarioList> getCentrosUsuario(String idUsuario) {
		String mensaje = String.format("Obteniendo los centros del usuario con idUsuario = %s", idUsuario);

		LOG.info(mensaje);

		List<CentroUsuarioProjection> centrosList = usuarioRepository.getCentrosUsuario(idUsuario);

		return centrosList.stream().map(perfil -> modelMapper.map(perfil, CentroUsuarioList.class))
				.collect(Collectors.toList());
	}

	public List<CentroUsuarioList> getCentrosDelphosUsuario(String idUsuario) {
		//ESTE METODO USA EL DNI DEL USUARIO
		String mensaje = String.format("Obteniendo los centros de Delphos del usuario con idUsuario = %s", idUsuario);
		
		LOG.info(mensaje);

		List<CentroUsuarioProjection> centrosList = usuarioRepository.getCentrosDelphosUsuario(idUsuario);

		return centrosList.stream().map(perfil -> modelMapper.map(perfil, CentroUsuarioList.class))
				.collect(Collectors.toList());
	}

	public List<CentroUsuarioList> getCentrosDelphosUsuarioByXEmpleado(Long xEmpleado) {
		//ESTE METODO USA EL DNI DEL USUARIO
		String mensaje = String.format("Obteniendo los centros de Delphos del usuario con idUsuario = %s", xEmpleado);

		LOG.info(mensaje);

		List<CentroUsuarioProjection> centrosList = usuarioRepository.getCentrosDelphosUsuarioByXEmpleado(xEmpleado);

		return centrosList.stream().map(perfil -> modelMapper.map(perfil, CentroUsuarioList.class))
				.collect(Collectors.toList());
	}

	/*public DatosPersonalesUsuario getDatosPersonalesUsuario(Long idUsuario) {
		String mensaje = String.format("Obteniendo los datos personales del usuario con idUsuario = %s", idUsuario);
		
		LOG.info(mensaje);

		DatosPersonalesUsuario datosPersonales;

		try {
			datosPersonales = modelMapper.map(usuarioRepository.getDatosPersonalesUsuario(idUsuario),
					DatosPersonalesUsuario.class);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return datosPersonales;
	} */
	
	public DatosPersonalesUsuario getDatosPersonalesUsuario(Long idUsuario, Long oid) {
		String mensaje = String.format("Obteniendo los datos personales del usuario con idUsuario = %s", idUsuario);
		if(idUsuario == null  ) {
			return null;
		}
		LOG.info(mensaje);

		DatosPersonalesUsuario datosPersonales = null;

		try {			
			
			DatosPersonalesUsuarioProjection datosPersonalesUsuario = usuarioRepository.getDatosPersonalesUsuario(idUsuario);
			
			if (datosPersonalesUsuario == null) {
				
				datosPersonalesUsuario = usuarioRepository.getDatosPersonalesUsuarioOid(idUsuario);
			}
			
			if (datosPersonalesUsuario != null) {
				
				datosPersonales = modelMapper.map(datosPersonalesUsuario, DatosPersonalesUsuario.class);
				
			} else {
				
				datosPersonales = new DatosPersonalesUsuario();
			}			 

		} catch (Exception e) {
			LOG.error(mensaje);
			throw e;
		}

		return datosPersonales;
	}

	public CursoAcademico getAnnoActual() {

		LOG.info("Obteniendo el curso academico actual");

		CursoAcademico cursoAcademico;

		try {
			cursoAcademico = modelMapper.map(usuarioRepository.getAnnoActual(), CursoAcademico.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return cursoAcademico;
	}

	public Long getAnnoAacademico() {
		return usuarioRepository.getAnnoAacademico();
	}
	
	public DatosUsuario getDatosUsuarioModAcc(String xPerfil, Long oid) {
		String mensaje = String.format("Obteniendo datos del usuario con oid = %s", oid);
		
		LOG.info(mensaje);
		
		DatosUsuario user = null;

		if (xPerfil.equals(this.PERFIL_ALUMNADO)) {
			
			user = modelMapper.map(usuarioRepository.getDatosUsuarioSeguimiento(oid), DatosUsuario.class);
			
		} else {
			
			user = modelMapper.map(usuarioRepository.getDatosUsuarioDelphos(oid), DatosUsuario.class);
			
		}		
		
		Blob img = usuarioRepository.getfotoUsuario(user.getNumide());
		try {
			if (img != null) {
				user.setFoto(img.getBytes(1, (int) img.length()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;

	}
	
	public List<PerfilUsuarioList> getPerfilesModAccUsuarioFCTDGC(String xPerfil, String xCentro, Long oid) {
		String mensaje = String.format("Obteniendo los perfiles de Delphos del usuario con oid = %s", oid);
		
		LOG.info(mensaje);
		
		List<PerfilUsuarioProjection> perfilesList = null;
		
		if (xPerfil.equals(this.PERFIL_ALUMNADO)) {
			
			perfilesList = usuarioRepository.getPerfilesSeguimientoUsuarioFCTDGC(xPerfil,xCentro,oid);
			
		} else {
			perfilesList = usuarioRepository.getPerfilesDelphosUsuarioFCTDGC(oid);
			
		}		

		return perfilesList.stream().map(perfil -> modelMapper.map(perfil, PerfilUsuarioList.class))
				.collect(Collectors.toList());
	}
	
	public String getPosicionComponentesEscritorio(String dni) {
		String mensaje = String.format("Obteniendo la posición de los componentes del usuario con dni = %s", dni);
		
		LOG.info(mensaje);

		return usuarioRepository.getPosicionComponentesEscritorio(dni);
	}

	@Transactional
	public void setPosicionComponentesEscritorio(String dni, String posicion) {
		String mensaje = String.format("Actualizando la posición de los componentes del usuario con dni = %s", dni);
		
		LOG.info(mensaje);

		usuarioRepository.setPosicionComponentesEscritorio(dni, posicion);
	}
	
	@Transactional
	public void setConfigUsuario(String dni, String options) {
		JSONObject json = new JSONObject(options);  
		String rol = json.get("profile_def").toString();
		String centro = json.get("center_def").toString();
		usuarioRepository.setConfgUsuarioDefault(dni, rol, centro);
	}
	
	@Transactional
	public void setTourUsuario(String dni) {
		usuarioRepository.setTourUsuario(dni);
	}
	
	@Transactional
	public void setTourEvaluacionUsuario(String dni) {
		usuarioRepository.setTourEvaluacionUsuario(dni);
	}
	
    public DatosUsuario getDatosUsuarioDelphos(Long oidUsuario) {
        String mensaje = String.format("Obteniendo datos del usuario con idUsuario = %s", oidUsuario);
		
		LOG.info(mensaje);
        DatosUsuario user = modelMapper.map(usuarioRepository.getDatosUsuarioDelphos(oidUsuario.longValue()), DatosUsuario.class);	
			Blob img = usuarioRepository.getfotoUsuario(user.getNumide());
		try {
			if (img != null) {
				user.setFoto(img.getBytes(1, (int) img.length()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;

	}

	public List<PerfilUsuarioList> getPerfilesDelphosUsuarioFCTDGC(String idUsuario) {
		String mensaje = String.format("Obteniendo los perfiles de Delphos del usuario con idUsuario = %s", idUsuario);
		
		LOG.info(mensaje);

		List<PerfilUsuarioProjection> perfilesList = usuarioRepository.getPerfilesDelphosUsuarioFCTDGC(Long.valueOf(idUsuario).longValue());

		return perfilesList.stream().map(perfil -> modelMapper.map(perfil, PerfilUsuarioList.class))
				.collect(Collectors.toList());
	}
	
	public Boolean isDocenteSustituto(Long idEmpleado, Integer anno) {
		return usuarioRepository.isDocenteSustituto(idEmpleado, anno);
	}

	public List<DocenteSustitutoDTO> getDocentesSustitutos(Long idEmpleado, Integer anno) {
		return usuarioRepository.getDocentesSustitutos(idEmpleado, anno).stream().map(docente -> modelMapper.map(docente, DocenteSustitutoDTO.class)).collect(Collectors.toList());
	}
	
	public List<DocenteSustitutoDTO> getDocentesSustituye(Long idEmpleado, Integer anno) {
		return usuarioRepository.getDocentesSustituidos(idEmpleado, anno).stream().map(docente -> modelMapper.map(docente, DocenteSustitutoDTO.class)).collect(Collectors.toList());
	}

	public PerfilDefectoUsuarioDTO getPerfilDefecto(Long oidUsuario){

		PerfilDefectoUsuarioDTO perfilDefecto = modelMapper.map(usuarioRepository.getPerfilDefecto(oidUsuario), PerfilDefectoUsuarioDTO.class);

		return perfilDefecto;

	}

	@Transactional
	public void resetConfigUsuario(Long oidUsuario) {
		usuarioRepository.resetConfgUsuarioDefault(oidUsuario);
	}

	public Boolean isCoordinadorCicloOrFP(Long idEmpleado, String fechaTomaPosesion) {

		Long isCoord = usuarioRepository.isCoordinadorCicloOrFP(idEmpleado, fechaTomaPosesion);

		Boolean isCoordBoolean = false;

		if (isCoord != null && isCoord == 1)
			isCoordBoolean = true;

		return isCoordBoolean;
	}

	public Boolean isJefeDepartamento(Long idEmpleado, String fechaTomaPosesion) {

		Long isJefe = usuarioRepository.isJefeDepartamento(idEmpleado, fechaTomaPosesion);

		Boolean isJefeBoolean = false;

		if (isJefe != null && isJefe == 1)
			isJefeBoolean = true;

		return isJefeBoolean;
	}

	public Long isInspector(Long idEmpleado) {
		Long inspector =  usuarioRepository.isInspector(idEmpleado);
		return inspector;
	}

	public Boolean getAccesoShellProfesorYDirector(Long idUsuarioModAcc) {
		return usuarioRepository.getAccesoShellProfesorYDirector(idUsuarioModAcc);
	}

	public Long getDirectorReport(Long idCentro) {
		return usuarioRepository.getDirectorReport(idCentro);
	}
	
	public List<UsuarioSimuSh> getListadoUsuariosSimulacionSh(Long codCentro,String codRol) {
		return usuarioRepository.getUsuariosSh(codCentro,codRol);
	}

	public List<Long> centrosAnteriores(Long idEmpleado) {
		return usuarioRepository.getCentroAnterior(idEmpleado);
	}

	public List<Long> anyosAnteriores() {
		return usuarioRepository.getAnyosAnteriores();
	}


}
