package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.CentroDto;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.programas.projection.DatosCabeceraAnexoIAlumnadoProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.FamiliaProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ListadoAnexoIAlumnadoProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.Proyectos;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QProyectos;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ListadoProyectosProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ModuloProyectosProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ProyectosRepository extends AbstractRepository<Proyectos, Long, QProyectos> {

 @Query(value = "SELECT ID, " +
         "CASE WHEN LG_LOFP = 1 THEN 'Plan' ELSE DS_TIPO END AS DS_TIPO, " +
         "DS_PROYECTO, " +
         "DECODE(nombresustituto, NULL, DS_TUTOR, DS_TUTOR || nombresustituto) AS DS_TUTOR, " +
         "DS_FAMILIA, " +
         "DS_MODALIDAD, " +
         "NU_HORAS, " +
         "NU_ALUMNOS, " +
         "IDTUTORFCT, " +
         "DS_CURSO, " +
         "LG_LOFP, " +
         "ACTIVIDADES, " +
         "X_OFERTAMATRIG, " +
         "CASE "+
         "     WHEN LG_LOFP = 0 THEN 0 " +
         "     WHEN IDTUTORFCT = :idTutorActual THEN 1 " +
         "     WHEN IDTUTORFCT <> :idTutorActual " +
         "     AND X_OFERTAMATRIG IN ( " +
         "            SELECT DISTINCT cpro_tutor.X_OFERTAMATRIG " +
         "            FROM FCT_CURSOS_PROYECTOS cpro_tutor " +
         "            JOIN FCT_PROYECTOS pro_tutor ON pro_tutor.ID_PROYECTO = cpro_tutor.ID_PROYECTO " +
         "            WHERE pro_tutor.ID_TUTORFCTDUAL = :idTutorActual " +
         "     ) THEN 1 " +
         "     ELSE 0 " +
         "END AS LG_COPIAR " +
         "FROM (SELECT pro.id_proyecto AS ID, " +
         "             tip.ds_nombre AS DS_TIPO, " +
         "             pro.ds_proyecto AS DS_PROYECTO, " +
         "             emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2 AS DS_TUTOR, " +
         "             (SELECT DISTINCT ' (sustituido/a por ' || emp1.nombre || ' ' || emp1.apellido1 || ' ' || emp1.apellido2 || ')' " +
         "              FROM TLPTOTRAEMP pto1, TLEMPLEADOS emp1, tlpuestos put " +
         "              WHERE pto1.x_empleado = emp1.x_empleado " +
         "                AND pto1.x_centro = pro.x_centro " +
         "                AND pto1.x_empleado_sustituye = tut.x_empleado " +
         "                AND (pto1.f_tomapos_sustituye = tut.f_tomapos OR " +
         "                     pto1.f_tomapos_sustituye IN (SELECT f_tomapos FROM TLPTOTRAEMP pto2, tlcursoaca aca " +
         "                                                WHERE pto2.x_empleado = pto1.x_empleado " +
         "                                                  AND pto2.x_centro = pto1.x_centro " +
         "                                                  AND aca.c_anno = :cAnno " +
         "                                                  AND TLF_INTERSECPER(pto2.f_tomaposrea, pto2.f_cese, aca.f_inicio, aca.f_final) = 1)) " +
         "                AND put.l_docente = 'S' " +
         "                AND emp.L_ACTIVO = 'S' " +
         "                AND TRUNC(pto1.F_TOMAPOS) <= SYSDATE " +
         "                AND (pto1.F_CESE IS NULL OR TRUNC(pto1.F_CESE) >= SYSDATE)) AS nombresustituto, " +
         "             fam.d_familia AS DS_FAMILIA, " +
         "             moda.d_modalidad AS DS_MODALIDAD, " +
         "             pro.nu_horas AS NU_HORAS, " +
         "             pro.X_OFERTAMATRIG AS X_OFERTAMATRIG, " +
         "             (SELECT COUNT(distinct mat.x_matricula) " +
         "              FROM FCT_CONV_PROY conv, FCT_CONVPROY_ALU alu, TLMATALU mat " +
         "              WHERE conv.id_proyecto = pro.id_proyecto " +
         "                AND alu.id_conv_proy = conv.id_conv_proy " +
         "                AND mat.x_matricula = alu.x_matricula " +
         "                AND mat.c_anno = :cAnno) AS NU_ALUMNOS, " +
         "             pro.lg_lofp AS LG_LOFP, " +
         "             tut.id_tutorfctdual AS IDTUTORFCT, " +
         "             omg.d_ofertamatrig AS DS_CURSO, " +
         "             (SELECT COUNT(*) " +
         "              FROM fct_actividades_modulos act " +
         "              WHERE act.id_modulo_curso IN (SELECT modu.id_modulo_curso " +
         "                                          FROM fct_modulos_cursos modu " +
         "                                          WHERE modu.id_curso_proyecto IN (SELECT curso.id_curso_proyecto " +
         "                                                                           FROM fct_cursos_proyectos curso " +
         "                                                                           WHERE curso.id_proyecto = pro.id_proyecto))) AS ACTIVIDADES " +
         "      FROM FCT_PROYECTOS pro, " +
         "           FCT_TIPOS_PROYECTOS tip, " +
         "           FCT_TUTORFCTDUAL tut, " +
         "           TLFAMILIAS fam, " +
         "           TLMODALIDADES moda, " +
         "           TLEMPLEADOS emp, " +
         "           tlofematrgen omg " +
         "      WHERE pro.id_tipo_proyecto = tip.id_tipo_proyecto " +
         "        AND pro.id_tutorfctdual = tut.id_tutorfctdual " +
         "        AND pro.x_familia = fam.x_familia " +
         "        AND pro.x_modalidad = moda.x_modalidad " +
         "        AND tut.x_empleado = emp.x_empleado " +
         "        AND pro.x_centro = :idCentro " +
         "        AND omg.x_modalidad(+) = pro.x_modalidad " +
         "        AND omg.x_ofertamatrig(+) = pro.x_ofertamatrig " +
         "        AND (:cAnno = -1 OR :cAnno BETWEEN pro.c_anno_desde AND NVL(pro.c_anno_hasta, 2099)) " +
         "        AND ((:idTipoProyecto = 4 AND pro.lg_lofp = 1) " +
         "             OR (:idTipoProyecto = 1 AND pro.lg_lofp = 0 AND pro.ID_TIPO_PROYECTO = :idTipoProyecto) " +
         "             OR (:idTipoProyecto != 1 AND :idTipoProyecto != 4 AND (:idTipoProyecto = -1 OR pro.ID_TIPO_PROYECTO = :idTipoProyecto))) " +
         "        AND (:idTutor = -1 OR pro.ID_TUTORFCTDUAL = :idTutor) " +
         "        AND (:idFamilia = -1 OR pro.X_FAMILIA = :idFamilia) " +
         "        AND (:idModalidad = -1 OR pro.X_MODALIDAD = :idModalidad) " +
         "        AND (:idCurso = -1 OR pro.x_ofertamatrig = :idCurso) " +
         "      ORDER BY pro.id_proyecto DESC)", nativeQuery = true)
 List<ListadoProyectosProjection> getAllProyectos(Long idCentro, Integer cAnno, Long idTutorActual, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad, Long idCurso);
 
 @Query(value = "select modu.ID_MODULO_CURSO AS ID, "
  + " CASE  "
  + " WHEN (select lg_lofp from fct_proyectos proy where proy.id_proyecto = cur.id_proyecto) = 1 THEN omg.s_ofertamatrig  "
     + " ELSE omg.n_orden || 'º Curso' end curso,          "
     + "momg.c_codigomec codigo, "
     + "omg.x_ofertamatrig idOfertamatrig, "
     + "momg.x_materiaomg idMateriaomg, "     
     + "CASE   "
     + "WHEN (select lg_lofp from fct_proyectos proy where proy.id_proyecto = cur.id_proyecto) = 1 AND  (NOT EXISTS (SELECT 1  "
  + "                                                                                                        FROM tlunicommat unm, tlunicom uni, tlcatcuaprof cua, " 
     + "                                                                                                             tlcuaprofunicom cpuc, tlnivelcp niv, tlfamilias fam " 
  + "                                                                                                        WHERE unm.x_unicom = uni.x_unicom  "
     + "                                                                                                    AND uni.x_unicom = cpuc.x_unicom  "
  + "                                                                                                        AND cpuc.x_cualificacion = cua.x_cualificacion " 
     + "                                                                                                    AND cua.x_nivelcp = niv.x_nivelcp  "
     + "                                                                                                    AND cua.x_familia = fam.x_familia  "
  + "                                                                                                       AND unm.x_materiaomg = momg.x_materiaomg)) "     
     + " THEN mcur.d_materiac || ' (Sin estándares de competencia)' "   
     + " ELSE  mcur.d_materiac "
     + " END modulo, "     
     + "modu.nu_horas_totales horasTotales, "
     + "modu.nu_horas_semanales horasSemanales, "
     + "modu.nu_horas_centro horasCentro, "
     + "modu.nu_horas_empresa horasEmpresa, "
     + "(select count(*) from fct_actividades_modulos where id_modulo_curso = modu.ID_MODULO_CURSO) as actividades, "
     +  "CASE "
     +  "   WHEN EXISTS ( "
     +  "      SELECT 1 FROM fct_modulos_empresas WHERE id_modulo_curso = modu.id_modulo_curso) THEN 0 "
     +  "   ELSE 1 "
     +  "END AS puedeEliminar "
     + "from   FCT_CURSOS_PROYECTOS cur, TLOFEMATRGEN omg, FCT_MODULOS_CURSOS modu, tlmatofematrg momg, tlmateriascurso mcur "
     + "where  cur.id_curso_proyecto = modu.id_curso_proyecto "
     + "and    cur.id_proyecto = ?1 "
     + "and    cur.x_ofertamatrig = omg.x_ofertamatrig "
     + "and    modu.x_materiaomg = momg.x_materiaomg "
     + "and    momg.x_materiac = mcur.x_materiac "
     + "order  by omg.n_orden, mcur.d_acta ", nativeQuery = true)
 List<ModuloProyectosProjection> getModulosProyecto(Long idProyecto);
 
 @Query(value = " select distinct FAM.X_FAMILIA AS id, FAM.D_FAMILIA AS descripcionLarga, FAM.S_FAMILIA AS descripcionCorta   "
    + "from   tlofematrgen omg, tlprogramas pro, tlofegenprog ogp, tlmodalidades moda, tlfamilias fam, "
    + "(select ds_abrev from FCT_TIPOS_PROYECTOS where id_tipo_proyecto = ?3 ) tipo "
    + "where  omg.x_ofertamatrig = ogp.x_ofertamatrig "
    + "and    pro.x_programa = ogp.x_programa "
    + "and    omg.x_modalidad = moda.x_modalidad "
    + "and    moda.x_familia = fam.x_familia "
    + "and    ogp.x_centro = ?1 "
    + "and    pro.x_tipoprograma in (124) "
    + "and    ?2 between omg.c_anno and nvl(omg.c_annotermina,2099) "
    + "and    ?2 between ogp.c_anno and nvl(ogp.c_annohasta,2099) "
    + "and    ((tipo.ds_abrev <> 'B' and pro.l_materia = 'S')  "
    + "or (tipo.ds_abrev = 'B' and pro.l_materia = 'N')) " 
    + "order  by fam.d_familia " , nativeQuery = true)
     List<FamiliaProjection> allFamiliasCentro(Long idCentro, int cAnno, Long idTipo);

 @Query(value = " SELECT DEN.S_DENOMINACION || ' ' || DAT.D_ESPECIFICA || ', ' || MUN.D_MUNICIPIO || ' (' || PRO.D_PROVINCIA || ')' AS CENTRO,      "
   + "         CEN.C_CODIGO AS CODIGO_CENTRO,      "
   + "         EMP.NOMBRE || ' ' || EMP.APELLIDO1 || ' ' || EMP.APELLIDO2 AS NOMBRE_TUTOR,      "
   //+ "         EPR.D_EMPRESA AS CENTRO_TRABAJO,      "
   + "         ((CASE WHEN tip.s_tipovia IS NOT NULL THEN tip.s_tipovia || ' ' ELSE '' END) ||  "
   + "           (CASE WHEN sede.t_domicilio IS NOT NULL THEN sede.t_domicilio || ' ' ELSE '' END) ||  "
   + "           (CASE WHEN sede.t_numero IS NOT NULL THEN 'Nº ' || sede.t_numero || ' ' ELSE '' END) ||  "
   + "           (CASE WHEN sede.t_escalera IS NOT NULL THEN 'Esc. ' || sede.t_escalera || ' ' ELSE '' END) ||  "
   + "           (CASE WHEN sede.t_piso IS NOT NULL THEN 'Piso ' || sede.t_piso || ' ' ELSE '' END) ||  "
   + "           (CASE WHEN sede.t_letra IS NOT NULL THEN 'Letra ' || sede.t_letra ELSE '' END))  "
   + "            || ' ' || MUNSEDE.D_MUNICIPIO AS CENTRO_TRABAJO,  "
   + "         EEMP.TX_NOMBRE || ' ' || EEMP.TX_APELLIDO1 || ' ' || EEMP.TX_APELLIDO2 AS RESPONSABLE,      "
   + "         NVL((select TO_CHAR(MIN(fh_inicio), 'DD/MM/YYYY') "
   + "              from fct_conv_proyaluhoraper  "
   + "              where id_conv_proy = :idConvProy "
   + "              and x_matricula = :idMatricula),TO_CHAR(CPY.FH_INICIO, 'DD/MM/YYYY')) || ' - ' || " 
   + "         NVL((select TO_CHAR(MAX(fh_fin), 'DD/MM/YYYY') "
   + "              from fct_conv_proyaluhoraper  "
   + "              where id_conv_proy = :idConvProy "
   + "              and x_matricula = :idMatricula),TO_CHAR(CPY.FH_FIN, 'DD/MM/YYYY')) AS PERIODO, " 
   + "         PROY.DS_PROYECTO AS DESCRIPCION,      "
   + "         FAM.D_FAMILIA AS FAMILIA,      "
   + "         moda.D_MODALIDAD AS CURSO,      "
   + "         TRAP.DS_DEPARTAMENTO AS AREA,      "
   + "         PROY.NU_HORAS AS HORAS,      "
   + "          CONY.ID_CONVENIO AS IDCONVENIO,      "
   + "          loc.d_localidad AS LOCALIDAD      "
   + "        FROM FCT_CONV_PROY CPY, "
   + "             FCT_PROYECTOS PROY,      "
   + "             TLCENTROS CEN,      "
   + "         TLDATOSCEN DAT,      "
   + "         TLDENGEN DEN,      "
   + "         TLPROVINCIAS PRO,      "
   + "         TLMUNICIPIOS MUN,      "
   + "         FCT_TUTORFCTDUAL TUT,      "
   + "         TLEMPLEADOS EMP,      "
   + "         FCT_CONV_PROY CONY,      "
   + "         EMP_TRAEMP TRAP,      "
   + "         TLEMPRESAS EPR,      "
   + "         EMP_EMPLEADOS EEMP,      "
   + "         TLMODALIDADES MODA,      "
   + "         TLFAMILIAS FAM,      "
   + "         TLLOCALIDADES loc, "
   + "         FCT_CONVENIOS CONV,"
   + "         EMP_SEDEMP SEDE,   "
   + "         TLTIPOVIAS tip,  "
   + "         TLMUNICIPIOS MUNSEDE    "
   + "       WHERE CPY.ID_CONV_PROY = :idConvProy "
   + "         AND PROY.ID_PROYECTO  = CPY.ID_PROYECTO "
   + "         AND CONV.ID_CONVENIO = CPY.ID_CONVENIO  "
   + "         AND EPR.X_EMPRESA = CONV.X_EMPRESA      "
   + "         AND PROY.X_CENTRO = CEN.X_CENTRO      "
   + "         AND PROY.X_CENTRO = DAT.X_CENTRO      "
   + "         AND DEN.X_DENGEN = DAT.X_DENGEN      "
   + "         AND PRO.C_PROVINCIA = DAT.C_PROVINCIA      "
   + "         AND MUN.C_PROVINCIA = PRO.C_PROVINCIA      "
   + "         AND MUN.C_MUNICIPIO = DAT.C_MUNICIPIO      "
   + "         AND TUT.ID_TUTORFCTDUAL = PROY.ID_TUTORFCTDUAL      "
   + "         AND TUT.X_EMPLEADO = EMP.X_EMPLEADO      "
   + "         AND PROY.ID_PROYECTO = CONY.ID_PROYECTO       "
   + "         AND CONY.ID_TRAEMP = TRAP.ID_TRAEMP       "
   + "         AND TRAP.X_EMPRESA = EPR.X_EMPRESA      "
   + "         AND EEMP.ID_EMPLEADO = TRAP.ID_EMPLEADO      "
   + "         AND PROY.X_MODALIDAD = MODA.X_MODALIDAD      "
   + "         AND FAM.X_FAMILIA = MODA.X_FAMILIA      "
   + "         AND loc.x_localidad = dat.x_localidad     "
   + "         AND CONY.ID_SEDE_RESP = SEDE.ID_SEDEMP          "
   + "         AND SEDE.C_PROVINCIA = MUNSEDE.C_PROVINCIA(+)          "
   + "         AND SEDE.C_MUNICIPIO = MUNSEDE.C_MUNICIPIO(+)          "
   + "         AND tip.x_tipovia =  SEDE.x_tipovia          "
   + "         AND rownum <=1  " , nativeQuery = true)
 DatosCabeceraEvaluacionProjection getDatosCabeceraFCT(Long idConvProy, Long idMatricula);

 @Query(value = " SELECT COV.CD_CONVENIO AS NUMCONV,        "
   + "               EXTRACT(DAY FROM NVL(COV.F_FIRMA,F_INICIO)) AS DIA,          "
   + "               CASE TO_CHAR(NVL(COV.F_FIRMA,F_INICIO),'MM')            "
   + "               WHEN '01' THEN 'Enero '              "
   + "               WHEN '02' THEN 'Febrero '              "
   + "               WHEN '03' THEN 'Marzo '               "
   + "               WHEN '04' THEN 'Abril '               "
   + "               WHEN '05' THEN 'Mayo '               "
   + "               WHEN '06' THEN 'Junio '              "
   + "               WHEN '07' THEN 'Julio '              "
   + "               WHEN '08' THEN 'Agosto '              "
   + "               WHEN '09' THEN 'Septiembre '              "
   + "               WHEN '10' THEN 'Octubre '              "
   + "               WHEN '11' THEN 'Noviembre '             "
   + "               ELSE 'Diciembre 'END  AS MES,          "
   + "               EXTRACT(YEAR FROM NVL(COV.F_FIRMA,F_INICIO)) AS ANNO,         "
   + "               DEN.S_DENOMINACION || ' ' || DAT.D_ESPECIFICA || ', ' || MUN.D_MUNICIPIO || ' (' || PRO.D_PROVINCIA || ')' AS CENTRO,        "
   + "               EMP.D_EMPRESA AS EMPRESA,        "
   + "               ((CASE WHEN tip.s_tipovia IS NOT NULL THEN tip.s_tipovia || ' ' ELSE '' END) ||  "
   + "               (CASE WHEN sede.t_domicilio IS NOT NULL THEN sede.t_domicilio || ' ' ELSE '' END) ||  "
   + "               (CASE WHEN sede.t_numero IS NOT NULL THEN 'Nº ' || sede.t_numero || ' ' ELSE '' END) ||  "
   + "               (CASE WHEN sede.t_escalera IS NOT NULL THEN 'Esc. ' || sede.t_escalera || ' ' ELSE '' END) ||  "
   + "               (CASE WHEN sede.t_piso IS NOT NULL THEN 'Piso ' || sede.t_piso || ' ' ELSE '' END) ||  "
   + "               (CASE WHEN sede.t_letra IS NOT NULL THEN 'Letra ' || sede.t_letra ELSE '' END)  "
   + "                ) || ', ' || MUNSEDE.D_MUNICIPIO AS DIRECCION,        "
   + "               MODA.D_MODALIDAD  AS CURSO,        "
   + "               (SELECT C_ANNO || '/' || (C_ANNO+1) FROM TLCURSOACA WHERE L_ACTUAL = 'S') AS cursoAcademico,        "
   + "               TLF_NOMBRE(epltut.nombre, epltut.APELLIDO1 , epltut.APELLIDO2) AS tutor,        "
   + "               TLF_NOMBRE(eplrep.TX_NOMBRE, eplrep.TX_APELLIDO1, eplrep.TX_APELLIDO2) AS representante,        "
   + "                TLF_NOMBRE(eplrep1.TX_NOMBRE, eplrep1.TX_APELLIDO1, eplrep1.TX_APELLIDO2) AS responsable,        "
   + "                   COP.ID_CONVENIO AS IDCONVENIO,        "
   + "                   loc.d_localidad AS LOCALIDAD,        "
   + "                   cen.x_centro AS IDCENTRO,        "
   + "                   EMP.C_NUMIDE AS cif,        "
   + "                   COP.ID_PROYECTO as idProyecto,        "
   + "                   FAM.S_FAMILIA  AS familia,         "
   + "                   SEDE.T_CORREO AS email,         "
   + "                   (select distinct decode(omg.n_orden,1,'S','N') primero from FCT_CONV_PROY cop, fct_convproy_alu coa, tlmatalu mat, tlofematrgen omg        "
   + "                                    where cop.id_convenio = COV.id_convenio        "
   + "                                    and coa.id_conv_proy = cop.id_conv_proy        "
   + "                                    and mat.x_matricula = coa.x_matricula        "
   + "                                    and omg.x_ofertamatrig = mat.x_ofertamatrig  "
   + "                                    and coa.id_conv_proy = :idConvProy) as primero,        "
   + "                    epltut.C_NUMIDE AS dniTutor,        "
   + "                    eplrep1.C_NUMIDE AS dniRep,        "
   + "                    COP.CD_PROYECTO AS codigo,        "
   + "                    sdemp.n_telefono AS telefono        "
   + "               FROM FCT_CONV_PROY COP,        "
   + "             FCT_CONVENIOS COV,        "
   + "             TLCENTROS CEN,         "
   + "           TLDATOSCEN DAT,         "
   + "           TLDENGEN DEN,         "
   + "           TLPROVINCIAS PRO,         "
   + "           TLMUNICIPIOS MUN,         "
   + "           TLEMPRESAS EMP,        "
   + "           EMP_SEDEMP SEDE,        "
   + "           TLMUNICIPIOS MUNSEDE,        "
   + "           FCT_PROYECTOS PROY,        "
   + "           TLMODALIDADES MODA,        "
   + "           FCT_TUTORFCTDUAL tut,        "
   + "           TLEMPLEADOS epltut,        "
   + "           EMP_TRAEMP temp,        "
   + "           EMP_EMPLEADOS eplrep,        "
   + "            EMP_TRAEMP temp1,        "
   + "            EMP_EMPLEADOS eplrep1,        "
   + "               TLLOCALIDADES loc,        "
   + "            TLFAMILIAS FAM,       "
   + "            EMP_SEDEMP sdemp,"
   + "            TLTIPOVIAS tip       "
   + "         WHERE COP.ID_CONV_PROY = :idConvProy            "
   + "           AND COV.X_CENTRO = CEN.X_CENTRO        "
   + "           AND COP.ID_CONVENIO = COV.ID_CONVENIO        "
   + "           AND COP.ID_TRAEMP = temp1.ID_TRAEMP       "
   + "           AND CEN.X_CENTRO = DAT.X_CENTRO         "
   + "           AND DAT.X_DENGEN = DEN.X_DENGEN        "
   + "           AND PRO.C_PROVINCIA = DAT.C_PROVINCIA        "
   + "           AND MUN.C_PROVINCIA = DAT.C_PROVINCIA        "
   + "           AND MUN.C_MUNICIPIO = DAT.C_MUNICIPIO        "
   + "           AND EMP.X_EMPRESA = COV.X_EMPRESA        "
   + "           AND EMP.X_EMPRESA = SEDE.X_EMPRESA        "
//   + "           AND COV.ID_SEDEMP = SEDE.ID_SEDEMP       "
   + "           AND COP.ID_SEDE = SEDE.ID_SEDEMP "
   + "           AND SEDE.C_PROVINCIA = MUNSEDE.C_PROVINCIA(+)         "
   + "           AND SEDE.C_MUNICIPIO = MUNSEDE.C_MUNICIPIO(+)          "
   + "           AND COP.ID_PROYECTO  = PROY.ID_PROYECTO         "
   + "           AND PROY.X_MODALIDAD  = MODA.X_MODALIDAD         "
   + "           AND PROY.ID_TUTORFCTDUAL = tut.ID_TUTORFCTDUAL         "
   + "           AND tut.X_EMPLEADO = epltut.X_EMPLEADO        "
   + "           AND cop.ID_REPRESENTANTE = temp.ID_TRAEMP         "
   + "           AND temp.ID_EMPLEADO = eplrep.ID_EMPLEADO       "
   + "           AND loc.x_localidad = dat.x_localidad        "
   + "           and temp1.id_traemp = COP.id_traemp        "
   + "           and eplrep1.id_empleado = temp1.id_empleado        "
   + "           AND FAM.X_FAMILIA = MODA.X_FAMILIA         "
   + "           AND sdemp.x_empresa = temp1.x_empresa    "
   + "           AND tip.x_tipovia =  SEDE.x_tipovia         "
   + "           and rownum <=1 " , nativeQuery = true)
 DatosCabeceraAnexoIAlumnadoProjection getDatosCabeceraAnexoIFCT(Long idConvProy);

 @Query(value = "SELECT TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) AS nombre, "
   + "   alu.C_NUMIDE AS numide, "
    + "  (select d_localidad from tllocalidades where x_localidad = alu.x_localidad_reside) AS residencia, "
    + "  (SELECT LISTAGG(tramos,'\n') WITHIN GROUP (ORDER BY fh_inicio) FROM    "
    + "       (select '(' || TO_CHAR(per.fh_inicio,'dd/MM/yyyy') || '-' || TO_CHAR(per.fh_fin,'dd/MM/yyyy') || ') ' || LISTAGG(decode(tra.n_diasemana,1,'L',2,'M','3','X',4,'J',5,'V',6,'S','D'), '-') || ' (' || convierte_hora(n_horini) || '/' || convierte_hora(n_horfin)  || ')' AS tramos "  
       + "        from fct_conv_proyaluhoraper per,    "
       + "             fct_conv_proyaluhoratra tra   "
       + "        where per.id_conv_proy = cop.id_conv_proy "   
       + "        and per.x_matricula= mat.X_MATRICULA   "
       + "        and tra.id_conv_proyaluhoraper = per.id_conv_proyaluhoraper "  
       + "        GROUP BY per.id_conv_proyaluhoraper,per.fh_inicio, per.fh_fin, n_horini, n_horfin)) AS franjas, " 
    + "     CASE WHEN cop.nu_horas_totales = 0 THEN (SELECT SUM(NU_HORAS) FROM fct_conv_proyaluhoraper WHERE id_conv_proy = cop.id_conv_proy AND x_matricula = mat.X_MATRICULA) ELSE cop.nu_horas_totales END AS numHoras,   "
    + "    TO_CHAR((select MIN(FH_INICIO) FROM fct_conv_proyaluhoraper where id_conv_proy = cop.id_conv_proy and x_matricula = mat.X_MATRICULA),'DD/MM/RRRR')  AS fechaIni, "  
    + "    TO_CHAR((select MAX(FH_FIN) FROM fct_conv_proyaluhoraper where id_conv_proy = cop.id_conv_proy and x_matricula = mat.X_MATRICULA),'DD/MM/RRRR') AS fechaFin, "
    + "    (select COUNT( distinct tra.n_diasemana) " 
    + "     from fct_conv_proyaluhoratra tra, fct_conv_proyaluhoraper per "     
    + "     where per.ID_CONV_PROY = cop.id_conv_proy       "
    + "     and tra.id_conv_proyaluhoraper =  per.id_conv_proyaluhoraper "
    + "     and per.x_matricula = mat.X_MATRICULA)  as numdias "    
    /*+ "    (select MAX(dias) from ( "
    + "       (select  per.id_conv_proyaluhoraper , COUNT(tra.n_diasemana) dias     "                                                                                   
    + "        from fct_conv_proyaluhoratra tra, fct_conv_proyaluhoraper per     " 
    + "        where per.ID_CONV_PROY = cop.id_conv_proy      "
    + "        and tra.id_conv_proyaluhoraper =  per.id_conv_proyaluhoraper     " 
    + "        and per.x_matricula = mat.X_MATRICULA "
            + "        and tra.n_tramo = (select n_tramo FROM ( "
            + "        select count(n_diasemana) dias,n_tramo, MAX(n_diasemana) maximo " 
            + "        from fct_conv_proyaluhoratra where id_conv_proyaluhoraper = tra.id_conv_proyaluhoraper group by n_tramo) "
            + "        where dias = maximo "
            + "        and ROWNUM = 1) "
            + "     group by per.id_conv_proyaluhoraper ))) as numdias " */
   + "  FROM FCT_CONV_PROY cop, "
   + "       FCT_CONVPROY_ALU coalu, "
   + "       TLMATALU mat, "
   + "       TLALUMNOS alu "
   + " WHERE cop.id_conv_proy = :idConvProy "
   + "   AND coalu.ID_CONV_PROY = cop.ID_CONV_PROY "
   + "   AND mat.X_MATRICULA = COALU.X_MATRICULA "
   + "   AND alu.X_ALUMNO = mat.X_ALUMNO "
   + "  ORDER BY ALU.T_APELLIDO1, ALU.T_APELLIDO2, ALU.T_NOMBRE" , nativeQuery = true)
 List<ListadoAnexoIAlumnadoProjection> getListadoAlumnadoAnexoI(Long idConvProy);

 @Query(value = "SELECT ID_PROYECTO, ID_TIPO_PROYECTO, "
   + "   DS_PROYECTO, ID_TUTORFCTDUAL, "
   + "   X_CENTRO, X_FAMILIA, "
   + "   X_MODALIDAD , C_ANNO_DESDE, "
   + "   C_ANNO_HASTA, NU_HORAS, "
   + "   NU_ALUMNOS, C_USUCREACION, "
   + "   F_CREACION, C_USUACTUALIZA, "
   + "   F_ACTUALIZA, LG_LOFP, X_OFERTAMATRIG, LG_IDIARIO, LG_ISEMANAL, LG_IMENSUAL, LG_IOTROS, LG_IVARIAS_EMPRESAS, LG_REGIMEN "
   + " FROM FCT_PROYECTOS pro "
   + " WHERE pro.X_CENTRO = :idCentro "
   + " AND (:cAnno = -1 OR :cAnno BETWEEN pro.c_anno_desde AND nvl(pro.c_anno_hasta, 2099)) "
   + " AND ( (:idTipoProyecto = 4 AND pro.lg_lofp = 1) "
   + "    OR ( :idTipoProyecto = 1 AND pro.lg_lofp = 0 AND pro.ID_TIPO_PROYECTO = :idTipoProyecto) "
   + "    OR (:idTipoProyecto != 1 AND :idTipoProyecto != 4 AND (:idTipoProyecto = -1 OR pro.ID_TIPO_PROYECTO = :idTipoProyecto) ) ) "
   + " AND (:idTutor = -1 "
   + "  OR pro.ID_TUTORFCTDUAL = :idTutor) "
   + " AND (:idFamilia = -1 "
   + "  OR pro.X_FAMILIA = :idFamilia) "
   + " AND (:idModalidad =-1 "
   + "  OR pro.X_MODALIDAD = :idModalidad)" , nativeQuery = true)
 List<Proyectos> getAllProyectosByIdCentroAndCanno(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad);

 @Query(value = " SELECT distinct dcen1.x_centro id , tlf_datoscentro(dcen1.x_centro) descripcion  "
   + "   from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , "
   + "   TLDATOSCEN dcen1, TLCENTROS cen, FCT_PROYECTOS pro  "
   + "   WHERE u.x_usuario = :idUsuario "
   + "   AND pop.x_perfil = :idPerfil "
   + "   AND pto.x_centro = :idCentro "
   + "   AND pto.x_empleado=u.x_empleado "
   + "   AND pop.x_empleado=pto.x_empleado "
   + "   AND pop.f_tomapos = pto.f_tomapos "
   + "   AND pto.x_centro = dcen.x_centro "
   + "   AND dcen.c_provincia = prv.c_provincia  "
   + "   AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
   + "   AND dcen1.c_provincia = prv.c_provincia "
   + "   AND dcen1.l_vigente = 'S' "
   + "   AND cen.x_centro = dcen1.x_centro "
   + "   AND cen.l_delegacion = 'N' "
   + "   AND cen.l_extranjero = 'N' "
   + "   AND cen.x_centro = pro.x_centro order by descripcion ", nativeQuery = true)
 List<ElementoSelectProjection> allCentroDelegacion(Long idUsuario, Long idPerfil, Long idCentro);
 
  @Query(value = " SELECT distinct dcen.x_centro id , tlf_datoscentro(dcen.x_centro) descripcion  "
      + "      from  TLDATOSCEN dcen, TLPROVINCIAS prv , "
      + "            TLCENTROS cen, FCT_PROYECTOS pro  "
      + "      WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia)"
      + "      AND dcen.c_provincia = prv.c_provincia  "
      + "      AND dcen.l_vigente = 'S' "
      + "      AND cen.x_centro = dcen.x_centro "
      + "      AND cen.l_delegacion = 'N' "
      + "      AND cen.l_extranjero = 'N' "
      + "      AND cen.x_centro = pro.x_centro order by descripcion ", nativeQuery = true)
 List<ElementoSelectProjection> allCentroDelegacionProvincias(Long idProvincia);

 @Query(value = " select distinct tut.id_tutorfctdual id , emp.nombre || ' ' || emp.apellido1 || '  ' || emp.apellido2  descripcion  "
  + "   from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , "
  + "        TLDATOSCEN dcen1, TLCENTROS cen, FCT_PROYECTOS pro , FCT_TUTORFCTDUAL tut, TLEMPLEADOS emp   "
  + "   where u.x_usuario = :idUsuario"
  + "   and pop.x_perfil = :idPerfil "
  + "   and pto.x_centro = :idCentroProvincia"
  + "   and (:idCentro = -1 OR pro.x_centro = :idCentro)"
  + "   and (:idTipo = -1 OR (:idTipo != 4 AND pro.id_tipo_proyecto = :idTipo) OR (:idTipo = 4 AND pro.lg_lofp = 1)) "
  + "   and pto.x_empleado=u.x_empleado "
  + "   and pop.x_empleado=pto.x_empleado "
  + "   and pop.f_tomapos = pto.f_tomapos "
  + "   and pto.x_centro = dcen.x_centro "
  + "   and dcen.c_provincia = prv.c_provincia    "
  + "   and (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)"
  + "   and dcen1.c_provincia = prv.c_provincia"
  + "   and dcen1.l_vigente = 'S'"
  + "   and cen.x_centro = dcen1.x_centro "
  + "   and cen.l_delegacion = 'N' "
  + "   and cen.l_extranjero = 'N'   "
  + "   and cen.x_centro = pro.x_centro"
  + "   and tut.id_tutorfctdual = pro.id_tutorfctdual"
  + "   and (:idAnno = -1 OR :idAnno between pro.c_anno_desde and nvl(pro.c_anno_hasta,2099)) "
  + "   and emp.x_empleado = tut.x_empleado"
  + "   order by descripcion ", nativeQuery = true)
    List<ElementoSelectProjection> allTutoresDelegacion(Long idUsuario, 
                    Long idPerfil, 
                    Long idCentro,
                    Long idCentroProvincia,
                    Integer idAnno,
                    Long idTipo);
 
  @Query(value = " select distinct tut.id_tutorfctdual id , emp.nombre || ' ' || emp.apellido1 || '  ' || emp.apellido2  descripcion  "
      + "      from TLDATOSCEN dcen, TLPROVINCIAS prv , "
      + "           TLCENTROS cen, FCT_PROYECTOS pro , FCT_TUTORFCTDUAL tut, TLEMPLEADOS emp   "
      + "      where  (:idCentro = -1 OR pro.x_centro = :idCentro)"
      + "      and (:idTipo = -1 OR (:idTipo != 4 AND pro.id_tipo_proyecto = :idTipo) OR (:idTipo = 4 AND pro.lg_lofp = 1))"
      + "               and (:idProvincia = -1 OR dcen.c_provincia = :idProvincia)"
      + "      and dcen.c_provincia = prv.c_provincia           "
      + "      and dcen.l_vigente = 'S'"
      + "      and cen.x_centro = dcen.x_centro "
      + "      and cen.l_delegacion = 'N' "
      + "      and cen.l_extranjero = 'N'   "
      + "      and cen.x_centro = pro.x_centro"
      + "      and tut.id_tutorfctdual = pro.id_tutorfctdual"
      + "      and (:idAnno = -1 OR :idAnno between pro.c_anno_desde and nvl(pro.c_anno_hasta,2099)) "
      + "      and emp.x_empleado = tut.x_empleado"
      + "      order by descripcion ", nativeQuery = true)
     List<ElementoSelectProjection> allTutoresDelegacionProvincias(Long idCentro,
                               Integer idAnno,
                               Long idTipo,
                               Long idProvincia);

 @Query(value = " select distinct fam.x_familia id , fam.d_familia AS  descripcion  "
  + "   from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , "
  + "        TLDATOSCEN dcen1, TLCENTROS cen, FCT_PROYECTOS pro , FCT_TUTORFCTDUAL tut, TLEMPLEADOS emp, TLFAMILIAS fam   "
  + "   where u.x_usuario = :idUsuario"
  + "   and pop.x_perfil = :idPerfil "
  + "   and pto.x_centro = :idCentroProvincia"
  + "   and (:idCentro = -1 OR pro.x_centro = :idCentro)"
  + "   and (:idTutor = -1 OR tut.id_tutorfctdual = :idTutor)"
  + "   and (:idTipo = -1 OR (:idTipo != 4 AND pro.id_tipo_proyecto = :idTipo) OR (:idTipo = 4 AND pro.lg_lofp = 1)) "
  + "   and pto.x_empleado=u.x_empleado "
  + "   and pop.x_empleado=pto.x_empleado "
  + "   and pop.f_tomapos = pto.f_tomapos "
  + "   and pto.x_centro = dcen.x_centro "
  + "   and dcen.c_provincia = prv.c_provincia    "
  + "   and (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)"
  + "   and dcen1.c_provincia = prv.c_provincia"
  + "   and dcen1.l_vigente = 'S'"
  + "   and cen.x_centro = dcen1.x_centro "
  + "   and cen.l_delegacion = 'N' "
  + "   and cen.l_extranjero = 'N'   "
  + "   and cen.x_centro = pro.x_centro"
  + "   and tut.id_tutorfctdual = pro.id_tutorfctdual"
  + "   and emp.x_empleado = tut.x_empleado"
  + "   and fam.x_familia = pro.x_familia "
  + "   and (:idAnno = -1 OR :idAnno between pro.c_anno_desde and nvl(pro.c_anno_hasta,2099)) "
  + "   order by descripcion ", nativeQuery = true)
     List<ElementoSelectProjection> allFamiliasDelegacion(Long idUsuario, 
                   Long idPerfil,  
                   Long idCentro,
                   Long idCentroProvincia, 
                   Integer idAnno,
                   Long idTipo,
                   Long idTutor);
 
  @Query(value = " select distinct fam.x_familia id , fam.d_familia AS  descripcion  "
      + "      from TLDATOSCEN dcen, TLPROVINCIAS prv , "
      + "           TLCENTROS cen, FCT_PROYECTOS pro , FCT_TUTORFCTDUAL tut, TLEMPLEADOS emp, TLFAMILIAS fam   "
      + "      where (:idCentro = -1 OR pro.x_centro = :idCentro)"
      + "      and (:idTutor = -1 OR tut.id_tutorfctdual = :idTutor)"
      + "      and (:idTipo = -1 OR (:idTipo != 4 AND pro.id_tipo_proyecto = :idTipo) OR (:idTipo = 4 AND pro.lg_lofp = 1)) "
      + "               and (:idProvincia = -1 OR dcen.c_provincia = :idProvincia)"
      + "      and dcen.c_provincia = prv.c_provincia    "
      + "      and dcen.l_vigente = 'S'"
      + "      and cen.x_centro = dcen.x_centro "
      + "      and cen.l_delegacion = 'N' "
      + "      and cen.l_extranjero = 'N'   "
      + "      and cen.x_centro = pro.x_centro"
      + "      and tut.id_tutorfctdual = pro.id_tutorfctdual"
      + "      and emp.x_empleado = tut.x_empleado"
      + "      and fam.x_familia = pro.x_familia "
      + "      and (:idAnno = -1 OR :idAnno between pro.c_anno_desde and nvl(pro.c_anno_hasta,2099)) "
      + "      order by descripcion ", nativeQuery = true)
      List<ElementoSelectProjection> allFamiliasDelegacionProvincias(Long idCentro,                         
                        Integer idAnno,
                        Long idTipo,
                        Long idTutor,
                        Long idProvincia);
 
 
 
 @Query(value = " select distinct mod.x_modalidad id , mod.d_modalidad AS  descripcion  "
  + "   from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , "
  + "        TLDATOSCEN dcen1, TLCENTROS cen, FCT_PROYECTOS pro , FCT_TUTORFCTDUAL tut, TLEMPLEADOS emp, TLFAMILIAS fam , TLMODALIDADES mod   "
  + "   where u.x_usuario = :idUsuario"
  + "   and pop.x_perfil = :idPerfil "
  + "   and pto.x_centro = :idCentroProvincia"
  + "   and (:idCentro = -1 OR pro.x_centro = :idCentro)"
  + "   and (:idTutor = -1 OR tut.id_tutorfctdual = :idTutor)"
  + "   and (:idTipo = -1 OR (:idTipo != 4 AND pro.id_tipo_proyecto = :idTipo) OR (:idTipo = 4 AND pro.lg_lofp = 1)) "
  + "   and (:idFamilia = -1 OR fam.x_familia = :idFamilia)"
  + "   and pro.x_familia = fam.x_familia  "
  + "   and mod.x_familia=fam.x_familia "
  + "   and pto.x_empleado=u.x_empleado "
  + "   and pop.x_empleado=pto.x_empleado "
  + "   and pop.f_tomapos = pto.f_tomapos "
  + "   and pto.x_centro = dcen.x_centro "
  + "   and dcen.c_provincia = prv.c_provincia    "
  + "   and (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)"
  + "   and dcen1.c_provincia = prv.c_provincia"
  + "   and dcen1.l_vigente = 'S'"
  + "   and cen.x_centro = dcen1.x_centro "
  + "   and cen.l_delegacion = 'N' "
  + "   and cen.l_extranjero = 'N'   "
  + "   and cen.x_centro = pro.x_centro"
  + "   and tut.id_tutorfctdual = pro.id_tutorfctdual"
  + "   and emp.x_empleado = tut.x_empleado"
  + "   and fam.x_familia = pro.x_familia "
  + "   and (:idAnno = -1 OR :idAnno between pro.c_anno_desde and nvl(pro.c_anno_hasta,2099)) "
  + "   order by descripcion ", nativeQuery = true)
 List<ElementoSelectProjection> allModalidadesDelegacion(Long idUsuario, 
               Long idPerfil, 
               Long idCentro,
               Long idCentroProvincia, 
               Integer idAnno,
               Long idTipo, 
               Long idTutor, 
               Long idFamilia);
 
  @Query(value = " select distinct mod.x_modalidad id , mod.d_modalidad AS  descripcion  "
      + "      from TLDATOSCEN dcen, TLPROVINCIAS prv , "
      + "           TLCENTROS cen, FCT_PROYECTOS pro , FCT_TUTORFCTDUAL tut, TLEMPLEADOS emp, TLFAMILIAS fam , TLMODALIDADES mod   "
      + "      where (:idCentro = -1 OR pro.x_centro = :idCentro)"
      + "      and (:idTutor = -1 OR tut.id_tutorfctdual = :idTutor)"
      + "      and (:idTipo = -1 OR (:idTipo != 4 AND pro.id_tipo_proyecto = :idTipo) OR (:idTipo = 4 AND pro.lg_lofp = 1)) "
      + "      and (:idFamilia = -1 OR fam.x_familia = :idFamilia)"
      + "               and (:idProvincia = -1 OR dcen.c_provincia = :idProvincia)"
      + "      and pro.x_familia = fam.x_familia  "
      + "      and mod.x_familia=fam.x_familia "
      + "      and dcen.c_provincia = prv.c_provincia    "
      + "      and dcen.l_vigente = 'S'"
      + "      and cen.x_centro = dcen.x_centro "
      + "      and cen.l_delegacion = 'N' "
      + "      and cen.l_extranjero = 'N'   "
      + "      and cen.x_centro = pro.x_centro"
      + "      and tut.id_tutorfctdual = pro.id_tutorfctdual"
      + "      and emp.x_empleado = tut.x_empleado"
      + "      and fam.x_familia = pro.x_familia "
      + "      and (:idAnno = -1 OR :idAnno between pro.c_anno_desde and nvl(pro.c_anno_hasta,2099)) "
      + "      order by descripcion ", nativeQuery = true)
  List<ElementoSelectProjection> allModalidadesDelegacionProvincias(Long idCentro,
                    Integer idAnno,
                    Long idTipo, 
                    Long idTutor, 
                    Long idFamilia,
                    Long idProvincia);

   @Query(value = " SELECT ID, "
 + "   DS_CENTRO,"
 + "   CASE WHEN LG_LOFP = 1 THEN 'Plan' ELSE DS_TIPO END AS DS_TIPO, "
 + "   DS_PROYECTO, "
 + "   DECODE(nombresustituto,NULL,DS_TUTOR,DS_TUTOR||nombresustituto) DS_TUTOR, "
 + "   DS_FAMILIA, "
 + "   DS_MODALIDAD,"
 + "   DS_CURSO, "
 + "   LG_LOFP, "
 + "   NU_HORAS, "
 + "   NU_ALUMNOS, LG_COPIAR  FROM (select pro.id_proyecto AS ID , "
 + "        cen.c_codigo AS DS_CENTRO,"
 + " tip.ds_nombre AS DS_TIPO, "
 + " pro.ds_proyecto AS DS_PROYECTO, "
 + "  emp.nombre || ' ' || emp.apellido1 || '  ' || emp.apellido2 AS DS_TUTOR, "
 + "  (select distinct ' (sustituido/a por ' || emp1.nombre || ' ' || emp1.apellido1 || ' ' || "
 + "  emp1.apellido2 || ')'  "
 + "  from TLPTOTRAEMP pto1, TLEMPLEADOS emp1, tlpuestos put  "
 + "            where pto1.x_empleado = emp1.x_empleado "
 + "            and pto1.x_centro = pro.x_centro "
 + "            and pto1.x_empleado_sustituye = tut.x_empleado  "
 + "             and (pto1.f_tomapos_sustituye = tut.f_tomapos OR pto1.f_tomapos_sustituye IN (select f_tomapos from TLPTOTRAEMP pto2, tlcursoaca aca "
 + "                     where pto2.x_empleado = pto1.x_empleado   "
 + "                     and pto2.x_centro = pto1.x_centro  "
 + "                     and aca.c_anno = :idAnno  "
 + "                     and TLF_INTERSECPER( pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final ) = 1)) "
 + "            and put.l_docente ='S' "
 + "            and EMP.L_ACTIVO = 'S'  "
 + "            and TRUNC(pto1.F_TOMAPOS) <= SYSDATE  "
 + "            and (pto1.F_CESE IS NULL OR TRUNC(PTO1.F_CESE) >= SYSDATE)) AS nombresustituto, "
 + "  fam.d_familia AS DS_FAMILIA, "
 + "  moda.d_modalidad AS DS_MODALIDAD, "
 + "  omg.d_ofertamatrig AS DS_CURSO, "
 + "  pro.lg_lofp AS LG_LOFP,"
 + "  nu_horas AS NU_HORAS, "
 + " (select count(distinct mat.x_matricula) FROM FCT_CONV_PROY conv, "
 + "                  FCT_CONVPROY_ALU alu, "
 + "                  TLMATALU mat "
 + "  where conv.id_proyecto = pro.id_proyecto  "
 + "  and alu.id_conv_proy = conv.id_conv_proy  "
 + "  and mat.x_matricula = alu.x_matricula "
 + "  and mat.c_anno = :idAnno "
 + "       )  AS NU_ALUMNOS, "
 + "     0 AS LG_COPIAR "
 + "  from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv, TLDATOSCEN dcen1, TLCENTROS cen, TLOFEMATRGEN omg, "
 + "            FCT_PROYECTOS pro, FCT_TIPOS_PROYECTOS tip, FCT_TUTORFCTDUAL tut, TLFAMILIAS fam, TLMODALIDADES moda, TLEMPLEADOS emp "
 + "  where u.x_usuario = :idUsuario"
 + "            and pto.x_empleado=u.x_empleado"
 + "            and pto.x_centro = :idCentroProvincia"
 + "            and pop.x_perfil = :idPerfil"
 + "            and pto.x_centro = dcen.x_centro"
 + "            and dcen.c_provincia = prv.c_provincia"
 + "            and pto.x_empleado=u.x_empleado"
 + "            and pop.x_empleado=pto.x_empleado"
 + "            and pop.f_tomapos = pto.f_tomapos"
 + "            and (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)"
 + "            and dcen.c_provincia = prv.c_provincia"
 + "            and dcen1.c_provincia = prv.c_provincia"
 + "            and dcen1.c_provincia = prv.c_provincia"
 + "            and dcen1.l_vigente = 'S'"
 + "            and cen.x_centro = dcen1.x_centro"
 + "            and cen.l_extranjero = 'N'"
 + "            and cen.x_centro = pro.x_centro"
 + "            and pro.id_tipo_proyecto = tip.id_tipo_proyecto "
 + "  and pro.id_tutorfctdual = tut.id_tutorfctdual "
 + "  and pro.x_familia = fam.x_familia "
 + "         and moda.x_familia = fam.x_familia "
 + "  and pro.x_modalidad = moda.x_modalidad "
 + "  and omg.x_modalidad(+) = pro.x_modalidad "
 + "  and omg.x_ofertamatrig(+) = pro.x_ofertamatrig "
 + "  and tut.x_empleado = emp.x_empleado "
 + "   and (:idAnno = -1 OR :idAnno between pro.c_anno_desde and nvl(pro.c_anno_hasta,2099)) "
 + "   AND ("
 + "   (:idTipo = 4 AND pro.lg_lofp = 1) "
 + "   OR (:idTipo = 1 AND pro.lg_lofp = 0 AND pro.ID_TIPO_PROYECTO = :idTipo) "
 + "   OR (:idTipo != 1 AND :idTipo != 4 AND (:idTipo = -1 OR pro.ID_TIPO_PROYECTO = :idTipo))"
 + "   ) "
 + "   AND (:idTutor = -1 OR pro.ID_TUTORFCTDUAL = :idTutor) "
 + "   AND (:idFamilia = -1 OR pro.X_FAMILIA = :idFamilia) "
 + "   AND (:idModalidad =-1 OR pro.X_MODALIDAD = :idModalidad) "
 + "   AND (:idCentro =-1 OR pro.X_CENTRO = :idCentro) "
 + "   AND (:idCurso = -1 OR pro.x_ofertamatrig = :idCurso) "
 + "   order by 1 desc) ", nativeQuery = true)
 List<ListadoProyectosProjection> getAllProyectosDelegacion(Long idUsuario, 
                     Long idPerfil,
                  Long idCentro, 
                  Long idCentroProvincia,
                  Integer idAnno, 
                  Long idTipo, 
                  Long idTutor, 
                  Long idFamilia, 
                  Long idModalidad,
      Long idCurso);
   
   @Query(value = " SELECT ID, "
       + "      DS_PROVINCIA,"
       + "      DS_CENTRO,"
    + "    CASE WHEN LG_LOFP = 1 THEN 'Plan' ELSE DS_TIPO END AS DS_TIPO, "
       + "      DS_PROYECTO, "
       + "      DECODE(nombresustituto,NULL,DS_TUTOR,DS_TUTOR||nombresustituto) DS_TUTOR, "
       + "      DS_FAMILIA, "
       + "      DS_MODALIDAD,"
    + "  DS_CURSO, "
    + "    LG_LOFP, "
    + "      NU_HORAS, "
       + "      NU_ALUMNOS, LG_COPIAR FROM (select pro.id_proyecto AS ID , "
       + "      prv.d_provincia AS DS_PROVINCIA,"
       + "      cen.c_codigo AS DS_CENTRO,"
       + "      tip.ds_nombre AS DS_TIPO, "
       + "      pro.ds_proyecto AS DS_PROYECTO, "
       + "     emp.nombre || ' ' || emp.apellido1 || '  ' || emp.apellido2 AS DS_TUTOR, "
       + "     (select distinct ' (sustituido/a por ' || emp1.nombre || ' ' || emp1.apellido1 || ' ' || "
       + "     emp1.apellido2 || ')'  "
       + "     from TLPTOTRAEMP pto1, TLEMPLEADOS emp1, tlpuestos put  "
       + "               where pto1.x_empleado = emp1.x_empleado "
       + "               and pto1.x_centro = pro.x_centro "
       + "               and pto1.x_empleado_sustituye = tut.x_empleado  "
       + "               and (pto1.f_tomapos_sustituye = tut.f_tomapos OR pto1.f_tomapos_sustituye IN (select f_tomapos from TLPTOTRAEMP pto2, tlcursoaca aca "
       + "                     where pto2.x_empleado = pto1.x_empleado   "
       + "                     and pto2.x_centro = pto1.x_centro  "
       + "                     and aca.c_anno = :idAnno  "
       + "                     and TLF_INTERSECPER( pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final ) = 1)) "
       + "               and put.l_docente ='S' "
       + "               and EMP.L_ACTIVO = 'S'  "
       + "               and TRUNC(pto1.F_TOMAPOS) <= SYSDATE  "
       + "               and (pto1.F_CESE IS NULL OR TRUNC(PTO1.F_CESE) >= SYSDATE)) AS nombresustituto, "
       + "     fam.d_familia AS DS_FAMILIA, "
       + "     moda.d_modalidad AS DS_MODALIDAD, "
       + "     nu_horas AS NU_HORAS, "
    + "    omg.d_ofertamatrig AS DS_CURSO, "
    + "     pro.lg_lofp AS LG_LOFP,"
    + "     nu_alumnos AS NU_ALUMNOS, "
    + "     0 AS LG_COPIAR "
       + "     from TLDATOSCEN dcen, TLPROVINCIAS prv, TLCENTROS cen, TLOFEMATRGEN omg,  "
       + "               FCT_PROYECTOS pro, FCT_TIPOS_PROYECTOS tip, FCT_TUTORFCTDUAL tut, TLFAMILIAS fam, TLMODALIDADES moda, TLEMPLEADOS emp "
       + "     where     (:idAnno = -1 OR :idAnno between pro.c_anno_desde and nvl(pro.c_anno_hasta,2099)) "
    + "      AND ("
    + "      (:idTipo = 4 AND pro.lg_lofp = 1) "
    + "      OR (:idTipo = 1 AND pro.lg_lofp = 0 AND pro.ID_TIPO_PROYECTO = :idTipo) "
    + "      OR (:idTipo != 1 AND :idTipo != 4 AND (:idTipo = -1 OR pro.ID_TIPO_PROYECTO = :idTipo))"
    + "      ) "
       + "              AND (:idTutor = -1 OR pro.ID_TUTORFCTDUAL = :idTutor) "
       + "              AND (:idFamilia = -1 OR pro.X_FAMILIA = :idFamilia) "
       + "              AND (:idModalidad =-1 OR pro.X_MODALIDAD = :idModalidad) "
    + "     AND (:idCurso = -1 OR pro.x_ofertamatrig = :idCurso) "
    + "              AND (:idCentro =-1 OR pro.x_centro = :idCentro) "
       + "              AND (:idProvincia =-1 OR dcen.c_provincia = :idProvincia) "
       + "              and dcen.c_provincia = prv.c_provincia"
       + "        and dcen.l_vigente = 'S'"
       + "        and cen.x_centro = dcen.x_centro"
       + "        and cen.l_extranjero = 'N'"
       + "        and cen.x_centro = pro.x_centro"
    + "    and omg.x_modalidad(+) = pro.x_modalidad "
    + "    and omg.x_ofertamatrig(+) = pro.x_ofertamatrig "
       + "        and pro.id_tipo_proyecto = tip.id_tipo_proyecto "
       + "     and pro.id_tutorfctdual = tut.id_tutorfctdual "
       + "     and pro.x_familia = fam.x_familia "
       + "        and moda.x_familia = fam.x_familia "
       + "     and pro.x_modalidad = moda.x_modalidad "
       + "     and tut.x_empleado = emp.x_empleado       "
       + "      order by 1 desc) ", nativeQuery = true)
   List<ListadoProyectosProjection> getAllProyectosDelegacionProvincias(Long idCentro,                      
                        Integer idAnno, 
                        Long idTipo, 
                        Long idTutor, 
                        Long idFamilia, 
                        Long idModalidad,
                        Long idProvincia,
      Long idCurso);
   
   @Query(value = " select distinct FAM.X_FAMILIA AS id, FAM.D_FAMILIA AS descripcionLarga, FAM.S_FAMILIA AS descripcionCorta   "
     + "  from   tlofematrgen omg, tlprogramas pro, tlofegenprog ogp, tlmodalidades moda, tlfamilias fam, FCT_TUTORFCTDUAL tut,"
     + "         tluniafetrahor UAT, tlhorariosr HOR,"
     + "  (select ds_abrev from FCT_TIPOS_PROYECTOS where id_tipo_proyecto = :idTipo ) tipo,"
     + "  (select MOG.X_MATERIAOMG, MAC.T_ABREV, MAC.S_MATERIAC, MOG.X_OFERTAMATRIG, omg.X_MODALIDAD from tlmatofematrg MOG,  TLOFEMATRGEN omg, "
     + "          tlmateriascurso MAC where MOG.X_MATERIAC = MAC.X_MATERIAC AND OMG.X_OFERTAMATRIG = MOG.X_OFERTAMATRIG ) A "
     + "  where  omg.x_ofertamatrig = ogp.x_ofertamatrig "
     + "  and    omg.d_ofertamatrig not like '%LOFP%' "
     + "  and    pro.x_programa = ogp.x_programa "
     + "  and    omg.x_modalidad = moda.x_modalidad "
     + "  and    moda.x_familia = fam.x_familia "
     + "  and    ogp.x_centro = :idCentro"
     + "  and    pro.x_tipoprograma in (124) "
     + "  and    :cAnno between omg.c_anno and nvl(omg.c_annotermina,2099) "
     + "  and    :cAnno between ogp.c_anno and nvl(ogp.c_annohasta,2099) "
     + "  and    ((tipo.ds_abrev <> 'B' and pro.l_materia = 'S')  "
     + "  or (tipo.ds_abrev = 'B' and pro.l_materia = 'N')) "
     + "  AND tut.id_tutorfctdual = :idTutor "
     + "  AND HOR.X_EMPLEADO = tut.X_EMPLEADO  "
     + "  AND HOR.F_TOMAPOS in (select pto1.f_tomapos from tlptotraemp pto1 , tlcursoaca aca "
     + "        where aca.c_anno = :cAnno "
     + "        and TLF_INTERSECPER( pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final ) = 1 "
     + "        and pto1.x_centro =  :idCentro "
     + "        and pto1.x_empleado =  hor.x_empleado) "
     + " AND HOR.C_ANNO = :cAnno "
     + " AND UAT.X_HORARIORE = HOR.X_HORARIORE "
     + " AND UAT.X_MATERIAOMG = A.X_MATERIAOMG"
     + " AND moda.x_modalidad = A.x_modalidad"
     + " AND fam.x_familia = moda.x_familia  ", nativeQuery = true)
List<FamiliaProjection> allFamiliasCentroTutor(Long idCentro, int cAnno, Long idTipo, Long idTutor);

   @Query(value = " select count(*) from fct_conv_proy where id_proyecto = :id ", nativeQuery = true)
Integer getProyectoUsado(Long id);

 @Query(value = "SELECT distinct omg.x_ofertamatrig AS id, omg.d_ofertamatrig AS descripcion " +
   "FROM FCT_PROYECTOS pro, FCT_TIPOS_PROYECTOS tip, FCT_TUTORFCTDUAL tut, TLFAMILIAS fam, " +
   "     TLMODALIDADES moda, TLEMPLEADOS emp, tlofematrgen omg " +
   "WHERE pro.id_tipo_proyecto = tip.id_tipo_proyecto " +
   "  AND pro.id_tutorfctdual = tut.id_tutorfctdual " +
   "  AND pro.x_familia = fam.x_familia " +
   "  AND pro.x_modalidad = moda.x_modalidad " +
   "  AND tut.x_empleado = emp.x_empleado " +
   "  AND pro.x_centro = :idCentro " +
   "  AND omg.x_modalidad = pro.x_modalidad " +
   "  AND omg.x_ofertamatrig = pro.x_ofertamatrig " +
   "  AND (:cAnno = -1 OR :cAnno BETWEEN pro.c_anno_desde AND NVL(pro.c_anno_hasta, 2099)) " +
   "  AND (" +
   "   (:idTipoProyecto = 4 AND pro.lg_lofp = 1) " +
   "   OR (:idTipoProyecto = 1 AND pro.lg_lofp = 0 AND pro.ID_TIPO_PROYECTO = :idTipoProyecto) " +
   "   OR (:idTipoProyecto != 1 AND :idTipoProyecto != 4 AND (:idTipoProyecto = -1 OR pro.ID_TIPO_PROYECTO = :idTipoProyecto))" +
   "    ) " +
   "  AND (:idTutor = -1 OR pro.id_tutorfctdual = :idTutor) " +
   "  AND (:idFamilia = -1 OR pro.x_familia = :idFamilia) " +
   "  AND (:idModalidad = -1 OR pro.x_modalidad = :idModalidad) " +
   "ORDER BY omg.d_ofertamatrig ",
   nativeQuery = true)
 List<ElementoSelectProjection> findCursosCentroAnnoTutorFamiliaModalidad(Long idCentro,
                    Integer cAnno, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad);

 @Query(value = "select DISTINCT DECODE(lg_regimen,0,'GENERAL','INTENSIVO') as regimen, " +
         "        TO_CHAR(SYSDATE, 'DD \"de\" Month \"de\" YYYY', 'NLS_DATE_LANGUAGE=SPANISH') as fecha, " +
         "        mat.c_anno || '/' || (mat.c_anno+1)  as academico, " +
         "        omg.n_orden orden, " +
         "        omg.d_ofertamatrig as ciclo, " +
         "         uni.t_nombre grupo," +
         "         alu.t_nombre || ' ' || alu.t_apellido1 || ' ' || alu.t_apellido2 nombre, " +
         "         alu.c_numide dni, " +
         "         alu.t_nuss nuss, " +
         "         alu.t_correo_e email," +
         "         alu.t_telefono telefono," +
         "         TO_CHAR(alu.f_nacimiento,'DD/MM/YYYY') nacimiento," +
         "         NVL((select LG_PRL " +
         "          from FCT_DATOSALU_PLAN " +
         "          where x_matricula = mat.x_matricula),0) basico," +
         "         NVL((select LG_CERTIFICACION " +
         "          from FCT_DATOSALU_PLAN " +
         "          where x_matricula = mat.x_matricula),0) certificacion, " +
         "         (select DS_CERTIFICACION " +
         "          from FCT_DATOSALU_PLAN " +
         "          where x_matricula = mat.x_matricula) especificar, " +
         "         den.s_denominacion || ' ' || dat.d_especifica centro, " +
         "         dat.t_correo_corporativo mailcen, " +
         "         cen.c_codigo codigo, " +
         "         emp.nombre ||' ' || emp.apellido1 || ' ' || emp.apellido2 tutor, " +
         "         (select ust.correo_aula " +
         "          from delphos.usuarios_t ust, delphos_modacc.documentaciones doc " +
         "          where doc.t_identificacion = emp.c_numide " +
         "          and ust.oid_persona = doc.oid_persona " +
         "          and rownum <=1) mailtut, " +
         "          dat.n_telefono tfntut, " +
         "          NVL((select LG_ADAPTACIONES " +
         "          from FCT_DATOSALU_PLAN " +
         "          where x_matricula = mat.x_matricula),0) adaptaciones, " +
         "          (select DS_ADAPTACIONES " +
         "          from FCT_DATOSALU_PLAN " +
         "          where x_matricula = mat.x_matricula) dsadaptaciones, " +
         "          NVL((select LG_AUTORIZACIONES " +
         "          from FCT_DATOSALU_PLAN " +
         "          where x_matricula = mat.x_matricula),0) autorizaciones, " +
         "          (select DS_AUTORIZACIONES " +
         "          from FCT_DATOSALU_PLAN  " +
         "          where x_matricula = mat.x_matricula) dsautorizaciones," +
         "          (select DS_OBSERVACIONES " +
         "          from FCT_DATOSALU_PLAN " +
         "          where x_matricula = mat.x_matricula) observaciones," +
         "          proy.lg_idiario diario, " +
         "          proy.lg_isemanal semanal, " +
         "          proy.lg_imensual mensual, " +
         "          proy.lg_iotros otros, " +
         "          DECODE((select count(*) from fct_convproy_alu where x_matricula = mat.x_matricula),1,0,1) varias," +
         "          CASE   " +
         "             WHEN :idConvProy = -1  " +
         "             THEN (SELECT SUM(proy.nu_horas_totales)  " +
         "                   FROM fct_conv_proy proy  " +
         "                   INNER JOIN fct_convproy_alu alu  " +
         "                       ON alu.id_conv_proy = proy.id_conv_proy  " +
         "                   WHERE alu.x_matricula = :idMatricula)  " +
         "             ELSE cproy.nu_horas_totales   " +
         "          END AS horasConvenio " +
         "from fct_convproy_alu cproa,"
         + "   fct_conv_proy cproy, " +
         "     fct_proyectos proy," +
         "     tlmatalu mat, " +
         "     tlofematrgen omg, " +
         "     tlunidadescen uni, " +
         "     tlalumnos alu, " +
         "     tlcentros cen, " +
         "     tldatoscen dat, " +
         "     tldengen den, " +
         "     fct_tutorfctdual tut, " +
         "     tlempleados emp " +
         "where (-1 = :idConvProy OR cproy.id_conv_proy = :idConvProy ) " +
         "and proy.id_proyecto =  cproy.id_proyecto " +
         "and mat.x_matricula = :idMatricula " +
         "and cproa.x_matricula = mat.x_matricula " +
         "and cproa.id_conv_proy = cproy.id_conv_proy " +
         "and omg.x_ofertamatrig = proy.x_ofertamatrig " +
         "and uni.x_unidad = mat.x_unidad " +
         "and alu.x_alumno = mat.x_alumno " +
         "and mat.x_ofertamatrig = omg.x_ofertamatrig " +
         "and cen.x_centro = mat.x_centro " +
         "and dat.x_centro = cen.x_centro " +
         "and den.x_dengen = dat.x_dengen " +
         "and tut.id_tutorfctdual = proy.id_tutorfctdual " +
         "and emp.x_empleado = tut.x_empleado " +
         "and rownum=1 " , nativeQuery = true)
 DatosCabeceraAnexo2PlanProjection getDatosCabeceraPlan(Long idConvProy, Long idMatricula);

@Query(value = "select DISTINCT emp.d_empresa denominacion,   " +
        "        sed.t_correo mailemp,  " +
        "        emp.c_numide cif,  " +
        "        tutemp.nombretut,  " +
        "        sed.t_correo mailtut,  " +
        "        sed.n_telefono tlfemp,  " +
        "        tutemp.dni dnitut  " +
        "from fct_conv_proy cproy,   " +
        "     fct_convproy_alu cpra,  " +
        "     fct_convenios conv,  " +
        "     tlempresas emp,  " +
        "     emp_sedemp sed,  " +
        "     (select tra.id_traemp,  " +
        "       emp.tx_nombre ||' '||emp.tx_apellido1||' '|| emp.tx_apellido2 nombretut,  " +
        "       emp.c_numide dni  " +
        "     from emp_traemp tra,   " +
        "          emp_empleados emp  " +
        "     where emp.id_empleado = tra.id_empleado) tutemp  " +
        "where (-1 = :idConvProy OR cproy.id_conv_proy = :idConvProy)  " +
        "and cpra.id_conv_proy = cproy.id_conv_proy  " +
        "and cpra.x_matricula = :idMatricula  " +
        "and cproy.id_convenio = conv.id_convenio  " +
        "and emp.x_empresa = conv.x_empresa  " +
        "and sed.id_sedemp = conv.id_sedemp  " +
        "and tutemp.id_traemp = cproy.id_traemp "  , nativeQuery = true)
List<DatosTablaEmpresasAnexo2PlanProjection> getDatosTablaEmpresasPlan(Long idConvProy, Long idMatricula);

 @Query(value = "SELECT CASE      " +
         "        WHEN periodo_num = 1 THEN '1er Periodo. Calendario y horario / ' || columna2     " +
         "        ELSE periodo_num || 'º Periodo. Calendario y horario / ' || columna2     " +
         "    END AS periodos,     " +
         "    horas,     " +
         "    empresa FROM (     " +
         "SELECT      " +
         "    ROW_NUMBER() OVER (ORDER BY fh_inicio) AS periodo_num,       " +
         "        'Del ' || TO_CHAR(fh_inicio, 'DD') || ' de ' || INITCAP(TO_CHAR(fh_inicio, 'FMMonth')) ||      " +
         "        ' al ' || TO_CHAR(fh_fin, 'DD') || ' de ' || INITCAP(TO_CHAR(fh_fin, 'FMMonth')) || ' / ' ||     " +
         "        LISTAGG(     " +
         "            '(' || jornadas || ' jornadas) Jornadas de ' ||      " +
         "            convierte_hora(n_horini) || ' a ' ||      " +
         "            convierte_hora(n_horfin) || ' (' ||      " +
         "            ROUND((n_horfin - n_horini) / 60, 2) || ' horas)', ' / '     " +
         "        ) WITHIN GROUP (ORDER BY n_horini) AS columna2,     " +
         "        MAX(nu_horas) AS horas,     " +
         "        d_empresa AS empresa     " +
         "FROM (SELECT      " +
         "            per.fh_inicio,     " +
         "            per.fh_fin,     " +
         "            tra.n_horini,     " +
         "            tra.n_horfin,     " +
         "            COUNT(*) AS jornadas,  " +
         "            per.nu_horas,     " +
         "            emp.d_empresa             " +
         "        FROM fct_conv_proy covp     " +
         "        JOIN fct_conv_proyaluhoraper per ON per.id_conv_proy = covp.id_conv_proy     " +
         "        JOIN fct_conv_proyaluhoratra tra ON tra.id_conv_proyaluhoraper = per.id_conv_proyaluhoraper     " +
         "        JOIN fct_convenios conv ON conv.id_convenio = covp.id_convenio     " +
         "        JOIN tlempresas emp ON emp.x_empresa = conv.x_empresa     " +
         "        WHERE      " +
         "            (-1 = :idConvProy OR covp.id_conv_proy = :idConvProy)      " +
         "            AND per.x_matricula = :idMatricula           " +
         "        GROUP BY      " +
         "            per.fh_inicio, per.fh_fin, tra.n_horini, tra.n_horfin, per.nu_horas, emp.d_empresa )     " +
         "GROUP BY fh_inicio, fh_fin, d_empresa     " +
         "ORDER BY fh_inicio)"  , nativeQuery = true)
 List<DatosTablaHorariosAnexo2PlanProjection> getDatosTablaPeriodosPlan(Long idConvProy, Long idMatricula);

 @Query(value = "select modcur.id_modulo_curso idModuloCurso, " +
         "       mcur.d_materiac modulo, " +
         "       momg.c_codigomec codigo, " +
         "       1 colaboracion, " +
         "       momg.n_ordenpres " +
         "from fct_conv_proy cproy,  " +
         "     fct_cursos_proyectos cur, " +
         "     fct_convproy_alu cpra, " +
         "     fct_modulos_cursos modcur, " +
         "     fct_modulos_empresas modemp, " +
         "     tlmatofematrg momg,  " +
         "     tlmateriascurso mcur, " +
         "     fct_convenios conv " +
         "where (-1 = :idConvProy OR cproy.id_conv_proy = :idConvProy) " +
         "and cpra.id_conv_proy = cproy.id_conv_proy  " +
         "and cur.id_proyecto = cproy.id_proyecto  " +
         "and cpra.x_matricula = :idMatricula  " +
         "and cproy.id_convenio = conv.id_convenio " +
         "and modcur.id_curso_proyecto = cur.id_curso_proyecto " +
         "and modemp.id_modulo_curso = modcur.id_modulo_curso " +
         "and modemp.id_conv_proy = cpra.id_conv_proy " +
         "and momg.x_materiaomg = modcur.x_materiaomg " +
         "and mcur.x_materiac = momg.x_materiac " +
         "UNION " +
         "SELECT DISTINCT -1 idModuloCurso, " +
         "                   mcur.d_materiac modulo, " +
         "                   momg.c_codigomec codigo, " +
         "                0 colaboracion, " +
         "                momg.n_ordenpres " +
         "FROM tlofematrgen omg, tlofematrcen omc, tlmatofematrg momg, tlmateriascurso mcur,          " +
         "     tlmatofematrcen momc, tlperiodosomc per, tlmatalu mat, tlmatmatrialu mma          " +
         "WHERE -1 = :idConvProy " +
         "AND mat.x_matricula =  :idMatricula  " +
         "AND omg.x_ofertamatrig = mat.x_ofertamatrig          " +
         "AND omc.x_ofertamatrig = omg.x_ofertamatrig          " +
         "AND omc.x_centro = mat.x_centro          " +
         "AND mat.c_anno BETWEEN omg.c_anno AND NVL(omg.c_annotermina, 2099)          " +
         "AND momg.x_materiac = mcur.x_materiac          " +
         "AND momg.x_materiaomg = momc.x_materiaomg          " +
         "AND momc.x_ofertamatric = omc.x_ofertamatric          " +
         "AND per.x_ofertamatric = omc.x_ofertamatric          " +
         "AND mat.c_anno BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 2099) " +
         "AND mma.x_matricula = mat.x_matricula " +
         "AND mma.x_materiaomg = momg.x_materiaomg " +
         "AND momg.x_materiaomg not in ( select momg.x_materiaomg " +
         "from fct_conv_proy cproy,  " +
         "     fct_cursos_proyectos cur, " +
         "     fct_convproy_alu cpra, " +
         "     fct_modulos_cursos modcur, " +
         "     fct_modulos_empresas modemp, " +
         "     tlmatofematrg momg,  " +
         "     tlmateriascurso mcur, " +
         "     fct_convenios conv " +
         "where cpra.id_conv_proy = cproy.id_conv_proy " +
         "and cur.id_proyecto = cproy.id_proyecto " +
         "and cpra.x_matricula = :idMatricula " +
         "and cproy.id_convenio = conv.id_convenio " +
         "and modcur.id_curso_proyecto = cur.id_curso_proyecto " +
         "and modemp.id_modulo_curso = modcur.id_modulo_curso " +
         "and modemp.id_conv_proy = cpra.id_conv_proy " +
         "and momg.x_materiaomg = modcur.x_materiaomg " +
         "and mcur.x_materiac = momg.x_materiac) " +
         "order by n_ordenpres" , nativeQuery=true)
 List<DatosListaModuloAnexo2PlanProjection> getDatosListaModuloAnexoIIPlan(Long idConvProy, Long idMatricula);


 @Query(value = "SELECT aprendizaje, centro, empresa, dempresa FROM (    " +
         "SELECT   " +
         "    comes.t_abrev || ':' || comes.d_comesp AS aprendizaje,  " +
         "    rel.lg_centro AS centro,  " +
         "    rel.lg_empresa AS empresa,  " +
         "    CASE   " +
         "        WHEN rel.lg_centro = 1 AND rel.lg_empresa = 0 THEN NULL  " +
         "        ELSE (  " +
         "            SELECT LISTAGG(emp.d_empresa, ' / ') WITHIN GROUP (ORDER BY emp.d_empresa)  " +
         "            FROM fct_modulos_empresas mod  " +
         "            JOIN fct_conv_proy cproy ON cproy.id_conv_proy = mod.id_conv_proy  " +
         "            JOIN fct_convenios conv ON conv.id_convenio = cproy.id_convenio  " +
         "            JOIN fct_convproy_alu cproa ON cproa.id_conv_proy = cproy.id_conv_proy " +
         "            JOIN tlempresas emp ON emp.x_empresa = conv.x_empresa  " +
         "            WHERE mod.id_modulo_curso = rel.id_modulo_curso  " +
         "            AND cproa.x_matricula = :idMatricula " +
         "        )  " +
         "    END AS dempresa,  " +
         "    comes.n_ordenpres orden  " +
         "FROM   " +
         "    fct_resultadosa_modulos rel  " +
         "JOIN   " +
         "    tlcomesp comes ON comes.x_comesp = rel.x_comesp  " +
         "WHERE   " +
         "    rel.id_modulo_curso = :idModulo  " +
         "UNION  " +
         "SELECT   " +
         "     'TODOS' aprendizaje,  " +
         "    1 AS centro,  " +
         "    0 AS empresa,  " +
         "    null AS dempresa,  " +
         "    1 orden  " +
         "FROM   " +
         "DUAL where -1 = :idModulo )  " +
         "ORDER BY   " +
         "    orden " , nativeQuery = true)
List<DatosListaResultadosAprendizajeAnexo2PlanProjection> getDatosListaResultadosAprendizajeAnexo2Plan (Long idModulo, Long idMatricula);


@Query(value= "SELECT 'Actividades formativas a realizar en la empresa relacionadas con los resultados de aprendizaje del módulo profesional. Relacionar cada actividad con el resultado de aprendizaje asociado.' actividades, 1 orden     " +
        "FROM DUAL    " +
        "UNION    " +
        "SELECT actividades, orden FROM (    " +
        "SELECT act.tx_nombre || ':' || LISTAGG(com.t_abrev, ',') WITHIN GROUP (ORDER BY com.n_ordenpres) AS actividades, 2 orden     " +
        "FROM fct_actividades_modulos act     " +
        "JOIN fct_resultadosa_actividades react ON react.id_actividad_modulo = act.id_actividad_modulo    " +
        "JOIN fct_resultadosa_modulos remod ON remod.id_resultadoa_modulo = react.id_resultadoa_modulo    " +
        "JOIN tlcomesp com ON com.x_comesp = remod.x_comesp    " +
        "WHERE act.id_modulo_curso = :idModulo    " +
        "GROUP BY act.tx_nombre, act.nu_orden    " +
        "order by act.nu_orden)    " +
        "order by orden " , nativeQuery = true)
 List<DatosListaActividadesAnexo2PlanProjection> getDatosListaActividadesAnexo2Plan(Long idModulo);


 @Query(value = "select info.ds_descripcion as descripcion, " +
         "       info.tx_calendario_horario as calendario, " +
         "       info.tx_resultado_previsto as resultadoPrevisto, " +
         "       info.tx_contenidos_desarrollar as contenidos, " +
         "       info.tx_actividades_formativas as actividades " +
         "from fct_convproy_alu conva " +
         "join fct_conv_proy con on con.id_conv_proy = conva.id_conv_proy " +
         "join fct_info_adicional_plan info on info.id_proyecto = con.id_proyecto " +
         "where conva.x_matricula = :idMatricula " +
         "and rownum <= 1",
         nativeQuery = true)
 DatosTablaFormEspAnexo2PlanProjection getDatosListaFormEspAnexo2Plan(Long idMatricula);

 @Query(value = "SELECT con.id_proyecto " +
         "FROM fct_convproy_alu conva " +
         "JOIN fct_conv_proy con ON conva.id_conv_proy = con.id_conv_proy " +
         "WHERE conva.x_matricula = :idMatricula " +
         "AND ROWNUM <= 1", nativeQuery = true)
 Long findProyectoIdByMatricula(Long idMatricula);

 
   @Query(value = "select count(distinct id_proyecto) from fct_proyectos proy, tlcursoaca aca "
   		+ "where proy.x_centro = :idCentro "
   		+ "and aca.c_anno BETWEEN  proy.c_anno_desde and proy.c_anno_hasta "
   		+ "and TRIM(proy.ds_proyecto) = TRIM(:dsDescripcion) "
   		+ "and aca.l_actual = 'S' ", nativeQuery = true)
   int getDescripcionUsada(Long idCentro, String dsDescripcion);

}
