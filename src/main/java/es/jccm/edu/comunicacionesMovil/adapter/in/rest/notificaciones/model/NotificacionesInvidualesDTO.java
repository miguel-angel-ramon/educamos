package es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionesInvidualesDTO {

    private String titulo;
    private String mensaje;
    private String foto;
    private Long xUsuario;


}
