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
public class DatosVigentePFE implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="dsCurso")
	private String dsCurso;

	@Column(name="dsAlcance")
	private String dsAlcance;	

	@Column(name="dsModalidad")
	private String dsModalidad;	

	@Column(name="dsAnnoIni")
	private String dsAnnoIni;	
	
	@Column(name="dsAnnoFin")
	private String dsAnnoFin;	

	@Column(name="txAutor")
	private String txAutor;
	
	@Column(name="idRodal")
	private String idRodal;
	
	@Column(name="fichero")
	private String fichero;

}
