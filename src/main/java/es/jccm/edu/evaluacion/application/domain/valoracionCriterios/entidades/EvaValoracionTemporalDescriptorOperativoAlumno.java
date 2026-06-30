package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "EVA_VALDESOPEALUTEMP")
@Data
@EqualsAndHashCode(callSuper = true)
public class EvaValoracionTemporalDescriptorOperativoAlumno extends BaseAudited {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_VALDESOPEALUTEMP")
    @SequenceGenerator(name = "SQ_EVA_VALDESOPEALUTEMP", sequenceName = "SQ_EVA_VALDESOPEALUTEMP", allocationSize = 1)
    @Column(name = "ID_VALDESOPEALUTEMP", nullable = false)
    private Long id;

    @Column(name = "X_DESOPERATIVO", nullable = false)
    private Long idDescriptorOperativo;

    @Column(name = "X_MATRICULA", nullable = false)
    private Long idMatricula;
    
    // ---------- Relationships -----------
    
    @OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CALIFICA")
	private EvaCalificacion calificacion;
    
}

