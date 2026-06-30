package es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SincronizaUsuarioMA", description = "SincronizaUsuarioMA")
public class SincronizaUsuarioMADto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "sTipoPersonal")
	private String stipopersonal;
	
	@Schema(description = "dni")
	private String dni;
	
	@Schema(description = "tipide")
	private String tipide;
	
	@Schema(description = "centro")
	private String centro;
	
	@Schema(description = "tcorreoexterno")
	private String tcorreoexterno;
	
	@Schema(description = "xusuario")
	private int xusuario;
	
	@Schema(description = "centroperteneceaccesoeducamos")
	private String centroperteneceaccesoeducamos;
	
	@Schema(description = "fTomapos")
	private String ftomapos;
	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String documento;
	private String lsexo;
	private Date fechaNacimiento;
	private String correoLdap;	
}
