package es.jccm.edu.ausencias.application.domain.profesores;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AusenciasProfesores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    private Long idAusencia;
	
	private Long idEmpleado;
	
	private String motivo;
	
	private Date fechaInicioAusencia;

	private Date fechaFinAusencia;

	private String nombre;
	
	private String telefono;
	
	private String correo;

}
