package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
@Entity
@Table(name = "SOL_REC_CLAVE" , schema = "DELPHOS_MODACC")
public class SolicitudRecuperacionClave {
	
	private String user; 
	
	@Id
	@NotNull
	@Column(name = "ID")
	private Long id; 
	
	@NotNull
	@Column(name = "FECHA")
	private Date fecha; 
	
	@NotNull
	@Column(name = "T_TOKEN")
	private String token; 

}
