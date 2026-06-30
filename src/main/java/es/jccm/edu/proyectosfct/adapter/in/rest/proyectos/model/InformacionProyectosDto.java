package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.Proyectos;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.SecuenciacionProyectos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ProyectosDto", description = "Descripcion para el modelo de informacion de proyectos dto")
public class InformacionProyectosDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del informacion del proyecto")
	private Long id;
	
	@Schema(description = "Campo de texto para Introducción, características generales del proyecto de Formación Profesional Dual")
	private String tx_introduccion;
	
	@Schema(description = "Campo de texto para definir los objetivos generales que se pretenden impulsar o alcanzar con el proyecto")
	private String tx_objetivos;
	
	@Schema(description = "Campo de texto para definir los recursos no disponibles en el centro y su necesidad")
	private String tx_recursos;
	
	@Schema(description = "Campo de texto para definir la metodología a seguir para llevar a cabo el proyecto")
	private String tx_metodologia;
	
	@Schema(description = "Campo de texto para definir los procedimientos e instrumentos para la evaluación y seguimiento")
	private String tx_evaluacion;
	
	@Schema(description = "Campo de texto para definir el plan de coordinación entre el centro educativo y la empresa")
	private String lb_coordinacion;
	
	@Schema(description = "Campo de texto para definir la adaptación a los alumnos con necesidades específicas de apoyo educativo")
	private String tx_adaptacion;
	
	@Schema(description = "Campo de texto para definir otros datos relevantes")
	private String tx_otros;
	
	@Schema(description = "Usuario de creación del registro")
	private Long idUsuarioCreacion;
	
	@Schema(description = "Fecha de creación del registro")
	private Date fechaCreacion;
	
	@Schema(description = "Usuario de última modificación del registro")
	private Long idUsuarioModificacion;
	
	@Schema(description = "Fecha de última modificación del registro")
	private Date fechaModificacion;

	@Schema(description = "Campo de texto ampliado para definir los objetivos generales que se pretenden impulsar o alcanzar con el proyecto")
	private String lb_objetivos;

	@Schema(description = "Campo de texto ampliado para definir los procedimientos e instrumentos para la evaluación y seguimiento")
	private String lb_evaluacion;

	@Schema(description = "")
	private SecuenciacionProyectos secuenciacionProy;
	
	// ----------- Relationships ------------
	
	@Schema(description = "Id del proyecto")
	private Proyectos proyecto;


}
