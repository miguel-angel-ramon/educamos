package es.jccm.edu.horarios.adapter.in.rest.dependencias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Dependencia", description = "Entidad para rescatar las dependencias de un centro")
public class DependenciaLibreListDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador del tramo")
    private Long idTramo;

    @Schema(description = "Identificador de la dependencia")
    private Long idDependencia;

    @Schema(description = "Dependencia")
    private String dependencia;

    @Schema(description = "Tipo")
    private String tipo;

    @Schema(description = "Dimension")
    private Long dimension;

    @Schema(description = "Capacitada")
    private Boolean capacitada;

    @Schema(description = "Inicio del tramo")
    private String inicioTramo;

    @Schema(description = "Fin del tramo")
    private String finTramo;

}
