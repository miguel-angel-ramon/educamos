package es.jccm.edu.alumnos.adapter.out.repository.programas;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.alumnos.application.domain.programas.PMatricula;
import es.jccm.edu.alumnos.application.domain.programas.QPMatricula;
import es.jccm.edu.alumnos.application.domain.programas.projection.AlumnoProgProjection;
import es.jccm.edu.alumnos.application.domain.programas.projection.AlumnoProgProjection;
import es.jccm.edu.alumnos.application.domain.programas.projection.MateriaAsociadaProjection;
import es.jccm.edu.alumnos.application.domain.programas.projection.MateriaProgramaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface MateriasProgRepository extends AbstractRepository<PMatricula, Long, QPMatricula> {

	@Query(value = "select t_abrev abreviatura, x_materiaomc idMateria, programa , x_matmatricula idMateriaMatricula, orden "
			+ "  from (  " + "  select distinct mc.T_ABREV, mprog.X_MATERIAOMC, "
			+ "       case when ((mma.x_programa = mprog.x_programa) or (select count(*)  "
			+ "            from tlmmaprog mp where mp.x_matmatricula= mma.x_matmatricula"
			+ "            and mp.x_programa=mprog.x_programa)>0) then 'S' "
			+ "          when ((mma.x_programa != mprog.x_programa)  and "
			+ "            ((select tpp.l_idioma from tltipoprograma tpp, tlprogramas pr "
			+ "               where tpp.x_tipoprograma=pr.x_tipoprograma "
			+ "               and pr.x_programa=mprog.x_programa)='S'))  then 'N'    else '' END PROGRAMA, "
			+ "       mma.x_matmatricula, " + "       1 orden "
			+ "    from tlmatmatrialu mma,  tlmatalu ma , tlmatomcprog mprog,  tlmatofematrg momg,  tlmateriascurso mc "
			+ "   where mma.x_matricula = ma.x_matricula     and mma.x_materiaomc = mprog.x_materiaomc "
			+ "     and momg.X_MATERIAOMG = mma.X_MATERIAOMG     and momg.X_MATERIAC = mc.X_MATERIAC "
			+ "     and momg.X_OFERTAMATRIG = ma.X_OFERTAMATRIG     and ma.x_matricula = :idMatricula "
			+ "     and (mprog.x_programa = :idPrograma) "
			+ "     and ma.c_anno between mprog.C_ANNO and nvl(mprog.C_ANNOHASTA,9999)      union   "
			+ "  select distinct mc.T_ABREV||' ('||omg.t_abreviatura||')' T_ABREV  , mprog.X_MATERIAOMC,      "
			+ "       case when ((mma.x_programa = mprog.x_programa) or (select count(*)"
			+ "            from tlmmaprog mp where mp.x_matmatricula= mma.x_matmatricula"
			+ "            and mp.x_programa=mprog.x_programa)>0) then 'S' "
			+ "          when ((mma.x_programa != mprog.x_programa) and "
			+ "            ((select tpp.l_idioma from tltipoprograma tpp, tlprogramas pr  "
			+ "               where tpp.x_tipoprograma=pr.x_tipoprograma "
			+ "               and pr.x_programa=mprog.x_programa)='S')) then 'N'    else '' END PROGRAMA,  "
			+ "       mma.x_matmatricula,     2 orden  "
			+ "    from tlmatmatrialu mma, tlmatalu ma , tlmatomcprog mprog, tlmatofematrg momg,  tlmateriascurso mc,  tlmatofematrcen momc, tlofematrgen omg      "
			+ "   where mma.x_matricula = ma.x_matricula    and momg.X_MATERIAOMG = mma.X_MATERIAOMG"
			+ "     and momg.X_MATERIAC = mc.X_MATERIAC     and momg.X_OFERTAMATRIG <> ma.X_OFERTAMATRIG "
			+ "     and momg.x_ofertamatrig = omg.x_ofertamatrig     and ma.x_matricula =:idMatricula "
			+ "     and mma.x_materiaomg = momc.x_materiaomg     and momc.x_centro = ma.x_centro "
			+ "     and momc.x_materiaomc = mprog.x_materiaomc"
			+ "      AND ( (mprog.x_programa = :idPrograma AND momc.x_ofertamatric = :idOfertaMatriculacion) OR (momc.x_ofertamatric <> :idOfertaMatriculacion AND mprog.c_anno < :annio))  "
			+ "     and (ma.c_anno - 1)  between mprog.C_ANNO and nvl(mprog.C_ANNOHASTA,9999)) "
			+ "    order by orden, T_ABREV  ", nativeQuery = true)
	List<MateriaProgramaProjection> findMateriasPrograma(@Param("idPrograma") Long idPrograma,
			@Param("idMatricula") Long idMatricula, @Param("idOfertaMatriculacion") Long ofertaMatriculacion,
			@Param("annio") Integer annio);

	@Query(value =  " select case when momc.x_ofertamatric = :idOfertaMatriculacion then mc.t_abrev"   +
			"         else mc.T_ABREV||' ('||omg.t_abreviatura||')' end abreviatura,"   +
			"      X_MATERIAG idMateria, mprog.x_programa  idPrograma, "   +
			"      case when momc.x_ofertamatric = :idOfertaMatriculacion then 1"   +
			"         else 2 end orden"   +
			"   from tlmatomcprog mprog,"   +
			"      tlmatofematrg momg,"   +
			"      tlmateriascurso mc, "   +
			"      tlmatofematrcen momc,"   +
			"      tlofematrgen omg, "   +
			"      tlofematrcen omc"   +
			"  where momc.x_centro = :idCentro "   +
			"    and momc.x_materiaomc = mprog.x_materiaomc"   +
			"    and momc.x_materiaomg = momg.x_materiaomg"   +
			"    and momg.x_ofertamatrig = omg.x_ofertamatrig"   +
			"    and omg.x_ofertamatrig = omc.x_ofertamatrig"   +
			"    and omc.x_centro = momc.x_centro"   +
			"    and momg.x_materiac = mc.x_materiac"   +
			"    AND ((mprog.x_programa = :idPrograma AND momc.x_ofertamatric = :idOfertaMatriculacion) OR (momc.x_ofertamatric <> :idOfertaMatriculacion AND mprog.c_anno < :annio AND mprog.x_programa = :idPrograma)) "   +
			"  and ((momc.x_ofertamatric = :idOfertaMatriculacion and (:annio between mprog.C_ANNO and nvl(mprog.C_ANNOHASTA,9999))) " +
			"     or (momc.x_ofertamatric <> :idOfertaMatriculacion) and ((:annio-1) between mprog.C_ANNO and nvl(mprog.C_ANNOHASTA,9999))) " +
			"    and exists (select 1 from tlmatmatrialu mma, tlmatalu ma"   +
			"               where ma.x_centro = momc.x_centro"   +
			"           and ma.c_anno = :annio"   +
			"           and nvl(ma.c_resultado, 2) > 1"   +
			"           and ma.x_ofertamatric = :idOfertaMatriculacion "   +
			"           and ma.x_matricula = mma.x_matricula "   +
			"           and mma.x_materiaomg = momg.x_materiaomg) "   +
			"    union "   + 
			"   select 'ASIGNADO' t_abrev, "   +
			"       -1 X_MATERIAG, PC.x_programa X_PROGRAMA, "   +
			"        1 orden from tlprogcen pc where pc.x_centro=:idCentro and pc.x_programa=:idPrograma "   +
			"      and pc.x_programa in (select p.x_programa from tlprogramas p where p.l_materia='N' and p.x_programa=pc.x_programa) "   +
			"    order by orden,abreviatura " ,nativeQuery=true)
	List<MateriaAsociadaProjection> findMateriasAsociadas(@Param("idPrograma") Long idPrograma,
			@Param("idCentro") Long idCentro, @Param("idOfertaMatriculacion") Long idOfertaMatriculacion,
			@Param("annio") int annio);
	
}


