package es.jccm.edu.simulacion.application.domain.usuarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CentroUsuarioList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codCentro;
	
	private Long idCentro;
	
	private String denominacionCentro;
	
	private String nombreCentro;
	
	private Long idPerfil;
	
	private Date fechaTomaPosesion;

}
