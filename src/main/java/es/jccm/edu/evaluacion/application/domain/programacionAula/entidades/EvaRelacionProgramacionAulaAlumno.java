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
@Table(name = "EVA_RELPROGAULALUM", schema = "DELPHOS")
public class EvaRelacionProgramacionAulaAlumno extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_RELPROGAULALUM")
	@SequenceGenerator(name = "SQ_EVA_RELPROGAULALUM", sequenceName = "SQ_EVA_RELPROGAULALUM", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	@Column(name="ID_RELPROGAULALUM")
	private Long id;
	
	// ---------- Relationships -----------

	@Column(name="ID_PROGAULA")
	private Long programacionAula;

	@Column(name="X_UNIDAD")
	private Long unidadCentro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_MATRICULA")
	private EvaMatriculaAlumno matriculaAlumno;
	
	@Column(name="ID_USUARIO_MOODLE")
	private Long idUsuarioMoodle;
	
}