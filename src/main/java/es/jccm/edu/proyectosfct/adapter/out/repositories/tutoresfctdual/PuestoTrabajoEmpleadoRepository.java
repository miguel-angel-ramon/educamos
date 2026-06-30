package es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.PuestoTrabajoEmpleado;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.DocenteProjection;

@Repository
public interface PuestoTrabajoEmpleadoRepository extends JpaRepository<PuestoTrabajoEmpleado, Long> {
	
	PuestoTrabajoEmpleado findByIdEmpleadoAndIdFechaTomaPosesion(Long id, Date tomaPos);
	
	// Usamos una projection para poder usar native query y no tener que mapear todas las entities relacionadas
	// mediante inner join
	
	@Query(value = "SELECT EMP.X_EMPLEADO as IdEmpleado, "
			+ "PTO.F_TOMAPOS as idFechaTomaPosesion, "
			+ "EMP.NOMBRE as Nombre, "
			+ "EMP.APELLIDO1 as Apellido1, "
			+ "EMP.APELLIDO2 as Apellido2, "
			+ "EMP.C_NUMIDE as DniEmpleado, " 
	      	+ "(select distinct ' (sustituido/a por ' || emp1.apellido1 || ' ' || emp1.apellido2 || ', ' || "
	      	+ " emp1.nombre || ')' "
	      	+ "from TLPTOTRAEMP pto1, TLEMPLEADOS emp1, tlpuestos put  "
	        + "where pto1.x_empleado = emp1.x_empleado "
	        + "and pto1.x_centro = pto.x_centro  "
	        + "and pto1.x_empleado_sustituye = pto.x_empleado  "
	        + "and pto1.f_tomapos_sustituye = pto.f_tomapos "
	        + "and put.l_docente ='S' "
	        + "and EMP1.L_ACTIVO = 'S'  "
	        + "and TRUNC(pto1.F_TOMAPOS) <= SYSDATE  "
	        + "and (pto1.F_CESE IS NULL OR TRUNC(PTO1.F_CESE) >= SYSDATE)"
	        + "and rownum = 1) AS sustituto "			
			+ "FROM TLPTOTRAEMP PTO "
			+ "INNER JOIN TLEMPLEADOS EMP ON EMP.X_EMPLEADO  = PTO.X_EMPLEADO " 
			+ "INNER JOIN TLPUESTOS PUT ON PTO.X_PUESTO = PUT.X_PUESTO " 
			+ "WHERE PTO.X_CENTRO = ?1 " 
			+ "AND PUT.L_DOCENTE ='S' " 
			+ "AND NVL(EMP.L_ACTIVO,'S') = 'S' " 
			+ "AND (PTO.X_EMPLEADO_SUSTITUYE IS NULL OR exists (select 1 from tlempleados emp1 where emp1.x_empleado = pto.X_EMPLEADO_SUSTITUYE))  "
		    + "AND sysdate between PTO.F_TOMAPOS AND NVL(F_CESE,'01/01/2099')  " 
		    + "AND not exists (select 1 from FCT_TUTORFCTDUAL tut, TLPTOTRAEMP PTO1 "  
		    + "                where tut.x_empleado =  PTO.x_empleado  "
            + "                and PTO1.x_empleado = tut.x_empleado "
            + "                and PTO1.f_tomapos = tut.f_tomapos "
            + "                and PTO1.x_centro = PTO.x_centro) "
			+ "ORDER BY APELLIDO1,APELLIDO2,NOMBRE ", nativeQuery = true)
	List<DocenteProjection> findDocentesBycentro(Long centroId);

	List<PuestoTrabajoEmpleado> findAllByCentroId(Long centroId);

}
