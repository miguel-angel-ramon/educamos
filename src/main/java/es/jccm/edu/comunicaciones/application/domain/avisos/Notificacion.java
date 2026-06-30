package es.jccm.edu.comunicaciones.application.domain.avisos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Notificacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idNotificacion;
	
	private String titulo;
	
	private String cuerpo;
	
	private String tipo;
	
	private String fichero;
	
	private String idrodal;
	
	private Long idAdjunto;
	
	private Date fechaInicio;
	
	private Date fechaFin;
}
