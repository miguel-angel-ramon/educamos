package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.application.domain.DatosCentroInt;
import es.jccm.edu.gestionidentidades.application.domain.Ptotraemp;
import es.jccm.edu.gestionidentidades.application.domain.Pueoriper;
import es.jccm.edu.gestionidentidades.application.domain.QUsuario;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.CargosActivosFechaProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.CargosCesadosProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.DatosAlumnosProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.DatosCentroNoVigenteProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.DatosParaUsuariosTProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.ExistePerfilProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.PerfilesAEliminarProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.PerfilesBorradosProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.PuestoTrabajoActivoProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.PuestoTrabajoCesadoProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.RegistrosCargosInexistentesProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.UsuarioAccesoBISinPerfilAsociadoProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.UsuarioActivoPerfilAsociadoProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.UsuarioAsociadoEmpleadoProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.UsuarioSinPerfilAsociadoProjection;
import es.jccm.edu.gestionidentidades.application.domain.interfazUsuarioProjections.cargarTodosUsuariosCAUPojection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface InterfazUsuarioRepository extends AbstractRepository<Usuario, Integer, QUsuario>{
	
	//Funcionalidad: Se obtienen los centros no vigentes
	@Query(value = "SELECT dc.f_fin_vigen,"
			+ "       dc.x_centro"
			+ "     FROM tldatoscen dc"
			+ "     WHERE dc.f_fin_vigen is not null", nativeQuery = true)
	List<DatosCentroInt> getCentrosNoVigentes();
	
	//Funcionalidad: Se cesan los puestos activos en centros no vigentes
	@Query(value = "UPDATE tlptotraemp pte"
			+ "     set pte.f_cese = vc_cen.f_fin_vigen"
			+ "   	WHERE pte.x_centro = vc_cen.x_centro"
			+ "     AND pte.f_cese is null", nativeQuery = true)
	void cesarPuestosActivosCentrosNoVigentes();
	
	//Funcionalidad: se cesan los cargos activos en centros no vigentes
	@Query(value = "UPDATE tlcargosemp ce"
			+ "     set ce.f_cese = vc_cen.f_fin_vigen"
			+ "   	WHERE (ce.x_empleado, ce.f_tomapos) in (SELECT pte.x_empleado, pte.f_tomapos"
			+ "                                             FROM tlptotraemp pte"
			+ "     WHERE pte.x_centro = vc_cen.x_centro)"
			+ "     AND ce.f_cese is null", nativeQuery = true)
	void cesarCargosActivosCentrosNoVigentes();
	
	//tlf_cesa_PueOriPer_cargo
	//Funcionalidad: Se obtienen los registros de tlpueoriper relacionados con cargos inexistentes
	@Query(value = "SELECT pte.x_empleado, pte.f_tomapos, pop.x_usuario, pop.x_perfil, pte.x_puesto"
			+ "    FROM tlpueoriper pop,"
			+ "         tlptotraemp pte"
			+ "    WHERE (trunc(pte.f_cese) >= trunc(sysdate)"
			+ "          or pte.f_cese is null)"
			+ "     AND pop.x_empleado = pte.x_empleado"
			+ "     AND trunc(pop.f_tomapos) = trunc(pte.f_tomapos)"
			+ "     AND pop.l_asigman = 'N'"
			+ "     AND exists (SELECT 1"
			+ "                   FROM tlperfilescargo pg,"
			+ "                        tlcargos car"
			+ "                  WHERE pg.x_perfil = pop.x_perfil"
			+ "                    AND pg.c_cargo = car.c_cargo"
			+ "                    AND car.l_vigente = 'S' )"
			+ "     AND not exists (SELECT 1"
			+ "                       FROM tlperfilescargo pg,"
			+ "                            tlcargosemp cemp"
			+ "                      WHERE pg.x_perfil = pop.x_perfil"
			+ "                        AND cemp.x_empleado = pte.x_empleado"
			+ "                        AND cemp.f_tomapos = pte.f_tomapos"
			+ "                        AND cemp.c_cargo = pg.c_cargo)", nativeQuery = true)
	List<Pueoriper> obtenerRegistrosCargosInexistentes();
	
	//Funcionalidad: Se elmina el registro de tlpueoriper que no tiene razón de ser
	@Query(value = " DELETE FROM tlpueoriper"
			+ "      WHERE x_empleado = pueoriper_r.x_empleado"
			+ "         AND trunc(f_tomapos) = trunc(pueoriper_r.f_tomapos)"
			+ "       	AND x_perfil = pueoriper_r.x_perfil"
			+ "         AND l_asigman = 'N'", nativeQuery = true)
	void eliminarRegistroTlpueoriperInutil ();
	
	//@Modifying
	//@Query(value="update TlptoTraEmpEntity pte set pte.fCese = :fFinVigen WHERE pte.xCentro = :xCentro AND pte.fCese is null", nativeQuery = true)
	//int updatePuestosActivos(String xCentro, Date fFinVigen);
	
	/**
	 * PKG_INTERFAZUSUARIO function tlf_cesarptocar cesa puestos de trabajo y cargos activos en centros no vigentes
	 */
	
	//se obtienen los centros no vigentes
	@Query(value="SELECT dc.fFinVigen, dc.xCentro "
			+ "FROM DatosCenEntity dc "
			+ "WHERE dc.fFinVigen is not null", nativeQuery = true)
	List<DatosCentroNoVigenteProjection> findCentrosNoVigentes();
	
	//se cesan los puestos activos en centros no vigentes
	@Modifying
	@Query(value="UPDATE tlptotraemp pte set pte.fCese = :fFinVigen "
			+ "WHERE pte.xCentro = :xCentro "
			+ "AND pte.fCese is null", nativeQuery = true)
	void updatePuestosActivos(Long xCentro, Date fFinVigen);

	//se cesan los cargos activos en centros no vigentes
	@Modifying
	@Query(value="UPDATE tlcargosemp ce set ce.fCese = :fFinVigen "
			+ "WHERE (ce.xEmpleado, ce.fTomapos) "
			+ "IN (SELECT pte.xEmpleado, pte.fTomapos "
			+ "		FROM TlptoTraEmpEntity pte "
			+ "		WHERE pte.xCentro = :xCentro) "
			+ "AND ce.fCese is null", nativeQuery = true)
	void updateCargosActivos(Long xCentro, Date fFinVigen);

	/**
	 * PKG_INTERFAZUSUARIO function tlf_cesa_PueOriPer_cargo elimina registros de tlpueoriper correspondientes a cargos inexistentes
	 */
	
	//se obtienen los registros de tlpueoriper relacionados con cargos inexistentes
	@Query(value = "SELECT pte.x_empleado, pte.f_tomapos, pop.x_usuario, pop.x_perfil, pte.x_puesto "
			+ "FROM tlpueoriper pop, tlptotraemp pte "
			+ "WHERE (trunc(pte.f_cese) >= trunc(sysdate) "
			+ "OR pte.f_cese IS NULL) "
			+ "AND pop.x_empleado = pte.x_empleado "
			+ "AND trunc(pop.f_tomapos) = trunc(pte.f_tomapos) "
			+ "AND pop.l_asigman = 'N' "
			+ "AND EXISTS (SELECT 1 "
			+ "				FROM tlperfilescargo pg, tlcargos car "
			+ "				WHERE pg.x_perfil = pop.x_perfil AND pg.c_cargo = car.c_cargo "
			+ "				AND car.l_vigente = 'S') "
			+ "AND NOT EXISTS (SELECT 1 "
			+ "					FROM tlperfilescargo pg, tlcargosemp cemp "
			+ "					WHERE pg.x_perfil = pop.x_perfil "
			+ "					AND cemp.x_empleado = pte.x_empleado "
			+ "					AND cemp.f_tomapos = pte.f_tomapos "
			+ "					AND cemp.c_cargo = pg.c_cargo)",nativeQuery = true )
	List<RegistrosCargosInexistentesProjection> findRegistrosCargosInexistentesToDelete();
	
	//el registro de tlpueoriper no tiene razón de ser --> lo eliminamos
	@Modifying
	@Query(value = "DELETE FROM tlpueoriper WHERE x_empleado = :xEmpleado "
			+ "AND trunc(f_tomapos) = trunc(:fTomapos) "
			+ "AND x_perfil = :xPerfil "
			+ "AND l_asigman = 'N'", nativeQuery = true)
	void DeletePueOriPer(Long xEmpleado, Date fTomapos, Long xPerfil);

	//Borramos registro de tlperfilusu
	@Modifying
	@Query(value = "DELETE FROM tlperfilusu "
			+ "WHERE x_usuario = :xUsuario "
			+ "AND x_perfil = :xPerfil "
			+ "AND l_asigman = 'N'",nativeQuery = true)
	void DeletePerfilUsu(Long xUsuario, Long xPerfil);

	/**
	 * PKG_INTERFAZUSUARIO function tlf_Borra_Perfiles_Cese Borra perfiles 100- Coordinador de Evaluación y 110 - Corrector de Datos de Eval para profesores con Fec Cese = Fec. Sistema
	 */
	
	//Obtenemos los registros correspondientes a los perfiles que deben quitarse, al ejecutarse el proceso a las 22:00 se supone que si cesan ese día, ya se puede
	@Query(value = "SELECT PXP.X_EMPLEADO, PXP.F_TOMAPOS, P.C_CODIGO FROM TLPUEORIPER PXP, TLPERFILES P, TLPTOTRAEMP PTE "
			+ "WHERE PXP.X_EMPLEADO = PTE.X_EMPLEADO "
			+ "AND trunc(PXP.f_tomapos) = trunc(PTE.f_tomapos) "
			+ "AND trunc(PTE.f_cese) = trunc(sysdate) "
			+ "AND PXP.X_PERFIL IN (100,110) "
			+ "AND P.X_PERFIL = PXP.X_PERFIL", nativeQuery = true)
	List<PerfilesAEliminarProjection> findPerfilesCese();

	/**
	 * PKG_INTERFAZUSUARIO function tlf_borpercarpto Borra perfiles y desactiva usuarios de puestos de trabajo o cargos cesados
	 */
	
	//se obtienen los puestos de trabajo cesados
	@Query(value = "SELECT pte.x_empleado, pte.f_tomapos, pop.x_usuario, pop.x_perfil "
			+ "FROM tlptotraemp pte, tlpueoriper pop"
			+ "WHERE trunc(f_cese) < trunc(sysdate) "
			+ "AND tlpueoriper.x_empleado = tlptotraemp.x_empleado "
			+ "AND trunc(tlpueoriper.f_tomapos) = trunc(tlptotraemp.f_tomapos) "
			+ "AND tlpueoriper.l_asigman = 'N'", nativeQuery = true)
    List<PuestoTrabajoCesadoProjection> findPuestosCesados();

	//se obtienen los cargos cesados
    @Query(value = "SELECT cemp.x_empleado, cemp.f_tomapos, pop.x_usuario, pop.x_perfil "
    		+ "FROM tlcargosemp cemp, tlpueoriper pop"
    		+ "WHERE trunc(f_cese) < trunc(sysdate) "
    		+ "AND tlpueoriper.x_empleado = tlcargosemp.x_empleado "
    		+ "AND trunc(tlpueoriper.f_tomapos) = trunc(tlcargosemp.f_tomapos) "
    		+ "AND tlpueoriper.l_asigman = 'N'", nativeQuery = true)
    List<CargosCesadosProjection> findCargosCesados();

    //se obtienen los perfiles que se hayan borrado en TLPERFILESPTO y TLPERFILESCARGO
    @Query(value = "SELECT x_perfil "
    		+ "FROM tlperfilusu "
    		+ "WHERE l_asigman = 'N' MINUS (SELECT x_perfil "
    		+ "								FROM tlperfilespto UNION SELECT x_perfil FROM tlperfilescargo)", nativeQuery = true)
    List<PerfilesBorradosProjection> findPerfilesBorrados();

    //se obtienen los usuarios sin ningun perfil asociado
    @Query(value = "SELECT x_usuario "
    		+ "FROM tlusuarios "
    		+ "WHERE l_activo = 'S' MINUS "
    		+ "	SELECT x_usuario "
    		+ "	FROM tlperfilusu", nativeQuery = true)
    List<UsuarioSinPerfilAsociadoProjection> findUsuariosSinPerfiles();
    
    //se borran los perfiles originados por puestos de trabajo cesados
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tlpueoriper "
    		+ "WHERE x_empleado = :xEmpleado "
    		+ "AND trunc(f_tomapos) = trunc(:fTomapos) "
    		+ "AND x_perfil = :xPerfil "
    		+ "AND L_ASIGMAN = 'N'")
    void DeleteByEmpleadoAndFechaAndPerfil(Long xEmpleado, Date fTomapos, Long xPerfil);

	//Se comprueba que no existan más registros en tlpueoriper
    @Query(nativeQuery = true, value = "SELECT COUNT(*) "
    		+ "FROM tlpueoriper "
    		+ "WHERE x_empleado = :xEmpleado "
    		+ "AND X_PERFIL = :xPerfil")
    int countByEmpleadoAndPerfil(Long xEmpleado, Long xPerfil);
    
    //Se eliminan los registros de la tabla tlusuariozona para ese usuario y perfil
    @Query(nativeQuery = true, value = "DELETE FROM TLUSUARIOZONA "
    		+ "WHERE X_USUARIO = :xUsuario "
    		+ "AND X_PERFIL = :xPerfil")
    void DeleteFromTlusuariozona(Long xUsuario, Long xPerfil);

    @Query(nativeQuery = true, value = "DELETE FROM TLPERFILUSU "
    		+ "WHERE X_USUARIO = :xUsuario "
    		+ "AND X_PERFIL = :xPerfil "
    		+ "AND l_asigman = 'N'")
    void DeleteFromTlperfilusu(Long xUsuario, Long xPerfil);
    
    //se comprueba si el perfil a borrar pertenece a algun cargo activo
    @Query(nativeQuery = true, value = "SELECT COUNT(*) INTO temp_n FROM TLCARGOSEMP car, TLPERFILESCARGO per "
    		+ "WHERE car.x_empleado = :xEmpleado "
    		+ "AND trunc(car.f_tomapos) = trunc(:fTomapos) "
    		+ "AND nvl(trunc(car.f_cese), to_date('31/12/9999','DD/MM/YYYY')) > trunc(sysdate+1) "
    		+ "AND per.c_cargo = car.c_cargo "
    		+ "AND per.x_perfil = :xPerfil")
    int countFromTlcargosemp(Long xEmpleado, Date fTomapos, Long xPerfil);

    //se borran los perfiles de usuarios asociados a puestos de trabajo cesados
    @Query(nativeQuery = true, value = "DELETE FROM TLPERFILUSU "
    		+ "WHERE x_perfil = :xPerfil "
    		+ "AND x_usuario = :xUsuario "
    		+ "AND l_asigman = 'N'")
    void DeleteFromTlperfilusua(Long xPerfil, Long xUsuario);
    
    //se borran los perfiles originados por cargos cesados
    @Modifying
    @Query(value="DELETE FROM tlpueoriper t "
    		+ "WHERE t.x_empleado = :xEmpleado "
    		+ "AND trunc(t.f_tomapos) = trunc(:fTomapos) "
    		+ "AND t.x_perfil = :xPerfil "
    		+ "AND t.l_asigman = 'N'", nativeQuery = true)
    void deleteTlpueoriperByXEmpleadoAndFTomaposAndXPerfil(Long xEmpleado, Date fTomapos, Long xPerfil);
    
    //se comprueba si el perfil a borrar pertenece a algun puesto de trabajo activo
    @Query(value = "SELECT count(*) INTO temp_n "
    		+ "FROM tlptotraemp pto, tlperfilespto per "
    		+ "WHERE pto.x_empleado = :xEmpleado "
    		+ "AND trunc(pto.f_tomapos) = trunc(:fTomapos) "
    		+ "AND nvl(trunc(pto.f_cese), to_date('31/12/9999','DD/MM/YYYY')) > trunc(sysdate+1) "
    		+ "AND per.x_puesto = pto.x_puesto "
    		+ "AND per.x_perfil = :xPerfil", nativeQuery = true)
    int countByXEmpleadoAndFTomaposAndXPerfil(Long xEmpleado, Date fTomapos, Long xPerfil);
    
    //se borran los perfiles de usuarios asociados a cargos cesados
    @Modifying
    @Query(value="DELETE FROM Tlperfilusu "
    		+ "WHERE x_perfil = :perfil "
    		+ "AND x_usuario = :usuario "
    		+ "AND l_asigman = 'N'", nativeQuery = true)
    void DeleteByPerfilAndUsuario(Long perfil, Long usuario);
    
    //se borra el perfil asociado a puestos de trabajo y cargos
    @Modifying
    @Query(value="DELETE FROM tlpueoriper "
    		+ "WHERE x_perfil = :xPerfil "
    		+ "AND l_asigman = 'N'", nativeQuery = true)
    void DeleteByXPerfil(Long xPerfil);

    //se borra el perfil asociado a usuarios
    @Query(value="DELETE FROM tlPerfilUsu "
    		+ "WHERE x_perfil = :xPerfil "
    		+ "AND l_asigman = 'N'", nativeQuery = true)
    void DeleteByXPerfilAndLAsigman(Long xPerfil);
    
    //se desactivan los usuarios
    @Modifying
    @Query(value="UPDATE TLUsuarios u "
    		+ "SET u.lActivo = 'N' "
    		+ "WHERE u.xUsuario = :xUsuario", nativeQuery = true)
    void desactivarUsuario(Long xUsuario);
    
    /**
	 * PKG_INTERFAZUSUARIO function tlf_empptoperfil Crea los usuarios y los perfiles de usuarios asociados a puestos de trabajo activos.
	 */
    
    //se obtienen los puestos de trabajo activos
    @Query(value="SELECT pte.x_empleado, pte.f_tomapos, ppto.x_perfil, pto.l_docente "
    		+ "FROM tlptotraemp pte, tlperfilespto ppto, tlpuestos pto "
    		+ "WHERE pte.l_usuario = 'S' "
    		+ "AND pte.f_tomaposrea <= sysdate "
    		+ "AND nvl(pte.f_cese, to_date('31/12/9999','DD/MM/YYYY')) > trunc(sysdate) "
    		+ "AND ppto.x_puesto = pto.x_puesto "
    		+ "AND pto.x_puesto = pte.x_puesto", nativeQuery = true)
    List<PuestoTrabajoActivoProjection> findEmpleadosByUsuario();
    
    //TODO Implementar función tlf_creausuempperfil
    
    /**
	 * PKG_INTERFAZUSUARIO function tlf_creausuempperfil Crea el usuario y el perfil de usuario para personal docente.
	 */
    
    //se comprueba la existencia del empleado
    @Query(value = "SELECT emp.c_numide " +
            "FROM tlptotraemp pte, tlempleados emp " +
            "WHERE pte.x_empleado = :px_empleado " +
            "AND pte.f_tomapos = :pf_tomapos " +
            "AND emp.x_empleado = pte.x_empleado", nativeQuery = true)
    Long getNumideByEmpleadoAndTomapos(Long px_empleado, Date pf_tomapos);
    
    //se comprueba la existencia del perfil
    @Query(value = "SELECT x_perfil, lg_perfil_acc_bi "
            + "FROM tlperfiles "
            + "WHERE x_perfil = :px_perfil "
            + "AND l_activo = 'S';",nativeQuery = true)
    List<ExistePerfilProjection> findPerfilByXPerfil(Long px_perfil);
    
    //se obtiene el usuario asociado al empleado
    @Query(value = "SELECT usu.x_usuario, usu.l_activo " 
            + "FROM tlusuarios usu " 
            + "WHERE usu.x_empleado = :pxEmpleado",
       nativeQuery = true)
    UsuarioAsociadoEmpleadoProjection getUsuarioActivo(Long pxEmpleado);
    
    //se comprueba si el usuario tiene asociado el perfil
    @Query(value = "SELECT 1 FROM tlperfilusu pusu "
    		+ "WHERE pusu.x_usuario = :px_usuario "
    		+ "AND pusu.x_perfil = :px_perfil",
            nativeQuery = true)
    Long findPerfilUsuario(Long px_usuario, Long px_perfil);
    
    //se comprueba si existe el perfil de usuario originado por el puesto de trabajo
    @Query(value = "SELECT 1 "
            + "FROM tlpueoriper "
            + "WHERE x_empleado = :px_empleado "
            + "AND f_tomapos = :pf_tomapos "
            + "AND x_usuario = :px_usuario "
            + "AND x_perfil = :px_perfil", nativeQuery = true)
    Integer findPueOriPer(Long px_empleado, Date pf_tomapos, Long px_usuario, Long px_perfil);
    
    //se obtiene el identificador del perfil de acceso a BI
    @Query(value = "SELECT x_perfil  "
    		+ "FROM tlperfiles "
    		+ "WHERE c_codigo = 'ABI'", nativeQuery = true)
    Long getPerfil();
    
    /*se crea el usuario
    //TODO: utilizar repository de usuario y recuperar el objeto que ha insertado
    insert into tlusuarios (x_usuario, usuario, clave, l_bloqueado,
            n_intentos, l_activo, l_primeraconexion, n_accesos,
            x_empleado)
    values (tls_usuxusuario.nextval, dni_v, tlf_encriptar(tlf_encriptar(clave_v)), 'N',
            0, 'S', 'S', 0,
            px_empleado)
    returning x_usuario into vx_usuario;*/
    
    //se crea el perfil de usuario
    @Query(value = "INSERT INTO tlperfilusu (x_usuario, x_perfil, l_asigman) "
            + "VALUES (:vx_usuario, :px_perfil, 'N')", nativeQuery = true)
    void insertPerfilUsuario(Long vx_usuario, Long px_perfil);
    
    //se crea el perfil de usuario originado por el puesto de trabajo
    @Query(value = "INSERT INTO tlpueoriper (x_pueoriper, l_asigman, x_empleado, f_tomapos, x_usuario, x_perfil) "
            + "VALUES (tls_popxpueoriper.nextval, 'N', :px_empleado, :pf_tomapos, :vx_usuario, :px_perfil)", nativeQuery = true)
    void insertPueOriPer(Long px_empleado,  Date pf_tomapos, Long vx_usuario, Long px_perfil);
    
    
    //si el perfil conlleva acceso a BI insertamos directamente ya que no puede tener registro con anterioridad a este punto
    @Query(value = "INSERT INTO tlperfilusu (x_usuario, x_perfil, l_asigman) " 
            + "VALUES (:vx_usuario, :vgx_perfil_BI, 'N')", nativeQuery = true)
    void insertPerfilBIUsuario(Long vx_usuario, Long vgx_perfil_BI);
    
    //se crea el perfil de usuario originado por el puesto de trabajo
    @Query(value = "INSERT INTO tlpueoriper (x_pueoriper, l_asigman, x_empleado, f_tomapos, x_usuario, x_perfil) "
            + "VALUES (tls_popxpueoriper.nextval, 'N', :px_empleado, :pf_tomapos, :vx_usuario, :vgx_perfil_BI)", nativeQuery = true)
    void insertPueoroPer(Long px_empleado, Date pf_tomapos, Long vx_usuario, Long vgx_perfil_BI);
    
    //se activa el usuario
    @Modifying
    @Query(value = "UPDATE tlusuarios SET l_activo = 'S' "
    		+ "WHERE x_usuario = :idUsuario", nativeQuery = true)
    void activarUsuario(Long idUsuario);
    
    //se crea el perfil de usuario
    @Query(value = "INSERT INTO tlperfilusu (x_usuario, x_perfil, l_asigman) "
            + "VALUES (:xUsuario, :xPerfil, 'N')",nativeQuery = true)
    void insertarPerfilUsuario(Long xUsuario, Long xPerfil);
    
    //se crea el perfil de usuario originado por el puesto de trabajo
    @Query(value = "INSERT INTO tlpueoriper (x_pueoriper, l_asigman, x_empleado, f_tomapos, "
            + "x_usuario, x_perfil) "
            + "VALUES (tls_popxpueoriper.nextval, 'N', :px_empleado, :pf_tomapos, "
            + ":vx_usuario, :px_perfil)", nativeQuery = true)
    void insertPueOriper(Long px_empleado, Date pf_tomapos, Long vx_usuario, Long px_perfil);
    
    //si el perfil conlleva acceso a BI insertamos directamente ya que no puede tener registro con anterioridad a este punto
    @Query(value = "INSERT INTO tlperfilusu (x_usuario, x_perfil, l_asigman) "
            + "VALUES (:vx_usuario, :vgx_perfil_BI, 'N')", nativeQuery = true)
    void insertarPerfilUsu(Long vx_usuario, Long vgx_perfil_BI);
    
    //se crea el perfil de usuario originado por el puesto de trabajo
    @Query(value = "INSERT INTO tlpueoriper (x_pueoriper, l_asigman, x_empleado, f_tomapos, x_usuario, x_perfil) " +
            "VALUES (tls_popxpueoriper.nextval, 'N', :px_empleado, :pf_tomapos, :vx_usuario, :vgx_perfil_BI)", nativeQuery = true)
    void insertPueOriPerPerfilBI(Long px_empleado, Date pf_tomapos, Long vx_usuario, Long vgx_perfil_BI);
    
    //se crea el perfil de usuario
    @Query(value = "INSERT INTO tlperfilusu (x_usuario, x_perfil, l_asigman) " +
            "VALUES (:vx_usuario, :px_perfil, 'N')", nativeQuery = true)
    void insertPerfilUsu(Long vx_usuario, Long px_perfil);
    
    //se crea el perfil de usuario originado por el puesto de trabajo
    @Query(value = "insert into tlpueoriper (x_pueoriper, l_asigman, x_empleado, f_tomapos, x_usuario, x_perfil) " +
            "values (tls_popxpueoriper.nextval, 'N', :px_empleado, :pf_tomapos, :vx_usuario, :px_perfil)", nativeQuery = true)
    void insertPueOriPerPerfil(Long px_empleado, Date pf_tomapos, Long vx_usuario, Long px_perfil);
    
    // insertarPerfilUsu y insertPueOriPerPerfilBI x2
    
    /**
	 * PKG_INTERFAZUSUARIO function tlf_empcargoperfil Crea los usuarios y los perfiles de usuarios asociados a cargos activos.
	 */
    
    @Query(value="SELECT car.x_empleado, car.f_tomapos, per.x_perfil "
    		+ "FROM tlcargosemp car, tlperfilescargo per "
    		+ "WHERE car.f_tomposrea <= sysdate "
    		+ "AND nvl(car.f_cese, to_date('31/12/9999','DD/MM/YYYY')) > trunc(sysdate) "
    		+ "AND per.c_cargo = car.c_cargo ", nativeQuery = true)
    List<CargosActivosFechaProjection> findEmployeeDataByCargoId();
    
    //TODO Implementar funcion tlf_creausuariopas
    
    /**
	 * PKG_INTERFAZUSUARIO function tlf_crearperfilaccesoBI revisa usuarios activos que tienen algún perfil asociado a un puesto de trabajo activo, y que deben tener
   	 * acceso a BI y no lo tienen
	 */
    
    @Query(value="SELECT distinct usu.x_usuario, usu.x_empleado, p.x_perfil " +
            "FROM tlusuarios usu, tlperfilusu pus, tlperfiles p " +
            "WHERE pus.x_usuario = usu.x_usuario " +
            "AND pus.x_perfil = p.x_perfil " +
            "AND usu.l_activo = 'S' " +
            "AND usu.l_bloqueado = 'N' " +
            "AND p.lg_perfil_acc_bi = 1 " +
            "AND p.l_activo = 'S' " +
            "AND exists ( " +
            "    SELECT 1 " +
            "    FROM tlptotraemp pte, tlpueoriper pop " +
            "    WHERE pte.x_empleado = pop.x_empleado " +
            "    AND pte.f_tomapos = pop.f_tomapos " +
            "    AND pte.x_empleado = usu.x_empleado " +
            "    AND 1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, sysdate, sysdate) " +
            "    AND pop.x_perfil = p.x_perfil " +
            ") " +
            "	 AND not exists ( " +
            "    SELECT 1 " +
            "    FROM tlptotraemp pte, tlpueoriper pop " +
            "    WHERE pte.x_empleado = pop.x_empleado " +
            "    AND pte.f_tomapos = pop.f_tomapos " +
            "    AND pte.x_empleado = usu.x_empleado " +
            "    AND 1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, sysdate, sysdate) " +
            "    AND pop.x_perfil = ( " +
            "        SELECT x_perfil " +
            "        FROM tlperfiles " +
            "        WHERE c_codigo = 'ABI' " +
            "    ) " +
            ") ", nativeQuery = true)
    UsuarioActivoPerfilAsociadoProjection findUsuariosByFiltro();
    
    @Query(value="SELECT pus.x_usuario "
    		+ "FROM tlperfilusu pus "
    		+ "WHERE pus.x_usuario = :p_x_usuario "
    		+ "AND pus.x_perfil = (SELECT x_perfil "
    		+ "						FROM tlperfiles "
    		+ "						WHERE c_codigo = :p_c_codigoperfil)", nativeQuery = true)
    Long findByXUsuarioAndXPerfil(Long p_x_usuario, Long p_c_codigoperfil);
    
    @Query(value="SELECT pop.x_pueoriper FROM TlpTotraEmp pte, TlpUeoRiPer pop "
            + "WHERE pte.x_empleado = pop.x_empleado "
            + "AND pte.f_tomapos = pop.f_tomapos " 
            + "AND pte.x_empleado = :xEmpleado "
            + "AND pte.f_tomapos = :f_tomapos " 
            + "AND pop.x_usuario = :xUsuario "
            + "AND 1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, sysdate, sysdate) "
            + "AND pop.x_perfil = (SELECT x_perfil FROM TlPerfiles WHERE c_codigo = :c_codigoperfil)", nativeQuery = true)
    Long findX_PueOriPer(Long xEmpleado, Date f_tomapos, String xUsuario, String c_codigoperfil);
    
    @Query(value="SELECT pte* FROM TlpTotraEmp pte "
            + "WHERE x_empleado = :p_x_empleado "
            + "AND 1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, sysdate, sysdate) " 
            + "AND exists (SELECT 1 FROM tlpUeoRiPer pop " 
            + "			WHERE pte.x_empleado = pop.x_empleado "
            + "			AND pte.f_tomapos = pop.f_tomapos " 
            + "			AND 1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, sysdate, sysdate) "
            + "			AND pop.x_perfil = :p_x_perfil) " 
            + "order by nvl(trunc(pte.f_cese), trunc(sysdate)) desc", nativeQuery = true)
    List<Ptotraemp> findByXEmpleadoAndXPerfil(Long p_x_empleado, Long p_x_perfil);
    
    @Query(value="SELECT x_perfil "
    		+ "FROM TlPerfiles "
    		+ "WHERE c_codigo = 'ABI'", nativeQuery = true)
    Long findX_PerfilByC_Codigo();
    
    @Query(value="SELECT x_usuario "
    		+ "FROM TlUsuarios "
    		+ "WHERE x_empleado = :xEmpleado", nativeQuery = true)
    Long findX_UsuarioByX_Empleado(Long xEmpleado);
    
    @Modifying
    @Transactional
    @Query(value="DELETE FROM tlpueoriper pue "
    		+ "WHERE pue.x_usuario = :v_x_usuario "
    		+ "AND pue.x_perfil = :vgx_perfil_BI", nativeQuery = true)
    void DeleteByX_UsuarioAndX_Perfil(Long v_x_usuario, Long vgx_perfil_BI);
    
    @Modifying
    @Query(value = "insert into tlpueoriper (x_pueoriper, l_asigman, x_empleado, f_tomapos, x_usuario, x_perfil, c_usucreacion, f_creacion, c_usuactualiza, f_actualiza, t_orireg) values (tls_popxpueoriper.nextval, 'N', :x_empleado, :f_tomapos, :x_usuario, :x_perfil, -23700, sysdate, null, null, null)", nativeQuery = true)
    void insertIntoTlpueoriperCrearperfilaccesoBI(Long x_empleado, Date f_tomapos, Long x_usuario, Long x_perfil);
    
    /**
	 * PKG_INTERFAZUSUARIO funcion tlf_borrarperfilaccesoBI revisa usuarios activos que tienen acceso a BI pero no tienen perfil asociado a BI en un puesto de trabajo activo, y que, por tanto,
   	 * no deben tener acceso a BI y, por tanto, hay que eliminarlo
	 */
    
    @Query(value="SELECT pte.x_empleado, pte.f_tomapos "
    		+ "FROM TlpTotraEmp pte, TlpUeoRiPer pop " +
            "WHERE pte.x_empleado = pop.x_empleado AND pte.f_tomapos = pop.f_tomapos " +
            "AND 1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, sysdate, sysdate) " +
            "AND pop.x_perfil = (SELECT x_perfil FROM TlPerfiles WHERE c_codigo = 'ABI') " +
            "AND not exists (SELECT 1 FROM TlUsuarios usu, TlPerfilUsu pus, TlPerfiles p " +
            "WHERE pus.x_usuario = usu.x_usuario AND pus.x_perfil = p.x_perfil " +
            "AND usu.x_empleado = pte.x_empleado AND usu.l_activo = 'S' " +
            "AND usu.l_bloqueado = 'N' AND p.lg_perfil_acc_bi = 1 AND p.l_activo = 'S')", nativeQuery = true)
    List<UsuarioAccesoBISinPerfilAsociadoProjection> findX_EmpleadoAndF_Tomapos();
    
    //cursor checkexisteperfilusu
    @Query(value="SELECT pus.x_usuario FROM TlPerfilUsu pus " +
            "WHERE pus.x_usuario = :xUsuario " +
            "AND pus.x_perfil = (SELECT x_perfil FROM TlPerfiles WHERE c_codigo = :c_codigoperfil)", nativeQuery = true)
    List <Long> findX_UsuarioByX_Perfil(Long xUsuario, String c_codigoperfil);
    
    //cursor checkexistepueoriper
    @Query(value="SELECT pop.x_pueoriper FROM TlpTotraEmp pte, TlpUeoRiPer pop " 
            + "WHERE pte.x_empleado = pop.x_empleado AND pte.f_tomapos = pop.f_tomapos " 
            + "AND pte.x_empleado = :p_x_empleado "
            + "AND pte.f_tomapos = :p_f_tomapos " 
            + "AND pop.x_usuario = :p_x_usuario "
            + "AND 1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, sysdate, sysdate) " 
            + "AND pop.x_perfil = (SELECT x_perfil FROM TlPerfiles WHERE c_codigo = :p_c_codigoperfil)", nativeQuery = true)
    List<Long> findX_PueOriPerCursorCheckexistepueoriper(Long p_x_empleado, Date p_f_tomapos,Long p_x_usuario,String p_c_codigoperfil);
    
    @Query(value="SELECT x_perfil  "
    		+ "FROM TlPerfiles "
    		+ "WHERE c_codigo = 'ABI'", nativeQuery = true)
    List <Long> findX_PerfilByC_Codigos();
    
    @Query(value="SELECT x_usuario "
    		+ "FROM TlUsuarios "
    		+ "WHERE x_empleado = :xEmpleado", nativeQuery = true)
    Long findX_UsuarioByX_Empleados(Long xEmpleado);
    
    /*@Modifying
    @Transactional
    @Query("DELETE FROM tlpueoriper pue WHERE pue.x_usuario = :v_x_usuario AND pue.x_perfil = :vgx_perfil_BI")
    void DELETEByX_UsuarioANDX_Perfil(Long v_x_usuario, Long vgx_perfil_BI);*/
    
    /*@Modifying
    @Transactional
    @Query("DELETE FROM tlpueoriper pue WHERE pue.x_usuario = :v_x_usuario AND pue.x_perfil = :vgx_perfil_BI")
    void DELETEByX_UsuarioANDX_Perfil(Long v_x_usuario, Long vgx_perfil_BI);*/
    
    /**
	 * PKG_INTERFAZUSUARIO funcion tlf_revisionusuariost 
	 * función que se ejecutará desde el lanza para revisar en cada ejecución si faltan usuarios en usuarios_t, para INSERTarlos
	 * premisa: usuarios en delphos_modacc, que además son usuarios activos 
	 *	- en delphos para el caso de empleados, 
	 *	- en delphos_segedu en el caso de alumnos
	 * se busca al usuario a través de la documentación y no a través del oid por si el usuario existe pero no está sincronizado 
     */
  
    @Query(value="SELECT usu.oid FROM delphos_modacc.usuarios usu, delphos_modacc.personas pers, delphos_modacc.documentaciones doc, tlUsuarios tlUs, tlEmpleados tlEmp " +
            "WHERE doc.oidPersona = pers.oid " +
            "AND usu.oidPersona = pers.oid " +
            "AND usu.lActivo = 'S' " +
            "AND decode(tlEmp.lTipide, 'D', 1, 'P', 2, 'N', 3, 4) = doc.oidTipoDocumentacion " +
            "AND tlEmp.cNumide = doc.tIdentificacion " +
            "AND tlEmp.xEmpleado = tlUs.xEmpleado " +
            "AND tlUs.lActivo = 'S' " +
            "AND exists (SELECT 1 FROM TlPtoTraEmp pte, TlCentrosConjuntos cc " +
            "WHERE tlEmp.xEmpleado = pte.xEmpleado " +
            "AND pte.xCentro = cc.xCentro " +
            "AND pte.lUsuario = 'S' " +
            "AND cc.cConjunto = 'ACCESO_EDUCAMOSCLM' " +
            "AND 1 = tlfIntersecper(pte.fTomaposrea, pte.fCese, sysdate, sysdate)) " +
            "AND not exists (SELECT 1 FROM UsuariosT usut WHERE usut.oid = usu.oid)", nativeQuery = true)
    List<Long> findOidByFilters();
    
    @Query(value="SELECT usu.oid, emp.l_tipide, emp.c_numide FROM delphosModacc.Usuarios usu, " 
            + "delphosModacc.Personas per, delphosModacc.Documentaciones doc, " 
            + "delphosSegedu.TlUsuarios us, delphosSegedu.TlEmpleados emp " 
            + "WHERE doc.oid_persona = per.oid "
            + "AND usu.oid_persona = per.oid " 
            + "AND usu.l_activo = 'S' "
            + "AND decode(emp.l_tipide, 'D', 1, 'P', 2, 'N', 3, 4) = doc.oid_tipo_documentacion " 
            + "AND emp.c_numide = doc.t_identificacion "
            + "AND emp.x_empleado = us.x_empleado AND us.l_activo = 'S' " 
            + "AND exists (SELECT 1 FROM TlAlumnos alu, TlMatAlu ma, TlCentrosConjuntos cc " 
            + "				WHERE emp.l_tipide = alu.c_tipide "
            + "				AND emp.c_numide = decode(alu.c_tipide, 'N', alu.c_numescolar, alu.c_numide) " 
            + "				AND alu.x_alumno = ma.x_alumno "
            + "				AND ma.c_anno = ?1 AND nvl(ma.c_resultado, 99) > 1 " 
            + "				AND ma.x_centro = cc.x_centro "
            + "				AND cc.c_conjunto = 'ACCESO_EDUCAMOSCLM') " 
            + "AND not exists (SELECT 1 FROM DelphosModacc.Usuarios_t usut "
            + "					WHERE usut.oid = usu.oid) "
            + "					AND rownum < 2000", nativeQuery = true)
    List<DatosAlumnosProjection> findOidAndL_TipIdeAndC_NumIdeByPcAnno(int anno);
    
    @Query(value="SELECT oid, oid_persona, t_login, t_correo_e, t_clave, l_activo, t_identificacion, oid_tipo_documentacion,"
    		+ "	       t_nombre, t_apellido1, t_apellido2, f_nacimiento, es_docente, es_alumno, l_pendiente, "
    		+ "	       uid_azure, correo_aula, centro, uid_ldap, mail_ldap, lg_equidirectivo, listacargos, tipo_personal, "
    		+ "	       lg_tutor_unidad, curso_tutor_unidad, departamento, unidad_organizativa, ptotraemp, lg_comisioncoordpeda"
    		+ "	  FROM (SELECT usu.oid, usu.oid_persona, lower(usu.t_login) t_login, "
    		+ "	               nvl(usu.t_correo_e, emp.t_correo_externo) t_correo_e, "
    		+ "	               usu.t_clave t_clave, usu.l_activo,"
    		+ "	               doc.t_identificacion, doc.oid_tipo_documentacion,"
    		+ "	               emp.nombre t_nombre, emp.apellido1 t_apellido1, emp.apellido2 t_apellido2, emp.f_nacimiento,"
    		+ "	               'S' es_docente,"
    		+ "	               'N' es_alumno,"
    		+ "	               case WHEN us.ldap_uid is null then 'N' else 'S' end l_pendiente, "
    		+ "	               null uid_azure,"
    		+ "	               case WHEN us.ldap_uid is null then null else us.ldap_uid||'@educastillalamancha.es' end correo_aula,"
    		+ "	               cen.c_codigo centro,"
    		+ "	               us.ldap_uid uid_ldap,"
    		+ "	               emp.t_correo_ldap mail_ldap,	 "
    		+ "	               case WHEN exists (SELECT 1 "
    		+ "	                                   FROM delphos.tlcargosemp cemp ,"
    		+ "	                                        delphos.tlcargos car"
    		+ "	                                  WHERE car.c_cargo = cemp.c_cargo "
    		+ "	                                    AND cemp.x_empleado = pte.x_empleado "
    		+ "	                                    AND cemp.f_tomapos = pte.f_tomapos "
    		+ "	                                    AND car.lg_equdir = 1  "
    		+ "	                                    AND 1 = delphos.tlf_intersecper(cemp.f_tomposrea, cemp.f_cese, sysdate, sysdate)) then 1 else 0 end lg_equidirectivo,"
    		+ "	               (SELECT listagg(cemp.c_cargo, '|') lista_cargos"
    		+ "	                  FROM delphos.tlcargosemp cemp"
    		+ "	                 WHERE cemp.x_empleado = emp.x_empleado"
    		+ "	                   AND 1 = delphos.tlf_intersecper(cemp.f_tomposrea, cemp.f_cese, sysdate, sysdate)"
    		+ "	                 group by cemp.x_empleado) listacargos,"
    		+ "	               (SELECT decode(cen.l_delegacion, 'S', 3, decode(tpe.l_docente, 'S', 2, decode(tpe.l_funcionario , 'S', 1, 0 ))) tipo_personal"
    		+ "	                  FROM delphos.tltipper tpe,"
    		+ "	                       delphos.tlcentros cen"
    		+ "	                 WHERE tpe.x_tipoper = pte.x_tipoper"
    		+ "	                   AND cen.x_centro = pte.x_centro) tipo_personal,"
    		+ "	               case WHEN exists (SELECT 1"
    		+ "	                                    FROM delphos.tlunidadescen udc"
    		+ "	                                   WHERE udc.c_anno = pc_anno"
    		+ "	                                     AND udc.x_empleado = pte.x_empleado "
    		+ "	                                     AND udc.f_tomapos = pte.f_tomapos) then 1 else 0 end lg_tutor_unidad,"
    		+ "	               (SELECT listagg(omg.t_abreviatura , '|') cursos"
    		+ "	                  FROM delphos.tlunidadescen udc,"
    		+ "	                       delphos.tlofertasunidad ofu,"
    		+ "	                       delphos.tlofematrcen omc,"
    		+ "	                       delphos.tlofematrgen omg"
    		+ "	         		 WHERE ofu.x_unidad = udc.x_unidad"
    		+ "	                   AND udc.c_anno = pc_anno"
    		+ "	                   AND udc.x_empleado = pte.x_empleado "
    		+ "	                   AND udc.f_tomapos = pte.f_tomapos"
    		+ "	                   AND omc.x_ofertamatric = ofu.x_ofertamatric"
    		+ "	                   AND omg.x_ofertamatrig = omc.x_ofertamatrig) curso_tutor_unidad,"
    		+ "	               (SELECT listagg(t_abreviatura , '|')"
    		+ "	                  FROM (SELECT dep.t_abreviatura"
    		+ "	                          FROM delphos.tldepdengen dep, "
    		+ "	                               delphos.tldepartcen dpc,"
    		+ "	                               delphos.tlmiedepart mdp, "
    		+ "	                               delphos.tlcursoaca cua"
    		+ "	                         WHERE dep.x_depdengen = dpc.x_depdengen"
    		+ "	                           AND mdp.x_departcen = dpc.x_departcen"
    		+ "	                           AND dpc.c_anno = pc_anno"
    		+ "	                           AND dpc.x_centro = pte.x_centro"
    		+ "	                           AND mdp.x_empleado = emp.x_empleado"
    		+ "	                           AND mdp.f_tomapos = pte.f_tomapos"
    		+ "	                         union"
    		+ "	                        SELECT dep.t_abreviatura"
    		+ "	                          FROM delphos.tldepartamentos dep, "
    		+ "	                               delphos.tldepartcen dpc,   "
    		+ "	                               delphos.tlmiedepart mdp, "
    		+ "	                               delphos.tlcursoaca cua"
    		+ "	                         WHERE dep.x_departamento = dpc.x_departamento"
    		+ "	                           AND mdp.x_departcen = dpc.x_departcen"
    		+ "	                           AND dpc.c_anno = pc_anno"
    		+ "	                           AND dpc.x_centro = pte.x_centro"
    		+ "	                           AND mdp.x_empleado = pte.x_empleado"
    		+ "	                           AND mdp.f_tomapos = pte.f_tomapos)) departamento,"
    		+ "	               null unidad_organizativa,"
    		+ "	               pte.x_empleado||'|'||pte.f_tomapos ptotraemp,"
    		+ "	               case WHEN exists (SELECT mie.x_empleado"
    		+ "	                                   FROM delphos.tlorganoscen orc,"
    		+ "	                                        delphos.tlmiembros mie"
    		+ "	                                  WHERE pte.x_empleado = mie.x_empleado"
    		+ "	                                    AND pte.f_tomapos = mie.f_tomapos"
    		+ "	                                    AND orc.x_centro = pte.x_centro"
    		+ "	                                    AND orc.c_anno = pc_anno"
    		+ "	                                    AND mie.x_organo = orc.x_organo"
    		+ "	                                    AND orc.x_tiporgano = 3) then 1 else 0 end lg_comisioncoordpeda,"
    		+ "	                1 lg_nocturno"
    		+ "		FROM"
    		+ "			delphos.tlempleados emp,"
    		+ "			delphos.tlptotraemp pte,"
    		+ "			delphos.tlcentros cen,"
    		+ "			delphos_modacc.usuarios usu,"
    		+ "			delphos_modacc.documentaciones doc,"
    		+ "			delphos.tlusuarios us"
    		+ "		WHERE pte.x_empleado = emp.x_empleado"
    		+ "			AND pte.x_centro = cen.x_centro"
    		+ "			AND doc.oid_persona = usu.oid_persona "
    		+ "			AND decode(emp.l_tipide, 'D', 1, 'P', 2, 'N', 3, 4) = doc.oid_tipo_documentacion"
    		+ "	    AND emp.c_numide = doc.t_identificacion"
    		+ "			AND emp.x_empleado = us.x_empleado "
    		+ "			AND usu.oid = :p_oid"
    		+ "			AND 1 = tlf_intersecper(pte.f_tomaposrea, pte.f_cese, sysdate, sysdate)"
    		+ "		order by 23 desc, 21 desc --tipo_personal desc, lg_equidirectivo desc"
    		+ "		)"
    		+ "	WHERE rownum = 1" , nativeQuery = true)
    List<DatosParaUsuariosTProjection> datosParaUsuariosT(Integer p_oid);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO delphos_modacc.usuarios_t (oid, oid_persona, t_login, t_correo_e, t_clave, l_activo, t_identificacion,"
                   + "oid_tipo_documentacion, t_nombre, t_apellido1, t_apellido2, f_nacimiento, es_docente, es_alumno, l_pendiente,"
                   + "uid_azure, correo_aula, centro, uid_ldap, mail_ldap, lg_equidirectivo, listacargos, tipo_personal, lg_tutor_unidad,"
                   + "curso_tutor_unidad, departamento, unidad_organizativa, ptotraemp, lg_comisioncoordpeda)"
                   + "VALUES(:oid, :oidPersona, :tLogin, :tCorreoE, :tClave, :lActivo, :tIdentificacion, :oidTipoDocumentacion,"
                   + ":tNombre, :tApellido1, :tApellido2, :fNacimiento, :esDocente, :esAlumno, :lPendiente, :uidAzure, :correoAula,"
                   + ":centro, :uidLdap, :mailLdap, :lgEquidirectivo, :listaCargos, :tipoPersonal, :lgTutorUnidad, :cursoTutorUnidad,"
                   + ":departamento, :unidadOrganizativa, :ptotraemp, :lgComisioncoordpeda)", nativeQuery = true)
    void insertarUsuarioT(Long oid, Long oidPersona, String tLogin, String tCorreoE, String tClave, String lActivo,
                          String tIdentificacion, Long oidTipoDocumentacion, String tNombre, String tApellido1,
                          String tApellido2, Date fNacimiento, String esDocente, String esAlumno,
                          String lPendiente, Long uidAzure, String correoAula, String centro,
                          Long uidLdap, String mailLdap, Long lgEquidirectivo, Long listaCargos,
                          Long tipoPersonal, Long lgTutorUnidad, Long cursoTutorUnidad, Long departamento,
                          Long unidadOrganizativa, Long ptotraemp, Long lgComisioncoordpeda);
    
    //buscamos el identificador de una matrícula activa del alumno en centros educamosclm
    @Query(value = "SELECT max(x_matricula) INTO vx_matricula " 
            + "FROM tlalumnos alu, tlmatalu ma, tlcentrosconjuntos cc " 
            + "WHERE alu.c_tipide = :l_tipide "
            + "AND decode(alu.c_tipide, 'N', alu.c_numescolar, alu.c_numide) = :c_numide "
            + "AND alu.x_alumno = ma.x_alumno " 
            + "AND ma.c_anno = pc_anno " 
            + "AND nvl(ma.c_resultado, 99) > 1 " 
            + "AND ma.x_centro = cc.x_centro " 
            + "AND cc.c_conjunto = 'ACCESO_EDUCAMOSCLM'", nativeQuery = true)
    Long getMaxMatricula(String l_tipide, String c_numide);
    	 
    @Transactional
    @Modifying
    @Query(value = "UPDATE delphos_modacc.usuarios_t SET lg_nocturno = 1 WHERE oid = :oid", nativeQuery = true)
    void actualizarLgNocturnoPorOid(Long oid);
    
    /**
	 * PROCEDURE CAU_TOLEDO.tlp_cargartodosusuarioscau
	 * Actualiza o inserta los usuarios de Delphos en CAU
	 * incluye llamada a la eliminacion de usuarios duplicados (por tener documentacion con cero y sin cero)
     */
    
    @Query(value = "SELECT usu.x_usuario, usu.usuario, emp.c_numide " +
            "FROM delphos.tlusuarios usu, delphos.tlempleados emp " +
            "WHERE usu.x_empleado = emp.x_empleado " +
            "AND usu.l_activo = 'S' " +
            "AND usu.usuario IS NOT NULL", nativeQuery = true)
    List<cargarTodosUsuariosCAUPojection> findAllActiveUsuariosWithNotNullUsuario();
    
    //Procedimientos y funciones inyectadas
    
    /*@Procedure(procedureName = "delphos_segedu.gestion_usuarios_matricula.insertarusuariost(:vx_matricula)")
    void insertarusuariost(Long vx_matricula);
    
    @Procedure(procedureName = "TLF_ACTPERFILPGD('D', :X_EMPLEADO, :F_TOMAPOS, :C_CODIGO )")
    void TLF_ACTPERFILPGD(String c, Long X_EMPLEADO, Date F_TOMAPOS, String C_CODIGO );

    @Procedure(procedureName = "tlf_creausuariopas(:x_empleado, :f_tomapos)")
    void tlf_creausuariopas(Long X_EMPLEADO, Date F_TOMAPOS);
    
    @Procedure(procedureName = "tlf_creausuempperfil(:x_empleado, :x_perfil, :f_tomapos)")
    void tlf_creausuempperfil(Long X_EMPLEADO, Long x_perfil, Date F_TOMAPOS);
 
    @Procedure(procedureName = "cau_toledo.tlp_cargartodosusuarioscau")
    void tlp_cargartodosusuarioscau();
    
    @Procedure(procedureName = "tlf_borrarperfilaccesoBI")
    void tlf_borrarperfilaccesoBI();
    
    @Procedure(procedureName = "tlf_revisionusuariost")
    void tlf_revisionusuariost(); */
        
}
