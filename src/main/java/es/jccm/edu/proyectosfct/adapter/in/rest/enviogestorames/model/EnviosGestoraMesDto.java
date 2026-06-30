package es.jccm.edu.proyectosfct.adapter.in.rest.enviogestorames.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "EnviosGestoraMesDto", description = "Tabla que almacena y controla el uso del botón para consultar datos de la gestora mensual")
public class EnviosGestoraMesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "fechaEnvio")
    private Date fechaEnvio;

    @Schema(name = "fechaFin")
    private Date fechaFin;

    @Schema(name = "cdResultado")
    private String cdResultado;

}
