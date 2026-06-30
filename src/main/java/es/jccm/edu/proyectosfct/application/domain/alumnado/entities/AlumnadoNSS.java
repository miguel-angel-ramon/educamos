package es.jccm.edu.proyectosfct.application.domain.alumnado.entities;

import java.io.Serializable;

import javax.persistence.Column;
import lombok.Data;

@Data 
public class AlumnadoNSS implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="Tipo documento")
	private String ipf;
	
	@Column(name="Provincia")
	private String prov;
	
	@Column(name="Primer apellido")
	private String ap1;
	
	@Column(name="Segundo apellido")
	private String ap2;
	
	@Column(name="Nombre")
	private String nom;
	
	@Column(name="Fecha nacimiento")
	private String fnac;
	
	@Column(name="Nacionalidad")
	private String nacionalidad;
	
	@Column(name="Nombre padre")
	private String nombrepadre;
	
	@Column(name="Nombre madre")
	private String nombremadre;
	
	@Column(name="Prefijo móvil")
	private String movilpre;
	
	@Column(name="Móvil")
	private String movil;
	

}
