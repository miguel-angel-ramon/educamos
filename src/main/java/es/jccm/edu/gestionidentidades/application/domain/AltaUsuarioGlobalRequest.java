package es.jccm.edu.gestionidentidades.application.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AltaUsuarioGlobalRequest {
	private Persona persona;
	private List<Aplicacion> codAplicaciones;
	private String modo;
	private int usuarioPeticionario;
	private String correoRecuperacion;
	private String identificacion;
	private String tipide;
	//TODO meter xcentro
	

}