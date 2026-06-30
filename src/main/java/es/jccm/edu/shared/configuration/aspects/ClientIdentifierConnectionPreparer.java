package es.jccm.edu.shared.configuration.aspects;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Este componente se encarga de meter el usuario en sesión en todas las conexiones contra oracle.
 * En las tablas de oracle hay ciertos triggers que recogen el usuario conectado de la sesión de la conexión
 * y con ese usuario meten las columnas de auditoria en las tablas.
 * 
 * @author jesus
 *
 */

@Component
@Aspect
@Slf4j
@ConditionalOnProperty(name="educamosclm.featureinyectasesionoracle", havingValue="true")
public class ClientIdentifierConnectionPreparer  {

	
	private Long getOidDeUsuarioDesdeTokenJwt() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth==null) {
			return null;
		}
 
		Object principal = auth.getPrincipal();
		if(principal==null) {
			return null;
		}

		if(!(principal instanceof Jwt)) {
			return null;
		}
		
		
		Jwt jwt=(Jwt)principal;
				
		String id;
		if(jwt.getClaim("oid_username")==null) {
			return null;
		}
		
		id = jwt.getClaim("oid_username").toString();
		
		try {
			
			return Long.parseLong(id);
		} catch (NumberFormatException e) {
			log.error("no se pudo parsear string de oid a long "+ id);
			return null;
		}
	}
	
  @AfterReturning(pointcut = "execution(* *.getConnection(..))", returning = "connection")
  public Connection prepare(Connection connection) throws SQLException  {
	    Long webAppUser = getOidDeUsuarioDesdeTokenJwt();//from Spring Security Context or wherever;
	    if(webAppUser==null) {
	    	log.debug("no se pudo obtener usuario desde token para establecer la sesión oracle");
	    	return connection; //si no se pudo recuperar el usuario, los triggers se encargan de poner el usuario -99901
	    }
    	log.debug("inyectando usuario de sesión en oracle {}",webAppUser);

	    CallableStatement cs = connection.prepareCall(
	            //"{ call DBMS_SESSION.SET_IDENTIFIER(?) }"); 
	    		//cs.setString(1, webAppUser);
	    		// esta sería la forma "estandar" de oracle (tiene un paquete para ello), pero en delphos usa este otro:....
	    		
	    		"{ call DELPHOS.TLPQ_DATOSSESION.tlp_setUsuario(?) }");
	    
	    cs.setLong(1, webAppUser);
	    cs.execute();
	    cs.close();

	    return connection;
	  }

}