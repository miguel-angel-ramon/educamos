package es.jccm.edu.proyectosfct.adapter.out.repositories.descarga;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.descarga.PrintForm;

@Repository
public interface PrintFormRepository extends JpaRepository<PrintForm, Long>{
	
	PrintForm findByPrintFormAlias(String name);

}
