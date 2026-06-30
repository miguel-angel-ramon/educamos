package es.jccm.edu.evaluacion.application.domain.evaluacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class DescriptorOperativoAlumno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idDescriptorOperativo;
	
	private String descDescriptorOperativo;
	
	private String abrevDescriptorOperativo;
	
	private Long idCalifica;
	
	private String descCal;

	private Long nota;

	private String aprueba;
	
	private Long idMatricula;
	
	private Long idConvCentroOmc;

	private Boolean notaCambiada = false;
	
	private Long idValDesOpeAluTemp;
}
