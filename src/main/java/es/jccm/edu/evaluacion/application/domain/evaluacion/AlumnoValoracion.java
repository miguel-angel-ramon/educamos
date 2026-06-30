package es.jccm.edu.evaluacion.application.domain.evaluacion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class AlumnoValoracion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	private Long idMatricula;
	
	private Long idAlumno;
	
	private Long idEtapa;
	
	private Long idConvCentroOmc;
	
	private String numEscolar;

	private String nombreAlumno;
	
	private String nombreEtapa;
	
	private String nombreConvocatoria;
	
	private Boolean todasMateriasEvaluadas;
	
	private byte[] foto;

	private Integer acnee;

	private String nivelCurricular;
	
	private Long idEtapaAdaptacion;

	@OneToMany(mappedBy = "idCompetenciaClave")
	private List<ValoracionCompetenciaClaveAlumno> valoracionesCompetenciasClave;

}
