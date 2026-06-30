package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "EVA_BLOQSABB", schema = "DELPHOS")
@SequenceGenerator(name = "SQ_EVA_BLOQSABB", sequenceName = "SQ_EVA_BLOQSABB", allocationSize = 1)
public class EvaBloqueSaberBasico extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_BLOQSABB", nullable = false)
    private Long id;

    @Column(name = "DS_BLOQSABB", length = 200)
    private String descripcion;

    @Column(name = "CD_ABREV", length = 15)
    private String abreviatura;

    @Column(name = "N_ORDENPRES")
    private Long orden;
    
    // ---------- Relaciones -----------

    //@OneToMany(mappedBy = "bloqueSaberBasico",  fetch = FetchType.LAZY)
    //private List<EvaSaberBasico> saberesBasicos;
	
}
