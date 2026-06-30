package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.QUsuario;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface UsuariosRepository extends AbstractRepository<Usuario, Long, QUsuario>{
		
	@Query(value = "SELECT u.* FROM DELPHOS_MODACC.usuarios u, DELPHOS_MODACC.documentaciones d, DELPHOS_MODACC.tipos_documentacion td WHERE u.OID_PERSONA = d.OID_PERSONA "
			+ "AND d.T_IDENTIFICACION = ? "
			+ "AND d.oid_tipo_documentacion = td.oid "
			+ "AND td.c_codigo = ?", nativeQuery = true)
	Usuario findUsuarioByIdentificacion(@Param("identificacion") String identificacion, @Param("tipide")String tipide);

	@Query(value = "DELETE FROM DELPHOS_MODACC.USUARIOS_REASIGNA_CLAVE u WHERE u.OID_USUARIO = ?", nativeQuery = true)
	void limpiarBorrar(@Param("idUsuario") Long idUsuario);
	
	@Query(value = "DELETE FROM DELPHOS_MODACC.USUARIOS u WHERE u.OID_PERSONA = ?", nativeQuery = true)
	Usuario borrarUsuarioByIdPersona(@Param("idPersona") Long idPersona);
			
	@Query(value = "SELECT * FROM DELPHOS_MODACC.USUARIOS u WHERE u.T_LOGIN=?", nativeQuery = true)
	List<Usuario> findByLogin(@Param("login") String login);
	
	@Query(value = "SELECT u.* FROM DELPHOS_MODACC.usuarios u, DELPHOS_MODACC.documentaciones d, DELPHOS_MODACC.tipos_documentacion td WHERE u.OID_PERSONA = d.OID_PERSONA "
			+ "AND d.T_IDENTIFICACION = ? "
			+ "AND d.oid_tipo_documentacion = td.oid ", nativeQuery = true)
	List<Usuario> findUsuariosByIdentificacion(@Param("identificacion") String identificacion);
	
	@Query(value = "SELECT u.* FROM DELPHOS_MODACC.usuarios u, DELPHOS_MODACC.documentaciones d, DELPHOS_MODACC.tipos_documentacion td WHERE u.OID_PERSONA = d.OID_PERSONA "
			+ "AND d.T_IDENTIFICACION = ? "
			+ "AND d.oid_tipo_documentacion = td.oid ", nativeQuery = true)
	List<Usuario> findUsuariosByIdentificacionP(@Param("identificacion") String identificacion);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM DELPHOS_MODACC.USUARIOS t WHERE t.OID =:oidUsuario", nativeQuery = true)
	void deleteUsuarioByOid(Long oidUsuario);
	
}
