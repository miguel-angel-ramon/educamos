package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TLRELPONCOMESP", schema = "DELPHOS")
public class EvaRelacionPonderacionCompetenciaEspecifica extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLS_RELPONCOMESP")
	@SequenceGenerator(name = "TLS_RELPONCOMESP", sequenceName = "TLS_RELPONCOMESP", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	@Column(name="X_RELPONCOMESP")
	private Long id;
	
	@Column(name = "PESO")
	private Integer peso;
	
	@Column(name = "PORCENTAJE")
	private Integer porcentaje;
	
	@Column(name = "X_COMESP")
	private Long idCompetenciaEspecifica;
	
	// ---------- Relationships -----------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_PONDERACION")
	private EvaPonderacion ponderacion;

}