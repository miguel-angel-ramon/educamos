package es.jccm.edu.evaluacion.application.ports.in.programacionDidactica;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CompetenciaEspecificaDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.HistorialResponsableProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.HistoricoProgramacionDidacticaDTO;

import java.util.List;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.ProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaDepartamento;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaCompetenciaEspecificaDidacticaProjection;

import java.util.List;

public interface IEvaProgramacionDidacticaService {

    ProgramacionDidacticaDTO getProgramacionDidacticaByMateriaAndCursoAndCodigoCentroAndAnyo(Long idMateria, Long idOfertamatrig, Long codigoCentro, Integer anyo) throws Exception;

    void insertProgramacionDidactica(ProgramacionDidacticaDTO programacionDidacticaDTO, Long idMateria);

    Integer puedeCerrarProgramacion(Long idProgramacionDidactica, Long idOfertaMatrig);

    boolean puedeAbrirProgramacion(Long idProgramacionDidactica);

    void cerrarProgramacionDidactica(Long idProgramacionDidactica, Integer cerrar) throws Exception;

    void updateProgramacionDidactica(ProgramacionDidacticaDTO programacionDidacticaDTO);

    Boolean deleteProgramacionDidactica(Long idProgramacionDidactica);

    EvaCompetenciaEspecificaDidacticaProjection comprobarDuplicarProgramacionDidactica(Long idMateriaOmg, Long idOfermatrig, Long idCentro, Integer anno);

    EvaProgramacionDidactica duplicarProgramacionDidactica(Long idProgramacionDidactica, Long idMateriaOmg);

    EvaProgramacionDidactica duplicarProgramacionDidactica(Long idProgramacionDidactica, Integer anyo);

    EvaProgramacionDidactica duplicarProgramacionDidactica(Long idProgramacionDidactica, Long idMateriaOmg, Integer anyo);

    void insertProgramacionDidacticaACNEAE(ProgramacionDidacticaDTO programacionDidacticaDto, Long idMateriaOmgNivelCurricular, List<Long> criterios);


    ProgramacionDidacticaDTO getProgramacionDidacticaByMateriaAndCursoAndCodigoCentroAndAnyoAndAcneaeAndNivelCurricular(Long idMateria, Long idOfertamatrig, Long codigoCentro, Integer anyo, Long idNivelCurricular) throws Exception;


    List<CompetenciaEspecificaDidacticaDTO> competenciasCriteriosAcneae(Long idMateriaOmg, Long idNivelCurricular) throws Exception;

    Boolean deleteDuplicatesProgramacionDidacticaByMateriaAndCursoAndCentroAndAnyo(Long idMateriaOmg, Long idOfertamatrig, Long idCentro, Integer anyo);

    Boolean deleteDuplicatesProgramacionDidacticaByMateriaAndCursoAndCentroAndAnyoAndNivelCurricular(Long idMateriaOmg, Long idOfertamatrig, Long idCentro, Integer anyo, Long idNivelCurricular, Long idMateriaOmgNivelCurricular);

    Boolean borrarTodosDuplicadosProgramacionesDidacticas();

    List<HistorialResponsableProgramacionDidacticaDTO> getHistorialResponsablesProgramacionDidactica(Long idOfertaMatrig, Long idMateriaOmg, Long idCentro, Integer anyo, Long idNivelCurricular, Long idMateriaOmgAdap);

    List<HistorialResponsableProgramacionDidacticaDTO> getProfesoresDepartamentoProgramacionDidactica(Long idCentro, Integer anyo, Long idEmpleado);

    void appointResponsableProgramacionDidactica(Long idEmpleado, Long idOfertaMatrig, Long idMateriaOmg, Long idCentro, Integer anyo, Long idNivelCurricular, Long idMateriaOmgAdap);

    void revokeResponsableProgramacionDidactica(Long idOfertaMatrig, Long idMateriaOmg, Long idCentro, Integer anyo, Long idNivelCurricular, Long idMateriaOmgAdap);

    List<EvaDepartamento> getDepartamentosCentro(Long idCentro, Long anyo);

    List<HistoricoProgramacionDidacticaDTO> getProgramacionesDidacticasAnyosAnteriores(Long codigoCentro, Long idMateriaOmg, Long idOfertamatrig);

    List<HistoricoProgramacionDidacticaDTO> getProgramacionesDidacticasACNEEAnyosAnteriores(Long codigoCentro, Long idMateriaOmg, Long idOfertamatrig, Long idNivelCurricular);

    EvaProgramacionDidactica duplicarProgramacionDidactica(Long idProgramacionDidactica, Long idMateriaOmg, Integer anyo, Long idMateriaOmgAdaptacion);

    Integer hasProgramacionDidacticaAnnoAcademicoActual(Long codigoCentro, Long idMateriaOmg, Long idOfertamatrig);

    Integer hasProgramacionDidacticaAnnoAcademicoActualAcnee(Long codigoCentro, Long idMateriaOmg, Long idOfertamatrig, Long aLong);
}
