package es.jccm.edu.horarios.adapter.out.repositories.materias;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.horarios.application.domain.horarios.Horario;
import es.jccm.edu.horarios.application.domain.horarios.QHorario;
import es.jccm.edu.horarios.application.domain.materias.projection.MateriaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface MateriaRepository extends AbstractRepository<Horario, Integer, QHorario>{
	
	@Modifying
	@Query(value= "SELECT DISTINCT a.t_ABREV abreviatura, a.S_MATERIAC descripcion "
			+ "FROM TLUNIAFETRAHOR uat, TLHORARIOSR hor, TLUSUARIOS usu, ("
			+ "     SELECT mog.X_MATERIAOMG, mac.T_ABREV, mac.S_MATERIAC "
			+ "     FROM TLMATOFEMATRG mog, TLMATERIASCURSO mac"
			+ "     WHERE mog.X_MATERIAC = mac.X_MATERIAC) a "
			+ "WHERE hor.X_EMPLEADO = usu.X_EMPLEADO "
			+ "AND usu.USUARIO = :idUsuario "
			+ "AND hor.C_ANNO = :anno "
			+ "AND uat.X_HORARIORE = hor.X_HORARIORE "
			+ "AND uat.X_MATERIAOMG = a.X_MATERIAOMG "
			+ "UNION "
			+ "SELECT DISTINCT a.T_ABREV abreviatura, (a.S_MATERIAC || omgmomg.T_ABREVIATURA) descripcion "
			+ "FROM TLUNIAFETRAHOR uat, TLHORARIOSR hor, TLOFEMATRCEN omc, TLOFEMATRGEN omg, TLOFEMATRGEN omgmomg, TLUSUARIOS usu, ("
			+ "     SELECT mog.X_MATERIAOMG, mac.T_ABREV, mac.S_MATERIAC, mog.X_OFERTAMATRIG "
			+ "     FROM TLMATOFEMATRG mog, TLMATERIASCURSO mac "
			+ "     WHERE mog.X_MATERIAC = mac.X_MATERIAC) a "
			+ "WHERE hor.X_EMPLEADO = usu.X_EMPLEADO "
			+ "AND usu.USUARIO = :idUsuario "
			+ "AND hor.C_ANNO = :anno "
			+ "AND uat.X_HORARIORE = hor.X_HORARIORE "
			+ "AND uat.X_MATERIAOMG = a.X_MATERIAOMG "
			+ "AND uat.X_OFERTAMATRIC = omc.X_OFERTAMATRIC "
			+ "AND omc.X_OFERTAMATRIG = omg.X_OFERTAMATRIG "
			+ "AND a.X_OFERTAMATRIG <> omg.X_OFERTAMATRIG "
			+ "AND omgmomg.X_OFERTAMATRIG = a.X_OFERTAMATRIG "
			+ "ORDER BY descripcion",nativeQuery = true)
	List<MateriaProjection> findAllMaterias(@Param("idUsuario") String idUsuario,  @Param("anno") Integer anno);

}
