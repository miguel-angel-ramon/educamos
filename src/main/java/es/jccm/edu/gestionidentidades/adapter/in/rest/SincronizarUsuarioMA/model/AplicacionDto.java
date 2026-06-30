package es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AplicacionDto implements Serializable {

    private Long id;

    private String codigo;

    private String nombre;

    private Character activa;

    private String motivoDesactivacion;

    private String informacion;

    private String nombre2;

}
