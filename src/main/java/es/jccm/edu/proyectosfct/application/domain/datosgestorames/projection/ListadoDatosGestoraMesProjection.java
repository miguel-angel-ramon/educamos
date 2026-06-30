package es.jccm.edu.proyectosfct.application.domain.datosgestorames.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListadoDatosGestoraMesProjection", description = "Listado de datos de la seguridad social de alumnos ofrecido por la gestora filtrado por mes")
public interface ListadoDatosGestoraMesProjection {

    @Schema(description = "id")
    Long getId();

    @Schema(description = "Número de la Seguridad Social del alumno")
    String getNuss();

    @Schema(description = "nombre del alumno")
    String getNombreAlumno();

    @Schema(description = "dni")
    String getDni();
    
    @Schema(description = "unidad")
    String getUnidad();

    @Schema(description = "curso")
    String getCurso();
    
    @Schema(description = "Número del mes")
    Integer getNuMes();
    
    @Schema(description = "tutor")
    String getTutor();

    @Schema(description = "Número de días Inte")
    Integer getNuDiasInteMes();
    
    @Schema(description = "estado")
    String getEstado();

    @Schema(description = "Número de días reales")
    Integer getNuDiasRealMes();

    @Schema(description = "Número de días Nacu")
    Integer getNuDiasNacuMes();

    @Schema(name = "diasInteEra")
    Integer getNuDiasInteEraMes();
    
    @Schema(description = "Nombre del centro")
    String getCentro();

    @Schema(description = "idGestora")
    Integer getIdGestora();

}
