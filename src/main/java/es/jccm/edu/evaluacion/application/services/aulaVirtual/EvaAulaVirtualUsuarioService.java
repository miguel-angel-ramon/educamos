package es.jccm.edu.evaluacion.application.services.aulaVirtual;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualUsuarioDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual.EvaAulaVirtualUsuarioRepository;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.EvaAulaVirtualUsuario;
import es.jccm.edu.evaluacion.application.ports.in.aulaVirtual.IEvaAulaVirtualUsuarioService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaAulaVirtualUsuarioService implements IEvaAulaVirtualUsuarioService {
	
	@Autowired
	private EvaAulaVirtualUsuarioRepository aulaVirtualUsuarioRepository;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public AulaVirtualUsuarioDTO getAulaVirtualUsuarioById(Long idAulaVirtualUsuario) {
		Optional<EvaAulaVirtualUsuario> aulaVirtualUsuario = aulaVirtualUsuarioRepository.findById(idAulaVirtualUsuario);
		if(aulaVirtualUsuario.isPresent()) {
			return modelMapper.map(aulaVirtualUsuario.get(), AulaVirtualUsuarioDTO.class);
		} else {
			return null;
		}
	}
}
