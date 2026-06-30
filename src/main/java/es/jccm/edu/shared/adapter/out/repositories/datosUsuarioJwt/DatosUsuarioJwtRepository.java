package es.jccm.edu.shared.adapter.out.repositories.datosUsuarioJwt;

import es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection.AplicacionUsuarioJwtProjection;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection.DatosFreshServiceJwtProjection;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection.NombreUsuarioJwtProjection;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection.RolUsuarioJwtProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.QCentro;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection.DatosUsuarioJwtProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;

@Repository
public interface DatosUsuarioJwtRepository extends AbstractRepository<Centro, Long, QCentro> {

	@Query(value = "SELECT rownum ID, aux.* " +
			"FROM ( " +
			"SELECT rownum ID2,   (SELECT USUDEL.X_USUARIO    FROM TLUSUARIOS USUDEL ,   TLEMPLEADOS EMPDEL " +
			" WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE   " +
			" AND EMPDEL.X_EMPLEADO = USUDEL.X_EMPLEADO) xUsuarioDelphos,   (SELECT USUCOM.X_USUARIO " +
			"         FROM DELPHOS_SEGEDU.TLUSUARIOS USUCOM,   DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM " +
			" WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE AND EMPCOM.X_EMPLEADO = USUCOM.X_EMPLEADO) xUsuarioComunica, " +
			" (SELECT USUDEL.USUARIO    FROM TLUSUARIOS USUDEL ,   TLEMPLEADOS EMPDEL " +
			" WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE " +
			" AND EMPDEL.X_EMPLEADO = USUDEL.X_EMPLEADO) usuarioDelphos,   (SELECT USUCOM.USUARIO " +
			"         FROM DELPHOS_SEGEDU.TLUSUARIOS USUCOM,   DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM " +
			" WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE AND EMPCOM.X_EMPLEADO = USUCOM.X_EMPLEADO) usuarioComunica, " +
			" (SELECT EMPDEL.X_EMPLEADO    FROM TLEMPLEADOS EMPDEL   " +
			" WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE) idEmpleadoDelphos,   (SELECT EMPCOM.X_EMPLEADO " +
			" FROM DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM   " +
			" WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE) idEmpleadoComunica,   DOC.T_IDENTIFICACION NIF, " +
			" USUMODACC.T_CORREO_E email   FROM DELPHOS_MODACC.USUARIOS USUMODACC,  " +
			"     DELPHOS_MODACC.DOCUMENTACIONES DOC   WHERE       USUMODACC.OID_PERSONA = DOC.OID_PERSONA " +
			"     AND USUMODACC.OID = :oidUsuario "
            //Se incluye la siguiente clausula para los casos con más de una documentación con la finalidad de obtener la única que está activa 
            // para un empleado en comunica o para un empleado en delphos. Esto se incluye ya que solo se puede meter un documetno identificativo en el token
            // Se debe contemplar a futuro incluir una bandera que indique la documentación activa.
            + "AND (EXISTS (SELECT EMPCOM.X_EMPLEADO  " +
			"     FROM DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM  " +
			"     WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE)  " +
			"     OR EXISTS (  " +
			"     SELECT EMPDEL.X_EMPLEADO  " +
			"     FROM  TLEMPLEADOS EMPDEL  " +
			"     WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE)) "
          //Se incluye el union ya que se han detectado usuarios que solo etán en el módulo de acceso y se debe devolcer su infromación para el token y 
            // para que Cerberos pueda añadir los datos a los tokens
            + "UNION   " +
			"         SELECT rownum ID2, " +
			"         (SELECT  " +
			"             USUDEL.X_USUARIO    " +
			"         FROM  " +
			"             TLUSUARIOS USUDEL ,  " +
			"             TLEMPLEADOS EMPDEL    " +
			"         WHERE  " +
			"             DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE    " +
			"             AND EMPDEL.X_EMPLEADO = USUDEL.X_EMPLEADO) xUsuarioDelphos,  " +
			"         (SELECT  " +
			"             USUCOM.X_USUARIO             " +
			"         FROM  " +
			"             DELPHOS_SEGEDU.TLUSUARIOS USUCOM,  " +
			"             DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM    " +
			"         WHERE  " +
			"             DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE   " +
			"             AND EMPCOM.X_EMPLEADO = USUCOM.X_EMPLEADO) xUsuarioComunica,  " +
			"         (SELECT  " +
			"             USUDEL.USUARIO    " +
			"         FROM  " +
			"             TLUSUARIOS USUDEL ,  " +
			"             TLEMPLEADOS EMPDEL    " +
			"         WHERE  " +
			"             DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE    " +
			"             AND EMPDEL.X_EMPLEADO = USUDEL.X_EMPLEADO) usuarioDelphos,  " +
			"         (SELECT  " +
			"             USUCOM.USUARIO             " +
			"         FROM  " +
			"             DELPHOS_SEGEDU.TLUSUARIOS USUCOM,  " +
			"             DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM    " +
			"         WHERE  " +
			"             DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE   " +
			"             AND EMPCOM.X_EMPLEADO = USUCOM.X_EMPLEADO) usuarioComunica,  " +
			"         (SELECT  " +
			"             EMPDEL.X_EMPLEADO    " +
			"         FROM  " +
			"             TLEMPLEADOS EMPDEL    " +
			"         WHERE  " +
			"             DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE) idEmpleadoDelphos,  " +
			"         (SELECT  " +
			"             EMPCOM.X_EMPLEADO    " +
			"         FROM  " +
			"             DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM    " +
			"         WHERE  " +
			"             DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE) idEmpleadoComunica,  " +
			"         DOC.T_IDENTIFICACION NIF,  " +
			"         USUMODACC.T_CORREO_E email   " +
			"     FROM  " +
			"         DELPHOS_MODACC.USUARIOS USUMODACC,  " +
			"         DELPHOS_MODACC.DOCUMENTACIONES DOC   " +
			"     WHERE  " +
			"         USUMODACC.OID_PERSONA = DOC.OID_PERSONA       " +
			"         AND USUMODACC.OID = :oidUsuario       " +
			"         AND (  " +
			"            NOT EXISTS (  " +
			"                 SELECT  " +
			"                     EMPCOM.X_EMPLEADO       " +
			"                 FROM  " +
			"                     DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM       " +
			"                 WHERE  " +
			"                     DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE  " +
			"             )       " +
			"             AND NOT EXISTS (  " +
			"                 SELECT  " +
			"                     EMPDEL.X_EMPLEADO       " +
			"                 FROM  " +
			"                     TLEMPLEADOS EMPDEL       " +
			"                 WHERE  " +
			"                     DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE  " +
			"             )  " +
			"         )) aux " +
			"WHERE ROWNUM <= 1 ", nativeQuery = true)
    DatosUsuarioJwtProjection getDatosUsuarioByJwt(@Param("oidUsuario") String oidUsuario);
	
	@Query(value = " "
			+ "SELECT  "
			+ "    rownum ID,  "
			+ "    (SELECT USUDEL.X_USUARIO "
			+ "       FROM TLUSUARIOS USUDEL,  "
			+ "            TLEMPLEADOS EMPDEL "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE "
			+ "        AND EMPDEL.X_EMPLEADO = USUDEL.X_EMPLEADO "
			+ "    ) xUsuarioDelphos, "
			+ "    (SELECT USUCOM.X_USUARIO "
			+ "       FROM DELPHOS_SEGEDU.TLUSUARIOS USUCOM,  "
			+ "            DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE "
			+ "        AND EMPCOM.X_EMPLEADO = USUCOM.X_EMPLEADO "
			+ "    ) xUsuarioComunica, "
			+ "    (SELECT USUDEL.USUARIO "
			+ "       FROM TLUSUARIOS USUDEL,  "
			+ "            TLEMPLEADOS EMPDEL "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE "
			+ "        AND EMPDEL.X_EMPLEADO = USUDEL.X_EMPLEADO "
			+ "    ) usuarioDelphos, "
			+ "    (SELECT USUCOM.USUARIO "
			+ "       FROM DELPHOS_SEGEDU.TLUSUARIOS USUCOM,  "
			+ "            DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE "
			+ "        AND EMPCOM.X_EMPLEADO = USUCOM.X_EMPLEADO "
			+ "    ) usuarioComunica, "
			+ "    (SELECT EMPDEL.X_EMPLEADO "
			+ "       FROM TLEMPLEADOS EMPDEL "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE "
			+ "    ) idEmpleadoDelphos, "
			+ "    (SELECT EMPCOM.X_EMPLEADO "
			+ "       FROM DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE "
			+ "    ) idEmpleadoComunica, "
			+ "    DOC.T_IDENTIFICACION NIF, "
			+ "    USUMODACC.T_CORREO_E email, "
			+ "    PER.F_NACIMIENTO fNacimiento, "
			+ "    DECODE(DOC.OID_TIPO_DOCUMENTACION, 1, 1, 2, 3, 3, 2, 4) prioridad "
			+ "FROM  "
			+ "    DELPHOS_MODACC.USUARIOS USUMODACC, "
			+ "    DELPHOS_MODACC.DOCUMENTACIONES DOC, "
			+ "    DELPHOS_MODACC.PERSONAS PER "
			+ "WHERE  "
			+ "    USUMODACC.OID_PERSONA = DOC.OID_PERSONA "
			+ "    AND PER.OID = USUMODACC.OID_PERSONA "
			+ "    AND USUMODACC.OID = :oidUsuario "
			+ "    AND ( "
			+ "        EXISTS ( "
			+ "            SELECT EMPCOM.X_EMPLEADO "
			+ "              FROM DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM "
			+ "             WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE "
			+ "        ) "
			+ "        OR EXISTS ( "
			+ "            SELECT EMPDEL.X_EMPLEADO "
			+ "              FROM TLEMPLEADOS EMPDEL "
			+ "             WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE "
			+ "        ) "
			+ "    ) "
			+ "UNION "
			+ "SELECT  "
			+ "    rownum ID, "
			+ "    (SELECT USUDEL.X_USUARIO "
			+ "       FROM TLUSUARIOS USUDEL,  "
			+ "            TLEMPLEADOS EMPDEL "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE "
			+ "        AND EMPDEL.X_EMPLEADO = USUDEL.X_EMPLEADO "
			+ "    ) xUsuarioDelphos, "
			+ "    (SELECT USUCOM.X_USUARIO "
			+ "       FROM DELPHOS_SEGEDU.TLUSUARIOS USUCOM,  "
			+ "            DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE "
			+ "        AND EMPCOM.X_EMPLEADO = USUCOM.X_EMPLEADO "
			+ "    ) xUsuarioComunica, "
			+ "    (SELECT USUDEL.USUARIO "
			+ "       FROM TLUSUARIOS USUDEL,  "
			+ "            TLEMPLEADOS EMPDEL "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE "
			+ "        AND EMPDEL.X_EMPLEADO = USUDEL.X_EMPLEADO "
			+ "    ) usuarioDelphos, "
			+ "    (SELECT USUCOM.USUARIO "
			+ "       FROM DELPHOS_SEGEDU.TLUSUARIOS USUCOM,  "
			+ "            DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE "
			+ "        AND EMPCOM.X_EMPLEADO = USUCOM.X_EMPLEADO "
			+ "    ) usuarioComunica, "
			+ "    (SELECT EMPDEL.X_EMPLEADO "
			+ "       FROM TLEMPLEADOS EMPDEL "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE "
			+ "    ) idEmpleadoDelphos, "
			+ "    (SELECT EMPCOM.X_EMPLEADO "
			+ "       FROM DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM "
			+ "      WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE "
			+ "    ) idEmpleadoComunica, "
			+ "    DOC.T_IDENTIFICACION NIF, "
			+ "    USUMODACC.T_CORREO_E email, "
			+ "     PER.F_NACIMIENTO fNacimiento, "
			+ "    DECODE(DOC.OID_TIPO_DOCUMENTACION, 1, 1, 2, 3, 3, 2, 4) prioridad "
			+ "FROM  "
			+ "    DELPHOS_MODACC.USUARIOS USUMODACC, "
			+ "    DELPHOS_MODACC.DOCUMENTACIONES DOC, "
			+ "    DELPHOS_MODACC.PERSONAS PER "
			+ "WHERE  "
			+ "    USUMODACC.OID_PERSONA = DOC.OID_PERSONA "
			+ "    AND PER.OID = USUMODACC.OID_PERSONA "
			+ "    AND USUMODACC.OID = :oidUsuario "
			+ "    AND ( "
			+ "        NOT EXISTS ( "
			+ "            SELECT EMPCOM.X_EMPLEADO "
			+ "              FROM DELPHOS_SEGEDU.TLEMPLEADOS EMPCOM "
			+ "             WHERE DOC.T_IDENTIFICACION = EMPCOM.C_NUMIDE "
			+ "        ) "
			+ "         "
			+ "        AND NOT EXISTS ( "
			+ "            SELECT EMPDEL.X_EMPLEADO "
			+ "              FROM TLEMPLEADOS EMPDEL "
			+ "             WHERE DOC.T_IDENTIFICACION = EMPDEL.C_NUMIDE "
			+ "        ) "
			+ "    ) "
			+ "ORDER BY  "
			+ "    prioridad"
            , nativeQuery = true)
    List<DatosUsuarioJwtProjection> getDatosUsuarioByJwtList(@Param("oidUsuario") String oidUsuario);

	@Query(value = "SELECT AP.OID oidAplicacion, AP.C_CODIGO codigo, AP.T_NOMBRE nombre, AP.INFORMACION informacion, AP.D_NOMBRE nombreAlternativo "
			+ "FROM DELPHOS_MODACC.APLICACIONES AP " + "INNER JOIN DELPHOS_MODACC.APL_ACCESIBLES_USUARIO AAU "
			+ "ON AAU.OID_APLICACION = AP.OID " + "WHERE AAU.OID_USUARIO = :oidUsuario " + "AND AP.l_activa = 'S' "
			+ "AND AAU.l_bloqueado = 'N'", nativeQuery = true)
	List<AplicacionUsuarioJwtProjection> getAplicacionesUsuario(@Param("oidUsuario") String oidUsuario);

	@Query(value = "SELECT V_ROLES_ACC_USR.X_PERFIL idPerfil, V_ROLES_ACC_USR.D_PERFIL descripcionPerfil, "
			+ "V_ROLES_ACC_USR.T_USUARIOBD usuarioBD, V_ROLES_ACC_USR.C_CODIGO codigoPerfil, V_ROLES_ACC_USR.C_AMBVISCEN ambitoPerfil "
			+ "FROM (SELECT USU.USUARIO, EMP.C_NUMIDE, P.X_PERFIL, P.D_PERFIL, P.T_USUARIOBD, P.C_CODIGO, P.C_AMBVISCEN "
			+ "FROM DELPHOS.TLUSUARIOS USU " + "INNER JOIN DELPHOS.TLEMPLEADOS EMP ON EMP.X_EMPLEADO = USU.X_EMPLEADO "
			+ "INNER JOIN DELPHOS.TLPERFILUSU PUS ON pus.X_USUARIO = usu.x_usuario "
			+ "INNER JOIN DELPHOS.tlperfiles P ON pus.X_PERFIL = p.X_PERFIL " + "WHERE USU.L_ACTIVO = 'S' "
			+ "AND USU.L_BLOQUEADO = 'N' " + "AND P.L_ACTIVO = 'S' " + "AND EXISTS (SELECT 1 "
			+ "FROM DELPHOS.TLPTOTRAEMP PTE " + "INNER JOIN DELPHOS.TLPUEORIPER PUE ON PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS " + "WHERE pte.x_empleado = usu.x_empleado "
			+ "AND TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND NVL(TRUNC(PTE.F_CESE), TRUNC(SYSDATE)) >= TRUNC(SYSDATE) " + "AND PUE.X_PERFIL = P.X_PERFIL ) "
			+ "UNION "
			+ "SELECT USU.USUARIO, EMP.C_NUMIDE, P.X_PERFIL, P.T_USUARIOBD, P.D_PERFIL, P.C_CODIGO, P.C_AMBVISCEN "
			+ "FROM DELPHOS_SEGEDU.TLUSUARIOS USU "
			+ "INNER JOIN DELPHOS_SEGEDU.TLEMPLEADOS EMP ON EMP.X_EMPLEADO = USU.X_EMPLEADO "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPERFILUSU PUS ON pus.X_USUARIO = usu.x_usuario "
			+ "INNER JOIN DELPHOS_SEGEDU.tlperfiles P ON pus.X_PERFIL = p.X_PERFIL " + "WHERE USU.L_ACTIVO = 'S' "
			+ "AND USU.L_BLOQUEADO = 'N' " + "AND P.L_ACTIVO = 'S' " + "AND EXISTS (SELECT 1 "
			+ "FROM DELPHOS_SEGEDU.TLPTOTRAEMP PTE "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPUEORIPER PUE ON PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS " + "WHERE pte.x_empleado = usu.x_empleado "
			+ "AND TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND NVL(TRUNC(PTE.F_CESE), TRUNC(SYSDATE)) >= TRUNC(SYSDATE) " + "AND PUE.X_PERFIL = P.X_PERFIL ) "
			+ "UNION "
			+ "SELECT USU.USUARIO, EMP.C_NUMIDE, P.X_PERFIL, P.T_USUARIOBD, P.D_PERFIL, P.C_CODIGO, P.C_AMBVISCEN "
			+ "FROM DELPHOS_SECVIR.TLUSUARIOS USU "
			+ "INNER JOIN DELPHOS_SECVIR.TLEMPLEADOS EMP ON EMP.X_EMPLEADO = USU.X_EMPLEADO "
			+ "INNER JOIN DELPHOS_SECVIR.TLPERFILUSU PUS ON pus.X_USUARIO = usu.x_usuario "
			+ "INNER JOIN DELPHOS_SECVIR.tlperfiles P ON pus.X_PERFIL = p.X_PERFIL " + "WHERE USU.L_ACTIVO = 'S' "
			+ "AND USU.L_BLOQUEADO = 'N' " + "AND P.L_ACTIVO = 'S' " + "AND EXISTS (SELECT 1 "
			+ "FROM DELPHOS_SECVIR.TLPTOTRAEMP PTE "
			+ "INNER JOIN DELPHOS_SECVIR.TLPUEORIPER PUE ON PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS " + "WHERE pte.x_empleado = usu.x_empleado "
			+ "AND TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND NVL(TRUNC(PTE.F_CESE), TRUNC(SYSDATE)) >= TRUNC(SYSDATE) "
			+ "AND PUE.X_PERFIL = P.X_PERFIL )) V_ROLES_ACC_USR "
			+ "WHERE USUARIO = :oidUsuario OR C_NUMIDE = :nif", nativeQuery = true)
	List<RolUsuarioJwtProjection> getRolesUsuario(@Param("oidUsuario") String oidUsuario, @Param("nif") String nif);

	@Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 'true' ELSE 'false' END "
			+ "FROM (SELECT V_ROLES_ACC_USR.X_PERFIL idPerfil, V_ROLES_ACC_USR.D_PERFIL descripcionPerfil, "
			+ "V_ROLES_ACC_USR.T_USUARIOBD usuarioBD, V_ROLES_ACC_USR.C_CODIGO codigoPerfil, V_ROLES_ACC_USR.C_AMBVISCEN ambitoPerfil "
			+ "FROM (SELECT USU.USUARIO, EMP.C_NUMIDE, P.X_PERFIL, P.D_PERFIL, P.T_USUARIOBD, P.C_CODIGO, P.C_AMBVISCEN "
			+ "FROM DELPHOS.TLUSUARIOS USU " + "INNER JOIN DELPHOS.TLEMPLEADOS EMP ON EMP.X_EMPLEADO = USU.X_EMPLEADO "
			+ "INNER JOIN DELPHOS.TLPERFILUSU PUS ON pus.X_USUARIO = usu.x_usuario "
			+ "INNER JOIN DELPHOS.tlperfiles P ON pus.X_PERFIL = p.X_PERFIL " + "WHERE USU.L_ACTIVO = 'S' "
			+ "AND USU.L_BLOQUEADO = 'N' " + "AND P.L_ACTIVO = 'S' " + "AND C_CODIGO = 'SU' " + "AND EXISTS (SELECT 1 "
			+ "FROM DELPHOS.TLPTOTRAEMP PTE " + "INNER JOIN DELPHOS.TLPUEORIPER PUE ON PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS " + "WHERE pte.x_empleado = usu.x_empleado "
			+ "AND TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND NVL(TRUNC(PTE.F_CESE), TRUNC(SYSDATE)) >= TRUNC(SYSDATE) " + "AND PUE.X_PERFIL = P.X_PERFIL ) "
			+ "UNION "
			+ "SELECT USU.USUARIO, EMP.C_NUMIDE, P.X_PERFIL, P.T_USUARIOBD, P.D_PERFIL, P.C_CODIGO, P.C_AMBVISCEN "
			+ "FROM DELPHOS_SEGEDU.TLUSUARIOS USU "
			+ "INNER JOIN DELPHOS_SEGEDU.TLEMPLEADOS EMP ON EMP.X_EMPLEADO = USU.X_EMPLEADO "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPERFILUSU PUS ON pus.X_USUARIO = usu.x_usuario "
			+ "INNER JOIN DELPHOS_SEGEDU.tlperfiles P ON pus.X_PERFIL = p.X_PERFIL " + "WHERE USU.L_ACTIVO = 'S' "
			+ "AND USU.L_BLOQUEADO = 'N' " + "AND P.L_ACTIVO = 'S' " + "AND C_CODIGO = 'SU' " + "AND EXISTS (SELECT 1 "
			+ "FROM DELPHOS_SEGEDU.TLPTOTRAEMP PTE "
			+ "INNER JOIN DELPHOS_SEGEDU.TLPUEORIPER PUE ON PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS " + "WHERE pte.x_empleado = usu.x_empleado "
			+ "AND TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND NVL(TRUNC(PTE.F_CESE), TRUNC(SYSDATE)) >= TRUNC(SYSDATE) " + "AND PUE.X_PERFIL = P.X_PERFIL ) "
			+ "UNION "
			+ "SELECT USU.USUARIO, EMP.C_NUMIDE, P.X_PERFIL, P.T_USUARIOBD, P.D_PERFIL, P.C_CODIGO, P.C_AMBVISCEN "
			+ "FROM DELPHOS_SECVIR.TLUSUARIOS USU "
			+ "INNER JOIN DELPHOS_SECVIR.TLEMPLEADOS EMP ON EMP.X_EMPLEADO = USU.X_EMPLEADO "
			+ "INNER JOIN DELPHOS_SECVIR.TLPERFILUSU PUS ON pus.X_USUARIO = usu.x_usuario "
			+ "INNER JOIN DELPHOS_SECVIR.tlperfiles P ON pus.X_PERFIL = p.X_PERFIL " + "WHERE USU.L_ACTIVO = 'S' "
			+ "AND USU.L_BLOQUEADO = 'N' " + "AND P.L_ACTIVO = 'S' " + "AND C_CODIGO = 'SU' " + "AND EXISTS (SELECT 1 "
			+ "FROM DELPHOS_SECVIR.TLPTOTRAEMP PTE "
			+ "INNER JOIN DELPHOS_SECVIR.TLPUEORIPER PUE ON PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS " + "WHERE pte.x_empleado = usu.x_empleado "
			+ "AND TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND NVL(TRUNC(PTE.F_CESE), TRUNC(SYSDATE)) >= TRUNC(SYSDATE) "
			+ "AND PUE.X_PERFIL = P.X_PERFIL )) V_ROLES_ACC_USR "
			+ "WHERE USUARIO = :oidUsuario OR C_NUMIDE = :nif)", nativeQuery = true)
	boolean canUsuarioSimulate(@Param("oidUsuario") String oidUsuario, @Param("nif") String nif);

	@Query(value = "SELECT " + "P.T_NOMBRE AS NOMBRE, " + "P.T_APELLIDO1 || ' ' || P.T_APELLIDO2 AS APELLIDO, "
			+ "U.T_CORREO_E AS CORREO " + "FROM " + "DELPHOS_MODACC.USUARIOS U "
			+ "INNER JOIN DELPHOS_MODACC.PERSONAS P ON " + "U.OID_PERSONA = P.OID " + "WHERE "
			+ "U.OID = :oidUsuario ", nativeQuery = true)
	NombreUsuarioJwtProjection getNombreUsuario(@Param("oidUsuario") String oidUsuario);

	@Query(value = "SELECT DISTINCT NVL(doc.T_IDENTIFICACION,'unknown') AS NIF, pfu.x_perfil AS PERFIL, per.c_codigo AS CODIGO, NVL(emp.TELEFONO,'unknown') AS TELEFONO, NVL(modu.t_correo_e,'unknown') AS CORREO "
			+ " FROM   DELPHOS.TLEMPLEADOS emp, DELPHOS.TLUSUARIOS usu, DELPHOS.TLPERFILUSU pfu, DELPHOS.TLPERFILES per, "
			+ "        DELPHOS_MODACC.USUARIOS modu, DELPHOS_MODACC.DOCUMENTACIONES doc "
			+ " WHERE  emp.x_empleado = usu.x_empleado " + " AND    usu.x_usuario = pfu.x_usuario "
			+ " AND    per.x_perfil = pfu.x_perfil " + " AND    emp.c_numide = doc.t_identificacion "
			+ " AND    doc.oid_persona = modu.oid_persona " + " AND    modu.oid = :oidUsuario " + " UNION "
			+ " /* SEGEDU */"
			+ " SELECT DISTINCT NVL(doc.T_IDENTIFICACION, 'unknown') AS NIF, pfu.x_perfil AS PERFIL, per.c_codigo AS CODIGO, NVL(emp.TELEFONO,'unknown') AS TELEFONO, NVL(modu.t_correo_e,'unknown') AS CORREO "
			+ " FROM   DELPHOS_SEGEDU.TLEMPLEADOS emp, DELPHOS_SEGEDU.TLUSUARIOS usu, DELPHOS_SEGEDU.TLPERFILUSU pfu, DELPHOS_SEGEDU.TLPERFILES per,  "
			+ "        DELPHOS_MODACC.USUARIOS modu, DELPHOS_MODACC.DOCUMENTACIONES doc "
			+ " WHERE  emp.x_empleado = usu.x_empleado " + " AND    usu.x_usuario = pfu.x_usuario "
			+ " AND    per.x_perfil = pfu.x_perfil " + " AND    emp.c_numide = doc.t_identificacion "
			+ " AND    doc.oid_persona = modu.oid_persona " + " AND    modu.oid = :oidUsuario "
			+ " and    per.x_perfil <> 5000  " + " UNION "
			+ " SELECT DISTINCT NVL(doc.T_IDENTIFICACION,'unknown') AS NIF, pfu.x_perfil AS PERFIL, per.c_codigo AS CODIGO, NVL(alu.T_TELEFONO,'unknown') AS TELEFONO, NVL(modu.t_correo_e,'unknown') AS CORREO "
			+ " FROM   DELPHOS_SEGEDU.TLEMPLEADOS emp, DELPHOS_SEGEDU.TLUSUARIOS usu, DELPHOS_SEGEDU.TLPERFILUSU pfu, DELPHOS_SEGEDU.TLPERFILES per, "
			+ "        DELPHOS_MODACC.USUARIOS modu, DELPHOS_MODACC.DOCUMENTACIONES doc, DELPHOS_SEGEDU.TLALUMNOS alu "
			+ " WHERE  emp.x_empleado = usu.x_empleado " + " AND    usu.x_usuario = pfu.x_usuario "
			+ " AND    per.x_perfil = pfu.x_perfil " + " AND    emp.c_numide = doc.t_identificacion "
			+ " AND    doc.oid_persona = modu.oid_persona " + " AND    emp.c_numide = alu.c_numide "
			+ " AND    modu.oid = :oidUsuario " + " and    per.x_perfil = 5000 " + " UNION " + " /* SECVIR */ "
			+ " SELECT DISTINCT NVL(doc.T_IDENTIFICACION,'unknown') AS  NIF, pfu.x_perfil AS PERFIL, per.c_codigo AS CODIGO, NVL(emp.TELEFONO,'unknown') AS TELEFONO, NVL(modu.t_correo_e,'unknown') AS CORREO "
			+ " FROM   DELPHOS_SECVIR.TLEMPLEADOS emp, DELPHOS_SECVIR.TLUSUARIOS usu, DELPHOS_SECVIR.TLPERFILUSU pfu, DELPHOS_SECVIR.TLPERFILES per, "
			+ "        DELPHOS_MODACC.USUARIOS modu, DELPHOS_MODACC.DOCUMENTACIONES doc "
			+ " WHERE  emp.x_empleado = usu.x_empleado " + " AND    usu.x_usuario = pfu.x_usuario  "
			+ " AND    per.x_perfil = pfu.x_perfil " + " AND    emp.c_numide = doc.t_identificacion "
			+ " AND    doc.oid_persona = modu.oid_persona " + " AND    modu.oid = :oidUsuario", nativeQuery = true)
	List<DatosFreshServiceJwtProjection> getDatosFreshService(@Param("oidUsuario") Long oidUsuario);
	
	@Query(value = "SELECT NVL(MAX(RP.NU_NIVELSEGURIDAD), 2) "
			+ "FROM DELPHOS_MODACC.USUARIOS U "
			+ "INNER JOIN DELPHOS_MODACC.ROLES_USUARIOS RU  "
			+ "    ON RU.ID_USUARIO = U.OID "
			+ "INNER JOIN DELPHOS_MODACC.ROLES_PERMISOS RP  "
			+ "    ON RU.ID_ROL = RP.ID_ROL "
			+ "WHERE U.OID = :oidUsuario ", nativeQuery = true)
	Long getSecurityLevel(@Param("oidUsuario") String oidUsuario);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END "
			+ "FROM DELPHOS_MODACC.SEGURIDAD_EXCEPCIONES SE "
			+ "WHERE SE.OID_USUARIO = :oidUsuario "
			+ "AND (SE.F_BAJA IS NULL OR SE.F_BAJA > SYSDATE)", nativeQuery = true)
    int tieneExcepcionActiva(@Param("oidUsuario") String oidUsuario);

    default boolean existeExcepcionActiva(String oidUsuario) {
        return tieneExcepcionActiva(oidUsuario) > 0;
    }
    
    @Query(value = "SELECT count(*) FROM DELPHOS.TLEMPLEADOS EMP "
    		+ "INNER JOIN DELPHOS.TLPTOTRAEMP PTO "
    		+ "ON PTO.X_EMPLEADO = EMP.X_EMPLEADO "
    		+ "AND DELPHOS.TLF_INTERSECPER(SYSDATE, SYSDATE, PTO.F_TOMAPOSREA, PTO.F_CESE) = 1 "
    		+ "AND PTO.X_CENTRO  IN (3047,\r\n"
    		+ "3048, "
    		+ "3049, "
    		+ "3050, "
    		+ "3051, "
    		+ "3052) AND EMP.X_EMPLEADO = :idEmpleado", nativeQuery = true)
    int contarPuestosActivos(@Param("idEmpleado") String idEmpleado);

    default boolean tienePuestoActivoEnCentros(String idEmpleado) {
        return contarPuestosActivos(idEmpleado) > 0;
    }

}
