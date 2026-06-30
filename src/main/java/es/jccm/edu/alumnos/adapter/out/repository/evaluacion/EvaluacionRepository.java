package es.jccm.edu.alumnos.adapter.out.repository.evaluacion;

import java.sql.Blob;
import java.util.List;

import es.jccm.edu.alumnos.application.domain.evaluacion.projection.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.alumnos.application.domain.evaluacion.Evaluacion;
import es.jccm.edu.alumnos.application.domain.evaluacion.QEvaluacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaluacionRepository extends AbstractRepository<Evaluacion, Integer, QEvaluacion> {

	@Query(value = "SELECT " +
			"    mua.x_matricula idMatricula, " +
			"    alu.x_alumno idAlumno, " +
			"    DELPHOS.TLF_NOMBRE(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) nombreAlumno, " +
			"    FOT.B_FOTO foto, " +
			"    estconvuni.x_estadoconv idEstadoConvocatoria, " +
			"    estconvuni.d_estadoconv nombreEstadoConvocatoria, " +
			"    CASE WHEN MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG THEN 1 ELSE 0 END AS acnee, " +
			"    (SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG) nivelCurricular " +
			"FROM " +
			"    DELPHOS_SEGEDU.tluniafegruactpro uag, " +
			"    tlmatalu mua, " +
			"    DELPHOS_SEGEDU.tlalumnos alu, " +
			"    DELPHOS_SEGEDU.TLGRUACTPROALU grua, " +
			"    DELPHOS.tlmatmatrialu mat, " +
			"    DELPHOS.TLALUMNOSFOTO FOT, " +
			"    ( " +
			"        SELECT " +
			"            est.x_estadoconv, " +
			"            est.d_estadoconv, " +
			"            cco.x_ofertamatric, " +
			"            cu.x_unidad " +
			"        FROM " +
			"            DELPHOS_SEGEDU.tlconvcenomc cco, " +
			"            DELPHOS.tlconvunidad cu, " +
			"            DELPHOS.tlestadosconv est  " +
			"        WHERE " +
			"            cco.x_convcentro = :idConvocatoria " +
			"            AND cu.x_convcentroomc = cco.x_convcentroomc " +
			"            AND est.x_estadoconv = cu.x_estadoconv " +
			"    ) estconvuni " +
			"WHERE " +
			"    uag.x_gruactproalu IN (:idGrupoAct) " +
			"    AND grua.x_empleado = :idEmpleado  " +
			"    AND ALU.X_ALUMNO = FOT.X_ALUMNO (+) " +
			"    AND mua.x_unidad = uag.x_unidad " +
			"    AND mat.x_MATRICULA = mua.X_MATRICULA " +
			"    AND nvl(mua.c_resultado, 99) > tlf_parametro('MAT_CANCEL') " +
			"    AND tlf_valida_grupo_matricula(uag.x_gruactproalu, mua.x_matricula) = 1 " +
			"    AND alu.x_alumno = mua.x_alumno " +
			"    AND uag.x_gruactproalu = grua.x_gruactproalu  " +
			"    AND uag.x_materiaomg = mat.x_materiaomg " +
			"    AND mua.x_ofertamatric = estconvuni.x_ofertamatric (+) " +
			"    AND uag.x_unidad = estconvuni.x_unidad (+) " +
			"    AND NVL(mua.C_RESULTADO,99) > 1  " +
			"ORDER BY " +
			"    grua.s_gruactproalu, uag.x_unidad, nombreAlumno", nativeQuery = true)
	List<EvaluacionProjection> getNotasByGrupoActividad(@Param("idGrupoAct") Long[] idGrupoAct, @Param("idEmpleado") Long idEmpleado, 
			@Param("idConvocatoria") Long idConvocatoria);

	@Query(value = "SELECT FOT.B_FOTO FOTO from DELPHOS.TLALUMNOSFOTO FOT WHERE FOT.X_ALUMNO = :idAlumno", nativeQuery = true)
	Blob getFotoAlumno(@Param("idAlumno") Long idAlumno);

	@Query(value = "SELECT DISTINCT mma.x_matmatricula idMatMatricula,  " +
			" uag.x_gruactproalu idGrupoAct,  " +
			" cco.x_convcentroomc idConvCentroOmc,  " +
			" cce.x_convocatoria idConvocatoria,  " +
			" uag.X_MATERIAOMG idMateriaOmg, " +
			" mua.X_OFERTAMATRIG idOfertaMatrig, " +
			" est.x_estado idEstado,  " +
			" est.t_abrev nombreEstado,  " +
			" (SELECT nvl((cal.t_abrev), eva.n_nota)  " +
			"     FROM tlnoteva eva,  " +
			"     TLCALIFICACIONES cal  " +
			"     WHERE eva.x_matmatricula = mma.x_matmatricula  " +
			"     AND eva.x_convcentroomc = cco.x_convcentroomc  " +
			"     and cal.X_CALIFICA = eva.X_CALIFICA ) nota,  " +
			" uag.x_unidad idUnidad,  " +
			" nvl((notasPropuestas.X_CALIFICA), notasPropuestas.n_nota) idNotaPropuesta, " +
			" nvl((notasPropuestas.T_ABREV), notasPropuestas.n_nota) notaPropuesta, " +
			" nvl((notasPropuestas.L_APRUEBA), notasPropuestas.n_nota) apruebaMateriaNotaPropuesta, " +
			" CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND mma.x_adaptacion IS NOT null) THEN 1 ELSE 0 END AS acnee, " +
			" (SELECT MATCURSO.T_ABREV FROM EVA_PROGDIDAC PD " +
			" INNER JOIN TLMATOFEMATRG MATOFEMATRG ON MATOFEMATRG.X_MATERIAOMG = PD.X_MATERIAOMGADAP " +
			" INNER JOIN TLMATERIASCURSO MATCURSO ON MATCURSO.X_MATERIAC = MATOFEMATRG.X_MATERIAC " +
			" WHERE PD.NU_ANNO = mua.C_ANNO AND PD.X_OFERTAMATRIG = MUA.X_OFERTAMATRIG " +
			" AND PD.X_NIVEADAP = MUA.X_NIVEADAP AND PD.X_MATERIAOMG = MMA.X_MATERIAOMG AND PD.X_CENTRO = mua.x_centro) materiaAdap, " +
			" (SELECT nvl((cal.t_abrev), eva.n_nota) FROM TLNOTEVA eva, " +
			"    TLCALIFICACIONES cal, " +
			"    (SELECT cce2.X_CONVCENTRO, cco2.X_CONVCENTROOMC, cu.F_SESION FROM DELPHOS.TLCONVUNIDAD cu " +
			"    INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTROOMC = cu.X_CONVCENTROOMC " +
			"    INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CONVCENTRO = cco2.X_CONVCENTRO  " +
			"    WHERE cu.X_UNIDAD = mua.X_UNIDAD AND cce2.X_CONVOCATORIA = 1) convord " +
			"    WHERE eva.x_matmatricula = mma.x_matmatricula " +
			"    AND cal.X_CALIFICA = eva.X_CALIFICA " +
			"    AND cce.X_CONVOCATORIA = 2 " +
			"    AND mua.F_PROMOCION = convord.F_SESION " +
			"    AND eva.X_CONVCENTROOMC = convord.X_CONVCENTROOMC) notaConvocatoriaOrdinaria " +
			" FROM DELPHOS_SEGEDU.tlconvcentros cce,  " +
			" DELPHOS_SEGEDU.tlconvcenomc cco,  " +
			" DELPHOS_SEGEDU.tlalumnos alu,  " +
			" tlmatalu mua,  " +
			" DELPHOS_SEGEDU.tlgruactproalu gap,  " +
			" tlmatmatrialu mma,  " +
			" DELPHOS_SEGEDU.tlmatofematrg mog,  " +
			" DELPHOS_SEGEDU.tlgrupomat gdm,  " +
			" DELPHOS_SEGEDU.tlcondmaterias con,  " +
			" DELPHOS_SEGEDU.tlestadomat est,  " +
			" DELPHOS_SEGEDU.tlmateriascurso mcu, " +
			" DELPHOS_SEGEDU.tluniafegruactpro uag, " +
			" (SELECT ncm.x_califica, cal.T_ABREV, cal.L_APRUEBA, NCM.N_NOTA, NCM.X_CONVCENTROOMC, NCM.X_MATMATRICULA " +
			" FROM DELPHOS.TLNOTCALMAT NCM, DELPHOS.TLCALIFICACIONES CAL " +
			" WHERE CAL.X_CALIFICA = NCM.X_CALIFICA) notasPropuestas " +
			" WHERE cce.x_convcentro = cco.x_convcentro  " +
			" AND cce.c_anno = MUA.C_ANNO  " +
			" and alu.x_alumno = mua.x_alumno  " +
			" AND mua.x_centro = gap.x_centro  " +
			" AND mua.x_unidad = uag.x_unidad  " +
			" AND cco.x_ofertamatric = mua.x_ofertamatric  " +
			" AND mua.x_matricula = mma.x_matricula  " +
			" AND uag.x_materiaomg = mma.x_materiaomg  " +
			" AND gap.x_gruactproalu = uag.x_gruactproalu  " +
			" AND DELPHOS_SEGEDU.tlf_valida_grupo_matricula(gap.x_gruactproalu, mua.x_matricula) = 1  " +
			" AND gdm.x_grupomat = mog.x_grupomat  " +
			" AND mog.x_ofertamatrig = con.x_ofertamatrig  " +
			" AND mma.x_estado = est.x_estado  " +
			" AND mog.x_materiac = mcu.x_materiac  " +
			" AND uag.x_materiaomg = mma.x_materiaomg  " +
			" AND mma.x_matmatricula = notasPropuestas.x_matmatricula (+) " +
			" AND cco.x_convcentroomc = notasPropuestas.x_convcentroomc (+) " +
			" AND cce.x_convcentro = :idConvocatoria  " +
			" AND mua.x_matricula = :idMatricula  " +
			" AND uag.x_gruactproalu IN (:idGrupoAct) order by idGrupoAct", nativeQuery = true)
	List<CalificacionProjection> getCalificacionesByMatricula(@Param("idGrupoAct") Long[] idGrupoAct, @Param("idMatricula") Long idMatricula, @Param("idConvocatoria") Long idConvocatoria);

    
	
	@Query(value = "SELECT COUNT(DISTINCT nvl(mom.x_sistcal, nvl(sco.x_sistcal,omg.x_sistcal))) "
			+ "FROM tluniafegruactpro UAG, TLMATOFEMATRG MOM, tlofematrgen omg, "
			+ "(select * from tlsiscalOMG where :anno between c_annodesde and nvl(c_annohasta, 9999)) sco "
			+ "WHERE UAG.X_MATERIAOMG = MOM.X_MATERIAOMG " + "and mom.x_ofertamatrig = omg.x_ofertamatrig "
			+ "and omg.x_ofertamatrig = sco.x_ofertamatrig(+) "
			+ "AND UAG.X_GRUACTPROALU = :idGrupoAct", nativeQuery = true)
	Integer getNumeroSistemasCalificacion(@Param("idGrupoAct") Long idGrupoAct, @Param("anno") Integer anno);

	@Query(value = "SELECT DISTINCT cal.x_califica idCalifica, cal.d_califica calificacion, cal.t_abrev abreviatura, cal.L_APRUEBA apruebaMateria "
			+ "from tlcalificaciones cal, tlmatofematrg mog, tlofematrgen omg, "
			+ "(SELECT x_materiaomg, X_GRUACTPROALU FROM tluniafegruactpro WHERE X_GRUACTPROALU = :idGrupoAct) uag, "
			+ "(select * from tlsiscalOMG where :anno between c_annodesde and nvl(c_annohasta, 9999)) sco "
			+ "WHERE uag.x_materiaomg = mog.x_materiaomg " + "and mog.x_ofertamatrig = omg.x_ofertamatrig "
			+ "and omg.x_ofertamatrig = sco.x_ofertamatrig(+) "
			+ "and cal.x_sistcal = nvl(mog.x_sistcal, nvl(sco.x_sistcal,omg.x_sistcal)) "
			+ "order by idCalifica", nativeQuery = true)
	List<ListCalificacionesProjection> getTipoSistemaCalificacion(@Param("idGrupoAct") Long idGrupoAct,
			@Param("anno") Integer anno);

	@Query(value = "select DISTINCT cal.X_CALIFICA idCalifica, cal.d_califica calificacion, cal.t_abrev abreviatura, cal.L_APRUEBA apruebaMateria "
			+ "from tlcalificaciones cal, tlmatmatrialu mma, tlmatofematrg mog, tlofematrgen omg, "
			+ "(select * from tlsiscalOMG where :anno between c_annodesde and nvl(c_annohasta, 9999)) sco "
			+ "where mma.x_matmatricula = :idMatMatricula " + "and mma.x_materiaomg = mog.x_materiaomg "
			+ "and mog.x_ofertamatrig = omg.x_ofertamatrig " + "and omg.x_ofertamatrig = sco.x_ofertamatrig(+) "
			+ "and cal.x_sistcal = nvl(mog.x_sistcal, nvl(sco.x_sistcal,omg.x_sistcal)) "
			+ "order by idCalifica", nativeQuery = true)
	List<ListCalificacionesProjection> getTipoSistemaCalificacionPorAlumno(@Param("idMatMatricula") Long idMatMatricula,
			@Param("anno") Integer anno);

	// TODO: añadir idMatMatriAlu
	@Query(value = "SELECT mua.x_matricula idMatricula, alu.x_alumno idAlumno, DELPHOS.TLF_NOMBRE(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) nombreAlumno, " +
			"    estconvuni.x_estadoconv idEstadoConvocatoria, estconvuni.d_estadoconv nombreEstadoConvocatoria , " +
			"    CASE WHEN MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG THEN 1 ELSE 0 END AS acnee, " +
			"    (SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG) nivelCurricular " +
			"FROM  tlmatalu mua,  tlalumnos alu,  tlunidadescen udc, " +
			"    (SELECT est.x_estadoconv,  est.d_estadoconv,  cco.x_ofertamatric,  cu.x_unidad " +
			"    FROM  DELPHOS_SEGEDU.tlconvcenomc cco,  DELPHOS.tlconvunidad cu,  DELPHOS.tlestadosconv est " +
			"    WHERE  cco.x_convcentro = :idConvocatoria  AND cu.x_convcentroomc = cco.x_convcentroomc " +
			"    AND est.x_estadoconv = cu.x_estadoconv) estconvuni " +
			"WHERE  mua.X_UNIDAD = :idUnidad  AND mua.x_unidad = udc.x_unidad  AND alu.x_alumno = mua.x_alumno " +
			" AND mua.X_OFERTAMATRIG = :idOfertaMatrig " +
			"AND mua.x_ofertamatric = estconvuni.x_ofertamatric (+)  AND mua.x_unidad = estconvuni.x_unidad (+) " +
			"AND NVL(mua.C_RESULTADO,99) > 1 " +
			"ORDER BY  udc.x_unidad,  nombreAlumno", nativeQuery = true)
	List<EvaluacionProjection> getNotasByUnidad(@Param("idUnidad") Long idUnidad, @Param("idConvocatoria") Long idConvocatoria,
												@Param("idOfertaMatrig") Long idOfertaMatrig);

	
    @Query(value = "SELECT mma.x_matmatricula idMatMatricula,  " +
			" MCU.X_MATERIAC idMateria,  " +
			" cco.x_convcentroomc idConvCentroOmc,  " +
			" mma.x_materiaomg idMateriaOmg, " +
			" cce.x_convocatoria idConvocatoria,  " +
			" mua.X_OFERTAMATRIG idOfertaMatrig, " +
			" est.x_estado idEstado,  " +
			" est.t_abrev nombreEstado,  " +
			" (SELECT nvl((cal.t_abrev), eva.n_nota)  " +
			"     FROM tlnoteva eva,  " +
			"     TLCALIFICACIONES cal  " +
			"     WHERE eva.x_matmatricula = mma.x_matmatricula  " +
			"     AND eva.x_convcentroomc = cco.x_convcentroomc  " +
			"     and cal.X_CALIFICA = eva.X_CALIFICA ) nota,  " +
			"(SELECT cal.l_aprueba   " +
			"     FROM tlnoteva eva,   " +
			"     TLCALIFICACIONES cal   " +
			"     WHERE eva.x_matmatricula = mma.x_matmatricula   " +
			"     AND eva.x_convcentroomc = cco.x_convcentroomc   " +
			"     and cal.X_CALIFICA = eva.X_CALIFICA ) aprueba," +
			" nvl((notasPropuestas.X_CALIFICA), notasPropuestas.n_nota) idNotaPropuesta, " +
			" nvl((notasPropuestas.T_ABREV), notasPropuestas.n_nota) notaPropuesta, " +
			" nvl((notasPropuestas.L_APRUEBA), notasPropuestas.n_nota) apruebaMateriaNotaPropuesta," +
			" CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND mma.x_adaptacion IS NOT null) THEN 1 ELSE 0 END AS acnee, " +
			"             (SELECT MATCURSO.T_ABREV FROM EVA_PROGDIDAC PD " +
			"             INNER JOIN TLMATOFEMATRG MATOFEMATRG ON MATOFEMATRG.X_MATERIAOMG = PD.X_MATERIAOMGADAP " +
			"             INNER JOIN TLMATERIASCURSO MATCURSO ON MATCURSO.X_MATERIAC = MATOFEMATRG.X_MATERIAC " +
			"             WHERE PD.NU_ANNO = mua.C_ANNO AND PD.X_OFERTAMATRIG = MUA.X_OFERTAMATRIG " +
			"             AND PD.X_NIVEADAP = MUA.X_NIVEADAP AND PD.X_MATERIAOMG = MMA.X_MATERIAOMG AND PD.X_CENTRO = mua.x_centro) materiaAdap, " +
			" (SELECT nvl((cal.t_abrev), eva.n_nota) FROM TLNOTEVA eva, " +
			"    TLCALIFICACIONES cal, " +
			"    (SELECT cce2.X_CONVCENTRO, cco2.X_CONVCENTROOMC, cu.F_SESION FROM DELPHOS.TLCONVUNIDAD cu " +
			"    INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTROOMC = cu.X_CONVCENTROOMC " +
			"    INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CONVCENTRO = cco2.X_CONVCENTRO  " +
			"    WHERE cu.X_UNIDAD = mua.X_UNIDAD AND cce2.X_CONVOCATORIA = 1) convord " +
			"    WHERE eva.x_matmatricula = mma.x_matmatricula " +
			"    AND cal.X_CALIFICA = eva.X_CALIFICA " +
			"    AND cce.X_CONVOCATORIA = 2 " +
			"AND MMA.L_APROBADA = 'S' " +
			"    AND eva.X_CONVCENTROOMC = convord.X_CONVCENTROOMC) notaConvocatoriaOrdinaria, " +
			"DECODE(mma.L_APROBADA, 'S', 'true', 'false') materiaAprobada " +
			" FROM tlmatalu mua,    " +
			" tlmatmatrialu mma,  " +
			" DELPHOS_SEGEDU.tlmatofematrg mog,    " +
			" DELPHOS_SEGEDU.tlgrupomat gdm,  " +
			" DELPHOS_SEGEDU.tlcondmaterias con,    " +
			" DELPHOS_SEGEDU.tlmateriascurso mcu,  " +
			" DELPHOS_SEGEDU.tlestadomat est,    " +
			" DELPHOS_SEGEDU.tlconvcenomc cco,    " +
			" DELPHOS_SEGEDU.tlconvcentros cce, " +
			" (SELECT ncm.x_califica, cal.T_ABREV, cal.L_APRUEBA, NCM.N_NOTA, NCM.X_CONVCENTROOMC, NCM.X_MATMATRICULA " +
			" FROM DELPHOS.TLNOTCALMAT NCM, DELPHOS.TLCALIFICACIONES CAL " +
			" WHERE CAL.X_CALIFICA = NCM.X_CALIFICA) notasPropuestas " +
			" WHERE   mog.x_materiac = mcu.x_materiac    " +
			" AND cco.x_ofertamatric = mua.x_ofertamatric  " +
			" and cce.x_convcentro = cco.x_convcentro    " +
			" AND cce.c_anno = mua.c_anno  " +
			" AND cce.x_centro = mua.x_centro  " +
			" AND mog.x_ofertamatrig = con.x_ofertamatrig  " +
			" AND gdm.x_grupomat = mog.x_grupomat  " +
			" AND mma.x_materiaomg = mog.x_materiaomg  " +
			" AND gdm.x_grupomat = con.x_grupomat  " +
			" AND mua.x_matricula = mma.x_matricula  " +
			" AND mma.x_estado = est.x_estado  " +
			" AND mma.x_matmatricula = notasPropuestas.x_matmatricula (+) " +
			" AND cco.x_convcentroomc = notasPropuestas.x_convcentroomc (+) " +
			" AND cce.x_convcentro = :idConvocatoria  " +
			" AND mua.x_matricula = :idMatricula  " +
			" and MUA.X_UNIDAD = :idUnidad  " +
			" order BY idMatMatricula", nativeQuery = true)
	List<CalificacionProjection> getCalificacionesByUnidadMatricula(@Param("idUnidad") Long idUnidad,
			@Param("idMatricula") Long idMatricula, @Param("idConvocatoria") Long idConvocatoria);


	@Query(value = " WITH MateriasAlumno AS ( " +
			"    SELECT EST.D_ESTADO, t.D_MATERIAC nombre, MMA.* " +
			"    FROM TLMATMATRIALU mma " +
			"    INNER JOIN TLESTADOMAT EST ON EST.X_ESTADO = MMA.X_ESTADO  " +
			"    INNER JOIN TLMATOFEMATRG matofe ON matofe.X_MATERIAOMG = mma.X_MATERIAOMG  " +
			"    INNER JOIN TLMATERIASCURSO t ON t.X_MATERIAC = matofe.X_MATERIAC  " +
			"    WHERE mma.X_MATRICULA = :idMatricula " +
			"), MateriasLlave AS ( " +
			"    SELECT mll.x_materiaomg AS materia, mll.X_MATERIAOMGLLAVE AS materia_llave " +
			"    FROM TLMATERIASLLAVE mll " +
			"), MateriasConLlave AS ( " +
			"    SELECT DISTINCT ma.X_MATERIAOMG,  ml.materia_llave " +
			"    FROM MateriasAlumno ma " +
			"    INNER JOIN MateriasLlave ml ON ma.X_MATERIAOMG = ml.materia " +
			"    WHERE ml.materia_llave IN (SELECT X_MATERIAOMG FROM MateriasAlumno WHERE L_APROBADA <> 'S') " +
			") " +
			"SELECT  " +
			"ma.X_MATERIAOMG idMateriaOmg, ma.materia_llave idMateriaOmgLlave " +
			"FROM  " +
			"    MateriasConLlave ma", nativeQuery = true)
	List<materiaLlaveProjection> getMateriasLlaveByMatricula(@Param("idMatricula") Long idMatricula);

	@Query(value = "SELECT distinct MCU.X_MATERIAC idMateria, MCU.D_MATERIAC nombreMateria, MCU.T_ABREV abreviatura, ciclo.X_ETAPADEPENDEDE idEtapa, " +
			"mog.X_MATERIAOMG idMateriaOmg, mog.X_OFERTAMATRIG idOfertaMatrig, MCU.N_ORDENACTA ordenActa, " +
			"DECODE(SIGN(INSTR(ETAPA.D_ETAPA, 'LOMLOE') || INSTR(CICLO.D_ETAPA, 'LOMLOE')),0,0,1) lomloe, omg.D_OFERTAMATRIG cursoMateria " +
			"FROM DELPHOS_SEGEDU.tlmatalu mua, " +
			"DELPHOS_SEGEDU.tlmatmatrialu mma, " +
			"DELPHOS_SEGEDU.tlmatofematrg mog, " +
			"DELPHOS_SEGEDU.tlgrupomat gdm, " +
			"DELPHOS_SEGEDU.tlcondmaterias con, " +
			"DELPHOS_SEGEDU.tlmateriascurso mcu, " +
			"TLETAPAS CICLO, " +
			"TLETAPAS ETAPA, " +
			"TLETAPAS CURSO, " +
			"TLCURSOMODA MODALIDAD, " +
			"TLOFEMATRGEN omg " +
			"WHERE MUA.X_UNIDAD = :idUnidad " +
			"AND mog.x_materiac = mcu.x_materiac " +
			"AND mog.x_ofertamatrig = con.x_ofertamatrig " +
			"AND mog.X_OFERTAMATRIG = omg.X_OFERTAMATRIG  " +
			"AND gdm.x_grupomat = mog.x_grupomat " +
			"AND mma.x_materiaomg = mog.x_materiaomg " +
			"AND mma.X_ESTADO IN (1,3) " +
			"AND gdm.x_grupomat = con.x_grupomat " +
			"AND NVL(mua.C_RESULTADO,99) > 1   " +
			"AND mua.x_matricula = mma.x_matricula " +
			"AND mcu.X_CURSOMOD = MODALIDAD.X_CURSOMOD " +
			"AND CURSO.X_ETAPA = MODALIDAD.X_ETAPA " +
			"AND CURSO.X_ETAPADEPENDEDE = CICLO.X_ETAPA " +
			"AND CICLO.X_ETAPADEPENDEDE = ETAPA.X_ETAPA " +
			"order BY mog.X_OFERTAMATRIG DESC, MCU.N_ORDENACTA ASC", nativeQuery = true)
	List<MateriaUnidadProjection> getMateriaByUnidad(@Param("idUnidad") Long idUnidad);

	@Query(value = "SELECT CAL.X_CALIFICA idCalifica, CAL.D_CALIFICA calificacion, CAL.t_abrev abreviatura, cal.L_APRUEBA apruebaMateria "
			+ "FROM TLCALIFICACIONES CAL, TLMATOFEMATRG MOG, TLOFEMATRGEN OMG, "
			+ "(SELECT * FROM TLSISCALOMG WHERE :anno BETWEEN C_ANNODESDE AND NVL(C_ANNOHASTA, 9999)) SCO "
			+ "WHERE MOG.X_MATERIAC = :idMateria " + "AND MOG.X_OFERTAMATRIG = OMG.X_OFERTAMATRIG "
			+ "AND OMG.X_OFERTAMATRIG = SCO.X_OFERTAMATRIG(+) "
			+ "AND CAL.X_SISTCAL = NVL(MOG.X_SISTCAL, NVL(SCO.X_SISTCAL,OMG.X_SISTCAL)) "
			+ "ORDER BY idCalifica", nativeQuery = true)
	List<ListCalificacionesProjection> getSistemaCalificacionByIdMateria(@Param("idMateria") Long idMateria,
			@Param("anno") Integer anno);
	
	@Query(value= "SELECT ccen.x_convcentro idConvocatoria, ccen.d_convocatoria nombre, ccen.f_fecinicon fechaInicio, ccen.f_fecfincon fechaFin, "
			+ "NVL(conv.d_convocatoria, 'Parcial') AS tipoConvocatoria, NVL((SELECT d_estadoconv FROM delphos.tlestadosconv "
			+ "WHERE x_estadoconv = (SELECT MIN(decode(cco.x_convfinal, null, NVL(cu.x_estadoconv, -1), " 
			+ "DECODE(cu.f_sesion, NULL, (select max(x_estadoconv)+1 from delphos.tlestadosconv),NVL (cu.x_estadoconv, -1)))) "
			+ "FROM delphos.tlconvunidad cu, delphos.tlconvcenomc cco, delphos.tlofertasunidad ou "
			+ "WHERE cu.x_convcentroomc = cco.x_convcentroomc AND cco.x_convcentro = ccen.x_convcentro AND ou.x_ofertamatric = cco.x_ofertamatric "
			+ "AND ou.x_unidad = cu.x_unidad)), 'Bloqueada') estado, DECODE(ccen.x_convocatoria, NULL, 0, ccen.n_orden) orden "
			+ "FROM delphos.tlconvcentros ccen, delphos.tlconvogeneral convgen, delphos.TLCONVOPARCGEN convpar, delphos.TLTIPOSCONVF conv, delphos.tlcentros cen "
			+ "WHERE ccen.X_CONVOCATORIA = conv.X_CONVOCATORIA (+) AND ccen.X_CONVOGENERAL = CONVGEN.X_CONVOGENERAL (+) "
			+ "AND ccen.X_CONVOPARCGEN = convpar.X_CONVOPARCGEN (+) "
			+ "AND cen.c_codigo = :idCentro AND ccen.x_centro = cen.x_centro AND ccen.c_anno = :anno "
			+ "ORDER BY orden, fechaInicio",nativeQuery = true)
	List<ConvocatoriaProjection> getConvocatorias(@Param("idCentro") Long idCentro, @Param("anno") Integer anno);
	
	@Query(value= "SELECT DISTINCT convu.X_UNIDAD idUnidad, " 
			+ "  (select UNI.T_NOMBRE from tlunidadescen uni where x_unidad = convu.x_unidad) nombre, " 
			+ "  (select EST.D_ESTADOCONV from TLESTADOSCONV EST where EST.X_ESTADOCONV = convu.X_ESTADOCONV) estado , " 
			+ "  convu.X_CONVUNIDAD idConvUnidad, " 
			+ "  OMC.X_OFERTAMATRIG idOfermatrig, omg.d_OFERTAMATRIG Oferta, " 
			+ "  convu.X_CONVCENTROOMC idConvOmc , " 
			+ "  (select D_ETAPA from tletapas where x_etapa = curm.x_etapa) etapa, curm.x_etapa idEtapa, omc.X_CENTRO idCentro, omc.X_OFERTAMATRIC idOfermatric  " 
			+ "FROM TLCONVUNIDAD convu, TLCONVCENOMC cco, tlofematrcen omc, tlofematrgen omg, tlcursoorg curo, tlcursomoda curm "  
			+ "WHERE convu.X_UNIDAD IN (:idsUnidades) " 
			+ "AND convu.X_CONVCENTROOMC = cco.X_CONVCENTROOMC " 
			+ "AND cco.X_CONVCENTRO = :idConvocatoria " 
			+ "AND cco.x_ofertamatric = omc.x_ofertamatric " 
			+ "AND omc.x_ofertamatrig = omg.x_ofertamatrig " 
			+ "AND omg.x_ofertamatrig = curo.x_ofertamatrig " 
			+ "AND curo.X_CURSOMOD = curm.X_CURSOMOD " 
			+ "AND tlf_omgcuelgaetapa (omg.x_ofertamatrig, curm.x_etapa)=1 ORDER BY idEtapa, nombre", nativeQuery = true)
	List<UnidadConvProjection> getUnidadesConvocatoria(@Param("idConvocatoria") Long idConvocatoria, @Param("idsUnidades") Long[] idsUnidades);
	
	@Query(value= "SELECT DISTINCT grua.x_gruactproalu idGrupoActividad, grua.s_gruactproalu nombre, grua.c_gruactproalu abreviatura, "
			+ "DECODE(COUNT(DISTINCT ecuag.x_estadoconv) OVER(PARTITION BY ecuag.x_gruactproalu), 1, ecuag.d_estadoconv, 'Mixta') estado "
			+ "FROM delphos_segedu.tlgruactproalu grua, delphos_segedu.tlconvcentros ccen, delphos_segedu.tlconvcenomc cco, "
			+ "delphos_segedu.tlmatalu mua, delphos_segedu.tlmatmatrialu mat, "
			+ "(SELECT uag.x_gruactproalu, uag.x_unidad, "
			+ "uag.x_materiaomg, cu.x_convcentroomc, cu.x_convunidad, "
			+ "est.x_estadoconv, est.d_estadoconv FROM delphos_segedu.tluniafegruactpro uag, "
			+ "delphos.tlconvunidad cu, delphos.tlestadosconv est "
			+ "WHERE uag.x_unidad = cu.x_unidad AND cu.x_estadoconv = est.x_estadoconv) ecuag "
			+ "WHERE grua.x_empleado = :idEmpleado  AND ccen.x_convcentro = :idConvocatoria AND ccen.x_convcentro = cco.x_convcentro "
			+ "AND grua.x_gruactproalu = ecuag.x_gruactproalu AND mua.x_unidad = ecuag.x_unidad "
			+ "AND ecuag.x_convcentroomc = cco.x_convcentroomc AND mua.x_matricula = mat.x_matricula "
			+ "AND ecuag.x_materiaomg = mat.x_materiaomg AND mua.x_ofertamatric = cco.x_ofertamatric "
			+ "ORDER BY GRUA.C_GRUACTPROALU", nativeQuery = true)
	List<GrupoActividadConvocatoriaProjection> getGruposActividadConvocatoria(@Param("idConvocatoria") Long idConvocatoria, @Param("idEmpleado") Long idEmpleado);

	@Query(value= "SELECT DISTINCT grua.x_gruactproalu idGrupoActividad, grua.s_gruactproalu nombre, grua.c_gruactproalu abreviatura,  uag.x_unidad idUnidad, "
			+ "uni.t_nombre unidad, ciclo.X_ETAPADEPENDEDE idEtapa, MATOFE.X_MATERIAOMG idMateriaOmg, MATOFE.X_OFERTAMATRIG idOfertaMatrig, est.d_estadoconv estado, "
			+ "DECODE(SIGN(INSTR(ETAPA.D_ETAPA, 'LOMLOE') || INSTR(CICLO.D_ETAPA, 'LOMLOE')),0,0,1) lomloe, CU.X_CONVUNIDAD idConvUnidad, curso.D_ETAPA curso, "
			+ "(CASE (SELECT count(*) FROM (SELECT UN.X_MATERIAOMG FROM TLUNIAFEGRUACTPRO un "
			+ "INNER JOIN TLGRUACTPROALU grp ON grp.X_GRUACTPROALU = un.X_GRUACTPROALU AND grp.X_GRUACTPROALU = GRUA.X_GRUACTPROALU "
			+ "WHERE un.X_UNIDAD = uni.X_UNIDAD "
			+ "GROUP BY UN.X_MATERIAOMG) dual) WHEN 1 THEN 0 ELSE 1 END)  cra "
			+ "FROM DELPHOS_SEGEDU.TLGRUACTPROALU GRUA, "
			+ "DELPHOS_SEGEDU.TLCONVCENTROS CCEN, "
			+ "DELPHOS_SEGEDU.TLCONVCENOMC CCO, "
			+ "DELPHOS_SEGEDU.TLMATALU MUA, "
			+ "DELPHOS_SEGEDU.TLMATMATRIALU MAT, "
			+ "DELPHOS_SEGEDU.TLUNIAFEGRUACTPRO UAG, "
			+ "DELPHOS_SEGEDU.TLUNIDADESCEN UNI, "
			+ "TLETAPAS CICLO, "
			+ "TLETAPAS ETAPA, "
			+ "TLETAPAS CURSO, "
			+ "TLCURSOMODA MODALIDAD, "
			+ "TLMATOFEMATRG MATOFE, "
			+ "TLMATERIASCURSO MATCURSO, "
			+ "TLESTADOSCONV EST, "
			+ "TLCONVUNIDAD CU "
			+ "WHERE GRUA.X_EMPLEADO = :idEmpleado "
			+ "AND CCEN.X_CONVCENTRO = :idConvocatoria "
			+ "AND CCEN.X_CONVCENTRO = CCO.X_CONVCENTRO "
			+ "AND UNI.X_UNIDAD = CU.X_UNIDAD "
			+ "AND MUA.X_UNIDAD = uni.X_UNIDAD "
			+ "AND CU.X_CONVCENTROOMC = CCO.X_CONVCENTROOMC "
			+ "AND MUA.X_MATRICULA = MAT.X_MATRICULA "
			+ "AND UAG.X_GRUACTPROALU = GRUA.X_GRUACTPROALU "
			+ "AND UAG.X_UNIDAD = UNI.X_UNIDAD "
			+ "AND UAG.X_MATERIAOMG = MAT.X_MATERIAOMG "
			+ "AND CU.X_ESTADOCONV = EST.X_ESTADOCONV "
			+ "AND MUA.X_OFERTAMATRIC = CCO.X_OFERTAMATRIC "
			+ "AND UAG.X_MATERIAOMG = MATOFE.X_MATERIAOMG "
			+ "AND MATOFE.X_MATERIAC = MATCURSO.X_MATERIAC "
			+ "AND MATCURSO.X_CURSOMOD = MODALIDAD.X_CURSOMOD "
			+ "AND CURSO.X_ETAPA = MODALIDAD.X_ETAPA "
			+ "AND CURSO.X_ETAPADEPENDEDE = CICLO.X_ETAPA "
			+ "AND CICLO.X_ETAPADEPENDEDE = ETAPA.X_ETAPA "
			+ "ORDER BY GRUA.C_GRUACTPROALU, UNI.T_NOMBRE", nativeQuery = true)
	List<GrupoActividadConvocatoriaProjection> getGruposActividadUnidades(@Param("idConvocatoria") Long idConvocatoria, @Param("idEmpleado") Long idEmpleado);
	
    @Query(value= "SELECT unic.x_unidad idUnidad, UNI.T_NOMBRE nombre, EST.D_ESTADOCONV estado, CONVUNI.X_CONVUNIDAD idConvUnidad, "
    		+ "CONVCEN.X_CONVCENTROOMC idConvOmc, OMC.X_OFERTAMATRIG idOfermatrig "  
			+ "FROM delphos.tluniafegruactpro unic , TLUNIDADESCEN UNI, TLCONVUNIDAD CONVUNI, TLCONVCENOMC CONVCEN, TLESTADOSCONV EST, TLOFEMATRCEN OMC  " 
			+ "WHERE unic.x_gruactproalu = :idGrupoAct AND CONVUNI.X_UNIDAD = UNI.X_UNIDAD  " 
			+ "AND UNIC.X_UNIDAD = UNI.X_UNIDAD " 
			+ "AND CONVCEN.X_CONVCENTROOMC = CONVUNI.X_CONVCENTROOMC " 
			+ "AND CONVUNI.X_ESTADOCONV = EST.X_ESTADOCONV " 
			+ "AND OMC.X_OFERTAMATRIC = CONVCEN.X_OFERTAMATRIC "
			+ "AND CONVCEN.X_CONVCENTRO = :idConvocatoria  " 
            + "GROUP BY unic.x_unidad, UNI.T_NOMBRE,  EST.D_ESTADOCONV, CONVUNI.X_CONVUNIDAD, CONVCEN.X_CONVCENTROOMC, OMC.X_OFERTAMATRIG ", nativeQuery = true)
	List<UnidadConvProjection> getUnidadesGrupoActividad(@Param("idConvocatoria") Long idConvocatoria, @Param("idGrupoAct") Long idGrupoAct);
	
	
	@Modifying
	@Query(value= "DELETE TLNOTEVA "
			+ "WHERE X_CONVCENTROOMC = :idConvCentroOmc "
			+ " AND X_MATMATRICULA = :idMatMatricula ", nativeQuery = true)
	void eliminarNota(@Param("idConvCentroOmc") Long idConvCentroOmc, @Param("idMatMatricula") Long idMatMatricula);
	
	@Query(value= "SELECT DISTINCT CVU.X_CONVUNIDAD AS IDCONVUNIDAD, CVU.F_SESION AS DESCRIPCION, EST.D_ESTADOCONV AS ESTADO "
			+ "FROM TLCONVUNIDAD CVU, "
			+ "TLCONVCENOMC CCO, "
			+ "TLCONVCENTROS CVC, "
			+ "TLOFEMATRCEN matrc, "
			+ "TLESTADOSCONV est, "
			+ "TLOFEMATRGEN matrg, "
			+ "tlmatofematrg mog, "
			+ "tlmatmatrialu mma, "
			+ "tlmatalu mua "
			+ "WHERE CVU.X_UNIDAD = :unidad "
			+ "AND CCO.X_CONVCENTRO = :convocatoria "
			+ "AND EST.X_ESTADOCONV = CVU.X_ESTADOCONV "
			+ "AND CCO.X_CONVCENTROOMC = CVU.X_CONVCENTROOMC "
			+ "AND CCO.X_CONVCENTRO = CVC.X_CONVCENTRO "
			+ "AND cco.X_OFERTAMATRIC = matrc.X_OFERTAMATRIC "
			+ "AND MATRC.X_OFERTAMATRIG = matrg.X_OFERTAMATRIG "
			+ "AND mog.X_OFERTAMATRIG = matrg.X_OFERTAMATRIG "
			+ "AND mma.x_materiaomg = mog.x_materiaomg "
			+ "AND mua.x_matricula = mma.x_matricula "
			+ "and MUA.X_UNIDAD = CVU.X_UNIDAD "
			+ "AND ((CVU.F_SESION IS NOT NULL "
			+ "AND CVC.C_ANNO > 2006 "
			+ "AND CVC.X_CONVOCATORIA IS NOT NULL) "
			+ "OR (CVC.C_ANNO <= 2006) "
			+ "OR (CVC.C_ANNO > 2006 "
			+ "AND CVC.X_CONVOCATORIA IS NULL))", nativeQuery = true)
	List<ConvUnidadProjection> getFechaSesion(@Param("unidad") Long unidad, @Param("convocatoria") Long convocatoria);
	
	
	
	@Query(value= "select DISTINCT pev.x_proeva idPromocion, pev.X_ESTGENMATR idEstado, est.D_ESTGENMATR estado, pev.f_sesion fechaSesion " + 
			" from tlproeva pev, TLESTGENMATR est, DELPHOS_SEGEDU.tlconvcenomc cco " + 
			" where pev.x_matricula = :idMatMatricula " + 
			" AND est.X_ESTGENMATR = pev.X_ESTGENMATR " + 
			" AND cco.x_convcentro = :idConvocatoria  " + 
			" AND pev.x_convcentroomc = CCO .X_CONVCENTROOMC  ", nativeQuery = true)
	PromocionProjection getPromotion(@Param("idMatMatricula") Long idMatMatricula, @Param("idConvocatoria") Long idConvocatoria);
	
	@Query(value = "SELECT DISTINCT RES.C_RESULTADO cResultado, ESG.D_ESTGENMATR descripcion, ESG.T_ABREV abrev, ESG.X_ESTGENMATR idEstado "
		    + "FROM TLESTMATROMG EST, TLESTGENMATR ESG, TLRESULTADO RES "
		    + "WHERE EST.X_OFERTAMATRIG = :idOfermatrig "
		    + "AND EST.X_ESTGENMATR = ESG.X_ESTGENMATR "
		    + "AND ESG.C_RESULTADO > 1 "
		    + "AND ESG.C_RESULTADO = RES.C_RESULTADO ORDER BY DESCRIPCION", nativeQuery = true)
	List<EstadosPromocionProjection> getEstadosPromocion(@Param("idOfermatrig") Long idOfermatrig);
	
	@Query(value="SELECT  nvl(N_MAXHOR, -1) N_MAXHOR FROM TLOFEMATRGEN omg WHERE omg.x_ofertamatrig = :idOfermatrig", nativeQuery = true)
	Long getNumberHours(@Param("idOfermatrig") Long idOfermatrig);	
	
	@Query(value="SELECT COUNT(ETA.X_ETAPA) CICLO " 
			+ " FROM TLPARGEN PAR, TLETAPAS ETA " 
			+ " WHERE T_NOMPAR IN ('ID_ETAPA_CFGM', 'ID_ETAPA_CFGS', " 
			+ " 'ETP_CFGM', 'ETP_CFGS', 'ETP_CFGM_ADU', 'ETP_CFGS_ADU') " 
			+ " AND PAR.T_VALPAR = ETA.X_ETAPA  "
			+ " AND Tlf_Omgcuelgaetapa( :idOfermatrig, ETA.X_ETAPA) = 1", nativeQuery = true)
	Long getIsCicle(@Param("idOfermatrig") Long idOfermatrig);
	
	@Query(value = "SELECT mua.x_matricula idMatricula, alu.x_alumno idAlumno, UNI.X_UNIDAD idUnidad, "
			+ "DELPHOS.TLF_NOMBRE(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) nombreAlumno, UNI.T_NOMBRE nombreUnidad "
			+ "FROM DELPHOS_SEGEDU.tluniafegruactpro uag, DELPHOS_SEGEDU.tlmatalu mua, "
			+ "DELPHOS_SEGEDU.tlalumnos alu, DELPHOS_SEGEDU.TLGRUACTPROALU grua, "
			+ "DELPHOS_SEGEDU.tlconvcenomc cco, DELPHOS.tlconvunidad cu, DELPHOS_SEGEDU.tlunidadescen uni "
			+ "WHERE cco.x_convcentro = :idConvocatoria AND cu.x_convcentroomc = cco.x_convcentroomc "
			+ "AND uag.x_gruactproalu IN (:idGrupoAct) AND grua.x_empleado = :idEmpleado AND mua.x_unidad = uag.x_unidad "
			+ "AND nvl(mua.c_resultado, 99) > tlf_parametro('MAT_CANCEL') "
			+ "AND tlf_valida_grupo_matricula(uag.x_gruactproalu, mua.x_matricula) = 1 "
			+ "AND NVL(mua.C_RESULTADO,99) > 1  "
			+ "AND alu.x_alumno = mua.x_alumno AND uag.x_gruactproalu = grua.x_gruactproalu AND mua.x_ofertamatric = cco.x_ofertamatric (+) "
			+ "AND uag.x_unidad = uni.x_unidad AND uni.x_unidad = cu.x_unidad (+) ORDER BY grua.s_gruactproalu, uag.x_unidad, nombreAlumno", nativeQuery = true)
	List<AlumnoEvalIndProjection> getAlumnosByGruposActividad(@Param("idGrupoAct") Long[] idGrupoAct, @Param("idEmpleado") Long idEmpleado, 
			@Param("idConvocatoria") Long idConvocatoria);
	
	@Query(value = "SELECT mua.x_matricula idMatricula, alu.x_alumno idAlumno, udc.X_UNIDAD idUnidad, "
			+ "DELPHOS.TLF_NOMBRE(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) nombreAlumno, udc.T_NOMBRE nombreUnidad "
			+ "FROM DELPHOS_SEGEDU.tlmatalu mua, DELPHOS_SEGEDU.tlalumnos alu, DELPHOS_SEGEDU.tlunidadescen udc, "
			+ "DELPHOS_SEGEDU.tlconvcenomc cco, DELPHOS_SEGEDU.tlconvcentros cce, DELPHOS.tlconvunidad cu "
			+ "WHERE udc.X_UNIDAD = :idUnidad AND mua.x_unidad = udc.x_unidad AND alu.x_alumno = mua.x_alumno "
			+ "AND cce.x_convcentro = :idConvocatoria AND cco.x_convcentro = cce.x_convcentro AND mua.X_OFERTAMATRIG = :idOfertaMatrig "
			+ "AND cu.x_convcentroomc = cco.x_convcentroomc AND mua.x_centro = cce.x_centro "
			+ "AND mua.x_ofertamatric = cco.x_ofertamatric (+) AND udc.x_unidad = cu.x_unidad (+) "
			+ "AND NVL(mua.C_RESULTADO,99) > 1  "
			+ "ORDER BY udc.x_unidad, nombreAlumno", nativeQuery = true)
	List<AlumnoEvalIndProjection> getAlumnosByUnidad(@Param("idUnidad") Long idUnidad, @Param("idConvocatoria") Long idConvocatoria,
													 @Param("idOfertaMatrig") Long idOfertaMatrig);
	
	@Query(value = " SELECT NVL (SUM (CASE    " + 
			"	 WHEN nvl((select l_aprueba from tlnoteva ne, tlcalificaciones cal   " + 
			"	 where ne.x_califica = cal.x_califica   " + 
			"	and   ne.x_matmatricula = mmt.x_matmatricula   " + 
			"	 and   ne.x_convcentroomc = :idConvOmc),  " + 
			" " + 
			"		DECODE (   " + 
			"		(SELECT COUNT (1) FROM tlmatmatrialu mmt2,   " + 
			"		  tlmatofematrg mog2 WHERE mmt2.x_matricula = :idMatricula   " + 
			"		AND mmt2.l_aprobada  = 'N'   " + 
			"		AND mmt2.x_materiaomg  = mog2.x_materiaomg   " + 
			"		AND mmt2.x_matmatricula = mmt.x_matmatricula   " + 
			"		AND mog2.x_grupomat NOT IN   " + 
			"		  (SELECT par.t_valpar FROM tlpargen par WHERE par.t_nompar = 'GRU_MAT_FCT'   " + 
			"		  )   " + 
			"		AND MOG2.L_VIGENTE   = 'S' " + 
			"		AND MOG2.L_EVALUABLE = 'S' " + 
			"		), 0, 'S', 'N')  " + 
			" " + 
			"		) = 'N'   " + 
			"		          THEN mog.n_horasanuales    " + 
			"		          ELSE 0    " + 
			"		          END), -1) suspensas,   " + 
			"		 NVL(SUM(CASE WHEN MOG.N_HORASANUALES IS NULL THEN 1 ELSE 0 END),-1) EXISTEN_NULOS   " + 
			"		 FROM tlmatmatrialu mmt, tlmatofematrg mog, tlofematrgen omg, tlestadomat est   " + 
			"		 WHERE mmt.x_matricula = :idMatricula   " + 
			"		 AND mmt.x_materiaomg = mog.x_materiaomg   " + 
			"		 AND mog.x_grupomat NOT IN (SELECT par.t_valpar FROM tlpargen par WHERE par.t_nompar = 'GRU_MAT_FCT')   " + 
			"		 AND mog.l_vigente = 'S'   " + 
			"		 AND mog.l_evaluable = 'S'   " + 
			"		 AND mog.x_ofertamatrig = omg.x_ofertamatrig   " + 
			"		 and mmt.x_estado = est.x_estado   " + 
			"		 and est.l_evalua = 'S' ", nativeQuery = true)
	List<List<Long>> getInfoReprobedFCT(@Param("idMatricula") Long idMatricula, @Param("idConvOmc") Long idConvOmc );
	
	
	@Query(value = "SELECT DISTINCT OFEMATRG.X_OFERTAMATRIG idOfermatrig, MATRGEN.S_OFERTAMATRIG descripcionCorta, MATRGEN.X_TIPOEXP tipo, MATRGEN.N_ORDEN orden" + 
			"  FROM TLMATOFEMATRG OFEMATRG " + 
			"  INNER JOIN TLGRUACTPROALU GRUACT ON GRUACT.C_ANNO = :anno AND GRUACT.X_CENTRO = :idCentro " + 
			"  INNER JOIN TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU " + 
			"  INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.X_OFERTAMATRIG = OFEMATRG.X_OFERTAMATRIG AND PROGDIDAC.NU_ANNO = :anno AND PROGDIDAC.X_MATERIAOMG = UNIGRUACT.X_MATERIAOMG AND PROGDIDAC.LG_CERRADA = 1 " + 
			"  INNER JOIN TLOFEMATRGEN MATRGEN ON MATRGEN.X_OFERTAMATRIG = OFEMATRG.X_OFERTAMATRIG " + 
			"  WHERE (SELECT count(PROGDIDAC.X_MATERIAOMG)  " + 
			"  FROM EVA_PROGDIDAC didac   " + 
			"  INNER JOIN TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU AND UNIGRUACT.X_MATERIAOMG = didac.X_MATERIAOMG   " + 
			"  INNER JOIN TLMATOFEMATRG matofer ON matofer.X_MATERIAOMG = DIDAC.X_MATERIAOMG   " + 
			"  INNER JOIN TLMATERIASCURSO matcurse ON matofer.X_MATERIAC  = matcurse.X_MATERIAC   " + 
			"  WHERE DIDAC.X_OFERTAMATRIG = OFEMATRG.X_OFERTAMATRIG   " + 
			"  AND didac.NU_ANNO = :anno   " + 
			"  AND DIDAC.X_CENTRO = :idCentro) > 0 ORDER BY MATRGEN.X_TIPOEXP, MATRGEN.N_ORDEN", nativeQuery = true)
	List<CursoCentroProjection> getCursosByCentroAndAnno(@Param("idCentro") Long idCentro, @Param("anno") Long anno);
	
	
	@Query(value = "SELECT DISTINCT convu.X_UNIDAD idUnidad, " + 
			" (select UNI.T_NOMBRE from tlunidadescen uni where x_unidad = convu.x_unidad) nombre, " + 
			" (select EST.D_ESTADOCONV from TLESTADOSCONV EST where EST.X_ESTADOCONV = convu.X_ESTADOCONV) estado , " + 
			" convu.X_CONVUNIDAD idConvUnidad, " + 
			" OMC.X_OFERTAMATRIG idOfermatrig, omg.d_OFERTAMATRIG Oferta, " + 
			" convu.X_CONVCENTROOMC idConvOmc , " + 
			" (select D_ETAPA from tletapas where x_etapa = curm.x_etapa) etapa, curm.x_etapa idEtapa, omc.X_CENTRO idCentro, omc.X_OFERTAMATRIC idOfermatric  " + 
			"FROM TLCONVUNIDAD convu, TLCONVCENOMC cco, tlofematrcen omc, tlofematrgen omg, tlcursoorg curo, tlcursomoda curm  " + 
			"WHERE convu.X_CONVCENTROOMC = cco.X_CONVCENTROOMC " + 
			"AND cco.X_CONVCENTRO = :idConvocatoria " + 
			"AND cco.x_ofertamatric = omc.x_ofertamatric " + 
			"AND omc.x_ofertamatrig = omg.x_ofertamatrig " + 
			"AND omg.X_OFERTAMATRIG = :idOfertamatrig " +
			"AND omg.x_ofertamatrig = curo.x_ofertamatrig " + 
			"AND curo.X_CURSOMOD = curm.X_CURSOMOD " + 
			"AND tlf_omgcuelgaetapa (omg.x_ofertamatrig, curm.x_etapa)=1 " + 
			"ORDER BY idEtapa, nombre", nativeQuery = true)
	List<UnidadConvProjection> getAllUnidadesByCurso(@Param("idConvocatoria") Long idConvocatoria,
			@Param("idOfertamatrig") Long idOfertamatrig);
	
	
}