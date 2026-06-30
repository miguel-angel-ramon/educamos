package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaSaberBasico;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaSaberBasico;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;

@Repository
public interface EvaSaberBasicoRepository extends AbstractRepository<EvaSaberBasico, Long, QEvaSaberBasico> {
    List<EvaSaberBasico> findAllByIdBloque(Long idBloque);
    
    @Query("SELECT sabb FROM EvaUnidadProgramacion up " +
    "INNER JOIN EvaRelacionUnidadProgramacionSaberBasico rupsb ON rupsb.unidadProgramacion = up " +
    "INNER JOIN EvaSaberBasico sabb ON sabb = rupsb.saberBasico " +
    "WHERE up.id = :idUnidadProgramacion " +
    "ORDER BY sabb.abreviatura")
    List<EvaSaberBasico> findAllByIdUnidadProgramacion(@Param("idUnidadProgramacion") Long idUnidadProgramacion); 
	
}
