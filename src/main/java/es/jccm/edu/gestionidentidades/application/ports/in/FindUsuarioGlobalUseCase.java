package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.UsuarioDto;

public interface FindUsuarioGlobalUseCase {

	UsuarioDto findFirst(String identificacion, String tipide);

}