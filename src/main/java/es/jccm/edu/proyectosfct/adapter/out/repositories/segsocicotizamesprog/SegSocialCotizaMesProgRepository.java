package es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesprog;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection.ListadoHistoricoCotizaMesProjection;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesprogramas.entities.CotizaMesProgramas;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesprogramas.entities.QCotizaMesProgramas;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SegSocialCotizaMesProgRepository  extends AbstractRepository<CotizaMesProgramas, Long, QCotizaMesProgramas> {

 List<CotizaMesProgramas> findByIdConvProgAlu(Long id);
 
 @Query(value = " select distinct alu.T_APELLIDO1 || ' ' || alu.T_APELLIDO2 || ',' || alu.T_NOMBRE as nombreCompleto,   "
   + "              TO_CHAR(cot.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio,           "
   + "              LPAD((SELECT COUNT(nu_peticion) + 1    "
   + "                    FROM fct_historicomes_prog    "
   + "                    WHERE id_cotizames_prog = cot.id_cotizames_prog),2,'0')  as nuPeticion,            "
   + "              CASE     "
   + "                   WHEN hag.id_historicomes_prog is null then 'Alta'     "
   + "                   ELSE 'Modificacion'    "
   + "              END as accion,    "
   + "              cot.ds_warnings as dsWarnings,   "
   + "              cot.NU_DIAS_REAL as nuDiasRealMesProg,   "
   + "              cot.NU_DIAS_INTE as nuDiasInteMesProg,   "
   + "              cot.NU_DIAS_NACU as nuDiasNacuMesProg,   "
   + "              cot.NU_DIAS_INTE_ERA as nuDiasInteEraMesProg  ,  "
   + "                    CASE cot.nu_mes  "
   + "                         WHEN 1 THEN 'Enero '  "
   + "                         WHEN 2 THEN 'Febrero '   "
   + "                         WHEN 3 THEN 'Marzo '   "
   + "                         WHEN 4 THEN 'Abril ' "
   + "                         WHEN 5 THEN 'Mayo '    "
   + "                         WHEN 6 THEN 'Junio '  "
   + "                         WHEN 7 THEN 'Julio '  "
   + "                         WHEN 8 THEN 'Agosto '   "
   + "                         WHEN 9 THEN 'Septiembre '   "
   + "                         WHEN 10 THEN 'Octubre '   "
   + "                         WHEN 11 THEN 'Noviembre '  "
   + "                         ELSE 'Diciembre '  "
   + "                         END as mes"
   + "        from fct_cotizames_prog cot, tlmatalu mat, tlalumnos alu, fct_historicomes_prog hag    "
   + "        where mat.x_matricula = cot.x_matricula    "
   + "        and alu.x_alumno = mat.x_alumno    "
   + "        and cot.id_cotizames_prog = hag.id_cotizames_prog( +)    "
   + "        and cot.id_cotizames_prog = :idCotizaMes    "
   + "        union all    "
   + "        select distinct cot.nombre as nombreCompleto,    "
   + "              TO_CHAR(hag.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio,    "
   + "              LPAD(hag.nu_peticion,2,'0') as nuPeticion,    "
   + "              CASE     "
   + "                   WHEN hag.nu_peticion = 1 then 'Alta'     "
   + "                   ELSE 'Modificacion'    "
   + "              END as accion,       "
   + "          hag.ds_warnings as dsWarnings,   "
   + "          hag.NU_DIAS_REAL as nuDiasRealMesProg,   "
   + "          hag.NU_DIAS_INTE as nuDiasInteMesProg,   "
   + "          hag.NU_DIAS_NACU as nuDiasNacuMesProg,   "
   + "          hag.NU_DIAS_INTE_ERA as nuDiasInteEraMesProg , "
   + "                CASE hag.nu_mes  "
   + "                     WHEN 1 THEN 'Enero '   "
   + "                     WHEN 2 THEN 'Febrero '   "
   + "                     WHEN 3 THEN 'Marzo '   "
   + "                     WHEN 4 THEN 'Abril ' "
   + "                     WHEN 5 THEN 'Mayo '    "
   + "                     WHEN 6 THEN 'Junio '  "
   + "                     WHEN 7 THEN 'Julio '  "
   + "                     WHEN 8 THEN 'Agosto '   "
   + "                     WHEN 9 THEN 'Septiembre '   "
   + "                     WHEN 10 THEN 'Octubre '   "
   + "                     WHEN 11 THEN 'Noviembre '  "
   + "                     ELSE 'Diciembre '  "
   + "                     END as mes "
   + "        from fct_historicomes_prog hag,    "
   + "            ( SELECT alu.T_APELLIDO1 || ' ' || alu.T_APELLIDO2 || ',' || alu.T_NOMBRE as nombre    "
   + "              FROM fct_cotizames_prog cot, tlmatalu mat, tlalumnos alu     "
   + "              WHERE mat.x_matricula = cot.x_matricula    "
   + "              AND alu.x_alumno = mat.x_alumno    "
   + "              AND cot.id_cotizames_prog = :idCotizaMes    "
   + "            ) cot    "
   + "        where hag.id_cotizames_prog =  :idCotizaMes    "
   + "        order by nuPeticion desc ", nativeQuery = true)
 List<ListadoHistoricoCotizaMesProjection> getListadoHistoricoMes(Long idCotizaMes);

    @Query(value = "select count(*) " +
            "              from fct_cotizames_prog " +
            "              where id_cotizames_prog = :id " +
            "              and nu_dias_real = :nuDiasReal " +
            "              and nu_dias_nacu = :nuDiasNacu " +
            "              and nu_dias_inte = :nuDiasInte " +
            "              and NVL(nu_dias_inte_era, 0) = :nuDiasInteEra " +
            "              and nu_mes = :nuMes" , nativeQuery = true)
    int getConsistenciaMesGestora(Long id, Integer nuMes, Integer nuDiasReal, Integer nuDiasNacu, Integer nuDiasInte, Integer nuDiasInteEra);

    CotizaMesProgramas findByIdConvProgAluAndNuMes(Long idConvProgAlu, Integer nuMes);


    @Query(value = "SELECT c.* " +
            " FROM FCT_COTIZAMES_PROG c " +
            " JOIN FCT_ALTASS_PROG a ON c.ID_CONVPROG_ALU = a.ID_CONVPROG_ALU AND c.X_MATRICULA = a.X_MATRICULA " +
            " WHERE c.F_ENVIOSS IS NULL " +
            " AND a.ID_ALTASS_PROG = :idAltaProg " +
            " AND TO_DATE( " +
            "    '01-' || LPAD(c.NU_MES, 2, '0') || '-' || " +
            "    CASE " +
            //"        WHEN c.NU_MES IN (1,2,3,4,5,6,7,8) THEN TO_CHAR(EXTRACT(YEAR FROM a.F_INICIO) + 1) " +
            "        WHEN c.NU_MES IN (9,10,11,12) THEN TO_CHAR(EXTRACT(YEAR FROM a.F_INICIO) + 1) " +
            "        ELSE TO_CHAR(EXTRACT(YEAR FROM a.F_INICIO)) " +
            "    END, " +
            "    'DD-MM-YYYY') " +
            " BETWEEN TRUNC(a.F_INICIO, 'MM') AND LAST_DAY(a.F_FIN) " + 
            " AND not exists ( SELECT 1        " +
            "                  FROM FCT_ALTASS_PROY aly2    " +    
            "                  WHERE aly2.X_MATRICULA = a.X_MATRICULA " +                                                
            "                  AND NVL(aly2.LG_ANULADO, 0) = 0        " +
            "                  AND TO_DATE('01-' || LPAD(c.NU_MES, 2, '0') || '-' || " +       
            "                  CASE                " +
            "                  WHEN c.NU_MES IN (9,10,11,12) THEN TO_CHAR(aly2.F_INICIO + INTERVAL '1' YEAR) " +   
            "                  ELSE TO_CHAR(aly2.F_INICIO, 'YYYY')    " +
            "                  END, 'DD-MM-YYYY') " +
            "                  BETWEEN TRUNC(aly2.F_INICIO, 'MM') AND LAST_DAY(aly2.F_FIN)) " +                          
            " AND not exists ( SELECT 1        " +
            "                  FROM FCT_ALTASS_PROG alg2       " + 
            "                  WHERE alg2.X_MATRICULA = a.X_MATRICULA " +    
            "                  AND alg2.ID_ALTASS_PROG <> a.ID_ALTASS_PROG  " +
            "                  AND NVL(alg2.LG_ANULADO, 0) = 0        " +
            "                  AND TO_DATE('01-' || LPAD(c.NU_MES, 2, '0') || '-' || " +       
            "                  CASE                " +
            "                  WHEN c.NU_MES IN (9,10,11,12) THEN TO_CHAR(alg2.F_INICIO + INTERVAL '1' YEAR) " +   
            "                  ELSE TO_CHAR(alg2.F_INICIO, 'YYYY')    " +
            "                  END, 'DD-MM-YYYY') " +
            "                  BETWEEN TRUNC(alg2.F_INICIO, 'MM') AND LAST_DAY(alg2.F_FIN)) ",nativeQuery = true)
    List<CotizaMesProgramas> findCotizaMesNoEnviadaSolapadaAltaProg(Long idAltaProg);


}
