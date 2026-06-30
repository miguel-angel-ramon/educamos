package es.jccm.edu.documentosGC.application.domain.registrosdocumentodeportiva;

import java.io.Serializable;

public class RegSelDocDeportivaPK implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private Long idIdentificador;
	private Long idClave1;
	
	
	public Long getIdIdentificador() {
		return idIdentificador;
	}
	public void setIdIdentificador(Long idIdentificador) {
		this.idIdentificador = idIdentificador;
	}
	public Long getIdClave1() {
		return idClave1;
	}
	public void setIdClave1(Long idClave1) {
		this.idClave1 = idClave1;
	}
	

}
