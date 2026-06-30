package es.jccm.edu.proyectosfct.application.domain.programas.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado programas", description = "Descripcion para el modelo de programas de un centro")
public interface ListadoProgramaFctProjection {
	
	@Schema(description = "Identificador del identificador del programa")
	Long getId();
	
	@Schema(description = "Nombre del programa")
	String getDescripcion();
	
	@Schema(description = "Nombre del tutor programa")
	String getTutor();
	
	@Schema(description = "Descripcion provincia del programa")
	String getProvincia();
	
	@Schema(description = "Condigo centro del programa")
	Long getCentro();
	
	@Schema(description = "Familia del programa")
	String getFamilia();
	
	@Schema(description = "Oferta del programa")
	String getOfertaMatriculaGenerico();
	
	@Schema(description = "Id Tutor del programa")
	Long getIdTutorFct();	
	
	@Schema(description = "Convenio")
	String getConvenio();

}
