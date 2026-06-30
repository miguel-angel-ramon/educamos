package es.jccm.edu.evaluacion.adapter.in.rest.materias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoMateriasUnidad", description = "Materias y unidades dónde se imparten y alumnos que están matriculados en ellas")
public class AlumnoMateriasUnidadDTO {

    @Schema(description = "IdMateria")
    private Long idMateria;

    @Schema(description = "NombreMateria")
    private String nombreMateria;

    @Schema(description = "DescripcionMateria")
    private String descripcionMateria;

    @Schema(description = "MateriaAbreviatura")
    private String AbreviaturaMateria;

    @Schema(description = "IdUnidad")
    private Long idUnidad;

    @Schema(description = "NombreCurso")
    private String nombreCurso;

    @Schema(description = "IdAlumno")
    private Long idAlumno;

    @Schema(description = "Nombre")
    private String nombre;

    @Schema(description = "Apellidos")
    private String apellidos;
}
