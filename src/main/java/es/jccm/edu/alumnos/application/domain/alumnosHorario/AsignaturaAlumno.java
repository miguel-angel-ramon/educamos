package es.jccm.edu.alumnos.application.domain.alumnosHorario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AsignaturaAlumno { 
	  @Id
	  private Long id; // Este ID es ficticio, ya que tu consulta no tiene campo ID. Debes asignar uno válido si persistes esta entidad.

	@Column(name = "abreviatura")
	private String abreviatura;

	@Column(name = "descripcion")
	private String descripcion;

}
