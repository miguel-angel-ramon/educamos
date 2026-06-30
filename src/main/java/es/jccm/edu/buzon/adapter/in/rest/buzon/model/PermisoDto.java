package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Permiso", description = "Permisos que tienen los alumnos en sus correos")
public class PermisoDto {
	
	@Schema(description = "Id del permiso de correo del alumnado")
    Long idPermiso;
	
	@Schema(description = "Nombre del permiso de correo del alumnado")
    String codPermiso;

    @Schema(description = "Descripción del permiso de correo del alumnado")
    String descripcion;

}

