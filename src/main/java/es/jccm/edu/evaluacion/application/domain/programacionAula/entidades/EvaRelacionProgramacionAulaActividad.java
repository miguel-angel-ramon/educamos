package es.jccm.edu.evaluacion.application.domain.programacionAula.entidades;

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
@Table(name = "EVA_RELPROGAULACT", schema = "DELPHOS")
public class EvaRelacionProgramacionAulaActividad extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_RELPROGAULACT")
	@SequenceGenerator(name = "SQ_EVA_RELPROGAULACT", sequenceName = "SQ_EVA_RELPROGAULACT", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	@Column(name="ID_RELPROGAULACT")
	private Long id;
	
	// ---------- Relationships -----------

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_PROGAULA")
	private EvaProgramacionAula programacionAula;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ACTIVIDAD")
	private EvaActividad actividad;

}