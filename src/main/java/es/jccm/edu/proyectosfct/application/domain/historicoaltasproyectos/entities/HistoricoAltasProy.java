package es.jccm.edu.proyectosfct.application.domain.historicoaltasproyectos.entities;

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
@Table(name="FCT_HISTORICOALTAS_PROY")
public class HistoricoAltasProy extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_HISTORICOALTAS_PROY")
    @SequenceGenerator(name = "SQ_FCT_HISTORICOALTAS_PROY", sequenceName = "SQ_FCT_HISTORICOALTAS_PROY", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_HISTORICOALTAS_PROY")
    private Long id;

    @Column(name = "F_INICIO")
    private Date fechaInicio;

    @Column(name = "F_FIN")
    private Date fechaFin;

    @Column(name = "LG_ERASMUS_CB")
    private Integer lgErasumusCb;

    @Column(name = "LG_ERASMUS_SB")
    private Integer lgErasmusSb;

    @Column(name = "LG_VALTUT")
    private Integer lgValTut;

    @Column(name = "LG_VALCEN")
    private Integer lgValCen;

    @Column(name = "LG_VALDEL")
    private Integer lgValDel;

    @Column(name = "DS_WARNINGS")
    private String dsWarnings;

    @Column(name = "F_ENVIOSS")
    private Date fechaEnvio;

    @Column(name = "LG_ANULADO")
    private Integer lgAnulado;

    @Column(name = "F_AUDITORIA")
    private Date fechaAuditoria;

    @Column(name = "NU_PETICION")
    private Integer nuPeticion;

    @Column(name = "X_USUARIO_ULT")
    private Long xusuarioUlt;
    
    @Column(name = "ID_GESTORA")
    private Long idGestora;

    // ---------- Relationships -----------

    @Column(name = "ID_ALTASS_PROY")
    private Long idAltassProy;

}