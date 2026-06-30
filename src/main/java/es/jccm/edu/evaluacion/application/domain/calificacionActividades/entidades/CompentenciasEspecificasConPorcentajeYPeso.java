package es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@Entity
public class CompentenciasEspecificasConPorcentajeYPeso extends BaseAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idComEsp;

	private Long idValComEspTemp;

	private Long idCalifica;

	private int numero;

	private Integer peso;

	private Float porcentaje;
	

}