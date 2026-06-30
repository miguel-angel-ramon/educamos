package es.jccm.edu.pdc.application.domain.cuestionarios.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class LineaActuacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String fechaCreacion;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idLinAct;
	
	private Long idObjEsp;

	private String titulo;

	private String descripcion;

	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicio;

	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFin;

	private String responsable;

	private String logro;

	private String instrumentos;

	private String activo;
	
	private String estado;
	
	private Integer porcentaje;
}
