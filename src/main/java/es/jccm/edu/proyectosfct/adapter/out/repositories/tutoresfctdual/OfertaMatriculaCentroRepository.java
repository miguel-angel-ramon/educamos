package es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.OfertaMatriculaCentro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.OfertaMatriculaProjection;

@Repository
public interface OfertaMatriculaCentroRepository extends JpaRepository<OfertaMatriculaCentro, Long> {
	
	// Usamos una projection para poder usar native query y no tener que mapear todas las entities relacionadas
	// mediante inner join
	
	@Query(value = "SELECT OMC.X_OFERTAMATRIC as Id, OMG.D_OFERTAMATRIG as Descripcion FROM " + 
			"TLOFEMATRCEN OMC " + 
			"INNER JOIN TLOFEMATRGEN OMG ON OMG.X_OFERTAMATRIG = OMC.X_OFERTAMATRIG " + 
			"WHERE OMC.X_CENTRO = 2879 " + 
			"AND OMG.C_ANNO = 2015 " ,nativeQuery = true)
	List<OfertaMatriculaProjection> getOfertasByCentro(Long idCentro, Integer anno);

}
