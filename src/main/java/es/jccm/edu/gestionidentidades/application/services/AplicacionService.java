package es.jccm.edu.gestionidentidades.application.services;

import es.jccm.edu.gestionidentidades.adapter.out.repository.AplicacionRepository;
import es.jccm.edu.gestionidentidades.application.domain.Aplicacion;
import es.jccm.edu.gestionidentidades.application.domain.TipoPersonal;
import es.jccm.edu.gestionidentidades.application.ports.in.IAplicacionService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AplicacionService implements IAplicacionService {
	
	private static final String SECRETARIA_VIRTUAL = "SECRETARIA_VIRTUAL";
	
	public static final String SEGUIMIENTO_EDUCATIVO = "SEGUIMIENTO_EDUCATIVO_18";
	
	//TODO pasar a PROPIEDADES
	private static final String aplicacionesPorDefecto []= {
			"MODULO_ACCESO",SEGUIMIENTO_EDUCATIVO,SECRETARIA_VIRTUAL
		};
	
	private static final String aplicacionesPorDefectoNoDocente []= {
			"MODULO_ACCESO"
	};

    private final AplicacionRepository aplicacionRepository;

    public AplicacionService(AplicacionRepository aplicacionRepository) {
        this.aplicacionRepository = aplicacionRepository;
    }

    @Override
    public Aplicacion findAplicacionByCodigo(String codigo) {
        return aplicacionRepository.findByCodigo(codigo);
    }
    
    @Override
	public List<Aplicacion> getAplicacionesPorDefectoSegunTipoPersonal( TipoPersonal tipoPersonal) {
		return tipoPersonal != TipoPersonal.NO_DOCENTE?
				getAplicacionesPorDefecto():
				getAplicacionesPorDefectoNoDocente();
	}

	@Override
	public List<Aplicacion> getAplicacionesPorDefecto(){
		List<Aplicacion> listaAplicaciones = new ArrayList<Aplicacion>();
		for (String codigo : aplicacionesPorDefecto) {
			listaAplicaciones.add(findAplicacionByCodigo(codigo));			
		}
		return listaAplicaciones;
	}

	@Override
	public List<Aplicacion> getAplicacionesPorDefectoNoDocente(){
		List<Aplicacion> listaAplicaciones = new ArrayList<Aplicacion>();
		for (String codigo : aplicacionesPorDefectoNoDocente) {
			listaAplicaciones.add(findAplicacionByCodigo(codigo));			
		}
		return listaAplicaciones;
	}

}
