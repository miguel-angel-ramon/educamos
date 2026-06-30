package es.jccm.edu.pdc.application.domain.planActuacion.tablas;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TLPDCINFIMPAC", schema = "DELPHOS_SEGEDU")
@Getter
@Setter
public class InformacionImpactoPDC extends BaseAudited {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_SEGEDU.TLS_INFIMPAC")
	@SequenceGenerator(name = "DELPHOS_SEGEDU.TLS_INFIMPAC", sequenceName = "DELPHOS_SEGEDU.TLS_INFIMPAC", allocationSize = 1)
    @Column(name = "ID_INFIMPAC")
    private Long idinfimpac;

    @Column(name = "X_CENTRO")
    private Integer centro;

    @Column(name = "X_CUEPUB")
    private Integer cuepub;

    @Column(name = "C_ANNO")
    private Integer anno;

    @Column(name = "D_MEJORA_GESTION", length = 4000)
    private String mejoragestion;

    @Column(name = "D_MEJORA_APREN", length = 4000)
    private String mejoraaprendizaje;

    @Column(name = "D_OTRAS_TAREAS", length = 4000)
    private String otrastareas;
}
