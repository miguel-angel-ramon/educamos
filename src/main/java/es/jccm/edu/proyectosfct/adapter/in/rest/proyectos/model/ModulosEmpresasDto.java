package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyecto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosCurso;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ProyectosDto", description = "Descripcion para el modelo de proyectos dto")
public class ModulosEmpresasDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador de un módulo asociado a una empresa participante")
	private Long id;
	
	@Schema(description = "Usuario de creación del registro")
	private Long idUsuarioCreacion;
	
	@Schema(description = "Fecha de creación del registro")
	private Date fechaCreacion;
	
	@Schema(description = "Usuario de última modificación del registro")
	private Long idUsuarioModificacion;
	
	@Schema(description = "Fecha de última modificación del registro")
	private Date fechaModificacion;

	
	// ----------- Relationships ------------
	
	@Schema(description = "Identificador del módulo de un curso en el Proyecto de FP Dual")
	private ModulosCurso moduloCurso;
	
	@Schema(description = "Identificador de la asociación de un Proyecto de FP Dual a un Convenio")
	private ConveniosProyecto convenioProyecto;
	
	
	

}
