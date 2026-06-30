package es.jccm.edu.proyectosfct.adapter.out.repositories.descarga;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.descarga.PrintField;

@Repository
public interface PrintFieldRepository extends JpaRepository<PrintField, Long> {
	
	List<PrintField> findByPrintFormId(Long printForm);

}
