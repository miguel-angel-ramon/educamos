package es.jccm.edu.proyectosfct.application.ports.in.datosprograma;

import java.util.List;
import es.jccm.edu.proyectosfct.application.domain.datosprograma.DatosProgramaFct;

public interface IDatosProgramaFctService {

	List<DatosProgramaFct> createDatosPrograma(List<DatosProgramaFct> listDatosProgramaIn, Long idPrograma);

	void deleteDatosPrograma(Long id);

	List<DatosProgramaFct> getActividadesPrograma(Long idPrograma);

	Integer getCountDatosPrograma(Long idPrograma);
	
}
