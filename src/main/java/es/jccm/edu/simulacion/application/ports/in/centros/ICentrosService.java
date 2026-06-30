package es.jccm.edu.simulacion.application.ports.in.centros;

import java.util.List;

import es.jccm.edu.simulacion.application.domain.centros.entities.Centros;
import es.jccm.edu.simulacion.application.domain.centros.entities.DepartamentoCentro;
import es.jccm.edu.simulacion.application.domain.centros.projection.CentroProjection;


public interface ICentrosService {

	CentroProjection getCentro(Long codCentro);
	
	CentroProjection getCentroById(Long idCentro);
	
	List<Centros> getListadoCentros(Long xUsuarioComunica, String codCuestionario);

	List<Centros> getCentrosInspector(Long idEmpleado);

	List<DepartamentoCentro>getDepartamentosCentro(Long idCentro, Long anyo);

}
