package es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.EvaAulaVirtualUsuario;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.QEvaAulaVirtualUsuario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaAulaVirtualUsuarioRepository extends AbstractRepository<EvaAulaVirtualUsuario, Long, QEvaAulaVirtualUsuario> {
}