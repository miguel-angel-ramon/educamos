package es.jccm.edu.simulacion.adapter.out.repositories.usuarios;

import java.sql.Blob;
import java.util.List;

import es.jccm.edu.simulacion.application.domain.usuarios.projection.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.QCentro;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import es.jccm.edu.shared.configuration.common.Constants;

@Repository
public interface UsuarioRepository extends AbstractRepository<Centro, Long, QCentro> {

	@Query(value = "SELECT USU.X_USUARIO idUsuario, USU.USUARIO oIdUsuario, DELPHOS_SEGEDU.tlf_nombreempleado(USU.X_EMPLEADO) nombre "
			+ "FROM DELPHOS_SEGEDU.TLUSUARIOS USU " + "WHERE USU.L_ACTIVO = 'S' " + "AND USU.L_BLOQUEADO = 'N'  "
			+ "AND EXISTS (SELECT  1 "
			+ "FROM DELPHOS_SEGEDU.TLPTOTRAEMP PTE, DELPHOS_SEGEDU.TLPUEORIPER PUE, DELPHOS_SEGEDU.TLCENTROS CEN "
			+ "WHERE USU.X_USUARIO = PUE.X_USUARIO " + "AND PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS " + "AND TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND NVL(TRUNC(PTE.F_CESE), TRUNC(SYSDATE)) >= TRUNC(SYSDATE) " + "AND PTE.X_CENTRO = CEN.X_CENTRO "
			+ "AND CEN.C_CODIGO = :codCentro " + "AND PUE.X_PERFIL = :codPerfil) "
			+ "ORDER BY nombre", nativeQuery = true)
	List<UsuarioProjection> getAllUsuarios(@Param("codCentro") Long codCentro, @Param("codPerfil") Long codPerfil);

	@Query(value = "SELECT USU.X_USUARIO idUsuario, USU.USUARIO oIdUsuario, tlf_nombreempleado(USU.X_EMPLEADO) nombre "
			+ "FROM DELPHOS_SEGEDU.TLUSUARIOS USU " + "WHERE USU.L_ACTIVO = 'S' " + "AND USU.L_BLOQUEADO = 'N' "
			+ "AND EXISTS (SELECT 1 FROM TLPTOTRAEMP PTE, TLPUEORIPER PUE, TLCENTROS CEN "
			+ "WHERE USU.X_USUARIO = PUE.X_USUARIO " + "AND PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS " + "AND TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND NVL(TRUNC(PTE.F_CESE), TRUNC(SYSDATE)) >= TRUNC(SYSDATE) " + "AND PTE.X_CENTRO = CEN.X_CENTRO "
			+ "AND CEN.C_CODIGO = :codCentro " + "AND PUE.X_PERFIL = :codPerfil) "
			+ "ORDER BY nombre", nativeQuery = true)
	List<UsuarioProjection> getAllUsuariosDelphos(@Param("codCentro") Long codCentro,
			@Param("codPerfil") Long codPerfil);

	@Query(value = "SELECT USU.USUARIO idUsuario, USU.X_USUARIO xUsuario, EMP.X_EMPLEADO idEmpleado, EMP.C_NUMIDE numide,"
			+ " EMP.NOMBRE nombre, EMP.APELLIDO1 apellido1, EMP.APELLIDO2 apellido2, EMP.T_CORREO_E email, CONF.DEFAULT_ROL perfil_def, "
            + " CONF.DEFAULT_CEN centro_def,  CONF.LG_SKIPTOUR tour, CONF.LG_SKIPTOUREVA tourEvaluacion, CONF.LG_ACCPILOTO piloto "
			+ " FROM TLEMPLEADOS EMP, TLUSUARIOS USU, DELPHOS_MODACC.CONF_USUARIOS CONF "
			+ " WHERE EMP.X_EMPLEADO = USU.X_EMPLEADO " + "AND USU.USUARIO = :idUsuario AND CONF.OID_USUARIO = ( "
			+ " SELECT USU.OID"
			+ " FROM DELPHOS_MODACC.USUARIOS USU, DELPHOS_MODACC.DOCUMENTACIONES DOC "
			+ " WHERE USU.OID_PERSONA = DOC.OID_PERSONA "
			+ " AND DOC.T_IDENTIFICACION = emp.c_numide) ", nativeQuery = true)
	DatosUsuarioProjection getDatosUsuario(@Param("idUsuario") String idUsuario);

	@Query(value = "SELECT USU.USUARIO idUsuario, USU.X_USUARIO xUsuario, EMP.X_EMPLEADO idEmpleado, EMP.C_NUMIDE numide,"
			+ " EMP.NOMBRE nombre, EMP.APELLIDO1 apellido1, EMP.APELLIDO2 apellido2, EMP.T_CORREO_E email, CONF.DEFAULT_ROL perfil_def, "
			+ " CONF.DEFAULT_CEN centro_def,  CONF.LG_SKIPTOUR tour, CONF.LG_SKIPTOUREVA tourEvaluacion, CONF.LG_ACCPILOTO piloto "
			+ " FROM DELPHOS_SEGEDU.TLEMPLEADOS EMP, DELPHOS_SEGEDU.TLUSUARIOS USU, DELPHOS_MODACC.CONF_USUARIOS CONF "
			+ " WHERE EMP.X_EMPLEADO = USU.X_EMPLEADO " + "AND USU.USUARIO = :idUsuario AND CONF.OID_USUARIO = ( "
			+ " SELECT USU.OID"
			+ " FROM DELPHOS_MODACC.USUARIOS USU, DELPHOS_MODACC.DOCUMENTACIONES DOC "
			+ " WHERE USU.OID_PERSONA = DOC.OID_PERSONA "
			+ " AND DOC.T_IDENTIFICACION = emp.c_numide) ", nativeQuery = true)
	DatosUsuarioProjection getDatosUsuarioComunica(@Param("idUsuario") String idUsuario);

	@Query(value = "SELECT FOT.B_FOTO foto " + "FROM TLEMPLEADOS E, TLEMPLEADOSFOTO FOT "
			+ "WHERE E.C_NUMIDE = :numide AND E.X_EMPLEADO = FOT.X_EMPLEADO", nativeQuery = true)
	Blob getfotoUsuario(@Param("numide") String numide);

	@Query(value = "SELECT DISTINCT PER.X_PERFIL idPerfil, PER.C_CODIGO codPerfil, PER.D_PERFIL nombrePerfil "
			+ "FROM DELPHOS_SEGEDU.TLPERFILES PER, DELPHOS_SEGEDU.TLUSUARIOS USU, DELPHOS_SEGEDU.TLPTOTRAEMP PTE, DELPHOS_SEGEDU.TLPUEORIPER PUE "
			+ "WHERE PTE.X_EMPLEADO = USU.X_EMPLEADO " + "AND PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS " + "AND PUE.X_PERFIL = PER.X_PERFIL "
			+ "AND (TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND (TRUNC(PTE.F_CESE) >= TRUNC(SYSDATE) OR PTE.F_CESE IS NULL)) "
			+ "AND USU.USUARIO = :idUsuario", nativeQuery = true)
	List<PerfilUsuarioProjection> getPerfilesUsuario(@Param("idUsuario") String idUsuario);

	@Query(value = "SELECT DISTINCT PER.X_PERFIL idPerfil, PER.C_CODIGO codPerfil, PER.D_PERFIL nombrePerfil  " +
			" FROM TLPERFILES PER, TLUSUARIOS USU, TLPTOTRAEMP PTE, TLPUEORIPER PUE, TLEMPLEADOS EMP  " +
			" WHERE PTE.X_EMPLEADO = USU.X_EMPLEADO   AND USU.X_EMPLEADO = EMP.X_EMPLEADO  " +
			" AND PTE.X_EMPLEADO = PUE.X_EMPLEADO   AND PTE.F_TOMAPOS = PUE.F_TOMAPOS  " +
			" AND PUE.X_PERFIL = PER.X_PERFIL   AND (TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE)  " +
			" AND (TRUNC(PTE.F_CESE) >= TRUNC(SYSDATE) OR PTE.F_CESE IS NULL)) " +
			" AND USU.USUARIO = :idUsuario", nativeQuery = true)
	List<PerfilUsuarioProjection> getPerfilesDelphosUsuario(@Param("idUsuario") String idUsuario);

	// TODO Quitar la condición de X_PERFIL = 5002 en el futuro para el resto de
	// dashboards de perfiles
	// TODO Revisar comprobación AND PUE.X_USUARIO = USU.X_USUARIO
	@Query(value = "SELECT  CEN.C_CODIGO codCentro, CEN.X_CENTRO idCentro, DGE.S_DENOMINACION denominacionCentro, "
			+ "DCE.D_ESPECIFICA nombreCentro, PUE.X_PERFIL idPerfil, PTE.F_TOMAPOS fechaTomaPosesion  "
			+ " FROM DELPHOS_SEGEDU.TLEMPLEADOS EMP, DELPHOS_SEGEDU.TLUSUARIOS USU, "
			+ " DELPHOS_SEGEDU.TLPTOTRAEMP PTE, DELPHOS_SEGEDU.TLPUEORIPER PUE, DELPHOS_SEGEDU.TLCENTROS CEN,  "
			+ " DELPHOS_SEGEDU.TLDATOSCEN DCE, DELPHOS_SEGEDU.TLDENGEN DGE   WHERE USU.USUARIO  = :idUsuario  "
			+ " AND USU.X_EMPLEADO = EMP.X_EMPLEADO   AND EMP.X_EMPLEADO = PTE.X_EMPLEADO  "
			+ " AND PTE.F_TOMAPOS = PUE.F_TOMAPOS   AND PTE.X_EMPLEADO = PUE.X_EMPLEADO  "
			+ " AND (TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE)  "
			+ " AND (TRUNC(PTE.F_CESE) >= TRUNC(SYSDATE) OR PTE.F_CESE IS NULL)) "
			+ " AND PTE.X_CENTRO = CEN.X_CENTRO   AND CEN.X_CENTRO = DCE.X_CENTRO  "
			+ " AND DCE.X_DENGEN = DGE.X_DENGEN   AND DCE.L_VIGENTE = 'S'  "
			+ " ORDER BY fechaTomaPosesion DESC", nativeQuery = true)
	List<CentroUsuarioProjection> getCentrosUsuario(@Param("idUsuario") String idUsuario);

	// TODO Quitar la condición de X_PERFIL = 161 en el futuro para el resto de
	// dashboards de perfiles
	@Query(value = "SELECT DISTINCT CEN.C_CODIGO codCentro, CEN.X_CENTRO idCentro, DGE.S_DENOMINACION denominacionCentro, DCE.D_ESPECIFICA nombreCentro, PUE.X_PERFIL idPerfil, PTE.F_TOMAPOS fechaTomaPosesion "
			+ "	FROM TLEMPLEADOS EMP, TLUSUARIOS USU, TLPTOTRAEMP PTE, TLPUEORIPER PUE, TLCENTROS CEN, TLDATOSCEN DCE, TLDENGEN DGE "
			+ "	WHERE EMP.X_EMPLEADO = USU.X_EMPLEADO AND PTE.X_EMPLEADO = EMP.X_EMPLEADO "
			+ "	AND PTE.F_TOMAPOS = PUE.F_TOMAPOS AND PUE.X_USUARIO = USU.X_USUARIO "
			+ "	AND (TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "	AND (TRUNC(PTE.F_CESE) >= TRUNC(SYSDATE) OR PTE.F_CESE IS NULL)) AND PTE.X_CENTRO = CEN.X_CENTRO "
			+ "	AND CEN.X_CENTRO = DCE.X_CENTRO AND DCE.X_DENGEN = DGE.X_DENGEN AND DCE.L_VIGENTE = 'S' "
			+ "	AND USU.USUARIO = :idUsuario "
			+ "	ORDER BY fechaTomaPosesion DESC", nativeQuery = true)
	List<CentroUsuarioProjection> getCentrosDelphosUsuario(@Param("idUsuario") String idUsuario);

	@Query(value = "SELECT DISTINCT CEN.C_CODIGO codCentro, CEN.X_CENTRO idCentro, DGE.S_DENOMINACION denominacionCentro, DCE.D_ESPECIFICA nombreCentro, PUE.X_PERFIL idPerfil, PTE.F_TOMAPOS fechaTomaPosesion "
			+ " FROM TLEMPLEADOS EMP, TLUSUARIOS USU, TLPTOTRAEMP PTE, TLPUEORIPER PUE, TLCENTROS CEN, TLDATOSCEN DCE, TLDENGEN DGE "
			+ " WHERE EMP.X_EMPLEADO = USU.X_EMPLEADO AND PTE.X_EMPLEADO = EMP.X_EMPLEADO "
			+ " AND PTE.F_TOMAPOS = PUE.F_TOMAPOS AND PUE.X_USUARIO = USU.X_USUARIO "
			+ " AND (TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ " AND (TRUNC(PTE.F_CESE) >= TRUNC(SYSDATE) OR PTE.F_CESE IS NULL)) AND PTE.X_CENTRO = CEN.X_CENTRO "
			+ " AND CEN.X_CENTRO = DCE.X_CENTRO AND DCE.X_DENGEN = DGE.X_DENGEN AND DCE.L_VIGENTE = 'S' "
			+ " AND USU.X_EMPLEADO = :xEmpleado "
			+ " ORDER BY fechaTomaPosesion DESC ", nativeQuery = true)
	List<CentroUsuarioProjection> getCentrosDelphosUsuarioByXEmpleado(@Param("xEmpleado") Long xEmpleado);

	@Query(value = "SELECT EMP.C_NUMIDE documentacion, DECODE(EMP.CALLE, NULL, NULL, (SELECT S_TIPOVIA FROM DELPHOS_SEGEDU.TLTIPOVIAS WHERE X_TIPOVIA = EMP.X_TIPOVIA) || ' ' || EMP.CALLE || ', ') "
			+ "|| DECODE(EMP.NUMCAL, NULL, NULL, EMP.NUMCAL || ' ') || DECODE(EMP.ESCALERA, NULL, NULL, EMP.ESCALERA || ' ') || DECODE(EMP.PISO, NULL, NULL, EMP.PISO || ' ') "
			+ "|| DECODE(EMP.LETRA, NULL, NULL, EMP.LETRA) direccion, (SELECT D_PROVINCIA FROM DELPHOS_SEGEDU.TLPROVINCIAS WHERE C_PROVINCIA = EMP.C_PROVINCIA) provincia, "
			+ "(SELECT D_MUNICIPIO FROM DELPHOS_SEGEDU.TLMUNICIPIOS WHERE C_MUNICIPIO = EMP.C_MUNICIPIO AND C_PROVINCIA = EMP.C_PROVINCIA) municipio, "
			+ "EMP.F_NACIMIENTO fechaNacimiento, DECODE(EMP.L_SEXO, 'H', 'Hombre', 'Mujer') sexo, USUTMODACC.UID_LDAP usuarioLDAP, EMP.TELEFONO telefono1, EMP.T_MOVIL telefono2, "
			+ "(SELECT T_TELEFONO FROM DELPHOS_SEGEDU.TLTELUSU WHERE X_USUARIO = USU.X_USUARIO AND X_TIPTEL = 1) telefonoNotificaciones, "
			+ "EMP.T_CORREO_E CORREO, (SELECT T_CORREO_E FROM DELPHOS_SEGEDU.TLCORUSU WHERE X_USUARIO = USU.X_USUARIO) correoNotificaciones, USUTMODACC.MAIL_LDAP correoLdap "
			+ "FROM DELPHOS_SEGEDU.TLEMPLEADOS EMP, DELPHOS_SEGEDU.TLUSUARIOS USU, DELPHOS_MODACC.USUARIOS USUMODACC, DELPHOS_MODACC.USUARIOS_T USUTMODACC, DELPHOS_MODACC.DOCUMENTACIONES DOCMODACC "
			+ "WHERE EMP.X_EMPLEADO = USU.X_EMPLEADO " + "AND EMP.C_NUMIDE = DOCMODACC.T_IDENTIFICACION "
			+ "AND DOCMODACC.OID_PERSONA = USUMODACC.OID_PERSONA "
			+ "AND USUMODACC.OID_PERSONA = USUTMODACC.OID_PERSONA "
			+ "AND USU.X_USUARIO = :idUsuario AND ROWNUM = 1 ", nativeQuery = true)
	DatosPersonalesUsuarioProjection getDatosPersonalesUsuario(@Param("idUsuario") Long idUsuario);
	
	@Query(value = "SELECT EMP.C_NUMIDE documentacion,  "
			+ "       DECODE(EMP.CALLE,  "
			+ "       NULL,  "
			+ "       NULL,  "
			+ "      (SELECT S_TIPOVIA FROM DELPHOS_SEGEDU.TLTIPOVIAS WHERE X_TIPOVIA = EMP.X_TIPOVIA) || ' ' || EMP.CALLE || ', ')      "
			+ "         || DECODE(EMP.NUMCAL, NULL, NULL, EMP.NUMCAL || ' ') || DECODE(EMP.ESCALERA, NULL, NULL, EMP.ESCALERA || ' ') || DECODE(EMP.PISO, NULL, NULL, EMP.PISO || ' ')      "
			+ "         || DECODE(EMP.LETRA, NULL, NULL, EMP.LETRA) direccion, (SELECT D_PROVINCIA FROM DELPHOS_SEGEDU.TLPROVINCIAS WHERE C_PROVINCIA = EMP.C_PROVINCIA) provincia,      "
			+ "      (SELECT D_MUNICIPIO FROM DELPHOS_SEGEDU.TLMUNICIPIOS WHERE C_MUNICIPIO = EMP.C_MUNICIPIO AND C_PROVINCIA = EMP.C_PROVINCIA) municipio,      "
			+ "      EMP.F_NACIMIENTO fechaNacimiento, DECODE(EMP.L_SEXO, 'H', 'Hombre', 'Mujer') sexo, USUTMODACC.UID_LDAP usuarioLDAP, EMP.TELEFONO telefono1, EMP.T_MOVIL telefono2,      "
			+ "      (SELECT T_TELEFONO FROM DELPHOS_SEGEDU.TLTELUSU WHERE X_USUARIO = USU.X_USUARIO AND X_TIPTEL = 1) telefonoNotificaciones,      "
			+ "      EMP.T_CORREO_E CORREO,  "
			+ "     (SELECT T_CORREO_E FROM DELPHOS_SEGEDU.TLCORUSU WHERE X_USUARIO = USU.X_USUARIO) correoNotificaciones,  "
			+ "      USUTMODACC.MAIL_LDAP correoLdap      "
			+ " FROM DELPHOS_SEGEDU.TLEMPLEADOS EMP, DELPHOS_SEGEDU.TLUSUARIOS USU, DELPHOS_MODACC.USUARIOS USUMODACC, DELPHOS_MODACC.USUARIOS_T USUTMODACC, DELPHOS_MODACC.DOCUMENTACIONES DOCMODACC      "
			+ " WHERE EMP.X_EMPLEADO = USU.X_EMPLEADO          "
			+ "     AND EMP.C_NUMIDE = DOCMODACC.T_IDENTIFICACION      "
			+ " AND DOCMODACC.OID_PERSONA = USUMODACC.OID_PERSONA      "
			+ " AND USUMODACC.OID_PERSONA = USUTMODACC.OID_PERSONA      "
			+ " AND USUMODACC.OID = :oid "
			+ " AND ROWNUM = 1", nativeQuery = true)
	DatosPersonalesUsuarioProjection getDatosPersonalesUsuarioOid(@Param("oid") Long oid);

	@Query(value = "SELECT c_anno anno FROM DELPHOS.TLCURSOACA where TRUNC(SYSDATE) BETWEEN f_inicio AND f_final", nativeQuery = true)
	CursoAcademicoProjection getAnnoActual();

	@Query(value = "SELECT TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION from DUAL", nativeQuery = true)
	Long getAnnoAacademico();

	@Query(value = "SELECT DISTINCT USU.USUARIO idUsuario, USU.X_USUARIO xUsuario, EMP.X_EMPLEADO idEmpleado, EMP.C_NUMIDE numide, EMP.NOMBRE nombre, EMP.APELLIDO1 apellido1, EMP.APELLIDO2 apellido2, EMP.T_CORREO_E email "
			+ "     FROM TLEMPLEADOS EMP, TLUSUARIOS USU, DELPHOS_MODACC.usuarios modu, DELPHOS_MODACC.documentaciones doc "
			+ "     WHERE EMP.X_EMPLEADO = USU.X_EMPLEADO "
			+ "     AND EMP.C_NUMIDE = doc.t_identificacion "
			+ "     AND doc.oid_persona = modu.oid_persona "
			+ "     AND modu.oid = :oid", nativeQuery = true)
	DatosUsuarioProjection getDatosUsuarioDelphos(Long oid);
	
	@Query(value = "SELECT DISTINCT USU.USUARIO idUsuario, USU.X_USUARIO xUsuario, EMP.X_EMPLEADO idEmpleado, EMP.C_NUMIDE numide, EMP.NOMBRE nombre, EMP.APELLIDO1 apellido1, EMP.APELLIDO2 apellido2, EMP.T_CORREO_E email "
			+ "     FROM DELPHOS_SEGEDU.TLEMPLEADOS EMP, DELPHOS_SEGEDU.TLUSUARIOS USU, DELPHOS_MODACC.usuarios modu, DELPHOS_MODACC.documentaciones doc "
			+ "     WHERE EMP.X_EMPLEADO = USU.X_EMPLEADO "
			+ "     AND EMP.C_NUMIDE = doc.t_identificacion "
			+ "     AND doc.oid_persona = modu.oid_persona "
			+ "     AND modu.oid = :oid", nativeQuery = true)
	DatosUsuarioProjection getDatosUsuarioSeguimiento(Long oid);
	
	@Query(value = "SELECT DISTINCT PER.X_PERFIL idPerfil, PER.C_CODIGO codPerfil, PER.D_PERFIL nombrePerfil "
			+ "FROM TLPERFILES PER, TLUSUARIOS USU, TLPTOTRAEMP PTE, TLPUEORIPER PUE, TLEMPLEADOS EMP, DELPHOS_MODACC.usuarios modu, DELPHOS_MODACC.documentaciones doc "
			+ "WHERE PTE.X_EMPLEADO = USU.X_EMPLEADO  "
			+ "AND USU.X_EMPLEADO = EMP.X_EMPLEADO "
			+ "AND PTE.X_EMPLEADO = PUE.X_EMPLEADO  "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS "
			+ "AND PUE.X_PERFIL = PER.X_PERFIL  "
			+ "AND (TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND (TRUNC(PTE.F_CESE) >= TRUNC(SYSDATE) OR PTE.F_CESE IS NULL)) "
			+ "AND EMP.C_NUMIDE = doc.t_identificacion "
			+ "AND doc.oid_persona = modu.oid_persona  "
			+ "AND modu.oid = :oid ", nativeQuery = true)
	List<PerfilUsuarioProjection> getPerfilesDelphosUsuarioFCTDGC(@Param("oid") Long oid);

	@Query(value = "SELECT DISTINCT PER.X_PERFIL idPerfil, PER.C_CODIGO codPerfil, PER.D_PERFIL nombrePerfil "
			+ "FROM DELPHOS_SEGEDU.TLEMPLEADOS EMP, DELPHOS_SEGEDU.TLUSUARIOS USU, DELPHOS_MODACC.usuarios modu, DELPHOS_MODACC.documentaciones doc, DELPHOS_SEGEDU.TLPTOTRAEMP pto, DELPHOS_SEGEDU.TLPERFILES PER "
			+ "WHERE EMP.X_EMPLEADO = USU.X_EMPLEADO "
			+ "AND EMP.C_NUMIDE = doc.t_identificacion "
			+ "AND doc.oid_persona = modu.oid_persona "
			+ "AND pto.x_empleado = emp.x_empleado "
			+ "AND pto.x_centro = :xCentro "
			+ "AND per.x_perfil = :xPerfil  "
			+ "AND modu.oid = :oid ", nativeQuery = true)
	List<PerfilUsuarioProjection> getPerfilesSeguimientoUsuarioFCTDGC(@Param("xPerfil") String xPerfil, 
			                                                          @Param("xCentro") String xCentro ,
			                                                          @Param("oid") Long oid);
	

	@Query(value = "SELECT CONF.TX_COMPONENTES posicionComponentes "
			+ "FROM DELPHOS_MODACC.CONF_USUARIOS CONF, DELPHOS_MODACC.DOCUMENTACIONES DOC, DELPHOS_MODACC.USUARIOS USU "
			+ "WHERE DOC.OID_PERSONA = USU.OID_PERSONA "
			+ "AND USU.OID = CONF.OID_USUARIO "
			+ "AND DOC.T_IDENTIFICACION = :dni", nativeQuery = true)
	String getPosicionComponentesEscritorio(@Param("dni") String dni);


	@Modifying
	@Query(value = "UPDATE DELPHOS_MODACC.CONF_USUARIOS CONF " +
			"SET CONF.TX_COMPONENTES = :posicion " +
			"WHERE CONF.OID_USUARIO = ( " +
			"	SELECT USU.OID " +
			"	FROM DELPHOS_MODACC.USUARIOS USU, DELPHOS_MODACC.DOCUMENTACIONES DOC " +
			"	WHERE USU.OID_PERSONA = DOC.OID_PERSONA " +
			"	AND DOC.T_IDENTIFICACION = :dni)", nativeQuery = true)
	void setPosicionComponentesEscritorio(@Param("dni") String dni, @Param("posicion") String posicion);
	
	@Modifying
	@Query(value = "UPDATE DELPHOS_MODACC.CONF_USUARIOS CONF " +
			"SET CONF.DEFAULT_ROL = :rol, CONF.DEFAULT_CEN = :centro " +
			"WHERE CONF.OID_USUARIO = ( " +
			"	SELECT USU.OID " +
			"	FROM DELPHOS_MODACC.USUARIOS USU, DELPHOS_MODACC.DOCUMENTACIONES DOC " +
			"	WHERE USU.OID_PERSONA = DOC.OID_PERSONA " +
			"	AND DOC.T_IDENTIFICACION = :dni )", nativeQuery = true)
	void setConfgUsuarioDefault(@Param("dni") String dni, @Param("rol") String rol, @Param("centro") String centro);
	
	@Modifying
	@Query(value = "UPDATE DELPHOS_MODACC.CONF_USUARIOS CONF " +
			"SET CONF.LG_SKIPTOUR = 1 " +
			"WHERE CONF.OID_USUARIO = ( " +
			"	SELECT USU.OID " +
			"	FROM DELPHOS_MODACC.USUARIOS USU, DELPHOS_MODACC.DOCUMENTACIONES DOC " +
			"	WHERE USU.OID_PERSONA = DOC.OID_PERSONA " +
			"	AND DOC.T_IDENTIFICACION = :dni )", nativeQuery = true)
	void setTourUsuario(@Param("dni") String dni);
	
	@Modifying
	@Query(value = "UPDATE DELPHOS_MODACC.CONF_USUARIOS CONF " +
			"SET CONF.LG_SKIPTOUREVA = 1 " +
			"WHERE CONF.OID_USUARIO = ( " +
			"	SELECT USU.OID " +
			"	FROM DELPHOS_MODACC.USUARIOS USU, DELPHOS_MODACC.DOCUMENTACIONES DOC " +
			"	WHERE USU.OID_PERSONA = DOC.OID_PERSONA " +
			"	AND DOC.T_IDENTIFICACION = :dni )", nativeQuery = true)
	void setTourEvaluacionUsuario(@Param("dni") String dni);
	
	@Query(value = "SELECT DECODE(COUNT(DISTINCT pte.X_EMPLEADO), 1, 'true', 'false') FROM DELPHOS.TLPTOTRAEMP pte "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU gruact1 ON gruact1.X_EMPLEADO = pte.X_EMPLEADO AND gruact1.F_TOMAPOS = pte.F_TOMAPOS AND gruact1.X_CENTRO = pte.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLCURSOACA curso ON curso.C_ANNO = gruact1.C_ANNO "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag1 ON uag1.X_GRUACTPROALU = gruact1.X_GRUACTPROALU "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU gruact2 ON gruact2.X_EMPLEADO = pte.X_EMPLEADO_SUSTITUYE AND gruact2.F_TOMAPOS = pte.F_TOMAPOS_SUSTITUYE AND gruact2.X_CENTRO = pte.X_CENTRO AND gruact2.C_ANNO = curso.C_ANNO "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag2 ON uag2.X_GRUACTPROALU = gruact2.X_GRUACTPROALU AND uag2.X_MATERIAOMG = uag1.X_MATERIAOMG AND uag2.X_UNIDAD = uag1.X_UNIDAD "
			+ "WHERE (pte.F_CESE IS NULL OR pte.F_CESE > SYSDATE) AND pte.X_EMPLEADO_SUSTITUYE IS NOT NULL AND pte.X_EMPLEADO = :idEmpleado AND curso.C_ANNO = :anno", nativeQuery = true)
	Boolean isDocenteSustituto(@Param("idEmpleado") Long idEmpleado, @Param("anno") Integer anno);
	
	@Query(value = "SELECT DISTINCT emp.X_EMPLEADO idEmpleadoDelphos, empcom.X_EMPLEADO idEmpleadoComunica, pte.F_TOMAPOS fechaTomaPosesion, "
			+ "pte.F_CESE fechaCese, pte.X_CENTRO idCentro, curso.C_ANNO anno, usu.\"OID\" oidUsuario "
			+ "FROM DELPHOS.TLPTOTRAEMP pte "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU gruact1 ON gruact1.X_EMPLEADO = pte.X_EMPLEADO AND gruact1.F_TOMAPOS = pte.F_TOMAPOS AND gruact1.X_CENTRO = pte.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLCURSOACA curso ON curso.C_ANNO = gruact1.C_ANNO "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag1 ON uag1.X_GRUACTPROALU = gruact1.X_GRUACTPROALU "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU gruact2 ON gruact2.X_EMPLEADO = pte.X_EMPLEADO_SUSTITUYE AND gruact2.F_TOMAPOS = pte.F_TOMAPOS_SUSTITUYE AND gruact2.X_CENTRO = pte.X_CENTRO AND gruact2.C_ANNO = curso.C_ANNO "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag2 ON uag2.X_GRUACTPROALU = gruact2.X_GRUACTPROALU AND uag2.X_MATERIAOMG = uag1.X_MATERIAOMG AND uag2.X_UNIDAD = uag1.X_UNIDAD "
			+ "INNER JOIN DELPHOS.TLEMPLEADOS emp ON emp.X_EMPLEADO = pte.X_EMPLEADO "
			+ "INNER JOIN DELPHOS_SEGEDU.TLEMPLEADOS empcom ON empcom.C_NUMIDE = emp.C_NUMIDE "
			+ "INNER JOIN DELPHOS_MODACC.DOCUMENTACIONES doc ON doc.T_IDENTIFICACION = emp.C_NUMIDE "
			+ "INNER JOIN DELPHOS_MODACC.USUARIOS usu ON usu.OID_PERSONA = doc.OID_PERSONA "
			+ "WHERE (TLF_INTERSECPER(pte.F_TOMAPOS, pte.F_CESE, curso.F_INICIO, curso.F_FINAL) = 1) AND pte.X_EMPLEADO_SUSTITUYE = :idEmpleado AND curso.C_ANNO = :anno", nativeQuery = true)
	List<DocenteSustitutoProjection> getDocentesSustitutos(@Param("idEmpleado") Long idEmpleado, @Param("anno") Integer anno);
	
	@Query(value = "SELECT DISTINCT emp.X_EMPLEADO idEmpleadoDelphos, empcom.X_EMPLEADO idEmpleadoComunica, pte2.F_TOMAPOS fechaTomaPosesion, "
			+ "pte2.F_CESE fechaCese, pte2.X_CENTRO idCentro, curso.C_ANNO anno, usu.\"OID\" oidUsuario "
			+ "FROM DELPHOS.TLPTOTRAEMP pte1 "
			+ "INNER JOIN DELPHOS.TLPTOTRAEMP pte2 ON pte2.X_EMPLEADO = pte1.X_EMPLEADO_SUSTITUYE AND pte2.F_TOMAPOS = pte1.F_TOMAPOS_SUSTITUYE "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU gruact1 ON gruact1.X_EMPLEADO = pte1.X_EMPLEADO AND gruact1.F_TOMAPOS = pte1.F_TOMAPOS AND gruact1.X_CENTRO = pte1.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLCURSOACA curso ON curso.C_ANNO = gruact1.C_ANNO "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag1 ON uag1.X_GRUACTPROALU = gruact1.X_GRUACTPROALU "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU gruact2 ON gruact2.X_EMPLEADO = pte2.X_EMPLEADO AND gruact2.F_TOMAPOS = pte2.F_TOMAPOS AND gruact2.X_CENTRO = pte2.X_CENTRO AND gruact2.C_ANNO = curso.C_ANNO "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag2 ON uag2.X_GRUACTPROALU = gruact2.X_GRUACTPROALU AND uag2.X_MATERIAOMG = uag1.X_MATERIAOMG AND uag2.X_UNIDAD = uag1.X_UNIDAD "
			+ "INNER JOIN DELPHOS.TLEMPLEADOS emp ON emp.X_EMPLEADO = pte2.X_EMPLEADO "
			+ "INNER JOIN DELPHOS_SEGEDU.TLEMPLEADOS empcom ON empcom.C_NUMIDE = emp.C_NUMIDE "
			+ "INNER JOIN DELPHOS_MODACC.DOCUMENTACIONES doc ON doc.T_IDENTIFICACION = emp.C_NUMIDE "
			+ "INNER JOIN DELPHOS_MODACC.USUARIOS usu ON usu.OID_PERSONA = doc.OID_PERSONA "
			+ "WHERE (pte1.F_CESE IS NULL OR pte1.F_CESE > SYSDATE AND TLF_INTERSECPER(pte1.F_TOMAPOS, pte1.F_CESE, curso.F_INICIO, curso.F_FINAL) = 1) "
			+ "AND pte1.X_EMPLEADO_SUSTITUYE IS NOT NULL AND pte1.X_EMPLEADO = :idEmpleado AND curso.C_ANNO = :anno", nativeQuery = true)
	List<DocenteSustitutoProjection> getDocentesSustituidos(@Param("idEmpleado") Long idEmpleado, @Param("anno") Integer anno);

	@Query(value = "SELECT cu.OID_USUARIO oidUsuario, cu.DEFAULT_ROL rolDef, cu.DEFAULT_CEN cenDef " +
			"FROM DELPHOS_MODACC.CONF_USUARIOS cu " +
			"WHERE cu.OID_USUARIO = :oidUsuario", nativeQuery = true)
	PerfilDefectoProjection getPerfilDefecto(@Param("oidUsuario") Long oidUsuario);

	@Modifying
	@Query(value = "UPDATE DELPHOS_MODACC.CONF_USUARIOS CONF " +
			"SET CONF.DEFAULT_ROL = NULL, CONF.DEFAULT_CEN = NULL " +
			"WHERE CONF.OID_USUARIO = :oidUsuario", nativeQuery = true)
	void resetConfgUsuarioDefault(@Param("oidUsuario") Long oidUsuario);

	@Query(value = "SELECT DISTINCT  1 " +
			"FROM TLCARGOSEMP car " +
			"WHERE car.X_EMPLEADO = :idEmpleado " +
			"    AND car.X_EMPLEADO IS NOT NULL " +
			"    AND car.F_TOMAPOS = :fechaTomaPosesion " +
			"    AND (CAR.F_CESE  > TRUNC(SYSDATE) OR CAR.F_CESE  IS NULL) " +
			"    AND car.C_CARGO IN ('XCC', 'CIN', 'CFP')", nativeQuery = true)
	Long isCoordinadorCicloOrFP(@Param("idEmpleado") Long idEmpleado,
							@Param("fechaTomaPosesion") String fechaTomaPosesion);

	@Query(value = "SELECT DISTINCT 1 " +
			"FROM  TLMIEDEPART dep " +
			"WHERE  dep.X_EMPLEADO = :idEmpleado " +
			"AND dep.F_TOMAPOS = :fechaTomaPosesion " +
			"AND dep.L_JEFE = 'S' " +
			"AND (DEP.F_FIN > TRUNC(SYSDATE) OR DEP.F_FIN IS NULL)", nativeQuery = true)
	Long isJefeDepartamento(@Param("idEmpleado") Long idEmpleado,
							@Param("fechaTomaPosesion") String fechaTomaPosesion);

	@Query(value = "SELECT DISTINCT max(CASE " +
			"  WHEN per.C_CODIGO = 'ICO' THEN 2 " +
			"  WHEN per.C_CODIGO IN ('INZ','INC','I') THEN 1 " +
			"  ELSE 0 " +
			"END) AS perfil " +
			"FROM  TLPERFILES per " +
			"INNER JOIN TLUSUARIOS usu ON usu.X_EMPLEADO = :idEmpleado " +
			"INNER JOIN TLPERFILUSU perUsu ON PERUSU.X_USUARIO = usu.X_USUARIO " +
			"INNER JOIN TLPTOTRAEMP em ON em.X_EMPLEADO = USU.X_EMPLEADO AND (em.F_CESE IS NULL OR em.F_CESE >= TRUNC(SYSDATE) ) " +
			"WHERE per.X_PERFIL = PERUSU.X_PERFIL", nativeQuery = true)
	Long isInspector(@Param("idEmpleado") Long idEmpleado);

	@Query(value = "select CASE WHEN MAX(ROLES.ID_ROL_USUARIO) IS NULL THEN 'false' ELSE 'true' END tieneAcceso " +
			"FROM DELPHOS_MODACC.ROLES_USUARIOS ROLES " +
			"WHERE ROLES.ID_ROL IN (2,3) " +
			"AND ROLES.ID_USUARIO = :idUsuarioModAcc", nativeQuery = true)
	Boolean getAccesoShellProfesorYDirector(@Param("idUsuarioModAcc") Long idUsuarioModAcc);


	@Query(value = "SELECT ptotra.x_centro FROM TLPTOTRAEMP ptotra " +
			"WHERE PTOTRA.X_EMPLEADO = :idEmpleado " +
			"AND (ptotra.F_CESE IS NULL OR TRUNC(ptotra.F_CESE) >= SYSDATE) " +
			"AND (EXTRACT(YEAR FROM ptotra.F_TOMAPOS)) <= (SELECT TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION from DUAL) - 1 ", nativeQuery = true)
	List<Long> getCentroAnterior(@Param("idEmpleado") Long idEmpleado);

	@Query(value = "SELECT CUR.C_ANNO " +
			"FROM TLCURSOACA CUR " +
			"WHERE CUR.F_INICIO < SYSDATE " +
			"AND CUR.C_ANNO > 2022 " +
			"AND CUR.C_ANNO <> DELPHOS.TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION ", nativeQuery = true)
	List<Long> getAnyosAnteriores();

	@Query(value = "SELECT PTO.X_EMPLEADO " +
			"FROM TLCARGOSEMP CGE, TLPTOTRAEMP PTO  " +
			"WHERE PTO.X_CENTRO = :idCentro  " +
			"AND CGE.X_EMPLEADO = PTO.X_EMPLEADO  " +
			"AND CGE.F_TOMAPOS = PTO.F_TOMAPOS  " +
			"AND CGE.C_CARGO IN ('XDI')  " +
			"AND 1 = TLF_INTERSECPER(CGE.F_TOMPOS, CGE.F_CESE, SYSDATE, SYSDATE)", nativeQuery = true)
	Long getDirectorReport(@Param("idCentro") Long idCentro);
	
	
	@Query(value = "SELECT u.OID AS oid, p.T_NOMBRE AS nombre, "
			+ "p.T_APELLIDO1 AS apellido1, p.T_APELLIDO2 AS apellido2 FROM DELPHOS_MODACC.usuarios u "
			+ "INNER JOIN DELPHOS_MODACC.PERSONAS p ON p.OID  = u.OID_PERSONA "
			+ "INNER JOIN delphos_modacc.ROLES_USUARIOS ru "
			+ "INNER JOIN DELPHOS_MODACC.ROLES rol ON rol.ID_ROL  = ru.ID_ROL "
			+ "ON ru.ID_USUARIO = u.OID WHERE ru.CD_CENTRO = :codCentro AND rol.CD_CODIGO = :codRol", nativeQuery = true)
	List<UsuarioSimuSh> getUsuariosSh(@Param("codCentro") Long codCentro,@Param("codRol") String codRol);

}
