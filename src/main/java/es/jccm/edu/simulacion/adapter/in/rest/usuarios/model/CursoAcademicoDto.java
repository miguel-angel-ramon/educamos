package es.jccm.edu.simulacion.adapter.in.rest.usuarios.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CursoAcademico", description = "Datos rescatados para el año academico actual")
public class CursoAcademicoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Año academico")
	private Integer anno;

}