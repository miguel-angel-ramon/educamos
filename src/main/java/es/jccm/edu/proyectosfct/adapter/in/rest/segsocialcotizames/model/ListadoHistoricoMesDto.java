package es.jccm.edu.proyectosfct.adapter.in.rest.segsocialcotizames.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Schema(name = "ListadoHistoricoMesDto", description = "Listado que muestra el histórico de la cotización mensual de un alumno tanto de programa como de proyecto")
public class ListadoHistoricoMesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "idCotizaMes")
    private Long idCotizaMes;

    @Schema(name = "nombreCompleto")
    private String nombreCompleto;

    @Schema(name = "dsWarnings")
    private String dsWarnings;

    @Schema(name = "NU_DIAS_REAL")
    private Integer nuDiasRealMesProg;

    @Schema(name = "NU_DIAS_INTE")
    private Integer nuDiasInteMesProg;

    @Schema(name = "NU_DIAS_NACU")
    private Integer nuDiasNacuMesProg;

    @Schema(name = "NU_DIAS_INTE_ERA")
    private Integer nuDiasInteEraMesProg;

    @Schema(name = "fechaEnvio")
    private String fechaEnvio;

    @Schema(name = "nuPeticion")
    private String nuPeticion;

    @Schema(name = "accion")
    private String accion;

    @Schema(name = "tipo")
    private String tipo;
    
    @Schema (name = "mes")
	private String mes;

}
