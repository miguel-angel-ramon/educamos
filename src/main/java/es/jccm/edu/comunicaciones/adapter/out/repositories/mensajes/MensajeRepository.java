package es.jccm.edu.comunicaciones.adapter.out.repositories.mensajes;

import java.sql.Blob;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.ColectivosProjection;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.GrupoDeAlumnosProjection;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.MiembrosColectivosProjection;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.OfertaMatriculaProjection;
import es.jccm.edu.comunicaciones.application.domain.mensajes.Mensaje;
import es.jccm.edu.comunicaciones.application.domain.mensajes.QMensaje;
import es.jccm.edu.comunicaciones.application.domain.mensajes.projection.MensajeProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface MensajeRepository extends AbstractRepository<Mensaje, Long, QMensaje> {

	@Query(value = "SELECT B_FICHERO FROM DELPHOS_SEGEDU.TLFICMENUSU WHERE X_FICMENUSU = :idFicheroAdjunto", nativeQuery = true)
	Blob getDatosAdjuntosById(@Param("idFicheroAdjunto") Long idFicheroAdjunto);
	
	@Query(value = "SELECT "
			+ "des.x_desmenusu idDestinatarioMensaje, "
			+ "men.x_menusu id, "
			+ "men.x_usuario_remitente idRemitente, "
			+ "men.f_envio fechaMensaje, "
			+ "men.t_mensaje cuerpoMensaje, "
			+ "nvl(("
			+ "SELECT "
			+ "delphos.tlf_nombreempleado(usu.x_empleado) "
			+ "FROM "
			+ "delphos.tlusuarios usu "
			+ "WHERE "
			+ "usu.x_usuario = men.x_usuario_remitente "
			+ "),("
			+ "SELECT "
			+ "delphos_segedu.tlf_nombreempleado(usu.x_empleado) "
			+ "FROM "
			+ "DELPHOS_SEGEDU.tlusuarios usu "
			+ "WHERE "
			+ "usu.x_usuario = men.x_usuario_remitente "
			+ ")) remitente, "
			+ "men.t_asunto asunto, "
			+ "DECODE(MEN.X_MENUSU_ORIGEN, NULL, 'false', 'true') respuesta, "
			+ "col.d_colusurec grupo, "
			+ "men.x_colectivo idGrupo, "
			+ "MEN.F_ENVIO AS F_ENVIDO, "
			+ "DECODE(des.l_leido, 'S', 'true', 'false') leido, "
			+ "decode(nvl(("
			+ " SELECT DISTINCT "
			+ " (1) "
			+ "FROM "
			+ "DELPHOS_SEGEDU.tlficmenusu fmu "
			+ "WHERE "
			+ "fmu.x_menusu = men.x_menusu "
			+ " ), 0), 0, 'false', 'true') adjuntos, "
			+ "DECODE(men.l_borrado, 'S', 'true', 'false') borradoParaTodos "
			+ "FROM "
			+ "DELPHOS_SEGEDU.tlmenusu men, "
			+ "DELPHOS_SEGEDU.tldesmenusu des, "
			+ "DELPHOS_SEGEDU.tlcolectivos col "
			+ "WHERE "
			+ "des.x_usuario_destinatario = :idUsuario  "
			+ "AND des.x_menusu = men.x_menusu "
			+ "AND nvl(des.l_eliminado, 'N') = 'N' "
			+ "AND NVL(men.l_borrado, 'N') = 'N' "
			+ "AND des.l_eliminado = 'N' "
			+ "AND col.x_colectivo (+) = men.x_colectivo "
			+ "AND (-1 = :idColectivo OR MEN.X_COLECTIVO = :idColectivo) " 
			+ "AND ('-1' = :leido OR (DES.L_LEIDO = :leido OR NVL(DES.L_LEIDO, 'N') = :leido))"
			+ "ORDER BY f_envido DESC", 
			countQuery = " SELECT   "
					+ "COUNT(*)   "
					+ "FROM   "
					+ "DELPHOS_SEGEDU.tlmenusu men,   "
					+ "DELPHOS_SEGEDU.tldesmenusu des,   "
					+ "DELPHOS_SEGEDU.tlcolectivos col  "
					+ "WHERE   "
					+ "des.x_usuario_destinatario =:idUsuario   "
					+ "AND des.x_menusu = men.x_menusu   "
					+ "AND nvl(des.l_eliminado, 'N') = 'N'   "
					+ "AND des.l_eliminado = 'N'   "
					+ "AND NVL(men.l_borrado, 'N') = 'N'   "
					+ "AND col.x_colectivo (+) = men.x_colectivo   "
					+ "AND (-1 = :idColectivo OR MEN.X_COLECTIVO = :idColectivo)    "
					+ "AND ('-1' = :leido OR (DES.L_LEIDO = :leido OR NVL(DES.L_LEIDO, 'N') = :leido))", nativeQuery = true)
	Page<MensajeProjection> getMensajesRecibidos(@Param("idUsuario") String idUsuario, @Param("idColectivo") int idColectivo,
			@Param("leido") String leido, Pageable pageable);

	@Query(value = "SELECT * FROM (" +
	        "SELECT men.x_menusu id, " +
	        "des.x_desmenusu idDestinatarioMensaje, " +
	        "men.f_envio fechaMensaje, " +
	        "men.t_mensaje cuerpoMensaje, " +
	        "men.x_usuario_remitente idRemitente, " +
	        "NVL((" +
	        "    SELECT delphos.tlf_nombreempleado(usu.x_empleado) " +
	        "    FROM delphos.tlusuarios usu " +
	        "    WHERE usu.x_usuario = men.x_usuario_remitente" +
	        "),(" +
	        "    SELECT delphos_segedu.tlf_nombreempleado(usu.x_empleado) " +
	        "    FROM DELPHOS_SEGEDU.tlusuarios usu " +
	        "    WHERE usu.x_usuario = men.x_usuario_remitente" +
	        ")) remitente, " +
	        "DECODE(des.l_leido, 'S', 'true', 'false') leido, " +
	        "DECODE(col.x_colectivo, " +
	        "NULL, delphos_segedu.tlf_destinatariosmensaje(men.x_menusu), " +
	        "NVL((SELECT d_colusuenv " +
	        "FROM delphos_segedu.tlcolperpueenvmen " +
	        "WHERE x_colectivo = col.x_colectivo AND x_perfil = 5001), " +
	        "(SELECT d_colectivo FROM delphos_segedu.tlcolectivos WHERE x_colectivo = col.x_colectivo))) destinatarios, " +
	        "men.t_asunto asunto, " +
	        "men.f_envio AS F_ENVIDO, " +
	        "DECODE(MEN.X_MENUSU_ORIGEN, NULL, 'false', 'true') respuesta, " +
	        "delphos_segedu.tlf_destinatarios(men.x_menusu, 10) numeroDestinatarios, " +
	        "delphos_segedu.tlf_destinatarios(men.x_menusu, 1) numeroLeidos, " +
	        "DECODE(NVL((SELECT DISTINCT 1 " +
	        "FROM delphos_segedu.tlficmenusu fmu " +
	        "WHERE fmu.x_menusu = men.x_menusu), 0), " +
	        "0, 'false', 'true') adjuntos, " +
	        "DECODE(men.l_borrado, 'S', 'true', 'false') borradoParaTodos, " +
	        "ROW_NUMBER() OVER (PARTITION BY men.x_menusu ORDER BY men.f_envio DESC) AS rn " +
	        "FROM delphos_segedu.tlmenusu men, delphos_segedu.tldesmenusu des, " +
	        "delphos_segedu.tlcolectivos col " +
	        "WHERE men.x_usuario_remitente = :idUsuario " +
	        "AND men.l_eliminado = 'N' " +
	        "AND des.x_menusu = men.x_menusu " +
	        "AND col.x_colectivo(+) = men.x_colectivo " +
	        "AND NVL(men.l_borrado, 'N') = 'N' " +
	        "AND (-1 = :idColectivo OR MEN.X_COLECTIVO = :idColectivo) " +
	        "AND ('-1' = :leido OR (DES.L_LEIDO = :leido OR NVL(DES.L_LEIDO, 'N') = :leido)) " +
	        ") WHERE rn = 1 " +
	        "ORDER BY fechaMensaje DESC",
	        countQuery = "SELECT COUNT(DISTINCT men.x_menusu) " +
	        "FROM delphos_segedu.tlmenusu men,  delphos_segedu.tldesmenusu des, " +
	        "delphos_segedu.tlcolectivos col " +
	        "WHERE men.x_usuario_remitente = :idUsuario " +
	        "AND men.l_eliminado = 'N' " +
	        "AND des.x_menusu = men.x_menusu " +
	        "AND NVL(men.l_borrado, 'N') = 'N' " +
	        "AND col.x_colectivo(+) = men.x_colectivo " +
	        "AND (-1 = :idColectivo OR MEN.X_COLECTIVO = :idColectivo) " +
	        "AND ('-1' = :leido OR (DES.L_LEIDO = :leido OR NVL(DES.L_LEIDO, 'N') = :leido))",
	        nativeQuery = true)
	Page<MensajeProjection> getMensajesEnviados(@Param("idUsuario") String idUsuario,
	        @Param("idColectivo") int idColectivo,
	        @Param("leido") String leido, Pageable pageable);




	
	@Query(value = "SELECT DISTINCT des.x_desmenusu idDestinatarioMensaje, "
			+ "men.x_menusu id, "
			+ "men.f_envio fechaMensaje, "
			+ "men.t_mensaje cuerpoMensaje, "
			+ "men.x_usuario_remitente idRemitente, "
			+ "NVL ( "
			+ "(select delphos.tlf_nombreempleado(usu.x_empleado) from delphos.tlusuarios usu where usu.x_usuario = men.x_usuario_remitente), "
			+ "(select tlf_nombreempleado(usu.x_empleado) from delphos_segedu.tlusuarios usu where usu.x_usuario = men.x_usuario_remitente) "
			+ ") remitente, "
			+ "men.t_asunto asunto, "
			+ "DECODE (men.x_menusu_origen, NULL, 'false', 'true') respuesta, "
			+ "col.d_colusurec grupo, men.x_colectivo idGrupo, "
			+ "men.f_envio AS F_ENVIDO, DECODE(des.l_leido, 'S', 'true', 'false') leido, "
			+ "DECODE (NVL ((SELECT DISTINCT (1) "
			+ "FROM delphos_segedu.tlficmenusu fmu "
			+ "WHERE fmu.x_menusu = men.x_menusu), 0), "
			+ "0, 'false', 'true') adjuntos "
			+ "FROM delphos_segedu.tlmenusu men, delphos_segedu.tldesmenusu des, delphos_segedu.tlcolectivos col "
			+ "WHERE des.x_menusu = men.x_menusu "
			+ "AND des.x_usuario_destinatario = :idUsuario "
			+ "AND des.l_eliminado = 'S' "
			+ "AND NVL(men.l_borrado, 'N') = 'N' "
			+ "AND des.x_menusu = men.x_menusu "
			+ "AND col.x_colectivo(+) = men.x_colectivo "
			+ "AND (-1 = :idColectivo OR MEN.X_COLECTIVO = :idColectivo) " 
			+ "AND ('-1' = :leido OR (DES.L_LEIDO = :leido OR NVL(DES.L_LEIDO, 'N') = :leido))",
			countQuery = "SELECT COUNT(*) "
					+ "FROM delphos_segedu.tlmenusu men, delphos_segedu.tldesmenusu des, delphos_segedu.tlcolectivos col "
					+ "WHERE des.x_menusu = men.x_menusu "
					+ "AND des.x_usuario_destinatario = :idUsuario "
					+ "AND des.l_eliminado = 'S' "
					+ "AND NVL(men.l_borrado, 'N') = 'N' "
					+ "AND des.x_menusu = men.x_menusu "
					+ "AND col.x_colectivo(+) = men.x_colectivo "
					+ "AND (-1 = :idColectivo OR MEN.X_COLECTIVO = :idColectivo) " 
					+ "AND ('-1' = :leido OR (DES.L_LEIDO = :leido OR NVL(DES.L_LEIDO, 'N') = :leido))", nativeQuery = true)
	Page<MensajeProjection> getMensajesArchivados(@Param("idUsuario") String idUsuario, @Param("idColectivo") String idColectivo,
			@Param("leido") String leido, Pageable pageable);
	
	@Query(value = "SELECT DISTINCT des.x_desmenusu idDestinatarioMensaje, "
			+ "men.x_menusu id, "
			+ "men.f_envio fechaMensaje, "
			+ "NVL ( "
			+ "(select delphos.tlf_nombreempleado(usu.x_empleado) from delphos.tlusuarios usu where usu.x_usuario = men.x_usuario_remitente), "
			+ "(select tlf_nombreempleado(usu.x_empleado) from delphos_segedu.tlusuarios usu where usu.x_usuario = men.x_usuario_remitente) "
			+ ") remitente, "
			+ "men.t_asunto asunto,"
			+ "DECODE (men.x_menusu_origen, NULL, 'false', 'true') respuesta, "
			+ "col.d_colusurec grupo, men.x_colectivo idGrupo, "
			+ "men.f_envio AS F_ENVIDO, DECODE(des.l_leido, 'S', 'true', 'false') leido, "
			+ "DECODE (NVL ((SELECT DISTINCT (1) "
			+ "FROM delphos_segedu.tlficmenusu fmu "
			+ "WHERE fmu.x_menusu = men.x_menusu), 0), "
			+ "0, 'false', 'true') adjuntos "
			+ "FROM delphos_segedu.tlmenusu men, delphos_segedu.tldesmenusu des, delphos_segedu.tlcolectivos col "
			+ "WHERE des.x_usuario_destinatario = :idUsuario "
			+ "AND des.x_menusu = men.x_menusu "
			+ "AND des.l_eliminado = 'S' "
			+ "AND col.x_colectivo(+) = men.x_colectivo "
			+ "AND MEN.X_COLECTIVO = :idColectivo", 
			countQuery = "SELECT COUNT(*) "
			+ "FROM delphos_segedu.tlmenusu men, delphos_segedu.tldesmenusu des, delphos_segedu.tlcolectivos col "
			+ "WHERE des.x_usuario_destinatario = :idUsuario "
			+ "AND des.x_menusu = men.x_menusu "
			+ "AND des.l_eliminado = 'S' "
			+ "AND col.x_colectivo(+) = men.x_colectivo "
			+ "AND MEN.X_COLECTIVO = :idColectivo", nativeQuery = true)
	Page<MensajeProjection> getMensajesArchivadosByColectivo(@Param("idUsuario") String idUsuario, @Param("idColectivo") String idColectivo, Pageable pageable);
	
	@Query(value = "SELECT "
			+ "fot.b_foto foto "
			+ "FROM "
			+ "delphos_segedu.tlusuarios usu, "
			+ "delphos_segedu.tlempleados emp1, "
			+ "delphos_modacc.conf_usuarios cnf, "
			+ "delphos.tlempleados emp2, "
			+ "delphos.tlempleadosfoto fot "
			+ "WHERE usu.x_usuario = :idRemitente "
			+ "AND emp1.x_empleado = usu.x_empleado "
			+ "AND cnf.oid_usuario = (SELECT USU.OID "
			+ "FROM DELPHOS_MODACC.USUARIOS USU, DELPHOS_MODACC.DOCUMENTACIONES DOC "
			+ "WHERE USU.OID_PERSONA = DOC.OID_PERSONA  "
			+ "AND DOC.T_IDENTIFICACION = emp1.c_numide) "
			+ "AND UPPER(emp2.c_numide) = UPPER(emp1.c_numide) "
			+ "AND fot.x_empleado = emp2.x_empleado", nativeQuery = true)
	Blob getFotoRemitente(@Param("idRemitente") Long idRemitente);
	
	@Query(value = "SELECT id, codigoColectivo, descripcionCortaColectivo, descripcionLargaColectivo, permiteFiltrar, permiteRespuesta, xPerfil FROM ( "
            + "SELECT COL.X_COLECTIVO AS id, "
            + "       COL.C_COLECTIVO AS codigoColectivo, "
            + "       COLENV.D_COLUSUENV AS descripcionCortaColectivo, "
            + "       COLENV.D_COLUSUENV AS descripcionLargaColectivo, "
            + "       COL.L_FILTRAR AS permiteFiltrar, "
            + "       COL.L_PERRES AS permiteRespuesta, "
            + "       PER.X_PERFIL AS xPerfil, "
            + "       ROW_NUMBER() OVER (PARTITION BY COL.X_COLECTIVO ORDER BY PER.X_PERFIL) AS rn "
            + "FROM DELPHOS_SEGEDU.TLPERFILUSU PER "
            + "INNER JOIN DELPHOS_SEGEDU.TLCOLPERPUEENVMEN COLENV ON COLENV.X_PERFIL = PER.X_PERFIL "
            + "INNER JOIN DELPHOS_SEGEDU.TLCOLECTIVOS COL ON COL.X_COLECTIVO = COLENV.X_COLECTIVO "
            + "WHERE PER.X_USUARIO = :idUsuarioComunica "
            + "AND PER.X_PERFIL = :xPerfil "
            + ") Subquery "
            + "WHERE rn = 1 ORDER BY descripcionLargaColectivo ASC", nativeQuery = true)
	List<ColectivosProjection> getColectivosUsuario(@Param("idUsuarioComunica") Long idUsuarioComunica, @Param("xPerfil") Long xPerfil);
	
	@Query(value = "SELECT "
	        + "parcol.X_COLECTIVO AS xColectivo, "
	        + "parcol.N_ORDEN AS nOrden, "
	        + "t.C_TIPPARMENCOL AS cTipParmEnCol, "
	        + "t.D_TIPPARMENCOL AS dTipParmEnCol, "
	        + "t2.T_SENTENCIA AS tSentencia, "
	        + "t2.T_SENTENCIA_CONTINUA AS tSentenciaContinua "
	        + "FROM DELPHOS_SEGEDU.TLPARSENMENCOL parcol "
	        + "INNER JOIN DELPHOS_SEGEDU.TLTIPPARMENCOL t ON t.X_TIPPARMENCOL = parcol.X_TIPPARMENCOL "
	        + "INNER JOIN DELPHOS_SEGEDU.TLCOLECTIVOS t2 ON t2.X_COLECTIVO = parcol.X_COLECTIVO "
	        + "WHERE parcol.X_COLECTIVO IN ( "
	        + "    SELECT COL.X_COLECTIVO "
	        + "    FROM DELPHOS_SEGEDU.TLPERFILUSU PER "
	        + "    INNER JOIN DELPHOS_SEGEDU.TLCOLPERPUEENVMEN COLENV ON COLENV.X_PERFIL = PER.X_PERFIL "
	        + "    INNER JOIN DELPHOS_SEGEDU.TLCOLECTIVOS COL ON COL.X_COLECTIVO = COLENV.X_COLECTIVO "
	        + "    WHERE COL.X_COLECTIVO = :idColectivo) "
	        + "AND parcol.C_TIPSEN = 'SENT_MIEMBROS' "
	        + "ORDER BY parcol.X_COLECTIVO, parcol.N_ORDEN",
	        nativeQuery = true)
	List<MiembrosColectivosProjection> getMiembrosColectivo(@Param("idColectivo") Long idColectivo);

	@Query(value = "SELECT omg.X_OFERTAMATRIG AS xOfertaMatrig, omg.X_MODALIDAD AS xModalidad, omg.D_OFERTAMATRIG AS dOfertaMatrig "
			+ "FROM DELPHOS.TLOFEMATRGEN omg "
			+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLCENTROS centro ON centro.X_CENTRO = omc.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLPERIODOSOMC periodo ON periodo.X_OFERTAMATRIC = omc.X_OFERTAMATRIC "
			+ "INNER JOIN DELPHOS.TLCURSOMODA cm ON cm.X_CURSOMOD = omg.X_OFERTAMATRIG "
			+ "WHERE centro.C_CODIGO = :codigoCentro "
			+ "AND omg.L_VIGENTE = 'S' "
			+ "AND periodo.C_ANNO <= :anyo "
			+ "AND (periodo.C_ANNOPUEDETERMINAR IS NULL OR periodo.C_ANNOPUEDETERMINAR >= :anyo) "
			+ "AND exists (select 1 from DELPHOS.tletapas e where e.x_etapa=cm.x_etapa) ", nativeQuery = true)
	List<OfertaMatriculaProjection> getOfertaMatriculabyCodCentro(@Param("codigoCentro") Long codigoCentro, @Param("anyo") Long anyo);
	
	@Query(value = "SELECT DISTINCT convu.X_UNIDAD AS idUnidad, omg.X_OFERTAMATRIG AS xOfertaMatrig, omg.D_OFERTAMATRIG AS dOfertaMatrig, uni.T_NOMBRE AS tNombre "
	        + "FROM DELPHOS.TLOFEMATRGEN omg "
	        + "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
	        + "INNER JOIN DELPHOS.TLCENTROS centro ON centro.X_CENTRO = omc.X_CENTRO "
	        + "INNER JOIN DELPHOS.TLPERIODOSOMC periodo ON periodo.X_OFERTAMATRIC = omc.X_OFERTAMATRIC "
	        + "INNER JOIN DELPHOS.TLCURSOMODA cm ON cm.X_CURSOMOD = omg.X_OFERTAMATRIG "
	        + "INNER JOIN DELPHOS.TLCONVCENOMC cco ON cco.X_OFERTAMATRIC = omc.X_OFERTAMATRIC "
	        + "INNER JOIN DELPHOS.TLCONVUNIDAD convu ON convu.X_CONVCENTROOMC = cco.X_CONVCENTROOMC "
	        + "INNER JOIN delphos.TLUNIDADESCEN uni ON convu.X_UNIDAD = uni.X_UNIDAD AND uni.C_ANNO = :anyo "
	        + "WHERE centro.C_CODIGO = :codigoCentro "
	        + "AND omg.L_VIGENTE = 'S' "
	        + "AND periodo.C_ANNO <= :anyo "
	        + "AND (periodo.C_ANNOPUEDETERMINAR IS NULL OR periodo.C_ANNOPUEDETERMINAR >= :anyo)", 
	        nativeQuery = true)
	List<GrupoDeAlumnosProjection> getGrupoDeAlumnos(@Param("codigoCentro") Long codigoCentro, @Param("anyo") Long anyo);

	
}
