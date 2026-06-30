package es.jccm.edu.horarios.application.domain.horarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class HorarioGrupoActividadList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idGrupoActividad;
	
	private Long idTramo;
	
	private Long idHorario;

	private Date fechaInicio;

	private Date fechaFin;
	
	private Integer diaSemana;
	
	private String horaInicio;
	
	private String horaFin;
	
	private String descripcion;
	
	private String dependencia;

}
