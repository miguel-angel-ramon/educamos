package es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConveniosProgramasFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Transient;

@Data
@Schema(name = "Alumno Programa", description = "Descripcion para el modelo de Alumno de un programa")
public class AlumnoProgramaDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234012742390577057L;

	@Schema(description = "Id de alumnos a programa")
	private Long id;
	
	@Schema(description = "Orientaciones")
	private String orientaciones;
	
	@Schema(description = "Campo que recoge 0 si el alumno ya tenía un número de la SS y 1 si el alumno no tenía número de la SS")
	private Integer lgNuss;
	
	@Schema(description = "Identificacion Rodal")
	private String idEvaRodal;
	
	@Schema(description = "Campo que recoge el número de la seguridad social del alumno")
	private String tnuss;
	
	@Schema(description = "Nombre del fichero Rodal")
	private String txEvafirFichero;
	
	@Schema(description = "Campo que recoge el tipo de operación a realizar en la base de datos")
	private String accion; 
	
	@Schema(description = "Fecha de la firma")
	private Date fechaFirma;
	
	@Schema(description = "Valor para saber si alumno va a cotizar a la SS")
	private Integer lgCotiza;	
		
	@Schema(description = "Campo que recoge 0 si el alumno no es erasmus, 1 si tiene erasmus sin beca y 2 para erasmus con beca")
	private Integer lgErasmus;

	@Schema(description = "Campo que recoge 0 si el alumno no es excluido, 1 si es excluido")
	private Integer lgExcluir;

	@Schema(description = "Campo que recoge el número de horas del alumnado para evaluación.")
	private Integer nuHorasEva;

	@Transient
	private String periodo;

	// ---------- Relationships -----------	
	
	@Schema(description = "Id convenio")
	private ConveniosProgramasFctDto convenioPrograma;
	
	@Schema(description = "Id matricula")
	private Long idMatricula;
	
	@Schema(description = "Id evaluacion")
	private Long idEvaluacion;
	
}
