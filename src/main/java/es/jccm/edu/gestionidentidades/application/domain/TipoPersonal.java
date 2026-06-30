package es.jccm.edu.gestionidentidades.application.domain;

public enum TipoPersonal {
	PROFESOR("PROFESOR"),
	CARGO("CARGO"),
	NO_DOCENTE("NO_DOCENTE");
	
	private String id;
	
	TipoPersonal(String id) {
		this.id=id;
	}

	public String getId() {
		return id;
	}
	
	public static TipoPersonal findById(String id) {
		for(TipoPersonal tipoPersonal:TipoPersonal.values()) {
			if(tipoPersonal.id.equals(id)) {
				return tipoPersonal;
			}
		}
		throw new IllegalArgumentException("tipo de personal no encontrado: "+id);
	}
	
}
