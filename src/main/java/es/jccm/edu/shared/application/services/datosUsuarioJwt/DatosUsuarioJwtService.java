package es.jccm.edu.shared.application.services.datosUsuarioJwt;

import es.jccm.edu.shared.adapter.out.repositories.datosUsuarioJwt.DatosUsuarioJwtRepository;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.AplicacionUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosFreshServiceJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.NombreUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.RolUsuarioJwt;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection.DatosUsuarioJwtProjection;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatosUsuarioJwtService implements IDatosUsuarioJwtService {

	@Autowired
	private DatosUsuarioJwtRepository datosUsuarioJwtRepository;

	@Autowired
	private ModelMapper modelMapper;

	public DatosUsuarioJwt getDatosUsuarioByJwt(String jwt) {

        String[] jwtChunks = jwt.split(" ")[1].split("\\.");
        JSONObject myJsonObj = new JSONObject((new String(Base64.getUrlDecoder().decode(jwtChunks[1]))));

        DatosUsuarioJwt datosUsuarioJwt = new DatosUsuarioJwt();

        if (!myJsonObj.isNull("idUsuarioDelphos")) datosUsuarioJwt.setXUsuarioDelphos(myJsonObj.getLong("idUsuarioDelphos"));
        if (!myJsonObj.isNull("idUsuarioComunica")) datosUsuarioJwt.setXUsuarioComunica(myJsonObj.getLong("idUsuarioComunica"));
        if (!myJsonObj.isNull("usuarioDelphos")) datosUsuarioJwt.setUsuarioDelphos(myJsonObj.getString("usuarioDelphos"));
        if (!myJsonObj.isNull("usuarioComunica")) datosUsuarioJwt.setUsuarioComunica(myJsonObj.getString("usuarioComunica"));
        if (!myJsonObj.isNull("idEmpleadoDelphos")) datosUsuarioJwt.setIdEmpleadoDelphos(myJsonObj.getLong("idEmpleadoDelphos"));
        if (!myJsonObj.isNull("idEmpleadoComunica")) datosUsuarioJwt.setIdEmpleadoComunica(myJsonObj.getLong("idEmpleadoComunica"));
        if (!myJsonObj.isNull("nif")) datosUsuarioJwt.setNif(myJsonObj.getString("nif"));
        if (!myJsonObj.isNull("email")) datosUsuarioJwt.setEmail(myJsonObj.getString("email"));
        if (!myJsonObj.isNull("given_name")) datosUsuarioJwt.setNombre(myJsonObj.getString("given_name"));
        if (!myJsonObj.isNull("family_name")) datosUsuarioJwt.setApellidos(myJsonObj.getString("family_name"));
        if (!myJsonObj.isNull("oid_username")) {
        	Long oid = null;
        	oid = myJsonObj.optLong("oid_username") != 0 ? myJsonObj.optLong("oid_username") : myJsonObj.optLong("oid"); 
        	datosUsuarioJwt.setOid(oid);
        } 
        
        
        return datosUsuarioJwt;

    }

    public DatosUsuarioJwt getDatosUsuarioByJwtUsingRepository(String jwt) {

        String oidUsuario = getOidUsuarioFromJwt(jwt);

        DatosUsuarioJwt datosUsuario = modelMapper.map(datosUsuarioJwtRepository.getDatosUsuarioByJwt(oidUsuario), DatosUsuarioJwt.class);

        return datosUsuario;

	}
    
    public DatosUsuarioJwt getDatosUsuarioByOidUsuarioUsingRepository(Long oidUsuario) {
    	
    	List<DatosUsuarioJwtProjection> datosUsuarioList = datosUsuarioJwtRepository.getDatosUsuarioByJwtList(oidUsuario.toString());

        DatosUsuarioJwt datosUsuario = modelMapper.map(datosUsuarioList.get(0), DatosUsuarioJwt.class);

        //FIXME: Controlar si es nulo o no
        
        return datosUsuario;

	}

	public DatosUsuarioJwt getDatosUsuarioByOidUsuario(String oidUsuario) {

		DatosUsuarioJwt datosUsuario = modelMapper.map(datosUsuarioJwtRepository.getDatosUsuarioByJwt(oidUsuario),
				DatosUsuarioJwt.class);

		return datosUsuario;

	}

	@Transactional(readOnly = true)
	public List<AplicacionUsuarioJwt> getAplicacionesUsuario(String oidUsuario) {
		return datosUsuarioJwtRepository.getAplicacionesUsuario(oidUsuario).stream()
				.map(aplicacionUsuario -> modelMapper.map(aplicacionUsuario, AplicacionUsuarioJwt.class))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<RolUsuarioJwt> getRolesUsuario(String oidUsuario, String nif) {
		return datosUsuarioJwtRepository.getRolesUsuario(oidUsuario, nif).stream()
				.map(rolUsuario -> modelMapper.map(rolUsuario, RolUsuarioJwt.class)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public boolean hasSimulacionRole(String jwt) {

		String oidUsuario = getOidUsuarioSimulacionFromJWT(jwt);

		DatosUsuarioJwtProjection datosUsuarioJwtProjection = datosUsuarioJwtRepository
				.getDatosUsuarioByJwt(oidUsuario);

		if (datosUsuarioJwtProjection == null) {
			return false;
		}

		DatosUsuarioJwt datosUsuario = modelMapper.map(datosUsuarioJwtProjection, DatosUsuarioJwt.class);

		if (datosUsuario == null || datosUsuario.getNif() == null) {
			return false;
		}

		return datosUsuarioJwtRepository.canUsuarioSimulate(oidUsuario, datosUsuario.getNif());
	}

	@Override
	public NombreUsuarioJwt getUsuarioNombre(String oidUsuario) {
		return modelMapper.map(datosUsuarioJwtRepository.getNombreUsuario(oidUsuario), NombreUsuarioJwt.class);
	}

	private String getOidUsuarioFromJwt(String jwt) {
		String[] chunks = jwt.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JSONObject myJsonObj = new JSONObject(payload);
		return String.valueOf(myJsonObj.getLong("oid_username"));
	}
	
	private String getOidUsuarioSimulacionFromJWT(String jwt) {
	
		String id = null;
		String[] chunks = jwt.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JSONObject myJsonObj = new JSONObject(payload);
		id = String.valueOf(myJsonObj.getLong("oid_username"));

		if(myJsonObj.has("usuarioSimulador")) {
			JSONObject usuarioSimulador = myJsonObj.getJSONObject("usuarioSimulador");
			id = String.valueOf(usuarioSimulador.getLong("oid_username"));		
		}
		
		return id;
	}

	@Override
	public List<DatosFreshServiceJwt> getDatosFreshService(Long oidUsuario) {
		return datosUsuarioJwtRepository.getDatosFreshService(oidUsuario).stream()
				.map(datosFresService -> modelMapper.map(datosFresService, DatosFreshServiceJwt.class))
				.collect(Collectors.toList());
	}

    public Long getSecurityLevel(String oidUsuario) {

		return datosUsuarioJwtRepository.getSecurityLevel(oidUsuario);

	}
    
    public Long getSecurityLevelExcepcion(String oidUsuario) {
        boolean existeExcepcionValida = datosUsuarioJwtRepository.existeExcepcionActiva(oidUsuario);
        return existeExcepcionValida ? 1L : null;
    }
    
    public Long getSecurityLevelEmpleado(String idEmpleado) {
        boolean tienePuestoActivo = datosUsuarioJwtRepository.tienePuestoActivoEnCentros(idEmpleado);
        return tienePuestoActivo ? 2L : 1L;
    }
}
