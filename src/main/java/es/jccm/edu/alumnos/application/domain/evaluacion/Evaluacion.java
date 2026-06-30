package es.jccm.edu.alumnos.application.domain.evaluacion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	private Long idMatricula;
	
	private Long idAlumno;

	private String nombreAlumno;

	@OneToMany(mappedBy = "idMatMatricula")
	private List<Calificacion> calificaciones;

	private byte[] foto;
	
	private Long idEstadoConvocatoria;
	
	private String nombreEstadoConvocatoria;

	private Long acnee;

	private String nivelCurricular;

}
