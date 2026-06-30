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
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaPonderacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "EVA_VALCOMALUTEMP")
@Data
@EqualsAndHashCode(callSuper = true)
public class EvaValoracionTemporalCompetenciaEspecificaAlumno extends BaseAudited {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVA_VALCOMALUTEMP_SEQ")
    @SequenceGenerator(name = "EVA_VALCOMALUTEMP_SEQ", sequenceName = "SQ_EVA_VALCOMALUTEMP", allocationSize = 1)
    @Column(name = "X_VALCOMALUTEMP", nullable = false)
    private Long idCompetencia;

    @Column(name = "X_COMESP", nullable = false)
    private Long comEsp;

    @Column(name = "X_MATMATRICULA", nullable = false)
    private Long matMatricula;
    
    @Column(name="X_PONDERACION")
	private Long idPonderacion;
    
    // ---------- Relationships -----------
    
    @OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CALIFICA")
	private EvaCalificacion calificacion;
    
}
