package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.ConfiguracionUsuarioDto;

public interface IConfiguracionUsuarioService {

	ConfiguracionUsuarioDto saveUpdate(ConfiguracionUsuarioDto configuracionUsuarioDto);

}
