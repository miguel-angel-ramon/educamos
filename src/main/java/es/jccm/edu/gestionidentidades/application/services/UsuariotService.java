package es.jccm.edu.gestionidentidades.application.services;

import es.jccm.edu.gestionidentidades.adapter.out.repository.UsuariostRepository;
import es.jccm.edu.gestionidentidades.application.domain.EmpleadoDelphos;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.domain.Usuariot;
import es.jccm.edu.gestionidentidades.application.ports.in.IUsuariotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Transactional
@Service
public class UsuariotService implements IUsuariotService {
    
    @Autowired
    UsuariostRepository usuariostRepository;

    public UsuariotService(UsuariostRepository usuariostRepository) {
        this.usuariostRepository = usuariostRepository;
    }

    @Override
    public Usuariot findUsuarioByIdentificacion(String identificacion) {
        return usuariostRepository.findByIdentificacion(identificacion);
    }
    
    @Override
    public Usuariot crearUsuariot(Usuariot usuariot) {
    	return usuariostRepository.save(usuariot);
    }

    @Override
    public Usuariot construirUsuariot(Persona person, Usuario user) {
    	
    	Usuariot usuariot = new Usuariot(); 
    	usuariot.setOid(user.getId());
    	usuariot.setDocente('S');
		usuariot.setOidPersona(person.getId());
		usuariot.setNombre(person.getNombre());	
		usuariot.setApellido1(person.getApellido1());
		usuariot.setApellido2(person.getApellido2());
		usuariot.setFechaNacimiento(person.getFechaNacimiento());
		usuariot.setCorreo(user.getCorreoE());
		usuariot.setNocturno('0');

		return usuariot;
    }

    @Override
    public Usuariot construirUsuariotDesdeDelphos(Persona person, Usuario user, EmpleadoDelphos es, Integer xCentro) {

    	Usuariot usuariot = new Usuariot(); 
    	usuariot.setDocente('S');
		usuariot.setOidPersona(person.getId());
		usuariot.setNombre(person.getNombre());	
		usuariot.setApellido1(person.getApellido1());
		usuariot.setApellido2(person.getApellido2());
		usuariot.setFechaNacimiento(person.getFechaNacimiento());
		usuariot.setCorreo(user.getCorreoE());
		usuariot.setNocturno('0');
		usuariot.setEquipoDirectivo(es.isEquipoDirectivo());
		usuariot.setCentro(xCentro.toString());		
		usuariot.setListaCargos(es.getListaCargos());
		usuariot.setTipoPersonal(Long.parseLong(es.getTipoPersonalEmpleado()));
		usuariot.setTutorUnidad(es.isTutorUnidad());
		usuariot.setCursoTutorUnidad(es.getCursoTutorUnidad());
		usuariot.setDepartamento(es.getDepartamento());
		usuariot.setUnidadOrganizativa(es.getUnidadOrganizativa());
		usuariot.setPtotraemp(es.getPtotraemp());
		usuariot.setComisioncoordinacionpedagojica(es.isComisioncoordinacionpedagojica());

		return usuariot;
    }

}
