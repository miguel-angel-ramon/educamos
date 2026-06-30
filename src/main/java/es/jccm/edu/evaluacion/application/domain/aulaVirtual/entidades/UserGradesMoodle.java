package es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
public class UserGradesMoodle {

	@OneToMany(fetch=FetchType.LAZY)
	private AlumnoMoodle[] usergrades;


}