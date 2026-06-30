package es.jccm.edu.gestionidentidades.application.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import es.jccm.edu.gestionidentidades.application.domain.PersonaId;
import es.jccm.edu.gestionidentidades.application.ports.in.AccesoAplicacionesUseCase;


@Service
public class AutorizacionAplicacionesService implements AccesoAplicacionesUseCase{
	
	private static final String SECRETARIA_VIRTUAL = "SECRETARIA_VIRTUAL";
	
	/*@Autowired
	AutorizacionAplicaciones autorizacionAplicaciones;*/
	
	
	public static final String SEGUIMIENTO_EDUCATIVO = "SEGUIMIENTO_EDUCATIVO_18";

	
	private static final String aplicacionesPorDefecto []= {
		"MODULO_ACCESO",SEGUIMIENTO_EDUCATIVO,SECRETARIA_VIRTUAL
	};
	
	private static final String aplicacionesPorDefectoNoDocente []= {
			"MODULO_ACCESO"
	};
	
	public List<String> getAplicacionesPorDefecto(){
		return Arrays.asList(aplicacionesPorDefecto);
	}
	
	public List<String> getAplicacionesPorDefectoNoDocente(){
		return Arrays.asList(aplicacionesPorDefectoNoDocente);
	}

	@Override
	public boolean tieneAccesoAplicacion(PersonaId personaId, String aplicacion) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void darAccesoAplicacionSiNoTiene(PersonaId personaId, String codAplicacion) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	public void darAccesoAplicacionesEducamosPorDefecto(PersonaId personaId){
		this.darAccesoAplicaciones(personaId, getAplicacionesPorDefecto());
	}
	
	public void darAccesoAplicaciones(PersonaId personaId, List<String> codAplicaciones) {
		for(String codAplicacion:codAplicaciones) {
			autorizacionAplicaciones.darAccesoAplicacion(personaId, codAplicacion);
		}
	}
	

	@Override
	public boolean tieneAccesoAplicacion(PersonaId personaId, String aplicacion) {
		return autorizacionAplicaciones.tieneAccesoAplicacion(personaId, aplicacion);
	}

	@Override
	public void darAccesoAplicacionSiNoTiene(PersonaId personaId, String codAplicacion) {
		autorizacionAplicaciones.darAccesoAplicacion(personaId, codAplicacion);
	}

	public List<String> getAplicacionesPorDefectoSegunTipoPersonal( TipoPersonal tipoPersonal) {
		return tipoPersonal != TipoPersonal.NO_DOCENTE?
				getAplicacionesPorDefecto():
				getAplicacionesPorDefectoNoDocente();
	}*/
		
}
