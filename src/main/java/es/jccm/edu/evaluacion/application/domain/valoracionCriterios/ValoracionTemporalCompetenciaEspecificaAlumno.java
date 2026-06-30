package es.jccm.edu.evaluacion.application.domain.valoracionCriterios;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ValoracionTemporalCompetenciaEspecificaAlumno extends BaseAudited {

    @Id
    private Long comEsp;

    private Long idCompetencia;

    private Long matMatricula;

	private Long idPonderacion;

    private String descCal;

    private String aprueba;

    private Long nota;
    
}
