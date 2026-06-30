package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.AltaLdapResponse;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalResponse;
import es.jccm.edu.gestionidentidades.application.domain.EstadoRegistro;
import es.jccm.edu.gestionidentidades.application.domain.NuevoUsuarioPlataforma;
import es.jccm.edu.gestionidentidades.application.domain.NuevoUsuarioPlataformaDesdeDelphos;
import es.jccm.edu.gestionidentidades.application.domain.RegistroUsuarioPlataformaRequest;
import es.jccm.edu.gestionidentidades.application.domain.RegistroUsuarioPlataformaResponse;
import es.jccm.edu.gestionidentidades.application.domain.TipoPersonal;
import es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos.RegistroUsuarioPlataformaException;

import java.util.Date;

public interface IRegistroUsuarioPlataformaService {

    EstadoRegistro registroUsuarioPlataforma(RegistroUsuarioPlataformaRequest request)
            throws RegistroUsuarioPlataformaException;

    RegistroUsuarioPlataformaResponse recibeUsuario(NuevoUsuarioPlataforma usuario);

    AltaLdapResponse altaLdap(AltaUsuarioGlobalRequest request, TipoPersonal tipoPersonal);

    RegistroUsuarioPlataformaResponse recibeUsuarioDesdeDelphos(NuevoUsuarioPlataformaDesdeDelphos usuario);

    AltaUsuarioGlobalResponse altaUsuarioGlobal(AltaUsuarioGlobalRequest request, TipoPersonal tipoPersonal,
                                                Integer xCentro, Date fechaTomaPos);

}
