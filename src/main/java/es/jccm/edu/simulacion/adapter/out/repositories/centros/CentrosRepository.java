package es.jccm.edu.simulacion.adapter.out.repositories.centros;

import java.util.List;

import es.jccm.edu.simulacion.application.domain.centros.projection.DepartamentoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.QCentro;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import es.jccm.edu.simulacion.application.domain.centros.projection.CentroProjection;

@Repository
public interface CentrosRepository extends AbstractRepository<Centro, Long, QCentro>{
	
	@Query(value= "SELECT CEN.X_CENTRO idCentro, CEN.C_CODIGO codCentro, TCE.D_TIPO tipo, DGE.S_DENOMINACION denominacion, DCE.D_ESPECIFICA nombre "
			+ "FROM DELPHOS_SEGEDU.TLCENTROS CEN, DELPHOS_SEGEDU.TLTIPOCEN TCE, DELPHOS_SEGEDU.TLDATOSCEN DCE, DELPHOS_SEGEDU.TLPROVINCIAS PRO, "
			+ "DELPHOS_SEGEDU.TLDENGEN DGE, DELPHOS_SEGEDU.TLMUNICIPIOS MUN "
			+ "WHERE CEN.X_TIPO = TCE.X_TIPO "
			+ "AND CEN.X_CENTRO = DCE.X_CENTRO "
			+ "AND DCE.C_PROVINCIA = MUN.C_PROVINCIA "
			+ "AND DCE.C_MUNICIPIO = MUN.C_MUNICIPIO "
			+ "AND MUN.C_PROVINCIA = PRO.C_PROVINCIA "
			+ "AND DCE.X_DENGEN = DGE.X_DENGEN "
			+ "AND DCE.L_VIGENTE = 'S' "
			+ "AND CEN.C_CODIGO = :codCentro",nativeQuery = true)
	CentroProjection findByCodCentro(@Param("codCentro") Long codCentro);
	
	@Query(value= "SELECT CEN.X_CENTRO idCentro, CEN.C_CODIGO codCentro, TCE.D_TIPO tipo, DGE.S_DENOMINACION denominacion, DCE.D_ESPECIFICA nombre "
			+ "FROM DELPHOS_SEGEDU.TLCENTROS CEN, DELPHOS_SEGEDU.TLTIPOCEN TCE, DELPHOS_SEGEDU.TLDATOSCEN DCE, DELPHOS_SEGEDU.TLPROVINCIAS PRO, "
			+ "DELPHOS_SEGEDU.TLDENGEN DGE, DELPHOS_SEGEDU.TLMUNICIPIOS MUN "
			+ "WHERE CEN.X_TIPO = TCE.X_TIPO "
			+ "AND CEN.X_CENTRO = DCE.X_CENTRO "
			+ "AND DCE.C_PROVINCIA = MUN.C_PROVINCIA "
			+ "AND DCE.C_MUNICIPIO = MUN.C_MUNICIPIO "
			+ "AND MUN.C_PROVINCIA = PRO.C_PROVINCIA "
			+ "AND DCE.X_DENGEN = DGE.X_DENGEN "
			+ "AND DCE.L_VIGENTE = 'S' "
			+ "AND CEN.X_CENTRO = :idCentro",nativeQuery = true)
	CentroProjection findByIdCentro(@Param("idCentro") Long idCentro);
	
	@Query(value= "SELECT CEN.X_CENTRO idCentro, CEN.C_CODIGO codCentro, TCE.D_TIPO tipo, DGE.S_DENOMINACION denominacion, DCE.D_ESPECIFICA nombre "
			+ "FROM DELPHOS_SEGEDU.TLCENTROS CEN, DELPHOS_SEGEDU.TLTIPOCEN TCE, DELPHOS_SEGEDU.TLDATOSCEN DCE, DELPHOS_SEGEDU.TLPROVINCIAS PRO, "
			+ "DELPHOS_SEGEDU.TLDENGEN DGE, DELPHOS_SEGEDU.TLMUNICIPIOS MUN, DELPHOS_SEGEDU.TLCUESTIONARIO CUES, DELPHOS_SEGEDU.TLCUEPUB CUEPUB, "
			+ "DELPHOS_SEGEDU.TLCUEPUBUSU CUEPUBUSU "
			+ "WHERE CEN.X_TIPO = TCE.X_TIPO "
			+ "AND CEN.X_CENTRO = DCE.X_CENTRO "
			+ "AND DCE.C_PROVINCIA = MUN.C_PROVINCIA "
			+ "AND DCE.C_MUNICIPIO = MUN.C_MUNICIPIO "
			+ "AND MUN.C_PROVINCIA = PRO.C_PROVINCIA "
			+ "AND DCE.X_DENGEN = DGE.X_DENGEN "
			+ "AND DCE.L_VIGENTE = 'S' "
			+ "AND CEN.X_CENTRO = CUEPUBUSU.X_CENTRO "
			+ "AND CUEPUBUSU.X_USUARIO = :xUsuarioComunica "
			+ "AND CUEPUBUSU.X_CUEPUB = CUEPUB.X_CUEPUB "
			+ "AND CUEPUB.X_CUESTIONARIO = CUES.X_CUESTIONARIO "
			+ "AND SYSDATE BETWEEN CUEPUB.F_INI_RESPUESTAS AND CUEPUB.F_FIN_RESPUESTAS "
			+ "AND CUES.C_CODIGO = :codCuestionario",nativeQuery = true)
	List<CentroProjection> getListadoCentros(@Param("xUsuarioComunica") Long xUsuarioComunica, @Param("codCuestionario") String codCuestionario);


	@Query(value= "SELECT DISTINCT CENTROS.X_CENTRO as idCentro , CENTROS.C_CODIGO AS codCentro,   " +
			" DEN.S_DENOMINACION AS denominacion,  DT.D_ESPECIFICA nombre " +
			" FROM DELPHOS.TLINSPECTORESCEN INSPECENTRO   " +
			" INNER JOIN DELPHOS_SEGEDU.TLCENTROS CENTROS ON CENTROS.X_CENTRO = INSPECENTRO.X_CENTRO  " +
			" INNER JOIN DELPHOS.TLDATOSCEN DT ON DT.X_CENTRO = CENTROS.X_CENTRO AND DT.L_VIGENTE = 'S'  " +
			" INNER JOIN DELPHOS.TLDATOSCEN DATOSCEN ON DATOSCEN.X_CENTRO = INSPECENTRO.X_CENTRO   " +
			" INNER JOIN DELPHOS.TLDENGEN DEN ON DEN.X_DENGEN = DATOSCEN.X_DENGEN  " +
			" WHERE INSPECENTRO.X_EMPLEADO = :idEmpleado   " +
			" ORDER BY CENTROS.X_CENTRO ASC",nativeQuery = true)
	List<CentroProjection> getInspectorCentros(@Param("idEmpleado") Long idEmpleado);

	@Query(value = "SELECT DISTINCT dpc.X_DEPARTCEN idDepartamento, gen.D_DEPARTAMENTO  nombreDepartamento " +
			"FROM DELPHOS.TLDEPDENGEN gen " +
			"INNER JOIN delphos.TLDEPARTCEN dpc ON gen.X_DEPDENGEN  = dpc.X_DEPDENGEN  " +
			"INNER JOIN delphos.TLMIEDEPART mdp ON mdp.X_DEPARTCEN = dpc.X_DEPARTCEN " +
			"INNER JOIN delphos.TLCURSOACA cua ON cua.C_ANNO = dpc.C_ANNO " +
			"WHERE dpc.X_CENTRO = :idCentro " +
			"AND dpc.C_ANNO = :anyo", nativeQuery = true)
	public List<DepartamentoProjection> getDepartamentosCentro(@Param("idCentro") Long idCentro,
															   @Param("anyo") Long anyo);


}
