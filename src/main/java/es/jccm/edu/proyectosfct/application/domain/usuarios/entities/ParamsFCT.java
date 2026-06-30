package es.jccm.edu.proyectosfct.application.domain.usuarios.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ParamsFCT implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String x_empleado;
	
	String f_tomapos;
	
	String x_centro;
	
	String c_anno;
	
	String x_usuario;	
	
  	String x_perfil;
	
	String codigo_perfil;

	String sustituto;
	
	String idtutorfctdual;
	
	String idtutorfctdualsus;
	
	String s_modulo;
	
	String x_ident;
	
	String esDirector;
	
	String esJefeDep;
	
	String esCoorCiclo;
	
	String c_provincia;
}
