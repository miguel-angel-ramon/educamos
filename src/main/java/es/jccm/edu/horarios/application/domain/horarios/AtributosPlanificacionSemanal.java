package es.jccm.edu.horarios.application.domain.horarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class AtributosPlanificacionSemanal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long identificador;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private Date fecha;
	
	private String descripcion;
	
	private String tipo;

}
