package es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialproy;

import es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection.ListadoHistoricoAltasProjection;
import es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities.AltasSegSociProy;
import es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities.QAltasSegSociProy;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AltaSegSocialProyRepository extends AbstractRepository<AltasSegSociProy, Long, QAltasSegSociProy> {

    List<AltasSegSociProy> findByIdConvProyAlu(Long idConvProyAlu);

    @Query(value = "select distinct alu.T_APELLIDO1 || ' ' || alu.T_APELLIDO2 || ',' || alu.T_NOMBRE as nombreCompleto, "
    		+ "       alp.F_INICIO as fechaInicio, "
    		+ "       alp.F_FIN as fechaFin, "
    		+ "       DECODE(alp.LG_ERASMUS_CB, 1, 'SI', 'NO') as erasmusCb, "
    		+ "       DECODE(alp.LG_ERASMUS_SB, 1, 'SI', 'NO') as erasmusSb, "
    		+ "       DECODE(alp.LG_ANULADO, 1, 'SI', 'NO') as anulado, "
    		+ "       TO_CHAR(alp.F_ENVIOSS, 'DD/MM/YYYY') as fechaEnvio,        "
    		+ "       LPAD((SELECT COUNT(nu_peticion) +1 "
    		+ "             FROM fct_historicoaltas_proy "
    		+ "             WHERE id_altass_proy = alp.id_altass_proy),2,'0')  as nuPeticion,         "
    		+ "       CASE  "
    		+ "            WHEN hag.id_historicoaltas_proy is null then 'Alta' "
    		+ "            WHEN hag.id_historicoaltas_proy is not null AND alp.lg_anulado = 1 then 'Anulado' "
    		+ "            ELSE 'Modificacion' "
    		+ "       END as accion, "
			+ "       alp.ds_warnings as dsWarnings "
    		+ " from fct_altass_proy alp, tlmatalu mat, tlalumnos alu, fct_historicoaltas_proy hag "
    		+ " where mat.x_matricula = alp.x_matricula "
    		+ " and alu.x_alumno = mat.x_alumno "
    		+ " and alp.id_altass_proy = hag.id_altass_proy(+) "
    		+ " and alp.id_altass_proy = :idAltass "
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
    		+ "       END as accion,        "
			+ "       hag.ds_warnings as dsWarnings "
    		+ " from fct_historicoaltas_proy hag, "
    		+ "     ( SELECT alu.T_APELLIDO1 || ' ' || alu.T_APELLIDO2 || ',' || alu.T_NOMBRE as nombre "
    		+ "       FROM fct_altass_proy alp, tlmatalu mat, tlalumnos alu  "
    		+ "       WHERE mat.x_matricula = alp.x_matricula "
    		+ "       AND alu.x_alumno = mat.x_alumno "
    		+ "       AND alp.id_altass_proy = :idAltass "
    		+ "     ) alp "
    		+ " where hag.id_altass_proy =  :idAltass "
    		+ " order by nuPeticion desc ", nativeQuery = true)
    List<ListadoHistoricoAltasProjection> getListadoHistoricoAltas(Long idAltass);

    @Query(value = "select count(*)  "
    		+ "from fct_altass_proy apr "
    		+ "where apr.id_altass_proy = :id "
    		+ "and TRUNC(apr.f_inicio) = TRUNC(:fechaInicio)  "
    		+ "and TRUNC(apr.f_fin) =  TRUNC(:fechaFin) ", nativeQuery = true)
	int getConsistenciaGestora(Long id, Date fechaInicio, Date fechaFin);

    @Query(value = "select * from fct_altass_proy where x_matricula = :idMatricula  ", nativeQuery = true)
	List<AltasSegSociProy> getListForMatricula(Long idMatricula);    

	 @Query(value="select max(f_fin) from fct_altass_proy where id_convproy_alu = :idConvProyAlu and x_matricula = :idMatricula and f_envioss is not null ", nativeQuery = true)
	Date findFechaFinAltaSSDual(Long idConvProyAlu,Long idMatricula);
}
