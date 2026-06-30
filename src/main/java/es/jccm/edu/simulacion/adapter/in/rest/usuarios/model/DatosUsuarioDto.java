package es.jccm.edu.simulacion.adapter.in.rest.usuarios.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatosUsuario", description = "Datos rescatados de usuarios para el módulo de simulación de usuarios del escritorio")
public class DatosUsuarioDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del usuario")
	private String idUsuario;
	
	@Schema(description = "X_USUARIO del usuario")
	private String xUsuario;
	
	@Schema(description = "Id de empleado")
	private String idEmpleado;

	@Schema(description = "Numide del empleado")
	private String numide;
	
	@Schema(description = "Foto")
	private byte[] foto;
	
	@Schema(description = "Nombre")
	private String nombre;

	@Schema(description = "Apellido1")
	private String apellido1;

	@Schema(description = "Apellido2")
	private String apellido2;

	@Schema(description = "Email")
	private String email;
	
	@Schema(description = "Perfil_def")
	private String perfil_def;
	
	@Schema(description = "Centro_def")
	private String centro_def;
	
	@Schema(description = "Tour")
	private Boolean tour;
	
	@Schema(description = "Tour Evaluacion")
	private Boolean tourEvaluacion;
	
    @Schema(description = "Piloto")
    private Boolean piloto;

	@Schema(description = "OID del usuario en Módulo de acceso")
	private Long oid;

	@Schema(description = "OID del usuario en Módulo de acceso")
	private List<Long> centroAnterior;

	@Schema(description = "OID del usuario en Módulo de acceso")
	private List<Long> anyosAnteriores;

}
