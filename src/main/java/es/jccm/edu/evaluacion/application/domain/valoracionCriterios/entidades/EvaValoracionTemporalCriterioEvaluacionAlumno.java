package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaPonderacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "EVA_VALCRIALUTEMP")
public class EvaValoracionTemporalCriterioEvaluacionAlumno extends BaseAudited {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVA_VALCRIALUTEMP_SEQ")
    @SequenceGenerator(name = "EVA_VALCRIALUTEMP_SEQ", sequenceName = "SQ_EVA_VALCRIALUTEMP", allocationSize = 1)
    @Column(name = "X_VALCRIALUTEMP", nullable = false)
    private Long id;

    @Column(name = "X_MATMATRICULA", nullable = false)
    private Long matMatricula;
    
    @Column(name="X_PONDERACION", nullable = false)
	private Long idPonderacion;
    
    @Column(name="X_CRIEVA", nullable = false)
	private Long criEva;
    
    // ---------- Relationships -----------
   
    
    @OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CALIFICA")
	private EvaCalificacion calificacion;

}
