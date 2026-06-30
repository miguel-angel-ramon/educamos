package es.jccm.edu.proyectosfct.application.domain.historicomesprogramas.entities;

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
@Table(name = "FCT_HISTORICOMES_PROG")
public class HistoricoMesProg extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_HISTORICOMES_PROG")
    @SequenceGenerator(name = "SQ_FCT_HISTORICOMES_PROG", sequenceName = "SQ_FCT_HISTORICOMES_PROG", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_HISTORICOMES_PROG")
    private Long idHistoricoMesProg;

    @Column(name = "NU_MES")
    private Integer nuMesProg;

    @Column(name = "NU_DIAS_REAL")
    private Integer nuDiasRealMesProg;

    @Column(name = "NU_DIAS_INTE")
    private Integer nuDiasInteMesProg;

    @Column(name = "NU_DIAS_NACU")
    private Integer nuDiasNacuMesProg;

    @Column(name = "NU_DIAS_INTE_ERA")
    private Integer nuDiasInteEraMesProg;

    @Column(name = "LG_VALTUT")
    private Integer lgValTutMesProg;

    @Column(name = "LG_VALCEN")
    private Integer lgValCenMesProg;

    @Column(name = "LG_VALDEL")
    private Integer lgValDelMesProg;

    @Column(name = "F_ENVIOSS")
    private Date fechaEnvioMesProg;

    @Column(name = "NU_PETICION")
    private Integer nuPeticionMesProg;

    @Column(name = "DS_WARNINGS")
    private String dsWarningsMesProg;

    @Column(name = "ID_USUARIO_ULT")
    private Long idUsuarioUltMesProg;

    // ---------- Relationships -----------

    @Column(name = "ID_COTIZAMES_PROG")
    private Long idCotizaMesProg;

}
