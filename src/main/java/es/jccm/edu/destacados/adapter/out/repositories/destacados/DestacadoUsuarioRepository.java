package es.jccm.edu.destacados.adapter.out.repositories.destacados;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.destacados.application.domain.destacados.Destacado;
import es.jccm.edu.destacados.application.domain.destacados.DestacadoUsuario;
import es.jccm.edu.destacados.application.domain.destacados.QDestacadoUsuario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface DestacadoUsuarioRepository extends AbstractRepository<DestacadoUsuario, Long, QDestacadoUsuario> {

    @Query(value = "SELECT DESUSU.* FROM DELPHOS_MODACC.ESC_DESTACADOS_USUARIOS DESUSU, " +
            "DELPHOS_MODACC.ESC_DESTACADOS DES, " +
            "DELPHOS_MODACC.DOCUMENTACIONES DOC, " +
            "DELPHOS_MODACC.USUARIOS USU " +
            "WHERE DESUSU.ID_DESTACADO = DES.ID_DESTACADO " +
            "AND DESUSU.OID_USUARIO = USU.OID " +
            "AND DOC.OID_PERSONA = USU.OID_PERSONA " +
            "AND DOC.T_IDENTIFICACION = :nif " +
            "ORDER BY DES.ID_DESTACADO", nativeQuery = true)
    List<DestacadoUsuario> findDestacadosUsuariosByNif(String nif);

    @Query(value = "SELECT DESUSU.* FROM DELPHOS_MODACC.ESC_DESTACADOS_USUARIOS DESUSU, " +
            "DELPHOS_MODACC.ESC_DESTACADOS DES, " +
            "DELPHOS_MODACC.DOCUMENTACIONES DOC, " +
            "DELPHOS_MODACC.USUARIOS USU " +
            "WHERE DESUSU.ID_DESTACADO = DES.ID_DESTACADO  " +
            "AND DESUSU.OID_USUARIO = USU.OID " +
            "AND DOC.OID_PERSONA = USU.OID_PERSONA " +
            "AND DESUSU.LG_ACTIVO = 1 " +
            "AND DES.LG_ACTIVO = 1 " +
            "AND DOC.T_IDENTIFICACION = :nif " +
            "ORDER BY DES.ID_DESTACADO", nativeQuery = true)
    List<DestacadoUsuario> findDestacadosUsuariosActivosByNif(String nif);
    
  


}
