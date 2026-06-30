package es.jccm.edu.gestionidentidades.application.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.cstic.marte.util.ldapwsclient.LdapCliente;
import es.jccm.cstic.marte.util.ldapwsclient.jaxws.Atributo;
import es.jccm.cstic.marte.util.ldapwsclient.jaxws.ModificacionAtributosRequest;
import es.jccm.cstic.marte.util.ldapwsclient.jaxws.Nuevousuario;
import es.jccm.cstic.marte.util.ldapwsclient.jaxws.Nuevousuariorespuesta;
import es.jccm.edu.gestionidentidades.adapter.out.repository.Ldap;
import es.jccm.edu.gestionidentidades.application.domain.AltaLdapRequest;
import es.jccm.edu.gestionidentidades.application.domain.AltaLdapResponse;
import es.jccm.edu.gestionidentidades.application.domain.TipoPersonal;


@Transactional
@Service
public class AltaLdapService {
	
	private static final String CODIGO_CONSEJERIA = "18";
	private static final String CODIGO_DG_PROFESOR = "02";
	private static final String CODIGO_SERVICIO_PROFESOR = "03";
	private static final String CODIGO_DG_NO_DOCENTE = "14";
	private static final String CODIGO_SERVICIO_NO_DOCENTE = "10";
	
	@Autowired
	private Ldap ldap;
	
	@Autowired
	private LdapCliente ldapCliente;
	
	/** Log de la clase. */
	private static Log log = LogFactory.getLog(AltaLdapService.class);
	
	
	/**
	 * Alta en LDAP e insercion usuariosT
	 * @param oid
	 * @param dni
	 * @param tipide
	 * @param centro
	 * @param correoRecuperacion
	 * @param esProfesor
	 * @param data.eje
	 * @throws BDException
	 */
	public AltaLdapResponse altaLDAP( AltaLdapRequest request ) {	
		try {
			AltaLdapResponse usuarioSincronizadoEnLdap = this.altaEnLdapConPosibleException(request);

			return usuarioSincronizadoEnLdap;


		} catch (Exception e) {
			log.error("Error al sincronizar usuario con Modulo de Acceso con identificador["+ request.getDocumento()+"]", e);
			return null;
		}		
	}
	
	
	/**
	 * inserta en el ldap el usuario. Si ya está, solo pone el correo externo
	 * @param personaId
	 * @param empleadoDelphos
	 * @param esProfesor
	 * @param correoExterno
	 * @return
	 */
	private AltaLdapResponse altaEnLdapConPosibleException( AltaLdapRequest request ) {
		Nuevousuariorespuesta nuevoUsuariosRespuesta;
		
		String codConsejeria = CODIGO_CONSEJERIA;
		String codDg = "";
		String codServicio = "";
		
		if (request.getTipoPersonal() == TipoPersonal.NO_DOCENTE)  {			
			codDg = CODIGO_DG_NO_DOCENTE;
			codServicio = CODIGO_SERVICIO_NO_DOCENTE; 
		} else {			
			codDg = CODIGO_DG_PROFESOR;
			codServicio = CODIGO_SERVICIO_PROFESOR;			
		}

		Nuevousuario usuario = new Nuevousuario();
		
			usuario.setCodigoConsejeria(codConsejeria);
			usuario.setCodigoDg(codDg);
			usuario.setCodigioServicio(codServicio);        		
			usuario.setDni(request.getDocumento());
			usuario.setNombre(request.getNombre());
			usuario.setApellidos(request.getApellido1() + " "+ request.getApellido2());
			usuario.setSufijoEmail(request.getSufijoEmail());
			usuario.setGenerarCorreo("true");
			usuario.setOculto("false"); 			
			
		nuevoUsuariosRespuesta = ldapCliente.altaUsuario(usuario);
		

		//Control para ver si al dar de alta en ldap, ese dni ya existia
		//En tal caso, se obtiene el uid del ldap
		if(nuevoUsuariosRespuesta != null && "false".equals(nuevoUsuariosRespuesta.getCorrecto())) {
			if("El DNI indicado ya existe para otro usuario".equals(nuevoUsuariosRespuesta.getErrdescrip())){

				nuevoUsuariosRespuesta.setUid(ldap.getAtributoLDAP(request.getDocumento(), "uid"));
				nuevoUsuariosRespuesta.setEmail(ldap.getAtributoLDAP(request.getDocumento(), "mail"));
				
			}
		}
		String uid = nuevoUsuariosRespuesta.getUid();
		
		if(uidVacio(uid)) {
			return null;
		}

		if(request.getCorreoRecuperacion()!=null) {
  			actualizarCorreoExternoParaUid(uid, request.getCorreoRecuperacion());
		}
	
		return AltaLdapResponse.builder()
				.uid(nuevoUsuariosRespuesta.getUid())
				.email(nuevoUsuariosRespuesta.getEmail())
				.build();
	}

	private boolean uidVacio(String uid) {
		return uid==null || uid.equals("");
	}
	
	private void actualizarCorreoExternoParaUid(String uid, String tCorreoExterno) {
		ModificacionAtributosRequest request = new ModificacionAtributosRequest();
		request.setUid(uid);
		Atributo attr = new Atributo();
		attr.setNombre("mailExternalAddress");
		attr.getValores().add(tCorreoExterno);
		request.getAtributos().add(attr);
		
		ldapCliente.modificarUsuario(request);
	}

}
