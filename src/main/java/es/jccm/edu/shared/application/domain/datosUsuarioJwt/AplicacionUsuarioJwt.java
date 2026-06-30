package es.jccm.edu.shared.application.domain.datosUsuarioJwt;

import lombok.Data;

import java.io.Serializable;

@Data
public class AplicacionUsuarioJwt implements Serializable {

    private Long oidAplicacion;

    private String codigo;

    /*private String nombre;

    private String informacion;

    private String nombreAlternativo;*/

}
