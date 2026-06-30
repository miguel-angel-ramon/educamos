package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "EVA_VALCOMCLAALUTEMP")
@Data
@EqualsAndHashCode(callSuper = true)
public class EvaValoracionTemporalCompetenciaClaveAlumno extends BaseAudited {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_VALCOMCLAALUTEMP")
    @SequenceGenerator(name = "SQ_EVA_VALCOMCLAALUTEMP", sequenceName = "SQ_EVA_VALCOMCLAALUTEMP", allocationSize = 1)
    @Column(name = "ID_VALCOMCLAALUTEMP", nullable = false)
    private Long id;

    @Column(name = "X_COMCLAVE", nullable = false)
    private Long idCompetenciaClave;

    @Column(name = "X_MATRICULA", nullable = false)
    private Long idMatricula;
    
    // ---------- Relationships -----------
    
    @OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CALIFICA")
	private EvaCalificacion calificacion;
    
}

