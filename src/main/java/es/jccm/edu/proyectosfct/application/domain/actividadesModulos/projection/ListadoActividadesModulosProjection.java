package es.jccm.edu.proyectosfct.application.domain.actividadesModulos.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListadoActividadesModulosProjection", description = "Listado de actividades plan con información adicional")

public interface ListadoActividadesModulosProjection {

	@Schema(description = "Identificador de la actividad")
	Long getId();

	@Schema(description = "Nombre de la actividad")
	String getNombre();

	@Schema(description = "Texto con los resultados asociados")
	String getLsResula();
	
	@Schema(description = "Texto con la descripciones")
	String getDsDescripcion();

	@Schema (description = "Abreviatura de la actividad")
	String getTxAbrev();

	@Schema (description = "Orden de la actividad")
	Integer getNuOrden();

	@Schema (description = "Flag que indica si la actividad está o no registrada")
	Integer getActividadRegistrada();

}