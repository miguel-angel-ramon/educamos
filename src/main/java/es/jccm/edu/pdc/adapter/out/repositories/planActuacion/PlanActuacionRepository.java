package es.jccm.edu.pdc.adapter.out.repositories.planActuacion;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Cuestionario;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.QCuestionario;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.InformeProjection;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.ObjetivoGeneralProjection;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.PuntoPartidaProjection;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.SugerenciaProjection;
import es.jccm.edu.pdc.application.domain.planActuacion.projection.AmbitosCompletosProjection;
import es.jccm.edu.pdc.application.domain.planActuacion.projection.LineaActuacionHistoricoProjection;
import es.jccm.edu.pdc.application.domain.planActuacion.projection.LineaActuacionProjection;
import es.jccm.edu.pdc.application.domain.planActuacion.projection.ObjetivoEspecificoActualizadoProjection;
import es.jccm.edu.pdc.application.domain.planActuacion.projection.ObjetivoEspecificoProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanActuacionRepository extends AbstractRepository<Cuestionario, Long, QCuestionario> { //cambiar tipos AbstractRepository

	@Query(value = "SELECT X_CENTRO " + //TODO: añadir a un repository común
			"FROM DELPHOS_SEGEDU.TLCENTROS " +
			"WHERE C_CODIGO = :codCentro", nativeQuery = true)
	Long getIdCentroByCod(
			@Param("codCentro") Long codCentro);

	@Query(value = "SELECT DISTINCT competencias.N_ORDPRE AS codCompetencia,  "
			+ "			sectores.D_SECTOR AS sector,  "
			+ "			niveles.C_NIVEL AS nivel ,  "
			+ "			competencias.D_PDCCOMPETENCIA AS descCompetencia,  "
			+ "			competencias.X_COMPETENCIA AS idCompetencia,    "
			+ "			VALCEN3.N_VALOR AS valor,  "
			+ "			VALCEN3.resp AS respuestas,  "
			+ "			VALCEN3.C_ANNO AS anno,  "
			+ "			CENTROS.c_codigo AS codCentro,  "
			+ "			OBJ.D_OBJETIVO  AS desObjetivo  "
			+ "			FROM (SELECT "
			+ "				    valcen2.X_PDCVALCEN, "
			+ "				    valcen2.C_ANNO, "
			+ "				    valcen2.X_CENTRO, "
			+ "				    valcen2.X_NIVEL, "
			+ "				    valcen2.N_VALOR, "
			+ "				    valcen2.X_COMPETENCIA, "
			+ "				    valcen2.resp, "
			+ "				    valcen2.niv, "
			+ "				    valcen2.X_SECTOR, "
			+ "				    valcen2.F_CREACION "
			+ "				FROM ( "
			+ "				    SELECT "
			+ "				        VALCEN.X_PDCVALCEN, VALCEN.C_ANNO, VALCEN.X_CENTRO, VALCEN.X_NIVEL AS niv, "
			+ "				        VALCEN.N_VALOR, VALCEN.X_COMPETENCIA, VALCEN.N_RESPUESTAS AS resp, "
			+ "				        VALCEN.X_NIVEL, VALCEN.X_SECTOR, VALCEN.F_CREACION, "
			+ "				        ROW_NUMBER() OVER (PARTITION BY VALCEN.X_COMPETENCIA ORDER BY VALCEN.F_CREACION DESC) AS numeroFila "
			+ "				    FROM "
			+ "				        DELPHOS_SEGEDU.tlpdcvalcen valcen "
			+ "				        INNER JOIN DELPHOS_SEGEDU.TLPDCSECTORES sectores ON valcen.X_SECTOR = sectores.X_SECTOR "
			+ "				        INNER JOIN DELPHOS_SEGEDU.TLPDCNIVVAL niveles ON valcen.X_NIVEL = niveles.X_NIVEL "
			+ "				        INNER JOIN DELPHOS_SEGEDU.TLCENTROS centros ON valcen.X_CENTRO = centros.X_CENTRO "
			+ "				    WHERE "
			+ "				        sectores.X_SECTOR = :idSector  AND valcen.C_ANNO = :idAnno  AND centros.c_codigo = :codCentro "
			+ "				) valcen2 "
			+ "				WHERE "
			+ "				    valcen2.numeroFila = 1) valcen3    "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLPDCSECTORES sectores ON valcen3.X_SECTOR = sectores.X_SECTOR     "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLPDCNIVVAL niveles ON valcen3.X_NIVEL = niveles.X_NIVEL     "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLPDCCOM competencias ON valcen3.X_COMPETENCIA = COMPETENCIAS.X_COMPETENCIA    "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLCENTROS centros ON valcen3.X_CENTRO = centros.X_CENTRO   "
			+ "			LEFT JOIN DELPHOS_SEGEDU.TLPDCOBJGRPCOM GRP ON GRP.X_COMPETENCIA = COMPETENCIAS.X_COMPETENCIA  "
			+ "			LEFT JOIN DELPHOS_SEGEDU.TLPDCOBJ OBJ ON OBJ.X_OBJETIVO = GRP.X_OBJETIVO   "
			+ "			ORDER BY codCompetencia ASC ", nativeQuery = true)
	List<InformeProjection> getAmbitosPlanActuacion(
			@Param("idSector") Long idSector, @Param("idAnno") Long idAnno, @Param("codCentro") Long codCentro);
	
	
	@Query(value = "SELECT DISTINCT VALCEN.X_CENTRO, GRP.X_COMPETENCIA, SEC.D_SECTOR,        "
			+ "			  NIVELES.C_NIVEL, COM.C_PDCCOMPETENCIA, COM.D_PDCCOMPETENCIA,        "
			+ "			  VALCEN.N_VALOR, VALCEN.N_RESPUESTAS, VALCEN.C_ANNO,        "
			+ "			  GRP.X_OBJGRPCOM, GRP.X_OBJETIVO, COM.X_SUBDIMENSION,        "
			+ "			  OBJ.X_TIPOBJ, OBJ.L_ACTIVO, OBJ.D_OBJETIVO ,    "
			+ "			  OBJESP.X_PDCENOBJCEN, OBJESP.D_OBJESPECIFICO,        "
			+ "			  OBJESP.X_PDCCENOBJLINACT, OBJESP.T_LINEAACT,        "
			+ "			  OBJESP.D_LINEAACT, OBJESP.F_INICIO,        "
			+ "			  OBJESP.F_CREACION,  OBJESP.F_FIN,        "
			+ "			  OBJESP.T_RESPONSABLE, OBJESP.D_INDLOGRO,          "
			+ "			  OBJESP.D_INSTRUMENTOS, OBJESP.PORC_EJEC,        "
			+ "			  OBJESP.F_INI_SEGUI, OBJESP.F_FIN_SEGUI,         "
			+ "			  OBJESP.D_TAREAS, OBJESP.D_VALORACION,        "
			+ "			  OBJESP.D_DIFICULTADES_ACCIONES, OBJESP.D_COMENTARIOS,    "
			+ "			  SUG.X_PDCSUGNIV, SUG.X_SUGERENCIA, SUG.X_NIVEL,       "
			+ "			  COMMEJ.X_CUEPRE, COMMEJ.D_COMOMEJORAR, SUG.D_SUGERENCIA,        "
			+ "			  PUNPAR.X_CUEOPC, PUNPAR.D_CUEPRE, PUNPAR.D_CUEOPC       "
			+ "			  FROM DELPHOS_SEGEDU.TLPDCOBJ OBJ         "
			+ "			  INNER JOIN DELPHOS_SEGEDU.TLPDCOBJGRPCOM GRP ON GRP.X_OBJETIVO = OBJ.X_OBJETIVO        "
			+ "			  INNER JOIN DELPHOS_SEGEDU.TLPDCSECTORES SEC ON SEC.X_SECTOR = :idSector        "
			+ "			  INNER JOIN DELPHOS_SEGEDU.TLPDCCOM COM ON COM.X_COMPETENCIA = GRP.X_COMPETENCIA         "
			+ "			 LEFT JOIN (  "
			+ "			  	SELECT DISTINCT V.X_CENTRO, V.N_VALOR, V.resp AS N_RESPUESTAS, V.C_ANNO,V.X_COMPETENCIA, V.niv AS X_NIVEL "
			+ "				FROM ( "
			+ "				    SELECT "
			+ "				        VALCEN1.X_PDCVALCEN, VALCEN1.C_ANNO, VALCEN1.X_CENTRO, VALCEN1.X_NIVEL AS niv, "
			+ "				        VALCEN1.N_VALOR, VALCEN1.X_COMPETENCIA, VALCEN1.N_RESPUESTAS AS resp, "
			+ "				        VALCEN1.X_NIVEL, VALCEN1.X_SECTOR, VALCEN1.F_CREACION, "
			+ "		        		ROW_NUMBER() OVER (PARTITION BY VALCEN1.X_COMPETENCIA, VALCEN1.X_CENTRO, VALCEN1.X_SECTOR, VALCEN1.C_ANNO ORDER BY VALCEN1.F_CREACION DESC) AS numeroFila "
			+ "				    FROM "
			+ "				        DELPHOS_SEGEDU.tlpdcvalcen valcen1 "
			+ "				    WHERE "
			+ "				        VALCEN1.C_ANNO = :idAnno AND VALCEN1.X_CENTRO  = :xCentro  "
			+ "				) v "
			+ "				WHERE "
			+ "				    v.numeroFila = 1) VALCEN ON VALCEN.X_COMPETENCIA = COM.X_COMPETENCIA           "
			+ "			  LEFT JOIN DELPHOS_SEGEDU.TLPDCNIVVAL NIVELES ON VALCEN.X_NIVEL = NIVELES.X_NIVEL         "
			+ "			  INNER JOIN (SELECT SUGNIV.X_PDCSUGNIV, SUGNIV.X_SUGERENCIA, SUGNIV.X_NIVEL, SUG.D_SUGERENCIA, SUGNIV.X_COMPETENCIA       "
			+ "			  		FROM DELPHOS_SEGEDU.TLPDCSUGNIV SUGNIV      "
			+ "			 		INNER JOIN DELPHOS_SEGEDU.TLPDCSUG SUG ON SUG.X_SUGERENCIA = SUGNIV.X_SUGERENCIA ) SUG ON SUG.X_COMPETENCIA = COM.X_COMPETENCIA         "
			+ "			  LEFT JOIN (        "
			+ "			  	SELECT DISTINCT com.X_COMPETENCIA, PREOPC.X_CUEOPC,         "
			+ "			 	com.D_PDCCOMPETENCIA,         "
			+ "			 	preg.D_CUEPRE,      "
			+ "			 	preg.X_CUEPRE,      "
			+ "			 	RESPOPC.D_CUEOPC         "
			+ "			 	FROM DELPHOS_SEGEDU.TLPDCCOM com         "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLPDCCOMPRE compre ON compre.X_COMPETENCIA  = com.X_COMPETENCIA         "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUESECCPRE seccion ON seccion.X_CUESECCPRE = compre.X_CUESECCPRE         "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUEPRE preg ON preg.X_CUEPRE = seccion.X_CUEPRE         "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUEPREOPC PREOPC ON PREOPC.X_CUEPRE = preg.X_CUEPRE         "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUEOPC RESPOPC ON RESPOPC.X_CUEOPC = PREOPC.X_CUEOPC         "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSURES RESPUESTA ON RESPUESTA.X_CUEPREOPC = PREOPC.X_CUEPREOPC         "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU cuestionario ON cuestionario.X_CUEPUBUSU  = RESPUESTA.X_CUEPUBUSU    "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON pub.X_CUEPUB = CUESTIONARIO.X_CUEPUB     "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLCURSOACA aca ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS    "
			+ "			 	WHERE  cuestionario.L_PRESENTADO = 'S' AND cuestionario.X_CENTRO  = :xCentro AND aca.C_ANNO = :idAnno    "
			+ "			 	AND CUESTIONARIO.X_CUEPUB = :x_cuepub) PUNPAR ON PUNPAR.X_COMPETENCIA = COM.X_COMPETENCIA         "
			+ "			 LEFT JOIN (      "
			+ "			 	SELECT DISTINCT SUGNIV.X_CUEPRE, SUG.D_COMOMEJORAR  FROM DELPHOS_SEGEDU.TLPDCSUGNIV SUGNIV      "
			+ "			 	INNER JOIN DELPHOS_SEGEDU.TLPDCSUG SUG ON SUG.X_SUGERENCIA = SUGNIV.X_SUGERENCIA) COMMEJ      "
			+ "			 	ON COMMEJ.X_CUEPRE = PUNPAR.X_CUEPRE      "
			+ "			 LEFT JOIN (SELECT DISTINCT OBJCEN.X_OBJETIVO,OBJCEN.X_PDCENOBJCEN,          "
			+ "			 			OBJCEN.D_OBJESPECIFICO, ACT.X_PDCCENOBJLINACT,           "
			+ "			 			ACT.T_LINEAACT,	ACT.D_LINEAACT, ACT.F_INICIO,         "
			+ "			 			ACT.F_CREACION, ACT.F_FIN ,          "
			+ "			 			ACT.T_RESPONSABLE , ACT.D_INDLOGRO,          "
			+ "			 			ACT.D_INSTRUMENTOS, SEG.PORC_EJEC ,         "
			+ "			 			SEG.F_INI_SEGUI , SEG.F_FIN_SEGUI,         "
			+ "			 			SEG.D_TAREAS,  SEG.D_VALORACION,         "
			+ "			 			SEG.D_DIFICULTADES_ACCIONES, SEG.D_COMENTARIOS        "
			+ "			 			FROM DELPHOS_SEGEDU.TLPDCENOBJCEN OBJCEN           "
			+ "			 			LEFT JOIN DELPHOS_SEGEDU.TLPDCCENOBJLINACT ACT ON ACT.X_PDCENOBJCEN = OBJCEN.X_PDCENOBJCEN AND ACT.L_ACTIVO = 'S'  AND ACT.EST_LINEAACT = 'D'      "
			+ "			 			LEFT JOIN (          "
			+ "			 				SELECT S.X_PDCCENOBJLINACT, S.PORC_EJEC, S.F_INI_SEGUI, S.F_FIN_SEGUI, S.D_TAREAS ,         "
			+ "			 									S.D_VALORACION ,S.D_DIFICULTADES_ACCIONES ,S.D_COMENTARIOS          "
			+ "			 				FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT S          "
			+ "			 				INNER JOIN (          "
			+ "			 				    SELECT X_PDCCENOBJLINACT, MAX(F_CREACION) AS FECHA_C          "
			+ "			 				    FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT          "
			+ "			 				    GROUP BY X_PDCCENOBJLINACT          "
			+ "			 				) SEG2 ON S.X_PDCCENOBJLINACT = SEG2.X_PDCCENOBJLINACT AND S.F_CREACION = SEG2.FECHA_C           "
			+ "			 			) SEG ON ACT.X_PDCCENOBJLINACT  = SEG.X_PDCCENOBJLINACT         "
			+ "			 			WHERE OBJCEN.X_CENTRO  = :xCentro        "
			+ "			 			AND OBJCEN.C_ANNO = :idAnno        "
			+ "			 			AND OBJCEN.L_ACTIVO = 'S') OBJESP ON OBJESP.X_OBJETIVO = OBJ.X_OBJETIVO         "
			+ "			  WHERE (VALCEN.C_ANNO = :idAnno OR VALCEN.C_ANNO IS NULL) AND (VALCEN.X_CENTRO  = :xCentro OR VALCEN.X_CENTRO IS NULL) AND OBJ.L_ACTIVO = 'S'        "
			+ "			  ORDER BY GRP.X_COMPETENCIA ASC", nativeQuery = true)
	List<AmbitosCompletosProjection> getAmbitosCompletosCentro(@Param("x_cuepub") Long x_cuepub, @Param("idAnno") Long idAnno, @Param("xCentro") Long xCentro, @Param("idSector") Long idSector);
	
	@Query(value = "SELECT COM.C_PDCCOMPETENCIA codCompetencia, COM.D_PDCCOMPETENCIA tituloCompetencia, COM.T_PDCCOMPETENCIA desCompetencia, COM.X_COMPETENCIA idCompetencia, COM.X_SUBDIMENSION idSubDimension, OBJCOM.X_OBJETIVO idObjetivo, OBJCOM.X_OBJGRPCOM idGrupoObjetivo, " +
			"obj.X_TIPOBJ tipObjetivo, OBJ.L_ACTIVO activo, OBJ.D_OBJETIVO desObjetivo " +
			"FROM DELPHOS_SEGEDU.TLPDCCOM com " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCOBJGRPCOM objcom ON objcom.X_COMPETENCIA = com.X_COMPETENCIA " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCOBJ obj ON obj.X_OBJETIVO  = objcom.X_OBJETIVO " +
			"WHERE com.X_COMPETENCIA = :idCompetencia ", nativeQuery = true)
	List<ObjetivoGeneralProjection> getObjetivoGeneral(
			@Param("idCompetencia") Long idCompetencia);

	@Query(value = "SELECT DISTINCT PREOPC.X_CUEOPC idOpcion, " +
			"com.D_PDCCOMPETENCIA desCompetencia, " +
			"preg.D_CUEPRE desPregunta, " +
			"RESPOPC.D_CUEOPC desOpcion " +
			"FROM DELPHOS_SEGEDU.TLPDCCOM com " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCCOMPRE compre ON compre.X_COMPETENCIA  = com.X_COMPETENCIA " +
			"INNER JOIN DELPHOS_SEGEDU.TLCUESECCPRE seccion ON seccion.X_CUESECCPRE = compre.X_CUESECCPRE " +
			"INNER JOIN DELPHOS_SEGEDU.TLCUEPRE preg ON preg.X_CUEPRE = seccion.X_CUEPRE " +
			"INNER JOIN DELPHOS_SEGEDU.TLCUEPREOPC PREOPC ON PREOPC.X_CUEPRE = preg.X_CUEPRE " +
			"INNER JOIN DELPHOS_SEGEDU.TLCUEOPC RESPOPC ON RESPOPC.X_CUEOPC = PREOPC.X_CUEOPC " +
			"INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSURES RESPUESTA ON RESPUESTA.X_CUEPREOPC = PREOPC.X_CUEPREOPC " +
			"INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU cuestionario ON cuestionario.X_CUEPUBUSU  = RESPUESTA.X_CUEPUBUSU " +
			"WHERE  cuestionario.L_PRESENTADO = 'S' AND com.X_COMPETENCIA = :idCompetencia " +
			"AND RESPUESTA.x_cuepubusu = :idCuePubUsu ORDER BY IDOPCION", nativeQuery = true)
	List<PuntoPartidaProjection> getPuntoPartida(
			@Param("idCompetencia") Long idCompetencia, @Param("idCuePubUsu") Long idCuepubUsu);

	@Query(value = "SELECT COM.C_PDCCOMPETENCIA codCompetencia, COM.D_PDCCOMPETENCIA tituloCompetencia, COM.T_PDCCOMPETENCIA desCompetencia, COM.X_COMPETENCIA idCompetencia, COM.X_SUBDIMENSION idSubDimension, " +
			"NIV.X_SUGERENCIA idSugerencia, NIV.X_SECTOR idSector, NIV.X_PDCSUGNIV idSugNiv, NIV.X_NIVEL idNivel, SUG.D_COMOMEJORAR comoMejorar, SUG.D_SUGERENCIA desSugerencia " +
			"FROM DELPHOS_SEGEDU.TLPDCCOM com " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCSUGNIV niv ON niv.X_COMPETENCIA = com.X_COMPETENCIA " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCSUG sug ON sug.X_SUGERENCIA = niv.X_SUGERENCIA " +
			"WHERE com.X_COMPETENCIA = :idCompetencia " +
			"ORDER BY com.X_COMPETENCIA ", nativeQuery = true)
	List<SugerenciaProjection> getSugerencia(
			@Param("idCompetencia") Long idCompetencia);


	@Query(value = "SELECT objcen.x_objetivo idObjetivo, objcen.X_PDCENOBJCEN idObjEsp,  "
			+ "			objcen.D_OBJESPECIFICO descripcion, objcen.L_ACTIVO activo  "
			+ "			FROM DELPHOS_SEGEDU.TLPDCENOBJCEN objcen "
			+ "			WHERE objcen.x_centro = :idCentro AND objcen.x_objetivo = :idObjetivo AND OBJCEN.C_ANNO = :idAnno"
			+ "			AND objcen.L_ACTIVO = 'S' ORDER BY objcen.X_PDCENOBJCEN", nativeQuery = true)
	List<ObjetivoEspecificoProjection> getObjetivosEspecificos(
			@Param("idCentro") Long idCentro,
			@Param("idObjetivo") Long idObjetivo,
			@Param("idAnno") Long idAnno);

	@Query(value = "SELECT linact.X_PDCCENOBJLINACT idLinAct, linact.T_LINEAACT titulo, linact.D_LINEAACT descripcion, " +
			"linact.F_INICIO fechaInicio, linact.F_FIN fechaFin, linact.T_RESPONSABLE responsable, " +
			"linact.D_INDLOGRO logro, linact.D_INSTRUMENTOS instrumentos, linact.L_ACTIVO activo  " +
			"FROM DELPHOS_SEGEDU.TLPDCCENOBJLINACT linact " +
			"INNER JOIN DELPHOS_SEGEDU.TLPDCENOBJCEN objcen ON linact.X_PDCENOBJCEN = objcen.X_PDCENOBJCEN " +
			"where objcen.x_centro = :idCentro AND objcen.X_PDCENOBJCEN = :idObjEsp " +
			"AND linact.L_ACTIVO = 'S' ORDER BY linact.X_PDCCENOBJLINACT", nativeQuery = true)
	List<LineaActuacionProjection> getLineasActuacion(
			@Param("idObjEsp") Long idObjEsp,
			@Param("idCentro") Long idCentro);

	@Modifying
	@Query(value = "INSERT INTO DELPHOS_SEGEDU.TLPDCENOBJCEN " +
			"(X_PDCENOBJCEN, X_CENTRO, C_ANNO, X_OBJETIVO, D_OBJESPECIFICO) " +
			"VALUES (DELPHOS_SEGEDU.TLS_OBJCXPDCENOBJCEN.nextval, :idCentro, :anno, :idObjetivo, :descripcionObjetivo)", nativeQuery = true)
	void setObjetivoEspecifico(
			@Param("idCentro") Long idCentro,
			@Param("anno") Long anno,
			@Param("idObjetivo") Long idObjetivo,
			@Param("descripcionObjetivo") String descripcionObjetivo);


	@Modifying
	@Query(value = "INSERT INTO DELPHOS_SEGEDU.TLPDCCENOBJLINACT(X_PDCCENOBJLINACT, X_PDCENOBJCEN, " +
			"T_LINEAACT, D_LINEAACT, F_INICIO, F_FIN, T_RESPONSABLE, D_INDLOGRO, D_INSTRUMENTOS, EST_LINEAACT, " +
			"C_USUCREACION,F_CREACION, C_USUACTUALIZA ,F_ACTUALIZA) " +
			"VALUES (DELPHOS_SEGEDU.TLS_LAOCXPDCCENOBJLINACT.nextval, :idObjEsp, :titulo, :descripcion, " +
			"TO_DATE(:fechaInicio, 'yyyy-MM-dd'), TO_DATE(:fechaFin, 'yyyy-MM-dd'), :responsable, " +
			":logro, :instrumentos, :estado, :idUsuario, TO_DATE(:fechaActual, 'yyyy-MM-dd'), :idUsuario, " +
			"TO_DATE(:fechaActual, 'yyyy-MM-dd'))", nativeQuery = true)
	void setLineasDeActuacion(
			@Param("idObjEsp") Long idObjEsp,
			@Param("titulo") String titulo,
			@Param("descripcion") String descripcion,
			@Param("fechaInicio") String fechaInicio,
			@Param("fechaFin") String fechaFin,
			@Param("responsable") String responsable,
			@Param("logro") String logro,
			@Param("instrumentos") String instrumentos,
			@Param("estado") String estado,
			@Param("idUsuario") Long idUsuario,
			@Param("fechaActual") String fechaActual
			);

	@Modifying
	@Query(value = "UPDATE DELPHOS_SEGEDU.TLPDCENOBJCEN set l_activo = 'N' where X_PDCENOBJCEN = :idObjEsp", nativeQuery = true)
	void deleteObjetivoEspecifico(@Param("idObjEsp") Long idObjEsp);

	@Modifying
	@Query(value = "UPDATE DELPHOS_SEGEDU.TLPDCCENOBJLINACT set l_activo = 'N' where X_PDCCENOBJLINACT = :idLinAct ", nativeQuery = true)
	void deleteLineaActuacion(@Param("idLinAct") Long idLinAct);


	@Modifying
	@Query(value = "UPDATE DELPHOS_SEGEDU.TLPDCENOBJCEN SET D_OBJESPECIFICO = :descripcion WHERE X_PDCENOBJCEN = :idObjEsp ", nativeQuery = true)
	void editObjetivosEspecificos(
			@Param("idObjEsp") Long idObj,
			@Param("descripcion") String descripcion);


	@Modifying
	@Query(value = "UPDATE DELPHOS_SEGEDU.TLPDCCENOBJLINACT " +
			"SET T_LINEAACT = :titulo,  " +
			"D_LINEAACT = :descripcion, " +
			"F_INICIO = TO_DATE(:fechaInicio, 'yyyy-MM-dd'), " +
			"F_FIN = TO_DATE(:fechaFin, 'yyyy-MM-dd'), " +
			"T_RESPONSABLE = :responsable, " +
			"D_INDLOGRO = :logro, " +
			"D_INSTRUMENTOS = :instrumentos, " +
			"EST_LINEAACT = :estado, " +
			"C_USUACTUALIZA = :idUsuario, " +
			"F_ACTUALIZA = TO_DATE(:fechaActual, 'yyyy-MM-dd') " +
			"WHERE X_PDCCENOBJLINACT = :idLinAct ", nativeQuery = true)
	void editLineasActuacion(
			@Param("idLinAct") Long idLinAct,
			@Param("titulo") String titulo,
			@Param("descripcion") String descripcion,
			@Param("fechaInicio") String fechaInicio,
			@Param("fechaFin") String fechaFin,
			@Param("responsable") String responsable,
			@Param("logro") String logro,
			@Param("instrumentos") String instrumentos,
			@Param("estado") String estado,
			@Param("idUsuario") Long idUsuario,
			@Param("fechaActual") String fechaActual
			);
	
	@Query(value = "SELECT competencias.C_PDCCOMPETENCIA AS codCompetencia, AVG(VALCEN.N_VALOR) AS valor, competencias.X_COMPETENCIA AS idCompetencia   "
			+ "FROM  "
			+ "	(SELECT DISTINCT V.X_CENTRO, V.N_VALOR, V.resp AS N_RESPUESTAS, V.C_ANNO,V.X_COMPETENCIA, V.niv AS X_NIVEL, V.X_SECTOR "
			+ "		FROM ( "
			+ "		    SELECT "
			+ "		        VALCEN1.X_PDCVALCEN, VALCEN1.C_ANNO, VALCEN1.X_CENTRO, VALCEN1.X_NIVEL AS niv, "
			+ "		        VALCEN1.N_VALOR, VALCEN1.X_COMPETENCIA, VALCEN1.N_RESPUESTAS AS resp, "
			+ "		        VALCEN1.X_NIVEL, VALCEN1.X_SECTOR, VALCEN1.F_CREACION, "
			+ "		        ROW_NUMBER() OVER (PARTITION BY VALCEN1.X_COMPETENCIA, VALCEN1.X_CENTRO, VALCEN1.X_SECTOR, VALCEN1.C_ANNO ORDER BY VALCEN1.F_CREACION DESC) AS numeroFila "
			+ "		    FROM "
			+ "		        DELPHOS_SEGEDU.tlpdcvalcen valcen1 "
			+ "		) v "
			+ "		WHERE "
			+ "		    v.numeroFila = 1) valcen   "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCSECTORES sectores ON valcen.X_SECTOR = sectores.X_SECTOR   "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCCOM competencias ON valcen.X_COMPETENCIA = COMPETENCIAS.X_COMPETENCIA   "
			+ "WHERE sectores.X_SECTOR = :idSector   "
			+ "AND valcen.C_ANNO = :idAnno   "
			+ "GROUP BY competencias.C_PDCCOMPETENCIA, competencias.X_COMPETENCIA "
			+ "ORDER BY competencias.X_COMPETENCIA", nativeQuery = true)
	List<InformeProjection> getValoresCentro(
			@Param("idSector") Long idSector, 
			@Param("idAnno") Long idAnno 
			);
	
	@Query(value = "SELECT AVG(valcue.N_VALOR) " + 
			"FROM DELPHOS_SEGEDU.tlpdcvalcue valcue " +
			"INNER JOIN  DELPHOS_SEGEDU.TLCUEPUBUSU us ON valcue.X_CUEPUBUSU = us.X_CUEPUBUSU " +
			"INNER JOIN DELPHOS_SEGEDU.TLCENTROS centro ON us.X_CENTRO = centro.X_CENTRO " +
			"WHERE centro.C_CODIGO  = :codCentro AND valcue.X_SECTOR = 1 AND us.L_PRESENTADO ='S' " +
			"AND EXTRACT (YEAR FROM us.F_ULTGRAB) = :Anno", nativeQuery = true)
	Double getValorAmbitoCinco(
			@Param("codCentro") Long codCentro, 
			@Param("Anno") Long Anno 
			);
	
	@Query(value = "SELECT AVG(AVG(valcue.N_VALOR)) "
			+ "FROM DELPHOS_SEGEDU.tlpdcvalcue valcue "
			+ "INNER JOIN  DELPHOS_SEGEDU.TLCUEPUBUSU us ON valcue.X_CUEPUBUSU = us.X_CUEPUBUSU "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCENTROS centro ON us.X_CENTRO = centro.X_CENTRO "
			+ "WHERE valcue.X_SECTOR = 1 AND us.L_PRESENTADO ='S' "
			+ "AND EXTRACT (YEAR FROM us.F_ULTGRAB) = :Anno "
			+ "GROUP BY us.X_CENTRO", nativeQuery = true)
	Double getValorAmbitoCincoGlobal( 
			@Param("Anno") Long Anno 
			);
	
	@Query(value = "SELECT count(linact.X_PDCCENOBJLINACT) "
			+ "FROM DELPHOS_SEGEDU.TLPDCCENOBJLINACT linact "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCENOBJCEN objcen ON linact.X_PDCENOBJCEN = objcen.X_PDCENOBJCEN "
			+ "where objcen.x_centro = :idCentro "
			+ "AND linact.L_ACTIVO = 'S' AND linact.EST_LINEAACT = 'D' ORDER BY linact.X_PDCCENOBJLINACT", nativeQuery = true)
	Long visualizaPlan(@Param("idCentro") Long idCentro);

	@Query(value = "SELECT X_PDCCENOBJLINACT FROM DELPHOS_SEGEDU.TLPDCCENOBJLINACT " +
			" WHERE X_PDCENOBJCEN = :idObjEsp AND EST_LINEAACT LIKE :estado AND T_LINEAACT LIKE :titulo AND D_LINEAACT LIKE :descripcion  AND T_RESPONSABLE LIKE :responsable AND D_INDLOGRO LIKE :logro AND D_INSTRUMENTOS LIKE :instrumentos AND ROWNUM = 1 " +
			" ORDER BY F_CREACION DESC", nativeQuery = true)
	Long getIdLinAct(
			@Param("idObjEsp") Long idObjEsp,
			@Param("estado") String estado,
			@Param("titulo") String titulo,
			@Param("descripcion") String descripcion,
			@Param("responsable") String responsable,
			@Param("logro") String logro,
			@Param("instrumentos") String instrumentos);

	
	@Modifying
	@Query(value = "INSERT INTO DELPHOS_SEGEDU.TLPDCSEGUILINACT(X_SEGUI_LIN_ACT, "
			+ "PORC_EJEC, X_PDCCENOBJLINACT, C_USUCREACION, F_CREACION, C_USUACTUALIZA, F_ACTUALIZA) "
			+ "VALUES(DELPHOS_SEGEDU.TLS_LINSEGUI.NEXTVAL, :porcentaje, :idPdcCenObjLinAct, :idUsuario, TO_DATE(:fechaActual, 'yyyy-MM-dd'), "
			+ ":idUsuario, TO_DATE(:fechaActual, 'yyyy-MM-dd'))" , nativeQuery = true)
	void setLineasDeSeguimiento(
			@Param("porcentaje") Double porcentaje,
			@Param("idPdcCenObjLinAct") Long idPdcCenObjLinAct,
			@Param("idUsuario") Long idUsuario,
			@Param("fechaActual") String fechaActual);
	
	@Modifying
	@Query(value = "DELETE FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT WHERE X_PDCCENOBJLINACT = :idLinAct ", nativeQuery = true)
	void deleteLineasDeSeguimiento(@Param("idLinAct") Long idLinAct);
	
	@Query(value = "	SELECT DISTINCT  COM.X_COMPETENCIA  AS idAmbito,  "
			+ "		OBJ.X_PDCENOBJCEN AS idObjetivoEsp,  "
			+ "		OBJ.D_OBJESPECIFICO AS descripcionObj,  "
			+ "		ACT.X_PDCCENOBJLINACT AS idLinAct,   "
			+ "		ACT.T_LINEAACT AS tituloActuacion,  "
			+ "		ACT.D_LINEAACT as descipcionActuacion,  "
			+ "		ACT.F_INICIO as fechaInicioActuacion , "
			+ "		ACT.F_CREACION  AS fechaCreacionActuacion, "
			+ "		ACT.F_FIN as fechaFinActuacion,  "
			+ "		ACT.T_RESPONSABLE as responsableActuacion,  "
			+ "		ACT.D_INDLOGRO AS logroActuacion,  "
			+ "		ACT.D_INSTRUMENTOS AS instrumentosActuacion, "
			+ "		SEG.PORC_EJEC AS porcActuacion, "
			+ "		SEG.F_INI_SEGUI AS fechaInicioEjecucionAct, "
			+ "		SEG.F_FIN_SEGUI AS fechaFinEjecucionAct, "
			+ "		SEG.D_TAREAS AS tareasActuacion, "
			+ "		SEG.D_VALORACION AS valoracionActuacion, "
			+ "		SEG.D_DIFICULTADES_ACCIONES AS dificultadesActuacion, "
			+ "		SEG.D_COMENTARIOS AS comentariosActuacion "
			+ "		FROM DELPHOS_SEGEDU.TLPDCCENOBJLINACT ACT   "
			+ "		INNER JOIN DELPHOS_SEGEDU.TLPDCENOBJCEN OBJ ON OBJ.X_PDCENOBJCEN  = ACT.X_PDCENOBJCEN   "
			+ "		INNER JOIN (  "
			+ "			SELECT S.X_PDCCENOBJLINACT, S.PORC_EJEC, S.F_INI_SEGUI, S.F_FIN_SEGUI, S.D_TAREAS , "
			+ "								S.D_VALORACION ,S.D_DIFICULTADES_ACCIONES ,S.D_COMENTARIOS  "
			+ "			FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT S  "
			+ "			INNER JOIN (  "
			+ "			    SELECT X_PDCCENOBJLINACT, MAX(F_CREACION) AS FECHA_C  "
			+ "			    FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT  "
			+ "			    GROUP BY X_PDCCENOBJLINACT  "
			+ "			) SEG2 ON S.X_PDCCENOBJLINACT = SEG2.X_PDCCENOBJLINACT AND S.F_CREACION = SEG2.FECHA_C   "
			+ "		) SEG ON ACT.X_PDCCENOBJLINACT  = SEG.X_PDCCENOBJLINACT  "
			+ "		INNER JOIN DELPHOS_SEGEDU.TLPDCOBJGRPCOM GRP ON GRP.X_OBJETIVO =OBJ.X_OBJETIVO   "
			+ "		INNER JOIN DELPHOS_SEGEDU.TLPDCCOM COM ON COM.X_COMPETENCIA = GRP.X_COMPETENCIA   "
			+ "		WHERE GRP.X_COMPETENCIA = :x_competencia AND OBJ.L_ACTIVO='S' AND ACT.L_ACTIVO= 'S' AND ACT.EST_LINEAACT = 'D' AND OBJ.C_ANNO = :anioEscolar "
			+ "		AND OBJ.X_CENTRO = :x_centro  "
			+ "		ORDER BY OBJ.X_PDCENOBJCEN", nativeQuery = true)
	List<ObjetivoEspecificoActualizadoProjection> getObjetivosEspecificos(@Param("anioEscolar") String anioEscolar,@Param("x_competencia") String x_competencia, @Param("x_centro") String x_centro);
	
	
	
	@Query(value = "SELECT SEG.F_CREACION AS fechaCreacion,   "
			+ "			 ACT.X_PDCCENOBJLINACT AS idLinAct,   "
			+ "			ACT.T_LINEAACT AS titulo,   "
			+ "			ACT.EST_LINEAACT  AS estado, "
			+ "			ACT.D_LINEAACT as descripcion,   "
			+ "			ACT.F_INICIO as fechaInicio ,   "
			+ "			ACT.F_FIN as fechaFin,   "
			+ "			ACT.T_RESPONSABLE as responsable,   "
			+ "			ACT.D_INDLOGRO AS logro,   "
			+ "			ACT.D_INSTRUMENTOS AS instrumentos,   "
			+ "			SEG.PORC_EJEC AS porcentaje,  "
			+ "			SEG.F_INI_SEGUI AS fechaInicioEjecucionAct,  "
			+ "			SEG.F_FIN_SEGUI AS fechaFinEjecucionAct,  "
			+ "			SEG.D_TAREAS AS tareasActuacion,  "
			+ "			SEG.D_VALORACION AS valoracionActuacion,  "
			+ "			SEG.D_DIFICULTADES_ACCIONES AS dificultadesActuacion,  "
			+ "			SEG.D_COMENTARIOS AS comentariosActuacion, "
			+ "			OBJ.D_OBJESPECIFICO AS descripcionObjetivoEspecifico "
			+ "			FROM DELPHOS_SEGEDU.TLPDCSEGUILINACT SEG   "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLPDCCENOBJLINACT ACT ON ACT.X_PDCCENOBJLINACT = SEG.X_PDCCENOBJLINACT "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLPDCENOBJCEN OBJ ON OBJ.X_PDCENOBJCEN = ACT.X_PDCENOBJCEN  "
			+ "			WHERE SEG.X_PDCCENOBJLINACT = :idActuacion  ", nativeQuery = true)
	List<LineaActuacionHistoricoProjection> getHistoricoLineasActuacion(@Param("idActuacion") Integer idActuacion);
	
	@Query(value = "SELECT DISTINCT  GRP.X_COMPETENCIA AS idCompetencia ,  "
			+ "GRP2.X_OBJETIVO,COM.D_PDCCOMPETENCIA AS descCompetencia,  "
			+ "GEN.D_OBJETIVO AS desObjetivo  "
			+ "FROM  DELPHOS_SEGEDU.TLPDCENOBJCEN OBJESP  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCOBJGRPCOM GRP ON GRP.X_OBJETIVO = OBJESP.X_OBJETIVO  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCCENOBJLINACT ACT ON ACT.X_PDCENOBJCEN = OBJESP.X_PDCENOBJCEN  "
			+ "AND ACT.L_ACTIVO = 'S' AND ACT.EST_LINEAACT = 'D' "
			+ "INNER JOIN (  "
			+ "			SELECT X_COMPETENCIA ,X_OBJETIVO FROM DELPHOS_SEGEDU.TLPDCOBJGRPCOM)   "
			+ "			GRP2 ON GRP2.X_COMPETENCIA = GRP.X_COMPETENCIA  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCCOM COM ON COM.X_COMPETENCIA = GRP.X_COMPETENCIA  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCOBJ GEN ON GEN.X_OBJETIVO = GRP2.X_OBJETIVO  "
			+ "WHERE OBJESP.X_CENTRO = :x_centro AND OBJESP.C_ANNO = :anno AND OBJESP.L_ACTIVO = 'S'  "
			+ "AND GEN.L_ACTIVO = 'S'  ", nativeQuery = true)
	List<InformeProjection> getAmbitosConObjetivosEspecificos(@Param("anno") String anno, @Param("x_centro") String x_centro);
	
	@Query(value = "SELECT DISTINCT  OBJESP.C_ANNO  "
			+ "FROM  DELPHOS_SEGEDU.TLPDCENOBJCEN OBJESP   "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCCENOBJLINACT ACT ON ACT.X_PDCENOBJCEN  = OBJESP.X_PDCENOBJCEN  "
			+ "INNER JOIN (SELECT DISTINCT aca.C_ANNO AS anio, pub.X_CUEPUB AS idCuestionario  , USU.X_CENTRO   "
			+ "			 FROM DELPHOS_SEGEDU.TLCURSOACA aca    "
			+ " 			INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS    "
			+ " 			INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB    "
			+ " 			INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL    "
			+ " 			INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL    "
			+ " 			WHERE sec.X_SECTOR = '2'    "
			+ " 			  AND usu.X_CENTRO = :x_centro   "
			+ " 			  AND usu.L_PRESENTADO = 'S'    "
			+ " 			  AND usu.L_ACTIVO = 'N'  "
			+ " 			ORDER BY aca.C_ANNO DESC "
			+ "			)AUX ON AUX.X_CENTRO = OBJESP.X_CENTRO AND AUX.ANIO = OBJESP.C_ANNO  "
			+ "WHERE OBJESP.X_CENTRO = :x_centro AND OBJESP.L_ACTIVO ='S' AND ACT.L_ACTIVO= 'S'  AND ACT.EST_LINEAACT = 'D' "
			+ "ORDER BY OBJESP.C_ANNO DESC", nativeQuery = true)
	List<Integer> getHistoricoAniosConObjEspYLineasActuacion(@Param("x_centro") String x_centro);
	
	
	
}

