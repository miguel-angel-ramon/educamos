package es.jccm.edu.gestionidentidades.application.ports.in;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

public interface IInterfazUsuarioService {

	void lanza();

	void tlf_creausuempperfil(Long x_empleado, Long x_perfil, Date f_tomapos);

	void tlf_borrarperfilaccesoBI();

	void tlf_crearperfilaccesoBI();

	void tlp_cargartodosusuarioscau();

	void tlf_empcargoperfil();

	void tlf_empptoperfil();

	void tlf_borpercarpto();

	void tlf_Borra_Perfiles_Cese();

	void tlf_cesa_PueOriPer_cargo();

	void tlf_cesarptocar();

	void tlf_revisionusuariost();

	Integer lanzaProcedureCompleto();

	void lanzaProcedurePorFunciones();

	/**
	* A contunuación se encuentran los métodos que ejecutan las funciones individualmente de la BBDD
	*/
	Integer tlf_cesarptocarFuncionBBDD();

	Integer tlf_cesa_PueOriPer_cargoFuncionBBDD();

	Integer tlf_Borra_Perfiles_CeseFuncionBBDD();

	Integer tlf_borpercarptoFuncionBBDD();

	Integer tlf_empptoperfilFuncionBBDD();

	Integer tlf_empcargoperfilFuncionBBDD();

	void tlp_cargartodosusuarioscauFuncionBBDD();

	Integer tlf_crearperfilaccesoBIFuncionBBDD();

	Integer tlf_borrarperfilaccesoBIFuncionBBDD();

	Integer tlf_revisionusuariostFuncionBBDD();
	void p_cargarusuarioscau_dniBBDD(String numide);
	
	CompletableFuture<Integer> bloque1();

	CompletableFuture<Integer> bloque2();

}
