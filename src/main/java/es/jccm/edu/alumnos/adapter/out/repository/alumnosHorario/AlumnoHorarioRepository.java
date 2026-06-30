package es.jccm.edu.alumnos.adapter.out.repository.alumnosHorario;

import java.sql.Blob;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoHorario;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.QAlumnoHorario;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AlumnoAndFaltasProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AlumnoDetalleProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AlumnoHorarioProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AlumnosProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.AsignaturaProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.GrupoActividadAlumnoProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.HorarioSemanalAlumnoProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.ListaAlumnosGrupoActividadProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.MateriaAlumnoProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TutorAlumnoProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TutorProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AlumnoHorarioRepository extends AbstractRepository<AlumnoHorario, Integer, QAlumnoHorario> {

	@Query(value = "SELECT count(ALU.X_ALUMNO) " + "FROM TLMATALU MAT, TLALUMNOS ALU, TLMATMATRIALU MTR "
			+ "WHERE MAT.X_UNIDAD = :idUnidad " + "AND MTR.X_MATERIAOMG = :idMateria "
			+ "AND MAT.X_ALUMNO = ALU.X_ALUMNO " + "AND MTR.X_MATRICULA = MAT.X_MATRICULA", nativeQuery = true)
	Long countByAsignatura(@Param("idUnidad") Long idUnidad, @Param("idMateria") Long idMateria);

	@Query(value = "SELECT t.* " + "FROM TLALUMNOS t " + "WHERE t.X_ALUMNO IN ( " + "SELECT m.X_ALUMNO "
			+ "FROM TLFALASIALU f, TLMATALU m " + "WHERE F_FALASI > SYSDATE - 30 " + "AND f.C_TIPFAL != 'R' "
			+ "AND m.X_UNIDAD = :idUnidad " + "AND f.X_MATERIAOMG = :idMateria " + "AND f.X_MATRICULA = m.X_MATRICULA "
			+ "HAVING COUNT (m.X_ALUMNO) > 2 " + "GROUP BY (m.X_ALUMNO)" + ")", nativeQuery = true)
	List<AlumnoHorario> findAlumnosHorarioByFaltasRecurrentes(@Param("idUnidad") Long idUnidad,
			@Param("idMateria") Long idMateria);

	@Query(value = "SELECT t.* " + "FROM TLALUMNOS t " + "WHERE t.X_ALUMNO IN ( " + "SELECT m.X_ALUMNO "
			+ "FROM TLFALASIALU f, TLMATALU m " + "WHERE F_FALASI > SYSDATE - 30 " + "AND f.C_TIPFAL = 'R' "
			+ "AND m.X_UNIDAD = :idUnidad " + "AND f.X_MATERIAOMG = :idMateria " + "AND f.X_MATRICULA = m.X_MATRICULA "
			+ "HAVING COUNT (m.X_ALUMNO) > 2 " + "GROUP BY (m.X_ALUMNO)" + ")", nativeQuery = true)
	List<AlumnoHorario> findAlumnosHorarioByRetrasosRecurrentes(@Param("idUnidad") Long idUnidad,
			@Param("idMateria") Long idMateria);

	@Query(value = "SELECT F.B_FOTO " + "FROM TLALUMNOS ALU, TLALUMNOSFOTO F "
			+ "WHERE ALU.C_NUMESCOLAR = :numescolar AND ALU.X_ALUMNO = F.X_ALUMNO ", nativeQuery = true)
	Blob getAlumnoFoto(@Param("numescolar") String numescolar);

	@Query(value = "SELECT mua.x_matricula idmatricula, alu.t_nombre || ' ' || alu.t_apellido1 || ' ' || alu.t_apellido2 nombreAlumno, "
			+ "alu.t_apellido1 || ' ' || alu.t_apellido2 || ', ' || alu.t_nombre nombreFormateado, alu.x_alumno idAlumno, "
			+ "(SELECT DECODE (l_diacom, 'S', 'C', c_tipfal) AS tipoFalta FROM DELPHOS_SEGEDU.tlfalasialu tlf "
			+ "WHERE (x_tramo IS NULL OR x_tramo = :idTramo) AND tlf.x_matricula = mua.x_matricula "
			+ "AND f_falasi = to_date(:fecha, 'DD/MM/YYYY')) tipoFalta, (SELECT c_appori "
			+ "FROM DELPHOS.tlfalasialu tlfd WHERE nvl(tlfd.X_TRAMO,:idTramo) = :idTramo "
			+ "AND tlfd.x_matricula = mua.x_matricula AND f_falasi = to_date(:fecha, 'DD/MM/YYYY')) DELPHOS, "
			+ "CAST (DELPHOS_SEGEDU.tlf_materia_grupo_matricula (gap.x_gruactproalu, mua.x_matricula) AS NUMBER(12)) idmateriaomg, fal.idNotificacion, fal.comentarioNotificacion, "
			+ "fal.motivoNotificacion, fal.nombreUsuarioNotificacion, fot.b_foto foto "
			+ "FROM DELPHOS_SEGEDU.tlmatalu mua, DELPHOS_SEGEDU.tlalumnos alu, DELPHOS_SEGEDU.tlgruactproalu gap, DELPHOS.tlalumnosfoto fot, "
			+ "(SELECT t.X_NOTFALASIALU idNotificacion, t.T_OBSERVACION comentarioNotificacion, "
			+ "MOTFAL.D_VALLISCAT motivoNotificacion, (SELECT EMP.NOMBRE||' '||EMP.APELLIDO1||' '|| EMP.APELLIDO2 FROM DELPHOS_SEGEDU.TLEMPLEADOS EMP WHERE USUFAL.X_EMPLEADO = EMP.X_EMPLEADO) nombreUsuarioNotificacion, "
			+ "t.X_NOTFALASIALU, t.X_MATRICULA X_MATRICULA "
			+ "FROM DELPHOS_SEGEDU.TLNOTFALASIALU t, DELPHOS_SEGEDU.tlfalasialu f, DELPHOS_SEGEDU.TLVALLISCAT MOTFAL, DELPHOS_SEGEDU.TLUSUARIOS USUFAL "
			+ "WHERE t.X_NOTFALASIALU = f.X_NOTFALASIALU(+) AND t.X_MATRICULA = f.X_MATRICULA (+) "
			+ "AND t.X_MOTFALASIALU = MOTFAL.X_VALLISCAT  "
			+ "AND t.C_USUCREACION = USUFAL.X_USUARIO (+) "
			+ "AND nvl(f.X_TRAMO,:idTramo) = :idTramo "
			+ "AND 1 = CASE "
			+ "WHEN t.F_FIN_FALASI IS NULL AND TRUNC(t.F_INICIO_FALASI) = to_date(:fecha, 'DD/MM/YYYY') THEN 1 "
			+ "WHEN t.F_FIN_FALASI IS NOT NULL "
			+ "AND to_date(:fecha, 'DD/MM/YYYY') BETWEEN f.F_FALASI AND t.F_FIN_FALASI THEN 1 ELSE 0 END) fal "
			+ "WHERE mua.x_centro = gap.x_centro AND mua.x_alumno = alu.x_alumno AND mua.c_anno = gap.c_anno "
			+ "AND alu.x_alumno = fot.x_alumno(+) AND mua.x_matricula = fal.x_matricula (+) "
			+ "AND NVL (c_resultado,99) > DELPHOS_SEGEDU.tlf_parametro ('MAT_CANCEL') "
			+ "AND mua.x_alumno = alu.x_alumno "
			+ "AND DELPHOS_SEGEDU.tlf_valida_grupo_matricula(gap.x_gruactproalu, mua.x_matricula) = 1 "
			+ "AND DELPHOS_SEGEDU.tlf_valida_grupo_tramo(gap.x_gruactproalu, mua.x_matricula, :idTramo, TO_DATE(:fecha, 'DD/MM/YYYY')) = 1 "
			+ "AND gap.x_gruactproalu = :idGrupo "
			+ "ORDER BY nombreFormateado", nativeQuery = true)
	List<AlumnoAndFaltasProjection> getAlumnosAndFaltas(@Param("idTramo") Long idTramo, @Param("idGrupo") Long idGrupo,
			@Param("fecha") String fecha);

	@Query(value = "SELECT ALU.X_ALUMNO idAlumnoHorario, ALU.T_NOMBRE nombre, ALU.T_APELLIDO1 apellido1, ALU.T_APELLIDO2 apellido2, ALU.C_NUMESCOLAR idescolar, ALU.T_CORREO_E correo, "
			+ "ALU.T_TELEFONO telefono, ALU.T_TELEFONOURG tlefnourg, ALU.F_NACIMIENTO fechaNacimiento, ALU.C_NUMIDE numide, "
			+ "DELPHOS_SEGEDU.TLF_DIRECCION_RRS(ALU.X_ALUMNO) || ' ' || DELPHOS_SEGEDU.TLF_DIRECCION_RRS(ALU.X_ALUMNO, 'MUN') "
			+ "|| ' (' || DELPHOS_SEGEDU.TLF_DIRECCION_RRS(ALU.X_ALUMNO, 'PRO') || ')' direccion,"
			+ " (SELECT T_OBSERVACION FROM DELPHOS_SEGEDU.TLMATMATRIALU WHERE X_MATERIAOMG = :idMatMatricula AND X_MATRICULA = MUA.X_MATRICULA) as observacion, "
			+ "(EXC.C_ANNO || '/' || EXC.N_NUMEXPCEN) as idExpediente FROM DELPHOS_SEGEDU.TLALUMNOS ALU, DELPHOS_SEGEDU.TLMATALU MUA, DELPHOS_SEGEDU.TLEXPCENTROS EXC "
			+ "WHERE ALU.X_ALUMNO = MUA.X_ALUMNO " + "AND MUA.X_MATRICULA = :idMatricula "
			+ "AND EXC.X_ALUMNO = MUA.X_ALUMNO " + "AND EXC.X_CENTRO = MUA.X_CENTRO "
			+ "AND EXC.X_TIPOEXP = MUA.X_TIPOEXP", nativeQuery = true)
	AlumnoHorarioProjection findAlumnosHorarioDetalle(@Param("idMatricula") Long idMatricula,
			@Param("idMatMatricula") Long idMatMatricula);
	
	@Query(value = "SELECT ALU.X_ALUMNO idAlumno, ALU.T_NOMBRE nombre, ALU.T_APELLIDO1 apellido1, ALU.T_APELLIDO2 apellido2, ALU.C_NUMESCOLAR idescolar, ALU.T_CORREO_E correo, "
			+ "ALU.T_TELEFONO telefono, ALU.T_TELEFONOURG tlefnourg, ALU.F_NACIMIENTO fechaNacimiento, ALU.C_NUMIDE numide, "
			+ "DELPHOS_SEGEDU.TLF_DIRECCION_RRS(ALU.X_ALUMNO) || ' ' || DELPHOS_SEGEDU.TLF_DIRECCION_RRS(ALU.X_ALUMNO, 'MUN') "
			+ "|| ' (' || DELPHOS_SEGEDU.TLF_DIRECCION_RRS(ALU.X_ALUMNO, 'PRO') || ')' direccion, "
			+ "(EXC.C_ANNO || '/' || EXC.N_NUMEXPCEN) as idExpediente FROM DELPHOS_SEGEDU.TLALUMNOS ALU, DELPHOS_SEGEDU.TLMATALU MUA, DELPHOS_SEGEDU.TLEXPCENTROS EXC "
			+ "WHERE ALU.X_ALUMNO = MUA.X_ALUMNO " 
			+ "AND MUA.X_MATRICULA = :idMatricula "
			+ "AND EXC.X_ALUMNO = MUA.X_ALUMNO " 
			+ "AND EXC.X_CENTRO = MUA.X_CENTRO "
			+ "AND EXC.X_TIPOEXP = MUA.X_TIPOEXP", nativeQuery = true)
	AlumnoDetalleProjection findAlumnoDetalle(@Param("idMatricula") Long idMatricula);
	
	@Modifying
	@Query(value = "UPDATE DELPHOS_SEGEDU.TLMATMATRIALU SET T_OBSERVACION = :observacion "
			+ "WHERE X_MATERIAOMG = :idMatMatricula "
			+ "AND X_MATRICULA = :idMatricula", nativeQuery = true)
	void SetAlumnosObservacion(@Param("idMatMatricula") Long idMatMatricula, @Param("observacion") String observacion, 
			@Param("idMatricula") Long idMatricula);
	
	@Query(value = " SELECT * FROM ("
			+ "SELECT ALU.X_ALUMNO idAlumno, ALU.C_NUMESCOLAR idEscolar, MUA.X_MATRICULA idMatricula, DELPHOS.TLF_NOMBRE(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) nombre, "
			+ "(SELECT UNI.T_NOMBRE FROM DELPHOS_SEGEDU.TLUNIDADESCEN UNI WHERE MUA.X_UNIDAD = UNI.X_UNIDAD AND UNI.C_ANNO = MUA.C_ANNO AND UNI.X_CENTRO = MUA.X_CENTRO) unidad, "
			+ "ALU.F_NACIMIENTO fechaNacimiento, DECODE( MUA.L_DIVERSIFICA, 'S', 'true', 'false') diversifica, "
			+ "(SELECT RES.D_RESULTADO FROM DELPHOS_SEGEDU.TLRESULTADO RES WHERE MUA.C_RESULTADO = RES.C_RESULTADO )  estadoMatricula, FOT.B_FOTO FOTO, "
			+ " ROW_NUMBER() OVER(PARTITION BY ALU.X_ALUMNO  ORDER BY ALU.X_ALUMNO  ASC) rn  "
			+ "FROM "
			+ "	DELPHOS_SEGEDU.TLALUMNOS ALU, "
			+ "	TLALUMNOSFOTO FOT, "
			+ " DELPHOS_SEGEDU.tluniafegruactpro uag, "
			+ " DELPHOS_SEGEDU.tlmatmatrialu mma, "
			+ "	DELPHOS_SEGEDU.TLGRUACTPROALU GAPA, "
			+ "	DELPHOS_SEGEDU.TLMATALU MUA, "
			+ " DELPHOS_SEGEDU.tlhorariosr hor "
			+ "WHERE "
			+ " uag.x_gruactproalu = GAPA.x_gruactproalu "
			+ " AND ALU.X_ALUMNO = MUA.X_ALUMNO "
			+ "	AND ALU.X_ALUMNO = FOT.X_ALUMNO(+) "
			+ "	AND GAPA.X_CENTRO = MUA.X_CENTRO "
			+ " AND GAPA.x_empleado = hor.x_empleado "
			+ " AND GAPA.c_anno = hor.c_anno "
			+ " AND GAPA.X_ACTIVIDAD = hor.X_ACTIVIDAD "
			+ "	AND MUA.C_ANNO = GAPA.C_ANNO "
			+ " AND mua.x_unidad = uag.x_unidad "
			+ " AND GAPA.F_TOMAPOS IN ( "
			+ "		SELECT ALU.F_TOMAPOS "
			+ "		FROM delphos.TLPTOTRAEMP ALU "
			+ "		WHERE ALU.X_EMPLEADO = :idEmpleado AND ALU.X_CENTRO = :idCentro "
			+ "		AND TLF_INTERSECPER(ALU.F_TOMAPOS, ALU.F_CESE, SYSDATE, SYSDATE) = 1) "
			+ "	AND NVL(MUA.C_RESULTADO, 99) > DELPHOS_SEGEDU.TLF_PARAMETRO('MAT_CANCEL') "
			+ " AND DELPHOS_SEGEDU.TLF_VALIDA_GRUPO_MATRICULA(GAPA.X_GRUACTPROALU, MUA.X_MATRICULA) = 1 "
			+ " AND DELPHOS_SEGEDU.tlf_valida_grupo_tramo2(GAPA.x_gruactproalu, MUA.x_matricula, hor.x_tramo) = 1 "
			+ " AND GAPA.C_ANNO = :anno "
			+ " AND GAPA.X_EMPLEADO = :idEmpleado "
			+ " AND (-1 = :idGrupoActividad "
			+ "  OR GAPA.X_GRUACTPROALU = :idGrupoActividad) "
			+ " AND mma.x_materiaomg = uag.x_materiaomg "
			+ " AND mma.x_matricula = MUA.X_matricula "
			+ " AND ('-1' = :nombre "
			+ " OR LOWER(ALU.T_NOMBRE) like %:nombre% OR LOWER(ALU.T_APELLIDO1) like %:nombre% OR LOWER(ALU.T_APELLIDO2) like %:nombre% )"
			+ " ) a WHERE rn = 1 ORDER BY CASE WHEN :order = 'ASC' THEN nombre END , CASE WHEN :order = 'DESC' THEN nombre END DESC", 
			countQuery = "SELECT count (DISTINCT ALU.X_ALUMNO) "
					+ "FROM "
					+ "	DELPHOS_SEGEDU.TLALUMNOS ALU, "
					+ " DELPHOS_SEGEDU.TLGRUACTPROALU GAPA, "
					+ " DELPHOS_SEGEDU.tluniafegruactpro uag, "
					+ " DELPHOS_SEGEDU.tlmatmatrialu mma, "
					+ " DELPHOS_SEGEDU.TLMATALU MUA, "
					+ " DELPHOS_SEGEDU.tlhorariosr hor "
					+ "WHERE "
					+ " uag.x_gruactproalu = GAPA.x_gruactproalu "
					+ "	AND ALU.X_ALUMNO = MUA.X_ALUMNO "
					+ " AND GAPA.X_CENTRO = MUA.X_CENTRO "
					+ "AND GAPA.x_empleado = hor.x_empleado "
					+ "AND GAPA.c_anno = hor.c_anno "
					+ "AND GAPA.X_ACTIVIDAD = hor.X_ACTIVIDAD "
					+ " AND MUA.C_ANNO = GAPA.C_ANNO "
					+ " AND mua.x_unidad = uag.x_unidad "
					+ " AND GAPA.F_TOMAPOS IN ( "
					+ "		SELECT ALU.F_TOMAPOS "
					+ "		FROM delphos.TLPTOTRAEMP ALU "
					+ "		WHERE ALU.X_EMPLEADO = :idEmpleado AND ALU.X_CENTRO = :idCentro "
					+ "		AND TLF_INTERSECPER(ALU.F_TOMAPOS, ALU.F_CESE, SYSDATE, SYSDATE) = 1) "
					+ " AND NVL(MUA.C_RESULTADO, 99) > DELPHOS_SEGEDU.TLF_PARAMETRO('MAT_CANCEL') "
					+ " AND DELPHOS_SEGEDU.TLF_VALIDA_GRUPO_MATRICULA(GAPA.X_GRUACTPROALU, MUA.X_MATRICULA) = 1 "
					+ " AND DELPHOS_SEGEDU.tlf_valida_grupo_tramo2(GAPA.x_gruactproalu, MUA.x_matricula, hor.x_tramo) = 1 "
					+ " AND GAPA.C_ANNO = :anno "
					+ " AND GAPA.X_EMPLEADO = :idEmpleado "
					+ " AND (-1 = :idGrupoActividad "
					+ "  OR GAPA.X_GRUACTPROALU = :idGrupoActividad) "
					+ " AND mma.x_materiaomg = uag.x_materiaomg "
					+ " AND mma.x_matricula = MUA.X_matricula "
					+ " AND ('-1' = :nombre "
					+ "  OR LOWER(ALU.T_NOMBRE) like %:nombre% OR LOWER(ALU.T_APELLIDO1) like %:nombre% OR LOWER(ALU.T_APELLIDO2) like %:nombre% )", nativeQuery = true)
	Page<AlumnosProjection> getAlumnosByGrupo(@Param("idEmpleado") Long idGrupo, @Param("anno") Long anno, @Param("order") String order, @Param("idGrupoActividad") Long idGrupoActividad, 
			@Param("nombre") String nombre, @Param("idCentro") Long idCentro, Pageable pageable);
	
	@Query(value = "SELECT GAP.X_GRUACTPROALU idGrupoActividad, GAP.S_GRUACTPROALU nombre, mma.X_MATERIAOMG idMateriaOmg, mma.T_OBSERVACION observacion "
			+ "FROM DELPHOS_SEGEDU.tlmatalu mua, DELPHOS_SEGEDU.tlgruactproalu gap, DELPHOS_SEGEDU.tluniafegruactpro uag, DELPHOS_SEGEDU.tlmatmatrialu mma "
			+ "WHERE mua.x_centro = gap.x_centro "
			+ "AND mua.c_anno = gap.c_anno "
			+ "AND mua.x_unidad = uag.x_unidad "
			+ "AND mua.x_matricula = mma.x_matricula "
			+ "AND uag.x_materiaomg = mma.x_materiaomg "
			+ "AND gap.x_gruactproalu = uag.x_gruactproalu "
			+ "AND NVL (c_resultado, 99) > DELPHOS_SEGEDU.tlf_parametro('MAT_CANCEL') "
			+ "AND DELPHOS_SEGEDU.tlf_valida_grupo_matricula(gap.x_gruactproalu, mua.x_matricula) = 1 "
			+ "AND GAP.X_EMPLEADO = :idEmpleado "
			+ "AND MUA.X_ALUMNO = :idAlumno "
			+ "AND MUA.C_ANNO = :anno "
			+ "ORDER BY nombre", nativeQuery = true)
	List<GrupoActividadAlumnoProjection> getGruposActividadAlumno(@Param("idEmpleado") Long idEmpleado, @Param("idAlumno") Long idAlumno, @Param("anno") Integer anno);
	
	@Query(value = "SELECT mua.x_matricula idMatricula, "
			+ "DELPHOS.TLF_NOMBRE(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) alumno "
			+ "FROM "
			+ "DELPHOS_SEGEDU.tluniafegruactpro uag, "
			+ "DELPHOS_SEGEDU.tlmatalu mua, "
			+ "DELPHOS_SEGEDU.tlalumnos alu, "
			+ "DELPHOS_SEGEDU.TLGRUACTPROALU grua, "
			+ "DELPHOS_SEGEDU.tlmatmatrialu mat "
			+ "WHERE "
			+ "uag.x_gruactproalu = :idGrupoActividad "
			+ "AND grua.x_empleado = :idEmpleado "
			+ "AND mua.x_unidad = uag.x_unidad "
			+ "AND mat.x_MATRICULA=mua.X_MATRICULA "
			+ "AND DELPHOS_SEGEDU.TLF_VALIDA_GRUPO_MATRICULA(grua.X_GRUACTPROALU, MUA.X_MATRICULA) = 1 "
			+ "AND alu.x_alumno = mua.x_alumno "
			+ "AND uag.x_gruactproalu = grua.x_gruactproalu "
			+ "AND uag.x_materiaomg = mat.x_materiaomg "
			+ "ORDER BY "
			+ "alumno", nativeQuery = true)
	List<ListaAlumnosGrupoActividadProjection> getListaAlumnosGrupoActividad(@Param("idGrupoActividad") Long idGrupoActividad ,@Param("idEmpleado") Long idEmpleado);
	
	@Query(value = "SELECT * FROM (SELECT ALU.X_ALUMNO idAlumno, ALU.C_NUMIDE idEscolar, ALULIST.X_MATRICULA idMatricula, DELPHOS.TLF_NOMBRE(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) nombre, "
			+ "ALULIST.T_NOMBRE unidad, ALU.F_NACIMIENTO fechaNacimiento, DECODE(ALULIST.L_DIVERSIFICA, 'S', 'true', 'false') diversifica, "
			+ "ALULIST.D_RESULTADO estadoMatricula, FOT.B_FOTO FOTO, "
			+ "ROW_NUMBER() OVER(PARTITION BY ALU.X_ALUMNO  ORDER BY ALU.X_ALUMNO  ASC) rn  "
			+ "FROM DELPHOS_SEGEDU.TLALUMNOS ALU, TLALUMNOSFOTO FOT, "
			+ "(SELECT DISTINCT mua.X_ALUMNO, MUA.X_MATRICULA, ALU.T_NOMBRE, MUA.L_DIVERSIFICA, RES.D_RESULTADO "
			+ "FROM DELPHOS_SEGEDU.TLUNIDADESCEN ALU, DELPHOS_SEGEDU.TLMATALU MUA, DELPHOS_SEGEDU.TLRESULTADO RES "
			+ "WHERE ALU.X_CENTRO = MUA.X_CENTRO "
			+ "AND MUA.C_ANNO = ALU.C_ANNO "
			+ "AND MUA.X_UNIDAD = ALU.X_UNIDAD (+) "
			+ "AND MUA.C_RESULTADO = RES.C_RESULTADO (+) "
			+ "AND NVL(MUA.C_RESULTADO, 99) > DELPHOS_SEGEDU.TLF_PARAMETRO('MAT_CANCEL') "
			+ "AND ALU.C_ANNO = :anno "
			+ "AND ALU.X_EMPLEADO = :idEmpleado "
			+ "AND (-1 = :idUnidad OR ALU.X_UNIDAD = :idUnidad)) ALULIST "
			+ "WHERE ALU.X_ALUMNO = ALULIST.X_ALUMNO "
			+ "AND ALU.X_ALUMNO = FOT.X_ALUMNO (+) "
			+ "AND ('-1' = :nombre OR LOWER(ALU.T_NOMBRE) like %:nombre% OR LOWER(ALU.T_APELLIDO1) like %:nombre% OR LOWER(ALU.T_APELLIDO2) like %:nombre% ) "
			+ " ) a WHERE rn = 1 ORDER BY CASE WHEN :order = 'ASC' THEN nombre END , CASE WHEN :order = 'DESC' THEN nombre END DESC",
			countQuery = "SELECT COUNT(*) "
					+ "FROM DELPHOS_SEGEDU.TLALUMNOS ALU, TLALUMNOSFOTO FOT, "
					+ "(SELECT DISTINCT mua.X_ALUMNO, MUA.X_MATRICULA, ALU.T_NOMBRE, MUA.L_DIVERSIFICA, RES.D_RESULTADO "
					+ "FROM DELPHOS_SEGEDU.TLUNIDADESCEN ALU, DELPHOS_SEGEDU.TLMATALU MUA, DELPHOS_SEGEDU.TLRESULTADO RES "
					+ "WHERE ALU.X_CENTRO = MUA.X_CENTRO "
					+ "AND MUA.C_ANNO = ALU.C_ANNO "
					+ "AND MUA.X_UNIDAD = ALU.X_UNIDAD (+) "
					+ "AND MUA.C_RESULTADO = RES.C_RESULTADO (+) "
					+ "AND NVL(MUA.C_RESULTADO, 99) > DELPHOS_SEGEDU.TLF_PARAMETRO('MAT_CANCEL') "
					+ "AND ALU.C_ANNO = :anno "
					+ "AND ALU.X_EMPLEADO = :idEmpleado "
					+ "AND (-1 = :idUnidad OR ALU.X_UNIDAD = :idUnidad)) ALULIST "
					+ "WHERE ALU.X_ALUMNO = ALULIST.X_ALUMNO "
					+ "AND ALU.X_ALUMNO = FOT.X_ALUMNO (+) "
					+ "AND ('-1' = :nombre OR LOWER(ALU.T_NOMBRE) like %:nombre% OR LOWER(ALU.T_APELLIDO1) like %:nombre% OR LOWER(ALU.T_APELLIDO2) like %:nombre% )", nativeQuery = true)
	Page<AlumnosProjection> getAlumnosByUnidad(@Param("idEmpleado") Long idGrupo, @Param("anno") Long anno, @Param("idUnidad") Long idUnidad,
			@Param("nombre") String nombre, @Param("order") String order, Pageable pageable);
	
	@Query(value = "SELECT MCU.X_MATERIAC idMateria, MCU.D_MATERIAC nombre, mma.X_MATERIAOMG idMateriaOmg, mma.T_OBSERVACION observacion "
			+ "FROM DELPHOS_SEGEDU.TLUNIDADESCEN UNI, DELPHOS_SEGEDU.tlmatalu mua, DELPHOS_SEGEDU.tlmatofematrg mog, DELPHOS_SEGEDU.tlmatmatrialu mma, DELPHOS_SEGEDU.tlmateriascurso mcu "
			+ "WHERE mua.x_centro = UNI.x_centro "
			+ "AND mua.c_anno = UNI.c_anno "
			+ "AND mua.x_unidad = UNI.x_unidad "
			+ "AND mua.x_matricula = mma.x_matricula "
			+ "AND mma.x_materiaomg = mog.x_materiaomg "
			+ "AND mog.x_materiac = mcu.x_materiac "
			+ "AND NVL (c_resultado, 99) > DELPHOS_SEGEDU.tlf_parametro('MAT_CANCEL') "
			+ "AND UNI.X_EMPLEADO = :idEmpleado "
			+ "AND MUA.X_ALUMNO = :idAlumno "
			+ "AND MUA.C_ANNO = :anno "
			+ "ORDER BY nombre ", nativeQuery = true)
	List<MateriaAlumnoProjection> getMateriasByUnidadAlumno(@Param("idEmpleado") Long idEmpleado, @Param("idAlumno") Long idAlumno, @Param("anno") Integer anno);
	
	@Query(value = "SELECT DISTINCT mua.x_matricula idMatricula, DELPHOS.TLF_NOMBRE(ALU.T_NOMBRE, ALU.T_APELLIDO1, ALU.T_APELLIDO2) alumno "
			+ "FROM DELPHOS_SEGEDU.TLUNIDADESCEN uni, DELPHOS_SEGEDU.tlmatalu mua, DELPHOS_SEGEDU.tlalumnos alu, DELPHOS_SEGEDU.tlmatmatrialu mat "
			+ "WHERE uni.X_UNIDAD = :idUnidad "
			+ "AND uni.x_empleado = :idEmpleado "
			+ "AND mua.x_unidad = uni.x_unidad "
			+ "AND mat.x_MATRICULA = mua.X_MATRICULA "
			+ "AND alu.x_alumno = mua.x_alumno "
			+ "ORDER BY alumno ", nativeQuery = true)
	List<ListaAlumnosGrupoActividadProjection> getListaAlumnosByUnidad(@Param("idUnidad") Long idUnidad, @Param("idEmpleado") Long idEmpleado);

	@Query(value = 
		    "SELECT n_diasemana, " +
		    "tra.x_tramo, " +
		    "tra.n_orden, " +
		    "convierte_hora(tra.n_inicio) || ' - ' || convierte_hora(tra.n_fin) AS d_tramo, " +
		    "tlf_seldatmatomg('ABR', mma.x_materiaomg) || '#' || '(' || emp.nombre || ' ' || apellido1 || ' ' || apellido2 || ')' AS profesor, " +
		    "tra.n_inicio " +
		    "FROM   DELPHOS_SEGEDU.tlmatalu mua " +
		    "JOIN DELPHOS_SEGEDU.tlmatmatrialu mma ON mua.x_matricula = mma.x_matricula " +
		    "JOIN DELPHOS_SEGEDU.tlhorariosr h ON mua.x_matricula = :idMatricula " +
		    "JOIN DELPHOS_SEGEDU.tlptotraemp pte ON h.x_empleado = pte.x_empleado AND h.f_tomapos = pte.f_tomapos " +
		    "JOIN DELPHOS_SEGEDU.tluniafetrahor u ON h.x_horariore = u.x_horariore " +
		    "JOIN DELPHOS_SEGEDU.tlempleados emp ON h.x_empleado = emp.x_empleado " +
		    "JOIN DELPHOS_SEGEDU.tltramoshor tra ON h.x_tramo = tra.x_tramo " +
		    "WHERE  1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, SYSDATE, SYSDATE) " +
		    "AND u.x_ofertamatric = mua.x_ofertamatric " +
		    "AND u.x_unidad = mua.x_unidad " +
		    "AND u.x_materiaomg = mma.x_materiaomg " +
		    "AND tlf_valida_grupo_matricula( " +
		    "tlf_grupo_actividad_horario( " +
		    "h.x_empleado, " +
		    "h.f_tomapos, " +
		    "mua.x_centro, " +
		    "mua.c_anno, " +
		    "h.x_tramo, " +
		    "h.n_diasemana, " +
		    "h.x_actividad, " +
		    "mua.x_unidad " +
		    "), " +
		    "mua.x_matricula " +
		    ") = 1 " +
		    "UNION " +
		    "SELECT n_diasemana, " +
		    "tra.x_tramo, " +
		    "tra.n_orden, " +
		    "convierte_hora(tra.n_inicio) || ' - ' || convierte_hora(tra.n_fin) AS d_tramo, " +
		    "act.d_actividad || '#' || '(' || emp.nombre || ' ' || apellido1 || ' ' || apellido2 || ')' AS profesor, " +
		    "tra.n_inicio " +
		    "FROM   DELPHOS_SEGEDU.tlmatalu mua " +
		    "JOIN DELPHOS_SEGEDU.tluniafetrahor u ON 1 = 1 " +
		    "JOIN DELPHOS_SEGEDU.tlhorariosr h ON 1 = 1 " +
		    "JOIN DELPHOS_SEGEDU.tlempleados emp ON h.x_empleado = emp.x_empleado " +
		    "JOIN DELPHOS_SEGEDU.tlactividades act ON h.x_actividad = act.x_actividad " +
		    "JOIN DELPHOS_SEGEDU.tltramoshor tra ON h.x_tramo = tra.x_tramo " +
		    "JOIN DELPHOS_SEGEDU.tlptotraemp pte ON h.x_empleado = pte.x_empleado AND h.f_tomapos = pte.f_tomapos " +
		    "WHERE  mua.x_matricula = :idMatricula " +
		    "AND 1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, SYSDATE, SYSDATE) " +
		    "AND h.x_horariore = u.x_horariore " +
		    "AND u.x_ofertamatric = mua.x_ofertamatric " +
		    "AND u.x_unidad = mua.x_unidad " +
		    "AND act.t_abreviatura <> 'LTESF' " +
		    "AND act.l_reqmateria = 'N' " +
		    "AND act.l_requnidad = 'S' " +
		    "AND tlf_valida_grupo_matricula( " +
		    "tlf_grupo_actividad_horario( " +
		    "h.x_empleado, " +
		    "h.f_tomapos, " +
		    "mua.x_centro, " +
		    "mua.c_anno, " +
		    "h.x_tramo, " +
		    "h.n_diasemana, " +
		    "h.x_actividad, " +
		    "mua.x_unidad " +
		    "), " +
		    "mua.x_matricula " +
		    ") = 1 " +
		    "ORDER BY n_orden, n_inicio, x_tramo, n_diasemana", 
		    nativeQuery = true)
		List<HorarioSemanalAlumnoProjection> getHorarioSemanalAlumno(@Param("idMatricula") Long idMatricula);
	
	@Query(value =
		    "SELECT DISTINCT " +
		    "    DELPHOS_SEGEDU.tlf_seldatmatomg('ABR', mma.x_materiaomg) AS abreviatura, " +
		    "    DELPHOS_SEGEDU.tlf_seldatmatomg('DES', mma.x_materiaomg) AS descripcion " +
		    "FROM " +
		    "    DELPHOS_SEGEDU.tlmatalu mua " +
		    "JOIN DELPHOS_SEGEDU.tlmatmatrialu mma ON mua.x_matricula = mma.x_matricula " +
		    "JOIN DELPHOS_SEGEDU.tlhorariosr h ON 1 = 1 " + // Joining to h but conditions are later
		    "JOIN DELPHOS_SEGEDU.tluniafetrahor u ON h.x_horariore = u.x_horariore " +
		    "WHERE " +
		    "    mua.x_matricula = :idMatricula " +
		    "    AND u.x_ofertamatric = mua.x_ofertamatric " +
		    "    AND u.x_unidad = mua.x_unidad " +
		    "    AND u.x_materiaomg = mma.x_materiaomg " +
		    "    AND DELPHOS_SEGEDU.tlf_valida_grupo_matricula( " +
		    "        DELPHOS_SEGEDU.tlf_grupo_actividad_horario( " +
		    "            h.x_empleado, " +
		    "            h.f_tomapos, " +
		    "            mua.x_centro, " +
		    "            mua.c_anno, " +
		    "            h.x_tramo, " +
		    "            h.n_diasemana, " +
		    "            h.x_actividad, " +
		    "            mua.x_unidad " +
		    "        ), " +
		    "        mua.x_matricula " +
		    "    ) = 1",
		    nativeQuery = true)
		List<AsignaturaProjection> getAsignaturaAlumno(@Param("idMatricula") Long idMatricula);
	
	


	    @Query(value =
	        "SELECT DISTINCT " +
	        "    DELPHOS_SEGEDU.tlf_nombrepersona('TLEMPLEADOS', hor.x_empleado) AS Tutor_Unidad, " +
	        "    DELPHOS_SEGEDU.tlf_horario_visita_tutor(hor.x_empleado, hor.f_tomapos, hor.c_anno) AS Horario_Visita " +
	        "FROM " +
	        "    DELPHOS_SEGEDU.tlhorariosr hor " +
	        "JOIN " +
	        "    DELPHOS_SEGEDU.tlempleados emp ON hor.x_empleado = emp.x_empleado " +
	        "JOIN " +
	        "    DELPHOS_SEGEDU.tlunidadescen uni ON hor.c_anno = uni.c_anno AND emp.x_empleado = uni.x_empleado " +
	        "JOIN " +
	        "    DELPHOS_SEGEDU.tlmatalu mat ON uni.x_unidad = mat.x_unidad " +
	        "WHERE " +
	        "    mat.x_matricula = :idMatricula",
	        nativeQuery = true)
	    	TutorAlumnoProjection getInfoTutorHorario(@Param("idMatricula") Long idMatricula);
	


	
}