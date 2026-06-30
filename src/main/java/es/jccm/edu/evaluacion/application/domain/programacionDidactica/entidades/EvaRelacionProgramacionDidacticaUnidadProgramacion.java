package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "EVA_RELPROGUNIDAD", schema = "DELPHOS")
public class EvaRelacionProgramacionDidacticaUnidadProgramacion extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_RELPROGUNIDAD")
	@SequenceGenerator(name = "SQ_EVA_RELPROGUNIDAD", sequenceName = "SQ_EVA_RELPROGUNIDAD", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	@Column(name="ID_RELPROGUNIDAD")
	private Long id;
	
	// ---------- Relationships -----------

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROGDIDAC")
	private EvaProgramacionDidactica programacionDidactica;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_UNIDADPROG")
	private EvaUnidadProgramacion unidadProgramacion;

}