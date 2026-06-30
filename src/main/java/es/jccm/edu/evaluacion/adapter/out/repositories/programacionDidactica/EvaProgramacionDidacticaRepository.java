package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaDepartamentoCentroProjection;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaInformeRodalProjection;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaProgramacionDidacticaDuplicadaProjection;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaProgramacionDidacticaHistoricaProjection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaCompetenciaEspecificaDidacticaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;

@Repository
public interface EvaProgramacionDidacticaRepository extends AbstractRepository<EvaProgramacionDidactica, Long, QEvaProgramacionDidactica> {
 
	EvaProgramacionDidactica findByMateriaomgAndOfertamatrigAndCentroAndAnnoAndAcneaeAndNiveadap(Long materiaomg, Long ofertamatrig, Long centro, Integer anno, Integer acneae, Long niveadap);

	@Query(value = "SELECT pd.ID_PROGDIDAC id, mc.S_MATERIAC ||' - '|| mc.T_ABREV nombre "
			+ "FROM EVA_PROGDIDAC pd "
			+ "INNER JOIN tlofematrcen OFC ON ofc.X_OFERTAMATRIG = pd.X_OFERTAMATRIG AND OFC.X_CENTRO = pd.X_CENTRO  "
			+ "INNER JOIN tlmatofematrg matofe ON matofe.X_MATERIAOMG = pd.X_MATERIAOMG "
			+ "INNER JOIN tlmateriascurso mc ON mc.x_materiac = matofe.x_materiac  "
			+ "WHERE pd.LG_CERRADA = 1 "
			+ "AND pd.X_CENTRO = :idCentro "
			+ "AND pd.X_OFERTAMATRIG = :idOfermatrig "
			+ "AND matofe.X_MATERIAOMG != :idMateriaOmg "
			+ "AND pd.NU_ANNO = :anno "
			+ "AND mc.X_MATERIAG = (SELECT mc2.X_MATERIAG  "
			+ "   FROM tlmatofematrg matofe2 "
			+ "   INNER JOIN tlmateriascurso mc2 ON mc2.x_materiac = matofe2.x_materiac  "
			+ "   WHERE matofe2.X_MATERIAOMG = :idMateriaOmg)", nativeQuery = true)
	EvaCompetenciaEspecificaDidacticaProjection comprobarDuplicarProgramacionDidactica(@Param("idMateriaOmg") Long idMateriaOmg, @Param("idOfermatrig") Long idOfermatrig,
			@Param("idCentro") Long idCentro, @Param("anno") Integer anno);

	@Query(value = "select ofg.X_OFERTAMATRIG from tlofematrgen ofg " +
			"where ofg.x_etapatipolib in (679,667,771) and ofg.l_vigente='S' " +
			"and ofg.c_annotermina is NULL", nativeQuery = true)
	List<Long> getIdOfertamatrigCiclos();

	@Query(value = "   SELECT  momg.X_MATERIAOMG " +
			"FROM " +
			"    (SELECT  " +
			"        mc.x_materiag, matofe.x_grupomat " +
			"    FROM " +
			"        tlmatofematrg matofe, " +
			"        tlmateriascurso mc " +
			"    WHERE " +
			"        matofe.x_materiaomg = :idMateriaOmg " +
			"        AND matofe.x_materiac = mc.x_materiac) generica, " +
			"    tlmateriascurso mc1, " +
			"    TLMATOFEMATRG momg " +
			"WHERE " +
			"    mc1.x_materiag = generica.x_materiag " +
			"    AND momg.X_MATERIAC = mc1.X_MATERIAC " +
			"    AND mc1.C_ANNOHASTA IS NULL " +
			"    AND mc1.x_cursomod = :idNivelCurricular " +
			"    and momg.x_grupomat=generica.x_grupomat", nativeQuery = true)
	Long getIdMateriaOmgACNEAE(@Param("idMateriaOmg") Long idMateriaOmg, @Param("idNivelCurricular") Long idNivelCurricular);

	EvaProgramacionDidactica findByMateriaomgAndOfertamatrigAndCentroAndAnnoAndAcneae(Long materiaomg, Long ofertamatrig, Long centro, Integer anno, Integer acneae);

	@Query(value = "select adj.ID_DOCHIS_RODAL idRodal, adj.TX_DOCHIS_FICHERO nombreFichero " +
			"from dgc_documentos doc, dgc_documento_historial his, " +
			"dgc_historial_adjuntos adj, eva_progdidac eva " +
			"where eva.id_progdidac = :idProgramacionDidactica " +
			"AND doc.id_progdidac = eva.id_progdidac " +
			"AND his.id_documento = doc.id_documento " +
			"AND adj.id_historial = his.id_historial " +
			"AND rownum = 1 order by his.fh_registro desc", nativeQuery = true)
	EvaInformeRodalProjection datosInformeRodal(@Param("idProgramacionDidactica") Long idProgramacionDidactica);
	
	@Query("SELECT progDidac FROM EvaProgramacionDidactica progDidac " +
			"WHERE progDidac.materiaomg = :materiaomg AND progDidac.ofertamatrig = :ofertamatrig " +
			"AND progDidac.centro = :centro AND progDidac.anno = :anno " +
			"AND progDidac.niveadap IS NULL AND progDidac.idMateriaOmgAdap IS NULL " +
			"ORDER BY progDidac.id")
	List<EvaProgramacionDidactica> getProgramacionesDidacticasByMateriaomgAndOfertamatrigAndCentroAndAnno(@Param("materiaomg") Long materiaomg, @Param("ofertamatrig") Long ofertamatrig, @Param("centro") Long centro, @Param("anno") Integer anno);
	
	List<EvaProgramacionDidactica> findAllByMateriaomgAndOfertamatrigAndCentroAndAnnoAndAcneaeAndNiveadapAndIdMateriaOmgAdap(Long materiaomg, Long ofertamatrig, Long centro, Integer anno, Integer acneae, Long niveadap, Long idMateriaOmgAdap);
	
	@Query(value = "select epd.x_materiaomg idMateriaOmg, epd.x_ofertamatrig idOfertaMatrig, "
			+ "epd.x_centro idCentro, epd.NU_ANNO anyo, count(*) countDuplicadas "
			+ "from DELPHOS.EVA_PROGDIDAC epd\n"
			+ "WHERE epd.X_MATERIAOMGADAP IS NULL AND epd.X_NIVEADAP IS null "
			+ "group by epd.x_materiaomg, epd.x_ofertamatrig, epd.x_centro, epd.NU_ANNO "
			+ "having count(*)>1", nativeQuery = true)
	List<EvaProgramacionDidacticaDuplicadaProjection> getProgramacionesDidacticasDuplicadas();

	@Query(value = "SELECT DISTINCT dpc.X_DEPARTCEN idDepartamento, gen.D_DEPARTAMENTO  nombreDepartamento " +
			"FROM DELPHOS.TLDEPDENGEN gen " +
			"INNER JOIN delphos.TLDEPARTCEN dpc ON gen.X_DEPDENGEN  = dpc.X_DEPDENGEN  " +
			"INNER JOIN delphos.TLMIEDEPART mdp ON mdp.X_DEPARTCEN = dpc.X_DEPARTCEN " +
			"INNER JOIN delphos.TLCURSOACA cua ON cua.C_ANNO = dpc.C_ANNO " +
			"INNER JOIN tlmatofematrcen mac ON mac.X_MATERIAOMG = :materia AND mac.X_CENTRO = dpc.X_CENTRO AND mac.X_DEPARTCEN = dpc.X_DEPARTCEN " +
			"WHERE dpc.X_CENTRO = :idCentro " +
			"AND dpc.C_ANNO = :anyo", nativeQuery = true)
	public EvaDepartamentoCentroProjection getDepartamentosProgramacionDidactica(@Param("idCentro") Long idCentro,
																				 @Param("anyo") Integer anyo,
																				 @Param("materia") Long materia);

	@Query(value = "SELECT pd.ID_PROGDIDAC idProgramacionDidactica, pd.NU_ANNO anyo, cur.C_ANNO || '/' || (cur.C_ANNO + 1) tramo, pd.X_MATERIAOMG idMateriaOmg " +
			"FROM DELPHOS.EVA_PROGDIDAC pd " +
			"INNER JOIN DELPHOS.TLCURSOACA cur ON cur.C_ANNO = pd.NU_ANNO " +
			"INNER JOIN DELPHOS.TLCENTROS cen ON cen.X_CENTRO = pd.X_CENTRO " +
			"WHERE cur.C_ANNO < DELPHOS.TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION() " +
			"AND cen.C_CODIGO = :codigoCentro AND pd.X_MATERIAOMG = :idMateriaOmg AND pd.X_OFERTAMATRIG = :idOfertamatrig " +
			"AND pd.LG_ACNEAE = 0", nativeQuery = true)
	List<EvaProgramacionDidacticaHistoricaProjection> getProgramacionesDidacticasAnyosAnteriores(@Param("codigoCentro") Long codigoCentro, @Param("idMateriaOmg") Long idMateriaOmg, @Param("idOfertamatrig") Long idOfertamatrig);

	@Query(value = "SELECT " +
			"    CASE " +
			"        WHEN EXISTS (" +
			"            SELECT 1" +
			"            FROM DELPHOS.EVA_PROGDIDAC pd" +
			"            INNER JOIN DELPHOS.TLCURSOACA cur ON cur.C_ANNO = pd.NU_ANNO" +
			"            INNER JOIN DELPHOS.TLCENTROS cen ON cen.X_CENTRO = pd.X_CENTRO" +
			"            WHERE cur.C_ANNO = DELPHOS.TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION()" +
			"            AND cen.C_CODIGO = :codigoCentro " +
			"            AND pd.X_MATERIAOMG = :idMateriaOmg " +
			"            AND pd.X_OFERTAMATRIG = :idOfertamatrig" +
			"            AND pd.LG_ACNEAE = 0" +
			"        ) " +
			"        THEN 1 " +
			"        ELSE 0 " +
			"    END AS resultado " +
			"FROM DUAL", nativeQuery = true)
	Integer hasProgramacionDidacticaAnnoAcademicoActual(@Param("codigoCentro") Long codigoCentro,
														@Param("idMateriaOmg") Long idMateriaOmg,
														@Param("idOfertamatrig") Long idOfertamatrig);

	@Query(value = "SELECT pd.ID_PROGDIDAC idProgramacionDidactica, pd.NU_ANNO anno, cur.C_ANNO || '/' || (cur.C_ANNO + 1) tramo, pd.X_MATERIAOMG idMateriaOmg, " +
			"pd.X_MATERIAOMGADAP idMateriaOmgAdaptacion, " +
			"(SELECT matc.S_MATERIAC || ' - ' || omg.T_ABREVIATURA nombre FROM DELPHOS.TLMATOFEMATRG mog " +
			"INNER JOIN DELPHOS.TLMATERIASCURSO matc ON matc.X_MATERIAC = mog.X_MATERIAC " +
			"INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = mog.X_OFERTAMATRIG " +
			"WHERE mog.X_MATERIAOMG = pd.X_MATERIAOMGADAP) nombreMateriaAdaptacion " +
			"FROM DELPHOS.EVA_PROGDIDAC pd " +
			"INNER JOIN DELPHOS.TLCURSOACA cur ON cur.C_ANNO = pd.NU_ANNO " +
			"INNER JOIN DELPHOS.TLCENTROS cen ON cen.X_CENTRO = pd.X_CENTRO " +
			"WHERE cur.C_ANNO < DELPHOS.TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION() " +
			"AND cen.C_CODIGO = :codigoCentro AND pd.X_MATERIAOMG = :idMateriaOmg AND pd.X_OFERTAMATRIG = :idOfertamatrig " +
			"AND pd.LG_ACNEAE = 1 AND pd.X_NIVEADAP = :idNivelCurricular", nativeQuery = true)
	List<EvaProgramacionDidacticaHistoricaProjection> getProgramacionesDidacticasACNEEAnyosAnteriores(@Param("codigoCentro") Long codigoCentro,
																									  @Param("idMateriaOmg") Long idMateriaOmg,
																									  @Param("idOfertamatrig") Long idOfertamatrig,
																									  @Param("idNivelCurricular") Long idNivelCurricular);

	@Query(value = "SELECT " +
			"    CASE " +
			"        WHEN EXISTS (" +
			"            SELECT 1" +
			"            FROM DELPHOS.EVA_PROGDIDAC pd" +
			"            INNER JOIN DELPHOS.TLCURSOACA cur ON cur.C_ANNO = pd.NU_ANNO" +
			"            INNER JOIN DELPHOS.TLCENTROS cen ON cen.X_CENTRO = pd.X_CENTRO" +
			"            WHERE cur.C_ANNO = DELPHOS.TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION()" +
			"            AND cen.C_CODIGO = :codigoCentro " +
			"            AND pd.X_MATERIAOMG = :idMateriaOmg " +
			"            AND pd.X_OFERTAMATRIG = :idOfertamatrig " +
			"            AND pd.LG_ACNEAE = 1 " +
			"            AND pd.X_NIVEADAP = :idNivelCurricular" +
			"        ) " +
			"        THEN 1 " +
			"        ELSE 0 " +
			"    END AS resultado" +
			"FROM DUAL", nativeQuery = true)
	Integer hasProgramacionDidacticaAnnoAcademicoActualAcnee(@Param("codigoCentro") Long codigoCentro,
															 @Param("idMateriaOmg") Long idMateriaOmg,
															 @Param("idOfertamatrig") Long idOfertamatrig,
															 @Param("idNivelCurricular") Long idNivelCurricular);
}