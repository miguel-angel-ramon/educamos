package es.jccm.edu.alumnos.application.domain.evaluacion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ConvUnidad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idConvUnidad;
	
	private Date descripcion;
	
	private String estado;

}
