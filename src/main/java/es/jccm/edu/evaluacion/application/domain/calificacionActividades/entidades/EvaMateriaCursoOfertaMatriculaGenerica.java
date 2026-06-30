package es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TLMATOFEMATRG", schema = "DELPHOS")
public class EvaMateriaCursoOfertaMatriculaGenerica extends BaseAudited implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="X_MATERIAOMG")
	private Long id;
	
	
	@Column(name="N_HORAS")
	private Long nHoras;
	
	
	@Column(name="N_HORASANUALES")
	private Long nHorasAnuales;
	
	
	// ---------- Relationships -----------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_MATERIAC")
	private EvaMateriaCursoGenerica materiaCurso;

}
