package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionUnidadProgramacionSaberBasico;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaSaberBasico;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaUnidadProgramacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaRelacionUnidadProgramacionSaberBasico;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface EvaRelacionUnidadProgramacionSaberBasicoRepository extends AbstractRepository<EvaRelacionUnidadProgramacionSaberBasico, Long, QEvaRelacionUnidadProgramacionSaberBasico> {
    EvaRelacionUnidadProgramacionSaberBasico findByUnidadProgramacionAndSaberBasico(EvaUnidadProgramacion unidadProgramacion, EvaSaberBasico saberBasico);

    void deleteByUnidadProgramacionAndSaberBasico(EvaUnidadProgramacion unidadProgramacion, EvaSaberBasico saberBasico);
    
    void deleteAllByUnidadProgramacion(EvaUnidadProgramacion unidadProgramacion);
    
    List<EvaRelacionUnidadProgramacionSaberBasico> findAllByUnidadProgramacion(EvaUnidadProgramacion unidadProgramacion);
    
    List<EvaRelacionUnidadProgramacionSaberBasico> findAllByUnidadProgramacionId(Long idUnidadProgramacion);
}
