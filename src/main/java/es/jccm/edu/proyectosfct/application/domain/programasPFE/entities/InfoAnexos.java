package es.jccm.edu.proyectosfct.application.domain.programasPFE.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class InfoAnexos implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="idRodal")
	private String idRodal;

	@Column(name="fichero")
	private String fichero;	
	
	@Column(name="tipo")
	private String tipo;

}
