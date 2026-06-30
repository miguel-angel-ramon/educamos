package es.jccm.edu.horarios.application.domain.calendario;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Calendario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;

	private Long anyo;
	
	private Date fechaFiesta;

	private String tipoFiesta;
	
	private String ambito;

	private Long usuarioCreacion;
	
	private Long usuarioActualiza;

	private Date fechaActualizacion;

}
