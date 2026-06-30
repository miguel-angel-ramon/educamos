package es.jccm.edu.documentosGC.adapter.out.repositories.datosterritoriales;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.documentosGC.application.domain.datosterritoriales.MunicipioDoc;
import es.jccm.edu.documentosGC.application.domain.datosterritoriales.QMunicipioDoc;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface MunicipioDocRepository extends AbstractRepository<MunicipioDoc, Long, QMunicipioDoc> {
	
	List<MunicipioDoc> findByIdProvincia(Long idProvincia);
	
	@Query(value = "select distinct mun.* "
			+ "from TLCENTROZONA zon, TLUSUARIOZONA usu, TLDATOSCEN dcen, TLMUNICIPIOS mun "
			+ "where zon.x_zona = usu.x_zona "
			+ "and zon.x_centro = dcen.x_centro "
			+ "and dcen.c_provincia = mun.c_provincia "
			+ "and dcen.c_municipio = mun.c_municipio "
			+ "and usu.x_perfil = ?2 "
			+ "and usu.x_usuario = ?3 "
			+ "and (?1 = -1 OR dcen.c_provincia = ?1) ", nativeQuery = true)
	List<MunicipioDoc> getMunicipioProvinciaZona(Long idProvincia, Long idPerfil, Long idUsuario);

	@Query(value = "select distinct mun.* "
			+ "from TLINSPECTORESCEN insc, TLDATOSCEN dcen, TLMUNICIPIOS mun "
			+ "where  insc.x_centro = dcen.x_centro "
			+ "and dcen.c_provincia = mun.c_provincia "
			+ "and dcen.c_provincia = mun.c_provincia "
			+ "and dcen.c_municipio = mun.c_municipio "
			+ "and insc.x_empleado = ?2 "
			+ "and insc.f_tomapos = ?3 "
			+ "and (?1 = -1 OR dcen.c_provincia = ?1) "
			+ "order by mun.d_municipio ", nativeQuery = true)
	List<MunicipioDoc> getMunicipioProvinciaCentro(Long idProvincia, Long xEmpleado, Date fTomaPos);	

	@Query(value = "select distinct mun.* "
			+ "from   TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv, TLMUNICIPIOS mun "
			+ "where  u.x_usuario = ?3 "
			+ "and pto.x_empleado=u.x_empleado "
			+ "and pop.x_empleado=pto.x_empleado "
			+ "and pop.f_tomapos = pto.f_tomapos "
			+ "and pto.x_centro = dcen.x_centro "
			+ "and dcen.c_provincia = prv.c_provincia "
			+ "and pop.x_perfil = ?2 "
			+ "and (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
			+ "and dcen.c_provincia = mun.c_provincia "
			+ "and (?1 = -1 OR dcen.c_provincia = ?1)    ", nativeQuery = true)
	List<MunicipioDoc> getMunicipioInspectorProvincial(Long idProvincia, Long idPerfil, Long idUsuario);

	@Query(value = "select distinct mun.* from  TLPROVINCIAS prv, TLDATOSCEN dcen, TLMUNICIPIOS mun "
			+ "where prv.l_manchega = 'S' "
			+ "and dcen.c_provincia = prv.c_provincia "
			+ "and dcen.c_municipio = mun.c_municipio "
			+ "and mun.c_provincia = prv.c_provincia "
			+ "and dcen.l_vigente = 'S' "
			+ "and (?1 = -1 OR mun.c_provincia = ?1) ", nativeQuery = true)
	List<MunicipioDoc> getMunicipioConsejeria(Long idProvincia);

}
