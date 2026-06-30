package es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual;

import java.util.Date;
import java.util.List;

import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.ProgramacionAulaVirtual;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.EvaAulaVirtual;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.QEvaAulaVirtual;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.AlumnoProjection;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.EvaAulaVirtualProjection;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection.RelacionAlumnoOidProjection;
import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.SistemaCalificacionProjection;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.AlumnosPorMateriaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaAulaVirtualRepository extends AbstractRepository<EvaAulaVirtual, Long, QEvaAulaVirtual> {

	@Query(value = "SELECT TLF_ANNOACTUAL FROM DUAL", nativeQuery = true)
    String getAnnoAcademicoActual();
	
	@Query(value = "SELECT AUL.X_AULA id, AUL.D_AULA aula, SUBSTR(REVERSE(aul.ID_PLATAFORMA), 6, 1) idPlataforma, "
			+ "(SELECT PARGEN.T_VALPAR "
			+ "FROM DELPHOS_SEGEDU.TLPARGEN PARGEN "
			+ "WHERE PARGEN.T_NOMPAR LIKE TRIM('URL_SW_MOODLE_API_TOKEN_'||SUBSTR(REVERSE(aul.ID_PLATAFORMA), 6, 1)||'_'||TLF_ANNOACTUAL)) tokenPlataforma, "
			+ "AUL.X_MATERIAOMG idMateriaOMG, AUL.X_OFERTAMATRIG idOfertaMatrig, AUL.ID_CURSO cursoString "
			+ "FROM DELPHOS_SEGEDU.TLAULAS AUL "
			+ "INNER JOIN DELPHOS_SEGEDU.TLAULASUSUARIOS AUSU ON AUSU.X_AULA = AUL.X_AULA AND AUSU.X_EMPLEADO IN (:idEmpleado) "
			+ "WHERE AUL.C_ANNO = :anno "
			+ "AND AUL.L_ACTIVA = 'S' "
			+ "ORDER BY AUL.D_AULA", nativeQuery = true)
    List<EvaAulaVirtualProjection> findAulasVirtualesByEmpleadoAnno(@Param("idEmpleado") List<Long> idEmpleado, @Param("anno") Integer anno);

	
	@Query(value = "SELECT ALU.X_ALUMNO idAlumno, tlf_nombre(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) nombreAlumno, mat.X_MATRICULA idMatricula,  " 
		+   "UNICEN.T_NOMBRE unidad, UNICEN.X_UNIDAD idUnidad, aulaU.X_AULA aula  " 
		+   "FROM TLALUMNOS ALU   " 
		+   "INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO " 
		+   "INNER JOIN EVA_RELPROGAULALUM alumnPro ON ALUMNPRO.X_MATRICULA = MAT.X_MATRICULA AND ALUMNPRO.ID_PROGAULA = :idProgramacionAula " 
		+   "INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = MAT.X_UNIDAD " 
		+   "LEFT JOIN DELPHOS_SEGEDU.TLAULASUSUARIOS aulaU ON aulaU.X_AULA = :idAula AND aulaU.X_ALUMNO = ALU.X_ALUMNO  " 
		+   "UNION  "  
		+   "SELECT ALU.X_ALUMNO idAlumno, tlf_nombre(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) nombreAlumno, mat.X_MATRICULA idMatricula,  " 
		+   "UNICEN.T_NOMBRE unidad, UNICEN.X_UNIDAD idUnidad, aulaU.X_AULA aula " 
		+   "FROM TLALUMNOS ALU   " 
		+   "INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO " 
		+   "INNER JOIN EVA_RELPROGAULAUNIDAD unidadPro ON UNIDADPRO.ID_PROGAULA  = :idProgramacionAula AND MAT.X_UNIDAD = UNIDADPRO.X_UNIDAD AND UNIDADPRO.LG_AFECTATODOS = 1 " 
		+   "INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = MAT.X_UNIDAD " 
		+   "LEFT JOIN DELPHOS_SEGEDU.TLAULASUSUARIOS aulaU ON aulaU.X_AULA = :idAula AND aulaU.X_ALUMNO = ALU.X_ALUMNO", nativeQuery = true)
	List<AlumnosPorMateriaProjection> getAlumnosByAula(@Param("idProgramacionAula") Long idProgramacionAula, @Param("idAula") Long idAula);
	
	@Query(value = "SELECT alu.X_ALUMNO id, alu.T_NOMBRE nombre, alu.T_APELLIDO1 apellido1, alu.T_APELLIDO2 apellido2, alu.C_NUMESCOLAR numEscolar, "
			+ 	"tlf_nombre(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) nombreCompleto  " 
			+   "FROM TLALUMNOS ALU   " 
			+   "INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO " 
			+   "INNER JOIN EVA_RELPROGAULALUM alumnPro ON ALUMNPRO.X_MATRICULA = MAT.X_MATRICULA AND ALUMNPRO.ID_PROGAULA = :idProgramacionAula " 
			+   "INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = MAT.X_UNIDAD " 
			+   "LEFT JOIN DELPHOS_SEGEDU.TLAULASUSUARIOS aulaU ON aulaU.X_AULA = :idAula AND aulaU.X_ALUMNO = ALU.X_ALUMNO  " 
			+   "UNION  "  
			+   "SELECT alu.X_ALUMNO id, alu.T_NOMBRE nombre, alu.T_APELLIDO1 apellido1, alu.T_APELLIDO2 apellido2, alu.C_NUMESCOLAR numEscolar, "
			+ 	"tlf_nombre(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) nombreCompleto " 
			+   "FROM TLALUMNOS ALU   " 
			+   "INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO " 
			+   "INNER JOIN EVA_RELPROGAULAUNIDAD unidadPro ON UNIDADPRO.ID_PROGAULA  = :idProgramacionAula AND MAT.X_UNIDAD = UNIDADPRO.X_UNIDAD AND UNIDADPRO.LG_AFECTATODOS = 1 " 
			+   "INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = MAT.X_UNIDAD " 
			+   "LEFT JOIN DELPHOS_SEGEDU.TLAULASUSUARIOS aulaU ON aulaU.X_AULA = :idAula AND aulaU.X_ALUMNO = ALU.X_ALUMNO "
			+   "ORDER BY nombreCompleto", nativeQuery = true)
		List<AlumnoProjection> getAlumnosByProgAulaAndAula(@Param("idProgramacionAula") Long idProgramacionAula, @Param("idAula") Long idAula);

	@Query(value = "SELECT AUL.X_AULA id, AUL.D_AULA aula, SUBSTR(REVERSE(aul.ID_PLATAFORMA), 6, 1) idPlataforma, "
			+ "(SELECT PARGEN.T_VALPAR "
			+ "FROM DELPHOS_SEGEDU.TLPARGEN PARGEN "
			+ "WHERE PARGEN.T_NOMPAR LIKE TRIM('URL_SW_MOODLE_API_TOKEN_'||SUBSTR(REVERSE(aul.ID_PLATAFORMA), 6, 1)||'_'||TLF_ANNOACTUAL)) tokenPlataforma, "
			+ "AUL.X_MATERIAOMG idMateriaOMG, AUL.X_OFERTAMATRIG idOfertaMatrig, AUL.ID_CURSO cursoString "
			+ "FROM DELPHOS_SEGEDU.TLAULAS AUL "
			+ "WHERE AUL.X_AULA  = :idAula", nativeQuery = true)
	EvaAulaVirtualProjection findAulaById(Long idAula);

	@Query(value = "SELECT DISTINCT ALU.X_ALUMNO idAlumno, USUMODACC.OID oid, MAT.X_MATRICULA idMatricula  " +
			" FROM TLALUMNOS ALU  " +
			" INNER JOIN DELPHOS_MODACC.DOCUMENTACIONES DOC ON DOC.T_IDENTIFICACION = ALU.C_NUMIDE OR DOC.T_IDENTIFICACION = ALU.C_NUMESCOLAR  " +
			" INNER JOIN DELPHOS_MODACC.USUARIOS USUMODACC ON USUMODACC.OID_PERSONA = DOC.OID_PERSONA   " +
			" INNER JOIN DELPHOS_SEGEDU.TLAULASUSUARIOS AULAUSU ON AULAUSU.X_AULA = :idAula AND AULAUSU.X_ALUMNO = ALU.X_ALUMNO  " +
			" INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO  " +
			" INNER JOIN EVA_RELPROGAULALUM alumnPro ON ALUMNPRO.X_MATRICULA = MAT.X_MATRICULA", nativeQuery = true)
	List<RelacionAlumnoOidProjection> getRelacionAlumnoOidByIdAula(Long idAula);

	@Query(value = "SELECT INF.D_VALOR "
			+ "FROM DELPHOS_SEGEDU.TLAULASINFO INF "
			+ "WHERE INF.X_AULA = :idAula "
			+ "AND INF.C_INFO = 'MOODLE_ID'", nativeQuery = true)
	String getCourseIdByIdAula(Long idAula);
	
	@Query(value = "SELECT cal.X_CALIFICA idCalifica, cal.X_SISTCAL idSistCal, " +
            "NVL(cal.N_NUMERO, cal.N_ORDEN) nota, cal.D_CALIFICA descripcion, cal.T_ABREV descCal, cal.L_APRUEBA aprueba " +  
			"FROM TLCALIFICACIONES CAL, EVA_ACTIVIDAD act,  EVA_PROGDIDAC didac, EVA_RELPROGUNIDAD relProuni, " + 
			"(SELECT * FROM TLSISCALOMG WHERE :anno BETWEEN C_ANNODESDE AND NVL(C_ANNOHASTA, 9999)) SCO " + 
			"WHERE act.ID_ACTIVIDAD = :idActividad " + 
			"AND act.ID_UNIDADPROG =  relProuni.ID_UNIDADPROG " + 
			"AND RELPROUNI.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC " + 
			"AND SCO.X_OFERTAMATRIG(+) = DIDAC.X_OFERTAMATRIG " + 
			"AND cal.X_SISTCAL = sco.X_SISTCAL", nativeQuery = true)
	List<SistemaCalificacionProjection> getSistemaCalifActividad(Long anno, Long idActividad);

	@Query(value = "SELECT cal.X_CALIFICA idCalifica, cal.X_SISTCAL idSistCal,   " +
			"NVL(cal.N_NUMERO, cal.N_ORDEN) nota, cal.D_CALIFICA descripcion, cal.T_ABREV descCal, cal.L_APRUEBA aprueba     " +
			"FROM TLCALIFICACIONES CAL " +
			"WHERE cal.X_SISTCAL = :idSistemaCalifica", nativeQuery = true)
	List<SistemaCalificacionProjection> getSistemaCalifActividadCalculoCriterio(@Param("idSistemaCalifica") Long idSistemaCalifica);

	@Query(value = "SELECT PROGAUL.ID_PROGAULA idProgAula, PROGAUL.ID_PROGDIDAC idProgDidac, " +
			"PROGAUL.TX_NOMBRE nombre, PROGAUL.X_AULA idAula " +
			"FROM DELPHOS_SEGEDU.TLAULASINFO AULINF " +
			"INNER JOIN DELPHOS_SEGEDU.TLAULAS AUL ON AUL.X_AULA = AULINF.X_AULA  " +
			"    AND AUL.C_ANNO = DELPHOS.TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION " +
			"    AND SUBSTR(REVERSE(aul.ID_PLATAFORMA), 6, 1) = :idPlataforma " +
			"INNER JOIN EVA_PROGAULA PROGAUL ON PROGAUL.X_AULA = AUL.X_AULA  " +
			"WHERE AULINF.C_INFO = 'MOODLE_ID' " +
			"AND AULINF.D_VALOR = :idCurso", nativeQuery = true)
	List<ProgramacionAulaVirtual> listDetalleProgramacionAula(@Param("idPlataforma") Long idPlataforma,
															  @Param("idCurso") Long idCurso);

	@Query(value = "SELECT DISTINCT ciclo.X_ETAPADEPENDEDE " +
			"FROM EVA_PROGAULA PROGAULA " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC  " +
			"INNER JOIN TLMATOFEMATRG mat ON mat.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG " +
			"INNER JOIN TLMATERIASCURSO matCurso ON mat.X_MATERIAC = matCurso.X_MATERIAC   " +
			"INNER JOIN TLCURSOMODA modalidad ON matCurso.x_cursomod = modalidad.x_cursomod   " +
			"INNER JOIN TLETAPAS curso ON curso.x_etapa = modalidad.x_etapa   " +
			"INNER JOIN TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA   " +
			"INNER JOIN TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA " +
			"WHERE PROGAULA.ID_PROGAULA = :idProgramacionAula  " +
			"AND (etapa.D_ETAPA like '%(LOMLOE)%' OR ciclo.D_ETAPA like '%(LOMLOE)%')", nativeQuery = true)
	Long getEtapaByIdProgAula(@Param("idProgramacionAula") Long idProgramacionAula);
}