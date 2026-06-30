package es.jccm.edu.gestionauditorias.adapter.in.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Logg acceso usuario sistema autenticación", description = "Dto Logg acceso usuario sistema autenticación")
public class LoggAccUsuSisAutDto {
	
	private String idUsuario;
	private String login;
	private String codSistemaAut;

}
