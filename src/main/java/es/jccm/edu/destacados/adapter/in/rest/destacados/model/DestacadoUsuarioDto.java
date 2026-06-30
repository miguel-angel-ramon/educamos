package es.jccm.edu.destacados.adapter.in.rest.destacados.model;

import lombok.Data;

import java.io.Serializable;

//TODO Schema
@Data
public class DestacadoUsuarioDto implements Serializable {

    private Long id;

    private DestacadoDto destacado;

    private Long oidUsuario;

    private Long numeroOrden;

    private Boolean activo;

}
