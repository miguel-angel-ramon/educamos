package es.jccm.edu.pdc.adapter.out.repositories.cuestionarios;

import es.jccm.edu.pdc.application.domain.cuestionarios.projection.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Cuestionario;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.QCuestionario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;

@Repository
public interface CuestionarioRepository extends AbstractRepository<Cuestionario, Long, QCuestionario> {

	@Query(value = "SELECT CUEPUBUSU.X_CUEPUBUSU idCuePubUsu, CUEPUBUSU.X_CUEPUB idCuePub, CUES.X_CUESTIONARIO idCuestionario, CUES.D_CUESTIONARIO nombre, CUES.T_CUESTIONARIO descripcion, DECODE(CUEPUBUSU.L_ACTIVO, 'S', 'true', 'false') activo, DECODE(CUEPUBUSU.L_PRESENTADO, 'S', 'true', 'false') presentado, "
			+ "CUEPUB.F_INI_RESPUESTAS AS fInicioRespuestas, CUEPUB.F_FIN_RESPUESTAS AS fFinRespuestas "
			+ "			FROM DELPHOS_SEGEDU.TLCUESTIONARIO CUES, DELPHOS_SEGEDU.TLCUEPUB CUEPUB, DELPHOS_SEGEDU.TLCUEPUBUSU CUEPUBUSU  "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLCENTROS centros ON CUEPUBUSU.X_CENTRO = centros.X_CENTRO  "
			+ "			WHERE CUEPUBUSU.X_USUARIO = :xUsuarioComunica  "
			+ "			AND CUEPUBUSU.X_CUEPUB = CUEPUB.X_CUEPUB  "
			+ "			AND CUEPUB.X_CUESTIONARIO = CUES.X_CUESTIONARIO  "
			+ "			AND CUES.C_CODIGO = :codCuestionario  "
			+ "			AND centros.c_codigo = :codCentro ", nativeQuery = true)
	CuestionarioProjection getCuestionarioDocenteByCodigo(@Param("xUsuarioComunica") Long xUsuarioComunica, @Param("codCentro") Long codCentro,
			@Param("codCuestionario") String codCuestionario);
	
	
	@Query(value = "SELECT DISTINCT aca.C_ANNO as anio,  "
			+ "			     usu.X_CUEPUBUSU idCuePubUsu,  "
			+ "			     usu.X_CUEPUB idCuePub,  "
			+ "			     CUES.X_CUESTIONARIO idCuestionario,  "
			+ "			     CUES.D_CUESTIONARIO nombre,  "
			+ "			     CUES.T_CUESTIONARIO descripcion,  "
			+ "			     DECODE(usu.L_ACTIVO, 'S', 'true', 'false') activo,  "
			+ "			     DECODE(usu.L_PRESENTADO, 'S', 'true', 'false') presentado,  "
			+ "	pub.F_INI_RESPUESTAS AS fInicioRespuestas, pub.F_FIN_RESPUESTAS AS fFinRespuestas "
			+ "FROM DELPHOS_SEGEDU.TLCURSOACA aca  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCUESTIONARIO CUES ON cues.X_CUESTIONARIO = usu.X_CUEPUB  "
			+ "WHERE sec.X_SECTOR = '2'  "
			+ "  AND usu.X_CENTRO = :idCentro  "
			+ "  AND aca.C_ANNO = :anio ", nativeQuery = true)
	CuestionarioProjection getCuestionarioCentro(@Param("idCentro") Long idCentro, @Param("anio") Long anio);
	

	@Query(value = "SELECT SEC.X_CUESEC idSeccion, SEC.D_CUESEC nombre, SEC.T_CUESEC descripcion, SEC.N_ORDPRE orden " +
			"FROM DELPHOS_SEGEDU.TLCUESEC SEC " +
			"WHERE SEC.X_CUESTIONARIO = :idCuestionario " +
			"ORDER BY orden", nativeQuery = true)
	List<SeccionProjection> getSeccionesByIdCuestionario(@Param("idCuestionario") Long idCuestionario);

	@Query(value = "SELECT PRE.X_CUEPRE idPregunta, PRE.D_CUEPRE titulo, PRE.T_CUEPRE descripcion, SECPRE.N_ORDPRE orden, PRE.L_OBLIGATORIA obligatoria, PRE.L_PERMITETXT permiteTexto, TIP.C_CUETIPPRE codTipo, TIP.D_CUETIPPRE tipo " +
			"FROM DELPHOS_SEGEDU.TLCUESEC SEC, DELPHOS_SEGEDU.TLCUESECCPRE SECPRE, DELPHOS_SEGEDU.TLCUEPRE PRE, DELPHOS_SEGEDU.TLCUETIPPRE TIP " +
			"WHERE SEC.X_CUESEC = SECPRE.X_CUESEC " +
			"AND SECPRE.X_CUEPRE = PRE.X_CUEPRE " +
			"AND PRE.X_CUETIPPRE = TIP.X_CUETIPPRE " +
			"AND SEC.X_CUESEC = :idSeccion " +
			"ORDER BY orden", nativeQuery = true)
	List<PreguntaProjection> getPreguntasByIdSeccion(@Param("idSeccion") Long idSeccion);


	@Query(value = "SELECT PREOPC.X_CUEPREOPC idRespuesta, CUEOPC.D_CUEOPC nombre, CUEOPC.N_VALOR valor, PREOPC.N_ORDPRE orden " +
			"FROM  DELPHOS_SEGEDU.TLCUEPREOPC PREOPC, DELPHOS_SEGEDU.TLCUEOPC CUEOPC " +
			"WHERE PREOPC.X_CUEOPC = CUEOPC.X_CUEOPC " +
			"AND PREOPC.X_CUEPRE = :idPregunta " +
			"ORDER BY orden", nativeQuery = true)
	List<RespuestaProjection> getRespuestasByIdPregunta(@Param("idPregunta") Long idPregunta);

    @Modifying
	@Query(value = "INSERT INTO DELPHOS_SEGEDU.TLCUEPUBUSURES " +
			"(X_CUEPUBUSURES,  X_CUEPUBUSU, X_CUEPRE, X_CUEPREOPC, T_TEXTO, F_ULTGRAB) " +
			"VALUES ( DELPHOS_SEGEDU.TLS_RCUXCUEPUBUSURES.nextval, :idCuePubUsu, :idPregunta, :idRespuesta, :textoRespuesta, SYSDATE)", nativeQuery = true)
	void setRespuestaUsuario(
			@Param("idCuePubUsu") Long idCuePubUsu,
			@Param("idPregunta") Long idPregunta,
			@Param("idRespuesta") Long idRespuesta,
			@Param("textoRespuesta") String textoRespuesta);


	@Modifying
	@Query(value = "UPDATE DELPHOS_SEGEDU.TLCUEPUBUSURES " +
			"SET X_CUEPREOPC = :idRespuesta, " +
			"T_TEXTO = :textoRespuesta, " +
			"F_ULTGRAB = SYSDATE " +
			"WHERE X_CUEPUBUSU = :idCuePubUsu " +
			"AND X_CUEPRE = :idPregunta", nativeQuery = true)
	void editRespuestaUsuario(
			@Param("idCuePubUsu") Long idCuePubUsu,
			@Param("idPregunta") Long idPregunta,
			@Param("idRespuesta") Long idRespuesta,
			@Param("textoRespuesta") String textoRespuesta);

	@Modifying
	@Query(value = "CALL DELPHOS_SEGEDU.TLPQ_CUESTIONARIO.tlp_finalizar(:idCuePubUsu)", nativeQuery = true)
	void finalizarCuestionario(
			@Param("idCuePubUsu") Long idCuePubUsu);


	@Modifying
	@Query(value = "call delphos_segedu.tlpq_cuestionario.tlp_actValCenCuePub(:idCuestionario) ", nativeQuery = true)
	void generarInforme(
			@Param("idCuestionario") Long idCuestionario);


	@Query(value = "SELECT DISTINCT res.x_cuepre AS idPregunta , res.x_cuepubusu AS idCuePubUsu, preopc.X_CUEPREOPC  AS idRespuesta, opc.D_CUEOPC  AS textoRespuesta "
			+ "from delphos_segedu.tlcuepubusures res "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCUEPREOPC preopc ON preopc.X_CUEPREOPC = res.X_CUEPREOPC "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCUEOPC opc ON opc.X_CUEOPC = preopc.X_CUEOPC "
			+ "WHERE res.X_CUEPUBUSU = :idCuePubUsu "
			+ "AND res.X_CUEPRE = :idPregunta", nativeQuery = true)
	List<RespuestaSeleccionadaProjection> getRespuestaSeleccionada(@Param("idCuePubUsu") Long idCuePubUsu, @Param("idPregunta") Long idPregunta);
	

	@Query(value = "SELECT DISTINCT MATRIZ.C_NIVEL AS NIVEL, "
			+ "NVL(DATOS.N_USUARIOS, 0) AS COUNTNIVEL, "
			+ "D_SUBDIMENSION AS SUBDIMENSION "
			+ "FROM "
			+ "(SELECT X_SUBDIMENSION, "
			+ "D_SUBDIMENSION, "
			+ "X_NIVEL, "
			+ "C_NIVEL "
			+ "FROM DELPHOS_SEGEDU.TLPDCSUBDIM sub "
			+ "CROSS JOIN DELPHOS_SEGEDU.TLPDCNIVVAL NIV "
			+ "WHERE NIV.X_CUEPUB = :x_cuepub AND sub.X_SUBDIMENSION IN (1,2,3,4,5,6) ) MATRIZ "
			+ "LEFT JOIN "
			+ "(SELECT DATOS.X_SUBDIMENSION, "
			+ "NIV.C_NIVEL, "
			+ "COUNT(DATOS.X_USUARIO) AS N_USUARIOS "
			+ "FROM "
			+ "(SELECT CPU.X_USUARIO, "
			+ "SDIM.X_SUBDIMENSION, "
			+ "AVG(VAL.N_VALOR) N_MEDIA "
			+ "FROM DELPHOS_SEGEDU.TLCUEPUBUSU CPU "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCVALCUE VAL ON VAL.X_CUEPUBUSU = CPU.X_CUEPUBUSU "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCCOM COM ON VAL.X_COMPETENCIA = COM.X_COMPETENCIA "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCSUBDIM SDIM ON COM.X_SUBDIMENSION = SDIM.X_SUBDIMENSION "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCENTROS CENTRO ON CPU.X_CENTRO = CENTRO.X_CENTRO "
			+ "WHERE CENTRO.C_CODIGO = :codCentro "
			+ "AND CPU.X_CUEPUB = :x_cuepub "
			+ "AND CPU.L_PRESENTADO = 'S' "
			+ "GROUP BY CPU.X_USUARIO, "
			+ "SDIM.X_SUBDIMENSION) DATOS, "
			+ "DELPHOS_SEGEDU.TLPDCNIVVAL NIV "
			+ "WHERE NIV.X_CUEPUB = :x_cuepub "
			+ "AND ((NIV.X_CUEPUB = 1 "
			+ "AND DATOS.N_MEDIA >= NIV.N_VALORMIN "
			+ "AND DATOS.N_MEDIA < NIV.N_VALORMAX) "
			+ "OR (NIV.X_CUEPUB > 1 "
			+ "AND DATOS.N_MEDIA > NIV.N_VALORMIN "
			+ "AND DATOS.N_MEDIA <= NIV.N_VALORMAX)) "
			+ "GROUP BY DATOS.X_SUBDIMENSION, "
			+ "NIV.C_NIVEL) DATOS ON MATRIZ.C_NIVEL = DATOS.C_NIVEL "
			+ "AND MATRIZ.X_SUBDIMENSION = DATOS.X_SUBDIMENSION "
			+ "ORDER BY D_SUBDIMENSION,NIVEL ", nativeQuery = true)
	List<ValoresAmbitoCincoProjection> getAreasAmbitoCinco(
			@Param("codCentro") Long codCentro, @Param("x_cuepub") String x_cuepub
			);
	
	@Query(value = "SELECT DISTINCT DATO AS NIVEL, NVL(COUNTNIVEL, 0) COUNTNIVEL FROM ( "
			+ "SELECT NI.C_NIVEL DATO, "
			+ "(SELECT NVL(COUNTNIVEL, 0) FROM( "
			+ "SELECT C_NIVEL, NVL(COUNT(1),0) COUNTNIVEL "
			+ "FROM ( "
			+ "SELECT DATOS.X_USUARIO, NIV.C_NIVEL FROM ( "
			+ "SELECT CPU.X_USUARIO,  AVG(VAL.N_VALOR) N_MEDIA "
			+ "FROM DELPHOS_SEGEDU.TLCUEPUBUSU CPU "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCVALCUE VAL ON VAL.X_CUEPUBUSU = CPU.X_CUEPUBUSU "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCCOM COM ON VAL.X_COMPETENCIA = COM.X_COMPETENCIA "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCSUBDIM SDIM ON COM.X_SUBDIMENSION = SDIM.X_SUBDIMENSION "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCENTROS CENTRO ON CPU.X_CENTRO = CENTRO.X_CENTRO "
			+ "WHERE CENTRO.C_CODIGO = :codCentro "
			+ "AND CPU.X_CUEPUB = :x_cuepub "
			+ "AND CPU.L_PRESENTADO = 'S' "
			+ "GROUP BY CPU.X_USUARIO) DATOS, "
			+ "DELPHOS_SEGEDU.TLPDCNIVVAL NIV "
			+ "WHERE NIV.X_CUEPUB = :x_cuepub "
			+ "AND "
			+ "((NIV.X_CUEPUB = 1 AND DATOS.N_MEDIA >= NIV.N_VALORMIN AND DATOS.N_MEDIA < NIV.N_VALORMAX AND NIV.C_NIVEL = NI.C_NIVEL) "
			+ "OR "
			+ "(NIV.X_CUEPUB > 1 AND DATOS.N_MEDIA > NIV.N_VALORMIN AND DATOS.N_MEDIA <= NIV.N_VALORMAX AND NIV.C_NIVEL = NI.C_NIVEL))) "
			+ "GROUP BY C_NIVEL ORDER BY C_NIVEL ) "
			+ ")COUNTNIVEL "
			+ "FROM DELPHOS_SEGEDU.TLPDCNIVVAL NI WHERE NI.X_CUEPUB = :x_cuepub ORDER BY NI.C_NIVEL ASC) ORDER BY NIVEL DESC", nativeQuery = true)

	List<ValoresAmbitoCincoProjection> getSumAreasAmbitoCinco(
			@Param("codCentro") Long codCentro , @Param("x_cuepub") String x_cuepub	);
	@Query(value = "select x_cuepre AS idCuepre , x_cueopc AS idCueopc, d_sugerencia AS DescSugerencia from (   "
			+ "			SELECT DELPHOS_SEGEDU.tlcuepubusu.x_cuepubusu, DELPHOS_SEGEDU.tlcuepubusures.x_cuepubusures,   "
			+ "			DELPHOS_SEGEDU.TLCUESTIONARIO.x_cuestionario, DELPHOS_SEGEDU.TLCUESTIONARIO.d_cuestionario, DELPHOS_SEGEDU.TLCUESTIONARIO.t_cuestionario, DELPHOS_SEGEDU.TLCUESTIONARIO.l_activo, DELPHOS_SEGEDU.TLCUEPUB.x_cuepub,  "
			+ "			DELPHOS_SEGEDU.TLCUESEC.x_cuesec,DELPHOS_SEGEDU.TLCUESEC.d_cuesec,TLCUESEC.t_cuesec,DELPHOS_SEGEDU.TLCUESEC.l_sinseccion,   "
			+ "			DELPHOS_SEGEDU.TLCUESECCPRE.x_cueseccpre, DELPHOS_SEGEDU.TLCUESECCPRE.t_cuesecpre, DELPHOS_SEGEDU.TLCUEPRE.x_cuepre, DELPHOS_SEGEDU.TLCUEPRE.d_cuepre,   "
			+ "			DELPHOS_SEGEDU.TLCUEPRE.t_cuepre, DELPHOS_SEGEDU.TLCUEPRE.l_obligatoria, DELPHOS_SEGEDU.TLCUEPRE.l_permitetxt L_PERMITETXTPreg, DELPHOS_SEGEDU.TLCUETIPPRE.x_cuetippre,  "
			+ "			DELPHOS_SEGEDU.TLCUETIPPRE.c_cuetippre,  DELPHOS_SEGEDU.TLCUETIPPRE.d_cuetippre , DELPHOS_SEGEDU.TLCUEPREOPC.x_cuepreopc, DELPHOS_SEGEDU.TLCUEPREOPC.x_cueopc ,   "
			+ "			DELPHOS_SEGEDU.TLCUEOPC.d_cueopc, DELPHOS_SEGEDU.TLCUEOPC.t_cueopc,  DELPHOS_SEGEDU.TLCUEOPC.L_PERMITETXT L_PERMITETXTOpc ,   "
			+ "			(SELECT DISTINCT DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepreopc   "
			+ "			FROM DELPHOS_SEGEDU.TLCUEPUBUSURES, DELPHOS_SEGEDU.tlcuepubusu tlcuepubusu2, DELPHOS_SEGEDU.tlcuepreopc tlcuepreopc2, DELPHOS_SEGEDU.tlcueopc tlcueopc2 , DELPHOS_SEGEDU.tlcuepre tlcuepre2   "
			+ "			WHERE DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepubusu = tlcuepubusu2.x_cuepubusu   "
			+ "			AND tlcuepubusu2.x_usuario       = tlcuepubusu.x_usuario   "
			+ "			AND tlcuepubusu2.x_centro        = tlcuepubusu.x_centro   "
			+ "			AND tlcuepubusu2.x_cuepub        = tlcuepubusu.x_cuepub   "
			+ "			AND tlcuepreopc2.x_cuepreopc = tlcuepreopc.x_cuepreopc   "
			+ "			AND tlcueopc2.x_cueopc = tlcueopc.x_cueopc   "
			+ "			AND tlcuepre2.x_cuepre = tlcuepre.x_cuepre   "
			+ "			AND DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepreopc  = tlcuepreopc2.x_cuepreopc) valorguardado,   "
			+ "			(select distinct sug.d_sugerencia from DELPHOS_SEGEDU.tlcuepubusu cpu  inner join DELPHOS_SEGEDU.tlcuepubusures res on res.x_cuepubusu = cpu.x_cuepubusu   "
			+ "			inner join DELPHOS_SEGEDU.tlpdcresval val on val.x_cuepubusures = res.x_cuepubusures  inner join DELPHOS_SEGEDU.tlcuepre pre on res.x_cuepre = pre.x_cuepre  inner join DELPHOS_SEGEDU.tlcueseccpre csc on csc.x_cuepre = pre.x_cuepre   "
			+ "			inner join DELPHOS_SEGEDU.tlcuesec secc on secc.x_cuesec = csc.x_cuesec inner join DELPHOS_SEGEDU.tlcuepreopc copc on val.x_cuepreopc = copc.x_cuepreopc inner join DELPHOS_SEGEDU.tlcueopc opc on copc.x_cueopc = opc.x_cueopc   "
			+ "			inner join DELPHOS_SEGEDU.tlpdcnivval niv on niv.x_cuepub =  :xCuepub and ((niv.x_cuepub = 1 and niv.n_valormin <= val.n_valor and val.n_valor < niv.n_valormax) or (niv.x_cuepub > 1 and niv.n_valormin < val.n_valor and val.n_valor <= niv.n_valormax))   "
			+ "			inner join DELPHOS_SEGEDU.tlpdcsugniv sniv on sniv.x_nivel = niv.x_nivel and sniv.x_cuepre = pre.x_cuepre   "
			+ "			inner join DELPHOS_SEGEDU.tlpdcsug sug on sniv.x_sugerencia = sug.x_sugerencia   "
			+ "			where pre.x_cuepre = tlcuepre.x_cuepre   "
			+ "			and opc.x_cueopc = TLCUEOPC. x_cueopc   "
			+ "			and copc.x_cuepreopc = TLCUEPREOPC.x_cuepreopc   "
			+ "			and res.x_cuepubusures = TLCUEPUBUSURES.x_cuepubusures  )d_sugerencia "
			+ "			FROM DELPHOS_SEGEDU.TLCUESTIONARIO,   "
			+ "			DELPHOS_SEGEDU.TLCUESEC,   "
			+ "			DELPHOS_SEGEDU.TLCUESECCPRE ,  "
			+ "			DELPHOS_SEGEDU.TLCUEPRE,   "
			+ "			DELPHOS_SEGEDU.TLCUETIPPRE ,   "
			+ "			DELPHOS_SEGEDU.TLCUEPREOPC,   "
			+ "			DELPHOS_SEGEDU.TLCUEOPC ,   "
			+ "			DELPHOS_SEGEDU.TLCUEPUB ,   "
			+ "			DELPHOS_SEGEDU.TLCUEPUBUSU,   "
			+ "			DELPHOS_SEGEDU.TLCUEPUBUSURES   "
			+ "			WHERE TLCUESTIONARIO.x_cuestionario = TLCUESEC.x_cuestionario   "
			+ "			AND DELPHOS_SEGEDU.TLCUESECCPRE.x_cuesec           = TLCUESEC.x_cuesec   "
			+ "			AND DELPHOS_SEGEDU.tlcuepre.x_cuepre               = tlcueseccpre.x_cuepre   "
			+ "			AND DELPHOS_SEGEDU.tlcuetippre.x_cuetippre         = tlcuepre.x_cuetippre   "
			+ "			AND DELPHOS_SEGEDU.TLCUEPREOPC.x_cuepre            = tlcuepre.x_cuepre   "
			+ "			AND DELPHOS_SEGEDU.TLCUEOPC.x_cueopc               = TLCUEPREOPC.x_cueopc   "
			+ "			AND DELPHOS_SEGEDU.TLCUESTIONARIO.x_cuestionario   = TLCUEPUB.x_cuestionario   "
			+ "			AND DELPHOS_SEGEDU.TLCUEPUBUSU.x_cuepub            = TLCUEPUB.x_cuepub   "
			+ "			AND DELPHOS_SEGEDU.TLCUESTIONARIO.x_cuestionario   =  :xCuepub   "
			+ "			AND DELPHOS_SEGEDU.TLCUEPUB.x_cuepub               =  :xCuepub   "
			+ "			AND DELPHOS_SEGEDU.TLCUEPUBUSU.x_centro            =  :xCentro    "
			+ "			and DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepubusu = DELPHOS_SEGEDU.TLCUEPUBUSU.x_cuepubusu   "
			+ "			and DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepre = tlcuepre.x_cuepre   "
			+ "			and DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepreopc = DELPHOS_SEGEDU.TLCUEPREOPC.x_cuepreopc   "
			+ "			ORDER BY DELPHOS_SEGEDU.TLCUESEC.n_ordpre ASC,  tlcueseccpre.n_ordpre ASC, TLCUEPREOPC.n_ordpre ASC   )  where valorguardado is not null  ", nativeQuery = true)
	List<SugerenciasMejorasProjection> getSugMejCentro(
			@Param("xCentro") Long xCentro, @Param("xCuepub") Long xCuepub);
	
	
	@Query(value = "select x_cuepre AS idCuepre , x_cueopc AS idCueopc, d_sugerencia AS DescSugerencia ,d_comomejorar AS descComomejorar from ( " +
			"SELECT DELPHOS_SEGEDU.tlcuepubusu.x_cuepubusu, DELPHOS_SEGEDU.tlcuepubusures.x_cuepubusures, " +
			"DELPHOS_SEGEDU.TLCUESTIONARIO.x_cuestionario, DELPHOS_SEGEDU.TLCUESTIONARIO.d_cuestionario, DELPHOS_SEGEDU.TLCUESTIONARIO.t_cuestionario, DELPHOS_SEGEDU.TLCUESTIONARIO.l_activo, DELPHOS_SEGEDU.TLCUEPUB.x_cuepub," +
			"DELPHOS_SEGEDU.TLCUESEC.x_cuesec,DELPHOS_SEGEDU.TLCUESEC.d_cuesec,TLCUESEC.t_cuesec,DELPHOS_SEGEDU.TLCUESEC.l_sinseccion, " +
			"DELPHOS_SEGEDU.TLCUESECCPRE.x_cueseccpre, DELPHOS_SEGEDU.TLCUESECCPRE.t_cuesecpre, DELPHOS_SEGEDU.TLCUEPRE.x_cuepre, DELPHOS_SEGEDU.TLCUEPRE.d_cuepre, " +
			"DELPHOS_SEGEDU.TLCUEPRE.t_cuepre, DELPHOS_SEGEDU.TLCUEPRE.l_obligatoria, DELPHOS_SEGEDU.TLCUEPRE.l_permitetxt L_PERMITETXTPreg, DELPHOS_SEGEDU.TLCUETIPPRE.x_cuetippre," +
			"DELPHOS_SEGEDU.TLCUETIPPRE.c_cuetippre,  DELPHOS_SEGEDU.TLCUETIPPRE.d_cuetippre , DELPHOS_SEGEDU.TLCUEPREOPC.x_cuepreopc, DELPHOS_SEGEDU.TLCUEPREOPC.x_cueopc , " +
			"DELPHOS_SEGEDU.TLCUEOPC.d_cueopc, DELPHOS_SEGEDU.TLCUEOPC.t_cueopc,  DELPHOS_SEGEDU.TLCUEOPC.L_PERMITETXT L_PERMITETXTOpc , " +
			"(SELECT DISTINCT DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepreopc " +
			"FROM DELPHOS_SEGEDU.TLCUEPUBUSURES, DELPHOS_SEGEDU.tlcuepubusu tlcuepubusu2, DELPHOS_SEGEDU.tlcuepreopc tlcuepreopc2, DELPHOS_SEGEDU.tlcueopc tlcueopc2 , DELPHOS_SEGEDU.tlcuepre tlcuepre2 " +
			"WHERE DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepubusu = tlcuepubusu2.x_cuepubusu " +
			"AND tlcuepubusu2.x_usuario       = tlcuepubusu.x_usuario " +
			"AND tlcuepubusu2.x_centro        = tlcuepubusu.x_centro " +
			"AND tlcuepubusu2.x_cuepub        = tlcuepubusu.x_cuepub " +
			"AND tlcuepreopc2.x_cuepreopc = tlcuepreopc.x_cuepreopc " +
			"AND tlcueopc2.x_cueopc = tlcueopc.x_cueopc " +
			"AND tlcuepre2.x_cuepre = tlcuepre.x_cuepre " +
			"AND DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepreopc  = tlcuepreopc2.x_cuepreopc) valorguardado, " +
			"(select distinct sug.d_sugerencia from DELPHOS_SEGEDU.tlcuepubusu cpu  inner join DELPHOS_SEGEDU.tlcuepubusures res on res.x_cuepubusu = cpu.x_cuepubusu " +
			"inner join DELPHOS_SEGEDU.tlpdcresval val on val.x_cuepubusures = res.x_cuepubusures  inner join DELPHOS_SEGEDU.tlcuepre pre on res.x_cuepre = pre.x_cuepre  inner join DELPHOS_SEGEDU.tlcueseccpre csc on csc.x_cuepre = pre.x_cuepre " +
			"inner join DELPHOS_SEGEDU.tlcuesec secc on secc.x_cuesec = csc.x_cuesec inner join DELPHOS_SEGEDU.tlcuepreopc copc on val.x_cuepreopc = copc.x_cuepreopc inner join DELPHOS_SEGEDU.tlcueopc opc on copc.x_cueopc = opc.x_cueopc " +
			"inner join DELPHOS_SEGEDU.tlpdcnivval niv on niv.x_cuepub =  :xCuepub and ((niv.x_cuepub = 1 and niv.n_valormin <= val.n_valor and val.n_valor < niv.n_valormax) or (niv.x_cuepub > 1 and niv.n_valormin < val.n_valor and val.n_valor <= niv.n_valormax)) " +
			"inner join DELPHOS_SEGEDU.tlpdcsugniv sniv on sniv.x_nivel = niv.x_nivel and sniv.x_cuepre = pre.x_cuepre " +
			"inner join DELPHOS_SEGEDU.tlpdcsug sug on sniv.x_sugerencia = sug.x_sugerencia " +
			"where pre.x_cuepre = tlcuepre.x_cuepre " +
			"and opc.x_cueopc = TLCUEOPC. x_cueopc " +
			"and copc.x_cuepreopc = TLCUEPREOPC.x_cuepreopc " +
			"and res.x_cuepubusures = TLCUEPUBUSURES.x_cuepubusures  )d_sugerencia, " +
			"(select distinct sug.d_comomejorar " +
			"from DELPHOS_SEGEDU.tlcuepubusu cpu " +
			"inner join DELPHOS_SEGEDU.tlcuepubusures res on res.x_cuepubusu = cpu.x_cuepubusu " +
			"inner join DELPHOS_SEGEDU.tlpdcresval val on val.x_cuepubusures = res.x_cuepubusures " +
			"inner join DELPHOS_SEGEDU.tlcuepre pre on res.x_cuepre = pre.x_cuepre " +
			"inner join DELPHOS_SEGEDU.tlcueseccpre csc on csc.x_cuepre = pre.x_cuepre " +
			"inner join DELPHOS_SEGEDU.tlcuesec secc on secc.x_cuesec = csc.x_cuesec " +
			"inner join DELPHOS_SEGEDU.tlcuepreopc copc on val.x_cuepreopc = copc.x_cuepreopc " +
			"inner join DELPHOS_SEGEDU.tlcueopc opc on copc.x_cueopc = opc.x_cueopc " +
			"inner join DELPHOS_SEGEDU.tlpdcnivval niv on niv.x_cuepub =  :xCuepub and ((niv.x_cuepub = 1 and niv.n_valormin <= val.n_valor and val.n_valor < niv.n_valormax) " +
			"or (niv.x_cuepub > 1 and niv.n_valormin < val.n_valor and val.n_valor <= niv.n_valormax)) " +
			"inner join DELPHOS_SEGEDU.tlpdcsugniv sniv on sniv.x_nivel = niv.x_nivel and sniv.x_cuepre = pre.x_cuepre " +
			"inner join DELPHOS_SEGEDU.tlpdcsug sug on sniv.x_sugerencia = sug.x_sugerencia " +
			"where  pre.x_cuepre = tlcuepre.x_cuepre " +
			"and opc.x_cueopc = TLCUEOPC. x_cueopc " +
			"and copc.x_cuepreopc = TLCUEPREOPC.x_cuepreopc " +
			"and res.x_cuepubusures = TLCUEPUBUSURES.x_cuepubusures   )d_comomejorar " +
			"FROM DELPHOS_SEGEDU.TLCUESTIONARIO, " +
			"DELPHOS_SEGEDU.TLCUESEC, " +
			"DELPHOS_SEGEDU.TLCUESECCPRE ," +
			"DELPHOS_SEGEDU.TLCUEPRE, " +
			"DELPHOS_SEGEDU.TLCUETIPPRE , " +
			"DELPHOS_SEGEDU.TLCUEPREOPC, " +
			"DELPHOS_SEGEDU.TLCUEOPC , " +
			"DELPHOS_SEGEDU.TLCUEPUB , " +
			"DELPHOS_SEGEDU.TLCUEPUBUSU, " +
			"DELPHOS_SEGEDU.TLCUEPUBUSURES " +
			"WHERE TLCUESTIONARIO.x_cuestionario = TLCUESEC.x_cuestionario " +
			"AND DELPHOS_SEGEDU.TLCUESECCPRE.x_cuesec           = TLCUESEC.x_cuesec " +
			"AND DELPHOS_SEGEDU.tlcuepre.x_cuepre               = tlcueseccpre.x_cuepre " +
			"AND DELPHOS_SEGEDU.tlcuetippre.x_cuetippre         = tlcuepre.x_cuetippre " +
			"AND DELPHOS_SEGEDU.TLCUEPREOPC.x_cuepre            = tlcuepre.x_cuepre " +
			"AND DELPHOS_SEGEDU.TLCUEOPC.x_cueopc               = TLCUEPREOPC.x_cueopc " +
			"AND DELPHOS_SEGEDU.TLCUESTIONARIO.x_cuestionario   = TLCUEPUB.x_cuestionario " +
			"AND DELPHOS_SEGEDU.TLCUEPUBUSU.x_cuepub            = TLCUEPUB.x_cuepub " +
			"AND DELPHOS_SEGEDU.TLCUESTIONARIO.x_cuestionario   =  :xCuepub " +
			"AND DELPHOS_SEGEDU.TLCUEPUB.x_cuepub               =  :xCuepub " +
			"AND DELPHOS_SEGEDU.TLCUEPUBUSU.x_centro            =  :xCentro " +
			"AND DELPHOS_SEGEDU.TLCUEPUBUSU.x_usuario           =  :xUsuario " +
			"and DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepubusu = DELPHOS_SEGEDU.TLCUEPUBUSU.x_cuepubusu " +
			"and DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepre = tlcuepre.x_cuepre " +
			"and DELPHOS_SEGEDU.TLCUEPUBUSURES.x_cuepreopc = DELPHOS_SEGEDU.TLCUEPREOPC.x_cuepreopc " +
			"ORDER BY DELPHOS_SEGEDU.TLCUESEC.n_ordpre ASC,  tlcueseccpre.n_ordpre ASC, TLCUEPREOPC.n_ordpre ASC   )  where valorguardado is not null ", nativeQuery = true)
	List<SugerenciasMejorasProjection> getSugMejDocente(
			@Param("xCentro") Long xCentro, @Param("xUsuario") Long xUsuario, @Param("xCuepub") Long xCuepub
			);
	
	// Obtiene todos los años y el xcuepub en los que se ha finalizado los cuestionarios de tipo x_sector, en el centro idCentro 
	@Query(value = "SELECT DISTINCT aca.C_ANNO AS anio, pub.X_CUEPUB AS idCuestionario  "
			+ "			 FROM DELPHOS_SEGEDU.TLCURSOACA aca  "
			+ "			 INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS  "
			+ "			 INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB  "
			+ "			 INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL  "
			+ "			 INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL  "
			+ "			 WHERE sec.X_SECTOR = '1'  "
			+ "			   AND usu.X_CENTRO = :idCentro  "
			+ "			   AND usu.L_PRESENTADO = 'S'  "
			+ "			   AND usu.L_ACTIVO = 'N' "
			+ "			ORDER BY aca.C_ANNO desc" , nativeQuery = true)
	List<HistoricoCuestionarioProjection> getHistoricoCuestionarioDocente(@Param("idCentro") Long idCentro);
	
	// Obtiene todos los años y el xcuepub en los que se ha finalizado los cuestionarios de tipo x_sector, en el centro idCentro 
		@Query(value = "SELECT DISTINCT aca.C_ANNO AS anio, pub.X_CUEPUB AS idCuestionario  "
				+ "			 FROM DELPHOS_SEGEDU.TLCURSOACA aca  "
				+ "			 INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS  "
				+ "			 INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB  "
				+ "			 INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL  "
				+ "			 INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL  "
				+ "			 WHERE sec.X_SECTOR = '1'  "
				+ "			   AND usu.X_CENTRO = :idCentro  "
				+ "			   AND usu.X_USUARIO = :xUsuario  "
				+ "			   AND usu.L_PRESENTADO = 'S'  "
				+ "			   AND usu.L_ACTIVO = 'N' "
				+ "			ORDER BY aca.C_ANNO desc" , nativeQuery = true)
		List<HistoricoCuestionarioProjection> getHistoricoCuestionarioDocenteIndividual(@Param("xUsuario") Long xUsuario, @Param("idCentro") Long idCentro);
	
	@Query(value = "SELECT DISTINCT aca.C_ANNO AS anio, pub.X_CUEPUB AS idCuestionario   "
			+ "			FROM DELPHOS_SEGEDU.TLCURSOACA aca   "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLCUEPUB pub ON aca.F_INICIO <= pub.f_ini_respuestas AND aca.F_FINAL >= pub.F_FIN_RESPUESTAS   "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU usu ON pub.X_CUEPUB = usu.X_CUEPUB   "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLCUEPUBCOL col ON usu.X_CUEPUBCOL = col.X_CUEPUBCOL   "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLPDCSECCOL sec ON col.X_CUEPUBCOL = sec.X_CUEPUBCOL   "
			+ "			WHERE sec.X_SECTOR = '2'   "
			+ "			  AND usu.X_CENTRO = :idCentro   "
			+ "			  AND usu.L_PRESENTADO = 'S'   "
			+ "			  AND usu.L_ACTIVO = 'N' "
			+ "			ORDER BY aca.C_ANNO desc" , nativeQuery = true)
	List<HistoricoCuestionarioProjection> getHistoricoCuestionarioCentro(@Param("idCentro") Long idCentro);
	
	@Query(value="    SELECT"
			+ "        D_SUBDIMENSION AS nombreArea ,"
			+ "        NIV.C_NIVEL AS CNivel,"
			+ "        DATOS.MEDIA AS media "
			+ "    FROM"
			+ "        (  SELECT"
			+ "            SDIM.X_SUBDIMENSION,"
			+ "            SDIM.D_SUBDIMENSION,"
			+ "            AVG(VAL.N_VALOR) MEDIA    "
			+ "        FROM"
			+ "            DELPHOS_SEGEDU.TLPDCVALCUE VAL,"
			+ "            DELPHOS_SEGEDU.TLPDCCOM COM,"
			+ "            DELPHOS_SEGEDU.TLPDCSUBDIM SDIM           "
			+ "        WHERE"
			+ "            VAL.X_CUEPUBUSU =  :x_cuepubusu   "
			+ "            AND VAL.X_COMPETENCIA = COM.X_COMPETENCIA     "
			+ "            AND COM.X_SUBDIMENSION = SDIM.X_SUBDIMENSION   "
			+ "        GROUP BY"
			+ "            SDIM.X_SUBDIMENSION,"
			+ "            SDIM.D_SUBDIMENSION) DATOS,"
			+ "        DELPHOS_SEGEDU.TLPDCNIVVAL NIV,"
			+ "        DELPHOS_SEGEDU.tlcuepubusu cpu  "
			+ "    WHERE"
			+ "        cpu.x_cuepubusu =  :x_cuepubusu  "
			+ "        and ("
			+ "            ("
			+ "                cpu.x_cuepub = 1 "
			+ "                and DATOS.MEDIA >= NIV.N_VALORMIN "
			+ "                AND DATOS.MEDIA < NIV.N_VALORMAX"
			+ "            )      "
			+ "            or      ("
			+ "                cpu.x_cuepub > 1 "
			+ "                and DATOS.MEDIA > NIV.N_VALORMIN "
			+ "                AND DATOS.MEDIA <= NIV.N_VALORMAX"
			+ "            )"
			+ "        )  "
			+ "        and niv.x_cuepub = cpu.x_cuepub   "
			+ "    ORDER BY"
			+ "        D_SUBDIMENSION ASC"
			, nativeQuery = true)
	List<MediaPorAreaProjection> getMediasPorAreaUsuario(@Param("x_cuepubusu") String x_cuepubusu);
	
	
	@Query(value="SELECT SDIM.X_SUBDIMENSION as nombreArea, AVG(VAL.N_VALOR) MEDIA  "
			+ "FROM DELPHOS_SEGEDU.TLCUEPUBUSU CPU       "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCVALCUE VAL ON VAL.X_CUEPUBUSU = CPU.X_CUEPUBUSU       "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCCOM COM ON VAL.X_COMPETENCIA = COM.X_COMPETENCIA       "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPDCSUBDIM SDIM ON COM.X_SUBDIMENSION = SDIM.X_SUBDIMENSION "
			+ "WHERE CPU.X_CUEPUB =  :x_cuepub   "
			+ "AND CPU.L_PRESENTADO = 'S' "
			+ "GROUP BY SDIM.X_SUBDIMENSION "
			+ "ORDER BY SDIM.X_SUBDIMENSION ASC"
			,nativeQuery = true)
	List<MediaPorAreaProjection> getMediaPorCastillaLaMancha(@Param("x_cuepub") String x_cuepub);
	
	@Query(value="SELECT DATOS.MEDIA, NIV.C_NIVEL AS CNivel FROM (SELECT AVG(VAL.N_VALOR) MEDIA  FROM DELPHOS_SEGEDU.TLPDCVALCUE VAL,       DELPHOS_SEGEDU.TLPDCCOM COM,       DELPHOS_SEGEDU.TLPDCSUBDIM SDIM "
			+ "WHERE VAL.X_CUEPUBUSU =  :x_cuepubusu   AND VAL.X_COMPETENCIA = COM.X_COMPETENCIA   AND COM.X_SUBDIMENSION = SDIM.X_SUBDIMENSION) DATOS,   DELPHOS_SEGEDU.TLPDCNIVVAL NIV,   DELPHOS_SEGEDU.tlcuepubusu cpu  "
			+ "WHERE cpu.x_cuepubusu =  :x_cuepubusu    and niv.x_cuepub = cpu.x_cuepub  and ((cpu.x_cuepub = 1 and DATOS.MEDIA >= NIV.N_VALORMIN AND DATOS.MEDIA < NIV.N_VALORMAX)      or      (cpu.x_cuepub > 1 and DATOS.MEDIA > NIV.N_VALORMIN AND DATOS.MEDIA <= NIV.N_VALORMAX))"
			,nativeQuery = true)
	MediaPorAreaProjection getMediaTotalUsuario(@Param("x_cuepubusu") String x_cuepubusu);
	
	
	@Query(value = "SELECT "
			+ "    aca.C_ANNO as anio, "
			+ "    CUEPUBUSU.X_CUEPUBUSU idCuePubUsu, "
			+ "    CUEPUBUSU.X_CUEPUB idCuePub, "
			+ "    CUES.X_CUESTIONARIO idCuestionario, "
			+ "    CUES.D_CUESTIONARIO nombre, "
			+ "    CUES.T_CUESTIONARIO descripcion, "
			+ "    DECODE(CUEPUBUSU.L_ACTIVO, 'S', 'true', 'false') activo, "
			+ "    DECODE(CUEPUBUSU.L_PRESENTADO, 'S', 'true', 'false') presentado "
			+ "FROM "
			+ "    DELPHOS_SEGEDU.TLCUESTIONARIO CUES "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCUEPUB CUEPUB ON "
			+ "    CUEPUB.X_CUEPUB = CUES.X_CUESTIONARIO "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU CUEPUBUSU ON "
			+ "     CUEPUBUSU.X_CUEPUB = CUEPUB.X_CUEPUB  "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCENTROS centros ON "
			+ "    CUEPUBUSU.X_CENTRO = centros.X_CENTRO "
			+ "INNER JOIN DELPHOS_SEGEDU.TLCURSOACA aca ON "
			+ "    aca.F_INICIO <= CUEPUB.F_INI_RESPUESTAS  "
			+ "    AND aca.F_FINAL >= CUEPUB.F_FIN_RESPUESTAS  "
			+ "WHERE "
			+ "    CUEPUBUSU.X_USUARIO = :xUsuarioComunica "
			+ "    AND CUES.X_CUESTIONARIO  = :xcues "
			+ "    AND centros.X_CENTRO = :xcentro", nativeQuery = true)
	CuestionarioProjection getCuestionarioDocenteByXCUEPUB(@Param("xUsuarioComunica") Long xUsuarioComunica,@Param("xcentro") String xcentro, @Param("xcues") String xcues);
	
	@Query(value = "SELECT  "
			+ "			    aca.C_ANNO as anio,  "
			+ "			    CUEPUBUSU.X_CUEPUBUSU idCuePubUsu,  "
			+ "			    CUEPUBUSU.X_CUEPUB idCuePub,  "
			+ "			    CUES.X_CUESTIONARIO idCuestionario,  "
			+ "			    CUES.D_CUESTIONARIO nombre,  "
			+ "			    CUES.T_CUESTIONARIO descripcion,  "
			+ "			    DECODE(CUEPUBUSU.L_ACTIVO, 'S', 'true', 'false') activo,  "
			+ "			    DECODE(CUEPUBUSU.L_PRESENTADO, 'S', 'true', 'false') presentado  "
			+ "			FROM  "
			+ "			    DELPHOS_SEGEDU.TLCUESTIONARIO CUES  "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLCUEPUB CUEPUB ON  "
			+ "			    CUEPUB.X_CUEPUB = CUES.X_CUESTIONARIO  "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLCUEPUBUSU CUEPUBUSU ON  "
			+ "			     CUEPUBUSU.X_CUEPUB = CUEPUB.X_CUEPUB   "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLCENTROS centros ON  "
			+ "			    CUEPUBUSU.X_CENTRO = centros.X_CENTRO  "
			+ "			INNER JOIN DELPHOS_SEGEDU.TLCURSOACA aca ON  "
			+ "			    aca.F_INICIO <= CUEPUB.F_INI_RESPUESTAS   "
			+ "			    AND aca.F_FINAL >= CUEPUB.F_FIN_RESPUESTAS   "
			+ "			WHERE  "
			+ "			    CUES.X_CUESTIONARIO  = :xcues  "
			+ "			    AND centros.X_CENTRO = :xcentro", nativeQuery = true)
	CuestionarioProjection getCuestionarioCentroByXCUEPUB(@Param("xcentro") String xcentro, @Param("xcues") String xcues);
	
	
	@Query(value = "SELECT "
			+ "	round(( (SELECT count(*) TOTAL FROM DELPHOS_SEGEDU.tlcuepubusu "
			+ "			WHERE DELPHOS_SEGEDU.tlcuepubusu.x_centro = :x_centro AND DELPHOS_SEGEDU.tlcuepubusu.l_presentado = 'S'  "
			+ "			AND DELPHOS_SEGEDU.tlcuepubusu.x_cuepub = :x_cuepub) * 100) / (SELECT count(*) FROM DELPHOS_SEGEDU.tlcuepubusu   "
			+ "			WHERE DELPHOS_SEGEDU.tlcuepubusu.x_centro = :x_centro  "
			+ "			AND DELPHOS_SEGEDU.tlcuepubusu.x_cuepub = :x_cuepub ) , 2) numero "
			+ "FROM "
			+ "	dual", nativeQuery = true)
	Double getPorcentajeParticipantesDocentes(@Param("x_centro") String x_centro, @Param("x_cuepub") String x_cuepub);
	
	@Query(value="SELECT DISTINCT EMP.C_CARGO as codCargo, CARGOS.D_CARGO as descripcionCargo "
			+ "FROM DELPHOS.TLCARGOSEMP EMP  "
			+ "INNER JOIN  DELPHOS.TLCARGOS CARGOS ON CARGOS.C_CARGO = EMP.C_CARGO  "
			+ "INNER JOIN DELPHOS.TLPTOTRAEMP PTE ON PTE.X_EMPLEADO = EMP.X_EMPLEADO AND PTE.X_CENTRO = :xCentro  "
			+ "WHERE EMP.X_EMPLEADO = :X_EMPLEADO AND CARGOS.L_VIGENTE = 'S' AND (EMP.F_CESE IS NULL OR TRUNC(EMP.F_CESE) >= TRUNC(SYSDATE)) "
			, nativeQuery = true)
	List<CargoPDCProjection> getCargos(@Param("X_EMPLEADO") Long X_EMPLEADO, @Param("xCentro") Long xCentro);
	
	@Query(value="SELECT ACA.F_INICIO AS inicio, ACA.F_FINAL AS fin  "
			+ "			  FROM DELPHOS_SEGEDU.TLCURSOACA ACA "
			+ "			  WHERE ACA.C_ANNO = :anio"
			, nativeQuery = true)
	CursoAcademicoProjection getCursoAcademino(@Param("anio") Long anio);
	
	@Query(value="select t.C_CODIGO  FROM DELPHOS.tlpueoriper tl "
			+ "INNER JOIN DELPHOS.TLPERFILES t ON t.X_PERFIL = tl.X_PERFIL  "
			+ "WHERE tl.X_EMPLEADO = :X_EMPLEADO"
			, nativeQuery = true)
	List<String> getPerfiles(@Param("X_EMPLEADO") Long X_EMPLEADO);



}

