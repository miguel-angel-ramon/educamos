package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.CalificacionCalculoTemporalMateriaProjection;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.CompentenciasEspecificasConPorcentajeYPesoProjection;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaNotaGlobalAlumnoMateria;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaPonderacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaNotaGlobalAlumnoMateria;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EvaNotaGlobalAlumnoMateriaRepository extends AbstractRepository<EvaNotaGlobalAlumnoMateria, Long, QEvaNotaGlobalAlumnoMateria> {

	EvaNotaGlobalAlumnoMateria findByIdMatMatriculaAndIdConvCentroOmc(Long idMatMatricula, Long idConvCentroOmc);

	@Query(value = "SELECT VCA.X_COMESP idComEsp, VCA.X_VALCOMALU idValComEsp, VCA.X_CALIFICA idCalifica,   " +
			"CAL.N_NUMERO numero, RELPONCOMESP.PESO, RELPONCOMESP.PORCENTAJE   " +
			"FROM TLVALCOMALU VCA     " +
			"INNER JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VCA.X_CALIFICA    " +
			"INNER JOIN TLRELPONCOMESP RELPONCOMESP ON RELPONCOMESP.X_PONDERACION = VCA.X_PONDERACION    " +
			" AND RELPONCOMESP.X_COMESP = VCA.X_COMESP   " +
			"WHERE VCA.X_PONDERACION = :idPonderacion   " +
			"AND VCA.X_MATMATRICULA = :idMatMatricula " +
			"AND VCA.X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
	List<CompentenciasEspecificasConPorcentajeYPesoProjection> findAllNotasCompentenciasEspecificasIdPonderacionAndIdMatMatricula(
			@Param("idPonderacion") Long idPonderacion,
			@Param("idMatMatricula") Long idMatMatricula,
			@Param("idConvCentroOmc") Long idConvCentroOmc);

	@Query(value = "SELECT SISCAL.X_CALIFICA idCalifica, SISCAL.N_MINIMO valorMinimo, SISCAL.N_MAXIMO valorMaximo, CAL.T_ABREV abreviatura, CAL.L_APRUEBA aprueba   " +
			"FROM TLRELNOTSIST SISCAL    " +
			"INNER JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = SISCAL.X_CALIFICA   " +
			"WHERE SISCAL.X_SISTCAL = :idSistCal", nativeQuery = true)
	List<CalificacionCalculoTemporalMateriaProjection> getSistemaCalificacion(@Param("idSistCal") Long idSistCal);
	
}