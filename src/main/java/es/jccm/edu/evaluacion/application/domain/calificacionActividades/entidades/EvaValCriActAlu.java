package es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@Entity
public class EvaValCriActAlu extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idCalifica;

	private String calificacion;

	private int numero;

	private int peso;

	private float porcentaje;

	private Date fechaActualiza;


}