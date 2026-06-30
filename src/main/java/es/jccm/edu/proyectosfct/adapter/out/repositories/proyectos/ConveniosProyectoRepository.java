package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.AlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyecto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QConveniosProyecto;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface ConveniosProyectoRepository extends AbstractRepository<ConveniosProyecto, Long, QConveniosProyecto> {
 
 List<ConveniosProyecto> findAllByConvenioId(Long idProyecto);

 List<ConveniosProyecto> findByProyectoId(Long idProyecto);
 
 @Query(value = "SELECT fcp.* "
   + "     FROM FCT_CONV_PROY fcp, TLCURSOACA aca "
   + "     WHERE fcp.ID_PROYECTO = :idConvenio "
   + "     AND aca.c_anno = :anno "
   + "     AND TLF_INTERSECPER(fcp.fh_inicio, fcp.fh_inicio, aca.f_inicio, aca.f_final) = 1 ", nativeQuery = true)
 List<ConveniosProyecto> getByProyectoIdAnno(@Param("idConvenio") Long idConvenio, @Param("anno") Integer anno);
 
 @Query(value = " select count(*) from fct_convproy_anexos where id_conv_proy = :idConvProy ", nativeQuery = true)
    Integer getCountAnexos(@Param("idConvProy") Long idConvProy);

 Integer countByProyectoId(Long idProyecto);

    @Query(value = "select 'a ' ||"
 + "  TO_CHAR(f_inicio,'DD')"
 + "  ||  ' de ' ||"
 + "  CASE TO_CHAR(f_inicio,'MM')"
 + "  WHEN '01' THEN 'Enero ' "
 + "  WHEN '02' THEN 'Febrero ' "
 + "  WHEN '03' THEN 'Marzo '  "
 + "  WHEN '04' THEN 'Abril ' "
 + "  WHEN '05' THEN 'Mayo '  "
 + "  WHEN '06' THEN 'Junio ' "
 + "  WHEN '07' THEN 'Julio ' "
 + "  WHEN '08' THEN 'Agosto ' "
 + "  WHEN '09' THEN 'Septiembre ' "
 + "  WHEN '10' THEN 'Octubre ' "
 + "  WHEN '11' THEN 'Noviembre '"
 + "  ELSE 'Diciembre '"
 + "  END  || 'de ' || TO_CHAR(f_inicio,'RRRR') "
 + " from FCT_CONVENIOS "
 + " where id_convenio = :idConvenio ", nativeQuery = true)
 String getFechaInicioConvenio(Long idConvenio);
    
    @Query(value = "select 'a ' ||"
 + "  TO_CHAR(sysdate,'DD')"
 + "  ||  ' de ' ||"
 + "  CASE TO_CHAR(sysdate,'MM')"
 + "  WHEN '01' THEN 'Enero ' "
 + "  WHEN '02' THEN 'Febrero ' "
 + "  WHEN '03' THEN 'Marzo '  "
 + "  WHEN '04' THEN 'Abril ' "
 + "  WHEN '05' THEN 'Mayo '  "
 + "  WHEN '06' THEN 'Junio ' "
 + "  WHEN '07' THEN 'Julio ' "
 + "  WHEN '08' THEN 'Agosto ' "
 + "  WHEN '09' THEN 'Septiembre ' "
 + "  WHEN '10' THEN 'Octubre ' "
 + "  WHEN '11' THEN 'Noviembre '"
 + "  ELSE 'Diciembre '"
 + "  END  || 'de ' || TO_CHAR(sysdate,'RRRR') "
 + " from DUAL ", nativeQuery = true)
 String getFechaActual();

  @Query(value = "select c_anno FROM ( "
 + "       select c_anno "
 + "       from tlcursoaca aca, fct_conv_proy proy"
 + "       where proy.id_conv_proy = :idConvProy  "
 + "       and TLF_INTERSECPER(proy.fh_inicio, proy.fh_inicio, aca.F_INICIO, aca.F_FINAL) = 1order by c_anno"
 + "       )"
 + " WHERE rownum = 1 ", nativeQuery = true)
 Integer getAnnoConvenioProyecto(Long idConvProy);

 @Query(value="select c.x_centro "
   + " from fct_conv_proy cp join fct_convenios c "
   + " on cp.id_convenio = c.id_convenio "
   + " where cp.id_conv_proy = :idConvProy ", nativeQuery = true) 
 Long getIdCentro(Long idConvProy);

  @Query(value = "SELECT alu.X_ALUMNO AS ID,  "
  + "       mat.X_MATRICULA AS IDMATRICULA,  "
  + "       mat.X_UNIDAD AS IDUNIDAD,   "
  + "       TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) AS nombreCompleto,  "
   + "       uni.T_NOMBRE AS nombreUnidad,  "
     + "       alu.T_NUSS as tnuss, "
     + "       NVL((select LG_COTIZA from fct_convproy_alu where x_matricula = mat.x_matricula and id_conv_proy = :idConvProy), NVL((select decode(emp.l_sscen,'S',1,0) "
     + "                                                                                                                             from fct_conv_proy proy, fct_convenios con, tlempresas emp " 
     + "                                                                                                                             where proy.id_conv_proy = :idConvProy "
     + "                                                                                                                             and con.id_convenio = proy.id_convenio "
     + "                                                                                                                             and emp.x_empresa = con.x_empresa),0))  as lgCotiza, "
  + "       NVL((select LG_NUSS from fct_convproy_alu where x_matricula = mat.x_matricula and id_conv_proy = :idConvProy),0)  as lgNuss,  "  
     + "       decode(alu.t_nuss,null,1,0)  as lgEditable,  "
  + "       CASE "
  + "       WHEN calcula_edad(alu.f_nacimiento) < 16 THEN 1 "
     + "       ELSE 0 END AS lgMenor, "
     + "       NVL((select LG_ERASMUS from fct_convproy_alu where x_matricula = mat.x_matricula and id_conv_proy = :idConvProy),0)  as lgErasmus "
     + "      FROM TLMATALU mat,  "
  + "           TLALUMNOS alu,  "
  + "           TLUNIDADESCEN uni "
  + "      WHERE mat.x_centro = :idCentro  "
  + "      AND mat.X_OFERTAMATRIG = :idOfertamatrig  "
  + "      AND mat.C_ANNO = :cAnno  "
  + "      AND nvl(mat.C_RESULTADO,99) > 1  "
  + "      AND mat.X_ALUMNO = alu.X_ALUMNO  "
  + "      AND mat.X_UNIDAD = uni.X_UNIDAD ", nativeQuery = true)
 List<AlumnoProjection> getAlumnosConvenio(Long idCentro, Long idOfertamatrig, int cAnno, Long idConvProy);

  List<ConveniosProyecto> findByConvenioIdAndProyectoIdAndTrabajadorId(Long idConvenio, Long idProyecto, Long idResponsable);

  @Query(value = "SELECT alu.X_ALUMNO AS ID,       "
    + "       mat.X_MATRICULA AS IDMATRICULA,        "
    + "       mat.X_UNIDAD AS IDUNIDAD,         "
    + "       TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) AS nombreCompleto,        "
    + "       uni.T_NOMBRE AS nombreUnidad,        "
    + "       alu.T_NUSS as tnuss,       "
          + "    NVL(( "
          + "        SELECT CASE "
          + "            WHEN NOT EXISTS ( "
          + "                SELECT 1 FROM fct_convproy_alu ca WHERE ca.x_matricula = mat.x_matricula "
          + "            ) THEN 0  "
          + "            WHEN EXISTS ( "
          + "                SELECT 1 FROM fct_convproy_alu ca "
          + "                WHERE ca.x_matricula = mat.x_matricula "
          + "                  AND ca.id_conv_proy = :idConvProy "
          + "            ) THEN 0  "
          + "            WHEN EXISTS ( "
          + "                SELECT 1 FROM fct_convproy_alu ca "
          + "                JOIN fct_conv_proy proy ON ca.id_conv_proy = proy.id_conv_proy "
          + "                WHERE ca.x_matricula = mat.x_matricula "
          + "                  AND ca.id_conv_proy <> :idConvProy "
          + "                  AND proy.id_convenio = (SELECT id_convenio FROM fct_conv_proy WHERE id_conv_proy = :idConvProy) "
          + "                  AND proy.id_proyecto = (SELECT id_proyecto FROM fct_conv_proy WHERE id_conv_proy = :idConvProy) "
          + "            ) THEN 1  "
          + "            WHEN EXISTS ( "
          + "                SELECT 1 FROM fct_convproy_alu ca "
          + "                JOIN fct_conv_proy proy ON ca.id_conv_proy = proy.id_conv_proy "
          + "                WHERE ca.x_matricula = mat.x_matricula "
          + "                  AND NOT EXISTS ( "
          + "                    SELECT 1 FROM fct_conv_proy p "
          + "                    WHERE p.id_conv_proy = :idConvProy "
          + "                      AND p.id_proyecto = proy.id_proyecto "
          + "                  ) "
          + "            ) THEN 2  "
          + "            ELSE 0 "
          + "        END "
          + "        FROM dual "
          + "    ), 0) AS lgAlumnoEnEmpresa, "
    + "       NVL((select LG_COTIZA from fct_convproy_alu where x_matricula = mat.x_matricula and id_conv_proy = :idConvProy), NVL((select decode(emp.l_sscen,'S',1,0)       "
    + "                                                                                                                                      from fct_conv_proy proy, fct_convenios con, tlempresas emp        "
    + "                                                                                                                                      where proy.id_conv_proy = :idConvProy       "
    + "                                                                                                                                      and con.id_convenio = proy.id_convenio       "
    + "                                                                                                                                      and emp.x_empresa = con.x_empresa),0))  as lgCotiza,       "
    + "       NVL((select LG_NUSS from fct_convproy_alu where x_matricula = mat.x_matricula and id_conv_proy = :idConvProy),0)  as lgNuss,          "
    + "       decode(alu.t_nuss,null,1,0)  as lgEditable,        "
    + "       CASE       "
    + "       WHEN calcula_edad(alu.f_nacimiento) < 16 THEN 1       "
    + "       ELSE 0 END AS lgMenor,       "
    + "       NVL((select LG_ERASMUS from fct_convproy_alu where x_matricula = mat.x_matricula and id_conv_proy = :idConvProy),0)  as lgErasmus       "
    + "       FROM TLMATALU mat,        "
    + "        TLALUMNOS alu,        "
    + "        TLUNIDADESCEN uni, "
    + "        TLOFEMATRGEN OMG "
    + "       WHERE mat.x_centro = :idCentro        "
    + "       AND mat.X_OFERTAMATRIG = OMG.X_OFERTAMATRIG  "
    + "       AND OMG.X_MODALIDAD =  :idModalidad "
    + "    AND OMG.D_OFERTAMATRIG LIKE '%LOFP%' "
    + "       AND mat.C_ANNO = :cAnno        "
    + "       AND nvl(mat.C_RESULTADO,99) > 1        "
    + "       AND mat.X_ALUMNO = alu.X_ALUMNO        "
    + "       AND mat.X_UNIDAD = uni.X_UNIDAD order by nombreCompleto  ", nativeQuery = true)
   List<AlumnoProjection> getAlumnosConvenioModalidad(Long idCentro, Long idModalidad, int cAnno, Long idConvProy);

   	@Query(value = "SELECT ID_PROYECTO FROM FCT_CONV_PROY WHERE ID_CONV_PROY = :idConvProy", nativeQuery = true)
	Long findProyectoIdByConvenioPrograma(Long idConvProy);

    ConveniosProyecto  findNuHorasTotalesById(Long idConvProy);
    
	@Query(value = " select ROUND(SUM(NU_HORAS),0) from fct_conv_proyhoraper WHERE id_conv_proy = :idConvProy ", nativeQuery = true)
    Integer getHorasProyecto(@Param("idConvProy") Long idConvProy);
	
    @Query(value = " select conv.* from fct_convproy_alu cona, fct_conv_proy conv, fct_convenios convs "
    		+ "where cona.x_matricula = :idMatricula "
    		+ "and cona.id_conv_proy = conv.id_conv_proy "
    		+ "and conv.id_convenio = convs.id_convenio "
    		+ "and convs.x_empresa = :xEmpresa", nativeQuery = true)
	List<ConveniosProyecto> updateHoras(Long xEmpresa, Long idMatricula);
}
