package es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipo autorizacion extraordinario", description = "Descripcion para el modelo de Tipo autorizacion extraordinario")
public class ListadoAutorizacionExtraordinarioDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador")
	private Long id;
	
	@Schema(description = "Nombre del alumno")
	private String nombreAlumno;
	
	@Schema(description = "Fin de semana extraordinario")
    private String finde;
	
	@Schema(description = "Horario extraordinario")
    private String horarioext;
	
	@Schema(description = "Vacaciones extraordinario")
    private String vacaciones;
	
	@JsonFormat(pattern="dd-MM-yyyy", timezone = "Europe/Madrid")
	@Schema(description = "fecha inicio extraordinario")
    private Date finiext;
	
	@JsonFormat(pattern="dd-MM-yyyy", timezone = "Europe/Madrid")
	@Schema(description = "fecha fin extraordinario")	
    private Date ffinext;
	
	@Schema(description = "horario fuera de provincia")
    private String horariofue;
	
	@JsonFormat(pattern="dd-MM-yyyy", timezone = "Europe/Madrid")
	@Schema(description = "fecha inicio fuera de provincia")	
    private Date finifue;

	@JsonFormat(pattern="dd-MM-yyyy", timezone = "Europe/Madrid")
	@Schema(description = "fecha fin fuera de provincia")	
    private Date ffinfue;
	
	@Schema(description = "Estado")
    private String estado;
	
	@Schema(description = "curso")
	private String curso;
	
	@Schema(description = "Identificador del alumno")
    private Long idMatricula;
	
	@Schema(description = "Puede editar")
    private Integer editarestado;
	
	@Schema(description = "Fecha de la última creación/modificación solicitud")
	String fultgen;
	
	@Schema(description = "tutor")
	private String tutor;
	
	@Schema(description = "Calendario extraordinario")
	private String calenExt;
	
	@Schema(description = "Calendario fuera de provincia")
	private String calenFue;
	
	@Schema(description = "Empresa")
	private String empresa;
	
	@Schema(description = "Habilitar boton de borrado")
	private String puedeBorrar;

	@Schema(description = "Número de petición")
	private Integer nuPeticion;

}
