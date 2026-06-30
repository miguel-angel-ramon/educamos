package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TLVALCRIALU")
public class ValCriAlu extends BaseAudited {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLVALCRIALU_SEQ")
    @SequenceGenerator(name = "TLVALCRIALU_SEQ", sequenceName = "TLS_TLVALCRIALU", allocationSize = 1)
    @Column(name = "X_VALCRIALU", nullable = false)
    private Long valCriAluId;

    @Column(name = "X_PONDERACION", nullable = false)
    private Long ponderacion;

    @Column(name = "X_CRIEVA", nullable = false)
    private Long crieva;

    @Column(name = "X_CALIFICA")
    private Long califica;

    @Column(name = "X_MATMATRICULA", nullable = false)
    private Long matMatricula;

    @Column(name = "X_CONVCENTROOMC", nullable = false)
    private Long convCentroOmc;
}
