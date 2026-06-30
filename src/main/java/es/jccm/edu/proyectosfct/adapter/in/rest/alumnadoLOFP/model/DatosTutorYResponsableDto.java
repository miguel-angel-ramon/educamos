package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tutor y responsable", description = "Descripcion para el modelo de tutor/responsable del plan")
public class DatosTutorYResponsableDto {

	@Schema(description = "Nombre del responsable en la empresa")
	private String nombreResponsable;

	@Schema(description = "Nombre del tutor del plan")
	private String nombreTutor;

}
