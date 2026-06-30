package es.jccm.edu.proyectosfct.adapter.out.repositories.sedeempresa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.empresas.QSedeEmpresa;
import es.jccm.edu.proyectosfct.application.domain.empresas.SedeEmpresa;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface SedeEmpresaRepository extends AbstractRepository<SedeEmpresa, Long, QSedeEmpresa>{
	
	List<SedeEmpresa> findByEmpresaId(Long idEmpresa);

	@Query(value = "SELECT sede.*" +
			" FROM FCT_CONVENIOS conv, EMP_SEDEMP sede " +
			" WHERE conv.id_convenio = :idConvenio " +
			" AND conv.id_sedemp = sede.id_sedemp ", nativeQuery = true)
	SedeEmpresa findByIdConvenio(Long idConvenio);

	@Query(value = "SELECT * FROM EMP_SEDEMP where id_sedemp = :idSede ", nativeQuery = true)
	SedeEmpresa findByIdSede(Long idSede);

	@Query(value = "SELECT * "
			+ "FROM ( "
			+ "  SELECT sed.* "
			+ "  FROM emp_traemp tra "
			+ "  JOIN emp_sedemp sed ON tra.id_sedemp = sed.id_sedemp "
			+ "  WHERE tra.id_traemp = :idTrabajador "
			+ "  ORDER BY sed.id_sedemp desc  "
			+ ") "
			+ "WHERE ROWNUM = 1 ", nativeQuery = true)
	SedeEmpresa findByIdTrabajador(Long idTrabajador);

}
