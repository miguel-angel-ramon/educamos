package es.jccm.edu.alumnos.application.domain.evaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UnidadConv implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idUnidad;
	
	private String nombre;
	
	private String estado;

	private Long idConvUnidad;
	
	private Long idOfermatrig;
	
	private Long idOfermatric;
	
	private Long idConvOmc;
	
	private String etapa;
	
	private Long idCentro;
	
	private Long idEtapa;

}
