package es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity

public class FaltaAlumnoCentro implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idFalasialu;
	
	private String tramo;
	
	private String fecha;
	
	private String motivo;
	
	private Long idMotivo;
	
	private Long idJustificacion;
	
	private Long idNotificacion;
	
	private String notificacion;
	
	private String observacion;
	
	private String tipo;
}
