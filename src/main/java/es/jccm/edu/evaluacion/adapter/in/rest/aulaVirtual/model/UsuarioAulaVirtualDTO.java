package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AulaVirtualUsuarioDTO", description = "DTO Usuario Aula Virtual ")
public class UsuarioAulaVirtualDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del Usuario")
	private Long id;
	
	@Schema(description = "Nombre del Usuario")
	private String username;
	
	@Schema(description = "Password del Usuario")
	private String password;
	
	@Schema(description = "Nombre del Usuario")
	private String firstname;
	
	@Schema(description = "Apellido del Usuario")
	private String lastname;
	
	@Schema(description = "Nombre completo del Usuario")
	private String fullname;
	
	@Schema(description = "Email del Usuario")
	private String email;
	
	@Schema(description = "Autorización del Usuario")
	private String auth;
	
	@Schema(description = "Id. del Usuario")
	private String idnumber;
	
	@Schema(description = "Departamento del Usuario")
	private String department;
	
	@Schema(description = "Institución del Usuario")
	private String institution;
	
	@Schema(description = "Descripción del Usuario")
	private String description;
	
	@Schema(description = "Ciudad del Usuario")
	private String city;
	
	@Schema(description = "País del Usuario")
	private String country;
}