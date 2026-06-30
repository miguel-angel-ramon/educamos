package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ListadoProyectosDto", description = "Descripcion para el modelo de listado de proyectos dto")
public class ListadoProyectosDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del proyecto")
	private Long id;
	
	@Schema(description = "Centro de proyecto")
	private String ds_provincia;
	
	@Schema(description = "Centro de proyecto")
	private String ds_centro;

	@Schema(description = "Tipo de proyecto")
	private String ds_tipo;
	
	@Schema(description = "Nombre del proyecto")
	private String ds_proyecto;

	@Schema(description = "Tutor del proyecto")
	private String ds_tutor;
	
	@Schema(description = "Familia profesional del proyecto")
	private String ds_familia;
	
	@Schema(description = "Modalidad del proyecto")
	private String ds_modalidad;
	
	@Schema(description = "Total horas proyecto")
	private Integer nu_horas;
	
	@Schema(description = "Número de alumnos del proyecto")
	private Integer nu_alumnos;
	
	@Schema(description = "Id tutor")
	private Long idTutorFct;

	@Schema(description = "Descripcion curso")
	private String ds_curso;

	@Schema(description = "Valor lógico para saber si el proyecto es de la nueva ley lofp")
	private String lg_lofp;

	@Schema(description = "Número de actividades asociadas")
	private Integer actividades;

	@Schema(description = "Valor lógico para saber si un plan se puede copiar o no")
	private Integer lg_copiar;}
