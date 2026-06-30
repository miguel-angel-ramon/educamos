package es.jccm.edu.horarios.application.ports.in.dependencias;

import java.util.List;

import es.jccm.edu.horarios.application.domain.dependencias.DependenciaLibreList;
import es.jccm.edu.horarios.application.domain.dependencias.DependenciaList;
import es.jccm.edu.horarios.application.domain.dependencias.TipoDependencia;
import org.springframework.data.domain.Page;


public interface IDependenciasService {
	
	List<DependenciaList> getDependencias(String idUsuario, Integer anno);

	Page<DependenciaLibreList> getDependenciasLibres(Long idCentro, Integer anno, Integer diaSemana, int page, int numitems);

	List<TipoDependencia> getTiposDependenciaByCentro(Long codCentro);

}
