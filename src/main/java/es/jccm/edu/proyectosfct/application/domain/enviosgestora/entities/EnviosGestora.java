package es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_ENVIOS_GESTORA")
public class EnviosGestora extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_ENVIOS_GESTORA")
    @SequenceGenerator(name = "SQ_FCT_ENVIOS_GESTORA", sequenceName = "SQ_FCT_ENVIOS_GESTORA", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_ENVIOS_GESTORA")
    private Long idEnviosGestora;

    @Column(name = "F_FIN")
    private Date fechaFin;
    
    @Column(name = "F_ENVIO")
    private Date fechaEnvio;

    @Column(name = "ID_USUARIO_ULT")
    private Long idUsuarioUlt;

    @Column(name = "CD_RESULTADO")
    private String cdResultado;

    // ---------- Relationships -----------

    @Column(name = "X_CENTRO")
    private Long xCentro;

    @Column(name = "NU_ANNO")
    private Integer nuAnno;
}
