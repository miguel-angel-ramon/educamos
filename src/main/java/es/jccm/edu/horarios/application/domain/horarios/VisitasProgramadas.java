package es.jccm.edu.horarios.application.domain.horarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class VisitasProgramadas implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@Id
	private String hora;
	
	private Date fecha;
	
	private String profesor;
	
	private String observacion;
	
	private Long xVisprotutleg;

}
