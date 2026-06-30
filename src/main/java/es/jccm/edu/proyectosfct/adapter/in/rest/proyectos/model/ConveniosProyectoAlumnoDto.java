package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;

@Data
@Schema(name = "Convenios a proyecto alumno", description = "Descripcion para el modelo de asignar alumnos a convenios proyectos")
public class ConveniosProyectoAlumnoDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4697497824020373014L;

	@Schema(description = "Id del convenios proyecto alumno")
	private Long id;
	
	@Schema(description = "Id de la matricula")
	private Long idMatricula;
	
	@Schema(description = "Id de la evaluacion")
	private Long idEvaluacion;
	
	@Schema(description = "Orientaciones")
	private String orientaciones;
	
	@Schema(description = "Identificacion Rodal")
	private String idEvaRodal;
	
	@Schema(description = "Nombre del fichero Rodal")
	private String txEvafirFichero;
	
	@Schema(description = "Fecha de la firma")
	private Date fechaFirma;

	@Schema(description = "Campo que recoge 0 si el alumno no cotiza en la SS o 1 si el alumno si cotiza en la SS")
	private Integer lgCotiza;

	@Schema(description = "Campo que recoge 0 si el alumno ya tenía un número de la SS y 1 si el alumno no tenía número de la SS")
	private Integer lgNuss;

	@Schema(description = "Campo que recoge el número de la seguridad social del alumno")
	private String tnuss;

	@Schema(description = "Campo que en función de la letra hace una accion diferente, C create, U update, D delete")
	private String accion;
	
	@Schema(description = "Campo que recoge 0 si el alumno no es erasmus, 1 si tiene erasmus sin beca y 2 para erasmus con beca")
	private Integer lgErasmus;

	@Schema(description = "Campo que recoge 0 si el alumno no es excluido, 1 si es excluido")
	private Integer lgExcluir;


	@Schema(description = "Campo que recoge el número de horas de evaluación del alumnado")
	private Integer nuHorasEva;

	@Transient
	private String periodo;

	// ---------- Relationships -----------
	
	@Schema(description = "Id del proyecto convenios")
	private ConveniosProyectoDto convenioProyecto;
	
}
