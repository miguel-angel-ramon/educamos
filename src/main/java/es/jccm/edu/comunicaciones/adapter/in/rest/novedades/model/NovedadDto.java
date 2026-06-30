package es.jccm.edu.comunicaciones.adapter.in.rest.novedades.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "Novedad", description = "Novedades rescatadas de la BBDD de comunica")
public class NovedadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id de la novedad")
    private Long idNovedad;

    @Schema(description = "Título")
    private String titulo;

    @Schema(description = "Descripción")
    private String descripcion;

    @Schema(description = "Fecha de generación")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fechaGeneracion;

}
