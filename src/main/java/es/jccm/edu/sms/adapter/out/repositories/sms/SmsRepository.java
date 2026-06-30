package es.jccm.edu.sms.adapter.out.repositories.sms;

import es.jccm.edu.pdc.application.domain.cuestionarios.entities.Cuestionario;
import es.jccm.edu.pdc.application.domain.cuestionarios.entities.QCuestionario;
import es.jccm.edu.pdc.application.domain.cuestionarios.projection.*;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import es.jccm.edu.sms.application.domain.sms.entities.QSms;
import es.jccm.edu.sms.application.domain.sms.entities.Sms;
import es.jccm.edu.sms.application.domain.sms.projection.FlujoAdjuntosProjection;
import es.jccm.edu.sms.application.domain.sms.projection.SmsProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface SmsRepository extends AbstractRepository<Sms, Long, QSms> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO GEN_VERIFICACION_SMS (ID_VERIFICACION_SMS, NU_TELEFONO, CD_VERIFICACION, LG_ACTIVO, CD_USUARIO, F_CREACION) " +
            "VALUES (SQ_GEN_VERIFICACION_SMS.nextval, :telefono , :codigoVerificacion , :lActivo , :xUsuario , CURRENT_TIMESTAMP) ", nativeQuery = true)
    void saveDatosEnvio(
            @Param("codigoVerificacion") String codigoVerificacion,
            @Param("telefono") Long telefono,
            @Param("lActivo") Long lActivo,
            @Param("xUsuario") Long xUsuario);

    @Query(value = "SELECT gvs.CD_VERIFICACION codigoVerificacion, gvs.LG_ACTIVO lActivo, gvs.F_CREACION fechaCreacion FROM GEN_VERIFICACION_SMS gvs WHERE gvs.CD_VERIFICACION = UPPER(:codigoVerificacion) AND gvs.CD_USUARIO = :xUsuario  ", nativeQuery = true)
    SmsProjection getVerificacionByCodigo(@Param("codigoVerificacion") String codigoVerificacion, @Param("xUsuario") Long xUsuario);


    @Transactional
    @Modifying
    @Query(value = "UPDATE GEN_VERIFICACION_SMS GVS SET GVS.LG_ACTIVO = 0 WHERE gvs.CD_VERIFICACION = :codigoVerificacion ", nativeQuery = true)
    void updateEstadoCodigoVerificacion(@Param("codigoVerificacion") String codigoVerificacion);

    @Query(value = "SELECT COUNT(*) FROM GEN_VERIFICACION_SMS " +
            "WHERE NU_TELEFONO = :numTelefono AND F_CREACION >= TRUNC(SYSDATE) AND LG_ACTIVO = 1", nativeQuery = true)
    Long countIntentos (@Param("numTelefono") String numTelefono);

    @Query(value = "SELECT COUNT(*) FROM GEN_VERIFICACION_SMS " +
            "WHERE CD_VERIFICACION = :codigoVerificacion " +
            "AND CD_USUARIO = :xUsuario " +
            "AND LG_ACTIVO = 0 ", nativeQuery = true)
    Long verificarCodigoFirma (@Param("codigoVerificacion") String codigoVerificacion, @Param("xUsuario") Long xUsuario); 
    
    @Query(value = "SELECT EMP.TELEFONO FROM DELPHOS_SEGEDU.TLEMPLEADOS EMP WHERE emp.x_empleado = :idEmpleadoComunica ", nativeQuery = true)
    String getTelefonoUsuarioLogado (@Param("idEmpleadoComunica") Long idEmpleadoComunica);

    @Query(value = "select t.NOMBRE AS nombre , t.APELLIDO1 AS apellido1 , t.APELLIDO2 AS apellido2 ,t.C_NUMIDE AS dni, adj.NU_ORDEN AS orden,adj.LG_FIRMADO AS firmado , adj.FH_FIRMA AS fechaFirma from delphos.dgc_adjunto_firmantes adj " +
            "INNER JOIN DELPHOS.TLEMPLEADOS t ON adj.X_EMPLEADO = t.X_EMPLEADO WHERE adj.ID_ADJUNTO = :idAdjunto " +
            "ORDER BY adj.NU_ORDEN ASC, t.NOMBRE ASC, t.APELLIDO1 ASC, t.APELLIDO2 ASC ", nativeQuery = true)
    List<FlujoAdjuntosProjection> getFlujoFirma (@Param("idAdjunto") Long idAdjunto);

}

