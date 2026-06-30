package es.jccm.edu.proyectosfct.application.services.params;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.ParamsFCTRepository;
import es.jccm.edu.proyectosfct.application.domain.usuarios.entities.ParamsFCT;
import es.jccm.edu.proyectosfct.application.ports.in.usuarios.IParamsFCTService;

@Service
public class ParamsFCTService implements IParamsFCTService {	
	
	@Autowired
	private ParamsFCTRepository paramsFCTRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<ParamsFCT> paramsFCT(String vista,  Long idCentro, Long xUsuario, Long xUsuarioComunica, Long oid ) {	
		
		Long idUsuario = xUsuario==null?-1:xUsuario;
		Long idUsuarioComunica = xUsuarioComunica==null?-1:xUsuarioComunica;	
		
		return paramsFCTRepository.getParamsFCT(vista, idCentro, idUsuario, idUsuarioComunica, oid).stream()
				.map(entity -> modelMapper.map(entity, ParamsFCT.class)).collect(Collectors.toList());		
		
	}

	@Override
	public Long getXusuarioComunicaByOid(Long oid, Long xUsuarioComunica) {
		
		if (xUsuarioComunica == null) {
		    return paramsFCTRepository.getXusuarioComunicaByOid(oid);	
		} else {
			return xUsuarioComunica;
		}
	}
	
	@Override
	public Long getXempleadoComunicaByOid(Long oid, Long xEmpleadoComunica) {
		
		if (xEmpleadoComunica == null) {
		    return paramsFCTRepository.getXempleadoComunicaByOid(oid);	
		} else {
			return xEmpleadoComunica;
		}
	}
	
}