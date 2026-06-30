package es.jccm.edu.proyectosfct.adapter.out.repositories.datosgestora;

import es.jccm.edu.proyectosfct.application.domain.tareasSincronizadas.entities.TareaSincronizada;
import es.jccm.edu.proyectosfct.application.domain.tareasSincronizadas.entities.QTareaSincronizada;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ControlTareasSincGestoraRepository extends AbstractRepository<TareaSincronizada, Long, QTareaSincronizada> {

    @Query(value = "SELECT NVL(LG_EN_EJECUCION, 0) FROM FCT_TAREAS_SINCRONIZADAS WHERE DS_NOMBRE_TAREA = :nombreTarea", nativeQuery = true)
    Integer trabajoEnProgreso(String nombreTarea);

    @Modifying
    @Transactional
    @Query(value = "MERGE INTO FCT_TAREAS_SINCRONIZADAS USING dual " +
            "ON (DS_NOMBRE_TAREA = :nombreTarea) " +
            "WHEN MATCHED THEN " +
            "UPDATE SET LG_EN_EJECUCION = 1, F_INICIO_EJECUCION = SYSDATE, F_FIN_EJECUCION = NULL " +
            "WHEN NOT MATCHED THEN " +
            "INSERT (ID_TAREA, DS_NOMBRE_TAREA, LG_EN_EJECUCION, F_INICIO_EJECUCION, F_FIN_EJECUCION) " +
            "VALUES (SQ_FCT_TAREAS_SINCRONIZADAS.NEXTVAL, :nombreTarea, 1, SYSDATE, NULL)",
            nativeQuery = true)
    void comenzarTrabajo(String nombreTarea);

    @Modifying
    @Transactional
    @Query(value = "UPDATE FCT_TAREAS_SINCRONIZADAS " +
            "SET LG_EN_EJECUCION = 0, F_FIN_EJECUCION = SYSDATE " +
            "WHERE DS_NOMBRE_TAREA = :nombreTarea",
            nativeQuery = true)
    void finalizarTrabajo(String nombreTarea);
}
