package es.jccm.edu.proyectosfct.application.ports.in.datosproyectos;

import java.util.List;
import es.jccm.edu.proyectosfct.application.domain.datosproyecto.DatosProyectosFct;

public interface IDatosProyectosFctService {

	List<DatosProyectosFct> createDatosProyecto(List<DatosProyectosFct> listDatosProyectoIn);

	void deleteDatosProyecto(Long id);

	List<DatosProyectosFct> getActividadesProyecto(Long idProyecto);

	Integer getCountDatosProyecto(Long idProyecto);
	
}
