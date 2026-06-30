package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CredencialesAcceso implements Serializable {
    
    private String login;
    
    private Clave clave;
    
    private boolean soloUnUso;
    
    public String toString() {
        StringBuffer sBuff = new StringBuffer();
        sBuff.append("Login: ").append(login);
        sBuff.append(" Clave: ").append(this.clave.getClave());
        sBuff.append(" SoloUso: ").append(this.soloUnUso);
        
        return sBuff.toString();
    }

}

