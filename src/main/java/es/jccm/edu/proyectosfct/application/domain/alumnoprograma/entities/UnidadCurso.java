package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UnidadCurso implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="IDOFERTAMATRIC")
	private Long idOfertamatric;
	
	@Column(name="NOMBRE")
	private String nombreUnidad;
	
	@Column(name="TIPO")
	private String tipoUnidad;

	@Column(name="CAPACIDAD")
	private Integer capacidad;
	
	@Column(name="ALUMNOSASIGNADOS")
	private Integer alumnosAsignados;
	
	@Column(name="NOMBRETUTOR")
	private String nombreTutor;
	
	@Column(name="FECHACESE")
	private Date fechaCese;
	
	@Column(name="ACTUACION")
	private String actuacion;
	
	@Column(name="IDMATRICULADELEGADO")
	private Long idMatriculaDelegado;
	
	@Column(name="IDMATRICULASUBDELEGADO")
	private Long idMatriculaSubDelegado;
	
	@Column(name="TURNO")
	private String turno;

}