package es.jccm.edu.destacados.adapter.in.rest.destacados.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "Destacado", description = "Destacados rescatados de la BBDD del modulo de acceso")
public class DestacadoDto implements Serializable {

    @Schema(description = "Id del destacado")
    Long id;

    @Schema(description = "Código del destacado")
    String codigo;

    @Schema(description = "Id del perfil al que pertenece el destacado")
    Long idPerfil;

    @Schema(description = "Código del perfil")
    String codigoPerfil;

    @Schema(description = "Código de la aplicación de la que proviene el perfil")
    String codigoAplicacion;

    @Schema(description = "Nombre del destacado")
    String nombre;

    @Schema(description = "URL de la aplicación de redirección")
    String url;

    @Schema(description = "Número de página de la aplicación de redirección")
    Long numeroPagina;

    @Schema(description = "Posición del destacado")
    Long numeroOrden;

    @Schema(description = "Indica si orden preferente está habilitado para posionarlo")
    Boolean ordenPreferente;

    @Schema(description = "Fecha inicio vigencia del destacado")
    Date fechaInicio;

    @Schema(description = "Fecha fin vigencia del destacado")
    Date fechaFin;

    @Schema(description = "Indica si el destacado está activo")
    Boolean activo;

}
