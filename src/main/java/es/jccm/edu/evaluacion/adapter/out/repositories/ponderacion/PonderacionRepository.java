package es.jccm.edu.evaluacion.adapter.out.repositories.ponderacion;

import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.*;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaPonderacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaPonderacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PonderacionRepository extends AbstractRepository<EvaPonderacion, Long, QEvaPonderacion> {

    @Query(value = "SELECT DISTINCT mat.X_MATERIAOMG idMateria, matCurso.S_MATERIAC TXMateria, unidades.T_NOMBRE TXunidad, unidades.X_UNIDAD IDUNIDAD, "
    		+ "modalidades.S_MODALIDAD TXMODALIDAD, modalidades.X_MODALIDAD IDMODALIDAD, "
            + "ofg.d_ofertamatrig TXCURSO, curso.x_ETAPA IDCURSO, ciclo.D_ETAPA TXCICLO, "
            + "ciclo.X_ETAPA IDCICLO, etapa.D_ETAPA TXETAPA, ciclo.X_ETAPADEPENDEDE IDETAPA "
    		+ "FROM TLMATOFEMATRG mat "
    		+ "INNER JOIN TLUNIAFEGRUACTPRO grupo ON grupo.X_MATERIAOMG = mat.X_MATERIAOMG "
    		+ "INNER JOIN TLGRUACTPROALU grupoActividad ON grupo.X_GRUACTPROALU = grupoActividad.X_GRUACTPROALU "
    		+ "INNER JOIN TLUNIDADESCEN unidades ON grupo.X_UNIDAD = unidades.X_UNIDAD "
    		+ "INNER JOIN TLCENTROS centros ON unidades.X_CENTRO = centros.X_CENTRO "
            + "INNER JOIN TLOFEMATRGEN OFG ON MAT.X_OFERTAMATRIG=OFG.X_OFERTAMATRIG "
    		+ "INNER JOIN TLMATERIASCURSO matCurso ON mat.X_MATERIAC = matCurso.X_MATERIAC "
    		+ "INNER JOIN TLCURSOMODA cursoModalidad ON matCurso.x_cursomod = cursoModalidad.x_cursomod "
    		+ "INNER JOIN TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = cursoModalidad.X_MODALIDAD "
    		+ "INNER JOIN TLETAPAS curso ON curso.x_etapa = cursoModalidad.x_etapa "
    		+ "INNER JOIN TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA "
    		+ "INNER JOIN TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA "
    		+ "WHERE (grupoActividad.X_EMPLEADO = :idEmpleado AND grupoActividad.C_ANNO = :anno AND centros.C_CODIGO = :codigoCentro) "
    		+ "AND (etapa.D_ETAPA  like '%LOMLOE%' OR ciclo.d_etapa like '%LOMLOE%') ORDER BY ciclo.D_ETAPA", nativeQuery = true)
    List<MateriasUnidadProjection> findAllMaterias(@Param("idEmpleado") Long idEmpleado, @Param("anno") Integer anno, @Param("codigoCentro") Long codigoCentro);

    @Query(value = "SELECT POND.X_PONDERACION AS idPonderacion, POND.X_DOCENTE AS idDocente, " +
            "POND.X_MATERIA AS idMateria, MATCUR.D_MATERIAC AS nombreMateria, POND.L_EDITABLE AS editable " +
            "FROM DELPHOS.TLPONDERACION POND " +
            "INNER JOIN DELPHOS.TLMATOFEMATRG MATGR ON MATGR.X_MATERIAOMG = POND.X_MATERIA " +
            "INNER JOIN DELPHOS.TLMATERIASCURSO MATCUR ON MATCUR.X_MATERIAC = MATGR.X_MATERIAC " +
            "WHERE POND.X_MATERIA = :idMateria AND POND.X_DOCENTE = :idEmpleado", nativeQuery = true)
    PonderacionProjection getPonderaciones(@Param("idMateria") Long idMateria, @Param("idEmpleado") Long idEmpleado);
    
    @Query(value = "SELECT POND.X_PONDERACION AS idPonderacion, POND.X_DOCENTE AS idDocente, " +
            "POND.X_MATERIA AS idMateria, MATCUR.D_MATERIAC ||' - '|| ofg.t_abreviatura AS nombreMateria, POND.L_EDITABLE AS editable " +
            "FROM DELPHOS.TLPONDERACION POND " +
            "INNER JOIN DELPHOS.TLMATOFEMATRG MATGR ON MATGR.X_MATERIAOMG = POND.X_MATERIA " +
            "INNER JOIN TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = MATGR.X_OFERTAMATRIG " +
            "INNER JOIN DELPHOS.TLMATERIASCURSO MATCUR ON MATCUR.X_MATERIAC = MATGR.X_MATERIAC " +
            "WHERE POND.X_PONDERACION = :idPonderacion", nativeQuery = true)
    PonderacionProjection getPonderacionesById(@Param("idPonderacion") Long idPonderacion);
    
    @Query(value = "SELECT POND.X_PONDERACION AS idPonderacion, POND.X_DOCENTE AS idDocente, " +
            "POND.X_MATERIA AS idMateria, MATCUR.D_MATERIAC AS nombreMateria, POND.L_EDITABLE AS editable " +
            "FROM DELPHOS.TLPONDERACION POND " +
            "INNER JOIN DELPHOS.TLMATOFEMATRG MATGR ON MATGR.X_MATERIAOMG = POND.X_MATERIA " +
            "INNER JOIN DELPHOS.TLMATOFEMATRCEN MATOMC ON MATOMC.X_MATERIAOMG = MATGR.X_MATERIAOMG " +
            "INNER JOIN DELPHOS.TLCENTROS CENT ON CENT.X_CENTRO = MATOMC.X_CENTRO " +
            "INNER JOIN DELPHOS.TLMATERIASCURSO MATCUR ON MATCUR.X_MATERIAC = MATGR.X_MATERIAC " +
            "WHERE POND.X_MATERIA = :idMateria AND POND.X_DOCENTE = :idEmpleado AND CENT.C_CODIGO = :codigoCentro", nativeQuery = true)
    PonderacionProjection getPonderacionesByCentro(@Param("idMateria") Long idMateria, @Param("idEmpleado") Long idEmpleado, @Param("codigoCentro") Long codigoCentro);

    @Query(value = "SELECT TLS_PONDERACION.nextval FROM dual", nativeQuery = true)
    Long getNewIdPonderacion();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DELPHOS.TLPONDERACION (X_PONDERACION,X_DOCENTE,X_MATERIA) " +
            "VALUES (:idPonderacion, :idEmpleado, :idMateria)", nativeQuery = true)
    void insertPonderacion(
            @Param("idPonderacion") Long idPonderacion,
            @Param("idMateria") Long idMateria,
            @Param("idEmpleado") Long idEmpleado);

    @Query(value = "SELECT PONCOMESP.X_RELPONCOMESP AS idRelacionCompe, PONCOMESP.X_COMESP AS idCompetencia, " +
            "COM.T_ABREV AS codigoCompe, COM.D_COMESP AS descripcionCompe, " +
            "PONCOMESP.PORCENTAJE AS porcentajeCompe, " +
            "PONCOMESP.PESO AS pesoCompe " +
            "FROM DELPHOS.TLRELPONCOMESP PONCOMESP " +
            "INNER JOIN DELPHOS.TLCOMESP COM ON COM.X_COMESP = PONCOMESP.X_COMESP " +
            "WHERE PONCOMESP.X_PONDERACION = :idPonderacion ORDER BY com.n_ordenpres", nativeQuery = true)
    List<CompetenciasEspecificasProjection> getCompetenciasEspecificas(@Param("idPonderacion") Long idPonderacion);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DELPHOS.TLRELPONCOMESP(X_RELPONCOMESP, X_PONDERACION, X_COMESP, PESO, PORCENTAJE) " +
            "VALUES(TLS_RELPONCOMESP.nextval, :idPonderacion, :idCompetencia, 1, :porcentajeCompe)", nativeQuery = true)
    void insertCompetencias(
            @Param("idPonderacion") Long idPonderacion,
            @Param("idCompetencia") Long idCompetencia,
            @Param("porcentajeCompe") Float porcentajeCompe);

    @Query(value = "SELECT COMESP.X_COMESP "
    		+ "FROM DELPHOS.TLMATOFEMATRG MAT "
    		+ "INNER JOIN DELPHOS.TLRELCOMPESMAT RCM ON RCM.X_MATERIAOMG = MAT.X_MATERIAOMG "
    		+ "INNER JOIN DELPHOS.TLCOMESP COMESP ON COMESP.X_COMESP = RCM.X_COMESP "
    		+ "WHERE MAT.X_MATERIAOMG =:idMateria", nativeQuery = true)
    List<Long> getCompetenciasByMateria(@Param("idMateria") Long idMateria);

    @Query(value = "SELECT DISTINCT COMESP.X_COMESP " +
            " FROM DELPHOS.TLCOMESP COMESP " +
            " INNER JOIN DELPHOS.TLCRIEVA CRI ON CRI.X_COMESP = COMESP.X_COMESP " +
            " WHERE CRI.X_CRIEVA IN (:idCriterios)", nativeQuery = true)
    List<Long> getCompetenciasByCriterios(@Param("idCriterios") List<Long> idCriterios);

    @Query(value = "SELECT ciclo.D_ETAPA " +
            "FROM DELPHOS.TLMATOFEMATRG MAT  " +
            "INNER JOIN DELPHOS.TLMATERIASCURSO MATCURSO ON MAT.X_MATERIAC = MATCURSO.X_MATERIAC " +
            "INNER JOIN DELPHOS.TLCURSOMODA CURSO ON MATCURSO.X_CURSOMOD = CURSO.X_CURSOMOD " +
            "INNER JOIN DELPHOS.TLETAPAS ETAPA ON ETAPA.X_ETAPA  = CURSO.X_ETAPA " +
            "INNER JOIN TLETAPAS ciclo ON ETAPA.X_ETAPADEPENDEDE = ciclo.X_ETAPA " +
            "WHERE MAT.X_MATERIAOMG = :idMateria", nativeQuery = true)
    String isBachiller(@Param("idMateria") Long idMateria);

    @Query(value = "SELECT DISTINCT RELCRI.X_RELPONCRIEVA AS idRelacionCri, CRI.X_CRIEVA AS idCriterio, " +
            "CRI.T_ABREV AS codigoCri,CRI.D_CRIEVA AS descripcionCri, " +
            "RELCRI.PORCENTAJE AS porcentajeCri, RELCRI.PESO AS pesoCri, cri.n_ordenpres orden, " +
            "relcri.ID_OPECALCRIEVA idTipoOperacion, OPE.TX_OPECALCRIEVA nombreTipoOperacion " +
            "FROM DELPHOS.TLCRIEVA CRI " +
            "INNER JOIN DELPHOS.TLRELPONCRIEVA RELCRI ON RELCRI.X_CRIEVA = CRI.X_CRIEVA " +
            "INNER JOIN DELPHOS.TLPONDERACION PON ON PON.X_PONDERACION = RELCRI.X_PONDERACION " +
            "INNER JOIN DELPHOS.TLRELPONCOMESP COMP ON COMP.X_COMESP  = CRI.X_COMESP " +
            "INNER JOIN EVA_OPECALCRIEVA OPE ON OPE.ID_OPECALCRIEVA = RELCRI.ID_OPECALCRIEVA " +
            "WHERE RELCRI.X_PONDERACION = :idPonderacion AND CRI.X_COMESP = :idCompetencia " +
            "ORDER BY cri.n_ordenpres", nativeQuery = true)
    List<CriteriosProjection> getCriteriosEvaluacion(@Param("idPonderacion") Long idPonderacion, @Param("idCompetencia") Long idCompetencia);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DELPHOS.TLRELPONCRIEVA(X_RELPONCRIEVA, X_PONDERACION, X_CRIEVA, PESO, PORCENTAJE, ID_OPECALCRIEVA) " +
            "VALUES(TLS_RELPONCRIEVA.nextval, :idPonderacion, :idCriterio, 1, :porcentajeCompe, 2 )", nativeQuery = true)
    void insertCriterios(
            @Param("idPonderacion") Long idPonderacion,
            @Param("idCriterio") Long idCriterio,
    @Param("porcentajeCompe") Float porcentajeCompe);

    @Query(value = "SELECT cri.x_crieva idCriterio FROM TLCRIEVA cri " +
            "INNER JOIN TLCOMESP com ON com.X_COMESP = cri.X_COMESP " +
            "WHERE cri.X_COMESP IN (SELECT X_COMESP FROM TLRELPONCOMESP t WHERE t.X_PONDERACION = :idPonderacion) " +
            "AND cri.T_ABREV LIKE '%'||com.T_ABREV||'%'", nativeQuery = true)
    List<Long> getCriteriosByPonderacion(@Param("idPonderacion") Long idPonderacion);
    
    @Query(value = "SELECT x_crieva as idCriterio, X_COMESP as idPonderacion  FROM DELPHOS.TLCRIEVA crieva WHERE X_CRIEVA IN (:idCriterios)", nativeQuery = true)
  List<CriteriosComEspProjection> getCriteriosCompEsp(@Param("idCriterios")  List<Long> idCriterios);

    @Modifying
    @Query(value = "UPDATE DELPHOS.TLRELPONCOMESP SET PESO = :pesoCompe, PORCENTAJE = :porcentajeCompe " +
            "WHERE X_RELPONCOMESP = :idRelacionCompe", nativeQuery = true)
    void updateCompetencias(
            @Param("idRelacionCompe") Long idRelacionCompe,
            @Param("pesoCompe") Integer pesoCompe,
            @Param("porcentajeCompe") Float porcentajeCompe);

    @Modifying
    @Query(value = "UPDATE DELPHOS.TLRELPONCRIEVA SET PESO = :pesoCri, PORCENTAJE = :porcentajeCri " +
            "WHERE X_RELPONCRIEVA = :idRelacionCri", nativeQuery = true)
    void updateCriterios(
            @Param("idRelacionCri") Long idRelacionCri,
            @Param("pesoCri") Integer pesoCri,
            @Param("porcentajeCri") Float porcentajeCri);

    
    @Modifying
    @Query(value = "UPDATE DELPHOS.TLRELPONCRIEVA SET PESO = :pesoCri, PORCENTAJE = :porcentajeCri, ID_OPECALCRIEVA = :idTipoOperacion " +
            "WHERE X_RELPONCRIEVA = :idRelacionCri", nativeQuery = true)
    void updateCriterios(
            @Param("idRelacionCri") Long idRelacionCri,
            @Param("pesoCri") Integer pesoCri,
            @Param("porcentajeCri") Float porcentajeCri,
            @Param("idTipoOperacion") Long idTipoOperacion);

    @Query(value = "SELECT DISTINCT emp.x_empleado idEmpleado, emp.nombre nombre, emp.apellido1 || ' ' || emp.apellido2 apellidos " +
            "FROM tlempleados emp " +
            "INNER JOIN tlgruactproalu grupoActividad ON grupoActividad.X_EMPLEADO = emp.X_EMPLEADO " +
            "INNER JOIN tlcentros cen ON cen.x_centro = grupoActividad.X_CENTRO " +
            "INNER JOIN tlponderacion pond ON pond.x_docente = emp.x_empleado " +
            "WHERE cen.c_codigo = :codCentro AND pond.x_materia = :idMateria " +
            "AND pond.x_docente <> :idEmpleado", nativeQuery = true)
    List<DocentePonderacionProjection> getDocentesPonderacion(
            @Param("codCentro") Long codCentro,
            @Param("idMateria") Long idMateria,
            @Param("idEmpleado") Long idEmpleado);
    
    @Query(value = "SELECT COUNT (*) "
    		+ "FROM DELPHOS.TLVALCRIALU VALCRI "
    		+ "WHERE VALCRI.X_PONDERACION = :idPonderacion", nativeQuery = true)
    Integer getValoracionesCriteriosAsociadasPonderacion(@Param("idPonderacion") Long idPonderacion);
    
    @Modifying
    @Query(value = "UPDATE DELPHOS.TLPONDERACION "
    		+ "SET L_EDITABLE = 'S' "
    		+ "WHERE X_PONDERACION = :idPonderacion", nativeQuery = true)
    void setPonderacionEditable(@Param("idPonderacion") Long idPonderacion);

    @Query(value = "SELECT com.T_ABREV abrev,  " +
            "com.D_COMESP descripcion, com.X_COMESP id, com.X_CICLO idCiclo, com.N_ORDENPRES nordenPres FROM TLRELPONCOMESP relCom " +
            "INNER JOIN TLCOMESP com ON com.X_COMESP = relcom.X_COMESP " +
            "WHERE RELCOM.X_ponderacion = :idPonderacion", nativeQuery = true)
    List<CompetenciasEspecificasPonderacionProjection> getCompetenciasByPonderacion(@Param("idPonderacion") Long idPonderacion);

    @Query(value = "SELECT cri.T_ABREV abreviatura, cri.X_CRIEVA id, cri.X_CICLO idCiclo, cri.D_CRIEVA nombre, cri.N_ORDENPRES orden " +
            "FROM TLRELPONCRIEVA relCri INNER JOIN TLCRIEVA cri ON cri.X_CRIEVA = relCri.X_CRIEVA " +
            "WHERE relCri.X_ponderacion = :idPonderacion AND cri.x_comesp = :idCompetencia", nativeQuery = true)
    List<CriteriosPonderacionProjection> getCriteriosByPonderacionyCompetencia(@Param("idPonderacion") Long idPonderacion, @Param("idCompetencia") Long idCompetencia);
    
}
