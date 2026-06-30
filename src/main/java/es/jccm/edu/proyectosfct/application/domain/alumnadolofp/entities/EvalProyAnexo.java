package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "FCT_EVAL_PROY_ANEXO")
@Data
@EqualsAndHashCode(callSuper = true)
public class EvalProyAnexo extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_fct_eval_proy_anexo")
    @SequenceGenerator(name = "seq_fct_eval_proy_anexo", sequenceName = "SQ_FCT_EVAL_PROY_ANEXO", allocationSize = 1)
    @Column(name = "ID_EVAL_PROY_ANEXO", nullable = false)
    private Long id;

    @Column(name = "X_MATRICULA", nullable = false)
    private Long xMatricula;

    @Column(name = "X_EMPRESA", nullable = false)
    private Long xEmpresa;
    @Column(name = "ID_EVAFIR_RODAL")
    private String idEvaFirRodal;

    @Column(name = "DS_EVAFIR_FICHERO")
    private String dsEvaFirFichero;

    @Column(name = "F_FIRMA")
    private Date fFirma;

    @Column(name = "C_USUFIRMA")
    private Long cUsuFirma;

    @Column(name = "LG_ACTUALIZADO")
    private Integer lgActualizado;
}

