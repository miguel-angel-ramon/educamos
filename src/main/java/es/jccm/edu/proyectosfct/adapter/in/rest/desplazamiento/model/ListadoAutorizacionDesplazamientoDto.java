package es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model;

import java.io.Serializable;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipo autorizacion desplazamiento", description = "Descripcion para el modelo de Tipo autorizacion desplazamiento")
public class ListadoAutorizacionDesplazamientoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador")
	private Long id;
	
	@Schema(description = "Nombre del alumno")
	private String nombreAlumno;
	
	@Schema(description = "Fecha de inicio")
    private Date fInicio;
	
	@Schema(description = "Fecha de fin")
    private Date fFin;
	
	@Schema(description = "Numero de dias")
    private String dias;
	
	@Schema(description = "Texto matricula")
    private String matricula;
	
	@Schema(description = "Texto itinerario")
    private String itinerario;
	
	@Schema(description = "Porcentaje kilometros dia")
    private String kmdia;
	
	@Schema(description = "Porcentaje kilometros totales")
    private String totalkm;
	
	@Schema(description = "Estado")
    private String estado;
	
	@Schema(description = "Fecha de la última creación/modificación solicitud")
	String fultgen;
	
	@Schema(description = "Identificador del alumno")
    private Long idMatricula;
	
	@Schema(description = "Puede editar")
    private Integer editarestado;
	
	@Schema(description = "Habilitar boton de borrado")
	private String puedeBorrar;
}
