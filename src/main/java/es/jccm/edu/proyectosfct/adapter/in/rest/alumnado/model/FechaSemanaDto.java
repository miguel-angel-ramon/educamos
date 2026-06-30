package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "FechaSemanaDto", description = "FechaSemanaDto")
public class FechaSemanaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="primerDia")
	private Date primerDia;
	// ---------- Relationships -----------	
	
	@Column(name="semana")
	private String semana;
	
	@Column(name="actual")
	private String actual;

}
