package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import lombok.Data;

@Data
public class CursoSolicitudDatosDTO {
	
	private String curso;
	
	private Long idOfertamatricCurso;
	
	private Long idEtapa;
	
	private Long idEtapas;
	
	private String etapa;
	
	private Long idCiclo;
	
	private Long ordenetapa;
	
	private Long ordencurso;
	
	private Long numAlum;
	
	private Long numAlumT;
	
	private Long numAlumTOWAN;
	
	private Long numAlumTOWAS;

}
