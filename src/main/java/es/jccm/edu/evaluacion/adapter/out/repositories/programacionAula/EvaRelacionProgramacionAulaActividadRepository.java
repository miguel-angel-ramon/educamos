package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaProgramacionAula;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaActividad;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaRelacionProgramacionAulaActividad;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;

@Repository
public interface EvaRelacionProgramacionAulaActividadRepository extends AbstractRepository<EvaRelacionProgramacionAulaActividad, Long, QEvaRelacionProgramacionAulaActividad> {

	void deleteAllByActividad(EvaActividad actividadABorrar);
    
    void deleteAllByProgramacionAula(EvaProgramacionAula programacionAula);

    List<EvaRelacionProgramacionAulaActividad> getAllByProgramacionAula(EvaProgramacionAula programacionAula);

    Long countByProgramacionAula(EvaProgramacionAula programacionAula);
    
    @Query("SELECT rpaact AS relacionProgramacionAulaActividad FROM EvaRelacionProgramacionAulaActividad rpaact INNER JOIN EvaActividad act ON act = rpaact.actividad WHERE act.id = :idActividad")
    EvaRelacionProgramacionAulaActividad findByIdActividad(Long idActividad);

}