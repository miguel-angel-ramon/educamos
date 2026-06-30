package es.jccm.edu.proyectosfct.application.domain.extraordinario.projections;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(name="AdjuntoAnexoXI",description = "Adjunto autogenerado para anexo XI")
public interface AdjuntoAnexoXIProjection {

    @Schema (description = "Centro")
    String getCentro();

    @Schema (description = "Lista de los alumnos")
    String getAlumnos();

    @Schema (description = "Provincia")
    String getProvincia();

    @Schema (description = "Nombre del curso")
    String getNombreCurso();

    @Schema (description = "Fecha")
    String getFecha();

}
