package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "TLOBSERVEVA")
public class ObservacionConv {

	@EmbeddedId
	private ObservacionPKConv id;
	
	@Column(name = "T_OBSERVACIONES")
	private String observaciones;
	
}
