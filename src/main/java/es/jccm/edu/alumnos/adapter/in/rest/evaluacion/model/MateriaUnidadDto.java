package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.alumnos.application.domain.evaluacion.ListCalificaciones;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Materia unidad", description = "Materia unidad")
public class MateriaUnidadDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id Materia")
	private Long idMateria;

	@Schema(description = "Nombre materia")
	private String nombreMateria;

	@Schema(description = "Sistema de calificación de la materia")
	private List<ListCalificaciones> sistemaCalificacion;
	
	@Schema(description = "Abreviatura")
	private String abreviatura;

	@Schema(description = "Id Etapa")
	private Long idEtapa;
	
	@Schema(description = "MateriaOmg")
	private Long idMateriaOmg;
	
	@Schema(description = "Oferta")
	private Long idOfertaMatrig;
	
	@Schema(description = "Lomloe")
	private Long lomloe;

	@Schema(description = "Curso de la Materia")
	private String cursoMateria;
}
