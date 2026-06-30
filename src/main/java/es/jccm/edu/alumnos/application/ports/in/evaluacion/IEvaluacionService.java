package es.jccm.edu.alumnos.application.ports.in.evaluacion;


import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

import es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model.CursoCentroDTO;
import es.jccm.edu.alumnos.application.domain.evaluacion.AlumnoEvalInd;
import es.jccm.edu.alumnos.application.domain.evaluacion.ConvUnidad;
import es.jccm.edu.alumnos.application.domain.evaluacion.Convocatoria;
import es.jccm.edu.alumnos.application.domain.evaluacion.EstadosPromocion;
import es.jccm.edu.alumnos.application.domain.evaluacion.Evaluacion;
import es.jccm.edu.alumnos.application.domain.evaluacion.GrupoActividadConvocatoria;
import es.jccm.edu.alumnos.application.domain.evaluacion.ListCalificaciones;
import es.jccm.edu.alumnos.application.domain.evaluacion.MateriaUnidad;
import es.jccm.edu.alumnos.application.domain.evaluacion.Promocion;
import es.jccm.edu.alumnos.application.domain.evaluacion.SistemaCalificacion;
import es.jccm.edu.alumnos.application.domain.evaluacion.UnidadConv;


public interface IEvaluacionService {

    List<Evaluacion> getNotasByGrupoActividad(String idGrupoAct, Long idEmpleado, Long idConvocatoria);
    
    SistemaCalificacion getTipoSistemaCalificacion(Long idGrupoAct, Integer anno);
    
    List<ListCalificaciones> getTipoSistemaCalificacionPorAlumno(Long idMatMatricula, Integer anno);
    
    void setNotaAlumnoParaGrupoActividad(Long idConvCentroOmc, Long idMatMatricula, Long idCalifica, Long idConvocatoria, Integer accion);
    
    Integer setNotaConvocatoriaFinalAlumno(Long idConvCentroOmc, Long idMatMatricula, Long idMatricula, Long idCalifica, String apruebaMateria, Long idConvUnidad, 
    		String fechaSesion, Integer accion);
    
    Integer setResultadoPromocionAlumno(Long idConvCentroOmc, Long idMatricula, Long resultado, Long idEstGenMatr, Long idConvUnidad, 
    		String fechaSesion, Integer accion);

	List<Evaluacion> getNotasByUnidad(Long idUnidad, Long idConvocatoria, Long idOfertaMatrig);

	List<MateriaUnidad> getMateriaByUnidad(Long idUnidad, Integer anno, Long idOferMatring);
	
	List<Convocatoria> getConvocatorias(Long idCentro, Integer anno);
	
	List<UnidadConv> getUnidadesConvocatoria(Long idConvocatoria, Long idOfertamatrig, String idUnidad);
	
	List<UnidadConv> getUnidadesByGrupo(Long idConvocatoria, Long idGrupoAct );

	List<GrupoActividadConvocatoria> getGruposActividadConvocatoria(Long idConvocatoria, Long idEmpleado);
	
	List<GrupoActividadConvocatoria> getGruposActividadUnidades(Long idConvocatoria, Long idEmpleado);

	List<ConvUnidad> getFechaSesion(Long unidad, Long convocatoria);
	
	Promocion getPromotion(Long idMatMatricula, Long convocatoria);
	
	List<EstadosPromocion> getStatesPromocion(Long idOfermatrig);

	Long getPromotionStates(Long idOfermatrig, Long action);

	List<AlumnoEvalInd> getAlumnosByGruposActividad(String idGrupoAct, Long idEmpleado, Long idConvocatoria);
	
	List<AlumnoEvalInd> getAlumnosByUnidad(Long idUnidad, Long idConvocatoria, Long idOfertaMatrig);
	
	HashMap<String, Long> getReprobedHours(Long idMatricula, Long idConvOmc);
	
	String getMediaEvaluacion(Long idAlumno,  Long idOfertMatrig, String notas, Long decimales);
	
	List<CursoCentroDTO> getCursosByCentroAndAnno(Long idCentro, Long anno);

}
