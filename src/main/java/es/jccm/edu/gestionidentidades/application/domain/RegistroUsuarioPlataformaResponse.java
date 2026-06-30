package es.jccm.edu.gestionidentidades.application.domain;

import es.jccm.edu.gestionidentidades.application.domain.PersonaId;
import es.jccm.edu.gestionidentidades.application.domain.TipoPersonal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class RegistroUsuarioPlataformaResponse {

	private TipoPersonal tipoPersonal;
	private PersonaId personaId;
	private String correoExterno;
	
}
