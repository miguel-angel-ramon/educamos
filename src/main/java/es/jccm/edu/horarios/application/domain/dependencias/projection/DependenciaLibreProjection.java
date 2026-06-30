package es.jccm.edu.horarios.application.domain.dependencias.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Dependencia", description = "Proyección para rescatar las dependecias libres de un centro")
public interface DependenciaLibreProjection {

    Long getIdTramo();

    Long getIdDependencia();

    String getDependencia();

    String getTipo();

    Long getDimension();

    Boolean getCapacitada();

    String getInicioTramo();

    String getFinTramo();

}
