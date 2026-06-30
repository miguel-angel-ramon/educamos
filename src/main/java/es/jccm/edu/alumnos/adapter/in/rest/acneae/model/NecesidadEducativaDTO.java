package es.jccm.edu.alumnos.adapter.in.rest.acneae.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(name="Alumnos",description="Entidad para rescatar el listado de alumnos con NEE")
public class NecesidadEducativaDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Schema(description="Id de la NEE")
	private Long id;
	
	@Schema(description="Descripción de la NEE")
	private String descripcionNEE;
	
	@Schema(description="Clave alternativa procedente del IES")
	private String clave;
	
	@Schema(description="Segunda clave alternativa procedente del IES")
	private String clave2;
	
	@Schema(description="Orden de presentacion de la NEE")
	private int nOrden;
	
	@Schema(description="Año de inicio de la vigencia de la Necesidad")
	private int annoInicio;
	
	@Schema(description="Año fin de la vigencia de la Necesidad")
	private Integer annoFin;
	
	@Schema(description="Indicador de necesidad de apoyo (S/N)")
	private String apoyo;
	
	@Schema(description="Indicador de necesidad de adaptación (S/N)")
	private String adaptacion;

}
