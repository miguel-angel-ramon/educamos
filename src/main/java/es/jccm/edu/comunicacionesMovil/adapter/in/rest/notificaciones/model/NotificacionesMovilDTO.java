package es.jccm.edu.comunicacionesMovil.adapter.in.rest.notificaciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionesMovilDTO {

    private String titulo;
    private String mensaje;
    private String foto;


}
