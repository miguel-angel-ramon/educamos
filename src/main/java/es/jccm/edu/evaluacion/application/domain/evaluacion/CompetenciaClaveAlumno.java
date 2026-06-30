package es.jccm.edu.evaluacion.application.domain.evaluacion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class CompetenciaClaveAlumno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long idCompetenciaClave;
	
	private String descCompetenciaClave;
	
	private String abrevCompetenciaClave;
	
	private Long idCalifica;
	
	private String descCal;

	private Long nota;

	private String aprueba;
	
	@OneToMany(mappedBy = "idDescriptorOperativo")
	private List<DescriptorOperativoAlumno> descriptoresOperativos;
	
	private Long idMatricula;
	
	private Long idConvCentroOmc;
	
	private Long idValComClaAluTemp;
}
