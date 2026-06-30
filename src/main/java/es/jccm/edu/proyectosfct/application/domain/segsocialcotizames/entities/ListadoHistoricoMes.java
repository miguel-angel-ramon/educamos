package es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class ListadoHistoricoMes extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "nombreCompleto")
    private String nombreCompletoMes;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long idCotizaMes;

    @Column(name = "dsWarnings")
    private String dsWarningsMes;

    @Column(name = "NU_DIAS_REAL")
    private Integer nuDiasRealMesProg;

    @Column(name = "NU_DIAS_INTE")
    private Integer nuDiasInteMesProg;

    @Column(name = "NU_DIAS_NACU")
    private Integer nuDiasNacuMesProg;

    @Column(name = "NU_DIAS_INTE_ERA")
    private Integer nuDiasInteEraMesProg;

    @Column(name = "fechaEnvio")
    private String fechaEnvioMes;

    @Column(name = "nuPeticion")
    private String nuPeticionMes;

    @Schema(name = "tipo")
    private String tipo;

}
