package es.jccm.edu.gestionidentidades.adapter.out.repository;

import static java.text.MessageFormat.format;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import es.jccm.cstic.marte.util.ldapwsclient.LdapCliente;
import es.jccm.cstic.marte.util.ldapwsclient.jaxws.Atributo;
import es.jccm.cstic.marte.util.ldapwsclient.jaxws.ModificacionAtributosRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LdapRepository implements Ldap{


	@Autowired
	private LdapTemplate template;
	
	@Autowired
	private LdapCliente ldapCliente;
	
	/**
	 * 
	 */
	@Override
	public String getAtributoLDAP(String usNif, String atribLdap) {
		
		try {
		    
		    List<String> valores = template.search("", format("{0}={1}", "us-nif", usNif), (AttributesMapper<String>) attrs -> (String) attrs.get(atribLdap).get());
		    return valores.size()==1?valores.get(0):"";
		} catch (Exception e) {
	 	   log.error("Error inesperado al recuperar datos de ldap",e);
		}
		
		return ""; 	
  }

	/**
	 * Servicio que modifica un atributo en el directorio LDAP.
	 *
	 * @param uidLdap Identificador del usuario en el directorio LDAP
	 * @param atribLdap Tipo de atributo solicitado por elservicio
	 * @param vAtribLdap Valor del atributo modificado
	 */	
	@Override
	public void setAtributoLDAP(String cNumide, String atribLdap, String vAtribLdap) {
		
    	
		String uid = this.getAtributoLDAP(cNumide, "uid");
		
		if (uid != null && !uid.equals("")) {
			
  			ModificacionAtributosRequest request = new ModificacionAtributosRequest();
  			request.setUid(uid);
  			Atributo attr = new Atributo();
  			attr.setNombre(atribLdap);
  			attr.getValores().add(vAtribLdap);
  			request.getAtributos().add(attr);
  			
  			ldapCliente.modificarUsuario(request);	
			
		}
  }

}
