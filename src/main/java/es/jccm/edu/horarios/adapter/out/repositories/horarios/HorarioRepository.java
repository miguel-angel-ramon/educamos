package es.jccm.edu.horarios.adapter.out.repositories.horarios;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.horarios.application.domain.horarios.Horario;
import es.jccm.edu.horarios.application.domain.horarios.QHorario;
import es.jccm.edu.horarios.application.domain.horarios.projection.AtributosPlanificacionSemanalProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.AtributosTotalHorasProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.GrupoActividadProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.HorarioDireccionProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.HorarioGrupoActividadProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.HorarioPersonalProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.HorarioProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.ReunionOrganoCentroProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.TotalHorasProjection;
import es.jccm.edu.horarios.application.domain.horarios.projection.VisitasProgramadasProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface HorarioRepository extends AbstractRepository<Horario, Integer, QHorario>{
	
	@Modifying
	@Query(value= "SELECT hor.X_HORARIORE idHorario, hor.F_INICIO fechaInicio, hor.F_FIN fechaFin, nvl(dep.D_DEPENDENCIA, '') AS dependencia, hor.N_DIASEMANA diaSemana, "
			+ "hor.N_HORINI horaInicio, hor.N_HORFIN horaFin, convierte_hora(hor.N_HORINI) horaInicioCadena, convierte_hora(hor.N_HORFIN) horaFinCadena, act.L_REQUNIDAD requnidad, "
			+ "act.L_ALUMNOS alumnos, act.X_ACTIVIDAD idActividad, act.T_ABREVIATURA abreviaturaActividad, act.D_ACTIVIDAD descripcionActividad, mog.X_MATERIAOMG idMateria,"
			+ "uat.X_UNIDAD idUnidad, mac.T_ABREV abreviaturaMateria, mac.S_MATERIAC descripcionMateria, dep.T_DEPENDENCIA abreviaturaAula, dep.D_DEPENDENCIA descripcionAula, "
			+ "hor.L_DOCDIRECTA docencia, hor.N_MINCOMHOR minutos, hor.L_CONSOLIDADO consolidado "
			+ "FROM TLHORARIOSR hor, TLDEPENDENCIAS dep, TLACTIVIDADES act, TLUSUARIOS usu, TLUNIAFETRAHOR uat, TLMATOFEMATRG mog, TLMATERIASCURSO mac "
			+ "WHERE hor.X_EMPLEADO = usu.X_EMPLEADO "
			+ "AND usu.USUARIO = :idUsuario "
			+ "AND hor.C_ANNO = :anno "
			+ "AND hor.X_HORARIORE = uat.X_HORARIORE "
			+ "AND uat.X_MATERIAOMG = mog.X_MATERIAOMG "
			+ "AND mog.X_MATERIAC = mac.X_MATERIAC "
			+ "AND hor.X_ACTIVIDAD = act.X_ACTIVIDAD "
			+ "AND hor.X_DEPENDENCIA = dep.X_DEPENDENCIA (+) "
			+ "ORDER BY horaInicio",nativeQuery = true)
	List<HorarioProjection> findAllHorarios(@Param("idUsuario") String idUsuario,  @Param("anno") Integer anno);
	
	@Modifying
	@Query(value= "SELECT DISTINCT "
			+ "    GRU.X_GRUACTPROALU AS idGrupoActividad, "
			+ "    TRA.X_TRAMO AS idTramo, "
			+ "    HOR.X_HORARIORE AS idHorario, "
			+ "    HOR.F_INICIO AS fechaInicio, "
			+ "    HOR.F_FIN AS fechaFin, "
			+ "    DELPHOS_SEGEDU.CONVIERTE_HORA(HOR.N_HORINI) AS horaInicio, "
			+ "    DELPHOS_SEGEDU.CONVIERTE_HORA(HOR.N_HORFIN) AS horaFin, "
			+ "    HOR.N_DIASEMANA AS diaSemana, "
			+ "    GRU.S_GRUACTPROALU AS descripcion, "
			+ "    DEP.D_DEPENDENCIA AS dependencia "
			+ "FROM "
			+ "    DELPHOS_SEGEDU.TLGRUACTPROALU GRU "
			+ "    JOIN DELPHOS_SEGEDU.TLUNIAFEGRUACTPRO GUNI ON GRU.X_GRUACTPROALU = GUNI.X_GRUACTPROALU "
			+ "    JOIN DELPHOS_SEGEDU.TLHORARIOSR HOR ON GRU.X_EMPLEADO = HOR.X_EMPLEADO "
			+ "        AND GRU.F_TOMAPOS = HOR.F_TOMAPOS "
			+ "        AND GRU.C_ANNO = HOR.C_ANNO "
			+ "        AND GRU.X_ACTIVIDAD = HOR.X_ACTIVIDAD "
			+ "    JOIN DELPHOS_SEGEDU.TLUNIAFETRAHOR HUNI ON GUNI.X_UNIDAD = HUNI.X_UNIDAD "
			+ "        AND HUNI.X_HORARIORE = HOR.X_HORARIORE "
			+ "    JOIN DELPHOS_SEGEDU.TLTRAMOSHOR TRA ON HOR.X_TRAMO = TRA.X_TRAMO "
			+ "    JOIN DELPHOS_SEGEDU.TLACTIVIDADES ACT ON GRU.X_ACTIVIDAD = ACT.X_ACTIVIDAD "
			+ "    JOIN DELPHOS.TLCENTROS CEN ON CEN.X_CENTRO = GRU.X_CENTRO "
			+ "    LEFT OUTER JOIN DELPHOS_SEGEDU.TLDEPENDENCIAS DEP ON HOR.X_DEPENDENCIA = DEP.X_DEPENDENCIA "
			+ "WHERE "
			+ "    GRU.X_EMPLEADO = :idEmpleado "
			+ "    AND HOR.C_ANNO = :anno "
			+ "    AND CEN.C_CODIGO = :codCentro "
			+ "    AND NVL(GUNI.X_MATERIAOMG, 0) = NVL(HUNI.X_MATERIAOMG, 0) "
			+ "    AND GRU.F_TOMAPOS IN ( "
			+ "        SELECT PTOTRA.F_TOMAPOS "
			+ "        FROM delphos.TLPTOTRAEMP ptotra "
			+ "        JOIN DELPHOS.TLCURSOACA CURSO ON CURSO.C_ANNO = :anno "
			+ "        WHERE ptotra.X_EMPLEADO = :idEmpleado "
			+ "        AND (ptotra.F_CESE IS NULL OR TRUNC(ptotra.F_CESE) >= SYSDATE) "
			+ "        AND TLF_INTERSECPER(PTOTRA.F_TOMAPOS, PTOTRA.F_CESE, CURSO.F_INICIO, CURSO.F_FINAL) = 1) "
			+ "ORDER BY "
			+ "    diaSemana, "
			+ "    horaInicio DESC",nativeQuery = true)
	List<HorarioGrupoActividadProjection> findAllHorariosGrupoActividad(@Param("idEmpleado") Long idEmpleado,  @Param("anno") Integer anno, @Param("codCentro") Long codCentro);

	@Modifying
	@Query(value= "SELECT hor.X_HORARIORE idHorario, hor.F_INICIO fechaInicio, hor.F_FIN fechaFin, nvl(dep.D_DEPENDENCIA, '') AS dependencia, hor.N_DIASEMANA diaSemana, "
			+ "hor.N_HORINI horaInicio, hor.N_HORFIN horaFin, convierte_hora(hor.N_HORINI) horaInicioCadena, convierte_hora(hor.N_HORFIN) horaFinCadena, act.L_REQUNIDAD requnidad, "
			+ "act.L_ALUMNOS alumnos, act.X_ACTIVIDAD idActividad, act.T_ABREVIATURA abreviaturaActividad, act.D_ACTIVIDAD descripcionActividad, mog.X_MATERIAOMG idMateria, "
			+ "mac.T_ABREV abreviaturaMateria, mac.S_MATERIAC descripcionMateria, dep.T_DEPENDENCIA abreviaturaAula, dep.D_DEPENDENCIA descripcionAula, hor.L_DOCDIRECTA docencia, "
			+ "hor.N_MINCOMHOR minutos, hor.L_CONSOLIDADO consolidado "
			+ "FROM TLHORARIOSR hor, TLDEPENDENCIAS dep, TLACTIVIDADES act, TLUSUARIOS usu, TLUNIAFETRAHOR uat, TLMATOFEMATRG mog, TLMATERIASCURSO mac "
			+ "WHERE hor.X_EMPLEADO = usu.X_EMPLEADO "
			+ "AND usu.USUARIO = :idUsuario "
			+ "AND hor.C_ANNO = :anno "
			+ "AND hor.n_diasemana = :diaSemana "
			+ "AND hor.X_HORARIORE = uat.X_HORARIORE "
			+ "AND uat.X_MATERIAOMG = mog.X_MATERIAOMG "
			+ "AND mog.X_MATERIAC = mac.X_MATERIAC "
			+ "AND hor.X_ACTIVIDAD = act.X_ACTIVIDAD "
			+ "AND hor.X_DEPENDENCIA = dep.X_DEPENDENCIA (+) "
			+ "ORDER BY horaInicio",nativeQuery = true)
	List<HorarioProjection> findAllHorariosByDia(@Param("idUsuario") String idUsuario,  @Param("anno") Integer anno, @Param("diaSemana") Integer diaSemana);
	
	@Modifying
	@Query(value= "SELECT hor.X_EMPLEADO idEmpleado, hor.F_TOMAPOS fechaTomaPosesion, hor.F_INICIO fechaInicio, hor.F_FIN fechaFin, SYSDATE fechaActual "
			+ "FROM TLHORARIOSR hor, TLUSUARIOS usu "
			+ "WHERE hor.X_EMPLEADO = usu.X_EMPLEADO "
			+ "AND usu.USUARIO = :idUsuario "
			+ "AND hor.C_ANNO = :anno "
			+ "AND rownum between 0 and 1 "
			+ "ORDER BY fechaInicio",nativeQuery = true)
	List<AtributosTotalHorasProjection> findAtributosTotalHoras(@Param("idUsuario") String idUsuario,  @Param("anno") Integer anno);
	
	@Modifying
	@Query(value= "SELECT convierte_hora(tlf_horaslecycomdehorario(:anno, :idEmpleado, :fechaTomaPosesion, 'LA', :fechaInicio, :fechaFin, NULL, :fechaActual)) horasLec, "
			+ "convierte_hora(tlf_horaslecycomdehorario(:anno, :idEmpleado, :fechaTomaPosesion, 'LS', :fechaInicio, :fechaFin, NULL, :fechaActual)) horasNoLec, "
			+ "convierte_hora(tlf_horaslecycomdehorario(:anno, :idEmpleado, :fechaTomaPosesion, 'C', :fechaInicio, :fechaFin, NULL, :fechaActual)) horasCompl "
			+ "FROM dual",nativeQuery = true)
	List<TotalHorasProjection> findTotalHoras(@Param("anno") Integer anno, @Param("idEmpleado") Integer idEmpleado, @Param("fechaTomaPosesion") Date fechaTomaPosesion, 
			@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("fechaActual") Date fechaActual);


	@Modifying
	@Query(value= "SELECT"
			+ " EGA.F_EXAGRUACTPRO AS FECHA, "
			+ " 'E/C: ' || EGA.D_EXAGRUACTPRO || ' (' || gap.s_gruactproalu || ')' AS DESCRIPCION,"
			+ " EGA.X_CONVCENTRO CONVOCATORIA,"
			+ " EGA.X_EXAGRUACTPRO IDENTIFICADOR,"
			+ " -1 X_NOTTAR ,"
			+ " -1 X_MATRICULA,"
			+ " 'CONTROL' TIPO,"
			+ " 'S' CON_LINK,"
			+ " GAP.X_GRUACTPROALU"
			+ " FROM"
			+ " DELPHOS_SEGEDU.TLACTIVIDADES ACT, "
			+ " DELPHOS_SEGEDU.TLGRUACTPROALU GAP, "
			+ " DELPHOS_SEGEDU.TLEXAGRUACTPRO EGA"
			+ " WHERE"
			+ " ACT.X_ACTIVIDAD = GAP.X_ACTIVIDAD"
			+ " AND EGA.F_EXAGRUACTPRO >= to_date('16/05/2022 00:00:00', 'dd-mm-yyyy hh24:mi:ss')"
			+ " AND GAP.X_GRUACTPROALU IN (44337672,44337673)"
			+ " AND GAP.X_GRUACTPROALU = EGA.X_GRUACTPROALU"
			+ " UNION"
			+ " SELECT"
			+ " TGAP.F_ENTREGA AS FECHA,"
			+ " 'T: ' || TGAP.D_TARGRUACTPROALU || ' (' || gap.s_gruactproalu || ')' AS DESCRIPCION,"
			+ " TGAP.X_CONVCENTRO CONVOCATORIA,"
			+ " TGAP.X_TARGRUACTPROALU IDENTIFICADOR,"
			+ " -1 X_NOTTAR,"
			+ " -1 X_MATRICULA ,"
			+ " 'TAREA' TIPO,"
			+ " 'S' CON_LINK,"
			+ " GAP.X_GRUACTPROALU "
			+ " FROM"
			+ " DELPHOS_SEGEDU.TLGRUACTPROALU GAP,"
			+ " DELPHOS_SEGEDU.TLTARGRUACTPROALU TGAP"
			+ " WHERE"
			+ " GAP.X_GRUACTPROALU IN(44337672,44337673)"
			+ " AND TGAP.F_ENTREGA >= to_date('16/05/2022 00:00:00', 'dd-mm-yyyy hh24:mi:ss')"
			+ " and GAP.X_GRUACTPROALU = TGAP.X_GRUACTPROALU"
			+ " ORDER BY"
			+ " FECHA,"
			+ " DESCRIPCION",nativeQuery = true)
	List<AtributosPlanificacionSemanalProjection> getPlanificacionSemanal(/*@Param("xGRUACTPROALU") List xGRUACTPROALU -- Comento este parametro ya que no se usa dentro de la consulta */); //14066

	@Modifying
	@Query(value = "SELECT x_anoageperusu idRegistro, "
			+ "f_anoageperusu fechaAnotacion, "
			+ "convierte_hora(n_hora) hora, "
			+ "t_titulo titulo, "
			+ "(f_anoageperusu - TO_DATE(:fechaInicio, 'dd/mm/rrrr')) dia "
			+ "FROM "
			+ "DELPHOS_SEGEDU.tlanoageperusu "
			+ "WHERE "
			+ "x_usuario = :idUsuario "
			+ "AND f_anoageperusu BETWEEN TO_DATE(:fechaInicio, 'dd/mm/rrrr') AND TO_DATE(:fechaFin, 'dd/mm/rrrr') "
			+ "ORDER BY "
			+ "dia, "
			+ "hora", nativeQuery = true)
	List<HorarioPersonalProjection> getHorarioPersonal(@Param("idUsuario") Long idUsuario,
													   @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);
	
	@Modifying
	@Query(value= "SELECT t.X_TRAMO idTramo, "
			+ "t.F_INICIO fechaInicio, "
			+ "t.F_FIN fechaFin, "
			+ "t.N_DIASEMANA diaSemana, "
			+ "convierte_hora(t.N_HORINI) horaInicio, "
			+ "convierte_hora(t.N_HORFIN) horaFin, "
			+ "t2.D_ACTIVIDAD titulo "
			+ "FROM TLHORARIOSR t "
			+ "INNER JOIN TLACTIVIDADES t2 ON t.X_ACTIVIDAD = t2.X_ACTIVIDAD "
			+ "WHERE F_TOMAPOS = to_date(:tomaPos, 'YYYY-MM-DD hh24:mi:ss') "
			+ "AND x_empleado = :xEmpleado "
			+ "ORDER BY diaSemana, fechaInicio, t.N_HORINI"
			,nativeQuery = true)
	List<HorarioDireccionProjection> findActividadesDireccion(@Param("xEmpleado") Long xEmpleado, @Param("tomaPos") String tomaPos);
	
	@Query(value= "SELECT REU.X_CONVREUNION idReunion, OC.D_ORGANO || ' - ' || DEP.D_DEPENDENCIA titulo, DECODE(REU.C_TIPO, 'O', 'Ordinaria', 'E', 'Extraordinaria') tipo, "
			+ "REU.F_REUNION fecha, CONVIERTE_HORA(REU.N_HORA) horaInicio, CONVIERTE_HORA(REU.N_HORA + 120) horaFin "
			+ "FROM TLORGANOSCEN OC, TLTIPOSORG TOR, TLCONVREUNIONES REU, TLDEPENDENCIAS DEP, TLCENTROS CEN "
			+ "WHERE OC.X_TIPORGANO = TOR.X_TIPORGANO "
			+ "AND OC.X_ORGANO = REU.X_ORGANO "
			+ "AND REU.X_DEPENDENCIA = DEP.X_DEPENDENCIA "
			+ "AND OC.X_CENTRO = CEN.X_CENTRO "
			+ "AND OC.X_TIPORGANO <> 1 "
			+ "AND CEN.C_CODIGO = :codCentro "
			+ "AND OC.C_ANNO = :anno "
			+ "UNION ALL "
			+ "SELECT REU.X_CONVREUNION idReunion, OC.D_ORGANO || ' - ' || DEP.D_DEPENDENCIA titulo, DECODE(REU.C_TIPO, 'O', 'Ordinaria', 'E', 'Extraordinaria') tipo, "
			+ "REU.F_REUNION fecha, CONVIERTE_HORA(REU.N_HORA) horaInicio, CONVIERTE_HORA(REU.N_HORA + 120) horaFin "
			+ "FROM TLORGANOSCEN OC, TLTIPOSORG TOR, TLCONVREUNIONES REU, TLDEPENDENCIAS DEP "
			+ "WHERE OC.X_TIPORGANO = TOR.X_TIPORGANO "
			+ "AND OC.X_ORGANO = REU.X_ORGANO "
			+ "AND REU.X_DEPENDENCIA = DEP.X_DEPENDENCIA "
			+ "AND OC.X_ORGANO = (SELECT MAX(X_ORGANO) FROM TLORGANOSCEN ORG, TLCENTROS CEN2 "
			+ "WHERE ORG.X_TIPORGANO = 1 AND CEN2.X_CENTRO = ORG.X_CENTRO AND CEN2.C_CODIGO = :codCentro) "
			+ "ORDER BY fecha, horaInicio",nativeQuery = true)
	List<ReunionOrganoCentroProjection> getReunionesOrganosCentro(@Param("codCentro") Long codCentro, @Param("anno") String anno);
	
	@Modifying
	@Query(value= "SELECT DISTINCT GRU.X_GRUACTPROALU idGrupoActividad, GRU.S_GRUACTPROALU nombre,  GRU.C_GRUACTPROALU abreviatura, UNI.X_UNIDAD idUnidad "
			+ "FROM DELPHOS_SEGEDU.TLGRUACTPROALU GRU, DELPHOS_SEGEDU.TLUNIAFEGRUACTPRO UNI "
			+ "WHERE GRU.X_EMPLEADO = :idEmpleado "
			+ "AND GRU.X_CENTRO = :idCentro "
			+ "AND GRU.C_ANNO = :anno "
			+ "AND GRU.X_GRUACTPROALU  = UNI.X_GRUACTPROALU "
			+ "AND GRU.F_TOMAPOS IN ( "
			+ "		SELECT PTOTRA.F_TOMAPOS "
			+ "		FROM delphos.TLPTOTRAEMP ptotra "
			+ "		JOIN DELPHOS.TLCURSOACA CURSO ON CURSO.C_ANNO = :anno "
			+ "		WHERE ptotra.X_EMPLEADO = :idEmpleado "
			+ "		AND TLF_INTERSECPER(PTOTRA.F_TOMAPOS, PTOTRA.F_CESE, CURSO.F_INICIO, CURSO.F_FINAL) = 1) "
			+ "ORDER BY nombre",nativeQuery = true)
	List<GrupoActividadProjection> findAllGrupoActividadByEmpleadoAnno(@Param("idEmpleado") Long idEmpleado,  @Param("anno") Integer anno, @Param("idCentro") Long idCentro);
	
	@Modifying
	@Query(value= "SELECT DISTINCT GAP.X_GRUACTPROALU idGrupoActividad, GAP.S_GRUACTPROALU nombre "
			+ "FROM DELPHOS_SEGEDU.tlmatalu mua, DELPHOS_SEGEDU.tlgruactproalu gap, DELPHOS_SEGEDU.tluniafegruactpro uag, DELPHOS_SEGEDU.tlmatmatrialu mma "
			+ "WHERE mua.x_centro = gap.x_centro "
			+ "AND mua.c_anno = gap.c_anno "
			+ "AND mua.x_unidad = uag.x_unidad "
			+ "AND mua.x_matricula = mma.x_matricula "
			+ "AND uag.x_materiaomg = mma.x_materiaomg "
			+ "AND gap.x_gruactproalu = uag.x_gruactproalu "
			+ "AND NVL (c_resultado, 99) > DELPHOS_SEGEDU.tlf_parametro('MAT_CANCEL') "
			+ "AND DELPHOS_SEGEDU.tlf_valida_grupo_matricula(gap.x_gruactproalu, mua.x_matricula) = 1 "
			+ "AND GAP.X_EMPLEADO = :idEmpleado "
			+ "AND MUA.X_MATRICULA = :idMatricula "
			+ "AND MUA.C_ANNO = :anno "
			+ "ORDER BY nombre",nativeQuery = true)
	List<GrupoActividadProjection> gruposActividadByEmpleadoAlumnoAnno(@Param("idEmpleado") Long idEmpleado, @Param("idMatricula") Long idMatricula, @Param("anno") Integer anno);

	@Modifying
	@Query(value = 
		  "SELECT VPT.F_VISPROTUTLEG AS FECHA "
		+ ", CONVIERTE_HORA(VPT.N_HORA) AS HORA "
		+ ", EMP.APELLIDO1 || ' ' || EMP.APELLIDO2 || ', ' || EMP.NOMBRE AS PROFESOR "
		+ ", SUBSTR(VPT.T_OBSERVACION, 0, 80) || ' ...' AS OBSERVACION "
		+ ", VPT.X_VISPROTUTLEG "
		+ "FROM DELPHOS_SEGEDU.TLVISPROTUTLEG VPT, DELPHOS_SEGEDU.TLEMPLEADOS EMP "
		+ "WHERE VPT.X_MATRICULA = :idMatricula "
		+ "AND EMP.X_EMPLEADO = VPT.X_EMPLEADO "
		+ "ORDER BY FECHA DESC, HORA DESC, PROFESOR", 
		nativeQuery = true)
	List<VisitasProgramadasProjection> getVisitasProgramadasPorMatricula(@Param("idMatricula") Long idMatricula);


	
}
