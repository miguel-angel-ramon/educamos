package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import es.jccm.edu.evaluacion.application.domain.evaluacion.DescriptorOperativoAlumno;
import es.jccm.edu.evaluacion.application.domain.evaluacion.QDescriptorOperativoAlumno;
import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.*;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.CursoValoracionProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Repository
public interface ValoracionCriteriosRepository extends AbstractRepository<DescriptorOperativoAlumno, Long, QDescriptorOperativoAlumno> {

    @Query(value = "SELECT cur.c_anno anno, cur.C_ANNO || '/' || (cur.C_ANNO + 1) tramo, cur.l_actual annoActual " +
            "FROM tlcursoaca cur " +
            "WHERE cur.C_ANNO <= (SELECT curso.C_ANNO FROM tlcursoaca curso WHERE curso.l_actual >= 'S') " +
            "ORDER BY cur.c_anno DESC FETCH NEXT 3 ROWS ONLY", nativeQuery = true)
    List<AnnosProjection> getUltimosAnnos();

    @Query(value = "SELECT DISTINCT mat.X_MATERIAOMG idMateria, ciclo.X_ETAPADEPENDEDE idEtapa,    " +
			"    matCurso.t_abrev || ' - '  || ofg.d_ofertamatrig materia, mat.x_ofertamatrig idOfertaMatrig, omc.X_OFERTAMATRIC idOfertaMatric,   " +
			"     nvl((SELECT PROGDIDAC.lg_cerrada FROM EVA_PROGDIDAC PROGDIDAC   " +
			"    WHERE PROGDIDAC.X_CENTRO = pd.X_CENTRO AND PROGDIDAC.NU_ANNO = pd.NU_ANNO AND PROGDIDAC.X_MATERIAOMG = pd.X_MATERIAOMG   " +
			"    AND PROGDIDAC.X_OFERTAMATRIG = pd.X_OFERTAMATRIG AND PROGDIDAC.lg_cerrada = 1 AND ROWNUM <= 1), 0) cerrada, " +
			"    NVL2(pd.ID_PROGDIDAC, 'true', 'false') hayProgDidac " +
			"    FROM TLGRUACTPROALU GRUACT " +
			"    INNER JOIN TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU   " +
			"    AND (-1 IN :idUnidades OR UNIGRUACT.X_UNIDAD IN (:idUnidades))  " +
			"    INNER JOIN TLMATOFEMATRG mat ON UNIGRUACT.X_MATERIAOMG = mat.X_MATERIAOMG    " +
			"    INNER JOIN TLUNIDADESCEN unidades ON UNIGRUACT.X_UNIDAD = unidades.X_UNIDAD    " +
			"    INNER JOIN TLMATERIASCURSO matCurso ON mat.X_MATERIAC = matCurso.X_MATERIAC    " +
			"    INNER JOIN TLOFEMATRGEN OFG ON MAT.X_OFERTAMATRIG = OFG.X_OFERTAMATRIG     " +
			"    INNER JOIN TLCURSOMODA modalidad ON matCurso.x_cursomod = modalidad.x_cursomod    " +
			"    INNER JOIN TLETAPAS curso ON curso.x_etapa = modalidad.x_etapa    " +
			"    INNER JOIN TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA    " +
			"    INNER JOIN TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA     " +
			"    INNER JOIN tlofematrcen omc ON omc.x_ofertamatrig = mat.x_ofertamatrig AND omc.X_CENTRO = GRUACT.X_CENTRO " +
			"    LEFT JOIN DELPHOS.EVA_PROGDIDAC pd ON pd.X_MATERIAOMG = UNIGRUACT.X_MATERIAOMG AND pd.X_OFERTAMATRIG = mat.X_OFERTAMATRIG AND pd.X_CENTRO = omc.X_CENTRO AND pd.NU_ANNO = GRUACT.C_ANNO " +
			"    WHERE GRUACT.C_ANNO = :anno    " +
			"    AND GRUACT.X_CENTRO = :idCentro  " +
			"    AND (-1 = :idEmpleado OR GRUACT.X_EMPLEADO = :idEmpleado) " +
			"    ORDER BY idOfertaMatrig", nativeQuery = true)
    List<MateriasValoracionProjection> getMateriasValoracion(
            @Param("idEmpleado") Long idEmpleado,
            @Param("anno") Long anno,
            @Param("idCentro") Long idCentro,
            @Param("idUnidades") List<Long> idUnidades);

	@Query(value = "SELECT DISTINCT mat.X_MATERIAOMG idMateria, ciclo.X_ETAPADEPENDEDE idEtapa, " +
			"matCurso.t_abrev || ' - '  || ofg.d_ofertamatrig materia, mat.x_ofertamatrig idOfertaMatrig, omc.X_OFERTAMATRIC idOfertaMatric,  " +
			" nvl((SELECT PROGDIDAC.lg_cerrada FROM EVA_PROGDIDAC PROGDIDAC  " +
			"WHERE PROGDIDAC.X_CENTRO = pd.X_CENTRO AND PROGDIDAC.NU_ANNO = pd.NU_ANNO AND PROGDIDAC.X_MATERIAOMG = pd.X_MATERIAOMG  " +
			"AND PROGDIDAC.X_OFERTAMATRIG = pd.X_OFERTAMATRIG AND PROGDIDAC.lg_cerrada = 1 AND ROWNUM <= 1), 0) cerrada, " +
			"decode(mat.x_ofertamatrig, ma.x_ofertamatrig, 'false', 'true') pendiente, " +
			"ma.X_OFERTAMATRIG idOfertaMatrigAlumno, " +
			"NVL2(pd.ID_PROGDIDAC, 'true', 'false') hayProgDidac " +
			"FROM TLMATALU MA " +
			"INNER JOIN TLMATMATRIALU MMA ON MMA.X_MATRICULA = MA.X_MATRICULA   " +
			"INNER JOIN TLMATOFEMATRG mat ON MMA.X_MATERIAOMG = mat.X_MATERIAOMG " +
			"INNER JOIN TLUNIDADESCEN unidades ON MA.X_UNIDAD = unidades.X_UNIDAD " +
			"INNER JOIN TLMATERIASCURSO matCurso ON mat.X_MATERIAC = matCurso.X_MATERIAC " +
			"INNER JOIN TLOFEMATRGEN OFG ON MAT.X_OFERTAMATRIG = OFG.X_OFERTAMATRIG  " +
			"INNER JOIN TLCURSOMODA modalidad ON matCurso.x_cursomod = modalidad.x_cursomod " +
			"INNER JOIN TLETAPAS curso ON curso.x_etapa = modalidad.x_etapa " +
			"INNER JOIN TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA " +
			"INNER JOIN TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA  " +
			"INNER JOIN tlofematrcen omc ON omc.x_ofertamatrig = mat.x_ofertamatrig AND omc.X_CENTRO = MA.X_CENTRO " +
			"LEFT JOIN DELPHOS.EVA_PROGDIDAC pd ON pd.X_MATERIAOMG = mat.X_MATERIAOMG AND pd.X_OFERTAMATRIG = mat.X_OFERTAMATRIG AND pd.X_CENTRO = omc.X_CENTRO AND pd.NU_ANNO = MA.C_ANNO " +
			"WHERE MA.X_UNIDAD IN (:idUnidades) AND MA.X_CENTRO = :idCentro AND MA.C_ANNO = :anno " +
			"ORDER BY idOfertaMatrig DESC, MATERIA", nativeQuery = true)
	List<MateriasValoracionProjection> getMateriasValoracionTutor(
			@Param("anno") Long anno,
			@Param("idCentro") Long idCentro,
			@Param("idUnidades") List<Long> idUnidades);

    @Query(value = "SELECT DISTINCT uag.x_unidad idUnidad, UNICEN.T_NOMBRE unidad, " +
			" (SELECT count(DISTINCT aula.ID_PROGAULA) FROM EVA_PROGAULA aula " +
			" INNER JOIN DELPHOS.EVA_RELPROGAULALUM rprogaulaalu ON rprogaulaalu.X_UNIDAD = uag.X_UNIDAD " +
			"  INNER JOIN DELPHOS.EVA_PROGAULA pa ON pa.ID_PROGAULA = rprogaulaalu.ID_PROGAULA AND pa.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC  " +
			"  INNER JOIN EVA_RELPROGAULAEMP em ON (X_EMPLEADO IN (:idEmpleados) AND :tutor = 0 AND aula.ID_PROGAULA = EM.ID_PROGAULA) OR :tutor = 1 OR :direccion = 1"  +
			" WHERE aula.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC) progAula " +
			" FROM tluniafegruactpro uag       " +
			" INNER JOIN TLGRUACTPROALU GRUACT ON GRUACT.X_GRUACTPROALU = UAG.X_GRUACTPROALU      " +
			" AND ((GRUACT.X_EMPLEADO IN (:idEmpleados) AND :tutor = 0) OR :tutor = 1 OR :direccion = 1)   " +
			" INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.X_MATERIAOMG = UAG.X_MATERIAOMG   " +
			" AND PROGDIDAC.NU_ANNO = GRUACT.C_ANNO      " +
			" AND PROGDIDAC.X_CENTRO = GRUACT.X_CENTRO      " +
			" INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = UAG.X_UNIDAD  " +
			" AND ((unicen.X_EMPLEADO IN (:idEmpleados) AND :tutor = 1) OR :tutor = 0 OR :direccion = 1)   " +
			" WHERE PROGDIDAC.X_MATERIAOMG = :idMateria     " +
			" AND PROGDIDAC.X_CENTRO = :idCentro     " +
			" AND PROGDIDAC.NU_ANNO = :anno     " +
			" AND (:idProgdidac = -1 OR PROGDIDAC.ID_PROGDIDAC = :idProgdidac) " +
			" ORDER BY unicen.T_NOMBRE ASC", nativeQuery = true)
    List<UnidadesValoracionProjection> getUnidadesValoracion(
            @Param("idEmpleados") List<Long> idDocentes,
            @Param("anno") Long anno,
            @Param("idMateria") Long idMateria,
            @Param("idCentro") Long idCentro,
            @Param("tutor") Long tutor,
            @Param("direccion") Long direccion,
            @Param("idProgdidac") Long idProgdidac);

    @Query(value = "SELECT DISTINCT alu.x_alumno idAlumno, mua.x_matricula idMatricula, mma.x_matmatricula idMatMatriAlu, alu.c_numescolar numEscolar, alu.t_nombre nombre, "
    		+ "alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos "
    		+ "from tlalumnos alu "
    		+ "inner join tlmatalu mua on alu.X_ALUMNO = mua.X_ALUMNO "
    		+ "inner join tlmatmatrialu mma on mma.x_matricula = mua.x_matricula "
    		+ "inner join tlunidadescen udc on udc.x_unidad = mua.x_unidad "
    		+ "inner join TLUNIAFEGRUACTPRO unigruact on unigruact.X_MATERIAOMG = mma.x_materiaomg "
    		+ "inner join TLGRUACTPROALU gruact on gruact.X_GRUACTPROALU = unigruact.X_GRUACTPROALU "
    		+ "where mma.x_materiaomg = :idMateria "
    		+ "and mua.x_unidad = :idUnidad "
    		+ "AND gruact.X_EMPLEADO = :idEmpleado "
    		+ "AND unigruact.X_UNIDAD = mua.X_UNIDAD "
    		+ "and udc.x_centro = mua.x_centro "
    		+ "and udc.c_anno = mua.c_anno "
    		+ "AND udc.C_ANNO = GRUACT.C_ANNO "
    		+ "AND tlf_valida_grupo_matricula(unigruact.x_gruactproalu, mua.x_matricula) = 1 "
    		+ "order by apellidos", nativeQuery = true)
    List<AlumnoEvaluacionProjection> getAlumnosEvaluacion(
            @Param("idMateria") Long idMateria,
            @Param("idUnidad") Long idUnidad,
            @Param("idEmpleado") Long idEmpleado);

	@Query(value = " SELECT DISTINCT * FROM ( SELECT DISTINCT ALU.X_ALUMNO idAlumno, mat.X_MATRICULA idMatricula, MATMATRI.x_matmatricula idMatMatriAlu,"  +
			" alu.c_numescolar numEscolar, alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos , MATMATRI.X_MATERIAOMG idMateriaOmg,"  +
			" CASE WHEN (MATMATRI.X_ESTADO NOT IN (1, 3) OR MATMATRI.X_ADAPTACION IS NOT NULL AND :nivelCurricular = 0 AND mat.x_niveadap <> mat.x_ofertamatrig) THEN 1 ELSE 0 END AS disabled"  +
			" FROM TLALUMNOS ALU " +
			" INNER JOIN EVA_RELPROGDIDPOND RELPROGDIDPOND ON RELPROGDIDPOND.X_PONDERACION = :idPonderacion"  +
			" INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = RELPROGDIDPOND.ID_PROGDIDAC"  +
			" INNER JOIN TLGRUACTPROALU GRUACT ON ((GRUACT.X_EMPLEADO IN (:idEmpleados) and TO_CHAR(GRUACT.f_tomapos, 'DD/MM/YYYY') IN (:fechaTomaPosesion) AND :tutor = 0) OR :tutor = 1 OR :direccion = 1) AND GRUACT.X_CENTRO = PROGDIDAC.X_CENTRO AND GRUACT.X_CENTRO = PROGDIDAC.X_CENTRO AND GRUACT.C_ANNO=PROGDIDAC.NU_ANNO"  +
			" INNER JOIN TLUNIAFEGRUACTPRO UAG ON UAG.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU AND UAG.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG AND UAG.L_AFECTATODOS='S'"  +
			" INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO AND MAT.C_ANNO = PROGDIDAC.NU_ANNO AND MAT.X_CENTRO = PROGDIDAC.X_CENTRO AND MAT.X_UNIDAD = UAG.X_UNIDAD AND (MAT.X_ESTGENMATR IS NULL OR MAT.X_ESTGENMATR NOT IN (22,41)) "  +
			" INNER JOIN TLMATMATRIALU MATMATRI ON MATMATRI.X_MATRICULA = MAT.X_MATRICULA AND MATMATRI.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG "  +
			" INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = UAG.X_UNIDAD AND UNICEN.X_CENTRO = PROGDIDAC.X_CENTRO  and ((UNICEN.X_EMPLEADO IN (:idEmpleados) AND :tutor =1) OR :tutor = 0 OR :direccion = 1)"  +
			" WHERE (:idUnidad = 0  OR MAT.X_UNIDAD = :idUnidad) AND ((:nivelCurricular != 0 AND MATMATRI.X_ADAPTACION is not null AND PROGDIDAC.X_NIVEADAP = MAT.X_NIVEADAP and MAT.X_NIVEADAP = :nivelCurricular) OR :nivelCurricular = 0)"  +
			" AND (MAT.C_RESULTADO <> 1 OR MAT.C_RESULTADO IS NULL) " +
			" AND (:idConvCentroOmc = -1 OR (SELECT count(cen.x_convcentro) FROM DELPHOS.TLCONVCENTROS cen " +
			" INNER JOIN DELPHOS.TLCONVCENOMC ccm ON ccm.X_CONVCENTROOMC = :idConvCentroOmc " +
			" WHERE cen.X_CONVCENTRO = ccm.X_CONVCENTRO AND ((cen.X_CONVOCATORIA IS NULL OR cen.X_CONVOCATORIA <> 2) OR (cen.X_CONVOCATORIA = 2 AND MATMATRI.X_CONVOCATORIA != 1 AND (mat.F_PROMOCION IS NULL  " +
			" OR mat.F_PROMOCION <> (SELECT cu.F_SESION FROM DELPHOS.TLCONVUNIDAD cu  " +
			" INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTROOMC = cu.X_CONVCENTROOMC " +
			" INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CONVCENTRO = cco2.X_CONVCENTRO  " +
			" WHERE cu.X_UNIDAD = :idUnidad AND cce2.X_CONVOCATORIA = 1))))) = 1) " +
			" UNION all " +
			" SELECT DISTINCT ALU.X_ALUMNO idAlumno, mat.X_MATRICULA idMatricula, MATMATRI.x_matmatricula idMatMatriAlu, " +
			" alu.c_numescolar numEscolar, alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos, MATMATRI.X_MATERIAOMG idMateriaOmg, " +
			" CASE WHEN (MATMATRI.X_ESTADO NOT IN (1, 3) OR MATMATRI.X_ADAPTACION IS NOT NULL AND :nivelCurricular = 0 AND mat.x_niveadap <> mat.x_ofertamatrig) THEN 1 ELSE 0 END AS disabled " +
			" FROM TLALUMNOS ALU " +
			" INNER JOIN EVA_RELPROGDIDPOND RELPROGDIDPOND ON RELPROGDIDPOND.X_PONDERACION = :idPonderacion " +
			" INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = RELPROGDIDPOND.ID_PROGDIDAC " +
			" INNER JOIN TLGRUACTPROALU GRUACT ON ((GRUACT.X_EMPLEADO IN (:idEmpleados) and TO_CHAR(GRUACT.f_tomapos, 'DD/MM/YYYY') IN (:fechaTomaPosesion) AND :tutor = 0) OR :tutor = 1 OR :direccion = 1) AND GRUACT.X_CENTRO = PROGDIDAC.X_CENTRO AND GRUACT.X_CENTRO = PROGDIDAC.X_CENTRO AND GRUACT.C_ANNO=PROGDIDAC.NU_ANNO " +
			" INNER JOIN TLUNIAFEGRUACTPRO UAG ON UAG.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU AND UAG.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG  AND UAG.L_AFECTATODOS='N'  " +
			" INNER JOIN TLUNIAFETRAHOR UNH ON UNH.X_UNIDAD=UAG.X_UNIDAD AND UNH.X_MATERIAOMG=UAG.X_MATERIAOMG " +
			" INNER JOIN TLHORARIOSR HR ON HR.X_EMPLEADO=GRUACT.X_EMPLEADO AND HR.F_TOMAPOS=GRUACT.F_TOMAPOS AND HR.X_HORARIORE=UNH.X_HORARIORE " +
			" INNER JOIN TLALUENDEPEN ADP on  adp.x_unidad=uag.x_unidad and adp.x_Horariore=UNH.X_HORARIORE " +
			" INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO AND MAT.C_ANNO = PROGDIDAC.NU_ANNO AND MAT.X_CENTRO= PROGDIDAC.X_CENTRO AND MAT.X_UNIDAD = UAG.X_UNIDAD  AND MAT.X_MATRICULA=ADP.X_MATRICULA AND (MAT.X_ESTGENMATR IS NULL OR MAT.X_ESTGENMATR NOT IN (22,41))   " +
			" INNER JOIN TLMATMATRIALU MATMATRI ON MATMATRI.X_MATRICULA = MAT.X_MATRICULA AND MATMATRI.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG    " +
			" INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = UAG.X_UNIDAD AND UNICEN.X_CENTRO = PROGDIDAC.X_CENTRO and ((UNICEN.X_EMPLEADO IN (:idEmpleados) AND :tutor =1) OR :tutor = 0 OR :direccion = 1)    " +
			" WHERE (:idUnidad = 0  OR MAT.X_UNIDAD = :idUnidad) AND ((:nivelCurricular != 0 AND MATMATRI.X_ADAPTACION is not null AND PROGDIDAC.X_NIVEADAP = MAT.X_NIVEADAP and MAT.X_NIVEADAP = :nivelCurricular) OR :nivelCurricular = 0)    " +
			" AND (MAT.C_RESULTADO <> 1 OR MAT.C_RESULTADO IS NULL) " +
			" AND (:idConvCentroOmc = -1 OR (SELECT count(cen.x_convcentro) FROM DELPHOS.TLCONVCENTROS cen " +
			" INNER JOIN DELPHOS.TLCONVCENOMC ccm ON ccm.X_CONVCENTROOMC = :idConvCentroOmc " +
			" WHERE cen.X_CONVCENTRO = ccm.X_CONVCENTRO AND ((cen.X_CONVOCATORIA IS NULL OR cen.X_CONVOCATORIA <> 2) OR (cen.X_CONVOCATORIA = 2 AND MATMATRI.X_CONVOCATORIA != 1 AND (mat.F_PROMOCION IS NULL  " +
			" OR mat.F_PROMOCION <> (SELECT cu.F_SESION FROM DELPHOS.TLCONVUNIDAD cu  " +
			" INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTROOMC = cu.X_CONVCENTROOMC " +
			" INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CONVCENTRO = cco2.X_CONVCENTRO  " +
			" WHERE cu.X_UNIDAD = :idUnidad AND cce2.X_CONVOCATORIA = 1))))) = 1) " +
			" ) order by apellidos ASC", nativeQuery = true)
	List<AlumnoEvaluacionProjection> getAlumnosEvaluacionYAcnee(@Param("idEmpleados") List<Long> idEmpleados,
														  @Param("fechaTomaPosesion") List<String> fechaTomaPosesion,
														  @Param("idPonderacion") Long idPonderacion,
														  @Param("nivelCurricular") Long idNivelCurricular,
														  @Param("idUnidad") Long idUnidad,
														  @Param("tutor") Long tutor,
														  @Param("direccion") Long direccion,
														  @Param("idConvCentroOmc") Long idConvCentroOmc);

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
			"    SELECT DISTINCT ma.X_MATERIAOMG " +
			"    FROM MateriasAlumno ma " +
			"    INNER JOIN MateriasLlave ml ON ma.X_MATERIAOMG = ml.materia " +
			"    WHERE ml.materia_llave IN (SELECT X_MATERIAOMG FROM MateriasAlumno WHERE L_APROBADA <> 'S') " +
			") " +
			"SELECT  " +
			"ma.X_MATERIAOMG idMateriaOmg " +
			"FROM  " +
			"    MateriasConLlave ma", nativeQuery = true)
	List<Long> getMateriasLlaveByMatricula(@Param("idMatricula") Long idMatricula);


	@Query(value = "SELECT DISTINCT * FROM ( SELECT DISTINCT ALU.X_ALUMNO idAlumno, mat.X_MATRICULA idMatricula, MATMATRI.x_matmatricula idMatMatriAlu, " +
			"alu.c_numescolar numEscolar, alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos " +
			"FROM TLALUMNOS ALU          " +
			"INNER JOIN EVA_RELPROGDIDPOND RELPROGDIDPOND ON RELPROGDIDPOND.X_PONDERACION = :idPonderacion " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = RELPROGDIDPOND.ID_PROGDIDAC " +
			"INNER JOIN TLGRUACTPROALU GRUACT ON ((GRUACT.X_EMPLEADO IN (:idEmpleados) AND TO_CHAR(GRUACT.f_tomapos, 'DD/MM/YYYY') IN (:fechaTomaPosesion) AND :tutor = 0) OR :tutor = 1) AND GRUACT.X_CENTRO = PROGDIDAC.X_CENTRO AND GRUACT.X_CENTRO = PROGDIDAC.X_CENTRO AND GRUACT.C_ANNO=PROGDIDAC.NU_ANNO " +
			"INNER JOIN TLUNIAFEGRUACTPRO UAG ON UAG.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU AND UAG.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG AND UAG.L_AFECTATODOS='S' " +
			"INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO AND MAT.C_ANNO = PROGDIDAC.NU_ANNO AND MAT.X_CENTRO = PROGDIDAC.X_CENTRO AND MAT.X_UNIDAD = UAG.X_UNIDAD " +
			"INNER JOIN TLMATMATRIALU MATMATRI ON MATMATRI.X_MATRICULA = MAT.X_MATRICULA AND MATMATRI.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG " +
			"INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = UAG.X_UNIDAD AND UNICEN.X_CENTRO = PROGDIDAC.X_CENTRO  and ((UNICEN.X_EMPLEADO IN (:idEmpleados) AND :tutor =1) OR :tutor = 0) " +
			"WHERE ALU.X_ALUMNO = :idAlumno AND (:idUnidad = 0  OR MAT.X_UNIDAD = :idUnidad) AND ((:nivelCurricular != 0 AND MATMATRI.X_ADAPTACION is not null AND PROGDIDAC.X_NIVEADAP = MAT.X_NIVEADAP and MAT.X_NIVEADAP = :nivelCurricular) OR (:nivelCurricular = 0 AND MATMATRI.X_ADAPTACION IS NULL))  " +
			"UNION all " +
			"SELECT DISTINCT ALU.X_ALUMNO idAlumno, mat.X_MATRICULA idMatricula, MATMATRI.x_matmatricula idMatMatriAlu, " +
			"alu.c_numescolar numEscolar, alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos " +
			"FROM TLALUMNOS ALU " +
			"INNER JOIN EVA_RELPROGDIDPOND RELPROGDIDPOND ON RELPROGDIDPOND.X_PONDERACION = :idPonderacion " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = RELPROGDIDPOND.ID_PROGDIDAC " +
			"INNER JOIN TLGRUACTPROALU GRUACT ON ((GRUACT.X_EMPLEADO IN (:idEmpleados) and TO_CHAR(GRUACT.f_tomapos, 'DD/MM/YYYY') IN (:fechaTomaPosesion) AND :tutor = 0) OR :tutor = 1) AND GRUACT.X_CENTRO = PROGDIDAC.X_CENTRO AND GRUACT.X_CENTRO = PROGDIDAC.X_CENTRO AND GRUACT.C_ANNO=PROGDIDAC.NU_ANNO " +
			"INNER JOIN TLUNIAFEGRUACTPRO UAG ON UAG.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU AND UAG.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG  AND UAG.L_AFECTATODOS='N' " +
			"INNER JOIN TLUNIAFETRAHOR UNH ON UNH.X_UNIDAD=UAG.X_UNIDAD AND UNH.X_MATERIAOMG=UAG.X_MATERIAOMG " +
			"INNER JOIN TLHORARIOSR HR ON HR.X_EMPLEADO=GRUACT.X_EMPLEADO AND HR.F_TOMAPOS=GRUACT.F_TOMAPOS AND HR.X_HORARIORE=UNH.X_HORARIORE " +
			"INNER JOIN TLALUENDEPEN ADP on  adp.x_unidad=uag.x_unidad and adp.x_Horariore=UNH.X_HORARIORE " +
			"INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO AND MAT.C_ANNO = PROGDIDAC.NU_ANNO AND MAT.X_CENTRO= PROGDIDAC.X_CENTRO AND MAT.X_UNIDAD = UAG.X_UNIDAD  AND MAT.X_MATRICULA=ADP.X_MATRICULA " +
			"INNER JOIN TLMATMATRIALU MATMATRI ON MATMATRI.X_MATRICULA = MAT.X_MATRICULA AND MATMATRI.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG " +
			"INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = UAG.X_UNIDAD AND UNICEN.X_CENTRO = PROGDIDAC.X_CENTRO and ((UNICEN.X_EMPLEADO IN (:idEmpleados) AND :tutor =1) OR :tutor = 0) " +
			"WHERE ALU.X_ALUMNO = :idAlumno AND (:idUnidad = 0  OR MAT.X_UNIDAD = :idUnidad) AND ((:nivelCurricular != 0 AND MATMATRI.X_ADAPTACION is not null AND PROGDIDAC.X_NIVEADAP = MAT.X_NIVEADAP and MAT.X_NIVEADAP = :nivelCurricular) OR (:nivelCurricular = 0 AND MATMATRI.X_ADAPTACION IS NULL)) ) "
			 , nativeQuery = true)
	AlumnoEvaluacionProjection getAlumnoConvEvaluacion(@Param("idEmpleados") List<Long> idEmpleados,
												   @Param("fechaTomaPosesion") List<String> fechaTomaPosesion,
												   @Param("idPonderacion") Long idPonderacion,
												   @Param("nivelCurricular") Long idNivelCurricular,
												   @Param("idUnidad") Long idUnidad,
												   @Param("tutor") Long tutor,
												   @Param("idAlumno") Long idAlumno);

    @Query(value = "SELECT F.B_FOTO " + "FROM TLALUMNOS ALU, TLALUMNOSFOTO F "
            + "WHERE ALU.C_NUMESCOLAR = :numescolar AND ALU.X_ALUMNO = F.X_ALUMNO ", nativeQuery = true)
    Blob getFotoAlumno(@Param("numescolar") String numescolar);

	@Query(value = "SELECT valcom.X_PONDERACION FROM TLVALCOMALU valcom " +
			"WHERE valcom.X_MATMATRICULA = :idMatmatricula AND X_CONVCENTROOMC = :idConvCentroOmc FETCH FIRST 1 ROW ONLY", nativeQuery = true)
	Long getIdPonderacionByMatMatriculaAndIdConvCentroOmc(@Param("idMatmatricula") Long idMatmatricula, @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Query(value = "SELECT PONCOMESP.X_COMESP comEsp, PONCOMESP.PORCENTAJE porcentaje, COM.t_abrev abreviatura, COM.d_comesp descripcion " +
            "FROM DELPHOS.TLRELPONCOMESP PONCOMESP " +
            "INNER JOIN DELPHOS.TLCOMESP COM ON COM.X_COMESP = PONCOMESP.X_COMESP " +
            "WHERE PONCOMESP.X_PONDERACION = :idPonderacion ORDER BY com.n_ordenpres", nativeQuery = true)
    List<CompetenciaAlumnoProjection> getCompetenciasAlumnos(@Param("idPonderacion") Long idPonderacion);

    @Query(value = "SELECT DISTINCT CRI.X_CRIEVA AS criEva, cri.t_abrev abreviatura, cri.D_CRIEVA descripcion, " +
			"relcri.porcentaje porcentaje, cri.n_ordenpres, relcri.ID_OPECALCRIEVA idTipoOperacion, OPE.TX_OPECALCRIEVA nombreTipoOperacion " +
            "FROM DELPHOS.TLCRIEVA CRI " +
            "INNER JOIN DELPHOS.TLRELPONCRIEVA RELCRI ON RELCRI.X_CRIEVA = CRI.X_CRIEVA " +
            "INNER JOIN DELPHOS.TLPONDERACION PON ON PON.X_PONDERACION = RELCRI.X_PONDERACION " +
            "INNER JOIN DELPHOS.TLRELPONCOMESP COMP ON COMP.X_COMESP  = CRI.X_COMESP " +
			"INNER JOIN EVA_OPECALCRIEVA OPE ON OPE.ID_OPECALCRIEVA = RELCRI.ID_OPECALCRIEVA " +
            "WHERE RELCRI.X_PONDERACION = :idPonderacion AND CRI.X_COMESP = :idCompetencia " +
            "ORDER BY cri.n_ordenpres", nativeQuery = true)
    List<CriterioAlumnoProjection> getCriteriosAlumno(@Param("idPonderacion") Long idPonderacion, @Param("idCompetencia") Long idCompetencia);

	@Query(value = "SELECT DISTINCT conv.X_CONVCENTRO idConvCentro, convCen.x_convcentroomc idConvCentroOmc, " +
			"convUni.X_ESTADOCONV estadoConv, convEst.D_ESTADOCONV descEstadoConv, conv.x_convocatoria idTipoConv, conv.D_CONVOCATORIA convocatoria, " +
			"(SELECT LISTAGG(DISTINCT(MATCUR.T_ABREV || ' - ' || MATCUR.D_MATERIAC), ', ')       " +
			"FROM EVA_PROGDIDAC PD " +
			"INNER JOIN TLMATALU MAT ON MAT.X_UNIDAD = :idUnidad AND MAT.X_OFERTAMATRIG = PD.X_OFERTAMATRIG AND MAT.X_CENTRO = PD.X_CENTRO AND MAT.C_ANNO = PD.NU_ANNO  AND mat.X_OFERTAMATRIG = ofermacen.X_OFERTAMATRIG " +
			"INNER JOIN TLMATMATRIALU MMA ON MMA.X_MATRICULA = MAT.X_MATRICULA AND PD.X_MATERIAOMG = MMA.X_MATERIAOMG " +
			"INNER JOIN TLMATOFEMATRG MATOFE ON MATOFE.X_MATERIAOMG = PD.X_MATERIAOMG      " +
			"INNER JOIN TLMATERIASCURSO MATCUR ON MATCUR.X_MATERIAC = MATOFE.X_MATERIAC        " +
			"AND PD.ID_PROGDIDAC NOT IN (SELECT DISTINCT PD2.ID_PROGDIDAC  " +
			"FROM EVA_PROGDIDAC PD2 " +
			"INNER JOIN TLMATALU MAT ON MAT.X_UNIDAD = :idUnidad   " +
			"INNER JOIN TLMATMATRIALU MMA ON MMA.X_MATRICULA = MAT.X_MATRICULA  " +
			"INNER JOIN EVA_RELPROGDIDPOND RPP ON RPP.ID_PROGDIDAC = PD2.ID_PROGDIDAC  " +
			"INNER JOIN TLRELPONCOMESP RPC ON RPC.X_PONDERACION = RPP.X_PONDERACION AND EXISTS (SELECT 1 FROM TLVALCOMALU COM WHERE RPC.X_COMESP = COM.X_COMESP AND COM.X_MATMATRICULA = MMA.X_MATMATRICULA AND COM.X_CONVCENTROOMC = convCen.x_convcentroomc) " +
			"WHERE MAT.X_OFERTAMATRIG = PD2.X_OFERTAMATRIG AND MAT.X_CENTRO = PD2.X_CENTRO AND MAT.C_ANNO = PD2.NU_ANNO AND PD2.X_MATERIAOMG = MMA.X_MATERIAOMG)) nombreMaterias " +
			"FROM TLCONVCENTROS conv " +
			"INNER JOIN TLOFEMATRCEN ofermacen ON ofermacen.X_OFERTAMATRIG  = :idOfertaMatrig " +
			"INNER JOIN TLCONVCENOMC convCen ON convCen.X_CONVCENTRO = conv.X_CONVCENTRO " +
			"INNER JOIN TLCONVUNIDAD convUni ON convUni.X_CONVCENTROOMC = convCen.X_CONVCENTROOMC AND convCen.X_OFERTAMATRIC = ofermacen.X_OFERTAMATRIC " +
			"INNER JOIN TLESTADOSCONV convEst ON convUni.X_ESTADOCONV = convEst.X_ESTADOCONV " +
			"WHERE conv.C_ANNO = :anno " +
			"AND convUni.x_unidad = :idUnidad " +
			"ORDER BY conv.x_convcentro", nativeQuery = true)
	List<ConvocatoriaProjection> getConvocatorias(@Param("anno") Long anno, @Param("idUnidad") Long idUnidad, @Param("idOfertaMatrig") Long idOfertaMatrig);

    @Query(value = "SELECT DISTINCT conv.X_CONVCENTRO idConvCentro, convCen.x_convcentroomc idConvCentroOmc, " +
			"convUni.X_ESTADOCONV estadoConv, convEst.D_ESTADOCONV descEstadoConv, conv.x_convocatoria idTipoConv, conv.D_CONVOCATORIA convocatoria " +
			"FROM TLCONVCENTROS conv " +
			"INNER JOIN TLCONVCENOMC convCen ON convCen.X_CONVCENTRO = conv.X_CONVCENTRO " +
			"INNER JOIN TLCONVUNIDAD convUni ON convUni.X_CONVCENTROOMC = convCen.X_CONVCENTROOMC " +
			"INNER JOIN TLESTADOSCONV convEst ON convUni.X_ESTADOCONV = convEst.X_ESTADOCONV " +
			"INNER JOIN TLOFEMATRCEN OFECEN ON OFECEN.X_OFERTAMATRIC = CONVCEN.X_OFERTAMATRIC " +
			"WHERE conv.C_ANNO = :anno " +
			"AND convUni.x_unidad = :idUnidad " +
			"AND OFECEN.X_OFERTAMATRIG = :idOfertaMatrig " +
			"ORDER BY conv.x_convcentro", nativeQuery = true)
    List<ConvocatoriaProjection> getConvocatoriasValoracionCriterios(@Param("anno") Long anno, @Param("idUnidad") Long idUnidad, @Param("idOfertaMatrig") Long idOfertaMatrig);

    @Query(value = "SELECT DISTINCT notcal.X_NOTCALMAT idNotAlu, notcal.X_CALIFICA idCalifica, " +
            "notcal.N_NOTA nota, cal.T_ABREV descCal, cal.L_APRUEBA aprueba " +
            "FROM TLNOTCALMAT notcal LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = notcal.X_CALIFICA " +
            "WHERE notcal.X_MATMATRICULA = :idMatMatriAlu AND notcal.X_CONVCENTROOMC = :idCentroOmc", nativeQuery = true)
    NotaGlobalProjection getNotaGlobal(@Param("idMatMatriAlu") Long idCompetencia, @Param("idCentroOmc") Long idMatMatriAlu);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DELPHOS.TLNOTCALMAT (X_NOTCALMAT, X_CALIFICA, X_MATMATRICULA, X_CONVCENTROOMC, N_NOTA) " +
            "VALUES (TLS_NOTCALMAT.nextval, TO_NUMBER(:idCalifica), :idMatricula, :idCentroOmc, TO_NUMBER(:nota))", nativeQuery = true)
    void insertNotaGlobalAlumno(
            @Param("idCalifica") Long idCalifica,
            @Param("idMatricula") Long idMatricula,
            @Param("idCentroOmc") Long idCentroOmc,
            @Param("nota") Float nota);

    @Transactional
    @Modifying
    @Query(value = "UPDATE DELPHOS.TLNOTCALMAT x SET x.N_NOTA = TO_NUMBER(:nota), x.X_CALIFICA = TO_NUMBER(:idCalifica) WHERE x.X_NOTCALMAT = :idNotAlu", nativeQuery = true)
    void editNotaGlobalAlumno(@Param("idNotAlu") Long idNotAlu,
                              @Param("idCalifica") Long idCalifica,
                              @Param("nota") Float nota);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM DELPHOS.TLNOTCALMAT x WHERE x.X_NOTCALMAT = :idNotAlu", nativeQuery = true)
    void deleteNotaGlobalAlumno(@Param("idNotAlu") Long idNotAlu);

    @Query(value = "SELECT comAlu.X_VALCOMALU idComAlu, comAlu.X_CALIFICA idCalifica, cal.N_NUMERO nota, cal.T_ABREV descCal, cal.L_APRUEBA aprueba " +
            "FROM TLVALCOMALU comAlu LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = comAlu.X_CALIFICA " +
            "WHERE comAlu.X_COMESP = :idCompetencia AND comAlu.X_MATMATRICULA = :idMatMatriAlu AND comAlu.X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
    NotaCompetenciaProjection getNotasCompetencias(@Param("idCompetencia") Long idCompetencia,
                                                   @Param("idMatMatriAlu") Long idMatMatriAlu,
                                                   @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DELPHOS.TLVALCOMALU (X_VALCOMALU, X_PONDERACION, X_COMESP, X_CALIFICA, " +
            "X_MATMATRICULA, X_CONVCENTROOMC) VALUES (TLS_TLVALCOMALU.nextval, :idPonderacion, " +
            ":idCompetencia, TO_NUMBER(:idCalifica), :idMatMatriAlu, :idCentroOmc)", nativeQuery = true)
    void insertNotasComAlumnos(
            @Param("idPonderacion") Long idPonderacion,
            @Param("idCalifica") Long idCalifica,
            @Param("idCompetencia") Long idCompetencia,
            @Param("idMatMatriAlu") Long idMatMatriAlu,
            @Param("idCentroOmc") Long idCentroOmc);

    @Transactional
    @Modifying
    @Query(value = "UPDATE TLVALCOMALU SET X_CALIFICA = TO_NUMBER(:idCalifica) WHERE X_VALCOMALU = :idComAlu", nativeQuery = true)
    void editNotasComAlumnos(@Param("idComAlu") Long idComAlu, @Param("idCalifica") Long idCalifica);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM TLVALCOMALU WHERE X_VALCOMALU = :idComAlu", nativeQuery = true)
    void deleteNotasComAlumnos(@Param("idComAlu") Long idComAlu);

    @Query(value = "SELECT criAlu.X_VALCRIALU idCriAlu, criAlu.X_CALIFICA idCalifica, cal.N_NUMERO nota, cal.T_ABREV descCal, cal.L_APRUEBA aprueba " +
            "FROM TLVALCRIALU criAlu LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = criAlu.X_CALIFICA " +
            "WHERE criAlu.X_CRIEVA = :idCriEva AND criAlu.X_MATMATRICULA = :idMatMatriAlu AND criAlu.X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
    NotaCriterioProjection getNotasCriterios(@Param("idCriEva") Long idCriEva,
                                             @Param("idMatMatriAlu") Long idMatMatriAlu,
                                             @Param("idConvCentroOmc") Long idConvCentroOmc);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM TLVALCRIALU WHERE X_VALCRIALU = :idCriAlu", nativeQuery = true)
    void deleteNotasCriAlumnos(@Param("idCriAlu") Long idCriAlu);

    @Query(value = "SELECT cal.X_CALIFICA idCalifica, cal.X_SISTCAL idSistCal, " +
            "cal.N_NUMERO nota, cal.D_CALIFICA descripcion, cal.T_ABREV descCal, cal.L_APRUEBA aprueba " +
            "FROM TLCALIFICACIONES cal " +
            "INNER JOIN TLRELSISETA sisEta ON sisEta.X_SISTCAL = cal.X_SISTCAL " +
            "WHERE sisEta.X_ETAPA = :idEtapa " +
            "ORDER BY cal.N_ORDEN", nativeQuery = true)
    List<SistemaCalificacionProjection> sistemaCalificacion(@Param("idEtapa") Long idSistCal);

    @Query(value = "select cal.X_CALIFICA idCalifica, cal.X_SISTCAL idSistCal, " +
            "cal.N_NUMERO nota, cal.D_CALIFICA descripcion, cal.T_ABREV descCal, cal.L_APRUEBA aprueba, sis.S_SISTCAL descSis, DECODE(sis.l_numerico, 'S', 'true', 'false') numerico " +
            "from tlofematrgen ofg " +
            "INNER JOIN TLSISCAL sis ON sis.x_sistcal = ofg.x_sistcal " +
            "INNER JOIN TLCALIFICACIONES CAL ON CAL.x_sistcal = sis.x_sistcal  " +
            "where OFG.X_OFERTAMATRIG = :idOfertaMatrig", nativeQuery = true)
    List<SistemaCalificacionProjection> sistemaCalificacionGlobal(@Param("idOfertaMatrig") Long idOfertaMatrig);

    @Transactional
    @Modifying
    @Query(value = "UPDATE DELPHOS.TLPONDERACION SET L_EDITABLE = 'N' WHERE X_PONDERACION = :idPonderacion", nativeQuery = true)
    void bloquearPonderacion(@Param("idPonderacion") Long idPonderacion);

    ////////////////////////
    
    @Query(value = "SELECT DISTINCT unidades.X_UNIDAD idUnidad, unidades.T_NOMBRE unidad, curso.X_ETAPA idCurso, OFG.D_OFERTAMATRIG curso, etapa.X_ETAPA idEtapa, etapa.X_ETAPA idEtapaSec, ciclo.X_ETAPA idCiclo, etapa.S_ETAPA etapa, "
			+ "etapa.n_orden ordenetapa, curso.n_orden ordencurso FROM TLGRUACTPROALU grupoActividad INNER JOIN TLUNIAFEGRUACTPRO grupo ON grupo.X_GRUACTPROALU = grupoActividad.X_GRUACTPROALU "
			+ "INNER JOIN TLUNIDADESCEN unidades ON unidades.X_UNIDAD = grupo.X_UNIDAD INNER JOIN TLMATOFEMATRG mat ON grupo.X_MATERIAOMG = mat.X_MATERIAOMG "
			+ "INNER JOIN TLOFEMATRGEN OFG ON MAT.X_OFERTAMATRIG=OFG.X_OFERTAMATRIG  "
			+ "INNER JOIN TLMATERIASCURSO matCurso ON mat.X_MATERIAC = matCurso.X_MATERIAC INNER JOIN TLCURSOMODA modalidad ON matCurso.X_CURSOMOD = modalidad.X_CURSOMOD "
			+ "INNER JOIN TLETAPAS curso ON curso.X_ETAPA = modalidad.X_ETAPA INNER JOIN TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA "
			+ "INNER JOIN TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA INNER JOIN TLPONDERACION pond ON pond.X_DOCENTE = grupoActividad.X_EMPLEADO AND pond.x_materia = mat.x_materiaomg "
			+ "WHERE unidades.X_EMPLEADO = :idEmpleado AND grupoActividad.C_ANNO = :anno ORDER BY etapa.n_orden, curso.n_orden, unidad", nativeQuery = true)
    List<UnidadEvaluacionProjection> getUnidadesEvaluacion(
            @Param("idEmpleado") Long idEmpleado,
            @Param("anno") Long anno);
    
    @Query(value = "SELECT mua.X_MATRICULA idMatricula, alu.X_ALUMNO idAlumno, "
    		+ "etapa.X_ETAPA idEtapa, ciclo.X_ETAPA idCiclo, udc.X_UNIDAD idUnidad, "
    		+ "DELPHOS.TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) nombreAlumno, "
    		+ "CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) THEN 1 ELSE 0 END AS acnee, "
    		+ "(SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) nivelCurricular, "
    		+ "etapa.S_ETAPA nombreEtapa, ciclo.S_ETAPA nombreCiclo, udc.T_NOMBRE nombreUnidad "
    		+ "FROM DELPHOS.tlunidadescen udc "
    		+ "INNER JOIN DELPHOS.TLMATALU mua ON mua.x_unidad = udc.x_unidad AND mua.x_centro = udc.x_centro AND mua.c_anno = udc.c_anno AND nvl(mua.c_resultado, 99) > 1 "
    		+ "INNER JOIN DELPHOS.TLALUMNOS alu ON alu.x_alumno = mua.x_alumno "
    		+ "INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = mua.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = omg.X_MODALIDAD "
    		+ "INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
    		+ "INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA "
    		+ "INNER JOIN DELPHOS.TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA "
    		+ "INNER JOIN DELPHOS.TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA "
    		+ "WHERE curso.x_etapa = :idCurso "
    		+ "AND udc.X_UNIDAD = :idUnidad "
			+ "AND omg.X_OFERTAMATRIG = :idOfertamatrig "
    		+ "ORDER BY nombreAlumno", nativeQuery = true)
    List<AlumnoEvaluacionSelProjection> getAlumnosUnidad(@Param("idCurso") Long idCurso, @Param("idUnidad") Long idUnidad, @Param("idOfertamatrig") Long idOfertamatrig);

	@Query(value = "SELECT mua.X_MATRICULA idMatricula, alu.X_ALUMNO idAlumno,  " +
			" etapa.X_ETAPA idEtapa, ciclo.X_ETAPA idCiclo, udc.X_UNIDAD idUnidad,  " +
			" DELPHOS.TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) nombreAlumno,  " +
			" CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) THEN 1 ELSE 0 END AS acnee,  " +
			" (SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) nivelCurricular,  " +
			" etapa.S_ETAPA nombreEtapa, ciclo.S_ETAPA nombreCiclo, udc.T_NOMBRE nombreUnidad  " +
			" FROM DELPHOS.tlunidadescen udc  " +
			" INNER JOIN DELPHOS.TLMATALU mua ON mua.x_unidad = udc.x_unidad AND mua.x_centro = udc.x_centro AND mua.c_anno = udc.c_anno AND nvl(mua.c_resultado, 99) > 1  " +
			" INNER JOIN DELPHOS.TLALUMNOS alu ON alu.x_alumno = mua.x_alumno  " +
			" INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = mua.X_OFERTAMATRIG  " +
			" INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = omg.X_OFERTAMATRIG  " +
			" INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = omg.X_MODALIDAD  " +
			" INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD  " +
			" INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA  " +
			" INNER JOIN DELPHOS.TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA  " +
			" INNER JOIN DELPHOS.TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA  " +
			" INNER JOIN DELPHOS.TLCONVUNIDAD cu ON cu.X_UNIDAD = udc.X_UNIDAD  " +
			" INNER JOIN DELPHOS.TLCONVCENOMC cco ON cco.X_CONVCENTROOMC = cu.X_CONVCENTROOMC  " +
			" INNER JOIN DELPHOS.TLCONVCENTROS cce ON cce.X_CONVCENTRO = cco.X_CONVCENTRO  " +
			" WHERE curso.x_etapa = :idCurso  " +
			" AND udc.X_UNIDAD = :idUnidad  " +
			" AND omg.X_OFERTAMATRIG = :idOfertamatrig  " +
			" AND cce.X_CONVCENTRO = :idConvocatoria  " +
			" AND (cce.X_CONVOCATORIA = 2 AND (mua.F_PROMOCION IS NULL OR mua.F_PROMOCION <> (SELECT cu2.F_SESION FROM DELPHOS.TLCONVUNIDAD cu2  " +
			" INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTROOMC = cu2.X_CONVCENTROOMC  " +
			" INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CONVCENTRO = cco2.X_CONVCENTRO  " +
			" WHERE cu2.X_UNIDAD = udc.X_UNIDAD AND cce2.X_CONVOCATORIA = 1)  " +
			" OR cce.X_CONVOCATORIA <> 2)) " +
			" ORDER BY nombreAlumno", nativeQuery = true)
	List<AlumnoEvaluacionSelProjection> getAlumnosUnidadConvExtra(@Param("idCurso") Long idCurso, @Param("idUnidad") Long idUnidad, @Param("idConvocatoria") Long idConvocatoria, @Param("idOfertamatrig") Long idOfertamatrig);
    
    @Query(value = "SELECT mua.X_MATRICULA idMatricula, alu.X_ALUMNO idAlumno,  " +
			" etapa.X_ETAPA idEtapa, cco.X_CONVCENTROOMC idConvCentroOmc, alu.C_NUMESCOLAR numEscolar,  " +
			" DELPHOS.TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) nombreAlumno,  " +
			" etapa.S_ETAPA nombreEtapa, cce.S_CONVOCATORIA nombreConvocatoria,  " +
			" CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) THEN 1 ELSE 0 END AS acnee,  " +
			" (SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) nivelCurricular  " +
			" FROM DELPHOS.TLMATALU mua  " +
			" INNER JOIN DELPHOS.TLCONVCENTROS cce ON cce.X_CONVCENTRO = :idConvocatoria AND cce.C_ANNO = mua.C_ANNO AND cce.X_CENTRO = mua.X_CENTRO  " +
			" INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = mua.X_OFERTAMATRIC AND omc.X_OFERTAMATRIG = mua.X_OFERTAMATRIG AND omc.X_CENTRO = cce.X_CENTRO  " +
			" INNER JOIN DELPHOS.TLCONVCENOMC cco ON cco.X_OFERTAMATRIC = omc.X_OFERTAMATRIC AND cco.X_CONVCENTRO = cce.X_CONVCENTRO  " +
			" INNER JOIN DELPHOS.TLALUMNOS alu ON alu.X_ALUMNO = mua.X_ALUMNO  " +
			" INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG  " +
			" INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = omg.X_OFERTAMATRIG  " +
			" INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = omg.X_MODALIDAD  " +
			" INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD  " +
			" INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA  " +
			" INNER JOIN DELPHOS.TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA  " +
			" INNER JOIN DELPHOS.TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA  " +
			" WHERE  curso.x_etapa = :idCurso  " +
			" AND mua.X_UNIDAD = :idUnidad  " +
			" AND omg.X_OFERTAMATRIG = :idOfertamatrig  " +
			" AND NVL(mua.C_RESULTADO, 99) > 1  " +
			" AND (cce.X_CONVOCATORIA <> 2 OR cce.X_CONVOCATORIA = 2 AND (mua.F_PROMOCION IS NULL  " +
			" OR mua.F_PROMOCION <> (SELECT cu.F_SESION FROM DELPHOS.TLCONVUNIDAD cu  " +
			" INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTROOMC = cu.X_CONVCENTROOMC  " +
			" INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CONVCENTRO = cco2.X_CONVCENTRO  " +
			" WHERE cu.X_UNIDAD = :idUnidad AND cce2.X_CONVOCATORIA = 1))) " +
			"ORDER BY nombreAlumno", nativeQuery = true)
    List<AlumnoValoracionProjection> getAlumnosValoracionByUnidadAndConvocatoria(@Param("idCurso") Long idCurso, @Param("idUnidad") Long idUnidad, @Param("idConvocatoria") Long idConvocatoria, @Param("idOfertamatrig") Long idOfertamatrig);
    
    @Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getIdEjecucionRegSelDoc(); 
    
    
    @Query(value = "SELECT DISTINCT mua.X_MATRICULA idMatricula, alu.X_ALUMNO idAlumno, "
    		+ "etapa.X_ETAPA idEtapa, ciclo.X_ETAPA idCiclo, udc.X_UNIDAD idUnidad, "
    		+ "DELPHOS.TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) nombreAlumno, "
    		+ "etapa.S_ETAPA nombreEtapa, ciclo.S_ETAPA nombreCiclo, udc.T_NOMBRE nombreUnidad, "
			+ "CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) THEN 1 ELSE 0 END AS acnee, "
			+ "(SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) nivelCurricular "
    		+ "FROM DELPHOS.TLMATALU mua "
    		+ "INNER JOIN DELPHOS.TLALUMNOS alu ON alu.X_ALUMNO = mua.X_ALUMNO "
    		+ "INNER JOIN DELPHOS.TLUNIDADESCEN udc ON udc.X_UNIDAD = mua.X_UNIDAD AND udc.X_CENTRO = mua.X_CENTRO AND udc.C_ANNO = mua.C_ANNO "
    		+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO grupo ON grupo.X_UNIDAD = udc.X_UNIDAD "
    		+ "INNER JOIN DELPHOS.TLMATOFEMATRG mat ON mat.X_MATERIAOMG = grupo.X_MATERIAOMG "
    		+ "INNER JOIN DELPHOS.TLMATERIASCURSO matCurso ON matCurso.X_MATERIAC = mat.X_MATERIAC "
    		+ "INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = mua.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = omg.X_MODALIDAD "
    		+ "INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = matCurso.X_CURSOMOD "
    		+ "INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA "
    		+ "INNER JOIN DELPHOS.TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA "
    		+ "INNER JOIN DELPHOS.TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA "
    		+ "WHERE mua.X_MATRICULA IN (:idsMatricula) "
    		+ "ORDER BY nombreAlumno", nativeQuery = true)
    List<AlumnoEvaluacionSelProjection> getAlumnosByMatriculas(@Param("idsMatricula") Long[] idsMatricula);
    
    @Query(value = "SELECT ccl.X_COMCLAVE idCompetenciaClave, ccl.D_COMCLAVE descCompetenciaClave, ccl.T_ABREV abrevCompetenciaClave, "
    		+ "vcc.X_CALIFICA idCalifica, cal.T_ABREV descCal, cal.n_numero nota, cal.L_APRUEBA aprueba, "
    		+ "vcc.X_MATRICULA idMatricula, vcc.X_CONVCENTROOMC idConvCentroOmc "
    		+ "FROM DELPHOS.TLCOMCLAVE ccl "
    		+ "LEFT JOIN DELPHOS.TLVALCOMCLAALU vcc ON vcc.X_COMCLAVE = ccl.X_COMCLAVE AND vcc.X_MATRICULA = :idMatricula AND vcc.X_CONVCENTROOMC = :idConvCentroOmc "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcc.X_CALIFICA "
    		+ "ORDER BY ccl.N_ORDENPRES", nativeQuery = true)
    List<CompetenciaClaveAlumnoProjection> getCompetenciasClaveAlumno(@Param("idMatricula") Long idMatricula, @Param("idConvCentroOmc") Long idConvCentroOmc);
    
    @Query(value = "SELECT dop.X_DESOPERATIVO idDescriptorOperativo, dop.D_DESOPERATIVO descDescriptorOperativo, dop.T_ABREV abrevDescriptorOperativo, "
    		+ "vdo.X_CALIFICA idCalifica, cal.T_ABREV descCal, cal.n_numero nota, cal.L_APRUEBA aprueba, "
    		+ "vdo.X_MATRICULA idMatricula, vdo.X_CONVCENTROOMC idConvCentroOmc "
    		+ "FROM DELPHOS.TLDESOPERATIVO dop "
    		+ "LEFT JOIN DELPHOS.TLVALDESOPEALU vdo ON vdo.X_DESOPERATIVO = dop.X_DESOPERATIVO AND vdo.X_MATRICULA = :idMatricula AND vdo.X_CONVCENTROOMC = :idConvCentroOmc "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vdo.X_CALIFICA "
    		+ "WHERE dop.X_COMCLAVE = :idCompetenciaClave AND dop.X_ETAPA = :idEtapa "
    		+ "ORDER BY dop.N_ORDENPRES", nativeQuery = true)
    List<DescriptorOperativoAlumnoProjection> getDescriptoresOperativosAlumno(@Param("idCompetenciaClave") Long idCompetenciaClave, @Param("idEtapa") Long idEtapa,
    																	@Param("idMatricula") Long idMatricula,
    																	@Param("idConvCentroOmc") Long idConvCentroOmc);
    
    @Query(value = "SELECT vcc.X_VALCOMCLAALU id, ccl.X_COMCLAVE idCompetenciaClave, ccl.T_ABREV nombreCompetenciaClave, "
    		+ "vcc.X_CALIFICA idCalifica, cal.T_ABREV descCal, cal.D_CALIFICA descDetCal, cal.n_numero nota, cal.L_APRUEBA aprueba "
    		+ "FROM DELPHOS.TLCOMCLAVE ccl "
    		+ "LEFT JOIN DELPHOS.TLVALCOMCLAALU vcc ON vcc.X_COMCLAVE = ccl.X_COMCLAVE AND vcc.X_MATRICULA = :idMatricula AND vcc.X_CONVCENTROOMC = :idConvCentroOmc "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcc.X_CALIFICA "
    		+ "ORDER BY ccl.N_ORDENPRES", nativeQuery = true)
    List<ValoracionCompetenciaClaveAlumnoProjection> getValoracionesCompetenciasClaveAlumno(@Param("idMatricula") Long idMatricula, @Param("idConvCentroOmc") Long idConvCentroOmc);
    
    @Query(value = "SELECT vdo.X_VALDESOPEALU id, dop.X_DESOPERATIVO idDescriptorOperativo, dop.T_ABREV nombreDescriptorOperativo, "
    		+ "vdo.X_CALIFICA idCalifica, cal.T_ABREV descCal, cal.D_CALIFICA descDetCal, cal.n_numero nota, cal.L_APRUEBA aprueba "
    		+ "FROM DELPHOS.TLDESOPERATIVO dop "
    		+ "LEFT JOIN DELPHOS.TLVALDESOPEALU vdo ON vdo.X_DESOPERATIVO = dop.X_DESOPERATIVO AND vdo.X_MATRICULA = :idMatricula AND vdo.X_CONVCENTROOMC = :idConvCentroOmc "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vdo.X_CALIFICA "
    		+ "WHERE dop.X_COMCLAVE = :idCompetenciaClave AND dop.X_ETAPA = :idEtapa "
    		+ "ORDER BY dop.N_ORDENPRES", nativeQuery = true)
    List<ValoracionDescriptorOperativoAlumnoProjection> getValoracionesDescriptoresOperativosAlumno(@Param("idCompetenciaClave") Long idCompetenciaClave,
    																								@Param("idEtapa") Long idEtapa,
																									@Param("idMatricula") Long idMatricula,
																									@Param("idConvCentroOmc") Long idConvCentroOmc);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DELPHOS.TLVALCOMCLAALU (X_VALCOMCLAALU, X_COMCLAVE, X_CALIFICA, X_MATRICULA, X_CONVCENTROOMC) " +
            "VALUES (TLS_TLVALCOMCLAALU.nextval, :idCompetenciaClave, :idCalifica, :idMatricula, :idConvCentroOmc)", nativeQuery = true)
    void insertCompetenciasClave(
            @Param("idCompetenciaClave") Long idCompetenciaClave,
            @Param("idCalifica") Long idCalifica,
            @Param("idMatricula") Long idMatricula,
            @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Transactional
    @Modifying
    @Query(value = "UPDATE DELPHOS.TLVALCOMCLAALU SET X_CALIFICA = :idCalifica " +
            "WHERE X_COMCLAVE = :idCompetenciaClave AND X_MATRICULA = :idMatricula AND X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
    void editCompetenciasClave(
            @Param("idCompetenciaClave") Long idCompetenciaClave,
            @Param("idCalifica") Long idCalifica,
            @Param("idMatricula") Long idMatricula,
            @Param("idConvCentroOmc") Long idConvCentroOmc);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM DELPHOS.TLVALCOMCLAALU x "
			     + "WHERE X_COMCLAVE = :idCompetenciaClave AND X_MATRICULA = :idMatricula AND X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
	void deleteCompetenciasClave(@Param("idCompetenciaClave") Long idCompetenciaClave,
								 @Param("idMatricula") Long idMatricula,
								 @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DELPHOS.TLVALDESOPEALU (X_VALDESOPEALU, X_DESOPERATIVO, X_CALIFICA, X_MATRICULA, X_CONVCENTROOMC) " +
            "VALUES (TLS_TLVALDESOPEALU.nextval, :idDescriptorOperativo, :idCalifica, :idMatricula, :idConvCentroOmc)", nativeQuery = true)
    void insertDescriptoresOperativos(
            @Param("idDescriptorOperativo") Long idDescriptorOperativo,
            @Param("idCalifica") Long idCalifica,
            @Param("idMatricula") Long idMatricula,
            @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Transactional
    @Modifying
    @Query(value = "UPDATE DELPHOS.TLVALDESOPEALU SET X_CALIFICA = :idCalifica " +
            "WHERE X_DESOPERATIVO = :idDescriptorOperativo AND X_MATRICULA = :idMatricula AND X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
    void editDescriptoresOperativos(
            @Param("idDescriptorOperativo") Long idDescriptorOperativo,
            @Param("idCalifica") Long idCalifica,
            @Param("idMatricula") Long idMatricula,
            @Param("idConvCentroOmc") Long idConvCentroOmc);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM DELPHOS.TLVALDESOPEALU x WHERE X_DESOPERATIVO = :idDescriptorOperativo AND X_MATRICULA = :idMatricula AND X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
	void deleteDescriptoresOperativos(@Param("idDescriptorOperativo") Long idDescriptorOperativo,
									  @Param("idMatricula") Long idMatricula,
									  @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Query(value = "SELECT x_valcomclaalu from TLVALCOMCLAALU WHERE X_COMCLAVE = :idCompetenciaClave AND X_MATRICULA = :idMatricula AND X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
    Long existCompetenciaClave(
            @Param("idCompetenciaClave") Long idCompetenciaClave,
            @Param("idMatricula") Long idMatricula,
            @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Query(value = "SELECT x_valdesopealu from TLVALDESOPEALU WHERE X_DESOPERATIVO = :idDescriptorOperativo AND X_MATRICULA = :idMatricula AND X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
    Long existDescriptoreOperativo(
            @Param("idDescriptorOperativo") Long idDescriptorOperativo,
            @Param("idMatricula") Long idMatricula,
            @Param("idConvCentroOmc") Long idConvCentroOmc);
    
    @Query(value = "SELECT vca.X_VALCOMALU idComAlu, vca.X_CALIFICA idCalifica, cal.N_NUMERO nota, cal.T_ABREV descCal, cal.L_APRUEBA aprueba "
    		+ "FROM DELPHOS.TLRELDESOPECOMESP rdc "
    		+ "INNER JOIN DELPHOS.TLRELCOMPESMAT rcm ON rcm.X_COMESP = rdc.X_COMESP "
    		+ "INNER JOIN DELPHOS.TLMATALU mua ON mua.X_MATRICULA = :idMatricula "
    		+ "INNER JOIN DELPHOS.TLMATOFEMATRG mog ON mog.X_MATERIAOMG = rcm.X_MATERIAOMG AND mog.X_OFERTAMATRIG = mua.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATRICULA = mua.X_MATRICULA AND mma.X_MATERIAOMG = mog.X_MATERIAOMG "
    		+ "INNER JOIN DELPHOS.TLVALCOMALU vca ON vca.X_COMESP = rdc.X_COMESP AND vca.X_CONVCENTROOMC = :idConvCentroOmc AND vca.X_MATMATRICULA = mma.X_MATMATRICULA "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vca.X_CALIFICA "
    		+ "WHERE rdc.X_DESOPERATIVO = :idDescriptorOperativo", nativeQuery = true)
    List<NotaCompetenciaProjection> getNotasCompetenciasEspAlumnoByDescriptorOperativo(@Param("idDescriptorOperativo") Long idDescriptorOperativo,
    																	@Param("idMatricula") Long idMatricula,
    																	@Param("idConvCentroOmc") Long idConvCentroOmc);
    
    @Query(value = "SELECT DISTINCT MAT.D_MATERIAC MATERIA, COMESP.T_ABREV COMPETENCIAESPABREV, COMESP.X_COMESP IDCOMPETENCIAESP, COMESP.D_COMESP COMPETENCIAESPD, "
    		+ "CAL.T_ABREV VALORACION, CAL.L_APRUEBA APRUEBA FROM TLVALCOMALU VAL INNER JOIN DELPHOS.TLCOMESP COMESP ON COMESP.X_COMESP = VAL.X_COMESP "
    		+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP RELDE ON RELDE.X_COMESP = COMESP.X_COMESP INNER JOIN DELPHOS.TLDESOPERATIVO DESOP ON DESOP.X_DESOPERATIVO = RELDE.X_DESOPERATIVO "
    		+ "INNER JOIN DELPHOS.TLCONVCENOMC CCO ON CCO.X_CONVCENTROOMC = VAL.X_CONVCENTROOMC INNER JOIN DELPHOS.TLETAPAS ETAPAS ON DESOP.X_ETAPA = ETAPAS.X_ETAPA "
    		+ "INNER JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VAL.X_CALIFICA INNER JOIN DELPHOS.TLMATALU MATR ON MATR.X_MATRICULA = :idMatricula "
    		+ "INNER JOIN DELPHOS.TLMATOFEMATRG OMG ON OMG.X_OFERTAMATRIG = MATR.X_OFERTAMATRIG INNER JOIN DELPHOS.TLMATOFEMATRCEN MATOFE ON MATOFE.X_MATERIAOMG = OMG.X_MATERIAOMG "
    		+ "INNER JOIN DELPHOS.TLMATMATRIALU MATMATRI ON MATMATRI.X_MATERIAOMC = MATOFE.X_MATERIAOMC AND MATMATRI.X_MATRICULA = MATR.X_MATRICULA AND MATOFE.X_OFERTAMATRIC = MATR.X_OFERTAMATRIC "
    		+ "INNER JOIN DELPHOS.TLCURSOORG CUO ON CUO.X_OFERTAMATRIG = MATR.X_OFERTAMATRIG INNER JOIN DELPHOS.TLMATERIASCURSO MAT ON MAT.X_MATERIAC = OMG.X_MATERIAC AND MAT.X_CURSOMOD = CUO.X_CURSOMOD "
    		+ "WHERE ETAPAS.X_ETAPA = :etapa AND DESOP.X_DESOPERATIVO = :descripcionOperativa AND CCO.X_CONVCENTROOMC = :idConvCentroOmc AND VAL.X_MATMATRICULA = MATMATRI.X_MATMATRICULA "
    		+ "UNION SELECT DISTINCT MAT.D_MATERIAC MATERIA, COMESP.T_ABREV COMPETENCIAESPABREV, COMESP.X_COMESP IDCOMPETENCIAESP, COMESP.D_COMESP COMPETENCIAESPD, CAL.T_ABREV VALORACION, "
    		+ "CAL.L_APRUEBA APRUEBA FROM DELPHOS.TLETAPAS ETAPAS INNER JOIN DELPHOS.TLDESOPERATIVO DESOP ON DESOP.X_DESOPERATIVO = :descripcionOperativa AND DESOP.X_ETAPA = ETAPAS.X_ETAPA "
    		+ "INNER JOIN DELPHOS.TLETAPAS CICLO ON CICLO.X_ETAPADEPENDEDE = ETAPAS.X_ETAPA  INNER JOIN DELPHOS.TLMATALU MATR ON MATR.X_MATRICULA = :idMatricula "
    		+ "INNER JOIN DELPHOS.TLCONVCENOMC CCO ON CCO.X_CONVCENTROOMC = :idConvCentroOmc INNER JOIN DELPHOS.TLMATOFEMATRG OMG ON OMG.X_OFERTAMATRIG = MATR.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLMATOFEMATRCEN MATOFE ON MATOFE.X_MATERIAOMG = OMG.X_MATERIAOMG AND MATOFE.X_OFERTAMATRIC = MATR.X_OFERTAMATRIC "
    		+ "INNER JOIN DELPHOS.TLCURSOORG CUO ON CUO.X_OFERTAMATRIG = MATR.X_OFERTAMATRIG  INNER JOIN DELPHOS.TLMATERIASCURSO MAT ON MAT.X_MATERIAC = OMG.X_MATERIAC AND MAT.X_CURSOMOD = CUO.X_CURSOMOD "
    		+ "INNER JOIN DELPHOS.TLPONDERACION POND ON POND.X_MATERIA = OMG.X_MATERIAOMG INNER JOIN DELPHOS.TLMATMATRIALU MATMATRI ON MATMATRI.X_MATRICULA = MATR.X_MATRICULA AND MATMATRI.X_MATERIAOMC = MATOFE.X_MATERIAOMC "
    		+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP RELDE ON RELDE.X_DESOPERATIVO = DESOP.X_DESOPERATIVO INNER JOIN DELPHOS.TLRELCOMPESMAT CEM ON CEM.X_MATERIAOMG = OMG.X_MATERIAOMG "
    		+ "INNER JOIN DELPHOS.TLCOMESP COMESP ON COMESP.X_COMESP = RELDE.X_COMESP AND COMESP.X_CICLO = CICLO.X_ETAPA AND COMESP.X_COMESP = CEM.X_COMESP LEFT JOIN TLVALCOMALU VAL ON VAL.X_COMESP = COMESP.X_COMESP "
    		+ "AND VAL.X_MATMATRICULA = MATMATRI.X_MATMATRICULA AND VAL.X_CONVCENTROOMC = CCO.X_CONVCENTROOMC AND VAL.X_PONDERACION = POND.X_PONDERACION LEFT JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VAL.X_CALIFICA "
    		+ "WHERE ETAPAS.X_ETAPA = :etapa AND COMESP.X_COMESP NOT IN (SELECT DISTINCT COMESP.X_COMESP IDCOMPETENCIAESP FROM TLVALCOMALU VAL INNER JOIN DELPHOS.TLCOMESP COMESP ON COMESP.X_COMESP = VAL.X_COMESP "
    		+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP RELDE ON RELDE.X_COMESP = COMESP.X_COMESP INNER JOIN DELPHOS.TLDESOPERATIVO DESOP ON DESOP.X_DESOPERATIVO = RELDE.X_DESOPERATIVO "
    		+ "INNER JOIN DELPHOS.TLCONVCENOMC CCO ON CCO.X_CONVCENTROOMC = VAL.X_CONVCENTROOMC INNER JOIN DELPHOS.TLETAPAS ETAPAS ON DESOP.X_ETAPA = ETAPAS.X_ETAPA INNER JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VAL.X_CALIFICA "
    		+ "INNER JOIN DELPHOS.TLMATALU MATR ON MATR.X_MATRICULA = :idMatricula INNER JOIN DELPHOS.TLMATOFEMATRG OMG ON OMG.X_OFERTAMATRIG = MATR.X_OFERTAMATRIG INNER JOIN DELPHOS.TLMATOFEMATRCEN MATOFE ON MATOFE.X_MATERIAOMG = OMG.X_MATERIAOMG "
    		+ "INNER JOIN DELPHOS.TLMATMATRIALU MATMATRI ON MATMATRI.X_MATERIAOMC = MATOFE.X_MATERIAOMC AND MATMATRI.X_MATRICULA = MATR.X_MATRICULA AND MATOFE.X_OFERTAMATRIC = MATR.X_OFERTAMATRIC "
    		+ "INNER JOIN DELPHOS.TLCURSOORG CUO ON CUO.X_OFERTAMATRIG = MATR.X_OFERTAMATRIG INNER JOIN DELPHOS.TLMATERIASCURSO MAT ON MAT.X_MATERIAC = OMG.X_MATERIAC AND MAT.X_CURSOMOD = CUO.X_CURSOMOD "
    		+ "WHERE ETAPAS.X_ETAPA = :etapa AND DESOP.X_DESOPERATIVO = :descripcionOperativa AND CCO.X_CONVCENTROOMC = :idConvCentroOmc AND VAL.X_MATMATRICULA = MATMATRI.X_MATMATRICULA)", nativeQuery = true)
    List<CompetenciaEspecificaProjection> getCompetenciasEspecificas(@Param("descripcionOperativa") Long descripcionOperativa,
    																	@Param("etapa") Long etapa,
    																	@Param("idMatricula") Long idMatricula,
    																	@Param("idConvCentroOmc") Long idConvCentroOmc);
    
    @Query(value = "SELECT dop.X_DESOPERATIVO idDescriptorOperativo, dop.T_ABREV nombreDescriptorOperativo, "
    		+ "vdo.X_CALIFICA idCalifica, cal.T_ABREV descCal, cal.D_CALIFICA descDetCal, cal.n_numero nota, cal.L_APRUEBA aprueba "
    		+ "FROM DELPHOS.TLDESOPERATIVO dop "
    		+ "LEFT JOIN DELPHOS.TLVALDESOPEALU vdo ON vdo.X_DESOPERATIVO = dop.X_DESOPERATIVO AND vdo.X_MATRICULA = :idMatricula AND vdo.X_CONVCENTROOMC = :idConvCentroOmc "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vdo.X_CALIFICA "
    		+ "WHERE dop.X_DESOPERATIVO = :idDescriptorOperativo AND dop.X_ETAPA = :idEtapa", nativeQuery = true)
    ValoracionDescriptorOperativoAlumnoProjection getValoracionDescriptorOperativoAlumno(@Param("idDescriptorOperativo") Long idDescriptorOperativo,
    																				@Param("idEtapa") Long idEtapa,
    																				@Param("idMatricula") Long idMatricula,
    																				@Param("idConvCentroOmc") Long idConvCentroOmc);
    
    @Query(value = "SELECT cal.X_CALIFICA idCalifica, cal.X_SISTCAL idSistCal, "
    		+ "cal.N_NUMERO nota, cal.D_CALIFICA descripcion, cal.T_ABREV descCal, cal.L_APRUEBA aprueba "
    		+ "FROM TLCALIFICACIONES cal "
    		+ "INNER JOIN TLRELSISETA sisEta ON sisEta.X_SISTCAL = cal.X_SISTCAL "
    		+ "WHERE sisEta.X_ETAPA = :idEtapa AND cal.N_NUMERO = :nota AND ROWNUM = 1", nativeQuery = true)
    SistemaCalificacionProjection getCalificacionSistemaByNota(@Param("idEtapa") Long idEtapa, @Param("nota") Long nota);
    
    @Query(value = "SELECT dop.X_DESOPERATIVO idDescriptorOperativo, dop.T_ABREV nombreDescriptorOperativo, "
    		+ "vdo.X_CALIFICA idCalifica, cal.T_ABREV descCal, cal.D_CALIFICA descDetCal, cal.n_numero nota, cal.L_APRUEBA aprueba "
    		+ "FROM DELPHOS.TLMATALU mua "
    		+ "INNER JOIN DELPHOS.TLMATALU oma ON oma.X_ALUMNO = mua.X_ALUMNO AND oma.C_ANNO < mua.C_ANNO "
    		+ "INNER JOIN DELPHOS.TLCONVCENTROS cce ON cce.X_CENTRO = oma.X_CENTRO AND cce.C_ANNO = oma.C_ANNO AND cce.X_CONVOGENERAL IS NOT NULL "
    		+ "INNER JOIN DELPHOS.TLCONVCENOMC cco ON cco.X_CONVCENTRO = cce.X_CONVCENTRO AND cco.X_OFERTAMATRIC = oma.X_OFERTAMATRIC  "
    		+ "INNER JOIN DELPHOS.TLDESOPERATIVO dop ON dop.X_DESOPERATIVO = :idDescriptorOperativo AND dop.X_ETAPA = :idEtapa "
    		+ "INNER JOIN DELPHOS.TLVALDESOPEALU vdo ON vdo.X_MATRICULA = oma.X_MATRICULA AND vdo.X_CONVCENTROOMC = cco.X_CONVCENTROOMC AND vdo.X_DESOPERATIVO = dop.X_DESOPERATIVO "
    		+ "INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vdo.X_CALIFICA "
    		+ "WHERE mua.X_MATRICULA = :idMatricula AND ROWNUM = 1 "
    		+ "ORDER BY oma.C_ANNO DESC", nativeQuery = true)
    ValoracionDescriptorOperativoAlumnoProjection getValoracionDescriptorOperativoAlumnoHistorico(@Param("idDescriptorOperativo") Long idDescriptorOperativo,
																					@Param("idEtapa") Long idEtapa,
																					@Param("idMatricula") Long idMatricula);
    
    @Query(value = "SELECT ccl.X_COMCLAVE idCompetenciaClave, ccl.T_ABREV nombreCompetenciaClave, "
    		+ "vcc.X_CALIFICA idCalifica, cal.T_ABREV descCal, cal.D_CALIFICA descDetCal, cal.n_numero nota, cal.L_APRUEBA aprueba "
    		+ "FROM DELPHOS.TLMATALU mua "
    		+ "INNER JOIN DELPHOS.TLMATALU oma ON oma.X_ALUMNO = mua.X_ALUMNO AND oma.C_ANNO < mua.C_ANNO "
    		+ "INNER JOIN DELPHOS.TLCONVCENTROS cce ON cce.X_CENTRO = oma.X_CENTRO AND cce.C_ANNO = oma.C_ANNO AND cce.X_CONVOGENERAL IS NOT NULL "
    		+ "INNER JOIN DELPHOS.TLCONVCENOMC cco ON cco.X_CONVCENTRO = cce.X_CONVCENTRO AND cco.X_OFERTAMATRIC = oma.X_OFERTAMATRIC  "
    		+ "INNER JOIN DELPHOS.TLCOMCLAVE ccl ON ccl.X_COMCLAVE = :idCompetenciaClave "
    		+ "INNER JOIN DELPHOS.TLVALCOMCLAALU vcc ON vcc.X_MATRICULA = oma.X_MATRICULA AND vcc.X_CONVCENTROOMC = cco.X_CONVCENTROOMC AND vcc.X_COMCLAVE = ccl.X_COMCLAVE  "
    		+ "INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcc.X_CALIFICA "
    		+ "WHERE mua.X_MATRICULA = :idMatricula AND ROWNUM = 1 "
    		+ "ORDER BY oma.C_ANNO DESC", nativeQuery = true)
    ValoracionCompetenciaClaveAlumnoProjection getValoracionCompetenciaClaveAlumnoHistorico(@Param("idCompetenciaClave") Long idCompetenciaClave, @Param("idMatricula") Long idMatricula);

    @Query(value = "select decode (totales.materias_matriculadas,con_notas.materias_evaluadas, 'true', 'false') todasEvaluadas "
    		+ "from "
    		//Número de materias matriculadas que tienen currículo
    		+ "(SELECT count(*) materias_matriculadas "
    		+ "  FROM DELPHOS.TLMATMATRIALU mat "
    		+ " where mat.x_estado IN (1, 3) and mat.X_MATRICULA =:idMatricula "
    		+ " and exists (select 1 from tlrelcompesmat rcm where rcm.x_materiaomg=mat.x_materiaomg)) totales, "
    		//Número de materias matriculadas que tienen currículo y alguna nota de competencia en la convocatoria pasada por parámetro
    		+ " (SELECT count(*) materias_evaluadas "
    		+ "  FROM DELPHOS.TLMATMATRIALU mat "
    		+ " where mat.x_estado IN (1, 3) and mat.X_MATRICULA = :idMatricula "
    		+ " and exists (select 1 from tlrelcompesmat rcm where rcm.x_materiaomg = mat.x_materiaomg) "
    		+ " and exists(select 1 from tlvalcomalu vc where vc.x_matmatricula = mat.x_matmatricula "
    		+ "  and vc.x_convcentroomc= :idConvCentroOmc) "
    		+ " ) con_notas ", nativeQuery = true)
    Boolean isTodasMateriasEvaluadas(@Param("idMatricula") Long idMatricula, @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Query(value = "SELECT matcurs.D_MATERIAC " +
			"FROM DELPHOS.TLMATMATRIALU mat " +
			"INNER JOIN TLMATOFEMATRG matofe ON matofe.X_MATERIAOMG = mat.X_MATERIAOMG " +
			"INNER JOIN TLMATERIASCURSO matcurs ON matcurs.X_MATERIAC = MATOFE.X_MATERIAC  " +
			"where mat.x_estado IN (1, 3) and mat.X_MATRICULA = :idMatricula " +
			"and exists (select 1 from tlrelcompesmat rcm where rcm.x_materiaomg = mat.x_materiaomg) " +
			"AND NOT exists(select 1 from tlvalcomalu vc where vc.x_matmatricula = mat.x_matmatricula " +
			"and vc.x_convcentroomc= :idConvCentroOmc)", nativeQuery = true)
	List<String> getNombreMateriasNoEvaluadas(@Param("idMatricula") Long idMatricula, @Param("idConvCentroOmc") Long idConvCentroOmc);

    @Query(value = "SELECT DECODE (count(t.X_OFERTAMATRIG), 0, 'NO', 'SI') " + 
    		"FROM TLOFEMATRGEN t, TLCURSOMODA cursomod, TLETAPAS etapa " + 
    		"WHERE ETAPA.S_ETAPA  LIKE '%Bach%' " + 
    		"AND CURSOMOD.X_ETAPA = :idEtapa " + 
    		"AND CURSOMOD.X_MODALIDAD = t.X_MODALIDAD " + 
    		"AND t.X_OFERTAMATRIG = :idOfertamatrig", nativeQuery = true)
    String getBachillerato(@Param("idEtapa") Long idEtapa, @Param("idOfertamatrig") Long idOfertamatrig);
    
    @Query(value = "SELECT vcomalu.X_VALCOMALU idComAlu, vcomalu.X_CALIFICA idCalifica, cal.N_NUMERO nota, cal.T_ABREV descCal, cal.L_APRUEBA aprueba "
    		+ "FROM TLVALCOMALU vcomalu "
    		+ "LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA "
    		+ "FULL OUTER JOIN EVA_VALCOMALUTEMP vcomat ON vcomat.X_COMESP = vcomalu.X_COMESP AND vcomat.X_MATMATRICULA = vcomalu.X_MATMATRICULA AND vcomat.X_PONDERACION = vcomalu.X_PONDERACION "
    		+ "WHERE vcomalu.X_MATMATRICULA = :idMatMatriAlu AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmc AND vcomalu.X_PONDERACION = :idPonderacion AND vcomat.X_VALCOMALUTEMP IS NULL", nativeQuery = true)
    List<NotaCompetenciaProjection> getNotasCompetenciasAlumnoSinCalculo(@Param("idMatMatriAlu") Long idMatMatriAlu, @Param("idConvCentroOmc") Long idConvCentroOmc, @Param("idPonderacion") Long idPonderacion);
    
    @Query(value = "SELECT vcrialu.X_VALCRIALU idCriAlu, vcrialu.X_CALIFICA idCalifica, cal.N_NUMERO nota, cal.T_ABREV descCal, cal.L_APRUEBA aprueba "
    		+ "FROM TLVALCRIALU vcrialu "
    		+ "LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = vcrialu.X_CALIFICA "
    		+ "FULL OUTER JOIN EVA_VALCRIALUTEMP vcriat ON vcriat.X_CRIEVA = vcrialu.X_CRIEVA AND vcriat.X_MATMATRICULA = vcrialu.X_MATMATRICULA AND vcriat.X_PONDERACION = vcrialu.X_PONDERACION "
    		+ "WHERE vcrialu.X_MATMATRICULA = :idMatMatriAlu AND vcrialu.X_CONVCENTROOMC = :idConvCentroOmc AND vcrialu.X_PONDERACION = :idPonderacion AND vcriat.X_VALCRIALUTEMP IS NULL", nativeQuery = true)
    List<NotaCriterioProjection> getNotasCriteriosAlumnoSinCalculo(@Param("idMatMatriAlu") Long idMatMatriAlu, @Param("idConvCentroOmc") Long idConvCentroOmc, @Param("idPonderacion") Long idPonderacion);
    
    @Query(value = "SELECT DISTINCT udc.X_UNIDAD idUnidad, udc.T_NOMBRE unidad, curso.X_ETAPA idCurso, ofg.X_OFERTAMATRIG idOfertamatrig, ofg.D_OFERTAMATRIG curso, "
    		+ "etapa.X_ETAPA idEtapa, etapa.X_ETAPA idEtapaSec, ciclo.X_ETAPA idCiclo, etapa.S_ETAPA etapa, etapa.n_orden ordenetapa, curso.n_orden ordencurso, omc.X_OFERTAMATRIC idOfertamatric, "
			+ "(SELECT COUNT(DISTINCT COM.X_COMESP) FROM TLVALCOMALU COM "
			+ "INNER JOIN TLMATMATRIALU MMA ON MMA.X_MATMATRICULA = COM.X_MATMATRICULA "
			+ "INNER JOIN TLMATALU MAT ON MAT.X_MATRICULA = MMA.X_MATRICULA AND MAT.X_UNIDAD = udc.X_UNIDAD) competenciasEvaluadas "
    		+ "FROM DELPHOS.TLUNIDADESCEN udc "
    		+ "INNER JOIN DELPHOS.TLOFERTASUNIDAD ou ON ou.X_UNIDAD = udc.X_UNIDAD "
    		+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = ou.X_OFERTAMATRIC "
    		+ "INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = ofg.X_MODALIDAD "
    		+ "INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
    		+ "INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA "
    		+ "INNER JOIN DELPHOS.TLETAPAS ciclo ON ciclo.X_ETAPA = curso.X_ETAPADEPENDEDE "
    		+ "INNER JOIN DELPHOS.TLETAPAS etapa ON etapa.X_ETAPA = ciclo.X_ETAPADEPENDEDE "
    		+ "WHERE udc.X_EMPLEADO IN (:idEmpleados) AND TO_CHAR(udc.F_TOMAPOS, 'DD/MM/YYYY') IN (:fechaTomaPosesion) "
    		+ "AND udc.X_CENTRO = :idCentro AND udc.C_ANNO = :anno "
    		+ "ORDER BY etapa.n_orden, curso.X_ETAPA, unidad", nativeQuery = true)
    List<UnidadEvaluacionProjection> getUnidadesEvaluacionCompClave(@Param("idEmpleados") List<Long> idEmpleados,
    																@Param("fechaTomaPosesion") List<String> fechaTomaPosesion,
    																@Param("idCentro") Long idCentro,
            														@Param("anno") Long anno);

	@Query(value = "SELECT DISTINCT udc.X_UNIDAD idUnidad, udc.T_NOMBRE unidad, curso.X_ETAPA idCurso, ofg.X_OFERTAMATRIG idOfertamatrig, ofg.D_OFERTAMATRIG curso,   " +
			"etapa.X_ETAPA idEtapa, etapa.X_ETAPA idEtapaSec, ciclo.X_ETAPA idCiclo, etapa.S_ETAPA etapa, etapa.n_orden ordenetapa, curso.n_orden ordencurso,   " +
			"omc.X_OFERTAMATRIC idOfertamatric, (SELECT COUNT(DISTINCT COM.X_COMESP) FROM TLVALCOMALU COM " +
			"INNER JOIN TLMATMATRIALU MMA ON MMA.X_MATMATRICULA = COM.X_MATMATRICULA " +
			"INNER JOIN TLMATALU MAT ON MAT.X_MATRICULA = MMA.X_MATRICULA AND MAT.X_UNIDAD = udc.X_UNIDAD) competenciasEvaluadas " +
			"FROM DELPHOS.TLUNIDADESCEN udc   " +
			"INNER JOIN DELPHOS.TLOFERTASUNIDAD ou ON ou.X_UNIDAD = udc.X_UNIDAD   " +
			"INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = ou.X_OFERTAMATRIC   " +
			"INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG   " +
			"INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG   " +
			"INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = ofg.X_MODALIDAD   " +
			"INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD   " +
			"INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA   " +
			"INNER JOIN DELPHOS.TLETAPAS ciclo ON ciclo.X_ETAPA = curso.X_ETAPADEPENDEDE   " +
			"INNER JOIN DELPHOS.TLETAPAS etapa ON etapa.X_ETAPA = ciclo.X_ETAPADEPENDEDE   " +
			"INNER JOIN DELPHOS.EVA_PROGDIDAC PROGDID ON PROGDID.X_OFERTAMATRIG = OFG.X_OFERTAMATRIG AND PROGDID.NU_ANNO = UDC.C_ANNO AND PROGDID.X_CENTRO = UDC.X_CENTRO   " +
			"WHERE udc.X_CENTRO = :idCentro AND udc.C_ANNO = :anno   " +
			"ORDER BY etapa.n_orden, curso.X_ETAPA, unidad", nativeQuery = true)
	List<UnidadEvaluacionProjection> getUnidadesEvaluacionCompClaveDirector(@Param("idCentro") Long idCentro, @Param("anno") Long anno);
    
    @Query(value = "SELECT ccl.X_COMCLAVE idCompetenciaClave, ccl.D_COMCLAVE descCompetenciaClave, ccl.T_ABREV abrevCompetenciaClave, "
    		+ "vccalt.X_CALIFICA idCalifica, cal.T_ABREV descCal, cal.n_numero nota, cal.L_APRUEBA aprueba, vccalt.X_MATRICULA idMatricula, vccalt.ID_VALCOMCLAALUTEMP idValComClaAluTemp "
    		+ "FROM DELPHOS.TLCOMCLAVE ccl "
    		+ "LEFT JOIN DELPHOS.EVA_VALCOMCLAALUTEMP vccalt ON vccalt.X_COMCLAVE = ccl.X_COMCLAVE AND vccalt.X_MATRICULA = :idMatricula "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vccalt.X_CALIFICA "
    		+ "ORDER BY ccl.N_ORDENPRES", nativeQuery = true)
    List<CompetenciaClaveAlumnoProjection> getCompetenciasClaveTemporalesAlumno(@Param("idMatricula") Long idMatricula);
    
    @Query(value = "SELECT dop.X_DESOPERATIVO idDescriptorOperativo, dop.D_DESOPERATIVO descDescriptorOperativo, dop.T_ABREV abrevDescriptorOperativo, dop.T_ABREV nombreDescriptorOperativo, "
    		+ "vdot.X_CALIFICA idCalifica, cal.T_ABREV descCal, cal.D_CALIFICA descDetCal, cal.n_numero nota, cal.L_APRUEBA aprueba, vdot.ID_VALDESOPEALUTEMP idValDesOpeAluTemp "
    		+ "FROM DELPHOS.TLDESOPERATIVO dop "
    		+ "LEFT JOIN DELPHOS.EVA_VALDESOPEALUTEMP vdot ON vdot.X_DESOPERATIVO = dop.X_DESOPERATIVO AND vdot.X_MATRICULA = :idMatricula  "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vdot.X_CALIFICA "
    		+ "WHERE dop.X_COMCLAVE = :idCompetenciaClave AND dop.X_ETAPA = :idEtapa "
    		+ "ORDER BY dop.N_ORDENPRES", nativeQuery = true)
    List<DescriptorOperativoAlumnoProjection> getDescriptoresOperativosTemporalesAlumno(@Param("idCompetenciaClave") Long idCompetenciaClave,
																						@Param("idMatricula") Long idMatricula,
																						@Param("idEtapa") Long idEtapa);

	@Query(value = "SELECT actcri.ID_ACTIVIDAD idActividad, uniProg.TX_NOMBRE nombreUnidad , uniprog.TX_ABREV abrevUnidad , act.TX_NOMBRE NombreActividad, act.TX_ABREV abrevAct, cal.T_ABREV descCal, " +
			"convCen.D_CONVOCATORIA convocatoria, actCri.NU_PESO Porcentaje " +
			"FROM EVA_ACTIVIDAD act " +
			"INNER JOIN EVA_UNIDADPROG uniProg ON uniProg.ID_UNIDADPROG = act.ID_UNIDADPROG " +
			"INNER JOIN TLCONVCENOMC convCenOmc ON convCenOmc.X_CONVCENTROOMC = UNIPROG.X_CONVCENTROOMC " +
			"INNER JOIN TLCONVCENTROS convCen ON convCen.X_CONVCENTRO = convCenOmc.X_CONVCENTRO " +
			"INNER JOIN EVA_RELACTCRIEVA actCri ON actcri.ID_ACTIVIDAD = act.ID_ACTIVIDAD    " +
			"INNER JOIN EVA_RELACTALUM actAlu ON ACTALU.ID_ACTIVIDAD = actcri.ID_ACTIVIDAD    " +
			"INNER JOIN TLCRIEVA cri ON cri.X_CRIEVA = ACTCRI.X_CRIEVA    " +
			"LEFT JOIN EVA_VALCRIACTALU criAlu ON CRIALU.ID_RELACTCRIEVA = ACTCRI.ID_RELACTCRIEVA AND CRIALU.ID_RELACTALUM = ACTALU.ID_RELACTALUM    " +
			"LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = criAlu.X_CALIFICA " +
			"INNER JOIN TLRELPONCRIEVA relPonCri ON relPonCri.X_CRIEVA = actCri.X_CRIEVA " +
			"WHERE actAlu.X_MATRICULA = :idMatriAlu  " +
			"AND actCri.X_CRIEVA = :idCriterio " +
			"AND relPonCri.X_PONDERACION = :idPonderacion " +
			"AND criAlu.X_CALIFICA IS NOT NULL " +
			"ORDER BY convCen.N_ORDEN,act.ID_UNIDADPROG ,act.NU_ORDENPRES", nativeQuery = true)
	List<ActividadCriterioProjection> getActividadesByCriterio(@Param("idMatriAlu") Long idMatriAlu,
															   @Param("idCriterio") Long idCriterio,
															   @Param("idPonderacion") Long idPonderacion);
	
	@Query(value = "SELECT ofg.X_OFERTAMATRIG idOfertamatrig, ofg.S_OFERTAMATRIG descripcionCorta "
			+ "FROM DELPHOS.TLOFEMATRGEN ofg "
			+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLPERIODOSOMC per ON per.X_OFERTAMATRIC = omc.X_OFERTAMATRIC "
			+ "WHERE omc.X_CENTRO = :idCentro AND per.C_ANNO <= :anno  "
			+ "AND (per.C_ANNOPUEDETERMINAR IS NULL OR per.C_ANNOPUEDETERMINAR >= :anno) "
			+ "AND ((SELECT count(pd.X_MATERIAOMG) "
			+ "FROM DELPHOS.EVA_PROGDIDAC pd "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU grua ON grua.X_CENTRO = pd.X_CENTRO AND grua.C_ANNO = pd.NU_ANNO "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_GRUACTPROALU = grua.X_GRUACTPROALU AND UNIGRUACT.X_MATERIAOMG = pd.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLMATOFEMATRG matofer ON matofer.X_MATERIAOMG = pd.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLMATERIASCURSO matcurse ON matofer.X_MATERIAC  = matcurse.X_MATERIAC "
			+ "WHERE pd.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG AND pd.NU_ANNO = :anno AND pd.X_CENTRO = :idCentro) > 0) "
			+ "ORDER BY ofg.N_ORDENPRES", nativeQuery = true)
	List<CursoValoracionProjection> getCursosValoracionByCentroAndAnno(@Param("idCentro") Long idCentro, @Param("anno") Integer anno);

	@Query(value = "SELECT DISTINCT mog.X_MATERIAOMG idMateria, ciclo.X_ETAPADEPENDEDE idEtapa, " +
			"matc.T_ABREV || ' - ' || matc.D_MATERIAC materia, mog.x_ofertamatrig idOfertaMatrig, omc.X_OFERTAMATRIC idOfertaMatric, " +
			"nvl((SELECT PROGDIDAC.lg_cerrada FROM DELPHOS.EVA_PROGDIDAC PROGDIDAC " +
			"WHERE PROGDIDAC.X_CENTRO = pd.X_CENTRO AND PROGDIDAC.NU_ANNO = pd.NU_ANNO AND PROGDIDAC.X_MATERIAOMG = pd.X_MATERIAOMG " +
			"AND PROGDIDAC.X_OFERTAMATRIG = pd.X_OFERTAMATRIG AND PROGDIDAC.lg_cerrada = 1 AND ROWNUM <= 1), 0) cerrada,  mog.N_ORDENPRES, " +
			"(SELECT count(*) FROM EVA_PROGAULA aula " +
			"INNER JOIN EVA_PROGDIDAC ep " +
			"ON ep.X_OFERTAMATRIG = pd.X_OFERTAMATRIG " +
			"AND ep.X_MATERIAOMG = pd.X_MATERIAOMG " +
			"AND ep.NU_ANNO = pd.NU_ANNO " +
			"AND ep.X_CENTRO = pd.X_CENTRO " +
			"WHERE aula.ID_PROGDIDAC = ep.ID_PROGDIDAC) progAula, " +
			"decode(mog.x_ofertamatrig, mua.x_ofertamatrig, 'false', 'true') pendiente, " +
			"NVL2(pd.ID_PROGDIDAC, 'true', 'false') hayProgDidac " +
			"FROM DELPHOS.TLMATALU mua " +
			"INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATRICULA = mua.X_MATRICULA " +
			"INNER JOIN DELPHOS.TLMATOFEMATRG mog ON mog.X_MATERIAOMG = mma.X_MATERIAOMG " +
			"INNER JOIN DELPHOS.TLMATERIASCURSO matc ON matc.X_MATERIAC = mog.X_MATERIAC " +
			"INNER JOIN DELPHOS.TLCURSOMODA modalidad ON matc.x_cursomod = modalidad.x_cursomod " +
			"INNER JOIN DELPHOS.TLETAPAS curso ON curso.x_etapa = modalidad.x_etapa " +
			"INNER JOIN DELPHOS.TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA " +
			"INNER JOIN DELPHOS.TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA " +
			"INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = mua.X_OFERTAMATRIG " +
			"INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG AND omc.X_CENTRO = mua.X_CENTRO " +
			"LEFT JOIN DELPHOS.EVA_PROGDIDAC pd ON pd.X_MATERIAOMG = mog.X_MATERIAOMG AND pd.X_OFERTAMATRIG = mog.X_OFERTAMATRIG AND pd.X_CENTRO = omc.X_CENTRO " +
			"LEFT JOIN DELPHOS.EVA_PROGDIDAC pd ON pd.X_MATERIAOMG = mog.X_MATERIAOMG AND pd.X_OFERTAMATRIG = mog.X_OFERTAMATRIG AND pd.X_CENTRO = omc.X_CENTRO AND pd.NU_ANNO = mua.C_ANNO  " +
			"WHERE mma.X_ESTADO IN (1, 3) AND mua.C_ANNO = :anno AND omc.X_CENTRO = :idCentro AND ofg.X_OFERTAMATRIG = :idOfertamatrig " +
			"ORDER BY idOfertaMatrig DESC, materia", nativeQuery = true)
	List<MateriasValoracionProjection> getMateriasValoracionCurso(@Param("anno") Long anno, @Param("idCentro") Long idCentro, @Param("idOfertamatrig") Long idOfertamatrig);
	
	@Query(value = "SELECT mua.X_MATRICULA idMatricula, alu.X_ALUMNO idAlumno, "
    		+ "DECODE(etapa.S_ETAPA, 'Bachillerato', ciclo.X_ETAPA, etapa.X_ETAPA) idEtapa, cco.X_CONVCENTROOMC idConvCentroOmc, alu.C_NUMESCOLAR numEscolar, "
    		+ "DELPHOS.TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) nombreAlumno, "
    		+ "etapa.S_ETAPA nombreEtapa, cce.S_CONVOCATORIA nombreConvocatoria, "
			+ "CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) THEN 1 ELSE 0 END AS acnee, "
			+ "(SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) nivelCurricular, "
			+ "(SELECT DECODE(etapa2.S_ETAPA, 'Bachillerato', ciclo2.X_ETAPA, etapa2.X_ETAPA) FROM DELPHOS.TLOFEMATRGEN omg2  "
			+ "INNER JOIN DELPHOS.TLCURSOORG cuo2 ON cuo2.X_OFERTAMATRIG = omg2.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLMODALIDADES modl2 ON modl2.X_MODALIDAD = omg2.X_MODALIDAD "
			+ "INNER JOIN DELPHOS.TLCURSOMODA curModa2 ON curModa2.X_MODALIDAD = modl2.X_MODALIDAD AND curModa2.X_CURSOMOD = cuo2.X_CURSOMOD "
			+ "INNER JOIN DELPHOS.TLETAPAS curso2 ON curso2.X_ETAPA = curModa2.X_ETAPA "
			+ "INNER JOIN DELPHOS.TLETAPAS ciclo2 ON ciclo2.X_ETAPA = curso2.X_ETAPADEPENDEDE "
			+ "INNER JOIN DELPHOS.TLETAPAS etapa2 ON etapa2.X_ETAPA = ciclo2.X_ETAPADEPENDEDE "
			+ "WHERE omg2.X_OFERTAMATRIG = mua.X_NIVEADAP AND mua.X_NIVEADAP <> mua.X_OFERTAMATRIG AND mua.X_NIVEADAP IS NOT NULL) idEtapaAdaptacion "
    		+ "FROM DELPHOS.TLMATALU mua "
    		+ "INNER JOIN DELPHOS.TLCONVCENTROS cce ON cce.X_CONVCENTRO = :idConvocatoria AND cce.C_ANNO = mua.C_ANNO AND cce.X_CENTRO = mua.X_CENTRO "
    		+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = mua.X_OFERTAMATRIC AND omc.X_OFERTAMATRIG = mua.X_OFERTAMATRIG AND omc.X_CENTRO = cce.X_CENTRO "
    		+ "INNER JOIN DELPHOS.TLCONVCENOMC cco ON cco.X_OFERTAMATRIC = omc.X_OFERTAMATRIC AND cco.X_CONVCENTRO = cce.X_CONVCENTRO "
    		+ "INNER JOIN DELPHOS.TLALUMNOS alu ON alu.X_ALUMNO = mua.X_ALUMNO "
    		+ "INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = omg.X_MODALIDAD "
    		+ "INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
    		+ "INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA "
    		+ "INNER JOIN DELPHOS.TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA "
    		+ "INNER JOIN DELPHOS.TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA "
    		+ "WHERE mua.X_UNIDAD = :idUnidad "
			+ "AND omg.X_OFERTAMATRIG = :idOfertamatrig "
    		+ "AND NVL(mua.C_RESULTADO, 99) > 1 "
			+ "AND (cce.X_CONVOCATORIA <> 2 OR cce.X_CONVOCATORIA = 2 AND (mua.F_PROMOCION IS NULL "
			+ "OR mua.F_PROMOCION <> (SELECT cu.F_SESION FROM DELPHOS.TLCONVUNIDAD cu "
			+ "INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTROOMC = cu.X_CONVCENTROOMC "
			+ "INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CONVCENTRO = cco2.X_CONVCENTRO "
			+ "WHERE cu.X_UNIDAD = :idUnidad AND cce2.X_CONVOCATORIA = 1)))  "
    		+ "ORDER BY nombreAlumno", nativeQuery = true)
    List<AlumnoValoracionProjection> getAlumnosValoracionByUnidadAndConvocatoriaAndCurso(@Param("idUnidad") Long idUnidad, @Param("idConvocatoria") Long idConvocatoria, @Param("idOfertamatrig") Long idOfertamatrig);
	
	@Query(value = "SELECT DISTINCT matMatricula.idMatMatricula FROM "
			+ "(SELECT mma.X_MATMATRICULA idMatMatricula "
			+ "FROM DELPHOS.TLDESOPERATIVO dop "
			+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO "
			+ "INNER JOIN DELPHOS.TLRELCOMPESMAT rcem ON rcem.X_COMESP = rdoce.X_COMESP "
			+ "INNER JOIN DELPHOS.TLMATOFEMATRG mogmatr ON mogmatr.X_MATERIAOMG = rcem.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLMATERIASCURSO mcmatr ON mcmatr.X_MATERIAC = mogmatr.X_MATERIAC "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATERIAOMG = mogmatr.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLMATALU mua ON mua.X_MATRICULA = mma.X_MATRICULA "
			+ "INNER JOIN DELPHOS.TLMATERIASCURSO mcadap ON mcadap.X_MATERIAG = mcmatr.X_MATERIAG AND mcadap.X_CURSOMOD = mua.X_NIVEADAP "
			+ "INNER JOIN DELPHOS.TLMATOFEMATRG mogadap ON mogadap.X_MATERIAC = mogadap.X_MATERIAC AND mogadap.X_GRUPOMAT = mogmatr.X_GRUPOMAT "
			+ "WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mua.X_MATRICULA = :idMatricula AND mma.X_ADAPTACION IS NOT NULL AND mua.X_NIVEADAP <> mua.X_OFERTAMATRIG AND mcadap.C_ANNOHASTA IS NULL "
			+ "UNION ALL "
			+ "SELECT mma2.X_MATMATRICULA idMatMatricula "
			+ "FROM DELPHOS.TLDESOPERATIVO dop2 "
			+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce2 ON rdoce2.X_DESOPERATIVO = dop2.X_DESOPERATIVO "
			+ "INNER JOIN DELPHOS.TLRELCOMPESMAT rcem2 ON rcem2.X_COMESP = rdoce2.X_COMESP "
			+ "INNER JOIN DELPHOS.TLMATOFEMATRG mog ON mog.X_MATERIAOMG = rcem2.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma2 ON mma2.X_MATERIAOMG = mog.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLMATALU mua2 ON mua2.X_MATRICULA = mma2.X_MATRICULA "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULALUM rpaa ON rpaa.X_MATRICULA = mua2.X_MATRICULA "
			+ "INNER JOIN DELPHOS.EVA_PROGAULA pa ON pa.ID_PROGAULA = rpaa.ID_PROGAULA "
			+ "INNER JOIN DELPHOS.EVA_PROGDIDAC pd ON pd.ID_PROGDIDAC = pa.ID_PROGDIDAC AND pd.X_MATERIAOMG = mog.X_MATERIAOMG AND pd.X_OFERTAMATRIG = mua2.X_OFERTAMATRIG AND pd.X_CENTRO = mua2.X_CENTRO AND pd.NU_ANNO = mua2.C_ANNO AND pd.X_NIVEADAP = mua2.X_NIVEADAP "
			+ "WHERE rdoce2.X_DESOPERATIVO = :idDescriptorOperativo AND mua2.X_MATRICULA = :idMatricula AND mma2.X_ADAPTACION IS NOT NULL AND mua2.X_NIVEADAP <> mua2.X_OFERTAMATRIG AND pd.LG_ACNEAE = 1) matMatricula", nativeQuery = true)
	List<Long> getIdsMatMatriculaACNEEByIdDescriptorOperativoAndIdMatricula(@Param("idDescriptorOperativo") Long idDescriptorOperativo, @Param("idMatricula") Long idMatricula);

	@Query(value = "SELECT ROUND(AVG(NVL(cal.N_NUMERO, cal.N_ORDEN))) notaMedia "
			+ "FROM DELPHOS.TLDESOPERATIVO dop "
			+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO "
			+ "INNER JOIN DELPHOS.TLVALCOMALU vcomalu ON vcomalu.X_COMESP = rdoce.X_COMESP "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalu.X_MATMATRICULA "
			+ "INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA "
			+ "INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA "
			+ "WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATRICULA = :idMatricula AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
	Integer getNotaMediaRedondeadaByIdDescriptorOperativoAndIdMatriculaAndIdConvCentroOmc(@Param("idDescriptorOperativo") Long idDescriptorOperativo, @Param("idMatricula") Long idMatricula, @Param("idConvCentroOmc") Long idConvCentroOmc);

	@Query(value = "SELECT DISTINCT uni.X_UNIDAD idUnidad "
			+ "FROM DELPHOS.TLCONVCENOMC cco "
			+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = cco.X_OFERTAMATRIC "
			+ "INNER JOIN DELPHOS.TLCONVUNIDAD cu ON cu.X_CONVCENTROOMC = cco.X_CONVCENTROOMC "
			+ "INNER JOIN DELPHOS.TLUNIDADESCEN uni ON uni.X_UNIDAD = cu.X_UNIDAD "
			+ "WHERE omc.X_OFERTAMATRIG IN (:idsOfertamatrigs) and uni.C_ANNO = :anno AND uni.X_CENTRO = :idCentro", nativeQuery = true)
	List<Long> getIdsUnidadesByIdCentroAndIdsOfertamatrigs(@Param("idCentro") Long idCentro, @Param("anno") Integer anno, @Param("idsOfertamatrigs") List<Long> idsOfertamatrigs);

	@Query(value = "SELECT conv.X_CONVCENTRO idConvCentro "
			+ "FROM TLCONVCENTROS conv "
			+ "INNER JOIN TLCONVCENOMC convCen ON convCen.X_CONVCENTRO = conv.X_CONVCENTRO "
			+ "INNER JOIN TLCONVUNIDAD convUni ON convUni.X_CONVCENTROOMC = convCen.X_CONVCENTROOMC "
			+ "WHERE conv.x_convocatoria = 1 AND convUni.x_unidad = :idUnidad AND ROWNUM = 1", nativeQuery = true)
	Long getIdConvocatoriaByIdUnidad(@Param("idUnidad") Long idUnidad);

	@Query(value = "SELECT DISTINCT * FROM (SELECT DISTINCT ALU.X_ALUMNO idAlumno, mat.X_MATRICULA idMatricula, MATMATRI.x_matmatricula idMatMatriAlu, "
			+ "alu.c_numescolar numEscolar, alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos "
			+ "FROM TLALUMNOS ALU "
			+ "INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO "
			+ "INNER JOIN TLMATMATRIALU MATMATRI ON MATMATRI.X_MATRICULA = MAT.X_MATRICULA "
			+ "INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = MAT.X_UNIDAD AND UNICEN.X_CENTRO = MAT.X_CENTRO AND UNICEN.C_ANNO = MAT.C_ANNO AND ((UNICEN.X_EMPLEADO IN (:idsEmpleado) AND :tutor =1) OR :tutor = 0 OR :direccion = 1) "
			+ "INNER JOIN TLUNIAFEGRUACTPRO UAG ON UAG.X_UNIDAD = UNICEN.X_UNIDAD AND UAG.X_MATERIAOMG = MATMATRI.X_MATERIAOMG AND UAG.L_AFECTATODOS='S' "
			+ "INNER JOIN TLGRUACTPROALU GRUACT ON GRUACT.X_GRUACTPROALU = UAG.X_GRUACTPROALU AND GRUACT.X_CENTRO = UNICEN.X_CENTRO AND GRUACT.C_ANNO = UNICEN.C_ANNO AND ((GRUACT.X_EMPLEADO IN (:idsEmpleado) and TO_CHAR(GRUACT.f_tomapos, 'DD/MM/YYYY') IN (:fechasTomaPosesion) AND :tutor = 0) OR :tutor = 1 OR :direccion = 1) "
			+ "INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.X_CENTRO = GRUACT.X_CENTRO AND PROGDIDAC.X_OFERTAMATRIG = MAT.X_OFERTAMATRIG AND PROGDIDAC.X_MATERIAOMG = UAG.X_MATERIAOMG AND PROGDIDAC.NU_ANNO = GRUACT.C_ANNO "
			+ "WHERE MATMATRI.X_ADAPTACION IS NOT NULL AND PROGDIDAC.X_NIVEADAP = MAT.X_NIVEADAP AND PROGDIDAC.X_MATERIAOMG = :idMateriaOmg AND PROGDIDAC.X_OFERTAMATRIG = :idOfertamatrig AND PROGDIDAC.X_CENTRO = :idCentro AND PROGDIDAC.NU_ANNO = :anno "
			+ "AND NOT EXISTS(SELECT * FROM EVA_PROGAULA progAula "
			+ "INNER JOIN EVA_RELPROGAULALUM rpaa ON rpaa.ID_PROGAULA = progAula.ID_PROGAULA "
			+ "WHERE progAula.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC AND rpaa.X_MATRICULA = MAT.X_MATRICULA AND rpaa.X_UNIDAD = UNICEN.X_UNIDAD) "
			+ "UNION ALL "
			+ "SELECT DISTINCT ALU.X_ALUMNO idAlumno, mat.X_MATRICULA idMatricula, MATMATRI.x_matmatricula idMatMatriAlu, "
			+ "alu.c_numescolar numEscolar, alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos "
			+ "FROM TLALUMNOS ALU "
			+ "INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO "
			+ "INNER JOIN TLMATMATRIALU MATMATRI ON MATMATRI.X_MATRICULA = MAT.X_MATRICULA "
			+ "INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = MAT.X_UNIDAD AND UNICEN.X_CENTRO = MAT.X_CENTRO AND UNICEN.C_ANNO = MAT.C_ANNO AND ((UNICEN.X_EMPLEADO IN (:idsEmpleado) AND :tutor =1) OR :tutor = 0 OR :direccion = 1) "
			+ "INNER JOIN TLALUENDEPEN ADP ON ADP.X_MATRICULA = MAT.X_MATRICULA AND ADP.X_UNIDAD = UNICEN.X_UNIDAD "
			+ "INNER JOIN TLHORARIOSR HR ON HR.X_HORARIORE = ADP.X_HORARIORE AND HR.C_ANNO = UNICEN.C_ANNO AND ((HR.X_EMPLEADO IN (:idsEmpleado) and TO_CHAR(HR.f_tomapos, 'DD/MM/YYYY') IN (:fechasTomaPosesion) AND :tutor = 0) OR :tutor = 1 OR :direccion = 1) "
			+ "INNER JOIN TLUNIAFETRAHOR UNH ON UNH.X_UNIDAD=UNICEN.X_UNIDAD AND UNH.X_HORARIORE = HR.X_HORARIORE AND UNH.X_MATERIAOMG = MATMATRI.X_MATERIAOMG "
			+ "INNER JOIN TLUNIAFEGRUACTPRO UAG ON UAG.X_UNIDAD = UNH.X_UNIDAD AND UAG.X_MATERIAOMG = UNH.X_MATERIAOMG AND UAG.L_AFECTATODOS='N' "
			+ "INNER JOIN TLGRUACTPROALU GRUACT ON GRUACT.X_GRUACTPROALU = UAG.X_GRUACTPROALU AND GRUACT.X_CENTRO = UNICEN.X_CENTRO AND GRUACT.C_ANNO = HR.C_ANNO AND GRUACT.X_EMPLEADO = HR.X_EMPLEADO AND GRUACT.F_TOMAPOS = HR.F_TOMAPOS "
			+ "INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.X_CENTRO = GRUACT.X_CENTRO AND PROGDIDAC.X_OFERTAMATRIG = MAT.X_OFERTAMATRIG AND PROGDIDAC.X_MATERIAOMG = UAG.X_MATERIAOMG AND PROGDIDAC.NU_ANNO = GRUACT.C_ANNO "
			+ "WHERE MATMATRI.X_ADAPTACION IS NOT NULL AND PROGDIDAC.X_NIVEADAP = MAT.X_NIVEADAP AND PROGDIDAC.X_MATERIAOMG = :idMateriaOmg AND PROGDIDAC.X_OFERTAMATRIG = :idOfertamatrig AND PROGDIDAC.X_CENTRO = :idCentro AND PROGDIDAC.NU_ANNO = :anno "
			+ "AND NOT EXISTS(SELECT * FROM EVA_PROGAULA progAula "
			+ "INNER JOIN EVA_RELPROGAULALUM rpaa ON rpaa.ID_PROGAULA = progAula.ID_PROGAULA "
			+ "WHERE progAula.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC AND rpaa.X_MATRICULA = MAT.X_MATRICULA AND rpaa.X_UNIDAD = UNICEN.X_UNIDAD))", nativeQuery = true)
	List<AlumnoEvaluacionProjection> getAlumnosACNEESinProgramacionAula(@Param("idsEmpleado") List<Long> idsEmpleado,
			  @Param("fechasTomaPosesion") List<String> fechasTomaPosesion,
			  @Param("idOfertamatrig") Long idOfertamatrig,
			  @Param("idMateriaOmg") Long idMateriaOmg,
			  @Param("idCentro") Long idCentro,
			  @Param("anno") Integer anno,
			  @Param("tutor") Long tutor,
			  @Param("direccion") Long direccion);
	
	@Query(value = "SELECT DECODE(etapa.S_ETAPA, 'Bachillerato', ciclo.X_ETAPA, etapa.X_ETAPA) "
			+ "FROM DELPHOS.TLMATALU mua "
			+ "INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = mua.X_NIVEADAP "
			+ "INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLMODALIDADES modalidad ON modalidad.X_MODALIDAD = ofg.X_MODALIDAD "
			+ "INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidad.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
			+ "INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA "
			+ "INNER JOIN DELPHOS.TLETAPAS ciclo ON ciclo.X_ETAPA = curso.X_ETAPADEPENDEDE  "
			+ "INNER JOIN DELPHOS.TLETAPAS etapa ON etapa.X_ETAPA = ciclo.X_ETAPADEPENDEDE "
			+ "WHERE ofg.X_OFERTAMATRIG = mua.X_NIVEADAP AND mua.X_NIVEADAP <> mua.X_OFERTAMATRIG AND mua.X_NIVEADAP IS NOT NULL AND mua.X_MATRICULA = :idMatricula", nativeQuery = true)
	Long getIdEtapaAdapatacionAlumnoACNEE(@Param("idMatricula") Long idMatricula);
	
	@Query(value = "SELECT ROUND(AVG(NVL(nota.numero, nota.orden))) notaMedia FROM "
			+ "(SELECT cal.N_NUMERO numero, cal.N_ORDEN orden "
			+ "FROM DELPHOS.TLDESOPERATIVO dop "
			+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO "
			+ "INNER JOIN DELPHOS.TLVALCOMALU vcomalu ON vcomalu.X_COMESP = rdoce.X_COMESP "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalu.X_MATMATRICULA "
			+ "INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA "
			+ "INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA "
			+ "WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATRICULA = :idMatricula AND mma.X_MATMATRICULA NOT IN (:idsMatMatriculaACNEE) AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmc "
			+ "UNION ALL "
			+ "SELECT CASE WHEN cal.N_ORDEN > 2 AND siseta.X_ETAPA = :idEtapaAdaptacion THEN (SELECT cal2.N_NUMERO FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) ELSE (SELECT cal2.N_NUMERO FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) END numero, "
			+ "CASE WHEN cal.N_ORDEN > 2 AND siseta.X_ETAPA = :idEtapaAdaptacion THEN (SELECT cal2.N_ORDEN FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) ELSE (SELECT cal2.N_ORDEN FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) END orden "
			+ "FROM DELPHOS.TLDESOPERATIVO dop "
			+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO "
			+ "INNER JOIN DELPHOS.TLVALCOMALU vcomalu ON vcomalu.X_COMESP = rdoce.X_COMESP "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalu.X_MATMATRICULA "
			+ "INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA "
			+ "INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA "
			+ "WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATMATRICULA IN (:idsMatMatriculaACNEE) AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmc) nota", nativeQuery = true)
	Integer getNotaMediaRedondeadaACNEEByIdDescriptorOperativoAndIdMatriculaAndIdConvCentroOmcAndIdEtapaAdaptacionAndIdsMatMatriculaACNEE(@Param("idDescriptorOperativo") Long idDescriptorOperativo, @Param("idMatricula") Long idMatricula, @Param("idConvCentroOmc") Long idConvCentroOmc, @Param("idEtapaAdaptacion") Long idEtapaAdaptacion, @Param("idsMatMatriculaACNEE") Long[] idsMatMatriculaACNEE);

	@Query(value = "SELECT DISTINCT (MATCUR.T_ABREV || ' - ' ||MATCUR.D_MATERIAC) nombreMateria  " +
			"FROM EVA_PROGDIDAC PD " +
			"INNER JOIN TLMATALU MAT ON MAT.X_UNIDAD = :idUnidad AND MAT.X_OFERTAMATRIG = PD.X_OFERTAMATRIG AND MAT.X_CENTRO = PD.X_CENTRO AND MAT.C_ANNO = PD.NU_ANNO  " +
			"INNER JOIN TLMATMATRIALU MMA ON MMA.X_MATRICULA = MAT.X_MATRICULA AND PD.X_MATERIAOMG = MMA.X_MATERIAOMG " +
			"INNER JOIN TLMATOFEMATRG MATOFE ON MATOFE.X_MATERIAOMG = PD.X_MATERIAOMG      " +
			"INNER JOIN TLMATERIASCURSO MATCUR ON MATCUR.X_MATERIAC = MATOFE.X_MATERIAC        " +
			"AND PD.ID_PROGDIDAC NOT IN (SELECT DISTINCT PD2.ID_PROGDIDAC  " +
			"FROM EVA_PROGDIDAC PD2 " +
			"INNER JOIN TLMATALU MAT ON MAT.X_UNIDAD = :idUnidad   " +
			"INNER JOIN TLMATMATRIALU MMA ON MMA.X_MATRICULA = MAT.X_MATRICULA  " +
			"INNER JOIN EVA_RELPROGDIDPOND RPP ON RPP.ID_PROGDIDAC = PD2.ID_PROGDIDAC  " +
			"INNER JOIN TLRELPONCOMESP RPC ON RPC.X_PONDERACION = RPP.X_PONDERACION AND EXISTS (SELECT 1 FROM TLVALCOMALU COM WHERE RPC.X_COMESP = COM.X_COMESP AND COM.X_MATMATRICULA = MMA.X_MATMATRICULA AND COM.X_CONVCENTROOMC = :idConvCentroOmc) " +
			"WHERE MAT.X_OFERTAMATRIG = PD2.X_OFERTAMATRIG AND MAT.X_CENTRO = PD2.X_CENTRO AND MAT.C_ANNO = PD2.NU_ANNO AND PD2.X_MATERIAOMG = MMA.X_MATERIAOMG)", nativeQuery = true)
	List<String> getMateriasNoEvaluadasCalculoCompetenciaFinal(@Param("idUnidad") Long idUnidad, @Param("idConvCentroOmc") Long idConvCentroOmc);

	@Query(value = "SELECT DISTINCT udc.x_unidad idUnidad, udc.T_NOMBRE unidad, " +
			"(SELECT count(DISTINCT aula.ID_PROGAULA) FROM EVA_PROGAULA aula " +
			"INNER JOIN DELPHOS.EVA_RELPROGAULALUM rprogaulaalu ON rprogaulaalu.X_UNIDAD = udc.X_UNIDAD " +
			"INNER JOIN DELPHOS.EVA_PROGAULA pa ON pa.ID_PROGAULA = rprogaulaalu.ID_PROGAULA AND pa.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC  " +
			"WHERE aula.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC) progAula " +
			"FROM DELPHOS.TLMATALU mua " +
			"INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATRICULA = mua.X_MATRICULA " +
			"INNER JOIN DELPHOS.TLUNIDADESCEN udc ON udc.X_UNIDAD = mua.X_UNIDAD AND udc.X_CENTRO = mua.X_CENTRO " +
			"INNER JOIN DELPHOS.EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.X_MATERIAOMG = mma.X_MATERIAOMG " +
			"AND PROGDIDAC.NU_ANNO = udc.C_ANNO " +
			"AND PROGDIDAC.X_CENTRO = udc.X_CENTRO " +
			"WHERE mua.X_OFERTAMATRIG = :idOfertamatrig AND mma.X_MATERIAOMG = :idMateria AND udc.X_CENTRO = :idCentro AND udc.C_ANNO = :anno " +
			"AND (:direccion = 0 AND :tutor = 1 AND udc.X_EMPLEADO = :idEmpleado OR :direccion = 1) " +
			"ORDER BY udc.T_NOMBRE", nativeQuery = true)
	List<UnidadesValoracionProjection> getUnidadesValoracionPendiente(@Param("anno") Integer anno,
																			   @Param("idOfertamatrig") Long idOfertamatrig,
																			   @Param("idMateria") Long idMateria,
																			   @Param("idCentro") Long idCentro,
																	           @Param("idEmpleado") Long idEmpleado,
																	           @Param("tutor") Long tutor,
																	           @Param("direccion") Long direccion);

	@Query(value = "SELECT DISTINCT ALU.X_ALUMNO idAlumno, mat.X_MATRICULA idMatricula, MATMATRI.x_matmatricula idMatMatriAlu,  " +
			" alu.c_numescolar numEscolar, alu.t_nombre nombre, alu.t_apellido1 || ' ' || alu.t_apellido2 apellidos ,  MATMATRI.X_MATERIAOMG idMateriaOmg,  " +
			" CASE WHEN (MATMATRI.X_ESTADO NOT IN (1, 3) OR MATMATRI.X_ADAPTACION IS NOT NULL AND :nivelCurricular = 0 AND mat.x_niveadap <> mat.x_ofertamatrig) THEN 1 ELSE 0 END AS disabled " +
			" FROM TLALUMNOS ALU  " +
			" INNER JOIN EVA_RELPROGDIDPOND RELPROGDIDPOND ON RELPROGDIDPOND.X_PONDERACION = :idPonderacion  " +
			" INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = RELPROGDIDPOND.ID_PROGDIDAC  " +
			" INNER JOIN TLMATALU MAT ON MAT.X_ALUMNO = ALU.X_ALUMNO AND MAT.C_ANNO = PROGDIDAC.NU_ANNO AND MAT.X_CENTRO = PROGDIDAC.X_CENTRO  " +
			" INNER JOIN TLMATMATRIALU MATMATRI ON MATMATRI.X_MATRICULA = MAT.X_MATRICULA AND MATMATRI.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG  " +
			" INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = MAT.X_UNIDAD AND UNICEN.X_CENTRO = PROGDIDAC.X_CENTRO  and (:direccion = 0 AND (UNICEN.X_EMPLEADO IN (:idEmpleados) AND :tutor =1) OR :direccion = 1)  " +
			"  INNER JOIN DELPHOS.TLCONVCENOMC ccm ON ccm.X_CONVCENTROOMC = :idConvCentroOmc  " +
			"  INNER JOIN DELPHOS.TLCONVCENTROS cce ON CCE.X_CONVCENTRO = CCM.X_CONVCENTRO  " +
			"  WHERE MAT.X_UNIDAD = :idUnidad AND ((:nivelCurricular != 0 AND MATMATRI.X_ADAPTACION is not null AND PROGDIDAC.X_NIVEADAP = MAT.X_NIVEADAP and MAT.X_NIVEADAP = :nivelCurricular) OR :nivelCurricular = 0)  " +
			"  AND (SELECT count(cen.x_convcentro) FROM DELPHOS.TLCONVCENTROS cen  " +
			"  INNER JOIN DELPHOS.TLCONVCENOMC ccm ON ccm.X_CONVCENTROOMC = :idConvCentroOmc  " +
			"   WHERE cen.X_CONVCENTRO = ccm.X_CONVCENTRO AND ((cen.X_CONVOCATORIA IS NULL OR cen.X_CONVOCATORIA <> 2) OR (cen.X_CONVOCATORIA = 2 AND (mat.F_PROMOCION IS NULL  " +
			"   OR mat.F_PROMOCION <> (SELECT cu.F_SESION FROM DELPHOS.TLCONVUNIDAD cu  " +
			"   INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTROOMC = cu.X_CONVCENTROOMC  " +
			"   INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CONVCENTRO = cco2.X_CONVCENTRO  " +
			"   WHERE cu.X_UNIDAD = :idUnidad AND cce2.X_CONVOCATORIA = 1))))) = 1  " +
			" order by apellidos ASC", nativeQuery = true)
	List<AlumnoEvaluacionProjection> getAlumnosEvaluacionPendiente(@Param("idEmpleados") List<Long> idEmpleados,
																   @Param("idPonderacion") Long idPonderacion,
																   @Param("nivelCurricular") Long idNivelCurricular,
																   @Param("idUnidad") Long idUnidad,
																   @Param("tutor") Long tutor,
																   @Param("direccion") Long direccion,
																   @Param("idConvCentroOmc") Long idConvCentroOmc);

	@Query(value = "SELECT mua.X_MATRICULA idMatricula, alu.X_ALUMNO idAlumno, "
			+ "DECODE(etapa.S_ETAPA, 'Bachillerato', ciclo.X_ETAPA, etapa.X_ETAPA) idEtapa, cco.X_CONVCENTROOMC idConvCentroOmc, alu.C_NUMESCOLAR numEscolar, "
			+ "DELPHOS.TLF_NOMBRE(alu.T_NOMBRE, alu.T_APELLIDO1, alu.T_APELLIDO2) nombreAlumno, "
			+ "etapa.S_ETAPA nombreEtapa, cce.S_CONVOCATORIA nombreConvocatoria, "
			+ "CASE WHEN (MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) THEN 1 ELSE 0 END AS acnee, "
			+ "(SELECT OFEMT.D_OFERTAMATRIG FROM TLOFEMATRGEN OFEMT WHERE OFEMT.X_OFERTAMATRIG = MUA.X_NIVEADAP AND MUA.X_NIVEADAP <> MUA.X_OFERTAMATRIG AND MUA.X_NIVEADAP IS NOT null) nivelCurricular, "
			+ "(SELECT DECODE(etapa2.S_ETAPA, 'Bachillerato', ciclo2.X_ETAPA, etapa2.X_ETAPA) FROM DELPHOS.TLOFEMATRGEN omg2  "
			+ "INNER JOIN DELPHOS.TLCURSOORG cuo2 ON cuo2.X_OFERTAMATRIG = omg2.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLMODALIDADES modl2 ON modl2.X_MODALIDAD = omg2.X_MODALIDAD "
			+ "INNER JOIN DELPHOS.TLCURSOMODA curModa2 ON curModa2.X_MODALIDAD = modl2.X_MODALIDAD AND curModa2.X_CURSOMOD = cuo2.X_CURSOMOD "
			+ "INNER JOIN DELPHOS.TLETAPAS curso2 ON curso2.X_ETAPA = curModa2.X_ETAPA "
			+ "INNER JOIN DELPHOS.TLETAPAS ciclo2 ON ciclo2.X_ETAPA = curso2.X_ETAPADEPENDEDE "
			+ "INNER JOIN DELPHOS.TLETAPAS etapa2 ON etapa2.X_ETAPA = ciclo2.X_ETAPADEPENDEDE "
			+ "WHERE omg2.X_OFERTAMATRIG = mua.X_NIVEADAP AND mua.X_NIVEADAP <> mua.X_OFERTAMATRIG AND mua.X_NIVEADAP IS NOT NULL) idEtapaAdaptacion "
			+ "FROM DELPHOS.TLMATALU mua "
			+ "INNER JOIN DELPHOS.TLCONVCENTROS cce ON cce.X_CONVCENTRO = :idConvocatoria AND cce.C_ANNO = mua.C_ANNO AND cce.X_CENTRO = mua.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = mua.X_OFERTAMATRIC AND omc.X_OFERTAMATRIG = mua.X_OFERTAMATRIG AND omc.X_CENTRO = cce.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLCONVCENOMC cco ON cco.X_OFERTAMATRIC = omc.X_OFERTAMATRIC AND cco.X_CONVCENTRO = cce.X_CONVCENTRO "
			+ "INNER JOIN DELPHOS.TLALUMNOS alu ON alu.X_ALUMNO = mua.X_ALUMNO "
			+ "INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = omg.X_MODALIDAD "
			+ "INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
			+ "INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA "
			+ "INNER JOIN DELPHOS.TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA "
			+ "INNER JOIN DELPHOS.TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA "
			+ "WHERE mua.X_UNIDAD = :idUnidad "
			+ "AND omg.X_OFERTAMATRIG IN (:idsOfertamatrig) "
			+ "AND NVL(mua.C_RESULTADO, 99) > 1 "
			+ "AND (cce.X_CONVOCATORIA <> 2 OR cce.X_CONVOCATORIA = 2 AND (mua.F_PROMOCION IS NULL "
			+ "OR mua.F_PROMOCION <> (SELECT cu.F_SESION FROM DELPHOS.TLCONVUNIDAD cu "
			+ "INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTROOMC = cu.X_CONVCENTROOMC "
			+ "INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CONVCENTRO = cco2.X_CONVCENTRO "
			+ "WHERE cu.X_UNIDAD = :idUnidad AND cce2.X_CONVOCATORIA = 1)))  "
			+ "ORDER BY nombreAlumno", nativeQuery = true)
	List<AlumnoValoracionProjection> getAlumnosValoracionByUnidadAndConvocatoria(@Param("idUnidad") Long idUnidad, @Param("idConvocatoria") Long idConvocatoria, @Param("idsOfertamatrig") List<Long> idsOfertamatrig);

	@Query(value = "SELECT DECODE(cce.X_CONVOCATORIA, 2, 'true', 'false') extraordinaria " +
			"FROM DELPHOS.TLCONVCENOMC cco " +
			"INNER JOIN DELPHOS.TLCONVCENTROS cce ON cce.X_CONVCENTRO = cco.X_CONVCENTRO " +
			"WHERE cco.X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
	boolean isConvocatoriaExtraordinaria(@Param("idConvCentroOmc") Long idConvCentroOmc);

	@Query(value = "SELECT convCen.X_CONVCENTROOMC idConvCentroOmc " +
			"FROM DELPHOS.TLCONVCENTROS conv " +
			"INNER JOIN DELPHOS.TLCONVCENOMC convCen ON convCen.X_CONVCENTRO = conv.X_CONVCENTRO " +
			"INNER JOIN DELPHOS.TLCONVUNIDAD convUni ON convUni.X_CONVCENTROOMC = convCen.X_CONVCENTROOMC " +
			"INNER JOIN DELPHOS.TLMATALU matAlu ON matAlu.X_CENTRO = conv.X_CENTRO AND matAlu.C_ANNO = conv.C_ANNO AND matAlu.X_UNIDAD = convUni.X_UNIDAD " +
			"WHERE conv.x_convocatoria = 1 AND matAlu.X_MATRICULA = :idMatricula AND ROWNUM = 1", nativeQuery = true)
	Long getIdConvCentroOmcrdinariaByIdMatricula(@Param("idMatricula") Long idMatricula);

	@Query(value = "SELECT ROUND(AVG(NVL(nota.numero, nota.orden))) notaMedia FROM " +
			"(SELECT cal.N_NUMERO numero, cal.N_ORDEN orden " +
			"FROM DELPHOS.TLDESOPERATIVO dop " +
			"INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO " +
			"INNER JOIN DELPHOS.TLVALCOMALU vcomalu ON vcomalu.X_COMESP = rdoce.X_COMESP " +
			"INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalu.X_MATMATRICULA " +
			"INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA " +
			"INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA " +
			"WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATRICULA = :idMatricula AND mma.X_CONVOCATORIA <> 1 AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmcExtraordinaria " +
			"UNION ALL " +
			"SELECT cal.N_NUMERO numero, cal.N_ORDEN orden " +
			"FROM DELPHOS.TLDESOPERATIVO dop " +
			"INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO " +
			"INNER JOIN DELPHOS.TLVALCOMALU vcomalu ON vcomalu.X_COMESP = rdoce.X_COMESP " +
			"INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalu.X_MATMATRICULA " +
			"INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA " +
			"INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA " +
			"WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATRICULA = :idMatricula AND mma.X_CONVOCATORIA = 1 AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmcOrdinaria) nota", nativeQuery = true)
	Integer getNotaMediaRedondeadaExtraordinariaByIdDescriptorOperativoAndIdMatriculaAndIdsConvCentroOmc(@Param("idDescriptorOperativo") Long idDescriptorOperativo, @Param("idMatricula") Long idMatricula, @Param("idConvCentroOmcExtraordinaria") Long idConvCentroOmcExtraordinaria, @Param("idConvCentroOmcOrdinaria") Long idConvCentroOmcOrdinaria);

	@Query(value = "SELECT ROUND(AVG(NVL(nota.numero, nota.orden))) notaMedia FROM " +
			"(SELECT cal.N_NUMERO numero, cal.N_ORDEN orden " +
			"FROM DELPHOS.TLDESOPERATIVO dop " +
			"INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO " +
			"INNER JOIN DELPHOS.TLVALCOMALU vcomalu ON vcomalu.X_COMESP = rdoce.X_COMESP " +
			"INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalu.X_MATMATRICULA " +
			"INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA " +
			"INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA " +
			"WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATRICULA = :idMatricula AND mma.X_MATMATRICULA NOT IN (:idsMatMatriculaACNEE) AND mma.X_CONVOCATORIA <> 1 AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmcExtraordinaria " +
			"UNION ALL " +
			"SELECT CASE WHEN cal.N_ORDEN > 2 AND siseta.X_ETAPA = :idEtapaAdaptacion THEN (SELECT cal2.N_NUMERO FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) ELSE (SELECT cal2.N_NUMERO FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) END numero, " +
			"CASE WHEN cal.N_ORDEN > 2 AND siseta.X_ETAPA = :idEtapaAdaptacion THEN (SELECT cal2.N_ORDEN FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) ELSE (SELECT cal2.N_ORDEN FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) END orden " +
			"FROM DELPHOS.TLDESOPERATIVO dop " +
			"INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO " +
			"INNER JOIN DELPHOS.TLVALCOMALU vcomalu ON vcomalu.X_COMESP = rdoce.X_COMESP " +
			"INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalu.X_MATMATRICULA " +
			"INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA " +
			"INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA " +
			"WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATMATRICULA IN (:idsMatMatriculaACNEE) AND mma.X_CONVOCATORIA <> 1 AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmcExtraordinaria " +
			"UNION ALL " +
			"SELECT cal.N_NUMERO numero, cal.N_ORDEN orden " +
			"FROM DELPHOS.TLDESOPERATIVO dop " +
			"INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO " +
			"INNER JOIN DELPHOS.TLVALCOMALU vcomalu ON vcomalu.X_COMESP = rdoce.X_COMESP " +
			"INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalu.X_MATMATRICULA " +
			"INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA " +
			"INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA " +
			"WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATRICULA = :idMatricula AND mma.X_MATMATRICULA NOT IN (:idsMatMatriculaACNEE) AND mma.X_CONVOCATORIA = 1 AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmcOrdinaria " +
			"UNION ALL " +
			"SELECT CASE WHEN cal.N_ORDEN > 2 AND siseta.X_ETAPA = :idEtapaAdaptacion THEN (SELECT cal2.N_NUMERO FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) ELSE (SELECT cal2.N_NUMERO FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) END numero, " +
			"CASE WHEN cal.N_ORDEN > 2 AND siseta.X_ETAPA = :idEtapaAdaptacion THEN (SELECT cal2.N_ORDEN FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) ELSE (SELECT cal2.N_ORDEN FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) END orden " +
			"FROM DELPHOS.TLDESOPERATIVO dop " +
			"INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO " +
			"INNER JOIN DELPHOS.TLVALCOMALU vcomalu ON vcomalu.X_COMESP = rdoce.X_COMESP " +
			"INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalu.X_MATMATRICULA " +
			"INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalu.X_CALIFICA " +
			"INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA " +
			"WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATMATRICULA IN (:idsMatMatriculaACNEE) AND mma.X_CONVOCATORIA = 1 AND vcomalu.X_CONVCENTROOMC = :idConvCentroOmcOrdinaria) nota", nativeQuery = true)
	Integer getNotaMediaRedondeadaExtarodinariaACNEEByIdDescriptorOperativoAndIdMatriculaAndIdsConvCentroOmcAndIdEtapaAdaptacionAndIdsMatMatriculaACNEE(@Param("idDescriptorOperativo") Long idDescriptorOperativo, @Param("idMatricula") Long idMatricula, @Param("idConvCentroOmcExtraordinaria") Long idConvCentroOmcExtraordinaria, @Param("idConvCentroOmcOrdinaria") Long idConvCentroOmcOrdinaria, @Param("idEtapaAdaptacion") Long idEtapaAdaptacion, @Param("idsMatMatriculaACNEE") Long[] idsMatMatriculaACNEE);
}
