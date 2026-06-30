package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Getter;

/**
 * @author fjluque
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
@Getter
public enum EstadoRegistro {
    
    USUARIO_NUEVO (1, "Usuario nuevo"),
    USUARIO_EXISTENTE(2, "Usuario ya existente");
	
	private int id;
	private String descripcion;
    
     EstadoRegistro(int id, String desc) {
       this.id=id;
       this.descripcion=desc;
    }
     

}
