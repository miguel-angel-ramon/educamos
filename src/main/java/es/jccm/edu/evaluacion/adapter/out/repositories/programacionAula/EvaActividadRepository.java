package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.ActividadProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaActividadRepository extends AbstractRepository<EvaActividad, Long, QEvaActividad> {

    List<EvaActividad> findAllByUnidadProgramacionId(Long idUnidadProgramacion);

    @Query(value = "SELECT act.id_actividad id, act.tx_abrev abreviatura, act.NU_ORDENPRES orden, act.tx_nombre nombre, act.ds_actividad descripcion, " +
            "conv.d_convocatoria descripcionConvocatoria, act.id_unidadProg idUnidadProgramacion, act.id_insteva idInstrumentoEvaluacion, omc.x_convcentroomc idConvCentroOmc, " +
            "act.LG_VIENEMOODLE lprocedeMoodle ,act.f_inicio fechaInicio, act.f_fin fechaFin, UNIPROG.TX_NOMBRE  unidadNombre, " +
            "decode(uniProg.x_convcentroomc, act.x_convcentroomc, 'true', 'false') convocatoriaUnidad, " +
            "rpaa.id_progaula idProgramacionAula, act.ID_ACTIVIDAD_MOODLE idActividadMoodle " +
            "FROM EVA_ACTIVIDAD act " +
            "INNER JOIN TLCONVCENOMC omc ON act.x_convcentroomc = omc.x_convcentroomc " +
            "INNER JOIN TLCONVCENTROS conv ON omc.x_convcentro = conv.x_convcentro " +
            "INNER JOIN EVA_UNIDADPROG uniProg ON act.id_unidadprog = uniprog.id_unidadprog " +
            "INNER JOIN EVA_RELPROGAULACT rpaa ON rpaa.id_actividad = act.id_actividad " +
            "WHERE (:idUnidadProgramacion = 0 OR act.id_unidadprog = :idUnidadProgramacion) AND rpaa.id_progaula = :idProgramacionAula " +
            "ORDER BY act.NU_ORDENPRES", nativeQuery = true)
    List<ActividadProjection> findAllActividades(@Param("idUnidadProgramacion") Long idUnidadProgramacion, @Param("idProgramacionAula") Long idProgramacionAula);

	List<EvaActividad> findAllByUnidadProgramacionIdAndConvCentroOmcId(Long idUnidadProgramacion, Long idConvOmc);
	
	@Query("SELECT act FROM EvaActividad act " +
	"INNER JOIN EvaRelacionProgramacionAulaActividad rpaact ON rpaact.actividad = act " +
	"INNER JOIN EvaProgramacionAula pa ON pa = rpaact.programacionAula " +
	"WHERE act.unidadProgramacion.id = :idUnidadProgramacion AND pa.id = :idProgramacionAula")
	List<EvaActividad> findAllByUnidadProgramacionIdAndProgramacionAulaId(Long idUnidadProgramacion, Long idProgramacionAula);

	@Query("SELECT act FROM EvaActividad act " +
			"INNER JOIN act.relacionProgramacionAulaActividad rel ON rel.actividad.id = act.id AND rel.programacionAula.id = :idprogaula " +
			"WHERE act.convCentroOmc.id = :idconvcentroomc")
	List<EvaActividad> findActividadesByConvCentroAndidProgAula(@Param("idprogaula") Long idprogaula,
																@Param("idconvcentroomc") Long idconvcentroomc);
	
	@Query("SELECT act FROM EvaActividad act " +
			"INNER JOIN act.relacionProgramacionAulaActividad rel ON rel.actividad.id = act.id " +
			"WHERE act.unidadProgramacion.id = :idUnidadProgramacion AND act.convCentroOmc.id = :idConvCentroOmc AND rel.programacionAula.id = :idProgramacionAula")
	List<EvaActividad> findAllByUnidadProgramacionIdAndConvCentroOmcIdAndProgramacionAulaId(@Param("idUnidadProgramacion") Long idUnidadProgramacion, @Param("idConvCentroOmc") Long idConvCentroOmc, @Param("idProgramacionAula") Long idProgramacionAula);

	@Query(value = "SELECT DISTINCT ACTCRIEVA.X_CRIEVA   " +
			"FROM EVA_RELACTCRIEVA ACTCRIEVA " +
			"INNER JOIN EVA_ACTIVIDAD ACT ON ACT.ID_ACTIVIDAD = ACTCRIEVA.ID_ACTIVIDAD AND ACT.ID_ACTIVIDAD = :idActividad  " +
			"INNER JOIN EVA_RELPROGAULACT RELPROGACT ON RELPROGACT.ID_ACTIVIDAD = ACT.ID_ACTIVIDAD " +
			"INNER JOIN EVA_PROGAULA PROGAULA ON PROGAULA.ID_PROGAULA = RELPROGACT.ID_PROGAULA  " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC  " +
			"INNER JOIN EVA_RELPROGDIDPOND RELPROGPOND ON RELPROGPOND.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC  " +
			"INNER JOIN TLRELPONCRIEVA RELPONCRI ON RELPONCRI.X_PONDERACION = RELPROGPOND.X_PONDERACION AND RELPONCRI.ID_OPECALCRIEVA IN (2,5)", nativeQuery = true)
	List<Long> getAllCriteriosUltimoValorAndMediaPonderadaByIdActividad(@Param("idActividad") Long idActividad);

}