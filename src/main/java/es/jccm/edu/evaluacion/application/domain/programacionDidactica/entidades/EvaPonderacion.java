package es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaMateriaCursoGenerica;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCompetenciaEspecificaAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCriterioEvaluacionAlumno;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TLPONDERACION", schema = "DELPHOS")
public class EvaPonderacion extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TLS_PONDERACION")
    @SequenceGenerator(name = "TLS_PONDERACION", sequenceName = "TLS_PONDERACION", allocationSize = 1)
    @Column(name = "X_PONDERACION")
    private Long id;

    @Column(name = "D_PONDERACION")
    private String descripcion;

    @NotNull
    @Column(name = "X_MATERIA")
    private Long idMateria;

    @NotBlank
    @Column(name = "L_EDITABLE", columnDefinition = "VARCHAR2(1) DEFAULT 'S'")
    private String editable = "S";
    
 // ---------- Relationships -----------
	
 	@ManyToOne(fetch=FetchType.LAZY)
 	@JoinColumn(name="X_DOCENTE")
 	private EvaEmpleado docente;
    
    @OneToMany(mappedBy = "ponderacion", fetch = FetchType.LAZY)
	private List<EvaRelacionPonderacionCriteriosEvaluacion> relacionesPonderacionCriteriosEvaluacion;
    
    @OneToMany(mappedBy = "ponderacion", fetch = FetchType.LAZY)
   	private List<EvaRelacionPonderacionCompetenciaEspecifica> relacionesCompetenciasEspecificas;
    
    @OneToMany(mappedBy = "ponderacion", fetch = FetchType.LAZY)
   	private List<EvaValoracionCompetenciaEspecificaAlumno> valoracionesCompetenciasEspecificasAlumnos;
    
    @OneToMany(mappedBy = "ponderacion", fetch = FetchType.LAZY)
   	private List<EvaValoracionCriterioEvaluacionAlumno> valoracionesCriteriosEvaluacionAlumnos;

}