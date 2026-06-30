package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.EvaMateriasCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.QEvaMateriasCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaMateriasCentroProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaMateriasCentroRepository extends AbstractRepository<EvaMateriasCentro, Long, QEvaMateriasCentro> {

    @Query(value = "SELECT mog.X_MATERIAOMG idMateriaOmg, materia.S_MATERIAC nombreMateria, materia.D_MATERIAC descMateria, ofg.S_OFERTAMATRIG nombreCurso, materia.T_ABREV abrevMateria "
    		+ " FROM TLCENTROS centros "
    		+ " INNER JOIN TLOFEMATRCEN omc ON omc.X_CENTRO = centros.X_CENTRO "
    		+ " INNER JOIN TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG "
    		+ " INNER JOIN TLMATOFEMATRG mog ON mog.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
    		+ " INNER JOIN TLMATERIASCURSO materia ON materia.X_MATERIAC = mog.X_MATERIAC "
    		+ " INNER JOIN TLMATERIASGEN matg ON matg.X_MATERIAG = materia.X_MATERIAG "
    		+ " WHERE centros.C_CODIGO = :codigoCentro AND ofg.C_ANNO = :anyo"
    		+ " ORDER BY mog.N_ORDENPRES", nativeQuery = true)
    List<EvaMateriasCentroProjection> findMateriasCentroByAnyo(@Param("codigoCentro") Long codigoCentro, @Param("anyo") Integer anyo);

    @Query(value = "SELECT mog.X_MATERIAOMG idMateriaOmg, materia.S_MATERIAC ||' - '|| materia.T_ABREV ||' - '|| ofg.t_abreviatura nombreMateria, materia.D_MATERIAC descMateria, ofg.S_OFERTAMATRIG nombreCurso, materia.T_ABREV abrevMateria "
    		+ "  FROM TLCENTROS centros "
    		+ "  INNER JOIN TLOFEMATRCEN omc ON omc.X_CENTRO = centros.X_CENTRO "
    		+ "  INNER JOIN TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG "
    		+ "  INNER JOIN TLMATOFEMATRG mog ON mog.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
    		+ "  INNER JOIN TLCURSOORG cuo ON cuo.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
    		+ "  INNER JOIN TLCURSOMODA curModa ON curModa.X_CURSOMOD = cuo.X_CURSOMOD AND curModa.X_MODALIDAD = ofg.X_MODALIDAD "
    		+ "  INNER JOIN TLMATERIASCURSO materia ON materia.X_MATERIAC = mog.X_MATERIAC AND materia.X_CURSOMOD = curModa.X_CURSOMOD "
    		+ "  INNER JOIN TLMATERIASGEN matg ON matg.X_MATERIAG = materia.X_MATERIAG "
    		+ "  INNER JOIN TLPERIODOSOMC POMC ON POMC.X_OFERTAMATRIC=OMC.X_OFERTAMATRIC "
			+ "  INNER JOIN TLMATOFEMATRCEN matomc ON matomc.X_CENTRO = centros.X_CENTRO AND matomc.X_OFERTAMATRIC = omc.X_OFERTAMATRIC AND matomc.X_MATERIAOMG = mog.X_MATERIAOMG "
    		+ "  WHERE centros.C_CODIGO = :codigoCentro "
    		+ "  AND POMC.C_ANNO <= :anyo "
    		+ "  AND (POMC.C_ANNOPUEDETERMINAR IS NULL OR POMC.C_ANNOPUEDETERMINAR>=:anyo) "
    		+ "  AND EXISTS(SELECT 1 FROM  TLRELCOMPESMAT RCM WHERE RCM.X_MATERIAOMG=MOG.X_MATERIAOMG) "
    		+ "  AND ('-1' = :idCurso OR ofg.X_OFERTAMATRIG = :idCurso) "
    		+ "  ORDER BY mog.N_ORDENPRES", nativeQuery = true)
	List<EvaMateriasCentroProjection> findMateriasCentroByAnyoAndCurso(@Param("codigoCentro") Long codigoCentro, @Param("anyo") Integer anyo, @Param("idCurso") Long idCurso);
    
    @Query(value = "SELECT DISTINCT mog.X_MATERIAOMG idMateriaOmg, materia.S_MATERIAC ||' - '|| materia.T_ABREV ||' - '|| ofg.t_abreviatura nombreMateria,   " +
			" materia.D_MATERIAC descMateria, ofg.S_OFERTAMATRIG nombreCurso, materia.T_ABREV abrevMateria, mog.N_ORDENPRES orden   " +
			" FROM DELPHOS.TLMATOFEMATRG mog   " +
			" INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = mog.X_OFERTAMATRIG   " +
			" INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATERIAOMG = mog.X_MATERIAOMG   " +
			" INNER JOIN DELPHOS.TLMATALU mua ON mua.X_MATRICULA = mma.X_MATRICULA   " +
			" INNER JOIN DELPHOS.TLALUMNOS alu ON alu.X_ALUMNO = mua.X_ALUMNO   " +
			" INNER JOIN DELPHOS.TLCENTROS cen ON cen.X_CENTRO = mua.X_CENTRO   " +
			" INNER JOIN DELPHOS.TLMATERIASCURSO materia ON materia.X_MATERIAC = mog.X_MATERIAC   " +
			" INNER JOIN DELPHOS.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = mua.X_OFERTAMATRIG   " +
			" INNER JOIN DELPHOS.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG   " +
			" INNER JOIN DELPHOS.TLPERIODOSOMC periodo ON periodo.X_OFERTAMATRIC = omc.X_OFERTAMATRIC   " +
			" INNER JOIN DELPHOS.TLCURSOMODA cm on cm.X_CURSOMOD = omg.X_OFERTAMATRIG   " +
			" WHERE omg.L_VIGENTE = 'S' AND mma.X_ADAPTACION IS NOT NULL AND mua.X_OFERTAMATRIG <> mua.X_NIVEADAP   " +
			" AND mua.C_ANNO = :anyo AND cen.C_CODIGO = :codigoCentro AND periodo.C_ANNO <= :anyo   " +
			" AND (periodo.C_ANNOPUEDETERMINAR IS NULL OR periodo.C_ANNOPUEDETERMINAR >= :anyo)   " +
			" AND (exists (SELECT 1 FROM DELPHOS.TLCOMESP ce WHERE ce.X_CICLO = cm.X_ETAPA)   " +
			" OR EXISTS (SELECT 1 FROM DELPHOS.TLCOMESP ce, DELPHOS.TLETAPAS et WHERE et.X_ETAPADEPENDEDE = ce.X_CICLO AND et.X_ETAPA = cm.X_ETAPA))  " +
			" AND ofg.X_OFERTAMATRIG = :idOfertaMatrig   " +
			" AND NOT EXISTS (SELECT PD.*   " +
			"  FROM DELPHOS.EVA_PROGDIDAC PD   " +
			"  WHERE PD.X_MATERIAOMG = mog.X_MATERIAOMG   " +
			"  AND PD.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG   " +
			"  AND PD.X_CENTRO = cen.X_CENTRO   " +
			"  AND PD.NU_ANNO = mua.C_ANNO   " +
			"  AND PD.X_NIVEADAP = mua.X_NIVEADAP)   " +
			" ORDER BY mog.N_ORDENPRES", nativeQuery = true)
    List<EvaMateriasCentroProjection> findMateriasCentroACNEAEByAnyoAndCurso(@Param("codigoCentro") Long codigoCentro, @Param("anyo") Integer anyo, @Param("idOfertaMatrig") Long idOfertaMatrig);
    
    @Query(value = "SELECT momg.X_MATERIAOMG idMateriaOmg, mc1.S_MATERIAC ||' - '|| mc1.T_ABREV ||' - '|| ofg.t_abreviatura nombreMateria,  " +
			" mc1.D_MATERIAC descMateria, ofg.S_OFERTAMATRIG nombreCurso, mc1.T_ABREV abrevMateria  " +
			" from (SELECT mc.x_materiag, matofe.x_grupomat " +
			"    FROM tlmatofematrg matofe, tlmateriascurso mc " +
			"    WHERE matofe.x_materiaomg = :idMateriaOmg " +
			"    AND matofe.x_materiac = mc.x_materiac) generica,   " +
			" tlmateriascurso mc1, TLMATOFEMATRG momg, tlofematrgen ofg   " +
			" where mc1.x_materiag = generica.x_materiag   " +
			" AND momg.X_MATERIAC = mc1.X_MATERIAC  " +
			" AND mc1.C_ANNOHASTA IS NULL  " +
			" and mc1.x_cursomod = :idNivelCurricular  " +
			" and momg.x_grupomat=generica.x_grupomat " +
			" AND ofg.X_OFERTAMATRIG = mc1.X_CURSOMOD", nativeQuery = true)
    EvaMateriasCentroProjection findMateriaACNEAE(@Param("idMateriaOmg") Long idMateriaOmg, @Param("idNivelCurricular") Long idNivelCurricular);
    
    @Query(value = "SELECT mog.X_MATERIAOMG idMateriaOmg, materia.S_MATERIAC ||' - '|| materia.T_ABREV ||' - '|| ofg.t_abreviatura nombreMateria,   " +
			" materia.S_MATERIAC ||'('|| materia.T_ABREV ||')' nivelCurricular , materia.D_MATERIAC descMateria, ofg.S_OFERTAMATRIG nombreCurso, materia.T_ABREV abrevMateria, mog.N_ORDENPRES orden   " +
			" FROM DELPHOS.TLMATOFEMATRG mog " +
			" INNER JOIN DELPHOS.TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = mog.X_OFERTAMATRIG " +
			" INNER JOIN DELPHOS.TLMATERIASCURSO materia ON materia.X_MATERIAC = mog.X_MATERIAC " +
			" WHERE mog.X_MATERIAOMG = :idMateriaOmg", nativeQuery = true)
    EvaMateriasCentroProjection findMateriaById(@Param("idMateriaOmg") Long idMateriaOmg);
    
    @Query(value = "select matofe.x_materiaomg idMateriaOmg, mc.d_materiac nombreMateria " +
			"from  tlmatofematrg matofe, tlmateriascurso mc, tlcursomoda cm  " +
			"where matofe.x_ofertamatrig = :idNivelCurricular  " +
			"and matofe.x_materiac=mc.x_materiac  " +
			"and cm.x_cursomod=matofe.x_ofertamatrig  " +
			"and cm.l_vigente='S'  " +
			"and cm.f_fin_vigen is NULL  " +
			"and exists (select 1 from tlrelcompesmat rcm where  rcm.x_materiaomg=matofe.x_materiaomg)", nativeQuery = true)
    List<EvaMateriasCentroProjection> findMateriasCentroByNivelCurricular(@Param("idNivelCurricular") Long idNivelCurricular);

}
