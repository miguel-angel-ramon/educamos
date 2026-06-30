package es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_ENVIOS_ALUMNOS_GESTORA_LOG")
@SequenceGenerator(
        name = "SQ_FCT_ENVIOS_ALUMNOS_GESTORA_LOG",
        sequenceName = "SQ_FCT_ENVIOS_ALUMNOS_GESTORA_LOG",
        allocationSize = 1
)
public class EnviosAlumnosGestoraLog extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_ENVIOS_ALUMNOS_GESTORA_LOG")
    @Column(name = "ID_ENVIO")
    private Long idEnvio;

    // Propiedades originales de RegisterTraineeStudent

    @Column(name = "ID_ALTASS_PROX")
    private Long idAlu;

    @Column(name = "CD_NIF")
    private String nif;

    @Column(name = "CD_AFILIATION_NUMBER")
    private String afiliation_number;

    @Column(name = "F_REAL_REGISTER")
    private String real_register_date;

    @Column(name = "F_END_COMPLETION")
    private String endDateCompletion;

    @Column(name = "LG_ERASMUSFPDUAL")
    private Integer erasmusfpdualScholarship;

    @Column(name = "CD_CIF")
    private String company_cif;

    @Column(name = "DS_REGIME")
    private String regime;

    @Column(name = "DS_CONTRACT_TYPE")
    private String contract_type;

    @Column(name = "CD_CCODIGO")
    private String ccodigo;

    @Column(name = "CD_CONTRIBUTION_GROUP")
    private String contribution_group;

    @Column(name = "DS_CN_OCCUPATION")
    private String cn_occupation;

    @Column(name = "DS_TIPO_EMPRESA")
    private String tipoEmpresa;

    @Column(name = "LG_CANCEL_REG")
    private Integer cancel_registration;

    @Column(name = "DS_WARNINGS")
    private String warnings;

    @Column(name = "DS_ERRORS")
    private String errors;

    @Column(name = "ID_GESTORA")
    private Long idGestora;

    @Column(name = "F_INICIO_HIST")
    private String inicioHist;

    @Column(name = "F_FIN_HIST")
    private String finHist;

    @Column(name = "ID")
    private Long id;

    @Column(name = "ID_WORKER_ID_EXT")
    private Long worker_id_ext;

    @Column(name = "DS_NAME")
    private String name;
    
    @Column(name = "DS_FECHA_NAC")
    private String birth_date;

    @Column(name = "DS_SEXO")
    private String gender;    

    // Campos adicionales propios del log

    @Column(name = "F_ENVIO", nullable = false)
    private Date fechaEnvio;

    @Column(name = "DS_TIPO_ENVIO", nullable = false)
    private String tipoEnvio;
}
