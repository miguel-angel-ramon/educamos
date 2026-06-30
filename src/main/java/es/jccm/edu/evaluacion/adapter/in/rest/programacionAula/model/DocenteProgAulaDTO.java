package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "DocenteProgAulaDTO", description = "DTO para la pantalla de preseleccion de prog. aula - direccion")
public class DocenteProgAulaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id del empleado")
    private Long idEmpleado;

    @Schema(description = "Nombre del empledao")
    private String nombre;

}
