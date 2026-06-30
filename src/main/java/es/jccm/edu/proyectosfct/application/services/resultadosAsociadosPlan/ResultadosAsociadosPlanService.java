package es.jccm.edu.proyectosfct.application.services.resultadosAsociadosPlan;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.ResultadosAsociadosPlanRepository;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ListadoResultadosAsociadosPlan;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ListadoResultadosAsociadosPlanRelacionados;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ResultadosAsociadosPlan;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.projection.ListadoResultadosAsociadosPlanProjection;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.projection.ListadoResultadosAsociadosPlanRelacionadosProjection;
import es.jccm.edu.proyectosfct.application.ports.in.resultadosAsociadosPlan.IResultadosAsociadosPlanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class ResultadosAsociadosPlanService implements IResultadosAsociadosPlanService {
	
	private static final Long ID_PERFIL_GESTOR = 11207L;
	private static final Long ID_PERFIL_CONSEJERIA_FCT = 13207L;
	private static final Long ID_PERFIL_CONSEJERIA_FCT_1 = 15207L;
	private static final Long ID_PERFIL_ALUMNADO = 5000L;

	@Autowired
	private ResultadosAsociadosPlanRepository resultadosAsociadosPlanRepository;

	@Autowired
	private ModelMapper modelMapper;


	@Override
	public List<ListadoResultadosAsociadosPlan> getListadoResultadosAsociadosPlan(Long idModulo) {
		List<ListadoResultadosAsociadosPlanProjection> resultados = resultadosAsociadosPlanRepository.findResultadosAsociadosPlanByModulo(idModulo);

		return resultados.stream().map(entity -> modelMapper.map(entity, ListadoResultadosAsociadosPlan.class)).collect(Collectors.toList());
	}

	@Override
	public List<ListadoResultadosAsociadosPlanRelacionados> getListadoResultadosAsociadosPlanRelacionados(Long idModulo, Long idActividad) {
		List<ListadoResultadosAsociadosPlanRelacionadosProjection> resultadosRelacionados =
				resultadosAsociadosPlanRepository.findResultadosAsociadosPlanByModuloEnRelacion(idModulo,idActividad);

		return resultadosRelacionados.stream()
				.map(entity -> modelMapper.map(entity, ListadoResultadosAsociadosPlanRelacionados.class))
				.collect(Collectors.toList());
	}


	private ResultadosAsociadosPlan createResultadosAsociadosPlan(ResultadosAsociadosPlan resultadosAsociadosPlanIn) {
		return resultadosAsociadosPlanRepository.save(resultadosAsociadosPlanIn);
	}


	private ResultadosAsociadosPlan updateResultadosAsociadosPlan(ResultadosAsociadosPlan resultadosAsociadosPlan) {
		Optional<ResultadosAsociadosPlan> resultadosAsociadosPlanUpdate = resultadosAsociadosPlanRepository.findById(resultadosAsociadosPlan.getIdResultadoaModulo());

		if (resultadosAsociadosPlanUpdate.isPresent()) {
			ResultadosAsociadosPlan existingPlan = resultadosAsociadosPlanUpdate.get();

			existingPlan.setLg_centro(resultadosAsociadosPlan.getLg_centro());
			existingPlan.setLg_empresa(resultadosAsociadosPlan.getLg_empresa());
			existingPlan.setX_comesp(resultadosAsociadosPlan.getX_comesp());
			existingPlan.setModulosCurso(resultadosAsociadosPlan.getModulosCurso());

			resultadosAsociadosPlanRepository.save(existingPlan);
		} else {
			String mes = crearMessage(ResultadosAsociadosPlan.class.getSimpleName(), resultadosAsociadosPlan.getIdResultadoaModulo().toString());
			throw new NotFoundException(mes);
		}

		return resultadosAsociadosPlanUpdate.get();
	}

	@Override
	public ResultadosAsociadosPlan saveResultadosAsociadosPlan(ResultadosAsociadosPlan resultadosAsociadosPlan, Long usuarioActual) {
		// Verificar si existe un registro con el mismo ID_MODULO_CURSO y X_COMESP
		Long idExiste = resultadosAsociadosPlanRepository.findIdByModulosCursoIdModuloCursoAndXComesp(
				resultadosAsociadosPlan.getModulosCurso().getId(),
				resultadosAsociadosPlan.getX_comesp()
		);

		if (idExiste!=null) {
			// Actualizar el registro existente
			Optional<ResultadosAsociadosPlan> planToUpdate = resultadosAsociadosPlanRepository.findById(idExiste);
			if (planToUpdate.isPresent()){
				planToUpdate.get().setLg_centro(resultadosAsociadosPlan.getLg_centro());
				planToUpdate.get().setLg_empresa(resultadosAsociadosPlan.getLg_empresa());
				planToUpdate.get().setCUsuActualiza(usuarioActual);
				planToUpdate.get().setFActualiza(new Date());
				return resultadosAsociadosPlanRepository.save(planToUpdate.get());
			}
		}
		// Crear un nuevo registro
		resultadosAsociadosPlan.setFCreacion(new Date());
		resultadosAsociadosPlan.setFActualiza(new Date());
		resultadosAsociadosPlan.setLg_centro(resultadosAsociadosPlan.getLg_centro());
		resultadosAsociadosPlan.setLg_empresa(resultadosAsociadosPlan.getLg_empresa());
		resultadosAsociadosPlan.setX_comesp(resultadosAsociadosPlan.getX_comesp());
		resultadosAsociadosPlan.setCUsuCreacion(usuarioActual);
		resultadosAsociadosPlan.setCUsuActualiza(usuarioActual);
		return resultadosAsociadosPlanRepository.save(resultadosAsociadosPlan);
	}

	private String crearMessage(String nameClass, String id) {
		return "No se ha encontrado el objeto relacionado con " + nameClass + " para el parámetro (" + id + ")\"";
	}
}
