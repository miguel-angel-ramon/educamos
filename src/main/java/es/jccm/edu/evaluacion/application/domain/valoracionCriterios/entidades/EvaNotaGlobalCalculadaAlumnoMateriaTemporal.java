package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "EVA_NOTCALMATTEMP")
@Data
@EqualsAndHashCode(callSuper = true)
public class EvaNotaGlobalCalculadaAlumnoMateriaTemporal extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVA_NOTCALMATTEMP_SEQ")
    @SequenceGenerator(name = "EVA_NOTCALMATTEMP_SEQ", sequenceName = "SQ_EVA_NOTCALMATTEMP", allocationSize = 1)
    @Column(name = "X_NOTCALMATTEMP", nullable = false)
    private Long id;

    @Column(name = "X_MATMATRICULA", nullable = false)
    private Long matMatricula;

    @Column(name = "N_NOTA", precision = 4, scale = 2)
    private Double nota;
    
    // ---------- Relationships -----------
    
    @OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CALIFICA")
	private EvaCalificacion calificacion;
}

