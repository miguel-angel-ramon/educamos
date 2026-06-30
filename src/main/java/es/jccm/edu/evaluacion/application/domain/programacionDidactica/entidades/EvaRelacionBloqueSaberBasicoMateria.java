package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "EVA_RELBLOQSABBMAT", schema = "DELPHOS")
@SequenceGenerator(name = "SQ_EVA_RELBLOQSABBMAT", sequenceName = "SQ_EVA_RELBLOQSABBMAT", allocationSize = 1)
public class EvaRelacionBloqueSaberBasicoMateria extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_RELBLOQSABBMAT", nullable = false)
    private Long id;

    @Column(name = "X_MATERIAOMG", nullable = false)
    private Long idMateriaOmg;
    
    // ---------- Relaciones -----------
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ID_BLOQSABB")
    private EvaBloqueSaberBasico bloqueSaberBasico;
}

