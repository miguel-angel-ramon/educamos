package es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_ENVIOS_COTMES_GESTORA_LOG")
@SequenceGenerator(
        name = "SQ_FCT_ENVIOS_COTMES_GESTORA_LOG",
        sequenceName = "SQ_FCT_ENVIOS_COTMES_GESTORA_LOG",
        allocationSize = 1
)
public class EnviosCotizaMesGestoraLog extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_ENVIOS_COTMES_GESTORA_LOG")
    @Column(name = "ID_ENVIO_COTIZA")
    private Long idEnvioCotiza;

    // Campos RegisterDaysContributed

    @Column(name = "ID_ALTASS_PROX")
    private Long idAlu;

    @Column(name = "NU_MONTH")
    private Integer month;
    
    @Column(name = "CD_AFILIATION_NUMBER")
    private String afiliation_number;
    
    @Column(name = "NU_YEAR")
    private Integer year;
    
    @Column(name = "NU_ABSENT_IT")
    private Integer absent_it;
    
    @Column(name = "LG_ERASMUS_SCHOLARSHIP")
    private Integer erasmusScholarship;

    @Column(name = "NU_DAYS")
    private Integer days;

    @Column(name = "DS_TIPO_EMPRESA")
    private String tipoEmpresa;

    @Column(name = "NU_ABSENT_BY_SON")
    private Integer absent_by_son;    

    @Column(name = "NU_ABSENT_ERASMUS")
    private Integer absent_erasmus;    

    @Column(name = "DS_WARNINGS")
    private String warnings;

    // Campos adicionales para el log

    @Column(name = "F_ENVIO", nullable = false)
    private Date fechaEnvio;

    @Column(name = "DS_TIPO_ENVIO", nullable = false)
    private String tipoEnvio;
}
