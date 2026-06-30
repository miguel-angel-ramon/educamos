package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TLCOMESP", schema = "DELPHOS")
public class EvaCompetenciaEspecificaDidactica extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "X_COMESP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLS_TLCOMESP")
    @SequenceGenerator(name = "TLS_TLCOMESP", sequenceName = "TLS_TLCOMESP", initialValue = 1, allocationSize = 1, schema = "DELPHOS")
    private Long id;

    @Column(name = "D_COMESP")
    @Size(max=750, message = "No puede superar los 750 caracteres")
    private String descripcion;

    @Column(name = "T_ABREV")
    @Size(max=20, message = "No puede superar los 20 caracteres")
    private String abrev;

    @Column(name = "X_CICLO", nullable = false)
    private Long idCiclo;

    @Column(name = "N_ORDENPRES", length = 9)
    private Long nOrdenPres;
    
}
