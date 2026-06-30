package es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "ListadoHistoricoAltasDto", description = "Listado que muestra el histórico del alta de la SS de un alumno tanto de programa como de proyecto")
public class ListadoHistoricoAltasDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "idAltaSS")
    private Long idAltaSS;

    @Schema(name = "nombreCompleto")
    private String nombreCompleto;

    @Schema(name = "fechaInicio")
    private Date fechaInicio;

    @Schema(name = "fechaFin")
    private Date fechaFin;

    @Schema(name = "dsWarnings")
    private String dsWarnings;

    @Schema(name = "erasmusCb")
    private String erasmusCb;

    @Schema(name = "erasmusSb")
    private String erasmusSb;

    @Schema(name = "anulado")
    private String anulado;

    @Schema(name = "fechaEnvio")
    private String fechaEnvio;

    @Schema(name = "tipoEmpresa")
    private String tipoEmpresa;

    @Schema(name = "accion")
    private String accion;

    @Schema(name = "nuPeticion")
    private String nuPeticion;

    @Schema(name = "estadoRegistro")
    private String estadoRegistro;

    @Schema(name = "lgFile")
    private Integer lgFile;

    @Schema(name = "lgData")
    private Integer lgData;

    @Schema(name = "idGestora")
    private Integer idGestora;

    @Schema(name = "idInternoAlta")
    private Long idInternoAlta;
}