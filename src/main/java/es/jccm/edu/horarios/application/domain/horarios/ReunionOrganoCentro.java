package es.jccm.edu.horarios.application.domain.horarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ReunionOrganoCentro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long idReunion;
	
	private String titulo;
	
	private String tipo;

	private Date fecha;

	private String horaInicio;
	
	private String horaFin;

}
