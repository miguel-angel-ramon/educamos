package es.jccm.edu.evaluacion.adapter.out.repositories.convocatoriasEvaluacion;

import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection.*;
import es.jccm.edu.evaluacion.application.domain.materiasProfesor.MateriasProfesor;
import es.jccm.edu.evaluacion.application.domain.materiasProfesor.QMateriasProfesor;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.util.List;

@Repository
public interface ConvocatoriasEvaluacionRepository extends AbstractRepository<MateriasProfesor, Long, QMateriasProfesor> {

    @Query(value = "SELECT ALU.X_ALUMNO IDALUMNO, MUA.X_MATRICULA IDMATRICULA, MMA.X_MATMATRICULA IDMATMATRIALU, ALU.C_NUMESCOLAR NUMESCOLAR, ALU.T_NOMBRE NOMBRE, "
    		+ "ALU.T_APELLIDO1 || ' ' || ALU.T_APELLIDO2 APELLIDOS, PEV.X_PROEVA idPromocion, PEV.X_ESTGENMATR idEstado, "
    		+ "ESG.D_ESTGENMATR descripcionEstado, PEV.F_SESION fechaSesion "
    		+ "FROM TLMATMATRIALU MMA "
    		+ "INNER JOIN TLMATALU MUA ON MMA.X_MATRICULA = MUA.X_MATRICULA "
    		+ "INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MUA.X_ALUMNO "
    		+ "LEFT JOIN TLPROEVA PEV ON PEV.X_MATRICULA = MUA.X_MATRICULA "
    		+ "LEFT JOIN TLESTGENMATR ESG ON ESG.X_ESTGENMATR = PEV.X_ESTGENMATR "
    		+ "WHERE MMA.X_MATMATRICULA IN (:idMatmatrialus) "
    		+ "ORDER BY ALU.T_APELLIDO1", nativeQuery = true)
    List<AlumnoConvocatoriasProjection> alumnosEvaluacion(@Param("idMatmatrialus") Long[] idMatmatrialus);

    @Query(value = "select distinct mma.x_matmatricula idMatMatriAlu, omg.x_ofertamatrig idOfertaMatrig, mua.c_anno anno, mcu.x_materiac idMateria, mcu.s_materiac materia, udc.x_unidad idUnidad, udc.t_nombre nombreCurso, "
    		+ "mua.x_matricula idMatricula, alu.x_alumno idAlumno, alu.c_numescolar numEscolar, alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos, "
    		+ "convCen.x_convcentroomc idConvCentroOmc, conv.D_CONVOCATORIA convocatoria, estConv.d_estadoconv estadoConv, cal.d_califica nota, cal.l_aprueba aprueba "
    		+ "from tlmatmatrialu mma "
    		+ "inner join tlmatalu mua on mma.X_MATRICULA = mua.X_MATRICULA "
    		+ "inner join tlmatofematrg mog on mog.x_materiaomg = mma.x_materiaomg "
    		+ "inner join tlmateriascurso mcu on mcu.x_materiac = mog.x_materiac "
    		+ "inner join tlalumnos alu on alu.x_alumno = mua.x_alumno "
    		+ "inner join tlunidadescen udc on mua.x_unidad = udc.x_unidad "
    		+ "inner join tlconvunidad cou on cou.x_unidad = udc.x_unidad "
			+ "inner join tlofematrgen omg on omg.x_ofertamatrig = mua.x_ofertamatrig "
    		+ "INNER JOIN tlestadosconv estConv ON estConv.x_estadoconv = cou.x_estadoconv "
    		+ "INNER JOIN TLCONVCENOMC convCen ON convCen.x_convcentroomc = cou.x_convcentroomc "
    		+ "INNER JOIN TLCONVCENTROS conv ON conv.X_CONVCENTRO = convCen.X_CONVCENTRO "
    		+ "LEFT JOIN TLNOTCALMAT ncal ON ncal.x_matmatricula = mma.x_matmatricula "
    		+ "LEFT JOIN TLCALIFICACIONES cal ON cal.x_califica = ncal.x_califica "
    		+ "where mma.X_MATMATRICULA = :idMatmatrialu "
    		+ "AND convCen.x_convcentroomc = :idConvCentroOmc", nativeQuery = true)
    DatosAlumnoConvProjection datosAlumnoConvocatoria(@Param("idMatmatrialu") Long idMatmatrialu,
                                                      @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Query(value = "SELECT F.B_FOTO " + "FROM TLALUMNOS ALU, TLALUMNOSFOTO F "
            + "WHERE ALU.C_NUMESCOLAR = :numescolar AND ALU.X_ALUMNO = F.X_ALUMNO ", nativeQuery = true)
    Blob getFotoAlumno(@Param("numescolar") String numescolar);

    @Query(value = "SELECT POND.X_PONDERACION AS idPonderacion   " +
			"FROM TLPONDERACION POND  " +
			"INNER JOIN DELPHOS.TLMATOFEMATRG MATGR ON MATGR.X_MATERIAOMG = POND.X_MATERIA   " +
			"INNER JOIN DELPHOS.TLMATERIASCURSO MATCUR ON MATCUR.X_MATERIAC = MATGR.X_MATERIAC " +
			"INNER JOIN DELPHOS.TLMATMATRIALU MMA ON MATGR.X_MATERIAOMG = MMA.X_MATERIAOMG " +
			"WHERE MMA.X_MATMATRICULA = :idMatMatriAlu  " +
			"AND POND.X_DOCENTE = :idEmpleado", nativeQuery = true)
    PonderacionConvProjection getPonderacionConvocatoria(@Param("idMatMatriAlu") Long idMatMatriAlu, @Param("idEmpleado") Long idEmpleado);

    @Query(value = "SELECT PONCOMESP.X_RELPONCOMESP AS idRelacionCompe, PONCOMESP.X_COMESP AS idCompetencia, " +
            "COM.T_ABREV AS codigo, COM.D_COMESP AS descripcion, PONCOMESP.PORCENTAJE peso " +
            "FROM DELPHOS.TLRELPONCOMESP PONCOMESP " +
            "INNER JOIN DELPHOS.TLCOMESP COM ON COM.X_COMESP = PONCOMESP.X_COMESP " +
            "WHERE PONCOMESP.X_PONDERACION = :idPonderacion ORDER BY com.n_ordenpres", nativeQuery = true)
    List<CompetenciasConvProjection> getCompetenciasConvocatoria(@Param("idPonderacion") Long idPonderacion);

    @Query(value = "SELECT CAL.T_ABREV valoracion, CAL.L_APRUEBA aprueba " +
            "FROM DELPHOS.TLRELPONCOMESP PONCOMESP " +
            "INNER JOIN DELPHOS.TLCOMESP COM ON COM.X_COMESP = PONCOMESP.X_COMESP " +
            "LEFT JOIN DELPHOS.TLVALCOMALU VALCOM ON VALCOM.X_COMESP = COM.X_COMESP " +
            "LEFT JOIN DELPHOS.TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VALCOM.X_CALIFICA " +
            "WHERE PONCOMESP.X_PONDERACION = :idPonderacion AND com.x_comesp = :idCompetencia AND VALCOM.X_MATMATRICULA = :idMatMatriAlu", nativeQuery = true)
    ValoracionConvProjection valoracionCompetencia(@Param("idPonderacion") Long idPonderacion,
                                  @Param("idCompetencia") Long idCompetencia,
                                  @Param("idMatMatriAlu") Long idMatMatriAlu);

    @Query(value = "SELECT DISTINCT RELCRI.X_RELPONCRIEVA AS idRelacionCri, CRI.X_CRIEVA AS idCriterio, " +
            "CRI.T_ABREV AS codigo, CRI.D_CRIEVA AS descripcion, " +
            "RELCRI.PORCENTAJE peso, cri.n_ordenpres orden " +
            "FROM DELPHOS.TLCRIEVA CRI  " +
            "INNER JOIN DELPHOS.TLRELPONCRIEVA RELCRI ON RELCRI.X_CRIEVA = CRI.X_CRIEVA " +
            "INNER JOIN DELPHOS.TLPONDERACION PON ON PON.X_PONDERACION = RELCRI.X_PONDERACION " +
            "INNER JOIN DELPHOS.TLRELPONCOMESP COMP ON COMP.X_COMESP = CRI.X_COMESP  " +
            "LEFT JOIN DELPHOS.TLVALCRIALU VALCRI ON VALCRI.X_CRIEVA = RELCRI.X_CRIEVA " +
            "LEFT JOIN DELPHOS.TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VALCRI.X_CALIFICA " +
            "WHERE RELCRI.X_PONDERACION = :idPonderacion AND CRI.X_COMESP = :idCompetencia " +
            "ORDER BY cri.n_ordenpres", nativeQuery = true)
    List<CriteriosConvProjection> getCriteriosConvocatoria(@Param("idPonderacion") Long idPonderacion,
                                                           @Param("idCompetencia") Long idCompetencia);

    @Query(value = "SELECT DISTINCT CAL.T_ABREV valoracion, CAL.L_APRUEBA aprueba " +
            "FROM DELPHOS.TLCRIEVA CRI  " +
            "INNER JOIN DELPHOS.TLRELPONCRIEVA RELCRI ON RELCRI.X_CRIEVA = CRI.X_CRIEVA " +
            "INNER JOIN DELPHOS.TLPONDERACION PON ON PON.X_PONDERACION = RELCRI.X_PONDERACION " +
            "INNER JOIN DELPHOS.TLRELPONCOMESP COMP ON COMP.X_COMESP = CRI.X_COMESP  " +
            "LEFT JOIN DELPHOS.TLVALCRIALU VALCRI ON VALCRI.X_CRIEVA = RELCRI.X_CRIEVA " +
            "LEFT JOIN DELPHOS.TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VALCRI.X_CALIFICA " +
            "WHERE RELCRI.X_PONDERACION = :idPonderacion AND CRI.X_COMESP = :idCompetencia " +
            "AND cri.x_crieva = :idCriterio AND VALCRI.X_MATMATRICULA = :idMatMatriAlu", nativeQuery = true)
    ValoracionConvProjection valoracionCriterios(@Param("idPonderacion") Long idPonderacion,
                                @Param("idCompetencia") Long idCompetencia,
                                @Param("idCriterio") Long idCriterio,
                                @Param("idMatMatriAlu") Long idMatMatriAlu);
    
    @Query(value = "select alu.x_alumno idAlumno, mua.x_matricula idMatricula, " +
    		"mua.c_anno anno, udc.t_nombre nombreCurso, alu.c_numescolar numEscolar, " +
    		"alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos, " +
    		"omg.x_ofertamatrig idOfertaMatrig, pev.x_proeva idPromocion, " +
    		"pev.x_estgenmatr idEstado, esg.d_estgenmatr descripcionEstado, " +
    		"pev.f_sesion fechaSesion, esg.c_resultado cResultado, " +
    		"etp.x_etapa idEtapa, etp.d_etapa descripcionEtapa, " +
			"CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) THEN 1 ELSE 0 END AS acnee, " +
			"(SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) nivelCurricular " +
    		"from tlmatalu mua " +
    		"inner join tlalumnos alu on alu.x_alumno = mua.x_alumno " +
    		"inner join tlunidadescen udc on udc.x_unidad = mua.x_unidad " +
    		"and udc.x_centro = mua.x_centro and udc.c_anno = mua.c_anno " +
    		"inner join tlofematrgen omg on omg.x_ofertamatrig = mua.x_ofertamatrig " +
    		"left join tlproeva pev on pev.x_matricula = mua.x_matricula " +
    		"left join tlestgenmatr esg on esg.x_estgenmatr = pev.x_estgenmatr " +
    		"inner join delphos.tlcursoorg cuo on cuo.x_ofertamatrig = omg.x_ofertamatrig " +
    		"inner join delphos.tlmodalidades mda on mda.x_modalidad = omg.x_modalidad " +
    		"inner join delphos.tlcursomoda cmd on cmd.x_modalidad = mda.x_modalidad and cmd.x_cursomod = cuo.x_cursomod " +
    		"inner join delphos.tletapas cur on cur.x_etapa = cmd.x_etapa " +
    		"inner join delphos.tletapas ccl on cur.x_etapadependede = ccl.x_etapa " +
    		"inner join delphos.tletapas etp on ccl.x_etapadependede = etp.x_etapa " +
    		"where cur.x_etapa = :idCurso " +
    		"AND mua.x_unidad = :idUnidad and nvl(mua.c_resultado, 99) > 1 " +
    		"order by alu.t_apellido1", nativeQuery = true)
    List<AlumnoEvalConvProjection> getAlumnosEvaluacion(@Param("idCurso") Long idCurso, @Param("idUnidad") Long idUnidad);
    
    @Query(value = "SELECT mma.x_matmatricula idMatMatriAlu, mcu.x_materiac idMateria, mcu.s_materiac nombreMateria,  " +
			"est.x_estado idEstado, est.t_abrev nombreEstado, CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND mma.x_adaptacion IS NOT null) THEN 1 ELSE 0 END AS acnee, " +
			"    (SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND mma.x_adaptacion IS NOT null) nivelCurricular, " +
			"    (SELECT MATCURSO.T_ABREV FROM EVA_PROGDIDAC PD " +
			"             INNER JOIN TLMATOFEMATRG MATOFEMATRG ON MATOFEMATRG.X_MATERIAOMG = PD.X_MATERIAOMGADAP " +
			"             INNER JOIN TLMATERIASCURSO MATCURSO ON MATCURSO.X_MATERIAC = MATOFEMATRG.X_MATERIAC " +
			"             WHERE PD.NU_ANNO = mua.C_ANNO AND PD.X_OFERTAMATRIG = MUA.X_OFERTAMATRIG " +
			"             AND PD.X_NIVEADAP = MUA.X_NIVEADAP AND PD.X_MATERIAOMG = MMA.X_MATERIAOMG AND PD.X_CENTRO = mua.x_centro) materiaAdap " +
			"FROM tlmatalu mua  " +
			"INNER JOIN tlmatmatrialu mma ON mma.x_matricula = mua.x_matricula  " +
			"INNER JOIN tlmatofematrg mog ON mog.x_materiaomg = mma.x_materiaomg  " +
			"INNER JOIN tlmateriascurso mcu ON mcu.x_materiac = mog.x_materiac  " +
			"INNER JOIN tlestadomat est ON est.x_estado = mma.x_estado  " +
			"WHERE nvl(mua.c_resultado, 99) > 1 AND mua.x_matricula = :idMatricula  " +
			"ORDER BY mcu.N_ORDENACTA", nativeQuery = true)
    List<MateriaAlumnoEvaluacionProjection> getMateriasAlumno(@Param("idMatricula") Long idMatricula);
    
    @Query(value = "SELECT cce.x_convcentro idConvocatoria, cce.s_convocatoria nombreConvocatoria, cce.d_convocatoria descripcionConvocatoria, " +
    		"cco.x_convcentroomc idConvCentroOmc, est.d_estadoconv estado, obs.t_observaciones observaciones, " +
    		"decode(nvl((SELECT DISTINCT (1) FROM DELPHOS.tlconvogeneral cge WHERE cge.x_convogeneral = cce.x_convogeneral ), 0), 0, 'false', 'true') esFinal " +
    		"FROM tlmatalu mua " +
    		"INNER JOIN tlconvcenomc cco ON cco.x_ofertamatric = mua.x_ofertamatric " +
    		"INNER JOIN tlconvcentros cce ON cco.x_convcentro = cce.x_convcentro " +
    		"INNER JOIN tlconvunidad cu ON cu.x_convcentroomc = cco.x_convcentroomc AND cu.x_unidad = mua.x_unidad " +
    		"INNER JOIN tlestadosconv est ON est.x_estadoconv = cu.x_estadoconv " +
    		"LEFT JOIN tlobserveva obs ON obs.x_matricula = mua.x_matricula AND obs.x_convcentroomc = cco.x_convcentroomc " +
    		"WHERE nvl(mua.c_resultado, 99) > 1 AND mua.x_matricula = :idMatricula " +
    		"ORDER BY cce.n_orden", nativeQuery = true)
    List<ConvocatoriaAlumnoEvaluacionProjection> getConvocatoriasAlumno(@Param("idMatricula") Long idMatricula);
    
    @Query(value = "SELECT cco.x_convcentroomc idConvCentroOmc, cce.x_convcentro idConvocatoria, " +
    		"clc.x_califica idCalificaCal, nvl((clc.t_abrev), ncm.n_nota) notaCal, clc.l_aprueba apruebaCal, " +
    		"cld.x_califica idCalificaDef, nvl((cld.t_abrev), eva.n_nota) notaDef, cld.l_aprueba apruebaDef " +
    		"FROM tlmatmatrialu mma " +
    		"INNER JOIN tlmatalu mua ON nvl(mua.c_resultado, 99) > 1 AND mua.x_matricula = mma.x_matricula " +
    		"INNER JOIN tlmatofematrg mog ON mog.x_materiaomg = mma.x_materiaomg " +
    		"INNER JOIN tlconvcenomc cco ON cco.x_ofertamatric = mua.x_ofertamatric " +
    		"INNER JOIN tlconvcentros cce ON cco.x_convcentro = cce.x_convcentro " +
    		"INNER JOIN tlconvunidad cu ON cu.x_convcentroomc = cco.x_convcentroomc AND cu.x_unidad = mua.x_unidad " +
    		"LEFT JOIN tlnoteva eva ON eva.x_matmatricula = mma.x_matmatricula AND eva.x_convcentroomc = cco.x_convcentroomc " +
    		"LEFT JOIN tlnotcalmat ncm ON ncm.x_matmatricula = mma.x_matmatricula AND ncm.x_convcentroomc = cco.x_convcentroomc " +
    		"LEFT JOIN tlcalificaciones clc ON clc.x_califica = ncm.x_califica " +
    		"LEFT JOIN tlcalificaciones cld ON cld.x_califica = eva.x_califica " +
    		"WHERE mma.x_matmatricula = :idMatMatriAlu " +
    		"ORDER BY cce.n_orden", nativeQuery = true)
    List<CalificacionMateriaAlumnoEvaluacionProjection> getCalificacionesMateriaAlumno(@Param("idMatMatriAlu") Long idMatMatriAlu);
    
    @Query(value = "SELECT obs.x_matricula idMatricula, obs.x_convcentroomc idConvCentroOmc, obs.t_observaciones observaciones " +
    		"FROM delphos.tlobserveva obs WHERE obs.x_matricula = :idMatricula AND obs.x_convcentroomc = :idConvCentroOmc", nativeQuery = true)
    ObservacionConvProjection getObservacion(@Param("idMatricula") Long idMatricula,
    								@Param("idConvCentroOmc") Long idConvCentroOmc);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DELPHOS.TLOBSERVEVA(x_matricula, x_convcentroomc, t_observaciones) VALUES(:idMatricula, :idConvCentroOmc, :observaciones)", nativeQuery = true)
    Integer insertarObservacionesEvaluacionAlumno(@Param("idMatricula") Long idMatricula,
    										@Param("idConvCentroOmc") Long idConvCentroOmc,
    										@Param("observaciones") String observaciones);
    
    @Modifying
    @Query(value = "UPDATE DELPHOS.TLOBSERVEVA SET t_observaciones = :observaciones WHERE x_matricula = :idMatricula AND x_convcentroomc = :idConvCentroOmc", nativeQuery = true)
    void actualizarObservacionesEvaluacionAlumno(@Param("idMatricula") Long idMatricula,
    										@Param("idConvCentroOmc") Long idConvCentroOmc,
											@Param("observaciones") String observaciones);
    
    @Query(value = "SELECT rns.X_RELNOTSIST idRelacionCalificacion, rns.X_CALIFICA idCalificacion, " +
    		"cal.T_ABREV descCal, cal.L_APRUEBA aprueba, " +
    		"rns.N_MINIMO minima, rns.N_MAXIMO maxima " +
    		"FROM TLRELNOTSIST rns " +
    		"INNER JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = rns.X_CALIFICA " +
    		"WHERE rns.X_SISTCAL = :idSistCal", nativeQuery = true)
    List<RelacionCalificacionProjection> getRelacionCalificaciones(@Param("idSistCal") Long idSistCal);
    
    @Query(value = "SELECT COUNT(mcu.x_materiac) " +
    		"FROM tlmatalu mua " +
    		"INNER JOIN tlmatmatrialu mma ON mma.x_matricula = mua.x_matricula " +
    		"INNER JOIN tlmatofematrg mog ON mog.x_materiaomg = mma.x_materiaomg " +
    		"INNER JOIN tlmateriascurso mcu ON mcu.x_materiac = mog.x_materiac " +
    		"WHERE mua.x_matricula = :idMatricula AND nvl(mua.c_resultado, 99) > 1", nativeQuery = true)
    Integer countMateriasAlumno(@Param("idMatricula") Long idMatricula);
}
