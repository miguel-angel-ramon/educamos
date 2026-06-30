package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP;

import java.util.List;
import java.util.Optional;

import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection.DatosTutorYResponsableProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection.ListadoSeguimientoLOFPProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ModuloModalidadProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.Alumnado;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.QAlumnado;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.ListadoAlumnadoTutorProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.UnidadCursoProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.CursoModalidadProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AlumnadoLOFPRepository extends AbstractRepository<Alumnado, Long, QAlumnado> {

 @Query(value= " SELECT distinct x_ofertamatrig AS idOfertamatrig, d_ofertamatrig AS curso, c_anno AS cAnno, n_ordenpres as orden FROM (          "
   + "           select omg.x_ofertamatrig , omg.d_ofertamatrig , hor.c_anno, omg.n_ordenpres       "
   + "    from tluniafetrahor UAT, tlhorariosr HOR, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,    "
   + "         FCT_TUTORFCTDUAL tut    "
   + "    where (-1 = :idTutor OR tut.id_tutorfctdual = :idTutor)    "
   + "    and HOR.X_EMPLEADO = tut.X_EMPLEADO     "
   + "    AND HOR.F_TOMAPOS IN (select pto1.f_tomapos from tlptotraemp pto1 , tlcursoaca aca         "
   + "           where aca.c_anno = :cAnno         "
   + "           and TLF_INTERSECPER( pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final ) = 1         "
   + "           and pto1.x_centro  = :idCentro       "
   + "           and pto1.x_empleado =  hor.x_empleado)         "
   + "    and HOR.C_ANNO = :cAnno    "
   + "    and UAT.X_HORARIORE = HOR.X_HORARIORE    "
   + "     and uni.x_unidad = UAT.x_unidad    "
   + "    and omc.x_ofertamatric =  uni.x_ofertamatric     "
   + "    and omg.x_ofertamatrig =  omc.x_ofertamatrig    "
   + "    and UAT.x_ofertamatric = omc.x_ofertamatric    "
   + "    and d_ofertamatrig like '%LOFP%') order by n_ordenpres ", nativeQuery = true)
 List<CursoModalidadProjection> findCursosEmpleadoCentroLOFP(Long idTutor, Long idCentro,
   Integer cAnno);

 @Query(value= "SELECT distinct x_ofertamatrig AS idOfertamatrig, d_ofertamatrig AS curso, c_anno AS cAnno, n_ordenpres as orden FROM ( "
   + "   select omg.x_ofertamatrig , omg.d_ofertamatrig , hor.c_anno, omg.n_ordenpres        "     
     + " from tluniafetrahor UAT, tlhorariosr HOR, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg, "         
     + "     FCT_TUTORFCTDUAL tut          "
     + " where (-1 = :idTutor OR tut.id_tutorfctdual = :idTutor) "         
     + " and HOR.X_EMPLEADO = tut.X_EMPLEADO          "
     + " and HOR.C_ANNO = :cAnno         "
     + " and UAT.X_HORARIORE = HOR.X_HORARIORE    "     
     + " and uni.x_unidad = UAT.x_unidad         "
     + " and omc.x_ofertamatric =  uni.x_ofertamatric    "     
     + " and omg.x_ofertamatrig =  omc.x_ofertamatrig  "         
     + " and UAT.x_ofertamatric = omc.x_ofertamatric "      
     + " and d_ofertamatrig like '%LOFP%' "
     + " and (-1 = :idCentroCombo OR omc.x_centro = :idCentroCombo) "
     + " and omc.x_centro IN (SELECT distinct dcen1.x_centro id  "
     + "          from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , " 
     + "          TLDATOSCEN dcen1, TLCENTROS cen " 
     + "    WHERE u.x_usuario = :idUsuario  "
     + "    AND pop.x_perfil = :idPerfil "
     + "    AND pto.x_centro = :idCentro  " 
     + "    AND pto.x_empleado=u.x_empleado  "
     + "    AND pop.x_empleado=pto.x_empleado  "
     + "    AND pop.f_tomapos = pto.f_tomapos  "
     + "    AND pto.x_centro = dcen.x_centro  "
     + "    AND dcen.c_provincia = prv.c_provincia   " 
     + "    AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) " 
     + "    AND dcen1.c_provincia = prv.c_provincia " 
     + "    AND dcen1.l_vigente = 'S'  "
     + "    AND cen.x_centro = dcen1.x_centro " 
     + "    AND cen.l_delegacion = 'N'  "
     + "    AND cen.l_extranjero = 'N') ) order by n_ordenpres ", nativeQuery = true) 
 List<CursoModalidadProjection> findCursosEmpleadoDelegacionLOFP(Long idTutor,
        Long idCentro,
        Integer cAnno,
        Long idPerfil,
        Long idCentroCombo,
        Long idUsuario);

  @Query(value= "SELECT distinct x_ofertamatrig AS idOfertamatrig, d_ofertamatrig AS curso, c_anno AS cAnno, n_ordenpres as orden FROM ( "
    + "   SELECT A.x_ofertamatrig , A.d_ofertamatrig , hor.c_anno, A.n_ordenpres " 
          + "         FROM TLUNIAFETRAHOR UAT, TLHORARIOSR HOR, TLFAMILIAS fam, TLMODALIDADES mod, FCT_TUTORFCTDUAL tut, TLACTIVIDADES act, tletapas eta, tlpargen par, "
          + "   (SELECT MOG.X_MATERIAOMG, MAC.T_ABREV, MAC.S_MATERIAC, omg.X_MODALIDAD, omg.x_ofertamatrig, omg.d_ofertamatrig, omg.n_ordenpres FROM TLMATOFEMATRG MOG, TLOFEMATRGEN omg, "
          + "    TLMATERIASCURSO MAC WHERE MOG.X_MATERIAC = MAC.X_MATERIAC AND OMG.X_OFERTAMATRIG = MOG.X_OFERTAMATRIG ) A "
          + "   WHERE d_ofertamatrig like '%LOFP%' "
          + "   AND  (-1 = :idTutor OR tut.id_tutorfctdual = :idTutor)  "      
          + "   AND HOR.X_EMPLEADO = tut.X_EMPLEADO    "     
          + "   AND HOR.F_TOMAPOS IN (select pto1.f_tomapos from tlptotraemp pto1 , tlcursoaca aca " 
          + "          where aca.c_anno = :cAnno  "
          + "          and TLF_INTERSECPER( pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final ) = 1 " 
          + "   and (-1 = :idCentro OR pto1.x_centro = :idCentro) "
          + "   and pto1.x_centro IN (SELECT distinct dcen1.x_centro id  "
          + "       from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , " 
          + "       TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv "  
          + "       WHERE (-1 = :idProvincia or dcen.c_provincia = :idProvincia) "
          + "       AND dcen.c_provincia = prv.c_provincia "   
          + "       AND dcen.l_vigente = 'S' "
          + "       AND cen.x_centro = dcen.x_centro " 
          + "       AND cen.l_delegacion = 'N'  "
          + "       AND cen.l_extranjero = 'N') "
          + "          and pto1.x_empleado =  hor.x_empleado) " 
          + "   AND HOR.C_ANNO = :cAnno       "
          + "   AND UAT.X_HORARIORE = HOR.X_HORARIORE   "    
          + "   AND UAT.X_MATERIAOMG = A.X_MATERIAOMG  "          
          + "   and act.x_actividad = HOR.x_actividad  "         
          + "   and par.t_nompar in ('ID_ETAPA_CFGM', "
          + " 'ID_ETAPA_CFGS', "
          + " 'ID_ETAPA_GFB', "
          + " 'ID_ETAPA_CFGMAPD', "
          + " 'ID_ETAPA_CFGSAPD', "
          + " 'ID_ETAPA_CFGM_APD_LOE', "
          + " 'ID_ETAPA_CFGS_APD_LOE', "
          + "            'ID_ETAPA_EASD', "
          + " 'ID_ETAPA_CFMCURESP', "
          + " 'ID_ETAPA_CFCURESP', "
          + " 'ID_ETAPA_CFGMAPD', "
          + " 'ID_ETAPA_CFGSAPD', "
          + " 'ID_ETAPA_CFGM_APD_LOE', "
          + " 'ID_ETAPA_CFGS_APD_LOE', "
          + " 'TIP_LIB_PEFP', "
          + " 'ID_ETAPA_CFGMPRESLOE', "
          + " 'ID_ETAPA_CFGSPRESLOE') "
          + "   and eta.x_etapa = par.t_valpar     "
          + "   and tlf_omgcuelgaetapa( A.x_ofertamatrig, eta.x_etapa) = 1  ) "     
          + "          order by n_ordenpres ", nativeQuery = true)
 List<CursoModalidadProjection> findCursosEmpleadoDelegacionProvinciasLOFP(Long idTutor,
            Long idCentro,
            Integer cAnno,
            Long idProvincia);

 @Query(value= " select omg.x_ofertamatrig as idOfertamatrig,        "
   + "        omg.d_ofertamatrig as curso        "
   + "        from delphos_segedu.tlempleados empl, tlalumnos alu, "
   + "        FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,        "
   + "        TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg        "
   + "        where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)    "
   + "        and empl.x_empleado = :idEmpleadoComunica "
   + "        and alu.c_numide = empl.c_numide "
   + "        and mat.x_alumno = alu.x_alumno "
   + "        and tut.id_tutorfctdual = pro.id_tutorfctdual        "
   + "        and pro.id_programa = cp.id_programa        "
   + "        and cp.id_conv_prog = cpa.id_conv_prog        "
   + "        and cpa.x_matricula = mat.x_matricula        "
   + "        and cp.id_convenio = con.id_convenio        "
   + "        and mat.x_ofertamatrig = omg.x_ofertamatrig        "
   + "        and pro.x_centro = :idCentro        "
   + "        and :cAnno between pro.c_anno_desde and pro.c_anno_hasta        "
   + "        and :tipoEmpresa in (-1,1)        "
   + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)        "
   + "        union        "
   + "        select omg.x_ofertamatrig as idOfertamatrig,        "
   + "        omg.d_ofertamatrig as curso        "
   + "        from delphos_segedu.tlempleados empl, tlalumnos alu, "
   + "        FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,        "
   + "        TLMATALU mat, FCT_CONVENIOS con, TLOFEMATRGEN omg        "
   + "        where (-1 = :idTutorfctdual OR  tut.id_tutorfctdual = :idTutorfctdual)   "
   + "        and empl.x_empleado = :idEmpleadoComunica "
   + "        and alu.c_numide = empl.c_numide "
   + "        and mat.x_alumno = alu.x_alumno "
   + "        and tut.id_tutorfctdual = pro.id_tutorfctdual        "
   + "        and pro.id_proyecto = cp.id_proyecto        "
   + "        and cp.id_conv_proy = cpa.id_conv_proy        "
   + "        and cpa.x_matricula = mat.x_matricula        "
   + "        and cp.id_convenio = con.id_convenio        "
   + "        and mat.x_ofertamatrig = omg.x_ofertamatrig        "
   + "        and pro.x_centro = :idCentro        "
   + "        and :cAnno between pro.c_anno_desde and pro.c_anno_hasta        "
   + "        and :tipoEmpresa in (-1,2)        "
   + "        and ( :idEmpresa = -1 or :idEmpresa = con.x_empresa)        "
   + "        order by curso ", nativeQuery = true)
List<CursoModalidadProjection> findCursosEmpleadoCentroAlumnadoLOFP(Long idTutorfctdual,
     Long idCentro,
     Integer cAnno,
     Long idEmpleadoComunica);


 @Query(value= "SELECT distinct x_unidad AS id, t_nombre as nombre FROM (     "
   + "   select  uni.x_unidad , unid.t_nombre        "
   + "    from tluniafetrahor UAT, tlhorariosr HOR, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,     "
   + "    FCT_TUTORFCTDUAL tut, tlunidadescen unid    "
   + "    where (-1 = :idTutor OR tut.id_tutorfctdual = :idTutor)     "
   + "    and HOR.X_EMPLEADO = tut.X_EMPLEADO     "
   + "    AND HOR.F_TOMAPOS IN (select pto1.f_tomapos from tlptotraemp pto1 , tlcursoaca aca          "
   + "      where aca.c_anno = :cAnno          "
   + "      and TLF_INTERSECPER( pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final ) = 1          "
   + "      and pto1.x_centro  = :idCentro        "
   + "      and pto1.x_empleado =  hor.x_empleado)          "
   + "           and HOR.C_ANNO = :cAnno     "
   + "           and UAT.X_HORARIORE = HOR.X_HORARIORE     "
   + "           and uni.x_unidad = UAT.x_unidad     "
   + "           and omc.x_ofertamatric =  uni.x_ofertamatric     "
   + "           and omg.x_ofertamatrig =  omc.x_ofertamatrig        "
   + "           and (-1 = :idOfertamatrig OR omg.x_ofertamatrig = :idOfertamatrig)        "
   + "           and UAT.x_ofertamatric = omc.x_ofertamatric        "
   + "           and unid.x_unidad = UAT.x_unidad          "
   + "           and d_ofertamatrig like '%LOFP%') order by t_nombre ", nativeQuery = true)
 List<UnidadCursoProjection> getUnidadesLOFP(Long idTutor, Long idCentro,
            Integer cAnno, Long idOfertamatrig);

 @Query(value= " SELECT distinct tut.id_tutorfctdual as id,            "
   + "        EMP.APELLIDO1 || ' ' || EMP.APELLIDO2  ||', ' || EMP.NOMBRE  as descripcion             "
   + "      from tluniafetrahor UAT, tlhorariosr HOR, TLACTIVIDADES act, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,                    "
   + "             FCT_TUTORFCTDUAL tut, tlempleados emp                    "
   + "      where tut.X_EMPLEADO = HOR.X_EMPLEADO    "
   + "      and emp.x_empleado = tut.x_empleado           "
   + "      and HOR.X_EMPLEADO = tut.X_EMPLEADO                     "
   + "      and HOR.C_ANNO = :cAnno                    "
   + "      and UAT.X_HORARIORE = HOR.X_HORARIORE                    "
   + "      and uni.x_unidad = UAT.x_unidad                    "
   + "      and omc.x_ofertamatric =  uni.x_ofertamatric                    "
   + "      and omg.x_ofertamatrig =  omc.x_ofertamatrig                     "
   + "      and UAT.x_ofertamatric = omc.x_ofertamatric                     "
   + "      and act.x_actividad = HOR.x_actividad                     "
   + "      and d_ofertamatrig like '%LOFP%'             "
   + "      and omc.x_centro = :idCentro "
   + "      and (-1 = :idTutor OR tut.id_tutorfctdual = :idTutor) "
   + "      order by descripcion ", nativeQuery = true)
List<ElementoSelectProjection> findTutoresCentroLOFP(Long idCentro, Integer cAnno, Long idTutor);
 
 @Query(value= "SELECT distinct tut.id_tutorfctdual as id, "
  + " EMP.APELLIDO1 || ' ' || EMP.APELLIDO2  ||', ' || EMP.NOMBRE  as descripcion  "
     + " from tluniafetrahor UAT, tlhorariosr HOR, TLACTIVIDADES act, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,   "      
     + "     FCT_TUTORFCTDUAL tut, tlempleados emp   "      
     + " where tut.X_EMPLEADO = HOR.X_EMPLEADO"
     //+ " and tut.f_tomapos = HOR.f_tomapos "
     + " and emp.x_empleado = tut.x_empleado"
     + " and HOR.X_EMPLEADO = tut.X_EMPLEADO          "
     + " and HOR.C_ANNO = :cAnno         "
     + " and UAT.X_HORARIORE = HOR.X_HORARIORE       "  
     + " and uni.x_unidad = UAT.x_unidad         "
     + " and omc.x_ofertamatric =  uni.x_ofertamatric     "    
     + " and omg.x_ofertamatrig =  omc.x_ofertamatrig       "   
     + " and UAT.x_ofertamatric = omc.x_ofertamatric         " 
     + " and act.x_actividad = HOR.x_actividad          "
     + " and d_ofertamatrig like '%LOFP%'  "
     + " and (-1 = :idCentroCombo or omc.x_centro = :idCentroCombo) "
     + " and omc.x_centro in (SELECT distinct dcen1.x_centro id     "
     + "     from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,       "         
     + "          TLDATOSCEN dcen1, TLCENTROS cen     "          
     + "    WHERE u.x_usuario = :idUsuario     "           
     + "    AND pop.x_perfil = :idPerfil       "         
     + "   AND pto.x_centro = :idCentro         "       
     + "    AND pto.x_empleado=u.x_empleado     "           
     + "    AND pop.x_empleado=pto.x_empleado      "           
     + "    AND pop.f_tomapos = pto.f_tomapos       "          
     + "    AND pto.x_centro = dcen.x_centro         "        
     + "    AND dcen.c_provincia = prv.c_provincia      " 
     + "    AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)    "  
     + "    AND dcen1.c_provincia = prv.c_provincia       "          
     + "    AND dcen1.l_vigente = 'S'          "      
     + "    AND cen.x_centro = dcen1.x_centro   "  
     + "    AND cen.l_delegacion = 'N'          "      
     + "    AND cen.l_extranjero = 'N')     "
     + " order by descripcion ", nativeQuery = true) 
List<ElementoSelectProjection> findTutoresDelegacionLOFP(Long idCentro, 
          Integer cAnno,
          Long idPerfil, 
          Long idCentroCombo,
          Long idUsuario); 
 
 

 @Query(value= "SELECT distinct cen.x_centro id , tlf_datoscentro(cen.x_centro) descripcion          "
   + "           from   tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,     "
   + "    tlcentros cen, tlperiodosomc per     "
   + "           where omc.x_ofertamatric =  uni.x_ofertamatric     "
   + "           and omg.x_ofertamatrig =  omc.x_ofertamatrig  "
   + "           and d_ofertamatrig like '%LOFP%'         "
   + "           and cen.x_centro =  omc.x_centro "
   + "           and per.x_ofertamatric = omc.x_ofertamatric "
   + "           AND :cAnno BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 2099) " 
   + "           and omc.x_centro in (SELECT distinct dcen1.x_centro id            "
   + "    from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,            "
   + "         TLDATOSCEN dcen1, TLCENTROS cen           "
   + "   WHERE u.x_usuario = :idUsuario            "
   + "   AND pop.x_perfil = :idPerfil            "
   + " AND pto.x_centro = :idCentro            "
   + "   AND pto.x_empleado=u.x_empleado            "
   + "   AND pop.x_empleado=pto.x_empleado "
   + "   AND pop.f_tomapos = pto.f_tomapos "
   + "   AND pto.x_centro = dcen.x_centro "
   + "   AND dcen.c_provincia = prv.c_provincia   "
   + "   AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
   + "   AND dcen1.c_provincia = prv.c_provincia "
   + "   AND dcen1.l_vigente = 'S'            "
   + "   AND cen.x_centro = dcen1.x_centro            "
   + "   AND cen.l_delegacion = 'N'            "
   + "   AND cen.l_extranjero = 'N')            "
   + "           order by descripcion ", nativeQuery = true) 
List<ElementoSelectProjection> allCentroDelegacionLOFP(Long idUsuario, 
     Long idPerfil, 
     Long idCentro,
     Integer cAnno);

 
 @Query(value= "SELECT distinct x_unidad AS id, t_nombre as nombre FROM ( "          
   + "    select  uni.x_unidad , unid.t_nombre  "
     + "from tluniafetrahor UAT, tlhorariosr HOR, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,    "      
     + "     FCT_TUTORFCTDUAL tut, tlunidadescen unid         "
     + "where (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)    "      
     + "and HOR.X_EMPLEADO = tut.X_EMPLEADO          "
     + "and HOR.C_ANNO = :cAnno          "
     + "and UAT.X_HORARIORE = HOR.X_HORARIORE          "
     + "and uni.x_unidad = UAT.x_unidad          "
     + "and omc.x_ofertamatric =  uni.x_ofertamatric         " 
     + "and omg.x_ofertamatrig =  omc.x_ofertamatrig "
     + "and (-1 = :idCentroCombo or omc.x_centro = :idCentroCombo) "
     + "and (-1 = :idOfertamatrig OR omg.x_ofertamatrig = :idOfertamatrig)  "
     + "and UAT.x_ofertamatric = omc.x_ofertamatric  "
     + "and unid.x_unidad = UAT.x_unidad "
     + "and d_ofertamatrig like '%LOFP%' "
     + "and omc.x_centro in (SELECT distinct dcen1.x_centro id      "           
     + "     from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv ,     " 
      + "         TLDATOSCEN dcen1, TLCENTROS cen       "          
     + "    WHERE u.x_usuario = :idUsuario     "           
     + "    AND pop.x_perfil = :idPerfil      "          
     + "    AND pto.x_centro = :idCentro       "         
     + "    AND pto.x_empleado=u.x_empleado     "           
     + "    AND pop.x_empleado=pto.x_empleado    " 
     + "    AND pop.f_tomapos = pto.f_tomapos      "          
     + "    AND pto.x_centro = dcen.x_centro    " 
     + "    AND dcen.c_provincia = prv.c_provincia      "           
     + "    AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese)     "           
     + "    AND dcen1.c_provincia = prv.c_provincia      "           
     + "    AND dcen1.l_vigente = 'S'        "          
     + "    AND cen.x_centro = dcen1.x_centro     " 
      + "   AND cen.l_delegacion = 'N'       "          
     + "    AND cen.l_extranjero = 'N') "
     + ") order by t_nombre ", nativeQuery = true) 
List<UnidadCursoProjection> findUnidadesEmpleadoDelegacionLOFP(Long idTutorfctdual, 
 Long idCentro, 
 Integer cAnno,
 Long idOfertamatrig, 
 Long idPerfil, 
 Long idCentroCombo, 
 Long idUsuario);

 /*@Query(value= "SELECT nombreAlumno, tnuss, nussActualizado, descripcion, nombreEmpresa, familia, curso, orden, seguridad, xMatricula, idCentro, estado, nussProvisional,  "
   + "       CASE "
   + "       WHEN :idPerfil = 161 AND descripcion IS NOT NULL THEN 1 " 
   + "       WHEN :idTutorfctdual != -1 AND id_tutorfctdual IS NOT NULL AND id_tutorfctdual = :idTutorfctdual AND descripcion IS NOT NULL  THEN 1 "
  + "       WHEN :idTutorfctdual != -1 AND id_tutorfctdual IS NOT NULL AND descripcion IS NOT NULL AND id_tutorfctdual = ( "
  + "               select dual2.id_tutorfctdual from fct_tutorfctdual dual, fct_tutorfctdual dual2, tlptotraemp tra where dual.id_tutorfctdual = :idTutorfctdual "
  + "                and tra.x_empleado = dual.x_empleado and tra.x_empleado_sustituye = dual2.x_empleado "
  + "        ) THEN 1 "
   + "       WHEN :idTutorfctdual = -1 AND descripcion IS NOT NULL THEN 1 "
   + "       ELSE 0 END puedeEvaluar "
   + "     FROM (            "
   + "WITH             "
   + "            Proyectos AS (                  "
   + "                SELECT coya.x_matricula,                   "
   + "                       LISTAGG(DISTINCT proy.ds_proyecto, '/') WITHIN GROUP (ORDER BY proy.ds_proyecto) AS descripcion,            "
   + "                       proy.x_familia,            "
   + "                       proy.x_ofertamatrig,   "
   + "                            proy.id_tutorfctdual "
   + "                FROM fct_convproy_alu coya                  "
   + "                JOIN fct_conv_proy cpy ON coya.id_conv_proy = cpy.id_conv_proy                  "
   + "                JOIN fct_proyectos proy ON cpy.id_proyecto = proy.id_proyecto                  "
   + "                GROUP BY coya.x_matricula, x_familia, x_ofertamatrig, id_tutorfctdual                   "
   + "            ),                  "
   + "            Empresas AS (                  "
   + "                SELECT coya.x_matricula,                   "
   + "                       LISTAGG(DISTINCT emp.d_empresa, '/') WITHIN GROUP (ORDER BY emp.d_empresa) AS nombreEmpresa                  "
   + "                FROM fct_convproy_alu coya                  "
   + "                JOIN fct_conv_proy cpy ON coya.id_conv_proy = cpy.id_conv_proy                  "
   + "                JOIN fct_convenios con ON cpy.id_convenio = con.id_convenio                  "
   + "                JOIN tlempresas emp ON con.x_empresa = emp.x_empresa                  "
   + "                GROUP BY coya.x_matricula                  "
   + "            ),                  "
   + "            Familias AS (                  "
   + "                SELECT mod.x_modalidad,                   "
   + "                       fam.s_familia AS familia,            "
   + "                       fam.x_familia AS x_familia            "
   + "                FROM tlmodalidades mod                  "
   + "                JOIN tlfamilias fam ON mod.x_familia = fam.x_familia                  "
   + "            ),                "
   + "            Validaciones AS (               "
   + "                SELECT v1.X_MATRICULA, v1.NU_ORDEN, v1.DS_ESTADO            "
   + "                FROM FCT_VALIDACIONES_ALUPLAN v1            "
   + "                JOIN (            "
   + "                    SELECT X_MATRICULA, MAX(NU_ORDEN) AS MAX_ORDEN            "
   + "                    FROM FCT_VALIDACIONES_ALUPLAN            "
   + "                    GROUP BY X_MATRICULA            "
   + "                ) v2             "
   + "                ON v1.X_MATRICULA = v2.X_MATRICULA             "
   + "                AND v1.NU_ORDEN = v2.MAX_ORDEN            "
   + "            )            "
   + "            SELECT DISTINCT                   "
   + "                alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre AS nombreAlumno,                  "
   + "                alu.t_nuss AS tnuss,                  "
   + "                NVL((SELECT CD_NUSS_OLD FROM ( "
   + "                        SELECT CD_NUSS_OLD "
   + "                        FROM FCT_CORRECCIONES_NUSS cn "
   + "                        WHERE cn.X_MATRICULA = mat.x_matricula "
   + "                        ORDER BY cn.ID_CORRECCION_NUSS DESC "
   + "                    ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
   + "                pr.descripcion,                  "
   + "                em.nombreEmpresa,                  "
   + "                fam.familia,                  "
   + "                (select d_ofertamatrig from tlofematrgen where x_ofertamatrig = pr.x_ofertamatrig) AS curso,             "
   + "                CASE             "
   + "                WHEN (select n_orden from tlofematrgen where x_ofertamatrig = pr.x_ofertamatrig) != null THEN (select n_orden from tlofematrgen where x_ofertamatrig = pr.x_ofertamatrig) || 'º'              "
   + "                ELSE '' END AS orden,            "
   + "                '1' AS seguridad,                  "
   + "                mat.x_matricula AS xMatricula,                  "
   + "                     mat.x_centro AS idCentro,              "
   + "                val.ds_estado AS estado, "
   + "                     pr.id_tutorfctdual, "
  + "           CASE WHEN alu.t_nuss LIKE '85%' THEN 1  "
  + "           ELSE 0 END AS nussProvisional "
   + "            FROM tluniafetrahor UAT                  "
   + "            JOIN tlhorariosr HOR ON HOR.X_HORARIORE = UAT.X_HORARIORE                  "
   + "            JOIN tlofertasunidad uni ON uni.x_unidad = UAT.x_unidad                  "
   + "            LEFT JOIN tlofematrcen omc ON omc.x_ofertamatric = uni.x_ofertamatric                 "
   + "            LEFT JOIN tlofematrgen omg ON omg.x_ofertamatrig = omc.x_ofertamatrig                  "
   + "            JOIN tlunidadescen unid ON unid.x_unidad = UAT.x_unidad                  "
   + "            JOIN tlmatalu mat ON mat.x_unidad = unid.x_unidad AND mat.x_ofertamatric = omc.x_ofertamatric                  "
   + "            JOIN tlalumnos alu ON alu.x_alumno = mat.x_alumno                  "
   + "            LEFT JOIN Proyectos pr ON pr.x_matricula = mat.x_matricula                  "
   + "            LEFT JOIN Empresas em ON em.x_matricula = mat.x_matricula                  "
   + "            LEFT JOIN Familias fam ON fam.x_modalidad = omg.x_modalidad AND pr.x_familia = fam.x_familia               "
   + "            JOIN FCT_TUTORFCTDUAL tut ON tut.X_EMPLEADO = HOR.X_EMPLEADO                  "
   + "            LEFT JOIN Validaciones val ON val.x_matricula = mat.x_matricula                 "
   + "            WHERE (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)                  "
   + "                AND mat.x_centro = :idCentro              "
   + "                AND omc.x_centro = mat.x_centro            "
   + "                AND unid.x_centro = mat.x_centro              "
   + "                AND HOR.C_ANNO = :cAnno              "
   + "                AND (-1 = :idOfertamatrig OR omg.x_ofertamatrig = :idOfertamatrig)                  "
   + "                AND (-1 = :idUnidad OR unid.x_unidad = :idUnidad)                  "
   + "                AND omg.d_ofertamatrig LIKE '%LOFP%'                  "
   + "                AND (0 = :esSinPlan OR (descripcion IS NULL AND :esSinPlan = 1))                    "
   + "                AND (                    "
   + "                    :idEstado = -1                    "
   + "                    OR (:idEstado = 1 AND val.ds_estado IS NULL AND descripcion IS NULL)                    "
   + "                    OR (:idEstado = 2 AND val.ds_estado IS NULL AND descripcion IS NOT NULL)                    "
   + "                    OR (:idEstado = 3 AND val.ds_estado = 'VP')                    "
   + "                    OR (:idEstado = 4 AND val.ds_estado = 'VA')                "
   + "                )                    "
   + "            ORDER BY nombreAlumno) ", nativeQuery = true)
List<ListadoAlumnadoTutorProjection> findListadoAlumnosLOFP(Long idTutorfctdual,
          Long idCentro,
          Integer cAnno,
          Long idOfertamatrig,
          Long idUnidad,
          Integer esSinPlan,
          Integer idEstado,
          Long idPerfil); */
 
 
 @Query(value= "SELECT  nombreAlumno, tnuss, nussActualizado, descripcion, nombreEmpresa, familia, curso, orden, seguridad, xMatricula, idCentro, estado, nussProvisional, advertenciaPartes, lgExcluir, "
   + "              CASE  "
   + "               WHEN :idPerfil = 161 AND descripcion IS NOT NULL THEN 1         "
   + "               WHEN :idTutorfctdual != -1 AND idTutor IS NOT NULL AND idTutor = :idTutorfctdual AND descripcion IS NOT NULL  THEN 1        "
   + "              WHEN :idTutorfctdual != -1 AND idTutor IS NOT NULL AND descripcion IS NOT NULL AND idTutor IN (        "
   + "                      select dual2.id_tutorfctdual from fct_tutorfctdual dual, fct_tutorfctdual dual2, tlptotraemp tra where dual.id_tutorfctdual = :idTutorfctdual        "
   + "                       and tra.x_empleado = dual.x_empleado and tra.x_empleado_sustituye = dual2.x_empleado        "
   + "               ) THEN 1        "   
   + "               WHEN :idTutorfctdual != -1 AND idTutor IS NOT NULL AND descripcion IS NOT NULL AND exists  (select 1 from fct_tutorfctdual tut1, tlempleados emp1, " 
   + "                                                                                                                       fct_tutorfctdual tut2, tlptotraemp pto "  
   + "                                                                                                            where tut1.id_tutorfctdual = idTutor "
   + "                                                                                                            and emp1.x_empleado = tut1.x_empleado "
   + "                                                                                                            and pto.x_empleado = emp1.x_empleado "
   + "                                                                                                            and pto.x_centro = :idCentro "
   + "                                                                                                            and tut2.id_tutorfctdual = :idTutorfctdual "
   + "                                                                                                            and tut2.x_empleado = pto.x_empleado_sustituye) THEN 1 "   
   + "               WHEN :idTutorfctdual = -1 AND descripcion IS NOT NULL THEN 1        "
   + "               ELSE 0 END puedeEvaluar                     "
   + "              FROM ( "
   + "              SELECT DISTINCT          "
   + "                     alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre AS nombreAlumno,         "
   + "                     alu.t_nuss AS tnuss,         "
   + "               NVL((SELECT CD_NUSS_OLD FROM (    "
   + "                            SELECT CD_NUSS_OLD    "
   + "                            FROM FCT_CORRECCIONES_NUSS cn    "
   + "                            WHERE cn.X_MATRICULA = mat.x_matricula    "
   + "                            ORDER BY cn.ID_CORRECCION_NUSS DESC    "
   + "                        ) WHERE ROWNUM = 1), NULL) AS nussActualizado,    "
   + "                     (SELECT LISTAGG(DISTINCT proy.ds_proyecto, '/') WITHIN GROUP (ORDER BY proy.ds_proyecto)         "
   + "                      FROM fct_convproy_alu coya,         "
   + "                           fct_conv_proy cpy,         "
   + "                           fct_proyectos proy        "
   + "                      WHERE coya.x_matricula = mat.x_matricula        "
   + "                      AND coya.id_conv_proy = cpy.id_conv_proy        "
   + "                      AND proy.id_proyecto = cpy.id_proyecto) AS descripcion,           "
   + "                     (SELECT LISTAGG(DISTINCT emp.d_empresa, '/') WITHIN GROUP (ORDER BY emp.d_empresa)         "
   + "                      FROM fct_convproy_alu coya,         "
   + "                          fct_conv_proy cpy,         "
   + "                          fct_convenios conv,        "
   + "                          tlempresas emp        "
   + "                      WHERE coya.x_matricula = mat.x_matricula        "
   + "                      AND coya.id_conv_proy = cpy.id_conv_proy        "
   + "                      AND conv.id_convenio = cpy.id_convenio        "
   + "                      AND emp.x_empresa = conv.x_empresa         "
   + "                      ) AS nombreEmpresa,                 "
   + "               (select LISTAGG(DISTINCT omg1.d_ofertamatrig, '/') WITHIN GROUP (ORDER BY omg1.d_ofertamatrig)      "
   + "                from tlofematrgen omg1, fct_convproy_alu coya, fct_conv_proy cpy, fct_proyectos proy                      "
   + "                WHERE coya.x_matricula = mat.x_matricula                     "
   + "                AND coya.id_conv_proy = cpy.id_conv_proy                     "
   + "                AND proy.id_proyecto = cpy.id_proyecto      "
   + "                AND proy.x_ofertamatrig = omg1.x_ofertamatrig)  AS curso,             "
   + "                     (SELECT LISTAGG(DISTINCT fam.s_familia, '/') WITHIN GROUP (ORDER BY fam.s_familia)                     "
   + "                      FROM tlmodalidades mod, tlfamilias fam, fct_convproy_alu coya,                      "
   + "                           fct_conv_proy cpy,                      "
   + "                           fct_proyectos proy                      "
   + "                      WHERE mod.x_modalidad = omg.x_modalidad                      "
   + "                      AND fam.x_familia = mod.x_familia    "
   + "                      AND coya.x_matricula = mat.x_matricula                     "
   + "                      AND coya.id_conv_proy = cpy.id_conv_proy                     "
   + "                      AND proy.id_proyecto = cpy.id_proyecto      "
   + "                     AND fam.x_familia = proy.x_familia     "
   + "                     ) AS familia,                  "
   + "                     omg.n_orden || 'º' AS orden,         "
   + "                     '1' AS seguridad,         "
   + "                     mat.x_matricula AS xMatricula,        "
   + "                 mat.x_centro AS idCentro,      "
   + "                     (select distinct ds_estado from fct_validaciones_aluplan val    "
   + "                      where val.x_matricula = mat.x_matricula    "
   + "                      and val.nu_orden = (select MAX(NU_ORDEN) from fct_validaciones_aluplan val1    "
   + "                     where val1.x_matricula = val.x_matricula)) AS estado,                "
   + "                   CASE                  "
   + "                   WHEN (SELECT LISTAGG(DISTINCT proy.ds_proyecto, '/') WITHIN GROUP (ORDER BY proy.ds_proyecto)            "
   + "                         FROM fct_convproy_alu coya,            "
   + "                              fct_conv_proy cpy,            "
   + "                              fct_proyectos proy           "
   + "                         WHERE coya.x_matricula = mat.x_matricula           "
   + "                         AND coya.id_conv_proy = cpy.id_conv_proy           "
   + "                         AND proy.id_proyecto = cpy.id_proyecto) IS null THEN 0    "
   + "                      ELSE 1 END puedeEvaluar,      "
   + "          CASE WHEN alu.t_nuss LIKE '85%' THEN 1     "
   + "    ELSE 0 END AS nussProvisional, "
         + "         (SELECT "
         + "             RTRIM( "
         + "                 CASE WHEN MAX(CASE WHEN fpa.CD_VISTA = 'ALU' THEN 1 ELSE 0 END) = 1 "
         + "                      THEN 'Tiene partes firmados por el alumno/a.' || CHR(10) "
         + "                      ELSE '' END "
         + "                 || "
         + "                 CASE WHEN MAX(CASE WHEN fpa.CD_VISTA = 'P' AND NVL(fpa.LG_ACTUALIZADO, 1) = 0 THEN 1 ELSE 0 END) = 1 "
         + "                      THEN 'Tiene partes cerrados que han sido modificados.' || CHR(10) "
         + "                      ELSE '' END "
         + "                 || "
         + "                 CASE WHEN MAX(CASE WHEN fpa.CD_VISTA = 'ALU' OR (fpa.CD_VISTA = 'P' AND NVL(fpa.LG_ACTUALIZADO, 1) = 0) THEN 1 ELSE 0 END) = 1 "
         + "                      THEN 'Puede acceder al seguimiento y comprobarlos.' "
         + "                      ELSE '' END "
         + "             , CHR(10) "
         + "             ) "
         + "          FROM FCT_PARSEM_ALUPLAN fpa "
         + "          WHERE fpa.ID_CONVPROY_ALU IN ( "
         + "              SELECT coya.ID_CONVPROY_ALU "
         + "              FROM FCT_CONVPROY_ALU coya "
         + "              WHERE coya.X_MATRICULA = mat.X_MATRICULA "
         + "          ) "
         + "         ) AS advertenciaPartes, "
         + "      NVL((SELECT distinct 1 FROM fct_convproy_alu convy WHERE convy.x_matricula= mat.x_matricula and convy.lg_excluir = 1),0) AS lgExcluir,   "
   + "          (SELECT proy.id_tutorfctdual      "
   + "                      FROM fct_convproy_alu coya,         "
   + "                           fct_conv_proy cpy,         "
   + "                           fct_proyectos proy        "
   + "                      WHERE coya.x_matricula = mat.x_matricula        "
   + "                      AND coya.id_conv_proy = cpy.id_conv_proy        "
   + "                      AND proy.id_proyecto = cpy.id_proyecto and ROWNUM = 1) AS idTutor "
   + "              FROM tluniafetrahor UAT, tlhorariosr HOR, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,   "
   + "                FCT_TUTORFCTDUAL tut, tlunidadescen unid, tlmatalu mat, tlalumnos alu, tlempleados emp, tlptotraemp pto, tlcursoaca aca    "
   + "              WHERE (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)       "
   + "              AND mat.x_centro = :idCentro "
   + "              AND HOR.X_EMPLEADO = tut.X_EMPLEADO          "
   + "              AND emp.x_empleado = tut.X_EMPLEADO          "
   + "              AND HOR.F_TOMAPOS = pto.F_TOMAPOS         "
   + "              AND aca.c_anno = :cAnno         "
   + "              AND TLF_INTERSECPER(pto.f_tomaposrea, pto.f_cese, aca.f_inicio, aca.f_final) = 1          "
   + "              AND pto.x_centro = omc.x_centro         "
   + "              AND pto.x_empleado = hor.x_empleado         "
   //+ "              AND TLF_INTERSECPER(tut.f_initutoria, tut.f_baja, aca.f_inicio, aca.f_final) = 1          "
   + "              AND HOR.C_ANNO = :cAnno       "
   + "              AND UAT.X_HORARIORE = HOR.X_HORARIORE       "
   + "              AND uni.x_unidad = UAT.x_unidad     "
   + "              AND omc.x_ofertamatric = uni.x_ofertamatric       "
   + "              AND omg.x_ofertamatrig = omc.x_ofertamatrig         "
   + "              AND (-1 = :idOfertamatrig OR omg.x_ofertamatrig = :idOfertamatrig)                 "
   + "              AND UAT.x_ofertamatric = omc.x_ofertamatric          "
   + "              AND unid.x_unidad = UAT.x_unidad         "
   + "              AND (-1 = :idUnidad OR unid.x_unidad = :idUnidad)          "
   + "              AND mat.x_unidad = unid.x_unidad         "
   + "              AND mat.x_ofertamatric = omc.x_ofertamatric         "
   + "              AND alu.x_alumno = mat.x_alumno         "
   + "              AND omg.d_ofertamatrig LIKE '%LOFP%'                                                      "
   + "          )        "
   + "           WHERE (0 = :esSinPlan OR (descripcion IS NULL AND :esSinPlan = 1))       "
   + "           AND (:idEstado = -1                         "
   + "           OR (:idEstado = 1 AND estado IS NULL AND descripcion IS NULL)                         "
   + "           OR (:idEstado = 2 AND estado IS NULL AND descripcion IS NOT NULL)                         "
   + "           OR (:idEstado = 3 AND estado = 'VP')                         "
   + "           OR (:idEstado = 4 AND estado = 'VA')                     "
   + "           )  "
   + "         ORDER BY nombreAlumno ", nativeQuery = true)
 List<ListadoAlumnadoTutorProjection> findListadoAlumnosLOFP(Long idTutorfctdual,
           Long idCentro,
           Integer cAnno,
           Long idOfertamatrig,
           Long idUnidad,
           Integer esSinPlan,
           Integer idEstado,
           Long idPerfil);

 
 


 @Query(value= "  SELECT *  "
         + "    FROM (  "
         + "    SELECT DISTINCT       "
         + "           alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre AS nombreAlumno,      "
         + "           alu.t_nuss AS tnuss,      "
   + "           NVL((SELECT CD_NUSS_OLD FROM ( "
   + "                        SELECT CD_NUSS_OLD "
   + "                        FROM FCT_CORRECCIONES_NUSS cn "
   + "                        WHERE cn.X_MATRICULA = mat.x_matricula "
   + "                        ORDER BY cn.ID_CORRECCION_NUSS DESC "
   + "                    ) WHERE ROWNUM = 1), NULL) AS nussActualizado, "
         + "           (SELECT LISTAGG(DISTINCT proy.ds_proyecto, '/') WITHIN GROUP (ORDER BY proy.ds_proyecto)      "
         + "    FROM fct_convproy_alu coya,      "
         + "       fct_conv_proy cpy,      "
         + "       fct_proyectos proy     "
         + "    WHERE coya.x_matricula = mat.x_matricula     "
         + "    AND coya.id_conv_proy = cpy.id_conv_proy     "
         + "    AND proy.id_proyecto = cpy.id_proyecto     "
         + "           ) AS descripcion,        "
         + "           (SELECT LISTAGG(DISTINCT emp.d_empresa, '/') WITHIN GROUP (ORDER BY emp.d_empresa)      "
         + "    FROM fct_convproy_alu coya,      "
         + "       fct_conv_proy cpy,      "
         + "       fct_convenios conv,     "
         + "       tlempresas emp     "
         + "    WHERE coya.x_matricula = mat.x_matricula     "
         + "    AND coya.id_conv_proy = cpy.id_conv_proy     "
         + "    AND conv.id_convenio = cpy.id_convenio     "
         + "    AND emp.x_empresa = conv.x_empresa      "
         + "           ) AS nombreEmpresa,     "         
   + " (select LISTAGG(DISTINCT omg1.d_ofertamatrig, '/') WITHIN GROUP (ORDER BY omg1.d_ofertamatrig)   "
   + "                 from tlofematrgen omg1, fct_convproy_alu coya, fct_conv_proy cpy, fct_proyectos proy "                  
   + "                 WHERE coya.x_matricula = mat.x_matricula     "             
   + "                 AND coya.id_conv_proy = cpy.id_conv_proy   "               
   + "                 AND proy.id_proyecto = cpy.id_proyecto   "
   + "                 AND proy.x_ofertamatrig = omg1.x_ofertamatrig)  AS curso, "         
         + " (SELECT LISTAGG(DISTINCT fam.s_familia, '/') WITHIN GROUP (ORDER BY fam.s_familia) "                 
         + "        FROM tlmodalidades mod, tlfamilias fam, fct_convproy_alu coya, "                  
         + "        fct_conv_proy cpy,                   "
         + "        fct_proyectos proy                   "
         + "        WHERE mod.x_modalidad = omg.x_modalidad "                  
         + "        AND fam.x_familia = mod.x_familia "
         + "        AND coya.x_matricula = mat.x_matricula     "             
         + "        AND coya.id_conv_proy = cpy.id_conv_proy "                 
         + "        AND proy.id_proyecto = cpy.id_proyecto "  
         + "        AND fam.x_familia = proy.x_familia " 
         + "        ) AS familia,      "         
         + "           omg.n_orden || 'º' AS orden,      "
         + "           '1' AS seguridad,      "
         + "           mat.x_matricula AS xMatricula,     "
         + "       mat.x_centro AS idCentro,   "
         + "           (select distinct ds_estado from fct_validaciones_aluplan val "
         + " where val.x_matricula = mat.x_matricula "
         + " and val.nu_orden = (select MAX(NU_ORDEN) from fct_validaciones_aluplan val1 "
         + "          where val1.x_matricula = val.x_matricula)) AS estado,    "         
         + "         CASE         "
         + "         WHEN (SELECT LISTAGG(DISTINCT proy.ds_proyecto, '/') WITHIN GROUP (ORDER BY proy.ds_proyecto) "        
         + "               FROM fct_convproy_alu coya,         "
         + "                    fct_conv_proy cpy,         "
         + "                    fct_proyectos proy        "
         + "               WHERE coya.x_matricula = mat.x_matricula "       
         + "               AND coya.id_conv_proy = cpy.id_conv_proy   "     
         + "               AND proy.id_proyecto = cpy.id_proyecto) IS null THEN 0 "
         + "            ELSE 1 END puedeEvaluar,   "
         + "  CASE WHEN alu.t_nuss LIKE '85%' THEN 1  "
         + "  ELSE 0 END AS nussProvisional, "
         + " '' AS advertenciaPartes, "
         + " NVL((SELECT distinct 1 FROM fct_convproy_alu convy WHERE convy.x_matricula= mat.x_matricula and convy.lg_excluir = 1),0) AS lgExcluir "
         + "    FROM tluniafetrahor UAT, tlhorariosr HOR, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,"
         + "      FCT_TUTORFCTDUAL tut, tlunidadescen unid, tlmatalu mat, tlalumnos alu, tlempleados emp, tlptotraemp pto, tlcursoaca aca "
         + "    WHERE (-1 = :idTutorfctdual OR tut.id_tutorfctdual = :idTutorfctdual)    "
         + "      AND HOR.X_EMPLEADO = tut.X_EMPLEADO       "
         + "    AND emp.x_empleado = tut.X_EMPLEADO       "
         + "    AND HOR.F_TOMAPOS = pto.F_TOMAPOS      "
         + "    AND aca.c_anno = :cAnno      "
         + "    AND TLF_INTERSECPER(pto.f_tomaposrea, pto.f_cese, aca.f_inicio, aca.f_final) = 1       "
         + "    AND pto.x_centro = omc.x_centro      "
         + "    AND pto.x_empleado = hor.x_empleado      "
         + "    AND TLF_INTERSECPER(tut.f_initutoria, tut.f_baja, aca.f_inicio, aca.f_final) = 1       "
         + "    AND HOR.C_ANNO = :cAnno    "
         + "    AND UAT.X_HORARIORE = HOR.X_HORARIORE    "
         + "    AND uni.x_unidad = UAT.x_unidad  "
         + "    AND omc.x_ofertamatric = uni.x_ofertamatric    "
         + "    AND omg.x_ofertamatrig = omc.x_ofertamatrig      "
         + "    AND (-1 = :idOfertamatrig OR omg.x_ofertamatrig = :idOfertamatrig)       "
         + "    AND (-1 = :idCentroCombo OR omc.x_centro = :idCentroCombo)           "
         + "    AND UAT.x_ofertamatric = omc.x_ofertamatric       "
         + "    AND unid.x_unidad = UAT.x_unidad      "
         + "    AND (-1 = :idUnidad OR unid.x_unidad = :idUnidad)       "
         + "    AND mat.x_unidad = unid.x_unidad      "
         + "    AND mat.x_ofertamatric = omc.x_ofertamatric      "
         + "    AND alu.x_alumno = mat.x_alumno      "
         + "    AND d_ofertamatrig LIKE '%LOFP%'          "
         + "    AND omc.x_centro IN (    "
         + "          SELECT DISTINCT dcen1.x_centro     "
         + "          FROM TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv,     "
         + "    TLDATOSCEN dcen1, TLCENTROS cen     "
         + " WHERE u.x_usuario = :idUsuario  "
         + " AND pop.x_perfil = :idPerfil     "
         + " AND pto.x_centro = :idCentro     "
         + " AND pto.x_empleado = u.x_empleado     "
         + " AND pop.x_empleado = pto.x_empleado     "
         + " AND unid.x_centro = omc.x_centro "
         + " and unid.c_anno = HOR.C_ANNO "
         + " AND pop.f_tomapos = pto.f_tomapos     "
         + " AND pto.x_centro = dcen.x_centro     "
         + " AND dcen.c_provincia = prv.c_provincia     "
         + " AND (pto.f_cese IS NULL OR SYSDATE BETWEEN pto.f_tomapos AND pto.f_cese)     "
         + " AND dcen1.c_provincia = prv.c_provincia     "
         + " AND dcen1.l_vigente = 'S'     "
         + " AND cen.x_centro = dcen1.x_centro     "
         + " AND cen.l_delegacion = 'N'     "
         + " AND cen.l_extranjero = 'N'    "
         + "      )      "
         + "    ORDER BY nombreAlumno    "
         + ")     "
         + " WHERE (0 = :esSinPlan OR (descripcion IS NULL AND :esSinPlan = 1))    "
         + " AND (-1 = :idEstado     "
   + " OR (:idEstado = 1 AND estado IS NULL AND descripcion IS NULL)                         "
   + " OR (:idEstado = 2 AND estado IS NULL AND descripcion IS NOT NULL)                         "
   + " OR (:idEstado = 3 AND estado = 'VP')                         "
   + " OR (:idEstado = 4 AND estado = 'VA'))     ", nativeQuery = true)
List<ListadoAlumnadoTutorProjection> findListadoAlumnosLOFPDelegacion(Long idTutorfctdual,
         Long idCentro,
         Integer cAnno,
         Long idOfertamatrig,
         Long idUnidad,
         Long idPerfil,
         Long idCentroCombo,
         Long idUsuario,
         Integer esSinPlan,
         Integer idEstado);


 @Query(value = " SELECT distinct fam.x_familia as id, " +
   "  fam.d_familia  as descripcion   " +
   "      from tluniafetrahor UAT, tlhorariosr HOR, TLACTIVIDADES act, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg,          " +
   "          FCT_TUTORFCTDUAL tut, tlempleados emp, tlfamilias fam, tlmodalidades mod          " +
   "      where tut.X_EMPLEADO = HOR.X_EMPLEADO  " +
   //"      and tut.f_tomapos = HOR.f_tomapos  " +
   "      and emp.x_empleado = tut.x_empleado  " +
   "      and HOR.X_EMPLEADO = tut.X_EMPLEADO          " +
   "      AND HOR.F_TOMAPOS IN (select pto1.f_tomapos from tlptotraemp pto1 , tlcursoaca aca    " +
   " where aca.c_anno = :cAnno    " +
   " and TLF_INTERSECPER( pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final ) = 1    " +
   " and pto1.x_centro  = :idCentro  " +
   " and pto1.x_empleado =  hor.x_empleado)    " +
   "      and HOR.C_ANNO = :cAnno          " +
   "      and UAT.X_HORARIORE = HOR.X_HORARIORE          " +
   "      and uni.x_unidad = UAT.x_unidad          " +
   "      and omc.x_ofertamatric =  uni.x_ofertamatric          " +
   "      and omg.x_ofertamatrig =  omc.x_ofertamatrig           " +
   "      and UAT.x_ofertamatric = omc.x_ofertamatric           " +
   "      and act.x_actividad = HOR.x_actividad " +
   "      and (-1 = :idTutor OR tut.id_tutorfctdual = :idTutor) " +
   "      and omg.x_modalidad = mod.x_modalidad" +
   "      and mod.x_familia = fam.x_familia" +
   "      and d_ofertamatrig like '%LOFP%' " +
   "    order by descripcion",
   nativeQuery = true)
 List<ElementoSelectProjection> findFamiliasCentroTutorLOFP(Long idCentro,int cAnno, Long idTutor);

 @Query(value = "SELECT DISTINCT mod.x_modalidad AS id, mod.d_modalidad AS descripcion " +
   "FROM tluniafetrahor UAT, tlhorariosr HOR, TLACTIVIDADES act, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg, " +
   "     FCT_TUTORFCTDUAL tut, tlempleados emp, tlfamilias fam, tlmodalidades mod " +
   "WHERE tut.X_EMPLEADO = HOR.X_EMPLEADO " +
   //"  AND tut.f_tomapos = HOR.f_tomapos " +
   "  AND emp.x_empleado = tut.x_empleado " +
   "  AND HOR.X_EMPLEADO = tut.X_EMPLEADO " +
   "  AND HOR.F_TOMAPOS IN (" +
   "      SELECT pto1.f_tomapos " +
   "      FROM tlptotraemp pto1, tlcursoaca aca " +
   "      WHERE aca.c_anno = :cAnno " +
   "        AND TLF_INTERSECPER(pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final) = 1 " +
   "        AND pto1.x_centro = :idCentro " +
   "        AND pto1.x_empleado = hor.x_empleado) " +
   "  AND HOR.C_ANNO = :cAnno " +
   "  AND UAT.X_HORARIORE = HOR.X_HORARIORE " +
   "  AND uni.x_unidad = UAT.x_unidad " +
   "  AND omc.x_ofertamatric = uni.x_ofertamatric " +
   "  AND omg.x_ofertamatrig = omc.x_ofertamatrig " +
   "  AND UAT.x_ofertamatric = omc.x_ofertamatric " +
   "  AND act.x_actividad = HOR.x_actividad " +
   "  AND (:idTutor = -1 OR tut.id_tutorfctdual = :idTutor) " +
   "  AND omg.x_modalidad = mod.x_modalidad " +
   "  AND mod.x_familia = fam.x_familia " +
   "  AND (:idFamilia = -1 OR mod.x_familia = :idFamilia) " +
   "  AND omg.d_ofertamatrig LIKE '%LOFP%' " +
   "ORDER BY mod.d_modalidad",
   nativeQuery = true)
 List<ElementoSelectProjection> findModCentroTutorFamiliaLOFP(Long idCentro, Integer cAnno, Long idTutor, Long idFamilia);

 @Query(value = "SELECT DISTINCT omg.x_ofertamatrig AS id, omg.d_ofertamatrig AS descripcion " +
   "FROM tluniafetrahor UAT, tlhorariosr HOR, TLACTIVIDADES act, tlofertasunidad uni, tlofematrcen omc, tlofematrgen omg, " +
   "     FCT_TUTORFCTDUAL tut, tlempleados emp, tlfamilias fam, tlmodalidades mod " +
   "WHERE tut.X_EMPLEADO = HOR.X_EMPLEADO " +
   //"  AND tut.f_tomapos = HOR.f_tomapos " +
   "  AND emp.x_empleado = tut.x_empleado " +
   "  AND HOR.X_EMPLEADO = tut.X_EMPLEADO " +
   "  AND HOR.F_TOMAPOS IN (" +
   "      SELECT pto1.f_tomapos " +
   "      FROM tlptotraemp pto1, tlcursoaca aca " +
   "      WHERE aca.c_anno = :cAnno " +
   "        AND TLF_INTERSECPER(pto1.f_tomaposrea, pto1.f_cese, aca.f_inicio, aca.f_final) = 1 " +
   "        AND pto1.x_centro = :idCentro " +
   "        AND pto1.x_empleado = hor.x_empleado) " +
   "  AND HOR.C_ANNO = :cAnno " +
   "  AND UAT.X_HORARIORE = HOR.X_HORARIORE " +
   "  AND uni.x_unidad = UAT.x_unidad " +
   "  AND omc.x_ofertamatric = uni.x_ofertamatric " +
   "  AND omg.x_ofertamatrig = omc.x_ofertamatrig " +
   "  AND UAT.x_ofertamatric = omc.x_ofertamatric " +
   "  AND act.x_actividad = HOR.x_actividad " +
   "  AND (:idTutor = -1 OR tut.id_tutorfctdual = :idTutor) " +
   "  AND omg.x_modalidad = mod.x_modalidad " +
   "  AND mod.x_familia = fam.x_familia " +
   "  AND (:idFamilia = -1 OR mod.x_familia = :idFamilia) " +
   "  AND (:idModalidad = -1 OR omg.x_modalidad = :idModalidad) " +
   "  AND omg.d_ofertamatrig LIKE '%LOFP%' " +
   "ORDER BY omg.d_ofertamatrig",
   nativeQuery = true)
 List<ElementoSelectProjection> findCursosCentroAnnoTutorFamiliaModalidadLOFP(
   Long idCentro, Integer cAnno, Long idTutor, Long idFamilia, Long idModalidad);

 @Query(value = "SELECT DISTINCT omg.d_ofertamatrig AS curso, " +
   "omg.x_ofertamatrig AS idOfertamatrig, omg.n_orden " +
   "FROM tlofematrgen omg, tlofematrcen omc, tlofertasunidad unf, tlunidadescen uni " +
   "WHERE omg.x_ofertamatrig = :idCurso " +
   "AND omc.x_ofertamatrig = omg.x_ofertamatrig " +
   "AND unf.x_ofertamatric = omc.x_ofertamatric " +
   "AND omc.x_centro = :idCentro " +
   "AND uni.x_unidad = unf.x_unidad " +
   "AND uni.c_anno = :cAnno", nativeQuery = true)
    List<CursoModalidadProjection>  findCursosByModalidadLOFP(Long idCurso, Integer idCentro, Integer cAnno);

 @Query(value = "SELECT codigo, idMateriaomg, modulo, horasAnuales, horasSemanales " +
   "FROM ( " +
   "   SELECT DISTINCT momg.c_codigomec AS codigo, " +
   "       momg.x_materiaomg AS idMateriaomg, " +
   "       mcur.d_materiac AS modulo, " +
   "       momg.n_horasanuales AS horasAnuales, " +
   "       momg.n_horas/60 AS horasSemanales, " +
   "       1 AS orden " +
   "   FROM tlofematrgen omg, tlofematrcen omc, tlmatofematrg momg, tlmateriascurso mcur, " +
   "        tlmatofematrcen momc, tlperiodosomc per " +
   "   WHERE omg.x_ofertamatrig = :idCurso " +
   "   AND omc.x_ofertamatrig = omg.x_ofertamatrig " +
   "   AND omc.x_centro = :idCentro " +
   "   AND :cAnno BETWEEN omg.c_anno AND NVL(omg.c_annotermina, 2099) " +
   "   AND momg.x_materiac = mcur.x_materiac " +
   "   AND momg.x_materiaomg = momc.x_materiaomg " +
   "   AND momc.x_ofertamatric = omc.x_ofertamatric " +
   "   AND per.x_ofertamatric = omc.x_ofertamatric " +
   "   AND :cAnno BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 2099) " +
   "   AND EXISTS ( " +
   "       SELECT 1 " +
   "       FROM tlunicommat unm, tlunicom uni, tlcatcuaprof cua, tlcuaprofunicom cpuc, tlnivelcp niv, tlfamilias fam " +
   "       WHERE unm.x_unicom = uni.x_unicom " +
   "       AND uni.x_unicom = cpuc.x_unicom " +
   "       AND cpuc.x_cualificacion = cua.x_cualificacion " +
   "       AND cua.x_nivelcp = niv.x_nivelcp " +
   "       AND cua.x_familia = fam.x_familia " +
   "       AND unm.x_materiaomg = momg.x_materiaomg " +
   "   ) " +
   "UNION " +
   "   SELECT DISTINCT momg.c_codigomec AS codigo, " +
   "       momg.x_materiaomg AS idMateriaomg, " +
   "       mcur.d_materiac || ' (Sin estándares de competencia)' AS modulo, " +
   "       momg.n_horasanuales AS horasAnuales, " +
   "       momg.n_horas/60 AS horasSemanales, " +
   "       2 AS orden " +
   "   FROM tlofematrgen omg, tlofematrcen omc, tlmatofematrg momg, tlmateriascurso mcur, " +
   "        tlmatofematrcen momc, tlperiodosomc per " +
   "   WHERE omg.x_ofertamatrig = :idCurso " +
   "   AND omc.x_ofertamatrig = omg.x_ofertamatrig " +
   "   AND omc.x_centro = :idCentro " +
   "   AND :cAnno BETWEEN omg.c_anno AND NVL(omg.c_annotermina, 2099) " +
   "   AND momg.x_materiac = mcur.x_materiac " +
   "   AND momg.x_materiaomg = momc.x_materiaomg " +
   "   AND momc.x_ofertamatric = omc.x_ofertamatric " +
   "   AND per.x_ofertamatric = omc.x_ofertamatric " +
   "   AND :cAnno BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 2099) " +
   "   AND NOT EXISTS ( " +
   "       SELECT 1 " +
   "       FROM tlunicommat unm, tlunicom uni, tlcatcuaprof cua, tlcuaprofunicom cpuc, tlnivelcp niv, tlfamilias fam " +
   "       WHERE unm.x_unicom = uni.x_unicom " +
   "       AND uni.x_unicom = cpuc.x_unicom " +
   "       AND cpuc.x_cualificacion = cua.x_cualificacion " +
   "       AND cua.x_nivelcp = niv.x_nivelcp " +
   "       AND cua.x_familia = fam.x_familia " +
   "       AND unm.x_materiaomg = momg.x_materiaomg " +
   "   ) " +
   ") ORDER BY orden ASC", nativeQuery = true)
 List<ModuloModalidadProjection> findModuloByModalidadLOFP(Long idCurso, Integer idCentro, Integer cAnno);

 @Query(value = "WITH semanas_cte AS (     "
   + "        SELECT DISTINCT     "
   + "          TO_CHAR(TRUNC(per.fh_inicio - 1 + LEVEL, 'IW'), 'YYYY-MM') AS mes,     "
   + "          TO_CHAR(TRUNC(per.fh_inicio - 1 + LEVEL, 'IW'), 'Month') AS mes_nombre,     "
   + "             TO_CHAR(TRUNC(per.fh_inicio - 1 + LEVEL, 'IW'), 'DD/MM/YYYY') AS fecha_inicio_sem,     "
   + "             TO_CHAR(TRUNC(per.fh_inicio - 1 + LEVEL, 'IW') + 6, 'DD/MM/YYYY') AS fecha_fin_sem,     "
   + "             per.id_conv_proy,     "
   + "             CASE     "
   + "   WHEN SYSDATE BETWEEN TRUNC(per.fh_inicio - 1 + LEVEL, 'IW') AND TRUNC(per.fh_inicio - 1 + LEVEL, 'IW') + 6 THEN 1     "
   + "   ELSE 0     "
   + "             END AS actual,     "
   + "             CASE     "
   + "   WHEN TRUNC(per.fh_inicio - 1 + LEVEL, 'IW') + 6 <= SYSDATE THEN 1     "
   + "   WHEN SYSDATE BETWEEN TRUNC(per.fh_inicio - 1 + LEVEL, 'IW') AND TRUNC(per.fh_inicio - 1 + LEVEL, 'IW') + 6 THEN 1     "
   + "   ELSE 0     "
   + "             END AS visible     "
   + "           FROM (     "
   + "             SELECT     "
   + "   MIN(cpa.fh_inicio) AS fh_inicio,     "
   + "   MAX(cpa.fh_fin) AS fh_fin,     "
   + "   (MIN(cpa.fh_fin) - MAX(cp.fh_inicio)) AS dias,     "
   + "   cpa.id_conv_proy     "
   + "             FROM     "
   + "   FCT_CONV_PROY cp     "
   + "             JOIN fct_conv_proyaluhoraper cpa ON cp.id_conv_proy = cpa.id_conv_proy     "
   + "             JOIN EMP_TRAEMP traemp ON cp.id_traemp = traemp.id_traemp     "
   + "             JOIN TLEMPRESAS emp ON traemp.x_empresa = emp.x_empresa     "
   + "             WHERE     "
   + "   cpa.x_matricula = :xMatricula     "
   + "   AND emp.x_empresa = :xEmpresa     "
   + "   AND cp.id_conv_proy = :idProyecto     "
   + "             GROUP BY cpa.id_conv_proy     "
   + "           ) per     "
   + "           CONNECT BY TRUNC(per.fh_inicio - 1 + LEVEL, 'IW') <= per.fh_fin     "
   + "         ),     "
   + "         semanas_numeradas AS (     "
   + "           SELECT     "
   + "             mes,     "
   + "             mes_nombre,     "
   + "             fecha_inicio_sem,     "
   + "             fecha_fin_sem,     "
   + "             id_conv_proy,     "
   + "             actual,     "
   + "             visible,     "
   + "             ROW_NUMBER() OVER (ORDER BY TO_DATE(fecha_inicio_sem, 'DD/MM/YYYY')) AS semana_numero     "
   + "           FROM semanas_cte     "
   + "         ),     "
   + "         meses_cte AS (     "
   + "           SELECT DISTINCT     "
   + "             mes,     "
   + "             mes_nombre,     "
   + "             CASE     "
   + "   WHEN mes <= TO_CHAR(SYSDATE, 'YYYY-MM') THEN 1     "
   + "   ELSE 0     "
   + "             END AS visible_mes     "
   + "           FROM semanas_numeradas     "
   + "         ),     "
   + "         periodos_unicos AS (     "
   + "           SELECT     "
   + "             hconv.id_conv_proy,     "
   + "             conva.id_convproy_alu, "
   + "             hconv.fh_inicio AS fecha_inicio_periodo,     "
   + "             hconv.fh_fin AS fecha_fin_periodo,     "
   + "          ROW_NUMBER() OVER (PARTITION BY hconv.id_conv_proy ORDER BY hconv.fh_inicio ASC) AS periodo_numero,     "
   + "          CASE     "
   + "            WHEN SYSDATE BETWEEN hconv.fh_inicio AND fh_fin THEN 1     "
   + "            ELSE 0     "
   + "          END AS es_periodo_actual,     "
   + "          CASE     "
   + "            WHEN hconv.fh_fin <= SYSDATE THEN 1     "
   + "            WHEN SYSDATE BETWEEN hconv.fh_inicio AND hconv.fh_fin THEN 1     "
   + "            ELSE 0     "
   + "          END AS periodo_visible     "
   + "        FROM FCT_CONV_PROYALUHORAPER hconv, FCT_CONVPROY_ALU conva      "
   + "        WHERE hconv.x_matricula = :xMatricula    "
   + "        and conva.id_conv_proy = hconv.id_conv_proy "
   + "        and conva.x_matricula = hconv.x_matricula "
   + "      )     "
   + "      SELECT     "
   + "        m.mes AS mesNumero,     "
   + "        m.mes_nombre AS mesNombre,     "
   + "        m.visible_mes AS mesVisible,     "
   + "        'Semana ' || LPAD(sn.semana_numero, 2, '0') AS semanaNumero,     "
   + "        sn.actual AS esSemanaActual,     "
   + "        sn.visible AS semanaVisible,     "
   + "        sn.fecha_inicio_sem AS fechaInicioSem,     "
   + "        sn.fecha_fin_sem AS fechaFinSem,     "
   + "        TO_CHAR(fp.fecha_inicio_periodo, 'DD/MM/YYYY') AS fechaInicioPeriodo,     "
   + "        TO_CHAR(fp.fecha_fin_periodo, 'DD/MM/YYYY') AS fechaFinPeriodo,     "
   + "        fp.periodo_numero AS periodoNumero,     "
   + "        fp.es_periodo_actual AS esPeriodoActual,     "
   + "        fp.periodo_visible AS periodoVisible,     "
   + "        (SELECT COUNT(distinct act.id_actividad_modulo) from fct_pardia_aluplan dia, fct_pardia_aluplan_actmod act " 
   + "        WHERE dia.id_parsem_aluplan = fpalu.id_parsem_aluplan "
        + "        AND act.id_pardia_aluplan = dia.id_pardia_aluplan) AS numActividades, " 
   + "        CASE fpalu.CD_VISTA "
   + "        WHEN 'ALU' THEN 1 "
   + "        WHEN 'P' THEN 2 "
   + "        ELSE 0 END AS estadoParte,         " 
   + "        fpalu.id_parsem_rodal   as idRodal, "
   + "        fpalu.tx_parsem_fichero as fichero, "
         + " CASE WHEN fpalu.LG_ACTUALIZADO = 0 THEN 0 ELSE 1 END AS parteActualizado "
   + "      FROM semanas_numeradas sn     "
   + "      JOIN meses_cte m     "
   + "        ON sn.mes = m.mes     "
   + "      LEFT JOIN periodos_unicos fp     "
   + "        ON sn.id_conv_proy = fp.id_conv_proy     "
   + "        AND TO_DATE(sn.fecha_inicio_sem, 'DD/MM/YYYY') <= fp.fecha_fin_periodo     "
   + "        AND TO_DATE(sn.fecha_fin_sem, 'DD/MM/YYYY') >= fp.fecha_inicio_periodo     "
   + "        AND (TO_DATE(sn.fecha_inicio_sem, 'DD/MM/YYYY') < fp.fecha_inicio_periodo     "
   + "  OR TO_DATE(sn.fecha_inicio_sem, 'DD/MM/YYYY') >= fp.fecha_inicio_periodo) "
   + "      LEFT JOIN FCT_PARSEM_ALUPLAN fpalu     "
   + "    ON fpalu.id_convproy_alu = fp.id_convproy_alu     "
   + "   AND TRUNC(TO_DATE(sn.fecha_inicio_sem, 'DD/MM/YYYY')) = TRUNC(fpalu.f_inisem)        "
   + "      ORDER BY m.mes DESC, sn.semana_numero DESC", nativeQuery = true)
 List<ListadoSeguimientoLOFPProjection> findListadoSeguimientoLofp(Long xMatricula, Long xEmpresa, Long idProyecto); 


 @Query(value = "SELECT " +
         "    resp.tx_apellido1 || ' ' || resp.tx_apellido2 || ', ' || resp.tx_nombre AS nombreResponsable,  " +
         "    emp.apellido1 || ' ' || emp.apellido2 || ', ' || emp.nombre AS nombreTutor  " +
         " FROM   " +
         "    fct_convproy_alu convproyalu  " +
         "    LEFT JOIN fct_conv_proy convproy ON convproyalu.id_conv_proy = convproy.id_conv_proy  " +
         "    LEFT JOIN emp_traemp traemp ON convproy.id_traemp = traemp.id_traemp  " +
         "    LEFT JOIN emp_empleados resp ON traemp.id_empleado = resp.id_empleado  " +
         "    LEFT JOIN fct_proyectos proy ON convproy.id_proyecto = proy.id_proyecto  " +
         "    LEFT JOIN fct_tutorfctdual tut ON proy.id_tutorfctdual = tut.id_tutorfctdual  " +
         "    LEFT JOIN tlempleados emp ON tut.x_empleado = emp.x_empleado  " +
         " WHERE   " +
         "    convproyalu.id_convproy_alu = :idConvProyAlu ", nativeQuery = true)
 DatosTutorYResponsableProjection findTutorYResponsable(Long idConvProyAlu);

 @Query(value = "select unique mat.x_matricula " +
         "       from  delphos_segedu.tlempleados empl, tlalumnos alu, tlmatalu mat,   " +
         "           FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,     " +
         "       FCT_CONVENIOS con, TLEMPRESAS emp     " +
         "       where empl.x_empleado = :idEmpleadoComunica  " +
         "     and alu.c_numide = empl.c_numide  " +
         "     and mat.x_alumno = alu.x_alumno  " +
         "     and mat.c_anno = :cAnno  " +
         "     and cpa.x_matricula = mat.x_matricula   " +
         "       and pro.id_proyecto = cp.id_proyecto     " +
         "       and cp.id_conv_proy = cpa.id_conv_proy     " +
         "       and cp.id_convenio = con.id_convenio     " +
         "       and con.x_empresa = emp.x_empresa     " +
         //"       and :cAnno between pro.c_anno_desde and pro.c_anno_hasta " +
         "       and pro.lg_lofp = 1", nativeQuery = true)
 Optional<Long> findAlumnoByAnnoAndIdDelphos(Integer cAnno, Long idEmpleadoComunica);
 
 @Query(value= " select mat.c_anno || '-' || (mat.c_anno+1) anno,  "
   + "       omg.d_ofertamatrig curso,  "
   + "       (select emp.apellido1 || ' ' || emp.apellido2 || ', ' || emp.nombre     "
   + "        from fct_convproy_alu conva,   "
   + "  fct_conv_proy conv,   "
   + "  fct_proyectos proy,   "
   + "  fct_tutorfctdual tut,   "
   + "  tlempleados emp   "
   + "        where conva.x_matricula = mat.x_matricula  "
   + "        and conv.id_conv_proy = conva.id_conv_proy  "
   + "        and proy.id_proyecto = conv.id_proyecto  "
   + "        and tut.id_tutorfctdual = proy.id_tutorfctdual  "
   + "        and emp.x_empleado = tut.x_empleado  "
   + "        and rownum = 1) tutor,  "
   + "          "
   + "        NVL((SELECT DECODE(DS_ESTADO,'VP','1','VA','2','0') FROM FCT_VALIDACIONES_ALUPLAN val  "
   + "        where val.x_matricula = mat.x_matricula  "
   + "        and f_registro = (select MAX(f_registro)  "
   + "    from FCT_VALIDACIONES_ALUPLAN val1   "
   + "    where val1.x_matricula = val.x_matricula)),'0') AS validado,   "
   + "        (SELECT TO_CHAR(f_registro,'DD/MM/YYYY HH24:MI:SS') FROM FCT_VALIDACIONES_ALUPLAN val  "
   + "        where val.x_matricula = mat.x_matricula  "
   + "        and f_registro = (select MAX(f_registro)  "
   + "    from FCT_VALIDACIONES_ALUPLAN val1   "
   + "    where val1.x_matricula = val.x_matricula)) AS fvalidacion,  "
   + "        emp.d_empresa AS empresaNombre,  "
   + "        emp.tx_apellido1 || ' ' || emp.tx_apellido2 || ', ' || emp.tx_nombre AS empresaTutor,  "
   + "        sed.t_correo mail,  "
   + "        TO_CHAR(sed.n_telefono) tlf,  "
   + "        TO_CHAR(peri.fh_inicio,'DD/MM/YYYY') || '-' || TO_CHAR(peri.fh_fin,'DD/MM/YYYY') AS per,   "
   + "        alu.t_nombre || ' ' || alu.t_apellido1 || ' ' || alu.t_apellido2 AS alumno, "
  + "        TO_CHAR(conva.id_convproy_alu) AS idConvProyAlu "
   + "from tlmatalu mat,   "
   + "     tlalumnos alu,   "
   + "     tlofematrgen omg,   "
   + "     fct_convproy_alu conva,   "
   + "     fct_conv_proy conv,   "
   + "     fct_convenios cov,   "
   + "     tlempresas emp,   "
   + "     emp_sedemp sed,   "
   + "     emp_traemp tra,   "
   + "     emp_empleados emp,   "
   + "     fct_conv_proyaluhoraper peri  "
   + "where mat.x_matricula = :idMatricula  "
   + "and alu.x_alumno = mat.x_alumno  "
   + "and omg.x_ofertamatrig = mat.x_ofertamatrig  "
   + "and conva.x_matricula = mat.x_matricula  "
   + "and conv.id_conv_proy = conva.id_conv_proy  "
   + "and cov.id_convenio = conv.id_convenio  "
   + "and emp.x_empresa = cov.x_empresa  "
   + "and cov.id_sedemp = sed.id_sedemp   "
   + "and tra.id_traemp = conv.id_traemp  "
   + "and emp.id_empleado = tra.id_empleado  "
   + "and peri.id_conv_proy = conv.id_conv_proy  "
   + "and peri.x_matricula = mat.x_matricula order by peri.fh_inicio ", nativeQuery = true)
 List<Object[]> getDatosFormacionPlan(Long idMatricula);

}
