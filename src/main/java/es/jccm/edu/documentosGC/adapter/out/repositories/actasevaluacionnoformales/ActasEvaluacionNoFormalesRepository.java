package es.jccm.edu.documentosGC.adapter.out.repositories.actasevaluacionnoformales;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.actaevaluacionesnoformales.projection.DirectorTutorProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.QDocumentosGC;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface ActasEvaluacionNoFormalesRepository extends AbstractRepository<DocumentosGC, Long, QDocumentosGC> {

	@Query(value =" SELECT "
			+ "    NVL((select x_empleado from ( "
			+ "         select pto.x_empleado    "
			+ "         from tlcargosemp ce, tlptotraemp pto, tlconvcenomc comc , tlconvcentros cc "
			+ "         where pto.x_centro = cc.x_centro  "
			+ "         and ce.x_empleado = pto.x_empleado  "
			+ "         and ce.f_tomapos = pto.f_tomapos   "
			+ "         and ce.c_cargo in ('XDI','XDP')  "
			+ "         and comc.x_convcentroomc = :idConvOmc "
			+ "         and comc.x_convcentro = cc.x_convcentro "
			+ "         and 1 = tlf_intersecper(ce.f_tompos, ce.f_cese, nvl(f_feciniconomc, f_fecinicon), nvl(f_fecfinconomc, f_fecfincon)) "
			+ "         and 1 = tlf_intersecper(pto.f_tomaposrea, pto.f_cese, nvl(f_feciniconomc, f_fecinicon), nvl(f_fecfinconomc, f_fecfincon)) "
			+ "         order by ce.f_tompos desc) "
			+ "         where rownum = 1),-1) AS idDirector, "
			+ "   NVL((SELECT X_EMPLEADO FROM TLUNIDADESCEN where x_unidad = :idUnidad),-1) AS idTutor "
			+ "FROM DUAL ", nativeQuery = true)
	DirectorTutorProjection getDirectorTutor(Long idConvOmc, Long idUnidad);

	@Query(value =" select x_materiaomg  "
			+ "     from tlmatofematrg "
			+ "     where x_materiac = :idMateriac "
			+ "     and x_ofertamatrig = :idOfertamatrig ", nativeQuery = true)
	Long getMateriasCurso(Long idMateriac, Long idOfertamatrig);
	
	

}
