package es.jccm.edu.gestionidentidades.application.ports.in;

import java.util.List;

import es.jccm.edu.gestionidentidades.application.domain.Aplicacion;
import es.jccm.edu.gestionidentidades.application.domain.TipoPersonal;

public interface IAplicacionService {

    Aplicacion findAplicacionByCodigo(String codigo);
    
    List<Aplicacion> getAplicacionesPorDefectoNoDocente();

	List<Aplicacion> getAplicacionesPorDefecto();

	List<Aplicacion> getAplicacionesPorDefectoSegunTipoPersonal(TipoPersonal tipoPersonal);

}
