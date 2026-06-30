package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "EVA_RELUNIPROGSABB", schema = "DELPHOS")
//@SequenceGenerator(name = "SQ_EVA_RELUNIPROGSABB", sequenceName = "SQ_EVA_RELUNIPROGSABB", allocationSize = 1)
public class EvaRelacionUnidadProgramacionSaberBasico extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_RELUNIPROGSABB", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVA_RELUNIPROGSABB")
    @SequenceGenerator(name = "SQ_EVA_RELUNIPROGSABB", sequenceName = "SQ_EVA_RELUNIPROGSABB", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    private Long id;
	
	// ---------- Relaciones -----------

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADPROG")
    private EvaUnidadProgramacion unidadProgramacion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ID_SABB")
    private EvaSaberBasico saberBasico;
}

