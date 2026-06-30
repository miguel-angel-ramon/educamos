package es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model;

import java.io.Serializable;

import es.jccm.edu.gestionidentidades.application.domain.Persona;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AlumnoHorario", description = "AlumnosHorario rescatados de la BBDD de comunica")
public class DocumentacionesDto implements Serializable{

	private static final long serialVersionUID = 7322754398224620992L;

	private Long id;
	
	private Long tipoDocumentacion;
	
	private Persona persona;
	
	private String identificacion;
	
	private Long xPersona;

}
