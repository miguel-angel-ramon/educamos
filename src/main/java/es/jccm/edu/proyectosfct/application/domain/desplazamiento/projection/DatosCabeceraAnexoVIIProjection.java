package es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado anexo VII", description = "Descripcion para el modelo cabecera anexo VII")
public interface DatosCabeceraAnexoVIIProjection {
	
	@Schema(description = "Centro")
	String getCentro();
	
	@Schema(description = "Provincia")
	String getProvincia();
	
	@Schema(description = "Localidad")
	String getLocalidad();
	
	@Schema(description = "Municipio")
	String getMunicipio();
	
	@Schema(description = "Fecha Firma")
	String getFechaFirma();	
	
	@Schema(description = "Codigo")
	String getCodigo();	
	
	@Schema(description = "Periodo")
	String getPeriodo();	
	
	@Schema(description = "Tutor")
	String getTutor();
	
	@Schema(description = "Anno")
	String getAnno();	
	
	@Schema(description = "Familia")
	String getFamilia();	
	
	@Schema(description = "Curso")
	String getCurso();	
	
	@Schema(description = "Director")
	String getDirector();

	@Schema(description = "Horas")
	String getHoras();

}
