package es.jccm.edu.buzon.application.ports.in.buzonCentro;

import java.util.List;

import es.jccm.edu.buzon.application.domain.buzonCentro.UnidadBuzonCentro;

public interface IBuzonCentroShService {
	
	List<UnidadBuzonCentro> getUnidadesCentroCompClave(Long idEmpleado, String fechaTomaPosesion, Long idCentro, Long anno, Boolean direccion);


}
