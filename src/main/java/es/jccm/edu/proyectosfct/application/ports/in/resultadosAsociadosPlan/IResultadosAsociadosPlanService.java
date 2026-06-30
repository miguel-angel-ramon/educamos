package es.jccm.edu.proyectosfct.application.ports.in.resultadosAsociadosPlan;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ListadoResultadosAsociadosPlan;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ListadoResultadosAsociadosPlanRelacionados;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ResultadosAsociadosPlan;

public interface IResultadosAsociadosPlanService {

	List<ListadoResultadosAsociadosPlan> getListadoResultadosAsociadosPlan(Long idModulo);

	List<ListadoResultadosAsociadosPlanRelacionados> getListadoResultadosAsociadosPlanRelacionados(Long idModulo, Long idActividad);

	ResultadosAsociadosPlan saveResultadosAsociadosPlan(ResultadosAsociadosPlan resultadosAsociadosPlanIn, Long usuarioActual);
}