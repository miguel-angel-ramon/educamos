package es.jccm.edu.evaluacion.application.domain.ponderacion.enums;

public enum SaveType {
	TODO(0),
	COMPETENCIAS(1),
	CRITERIOS(2);

	public final Integer value;

	SaveType(Integer value) {
		this.value = value;
	}
}