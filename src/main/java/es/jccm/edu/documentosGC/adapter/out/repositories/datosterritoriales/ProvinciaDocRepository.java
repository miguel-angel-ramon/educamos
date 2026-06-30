package es.jccm.edu.documentosGC.adapter.out.repositories.datosterritoriales;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.jccm.edu.documentosGC.application.domain.datosterritoriales.ProvinciaDoc;
import es.jccm.edu.documentosGC.application.domain.datosterritoriales.QProvinciaDoc;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ProvinciaDocRepository extends AbstractRepository<ProvinciaDoc, Long, QProvinciaDoc> {

	List<ProvinciaDoc> findAllByEsManchega(String esManchega);
	
	
	@Query(value = "select distinct prv.* "
			+ "from TLCENTROZONA zon, TLUSUARIOZONA usu, TLDATOSCEN dcen, TLPROVINCIAS prv "
			+ "where zon.x_zona = usu.x_zona "
			+ "and zon.x_centro = dcen.x_centro "
			+ "and dcen.c_provincia = prv.c_provincia "
			+ "and usu.x_perfil = ?1 "
			+ "and usu.x_usuario = ?2 "           
			+ "order by prv.d_provincia ", nativeQuery = true)
	List<ProvinciaDoc> getListadoProvinciasZona(Long idPerfil, 
										        Long idUsuario);

	@Query(value = "select distinct prv.* "
			+ "from TLINSPECTORESCEN insc, TLDATOSCEN dcen, TLPROVINCIAS prv "
			+ "where insc.x_centro = dcen.x_centro "
			+ "and dcen.c_provincia = prv.c_provincia "
			+ "and insc.x_empleado =  ?1 "
			+ "and insc.f_tomapos = ?2 "
			+ "order by prv.d_provincia ", nativeQuery = true)
	List<ProvinciaDoc> getListadoProvinciasCentro(Long xEmpleado, Date fTomapos);

	@Query(value = "select distinct prv.* "
			+ "from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv "
			+ "where u.x_usuario = ?2 "
			+ "and pto.x_empleado=u.x_empleado "
			+ "and pop.x_empleado=pto.x_empleado "
			+ "and pop.f_tomapos = pto.f_tomapos "
			+ "and pto.x_centro = dcen.x_centro "
			+ "and dcen.c_provincia = prv.c_provincia "
			+ "and pop.x_perfil = ?1 "
			+ "and pto.x_centro = ?3  "
			+ "and (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) ", nativeQuery = true)
	List<ProvinciaDoc> getListadoProvinciasProvincial(Long idPerfil, Long idUsuario, Long idCentroProvincia);

}
