package es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.modulosPFE;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ModulosProgPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.QModulosProgPFE;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface ModulosFPERepository extends AbstractRepository<ModulosProgPFE, Long, QModulosProgPFE>  {

	
	 @Query(value = "SELECT DISTINCT       "
	 		+ "           momg.x_materiaomg AS id,      "
	 		+ "           momg.c_codigomec || ' - ' || mcur.d_materiac AS descripcion      "
	 		+ "       FROM tlofematrgen omg, tlofematrcen omc, tlmatofematrg momg, tlmateriascurso mcur,      "
	 		+ "            tlmatofematrcen momc, tlperiodosomc per      "
	 		+ "       WHERE omg.x_ofertamatrig = :idCurso      "
	 		+ "       AND omc.x_ofertamatrig = omg.x_ofertamatrig      "
	 		+ "       AND omc.x_centro = :idCentro      "
	 		+ "       AND :cAnno BETWEEN omg.c_anno AND NVL(omg.c_annotermina, 2099)      "
	 		+ "       AND momg.x_materiac = mcur.x_materiac      "
	 		+ "       AND momg.x_materiaomg = momc.x_materiaomg      "
	 		+ "       AND momc.x_ofertamatric = omc.x_ofertamatric      "
	 		+ "       AND per.x_ofertamatric = omc.x_ofertamatric      "
	 		+ "       AND :cAnno BETWEEN per.c_anno AND NVL(per.c_annopuedeterminar, 2099)      "
	 		//+ "       AND EXISTS (      "
	 		//+ "           SELECT 1      "
	 		//+ "           FROM tlunicommat unm, tlunicom uni, tlcatcuaprof cua, tlcuaprofunicom cpuc, tlnivelcp niv, tlfamilias fam      "
	 		//+ "           WHERE unm.x_unicom = uni.x_unicom      "
	 		//+ "           AND uni.x_unicom = cpuc.x_unicom      "
	 		//+ "           AND cpuc.x_cualificacion = cua.x_cualificacion      "
	 		//+ "           AND cua.x_nivelcp = niv.x_nivelcp      "
	 		//+ "           AND cua.x_familia = fam.x_familia      "
	 		//+ "           AND unm.x_materiaomg = momg.x_materiaomg      "
	 		//+ "       )  "
	 		+ "      AND NOT EXISTS ( SELECT 1  "
	 		+ "                       FROM  FCT_MODAUTPROG mod "
	 		+ "                       where mod.id_progperfor = :idProgramaFPE "
	 		+ "                       and mod.x_materiaomg = momg.x_materiaomg)        "
	 		+ "       order by descripcion ", nativeQuery = true)
	List<ElementoSelectProjection> getModulosDisponibles(Long idProgramaFPE, Long idCurso, Long idCentro, Integer cAnno);
	 
	 @Query(value = "select DISTINCT       "
	 		+ "           momg.x_materiaomg AS id,      "
	 		+ "           momg.c_codigomec || ' - ' || mcur.d_materiac AS descripcion   "
	 		+ "   from FCT_MODAUTPROG map,  "
	 		+ "        tlmatofematrg momg,   "
	 		+ "        tlmateriascurso mcur       "
	 		+ "   where map.id_progperfor = :idProgramaFPE "
	 		+ "   AND map.x_materiaomg = momg.x_materiaomg "
	 		+ "   AND momg.x_materiac = mcur.x_materiac "
	 		+ "   order by descripcion ", nativeQuery = true) 
	List<ElementoSelectProjection> getModulosSeleccionados(Long idProgramaFPE);

	List<ModulosProgPFE> findByIdProgramaFPE(Long idProgramaFPE);
	
	

}
