package es.jccm.edu.shared.adapter.in.rest.plantilla.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Plantilla", description = "Descripcion para el modelo de plantilla")
public class PlantillaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String tituloFormulario;
	
	@NotEmpty
	@NotNull
	private String campo1;
	
	private String campo2;
	
	@NotEmpty
	@NotNull
	@Size(max=8, message = "No puede superar los 8 carácteres")
	private String campo3;
	
	private String campo4;
	
	private String campo5;
	
	private String campo6;
	
	private String campo7;
	
	private String select;
	
	private String campoTextarea;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date campoFecha;
	
	private String check1;
	
	private String check2;
	
	private String radioButton;
}
