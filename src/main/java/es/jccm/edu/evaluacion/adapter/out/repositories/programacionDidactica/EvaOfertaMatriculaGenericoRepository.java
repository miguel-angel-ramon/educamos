package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaOfertaMatriculaGenerico;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaOfertaMatriculaGenerico;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaOfertaMatriculaGenericoRepository extends AbstractRepository<EvaOfertaMatriculaGenerico, Long, QEvaOfertaMatriculaGenerico> {
	
	@Query(value = "Select omg.* "
			+ "From TLOFEMATRGEN omg "
			+ "Inner Join TLOFEMATRCEN omc On omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
			+ "Inner Join TLCENTROS centro On centro.X_CENTRO  = omc.x_centro "
			+ "INNER JOIN TLPERIODOSOMC periodo ON periodo.X_OFERTAMATRIC = omc.X_OFERTAMATRIC "
			+ "inner join tlcursomoda cm on cm.x_cursomod=omg.x_ofertamatrig "
			+ "Where centro.C_CODIGO = :codigoCentro "
			+ "AND omg.L_VIGENTE = 'S' "
			+ "AND PERIODO.C_ANNO <= :anyo "
			+ "and (periodo.C_ANNOPUEDETERMINAR IS NULL OR periodo.C_ANNOPUEDETERMINAR >= :anyo ) "
			+ "and (exists (select 1 from tlcomesp co where co.x_ciclo = cm.x_etapa) "
			+ "     or exists (select 1 from tlcomesp co, tletapas e where e.x_etapadependede=co.x_ciclo and e.x_etapa=cm.x_etapa))", nativeQuery = true)
	List<EvaOfertaMatriculaGenerico> findAllByAnyoAndCodigoCentro(@Param("anyo") Integer anyo, @Param("codigoCentro") Long codigoCentro);
	
	@Query(value = "SELECT DISTINCT omg.* FROM DELPHOS.TLOFEMATRGEN omg "
			+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLCENTROS cen ON cen.X_CENTRO = omc.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLPERIODOSOMC periodo ON periodo.X_OFERTAMATRIC = omc.X_OFERTAMATRIC "
			+ "INNER JOIN DELPHOS.TLMATALU mua ON mua.X_OFERTAMATRIG = omg.X_OFERTAMATRIG AND mua.X_CENTRO = cen.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATRICULA = mua.X_MATRICULA "
			+ "INNER JOIN DELPHOS.TLCURSOMODA cm on cm.X_CURSOMOD = omg.X_OFERTAMATRIG "
			+ "WHERE omg.L_VIGENTE = 'S' AND mma.X_ADAPTACION IS NOT NULL AND mua.X_OFERTAMATRIG <> mua.X_NIVEADAP "
			+ "AND mua.C_ANNO = :anyo AND cen.C_CODIGO = :codigoCentro AND periodo.C_ANNO <= :anyo "
			+ "AND (periodo.C_ANNOPUEDETERMINAR IS NULL OR periodo.C_ANNOPUEDETERMINAR >= :anyo) "
			+ "AND (exists (SELECT 1 FROM DELPHOS.TLCOMESP ce WHERE ce.X_CICLO = cm.X_ETAPA) "
			+ "OR EXISTS (SELECT 1 FROM DELPHOS.TLCOMESP ce, DELPHOS.TLETAPAS et WHERE et.X_ETAPADEPENDEDE = ce.X_CICLO AND et.X_ETAPA = cm.X_ETAPA)) "
			+ "AND NOT EXISTS (SELECT PD.* "
			+ " FROM DELPHOS.EVA_PROGDIDAC PD "
			+ " WHERE PD.X_MATERIAOMG = mma.X_MATERIAOMG "
			+ " AND PD.X_OFERTAMATRIG = mua.X_OFERTAMATRIG "
			+ " AND PD.X_CENTRO = cen.X_CENTRO "
			+ " AND PD.NU_ANNO = mua.C_ANNO "
			+ " AND PD.X_NIVEADAP = mua.X_NIVEADAP) "
			+ "ORDER BY omg.N_ORDENPRES", nativeQuery = true)
	List<EvaOfertaMatriculaGenerico> findAllACNEAEByAnyoAndCodigoCentro(@Param("anyo") Integer anyo, @Param("codigoCentro") Long codigoCentro);
	
	@Query(value = "SELECT DISTINCT nc.* FROM DELPHOS.TLOFEMATRGEN nc "
			+ "INNER JOIN DELPHOS.TLMATALU mua ON mua.X_NIVEADAP = nc.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = mua.X_OFERTAMATRIG  "
			+ "INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLCENTROS cen ON cen.X_CENTRO = omc.X_CENTRO AND cen.X_CENTRO = mua.X_CENTRO "
			+ "INNER JOIN DELPHOS.TLMATOFEMATRG mog ON mog.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATRICULA = mua.X_MATRICULA AND mma.X_MATERIAOMG = mog.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLCURSOMODA cm on cm.X_CURSOMOD = nc.X_OFERTAMATRIG "
			+ "INNER JOIN DELPHOS.TLPERIODOSOMC pomc ON pomc.X_OFERTAMATRIC=omc.X_OFERTAMATRIC "
			+ "WHERE omg.L_VIGENTE = 'S' AND mma.X_ADAPTACION IS NOT NULL AND mua.X_OFERTAMATRIG <> mua.X_NIVEADAP "
			+ "AND mua.C_ANNO = :anyo AND cen.C_CODIGO = :codigoCentro AND pomc.C_ANNO <= :anyo "
			+ "AND (pomc.C_ANNOPUEDETERMINAR IS NULL OR pomc.C_ANNOPUEDETERMINAR >= :anyo) "
			+ "AND omg.X_OFERTAMATRIG = :idOfertaMatrig AND mog.X_MATERIAOMG = :idMateriaOmg "
			+ "AND (exists (SELECT 1 FROM DELPHOS.TLCOMESP ce WHERE ce.X_CICLO = cm.X_ETAPA) "
			+ "OR EXISTS (SELECT 1 FROM DELPHOS.TLCOMESP ce, DELPHOS.TLETAPAS et WHERE et.X_ETAPADEPENDEDE = ce.X_CICLO AND et.X_ETAPA = cm.X_ETAPA)) "
			+ "AND NOT EXISTS (SELECT PD.* "
			+ " FROM DELPHOS.EVA_PROGDIDAC PD "
			+ " WHERE PD.X_MATERIAOMG = mog.X_MATERIAOMG "
			+ " AND PD.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
			+ " AND PD.X_CENTRO = cen.X_CENTRO "
			+ " AND PD.NU_ANNO = mua.C_ANNO "
			+ " AND PD.X_NIVEADAP = mua.X_NIVEADAP) "
			+ "ORDER BY nc.N_ORDENPRES", nativeQuery = true)
	List<EvaOfertaMatriculaGenerico> findAllNivelesCurricularesACNEAEByAnyoCodigoCentroCursoAndMateria(@Param("anyo") Integer anyo, @Param("codigoCentro") Long codigoCentro, @Param("idOfertaMatrig") Long idOfertaMatrig, @Param("idMateriaOmg") Long idMateriaOmg);
	
}