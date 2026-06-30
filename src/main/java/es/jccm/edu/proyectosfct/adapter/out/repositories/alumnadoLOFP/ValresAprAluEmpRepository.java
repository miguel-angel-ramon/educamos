package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP;

import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.ValresAprAluEmp;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.QValresAprAluEmp;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection.*;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValresAprAluEmpRepository extends AbstractRepository<ValresAprAluEmp, Long, QValresAprAluEmp> {

    @Query(value = "SELECT distinct alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre AS nombreAlumno,       "
      + "                mat.x_matricula AS xMatricula   "
      + "from tlmatalu mat, tlalumnos alu , fct_convproy_alu cona, fct_conv_proy cony, fct_proyectos proy            "
      + "where mat.x_centro = :idCentro "
      + "and mat.c_anno = :cAnno "
      + "and (:idTutorfctdual = -1 "
      + "       OR proy.id_tutorfctdual = :idTutorfctdual "
      + "       OR proy.id_tutorfctdual in ( "
      + "          select dual2.id_tutorfctdual from fct_tutorfctdual dual, fct_tutorfctdual dual2, tlptotraemp tra where dual.id_tutorfctdual = :idTutorfctdual "
      + "          and tra.x_empleado = dual.x_empleado and tra.x_empleado_sustituye = dual2.x_empleado ) "   
            + "       OR proy.id_tutorfctdual in (select tut2.id_tutorfctdual from fct_tutorfctdual tut1, tlempleados emp1, fct_tutorfctdual tut2, tlptotraemp pto  " 
            + "                    where tut1.id_tutorfctdual = :idTutorfctdual "
            + "                    and emp1.x_empleado = tut1.x_empleado "
            + "                    and tut2.id_tutorfctdual = proy.id_tutorfctdual " 
            + "                    and pto.x_centro = :idCentro "
            + "                    and pto.x_empleado = tut2.x_empleado "
            + "                    and pto.x_empleado_sustituye = emp1.x_empleado) " 
      + "     ) "
      + "and alu.x_alumno = mat.x_alumno "
      + "and cona.x_matricula = mat.x_matricula "
      + "and cony.id_conv_proy = cona.id_conv_proy "
      + "and proy.id_proyecto = cony.id_proyecto "
      + "and proy.lg_lofp = 1 order by nombreAlumno ", nativeQuery = true)
    List<AlumnosEvaluacionProjection> findListadoAlumnosEvaluacionLOFP(Long idTutorfctdual,
                                                                       Long idCentro,
                                                                       Integer cAnno);

    @Query(value = "SELECT DISTINCT " +
            "    moduCur.id_modulo_curso AS moduloId, " +
            "    materia.d_materiac AS moduloNombre, " +
            "    oferta.c_codigomec AS moduloCodigo, " +
            "    oferta.n_ordenpres AS moduloOrden, " +
            "    materiasAlu.x_matmatricula AS moduloMatriculaId, " +
            "    comEsp.x_comesp AS resultadoId, " +
            "    comEsp.t_abrev AS resultadoAbv,  " +
            "    comEsp.d_comesp AS resultadoNombre, " +
            "    comEsp.n_ordenpres AS resultadoOrden, " +            
   "    NVL((select val.id_valresapraluemp  "
   + "       from FCT_VALRESAPRALUEMP val "
   + "       where val.x_matmatricula = materiasAlu.x_matmatricula  "
   + "       AND val.x_comesp = comEsp.x_comesp  "
   + "       AND val.x_empresa = conv.x_empresa),-1) AS evaluacionId,   "
   + "  NVL((select cal.x_califica  "
   + "       from  FCT_VALRESAPRALUEMP val,  "
   + "             TLCALIFICACIONES cal  "
   + "       where val.x_matmatricula = materiasAlu.x_matmatricula  "
   + "       AND val.x_comesp = comEsp.x_comesp  "
   + "       AND val.x_empresa = conv.x_empresa   "
   + "       AND  cal.x_califica = val.x_califica  ),-1) AS calificacionId,   "
   + " (select val.tx_motivacion  "
   + "  from  FCT_VALRESAPRALUEMP val  "
   + "  where val.x_matmatricula = materiasAlu.x_matmatricula  "
   + "  AND val.x_comesp = comEsp.x_comesp  "
   + " AND val.x_empresa = conv.x_empresa) AS motivacion " +            
            " FROM fct_convproy_alu convProyAlu " +
            " JOIN fct_conv_proy convProy ON convProyAlu.id_conv_proy = convProy.id_conv_proy " +
            " JOIN fct_convenios conv ON convProy.id_convenio = conv.id_convenio " +
            " JOIN fct_modulos_empresas moduEmp ON moduEmp.id_conv_proy = convProyAlu.id_conv_proy " +
            " JOIN fct_modulos_cursos moduCur ON moduEmp.id_modulo_curso = moduCur.id_modulo_curso " +
            " JOIN fct_resultadosa_modulos resultadoModulo ON moduCur.id_modulo_curso = resultadoModulo.id_modulo_curso AND resultadoModulo.lg_empresa = 1 " +
            " JOIN tlcomesp comEsp ON comEsp.x_comesp = resultadoModulo.x_comesp " +
            " JOIN tlmatofematrg oferta ON moduCur.x_materiaomg = oferta.x_materiaomg " +
            " JOIN tlmateriascurso materia ON oferta.x_materiac = materia.x_materiac " +
            " JOIN tlmatmatrialu materiasAlu ON materiasAlu.x_matricula = :xMatricula AND materiasAlu.x_materiaomg = oferta.x_materiaomg " +
            " JOIN tlrelcompesmat rel ON rel.x_comesp = comEsp.x_comesp and rel.x_materiaomg = moduCur.x_materiaomg " +
            " WHERE convProyAlu.x_matricula = :xMatricula " +
            "  AND conv.x_empresa = :xEmpresa " +
            " ORDER BY moduloOrden, resultadoOrden ", nativeQuery = true)
    List<ListadoModuloEvaluacionProjection> findListadoModulosEvaluacionLOFP(Long xMatricula, Long xEmpresa);
    
    @Query(value = "SELECT DISTINCT                          "
      + "      moduCur.id_modulo_curso AS moduloId,                          "
      + "      materia.d_materiac AS moduloNombre,                          "
      + "      mog.c_codigomec AS moduloCodigo,                          "
      + "      mog.n_ordenpres AS moduloOrden,                          "
      + "      mmt.x_matmatricula AS moduloMatriculaId,                          "
      + "      comEsp.x_comesp AS resultadoId,                          "
      + "      comEsp.t_abrev AS resultadoAbv,                           "
      + "      comEsp.d_comesp AS resultadoNombre,                          "
      + "      comEsp.n_ordenpres AS resultadoOrden,                                      "
      + "      NVL((select val.id_valresapraluemp              "
      + "           from FCT_VALRESAPRALUEMP val             "
      + "           where val.x_matmatricula = mmt.x_matmatricula              "
      + "           AND val.x_comesp = comEsp.x_comesp              "
      + "           AND val.x_empresa = conv.x_empresa),-1) AS evaluacionId,               "
      + "      NVL((select cal.x_califica              "
      + "           from  FCT_VALRESAPRALUEMP val,              "
      + "              TLCALIFICACIONES cal              "
      + "        where val.x_matmatricula = mmt.x_matmatricula              "
      + "        AND val.x_comesp = comEsp.x_comesp              "
      + "        AND val.x_empresa = conv.x_empresa               "
      + "        AND  cal.x_califica = val.x_califica  ),-1) AS calificacionId,               "
      + "   (select val.tx_motivacion              "
      + "    from  FCT_VALRESAPRALUEMP val              "
      + "    where val.x_matmatricula = mmt.x_matmatricula              "
      + "    AND val.x_comesp = comEsp.x_comesp              "
      + "    AND val.x_empresa = conv.x_empresa) AS motivacion  "
      + "  from  fct_convproy_alu convProyAlu, fct_conv_proy convProy, fct_convenios conv, fct_modulos_empresas moduEmp, fct_modulos_cursos moduCur, fct_resultadosa_modulos resultadoModulo, "
      + "        tlcomesp comEsp, tlmatalu mat, tlofematrgen omg, tlofematrgen omg1, tlmatofematrg mog, tlmateriascurso materia, tlmatmatrialu mmt, tlrelcompesmat rel  "
      + "  where convProyAlu.x_matricula = :xMatricula  "
      + "  AND convProy.id_conv_proy = convProyAlu.id_conv_proy  "
      + "  AND conv.x_empresa = :xEmpresa "
      + "  AND conv.id_convenio = convProy.id_convenio "
      + "  AND moduEmp.id_conv_proy = convProy.id_conv_proy "
      + "  AND moduCur.id_modulo_curso = moduEmp.id_modulo_curso "
      + "  AND moduCur.id_modulo_curso = resultadoModulo.id_modulo_curso "
      + "  AND resultadoModulo.lg_empresa = 1 "
      + "  AND comEsp.x_comesp = resultadoModulo.x_comesp  "
      + "  AND mat.x_matricula = convProyAlu.x_matricula "
      + "  AND omg.x_ofertamatrig != mat.x_ofertamatrig "
      + "  AND omg1.x_modalidad = omg.x_modalidad  "
      + "  AND mog.x_ofertamatrig  = omg1.x_ofertamatrig "
      + "  AND materia.x_materiac = mog.x_materiac  "
      + "  AND mmt.x_matricula = mat.x_matricula  "
      + "  AND mmt.x_materiaomg = mog.x_materiaomg "
      + "  AND rel.x_comesp = comEsp.x_comesp "
      + "  AND rel.x_materiaomg = mmt.x_materiaomg  "
      + "  ORDER BY moduloOrden, resultadoOrden ",nativeQuery = true)
 List<ListadoModuloEvaluacionProjection> findListadoModulosEvaluacionLOFPModalidad(Long xMatricula, Long xEmpresa);

    @Query(value = "SELECT DISTINCT emp.x_empresa AS id, emp.d_empresa AS descripcion "
            + "FROM fct_convproy_alu coya "
            + "JOIN fct_conv_proy cpy ON coya.id_conv_proy = cpy.id_conv_proy "
            + "JOIN fct_convenios conv ON conv.id_convenio = cpy.id_convenio "
            + "JOIN tlempresas emp ON emp.x_empresa = conv.x_empresa "
            + "WHERE coya.x_matricula = :xMatricula "
            + "ORDER BY emp.d_empresa", nativeQuery = true)
    List<ElementoSelectProjection> findEmpresasByMatricula(Long xMatricula);

    @Query(value = "SELECT DISTINCT " +
            "    proy.ds_proyecto AS descripcionPlan, " +
            "    omg.d_ofertamatrig AS curso, " +
            "    tutor.X_EMPLEADO AS xEmpleado, " +
            "    tutor.NOMBRE || ' ' || tutor.APELLIDO1 || ' ' || tutor.APELLIDO2 AS nombreTutorDual, " +
            "    empTutor.ID_EMPLEADO AS idEmpleado, " +
            "    empTutor.tx_nombre || ' ' || empTutor.tx_apellido1 || ' ' || empTutor.tx_apellido2 AS nombreTutorEmpresa, " +
            "    CASE  " +
            "    WHEN cpy.NU_HORAS_TOTALES = 0 THEN (select SUM(NU_HORAS) FROM fct_conv_proyhoraper where id_conv_proy = cpy.id_conv_proy) " + 
            "    ELSE cpy.NU_HORAS_TOTALES END AS numHorasTotalesEmpresa, " +            
            //"    TO_CHAR((select SUM(NU_HORAS) FROM fct_conv_proyhoraper where id_conv_proy = cpy.id_conv_proy)) AS numHorasTotalesCalculadas,  " +            
            "   NVL((SELECT TO_CHAR(SUM(dia.nu_horas))  " +
            "        FROM fct_parsem_aluplan par " +
            "        JOIN fct_pardia_aluplan dia ON dia.id_parsem_aluplan = par.id_parsem_aluplan  " +
            "        JOIN fct_pardia_aluplan_actmod diac ON diac.id_pardia_aluplan = dia.id_pardia_aluplan  " +
            "        WHERE par.id_convproy_alu = coya.id_convproy_alu " +
            "        ),'0')  AS numHorasTotalesCalculadas, " +            
            "    (select n_telefono from tldatoscen where x_centro = mat.x_centro) AS tlfTutorCen, " +                 
            "    emp.n_telefonos AS tlfTutorEmp, " +
            "     (select dep.d_departamento from tlmiedepart mie, tlptotraemp pto, tldepartcen par, tldepdengen dep  " +
            "    where mie.x_empleado = tut.x_empleado " +
            "    and pto.x_empleado = mie.x_empleado " +
            "    and pto.x_centro = mat.x_centro " +
            "    and par.x_centro = pto.x_centro " +
            "    and pto.f_tomapos = mie.f_tomapos  " +
            "    and par.x_departcen = mie.x_departcen " + 
            "    and dep.x_depdengen =  par.x_depdengen                   " +
            "    and rownum <= 1) AS depCen, " +
            "    et.ds_departamento AS depTutorEmp, " +
            "    (select t_correo_corporativo from tldatoscen where x_centro = mat.x_centro) AS correoCen, " +
            "    emp.t_web AS correoEmp " +
            "FROM fct_convproy_alu coya " +
            "JOIN fct_conv_proy cpy ON coya.id_conv_proy = cpy.id_conv_proy " +
            "JOIN fct_proyectos proy ON cpy.id_proyecto = proy.id_proyecto " +
            "JOIN fct_convenios conv ON cpy.id_convenio = conv.id_convenio " +
            "JOIN tlempresas emp ON conv.x_empresa = emp.x_empresa " +
            "JOIN tlmatalu mat ON coya.x_matricula = mat.x_matricula " +
            "JOIN tlofematrgen omg ON mat.x_ofertamatrig = omg.x_ofertamatrig " +
            "LEFT JOIN emp_traemp et ON et.id_traemp = cpy.id_traemp " +
            "LEFT JOIN emp_empleados empTutor ON empTutor.id_empleado = et.id_empleado " +
            "LEFT JOIN fct_tutorfctdual tut ON tut.id_tutorfctdual = proy.id_tutorfctdual " +
            "LEFT JOIN tlempleados tutor ON tutor.x_empleado = tut.x_empleado " +
            "WHERE coya.x_matricula = :xMatricula " +
            "AND emp.x_empresa = :idEmpresa", nativeQuery = true)
    List<DatosGeneralesCabeceraEvaluacionProjection> findDatosGeneralesCabeceraEvaluacion(Long xMatricula, Long idEmpresa);

    @Query(value= "select x_califica AS calificacionId, t_abrev AS calificacionAbv, d_califica AS calificacionDesc " +
            "from tlcalificaciones cal, tlsiscal sis " +
            "where sis.s_sistcal = 'Resultados aprend. Empresa' " +
            "and cal.x_sistcal = sis.x_sistcal " +
            "order by cal.n_orden ", nativeQuery = true)
    List<CalificacionEvaluacionProjection> getListadoCalificacionesEvaluacionLOFP();

    @Query(value= "select DISTINCT DECODE(lg_regimen,0,'GENERAL','INTENSIVO') as regimen,   " +
            "        TO_CHAR(SYSDATE, 'DD \"de\" FMMonth \"de\" YYYY', 'NLS_DATE_LANGUAGE=SPANISH') as fecha,   " +
            "        mat.c_anno || '/' || (mat.c_anno+1)  as academico,   " +
            "        omg.n_orden orden,   " +
            "        omg.d_ofertamatrig as ciclo,   " +
            "         uni.t_nombre grupo,  " +
            "         alu.t_nombre || ' ' || alu.t_apellido1 || ' ' || alu.t_apellido2 nombreAlumno,   " +
            "         cen.c_codigo codigo,   " +
            "         emp.nombre ||' ' || emp.apellido1 || ' ' || emp.apellido2 nombreTutor,   " +
            //"         cproy.nu_horas_totales AS totalHoras,   " +            
            "         CASE  " +
            "         WHEN cproy.nu_horas_totales = 0 THEN (SELECT SUM(NU_HORAS) FROM fct_conv_proyaluhoraper WHERE id_conv_proy = cproy.id_conv_proy AND x_matricula = mat.X_MATRICULA) " + 
            "         ELSE cproy.nu_horas_totales END AS totalHoras,  " +            
            "         empr.D_EMPRESA AS nombreEmpresa,    " +
            "         tutemp.nombretut AS tutorEmpresa,   " +
            //"         TO_CHAR(cproy.FH_INICIO , 'DD/MM/YYYY') || ' - ' || TO_CHAR(cproy.FH_FIN , 'DD/MM/YYYY') AS periodo,  " +            
            "         TO_CHAR((select MIN(FH_INICIO) FROM fct_conv_proyaluhoraper where id_conv_proy = cproy.id_conv_proy and x_matricula = mat.X_MATRICULA),'DD/MM/YYYY') " + 
            "         || ' - ' || " +
            "         TO_CHAR((select MAX(FH_FIN) FROM fct_conv_proyaluhoraper where id_conv_proy = cproy.id_conv_proy and x_matricula = mat.X_MATRICULA),'DD/MM/YYYY') AS periodo, " +
            "         empr.c_numide AS codigoEmpresa,   " +
            "         den.s_denominacion || ' ' || dat.D_ESPECIFICA  AS centro,  " +
            "         loc.d_localidad AS localidad   " +
            "     from fct_convproy_alu cproa,  " +
            "     fct_conv_proy cproy,   " +
            "     fct_proyectos proy,  " +
            "     tlmatalu mat,   " +
            "     tlofematrgen omg,   " +
            "     tlunidadescen uni,   " +
            "     tlalumnos alu,   " +
            "     tlcentros cen,   " +
            "     tldatoscen dat,   " +
            "     tldengen den,   " +
            "     fct_tutorfctdual tut,   " +
            "     tlempleados emp,   " +
            "     tlempresas empr,   " +
            "     tllocalidades loc,  " +
            "     fct_convenios conv,    " +
            "     (select tra.id_traemp,    " +
            "       empi.tx_nombre ||' '||empi.tx_apellido1||' '|| empi.tx_apellido2 nombretut    " +
            "     from emp_traemp tra,     " +
            "          emp_empleados empi    " +
            "     where empi.id_empleado = tra.id_empleado) tutemp   " +
            "where proy.id_proyecto =  cproy.id_proyecto   " +
            "and mat.x_matricula = :idMatricula   " +
            "and cproa.x_matricula = mat.x_matricula   " +
            "and cproa.id_conv_proy = cproy.id_conv_proy   " +
            "and omg.x_ofertamatrig = proy.x_ofertamatrig   " +
            "and uni.x_unidad = mat.x_unidad   " +
            "and alu.x_alumno = mat.x_alumno   " +            
            "and (mat.x_ofertamatrig = omg.x_ofertamatrig " + 
            "     or exists (select 1 from tlofematrgen omg1 where omg1.x_ofertamatrig != omg.x_ofertamatrig and omg1.x_modalidad = omg.x_modalidad)) " +            
            "and cen.x_centro = mat.x_centro   " +
            "and dat.x_centro = cen.x_centro   " +
            "and den.x_dengen = dat.x_dengen   " +
            "and tut.id_tutorfctdual = proy.id_tutorfctdual   " +
            "and emp.x_empleado = tut.x_empleado   " +
            "AND empr.x_empresa = :xEmpresa   " +
            "and tutemp.id_traemp = cproy.id_traemp   " +
            "AND loc.x_localidad = dat.x_localidad   " +
            "AND conv.X_EMPRESA = empr.x_empresa   " +
            "AND conv.ID_CONVENIO = cproy.ID_CONVENIO   " +
            "and rownum=1 ",nativeQuery = true)
    HeaderAnexoIXProjection getHeaderAnexoIXEval(Long xEmpresa, Long idMatricula);

    @Query(value = "SELECT idModuloCurso, codModulo, horasEmpresa "
      + "FROM ( "
      + "    SELECT  "
      + "        modcur.id_modulo_curso AS idModuloCurso, "
      + "        (momg.c_codigomec || ' ' || mcur.d_materiac) AS codModulo, "
      + "        modcur.nu_horas_empresa AS horasEmpresa, "
      + "        ROW_NUMBER() OVER (PARTITION BY (momg.c_codigomec || ' ' || mcur.d_materiac) ORDER BY modcur.id_modulo_curso) AS rn "
      + "    FROM fct_conv_proy cproy, "
      + "         fct_cursos_proyectos cur, "
      + "         fct_convproy_alu cpra, "
      + "         fct_modulos_cursos modcur, "
      + "         fct_modulos_empresas modemp, "
      + "         tlmatofematrg momg, "
      + "         tlmateriascurso mcur, "
      + "         fct_convenios conv "
      + "    WHERE cpra.id_conv_proy = cproy.id_conv_proy "
      + "      AND cur.id_proyecto = cproy.id_proyecto "
      + "      AND cpra.x_matricula = :idMatricula "
      + "      AND cproy.id_convenio = conv.id_convenio "
      + "      AND modcur.id_curso_proyecto = cur.id_curso_proyecto "
      + "      AND modemp.id_modulo_curso = modcur.id_modulo_curso "
      + "      AND modemp.id_conv_proy = cpra.id_conv_proy "
      + "      AND momg.x_materiaomg = modcur.x_materiaomg "
      + "      AND mcur.x_materiac = momg.x_materiac "
      + "      AND conv.X_EMPRESA = :xEmpresa "
      + ") sub "
      + "WHERE rn = 1 "
      + "ORDER BY codModulo ", nativeQuery = true)
    List<TableAnexoIXHeaderProjection> getTableHeaderAnexoIX(Long xEmpresa, Long idMatricula);
    
    @Query(value = "SELECT idModuloCurso, codModulo, horasEmpresa    "
      + "      FROM (        "
      + "           SELECT DISTINCT                               "
      + "            moduCur.id_modulo_curso AS idModuloCurso,    "
      + "               (mog.c_codigomec || ' ' || materia.d_materiac) AS codModulo,    "
      + "               moduCur.nu_horas_empresa AS horasEmpresa,    "
      + "               ROW_NUMBER() OVER (PARTITION BY (mog.c_codigomec || ' ' || materia.d_materiac) ORDER BY moduCur.id_modulo_curso) AS rn       "
      + "           from  fct_convproy_alu convProyAlu, fct_conv_proy convProy, fct_convenios conv, fct_modulos_empresas moduEmp, fct_modulos_cursos moduCur, fct_resultadosa_modulos resultadoModulo,      "
      + "                 tlcomesp comEsp, tlmatalu mat, tlofematrgen omg, tlofematrgen omg1, tlmatofematrg mog, tlmateriascurso materia, tlmatmatrialu mmt, tlrelcompesmat rel       "
      + "           where convProyAlu.x_matricula = :idMatricula       "
      + "           AND convProy.id_conv_proy = convProyAlu.id_conv_proy       "
      + "           AND conv.x_empresa = :xEmpresa      "
      + "           AND conv.id_convenio = convProy.id_convenio      "
      + "           AND moduEmp.id_conv_proy = convProy.id_conv_proy      "
      + "           AND moduCur.id_modulo_curso = moduEmp.id_modulo_curso      "
      + "           AND moduCur.id_modulo_curso = resultadoModulo.id_modulo_curso      "
      + "           AND resultadoModulo.lg_empresa = 1      "
      + "           AND comEsp.x_comesp = resultadoModulo.x_comesp       "
      + "           AND mat.x_matricula = convProyAlu.x_matricula      "
      + "           AND omg.x_ofertamatrig != mat.x_ofertamatrig      "
      + "           AND omg1.x_modalidad = omg.x_modalidad       "
      + "           AND mog.x_ofertamatrig  = omg1.x_ofertamatrig      "
      + "           AND materia.x_materiac = mog.x_materiac       "
      + "           AND mmt.x_matricula = mat.x_matricula       "
   	  + "           AND mmt.x_materiaomg = mog.x_materiaomg      "
      + "           AND rel.x_comesp = comEsp.x_comesp      "
      + "           AND rel.x_materiaomg = mmt.x_materiaomg "
      + "          ) sub    "
      + "       WHERE rn = 1    "
      + "       ORDER BY codModulo ", nativeQuery = true)
      List<TableAnexoIXHeaderProjection> getTableHeaderAnexoIXModalidad(Long xEmpresa, Long idMatricula);

    @Query(value = "SELECT DISTINCT "
      + "       comes.T_ABREV orden,                  "
      + "       (comes.t_abrev || ': ' || comes.d_comesp) AS aprendizaje,                   "
      + "        CASE                   "
      + "       WHEN rel.lg_centro = 0 THEN 'Si'                   "
      + "       ELSE 'No'                   "
      + "       END AS intEmpresa,                    "
      + "       CASE "
      + "       WHEN cal.l_aprueba = 'S' THEN 'Si'                    "
      + "       ELSE 'No'                   "
      + "       END AS aprobada,                    "
      + "       cal.t_abrev AS gradoSuperacion,                     "
      + "      val.TX_MOTIVACION AS motivacion                   "
      + "      FROM fct_resultadosa_modulos rel                    "
      + "      INNER JOIN tlcomesp comes ON comes.x_comesp = rel.x_comesp              "
      + "      INNER JOIN FCT_VALRESAPRALUEMP val ON val.x_empresa = :xEmpresa AND val.x_comesp = comes.x_comesp   "
      + "      INNER JOIN TLMATMATRIALU mat ON mat.X_MATMATRICULA = val.X_MATMATRICULA    "
      + "      INNER JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = val.X_CALIFICA  "
      + "      WHERE rel.id_modulo_curso = :idModulo                    "
      + "       AND mat.x_matricula = :idMatricula  "
      + "       AND rel.lg_empresa = 1 "
      + "     ORDER BY orden ",nativeQuery = true)
    List<TableAnexoIXProjection> getTableAnexoIXEvaluacion(Long idModulo, Long idMatricula, Long xEmpresa);    
    
}
