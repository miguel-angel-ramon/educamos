package es.jccm.edu.buzon.adapter.out.repository.solicitudCorreoAlumno;

import es.jccm.edu.buzon.application.domain.buzon.projection.CursoSolicitudDatosProjection;
import es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno.*;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SolicitudCorreoAlumnoRepository extends JpaRepository<SolicitudCorreoAlumno, Long>{

  @Query(value = ""
  +"SELECT "
  +"    DISTINCT udc.X_UNIDAD AS idUnidad, "
  +"    udc.T_NOMBRE AS unidad, "
  +"    curso.X_ETAPA AS idCurso, "
  +"    ofg.X_OFERTAMATRIG AS idOfertamatric, "
  +"    ofg.D_OFERTAMATRIG AS curso, "
  +"    etapa.X_ETAPA AS idEtapa, "
  +"    etapa.X_ETAPA AS idEtapaSec, "
  +"    ciclo.X_ETAPA AS idCiclo, "
  +"    etapa.S_ETAPA AS etapa, "
  +"    etapa.n_orden AS ordenetapa, "
  +"    curso.n_orden AS ordencurso, "
  +"    ( SELECT count(*) FROM TLOFEMATRCEN OFERTA "
  +"        INNER JOIN TLMATALU MAT ON OFERTA.X_OFERTAMATRIG  = MAT.X_OFERTAMATRIG "
  +"        INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MAT.X_ALUMNO "
  +"      WHERE OFERTA.X_CENTRO = :idCentro "
  +"        AND MAT.C_ANNO = :anno "
  +"        AND MAT.X_UNIDAD = udc.X_UNIDAD "
  +"        AND nvl(MAT.c_resultado, 99) > 1 "
  +"    ) AS alumnos, "
  +"    ( SELECT count(*) FROM TLOFEMATRCEN OFERTA "
  +"        INNER JOIN TLMATALU MAT ON OFERTA.X_OFERTAMATRIG  = MAT.X_OFERTAMATRIG "
  +"        INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MAT.X_ALUMNO "
  +"        INNER JOIN DELPHOS_MODACC.USUARIOS_T T ON T.T_IDENTIFICACION  = ALU.C_NUMIDE "
  +"      WHERE OFERTA.X_CENTRO = :idCentro "
  +"        AND MAT.C_ANNO = :anno "
  +"        AND T.UID_AZURE  IS NOT NULL "
  +"        AND T.CORREO_AULA IS NOT NULL "
  +"        AND MAT.X_UNIDAD = udc.X_UNIDAD "
  +"        AND nvl(MAT.c_resultado, 99) > 1 "
  +"    ) AS numAlumT, "
  +"    ( SELECT count(*) FROM TLOFEMATRCEN OFERTA "
  +"        INNER JOIN TLMATALU MAT ON OFERTA.X_OFERTAMATRIG  = MAT.X_OFERTAMATRIG "
  +"        INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MAT.X_ALUMNO "
  +"        INNER JOIN DELPHOS_MODACC.USUARIOS_T T ON T.T_IDENTIFICACION  = ALU.C_NUMIDE "
  +"      WHERE OFERTA.X_CENTRO = :idCentro "
  +"        AND MAT.C_ANNO = :anno "
  +"        AND T.UID_AZURE  IS NOT NULL "
  +"        AND T.CORREO_AULA IS NOT NULL "
  +"        AND T.LG_HABILITAOWA = 0 "
  +"        AND MAT.X_UNIDAD = udc.X_UNIDAD "
  +"        AND nvl(MAT.c_resultado, 99) > 1 "
  +"    ) AS numAlumTOWAN, "
  +"    ( SELECT count(*) FROM TLOFEMATRCEN OFERTA "
  +"        INNER JOIN TLMATALU MAT ON OFERTA.X_OFERTAMATRIG  = MAT.X_OFERTAMATRIG "
  +"        INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MAT.X_ALUMNO "
  +"        INNER JOIN DELPHOS_MODACC.USUARIOS_T T ON T.T_IDENTIFICACION  = ALU.C_NUMIDE "
  +"      WHERE OFERTA.X_CENTRO = :idCentro "
  +"        AND MAT.C_ANNO = :anno "
  +"        AND T.UID_AZURE  IS NOT NULL "
  +"        AND T.CORREO_AULA IS NOT NULL "
  +"        AND T.LG_HABILITAOWA = 1 "
  +"        AND MAT.X_UNIDAD = udc.X_UNIDAD "
  +"        AND nvl(MAT.c_resultado, 99) > 1 "
  +"    ) AS numAlumTOWAS "
  +"  FROM DELPHOS.TLUNIDADESCEN udc "
  +"    INNER JOIN DELPHOS.TLOFERTASUNIDAD ou          ON ou.X_UNIDAD = udc.X_UNIDAD "
  +"    INNER JOIN DELPHOS.TLOFEMATRCEN    omc         ON omc.X_OFERTAMATRIC = ou.X_OFERTAMATRIC "
  +"    INNER JOIN DELPHOS.TLOFEMATRGEN    ofg         ON ofg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG "
  +"    INNER JOIN DELPHOS.TLCURSOORG      cuo         ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
  +"    INNER JOIN DELPHOS.TLMODALIDADES   modalidades ON modalidades.X_MODALIDAD = ofg.X_MODALIDAD "
  +"    INNER JOIN DELPHOS.TLCURSOMODA     curModa     ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
  +"    INNER JOIN DELPHOS.TLETAPAS        curso       ON curso.X_ETAPA = curModa.X_ETAPA "
  +"    INNER JOIN DELPHOS.TLETAPAS        ciclo       ON ciclo.X_ETAPA = curso.X_ETAPADEPENDEDE "
  +"    INNER JOIN DELPHOS.TLETAPAS        etapa       ON etapa.X_ETAPA = ciclo.X_ETAPADEPENDEDE "
  +"  WHERE (1 = tlf_omgcuelgaetapa(ofg.x_ofertamatrig, 1630) /* ESO */ "
  +"      OR 1 = tlf_omgcuelgaetapa(ofg.x_ofertamatrig, 1658) /* PRIMARIA */ "
  +"      OR 1 = tlf_omgcuelgaetapa(ofg.x_ofertamatrig, 1624) /* FORMACIÓN PROFESIONAL */ "
  +"      OR 1 = tlf_omgcuelgaetapa(ofg.x_ofertamatrig, 107)  /* BACHILLER */) "
  +"    AND udc.X_CENTRO = :idCentro "
  +"    AND udc.C_ANNO = :anno "
  +"  ORDER BY "
  +"    etapa.n_orden, "
  +"    curso.X_ETAPA, "
  +"    unidad "
  , nativeQuery = true)
  List<CursoSolicitudDatosProjection> getCursoSolicitudDatosUnidadByCentro(
    @Param("idCentro") Long idCentro
    ,@Param("anno") Long anno
  );

  @Query(value = "SELECT "
  +"    DISTINCT "
  +"    etapa.X_ETAPA AS idEtapa, "
  +"    ciclo.X_ETAPA AS idCiclo, "
  +"    etapa.S_ETAPA AS etapa,  "
  +"    ofg.X_OFERTAMATRIG AS idOfertamatricCurso, "
  +"    ofg.D_OFERTAMATRIG AS curso,  "
  +"    curso.X_ETAPA AS idEtapas,        "
  +"    etapa.n_ordenpres AS ordenetapa, "
  +"    curso.n_orden AS ordencurso, "
  +"     ( SELECT count(ALU.X_ALUMNO) FROM TLMATALU MAT  "
  +"   INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MAT.X_ALUMNO "
  +"   WHERE MAT.X_OFERTAMATRIC = omc.X_OFERTAMATRIC "
  +"   AND  nvl(MAT.c_resultado, 99) > 1 AND MAT.C_ANNO = :anno )  AS alumnos, "
  +"     ( SELECT count(ALU.X_ALUMNO) FROM TLMATALU MAT "
  +"   INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MAT.X_ALUMNO "
  +"   INNER JOIN DELPHOS_MODACC.PERSONAS per ON per.X_PERSONA = alu.X_PERSONA "
  +"   INNER JOIN DELPHOS_MODACC.USUARIOS_T T ON T.OID_PERSONA = per.OID  "
  +"   WHERE MAT.X_OFERTAMATRIC = omc.X_OFERTAMATRIC AND T.UID_AZURE  IS NOT NULL AND T.CORREO_AULA IS NOT NULL "
  +"   AND  nvl(MAT.c_resultado, 99) > 1 AND MAT.C_ANNO = :anno)  AS numAlumT,  "
  +"   ( SELECT count(ALU.X_ALUMNO) FROM TLMATALU MAT "
  +"   INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MAT.X_ALUMNO "
  +"   INNER JOIN DELPHOS_MODACC.PERSONAS per ON per.X_PERSONA = alu.X_PERSONA "
  +"   INNER JOIN DELPHOS_MODACC.USUARIOS_T T ON T.OID_PERSONA = per.OID "
  +"   WHERE MAT.X_OFERTAMATRIC = omc.X_OFERTAMATRIC AND T.UID_AZURE  IS NOT NULL AND T.CORREO_AULA IS NOT NULL "
  +"   AND  nvl(MAT.c_resultado, 99) > 1 AND MAT.C_ANNO = :anno "
  +"   AND T.LG_HABILITAOWA = 0 )  AS numAlumTOWAN, "
  +"   ( SELECT count(ALU.X_ALUMNO) FROM TLMATALU MAT "
  +"   INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MAT.X_ALUMNO "
  +"   INNER JOIN DELPHOS_MODACC.PERSONAS per ON per.X_PERSONA = ALU.X_PERSONA "
  +"   INNER JOIN DELPHOS_MODACC.USUARIOS_T T ON T.OID_PERSONA = per.OID "
  +"   WHERE MAT.X_OFERTAMATRIC = omc.X_OFERTAMATRIC AND T.UID_AZURE  IS NOT NULL AND T.CORREO_AULA IS NOT NULL "
  +"   AND T.LG_HABILITAOWA = 1 "
  +"   AND  nvl(MAT.c_resultado, 99) > 1 AND MAT.C_ANNO = :anno)  AS numAlumTOWAS "
  +"FROM "
  +"DELPHOS.TLOFEMATRCEN omc "
  +"INNER JOIN tlperiodosomc per ON "
  +" per.x_ofertamatric = omc.x_ofertamatric "
  +"INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON "
  +"    ofg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG "
  +"INNER JOIN DELPHOS.TLCURSOORG cuo ON "
  +"    cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
  +"INNER JOIN DELPHOS.TLMODALIDADES modalidades ON "
  +"    modalidades.X_MODALIDAD = ofg.X_MODALIDAD "
  +"INNER JOIN DELPHOS.TLCURSOMODA curModa ON "
  +"    curModa.X_MODALIDAD = modalidades.X_MODALIDAD "
  +"    AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
  +"INNER JOIN DELPHOS.TLETAPAS curso ON "
  +"    curso.X_ETAPA = curModa.X_ETAPA "
  +"INNER JOIN DELPHOS.TLETAPAS ciclo ON "
  +"    ciclo.X_ETAPA = curso.X_ETAPADEPENDEDE "
  +"INNER JOIN DELPHOS.TLETAPAS etapa ON "
  +"    etapa.X_ETAPA = ciclo.X_ETAPADEPENDEDE  "
  +"  WHERE (1 = tlf_omgcuelgaetapa(ofg.x_ofertamatrig, 1630) /* ESO */ "
  +"      OR 1 = tlf_omgcuelgaetapa(ofg.x_ofertamatrig, 1658) /* PRIMARIA */ "
  +"      OR 1 = tlf_omgcuelgaetapa(ofg.x_ofertamatrig, 1624) /* FORMACIÓN PROFESIONAL */ "
  +"      OR 1 = tlf_omgcuelgaetapa(ofg.x_ofertamatrig, 107)  /* BACHILLER */) "
  +"    AND omc.X_CENTRO = :idCentro "
  +"    AND :anno between per.c_anno and NVL(per.c_annopuedeterminar, 9999) "
  +"ORDER BY "
  +"    etapa.N_ORDENPRES, "
  +"    curso.N_ORDEN ", nativeQuery = true)
  List<CursoSolicitudDatosProjection> getCursoSolicitudDatosByCentro(
    @Param("idCentro") Long idCentro,
    @Param("anno") Long anno
  );

  List<SolicitudCorreoAlumno> findAllByIdCentro(Long idCentro);

  SolicitudCorreoAlumno findByIdCentroAndIdOfertamatricCursoAndEstadoSolicitud(
    Long idCentro
    ,Long idOfertamatricCurso
    ,Long estadoSolicitud
  );

  @Query(value = "SELECT"
  +"  correo.ID_SOLICITUD_CORREO_ALUMNADO AS ID,"
  +"  correo.ID_OFERTAMATRIG AS IDOFERTAMATRIG,"
  +"  correo.ID_CENTRO AS IDCENTRO,"
  +"  CEN.C_CODIGO AS CODCENTRO,"
  +"  correo.F_SOLICITUD AS FECHASOLICITUD,"
  +"  correo.ID_ESTADO AS IDESTADO,"
  +"  ofg.D_OFERTAMATRIG AS nombreCurso,"
  +"  tlf_datoscentro(correo.ID_CENTRO) as nombreCompletoCentro,"
  +"  datoscen.D_TITULO AS TITULO,"
  +"  (SELECT count(*) FROM TLOFEMATRCEN omc"
  +"   INNER JOIN TLOFEMATRGEN omg ON omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG"
  +"   INNER JOIN TLMATALU matalu ON omg.X_OFERTAMATRIG = matalu.X_OFERTAMATRIG"
  +"   INNER JOIN TLALUMNOS alumnos ON matalu.X_ALUMNO = alumnos.X_ALUMNO"
  +"   INNER JOIN DELPHOS_MODACC.PERSONAS per ON per.X_PERSONA = alumnos.X_PERSONA"
  +"   INNER JOIN DELPHOS_MODACC.USUARIOS_T ut ON ut.OID_PERSONA = per.OID"
  +"   WHERE omc.X_OFERTAMATRIG = correo.ID_OFERTAMATRIG"
  +"   AND omc.X_CENTRO = correo.ID_CENTRO"
  +"   AND matalu.C_ANNO = :cAnno"
  +"   AND matalu.X_CENTRO = correo.ID_CENTRO"
  +"   AND ut.UID_AZURE IS NOT NULL"
  +"   AND ut.CORREO_AULA IS NOT NULL"
  // +"   AND ut.LG_HABILITAOWA = 0"
  +"   AND ut.CRT_PT = correo.ID_SOLICITUD_CORREO_ALUMNADO"
  +"   AND nvl(matalu.C_RESULTADO, 99) > 1) AS numAlumnos"
  +" FROM DELPHOS_MODACC.SOLICITUD_CORREO_ALUMNADO correo"
  +"  INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = correo.ID_OFERTAMATRIG"
  +"  INNER JOIN DELPHOS.TLDATOSCEN datoscen ON datoscen.X_CENTRO = correo.ID_CENTRO"
  +"  INNER JOIN DELPHOS.TLCENTROS CEN ON CEN.X_CENTRO = datoscen.X_CENTRO"
  +"  INNER JOIN DELPHOS.TLDENGEN DEN ON DEN.X_DENGEN = datoscen.X_DENGEN"
  +" ORDER BY FECHASOLICITUD DESC", nativeQuery = true)
  List<SolicitudActivacionCorreoProjection> getSolicitudesAdministrar(
    @Param("cAnno") Long cAnno
  );

  @Query(value = ""
  +"SELECT "
  +"  correo.ID_SOLICITUD_CORREO_ALUMNADO AS ID, "
  +"  correo.ID_OFERTAMATRIG AS IDOFERTAMATRIG, "
  +"  correo.ID_CENTRO AS IDCENTRO, "
  +"  CEN.C_CODIGO AS CODCENTRO, "
  +"  correo.F_SOLICITUD AS FECHASOLICITUD, "
  +"  correo.ID_ESTADO AS IDESTADO, "
  +"  ofg.D_OFERTAMATRIG AS nombreCurso, "
  +"  etapa.S_ETAPA AS etapa, "
  +"  tlf_datoscentro(correo.ID_CENTRO) as nombreCompletoCentro, "
  +"  datoscen.D_TITULO AS TITULO, "
  +"  (SELECT count(*) "
  +"    FROM TLOFEMATRCEN omc "
  +"      INNER JOIN TLOFEMATRGEN omg ON omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
  +"      INNER JOIN TLMATALU matalu ON omg.X_OFERTAMATRIG = matalu.X_OFERTAMATRIG "
  +"      INNER JOIN TLALUMNOS alumnos ON matalu.X_ALUMNO = alumnos.X_ALUMNO "
  +"      INNER JOIN DELPHOS_MODACC.USUARIOS_T ut ON alumnos.C_NUMIDE = ut.T_IDENTIFICACION "
  +"    WHERE omc.X_OFERTAMATRIG = correo.ID_OFERTAMATRIG "
  +"      AND correo.ID_OFERTAMATRIG = :idOfertamatricCurso "
  +"      AND omc.X_CENTRO = correo.ID_CENTRO "
  +"      AND matalu.C_ANNO = :cAnno "
  +"      AND matalu.X_CENTRO = correo.ID_CENTRO "
  +"      AND ut.UID_AZURE IS NOT NULL "
  +"      AND ut.CORREO_AULA IS NOT NULL "
  +"      AND ut.CRT_PT = correo.ID_SOLICITUD_CORREO_ALUMNADO "
  +"      AND nvl(matalu.C_RESULTADO, 99) > 1 "
  +"  ) AS numAlumnos "
  +"  FROM DELPHOS_MODACC.SOLICITUD_CORREO_ALUMNADO correo "
  +"    INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = correo.ID_OFERTAMATRIG "
  +"    INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
  +"    INNER JOIN DELPHOS.TLDATOSCEN datoscen ON datoscen.X_CENTRO = correo.ID_CENTRO "
  +"    INNER JOIN DELPHOS.TLCENTROS CEN ON CEN.X_CENTRO = datoscen.X_CENTRO "
  +"    INNER JOIN DELPHOS.TLDENGEN DEN ON DEN.X_DENGEN = datoscen.X_DENGEN "
  +"    INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = ofg.X_MODALIDAD "
  +"    INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD "
  +"      AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
  +"    INNER JOIN DELPHOS.TLETAPAS ciclo ON ciclo.X_ETAPA = curModa.X_ETAPA "
  +"    INNER JOIN DELPHOS.TLETAPAS etapa ON etapa.X_ETAPA = ciclo.X_ETAPADEPENDEDE "
  +"  WHERE correo.ID_OFERTAMATRIG = :idOfertamatricCurso "
  +"  ORDER BY FECHASOLICITUD DESC "
  , nativeQuery = true)
  List<SolicitudActivacionCorreoProjection> getSolicitudesAdministrar(
    @Param("cAnno") Long cAnno,
    @Param("idOfertamatricCurso") Long idOfertamatricCurso
  );

  @Query(value = ""
  +"SELECT "
  +"  correo.ID_SOLICITUD_CORREO_ALUMNADO AS ID, "
  +"  ofg.X_OFERTAMATRIG AS IDOFERTAMATRIG, "
  +"  :idCentro AS IDCENTRO, "
  +"  (SELECT C_CODIGO FROM DELPHOS.TLCENTROS WHERE X_CENTRO=:idCentro) AS CODCENTRO, "
  +"  correo.F_SOLICITUD AS FECHASOLICITUD, "
  +"  correo.ID_ESTADO AS IDESTADO, "
  +"  ofg.D_OFERTAMATRIG AS nombreCurso, "
  +"  etapa.S_ETAPA AS etapa, "
  +"  tlf_datoscentro( :idCentro ) as nombreCompletoCentro, "
  +"  (SELECT D_TITULO FROM TLDATOSCEN WHERE X_CENTRO = :idCentro) AS TITULO "
  +"  ,( SELECT "
  +"      CASE "
  +"        WHEN (  /* GRUPO_1 */ "
  +"          1 = tlf_omgcuelgaetapa(:idOfertamatricCurso, 1630)  /* ESO */ "
  +"        or 1 = tlf_omgcuelgaetapa(:idOfertamatricCurso, 1658) /* PRIMARIA */ "
  +"        ) THEN 1 "
  +"        WHEN (  /* GRUPO_2 */ "
  +"          1 = tlf_omgcuelgaetapa(:idOfertamatricCurso, 1624)  /* FORMACIÓN PROFESIONAL */ "
  +"        or 1 = tlf_omgcuelgaetapa(:idOfertamatricCurso, 107)  /* BACHILLER */ "
  +"        ) THEN 2 "
  +"        ELSE 0 "
  +"      END AS tipoPermiso "
  +"      FROM TLETAPAS ET "
  +"        INNER JOIN TLCURSOMODA CMD ON CMD.X_ETAPA = ET.X_ETAPA "
  +"        INNER JOIN TLOFEMATRGEN OMG ON OMG.X_MODALIDAD = CMD.X_MODALIDAD "
  +"          AND OMG.X_OFERTAMATRIG = CMD.X_CURSOMOD "
  +"          AND CMD.L_CURSO = 'S' "
  +"      WHERE OMG.X_OFERTAMATRIG = OFG.X_OFERTAMATRIG "
  +"  ) AS tipoPermiso "
  +"  FROM DELPHOS.TLOFEMATRGEN ofg "
  +"    INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = ofg.X_MODALIDAD "
  +"    INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
  +"    INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD "
  +"      AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
  +"    INNER JOIN DELPHOS.TLETAPAS ciclo ON ciclo.X_ETAPA = curModa.X_ETAPA "
  +"    INNER JOIN DELPHOS.TLETAPAS etapa ON etapa.X_ETAPA = ciclo.X_ETAPADEPENDEDE "
  +"    LEFT JOIN DELPHOS_MODACC.SOLICITUD_CORREO_ALUMNADO correo ON correo.ID_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
  +"      AND correo.ID_CENTRO = :idCentro "
  +"  WHERE ofg.X_OFERTAMATRIG = :idOfertamatricCurso "
  +"  ORDER BY correo.ID_SOLICITUD_CORREO_ALUMNADO ASC "
  , nativeQuery = true)
  List<SolicitudActivacionCorreoProjection> getSolicitudesByCursoCentro(
    @Param("idOfertamatricCurso") Long idOfertamatricCurso,
    @Param("idCentro") Long idCentro
    // @Param("cAnno") Long cAnno
  );

  @Query(value = ""
  +"SELECT ut.* FROM TLOFEMATRCEN omc"
  +" INNER JOIN TLOFEMATRGEN omg ON omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG"
  +" INNER JOIN TLMATALU matalu ON omg.X_OFERTAMATRIG = matalu.X_OFERTAMATRIG"
  +" INNER JOIN TLALUMNOS alumnos ON matalu.X_ALUMNO = alumnos.X_ALUMNO"
  +" INNER JOIN DELPHOS_MODACC.PERSONAS per ON per.X_PERSONA = alumnos.X_PERSONA"
  +" INNER JOIN DELPHOS_MODACC.USUARIOS_T ut ON ut.OID_PERSONA = per.OID"
  +" WHERE omc.X_OFERTAMATRIG = :idOfertaMatrig"
  +" AND omc.X_CENTRO = :idCentro"
  +" AND matalu.C_ANNO = :anno"
  +" AND matalu.X_CENTRO = :idCentro"
  +" AND ut.CRT_PT = :idSolicitud"
  +" AND ut.UID_AZURE IS NOT NULL"
  +" AND ut.CORREO_AULA IS NOT NULL"
  // +" AND ut.LG_HABILITAOWA = 0"
  +" AND NVL(matalu.C_RESULTADO, 99) > 1"
  , nativeQuery = true
  )List<UsuariotProjection> getDatosAlumnosFromCursoCentro(
    @Param("idCentro") Long idCentro
    ,@Param("idOfertaMatrig") Long idOfertaMatrig
    ,@Param("anno") Long anno
    ,@Param("idSolicitud") Long idSolicitud
  );

  @Query(value = ""
  +"SELECT ut.* FROM TLOFEMATRCEN omc"
  +" INNER JOIN TLOFEMATRGEN omg ON omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG"
  +" INNER JOIN TLMATALU matalu ON omg.X_OFERTAMATRIG = matalu.X_OFERTAMATRIG"
  +" INNER JOIN TLALUMNOS alumnos ON matalu.X_ALUMNO = alumnos.X_ALUMNO"
  +" INNER JOIN DELPHOS_MODACC.PERSONAS per ON per.X_PERSONA = alumnos.X_PERSONA"
  +" INNER JOIN DELPHOS_MODACC.USUARIOS_T ut ON ut.OID_PERSONA = per.OID"
  +" WHERE omc.X_OFERTAMATRIG = :idOfertaMatrig"
  +" AND omc.X_CENTRO = :idCentro"
  +" AND matalu.C_ANNO = :anno"
  +" AND matalu.X_CENTRO = :idCentro"
  +" AND ut.CRT_PT IS NULL"
  +" AND ut.UID_AZURE IS NOT NULL"
  +" AND ut.CORREO_AULA IS NOT NULL"
  +" AND ut.LG_HABILITAOWA = 0"
  +" AND NVL(matalu.C_RESULTADO, 99) > 1"
  , nativeQuery = true
  )List<UsuariotProjection> getDatosAlumnosFromCursoCentro(
    @Param("idCentro") Long idCentro
    ,@Param("idOfertaMatrig") Long idOfertaMatrig
    ,@Param("anno") Long anno
  );

  @Modifying
  @Query(value = ""
  +"UPDATE DELPHOS_MODACC.USUARIOS_T"
  +" SET CRT_PT=:idSolicitud"
  +" WHERE OID=:oid"
  , nativeQuery = true
  )void saveAlumno(
    @Param("oid") Long oid
    ,@Param("idSolicitud") Long idSolicitud
  );

  @Transactional
  @Modifying
  @Procedure(procedureName="DELPHOS.ADD_ACTUALIZACION_ENVIADA_CON_NUEVOS_ALUMNOS")
  void updateCrtptFromUsuariost(
    @Param("anno") Long anno
  );

  @Query(value = "SELECT DISTINCT mat.X_UNIDAD idUnidad, UNI.T_NOMBRE AS nombreUnidad " +
      "FROM TLMATALU MAT " +
      "INNER JOIN TLUNIDADESCEN UNI ON UNI.X_UNIDAD = MAT.X_UNIDAD " +
      "WHERE MAT.X_CENTRO = :idCentro " +
      "AND MAT.C_ANNO = :anno " +
      "AND MAT.X_OFERTAMATRIG = :idCurso " +
      "AND (MAT.X_ESTGENMATR IS NULL OR MAT.X_ESTGENMATR NOT IN ('22','41')) " +
      "ORDER BY UNI.T_NOMBRE", nativeQuery = true)
  List<UnidadProjection> getUnidadesCorreoAlumnado(@Param("idCentro") Long idCentro, @Param("anno") Integer anno, @Param("idCurso") Long idCurso);

  @Query(value = "SELECT PER.ID_PERMISO_CORREO_ALUMNADO idPermiso, CD_PERMISO_CORREO_ALUMNADO codPermiso, " +
      "TX_DESCRIPCION descripcion " +
      "FROM DELPHOS_MODACC.CORR_PERMISOS_CORREO_ALUMNADO PER", nativeQuery = true)
  List<PermisoProjection> getPermisosCorreoAlumnado();


  @Query(value = "SELECT USUT.OID oidUsuario, "+
      "ALU.T_APELLIDO1||' '||ALU.T_APELLIDO2||', '||ALU.T_NOMBRE AS nombreCompleto, "+
      "UNI.T_NOMBRE AS unidad, " +
      "USUT.LG_HABILITAOWA AS estado, " +
      "USUT.CD_PERMISO_CORREO_ALUMNADO AS codPermiso, " +
      "USUT.L_PENDIENTE AS lPendiente " +
      "FROM TLMATALU MAT " +
      "INNER JOIN TLALUMNOS ALU ON ALU.X_ALUMNO = MAT.X_ALUMNO " +
      "INNER JOIN TLUNIDADESCEN UNI ON UNI.X_UNIDAD = MAT.X_UNIDAD " +
      "INNER JOIN DELPHOS_MODACC.PERSONAS PER ON PER.X_PERSONA = ALU.X_PERSONA " +
      "INNER JOIN DELPHOS_MODACC.USUARIOS_T USUT ON USUT.OID_PERSONA = PER.OID " +
      "WHERE MAT.X_CENTRO = :idCentro " +
      "AND MAT.C_ANNO = :anno " +
      "AND MAT.X_OFERTAMATRIG = :idCurso " +
      "AND USUT.UID_AZURE IS NOT NULL " +
      "AND USUT.CORREO_AULA IS NOT NULL " +
      "AND (MAT.X_ESTGENMATR IS NULL OR MAT.X_ESTGENMATR NOT IN ('22','41')) " +
      "ORDER BY ALU.T_APELLIDO1, ALU.T_APELLIDO2", nativeQuery = true)
  List<AlumnoDetalleProjection> getAlumnosDetalleCorreoAlumnado(
    @Param("idCentro") Long idCentro,
    @Param("anno") Integer anno,
    @Param("idCurso") Long idCurso);

  @Modifying
  @Query(value=""
  +"UPDATE USUARIOS_T SET"
  +"  CD_PERMISO_CORREO_ALUMNADO = :idPermiso,"
  +"  L_PENDIENTE = 'S'"
  +" WHERE OID = :idUsuarioT"
  ,nativeQuery=true)
  void actualizarPermisoUsuariosT(
    @Param("idUsuarioT") Long idUsuarioT,
    @Param("idPermiso")  String idPermiso
  );

  @Query(value = "SELECT CASE " +
          "  WHEN 1 = DELPHOS.tlf_omgcuelgaetapa(:idOfertamatrig, 1630) /* ESO */ " +
          "  OR 1 = DELPHOS.tlf_omgcuelgaetapa(:idOfertamatrig, 1658) /* PRIMARIA */ " +
          "  THEN 1 " +
          "  ELSE 0 " +
          "  END AS resultado " +
          "FROM DUAL ", nativeQuery = true)
  Integer esCursoPrimariaESO(@Param("idOfertamatrig") Long idOfertamatrig);

}
