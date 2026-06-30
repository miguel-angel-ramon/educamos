package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "TLRELSISETA", schema = "DELPHOS")
@Data
@EqualsAndHashCode(callSuper = true)
public class EvaRelacionSistemaCalificacionEtapaEducativa extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLS_RELSISETA")
	@SequenceGenerator(name = "TLS_RELSISETA", sequenceName = "TLS_RELSISETA", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "X_RELSISETA", nullable = false)
    private Long id;

    @Column(name = "X_SISTCAL", nullable = false)
    private Long idSistCal;

    @Column(name = "X_ETAPA", nullable = false)
    private Long idEtapa;
    
}
