package es.jccm.edu.evaluacion.application.domain.programacionAula.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "EVA_RELPROGAULAUNIDAD", schema = "DELPHOS")
public class EvaRelacionProgramacionAulaUnidad extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_RELPROGAULAUNIDAD")
	@SequenceGenerator(name = "SQ_EVA_RELPROGAULAUNIDAD", sequenceName = "SQ_EVA_RELPROGAULAUNIDAD", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	@Column(name="ID_RELPROGAULAUNIDAD")
	private Long id;
	
	// ---------- Relationships -----------

	@Column(name = "ID_PROGAULA")
	private Long programacionAula;
	
	@Column(name = "X_UNIDAD")
	private Long unidadCentro;
	
	@Column(name = "LG_AFECTATODOS", columnDefinition = "NUMBER(1,0) DEFAULT 0")
	private int lAfectaTodos;
}