package es.jccm.edu.proyectosfct.application.domain.gastos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class GastoEstadoFlujoSiguiente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="DSABREV")
	private String dsabrev;

	@Column(name="DSNOMBRE")
	private String dsnombre;
	
	@Column(name="FHINICIO")
	private Date fhinicio;
	
	@Column(name="FHFIN")
	private Date fhfin;
	
	@Column(name="TXAVISO")
	private String txaviso;
	
	@Column(name="ADJUNTO")
	private Integer adjunto;
	
	@Column(name="IDPERFIL")
	private Long idPerfil;
	
}