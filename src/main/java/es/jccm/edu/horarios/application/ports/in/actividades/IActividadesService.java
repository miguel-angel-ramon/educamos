package es.jccm.edu.horarios.application.ports.in.actividades;

import java.util.List;

import es.jccm.edu.horarios.application.domain.actividades.ActividadList;


public interface IActividadesService {
	
	List<ActividadList> getActividades(String idUsuario, Integer anno);

}
