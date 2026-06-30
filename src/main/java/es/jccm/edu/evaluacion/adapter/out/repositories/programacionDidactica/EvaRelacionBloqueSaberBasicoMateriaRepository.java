package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionBloqueSaberBasicoMateria;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaRelacionBloqueSaberBasicoMateria;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;

@Repository
public interface EvaRelacionBloqueSaberBasicoMateriaRepository extends AbstractRepository<EvaRelacionBloqueSaberBasicoMateria, Long, QEvaRelacionBloqueSaberBasicoMateria> {
    List<EvaRelacionBloqueSaberBasicoMateria> findAllByIdMateriaOmg(Long idMateriaOmg);
}
