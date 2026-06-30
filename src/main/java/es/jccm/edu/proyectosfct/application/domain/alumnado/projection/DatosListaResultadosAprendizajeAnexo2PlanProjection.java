package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosListaResultadosAprendizajeAnexo2PlanProjection", description = "Proyección del resultado de la consulta de módulos y cursos.")
public interface DatosListaResultadosAprendizajeAnexo2PlanProjection {

        @Schema(description = "Aprendizaje (combinación de abreviatura y descripción).")
        String getAprendizaje();

        @Schema(description = "Indica fct desarrollada en centro (1 = sí, 0 = no).")
        Integer getCentro();

        @Schema(description = "Indica fct desarrollada en centro (1 = sí, 0 = no).")
        Integer getEmpresa();

        @Schema(description = "Lista de empresas asociadas al módulo.")
        String getDempresa();
    }

