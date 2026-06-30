package es.jccm.edu.proyectosfct.adapter.out.repositories.convenios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasFct;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.QConveniosProgramasFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;


@Repository
public interface ConveniosProgramasFctRepository extends AbstractRepository<ConveniosProgramasFct, Long, QConveniosProgramasFct> {
	
	List<ConveniosProgramasFct> findAllByConvenioId(Long idConvenio);

	Integer countByConvenioId(Long idConvenio);
	
	 @Query(value = "select c_anno FROM ( "
	 		+ "       select c_anno "
	 		+ "       from tlcursoaca aca, fct_conv_prog prog"
	 		+ "       where prog.id_conv_prog = :idConvProg  "
	 		+ "       and TLF_INTERSECPER(prog.fh_inicio, prog.fh_inicio, aca.F_INICIO, aca.F_FINAL) = 1order by c_anno"
	 		+ "       )"
	 		+ " WHERE rownum = 1 ", nativeQuery = true)
	Integer getAnnoConvenioPrograma(Long idConvProg);
	
	 
	@Query(value = "SELECT fc.X_CENTRO "
			+ " FROM FCT_CONV_PROG fcp,FCT_CONVENIOS fc "
			+ " WHERE fcp.ID_CONVENIO  = fc.ID_CONVENIO "
			+ " AND fcp.ID_CONV_PROG = :idConvProg", nativeQuery = true)
	Long getIdCentroByidConvProg(Long idConvProg);


	@Query(value = " SELECT fcp.* " +
            " FROM FCT_CONV_PROG fcp, TLCURSOACA aca" +
            " WHERE fcp.ID_CONVENIO = :idConvenio " +
            " AND aca.c_anno = :anno" +
            " AND TLF_INTERSECPER(fcp.fh_inicio, fcp.fh_inicio, aca.f_inicio, aca.f_final) = 1 ", nativeQuery = true)
    List<ConveniosProgramasFct> getConveniosAnno(@Param("idConvenio") Long idConvenio, @Param("anno") Integer anno);
	
	@Query(value = " select count(*) from fct_convprog_anexos where id_conv_prog = :idConvProg ", nativeQuery = true)
    Integer getCountAnexos(@Param("idConvProg") Long idConvProg);

	List<ConveniosProgramasFct> findByConvenioIdAndProgramaId(Long idConvenio, Long idPrograma);
	
	@Query(value = " select ROUND(SUM(NU_HORAS),0) from fct_conv_proghoraper WHERE id_conv_prog = :idConvProg ", nativeQuery = true)
    Integer getHorasPrograma(@Param("idConvProg") Long idConvProg);
    
}
