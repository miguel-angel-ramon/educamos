package es.jccm.edu.evaluacion.application.domain.programacionDidactica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EVA_PROGDIDAC")
@Getter
@Setter
public class ProgramacionDidactica extends BaseAudited implements Serializable {
    
    private static final long serialVersionUID = 1234L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progdidac_seq")
    @SequenceGenerator(name = "progdidac_seq", sequenceName = "SQ_EVA_PROGDIDAC", allocationSize = 1)
    @Column(name = "ID_PROGDIDAC")
    private Long id;
    
    @Column(name = "X_MATERIAOMG", nullable = false)
    private Long materiaomg;
    
    @Column(name = "X_OFERTAMATRIG", nullable = false)
    private Long ofertamatrig;
    
    @Column(name = "X_CENTRO", nullable = false)
    private Long centro;
    
    @Column(name = "NU_ANNO", nullable = false)
    private Integer anno;
    
    @Column(name = "LG_ACNEAE", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private Integer acneae = 0;
    
    @Column(name = "LG_CERRADA", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private Integer cerrada = 0;
    
}
