package es.jccm.edu.evaluacion.application.ports.in.valoracionCriterios;

import es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model.*;
import es.jccm.edu.evaluacion.application.domain.evaluacion.*;

import java.util.List;

public interface IValoracionCriteriosService {

	List<AlumnoEvaluacion> getAlumnosEvaluacion(Long idPonderacion, Long idConvCentroOmc, List<Long> idEmpleados, List<String> fechaTomaPosesion, Long idNivelCurricular, Long idUnidad, Boolean pendiente, Long tutorLong, Long direccionLong);

	List<CompetenciaAlumno> getAlumnoCompetenciasEvaluacion(Long idConvCentroOmc, Long idMatmatricula);

	void trasladarResultadoEvaluacion(List<AlumnoEvaluacion> alumnos, Long idCentroOmc);

	void bloquearPonderacion(Long idPonderacion);

	List<Annos> getUltimosAnnos();

	List<Convocatorias> getConvocatorias(Long anno, Long idUnidad, Long idOfertaMatrig);

	List<Convocatorias> getConvocatoriasValoracionCriterios(Long anno, Long idUnidad, Long idOfertaMatrig);

	List<MateriasValoracion> getMateriasValoracion(Long idEmpleado, Long anno, Long idCentro, List<Long> idUnidades, Long idOfertamatrig);

	List<UnidadesValoracion> getUnidadesValoracion(List<Long> idDocentes, Long anno, Long idMateria, Long idCentro,Long idProgdidac, boolean tutor, boolean direccion);
	
	List<SistemaCalificacionCua> sistemaCalificacion(Long idEtapa);

	List<SistemaCalificacionCua> sistemaCalificacionGlobal(Long idOfertaMatrig);

	AlumnoEvaluacion calculoNotaCriterioParaAlumnoConvocatoria(AlumnoEvaluacion alumno, Long idPonderacion, Long idConvCentroOmc, Long idCriterio, Long idCalifica, Long idSistemaCalificacion);
	
	List<CompetenciaClave> getCompetenciasClave();
	
	List<DescriptorOperativo> getDescriptoresOperativosByComClaveAndEtapa(CompetenciaClave competenciaClave, Long idEtapa);
	
	List<UnidadEvaluacion> getUnidadesEvaluacion(Long idEmpleado, Long anno);
	
	List<AlumnoEvaluacionSel> getAlumnosUnidad(Long idCurso, Long idUnidad, Long idOfertamatrig);

	List<AlumnoEvaluacionSel> getAlumnosUnidadConvExtra(Long idCurso, Long idUnidad, Long idConvocatoria, Long idOfertamatrig);

	List<AlumnoEvaluacionSel> getAlumnosByMatriculas(String idsMatAlu, Long idEtapa);
	
	List<CompetenciaClaveAlumno> getCompetenciasClaveAlumno(Long idEtapa, Long idMatricula, Long idConvCentroOmc);

	void saveCompetenciasClaveAlumno(Long idMatricula, Long idConvCentroOmc, CompetenciaClaveAlumnoDTO competenciasClave);

	void saveCompetenciaClaveMatriz(Long idMatricula, Long idConvCentroOmc, ValoracionCompetenciaClaveAlumno competenciasClave);
	
	Long createRegSelDocsInformeValoracionCriterios(String idAlumnos);
	
	List<CompetenciaEspecifica> getCompetenciaEspecifica(Long descripcionOperativa, Long etapa, Long idMatricula, Long idConvCentroOmc);
	
	ValoracionDescriptorOperativoAlumno getValoracionDescriptorOperativoAlumno(Long idDescriptorOperativo, Long idEtapa, Long idMatricula, Long idConvCentroOmc);
	
	String getBachillerato(Long idEtapa, Long idOfertamatrig);
	
	List<AlumnoEvaluacionCalculadaDTO> getAlumnosEvaluacionCalculada(Long idPonderacion, List<Long> idEmpleados, List<String> fechaTomaPosesion, Long idNivelCurricular, Long idUnidad, boolean tutor, boolean direccion);
	
	void trasladarCalculoConvocatoria(Long idPonderacion, Long idConvCentroOmc, List<Long> idEmpleados, List<String> fechaTomaPosesion, Long idNivelCurricular, Long idSistemaCalifica, Long idUnidad, boolean tutor, boolean direccion);
	
	List<UnidadEvaluacion> getUnidadesEvaluacionCompClave(List<Long> idEmpleados, List<String> fechaTomaPosesion, Long idCentro, Long anno, Boolean direccion);
	
	List<AlumnoValoracion> getHistoricoAlumnosValoracionCurricular(Long idCurso, Long idUnidad, Long idConvocatoria, Long idEtapa, Long idOfertamatrig);
	
	List<AlumnoValoracionAdquisicionDTO> getAlumnosValoracionTemporalCurricular(Long idUnidad, Long idEtapa, Long idOfertamatrig);
	
	List<CompetenciaClaveAlumno> getCompetenciasClaveTemporalesAlumno(Long idMatricula, Long idEtapa);

	List<ActividadCriterioDTO> getActividadesbyCriterio(Long idMatriAlu, Long idCriterio, Long idPonderacion);

	List<CursoValoracionDTO> getCursosValoracionByCentroAndAnno(Long idCentro, Integer anno);

	void calcularValoracionCurricularUnidades(String idsUnidad, Long idConvocatoria, List<Long> idsOfertamatrig);

	void calcularValoracionCurricularUnidadesConvocatoriasCursos(List<UnidadConvocatoriaCursoDTO> unidadesConvocatoriasCursos);

	void calcularValoracionCurricularCursos(Long idCentro, Integer anno, List<Long> idsOfertamatrig, Long idConvocatoria);

	List<AlumnoEvaluacion> getAlumnosACNEESinProgramacionAula(List<Long> idsEmpleado, List<String> fechasTomaPosesion, Long idOfertamatrig, Long idMateriaOmg, Long idCentro, Integer anno, boolean tutor, boolean direccion);

	List<UnidadEvaluacionDTO> getUnidadesEvaluacionConConvocatorias(List<UnidadEvaluacion> unidadesEvaluacion, Long anno);

	List<String> getMateriasNoEvaluadasCalculoCompetenciaFinal(Long idUnidad, Long idConvCentroOmc);

	void setMateriasNoEvaluadas(List<AlumnoValoracionDTO> alumnosOut);

	List<UnidadesValoracion> getUnidadesValoracionPendiente(Integer anno, Long idOfertamatrig, Long idMateria, Long idCentro, Long idEmpleado, boolean tutor, boolean direccion);
}
