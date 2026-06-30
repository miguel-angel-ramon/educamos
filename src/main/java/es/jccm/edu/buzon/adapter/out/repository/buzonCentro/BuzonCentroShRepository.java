package es.jccm.edu.buzon.adapter.out.repository.buzonCentro;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.buzon.application.domain.buzonCentro.QUnidadBuzonCentro;
import es.jccm.edu.buzon.application.domain.buzonCentro.UnidadBuzonCentro;
import es.jccm.edu.buzon.application.domain.buzonCentro.projection.UnidadBuzonCentroProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface BuzonCentroShRepository extends AbstractRepository<UnidadBuzonCentro, Long, QUnidadBuzonCentro>{
	
	@Query(value = "SELECT DISTINCT udc.X_UNIDAD idUnidad, udc.T_NOMBRE unidad, curso.X_ETAPA idCurso, ofg.X_OFERTAMATRIG idOfertamatrig, ofg.D_OFERTAMATRIG curso, " +
			"etapa.X_ETAPA idEtapa, etapa.X_ETAPA idEtapaSec, ciclo.X_ETAPA idCiclo, etapa.S_ETAPA etapa, etapa.n_orden ordenetapa, curso.n_orden ordencurso " +
			"FROM DELPHOS.TLUNIDADESCEN udc " +
			"INNER JOIN DELPHOS.TLOFERTASUNIDAD ou ON ou.X_UNIDAD = udc.X_UNIDAD " +
			"INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = ou.X_OFERTAMATRIC " +
			"INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG " +
			"INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG " +
			"INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = ofg.X_MODALIDAD " +
			"INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD " +
			"INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA " +
			"INNER JOIN DELPHOS.TLETAPAS ciclo ON ciclo.X_ETAPA = curso.X_ETAPADEPENDEDE " +
			"INNER JOIN DELPHOS.TLETAPAS etapa ON etapa.X_ETAPA = ciclo.X_ETAPADEPENDEDE " +
			"WHERE udc.X_CENTRO = :idCentro AND udc.C_ANNO = :anno " +
			"ORDER BY etapa.n_orden, curso.X_ETAPA, unidad", nativeQuery = true)
	List<UnidadBuzonCentroProjection> getUnidadesBuzonCentroCompClaveDirector(@Param("idCentro") Long idCentro, @Param("anno") Long anno);

	
	@Query(value = "SELECT DISTINCT udc.X_UNIDAD idUnidad, udc.T_NOMBRE unidad, curso.X_ETAPA idCurso, ofg.X_OFERTAMATRIG idOfertamatrig, ofg.D_OFERTAMATRIG curso, "
    		+ "etapa.X_ETAPA idEtapa, etapa.X_ETAPA idEtapaSec, ciclo.X_ETAPA idCiclo, etapa.S_ETAPA etapa, etapa.n_orden ordenetapa, curso.n_orden ordencurso "
    		+ "FROM DELPHOS.TLUNIDADESCEN udc "
    		+ "INNER JOIN DELPHOS.TLOFERTASUNIDAD ou ON ou.X_UNIDAD = udc.X_UNIDAD "
    		+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = ou.X_OFERTAMATRIC "
    		+ "INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
    		+ "INNER JOIN DELPHOS.TLMODALIDADES modalidades ON modalidades.X_MODALIDAD = ofg.X_MODALIDAD "
    		+ "INNER JOIN DELPHOS.TLCURSOMODA curModa ON curModa.X_MODALIDAD = modalidades.X_MODALIDAD AND curModa.X_CURSOMOD = cuo.X_CURSOMOD "
    		+ "INNER JOIN DELPHOS.TLETAPAS curso ON curso.X_ETAPA = curModa.X_ETAPA "
    		+ "INNER JOIN DELPHOS.TLETAPAS ciclo ON ciclo.X_ETAPA = curso.X_ETAPADEPENDEDE "
    		+ "INNER JOIN DELPHOS.TLETAPAS etapa ON etapa.X_ETAPA = ciclo.X_ETAPADEPENDEDE "
    		+ "WHERE udc.X_EMPLEADO = :idEmpleado AND udc.F_TOMAPOS = :fechaTomaPosesion "
    		+ "AND udc.X_CENTRO = :idCentro AND udc.C_ANNO = :anno "
    		+ "ORDER BY etapa.n_orden, curso.n_orden, unidad", nativeQuery = true)
    List<UnidadBuzonCentroProjection> getUnidadesBuzonCentroCompClave(@Param("idEmpleado") Long idEmpleado,
    																@Param("fechaTomaPosesion") String fechaTomaPosesion,
    																@Param("idCentro") Long idCentro,
            														@Param("anno") Long anno);


}
