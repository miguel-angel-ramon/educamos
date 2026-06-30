package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.*;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaProgramacionDidactica;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaProgramacionAula;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaProgramacionAula;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaProgramacionAulaRepository extends AbstractRepository<EvaProgramacionAula, Long, QEvaProgramacionAula> {

	List<EvaProgramacionAula> findAllByProgramacionDidactica(EvaProgramacionDidactica programacionDidactica);

	@Query(value = "SELECT DISTINCT grua.x_gruactproalu idGrupo, grua.s_gruactproalu nombreGrupo, "
			+ "( SELECT COUNT(mat.x_Alumno) "
			+ "FROM DELPHOS.tlmatalu mat, DELPHOS.tlmatmatrialu matalu "
			+ "WHERE mat.x_matricula = matalu.x_matricula "
			+ "AND uag.x_materiaomg = matalu.x_materiaomg "
			+ "AND mat.x_unidad = uag.x_unidad "
			+ "AND DELPHOS.TLF_VALIDA_GRUPO_MATRICULA(grua.X_GRUACTPROALU,mat.X_MATRICULA) = 1) numeroAlumnos, "
			+ "( SELECT COUNT(idAlumno) FROM ( "
			+ "SELECT alu1.X_ALUMNO idAlumno "
			+ "FROM DELPHOS.TLMATALU mua1 "
			+ "INNER JOIN DELPHOS.TLALUMNOS alu1 ON alu1.X_ALUMNO = mua1.X_ALUMNO "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULAEMP pae1 ON pae1.X_EMPLEADO = grua.X_EMPLEADO "
			+ "INNER JOIN DELPHOS.EVA_PROGDIDAC pd1 ON pd1.X_MATERIAOMG = uag.X_MATERIAOMG AND pd1.NU_ANNO = grua.C_ANNO AND pd1.X_CENTRO = grua.X_CENTRO "
			+ "INNER JOIN DELPHOS.EVA_PROGAULA pa1 ON pa1.ID_PROGAULA = pae1.ID_PROGAULA AND pa1.ID_PROGDIDAC = pd1.ID_PROGDIDAC "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULAUNIDAD paun1 ON paun1.ID_PROGAULA = pa1.ID_PROGAULA AND paun1.X_UNIDAD = uag.X_UNIDAD AND paun1.LG_AFECTATODOS = 1 "
			+ "WHERE mua1.X_UNIDAD = uag.X_UNIDAD AND mua1.C_ANNO = grua.C_ANNO AND DELPHOS.TLF_VALIDA_GRUPO_MATRICULA(grua.X_GRUACTPROALU, mua1.X_MATRICULA) = 1 "
			+ "UNION "
			+ "SELECT alu2.X_ALUMNO idAlumno "
			+ "FROM DELPHOS.EVA_RELPROGAULAEMP pae2 "
			+ "INNER JOIN DELPHOS.EVA_PROGDIDAC pd2 ON pd2.X_MATERIAOMG = uag.X_MATERIAOMG AND pd2.NU_ANNO = grua.C_ANNO AND pd2.X_CENTRO = grua.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLUNIAFETRAHOR unh2 ON unh2.X_UNIDAD=uag.X_UNIDAD AND unh2.X_MATERIAOMG = pd2.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLHORARIOSR hr2 ON hr2.X_EMPLEADO=grua.X_EMPLEADO AND hr2.F_TOMAPOS=grua.F_TOMAPOS AND hr2.X_HORARIORE=unh2.X_HORARIORE "
			+ "INNER JOIN DELPHOS.TLALUENDEPEN adp2 on adp2.x_unidad=uag.x_unidad and adp2.x_Horariore=unh2.X_HORARIORE "
			+ "INNER JOIN DELPHOS.EVA_PROGAULA pa2 ON pa2.ID_PROGAULA = pae2.ID_PROGAULA AND pa2.ID_PROGDIDAC = pd2.ID_PROGDIDAC "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma2 ON mma2.X_MATERIAOMG = pd2.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULALUM paalu2 ON paalu2.ID_PROGAULA = pa2.ID_PROGAULA "
			+ "INNER JOIN DELPHOS.TLMATALU mua2 ON mua2.C_ANNO = pd2.NU_ANNO AND mua2.X_CENTRO = pd2.X_CENTRO AND mua2.X_MATRICULA = paalu2.X_MATRICULA AND mua2.X_MATRICULA = mma2.X_MATRICULA AND mua2.X_MATRICULA=adp2.X_MATRICULA "
			+ "INNER JOIN DELPHOS.TLALUMNOS alu2 ON alu2.X_ALUMNO = mua2.X_ALUMNO "
			+ "WHERE pae2.X_EMPLEADO = grua.X_EMPLEADO AND DELPHOS.TLF_VALIDA_GRUPO_MATRICULA(grua.X_GRUACTPROALU, mua2.X_MATRICULA) = 1 "
			+ "UNION "
			+ "SELECT alu3.X_ALUMNO idAlumno "
			+ "FROM DELPHOS.EVA_RELPROGAULAEMP pae3 "
			+ "INNER JOIN DELPHOS.EVA_PROGDIDAC pd3 ON pd3.X_MATERIAOMG = uag.X_MATERIAOMG AND pd3.NU_ANNO = grua.C_ANNO AND pd3.X_CENTRO = grua.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLUNIAFETRAHOR unh3 ON unh3.X_UNIDAD=uag.X_UNIDAD AND unh3.X_MATERIAOMG = pd3.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLHORARIOSR hr3 ON hr3.X_EMPLEADO=grua.X_EMPLEADO AND hr3.F_TOMAPOS=grua.F_TOMAPOS AND hr3.X_HORARIORE=unh3.X_HORARIORE "
			+ "INNER JOIN DELPHOS.TLALUENDEPEN adp3 on adp3.x_unidad=uag.x_unidad and adp3.x_Horariore=unh3.X_HORARIORE "
			+ "INNER JOIN DELPHOS.EVA_PROGAULA pa3 ON pa3.ID_PROGAULA = pae3.ID_PROGAULA AND pa3.ID_PROGDIDAC = pd3.ID_PROGDIDAC "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULAUNIDAD paun3 ON paun3.ID_PROGAULA = pa3.ID_PROGAULA AND paun3.X_UNIDAD = uag.X_UNIDAD AND paun3.LG_AFECTATODOS = 1 "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma3 ON mma3.X_MATERIAOMG = pd3.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLMATALU mua3 ON mua3.C_ANNO = pd3.NU_ANNO AND mua3.X_CENTRO = pd3.X_CENTRO AND mua3.X_MATRICULA = mma3.X_MATRICULA AND mua3.X_MATRICULA=adp3.X_MATRICULA "
			+ "INNER JOIN DELPHOS.TLALUMNOS alu3 ON alu3.X_ALUMNO = mua3.X_ALUMNO "
			+ "WHERE pae3.X_EMPLEADO = grua.X_EMPLEADO AND DELPHOS.TLF_VALIDA_GRUPO_MATRICULA(grua.X_GRUACTPROALU, mua3.X_MATRICULA) = 1 "
			+ "UNION "
			+ "SELECT alu4.X_ALUMNO idAlumno "
			+ "FROM DELPHOS.TLMATALU mua4 "
			+ "INNER JOIN DELPHOS.TLALUMNOS alu4 ON alu4.X_ALUMNO = mua4.X_ALUMNO "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULAEMP pae4 ON pae4.X_EMPLEADO = grua.X_EMPLEADO "
			+ "INNER JOIN DELPHOS.EVA_PROGDIDAC pd4 ON pd4.X_MATERIAOMG = uag.X_MATERIAOMG AND pd4.NU_ANNO = grua.C_ANNO AND pd4.X_CENTRO = grua.X_CENTRO "
			+ "INNER JOIN DELPHOS.EVA_PROGAULA pa4 ON pa4.ID_PROGAULA = pae4.ID_PROGAULA AND pa4.ID_PROGDIDAC = pd4.ID_PROGDIDAC "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULALUM paalu4 ON paalu4.ID_PROGAULA = pa4.ID_PROGAULA AND paalu4.X_MATRICULA = mua4.X_MATRICULA "
			+ "WHERE mua4.X_UNIDAD = uag.X_UNIDAD AND mua4.C_ANNO = grua.C_ANNO AND DELPHOS.TLF_VALIDA_GRUPO_MATRICULA(grua.X_GRUACTPROALU, mua4.X_MATRICULA) = 1 "
			+ ")) numeroAlumnosConProgramacion "
			+ "FROM DELPHOS.TLGRUACTPROALU grua "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag ON uag.X_GRUACTPROALU = grua.X_GRUACTPROALU "
			+ "INNER JOIN DELPHOS.TLMATOFEMATRG mog ON mog.X_MATERIAOMG = uag.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLOFERTASUNIDAD ou ON ou.X_UNIDAD = uag.X_UNIDAD "
			+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = ou.X_OFERTAMATRIC "
			+ "INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLMATERIASCURSO matc ON matc.X_MATERIAC = mog.X_MATERIAC AND matc.X_CURSOMOD = cuo.X_CURSOMOD "
			+ "WHERE grua.X_CENTRO = :idCentro "
			+ "AND grua.x_empleado = :idEmpleado "
			+ "AND grua.C_ANNO = :anno "
			+ "ORDER BY grua.s_gruactproalu ASC", nativeQuery = true)
	List<AlumnosPorGrupoProjection> findAlumnosByEmpleadoAndAnyo(@Param("idEmpleado") Long idEmpleado,
	   @Param("anno") Long anno, @Param("idCentro") Long idCentro);

	//Usaremos esta query cuando el profesor sea sustituto o sustituido
	@Query(value = "SELECT grua.S_GRUACTPROALU nombreGrupo, COUNT(DISTINCT mua.x_Alumno) numeroAlumnos, COUNT(DISTINCT rpaalu.X_MATRICULA) numeroAlumnosConProgramacion "
			+ "FROM DELPHOS.tlmatalu mua "
			+ "INNER JOIN DELPHOS.tlmatmatrialu mma ON mma.X_MATRICULA = mua.x_matricula "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag ON uag.X_UNIDAD = mua.X_UNIDAD AND uag.X_MATERIAOMG = mma.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU grua ON grua.X_GRUACTPROALU = uag.X_GRUACTPROALU "
			+ "INNER JOIN DELPHOS.EVA_PROGDIDAC pd ON pd.X_MATERIAOMG = uag.X_MATERIAOMG AND pd.NU_ANNO = grua.C_ANNO AND pd.X_CENTRO = grua.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLMATOFEMATRG mog ON mog.X_MATERIAOMG = pd.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = pd.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLMATERIASCURSO matc ON matc.X_MATERIAC = mog.X_MATERIAC AND matc.X_CURSOMOD = cuo.X_CURSOMOD "
			+ "LEFT JOIN DELPHOS.EVA_RELPROGAULAEMP rpae ON rpae.X_EMPLEADO = grua.X_EMPLEADO "
			+ "LEFT JOIN DELPHOS.EVA_PROGAULA pa ON pa.ID_PROGAULA = rpae.ID_PROGAULA AND pa.ID_PROGDIDAC = pd.ID_PROGDIDAC "
			+ "LEFT JOIN DELPHOS.EVA_RELPROGAULALUM rpaalu ON rpaalu.ID_PROGAULA = pa.ID_PROGAULA AND rpaalu.X_MATRICULA = mua.X_MATRICULA "
			+ "WHERE DELPHOS.TLF_VALIDA_GRUPO_MATRICULA(grua.X_GRUACTPROALU, mua.X_MATRICULA) = 1 "
			+ "AND grua.X_CENTRO = :idCentro  "
			+ "AND grua.x_empleado IN (:idsEmpleados) "
			+ "AND grua.C_ANNO = :anno "
			+ "GROUP BY grua.S_GRUACTPROALU "
			+ "ORDER BY grua.s_gruactproalu ASC", nativeQuery = true)
	List<AlumnosPorGrupoProjection> findAlumnosByAnyoAndEmpleados(@Param("anno") Long anno,
																  @Param("idCentro") Long idCentro,
																  @Param("idsEmpleados") Long[] idsEmpleados);

/*	PROGRAMACIONES DE AULA COMPARTIRDAS
	@Query(value = "select DISTINCT progAula.ID_PROGAULA idAula, PROGAULA.TX_NOMBRE nombreAula, PROGDIDAC.X_CENTRO, PROGAULA.X_AULA idAulaVirtual, tlf_nombreempleado(relEmp.X_EMPLEADO) nombreEmpleado, ofm.X_MATERIAOMG idMateria,   " +
			" MATCURSO.S_MATERIAC || ' - ' || matcurso.T_ABREV nombreMateria, matcurso.T_ABREV abreviatura, ofg.X_OFERTAMATRIG idCurso, ofg.S_OFERTAMATRIG nombreCurso, (select ofg2.d_ofertamatrig from tlofematrgen ofg2 where ofg2.x_ofertamatrig = progdidac.X_NIVEADAP) nivelCurricular,   " +
			" PROGDIDAC.X_NIVEADAP idNivelCurricular, PROGDIDAC.NU_ANNO anno, progdidac.ID_PROGDIDAC idProgramacionDidactica,   " +
			" (SELECT COUNT(*) FROM EVA_RELPROGUNIDAD WHERE ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS countUnidadesProgramacion,   " +
			" (SELECT LISTAGG(uniprog.TX_NOMBRE , ', ') WITHIN GROUP (ORDER BY uniprog.NU_ORDENPRES, uniprog.TX_ABREV, uniprog.TX_NOMBRE) FROM EVA_RELPROGUNIDAD rel, EVA_UNIDADPROG uniprog  WHERE rel.ID_UNIDADPROG = uniprog.ID_UNIDADPROG  AND rel.ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS nombresUnidadesProgramacion,   " +
			" (SELECT COUNT(*) FROM EVA_RELPROGAULACT WHERE ID_PROGAULA = progAula.ID_PROGAULA) AS countActividades   " +
			" from EVA_PROGAULA progAula   " +
			" INNER join EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = progAula.ID_PROGAULA   " +
			" INNER JOIN EVA_PROGDIDAC progdidac ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC AND PROGDIDAC.NU_ANNO = :anno AND PROGDIDAC.X_CENTRO = :idCentro  " +
			" INNER JOIN TLMATOFEMATRG ofm ON ofm.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG   " +
			" INNER JOIN TLMATERIASCURSO MATCURSO ON MATCURSO.X_MATERIAC = OFM.X_MATERIAC   " +
			" INNER JOIN TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = PROGDIDAC.X_OFERTAMATRIG   " +
			" INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag ON uag.X_MATERIAOMG  = ofm.X_MATERIAOMG   " +
			" INNER JOIN DELPHOS.TLGRUACTPROALU grua ON  grua.X_GRUACTPROALU = uag.X_GRUACTPROALU AND grua.X_CENTRO = PROGDIDAC.X_CENTRO  AND grua.C_ANNO = :anno   " +
			" where relEmp.X_EMPLEADO = :idEmpleado  " +
			" UNION     " +
			"  select DISTINCT progAula.ID_PROGAULA idAula, PROGAULA.TX_NOMBRE nombreAula, PROGDIDAC.X_CENTRO, PROGAULA.X_AULA idAulaVirtual, tlf_nombreempleado(relEmp.X_EMPLEADO) nombreEmpleado, ofm.X_MATERIAOMG idMateria,    " +
			"  MATCURSO.S_MATERIAC || ' - ' || matcurso.T_ABREV nombreMateria, matcurso.T_ABREV abreviatura, ofg.X_OFERTAMATRIG idCurso, ofg.S_OFERTAMATRIG nombreCurso, (select ofg2.d_ofertamatrig from tlofematrgen ofg2 where ofg2.x_ofertamatrig = progdidac.X_NIVEADAP) nivelCurricular,    " +
			"  PROGDIDAC.X_NIVEADAP idNivelCurricular, PROGDIDAC.NU_ANNO anno, progdidac.ID_PROGDIDAC idProgramacionDidactica,    " +
			"  (SELECT COUNT(*) FROM EVA_RELPROGUNIDAD WHERE ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS countUnidadesProgramacion,    " +
			"  (SELECT LISTAGG(uniprog.TX_NOMBRE , ', ') WITHIN GROUP (ORDER BY uniprog.NU_ORDENPRES, uniprog.TX_ABREV, uniprog.TX_NOMBRE) FROM EVA_RELPROGUNIDAD rel, EVA_UNIDADPROG uniprog  WHERE rel.ID_UNIDADPROG = uniprog.ID_UNIDADPROG  AND rel.ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS nombresUnidadesProgramacion,    " +
			"  (SELECT COUNT(*) FROM EVA_RELPROGAULACT WHERE ID_PROGAULA = progAula.ID_PROGAULA) AS countActividades    " +
			"  from EVA_PROGAULA progAula    " +
			"  INNER join EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = progAula.ID_PROGAULA AND  RELEMP.X_EMPLEADO <> :idEmpleado   " +
			"  INNER JOIN EVA_PROGDIDAC progdidac ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC AND PROGDIDAC.NU_ANNO = :anno AND PROGDIDAC.X_CENTRO = :idCentro  " +
			"  INNER JOIN TLMATOFEMATRG ofm ON ofm.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG    " +
			"  INNER JOIN TLMATERIASCURSO MATCURSO ON MATCURSO.X_MATERIAC = OFM.X_MATERIAC    " +
			"  INNER JOIN TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = PROGDIDAC.X_OFERTAMATRIG,    " +
			"  (SELECT DISTINCT unigru.x_materiaOmg idMateriaOmg, unigru.x_unidad idUnidad, gruact.D_GRUACTPROALU idGrupo  " +
			"  FROM TLGRUACTPROALU gruact    " +
			"  INNER JOIN TLUNIAFEGRUACTPRO unigru ON unigru.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU    " +
			"  WHERE unigru.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU     " +
			"  and gruact.x_empleado = :idEmpleado    " +
			"  AND gruact.C_ANNO = :anno    " +
			"  AND gruact.X_CENTRO = :idCentro  " +
			"  ) grupActTable  " +
			"  WHERE grupActTable.idGrupo IN (  " +
			"  SELECT DISTINCT gruact.D_GRUACTPROALU   " +
			"  FROM TLGRUACTPROALU gruact    " +
			"  WHERE gruact.x_empleado = RELEMP.X_EMPLEADO    " +
			"  AND GRUACT.D_GRUACTPROALU IN (grupActTable.idGrupo)  " +
			"  AND gruact.C_ANNO = :anno    " +
			"  AND gruact.X_CENTRO = PROGDIDAC.X_CENTRO   " +
			"  )  " +
			"  AND  PROGDIDAC.X_MATERIAOMG = grupActTable.idMateriaOmg     " +
			"  AND grupActTable.idUnidad IN (SELECT DISTINCT er.X_UNIDAD FROM EVA_RELPROGAULALUM  er WHERE er.ID_PROGAULA = PROGAULA.ID_PROGAULA)    " +
			" order by nombreAula ASC", nativeQuery = true)
	List<ProgramacionAulaProjection> findByEmpleado(@Param("idEmpleado") Long idEmpleado,
													@Param("anno") Long anno,
													@Param("idCentro") Long idCentro);*/


	@Query(value = "select DISTINCT progAula.ID_PROGAULA idAula, PROGAULA.TX_NOMBRE nombreAula,  PROGDIDAC.X_CENTRO, PROGAULA.X_AULA idAulaVirtual, tlf_nombreempleado(relEmp.X_EMPLEADO) nombreEmpleado,  ofm.X_MATERIAOMG idMateria, " +
			"MATCURSO.S_MATERIAC || ' - ' || matcurso.T_ABREV nombreMateria, matcurso.T_ABREV abreviatura, ofg.X_OFERTAMATRIG idCurso, ofg.S_OFERTAMATRIG nombreCurso, (select ofg2.d_ofertamatrig from tlofematrgen ofg2 where ofg2.x_ofertamatrig = progdidac.X_NIVEADAP) nivelCurricular, " +
			"PROGDIDAC.X_NIVEADAP idNivelCurricular, PROGDIDAC.NU_ANNO anno, progdidac.ID_PROGDIDAC idProgramacionDidactica, " +
			"(SELECT COUNT(*) FROM EVA_RELPROGUNIDAD WHERE ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS countUnidadesProgramacion, " +
			"(SELECT LISTAGG(uniprog.TX_NOMBRE , ', ') WITHIN GROUP (ORDER BY uniprog.NU_ORDENPRES, uniprog.TX_ABREV, uniprog.TX_NOMBRE) FROM EVA_RELPROGUNIDAD rel, EVA_UNIDADPROG uniprog  WHERE rel.ID_UNIDADPROG = uniprog.ID_UNIDADPROG  AND rel.ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS nombresUnidadesProgramacion, " +
			"(SELECT COUNT(*) FROM EVA_RELPROGAULACT WHERE ID_PROGAULA = progAula.ID_PROGAULA) AS countActividades " +
			"from EVA_PROGAULA progAula " +
			"INNER join EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = progAula.ID_PROGAULA " +
			"INNER JOIN EVA_PROGDIDAC progdidac ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC AND PROGDIDAC.NU_ANNO = :anno AND PROGDIDAC.X_CENTRO = :idCentro  " +
			"INNER JOIN TLMATOFEMATRG ofm ON ofm.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG " +
			"INNER JOIN TLMATERIASCURSO MATCURSO ON MATCURSO.X_MATERIAC = OFM.X_MATERIAC " +
			"INNER JOIN TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = PROGDIDAC.X_OFERTAMATRIG " +
			"INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag ON uag.X_MATERIAOMG  = ofm.X_MATERIAOMG " +
			"INNER JOIN DELPHOS.TLGRUACTPROALU grua ON  grua.X_GRUACTPROALU = uag.X_GRUACTPROALU AND grua.X_CENTRO = PROGDIDAC.X_CENTRO  AND grua.C_ANNO = :anno   " +
			"where relEmp.X_EMPLEADO = :idEmpleado " +
			"order by nombreAula ASC", nativeQuery = true)
	List<ProgramacionAulaProjection> findByEmpleado(@Param("idEmpleado") Long idEmpleado,
													@Param("anno") Long anno,
													@Param("idCentro") Long idCentro);
	
	@Query("select distinct programacionAula from EvaProgramacionAula programacionAula join programacionAula.relacionesProgramacionAulaEmpleado empleado "
	+ "left join programacionAula.relacionesProgramacionAulaUnidad unidad "
	+ "where empleado.id in (?1) "
	+ "and unidad is null "
	+ "order by programacionAula.nombre asc")
	List<EvaProgramacionAula> findByEmpleadoNotUnidad(@Param("idEmpleado") Long idEmpleado);
	
	@Query(value = "SELECT DISTINCT OFEMATRG.X_OFERTAMATRIG idOfermatrig, MATRGEN.S_OFERTAMATRIG descripcionCorta, MATRGEN.X_TIPOEXP, MATRGEN.N_ORDEN " +
			"FROM TLMATOFEMATRG OFEMATRG " +
			"INNER JOIN TLGRUACTPROALU GRUACT ON GRUACT.X_EMPLEADO = :idEmpleado AND GRUACT.C_ANNO = :anno AND GRUACT.X_CENTRO = :idCentro " +
			"INNER JOIN TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.X_OFERTAMATRIG = OFEMATRG.X_OFERTAMATRIG AND PROGDIDAC.NU_ANNO = :anno AND PROGDIDAC.X_MATERIAOMG = UNIGRUACT.X_MATERIAOMG AND PROGDIDAC.LG_CERRADA = 1 " +
			"INNER JOIN TLOFEMATRGEN MATRGEN ON MATRGEN.X_OFERTAMATRIG = OFEMATRG.X_OFERTAMATRIG " +
			"WHERE (SELECT count(PROGDIDAC.X_MATERIAOMG) " + 
			"FROM EVA_PROGDIDAC didac  " + 
			"INNER JOIN TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU AND UNIGRUACT.X_MATERIAOMG = didac.X_MATERIAOMG  " + 
			"INNER JOIN TLMATOFEMATRG matofer ON matofer.X_MATERIAOMG = DIDAC.X_MATERIAOMG  " + 
			"INNER JOIN TLMATERIASCURSO matcurse ON matofer.X_MATERIAC  = matcurse.X_MATERIAC  " + 
			"WHERE DIDAC.X_OFERTAMATRIG = OFEMATRG.X_OFERTAMATRIG  " + 
			"AND didac.NU_ANNO = :anno  " + 
			"AND DIDAC.X_CENTRO = :idCentro) > 0 ORDER BY MATRGEN.X_TIPOEXP, MATRGEN.N_ORDEN", nativeQuery = true)
	List<CursoProgramacionAulaProjection> getCursoProgramacionAulaByCentroAndAnno(@Param("idEmpleado") Long idEmpleado, @Param("idCentro") Long idCentro, @Param("anno") Integer anno);

	@Query(value = "SELECT DISTINCT OFEMATRG.X_OFERTAMATRIG idOfermatrig, MATRGEN.S_OFERTAMATRIG descripcionCorta, MATRGEN.X_TIPOEXP, MATRGEN.N_ORDEN  " +
			"FROM TLMATOFEMATRG OFEMATRG  " +
			"INNER JOIN TLGRUACTPROALU GRUACT ON GRUACT.C_ANNO = :anno AND GRUACT.X_CENTRO = :idCentro  " +
			"INNER JOIN TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU  " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.X_OFERTAMATRIG = OFEMATRG.X_OFERTAMATRIG AND PROGDIDAC.NU_ANNO = :anno AND PROGDIDAC.X_MATERIAOMG = UNIGRUACT.X_MATERIAOMG AND PROGDIDAC.LG_CERRADA = 1  " +
			"INNER JOIN TLOFEMATRGEN MATRGEN ON MATRGEN.X_OFERTAMATRIG = OFEMATRG.X_OFERTAMATRIG  " +
			"WHERE (SELECT count(PROGDIDAC.X_MATERIAOMG)   " +
			"FROM EVA_PROGDIDAC didac    " +
			"INNER JOIN TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU AND UNIGRUACT.X_MATERIAOMG = didac.X_MATERIAOMG    " +
			"INNER JOIN TLMATOFEMATRG matofer ON matofer.X_MATERIAOMG = DIDAC.X_MATERIAOMG    " +
			"INNER JOIN TLMATERIASCURSO matcurse ON matofer.X_MATERIAC  = matcurse.X_MATERIAC    " +
			"WHERE DIDAC.X_OFERTAMATRIG = OFEMATRG.X_OFERTAMATRIG    " +
			"AND didac.NU_ANNO = :anno    " +
			"AND DIDAC.X_CENTRO = :idCentro) > 0 ORDER BY MATRGEN.X_TIPOEXP, MATRGEN.N_ORDEN", nativeQuery = true)
	List<CursoProgramacionAulaProjection> getCursoProgramacionAula_Dir(@Param("idCentro") Long idCentro, @Param("anno") Integer anno);
	
	@Query(value = "SELECT DISTINCT DIDAC.X_MATERIAOMG idMateriaOmg, matcurse.S_MATERIAC descripcionMateria, matcurse.T_ABREV abreviatura " +
			"FROM EVA_PROGDIDAC didac " +
			"INNER JOIN TLGRUACTPROALU GRUACT ON GRUACT.X_EMPLEADO = :idEmpleado AND GRUACT.C_ANNO = didac.NU_ANNO AND GRUACT.X_CENTRO = DIDAC.X_CENTRO " +
			"INNER JOIN TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU AND UNIGRUACT.X_MATERIAOMG = didac.X_MATERIAOMG " +
			"INNER JOIN TLMATOFEMATRG matofer ON matofer.X_MATERIAOMG = DIDAC.X_MATERIAOMG " +
			"INNER JOIN TLMATERIASCURSO matcurse ON matofer.X_MATERIAC  = matcurse.X_MATERIAC " +
			"WHERE DIDAC.X_OFERTAMATRIG = :idOfermatrig " +
			"AND didac.NU_ANNO = :anno " +
			"AND DIDAC.X_CENTRO = :idCentro", nativeQuery = true)
	List<MateriaProgramacionAulaProjection> getMateriasProgramacionAula(@Param("idEmpleado") Long idEmpleado, @Param("idCentro") Long idCentro, @Param("idOfermatrig") Long idOfermatrig, @Param("anno") Integer anno);

	@Query(value = "SELECT DISTINCT DIDAC.X_MATERIAOMG idMateriaOmg, matcurse.S_MATERIAC descripcionMateria, matcurse.T_ABREV abreviatura  " +
			"FROM EVA_PROGDIDAC didac  " +
			"INNER JOIN TLGRUACTPROALU GRUACT ON GRUACT.C_ANNO = didac.NU_ANNO AND GRUACT.X_CENTRO = DIDAC.X_CENTRO  " +
			"INNER JOIN TLUNIAFEGRUACTPRO UNIGRUACT ON UNIGRUACT.X_MATERIAOMG = didac.X_MATERIAOMG  " +
			"INNER JOIN TLMATOFEMATRG matofer ON matofer.X_MATERIAOMG = DIDAC.X_MATERIAOMG  " +
			"INNER JOIN TLMATERIASCURSO matcurse ON matofer.X_MATERIAC  = matcurse.X_MATERIAC  " +
			"WHERE DIDAC.X_OFERTAMATRIG = :idOfermatrig  " +
			"AND didac.NU_ANNO = :anno  " +
			"AND DIDAC.X_CENTRO = :idCentro", nativeQuery = true)
	List<MateriaProgramacionAulaProjection> getMateriasProgramacionAula_Dir(@Param("idCentro") Long idCentro, @Param("idOfermatrig") Long idOfermatrig, @Param("anno") Integer anno);

	@Query(value = "SELECT DISTINCT DIDAC.ID_PROGDIDAC id, didac.X_MATERIAOMG materiaomg, didac.X_OFERTAMATRIG ofertamatrig,  " + 
			"didac.X_CENTRO centro, didac.NU_ANNO anno, didac.LG_CERRADA lCerrada, didac.LG_ACNEAE lAcneae,  " + 
			"mat.D_MATERIAC nombreMateria, ofe.D_OFERTAMATRIG nombreCurso, didac.X_NIVEADAP  " + 
			"FROM EVA_PROGDIDAC didac  " + 
			"INNER JOIN TLMATOFEMATRG mog ON mog.X_MATERIAOMG = didac.X_MATERIAOMG  " + 
			"INNER JOIN TLOFEMATRGEN ofe ON ofe.X_OFERTAMATRIG = didac.X_OFERTAMATRIG  " + 
			"INNER JOIN TLMATERIASCURSO mat ON mat.X_MATERIAC = mog.X_MATERIAC  " + 
			"WHERE  DIDAC.LG_ACNEAE = 0   " + 
			"AND didac.LG_CERRADA = 1  " + 
			"AND didac.ID_PROGDIDAC = :idDidactica " + 
			"AND mog.X_OFERTAMATRIG = didac.X_OFERTAMATRIG " + 
			"UNION SELECT DISTINCT DIDAC.ID_PROGDIDAC id, didac.X_MATERIAOMG materiaomg, didac.X_OFERTAMATRIG ofertamatrig,  " + 
			"didac.X_CENTRO centro, didac.NU_ANNO anno, didac.LG_CERRADA lCerrada, didac.LG_ACNEAE lAcneae,  " + 
			"mat.D_MATERIAC nombreMateria, ofe.D_OFERTAMATRIG nombreCurso, didac.X_NIVEADAP  " + 
			"FROM EVA_PROGDIDAC didac  " + 
			"INNER JOIN TLMATOFEMATRG mog ON mog.X_MATERIAOMG = didac.X_MATERIAOMG  " + 
			"INNER JOIN TLOFEMATRGEN ofe ON ofe.X_OFERTAMATRIG = didac.X_OFERTAMATRIG  " + 
			"INNER JOIN TLMATERIASCURSO mat ON mat.X_MATERIAC = mog.X_MATERIAC  " + 
			"WHERE DIDAC.LG_ACNEAE = 1  " + 
			"AND didac.LG_CERRADA = 1  " + 
			"AND didac.ID_PROGDIDAC = :idDidactica " + 
			"AND mog.X_OFERTAMATRIG = didac.X_OFERTAMATRIG", nativeQuery = true)
	EvaProgramacionDidacticaAulaProjection programacionDidacticaAulas(@Param("idDidactica") Long idDidactica);
	
	@Query(value = "SELECT DISTINCT mat.X_OFERTAMATRIG idOfermatrig , ofer.S_OFERTAMATRIG descripcionCorta "
	+ "FROM EVA_PROGAULA programacionAula, TLMATOFEMATRG mat "
	+ "INNER JOIN TLOFEMATRGEN ofer ON  mat.X_OFERTAMATRIG  = OFER.X_OFERTAMATRIG "
	+ "WHERE NOT EXISTS (SELECT 1 FROM EVA_RELPROGAULAUNIDAD aulaUnidad "
	+ "WHERE AULAUNIDAD.ID_PROGAULA = PROGRAMACIONAULA.ID_PROGAULA) "
	+ "AND mat.X_MATERIAOMG IN (SELECT DISTINCT DIDAC.X_MATERIAOMG  "
	+ "FROM EVA_PROGDIDAC didac "
	+ "WHERE didac.X_CENTRO = :idCentro AND DIDAC.ID_PROGDIDAC = programacionAula.ID_PROGDIDAC "
	+ "AND DIDAC.NU_ANNO = :anno AND didac.LG_CERRADA = 1)", nativeQuery = true)
	List<CursoProgramacionAulaProjection> getCursoPlantilla(@Param("anno") Integer anno, @Param("idCentro") Long idCentro);
	
	@Query(value = "SELECT DECODE(COUNT(1), 0, 'false', 'true') AS docenteCumpleRequisitos FROM DELPHOS.TLEMPLEADOS emp "
	+ "INNER JOIN DELPHOS.TLGRUACTPROALU gap ON gap.X_EMPLEADO = emp.X_EMPLEADO "
	+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag ON uag.X_GRUACTPROALU = gap.X_GRUACTPROALU "
	+ "INNER JOIN DELPHOS.TLHORARIOSR hor ON hor.X_EMPLEADO = gap.X_EMPLEADO AND hor.F_TOMAPOS = gap.F_TOMAPOS AND hor.C_ANNO = gap.C_ANNO AND hor.X_ACTIVIDAD = gap.X_ACTIVIDAD "
	+ "INNER JOIN DELPHOS.TLMATOFEMATRG mog ON mog.X_MATERIAOMG = uag.X_MATERIAOMG "
	+ "INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = mog.X_OFERTAMATRIG "
	+ "INNER JOIN DELPHOS.EVA_PROGDIDAC pd ON pd.X_MATERIAOMG = mog.X_MATERIAOMG AND pd.X_OFERTAMATRIG = omg.X_OFERTAMATRIG AND pd.X_CENTRO = gap.X_CENTRO AND pd.NU_ANNO = gap.C_ANNO "
	+ "WHERE emp.X_EMPLEADO = :idEmpleado AND gap.C_ANNO = :anno AND gap.X_CENTRO = :idCentro AND pd.LG_CERRADA = 1", nativeQuery = true)
	Boolean isDocenteValidoProgramacionAula(@Param("idEmpleado") Long idEmpleado, @Param("anno") Integer anno, @Param("idCentro") Long idCentro);
	

/*PROGRAMACIONES DE AULA COMPARTIRDAS
	@Query(value = "SELECT DISTINCT aula.ID_PROGAULA   " +
			" FROM EVA_PROGDIDAC didac   " +
			" INNER JOIN EVA_PROGAULA aula ON aula.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC   " +
			" INNER join EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = aula.ID_PROGAULA  " +
			" AND relEmp.X_EMPLEADO IN (:idEmpleados)   " +
			" AND DIDAC.X_MATERIAOMG = :idMateriaOmg   " +
			" AND DIDAC.X_OFERTAMATRIG = :idOfermatrig   " +
			" AND DIDAC.X_CENTRO = :idCentro   " +
			" AND DIDAC.NU_ANNO = :anno" +
			" UNION " +
			" SELECT DISTINCT aula.ID_PROGAULA   " +
			" FROM EVA_PROGDIDAC didac   " +
			" INNER JOIN EVA_PROGAULA aula ON aula.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC   " +
			" INNER join EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = aula.ID_PROGAULA  " +
			" AND relEmp.X_EMPLEADO NOT IN (:idEmpleados)   " +
			" AND DIDAC.X_MATERIAOMG = :idMateriaOmg   " +
			" AND DIDAC.X_OFERTAMATRIG = :idOfermatrig   " +
			" AND DIDAC.X_CENTRO = :idCentro   " +
			" AND DIDAC.NU_ANNO = :anno," +
			" (SELECT DISTINCT unigru.x_materiaOmg idMateriaOmg, unigru.x_unidad idUnidad, gruact.D_GRUACTPROALU idGrupo " +
			" FROM TLGRUACTPROALU gruact   " +
			" INNER JOIN TLUNIAFEGRUACTPRO unigru ON unigru.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU   " +
			" WHERE unigru.X_GRUACTPROALU = GRUACT.X_GRUACTPROALU    " +
			" AND gruact.x_empleado in (:idEmpleados)   " +
			" AND gruact.C_ANNO = :anno   " +
			" AND gruact.X_CENTRO = :idCentro  " +
			" ) grupActTable " +
			" WHERE grupActTable.idGrupo IN ( " +
			" SELECT DISTINCT gruact.D_GRUACTPROALU  " +
			" FROM TLGRUACTPROALU gruact   " +
			" WHERE gruact.x_empleado = RELEMP.X_EMPLEADO   " +
			" AND GRUACT.D_GRUACTPROALU IN (grupActTable.idGrupo) " +
			" AND gruact.C_ANNO = :anno   " +
			" AND gruact.X_CENTRO = :idCentro  " +
			" ) " +
			" AND  DIDAC.X_MATERIAOMG = grupActTable.idMateriaOmg    " +
			" AND grupActTable.idUnidad IN (SELECT DISTINCT er.X_UNIDAD FROM EVA_RELPROGAULALUM  er WHERE er.ID_PROGAULA = aula.ID_PROGAULA)", nativeQuery = true)
	List<Long> findAllByDidac(@Param("idMateriaOmg") Long idMateriaOmg ,@Param("idOfermatrig") Long idOfermatrig,
	@Param("idCentro") Long idCentro, @Param("anno") Integer anno, @Param("idEmpleados") List<Long> idsEmpleadosCompartidas );*/


	@Query(value = "SELECT DISTINCT aula.ID_PROGAULA   " +
			" FROM EVA_PROGDIDAC didac   " +
			" INNER JOIN EVA_PROGAULA aula ON aula.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC   " +
			" INNER join EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = aula.ID_PROGAULA  " +
			" AND relEmp.X_EMPLEADO IN (:idEmpleados)   " +
			" AND DIDAC.X_MATERIAOMG = :idMateriaOmg   " +
			" AND DIDAC.X_OFERTAMATRIG = :idOfermatrig   " +
			" AND DIDAC.X_CENTRO = :idCentro   " +
			" AND DIDAC.NU_ANNO = :anno", nativeQuery = true)
	List<Long> findAllByDidac(@Param("idMateriaOmg") Long idMateriaOmg ,@Param("idOfermatrig") Long idOfermatrig,
	@Param("idCentro") Long idCentro, @Param("anno") Integer anno, @Param("idEmpleados") List<Long> idsEmpleadosCompartidas );

	@Query(value = "SELECT DISTINCT aula.ID_PROGAULA "
			+ "FROM EVA_PROGDIDAC didac "
			+ "INNER JOIN EVA_PROGAULA aula ON aula.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC "
			+ "INNER join EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = aula.ID_PROGAULA "
			+ "AND DIDAC.X_MATERIAOMG = :idMateriaOmg "
			+ "AND DIDAC.X_OFERTAMATRIG = :idOfermatrig "
			+ "AND DIDAC.X_CENTRO = :idCentro "
			+ "AND DIDAC.NU_ANNO = :anno", nativeQuery = true)
	List<Long> findAllByDidac_dir(@Param("idMateriaOmg") Long idMateriaOmg ,@Param("idOfermatrig") Long idOfermatrig,
							  @Param("idCentro") Long idCentro, @Param("anno") Integer anno);
	
	@Query(value = "SELECT DISTINCT aula.ID_PROGAULA " + 
			" FROM EVA_PROGDIDAC didac " + 
			" INNER JOIN EVA_PROGAULA aula ON aula.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC " + 
			" INNER join EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = aula.ID_PROGAULA  " + 
			" AND DIDAC.X_MATERIAOMG = :idMateriaOmg " + 
			" AND DIDAC.X_OFERTAMATRIG = :idOfermatrig " + 
			" AND DIDAC.X_CENTRO = :idCentro " + 
			" AND DIDAC.NU_ANNO = :anno ", nativeQuery = true)
	List<Long> findAllDirector(@Param("idMateriaOmg") Long idMateriaOmg ,@Param("idOfermatrig") Long idOfermatrig,
	@Param("idCentro") Long idCentro, @Param("anno") Integer anno);
	
	@Query(value = "SELECT crieva.X_CRIEVA idCriterioEvaluacion, crieva.T_ABREV abrevCriterioEvaluacion, crieva.D_CRIEVA descripcionCriterioEvaluacion, "
			+ "act.ID_ACTIVIDAD idActividad, act.NU_ORDENPRES orden, act.TX_ABREV abrevActividad, act.TX_NOMBRE nombreActividad, "
			+ "up.ID_UNIDADPROG idUnidadProgramacion, up.TX_ABREV abrevUnidadProgramacion, up.TX_NOMBRE nombreUnidadProgramacion,  "
			+ "comesp.X_COMESP idCompetenciaEspecifica, comesp.T_ABREV abrevCompetenciaEspecifica, comesp.D_COMESP descripcionCompetenciaEspecifica, "
			+ "DECODE(ractcrieva.LG_PONDERADA, 1, 'true', 'false') esPonderada, ractcrieva.NU_PESO peso "
			+ "FROM DELPHOS.EVA_PROGAULA pa "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULACT rpaact ON rpaact.ID_PROGAULA = pa.ID_PROGAULA "
			+ "INNER JOIN DELPHOS.EVA_RELPROGUNIDAD rpdup ON rpdup.ID_PROGDIDAC = pa.ID_PROGDIDAC "
			+ "INNER JOIN DELPHOS.EVA_UNIDADPROG up ON up.ID_UNIDADPROG = rpdup.ID_UNIDADPROG "
			+ "INNER JOIN DELPHOS.EVA_ACTIVIDAD act ON act.ID_ACTIVIDAD = rpaact.ID_ACTIVIDAD AND act.ID_UNIDADPROG = up.ID_UNIDADPROG "
			+ "INNER JOIN DELPHOS.EVA_RELACTCRIEVA ractcrieva ON ractcrieva.ID_ACTIVIDAD = act.ID_ACTIVIDAD "
			+ "INNER JOIN DELPHOS.EVA_RELUNIPROGCRIEVA rupcrieva ON rupcrieva.ID_UNIDADPROG = up.ID_UNIDADPROG "
			+ "INNER JOIN DELPHOS.TLCRIEVA crieva ON crieva.X_CRIEVA = ractcrieva.X_CRIEVA AND crieva.X_CRIEVA = rupcrieva.X_CRIEVA "
			+ "INNER JOIN DELPHOS.TLCOMESP comesp ON comesp.X_COMESP = crieva.X_COMESP "
			+ "WHERE pa.ID_PROGAULA = :idProgramacionAula "
			+ "AND ('-1' = :idCriterioEvaluacion OR crieva.X_CRIEVA = :idCriterioEvaluacion) "
			+ "AND ('-1' = :idCompetenciaEspecifica OR comesp.X_COMESP = :idCompetenciaEspecifica) "
			+ "ORDER BY act.NU_ORDENPRES",
			countQuery = "SELECT COUNT(*) FROM DELPHOS.EVA_PROGAULA pa "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULACT rpaact ON rpaact.ID_PROGAULA = pa.ID_PROGAULA "
			+ "INNER JOIN DELPHOS.EVA_RELPROGUNIDAD rpdup ON rpdup.ID_PROGDIDAC = pa.ID_PROGDIDAC "
			+ "INNER JOIN DELPHOS.EVA_UNIDADPROG up ON up.ID_UNIDADPROG = rpdup.ID_UNIDADPROG "
			+ "INNER JOIN DELPHOS.EVA_ACTIVIDAD act ON act.ID_ACTIVIDAD = rpaact.ID_ACTIVIDAD AND act.ID_UNIDADPROG = up.ID_UNIDADPROG "
			+ "INNER JOIN DELPHOS.EVA_RELACTCRIEVA ractcrieva ON ractcrieva.ID_ACTIVIDAD = act.ID_ACTIVIDAD "
			+ "INNER JOIN DELPHOS.EVA_RELUNIPROGCRIEVA rupcrieva ON rupcrieva.ID_UNIDADPROG = up.ID_UNIDADPROG "
			+ "INNER JOIN DELPHOS.TLCRIEVA crieva ON crieva.X_CRIEVA = ractcrieva.X_CRIEVA AND crieva.X_CRIEVA = rupcrieva.X_CRIEVA "
			+ "INNER JOIN DELPHOS.TLCOMESP comesp ON comesp.X_COMESP = crieva.X_COMESP "
			+ "WHERE pa.ID_PROGAULA = :idProgramacionAula "
			+ "AND ('-1' = :idCriterioEvaluacion OR crieva.X_CRIEVA = :idCriterioEvaluacion) "
			+ "AND ('-1' = :idCompetenciaEspecifica OR comesp.X_COMESP = :idCompetenciaEspecifica)", nativeQuery = true)
	Page<CriterioActividadProjection> getCriteriosActividades(@Param("idProgramacionAula") Long idProgramacionAula, @Param("idCompetenciaEspecifica") Long idCompetenciaEspecifica, @Param("idCriterioEvaluacion") Long idCriterioEvaluacion, Pageable pageable);
	
	@Query(value = "SELECT SUM(ractcrieva.NU_PESO) sumPesos "
			+ "FROM DELPHOS.EVA_PROGAULA pa "
			+ "INNER JOIN DELPHOS.EVA_RELPROGAULACT rpaact ON rpaact.ID_PROGAULA = pa.ID_PROGAULA "
			+ "INNER JOIN DELPHOS.EVA_RELPROGUNIDAD rpdup ON rpdup.ID_PROGDIDAC = pa.ID_PROGDIDAC "
			+ "INNER JOIN DELPHOS.EVA_UNIDADPROG up ON up.ID_UNIDADPROG = rpdup.ID_UNIDADPROG "
			+ "INNER JOIN DELPHOS.EVA_ACTIVIDAD act ON act.ID_ACTIVIDAD = rpaact.ID_ACTIVIDAD AND act.ID_UNIDADPROG = up.ID_UNIDADPROG "
			+ "INNER JOIN DELPHOS.EVA_RELACTCRIEVA ractcrieva ON ractcrieva.ID_ACTIVIDAD = act.ID_ACTIVIDAD "
			+ "INNER JOIN DELPHOS.EVA_RELUNIPROGCRIEVA rupcrieva ON rupcrieva.ID_UNIDADPROG = up.ID_UNIDADPROG "
			+ "INNER JOIN DELPHOS.TLCRIEVA crieva ON crieva.X_CRIEVA = ractcrieva.X_CRIEVA AND crieva.X_CRIEVA = rupcrieva.X_CRIEVA "
			+ "WHERE pa.ID_PROGAULA = :idProgramacionAula AND ractcrieva.LG_PONDERADA = 1 "
			+ "AND ('-1' = :idCriterioEvaluacion OR crieva.X_CRIEVA = :idCriterioEvaluacion) "
			+ "AND ('-1' = :idCompetenciaEspecifica OR crieva.X_COMESP = :idCompetenciaEspecifica)", nativeQuery = true)
	Integer getSumaPesosCriteriosActividadesProgramacionAula(@Param("idProgramacionAula") Long idProgramacionAula, @Param("idCompetenciaEspecifica") Long idCompetenciaEspecifica, @Param("idCriterioEvaluacion") Long idCriterioEvaluacion);
	
	@Query(value = "SELECT DISTINCT DIDAC.ID_PROGDIDAC id, didac.X_MATERIAOMG materiaomg, didac.X_OFERTAMATRIG ofertamatrig, "
			+ "didac.X_CENTRO centro, didac.NU_ANNO anno, didac.LG_CERRADA lCerrada, didac.LG_ACNEAE lAcneae, "
			+ "mat.D_MATERIAC nombreMateria, ofe.D_OFERTAMATRIG nombreCurso "
			+ "FROM EVA_PROGDIDAC didac "
			+ "INNER JOIN TLMATOFEMATRG mog ON mog.X_MATERIAOMG = didac.X_MATERIAOMG "
			+ "INNER JOIN TLOFEMATRGEN ofe ON ofe.X_OFERTAMATRIG = didac.X_OFERTAMATRIG "
			+ "INNER JOIN TLMATERIASCURSO mat ON mat.X_MATERIAC = mog.X_MATERIAC "
			+ "WHERE DIDAC.ID_PROGDIDAC = :idProgramacionDidactica", nativeQuery = true)
	EvaProgramacionDidacticaAulaProjection programacionDidacticaAulaById(@Param("idProgramacionDidactica") Long idProgramacionDidactica);

	@Query(value = "select ofg.d_ofertamatrig " +
			"from tlofematrgen ofg " +
			"inner join EVA_PROGDIDAC progdidac ON progdidac.X_NIVEADAP = ofg.x_ofertamatrig " +
			"AND progdidac.ID_PROGDIDAC = :idProgramacionDidactica", nativeQuery = true)
	String getNivelCurricularDeProgramacionDidactica(@Param("idProgramacionDidactica") Long idProgramacionDidactica);

	@Query(value = "select progAula.ID_PROGAULA idAula, PROGAULA.TX_NOMBRE nombreAula, tlf_nombreempleado(relEmp.X_EMPLEADO) nombreEmpleado, PROGAULA.X_AULA idAulaVirtual, ofm.X_MATERIAOMG idMateria,  " +
            "MATCURSO.S_MATERIAC || ' - ' || matcurso.T_ABREV nombreMateria, matcurso.T_ABREV abreviatura, ofg.X_OFERTAMATRIG idCurso, ofg.S_OFERTAMATRIG nombreCurso, (select ofg2.d_ofertamatrig from tlofematrgen ofg2 where ofg2.x_ofertamatrig = progdidac.X_NIVEADAP) nivelCurricular,  " +
			"PROGDIDAC.X_NIVEADAP idNivelCurricular, PROGDIDAC.NU_ANNO anno, progdidac.ID_PROGDIDAC idProgramacionDidactica, " +
			"(SELECT COUNT(*) FROM EVA_RELPROGUNIDAD WHERE ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS countUnidadesProgramacion,  " +
			"(SELECT LISTAGG(uniprog.TX_NOMBRE , ', ') WITHIN GROUP (ORDER BY uniprog.NU_ORDENPRES, uniprog.TX_ABREV, uniprog.TX_NOMBRE) FROM EVA_RELPROGUNIDAD rel, EVA_UNIDADPROG uniprog  WHERE rel.ID_UNIDADPROG = uniprog.ID_UNIDADPROG  AND rel.ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS nombresUnidadesProgramacion,  " +
			"(SELECT COUNT(*) FROM EVA_RELPROGAULACT WHERE ID_PROGAULA = progAula.ID_PROGAULA) AS countActividades  " +
			"from EVA_PROGAULA progAula  " +
			"INNER join EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = progAula.ID_PROGAULA  " +
			"INNER JOIN EVA_PROGDIDAC progdidac ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC AND PROGDIDAC.NU_ANNO = :anno  " +
			"INNER JOIN TLMATOFEMATRG ofm ON ofm.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG  " +
			"INNER JOIN TLMATERIASCURSO MATCURSO ON MATCURSO.X_MATERIAC = OFM.X_MATERIAC  " +
			"INNER JOIN TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = PROGDIDAC.X_OFERTAMATRIG  " +
			"where relEmp.X_EMPLEADO IN (:idsEmpleado) AND PROGDIDAC.X_CENTRO = :idCentro  " +
			"order by progAula.TX_NOMBRE ASC", nativeQuery = true)
	List<ProgramacionAulaProjection> findByEmpleados(@Param("idsEmpleado") Long[] idsEmpleado,
													@Param("anno") Long anno,
													@Param("idCentro") Long idCentro);

	@Query(value = "SELECT DISTINCT progAula.ID_PROGAULA idAula, PROGAULA.TX_NOMBRE nombreAula,  tlf_nombreempleado(relEmp.X_EMPLEADO) nombreEmpleado, PROGAULA.X_AULA idAulaVirtual, ofm.X_MATERIAOMG idMateria,   " +
			"MATCURSO.S_MATERIAC || ' - ' || matcurso.T_ABREV nombreMateria, matcurso.T_ABREV abreviatura, ofg.X_OFERTAMATRIG idCurso, ofg.S_OFERTAMATRIG nombreCurso, (select ofg2.d_ofertamatrig from tlofematrgen ofg2 where ofg2.x_ofertamatrig = progdidac.X_NIVEADAP) nivelCurricular,   " +
			"PROGDIDAC.X_NIVEADAP idNivelCurricular, PROGDIDAC.NU_ANNO anno, progdidac.ID_PROGDIDAC idProgramacionDidactica, " +
			"(SELECT COUNT(*) FROM EVA_RELPROGUNIDAD WHERE ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS countUnidadesProgramacion, " +
			"(SELECT LISTAGG(uniprog.TX_NOMBRE , ', ') WITHIN GROUP (ORDER BY uniprog.NU_ORDENPRES, uniprog.TX_ABREV, uniprog.TX_NOMBRE) FROM EVA_RELPROGUNIDAD rel, EVA_UNIDADPROG uniprog  WHERE rel.ID_UNIDADPROG = uniprog.ID_UNIDADPROG  AND rel.ID_PROGDIDAC = progdidac.ID_PROGDIDAC) AS nombresUnidadesProgramacion, " +
			"(SELECT COUNT(*) FROM EVA_RELPROGAULACT WHERE ID_PROGAULA = progAula.ID_PROGAULA) AS countActividades " +
			"from EVA_PROGAULA progAula " +
			"INNER JOIN EVA_RELPROGAULAEMP relEmp ON relemp.ID_PROGAULA = progAula.ID_PROGAULA " +
			"INNER JOIN EVA_PROGDIDAC progdidac ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC AND PROGDIDAC.NU_ANNO = :anno " +
			"INNER JOIN TLMATOFEMATRG ofm ON ofm.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG " +
			"INNER JOIN TLMATERIASCURSO MATCURSO ON MATCURSO.X_MATERIAC = OFM.X_MATERIAC " +
			"INNER JOIN TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = PROGDIDAC.X_OFERTAMATRIG " +
			"INNER JOIN TLUNIDADESCEN unicen ON unicen.C_ANNO = progdidac.NU_ANNO AND unicen.X_CENTRO = progdidac.X_CENTRO " +
			"INNER JOIN tluniafegruactpro uag ON uag.X_UNIDAD = unicen.x_UNIDAD " +
			"INNER JOIN TLMATALU MA ON MA.X_UNIDAD = unicen.X_UNIDAD " +
			"INNER JOIN EVA_RELPROGAULALUM RPAA ON RPAA.X_MATRICULA = MA.X_MATRICULA AND RPAA.ID_PROGAULA = progAula.ID_PROGAULA " +
			"where ofg.X_OFERTAMATRIG = :idOfertaMatrig AND PROGDIDAC.X_CENTRO = :idCentro " +
			"AND ofm.X_MATERIAOMG = :idMateriaOMG AND (-1 = :idUnidad OR unicen.X_UNIDAD = :idUnidad) " +
			"AND progdidac.X_MATERIAOMG = uag.X_MATERIAOMG " +
			"order by progAula.TX_NOMBRE ASC", nativeQuery = true)
	List<ProgramacionAulaProjection> findProgByCursoMateriaUnidad(@Param("anno") Long anno,
																  @Param("idCentro") Long idCentro,
																  @Param("idOfertaMatrig") Long idOfertaMatrig,
																  @Param("idMateriaOMG") Long idMateriaOMG,
																  @Param("idUnidad") Long idUnidad);

	@Query(value = "SELECT DECODE(COUNT(1), 0, 'false', 'true') AS docenteCumpleRequisitos FROM DELPHOS.TLEMPLEADOS emp "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU gap ON gap.X_EMPLEADO = emp.X_EMPLEADO "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag ON uag.X_GRUACTPROALU = gap.X_GRUACTPROALU "
			+ "INNER JOIN DELPHOS.TLHORARIOSR hor ON hor.X_EMPLEADO = gap.X_EMPLEADO AND hor.F_TOMAPOS = gap.F_TOMAPOS AND hor.C_ANNO = gap.C_ANNO AND hor.X_ACTIVIDAD = gap.X_ACTIVIDAD "
			+ "INNER JOIN DELPHOS.TLMATOFEMATRG mog ON mog.X_MATERIAOMG = uag.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = mog.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.EVA_PROGDIDAC pd ON pd.X_MATERIAOMG = mog.X_MATERIAOMG AND pd.X_OFERTAMATRIG = omg.X_OFERTAMATRIG AND pd.X_CENTRO = gap.X_CENTRO AND pd.NU_ANNO = gap.C_ANNO "
			+ "WHERE emp.X_EMPLEADO IN (:idsEmpleado) AND gap.C_ANNO = :anno AND gap.X_CENTRO = :idCentro AND pd.LG_CERRADA = 1", nativeQuery = true)
	Boolean isDocenteValidoProgramacionAula(@Param("idsEmpleado") Long[] idsEmpleado, @Param("anno") Integer anno, @Param("idCentro") Long idCentro);

	@Query(value = "SELECT DISTINCT  emp.X_EMPLEADO idEmpleado, tlf_nombreempleado(emp.X_EMPLEADO) nombre " +
			"FROM TLEMPLEADOS emp " +
			"INNER JOIN EVA_RELPROGAULAEMP relEmp ON relEmp.X_EMPLEADO = emp.X_EMPLEADO " +
			"INNER JOIN EVA_PROGAULA progAula ON relemp.ID_PROGAULA = progAula.ID_PROGAULA " +
			"INNER JOIN EVA_PROGDIDAC progdidac ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC AND PROGDIDAC.NU_ANNO = :anno " +
			"WHERE PROGDIDAC.X_CENTRO = :idCentro " +
			"ORDER BY nombre", nativeQuery = true)
	List<DocenteProgAulaProjection> getDocentesProgAula(@Param("anno") Long anno,
														@Param("idCentro") Long idCentro);



	@Query(value = "select ofg2.d_ofertamatrig from tlofematrgen ofg2 where ofg2.x_ofertamatrig = :niveAdapt", nativeQuery = true)
	String getNombreNivelAdaptacion(@Param("niveAdapt") Long niveAdapt);
}