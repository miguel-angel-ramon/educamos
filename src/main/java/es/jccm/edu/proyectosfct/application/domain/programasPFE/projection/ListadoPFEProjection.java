package es.jccm.edu.proyectosfct.application.domain.programasPFE.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlumnadoTutorLofpDto", description = "DTO para el listado de alumnado con tutor en LOFP")
public interface ListadoPFEProjection {
	
	@Schema(description = "Id del convenio proyecto o convenio programa")
	Long getId();
	
	@Schema(description = "Nombre centro")
	String getCentro();
	
	@Schema(description = "Nombre alumno")
	String getCurso();
	
	@Schema(description = "Nombre empresa")
	String getTipo();
	
	@Schema(description = "Tipo empresa")
	String getFamilia();
	
	@Schema(description = "Curso")
	String getCreador();
	
	@Schema(description = "Unidad")
	String getModalidad();
	
	@Schema(description = "Unidad")
	String getEstado();
		
	@Schema(description = "Descripcion programa")
	String getCuIni();
	
	@Schema(description = "Partes")
	String getCuFin();

	@Schema(description = "Id_rodal")
	String getIdRodal();

	@Schema(description = "Nombre del fichero")
	String getFichero();
	
	@Schema(description = "Puede borrar")
	Integer getPuedeBorrar();

	@Schema(description = "Puede dar visto bueno")
	Integer getPuedeVisto();
	
	@Schema(description = "Id historial")
	Long getIdHistorial();

	@Schema(description = "Requiere Autorización")
	String getRequiereAutorizacion();
}
