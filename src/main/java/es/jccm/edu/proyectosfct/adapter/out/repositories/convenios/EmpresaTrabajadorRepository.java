package es.jccm.edu.proyectosfct.adapter.out.repositories.convenios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.convenios.entities.EmpresaTrabajador;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.QEmpresaTrabajador;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EmpresaTrabajadorRepository extends AbstractRepository<EmpresaTrabajador, Long, QEmpresaTrabajador> {
	
	List<EmpresaTrabajador> findAllByEmpresaIdAndEsRepresentante(Long idEmpresa,Boolean esRepresentante);
	
	List<EmpresaTrabajador> findAllBySedeIdAndEsRepresentante(Long idSede,Boolean esRepresentante);

	List<EmpresaTrabajador> findAllByEmpresaIdAndEsResponsable(Long idEmpresa, Boolean esResponsable);

	List<EmpresaTrabajador> findAllBySedeIdAndEsResponsable(Long idSede, Boolean esResponsable);



}
