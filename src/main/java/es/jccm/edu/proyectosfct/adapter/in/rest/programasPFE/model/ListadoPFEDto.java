package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnadoTutorLofpDto", description = "DTO para el listado de alumnado con tutor en LOFP")
public class ListadoPFEDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Nombre completo del alumno")
    private Long id;
	
	@Schema(description = "centro")
    private String centro;
	
	@Schema(description = "curso")
    private String curso;
	
	@Schema(description = "Nombre completo del alumno")
    private String tipo;
	
	@Schema(description = "Nombre completo del alumno")
    private String familia;
	
	@Schema(description = "Nombre completo del alumno")
    private String creador;
	
	@Schema(description = "Nombre completo del alumno")
    private String modalidad;
	
	@Schema(description = "Nombre completo del alumno")
    private String estado;
	
	@Schema(description = "Nombre completo del alumno")
    private String cuIni;
	
	@Schema(description = "Nombre completo del alumno")
    private String cuFin;

	@Schema(description = "Id rodal")
	private String idRodal;

	@Schema(description = "Nombre del fichero")
	private String fichero;

	@Schema(description = "puedeBorrar")
	private Integer puedeBorrar;

	@Schema(description = "puedeVisto")
	private Integer puedeVisto;
	
	@Schema(description = "idHistorial")
	private Long idHistorial;


	@Schema(description = "Requiere autorización")
	private String requiereAutorizacion;

}
