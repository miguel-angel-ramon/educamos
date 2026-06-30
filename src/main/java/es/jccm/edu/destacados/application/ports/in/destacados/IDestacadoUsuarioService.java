package es.jccm.edu.destacados.application.ports.in.destacados;

import es.jccm.edu.destacados.adapter.in.rest.destacados.model.DestacadoUsuarioDto;
import es.jccm.edu.destacados.application.domain.destacados.Destacado;
import es.jccm.edu.destacados.application.domain.destacados.DestacadoUsuario;

import java.util.List;

public interface IDestacadoUsuarioService {

    List<DestacadoUsuarioDto> getDestacadosUsuariosByNif(String nif);

    List<DestacadoUsuarioDto> getDestacadosUsuariosActivosByNif(String nif);

    void updateOrSaveDestacadosUsuarios(List<DestacadoUsuario> destacadosUsuarios);

	Destacado findById(Long destacado);

}
