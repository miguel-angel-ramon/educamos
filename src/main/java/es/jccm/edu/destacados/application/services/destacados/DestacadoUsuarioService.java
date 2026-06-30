package es.jccm.edu.destacados.application.services.destacados;

import es.jccm.edu.destacados.adapter.in.rest.destacados.model.DestacadoUsuarioDto;
import es.jccm.edu.destacados.adapter.out.repositories.destacados.DestacadoRepository;
import es.jccm.edu.destacados.adapter.out.repositories.destacados.DestacadoUsuarioRepository;
import es.jccm.edu.destacados.application.domain.destacados.Destacado;
import es.jccm.edu.destacados.application.domain.destacados.DestacadoUsuario;
import es.jccm.edu.destacados.application.ports.in.destacados.IDestacadoUsuarioService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestacadoUsuarioService implements IDestacadoUsuarioService {

    private final DestacadoUsuarioRepository destacadoUsuarioRepository;
    
    private final DestacadoRepository destacadoRepository;

    private final ModelMapper modelMapper;

    public DestacadoUsuarioService(DestacadoUsuarioRepository destacadoUsuarioRepository, ModelMapper modelMapper,DestacadoRepository destacadoRepository) {
        this.destacadoUsuarioRepository = destacadoUsuarioRepository;
        this.destacadoRepository = destacadoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestacadoUsuarioDto> getDestacadosUsuariosByNif(String nif) {
        return destacadoUsuarioRepository.findDestacadosUsuariosByNif(nif).stream().map(destacadoUsuario -> modelMapper.map(destacadoUsuario, DestacadoUsuarioDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestacadoUsuarioDto> getDestacadosUsuariosActivosByNif(String nif) {
        return destacadoUsuarioRepository.findDestacadosUsuariosActivosByNif(nif).stream().map(destacadoUsuario -> modelMapper.map(destacadoUsuario, DestacadoUsuarioDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateOrSaveDestacadosUsuarios(List<DestacadoUsuario> destacadosUsuarios) {
        destacadoUsuarioRepository.saveAll(destacadosUsuarios);
    }

	@Override
	@Transactional(readOnly = true)
	public Destacado findById(Long destacado) {
		return destacadoRepository.findByIdDestacado(destacado);
		
	}

}
