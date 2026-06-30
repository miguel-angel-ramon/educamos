package es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE;

import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.HistoricoFlujoAutorizacionProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ProgramasPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.QProgramasPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.AnexoVProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.CombosProgramasPFEProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.CursosProgramasPFEProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.DatosVigentePFEProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.ListadoPFEProjection;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramasPFERepository extends AbstractRepository<ProgramasPFE, Long, QProgramasPFE> {

    @Query(value = "SELECT DISTINCT cen.x_centro AS id, cen.c_codigo AS descripcion " +
      "FROM tlofertasunidad uni " +
      "JOIN tlofematrcen omc ON omc.x_ofertamatric = uni.x_ofertamatric " +
      "JOIN tlofematrgen omg ON omg.x_ofertamatrig = omc.x_ofertamatrig " +
      "JOIN tlcentros cen ON cen.x_centro = omc.x_centro " +
      "JOIN tlperiodosomc per ON per.x_ofertamatric = omc.x_ofertamatric " +
      "WHERE omg.d_ofertamatrig LIKE '%LOFP%' " +
      "AND :cAnno BETWEEN per.c_anno AND COALESCE(per.c_annopuedeterminar, 2099) " +
      "AND omc.x_centro IN ( " +
      "    SELECT DISTINCT dcen1.x_centro " +
      "    FROM TLUSUARIOS u " +
      "    JOIN TLPTOTRAEMP pto ON pto.x_empleado = u.x_empleado " +
      "    JOIN TLPUEORIPER pop ON pop.x_empleado = pto.x_empleado AND pop.f_tomapos = pto.f_tomapos " +
      "    JOIN TLDATOSCEN dcen ON dcen.x_centro = pto.x_centro " +
      "    JOIN TLPROVINCIAS prv ON prv.c_provincia = dcen.c_provincia " +
      "    JOIN TLDATOSCEN dcen1 ON dcen1.c_provincia = prv.c_provincia " +
      "    JOIN TLCENTROS cen ON cen.x_centro = dcen1.x_centro " +
      "    WHERE u.x_usuario = :idUsuario " +
      "    AND pop.x_perfil = :idPerfil " +
      "    AND pto.x_centro = :idCentro " +
      "    AND (pto.f_cese IS NULL OR SYSDATE BETWEEN pto.f_tomapos AND pto.f_cese) " +
      "    AND dcen1.l_vigente = 'S' " +
      "    AND cen.l_delegacion = 'N' " +
      "    AND cen.l_extranjero = 'N') " +
      "ORDER BY cen.c_codigo ", nativeQuery = true)
    List<CombosProgramasPFEProjection> getCodigoProgramasPFE(Long idCentro,
       Integer cAnno,
       Long idPerfil,
       Long idUsuario);


    @Query(value = "SELECT distinct cen.x_centro id , den.s_denominacion || ' ' || dat.d_especifica descripcion, dat.d_especifica " +
      "   from   tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,  " +
      "  tlcentros cen, tlperiodosomc per, tldatoscen dat, tldengen den  " +
      "   where omc.x_ofertamatric =  uni.x_ofertamatric  " +
      "   and omg.x_ofertamatrig =  omc.x_ofertamatrig     " +
      "   and d_ofertamatrig like '%LOFP%'      " +
      "   and cen.x_centro =  omc.x_centro    " +
      "   and per.x_ofertamatric = omc.x_ofertamatric    " +
      "   AND :cAnno BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 2099)     " +
      "   and omc.x_centro in (SELECT distinct dcen1.x_centro id   " +
      "  from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,   " +
      " TLDATOSCEN dcen1, TLCENTROS cen, tldengen den " +
      " WHERE u.x_usuario = :idUsuario   " +
      " AND pop.x_perfil = :idPerfil   " +
      " AND pto.x_centro = :idCentro   " +      
      " AND pto.x_empleado=u.x_empleado   " +
      " AND pop.x_empleado=pto.x_empleado    " +
      " AND pop.f_tomapos = pto.f_tomapos    " +
      " AND pto.x_centro = dcen.x_centro    " +
      " AND dcen.c_provincia = prv.c_provincia      " +
      " AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)    " +
      " AND dcen1.c_provincia = prv.c_provincia    " +
      " AND dcen1.l_vigente = 'S'   " +
      " AND cen.x_centro = dcen1.x_centro   " +
      " AND cen.l_delegacion = 'N'   " +
      " AND cen.l_extranjero = 'N')   " +
      " AND dat.x_centro = omc.x_centro " +
      " AND dat.x_dengen = den.x_dengen " +
      " AND (-1 = :idCodigo OR omc.x_centro = :idCodigo) " +     
      "   order by dat.d_especifica", nativeQuery = true)
    List<CombosProgramasPFEProjection> getCentrosProgramasPFE( Long idCentro,
   Integer cAnno,
   Long idPerfil,
   Long idUsuario,
   Long idCodigo);


    @Query(value = "select  distinct fam.x_familia AS id, fam.d_familia AS descripcion      " +
      "   from  tlofematrcen omc, tlofematrgen omg, tlmodalidades mod, tlfamilias fam " +
      "   where omg.x_ofertamatrig =  omc.x_ofertamatrig       " +
      "   and d_ofertamatrig like '%LOFP%'  " +
      "   and (-1 = :idCentroCombo OR omc.x_centro = :idCentroCombo)    " +
      "   and omg.x_modalidad = mod.x_modalidad " +
      "   and fam.x_familia = mod.x_familia  " +
      "   and omc.x_centro IN (SELECT distinct dcen1.x_centro id " +
      "      from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , " +
      "      TLDATOSCEN dcen1, TLCENTROS cen " +
      "      WHERE u.x_usuario = :idUsuario " +
      "      AND pop.x_perfil = :idPerfil      " +
      "      AND pto.x_centro = :idCentro  " +
      "      AND pto.x_empleado=u.x_empleado " +
      "      AND pop.x_empleado=pto.x_empleado " +
      "      AND pop.f_tomapos = pto.f_tomapos " +
      "      AND pto.x_centro = dcen.x_centro " +
      "      AND dcen.c_provincia = prv.c_provincia   " +
      "      AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) " +
      "      AND dcen1.c_provincia = prv.c_provincia " +
      "      AND dcen1.l_vigente = 'S' " +
      "      AND cen.x_centro = dcen1.x_centro " +
      "      AND cen.l_delegacion = 'N' " +
      "      AND cen.l_extranjero = 'N')     " +
      "      order by descripcion ", nativeQuery = true)
    List<CombosProgramasPFEProjection> getFamiliasProgramasPFEGestor(Long idCentroCombo,
   Long idUsuario,
   Long idPerfil,
   Long idCentro);



    @Query(value = "select  distinct fam.x_familia AS id, fam.d_familia AS descripcion      " +
      "   from  tlofematrcen omc, tlofematrgen omg, tlmodalidades mod, tlfamilias fam " +
      "   where omg.x_ofertamatrig =  omc.x_ofertamatrig       " +
      "   and d_ofertamatrig like '%LOFP%'  " +
      "   and (-1 = :idCentroCombo OR omc.x_centro = :idCentroCombo)    " +
      "   and omg.x_modalidad = mod.x_modalidad " +
      "   and fam.x_familia = mod.x_familia  " +
      "   and omc.x_centro IN (SELECT distinct dcen.x_centro id   " +
      "     from TLDATOSCEN dcen, TLPROVINCIAS prv ,  TLCENTROS cen  " +
      "     WHERE (-1 = :idProvincia or dcen.c_provincia = :idProvincia)  " +
      "     AND dcen.c_provincia = prv.c_provincia    " +
      "     AND dcen.l_vigente = 'S'  " +
      "     AND cen.x_centro = dcen.x_centro  " +
      "     AND cen.l_delegacion = 'N'   " +
      "     AND cen.l_extranjero = 'N')     " +
      "      order by descripcion ", nativeQuery = true)
    List<CombosProgramasPFEProjection> getFamiliasProgramasPFEDelegacion(Long idCentroCombo,
       Integer idProvincia);

    @Query(value = "SELECT  distinct fam.x_familia AS id, fam.d_familia AS descripcion      " +
      "      from  tlofematrcen omc, tlofematrgen omg, tlfamilias fam, tlmodalidades mod " +
      "      where omc.x_centro =  :idCentro " +
      "      and omg.x_ofertamatrig =  omc.x_ofertamatrig " +
      "      and mod.x_modalidad = omg.x_modalidad " +
      "      and fam.x_familia = mod.x_familia " +
      "      and d_ofertamatrig like '%LOFP%' order by descripcion", nativeQuery = true)
    List<CombosProgramasPFEProjection> getFamiliasProgramasPFEDirectorTutor(Long idCentro);



    @Query(value = "SELECT distinct x_ofertamatrig AS idOfertamatrig, d_ofertamatrig AS curso, n_ordenpres as orden FROM (  " +
      "   select omg.x_ofertamatrig , omg.d_ofertamatrig , omg.n_ordenpres      " +
      "   from  tlofematrcen omc, tlofematrgen omg, tlfamilias fam, tlmodalidades mod     " +
      "   where omg.x_ofertamatrig =  omc.x_ofertamatrig       " +
      "   and d_ofertamatrig like '%LOFP%'  " +
      "   and (-1 = :idCentroCombo OR omc.x_centro = :idCentroCombo) " +
      "   and mod.x_modalidad = omg.x_modalidad " +
      "   and fam.x_familia = mod.x_familia " +
      "   and (-1 = :idFamilia OR fam.x_familia = :idFamilia) " +
      "   and omc.x_centro IN (SELECT distinct dcen1.x_centro id " +
      "      from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , " +
      "      TLDATOSCEN dcen1, TLCENTROS cen " +
      "      WHERE u.x_usuario = :idUsuario " +
      "      AND pop.x_perfil = :idPerfil      " +
      "      AND pto.x_centro = :idCentro  " +
      "      AND pto.x_empleado=u.x_empleado " +
      "      AND pop.x_empleado=pto.x_empleado " +
      "      AND pop.f_tomapos = pto.f_tomapos " +
      "      AND pto.x_centro = dcen.x_centro " +
      "      AND dcen.c_provincia = prv.c_provincia   " +
      "      AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) " +
      "      AND dcen1.c_provincia = prv.c_provincia " +
      "      AND dcen1.l_vigente = 'S' " +
      "      AND cen.x_centro = dcen1.x_centro " +
      "      AND cen.l_delegacion = 'N' " +
      "      AND cen.l_extranjero = 'N') ) " +
      "      order by n_ordenpres ", nativeQuery = true)
    List<CursosProgramasPFEProjection> getCursosProgramasPFEGestor(Long idCentroCombo,
 Long idFamilia,
 Long idUsuario,
 Long idPerfil,
 Long idCentro);
    @Query(value = "SELECT distinct x_ofertamatrig AS idOfertamatrig, d_ofertamatrig AS curso, n_ordenpres as orden FROM ( " +
      " select omg.x_ofertamatrig , omg.d_ofertamatrig , omg.n_ordenpres  " +
      "      from  tlofematrcen omc, tlofematrgen omg, tlfamilias fam, tlmodalidades mod    " +
      "      where omc.x_centro =  :idCentro " +
      "      and omg.x_ofertamatrig =  omc.x_ofertamatrig  " +
      "      and mod.x_modalidad = omg.x_modalidad " +
      "      and fam.x_familia = mod.x_familia " +
      "      and (-1 = :idFamilia OR fam.x_familia = :idFamilia) " +
      "      and d_ofertamatrig like '%LOFP%') order by n_ordenpres", nativeQuery = true)
    List<CursosProgramasPFEProjection> getCursosProgramasPFEDirectorTutor(Long idCentro,
  Long idFamilia);

//Falta crear la proyeccion


    @Query(value = "SELECT distinct x_ofertamatrig AS idOfertamatrig, d_ofertamatrig AS curso, n_ordenpres as orden FROM (  " +
      "   select omg.x_ofertamatrig , omg.d_ofertamatrig , omg.n_ordenpres      " +
      "   from  tlofematrcen omc, tlofematrgen omg, tlfamilias fam, tlmodalidades mod   " +
      "   where omg.x_ofertamatrig =  omc.x_ofertamatrig       " +
      "   and d_ofertamatrig like '%LOFP%'  " +
      "   and (-1 = :idCentroCombo OR omc.x_centro = :idCentroCombo)  " +
      "   and mod.x_modalidad = omg.x_modalidad " +
      "   and fam.x_familia = mod.x_familia " +
      "   and (-1 = :idFamilia OR fam.x_familia = :idFamilia) " +
      "   and omc.x_centro IN (SELECT distinct dcen.x_centro id   " +
      "     from TLDATOSCEN dcen, TLPROVINCIAS prv ,  TLCENTROS cen  " +
      "     WHERE (-1 = :idProvincia or dcen.c_provincia = :idProvincia)  " +
      "     AND dcen.c_provincia = prv.c_provincia    " +
      "     AND dcen.l_vigente = 'S'  " +
      "     AND cen.x_centro = dcen.x_centro  " +
      "     AND cen.l_delegacion = 'N'   " +
      "     AND cen.l_extranjero = 'N') ) " +
      "      order by n_ordenpres ", nativeQuery = true)
    List<CursosProgramasPFEProjection> getCursosProgramasPFEDelegacion(Long idCentroCombo,
     Long idFamilia,
     Integer idProvincia);


    
    @Query(value = "SELECT DISTINCT "
      + "        omg.x_ofertamatrig AS id, "
      + "        omg.d_ofertamatrig AS descripcion, "
      + "        omg.n_ordenpres AS orden "
      + "     FROM tlofematrgen omg "
      + "     JOIN tlofematrcen omc ON omg.x_ofertamatrig = omc.x_ofertamatrig "
      + "     JOIN tlperiodosomc per ON per.x_ofertamatric = omc.x_ofertamatric "
      + "     WHERE omc.x_centro = :idCentro "
      + "      AND ("
      + "           :cAnno BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 9999) "
      + "           OR :cAnno + 1 BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 9999) "
      + "          )"
      + "      AND omg.d_ofertamatrig LIKE '%LOFP%' "
      + "      AND omg.d_ofertamatrig NOT LIKE '%Esp%' "
      + "ORDER BY orden ", nativeQuery = true)
 List<ElementoSelectProjection> getCursosCiclos(Long idCentro, Integer cAnno);

    @Query(value = "SELECT DISTINCT  "
      + "         omg.x_ofertamatrig AS id,  "
      + "         omg.d_ofertamatrig AS descripcion,  "
      + "         omg.n_ordenpres AS orden  "
      + "     FROM tlofematrgen omg  "
      + "     JOIN tlofematrcen omc ON omg.x_ofertamatrig = omc.x_ofertamatrig  "
      + "     JOIN tlperiodosomc per ON per.x_ofertamatric = omc.x_ofertamatric  "
      + "     WHERE omc.x_centro = :idCentro  "
      + "     AND ( "
      + "           :cAnno BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 9999)  "
      + "           OR :cAnno + 1 BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 9999)  "
      + "           ) "
      + "     AND omg.d_ofertamatrig LIKE '%LOFP%'  "
      + "     AND omg.d_ofertamatrig LIKE '%Esp.%'  "
      + "     ORDER BY orden ", nativeQuery = true)
 List<ElementoSelectProjection> getCursosEspecializacion(Long idCentro, Integer cAnno);    
    
    @Query(value = " SELECT prog.id_progperfor as id, "
      + " omg.d_ofertamatrig as dsCurso, "
      + " decode (prog.lg_alcance,1,'Todo el alumnado','El alumnado matriculado con formatos de PFE diferentes') as dsAlcance, "
      + " decode (prog.lg_modalidad,1,'General','Intensivo') as dsModalidad, "
      + " prog.NU_ANNO_DESDE || '/' || (prog.NU_ANNO_DESDE+1) as dsAnnoIni, "
      + " CASE "
      + " WHEN  prog.NU_ANNO_HASTA is null THEN 'Actualidad' "
      + " ELSE prog.NU_ANNO_HASTA || '/' || (prog.NU_ANNO_HASTA+1)  END as dsAnnoFin, "
      + " emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2  as txAutor, "
      + "      (select id_anehis_rodal from FCT_PROGPERFOR_HISTORIAL where id_progperfor = prog.id_progperfor and id_anehis_rodal is not null and rownum =1) as idRodal, "
      + "      (select tx_anehis_fichero from FCT_PROGPERFOR_HISTORIAL where id_progperfor = prog.id_progperfor and id_anehis_rodal is not null and rownum =1) as fichero "
      + "FROM FCT_PROGPERFOR prog, TLOFEMATRGEN omg, TLUSUARIOS usu, TLEMPLEADOS emp "
      + "WHERE prog.X_OFERTAMATRIG = :idCurso "
      + "  AND prog.X_CENTRO = :idCentro "
      //+ "  AND prog.LG_ALCANCE = :lgAlcance "
      + "  AND (-1 = :id OR prog.id_progperfor != :id)   "
      + "  AND prog.LG_MODALIDAD = :lgModalidad "
      + "  AND NVL(prog.NU_ANNO_DESDE, 0) <= :nuAnnoHasta "
      + "  AND :nuAnnoDesde <= NVL(prog.NU_ANNO_HASTA, 9999) "
      + "  AND omg.X_OFERTAMATRIG = prog.X_OFERTAMATRIG "
      + "  AND usu.x_usuario = prog.c_usu_prog "
      + "  AND emp.x_empleado = usu.x_empleado ", nativeQuery = true)
 List<DatosVigentePFEProjection> getExistePFE(Long id, 
    Long idCentro, 
 Long idCurso, 
 Integer nuAnnoDesde, 
 Integer nuAnnoHasta,
 //Integer lgAlcance, 
 Integer lgModalidad);


  
    @Query(value = " select distinct usu.x_usuario as id, "
      + " emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2 as descripcion "
      + "from fct_progperfor prog, tlusuarios usu, tlempleados emp "
      + "where prog.x_centro = :idCentro "
      + "and (-1 = :idCurso OR prog.x_ofertamatrig = :idCurso) "
      + "and (-1 = :idModalidad OR prog.lg_Modalidad = :idModalidad) "
      + "and usu.x_usuario = prog.c_usu_prog "
      + "and emp.x_empleado = usu.x_empleado "
      + "order by descripcion ", nativeQuery = true)
 List<ElementoSelectProjection> getListadoCreadoresProgramasPFEDirectorTutor(Long idCentro, Long idCurso, Long idModalidad);
    
    @Query(value = " select distinct usu.x_usuario as id,      "
      + "      emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2 as descripcion      "
      + "     from fct_progperfor prog, tlusuarios usu, tlempleados emp      "
      + "     where (-1 = :idCentroCombo OR  prog.x_centro = :idCentroCombo)      "
      + "     and (-1 = :idCurso OR prog.x_ofertamatrig = :idCurso)      "
      + "     and (-1 = :idModalidad OR prog.lg_Modalidad = :idModalidad)      "
      + "     and usu.x_usuario = prog.c_usu_prog      "
      + "     and emp.x_empleado = usu.x_empleado "
      + "   and prog.x_centro in (SELECT distinct dcen1.x_centro id  "
      + "   from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,  "
      + "  TLDATOSCEN dcen1, TLCENTROS cen, tldengen den      "
      + "  WHERE u.x_usuario = :idUsuario  "
      + "  AND pop.x_perfil = :idPerfil  "
      + "  AND pto.x_centro = :idCentro    "
      + "  AND pto.x_empleado=u.x_empleado  "
      + "  AND pop.x_empleado=pto.x_empleado   "
      + "  AND pop.f_tomapos = pto.f_tomapos   "
      + "  AND pto.x_centro = dcen.x_centro   "
      + "  AND dcen.c_provincia = prv.c_provincia     "
      + "  AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)   "
      + "  AND dcen1.c_provincia = prv.c_provincia   "
      + "  AND dcen1.l_vigente = 'S'  "
      + "  AND cen.x_centro = dcen1.x_centro  "
      + "  AND cen.l_delegacion = 'N'  "
      + "  AND cen.l_extranjero = 'N') "
      + "     order by descripcion ", nativeQuery = true)
 List<ElementoSelectProjection> getListadoCreadoresProgramasPFEGestor(Long idCentroCombo, Long idUsuario, Long idPerfil, Long idCentro, Long idModalidad, Long idCurso );
    
    @Query(value = " select  "
      + "    2025 id, "
      + "    2025 || '/' || 2026 descripcion "
      + "from tlcursoaca "
      + "where l_actual = 'S' "
      + "and c_anno = 2024 "
      + "UNION "
      + "SELECT c_anno AS id, "
      + " c_anno || '/' || (c_anno + 1) AS descripcion "
      + "FROM tlcursoaca "
      + "WHERE ( "
      + "    ( "
      + "  EXTRACT(MONTH FROM CURRENT_DATE) != 5 "
      + "  AND c_anno = EXTRACT(MONTH FROM CURRENT_DATE) "
      + "    ) "
      + "    OR ( "
      + "  EXTRACT(MONTH FROM CURRENT_DATE) = 5 "
      + "  AND c_anno IN ( "
      + "      EXTRACT(MONTH FROM CURRENT_DATE) - 1, "
      + "      EXTRACT(MONTH FROM CURRENT_DATE) "
      + "  ) "
      + "    )    "
      + ") "
      + "and c_anno != 2024 ", nativeQuery = true)
 List<ElementoSelectProjection> getAnnosIni();

    @Query(value = " select  "
      + "    aca1.c_anno id, "
      + "    aca1.c_anno || '/' || (aca1.c_anno+1) descripcion "
      + "from tlcursoaca aca, tlcursoaca aca1 "
      + "where aca.l_actual = 'S' "
      + "and aca1.c_anno  >= aca.c_anno +1 "
      + "and aca.c_anno = 2024 "
      + "UNION "
      + "select  "
      + "    aca1.c_anno id, "
      + "    aca1.c_anno || '/' || (aca1.c_anno+1) descripcion "
      + "from tlcursoaca aca, tlcursoaca aca1 "
      + "where aca.l_actual = 'S' "
      + "and aca.c_anno != 2024  "
      + "and aca1.c_anno  >= aca.c_anno ", nativeQuery = true)
 List<ElementoSelectProjection> getAnnosFin();

 @Query(value = "select den.s_denominacion || ' ' ||  dat.d_especifica centro,   " +
   " cen.c_codigo codigo,   " +
   " dat.c_nifcen nif,   " +
   " CASE   " +
   " WHEN 'S' = TLF_OFG_CONCERTADA_CENTRO(cen.c_codigo, prog.x_ofertamatrig, prog.c_anno) THEN 'Centro Concertado'    " +
   " WHEN cen.x_tipo = 1 THEN 'Centro Público'   " +
   " ELSE 'Centro Privado' END tipo,    " +
   " CASE   " +
   " WHEN (SELECT distinct 1 " +
   " FROM tlofematrgen omg2 " +
   " WHERE ( " +
   "      prog.c_anno BETWEEN omg2.c_anno AND NVL(omg2.c_annotermina, 9999) " +
   "      OR (prog.c_anno + 1) BETWEEN omg2.c_anno AND NVL(omg2.c_annotermina, 9999) " +
   "  ) " +
   "  AND omg2.x_tipoexp = omg.x_tipoexp " +
   "  AND omg2.n_orden != omg.n_orden " +
   "      ) = 1 THEN  'Ciclo Formativo: ' || omg.d_ofertamatrig    " +
   " ELSE 'Curso de Especialización: ' || omg.d_ofertamatrig END tipocurso,   " +
   " prog.lg_alcance alcance,   " +
   " prog.nu_alumnado nalumnos,   " +
   " prog.lg_ampliacion ampliacion,   " +
   " prog.nu_horas_fpe horaspfe,   " +
   " prog.nu_horas_cur horascur,   " +
   " prog.lg_diario lgDiario,   " +
   " prog.lg_semanal lgSemanal, " +
   " prog.lg_mensual lgMensual,    +  " +
   " prog.lg_otros lgOtros,    +  " +
   " prog.nu_horas_comfpe horascompfe,   " +
   " prog.nu_horas_comcur horasconcur,   " +
   " prog.lg_autorizacion autorizacion,  " +
   " (SELECT LISTAGG(momg.c_codigomec || ' - ' || mcur.d_materiac, ', ')    " +
   "     WITHIN GROUP (ORDER BY momg.c_codigomec)   " +
   "     FROM FCT_MODAUTPROG map     " +
   "     JOIN tlmatofematrg momg ON map.x_materiaomg = momg.x_materiaomg     " +
   "     JOIN tlmateriascurso mcur ON momg.x_materiac = mcur.x_materiac     " +
   "     WHERE map.id_progperfor = prog.id_progperfor   " +
   "     ) AS modulos,   " +
   " prog.ds_coo_modprof dsmod,   " +
   " prog.ds_emp_orgcol dsorg,   " +
   " prog.ds_car_conalt dscara,   " +
   " prog.ds_con_beca dsbeca,  " +
   " prog.lg_aut_400 lgaut400,  " +
   " prog.lg_aut_FORCOM lgautforcom,   " +
   " prog.lg_aut_curesp lgautcuresp,  " +
   " prog.lg_aut_3cur lgaut3cur,  " +
   " prog.lg_aut_camreg lgautcamreg,  " +
   " prog.lg_aut_proens  lgautproens," +
   " prog.lg_modalidad lgModalidad, " +
   " CASE " +
   " WHEN (SELECT distinct 1 " +
   " FROM tlofematrgen omg2 " +
   " WHERE ( " +
   " prog.c_anno BETWEEN omg2.c_anno AND NVL(omg2.c_annotermina, 9999) " +
   " OR (prog.c_anno + 1) BETWEEN omg2.c_anno AND NVL(omg2.c_annotermina, 9999)) " +
   " AND omg2.x_tipoexp = omg.x_tipoexp " +
   " AND omg2.n_orden != omg.n_orden) = 1 THEN 0 " +
   " ELSE 1 END lgTipoCiclo " +
   "from FCT_PROGPERFOR prog, tldatoscen dat, tldengen den, tlcentros cen, tltipocen tip, tlofematrgen omg   " +
   "where prog.id_progperfor = :idProgPerFor   " +
   "and dat.x_centro = prog.x_centro   " +
   "and dat.x_centro = prog.x_centro   " +
   "and den.x_dengen = dat.x_dengen   " +
   "and cen.x_centro = prog.x_centro   " +
   "and tip.x_tipo(+) = cen.x_tipo   " +
   "and omg.x_ofertamatrig = prog.x_ofertamatrig ", nativeQuery = true)
 AnexoVProjection getDatosAnexoV(Long idProgPerFor);

 
 @Query(value = " select * from ( "
   + "     select prog.id_progperfor as id,     "
   + "     tlf_datoscentro(prog.x_centro) as centro, "
   + "      omg.d_ofertamatrig   as curso, "
   + "      CASE  "
   + "      WHEN (select est.ds_abrev   "
   + "            from fct_progperfor_historial his "
   + "            join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo "
   + "            join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des "
   + "            where his.id_progperfor = prog.id_progperfor "
   + "            order by his.fh_registro desc "
   + "            fetch first 1 row only) = 'VBD' THEN 1  "
   + "      ELSE 0 "
   + "      END AS puedeVisto, "
   + "      case      "
   + "      WHEN  omg.d_ofertamatrig  LIKE ('%CFGS%') OR omg.d_ofertamatrig  LIKE ('%C.F.G.S.%')  THEN 'CFGS'     "
   + "      WHEN  omg.d_ofertamatrig  LIKE ('%CFGM%') OR omg.d_ofertamatrig  LIKE ('%C.F.G.M.%')  THEN 'CFGM'     "
   + "      WHEN  omg.d_ofertamatrig  LIKE ('%CFGB%') OR omg.d_ofertamatrig  LIKE ('%C.F.G.B.%')  THEN 'Básico'     "
   + "      WHEN  NOT EXISTS ( "
   + "      SELECT 1 "
   + "      FROM tlofematrgen omg2 "
   + "      WHERE  "
   + "      ( "
   + "   :cAnno BETWEEN omg2.c_anno AND NVL(omg2.c_annotermina, 9999) "
   + "   OR (:cAnno + 1) BETWEEN omg2.c_anno AND NVL(omg2.c_annotermina, 9999) "
   + "     ) "
   + "     AND omg2.x_tipoexp = omg.x_tipoexp "
   + "     AND omg2.n_orden != omg.n_orden "
   + "    "
   + "   ) THEN 'Curso Esp.'     "
   + "      ELSE '' end tipo,     "
   + "      fam.s_familia familia,     "
   + "      emp.nombre || ' ' || emp.apellido1 || ' ' ||emp.apellido2 creador,     "
   + "      decode(prog.lg_modalidad,1,'General','Intensiva') as modalidad,     "
   + " (select est.ds_nombre "
   + "  from fct_progperfor_historial his "
   + "  join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo "
   + "  join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des "
   + "  where his.id_progperfor = prog.id_progperfor "
   + "  order by his.fh_registro desc "
   + "  fetch first 1 row only) as estado, "
   + " (select est.id_estado_autorizacion      "
   + "  from fct_progperfor_historial his      "
   + "  join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo "     
   + "  join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des "     
   + "  where his.id_progperfor = prog.id_progperfor "  
   + "  order by his.fh_registro desc      "
   + "  fetch first 1 row only) as idEstadoAut, "   
   + "  prog.nu_anno_desde || '/' || (prog.nu_anno_desde+1) as cuIni,"
   + "  CASE prog.lg_autorizacion "
   + "  WHEN 1 THEN 'Sí'"
   + "  ELSE 'No'"
   + "  END AS requiereAutorizacion, "
   + "      CASE      "
   + "      WHEN prog.nu_anno_hasta IS NOT NULL THEN prog.nu_anno_hasta || '/' || (prog.nu_anno_hasta+1)      "
   + "      ELSE null END as cuFin,    "
   + "      (select ID_ANEHIS_RODAL  from fct_progperfor_historial where id_progperfor = prog.id_progperfor AND ID_ANEHIS_RODAL IS NOT NULL) AS idRodal,    "
   + "      (select TX_ANEHIS_FICHERO   from fct_progperfor_historial where id_progperfor = prog.id_progperfor AND ID_ANEHIS_RODAL IS NOT NULL) AS fichero,      "
   + "      0 as puedeBorrar,     "   
   + "      (select his.id_progperfor_historial    "   
   + " from fct_progperfor_historial his " 
   + " join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo " 
   + " join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des  "
   + " where his.id_progperfor = prog.id_progperfor  "
   + " and his.id_anehis_rodal is not null "
   + " order by his.fh_registro desc  "
   + " fetch first 1 row only) as idHistorial "
   + "      from fct_progperfor prog, tlofematrgen omg, tlmodalidades mod, tlfamilias fam, tlusuarios usu, tlempleados emp,  "
   + "    (SELECT distinct dcen1.x_centro      "
   + "       from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,     "
   + "      TLDATOSCEN dcen1, TLCENTROS cen, tldengen den   "
   + "      WHERE u.x_usuario = :idUsuario     "
   + "      AND pop.x_perfil = :idPerfil     "
   + "      AND pto.x_centro = :idCentro "
   + "      AND pto.x_empleado=u.x_empleado     "
   + "      AND pop.x_empleado=pto.x_empleado      "
   + "      AND pop.f_tomapos = pto.f_tomapos      "
   + "      AND pto.x_centro = dcen.x_centro      "
   + "      AND dcen.c_provincia = prv.c_provincia  "
   + "      AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)      "
   + "      AND dcen1.c_provincia = prv.c_provincia      "
   + "      AND dcen1.l_vigente = 'S'     "
   + "      AND cen.x_centro = dcen1.x_centro     "
   + "      AND cen.l_delegacion = 'N'     "
   + "      AND cen.l_extranjero = 'N') centros "
   + "      where (-1  = :idCentroCombo OR prog.x_centro = :idCentroCombo)     "
   + "      and omg.x_ofertamatrig = prog.x_ofertamatrig     "
   + "      and mod.x_modalidad = omg.x_modalidad     "
   + "      and fam.x_familia = mod.x_familia     "
   + "      and usu.x_usuario = prog.c_usu_prog     "
   + "      and emp.x_empleado = usu.x_empleado "
   + "      and prog.x_centro = centros.x_centro    "
   + "      and (-1 = :idCurso or omg.x_ofertamatrig = :idCurso)     "
   + "      and (-1 = :idUsuCrea or usu.x_usuario = :idUsuCrea)     "
   + "      and (-1 = :idModalidad or prog.lg_modalidad = :idModalidad)    "
   + "      and (-1 = :idFamilia or fam.x_familia = :idFamilia)    "
   + "      and (-1 = :idCodigo or prog.x_centro = :idCodigo)    "
   + "      and (0 = :lgVigente OR (prog.nu_anno_hasta IS NULL OR prog.nu_anno_hasta > :cAnno)) "
   + "      and (:reqAutorizacion = -1 OR :reqAutorizacion = prog.lg_autorizacion) "
   + "    ) "
   + "      where (-1 = :idEstado OR idEstadoAut = :idEstado)    "
   + "      order by curso ", nativeQuery = true)
 List<ListadoPFEProjection> getListadoPFEDelegacion(Long idCentro, Integer cAnno, Long idCurso,
   Long idPerfil, Long idCentroCombo, Long idUsuCrea, Long idModalidad, Long idFamilia, Long idUsuario, Long idCodigo, Integer lgVigente,  Integer idEstado,Integer reqAutorizacion );


 @Query(value = " select * from ( "
    + "  select prog.id_progperfor as id, "
   + " omg.d_ofertamatrig   as curso,   "
   + " case  "
   + " WHEN  omg.d_ofertamatrig  LIKE ('%CFGS%') OR omg.d_ofertamatrig  LIKE ('%C.F.G.S.%')  THEN 'CFGS' "
   + " WHEN  omg.d_ofertamatrig  LIKE ('%CFGM%') OR omg.d_ofertamatrig  LIKE ('%C.F.G.M.%')  THEN 'CFGM' "
   + " WHEN  omg.d_ofertamatrig  LIKE ('%CFGB%') OR omg.d_ofertamatrig  LIKE ('%C.F.G.B.%')  THEN 'Básico' "
   + " WHEN  NOT EXISTS (   "
   + "       SELECT 1   "
   + "       FROM tlofematrgen omg2   "
   + "       WHERE    "
   + "       (   "
   + "    :cAnno BETWEEN omg2.c_anno AND NVL(omg2.c_annotermina, 9999)   "
   + "    OR (:cAnno + 1) BETWEEN omg2.c_anno AND NVL(omg2.c_annotermina, 9999)   "
   + "      )   "
   + "      AND omg2.x_tipoexp = omg.x_tipoexp   "
   + "      AND omg2.n_orden != omg.n_orden   "
   + "       "
   + "    ) THEN 'Curso Esp.' "
   + " ELSE '' end tipo, "
   + " fam.s_familia familia, "
   + " emp.nombre || ' ' || emp.apellido1 || ' ' ||emp.apellido2 creador, "
   + " decode(prog.lg_modalidad,1,'General','Intensiva') as modalidad, "
   + " (select est.ds_nombre "
   + "  from fct_progperfor_historial his "
   + "  join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo "
   + "  join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des "
   + "  where his.id_progperfor = prog.id_progperfor "   
   + "  order by his.fh_registro desc "
   + "  fetch first 1 row only) as estado, "   
   + " (select est.id_estado_autorizacion      "
   + "  from fct_progperfor_historial his      "
   + "  join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo "     
   + "  join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des "     
   + "  where his.id_progperfor = prog.id_progperfor "  
   + "  order by his.fh_registro desc      "
   + "  fetch first 1 row only) as idEstadoAut, "   
   + " prog.nu_anno_desde || '/' || (prog.nu_anno_desde+1) as cuIni, "
   + " CASE  "
   + " WHEN prog.nu_anno_hasta IS NOT NULL THEN prog.nu_anno_hasta || '/' || (prog.nu_anno_hasta+1)  "
   + " ELSE null END as cuFin,"
   + " (select ID_ANEHIS_RODAL  from fct_progperfor_historial where id_progperfor = prog.id_progperfor AND ID_ANEHIS_RODAL IS NOT NULL) AS idRodal,"
   + " (select TX_ANEHIS_FICHERO   from fct_progperfor_historial where id_progperfor = prog.id_progperfor AND ID_ANEHIS_RODAL IS NOT NULL) AS fichero,  "
   + " CASE "
   + " WHEN :xPerfil = 2 AND prog.c_usu_prog = :xUsuario THEN 1 "
      + " WHEN :xPerfil = 161 THEN 1 "
      + " ELSE 0 END puedeBorrar, "
   + "    CASE prog.lg_autorizacion "
   + "    WHEN 1 THEN 'Sí'"
   + "    ELSE 'No'"
   + "    END AS requiereAutorizacion, "
   + "    CASE  "
   + "    WHEN :esDirector = 1 AND (select est.ds_abrev   "
   + "   from fct_progperfor_historial his "
   + "   join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo "
   + "   join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des "
   + "   where his.id_progperfor = prog.id_progperfor "
   + "   order by his.fh_registro desc "
   + "   fetch first 1 row only) = 'PBD' THEN 1  "
   + "     ELSE 0 "
   + " END AS puedeVisto, "   
   + " (select his.id_progperfor_historial "      
   + "  from fct_progperfor_historial his " 
   + "     join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo " 
   + "     join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des  "
   + "     where his.id_progperfor = prog.id_progperfor "
   + "     and his.id_anehis_rodal is not null "
   + "     order by his.fh_registro desc "
   + "     fetch first 1 row only) as idHistorial "   
   + "from fct_progperfor prog, tlofematrgen omg, tlmodalidades mod, tlfamilias fam, tlusuarios usu, tlempleados emp "
   + "where prog.x_centro = :idCentro "
   + "and omg.x_ofertamatrig = prog.x_ofertamatrig "
   + "and mod.x_modalidad = omg.x_modalidad "
   + "and fam.x_familia = mod.x_familia "
   + "and usu.x_usuario = prog.c_usu_prog "
   + "and emp.x_empleado = usu.x_empleado "
   + "and (-1 = :idCurso or omg.x_ofertamatrig = :idCurso) "
   + "and (-1 = :idUsuCrea or usu.x_usuario = :idUsuCrea) "
   + "and (-1 = :idModalidad or prog.lg_modalidad = :idModalidad)"
   + "and (-1 = :idFamilia or fam.x_familia = :idFamilia) "
   + " and (:reqAutorizacion = -1 OR :reqAutorizacion = prog.lg_autorizacion) "
   + " and (0 = :lgVigente OR (prog.nu_anno_hasta IS NULL OR prog.nu_anno_hasta > :cAnno)) "
   + "    ) "
   + "  where (-1 = :idEstado OR idEstadoAut = :idEstado) "    
   + "  order by curso  ", nativeQuery = true)
 List<ListadoPFEProjection> getListadoPFE(Long idCentro, Integer cAnno, Long idCurso, Long idUsuCrea,
   Long idModalidad, Long idFamilia, Long xPerfil, Long xUsuario,Integer lgVigente,Integer esDirector, Integer idEstado,Integer reqAutorizacion);

 @Query(value = "select 1 from tlofematrgen "
 		+ "where x_ofertamatrig = :idCurso  "
 		+ "and d_ofertamatrig LIKE '%LOFP%' "
 		+ "and d_ofertamatrig LIKE '%Esp.%'  ", nativeQuery = true)
 Integer obtenerTipoPrograma(Long idCurso);
 
 @Query(value = "select CASE  "
   + "    WHEN :xPerfil = 2 AND prog.c_usu_prog = :xUsuario AND  "
   + "       (select est.ds_abrev  "
   + "        from fct_progperfor_historial his  "
   + "        join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo  "
   + "        join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des  "
   + "        where his.id_progperfor = prog.id_progperfor    "
   + "        order by his.fh_registro desc  "
   + "       fetch first 1 row only) in ('PSUBP','ANEG') AND :esJefe = 0  THEN 1 "
   + "    WHEN :xPerfil = 2 AND   "
   + "        (select est.ds_abrev  "
   + "         from fct_progperfor_historial his  "
   + "         join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo  "
   + "         join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des  "
   + "         where his.id_progperfor = prog.id_progperfor    "
   + "         order by his.fh_registro desc  "
   + "         fetch first 1 row only) in ('PVJ','PSUBP','ANEG') AND :esJefe = 1  THEN 1    "
   + "    WHEN :xPerfil = 161 AND "
   + "         (select est.ds_abrev  "
   + "          from fct_progperfor_historial his "
   + "          join fct_autorizaciones_flujo flujo on flujo.id_autorizacion_flujo = his.id_autorizacion_flujo  "
   + "          join fct_estados_autorizaciones est on est.id_estado_autorizacion = flujo.id_estado_des  "
   + "          where his.id_progperfor = prog.id_progperfor "
   + "          order by his.fh_registro desc  "
   + "          fetch first 1 row only) in ('VBD','VCF','DCF') THEN 0    "
   + "    WHEN :xPerfil = 161 THEN 1  "
   + "    ELSE 0 END puede  "
   + "   FROM fct_progperfor prog "
   + "   WHERE prog.id_progperfor = :id ", nativeQuery = true)
 Integer puedeModificar(Long id, Long xPerfil, Long xUsuario, Integer esJefe );

 @Query(value = " select emp.nombre ||' '|| emp.apellido1 ||' '|| emp.apellido2 AS NOMBRE,   "
   + "       est.ds_nombre AS ESTADO,    "
   + "       his.fh_registro AS FECHA,   "
   + "       his.tx_observaciones AS OBSERVACIONES,   "
   + "       decode(his.fh_registro,MAX(his.fh_registro) OVER (PARTITION BY his.id_progperfor),'S','N') AS ACTUAL,   "
   + "       est.ds_abrev AS abreviatura   "
   + "       from fct_progperfor_historial his ,    "
   + "      fct_autorizaciones_flujo flu,   "
   + "      FCT_ESTADOS_AUTORIZACIONES est,   "
   + "      tlusuarios usu,   "
   + "      tlempleados emp   "
   + "       where his.id_progperfor = :id   "
   + "       and flu.id_tipo_autorizacion = 5   "
   + "       and his.id_autorizacion_flujo = flu.id_autorizacion_flujo   "
   + "       and flu.id_estado_des = est.id_estado_autorizacion    "
   + "       and usu.x_usuario = his.x_usuario    "
   + "       and emp.x_empleado = usu.x_empleado   "
   + "       order by FECHA desc ", nativeQuery = true)
List<HistoricoFlujoAutorizacionProjection> getHistoricoFlujoAutorizacionPFE(Long id);


 @Query(value = " select d_ofertamatrig from tlofematrgen where x_ofertamatrig = :idCurso ", nativeQuery = true)
 String getNombreCurso(Long idCurso);

}




