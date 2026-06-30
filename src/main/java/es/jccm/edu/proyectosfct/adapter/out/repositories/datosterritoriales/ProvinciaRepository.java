package es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Provincia;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.QProvincia;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ProvinciaRepository extends AbstractRepository<Provincia, Long, QProvincia> {

	List<Provincia> findAllByEsManchega(String esManchega);

	@Query(value = "SELECT distinct prv.* "
			+ "FROM   TLEMPRESAS emp, EMP_SEDEMP sed, TLEMPTIPEMP ete, TLTIPOEMPRESA tip, TLPROVINCIAS prv "
			+ "WHERE  emp.l_activa = 'S' "
			+ "AND    emp.x_empresa = sed.x_empresa "
			+ "AND    emp.x_empresa = ete.x_empresa "
			+ "AND    ete.x_tipoempresa = tip.x_tipoempresa  "
			+ "AND    sed.c_provincia = prv.c_provincia "
			+ "AND    tip.c_tipoempresa = 'F' "
			+ "AND    sed.lg_principal = 1 "
			+ "AND    ete.l_vigente = 'S' "
			+ "AND    emp.l_activa = 'S' "
			+ "order  by prv.d_provincia ", nativeQuery = true)
	List<Provincia> findAllProvincias();

}
