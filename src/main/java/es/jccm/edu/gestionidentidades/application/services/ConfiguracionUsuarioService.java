package es.jccm.edu.gestionidentidades.application.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.ConfiguracionUsuarioDto;
import es.jccm.edu.gestionidentidades.adapter.out.repository.ConfiguracionUsuarioRepository;
import es.jccm.edu.gestionidentidades.application.domain.ConfiguracionUsuario;
import es.jccm.edu.gestionidentidades.application.ports.in.IConfiguracionUsuarioService;

@Transactional
@Service
public class ConfiguracionUsuarioService implements IConfiguracionUsuarioService {

	 @Autowired
	 private ModelMapper modelMapper;
	 
	 @Autowired
	 private ConfiguracionUsuarioRepository configuracionUsuarioRepository;
	 
	 @Override
	 public ConfiguracionUsuarioDto saveUpdate(ConfiguracionUsuarioDto configuracionUsuarioDto) {
		 
		 ConfiguracionUsuario configuracionUsuario = toConfiguracionUsuarioEntity(configuracionUsuarioDto);
		 
	     return toConfiguracionUsuarioDto(configuracionUsuarioRepository.save(configuracionUsuario));
	    }
    
     private ConfiguracionUsuarioDto toConfiguracionUsuarioDto(ConfiguracionUsuario configuracionUsuario) {
        return modelMapper.map(configuracionUsuario, ConfiguracionUsuarioDto.class);
     }

     private ConfiguracionUsuario toConfiguracionUsuarioEntity(ConfiguracionUsuarioDto configuracionUsuarioDto) {
        return modelMapper.map(configuracionUsuarioDto, ConfiguracionUsuario.class);
     }

}
