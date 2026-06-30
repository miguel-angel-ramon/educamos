package es.jccm.edu.evaluacion.application.domain.evaluacion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class ValoracionCompetenciaClaveAlumno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idCompetenciaClave;
	
	private Long id;
	
	private String nombreCompetenciaClave;
	
	private Long idCalifica;
	
	private String descCal;

	private Long nota;

	private String aprueba;
	
	private String descDetCal;
	
	@OneToMany(mappedBy = "idDescriptorOperativo")
	private List<ValoracionDescriptorOperativoAlumno> valoracionesDescriptoresOperativos;
	
}
