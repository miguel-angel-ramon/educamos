package es.jccm.edu.gestionidentidades.application.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.out.repository.InterfazUsuarioRepository;
import es.jccm.edu.gestionidentidades.application.domain.Ptotraemp;
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
import es.jccm.edu.gestionidentidades.application.ports.in.IInterfazUsuarioService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InterfazUsuarioService implements IInterfazUsuarioService{
	
	@Autowired
    private InterfazUsuarioRepository interfazUsuarioRepository;
	
	public int obtenerAnioActual() {
	    Calendar calendario = Calendar.getInstance();
	    return calendario.get(Calendar.YEAR);
	}
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional
	public Integer lanzaProcedureCompleto() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						     
				   CallableStatement function = connection.prepareCall("{ call DELPHOS.PKG_INTERFAZUSUARIO.lanza}");
				   	                 function.execute();
				   	               
							        return function;
							    }
			});

       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }		
	}
	
	/*@Async
    public CompletableFuture<Void> bloque1() {
        log.info("Bloque 1 ejecutado en el hilo: " + Thread.currentThread().getName());
        
        try {
			
			log.info("Se inicia interfaz de usuario.");
			log.info("Comenzando el cese de puestos de trabajos y cargos en centros no vigentes.");
			Session session = em.unwrap(Session.class);
			CallableStatement callableStatement =  session.doReturningWork(
			
				new ReturningWork<CallableStatement>() {
			
				    @Override
					public CallableStatement execute(Connection connection) throws SQLException {
							     
					  CallableStatement tlf_cesarptocar = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_cesarptocar}");
					  tlf_cesarptocar.registerOutParameter(1, Types.INTEGER);
					  tlf_cesarptocar.execute();
	        
					return tlf_cesarptocar;
					}		    
				});
	            	
			}catch(Exception e) {
				e.printStackTrace();
				log.error("Fallo indefinido en el interfaz de usuarios");
			}		
        
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> bloque2() {
        log.info("Bloque 2 ejecutado en el hilo: " + Thread.currentThread().getName());
        
        try {
			
			log.info("Comenzando el borrado de tlpueoriper  y tlperfilusu de perfiles 100 y 110.");
			Session session = em.unwrap(Session.class);
			CallableStatement callableStatement =  session.doReturningWork(
			
				new ReturningWork<CallableStatement>() {
			
				    @Override
					public CallableStatement execute(Connection connection) throws SQLException {
	   	                 
	   	              CallableStatement tlf_Borra_Perfiles_Cese = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_Borra_Perfiles_Cese}");
	   	              tlf_Borra_Perfiles_Cese.registerOutParameter(1, Types.INTEGER);
	   	              tlf_Borra_Perfiles_Cese.execute();
				   	               
					return tlf_Borra_Perfiles_Cese;
				}		    
				});
			
			}catch(Exception e) {
				e.printStackTrace();
				log.error("Fallo indefinido en el interfaz de usuarios");
			}		
        
        return CompletableFuture.completedFuture(null);
    }*/
	
	@Async
	@Transactional
    public CompletableFuture<Integer> bloque1() {
		log.info("Bloque 1 ejecutado en el hilo: " + Thread.currentThread().getName());
		log.info("Se inicia interfaz de usuario.");
		log.info("Comenzando el cese de puestos de trabajos y cargos en centros no vigentes.");
		Integer number = tlf_cesarptocarFuncionBBDD();	
		log.info("Comenzando el borrado de tlpueoriper de cargos borrados.");
		tlf_cesa_PueOriPer_cargoFuncionBBDD();
		log.info("Comenzando el borrado de tlpueoriper  y tlperfilusu de perfiles 100 y 110.");
		tlf_Borra_Perfiles_CeseFuncionBBDD();
        return CompletableFuture.completedFuture(number);
    }

    @Async
    @Transactional
    public CompletableFuture<Integer> bloque2() {
    	log.info("Bloque 2 ejecutado en el hilo: " + Thread.currentThread().getName());
    	log.info("Comenzando el borrado de usuarios.");
    	Integer number = tlf_borpercarptoFuncionBBDD();
		log.info("Comenzando la carga de la interface de usuarios por puestos.");
		tlf_empptoperfilFuncionBBDD();
		log.info("Comenzando la carga de la interface de usuarios por cargos.");
		tlf_empcargoperfilFuncionBBDD();
        return CompletableFuture.completedFuture(number);
    }
	
	@Override
	@Transactional
	public void lanzaProcedurePorFunciones() {
		
		try {
			
			log.info("Se inicia interfaz de usuario.");
			log.info("Comenzando el cese de puestos de trabajos y cargos en centros no vigentes.");
			tlf_cesarptocarFuncionBBDD();	
			log.info("Comenzando el borrado de tlpueoriper de cargos borrados.");
			tlf_cesa_PueOriPer_cargoFuncionBBDD();
			log.info("Comenzando el borrado de tlpueoriper  y tlperfilusu de perfiles 100 y 110.");
			tlf_Borra_Perfiles_CeseFuncionBBDD();
			log.info("Comenzando el borrado de usuarios.");
			tlf_borpercarptoFuncionBBDD();
			log.info("Comenzando la carga de la interface de usuarios por puestos.");
			tlf_empptoperfilFuncionBBDD();
			log.info("Comenzando la carga de la interface de usuarios por cargos.");
			tlf_empcargoperfilFuncionBBDD();
			log.info("Comenzando la carga de usuarios en CAU.");
			tlp_cargartodosusuarioscauFuncionBBDD();
			log.info("Comenzando la carga de perfil de acceso BI.");
			tlf_crearperfilaccesoBIFuncionBBDD();
			log.info("Comenzando el borrado de perfiles de acceso BI.");
			tlf_borrarperfilaccesoBIFuncionBBDD();
			log.info("Comenzando revision usuarioT");
			tlf_revisionusuariostFuncionBBDD();
			log.info("Finaliza el interfaz de usuario.");
			}catch(Exception e) {
				e.printStackTrace();
				log.error("Fallo indefinido en el interfaz de usuarios");
			}				
	}
	
	@Transactional
	public void lanza() {
		
		try {
		
		//En este momento se encuentra de forma híbrida con la lógica migrada y las funciones de la BBDD hasta estabilizarlo
		log.info("Se inicia interfaz de usuario.");
		log.info("Comenzando el cese de puestos de trabajos y cargos en centros no vigentes.");
		tlf_cesarptocar();		
		log.info("Comenzando el borrado de tlpueoriper de cargos borrados.");
		tlf_cesa_PueOriPer_cargo();
		log.info("Comenzando el borrado de tlpueoriper  y tlperfilusu de perfiles 100 y 110.");
		tlf_Borra_Perfiles_Cese();
		log.info("Comenzando el borrado de usuarios.");
		tlf_borpercarpto();
		log.info("Comenzando la carga de la interface de usuarios por puestos.");
		tlf_empptoperfilFuncionBBDD();
		log.info("Comenzando la carga de la interface de usuarios por cargos.");
		tlf_empcargoperfilFuncionBBDD();
		log.info("Comenzando la carga de usuarios en CAU.");
		tlp_cargartodosusuarioscauFuncionBBDD();
		log.info("Comenzando la carga de perfil de acceso BI.");
		tlf_crearperfilaccesoBIFuncionBBDD();
		log.info("Comenzando el borrado de perfiles de acceso BI.");
		tlf_borrarperfilaccesoBIFuncionBBDD();
		log.info("Comenzando revision usuarioT");
		tlf_revisionusuariostFuncionBBDD();
		log.info("Finaliza el interfaz de usuario.");
		}catch(Exception e) {
			e.printStackTrace();
			log.error("Fallo indefinido en el interfaz de usuarios");
		}		
	}
	
	public void tlf_revisionusuariost(){
		
		int anioActual = obtenerAnioActual();
		Integer p_oid = 1; //TODO De donde se saca?
		
		//Cursores
		List<Long> c_datos_emp = interfazUsuarioRepository.findOidByFilters();
		List<DatosAlumnosProjection> c_datos_alu = interfazUsuarioRepository.findOidAndL_TipIdeAndC_NumIdeByPcAnno(anioActual);
		List<DatosParaUsuariosTProjection> c_info_usuario_emp = interfazUsuarioRepository.datosParaUsuariosT(p_oid);
		
		log.info("'C', 'USU_REVISIONUSUARIOT', 'Comenzando revision usuarioT'");
		
		for (Long r_datos_emp : c_datos_emp) {
			for (DatosParaUsuariosTProjection r_c_info_usuario_emp : c_info_usuario_emp) {
				try {
					interfazUsuarioRepository.insertarUsuarioT(r_datos_emp, r_c_info_usuario_emp.getOid_persona(), r_c_info_usuario_emp.getT_login(), r_c_info_usuario_emp.getT_correo_e(),
					r_c_info_usuario_emp.getT_clave(), r_c_info_usuario_emp.getL_activo(), r_c_info_usuario_emp.getT_identificacion(), r_c_info_usuario_emp.getOid_tipo_documentacion(),
					r_c_info_usuario_emp.getT_nombre(), r_c_info_usuario_emp.getT_apellido1(), r_c_info_usuario_emp.getT_apellido2(), r_c_info_usuario_emp.getF_nacimiento(),
					r_c_info_usuario_emp.getEs_docente(), r_c_info_usuario_emp.getEs_alumno(), r_c_info_usuario_emp.getL_pendiente(), r_c_info_usuario_emp.getUid_azure(), r_c_info_usuario_emp.getCorreo_aula(),
					r_c_info_usuario_emp.getCentro(), r_c_info_usuario_emp.getUid_ldap(), r_c_info_usuario_emp.getMail_ldap(), r_c_info_usuario_emp.getLg_equidirectivo(),
					r_c_info_usuario_emp.getListacargos(), r_c_info_usuario_emp.getTipo_personal(), r_c_info_usuario_emp.getLg_tutor_unidad(), r_c_info_usuario_emp.getCurso_tutor_unidad(),
					r_c_info_usuario_emp.getDepartamento(), r_c_info_usuario_emp.getUnidad_organizativa(), r_c_info_usuario_emp.getPtotraemp(), r_c_info_usuario_emp.getLg_comisioncoordpeda());
		    
				} catch (Exception e) {
					log.error("Error al dar de alta en usuarios_t el oid " + r_datos_emp);
				}
							}
		}
		
		for (DatosAlumnosProjection r_datos_alu : c_datos_alu) {
			
			try {
				
				Long vx_matricula = interfazUsuarioRepository.getMaxMatricula(r_datos_alu.getL_tipide(), r_datos_alu.getC_numide());
				//interfazUsuarioRepository.insertarusuariost(vx_matricula);
				interfazUsuarioRepository.actualizarLgNocturnoPorOid(r_datos_alu.getOid());
			    
			} catch (Exception e) {
				log.error("Error al dar de alta usuarios que no existen en usuarios_t en tlf_revisionusuariost");
			}			
			
		}		
	}
	
	public void tlf_cesarptocar() {
		
		//Cursores
		List<DatosCentroNoVigenteProjection> c_cen = interfazUsuarioRepository.findCentrosNoVigentes();
		
		for (DatosCentroNoVigenteProjection vc_cen : c_cen) {
			
			try {
				
				interfazUsuarioRepository.updatePuestosActivos(vc_cen.getX_centro(), vc_cen.getF_fin_vigen());
				interfazUsuarioRepository.updateCargosActivos(vc_cen.getX_centro(), vc_cen.getF_fin_vigen());
			    
			} catch (Exception e) {
				log.error("Fallo indefinido en tlf_cesarptocar");
			}
		}		
	}
	
	public void tlf_cesa_PueOriPer_cargo() {
		
		//Cursores
		List<RegistrosCargosInexistentesProjection> c_pueoriper = interfazUsuarioRepository.findRegistrosCargosInexistentesToDelete();
				
		for (RegistrosCargosInexistentesProjection pueoriper_r : c_pueoriper) {			
			try {
				
				interfazUsuarioRepository.DeletePueOriPer(pueoriper_r.getX_empleado(), pueoriper_r.getF_tomapos(), pueoriper_r.getX_perfil());
				interfazUsuarioRepository.DeletePerfilUsu(pueoriper_r.getX_usuario(), pueoriper_r.getX_perfil());
			    
			} catch (Exception e) {
				log.error("Fallo indefinido en tlf_cesa_PueOriPer_cargo");
			}
		}		
	}
	
	public void tlf_Borra_Perfiles_Cese() {
		
		//Cursores
		List<PerfilesAEliminarProjection> cPerfiles = interfazUsuarioRepository.findPerfilesCese();
		
		for (PerfilesAEliminarProjection rPerfiles : cPerfiles) {
			try {
				//interfazUsuarioRepository.TLF_ACTPERFILPGD("D", rPerfiles.getX_empleado(), rPerfiles.getF_tomapos(), rPerfiles.getC_codigo());			    
			} catch (Exception e) {
				log.error("Fallo indefinido en tlf_Borra_Perfiles_Cese");
			}
		}		
	}
	
	public void tlf_borpercarpto() {
		
		//Cursores
		List<PuestoTrabajoCesadoProjection> c_cesadospto = interfazUsuarioRepository.findPuestosCesados();
		List<CargosCesadosProjection> c_cargoscesados = interfazUsuarioRepository.findCargosCesados();
		List<PerfilesBorradosProjection> c_borrados = interfazUsuarioRepository.findPerfilesBorrados();
		List<UsuarioSinPerfilAsociadoProjection> c_borrarusu = interfazUsuarioRepository.findUsuariosSinPerfiles();
		
		for (PuestoTrabajoCesadoProjection cesados_r : c_cesadospto) {
			
			try {
				
				interfazUsuarioRepository.DeleteByEmpleadoAndFechaAndPerfil(cesados_r.getX_empleado(), cesados_r.getF_tomapos(), cesados_r.getX_perfil());
				
				int temp_n = interfazUsuarioRepository.countByEmpleadoAndPerfil(cesados_r.getX_empleado(), cesados_r.getX_perfil());
				
				if(temp_n == 0) {
					interfazUsuarioRepository.DeleteFromTlusuariozona(cesados_r.getX_usuario(), cesados_r.getX_perfil());
					interfazUsuarioRepository.DeleteFromTlperfilusu(cesados_r.getX_usuario(), cesados_r.getX_perfil());
				}
				
				int temp_n2 = interfazUsuarioRepository.countFromTlcargosemp(cesados_r.getX_empleado(), cesados_r.getF_tomapos(), cesados_r.getX_perfil());
				
				if(temp_n2 == 0) {
					interfazUsuarioRepository.DeleteFromTlperfilusua(cesados_r.getX_perfil(), cesados_r.getX_usuario());
				}
			    
			} catch (Exception e) {
				log.error("No se ha eliminado el perfil " + cesados_r.getX_perfil() + " al usuario "+ cesados_r.getX_usuario() + " porque existen datos de zona");
			}	
		}
		
		for (CargosCesadosProjection cargoscesados_r : c_cargoscesados) {
			
			try {
				interfazUsuarioRepository.deleteTlpueoriperByXEmpleadoAndFTomaposAndXPerfil(cargoscesados_r.getX_empleado(), cargoscesados_r.getF_tomapos(), cargoscesados_r.getX_perfil());
				
				int temp_n3 = interfazUsuarioRepository.countByXEmpleadoAndFTomaposAndXPerfil(cargoscesados_r.getX_empleado(), cargoscesados_r.getF_tomapos(), cargoscesados_r.getX_perfil());
				
				if(temp_n3 == 0) {
					interfazUsuarioRepository.DeleteByPerfilAndUsuario(cargoscesados_r.getX_perfil(), cargoscesados_r.getX_usuario());
				}			    
			} catch (Exception e) {
				log.error("Error al borrar cargos cesados");
			}			
		}
		
		for (PerfilesBorradosProjection borrados_r : c_borrados) {
			
			try {
				interfazUsuarioRepository.DeleteByXPerfil(borrados_r.getX_perfil());
				interfazUsuarioRepository.DeleteByXPerfilAndLAsigman(borrados_r.getX_perfil());					    
			} catch (Exception e) {
				log.error("No se ha eliminado el perfil "+borrados_r.getX_perfil()+" a todos los usuarios porque estan originados por puestos de trabajo y cargos");
			}		
		}
		
		for (UsuarioSinPerfilAsociadoProjection borrarusu_r : c_borrarusu) {
			
			try {				
				interfazUsuarioRepository.desactivarUsuario(borrarusu_r.getX_usuario());	
			} catch (Exception e) {
				log.error("Fallo indefinido en tlf_borpercarpto");
			}		
		}		
	}
	
	public void tlf_empptoperfil() {
		
		//Cursores
		List<PuestoTrabajoActivoProjection> c_emppto = interfazUsuarioRepository.findEmpleadosByUsuario();
		
		for (PuestoTrabajoActivoProjection emppto_r : c_emppto) {
			if(emppto_r.getL_docente() == 'S') {				
				try {
					//interfazUsuarioRepository.tlf_creausuempperfil(emppto_r.getX_empleado(), emppto_r.getX_perfil(), emppto_r.getF_tomapos());
					
				} catch (Exception e) {
					log.error("Fallo indefinido en tlf_empptoperfil");
				}
			}else if(emppto_r.getL_docente() == 'N') {
				try {
					//interfazUsuarioRepository.tlf_creausuariopas(emppto_r.getX_empleado(), emppto_r.getF_tomapos());
				} catch (Exception e) {
					log.error("Fallo indefinido en tlf_empptoperfil");
				}
			}			
		}
	}
	
	public void tlf_empcargoperfil() {
		
		//Cursores
		List<CargosActivosFechaProjection> c_cargosemp = interfazUsuarioRepository.findEmployeeDataByCargoId();
		
		for (CargosActivosFechaProjection cargosemp_r : c_cargosemp) {
			try {
				//interfazUsuarioRepository.tlf_creausuempperfil(cargosemp_r.getX_empleado(), cargosemp_r.getX_perfil(), cargosemp_r.getF_tomapos());
			} catch (Exception e) {
				log.error("Fallo indefinido en tlf_empptoperfil");
			}
		}		
	}
	
	public void tlp_cargartodosusuarioscau() {
		try {
			tlp_cargartodosusuarioscauFuncionBBDD();
			
			
			
			} catch (Exception e) {
			log.error("Fallo indefinido en tlp_cargartodosusuarioscau");
		}
	}
	
	public void p_cargarusuarioscau_dni() {
		
		//cursores
		List<cargarTodosUsuariosCAUPojection> usuariosCAU = interfazUsuarioRepository.findAllActiveUsuariosWithNotNullUsuario();
		
		try {
			for (cargarTodosUsuariosCAUPojection cargarTodosUsuariosCAUPojection : usuariosCAU) {
				p_cargarusuarioscau_dniBBDD(cargarTodosUsuariosCAUPojection.getNumide());
			}
				
			} catch (Exception e) {
			log.error("Fallo indefinido en p_cargarusuarioscau_dni");
		}
		
		
	}
	
	public void tlf_crearperfilaccesoBI() {
		
		//Cursores
		UsuarioActivoPerfilAsociadoProjection data = interfazUsuarioRepository.findUsuariosByFiltro();
		Long checkexisteperfilusu = interfazUsuarioRepository.findByXUsuarioAndXPerfil(data.getX_usuario(), data.getX_perfil());
		//TODO cursor checkexistepueoriper de donde sale la fecha
		//Long checkexistepueoriper = interfazUsuarioRepository.findX_PueOriPer(data.getX_empleado(), FECHA, data.getX_usuario(), data.getX_perfil());
		List<Ptotraemp> ptotraemps = interfazUsuarioRepository.findByXEmpleadoAndXPerfil(data.getX_empleado(), data.getX_perfil());
		
		Long vgx_perfil_BI = interfazUsuarioRepository.findX_PerfilByC_Codigo();
		
		//TODO terminar loop DATA
		
		for (Ptotraemp r_ptotraemps : ptotraemps) {
			Long checkexistepueoriper = interfazUsuarioRepository.findX_PueOriPer(r_ptotraemps.getEmpleadoSustituye(), r_ptotraemps.getFechaTomaPos(), r_ptotraemps.getUsuario(), "ABI");
		
			if(checkexistepueoriper ==  null || "".equals(checkexistepueoriper)) {
				try {
					interfazUsuarioRepository.insertIntoTlpueoriperCrearperfilaccesoBI(r_ptotraemps.getEmpleadoSustituye(), r_ptotraemps.getFechaTomaPos(), data.getX_usuario(), vgx_perfil_BI);
				} catch (Exception e) {
					log.error("Fallo indefinido en tlf_crearperfilaccesoBI");
				}
			}		
		}
		
	}
	
	public void tlf_borrarperfilaccesoBI() {
		
		//Si se consigue implementar la llamada a la función desde aquí se puede usar:
		// interfazUsuarioRepository.tlf_borrarperfilaccesoBI();
		
		//Cursores
		List<UsuarioAccesoBISinPerfilAsociadoProjection> data = interfazUsuarioRepository.findX_EmpleadoAndF_Tomapos();
		
		List <Long> vgx_perfil_BI = interfazUsuarioRepository.findX_PerfilByC_Codigos();
		
		for (UsuarioAccesoBISinPerfilAsociadoProjection r_data : data) {
			Long v_x_usuario = interfazUsuarioRepository.findX_UsuarioByX_Empleados(r_data.getX_empleado());
			//TODO de donde salen el X usuario y el CodigoPerfil
			//List<Long> checkexistepueoriper = findX_PueOriPerCursorCheckexistepueoriper(r_data.getX_empleado(), r_data.getF_tomapos(), r_data.get)
		}				
	}

	public void tlf_creausuempperfil (Long x_empleado, Long x_perfil, Date f_tomapos) {
		
		//Cursores
		Long ptotraempDni = interfazUsuarioRepository.getNumideByEmpleadoAndTomapos(x_empleado , f_tomapos);
		List<ExistePerfilProjection> c_perfil = interfazUsuarioRepository.findPerfilByXPerfil(x_perfil);
		UsuarioAsociadoEmpleadoProjection usuario = interfazUsuarioRepository.getUsuarioActivo(x_empleado);
		//TODO cursor cursor c_perfilusu (px_usuario number, px_perfil number) de donde sale px_usuario
		//TODO cursor c_pueoriper (px_usuario number, px_perfil number) de donde sale px_usuario
	
		if(ptotraempDni == null || "".equals(ptotraempDni)){
			log.info("El puesto de trabajo no existe");
		}
		
		if(c_perfil.isEmpty()){
			log.info("El perfil no existe o no esta activo");
		}
		
		Long vgx_perfil_BI = interfazUsuarioRepository.getPerfil();
	
	}
	

	
	/**
	* A contunuación se encuentran los métodos que ejecutan las funciones individualmente de la BBDD
	*/
	
	
	@Override
	@Transactional
	public Integer tlf_cesarptocarFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						     
				  CallableStatement tlf_cesarptocar = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_cesarptocar}");
				  tlf_cesarptocar.registerOutParameter(1, Types.INTEGER);
				  tlf_cesarptocar.execute();
        
				return tlf_cesarptocar;
				}		    
			});
       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }       
	}
	
	
	@Override
	@Transactional
	public Integer tlf_cesa_PueOriPer_cargoFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
                 
   	              CallableStatement tlf_cesa_PueOriPer_cargo = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_cesa_PueOriPer_cargo}");
   	              tlf_cesa_PueOriPer_cargo.registerOutParameter(1, Types.INTEGER);
   	              tlf_cesa_PueOriPer_cargo.execute();
   	                 				   	               
				return tlf_cesa_PueOriPer_cargo;
				}		    
			});
       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }       
	}
	
	
	@Override
	@Transactional
	public Integer tlf_Borra_Perfiles_CeseFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
   	                 
   	              CallableStatement tlf_Borra_Perfiles_Cese = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_Borra_Perfiles_Cese}");
   	              tlf_Borra_Perfiles_Cese.registerOutParameter(1, Types.INTEGER);
   	              tlf_Borra_Perfiles_Cese.execute();
			   	               
				return tlf_Borra_Perfiles_Cese;
			}		    
			});
       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }       
	}
	
	
	@Override
	@Transactional
	public Integer tlf_borpercarptoFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
        
   	              CallableStatement tlf_borpercarpto = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_borpercarpto}");
   	              tlf_borpercarpto.registerOutParameter(1, Types.INTEGER);
   	              tlf_borpercarpto.execute();
   
   	              return tlf_borpercarpto;
				}		    
			});
       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }      
	}
	
	
	@Override
	@Transactional
	public Integer tlf_empptoperfilFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
   	                 
   	              CallableStatement tlf_empptoperfil = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_empptoperfil}");
   	              tlf_empptoperfil.registerOutParameter(1, Types.INTEGER);
   	              tlf_empptoperfil.execute();
   	                 
   	              return tlf_empptoperfil;
				}		    
			});

       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }      
	}
	
	
	@Override
	@Transactional
	public Integer tlf_empcargoperfilFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						          
   	              CallableStatement tlf_empcargoperfil = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_empcargoperfil}");
   	              tlf_empcargoperfil.registerOutParameter(1, Types.INTEGER);
   	              tlf_empcargoperfil.execute();   	                 
   	              
				return tlf_empcargoperfil;
				}		    
			});

       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }      
	}
	
	
	@Override
	@Transactional
	public void tlp_cargartodosusuarioscauFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
   	                 
   	              CallableStatement tlp_cargartodosusuarioscau = connection.prepareCall("{ call CAU_TOLEDO.tlp_cargartodosusuarioscau}");
   	              tlp_cargartodosusuarioscau.execute();
 	               
				return tlp_cargartodosusuarioscau;
			}		    
			});    
	}
	
	
	@Override
	@Transactional
	public void p_cargarusuarioscau_dniBBDD(String numide) {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
   	                 
   	              CallableStatement p_cargarusuarioscau_dni = connection.prepareCall("{ call CAU_TOLEDO.p_cargarusuarioscau_dni("+numide+")}");
   	           p_cargarusuarioscau_dni.execute();
 	               
				return p_cargarusuarioscau_dni;
			}		    
			});    
	}
	
	@Override
	@Transactional
	public Integer tlf_crearperfilaccesoBIFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
    
   	              CallableStatement tlf_crearperfilaccesoBI = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_crearperfilaccesoBI}");
   	              tlf_crearperfilaccesoBI.registerOutParameter(1, Types.INTEGER);
   	              tlf_crearperfilaccesoBI.execute();
   	                 
   	              return tlf_crearperfilaccesoBI;
				}		    
			});
       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }
       
	}
	
	
	@Override
	@Transactional
	public Integer tlf_borrarperfilaccesoBIFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
   	                 
   	              CallableStatement tlf_borrarperfilaccesoBI = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_borrarperfilaccesoBI}");
   	              tlf_borrarperfilaccesoBI.registerOutParameter(1, Types.INTEGER);
   	              tlf_borrarperfilaccesoBI.execute();
   	               
				return tlf_borrarperfilaccesoBI;
			    }		    
			});
       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }      
	}
	
	
	@Override
	@Transactional
	public Integer tlf_revisionusuariostFuncionBBDD() {
		
		Session session = em.unwrap(Session.class);
		CallableStatement callableStatement =  session.doReturningWork(
		
			new ReturningWork<CallableStatement>() {
		
			    @Override
				public CallableStatement execute(Connection connection) throws SQLException {
						        	                 
   	              //No ejecutar para pruebas tarda demasiado
   	              CallableStatement tlf_revisionusuariost = connection.prepareCall("{ ? = call DELPHOS.PKG_INTERFAZUSUARIO.tlf_revisionusuariost}");
   	              tlf_revisionusuariost.registerOutParameter(1, Types.INTEGER);
   	              tlf_revisionusuariost.execute();
				   	               
				return tlf_revisionusuariost;
			    }		    
			});
       try {
           return callableStatement.getInt(1);
           } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	       }       
	}
	
}
