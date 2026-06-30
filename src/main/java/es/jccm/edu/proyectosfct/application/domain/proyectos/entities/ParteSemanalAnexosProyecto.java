package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="FCT_PARSEM_ANEXOSPROY")
public class ParteSemanalAnexosProyecto extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parsemaneproy_seq")
    @SequenceGenerator(name = "parsemaneproy_seq", sequenceName = "SQ_FCT_PARSEMANEXOSPROY", allocationSize = 1)
    @Column(name="ID_PARSEM_ANEXOSPROY")
    private Long id;

    @Column(name="ID_CONVPROY_ALU")
    private Long idConvProyAlu;

    @Column(name="ID_ANEXO_RODAL")
    private String idAnexoRodal;

    @Column(name="TX_ANEXO_FICHERO")
    private String txAnexoFichero;

}
