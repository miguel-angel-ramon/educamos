package es.jccm.edu.trazaerrores.adapter.in.rest.trazaErrorFrontEnd.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TrazaError", description = "Errores enviados desde cualquier aplicación FrontEnd perteneciente a educamosCLM")
public class ErrorClienteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String logLevel;
	private String stackTrace;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date timestamp;
	private String moduleName;
	private String userName;
	private String browser;
	private String ip;
	private List<String> params;

}
