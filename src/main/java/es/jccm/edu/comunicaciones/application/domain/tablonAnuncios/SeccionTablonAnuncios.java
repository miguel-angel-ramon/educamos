package es.jccm.edu.comunicaciones.application.domain.tablonAnuncios;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table (name="TLTABANUCEN", schema="DELPHOS_SEGEDU")
public class SeccionTablonAnuncios implements Serializable{
	private static final long serialVersionUID=1L;
	
	@Id
	@Column (name="X_SECTABANUCEN")
	private Long id;
	
	@Column (name="X_CENTRO")
	private Long idCentro;
	
	@Column (name="D_NOMBRESEC")
	private String descripcion;
	
	

	
	

}
