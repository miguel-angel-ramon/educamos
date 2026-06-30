package es.jccm.edu.shared.application.domain.datosUsuarioJwt;

import java.io.Serializable;

import lombok.Data;

@Data
public class DatosFreshServiceJwt implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String nif;

    private Long perfil;

    private String codigo;

    private String telefono;

    private String correo;

}
