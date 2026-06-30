package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.CompentenciasEspecificasConPorcentajeYPeso;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.CalificacionCalculoTemporalMateriaProjection;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.CompentenciasEspecificasConPorcentajeYPesoProjection;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.NotaGlobalCalculadaAlumnoMateriaTemporalProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.QEvaNotaGlobalCalculadaAlumnoMateriaTemporal;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaNotaGlobalCalculadaAlumnoMateriaTemporal;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface EvaNotaGlobalCalculadaAlumnoMateriaTemporalRepository extends AbstractRepository<EvaNotaGlobalCalculadaAlumnoMateriaTemporal, Long, QEvaNotaGlobalCalculadaAlumnoMateriaTemporal> {

	EvaNotaGlobalCalculadaAlumnoMateriaTemporal findByMatMatricula(Long matMatricula);
	
	@Query(value = "SELECT ncmt.* FROM DELPHOS.EVA_NOTCALMATTEMP ncmt "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = ncmt.X_MATMATRICULA "
			+ "INNER JOIN DELPHOS.TLMATALU mua ON mua.X_MATRICULA = mma.X_MATRICULA "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag ON uag.X_UNIDAD = mua.X_UNIDAD AND uag.X_MATERIAOMG = mma.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU grua ON grua.X_GRUACTPROALU = uag.X_GRUACTPROALU AND grua.C_ANNO = mua.C_ANNO "
			+ "WHERE uag.X_MATERIAOMG = :idMateriaOmg AND uag.X_UNIDAD = :idUnidad AND grua.X_EMPLEADO = :idEmpleado", nativeQuery = true)
	List<EvaNotaGlobalCalculadaAlumnoMateriaTemporal> findAllByIdMateriaOmgAndIdUnidadAndIdEmpleado(@Param("idMateriaOmg") Long idMateriaOmg,
																				@Param("idUnidad") Long idUnidad,
																				@Param("idEmpleado") Long idEmpleado);

	@Query(value = "SELECT VALCOMESPTEMP.X_COMESP idComEsp, VALCOMESPTEMP.X_VALCOMALUTEMP idValComEspTemp, VALCOMESPTEMP.X_CALIFICA idCalifica, " +
			"CAL.N_NUMERO numero, RELPONCOMESP.PESO, RELPONCOMESP.PORCENTAJE " +
			"FROM EVA_VALCOMALUTEMP VALCOMESPTEMP   " +
			"INNER JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VALCOMESPTEMP.X_CALIFICA  " +
			"INNER JOIN TLRELPONCOMESP RELPONCOMESP ON RELPONCOMESP.X_PONDERACION = VALCOMESPTEMP.X_PONDERACION  " +
			" AND RELPONCOMESP.X_COMESP = VALCOMESPTEMP.X_COMESP " +
			"WHERE VALCOMESPTEMP.X_PONDERACION = :idPonderacion " +
			"AND VALCOMESPTEMP.X_MATMATRICULA = :idMatMatricula", nativeQuery = true)
	List<CompentenciasEspecificasConPorcentajeYPesoProjection> findAllNotasCompentenciasEspecificasIdPonderacionAndIdMatMatricula(
			@Param("idPonderacion") Long idPonderacion,
			@Param("idMatMatricula") Long idMatMatricula);

	@Query(value = "SELECT SISCAL.X_CALIFICA idCalifica, SISCAL.N_MINIMO valorMinimo, SISCAL.N_MAXIMO valorMaximo " +
			"FROM TLRELNOTSIST SISCAL " +
			"WHERE SISCAL.X_SISTCAL = :idSistCal", nativeQuery = true)
    List<CalificacionCalculoTemporalMateriaProjection> getSistemaCalificacion(@Param("idSistCal") Long idSistCal);

	@Query(value = "SELECT CALMATTEMP.X_NOTCALMATTEMP id, CALMATTEMP.X_MATMATRICULA idMatMatricula, CALMATTEMP.N_NOTA nota, CALMATTEMP.X_CALIFICA idCalificacion, " +
			"cal.S_CALIFICA descCal, cal.L_APRUEBA aprueba " +
			"FROM EVA_NOTCALMATTEMP CALMATTEMP  " +
			"INNER JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = CALMATTEMP.X_CALIFICA " +
			"WHERE CALMATTEMP.X_NOTCALMATTEMP = :id", nativeQuery = true)
	NotaGlobalCalculadaAlumnoMateriaTemporalProjection getDatosNotaGlobalTemporal(@Param("id") Long id);
}
