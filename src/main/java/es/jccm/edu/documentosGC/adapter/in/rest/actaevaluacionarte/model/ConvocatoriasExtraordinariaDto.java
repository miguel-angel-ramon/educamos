package es.jccm.edu.documentosGC.adapter.in.rest.actaevaluacionarte.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convocatorias extraordinaria", description = "Descripcion para el modelo de convocatorias extraordinaria")
public class ConvocatoriasExtraordinariaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;

}
