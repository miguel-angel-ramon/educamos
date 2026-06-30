package es.jccm.edu.gestionidentidades.application.ports.in;

/**
 * Manda Mail de recuperación de clave desde un sistema federado
 * 
 * @author jesus
 *
 */
public interface MandarMailRecuperacionClaveUseCase {

	String mandarEmailRecuperacionClave(Long oidUsuario,String email); 
}
