package es.jccm.edu.horarios.application.domain.horarios;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TotalHorasList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String horasLec;

	private String horasNoLec;
	
	private String horasCompl;

}
