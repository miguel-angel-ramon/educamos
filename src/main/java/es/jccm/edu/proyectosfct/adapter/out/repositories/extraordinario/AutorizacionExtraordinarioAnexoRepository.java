package es.jccm.edu.proyectosfct.adapter.out.repositories.extraordinario;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.extraordinario.projections.AdjuntoAnexoXIProjection;
import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexos;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.QAutorizacionesAnexos;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.projections.DatosCabeceraProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface AutorizacionExtraordinarioAnexoRepository extends AbstractRepository<AutorizacionesAnexos, Long, QAutorizacionesAnexos> {

 
 AutorizacionesAnexos findByIdAnnoAndTipoIdAndIdCentroAndIdTutorFctAndIdCursoAndIdUnidadAndNuPeticion(Integer annoAnexo,
                          Long idTipo, 
                          Long idCentro,
                          Long idTutor,
                          Long idCurso,
                          Long idUnidad,
                       Integer nuPeticion);
 
 @Query(value="SELECT DEN.S_DENOMINACION || ' ' || DAT.D_ESPECIFICA AS centro, "
   + "       loc.d_localidad AS localidad, "
   + "       emp.nombre || ' ' ||emp.apellido1 || ' ' || emp.apellido2 tutor, "
   + "       omg.d_ofertamatrig || ' (' || uni.t_nombre || ')' curso        "
   + "   FROM TLCENTROS cen,   "
   + "        TLDATOSCEN dat,   "
   + "        TLDENGEN den,   "
   + "        TLLOCALIDADES loc, "
   + "        FCT_TUTORFCTDUAL tut, "
   + "        TLEMPLEADOS emp, "
   + "        TLOFEMATRGEN omg, "
   + "        TLUNIDADESCEN uni "
   + "WHERE cen.x_centro = :idCentro  "
   + "AND cen.x_centro = dat.x_centro    "
   + "AND dat.x_dengen = den.x_dengen  "
   + "AND loc.x_localidad = dat.x_localidad "
   + "AND tut.id_tutorfctdual = :idTutor "
   + "AND emp.x_empleado = tut.x_empleado "
   + "AND omg.x_ofertamatrig = :idCurso "
   + "AND uni.x_unidad = :idUnidad", nativeQuery = true)
 List<DatosCabeceraProjection> getDatosCabecera(Long idCentro,Long idTutor,Long idCurso,Long idUnidad);
 
 /*@Query(value="SELECT DEN.S_DENOMINACION || ' ' || DAT.D_ESPECIFICA AS centro "
      + "          FROM TLCENTROS cen,  "
      + "               TLDATOSCEN dat,  "
      + "               TLDENGEN den,  "
      + "               TLLOCALIDADES loc"
      + "          WHERE cen.x_centro = :idCentro "
      + "          AND cen.x_centro = dat.x_centro   "
      + "          AND dat.x_dengen = den.x_dengen "
      + "          AND loc.x_localidad = dat.x_localidad", nativeQuery = true)
 String getDatosCabecera(Long idCentro);
 
 @Query(value="SELECT  loc.d_localidad AS localidad "
      + "          FROM TLCENTROS cen,  "
      + "               TLDATOSCEN dat,  "
      + "               TLDENGEN den,  "
      + "               TLLOCALIDADES loc"
      + "          WHERE cen.x_centro = :idCentro "
      + "          AND cen.x_centro = dat.x_centro   "
      + "          AND dat.x_dengen = den.x_dengen "
      + "          AND loc.x_localidad = dat.x_localidad", nativeQuery = true)
 String getDatosCabeceraLocalidad(Long idCentro); */

    @Query(value = " select  'En ' || loc.d_localidad || ' a ' ||  "
       + "    TO_CHAR(sysdate,'DD')  "
       + "    ||  ' de ' ||  "
       + "    CASE TO_CHAR(sysdate,'MM')  "
       + "    WHEN '01' THEN 'Enero '   "
       + "    WHEN '02' THEN 'Febrero '   "
       + "    WHEN '03' THEN 'Marzo '    "
       + "    WHEN '04' THEN 'Abril '   "
       + "    WHEN '05' THEN 'Mayo '    "
       + "    WHEN '06' THEN 'Junio '   "
       + "    WHEN '07' THEN 'Julio '   "
       + "    WHEN '08' THEN 'Agosto '   "
       + "    WHEN '09' THEN 'Septiembre '   "
       + "    WHEN '10' THEN 'Octubre '   "
       + "    WHEN '11' THEN 'Noviembre '  "
       + "    ELSE 'Diciembre '  "
       + "    END  || 'de ' || TO_CHAR(sysdate,'RRRR') firma   "
       + "from tldatoscen dat, "
       + "     tllocalidades loc "
       + "where dat.x_centro = :idCentro  "
       + "and loc.x_localidad = dat.x_localidad ", nativeQuery = true)
 String getFirma(Long idCentro);

   @Query(value = " select emp.NOMBRE ||' '|| emp.APELLIDO1 ||' '|| emp.APELLIDO2 AS director  "
     + "from tlempleados emp, tlptotraemp pto, tlcargosemp ce,tlcentros cen, tlcargos car "
     + "where pto.x_empleado = emp.x_empleado "
     + "and pto.x_centro = :idCentro  "
     + "and sysdate >= pto.f_tomapos  "
     + "and (pto.f_cese is null OR pto.f_cese>sysdate) "
     + "and ce.x_empleado = pto.x_empleado  "
     + "and ce.f_tomapos = pto.f_tomapos  "
     + "and cen.x_centro = pto.x_centro "
     + "and car.c_cargo = ce.c_cargo "
     + "and (ce.f_cese is null OR ce.f_cese>sysdate) "
     + "and ce.c_cargo = 'XDI' "
     + "AND ROWNUM = 1 ", nativeQuery = true)
 String getDirector(Long idCentro);


   @Query(value="SELECT                                         "
     + "     den.s_denominacion || ' ' || dat.d_especifica || ' de ' || prov.d_provincia AS centro,                                        "
     + "     ( SELECT LISTAGG(nombre_completo, ', ')  WITHIN GROUP (ORDER BY ordenado) "
     + "          FROM ( SELECT alu.t_nombre || ' ' || alu.t_apellido1 || ' ' || alu.t_apellido2 AS nombre_completo, "
     + "                         NLSSORT(alu.t_nombre || alu.t_apellido1 || alu.t_apellido2, 'NLS_SORT=SPANISH_AI') AS ordenado "
     + "                 FROM fct_aut_extpro sol "
     + "                 JOIN tlmatalu mat ON mat.x_matricula = sol.x_matricula "
     + "                 JOIN tlalumnos alu ON alu.x_alumno = mat.x_alumno "
     + "                 WHERE sol.nu_peticion = ane.nu_peticion "
     + "                 AND sol.x_centro = ane.x_centro "
     + "                 AND sol.id_tutorfctdual = ane.id_tutorfctdual "
     + "                 AND mat.c_anno = ane.c_anno "
     + "                 AND mat.x_ofertamatrig = ane.x_ofertamatrig "
     + "                 AND mat.x_unidad = ane.x_unidad "
     + "                 AND EXISTS (SELECT 1 FROM fct_convprog_alu conv WHERE conv.x_matricula = mat.x_matricula "
     + "                             UNION "
     + "                             SELECT 1 FROM fct_convproy_alu conv WHERE conv.x_matricula = mat.x_matricula) "
     + "                              ) "
     + "                 ) AS alumnos,                                        "
     + "              (SELECT eta2.s_etapa FROM tlofematrgen omg, tlcursoorg cur, tlcursomoda moda, tletapas eta1 ,  tletapas eta2                                         "
     + "               WHERE omg.x_ofertamatrig = ane.x_ofertamatrig                                        "
     + "               AND cur.x_ofertamatrig = omg.x_ofertamatrig                                        "
     + "               AND moda.x_cursomod = cur.x_cursomod                                        "
     + "               AND eta1.x_etapa = moda.x_etapa                                        "
     + "               AND moda.x_modalidad = omg.x_modalidad                                        "
     + "               AND eta2.x_etapa = eta1.x_etapadependede) || ' : ' ||                                         "
     + "               (SELECT mod.d_modalidad FROM tlofematrgen omg, tlmodalidades mod                                        "
     + "                WHERE omg.x_ofertamatrig = ane.x_ofertamatrig                                        "
     + "                AND mod.x_modalidad = omg.x_modalidad) as nombreCurso,   "
     + "                   TO_CHAR(SYSDATE, 'DD \"de\" FMMonth \"de\" YYYY', 'NLS_DATE_LANGUAGE=SPANISH') AS fecha, "
     + "                prov.d_provincia AS provincia                                        "
     + "     FROM fct_autorizaciones_anexos ane, tlcentros cen, tldatoscen dat, tldengen den, tlprovincias prov                                        "
     + "     WHERE ane.id_autorizacion_anexo = :idAutAnexo                                       "
     + "     AND cen.x_centro = ane.x_centro                                        "
     + "     AND dat.x_centro = cen.x_centro                                        "
     + "     AND den.x_dengen = dat.x_dengen                                        "
     + "     AND prov.c_provincia = dat.c_provincia ",nativeQuery = true)
   AdjuntoAnexoXIProjection findDatosAdjuntoAnexo(Long idAutAnexo);

}
