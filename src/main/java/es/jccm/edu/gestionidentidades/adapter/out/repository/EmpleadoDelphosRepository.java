package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.EmpleadoDelphos;
import es.jccm.edu.gestionidentidades.application.domain.QEmpleadoDelphos;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EmpleadoDelphosRepository extends AbstractRepository<EmpleadoDelphos, Integer, QEmpleadoDelphos>{

	@Query(value = " select * "
			  +" from delphos.tlempleados "
			  +" where c_numide = ?", nativeQuery = true)
	List<EmpleadoDelphos> findEmpleadoDelphosByDocumento(@Param("documento") String documento);

	
	@Query(value = "  select emp.*, \n"
			+ " DECODE((SELECT count(*) \n"
			+ " from delphos.TLCARGOSEMP cge \n"
			+ " INNER JOIN delphos.tlcargos car ON car.C_CARGO =cge.C_CARGO \n"
			+ " WHERE cge.X_EMPLEADO = pte.X_EMPLEADO AND cge.F_TOMAPOS = pte.F_TOMAPOS AND car.LG_EQUDIR = 1  \n"
			+  "AND 1 = delphos.tlf_intersecper(cge.f_tomposrea, cge.f_cese, sysdate, sysdate)  "
			+ " ),0,0,1) lg_equipodirectivo, \n"
			+ " (SELECT LISTAGG(ce.C_CARGO, '|') lista_cargos \n"
			+ " FROM delphos.TLCARGOSEMP ce \n"
			+ " WHERE ce.X_EMPLEADO = emp.x_empleado "
			+  "AND 1 = delphos.tlf_intersecper(ce.f_tomposrea, ce.f_cese, sysdate, sysdate)  "
			+ " GROUP BY ce.X_EMPLEADO) lista_cargos,\n"
			+ " (SELECT DECODE(cen.L_DELEGACION, 'S',3, DECODE(tpe.L_DOCENTE,'S',2,DECODE(tpe.L_FUNCIONARIO ,'S',1,0 ))) tipo_personal\n"
			+ " FROM delphos.TLTIPPER tpe  \n"
			+ " ,delphos.TLCENTROS cen  \n"
			+ " WHERE tpe.X_TIPOPER = pte.X_TIPOPER AND cen.X_CENTRO =pte.X_CENTRO ) tipo_personal,\n"
			+ "  DECODE((SELECT  count(*) \n"
			+ " FROM delphos.TLUNIDADESCEN unc \n"
			+ " INNER JOIN delphos.TLCURSOACA cua ON cua.C_ANNO = unc.C_ANNO \n"
			+ " WHERE cua.L_ACTUAL = 'S' AND unc.X_EMPLEADO = emp.x_empleado AND unc.X_CENTRO = emp.x_centras )\n"
			+ " ,0,0,1) lg_tutor_unidad, \n"
			+ " (SELECT  LISTAGG(omg.T_ABREVIATURA , '|') cursos \n"
			+ " FROM delphos.TLUNIDADESCEN unc \n"
			+ " INNER JOIN delphos.TLOFERTASUNIDAD ofu ON ofu.X_UNIDAD = unc.X_UNIDAD \n"
			+ " INNER JOIN delphos.TLOFEMATRCEN omc ON omc.X_OFERTAMATRIC = ofu.X_OFERTAMATRIC \n"
			+ " INNER JOIN delphos.TLOFEMATRGEN omg ON omg.X_OFERTAMATRIG = omc.X_OFERTAMATRIG \n"
			+ " INNER JOIN delphos.TLCURSOACA cua ON cua.C_ANNO = unc.C_ANNO \n"
			+ " WHERE cua.L_ACTUAL = 'S' AND unc.X_EMPLEADO = emp.x_empleado AND unc.F_TOMAPOS =pte.F_TOMAPOS  AND unc.X_CENTRO = emp.x_centras) cursoTutorUnidad,\n"
			+" (SELECT LISTAGG( datos.T_ABREVIATURA , '|') departamentos"
			+ " FROM (SELECT dep.T_ABREVIATURA \n"
			+ " FROM delphos.TLDEPDENGEN dep \n"
			+ " INNER JOIN delphos.TLDEPARTCEN dpc ON dep.X_DEPDENGEN = dpc.X_DEPDENGEN \n"
			+ " INNER JOIN delphos.TLMIEDEPART mdp ON mdp.X_DEPARTCEN = dpc.X_DEPARTCEN \n"
			+ " INNER JOIN delphos.TLCURSOACA cua ON cua.C_ANNO = dpc.C_ANNO \n"
			+ " WHERE dpc.X_CENTRO = pte.x_centro\n"
			+ " AND cua.L_ACTUAL = 'S' \n"
			+ " AND mdp.X_EMPLEADO = emp.x_empleado \n"
			+ " AND mdp.F_TOMAPOS =pte.F_TOMAPOS\n"
			+ " UNION\n"
			+ " SELECT dep.T_ABREVIATURA  \n"
			+ " FROM delphos.TLDEPARTAMENTOS dep \n"
			+ " INNER JOIN delphos.TLDEPARTCEN dpc ON dep.X_DEPARTAMENTO = dpc.X_DEPARTAMENTO \n"
			+ " INNER JOIN delphos.TLMIEDEPART mdp ON mdp.X_DEPARTCEN = dpc.X_DEPARTCEN \n"
			+ " INNER JOIN delphos.TLCURSOACA cua ON cua.C_ANNO = dpc.C_ANNO \n"
			+ " WHERE dpc.X_CENTRO = pte.x_centro\n"
			+ " AND cua.L_ACTUAL = 'S' \n"
			+ " AND mdp.X_EMPLEADO = emp.x_empleado \n"
			+ " AND mdp.F_TOMAPOS = pte.F_TOMAPOS) datos ) departamentos "
			
			
			
			
			+ " from delphos.tlempleados emp\n"
			+ " INNER JOIN delphos.tlptotraemp pte ON pte.X_EMPLEADO = emp.X_EMPLEADO AND pte.X_CENTRO = emp.X_CENTRAS \n"
			+ " inner join delphos.tlcentros cen on cen.x_centro = emp.x_centras\n"
			+ " where emp.c_numide = ? \n"
			+ "	AND cen.c_codigo = ? \n"
			+ "	AND pte.f_tomapos=to_date(?,'dd/mm/yyyy')", nativeQuery = true)
	List<EmpleadoDelphos> findEmpleadoDelphosByDocumentoExtended(@Param("documento") String documento,@Param("xCentro") Integer xCentro,@Param("fTomaPos") Date fTomaPos);
	
}


