package es.jccm.edu.evaluacion.application.domain.evaluacion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TLCOMCLAVE")
public class CompetenciaClave implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="X_COMCLAVE")
	private Long idCompetenciaClave;
	
	@Column(name="D_COMCLAVE")
	private String descripcion;

	@Column(name="T_ABREV")
	private String abreviatura;
	
}
