package es.jccm.edu.alumnos.adapter.out.repository.faltasAsistenciaAlumno;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.FaltaAsistenciaAlumno;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.QFaltaAsistenciaAlumno;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection.ConvAlumnCentroProjection;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection.FaltaAlumnoCentroProjection;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection.MotivoJustificacionProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface FaltaAsistenciaAlumnoRepository extends AbstractRepository<FaltaAsistenciaAlumno, Integer, QFaltaAsistenciaAlumno> {

    @Query(value = "CALL TLPQ_FALASIALU.TLP_PMFALASIALU(:N_CLAVE1, );", nativeQuery = true)
    void updateFaltasRetrasosAlumnos(@Param("N_CLAVE1") Long idMatricula);

	@Query(value= "SELECT cc.X_CONVCENTRO INDICE, cc.D_CONVOCATORIA DESCRIPCION, cc.F_FECFINCON fecfincon, cc.F_FECINICON fecinicon "
			+ "FROM TLCONVCENTROS cc,   TLCONVCENOMC comc,  TLMATALU mat,  TLOFEMATRGEN omg,   TLOFEMATRCEN omc "
			+ "WHERE cc.X_CONVCENTRO = comc.X_CONVCENTRO " 
			+ "AND comc.X_OFERTAMATRIC = omc.x_ofertamatric " 
			+ "AND omc.x_ofertamatrig = omg.x_ofertamatrig "
			+ "AND omg.x_ofertamatrig = mat.x_ofertamatrig "
			+ "AND cc.c_anno = mat.C_ANNO "
			+ "AND mat.x_matricula = :idMatricula "
			+ "AND omc.x_centro = mat.x_centro "
			+ "ORDER BY cc.N_ORDEN",nativeQuery = true)
	List<ConvAlumnCentroProjection> getConvCentro(@Param("idMatricula") Long idMatricula);
	
    @Query(value= "SELECT " + 
    		"  fal.x_falasialu idFalasialu, " + 
    		"  fal.f_falasi fecha, " + 
    		"  fal.x_notfalasialu idNotificacion, " + 
    		"  convierte_hora(n_inicio)|| ' - ' || convierte_hora(n_fin) tramo, " + 
    		"  DECODE(c_tipfal, 'I', 'Injustificada', DECODE(c_tipfal, 'J', 'Justificada', DECODE(c_tipfal, 'R', 'Retraso'))) tipo, " + 
    		"  jusfal.X_JUSFALASIALU idJustificacion," + 
    		"  jusfal.D_VALLISCAT motivo, " + 
    		" jusfal.X_VALLISCAT idMotivo," +
    		"  jusfal.T_OBSERVACION observacion, " + 
    		"  DECODE(fal.x_notfalasialu, NULL, 'No', 'Sí') notificacion " + 
    		"FROM " + 
    		"  DELPHOS_SEGEDU.tlfalasialu fal, " + 
    		"  DELPHOS_SEGEDU.tltramoshor tra, " + 
    		"  DELPHOS_SEGEDU.tlmatalu mua," + 
    		"  (SELECT jus.X_JUSFALASIALU, jus.T_OBSERVACION, val.D_VALLISCAT, jus.X_FALASIALU, val.X_VALLISCAT FROM DELPHOS_SEGEDU.tljusfalasialu JUS, DELPHOS_SEGEDU.tlvalliscat VAL" + 
    		"  where val.X_VALLISCAT = JUS.X_MOTFALASIALU) jusfal " + 
    		"WHERE " + 
    		"  tra.x_tramo = fal.x_tramo " + 
    		"  AND FAL.X_FALASIALU = jusfal.X_FALASIALU (+)" + 
    		"  AND fal.x_matricula = :idMatricula " + 
    		"  AND ('-1' = :type OR fal.c_tipfal = :type) " + 
    		"  AND tlf_intersecper(TO_DATE(:fecIni, 'dd/mm/rrrr'), TO_DATE(:fecFin, 'dd/mm/rrrr'), fal.f_falasi, fal.f_falasi) = 1 " + 
    		"  AND fal.x_matricula = mua.x_matricula " + 
    		"  AND EXISTS (SELECT 1 FROM DELPHOS_SEGEDU.tlhorariosr hor, DELPHOS_SEGEDU.tluniafetrahor uni " + 
    		"      WHERE hor.x_tramo = tra.x_tramo " + 
    		"      AND hor.x_horariore = uni.x_horariore " + 
    		"      AND hor.n_diasemana = to_number(to_char(fal.f_falasi, 'd')) " + 
    		"      AND (x_materiaomg IS NULL OR x_materiaomg = fal.x_materiaomg) " + 
    		"      AND EXISTS (SELECT 1 FROM DELPHOS_SEGEDU.tlgruactproalu gap1 " + 
    		"        WHERE gap1.x_empleado = hor.x_empleado " + 
    		"          AND gap1.f_tomapos = hor.f_tomapos " + 
    		"          AND gap1.x_centro = mua.x_centro " + 
    		"          AND gap1.x_actividad = hor.x_actividad " + 
    		"          AND gap1.c_anno = tra.c_anno " + 
    		"          AND gap1.x_gruactproalu IN (:idGroup) " + 
    		"          AND EXISTS (SELECT 1 FROM DELPHOS_SEGEDU.tluniafegruactpro act1 " + 
    		"            WHERE gap1.x_gruactproalu = act1.x_gruactproalu " + 
    		"              AND (act1.x_materiaomg = uni.x_materiaomg OR (act1.x_materiaomg IS NULL AND uni.x_materiaomg IS NULL)) " + 
    		"              AND act1.x_unidad = mua.x_unidad" + 
    		"          )" + 
    		"      )" + 
    		"  ) " + 
    		"  AND fal.l_diacom = 'N' " + 
    		"ORDER BY fal.f_falasi DESC", nativeQuery = true)
	List<FaltaAlumnoCentroProjection> getFaltasByAlumn(@Param("idMatricula") Long idMatricula, @Param("fecIni") String fecIni,
			@Param("fecFin") String fecFin, @Param("type") String type, @Param("idGroup") Long[] idGroup);
    
    @Query(value= "SELECT " + 
    		"  fal.x_falasialu idFalasialu, " + 
    		"  fal.f_falasi fecha, " + 
    		"  fal.x_notfalasialu idNotificacion, " + 
    		"  convierte_hora(n_inicio)|| ' - ' || convierte_hora(n_fin) tramo, " + 
    		"  DECODE(c_tipfal, 'I', 'Injustificada', DECODE(c_tipfal, 'J', 'Justificada', DECODE(c_tipfal, 'R', 'Retraso'))) tipo, " + 
    		"  jusfal.X_JUSFALASIALU idJustificacion," + 
    		"  jusfal.D_VALLISCAT motivo, " + 
    		" jusfal.X_VALLISCAT idMotivo," +
    		"  jusfal.T_OBSERVACION observacion, " + 
    		"  DECODE(fal.x_notfalasialu, NULL, 'No', 'Sí') notificacion " + 
    		"FROM " + 
    		"  DELPHOS_SEGEDU.tlfalasialu fal, " + 
    		"  DELPHOS_SEGEDU.tltramoshor tra, " + 
    		"  DELPHOS_SEGEDU.tlmatalu mua," + 
    		"  (SELECT jus.X_JUSFALASIALU, jus.T_OBSERVACION, val.D_VALLISCAT, jus.X_FALASIALU, val.X_VALLISCAT FROM DELPHOS_SEGEDU.tljusfalasialu JUS, DELPHOS_SEGEDU.tlvalliscat VAL" + 
    		"  where val.X_VALLISCAT = JUS.X_MOTFALASIALU) jusfal " + 
    		"WHERE " + 
    		"  tra.x_tramo = fal.x_tramo " + 
    		"  AND FAL.X_FALASIALU = jusfal.X_FALASIALU (+)" + 
    		"  AND fal.x_matricula = :idMatricula " + 
    		"  AND ('-1' = :type OR fal.c_tipfal = :type) " + 
    		"  AND tlf_intersecper(TO_DATE(:fecIni, 'dd/mm/rrrr'), TO_DATE(:fecFin, 'dd/mm/rrrr'), fal.f_falasi, fal.f_falasi) = 1 " + 
    		"  AND fal.x_matricula = mua.x_matricula " + 
    		"  AND EXISTS (SELECT 1 FROM DELPHOS_SEGEDU.tlhorariosr hor, DELPHOS_SEGEDU.tluniafetrahor uni " + 
    		"      WHERE hor.x_tramo = tra.x_tramo " + 
    		"      AND hor.x_horariore = uni.x_horariore " + 
    		"      AND hor.n_diasemana = to_number(to_char(fal.f_falasi, 'd')) " + 
    		"      AND (x_materiaomg IS NULL OR x_materiaomg = fal.x_materiaomg) " + 
    		"      AND EXISTS (SELECT 1 FROM DELPHOS_SEGEDU.tlgruactproalu gap1 " + 
    		"        WHERE gap1.x_empleado = hor.x_empleado " + 
    		"          AND gap1.f_tomapos = hor.f_tomapos " + 
    		"          AND gap1.x_centro = mua.x_centro " + 
    		"          AND gap1.x_actividad = hor.x_actividad " + 
    		"          AND gap1.c_anno = tra.c_anno " +  
    		"          AND EXISTS (SELECT 1 FROM DELPHOS_SEGEDU.tluniafegruactpro act1 " + 
    		"            WHERE gap1.x_gruactproalu = act1.x_gruactproalu " + 
    		"              AND (act1.x_materiaomg = uni.x_materiaomg OR (act1.x_materiaomg IS NULL AND uni.x_materiaomg IS NULL)) " + 
    		"              AND act1.x_unidad = mua.x_unidad" + 
    		"          )" + 
    		"      )" + 
    		"  ) " + 
    		"  AND fal.l_diacom = 'N' " + 
    		"ORDER BY fal.f_falasi DESC", nativeQuery = true)
	List<FaltaAlumnoCentroProjection> getFaltasByAlumnSinGruposAct(@Param("idMatricula") Long idMatricula, @Param("fecIni") String fecIni,
			@Param("fecFin") String fecFin, @Param("type") String type);
    
    @Query(value= "SELECT VAL.X_VALLISCAT idMotivo, VAL.D_VALLISCAT nombreMotivo "
    		+ "FROM DELPHOS_SEGEDU.tlcatlisval cal, DELPHOS_SEGEDU.tlvalliscat val "
    		+ "WHERE cal.C_CATLISVAL = 'MOTIVO_AUSENCIA' "
    		+ "AND VAL.X_CATLISVAL = CAL.X_CATLISVAL",nativeQuery = true)
	List<MotivoJustificacionProjection> getMotivosJustificacionFaltaAlumno();
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM DELPHOS_SEGEDU.TLNOTFALASITRA WHERE X_NOTFALASIALU = :idNotificacion", nativeQuery = true)
    void deleteTramosNotificacion(@Param("idNotificacion") Long idNotificacion);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM DELPHOS_SEGEDU.TLNOTFALASIALU WHERE X_NOTFALASIALU = :idNotificacion", nativeQuery = true)
    void deleteNotificacion(@Param("idNotificacion") Long idNotificacion);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE DELPHOS_SEGEDU.TLFALASIALU SET X_NOTFALASIALU = 0 WHERE X_NOTFALASIALU = :idNotificacion", nativeQuery = true)
    void deleteRelacionNotificacion(@Param("idNotificacion") Long idNotificacion);


    
}