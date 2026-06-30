package es.jccm.edu.proyectosfct.application.ports.in.miformacionempresas;

import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.DatosFormacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model.DatosFormacionPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;

public interface IMiFormacionEmpresasService {
	
	List<DatosFormacionPlanDto> getDatosFormacionPlan(Long idMatricula);

	List<DatosFormacionDto> getDatosFormacionProyecto(Long idMatricula);

	List<DatosFormacionDto> getDatosFormacionPrograma(Long idMatricula);

	List<ElementoSelectDto> getTipoPracticasAlumno(Integer cAnno, Long idEmpleadoComunica);


}
