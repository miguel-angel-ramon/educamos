package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "EVA_PROGDIDAC", schema = "DELPHOS")
public class EvaProgramacionDidactica extends BaseAudited implements Serializable {
    
    private static final long serialVersionUID = 1234L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progdidac_seq")
    @SequenceGenerator(name = "progdidac_seq", sequenceName = "SQ_EVA_PROGDIDAC", allocationSize = 1)
    @Column(name = "ID_PROGDIDAC")
    private Long id;
    
	@NotNull
    @Column(name = "X_MATERIAOMG")
    private Long materiaomg;
    
	@NotNull
    @Column(name = "X_OFERTAMATRIG")
    private Long ofertamatrig;
    
	@NotNull
    @Column(name = "X_CENTRO")
    private Long centro;
    
	@NotNull
    @Column(name = "NU_ANNO")
    private Integer anno;
    
    @Column(name = "LG_ACNEAE", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private Integer acneae = 0;
    
    @Column(name = "LG_CERRADA", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private Integer cerrada = 0;
    
    @Column(name = "X_NIVEADAP")
    private Long niveadap;

    @Column(name = "X_MATERIAOMGADAP")
    private Long idMateriaOmgAdap;
    
    // ---------- Relationships -----------
    
    @OneToMany(mappedBy = "programacionDidactica", fetch = FetchType.LAZY)
	private List<EvaRelacionProgramacionDidacticaUnidadProgramacion> relacionesProgramacionDidacticaUnidadProgramacion;
    
}
