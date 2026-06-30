package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "MiembrosColectivoDto", description = "DTO para representar los datos del colectivo")
public class MiembrosColectivoDto {
	
	 @Schema(description = "Id del colectivo")
	  private  Long xColectivo;

    @Schema(description = "Unidad del centro de recursos")
    private Long xUnidadResCen;

    @Schema(description = "Identificador AMPA del centro")
    private Long xAmpcen;

    @Schema(description = "Código del centro")
    private String codCentro;

    @Schema(description = "Fecha de toma de posesión")
    private String fTomaPos;

    @Schema(description = "Identificador del centro")
    private Long xCentro;

    @Schema(description = "Identificador del empleado")
    private Long xEmpleado;

    @Schema(description = "Perfil del usuario")
    private Long xPerfil;

    @Schema(description = "Identificador del usuario")
    private Long xUsuario;

    @Schema(description = "Identificador del usuario que realiza la operación")
    private Long xUsuOperacion;

    @Schema(description = "Identificador de la matrícula del alumno")
    private Long xMatAlu;

    @Schema(description = "Identificador de la unidad")
    private Long xUnidad;

    @Schema(description = "Grupo de actividad programada seleccionado")
    private Long xGruactproaluSelec;

    @Schema(description = "Grupo de actividad programada del usuario")
    private Long xGruactproaluUsu;

    @Schema(description = "Identificador AMPA de la unidad de recursos del AMPA")
    private Long xAmpcenResAmpa;

    @Schema(description = "Año académico")
    private Integer cAnno;

    @Schema(description = "Identificador del departamento del centro")
    private Long xDepartcen;

    @Schema(description = "Identificador de la unidad tutorial del alumno")
    private Long xUnidadTutorAlu;
}
