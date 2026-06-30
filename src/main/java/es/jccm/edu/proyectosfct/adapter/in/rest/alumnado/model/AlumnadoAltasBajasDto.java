package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(name = "Alumnado altas y bajas", description = "Descripcion para el modelo del alumnado altas y bajas")
public class AlumnadoAltasBajasDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema (name =  "Código cuenta de cotización")
	private String ccc;
	
	@Schema (name = "Número de la Seguridad Social")
	private String nuss;
	
	@Schema (name = "Fecha Real de Alta")	
	private Date fechaRealAlta;
}
