package es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "InfoParteSemanalDto", description = "Información sobre el parte semanal del alumno en el plan")
public class InfoParteSemanalDto implements Serializable {
	
	 private static final long serialVersionUID = 1L;

	 @Schema(description = "Identificador del rodal asociado al parte semanal")
	 private String idParsemRodal;
	 
	 @Schema(description = "Identificador único del parte semanal")
	 private Long idParsemAluplan;
	 
	 @Schema(description = "Fecha de inicio de la semana")
	 private String fInisem;

	 @Schema(description = "Nombre del fichero del parte semanal")
	 private String txParsemFichero;
	 
	 @Schema(description = "Usuario que registró el parte")
	 private Long xUsuarioCreacion;

	 @Schema(description = "Fecha de registro del parte semanal")
	 private String fRegistro;
	 
	 @Schema(description = "Estado parte")
	 private String estado;

	 @Schema(description = "Vista del usuario que registra el parte (P: Profesorado, ALU: Alumnado)")
	 private String cdVista;
	 
	 @Schema(description = "Nombre subido el parte")
	 private String nombre;

	 @Schema(description = "Flag que indica si el parte está o no actualizado ")
	 private Integer lgActualizado;

}
