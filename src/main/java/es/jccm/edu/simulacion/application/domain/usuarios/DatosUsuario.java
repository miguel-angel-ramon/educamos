package es.jccm.edu.simulacion.application.domain.usuarios;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class DatosUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idUsuario;
	
	private String xUsuario;
	
	private String idEmpleado;
	
	private String numide;
	
	private String nombre;
	
	private byte[] foto;
	
	private String apellido1;
	
	private String apellido2;

	private String email;
	
	private String perfil_def;
	
	private String centro_def;
	
	private Boolean tour;
	
	private Boolean tourEvaluacion;
    
    private Boolean piloto;

	@ElementCollection(targetClass=Long.class)
	private List<Long> centroAnterior;

	@ElementCollection(targetClass=Long.class)
	private List<Long> anyosAnteriores;
}
