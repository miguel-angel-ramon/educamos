package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "EVA_SABB", schema = "DELPHOS")
@SequenceGenerator(name = "SQ_EVA_SABB", sequenceName = "SQ_EVA_SABB", allocationSize = 1)
public class EvaSaberBasico extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_SABB", nullable = false)
    private Long id;


    @Column(name = "ID_BLOQSABB")
    private Long idBloque;



    @Column(name = "DS_SABB", length = 200)
    private String descripcion;

    @Column(name = "CD_ABREV", length = 15)
    private String abreviatura;

    @Column(name = "N_ORDENPRES")
    private Long orden;
    
    // ---------- Relaciones -----------

    @OneToMany(mappedBy = "saberBasico", fetch = FetchType.LAZY)
    private List<EvaRelacionUnidadProgramacionSaberBasico> relacionesUnidadProgramacionSaberBasico;


    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BLOQSABB")
    @JsonBackReference
    private EvaBloqueSaberBasico bloqueSaberBasico;*/

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ID_SABBDEP")
    private EvaSaberBasico saberBasicoDepende;
    
    @OneToMany(mappedBy = "saberBasicoDepende", fetch = FetchType.LAZY)
    private List<EvaSaberBasico> saberesBasicosDependientes;

    // Nuevo campo seleccionado
    @Transient
    private boolean seleccionado = false;


}

