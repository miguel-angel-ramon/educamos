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
@Table(name = "TLRELCOMPESMAT", schema = "DELPHOS")
public class EvaRelacionCompetenciaEspecificaMateria extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLS_RCMXRELCOMPESMAT")
	@SequenceGenerator(name = "TLS_RCMXRELCOMPESMAT", sequenceName = "TLS_RCMXRELCOMPESMAT", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
	@Column(name="X_RELCOMPESMAT")
	private Long id;
	
	@Column(name="X_MATERIAOMG")
	private Long idMateriaOmg;
	
	// ---------- Relationships -----------

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_COMESP")
	private EvaCompetenciaEspecificaDidactica competenciaEspecificaDidactica;

}