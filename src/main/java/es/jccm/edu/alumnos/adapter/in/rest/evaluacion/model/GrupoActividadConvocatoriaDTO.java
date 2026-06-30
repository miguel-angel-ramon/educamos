package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Grupo Actividad Convocatoria", description = "Grupo de Actividad de la Convocatoria del centro")
public class GrupoActividadConvocatoriaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id Grupo Actividad")
	private Long idGrupoActividad;
	
	@Schema(description = "nombre Grupo Actividad")
	private String nombre;
	
	@Schema(description = "abreviatura Grupo Actividad")
	private String abreviatura;
	
	@Schema(description = "estado Grupo Actividad")
	private String estado;
	
	@Schema(description = "Id Unidad")
	private Long idUnidad;
	
	@Schema(description = "Id Etapa")
	private Long idEtapa;
	
	@Schema(description = "unidad")
	private String unidad;
	
	@Schema(description = "MateriaOmg")
	private Long idMateriaOmg;
	
	@Schema(description = "Oferta")
	private Long idOfertaMatrig;
	
	@Schema(description = "Lomloe")
	private Long lomloe;
	
	@Schema(description = "Id ConvUnidad")
	private Long idConvUnidad;

	@Schema(description = "Id ConvUnidad")
	private String curso;

	@Schema(description = "Si el grupo actividad tiene varios materiaOmg")
	private Long cra;

}
