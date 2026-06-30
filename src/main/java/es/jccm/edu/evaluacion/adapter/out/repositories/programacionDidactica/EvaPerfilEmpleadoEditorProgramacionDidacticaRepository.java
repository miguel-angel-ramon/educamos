package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaDepartamentoCentroProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaPerfilEmpleadoEditorProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaPerfilEmpleadoEditorProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.HistorialResponsableProgramacionDidacticaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface EvaPerfilEmpleadoEditorProgramacionDidacticaRepository
        extends AbstractRepository<EvaPerfilEmpleadoEditorProgramacionDidactica, Long, QEvaPerfilEmpleadoEditorProgramacionDidactica> {
	
	@Query(value = "SELECT emp.X_EMPLEADO idEmpleado, DELPHOS.TLF_NOMBRE(emp.NOMBRE, emp.APELLIDO1, emp.APELLIDO2) nombreEmpleado, "
			+ "depcen.X_DEPARTCEN idDepartamento, ddg.D_DEPARTAMENTO nombreDepartamento, eepd.F_INICIO fechaInicio, "
			+ "eepd.F_FIN fechaFin, DECODE(eepd.LG_ACTIVO, 1, 'true', 'false') activo "
			+ "FROM DELPHOS.EVA_EMPEDITPROGDID eepd "
			+ "INNER JOIN DELPHOS.TLEMPLEADOS emp ON emp.X_EMPLEADO = eepd.X_EMPLEADO "
			+ "INNER JOIN DELPHOS.TLPTOTRAEMP pte ON pte.X_EMPLEADO = emp.X_EMPLEADO AND pte.X_CENTRO = eepd.X_CENTRO "
			+ "LEFT JOIN DELPHOS.TLMIEDEPART md ON md.X_EMPLEADO = emp.X_EMPLEADO AND md.F_TOMAPOS = pte.F_TOMAPOS "
			+ "LEFT JOIN DELPHOS.TLDEPARTCEN depcen ON depcen.X_DEPARTCEN = md.X_DEPARTCEN AND depcen.X_CENTRO = pte.X_CENTRO "
			+ "LEFT JOIN DELPHOS.TLDEPDENGEN ddg ON ddg.X_DEPDENGEN = depcen.X_DEPDENGEN "
			+ "WHERE eepd.X_OFERTAMATRIG = :idOfertaMatrig AND eepd.X_MATERIAOMG = :idMateriaOmg "
			+ "AND eepd.X_CENTRO = :idCentro AND eepd.NU_ANNO = :anyo "
			+ "AND ('-1' = :idNivelCurricular OR eepd.X_NIVEADAP = :idNivelCurricular) "
			+ "AND ('-1' = :idMateriaOmgAdap OR eepd.X_MATERIAOMGADAP = :idMateriaOmgAdap) "
			+ "ORDER BY eepd.F_INICIO DESC", nativeQuery = true)
	public List<HistorialResponsableProgramacionDidacticaProjection> getHistorialResponsablesProgramacionDidactica(@Param("idOfertaMatrig") Long idOfertaMatrig,
																												   @Param("idMateriaOmg") Long idMateriaOmg,
																												   @Param("idCentro") Long idCentro,
																												   @Param("anyo") Integer anyo,
																												   @Param("idNivelCurricular") Long idNivelCurricular,
																												   @Param("idMateriaOmgAdap") Long idMateriaOmgAdap);

	@Query(value = "SELECT    " +
			"    idEmpleado,   " +
			"    nombreEmpleado,   " +
			"    idDepartamento,   " +
			"    nombreDepartamento   " +
			"FROM (   " +
			"    SELECT    " +
			"        emp.X_EMPLEADO AS idEmpleado,    " +
			"        DELPHOS.TLF_NOMBRE(emp.NOMBRE, emp.APELLIDO1, emp.APELLIDO2) AS nombreEmpleado,    " +
			"        depcen.X_DEPARTCEN AS idDepartamento,    " +
			"        ddg.D_DEPARTAMENTO AS nombreDepartamento   " +
			"    FROM     " +
			"        DELPHOS.TLEMPLEADOS emp   " +
			"    INNER JOIN    " +
			"        DELPHOS.TLPTOTRAEMP pte ON pte.X_EMPLEADO = emp.X_EMPLEADO   " +
			"    INNER JOIN    " +
			"        DELPHOS.TLPUEORIPER pue ON pue.X_EMPLEADO = pte.X_EMPLEADO AND pte.F_TOMAPOS = pue.F_TOMAPOS    " +
			"    INNER JOIN    " +
			"        DELPHOS.TLPERFILES per ON per.X_PERFIL = pue.X_PERFIL    " +
			"    LEFT JOIN    " +
			"        DELPHOS.TLMIEDEPART md ON md.X_EMPLEADO = emp.X_EMPLEADO AND md.F_TOMAPOS = pte.F_TOMAPOS   " +
			"    LEFT JOIN    " +
			"        DELPHOS.TLDEPARTCEN depcen ON depcen.X_DEPARTCEN = md.X_DEPARTCEN AND depcen.X_CENTRO = pte.X_CENTRO   " +
			"    LEFT JOIN    " +
			"        DELPHOS.TLDEPDENGEN ddg ON ddg.X_DEPDENGEN = depcen.X_DEPDENGEN   " +
			"    WHERE    " +
			"        pte.X_CENTRO = :idCentro AND depcen.C_ANNO = :anyo    " +
			"        AND (TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE)    " +
			"        AND (TRUNC(PTE.F_CESE) >= TRUNC(SYSDATE) OR PTE.F_CESE IS NULL))    " +
			"        AND (:idEmpleado = -1 OR emp.X_EMPLEADO != :idEmpleado)   " +
			"        AND PER.C_CODIGO = 'P'   " +
			"    UNION ALL  " +
			"    SELECT    " +
			"        emp.X_EMPLEADO AS idEmpleado,    " +
			"        DELPHOS.TLF_NOMBRE(emp.NOMBRE, emp.APELLIDO1, emp.APELLIDO2) AS nombreEmpleado,   " +
			"        -1 AS idDepartamento,   " +
			"        '-' AS nombreDepartamento " +
			"    FROM    " +
			"        DELPHOS.TLEMPLEADOS emp   " +
			"    INNER JOIN    " +
			"        DELPHOS.TLPTOTRAEMP pte ON pte.X_EMPLEADO = emp.X_EMPLEADO   " +
			"    INNER JOIN    " +
			"        DELPHOS.TLPUEORIPER pue ON pue.X_EMPLEADO = pte.X_EMPLEADO AND pte.F_TOMAPOS = pue.F_TOMAPOS    " +
			"    INNER JOIN    " +
			"        DELPHOS.TLPERFILES per ON per.X_PERFIL = pue.X_PERFIL    " +
			"    WHERE    " +
			"        pte.X_CENTRO = :idCentro    " +
			"        AND (:idEmpleado = -1 OR emp.X_EMPLEADO != :idEmpleado)   " +
			"        AND (TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE)    " +
			"        AND (TRUNC(PTE.F_CESE) >= TRUNC(SYSDATE) OR PTE.F_CESE IS NULL))    " +
			"        AND PER.C_CODIGO = 'P'   " +
			"        AND NOT EXISTS (   " +
			"            SELECT 1   " +
			"            FROM DELPHOS.TLMIEDEPART md " +
			"            INNER JOIN DELPHOS.TLDEPARTCEN depcen2 ON depcen2.X_DEPARTCEN = md.X_DEPARTCEN  " +
			"            WHERE md.X_EMPLEADO = emp.X_EMPLEADO AND md.F_TOMAPOS = pte.F_TOMAPOS AND depcen2.X_CENTRO = pte.X_CENTRO AND depcen2.C_ANNO = :anyo  " +
			"        ) " +
			"       AND NOT EXISTS ( " +
			"        SELECT 1 " +
			"        FROM DELPHOS.TLPTOTRAEMP pte2 " +
			"        INNER JOIN DELPHOS.TLCURSOACA curso ON curso.C_ANNO = :anyo " +
			"        WHERE pte2.X_EMPLEADO_SUSTITUYE = pte.X_EMPLEADO AND (TRUNC(PTE2.F_TOMAPOS) <= TRUNC(SYSDATE)    " +
			"        AND (TRUNC(PTE2.F_CESE) >= TRUNC(SYSDATE) OR PTE2.F_CESE IS NULL)) " +
			"        AND TLF_INTERSECPER(pte2.F_TOMAPOS, pte2.F_CESE, curso.F_INICIO, curso.F_FINAL) = 1) " +
			") resultado   " +
			"ORDER BY idDepartamento, nombreEmpleado", nativeQuery = true)
	public List<HistorialResponsableProgramacionDidacticaProjection> getProfesoresDepartamentoProgramacionDidactica(@Param("idCentro") Long idCentro,
																												   @Param("anyo") Integer anyo,
	                                                                                                               @Param("idEmpleado") Long idEmpleado);

	@Query(value = "SELECT emp.X_EMPLEADO idEmpleado, DELPHOS.TLF_NOMBRE(emp.NOMBRE, emp.APELLIDO1, emp.APELLIDO2) nombreEmpleado, " +
			"depcen.X_DEPARTCEN idDepartamento, ddg.D_DEPARTAMENTO nombreDepartamento, eepd.F_INICIO fechaInicio, " +
			"eepd.F_FIN fechaFin, DECODE(eepd.LG_ACTIVO, 1, 'true', 'false') activo " +
			"FROM DELPHOS.EVA_EMPEDITPROGDID eepd " +
			"INNER JOIN DELPHOS.TLEMPLEADOS emp ON emp.X_EMPLEADO = eepd.X_EMPLEADO " +
			"INNER JOIN DELPHOS.TLPTOTRAEMP pte ON pte.X_EMPLEADO = emp.X_EMPLEADO AND pte.X_CENTRO = eepd.X_CENTRO " +
			"LEFT JOIN DELPHOS.TLMIEDEPART md ON md.X_EMPLEADO = emp.X_EMPLEADO AND md.F_TOMAPOS = pte.F_TOMAPOS " +
			"LEFT JOIN DELPHOS.TLDEPARTCEN depcen ON depcen.X_DEPARTCEN = md.X_DEPARTCEN AND depcen.X_CENTRO = pte.X_CENTRO " +
			"LEFT JOIN DELPHOS.TLDEPDENGEN ddg ON ddg.X_DEPDENGEN = depcen.X_DEPDENGEN " +
			"WHERE eepd.X_OFERTAMATRIG = :idOfertaMatrig AND eepd.X_MATERIAOMG = :idMateriaOmg " +
			"AND eepd.LG_ACTIVO = 1 " +
			"AND eepd.X_CENTRO = (SELECT CEN.X_CENTRO FROM TLCENTROS CEN WHERE CEN.C_CODIGO = :idCentro) AND eepd.NU_ANNO = :anyo " +
			"AND ('-1' = :idNivelCurricular OR eepd.X_NIVEADAP = :idNivelCurricular) " +
			"AND ('-1' = :idMateriaOmgAdap OR eepd.X_MATERIAOMGADAP = :idMateriaOmgAdap) " +
			"ORDER BY eepd.LG_ACTIVO, eepd.F_INICIO DESC", nativeQuery = true)
	public List<HistorialResponsableProgramacionDidacticaProjection> getEmpleadosEditores(@Param("idOfertaMatrig") Long idOfertaMatrig,
																						   @Param("idMateriaOmg") Long idMateriaOmg,
																						   @Param("idCentro") Long idCentro,
																						   @Param("anyo") Integer anyo,
																						   @Param("idNivelCurricular") Long idNivelCurricular,
																						   @Param("idMateriaOmgAdap") Long idMateriaOmgAdap);

	public EvaPerfilEmpleadoEditorProgramacionDidactica findByIdOfertaMatrigAndIdMateriaOmgAndIdCentroAndAnnoAndNiveAdapAndIdMateriaOmgAdapAndActivo(Long idOfertaMatrig, Long idMateriaOmg, Long idCentro, Integer anno, Long niveAdap, Long idMateriaOmgAdap, Integer activo);

	@Query(value = "SELECT DISTINCT dpc.X_DEPARTCEN idDepartamento, gen.D_DEPARTAMENTO  nombreDepartamento " +
			"FROM DELPHOS.TLDEPDENGEN gen " +
			"INNER JOIN delphos.TLDEPARTCEN dpc ON gen.X_DEPDENGEN  = dpc.X_DEPDENGEN  " +
			"INNER JOIN delphos.TLMIEDEPART mdp ON mdp.X_DEPARTCEN = dpc.X_DEPARTCEN " +
			"INNER JOIN delphos.TLCURSOACA cua ON cua.C_ANNO = dpc.C_ANNO " +
			"WHERE dpc.X_CENTRO = :idCentro " +
			"AND dpc.C_ANNO = :anyo", nativeQuery = true)
	public List<EvaDepartamentoCentroProjection> getDepartamentosCentro(@Param("idCentro") Long idCentro,
																		@Param("anyo") Long anyo);
}
