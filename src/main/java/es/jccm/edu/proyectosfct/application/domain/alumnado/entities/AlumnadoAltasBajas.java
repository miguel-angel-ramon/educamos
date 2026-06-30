package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
public class AlumnadoAltasBajas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column (name =  "Código cuenta de cotización")
	private String ccc;
	
	@Column (name = "Número de la Seguridad Social")
	private String nuss;
	
	@Column (name = "Fecha Real de Alta")	
	private Date fechaRealAlta;
	
}
