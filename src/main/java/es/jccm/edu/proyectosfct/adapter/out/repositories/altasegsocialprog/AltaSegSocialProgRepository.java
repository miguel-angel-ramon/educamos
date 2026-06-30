package es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialprog;

import com.querydsl.jpa.impl.JPAQuery;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection.ListadoHistoricoAltasProjection;
import es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities.AltasSegSociProg;
import es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities.QAltasSegSociProg;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AltaSegSocialProgRepository extends AbstractRepository<AltasSegSociProg, Long, QAltasSegSociProg> {

    List<AltasSegSociProg> findByIdConvProgAlu(Long idConvProgAlu);

	@Query(value = "select * from fct_convprog_alu prog, fct_altass_prog alta where prog.x_matricula = alta.x_matricula and id_conv_prog = :idConvProg", nativeQuery = true)
	List<AltasSegSociProg> findByIdConvProg(Long idConvProg);
    
    @Query(value = "select distinct alu.T_APELLIDO1 || ' ' || alu.T_APELLIDO2 || ',' || alu.T_NOMBRE as nombreCompleto, "
    		+ "       alp.F_INICIO as fechaInicio, "
    		+ "       alp.F_FIN as fechaFin, "
    		+ "       DECODE(alp.LG_ERASMUS_CB, 1, 'SI', 'NO') as erasmusCb, "
    		+ "       DECODE(alp.LG_ERASMUS_SB, 1, 'SI', 'NO') as erasmusSb, "
    		+ "       DECODE(alp.LG_ANULADO, 1, 'SI', 'NO') as anulado, "
    		+ "       TO_CHAR(alp.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio,        "
    		+ "       LPAD((SELECT COUNT(nu_peticion) +1 "
    		+ "             FROM fct_historicoaltas_prog "
    		+ "             WHERE id_altass_prog = alp.id_altass_prog),2,'0')  as nuPeticion,         "
    		+ "       CASE  "
    		+ "            WHEN hag.id_historicoaltas_prog is null then 'Alta' "
    		+ "            WHEN hag.id_historicoaltas_prog is not null AND alp.lg_anulado = 1 then 'Anulado' "
    		+ "            ELSE 'Modificacion' "
    		+ "       END as accion, "
			+ "       alp.ds_warnings as dsWarnings "
    		+ " from fct_altass_prog alp, tlmatalu mat, tlalumnos alu, fct_historicoaltas_prog hag "
    		+ " where mat.x_matricula = alp.x_matricula "
    		+ " and alu.x_alumno = mat.x_alumno "
    		+ " and alp.id_altass_prog = hag.id_altass_prog(+) "
    		+ " and alp.id_altass_prog = :idAltass "
    		+ " union all "
    		+ " select distinct alp.nombre as nombreCompleto, "
    		+ "       hag.F_INICIO as fechaInicio, "
    		+ "       hag.F_FIN as fechaFin, "
    		+ "       DECODE(hag.LG_ERASMUS_CB, 1, 'SI', 'NO') as erasmusCb, "
    		+ "       DECODE(hag.LG_ERASMUS_SB, 1, 'SI', 'NO') as erasmusSb, "
    		+ "       DECODE(hag.LG_ANULADO, 1, 'SI', 'NO') as anulado, "
    		+ "       TO_CHAR(hag.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio, "
    		+ "       LPAD(hag.nu_peticion,2,'0') as nuPeticion, "
    		+ "       CASE  "
    		+ "            WHEN hag.nu_peticion = 1 then 'Alta' "
    		+ "            WHEN hag.lg_anulado = 1 then 'Anulado' "
    		+ "            ELSE 'Modificacion' "
    		+ "       END as accion,    "
			+ "       hag.ds_warnings as dsWarnings "
    		+ " from fct_historicoaltas_prog hag, "
    		+ "     ( SELECT alu.T_APELLIDO1 || ' ' || alu.T_APELLIDO2 || ',' || alu.T_NOMBRE as nombre "
    		+ "       FROM fct_altass_prog alp, tlmatalu mat, tlalumnos alu  "
    		+ "       WHERE mat.x_matricula = alp.x_matricula "
    		+ "       AND alu.x_alumno = mat.x_alumno "
    		+ "       AND alp.id_altass_prog = :idAltass "
    		+ "     ) alp "
    		+ " where hag.id_altass_prog =  :idAltass "
    		+ " order by nuPeticion desc ", nativeQuery = true)
    List<ListadoHistoricoAltasProjection> getListadoHistoricoAltas(Long idAltass);

    
    @Query(value = "select count(*)  "
    		+ "from fct_altass_prog apr "
    		+ "where apr.id_altass_prog = :id "
    		+ "and TRUNC(apr.f_inicio) = TRUNC(:fechaInicio)  "
    		+ "and TRUNC(apr.f_fin) =  TRUNC(:fechaFin) ", nativeQuery = true)
	int getConsistenciaGestora(Long id, Date fechaInicio, Date fechaFin);

    @Query(value = "select SUM(dupli) FROM ( "
    		+ "select count(*) dupli from fct_altass_prog prog "
    		+ "where prog.id_altass_prog = :idConv "
    		+ "and prog.lg_erasmus_cb = :lgBeca "
    		+ "and exists (select 1 from fct_altass_prog prog1  "
    		+ "            where prog1.x_matricula = prog.x_matricula  "
    		+ "            and prog1.id_altass_prog != prog.id_altass_prog  "
    		+ "            and prog1.lg_erasmus_cb = prog.lg_erasmus_cb  "
    		+ "            and prog1.f_envioss is not null) "
    		+ "UNION "
    		+ "select count(*) dupli from fct_altass_proy proy "
    		+ "where proy.id_altass_proy = :idConv "
    		+ "and proy.lg_erasmus_cb = :lgBeca "
    		+ "and exists (select 1 from fct_altass_proy proy1  "
    		+ "            where proy1.x_matricula = proy.x_matricula  "
    		+ "            and proy1.id_altass_proy != proy.id_altass_proy  "
    		+ "            and proy1.lg_erasmus_cb = proy.lg_erasmus_cb  "
    		+ "            and proy1.f_envioss is not null))  ", nativeQuery = true)
	Integer registerEnviado(Long idConv, Integer lgBeca);

    @Query(value = "select * from fct_altass_prog where x_matricula = :idMatricula  ", nativeQuery = true)
	List<AltasSegSociProg> getListForMatricula(Long idMatricula);

	/*@Query(value = " select MAX(maximo) + 1 from ( " +
			" select MAX(id_interno_alta) maximo from fct_altass_prog" +
			" UNION ALL " +
			" select MAX(id_interno_alta) maximo from fct_altass_proy) ", nativeQuery = true) */
    @Query(value = " select SQ_FCT_IDINTERNOSS.nextval from dual ", nativeQuery = true)
	Long generateWorkerIdExt();

    @Query(value="select max(f_fin) from fct_altass_prog where id_convprog_alu = :idConvProgAlu and x_matricula = :idMatricula and f_envioss is not null ", nativeQuery = true)
	Date findFechaFinAltaSS(Long idConvProgAlu,Long idMatricula);

}
