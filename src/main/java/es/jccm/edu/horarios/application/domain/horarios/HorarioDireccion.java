package es.jccm.edu.horarios.application.domain.horarios;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class HorarioDireccion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idTramo;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private String horaInicio;
	
	private String horaFin;

	private Integer diaSemana;
	
	private String titulo;

}
