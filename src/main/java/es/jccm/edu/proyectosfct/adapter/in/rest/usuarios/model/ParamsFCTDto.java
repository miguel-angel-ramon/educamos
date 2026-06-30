package es.jccm.edu.proyectosfct.adapter.in.rest.usuarios.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatosUsuarioDto", description = "Descripcion para el modelo de datos de usuario")
public class ParamsFCTDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del xempleado")
	private String x_empleado;
	
	@Schema(description = "date ftomapos")
	String f_tomapos;
	
	@Schema(description = "id del centro")	
	String x_centro;
	
	@Schema(description = "num anno")	
	String c_anno;
	
	@Schema(description = "id del usuario")
	String x_usuario;	
	
	@Schema(description = "id del perfil")
	String x_perfil;	
	
	@Schema(description = "Código perfil")
	String codigo_perfil;
	
	@Schema(description = "Nombre sustituto")
	String sustituto;
	
	@Schema(description = "Id tutor FCT")
	String idtutorfctdual;
	
	@Schema(description = "Id tutor FCT sustituto")
	String idtutorfctdualsus;
	
	@Schema(description = "Modulo FCT")
	String s_modulo;
	
	@Schema(description = "Id alumnado convenio")
	String x_ident;
	
	@Schema(description = "Es director")
	String esDirector;
	
	@Schema(description = "Es jefe de departamento")
	String esJefeDep;
	
	@Schema(description = "Es coordinador de ciclo")
	String esCoorCiclo;
	
	@Schema(description = "Código provincia")
	String c_provincia;

}
