package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Getter
@Setter
@Schema(name = "ObservacionesYMejorasAmbitoDto", description = "Descripcion para el modelo de Observaciones y Mejoras por ambito")
public class ObservacionesYMejorasGlobalDto implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	  
    private Integer centro;

   
    private Integer cuepub;

    
    private Integer anno;

   
    private String mejoragestion;


    private String mejoraaprendizaje;

  
    private String otrastareas;

	

}
