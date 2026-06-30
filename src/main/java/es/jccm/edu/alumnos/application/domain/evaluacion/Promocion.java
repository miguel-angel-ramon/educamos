package es.jccm.edu.alumnos.application.domain.evaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Promocion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idPromocion;
	
	private Long idEstado;
	
	private String estado;
	
	private String fechaSesion;

}
