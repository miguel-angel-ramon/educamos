package es.jccm.edu.trazaerrores.application.domain.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorCliente {

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
