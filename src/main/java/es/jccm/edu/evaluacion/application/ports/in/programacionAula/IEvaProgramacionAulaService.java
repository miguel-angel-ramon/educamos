package es.jccm.edu.evaluacion.application.ports.in.programacionAula;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.*;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.DocenteProgAulaProjection;
import org.springframework.data.domain.Page;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CompetenciaEspecificaDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.ProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.AlumnosPorGrupoProjection;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.ProgramacionAulaProjection;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;

public interface IEvaProgramacionAulaService {

	List<AlumnosPorGrupoProjection> findAlumnosByEmpleadoAndAnyo(Long idEmpleado, Long anno, Long idCentro);

	void updateActividad(ActividadDTO actividadDto) throws EvaluacionException;
	
	List<ProgramacionAulaDTO> getPlantillasProgramacionAulaByEmpleado(Long idEmpleado);
	
	ProgramacionAulaDTO getProgramacionAulaById(Long idProgAula);
	
	//void insertPlantillaProgramacionAula(Long idEmpleado, Long idMateriaOmg, Long idCentro, Long anno, Long idOfertamatrig, Long idnivelCurricular, ProgramacionAulaDTO programacionAulaDTO);
	
	void updateProgramacionAula(ProgramacionAulaDTO programacionAulaDTO);
	
    Long insertProgramacionAula(Long idEmpleado, Long idDidac, DataProgramacionAulaInsertDTO data, Long idProAula ) throws EvaluacionException;
	
	Boolean deleteProgramacionAula(Long idProgramacionAula);
	
	List<CursoProgramacionAulaDTO> getCursosProgramacionAulaByCentroAndAnno(Long idEmpleado, Long idCentro, Integer anno, Boolean direccion);


	List<MateriaProgramacionAulaDTO> getMateriasProgramacionAula(Long idEmpleado, Long idCentro, Long idOfermatrig, Integer anno, List<Long> idsDocenteSust) throws EvaluacionException;

	List<MateriaProgramacionAulaDTO> getMateriasProgramacionAula_dir(Long idCentro, Long idOfermatrig, Integer anno) throws EvaluacionException;

	ProgramacionDidacticaDTO programacionDidacticaAulas(Long idDidactica);

	List<CursoProgramacionAulaDTO> getCursosPlantilla(Long idCentro, Integer anno);

	List<ActividadDTO> getActividades(Long idUnidadProgramacion, Long idProgramacionAula);

	void insertActividad(ActividadDTO actividad);
	
	Boolean isDocenteValidoProgramacionAula(Long idEmpleado, Integer anno, Long idCentro);
	
	Boolean deleteActividad(Long idActividad);

	void editNombreProgramacionAula(Long idProgramacionAula, String nombre) throws Exception;
	
	List<ActividadDTO> getActividadesCriteriosPonderacion(Long idUnidadProgramacion, Long idProgramacionAula);

	Boolean isProgramacionAulaEliminable(Long idProgramacionAula);
	
	List<CompetenciaEspecificaDidacticaDTO> getCompetenciasEspecificasByProgramacionAula(Long idProgramacionAula);

	Page<CriterioActividadPonderacionDTO> getCriteriosActividadesPonderaciones(Long idProgramacionAula, Long idCompetenciaEspecifica, Long idCriterioEvaluacion, int page, int numItems);
	
	List<CriterioEvaluacionDTO> getCriteriosEvaluacionByProgramacionAulaAndCompetenciaEspecifica(Long idProgramacionAula, Long idCompetenciaEspecifica);

	Boolean insertActividades(List<ActividadDTO> actividades);

	List<ProgramacionAulaProjection> findProgramacionesAulaByEmpleadoAndAnyo(Long idEmpleado, Long anno, Long idCentro);

	List<ProgramacionAulaProjection> findProgramacionesAulaByEmpleadosAndAnyo(Long idEmpleado, Long anno, Long idCentro, String idsEmpleadosCompartidas);

    List<ProgramacionAulaProjection> findProgramacionesAulaByCursoMateriaUnidad(Long anno, Long idCentro, Long idOfertaMatrig, Long idMateriaOMG, Long idUnidad);

	Boolean isDocenteValidoProgramacionAula(Long idEmpleado, Integer anno, Long idCentro, String idsEmpleadosCompartidas);

    List<AlumnosPorGrupoProjection> findAlumnosByAnyoAndEmpleados(Long idEmpleado, Long anno, Long idCentro, String idsEmpleadosCompartidas);

	List<DocenteProgAulaProjection> getDocentesProgAula(Long anno, Long idCentro);

	void insertAlumnosActividades(List<Long> idsMatricula, List<Long> idsActividad);

}
