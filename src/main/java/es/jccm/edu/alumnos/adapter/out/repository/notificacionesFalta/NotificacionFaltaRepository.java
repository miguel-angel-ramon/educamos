package es.jccm.edu.alumnos.adapter.out.repository.notificacionesFalta;

import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.projection.NotificacionFaltaProjection;
import es.jccm.edu.alumnos.application.domain.notificacionesFalta.NotificacionFalta;
import es.jccm.edu.alumnos.application.domain.notificacionesFalta.QNotificacionFalta;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionFaltaRepository extends AbstractRepository<NotificacionFalta, Integer, QNotificacionFalta> {

    @Query(value = "SELECT COUNT(*) " +
            "FROM ( " +
                "SELECT DISTINCT t.* " +
                "FROM DELPHOS_SEGEDU.TLNOTFALASIALU t, DELPHOS_SEGEDU.TLNOTFALASITRA t2, TLMATALU m " +
                "WHERE m.X_UNIDAD = :idUnidad " +
                "AND t2.X_TRAMO IN ( " +
                    "SELECT DISTINCT x_tramo " +
                    "FROM DELPHOS_SEGEDU.TLHORARIOSR t3 " +
                    "WHERE t3.X_HORARIORE IN ( " +
                        "SELECT t4.X_HORARIORE " +
                        "FROM DELPHOS_SEGEDU.TLUNIAFETRAHOR t4 " +
                        "WHERE t4.X_MATERIAOMG = :idMateria " +
                        "AND t4.X_UNIDAD = :idUnidad) " +
                        "AND t3.N_DIASEMANA = ( " +
                            "SELECT TRUNC(t.F_INICIO_FALASI) - TRUNC(t.F_INICIO_FALASI, 'IW') + 1 " +
                            "FROM DUAL)) " +
                        "AND t.X_NOTFALASIALU = t2.X_NOTFALASIALU " +
                        "AND t.X_MATRICULA = m.X_MATRICULA " +
                "UNION " +
                "SELECT DISTINCT t.* " +
                "FROM DELPHOS_SEGEDU.TLNOTFALASIALU t, TLMATALU m " +
                "WHERE t.F_FIN_FALASI IS NOT NULL " +
                "AND m.X_UNIDAD = :idUnidad " +
                "AND TRUNC(t.F_INICIO_FALASI) - TRUNC(t.F_INICIO_FALASI, 'IW') + 1 IN ( " +
                    "SELECT t5.N_DIASEMANA " +
                    "FROM DELPHOS_SEGEDU.TLHORARIOSR t5 " +
                    "WHERE t5.X_HORARIORE IN ( " +
                        "SELECT t6.X_HORARIORE " +
                        "FROM DELPHOS_SEGEDU.TLUNIAFETRAHOR t6 " +
                        "WHERE t6.X_MATERIAOMG = :idMateria " +
                        "AND t6.X_UNIDAD = :idUnidad) " +
                        "AND t.X_MATRICULA = m.X_MATRICULA))", nativeQuery = true)
    Long countByAsignatura(@Param("idUnidad") Long idUnidad, @Param("idMateria") Long idMateria);

    
    @Query(value=" SELECT nof.x_notfalasialu idNotificacion, f_inicio_falasi fechau, f_fin_falasi fechdia, t_observacion observacion, d_valliscat motivo, x_valliscat idMotivo "
    		+ "FROM DELPHOS_SEGEDU.tlnotfalasialu nof, DELPHOS_SEGEDU.tlvalliscat val  "
    		+ "WHERE nof.x_motfalasialu = val.x_valliscat AND nof.x_notfalasialu = :idNotificacion", nativeQuery = true)
    NotificacionFaltaProjection getNotification(@Param("idNotificacion") Long idNotificacion);
}
