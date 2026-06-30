package es.jccm.edu.evaluacion.application.domain.evaluacion;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AlumnoEvaluacionSel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	private Long idMatricula;
	
	private Long idAlumno;
	
	private Long idEtapa;
	
	private Long idCiclo;
	
	private Long idUnidad;

	private String nombreAlumno;
	
	private String nombreEtapa;
	
	private String nombreCiclo;
	
	private String nombreUnidad;
	
	private Integer acnee;
	
	private String nivelCurricular;

}
