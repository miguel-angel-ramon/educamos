package es.jccm.edu.simulacion.application.domain.usuarios.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosUsuario", description = "Proyección para rescatar los datos de un usuario")
public interface DatosUsuarioProjection {
	
	@Schema(description = "Id del usuario")
	String getIdUsuario();
	
	@Schema(description = "X_USUARIO del usuario")
	String getXUsuario();
	
	@Schema(description = "Id de empleado")
	String getIdEmpleado();
	
	@Schema(description = "Numide del empleado ")
	String getNumide();
	
	@Schema(description = "Foto")
	byte[] getFoto();
	
	@Schema(description = "Nombre")
	String getNombre();
	
	@Schema(description = "Apellido1")
	String getApellido1();
	
	@Schema(description = "Apellido2")
	String getApellido2();
	
	@Schema(description = "Email")
	String getEmail();
	
	@Schema(description = "Perfil_def")
	String getPerfil_def();
	
	@Schema(description = "Centro_def")
	String getCentro_def();
	
	@Schema(description = "Tour")
	String getTour();
	
	@Schema(description = "Tour Evaluacion")
	String getTourEvaluacion();
    
    @Schema(description = "Piloto")
    String getPiloto();
}

