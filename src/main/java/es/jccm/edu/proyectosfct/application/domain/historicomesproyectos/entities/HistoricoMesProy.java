package es.jccm.edu.proyectosfct.application.domain.historicomesproyectos.entities;

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
@Table(name = "FCT_HISTORICOMES_PROY")
public class HistoricoMesProy extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_HISTORICOMES_PROY")
    @SequenceGenerator(name = "SQ_FCT_HISTORICOMES_PROY", sequenceName = "SQ_FCT_HISTORICOMES_PROY", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    @Column(name = "ID_HISTORICOMES_PROY")
    private Long idHistoricoMesProy;

    @Column(name = "NU_MES")
    private Integer nuMesProy;

    @Column(name = "NU_DIAS_REAL")
    private Integer nuDiasRealMesProy;

    @Column(name = "NU_DIAS_INTE")
    private Integer nuDiasInteMesProy;

    @Column(name = "NU_DIAS_NACU")
    private Integer nuDiasNacuMesProy;

    @Column(name = "NU_DIAS_INTE_ERA")
    private Integer nuDiasInteEraMesProy;

    @Column(name = "LG_VALTUT")
    private Integer lgValTutMesProy;

    @Column(name = "LG_VALCEN")
    private Integer lgValCenMesProy;

    @Column(name = "LG_VALDEL")
    private Integer lgValDelMesProy;

    @Column(name = "F_ENVIOSS")
    private Date fechaEnvioMesProy;

    @Column(name = "NU_PETICION")
    private Integer nuPeticionMesProy;

    @Column(name = "DS_WARNINGS")
    private String dsWarningsMesProy;

    @Column(name = "ID_USUARIO_ULT")
    private Long idUsuarioUltMesProy;

    // ---------- Relationships -----------

    @Column(name = "ID_COTIZAMES_PROY")
    private Long idCotizaMesProy;

}
