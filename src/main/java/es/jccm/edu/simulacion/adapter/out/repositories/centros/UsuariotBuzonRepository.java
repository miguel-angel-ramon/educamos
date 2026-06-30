package es.jccm.edu.simulacion.adapter.out.repositories.centros;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import es.jccm.edu.simulacion.application.domain.centros.entities.QUsuariotBuzon;
import es.jccm.edu.simulacion.application.domain.centros.entities.UsuariotBuzon;

@Repository
public interface UsuariotBuzonRepository extends AbstractRepository<UsuariotBuzon, Long, QUsuariotBuzon> {

	
	@Query(value = "SELECT * FROM DELPHOS_MODACC.USUARIOS_T ut WHERE ut.centro =:oidCentro AND ut.LG_ACCESOBUZON = '1' AND ut.l_activo = 'S' AND (ut.tipo_personal = '2' OR ut.tipo_personal = '1')", nativeQuery = true)
	List<UsuariotBuzon> findByCentro(String oidCentro);

	@Query(value = "SELECT * FROM DELPHOS_MODACC.USUARIOS_T ut WHERE ut.centro =:oidCentro AND ut.l_activo = 'S' AND ut.ES_ALUMNO = 'S'", nativeQuery = true)
	List<UsuariotBuzon> findAlumnadoByCentro(String oidCentro);
	
	@Query(value = "SELECT * FROM DELPHOS_MODACC.USUARIOS_T ut WHERE ut.centro =:oidCentro AND ut.LG_ACCESOBUZONALUMNADO = '1' AND ut.l_activo = 'S' AND (ut.tipo_personal = '2' OR ut.tipo_personal = '1')", nativeQuery = true)
	List<UsuariotBuzon> findAccesosBuzonAlumnadoByCentro(String oidCentro);
	
	//@Query(value = "SELECT * FROM DELPHOS_MODACC.USUARIOS_T ut WHERE ut.CENTRO =:oidCentro AND ut.LG_ADMINISTRADOR_BUZON = 1", nativeQuery = true)
	//List<Usuariot> findAdministradoresBuzonByCentro(String oidCentro);
	
	@Query(value = "SELECT * FROM DELPHOS_MODACC.USUARIOS_T ut WHERE ut.centro =:oidCentro AND ut.LG_ACCESOBUZON = '0' AND ut.tipo_personal = '2' AND ut.l_activo = 'S'", nativeQuery = true)
	List<UsuariotBuzon> findDocentesByCentro(String oidCentro);
	
	@Query(value = "SELECT * FROM DELPHOS_MODACC.USUARIOS_T ut WHERE ut.centro =:oidCentro AND ut.LG_ACCESOBUZON = '0' AND ut.tipo_personal = '1' AND ut.l_activo = 'S'", nativeQuery = true)
	List<UsuariotBuzon> findNoDocentesByCentro(String oidCentro);
	
	@Query(value = "SELECT * FROM DELPHOS_MODACC.USUARIOS_T ut WHERE ut.centro =:oidCentro AND ut.LG_EQUIDIRECTIVO = 1", nativeQuery = true)
	List<UsuariotBuzon> findDirectorByCentro(String oidCentro);
	
	@Query(value = "SELECT * FROM DELPHOS_MODACC.USUARIOS_T ut WHERE ut.centro =:oidCentro AND ut.LG_ACCESOBUZON = '0' AND ut.l_activo = 'S' AND (ut.tipo_personal = '2' OR ut.tipo_personal = '1')", nativeQuery = true)
	List<UsuariotBuzon> findCandidatos(String oidCentro);
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE DELPHOS_MODACC.USUARIOS_T SET   LG_ACCESOBUZON= 1 , L_PENDIENTE = 'S' WHERE T_IDENTIFICACION = :dni AND CENTRO = :centro", nativeQuery = true)
    void updateAccesoBuzon(@Param("dni") String dni, @Param("centro") String centro);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE DELPHOS_MODACC.USUARIOS_T SET  LG_ACCESOBUZONALUMNADO= 1 WHERE T_IDENTIFICACION = :dni AND CENTRO = :centro", nativeQuery = true)
	void updateAccesoBuzonAlumnado(@Param("dni") String dni, @Param("centro") String centro);
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE DELPHOS_MODACC.USUARIOS_T "
            + "SET  LG_ACCESOBUZON= 0 , L_PENDIENTE = 'S' "
            + "WHERE T_IDENTIFICACION = :dni AND CENTRO = :centro", nativeQuery = true)
    void deleteAccesoBuzon(String dni, String centro);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE DELPHOS_MODACC.USUARIOS_T "
			+ "SET  LG_ACCESOBUZONALUMNADO= 0 "
			+ "WHERE T_IDENTIFICACION = :dni AND CENTRO = :centro", nativeQuery = true)
	void deleteAccesoBuzonAlumnado(String dni, String centro);

	@Query(value = "select distinct emp.C_NUMIDE from tlusuarios usu, tlperfilusu per, tlptotraemp pto, tlcargosemp ce, tlcentros cen, tlcargos car, tlempleados emp"
	        + " where per.x_usuario = usu.x_usuario"
	        + " and pto.x_empleado = usu.x_empleado"
	        + " and per.x_perfil = 161"
	        + " and sysdate >= pto.f_tomapos"
	        + " and (pto.f_cese is null OR pto.f_cese > sysdate)"
	        + " and ce.x_empleado = pto.x_empleado"
	        + " and ce.f_tomapos = pto.f_tomapos"
	        + " and cen.x_centro = pto.x_centro"
	        + " and car.c_cargo = ce.c_cargo"
	        + " and ((cen.x_tipo = 1 and ce.c_cargo = 'XDI') or (cen.x_tipo = 2 and ce.c_cargo in ('XDI', 'XDP')))"
	        + " and usu.X_EMPLEADO = emp.X_EMPLEADO"
	        + " and emp.C_NUMIDE = :nif", nativeQuery = true)
	String isDirector(String nif);
	
	@Query(value = "SELECT ut.T_IDENTIFICACION FROM DELPHOS_MODACC.USUARIOS_T ut WHERE ut.LG_EQUIDIRECTIVO = 1 AND ut.T_IDENTIFICACION = :nif", nativeQuery = true)
	String isEquipoDirectivo(String nif);
	
	@Query(value = "INSERT INTO DELPHOS.ESC_BUZON_CENTROS VALUES (DELPHOS.SQ_ESC_BUZON_CENTROS.NEXTVAL, :oidPersona, :fechaAlta, :fechaBaja, :centro, :cUsuCreacion, :cUsuActualiza, :fechaCreacion, :fechaActualiza, :lgEstado)", nativeQuery = true)
	void añadeUsuarioAccesoBuzón(Long oidPersona, Date fechaAlta, Date fechaBaja, String centro, Long cUsuCreacion, Long cUsuActualiza, Date fechaCreacion, Date fechaActualiza, Long lgEstado);
}


