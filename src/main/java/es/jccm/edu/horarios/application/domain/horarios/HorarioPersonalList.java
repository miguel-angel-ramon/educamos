package es.jccm.edu.horarios.application.domain.horarios;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class HorarioPersonalList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idRegistro;

	private Date fechaAnotacion;

	private String hora;
	
	private String titulo;
	
	private String dia;

}
