package es.jccm.edu.documentosGC.adapter.out.repositories.centrodoc;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.CentroDocInspeccion;
import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.QCentroDocInspeccion;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.Date;
import java.util.List;

public interface CentroDocInspeccionRepository extends AbstractRepository<CentroDocInspeccion, Long, QCentroDocInspeccion> {
	@Query(value = "select cen.x_centro,  " 
			+ "cen.c_codigo || ' - ' || (den.s_denominacion) || ' ' || dat.d_especifica as descripcion    " 
			+ "from tlcentros cen, tldatoscen dat, tldengen den, TLCENTROS cen  "  
			+ "where dat.x_centro = cen.x_centro " 
			+ "and dat.x_dengen = den.x_dengen  "  
			+ "and dcen.l_vigente = 'S' "  
			+ "and cen.x_centro = dcen.x_centro  "
			+ "and cen.l_delegacion = 'N' "
			+ "and cen.l_extranjero = 'N' "
			+ "and dat.c_provincia = :cProvincia " 
			+ "and dat.c_municipio = :cMunicipio ", nativeQuery = true)
	List<CentroDocInspeccion> getCentroMunicipio(@Param("cProvincia") Long cProvincia, @Param("cMunicipio") Long cMunicipio);
	
	@Query(value = "select distinct dcen.x_centro, tlf_datoscentro(dcen.x_centro) descripcion "
			+ "from TLCENTROZONA zon, TLUSUARIOZONA usu, TLDATOSCEN dcen, TLCENTROS cen "
			+ "where zon.x_zona = usu.x_zona "
			+ "and zon.x_centro = dcen.x_centro "
			+ "and usu.x_perfil = :idPerfil "
			+ "and usu.x_usuario = :idUsuario "
			+ "and dcen.l_vigente = 'S' "
			+ "and cen.x_centro = dcen.x_centro "
			+ "and cen.l_delegacion = 'N' "
			+ "and cen.l_extranjero = 'N' "
			+ "and (:cProvincia = -1 OR dcen.c_provincia = :cProvincia) "
			+ "and (:cMunicipio = -1 OR dcen.c_municipio = :cMunicipio) ", nativeQuery = true)
	List<CentroDocInspeccion> getListadoCentrosMunicipioZona(@Param("cProvincia") Long cProvincia, 
															 @Param("cMunicipio") Long cMunicipio,
															 @Param("idPerfil") Long idPerfil,
															 @Param("idUsuario") Long idUsuario);

	@Query(value = "select distinct dcen.x_centro, tlf_datoscentro(dcen.x_centro) descripcion "
			+ "from TLINSPECTORESCEN insc, TLDATOSCEN dcen, TLCENTROS cen "
			+ "where insc.x_centro = dcen.x_centro "
			+ " and insc.x_empleado = :xEmpleado "
			+ " and insc.f_tomapos = :fTomaPos "
			+ " and cen.x_centro = dcen.x_centro "
			+ " and cen.l_delegacion = 'N' "
			+ " and cen.l_extranjero = 'N' "
			+ " and (:cProvincia = -1 OR dcen.c_provincia = :cProvincia) "
			+ " and (:cMunicipio = -1 OR dcen.c_municipio = :cMunicipio) "
			+ " order by 2 ", nativeQuery = true)
	List<CentroDocInspeccion> getListadoCentrosMunicipioInspectorCentro(@Param("cProvincia") Long cProvincia,
																        @Param("cMunicipio") Long cMunicipio,
																        @Param("xEmpleado") Long xEmpleado,
																        @Param("fTomaPos") Date fTomaPos);	
	
	@Query(value = "select distinct dcen1.x_centro, tlf_datoscentro(dcen1.x_centro) descripcion "
			+ "from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv, TLMUNICIPIOS mun, TLDATOSCEN dcen1 , TLCENTROS cen "
			+ "where  u.x_usuario = :idUsuario "
			+ "and pto.x_empleado=u.x_empleado "
			+ "and pop.x_empleado=pto.x_empleado "
			+ "and pop.f_tomapos = pto.f_tomapos "
			+ "and pto.x_centro = dcen.x_centro "
			+ "and dcen.c_provincia = prv.c_provincia "
			+ "and pop.x_perfil = :idPerfil "
			+ "and (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
			+ "and dcen.c_provincia = mun.c_provincia "
			+ "and dcen1.c_provincia = mun.c_provincia "
			+ "and dcen1.c_municipio = mun.c_municipio "
			+ "and dcen1.l_vigente = 'S' "
			+ "and cen.x_centro = dcen1.x_centro "
			+ "and cen.l_delegacion = 'N' "
			+ "and cen.l_extranjero = 'N' "
			+ "and (:cProvincia = -1 OR dcen1.c_provincia = :cProvincia) "
			+ "and (:cMunicipio = -1 OR dcen1.c_municipio = :cMunicipio) ", nativeQuery = true)
	List<CentroDocInspeccion> getListadoCentrosMunicipioInspectorProvincial(@Param("cProvincia") Long cProvincia, 
																			@Param("cMunicipio") Long cMunicipio,
																			@Param("idPerfil") Long idPerfil,
																			@Param("idUsuario") Long idUsuario);

	@Query(value = "select distinct dcen.x_centro, tlf_datoscentro(dcen.x_centro) descripcion "
			+ " from  TLPROVINCIAS prv, TLDATOSCEN dcen, TLMUNICIPIOS mun, TLCENTROS cen "
			+ " where prv.l_manchega = 'S' "
			+ " and dcen.c_provincia = prv.c_provincia "
			+ " and dcen.c_municipio = mun.c_municipio "
			+ " and mun.c_provincia = prv.c_provincia "
			+ " and dcen.l_vigente = 'S' "
			+ " and cen.x_centro = dcen.x_centro "
			+ " and cen.l_delegacion = 'N' "
			+ " and cen.l_extranjero = 'N' "
			+ " and (:cProvincia = -1 OR dcen.c_provincia = :cProvincia) "
			+ " and (:cMunicipio = -1 OR dcen.c_municipio = :cMunicipio)  ", nativeQuery = true)
	List<CentroDocInspeccion> getListadoCentrosConsejeria(@Param("cProvincia") Long cProvincia, 
														  @Param("cMunicipio") Long cMunicipio);
	
}


