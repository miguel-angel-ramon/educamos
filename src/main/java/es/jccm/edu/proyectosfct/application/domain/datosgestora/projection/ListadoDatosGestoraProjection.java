package es.jccm.edu.proyectosfct.application.domain.datosgestora.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListadoDatosGestoraProjection", description = "Listado de datos de la seguridad social de alumnos ofrecido por la gestora filtrado por centro")
public interface ListadoDatosGestoraProjection {

    @Schema(description = "id")
    Long getId();

    @Schema(description = "tutor")
    String getTutor();

    @Schema(description = "Nombre del centro")
    String getCentro();

    @Schema(description = "Número de la Seguridad Social del alumno")
    String getNuss();

    @Schema(description = "nombre del alumno")
    String getNombreAlumno();
    
    @Schema(description = "dni")
    String getDni();

    @Schema(description = "curso")
    String getCurso();

    @Schema(description = "unidad")
    String getUnidad();

    @Schema(description = "fechaAlta")
    String getFechaAlta();

    @Schema(description = "fechaBaja")
    String getFechaBaja();

    @Schema(description = "erasmusCB")
    String getErasmusCB();

    @Schema(description = "erasmusSB")
    String getErasmusSB();

    @Schema(description = "estado")
    String getEstado();

    @Schema(description = "tipo")
    String getTipo();
    
    @Schema(description = "Descripcion error")
    String getDs_error();

    @Schema(description = "idGestora")
    Integer getIdGestora();

    @Schema(description = "file")
    Integer getLgFile();

    @Schema(description = "data")
    Integer getLgData();
    
}
