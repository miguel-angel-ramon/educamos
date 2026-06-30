package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades;

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

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TLVALDESOPEALU", schema = "DELPHOS")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EvaValoracionDescriptorOperativoAlumno extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "VALDESOPEALU_SEQ", sequenceName = "TLS_TLVALDESOPEALU", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VALDESOPEALU_SEQ")
    @Column(name = "X_VALDESOPEALU", nullable = false)
    private Long id;

    @Column(name = "X_DESOPERATIVO", nullable = false)
    private Long idDescriptorOperativo;

    @Column(name = "X_MATRICULA", nullable = false)
    private Long idMatricula;

    @Column(name = "X_CONVCENTROOMC", nullable = false)
    private Long idConvCentroOmc;
    
    // ---------- Relationships -----------
    
    @OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="X_CALIFICA")
	private EvaCalificacion calificacion;

}
