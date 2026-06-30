package es.jccm.edu.pdc.application.domain.planActuacion.tablas;

import javax.persistence.*;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TLPDCINFSEG", schema = "DELPHOS_SEGEDU")
@Getter
@Setter
public class InformacionSeguimientoPDC extends BaseAudited {
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELPHOS_SEGEDU.TLS_INFSEG")
	@SequenceGenerator(name = "DELPHOS_SEGEDU.TLS_INFSEG", sequenceName = "DELPHOS_SEGEDU.TLS_INFSEG", allocationSize = 1)
    @Column(name = "ID_INFSEG")
    private Long idinfseg;

    @Column(name = "D_OBSERVACION", length = 4000)
    private String observacion;

    @Column(name = "D_PROPUESTA", length = 4000)
    private String propuesta;

    @Column(name = "X_CENTRO")
    private Integer centro;

    @Column(name = "X_CUEPUB")
    private Integer cuepub;

    @Column(name = "X_COMPETENCIA")
    private Integer competencia;

    @Column(name = "C_ANNO")
    private Integer anno;


    
}

