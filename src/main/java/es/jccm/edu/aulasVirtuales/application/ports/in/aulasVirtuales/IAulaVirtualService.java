package es.jccm.edu.aulasVirtuales.application.ports.in.aulasVirtuales;

import es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.AulaVirtualList;

import java.util.List;

public interface IAulaVirtualService {

    List<AulaVirtualList> getAulasVirtualesByProfesor(Long idEmpleado, Integer anno);
    
    List<AulaVirtualList> getAulasVirtuales(Long idEmpleado, Long oid, Integer anno);

}
