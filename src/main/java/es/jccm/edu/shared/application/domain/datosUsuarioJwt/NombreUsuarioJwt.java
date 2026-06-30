package es.jccm.edu.shared.application.domain.datosUsuarioJwt;

import lombok.Data;

import java.io.Serializable;

@Data
public class NombreUsuarioJwt implements Serializable {

    private String nombre;

    private String apellido;

    private String correo;

}
