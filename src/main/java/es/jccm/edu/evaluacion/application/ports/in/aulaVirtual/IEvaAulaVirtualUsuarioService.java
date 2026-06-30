package es.jccm.edu.evaluacion.application.ports.in.aulaVirtual;

import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualUsuarioDTO;

public interface IEvaAulaVirtualUsuarioService {
	
	AulaVirtualUsuarioDTO getAulaVirtualUsuarioById(Long idAulaVirtualUsuario);
}
