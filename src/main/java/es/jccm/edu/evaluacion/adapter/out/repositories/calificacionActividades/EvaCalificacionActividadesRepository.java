package es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades;

import java.sql.Blob;
import java.util.List;

import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.MateriasValoracionProjection;
import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.UnidadesValoracionProjection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.UnidadProgramacionProjection;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.ValCriActAluProjection;
import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.ConvocatoriaProjection;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaActividad;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaCalificacionActividadesRepository extends AbstractRepository<EvaActividad, Long, QEvaActividad> {
	
	@Query(value="SELECT DISTINCT cce.x_convcentro idconvcentro, cco.x_convcentroomc idconvcentroomc, " +
			"cce.x_convocatoria  idtipoconv, cce.d_convocatoria  convocatoria, " +
			"cco.f_feciniconomc  fechainicio, cco.f_fecfinconomc  fechafin, cce.n_orden " +
			"FROM tlconvcentros cce " +
			"INNER JOIN tlconvcenomc cco ON cco.x_convcentro = cce.x_convcentro " +
			"INNER JOIN eva_actividad act ON act.x_convcentroomc = cco.x_convcentroomc " +
			"INNER JOIN eva_relprogaulact relproagaulaact ON relproagaulaact.id_progaula = :idProgramacionAula " +
			"AND relproagaulaact.id_actividad = act.id_actividad ORDER BY cce.x_convcentro",nativeQuery = true)
	List<ConvocatoriaProjection> getConvocatorias(@Param("idProgramacionAula") Long idProgramacionAula);

	@Query(value="SELECT DISTINCT uniprog.ID_UNIDADPROG id, uniprog.TX_NOMBRE nombre, uniprog.TX_NOMBRE, " +
			"uniprog.TX_ABREV abreviatura, uniprog.DS_UNIDAD descripcion, uniprog.NU_ORDENPRES  orden " +
			"FROM eva_unidadprog uniprog " +
			"INNER JOIN eva_relprogaulact rel ON rel.id_progaula = :idProgramacionAula  " +
			"INNER JOIN eva_actividad act ON act.id_actividad = rel.id_actividad " +
			"AND act.id_unidadprog = uniprog.id_unidadprog " +
			"AND (:idConvCentroOmc = -1 OR act.x_convcentroomc = :idConvCentroOmc)",nativeQuery = true)
	List<UnidadProgramacionProjection> getUnidadesProgramacion(@Param("idConvCentroOmc") Long idConvCentroOmc,
			@Param("idProgramacionAula") Long idProgramacionAula);

	@Query(value = "SELECT DISTINCT mat.X_MATERIAOMG idMateria, ciclo.X_ETAPADEPENDEDE idEtapa,  " +
			"matCurso.t_abrev || ' - '  || ofg.d_ofertamatrig materia, mat.x_ofertamatrig idOfertaMatrig  " +
			"FROM TLUNIAFEGRUACTPRO grupo  " +
			"INNER JOIN TLGRUACTPROALU grupoActividad ON grupo.X_GRUACTPROALU = grupoActividad.X_GRUACTPROALU " +
			"INNER JOIN TLMATOFEMATRG mat ON grupo.X_MATERIAOMG = mat.X_MATERIAOMG " +
			"INNER JOIN TLUNIDADESCEN unidades ON grupo.X_UNIDAD = unidades.X_UNIDAD " +
			"INNER JOIN TLCENTROS centros ON unidades.X_CENTRO = centros.X_CENTRO " +
			"INNER JOIN TLMATERIASCURSO matCurso ON mat.X_MATERIAC = matCurso.X_MATERIAC " +
			"INNER JOIN TLOFEMATRGEN OFG ON MAT.X_OFERTAMATRIG=OFG.X_OFERTAMATRIG " +
			"INNER JOIN TLCURSOMODA modalidad ON matCurso.x_cursomod = modalidad.x_cursomod " +
			"INNER JOIN TLETAPAS curso ON curso.x_etapa = modalidad.x_etapa " +
			"INNER JOIN TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA " +
			"INNER JOIN TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA " +
			"WHERE grupoActividad.X_EMPLEADO = :idEmpleado AND grupoActividad.C_ANNO = :anno " +
			"AND centros.C_CODIGO = :codigoCentro AND " +
			"(etapa.D_ETAPA like '%(LOMLOE)%' OR ciclo.D_ETAPA like '%(LOMLOE)%') " +
			"ORDER BY materia", nativeQuery = true)
	List<MateriasValoracionProjection> getMateriasProgAula(
			@Param("idEmpleado") Long idEmpleado,
			@Param("anno") Long anno,
			@Param("codigoCentro") Long codigoCentro);

	@Query(value = "SELECT DISTINCT mat.X_MATERIAOMG idMateria, ciclo.X_ETAPADEPENDEDE idEtapa,   " +
			"matCurso.t_abrev || ' - '  || ofg.d_ofertamatrig materia, mat.x_ofertamatrig idOfertaMatrig   " +
			"FROM TLUNIAFEGRUACTPRO grupo   " +
			"INNER JOIN TLGRUACTPROALU grupoActividad ON grupo.X_GRUACTPROALU = grupoActividad.X_GRUACTPROALU  " +
			"INNER JOIN TLMATOFEMATRG mat ON grupo.X_MATERIAOMG = mat.X_MATERIAOMG  " +
			"INNER JOIN TLUNIDADESCEN unidades ON grupo.X_UNIDAD = unidades.X_UNIDAD  " +
			"INNER JOIN TLCENTROS centros ON unidades.X_CENTRO = centros.X_CENTRO  " +
			"INNER JOIN TLMATERIASCURSO matCurso ON mat.X_MATERIAC = matCurso.X_MATERIAC  " +
			"INNER JOIN TLOFEMATRGEN OFG ON MAT.X_OFERTAMATRIG=OFG.X_OFERTAMATRIG  " +
			"INNER JOIN TLCURSOMODA modalidad ON matCurso.x_cursomod = modalidad.x_cursomod  " +
			"INNER JOIN TLETAPAS curso ON curso.x_etapa = modalidad.x_etapa  " +
			"INNER JOIN TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA  " +
			"INNER JOIN TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA  " +
			"WHERE mat.x_ofertamatrig = :idOfertaMatrig AND grupoActividad.C_ANNO = :anno  " +
			"AND centros.C_CODIGO = :codigoCentro AND  " +
			"(etapa.D_ETAPA like '%(LOMLOE)%' OR ciclo.D_ETAPA like '%(LOMLOE)%')  " +
			"ORDER BY materia", nativeQuery = true)
	List<MateriasValoracionProjection> getMateriasProgAula_Dir(
			@Param("idOfertaMatrig") Long idOfertaMatrig,
			@Param("anno") Long anno,
			@Param("codigoCentro") Long codigoCentro);
	
	
	@Query(value = "SELECT VALCRIACTALU.X_CALIFICA idCalifica, CAL.D_CALIFICA calificacion, cal.N_NUMERO numero , crieva.NU_PESO peso , VALCRIACTALU.F_ACTUALIZA fechaActualiza  " + 
			"FROM EVA_VALCRIACTALU valcriactalu " + 
			"INNER JOIN EVA_RELACTCRIEVA crieva ON crieva.ID_RELACTCRIEVA = valcriactalu.ID_RELACTCRIEVA AND crieva.X_CRIEVA = :idCriterio " + 
			"INNER JOIN EVA_RELACTALUM ALU ON ALU.ID_RELACTALUM = VALCRIACTALU.ID_RELACTALUM AND alu.X_MATRICULA = :idMatricula " + 
			"INNER JOIN EVA_ACTIVIDAD ACT ON ACT.ID_ACTIVIDAD = CRIEVA.ID_ACTIVIDAD " + 
			"INNER JOIN EVA_RELPROGAULACT RELPRGGAULAACT ON RELPRGGAULAACT.ID_ACTIVIDAD = ACT.ID_ACTIVIDAD " + 
			"INNER JOIN EVA_PROGAULA PROGAULA ON PROGAULA.ID_PROGAULA = RELPRGGAULAACT.ID_PROGAULA  " + 
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC " +
			"INNER JOIN EVA_RELPROGDIDPOND RELPROGDIDPOND ON RELPROGDIDPOND.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC  " +
			"INNER JOIN TLPONDERACION POND ON POND.X_PONDERACION = RELPROGDIDPOND.X_PONDERACION " +
			"INNER JOIN TLRELPONCRIEVA RELPONDCRIEVA ON RELPONDCRIEVA.X_PONDERACION = POND.X_PONDERACION AND RELPONDCRIEVA.X_CRIEVA = crieva.X_CRIEVA " +
			"INNER JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VALCRIACTALU.X_CALIFICA", nativeQuery = true)
	List<ValCriActAluProjection> getNotasPorCriterio(
			@Param("idCriterio") Long idCriterio,
			@Param("idMatricula") Long idMatricula);
	
	@Query(value = "SELECT DISTINCT RELPONDCRIEVA.ID_OPECALCRIEVA idTipoCalculo " +
			"FROM EVA_PROGAULA PROGAULA " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC " +
			"INNER JOIN EVA_RELPROGDIDPOND RELPROGDIDPOND ON RELPROGDIDPOND.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC " +
			"INNER JOIN TLPONDERACION POND ON POND.X_PONDERACION = RELPROGDIDPOND.X_PONDERACION " +
			"INNER JOIN TLRELPONCRIEVA RELPONDCRIEVA ON RELPONDCRIEVA.X_PONDERACION = POND.X_PONDERACION AND RELPONDCRIEVA.X_CRIEVA = :idCriterio " +
			"WHERE PROGAULA.ID_PROGAULA = :idProgramacionAula ", nativeQuery = true)
	int getTipoCalculoCriterio(@Param("idProgramacionAula") Long idProgramacionAula,
			@Param("idCriterio") Long idCriterio);
	
	@Query(value = "SELECT DISTINCT UNI.X_UNIDAD idUnidad, UNI.T_NOMBRE  unidad " + 
			"FROM TLUNIDADESCEN UNI " + 
			"INNER JOIN TLMATALU MA ON MA.X_UNIDAD = UNI.X_UNIDAD  " + 
			"INNER JOIN EVA_RELACTALUM RAA ON RAA.X_MATRICULA = MA.X_MATRICULA  " + 
			"INNER JOIN EVA_ACTIVIDAD ACT ON RAA.ID_ACTIVIDAD = ACT.ID_ACTIVIDAD AND (:idActividad = '-1' OR :idActividad = ACT.ID_ACTIVIDAD) " + 
			"INNER JOIN EVA_UNIDADPROG UP ON ACT.ID_UNIDADPROG = UP.ID_UNIDADPROG AND (:idUnidadProg = '-1' OR :idUnidadProg = UP.ID_UNIDADPROG) " + 
			"INNER JOIN EVA_RELPROGAULACT RPAA ON RPAA.ID_ACTIVIDAD = ACT.ID_ACTIVIDAD AND RPAA.ID_PROGAULA = :idProgramacionAula " + 
			"ORDER BY T_NOMBRE", nativeQuery = true)
	List<UnidadesValoracionProjection> getUnidadesCentro(@Param("idProgramacionAula") Long idProgramacionAula,
			@Param("idActividad") Long idActividad,
			@Param("idUnidadProg") Long idUnidadProg);

	@Query(value = "SELECT F.B_FOTO " + "FROM TLALUMNOS ALU, TLALUMNOSFOTO F "
			+ "WHERE ALU.C_NUMESCOLAR = :numescolar AND ALU.X_ALUMNO = F.X_ALUMNO ", nativeQuery = true)
	Blob getAlumnoFoto(@Param("numescolar") String numescolar);
	
}
