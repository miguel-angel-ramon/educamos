package es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model;

import es.jccm.edu.gestionidentidades.application.domain.Sexo;
import lombok.Data;

import java.util.Date;

@Data
public class PersonaDto {

    private Long id;

    private String nombre;

    private Sexo sexo;

    private String apellido1;

    private String apellido2;

    private Date fechaNacimiento;

}
