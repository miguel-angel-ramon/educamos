package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Cuestionario", description = "Descripcion para el modelo de cuestionario")
public class CuestionarioDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer anio;

	private Long idCuePubUsu;

	private Long idCuePub;
	
	private Long idCuestionario;
	
	private String nombre;
	
	private String descripcion;

	private boolean activo;

	private boolean presentado;
	private Date fInicioRespuestas;
	
	private Date fFinRespuestas;

}
