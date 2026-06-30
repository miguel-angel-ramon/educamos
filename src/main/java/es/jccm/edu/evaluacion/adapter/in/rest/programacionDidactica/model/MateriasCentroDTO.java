package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MateriasCursoDTO", description = "Materias pertenecientes a cursos")
public class MateriasCentroDTO {

    @Schema(description = "Id. de la Materia de Curso Genérica")
    private Long idMateriaOmg;

    @Schema(description = "Nombre de la Materia")
    private String nombreMateria;

    @Schema(description = "Descripcion de la Materia")
    private String descMateria;

    @Schema(description = "Nombre del Curso")
    private String nombreCurso;

    @Schema(description = "Abreviatura de la Materia")
    private String abrevMateria;
}
