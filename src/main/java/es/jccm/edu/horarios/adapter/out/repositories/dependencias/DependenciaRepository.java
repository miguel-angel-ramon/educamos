package es.jccm.edu.horarios.adapter.out.repositories.dependencias;

import java.util.List;

import es.jccm.edu.horarios.application.domain.dependencias.projection.DependenciaLibreProjection;
import es.jccm.edu.horarios.application.domain.dependencias.projection.TipoDependenciaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.horarios.application.domain.dependencias.projection.DependenciaProjection;
import es.jccm.edu.horarios.application.domain.horarios.Horario;
import es.jccm.edu.horarios.application.domain.horarios.QHorario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface DependenciaRepository extends AbstractRepository<Horario, Integer, QHorario>{
	
	@Modifying
	@Query(value= "SELECT DISTINCT dep.T_DEPENDENCIA abreviatura, dep.D_DEPENDENCIA descripcion "
			+ "FROM TLHORARIOSR hor, TLDEPENDENCIAS dep, TLACTIVIDADES act, TLUSUARIOS usu "
			+ "WHERE hor.X_EMPLEADO = usu.X_EMPLEADO "
			+ "AND usu.USUARIO = :idUsuario "
			+ "AND hor.C_ANNO = :anno "
			+ "AND hor.X_ACTIVIDAD = act.X_ACTIVIDAD "
			+ "AND hor.X_DEPENDENCIA = dep.X_DEPENDENCIA "
			+ "ORDER BY descripcion",nativeQuery = true)
	List<DependenciaProjection> findAllDependencias(@Param("idUsuario") String idUsuario,  @Param("anno") Integer anno);

	@Query(value = "SELECT DISTINCT TRA.X_TRAMO idTramo, DEP.X_DEPENDENCIA idDependencia, TIP.D_TIPDEPEND tipo, DEP.D_DEPENDENCIA dependencia, DEP.N_DIMENSION dimension, "
			+ "DECODE(DEP.L_HABILITADA, 'S', 'true', 'false') capacitada, DELPHOS_SEGEDU.CONVIERTE_HORA(TRA.N_INICIO) inicioTramo, DELPHOS_SEGEDU.CONVIERTE_HORA(TRA.N_FIN) finTramo, "
			+ "TRA.N_INICIO " 
			+ "FROM  TLCENTROS CEN, TLDEPENDENCIAS DEP, TLTRAMOSHOR TRA, TLTIPOSDEP TIP "
			+ "WHERE TRA.X_CENTRO = CEN.X_CENTRO "
			+ "AND DEP.X_CENTRO = CEN.X_CENTRO " 
			+ "AND DEP.X_TIPODEPEND = TIP.X_TIPDEPEND "
			+ "AND CEN.C_CODIGO = :codCentro " 
			+ "AND TRA.C_ANNO = :anno " 
			+ "AND TRA.X_TRAMO NOT IN (SELECT HOR.X_TRAMO FROM TLHORARIOSR HOR WHERE HOR.X_DEPENDENCIA = DEP.X_DEPENDENCIA AND HOR.C_ANNO = TRA.C_ANNO AND HOR.N_DIASEMANA = :diaSemana) "
			+ "ORDER BY TRA.N_INICIO",
			countQuery = "SELECT count(*)" +
				"FROM TLCENTROS CEN, " +
				"TLDEPENDENCIAS DEP, " +
				"TLTRAMOSHOR TRA, " +
				"TLTIPOSDEP TIP " +
				"WHERE  TRA.X_CENTRO = CEN.X_CENTRO " +
				"AND DEP.X_CENTRO = CEN.X_CENTRO " +
				"AND DEP.X_TIPODEPEND = TIP.X_TIPDEPEND " +
				"AND CEN.C_CODIGO = :codCentro " +
				"AND TRA.C_ANNO = :anno " +
				"AND TRA.X_TRAMO NOT IN (SELECT HOR.X_TRAMO FROM TLHORARIOSR HOR WHERE HOR.X_DEPENDENCIA = DEP.X_DEPENDENCIA AND HOR.C_ANNO = TRA.C_ANNO AND HOR.N_DIASEMANA = :diaSemana)",
			nativeQuery = true)
	Page<DependenciaLibreProjection> findDependenciasLibresByCentro(@Param("codCentro") Long codCentro, @Param("anno") Integer anno, @Param("diaSemana") Integer diaSemana, Pageable pageable);

	@Query(value = "SELECT DISTINCT TDE.S_TIPDEPEND nombre " +
			"FROM TLTIPOSDEP TDE, " +
			"TLDEPENDENCIAS DEP, " +
			"TLCENTROS CEN " +
			"WHERE TDE.X_TIPDEPEND = DEP.X_TIPODEPEND " +
			"AND DEP.X_CENTRO = CEN.X_CENTRO " +
			"AND CEN.C_CODIGO = :codCentro " +
			"ORDER BY TDE.S_TIPDEPEND", nativeQuery = true)
	List<TipoDependenciaProjection> findTiposDependenciaByCentro(@Param("codCentro") Long codCentro);

}
