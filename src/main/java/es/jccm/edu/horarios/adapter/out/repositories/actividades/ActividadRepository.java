package es.jccm.edu.horarios.adapter.out.repositories.actividades;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.horarios.application.domain.actividades.projection.ActividadProjection;
import es.jccm.edu.horarios.application.domain.horarios.Horario;
import es.jccm.edu.horarios.application.domain.horarios.QHorario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ActividadRepository extends AbstractRepository<Horario, Integer, QHorario>{
	
	@Modifying
	@Query(value= "SELECT DISTINCT a.T_ABREVIATURA abreviatura, a.D_ACTIVIDAD descripcion, a.N_ORDEN orden "
			+ "FROM TLPTOTRAEMP p, TLACTIVIDADES a, TLHORARIOSR h, TLEMPLEADOS emple, TLUSUARIOS usu "
			+ "WHERE p.X_EMPLEADO = h.X_EMPLEADO "
			+ "AND h.X_ACTIVIDAD = a.x_actividad "
			+ "AND h.X_EMPLEADO = usu.X_EMPLEADO "
			+ "AND usu.X_EMPLEADO = emple.X_EMPLEADO "
			+ "AND p.X_CENTRO = emple.X_CENTRAS "
			+ "AND usu.USUARIO = :idUsuario "
			+ "AND h.C_ANNO = :anno "
			+ "ORDER BY orden",nativeQuery = true)
	List<ActividadProjection> findAllActividades(@Param("idUsuario") String idUsuario,  @Param("anno") Integer anno);

}
