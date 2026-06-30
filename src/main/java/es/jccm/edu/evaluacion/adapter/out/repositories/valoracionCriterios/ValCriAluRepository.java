package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import com.querydsl.core.types.Predicate;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.QValCriAlu;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.ValCriAlu;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValCriAluRepository extends AbstractRepository<ValCriAlu, Long, QValCriAlu> {
    // Puedes agregar métodos personalizados para consultas específicas si es necesario
    // Solo necesitas agregarlos aquí y Spring Data JPA generará la implementación automáticamente
}

