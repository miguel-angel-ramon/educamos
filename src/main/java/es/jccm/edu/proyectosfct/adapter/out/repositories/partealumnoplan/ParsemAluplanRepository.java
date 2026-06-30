package es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan;

import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosHojaSemanalProjection;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.ParsemAluplan;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.QParsemAluplan;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.projection.InfoParteSemanalProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ParsemAluplanRepository extends AbstractRepository<ParsemAluplan, Long, QParsemAluplan> {


    ParsemAluplan findFirstByIdParsemAluplan(Long idParsemAluPlan);

    @Query(value= "select TO_CHAR(pda.f_dia,'DD/MM/YYYY') jornadas, " +      
                  " (SELECT DBMS_LOB.SUBSTR(XMLAGG(XMLELEMENT(e, tx_nombre || ', ')).EXTRACT('//text()').GETCLOBVAL(), 4000, 1)  "
                  + "     FROM ( "
                  + "         SELECT tx_nombre "
                  + "         FROM FCT_PARDIA_ALUPLAN_ACTMOD diaa "
                  + "         JOIN fct_actividades_modulos actm  "
                  + "             ON actm.id_actividad_modulo = diaa.id_actividad_modulo "
                  + "         WHERE diaa.id_pardia_aluplan = pda.id_pardia_aluplan "
                  + "         AND ROWNUM <= 42 "
                  + "     )) AS actividades,         " +
            
                  "       TO_CHAR(pda.nu_horas) tiempos, " +
                  "       pda.tx_observaciones observaciones " +
                  "from FCT_PARSEM_ALUPLAN pap, FCT_PARDIA_ALUPLAN pda " +
                  "where pap.id_parsem_aluplan = :idParsemAluPlan " +
                  "and pap.id_parsem_aluplan = pda.id_parsem_aluplan order by pda.f_dia " ,nativeQuery = true)
    List<DatosHojaSemanalProjection> getDatosTablaParteSemanal(Long idParsemAluPlan);


    @Modifying
    @Query(value = "DELETE FROM FCT_PARSEM_ALUPLAN WHERE ID_PARSEM_ALUPLAN IN (" +
            "  SELECT DISTINCT ID_PARSEM_ALUPLAN FROM FCT_PARDIA_ALUPLAN WHERE ID_CONVPROY_ALU = :idConvProyAlu" +
            ")", nativeQuery = true)
    void deleteParsemAluplanByIdConvProyAlu(Long idConvProyAlu);

    @Query(value = "select * from fct_parsem_aluplan where id_convproy_alu = :idConvProyAlu and f_inisem = :fechaInicioSem", nativeQuery = true)
    ParsemAluplan findByIdConvProyAluAndFechaInicioSem(Long idConvProyAlu, Date fechaInicioSem);

    @Query("SELECT p FROM ParsemAluplan p WHERE p.idConvproyAlu = :idConvProyAlu AND p.fInisem IN :fechas")
    List<ParsemAluplan> findByIdConvProyAluAndFechas(Long idConvProyAlu, List<Date> fechas);

    
    @Query(value= "SELECT  "
    		+ "    id_parsem_aluplan AS idParsemAluplan,   "
    		+ "    TO_CHAR(f_inisem,'DD-MM-YYYY HH24:MI:SS') AS fInisem, "
    		+ "    id_parsem_rodal AS idParsemRodal, "
    		+ "    tx_parsem_fichero AS txParsemFichero, "
    		+ "    x_usuariocreacion AS xUsuarioCreacion, "
    		+ "    TO_CHAR(f_registro,'DD-MM-YYYY HH24:MI:SS') AS fRegistro, "
    		+ "    cd_vista AS cdVista, "
    		+ "    ( "
    		+ "        SELECT nombre_completo "
    		+ "        FROM ( "
    		+ "            SELECT  "
    		+ "                emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2 AS nombre_completo "
    		+ "            FROM tlusuarios usu "
    		+ "            JOIN tlempleados emp  "
    		+ "                ON usu.x_empleado = emp.x_empleado "
    		+ "            WHERE usu.x_usuario = p.x_usuariocreacion "
    		+ "            UNION "
    		+ "            SELECT  "
    		+ "                emp.nombre || ' ' || emp.apellido1 || ' ' || emp.apellido2 AS nombre_completo "
    		+ "            FROM delphos_segedu.tlusuarios usu "
    		+ "            JOIN delphos_segedu.tlempleados emp  "
    		+ "                ON usu.x_empleado = emp.x_empleado "
    		+ "            WHERE usu.x_usuario = p.x_usuariocreacion "
    		+ "        ) subquery "
    		+ "        WHERE ROWNUM = 1 "
    		+ "    ) AS nombre, "
    		+ "    CASE "
    		+ "    WHEN id_parsem_rodal is not null AND cd_vista = 'ALU' THEN 'FIRMADO ALUMNADO'  "
    	    + "    WHEN id_parsem_rodal is not null AND cd_vista = 'P' THEN 'PARTE CERRADO' "
    	    + "    ELSE 'Pendiente firma' "
    	    + "    END as estado, "
			+ "    lg_actualizado as lgActualizado "
    		+ "FROM fct_parsem_aluplan p  "
    		+ "WHERE p.id_convproy_alu = :idConvProyAluPar  "
    		+ "AND p.f_inisem  IN :fechas " ,nativeQuery = true)
	List<InfoParteSemanalProjection> getInfoPartes(Long idConvProyAluPar, List<Date> fechas);
}
