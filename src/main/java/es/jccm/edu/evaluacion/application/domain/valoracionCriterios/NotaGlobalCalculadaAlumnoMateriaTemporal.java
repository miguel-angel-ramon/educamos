package es.jccm.edu.evaluacion.application.domain.valoracionCriterios;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class NotaGlobalCalculadaAlumnoMateriaTemporal extends BaseAudited implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    private Long id;

    private Long matMatricula;

    private Double nota;

    private Long idCalificacion;

    private String descCal;

    private String aprueba;
}

