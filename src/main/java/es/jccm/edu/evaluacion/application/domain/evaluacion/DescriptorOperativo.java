package es.jccm.edu.evaluacion.application.domain.evaluacion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="TLDESOPERATIVO")
public class DescriptorOperativo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="X_DESOPERATIVO")
	private Long idDescriptorOperativo;

	@Column(name="D_DESOPERATIVO")
	private String descripcion;

	@Column(name="T_ABREV")
	private String abreviatura;

	@ManyToOne
	@JoinColumn(name="X_COMCLAVE")
	private CompetenciaClave competenciaClave;

	@Column(name="X_ETAPA")
	private Long idEtapa;

}
