package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;



@Data
@Schema(name = "Informe", description = "Informe generado.")
public class InformeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codCentro;

	private Long idCompetencia;
	private String sector;

	private String nivel;

	private String codCompetencia;

	private String descCompetencia;

	private Long valor;

	private Long respuestas;

	private Long anno;
	
	
	private List<String> descripcionesObjetivo;




}
