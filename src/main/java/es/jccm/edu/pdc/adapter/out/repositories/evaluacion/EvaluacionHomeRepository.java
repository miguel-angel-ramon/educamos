package es.jccm.edu.pdc.adapter.out.repositories.evaluacion;

import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionHome;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.QEvaluacionHome;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.AmbitoAsociadoProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.CentrosParaInspectoresProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionCompletoProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionHomeProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionVisionGlobalProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.ObjetivoEspecificoEvaProjection;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.ObjetivoEspecificoEvaProjection;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;


@Repository
public interface EvaluacionHomeRepository extends AbstractRepository<EvaluacionHome, Long, QEvaluacionHome> {
	
	@Query(value = "SELECT PORC_EJEC porcEjec, X_SEGUI_LIN_ACT xSeguiLinAct, F_ACTUALIZA factualizacion "
			+ "FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT"
			, nativeQuery = true)
	List<EvaluacionHomeProjection> getEvaluacionHomeAll();
	
	@Query(value = "SELECT PORC_EJEC porcEjec "
			+ "FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT "
			+ "WHERE F_ACTUALIZA IN (SELECT max(F_ACTUALIZA) FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT GROUP BY X_PDCCENOBJLINACT) "
			+ "GROUP BY X_PDCCENOBJLINACT,PORC_EJEC", nativeQuery = true)
	List<EvaluacionHomeProjection> getPorcentajes();
	
	@Query(value = "SELECT DISTINCT t.PORC_EJEC, t.F_ACTUALIZA, "
			+ "CASE "
			+ "WHEN t.PORC_EJEC > 0 AND t.PORC_EJEC < 100 THEN 'EN PROGRESO' "
			+ "WHEN t.PORC_EJEC = 100 THEN 'COMPLETADA' "
			+ "ELSE 'SIN EMPEZAR' "
			+ "END AS estado "
			+ "FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT t "
			+ "WHERE t.F_ACTUALIZA IN (SELECT max(F_ACTUALIZA) FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT GROUP BY X_PDCCENOBJLINACT) "
			+ "GROUP BY t.PORC_EJEC, t.X_PDCCENOBJLINACT, t.F_ACTUALIZA ", nativeQuery = true)
	List<EvaluacionHomeProjection> getEstadoPorcentajes();
	
	@Query(value = "SELECT AVG(PORC_EJEC) "
			+ "FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT"
			, nativeQuery = true)
	Double getMediaPorcentajes();
	
	@Query(value = "SELECT F_ACTUALIZA factualizacion, X_SEGUI_LIN_ACT XSeguiLinAct "
			+ "FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT "
			+ "ORDER BY F_ACTUALIZA DESC "
		    + "FETCH FIRST 3 ROWS ONLY", nativeQuery = true)
	List<EvaluacionHomeProjection> getFechasActualizacion();

	@Query(value = "SELECT X_COMPETENCIA idCompetencia ,D_PDCCOMPETENCIA desCompetencia "
			+ "FROM DELPHOS_SEGEDU.TLPDCCOM "
			+ "WHERE X_COMPETENCIA BETWEEN 35 AND 46", nativeQuery = true)
	List<AmbitoAsociadoProjection> getAmbitoAsociado();
	
	@Query(value = "SELECT X_PDCENOBJCEN idObjEsp, D_OBJESPECIFICO descripcion,X_OBJETIVO idObjetivo, X_CENTRO idCentro, C_ANNO anno "
			+ "FROM DELPHOS_SEGEDU.TLPDCENOBJCEN", nativeQuery = true)
	List<ObjetivoEspecificoEvaProjection> getObjetivoEspecificoEva();
	
	@Query(value = "SELECT DISTINCT OBJESP.X_OBJETIVO  AS IDOBJETIVO, "
			+ "OBJESP.D_OBJESPECIFICO objetivo, "
			+ "OBJESP.X_CENTRO IDCENTRO, "
			+ "OBJESP.C_ANNO anno, "
			+ "COM.D_PDCCOMPETENCIA competencia, "
			+ "COM.X_COMPETENCIA idCompetencia, "
			+ "ACT.X_PDCCENOBJLINACT idObjLinAct, "
			+ "ACT.T_LINEAACT tituloLinAct, "
			+ "ACT.T_RESPONSABLE responsable,  "
			+ "ACT.F_INICIO fechaInicio,  "
			+ "ACT.F_FIN fechaFin, "
			+ "SEG.X_SEGUI_LIN_ACT idSeguimiento,  "
			+ "SEG.F_ACTUALIZA AS fActualiza ,  "
			+ "SEG.PORC_EJEC porcentaje,  "
			+ "SEG.F_INI_SEGUI AS fechaInicioEjecucion,  "
			+ "SEG.F_FIN_SEGUI AS fechaFinEjecucion,  "
			+ "SEG.D_TAREAS tareas,  "
			+ "SEG.D_VALORACION valoracion,  "
			+ "SEG.D_DIFICULTADES_ACCIONES dificultades_acciones,  "
			+ "SEG.D_COMENTARIOS comentarios  "
			+ "FROM DELPHOS_SEGEDU.TLPDCCENOBJLINACT ACT "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCENOBJCEN OBJESP ON OBJESP.X_PDCENOBJCEN = ACT.X_PDCENOBJCEN  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCOBJGRPCOM OBJ ON OBJ.X_OBJETIVO = OBJESP.X_OBJETIVO  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCCOM COM ON COM.X_COMPETENCIA = OBJ.X_COMPETENCIA "
			+ "INNER JOIN (SELECT S.X_PDCCENOBJLINACT, S.PORC_EJEC, S.F_INI_SEGUI, S.F_FIN_SEGUI, S.D_TAREAS , "
			+ "			S.D_VALORACION ,S.D_DIFICULTADES_ACCIONES ,S.D_COMENTARIOS,S.F_ACTUALIZA,S.X_SEGUI_LIN_ACT  "
			+ "			FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT S  "
			+ "			INNER JOIN (  "
			+ "			    SELECT X_PDCCENOBJLINACT, MAX(F_CREACION) AS FECHA_C  "
			+ "			    FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT  "
			+ "			    GROUP BY X_PDCCENOBJLINACT  "
			+ "			) SEG2 ON S.X_PDCCENOBJLINACT = SEG2.X_PDCCENOBJLINACT AND S.F_CREACION = SEG2.FECHA_C "
			+ ")SEG ON SEG.X_PDCCENOBJLINACT = ACT.X_PDCCENOBJLINACT "
			+ "WHERE ACT.L_ACTIVO = 'S' AND ACT.EST_LINEAACT = 'D' AND OBJESP.X_CENTRO = :x_centro AND OBJESP.L_ACTIVO = 'S' "
			+ "AND OBJESP.C_ANNO = :anno "
			+ "ORDER BY OBJESP.X_OBJETIVO ASC", nativeQuery = true)
	List<EvaluacionCompletoProjection> getEvaluacionCompleto(@Param("x_centro") Long codCentro,
			@Param("anno") Integer anno);
	
	@Query(value = "SELECT  " +
			"    t.X_OBJETIVO idObjetivo, t.D_OBJESPECIFICO objetivo, t.X_CENTRO idCentro, t.C_ANNO anno,  " +
			"    t4.D_PDCCOMPETENCIA competencia, t4.X_COMPETENCIA xCompetencia, " +
			"    t5.X_PDCCENOBJLINACT idObjLinAct, t5.T_LINEAACT tituloLinAct, t5.T_RESPONSABLE responsable, t5.F_INICIO fechaInicio, t5.F_FIN fechaFin,  " +
			"    t6.X_SEGUI_LIN_ACT idSeguimiento, t6.PORC_EJEC porcentaje, t6.F_INI_SEGUI AS fechaInicioEjecucion, t6.F_FIN_SEGUI AS fechaFinEjecucion, t6.D_TAREAS tareas, t6.D_VALORACION valoracion, t6.D_DIFICULTADES_ACCIONES dificultades_acciones,t6.D_COMENTARIOS comentarios," +
			"    MAX(t6.F_ACTUALIZA) fActualiza  " +
			"FROM DELPHOS_SEGEDU.TLPDCENOBJCEN t  " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCOBJ t2 ON t2.X_OBJETIVO = t.X_OBJETIVO  " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCOBJGRPCOM t3 ON t3.X_OBJETIVO = t2.X_OBJETIVO  " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCCOM t4 ON t4.X_COMPETENCIA = t3.X_COMPETENCIA  " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCCENOBJLINACT t5 ON t5.X_PDCENOBJCEN = t.X_PDCENOBJCEN  " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCSEGUILINACT t6 ON t6.X_PDCCENOBJLINACT = t5.X_PDCCENOBJLINACT  " +
			"INNER JOIN DELPHOS_SEGEDU.TLCENTROS t7 ON t7.X_CENTRO = t.X_CENTRO " +
			"WHERE t4.X_COMPETENCIA IN (SELECT X_COMPETENCIA FROM DELPHOS_SEGEDU.TLPDCCOM WHERE X_COMPETENCIA BETWEEN 35 AND 46)  " +
			"AND t6.X_SEGUI_LIN_ACT IN (SELECT X_SEGUI_LIN_ACT FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT)  " +
			"AND t7.C_CODIGO = :codCentro AND t.C_ANNO = :anno  " +
			"GROUP BY  " +
			"    t.X_OBJETIVO, t.D_OBJESPECIFICO, t.X_CENTRO, t.C_ANNO,  " +
			"    t4.D_PDCCOMPETENCIA, t4.X_COMPETENCIA, " +
			"    t5.X_PDCCENOBJLINACT, t5.T_LINEAACT, t5.T_RESPONSABLE,t5.F_INICIO,t5.F_FIN, " +
			"t6.X_SEGUI_LIN_ACT, t6.PORC_EJEC,t6.F_INI_SEGUI, t6.F_FIN_SEGUI, t6.D_TAREAS, t6.D_VALORACION, t6.D_DIFICULTADES_ACCIONES, t6.D_COMENTARIOS, " +
			"t4.X_COMPETENCIA " +
			"ORDER BY t4.X_COMPETENCIA ASC, t5.F_INICIO DESC", nativeQuery = true)
	List<EvaluacionVisionGlobalProjection> getEvaluacionVisionGlobal(
			@Param("codCentro") Long codCentro,
			@Param("anno") Integer anno);
	
	@Query(value = "SELECT X_PDCCENOBJLINACT FROM DELPHOS_SEGEDU.TLPDCCENOBJLINACT "
			+ "WHERE X_PDCENOBJCEN = :idObjEsp AND C_USUCREACION = :idUsuario AND EST_LINEAACT LIKE :estado AND ROWNUM = 1 "
			+ "ORDER BY X_PDCCENOBJLINACT DESC", nativeQuery = true)
	Long getIdLinAct(
			@Param("idObjEsp") Long idObjEsp,
			@Param("idUsuario") Long idUsuario,
			@Param("estado") String estado);
	
	@Modifying
	@Query(value = "DELETE FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT WHERE X_PDCCENOBJLINACT = :idLinAct ", nativeQuery = true)
	void deleteLineasDeSeguimiento(@Param("idLinAct") Long idLinAct);
	
	@Modifying
	@Query(value = "INSERT INTO DELPHOS_SEGEDU.TLPDCSEGUILINACT(X_SEGUI_LIN_ACT, "
			+ "PORC_EJEC, D_TAREAS, D_VALORACION, D_DIFICULTADES_ACCIONES, D_COMENTARIOS, X_PDCCENOBJLINACT, C_USUCREACION, F_CREACION, C_USUACTUALIZA, F_ACTUALIZA) "
			+ "VALUES(DELPHOS_SEGEDU.TLS_LINSEGUI.NEXTVAL, :porcentaje, :tareas, :valoracion, :dificultades_acciones, :comentarios, :idLinAct, :idUsuario, TO_DATE(:fechaActual, 'yyyy-MM-dd'), :idUsuario, "
			+ "TO_DATE(:fechaActual, 'yyyy-MM-dd'))", nativeQuery = true)
	void setLineasDeSeguimiento(			
			@Param("porcentaje") Integer porcentaje, 
			@Param("tareas") String tareas,
			@Param("valoracion") String valoracion, 
			@Param("dificultades_acciones") String dificultades_acciones, 
			@Param("comentarios") String comentarios, 
			@Param("idLinAct") Long idLinAct,
			@Param("idUsuario") Long idUsuario, 
			@Param("fechaActual") String fechaActual);
		
	@Modifying
	@Query(value = "UPDATE DELPHOS_SEGEDU.TLPDCSEGUILINACT SET "
			+ "F_INI_SEGUI = TO_DATE(:fechaInicioEjecucion, 'yyyy-MM-dd'), "
			+ "F_FIN_SEGUI = TO_DATE(:fechaFinEjecucion, 'yyyy-MM-dd'), "
			+ "PORC_EJEC = :porcentaje, "
			+ "D_TAREAS = :tareas, "
			+ "D_VALORACION = :valoracion, "
			+ "D_DIFICULTADES_ACCIONES = :dificultades_acciones, "
			+ "D_COMENTARIOS = :comentarios, "
			+ "C_USUACTUALIZA = :idUsuario, "
			+ "F_ACTUALIZA = TO_DATE(:fechaActual , 'yyyy-MM-dd') "
			+ "WHERE X_SEGUI_LIN_ACT = :idSeguiLinAct", nativeQuery = true)
	void editLineasDeSeguimiento(
			@Param("idSeguiLinAct") Long idSegui,
			@Param("fechaInicioEjecucion") String fechaInicioEjecucion,
			@Param("fechaFinEjecucion") String fechaFinEjecucion,
			@Param("porcentaje") Integer porcentaje,
			@Param("tareas") String tareas,
			@Param("valoracion") String valoracion,
			@Param("dificultades_acciones") String dificultades_acciones,
			@Param("comentarios") String comentarios,
			@Param("idUsuario") Long idUsuario,
			@Param("fechaActual") String fechaActual);


	@Query(value = "SELECT F_ACTUALIZA factualizacion FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT WHERE X_SEGUI_LIN_ACT = :idLinAct ", nativeQuery = true)
	Date getFactualizaLinActById(@Param("idLinAct") Long idLinAct);
	
	
	@Query(value = "SELECT DISTINCT INSPECENTRO.X_EMPLEADO as idEmpleado , CENTROS.X_CENTRO as idCentro"
			+ ", CENTROZONA.X_ZONA as idZona, ZONA.D_ZONA as desZona, "
			+ "DATOSCEN.D_TITULO AS tituloCentro, CENTROS.C_CODIGO AS codCentro, "
			+ "PROV.C_PROVINCIA as idProvincia, PROV.D_PROVINCIA as desProvincia, CURSO.C_ANNO as anio, "
			+ " CURSO.X_CUEPUB AS idCuestionario "
			+ "FROM DELPHOS.TLINSPECTORESCEN INSPECENTRO "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCENTROS CENTROS ON CENTROS.X_CENTRO = INSPECENTRO.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLCENTROZONA CENTROZONA ON CENTROZONA.X_CENTRO = INSPECENTRO.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLZONA ZONA ON ZONA.X_ZONA = CENTROZONA.X_ZONA "
			+ "INNER JOIN DELPHOS.TLDATOSCEN DATOSCEN ON DATOSCEN.X_CENTRO = INSPECENTRO.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLPROVINCIAS PROV ON PROV.C_PROVINCIA = DATOSCEN.C_PROVINCIA "
			+ "INNER JOIN (SELECT aca.C_ANNO, pub.X_CUEPUB ,usu.X_CENTRO "
			+ "	FROM DELPHOS_SEGEDU.TLCURSOACA aca    "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS    "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB    "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL    "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL    "
			+ "	WHERE sec.X_SECTOR = '2' "
			+ "	  AND usu.L_PRESENTADO = 'S'    "
			+ "	  AND usu.L_ACTIVO = 'N'  "
			+ "	ORDER BY aca.C_ANNO desc) CURSO ON CURSO.X_CENTRO = CENTROS.X_CENTRO  "
			+ "WHERE INSPECENTRO.X_EMPLEADO = :x_empleado "
			+ "ORDER BY CENTROS.X_CENTRO ASC", nativeQuery = true)
	List<CentrosParaInspectoresProjection> getCentrosParaInspectores(@Param("x_empleado") Long x_empleado);
	
	@Query(value = "SELECT DISTINCT CENTROS.X_CENTRO as idCentro "
			+ ", CENTROZONA.X_ZONA as idZona, ZONA.D_ZONA as desZona,  "
			+ "DATOSCEN.D_TITULO AS tituloCentro, CENTROS.C_CODIGO AS codCentro,  "
			+ " PROV.C_PROVINCIA as idProvincia, PROV.D_PROVINCIA as desProvincia, CURSO.C_ANNO as anio,  "
			+ "  CURSO.X_CUEPUB AS idCuestionario  "
			+ " FROM DELPHOS_SEGEDU.TLCENTROS CENTROS  "
			+ " INNER JOIN DELPHOS.TLCENTROZONA CENTROZONA ON CENTROZONA.X_CENTRO = CENTROS.X_CENTRO  "
			+ " INNER JOIN DELPHOS.TLZONA ZONA ON ZONA.X_ZONA = CENTROZONA.X_ZONA  "
			+ " INNER JOIN DELPHOS.TLDATOSCEN DATOSCEN ON DATOSCEN.X_CENTRO = CENTROS.X_CENTRO  "
			+ " INNER JOIN DELPHOS.TLPROVINCIAS PROV ON PROV.C_PROVINCIA = DATOSCEN.C_PROVINCIA  "
			+ " INNER JOIN (SELECT aca.C_ANNO, pub.X_CUEPUB ,usu.X_CENTRO  "
			+ " 	FROM DELPHOS_SEGEDU.TLCURSOACA aca     "
			+ " 	INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS     "
			+ " 	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB     "
			+ " 	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL     "
			+ " 	INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL     "
			+ " 	WHERE sec.X_SECTOR = '2'  "
			+ " 	  AND usu.L_PRESENTADO = 'S'     "
			+ " 	  AND usu.L_ACTIVO = 'N'   "
			+ " 	ORDER BY aca.C_ANNO desc) CURSO ON CURSO.X_CENTRO = CENTROS.X_CENTRO   "
			+ " ORDER BY CENTROS.X_CENTRO ASC", nativeQuery = true)
	List<CentrosParaInspectoresProjection> getCentrosParaConserjeria();
	
	@Query(value = " SELECT DISTINCT INSPECENTRO.X_EMPLEADO as idEmpleado , CENTROS.X_CENTRO as idCentro "
			+ ", CENTROZONA.X_ZONA as idZona, ZONA.D_ZONA as desZona,  "
			+ "DATOSCEN.D_TITULO AS tituloCentro, CENTROS.C_CODIGO AS codCentro,  "
			+ "PROV.C_PROVINCIA as idProvincia, PROV.D_PROVINCIA as desProvincia, CURSO.C_ANNO as anio,  "
			+ " CURSO.X_CUEPUB AS idCuestionario  "
			+ "FROM DELPHOS.TLINSPECTORESCEN INSPECENTRO  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCENTROS CENTROS ON CENTROS.X_CENTRO = INSPECENTRO.X_CENTRO  "
			+ "INNER JOIN DELPHOS.TLCENTROZONA CENTROZONA ON CENTROZONA.X_CENTRO = INSPECENTRO.X_CENTRO  "
			+ "INNER JOIN DELPHOS.TLZONA ZONA ON ZONA.X_ZONA = CENTROZONA.X_ZONA  "
			+ "INNER JOIN DELPHOS.TLDATOSCEN DATOSCEN ON DATOSCEN.X_CENTRO = INSPECENTRO.X_CENTRO  "
			+ "INNER JOIN DELPHOS.TLPROVINCIAS PROV ON PROV.C_PROVINCIA = DATOSCEN.C_PROVINCIA  "
			+ "INNER JOIN (SELECT aca.C_ANNO, pub.X_CUEPUB ,usu.X_CENTRO  "
			+ "	FROM DELPHOS_SEGEDU.TLCURSOACA aca     "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS     "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB     "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL     "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL     "
			+ "	WHERE sec.X_SECTOR = '2'  "
			+ "	  AND usu.L_PRESENTADO = 'S'     "
			+ "	  AND usu.L_ACTIVO = 'N'   "
			+ "	ORDER BY aca.C_ANNO desc) CURSO ON CURSO.X_CENTRO = CENTROS.X_CENTRO "
			+ "INNER JOIN (SELECT DISTINCT OBJ.X_CENTRO, OBJ.C_ANNO,  GRP.X_COMPETENCIA "
			+ "		FROM DELPHOS_SEGEDU.TLPDCCENOBJLINACT ACT    "
			+ "		INNER JOIN DELPHOS_SEGEDU.TLPDCENOBJCEN OBJ ON OBJ.X_PDCENOBJCEN  = ACT.X_PDCENOBJCEN    "
			+ "		INNER JOIN DELPHOS_SEGEDU.TLPDCOBJGRPCOM GRP ON GRP.X_OBJETIVO =OBJ.X_OBJETIVO    "
			+ "		INNER JOIN DELPHOS_SEGEDU.TLPDCCOM COM ON COM.X_COMPETENCIA = GRP.X_COMPETENCIA    "
			+ "		WHERE OBJ.L_ACTIVO='S' AND ACT.L_ACTIVO= 'S' AND ACT.EST_LINEAACT = 'D') TL ON TL.X_CENTRO = CENTROS.X_CENTRO  "
			+ "	AND TL.C_ANNO = CURSO.C_ANNO "
			+ "WHERE INSPECENTRO.X_EMPLEADO = :x_empleado  "
			+ "ORDER BY CENTROS.X_CENTRO ASC", nativeQuery = true)
	List<CentrosParaInspectoresProjection> getCentrosParaInspectoresInformeEvaFinal(@Param("x_empleado") Long x_empleado);
	
	@Query(value = " SELECT DISTINCT CENTROS.X_CENTRO as idCentro  "
			+ "			 , CENTROZONA.X_ZONA as idZona, ZONA.D_ZONA as desZona,   "
			+ "			 DATOSCEN.D_TITULO AS tituloCentro, CENTROS.C_CODIGO AS codCentro,   "
			+ "			 PROV.C_PROVINCIA as idProvincia, PROV.D_PROVINCIA as desProvincia, CURSO.C_ANNO as anio,   "
			+ "			  CURSO.X_CUEPUB AS idCuestionario   "
			+ "			 FROM  DELPHOS_SEGEDU.TLCENTROS CENTROS "
			+ "			 INNER JOIN DELPHOS.TLCENTROZONA CENTROZONA ON CENTROZONA.X_CENTRO = CENTROS.X_CENTRO   "
			+ "			 INNER JOIN DELPHOS.TLZONA ZONA ON ZONA.X_ZONA = CENTROZONA.X_ZONA   "
			+ "			 INNER JOIN DELPHOS.TLDATOSCEN DATOSCEN ON DATOSCEN.X_CENTRO = CENTROS.X_CENTRO   "
			+ "			 INNER JOIN DELPHOS.TLPROVINCIAS PROV ON PROV.C_PROVINCIA = DATOSCEN.C_PROVINCIA   "
			+ "			 INNER JOIN (SELECT aca.C_ANNO, pub.X_CUEPUB ,usu.X_CENTRO   "
			+ "			 	FROM DELPHOS_SEGEDU.TLCURSOACA aca      "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS      "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB      "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL      "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL      "
			+ "			 	WHERE sec.X_SECTOR = '2'   "
			+ "			 	  AND usu.L_PRESENTADO = 'S'      "
			+ "			 	  AND usu.L_ACTIVO = 'N'    "
			+ "			 	ORDER BY aca.C_ANNO desc) CURSO ON CURSO.X_CENTRO = CENTROS.X_CENTRO  "
			+ "			 INNER JOIN (SELECT DISTINCT OBJ.X_CENTRO, OBJ.C_ANNO,  GRP.X_COMPETENCIA  "
			+ "			 		FROM DELPHOS_SEGEDU.TLPDCCENOBJLINACT ACT     "
			+ "			 		INNER JOIN DELPHOS_SEGEDU.TLPDCENOBJCEN OBJ ON OBJ.X_PDCENOBJCEN  = ACT.X_PDCENOBJCEN     "
			+ "			 		INNER JOIN DELPHOS_SEGEDU.TLPDCOBJGRPCOM GRP ON GRP.X_OBJETIVO =OBJ.X_OBJETIVO     "
			+ "			 		INNER JOIN DELPHOS_SEGEDU.TLPDCCOM COM ON COM.X_COMPETENCIA = GRP.X_COMPETENCIA     "
			+ "			 		WHERE OBJ.L_ACTIVO='S' AND ACT.L_ACTIVO= 'S' AND ACT.EST_LINEAACT = 'D') TL ON TL.X_CENTRO = CENTROS.X_CENTRO   "
			+ "			 	AND TL.C_ANNO = CURSO.C_ANNO  "
			+ "			 ORDER BY CENTROS.X_CENTRO ASC", nativeQuery = true)
	List<CentrosParaInspectoresProjection> getCentrosParaConserjeriaInformeEvaFinal();
	
	@Query(value = "SELECT DISTINCT INSPECENTRO.X_EMPLEADO as idEmpleado , CENTROS.X_CENTRO as idCentro"
			+ ", CENTROZONA.X_ZONA as idZona, ZONA.D_ZONA as desZona, "
			+ "DATOSCEN.D_TITULO AS tituloCentro, CENTROS.C_CODIGO AS codCentro, "
			+ "PROV.C_PROVINCIA as idProvincia, PROV.D_PROVINCIA as desProvincia, CURSO.C_ANNO as anio, "
			+ " CURSO.X_CUEPUB AS idCuestionario "
			+ "FROM DELPHOS.TLINSPECTORESCEN INSPECENTRO "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCENTROS CENTROS ON CENTROS.X_CENTRO = INSPECENTRO.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLCENTROZONA CENTROZONA ON CENTROZONA.X_CENTRO = INSPECENTRO.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLZONA ZONA ON ZONA.X_ZONA = CENTROZONA.X_ZONA "
			+ "INNER JOIN DELPHOS.TLDATOSCEN DATOSCEN ON DATOSCEN.X_CENTRO = INSPECENTRO.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLPROVINCIAS PROV ON PROV.C_PROVINCIA = DATOSCEN.C_PROVINCIA "
			+ "INNER JOIN (SELECT aca.C_ANNO, pub.X_CUEPUB ,usu.X_CENTRO "
			+ "	FROM DELPHOS_SEGEDU.TLCURSOACA aca    "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS    "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB    "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL    "
			+ "	INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL    "
			+ "	WHERE sec.X_SECTOR = '1' "
			+ "	  AND usu.L_PRESENTADO = 'S'    "
			+ "	  AND usu.L_ACTIVO = 'N'  "
			+ "	ORDER BY aca.C_ANNO desc) CURSO ON CURSO.X_CENTRO = CENTROS.X_CENTRO  "
			+ "WHERE INSPECENTRO.X_EMPLEADO = :x_empleado "
			+ "ORDER BY CENTROS.X_CENTRO ASC", nativeQuery = true)
	List<CentrosParaInspectoresProjection> getCentrosParaInspectoresInformeDocente(@Param("x_empleado") Long x_empleado);
	
	@Query(value = "SELECT DISTINCT CENTROS.X_CENTRO as idCentro  "
			+ "			  , CENTROZONA.X_ZONA as idZona, ZONA.D_ZONA as desZona,   "
			+ "			  DATOSCEN.D_TITULO AS tituloCentro, CENTROS.C_CODIGO AS codCentro,   "
			+ "			  PROV.C_PROVINCIA as idProvincia, PROV.D_PROVINCIA as desProvincia, CURSO.C_ANNO as anio,   "
			+ "			   CURSO.X_CUEPUB AS idCuestionario   "
			+ "			  FROM DELPHOS_SEGEDU.TLCENTROS CENTROS "
			+ "			  INNER JOIN DELPHOS.TLCENTROZONA CENTROZONA ON CENTROZONA.X_CENTRO = CENTROS.X_CENTRO   "
			+ "			  INNER JOIN DELPHOS.TLZONA ZONA ON ZONA.X_ZONA = CENTROZONA.X_ZONA   "
			+ "			  INNER JOIN DELPHOS.TLDATOSCEN DATOSCEN ON DATOSCEN.X_CENTRO = CENTROS.X_CENTRO   "
			+ "			  INNER JOIN DELPHOS.TLPROVINCIAS PROV ON PROV.C_PROVINCIA = DATOSCEN.C_PROVINCIA   "
			+ "			  INNER JOIN (SELECT aca.C_ANNO, pub.X_CUEPUB ,usu.X_CENTRO   "
			+ "			  	FROM DELPHOS_SEGEDU.TLCURSOACA aca      "
			+ "			  	INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS      "
			+ "			  	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB      "
			+ "			  	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL      "
			+ "			  	INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL      "
			+ "			  	WHERE sec.X_SECTOR = '1'   "
			+ "			  	  AND usu.L_PRESENTADO = 'S'      "
			+ "			  	  AND usu.L_ACTIVO = 'N'    "
			+ "			  	ORDER BY aca.C_ANNO desc) CURSO ON CURSO.X_CENTRO = CENTROS.X_CENTRO    "
			+ "			  ORDER BY CENTROS.X_CENTRO ASC", nativeQuery = true)
	List<CentrosParaInspectoresProjection> getCentrosParaConserjeriaInformeDocente();

}

