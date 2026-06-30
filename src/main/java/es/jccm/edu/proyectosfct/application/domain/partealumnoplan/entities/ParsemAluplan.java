package es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FCT_PARSEM_ALUPLAN")
public class ParsemAluplan extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FCT_PARSEM_ALUPLAN")
    @SequenceGenerator(name = "SQ_FCT_PARSEM_ALUPLAN", sequenceName = "SQ_FCT_PARSEM_ALUPLAN", allocationSize = 1)
    @Column(name = "ID_PARSEM_ALUPLAN")
    private Long idParsemAluplan;

    @Column(name = "ID_PARSEM_RODAL")
    private String idParsemRodal;

    @Column(name = "TX_PARSEM_FICHERO")
    private String txParsemFichero;

    @Column(name = "F_INISEM", nullable = false)
    private Date fInisem;

    @Column(name = "ID_CONVPROY_ALU", nullable = false)
    private Long idConvproyAlu;

    @Column(name = "F_REGISTRO")
    private Date fRegistro;

    @Column(name = "CD_VISTA")
    private String cdVista;

    @Column(name = "LG_ACTUALIZADO")
    private Integer lgActualizado;

    @Column(name = "X_USUARIOCREACION")
    private Long xUsuarioCreacion;

}
