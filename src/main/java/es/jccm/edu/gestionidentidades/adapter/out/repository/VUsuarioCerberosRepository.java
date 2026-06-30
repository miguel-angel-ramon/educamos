package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.QVUsuarioCerberos;
import es.jccm.edu.gestionidentidades.application.domain.VUsuarioCerberos;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface VUsuarioCerberosRepository extends AbstractRepository<VUsuarioCerberos, Long, QVUsuarioCerberos> {

	List<VUsuarioCerberos> findByUidLdap(String uidLdap);

	List<VUsuarioCerberos> findByLogin(String login);

	List<VUsuarioCerberos> findByIdentificacion(String nif);

	@Query(value = "SELECT u.oid, u.T_CORREO_E, p.oid OID_PERSONA, CAST(p.T_NOMBRE AS VARCHAR(50)) T_NOMBRE, CAST(p.T_APELLIDO1  AS VARCHAR(50)) T_APELLIDO1, "
			+ "CAST(p.T_APELLIDO2  AS VARCHAR(50)) T_APELLIDO2, p.C_SEXO , p.F_NACIMIENTO , d.OID_TIPO_DOCUMENTACION , "
			+ "CAST(d.T_IDENTIFICACION  AS VARCHAR(50)) T_IDENTIFICACION, "
			+ "u.T_LOGIN , u.T_CLAVE , u.L_ACTIVO , u.L_BLOQUEADO , ut.ES_DOCENTE , ut.ES_ALUMNO , ut.UID_AZURE , "
			+ "CAST(ut.CORREO_AULA AS VARCHAR(500)) CORREO_AULA, NVL(ut.UID_LDAP,u.UID_LDAP) UID_LDAP, NVL(ut.MAIL_LDAP,u.MAIL_LDAP) MAIL_LDAP, DECODE(d.OID_TIPO_DOCUMENTACION, 1, 1, 2, 3, 3, 2, 4) prioridad "
			+ "FROM DELPHOS_MODACC.USUARIOS u INNER JOIN DELPHOS_MODACC.PERSONAS p ON p.oid = u.OID_PERSONA "
			+ "INNER JOIN DELPHOS_MODACC.DOCUMENTACIONES d ON d.OID_PERSONA = u.OID_PERSONA "
			+ "LEFT OUTER JOIN DELPHOS_MODACC.USUARIOS_T ut ON u.OID = ut.OID "
			+ "WHERE ut.UID_LDAP= :uidLdap OR u.UID_LDAP = :uidLdap ORDER BY prioridad", nativeQuery = true)
	List<VUsuarioCerberos> findByUidLdapByQuery(String uidLdap);

	@Query(value = "SELECT 	u.oid,	u.T_CORREO_E,	p.oid OID_PERSONA,	CAST(p.T_NOMBRE AS VARCHAR2(50)) T_NOMBRE,	CAST(p.T_APELLIDO1  AS VARCHAR2(50)) T_APELLIDO1, "
			+ "CAST(p.T_APELLIDO2  AS VARCHAR2(50)) T_APELLIDO2,	p.C_SEXO ,	p.F_NACIMIENTO , d.OID_TIPO_DOCUMENTACION , "
			+ "CAST(d.T_IDENTIFICACION  AS VARCHAR2(50)) T_IDENTIFICACION, "
			+ "u.T_LOGIN ,	u.T_CLAVE ,	u.L_ACTIVO , u.L_BLOQUEADO ,	ut.ES_DOCENTE ,	ut.ES_ALUMNO ,	ut.UID_AZURE , "
			+ "CAST(ut.CORREO_AULA AS VARCHAR2(500)) CORREO_AULA,	NVL(ut.UID_LDAP,u.UID_LDAP) UID_LDAP, NVL(ut.MAIL_LDAP,u.MAIL_LDAP) MAIL_LDAP, DECODE(d.OID_TIPO_DOCUMENTACION, 1, 1, 2, 3, 3, 2, 4) prioridad "
			+ "FROM DELPHOS_MODACC.USUARIOS u " + "INNER JOIN DELPHOS_MODACC.PERSONAS p ON p.oid = u.OID_PERSONA "
			+ "INNER JOIN DELPHOS_MODACC.DOCUMENTACIONES d ON d.OID_PERSONA = u.OID_PERSONA "
			+ "LEFT OUTER JOIN DELPHOS_MODACC.USUARIOS_T ut ON u.OID = ut.OID "
			+ "WHERE u.T_LOGIN = :login ORDER BY prioridad", nativeQuery = true)
	List<VUsuarioCerberos> findByLoginByQuery(String login);

	@Query(value = "SELECT 	u.oid,	u.T_CORREO_E,	p.oid OID_PERSONA,	CAST(p.T_NOMBRE AS VARCHAR2(50)) T_NOMBRE,	CAST(p.T_APELLIDO1  AS VARCHAR2(50)) T_APELLIDO1, "
			+ "CAST(p.T_APELLIDO2  AS VARCHAR2(50)) T_APELLIDO2,	p.C_SEXO ,	p.F_NACIMIENTO ,	d.OID_TIPO_DOCUMENTACION , "
			+ "CAST(d.T_IDENTIFICACION  AS VARCHAR2(50)) T_IDENTIFICACION, "
			+ "u.T_LOGIN ,	u.T_CLAVE ,	u.L_ACTIVO , u.L_BLOQUEADO ,	ut.ES_DOCENTE ,	ut.ES_ALUMNO ,	ut.UID_AZURE , "
			+ "CAST(ut.CORREO_AULA AS VARCHAR2(500)) CORREO_AULA,	NVL(ut.UID_LDAP,u.UID_LDAP) UID_LDAP, NVL(ut.MAIL_LDAP,u.MAIL_LDAP) MAIL_LDAP "
			+ "FROM DELPHOS_MODACC.USUARIOS u " + "INNER JOIN DELPHOS_MODACC.PERSONAS p ON p.oid = u.OID_PERSONA "
			+ "INNER JOIN DELPHOS_MODACC.DOCUMENTACIONES d ON d.OID_PERSONA = u.OID_PERSONA "
			+ "LEFT OUTER JOIN DELPHOS_MODACC.USUARIOS_T ut ON u.OID = ut.OID "
			+ "WHERE d.T_IDENTIFICACION = :nif", nativeQuery = true)
	List<VUsuarioCerberos> findByIdentificacionByQuery(String nif);

	@Query(value = " SELECT u.oid, u.T_CORREO_E, p.oid OID_PERSONA, p.T_NOMBRE, p.T_APELLIDO1, p.T_APELLIDO2, p.C_SEXO,"
			+ " p.F_NACIMIENTO, d.OID_TIPO_DOCUMENTACION, d.T_IDENTIFICACION, u.T_LOGIN, u.T_CLAVE, u.L_ACTIVO,"
			+ " u.L_BLOQUEADO, ut.ES_DOCENTE, ut.ES_ALUMNO, ut.UID_AZURE, ut.CORREO_AULA, NVL(ut.UID_LDAP, u.UID_LDAP) UID_LDAP,"
			+ " NVL(ut.MAIL_LDAP, u.MAIL_LDAP) MAIL_LDAP, DECODE(d.OID_TIPO_DOCUMENTACION, 1, 1, 2, 3, 3, 2, 4) prioridad" 
			+ " FROM DELPHOS_MODACC.USUARIOS u"
			+ " INNER JOIN DELPHOS_MODACC.PERSONAS p ON p.oid = u.OID_PERSONA"
			+ " INNER JOIN DELPHOS_MODACC.DOCUMENTACIONES d ON d.OID_PERSONA = u.OID_PERSONA"
			+ " LEFT OUTER JOIN DELPHOS_MODACC.USUARIOS_T ut ON u.OID = ut.OID" + " WHERE u.T_LOGIN = :identificador"
			+ " OR (ut.UID_LDAP= :identificador OR u.UID_LDAP = :identificador) ORDER BY prioridad", nativeQuery = true)
	List<VUsuarioCerberos> findByIdentificadorByQuery(String identificador);

}
