package es.jccm.edu.pdc.application.domain.planActuacion.entities;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
public class LineaActuacionPDC {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;

	String titulo;
	
	Date fechaCreacion;

	String descripcion;

	Date fechaInicio;

	Date fechaFin;

	String responsable;

	String logro;

	String instrumentos;
	
	Integer porcentaje;
	
	Date fechaInicioEjecucion;
	
	Date fechaFinEjecucion;
	
	String tareas;
	
	String valoracion;
	
	String dificultades;
	
	String comentarios;
	
	String descripcionObjetivoEspecifico;
	
	String estado;

}
