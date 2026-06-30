package es.jccm.edu.gestionidentidades.application.services;

import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.gestionidentidades.application.domain.DatosUsuarioLdap;
import es.jccm.edu.gestionidentidades.application.ports.in.IGetUsuarioLdapService;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.UsuarioLDapException;
import es.jccm.edu.shared.configuration.ldap.LdapConnectionConfiguration;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class GetUserLdapService implements IGetUsuarioLdapService {

	@Autowired
	private LdapConnectionConfiguration ldapConnectionConfiguration;

	/*@Override
	public UsuarioLdap getDatosLdapByNif(String c_numide) throws UsuarioLDapException {
		Hashtable<String, String> env = new Hashtable<>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, ldapConnectionConfiguration.getInitialcontextfactory());
		env.put(Context.PROVIDER_URL, ldapConnectionConfiguration.getUrl());
		env.put(Context.SECURITY_AUTHENTICATION, ldapConnectionConfiguration.getSecurityauthentication());
		env.put(Context.SECURITY_PRINCIPAL, ldapConnectionConfiguration.getPrincipal());
		env.put(Context.SECURITY_CREDENTIALS, ldapConnectionConfiguration.getCredentials());

		UsuarioLdap usuario = new UsuarioLdap();
		PersonaLdap persona = new PersonaLdap();

		DirContext ctx;
		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			log.error("Error en la inicialización de la dirección del contexto. Datos del error: " + e.toString(true));
			throw new UsuarioLDapException(e.toString(true));

		}

		NamingEnumeration results = null;
		try {
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			String filter = "(us-nif={0})";
			
			results = ctx.search("o=Junta de Castilla-La Mancha,c=ES", filter, new String[] {c_numide}, controls);
			//results = ctx.search("o=Junta de Castilla-La Mancha,c=ES", "(us-nif=" + cNumide + ")", controls);
			while (results.hasMore()) {
				SearchResult searchResult = (SearchResult) results.next();
				Attributes attributes = searchResult.getAttributes();

				Attribute atributoNombre = attributes.get("givenName");
				Attribute atributoApellido = attributes.get("sn");
				Attribute atributoCnumide = attributes.get("us-nif");
				Attribute atributoUid = attributes.get("uid");

				usuario.setId_persona(persona);
				usuario.getId_persona()
						.setT_nombre(atributoNombre.get() != null ? atributoNombre.get().toString() : null);
				usuario.getId_persona()
						.setT_apellidos(atributoApellido.get() != null ? atributoApellido.get().toString() : null);
				usuario.getId_persona()
						.setT_nombre_completo((atributoNombre.get() != null ? atributoNombre.get().toString() : null)
							+" "+ (atributoApellido.get() != null ? atributoApellido.get().toString() : null));
				usuario.getId_persona()
						.setC_numide(atributoCnumide.get() != null ? atributoCnumide.get().toString() : null);
				usuario.setT_usuario(atributoUid.get() != null ? atributoUid.get().toString() : null);

			}
		} catch (NameNotFoundException e) {
			log.error("Error en LDapConnector al no encontrar o resolver el nombre. Datos del error: "
					+ e.toString(true));
			throw new UsuarioLDapException(e.toString(true));

		} catch (NamingException e) {
			log.error("Error en la dirección del contexto con el nombre de la subclase. Datos del error: "
					+ e.toString(true));
			throw new UsuarioLDapException(e.toString(true));
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (Exception e) {
					log.error("Excepcion en LDapConnector. Datos del error: " + e.toString());
					throw new UsuarioLDapException(e.toString());
				}
			}
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					log.error("Excepcion en LDapConnector. Datos del error: " + e.toString());
					throw new UsuarioLDapException(e.toString());
				}
			}
		}
		return usuario;

	}*/
	
	@Override
	public DatosUsuarioLdap getDatosLdapByUid(String uid) throws UsuarioLDapException {
		Hashtable<String, String> env = new Hashtable<>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, ldapConnectionConfiguration.getInitialcontextfactory());
		env.put(Context.PROVIDER_URL, ldapConnectionConfiguration.getUrl());
		env.put(Context.SECURITY_AUTHENTICATION, ldapConnectionConfiguration.getSecurityauthentication());
		env.put(Context.SECURITY_PRINCIPAL, ldapConnectionConfiguration.getPrincipal());
		env.put(Context.SECURITY_CREDENTIALS, ldapConnectionConfiguration.getCredentials());

		DatosUsuarioLdap usuario = new DatosUsuarioLdap();
		//PersonaLdap persona = new PersonaLdap();

		DirContext ctx;
		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			log.error("Error en la inicialización de la dirección del contexto. Datos del error: " + e.toString(true));
			throw new UsuarioLDapException(e.toString(true));

		}

		NamingEnumeration results = null;
		try {
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			String filter = "(uid={0})";
			
			results = ctx.search("o=Junta de Castilla-La Mancha,c=ES", filter, new String[] {uid}, controls);
			//results = ctx.search("o=Junta de Castilla-La Mancha,c=ES", "(us-nif=" + cNumide + ")", controls);
			while (results.hasMore()) {
				SearchResult searchResult = (SearchResult) results.next();
				Attributes attributes = searchResult.getAttributes();

				Attribute atributoNombre = attributes.get("givenName");
				Attribute atributoApellido = attributes.get("sn");
				Attribute atributoCnumide = attributes.get("us-nif");
				Attribute atributoUid = attributes.get("uid");
				Attribute atributoCorreo = attributes.get("mail");
				
				usuario.setAtributoNombre(atributoNombre.toString());
				usuario.setAtributoUid(atributoUid.toString());
				usuario.setAtributoApellido(atributoApellido.toString());
				usuario.setAtributoCnumide(atributoCnumide.toString());
				usuario.setCorreo(atributoCorreo.toString());

			}
		} catch (NameNotFoundException e) {
			log.error("Error en LDapConnector al no encontrar o resolver el nombre. Datos del error: "
					+ e.toString(true));
			throw new UsuarioLDapException(e.toString(true));

		} catch (NamingException e) {
			log.error("Error en la dirección del contexto con el nombre de la subclase. Datos del error: "
					+ e.toString(true));
			throw new UsuarioLDapException(e.toString(true));
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (Exception e) {
					log.error("Excepcion en LDapConnector. Datos del error: " + e.toString());
					throw new UsuarioLDapException(e.toString());
				}
			}
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					log.error("Excepcion en LDapConnector. Datos del error: " + e.toString());
					throw new UsuarioLDapException(e.toString());
				}
			}
		}
		return usuario;

	}
	
}
