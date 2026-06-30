package es.jccm.edu.ausencias.application.domain.guardias;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class GuardiasProfesores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idTramo;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private String horaInicio;
	
	private String horaFin;

	private Integer diaSemana;
	
	private Integer numEmpleados;

}
