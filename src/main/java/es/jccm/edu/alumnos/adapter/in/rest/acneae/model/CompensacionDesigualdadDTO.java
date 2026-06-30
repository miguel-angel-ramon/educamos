package es.jccm.edu.alumnos.adapter.in.rest.acneae.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="Alumnos",description="Entidad para recuperar el listado de Compensaciones de Desigualdades")
public class CompensacionDesigualdadDTO implements Serializable {
	
private static final long serialVersionUID = 1L;
	

	@Schema(description="Id de la ANCE")
	private Long id;
	
	@Schema(description="Descripción de la NCE")
	private String descripcion;
	
	@Schema(description="Clave alternativa procedente del IES")
	private String clave;
	
	@Schema(description="Segunda clave alternativa procedente del IES")
	private String clave2;
	
	@Schema(description="Orden de presentacion de la NCE")
	private int nOrden;
	
	@Schema(description="Año de inicio")
	private Integer annoInicio;
	
	@Schema(description="Año de finalizacion")
	private Integer annoFin;
	
	@Schema(description="Indica si se puede editar el campo o no.")
	private String editable;

}
