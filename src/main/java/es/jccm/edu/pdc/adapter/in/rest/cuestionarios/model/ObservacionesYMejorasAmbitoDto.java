package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Schema(name = "ObservacionesYMejorasAmbitoDto", description = "Descripcion para el modelo de Observaciones y Mejoras por ambito")
public class ObservacionesYMejorasAmbitoDto implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String observacion;

    private String propuesta;

    private Integer centro;


    private Integer cuepub;

    private Integer competencia;

    private Integer anno;
	
	

}
