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

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaEmpleado;
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
@Table(name = "EVA_RELPROGAULAEMP", schema = "DELPHOS")
public class EvaRelacionProgramacionAulaEmpleado extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_RELPROGAULAEMP")
	@SequenceGenerator(name = "SQ_EVA_RELPROGAULAEMP", sequenceName = "SQ_EVA_RELPROGAULAEMP", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	@Column(name="ID_RELPROGAULAEMP")
	private Long id;
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_EMPLEADO")
	private EvaEmpleado empleado;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROGAULA")
	private EvaProgramacionAula programacionAula;

}