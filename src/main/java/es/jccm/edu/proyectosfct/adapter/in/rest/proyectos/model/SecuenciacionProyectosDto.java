package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.Proyectos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ProyectosDto", description = "Descripcion para el modelo de proyectos dto")
public class SecuenciacionProyectosDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador del registro de información general del Proyecto de FP Dual")
	private Long id;
	
	@Schema(description = "Campo de texto para indicar la secuenciación a realizar en el Proyecto")
	private String txProyecto;
	
	@Schema(description = "Identificador del documento de secuenciación adjunto en Rodal")
	private String idSecficRodal;
	
	@Schema(description = "Nombre del fichero subido, incluida la extensión, para el documento de ")
	private String txSecficFichero;
	
	@Schema(description = "Usuario de creación del registro")
	private Long idUsuarioCreacion;
	
	@Schema(description = "Fecha de creación del registro")
	private Date fechaCreacion;
	
	@Schema(description = "Usuario de última modificación del registro")
	private Long idUsuarioModificacion;
	
	@Schema(description = "Fecha de última modificación del registro")
	private Date fechaModificacion;

	
	// ----------- Relationships ------------
	
	@Schema(description = "Identificador del Proyecto de FP Dual")
	private Proyectos proyecto;
	
	

}
