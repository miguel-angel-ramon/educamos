package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Data
@Entity
public class AlumnoAndFaltasList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer idMatricula;

	private String nombreAlumno;

	private String nombreFormateado;

	private Integer idAlumno;

	private String tipoFalta;

	private Integer idMateriaOmg;

	private String delphos;

	private String idNotificacion;

	private String comentarioNotificacion;

	private String motivoNotificacion;

	private String nombreUsuarioNotificacion;

	@Lob
	private byte[] foto;

}
