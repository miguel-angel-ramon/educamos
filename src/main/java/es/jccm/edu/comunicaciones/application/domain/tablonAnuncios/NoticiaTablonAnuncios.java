package es.jccm.edu.comunicaciones.application.domain.tablonAnuncios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
@Entity
@Table (name="TLNOTSECTABANUCEN", schema="DELPHOS_SEGEDU")
public class NoticiaTablonAnuncios implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="X_NOTSECTABANUCEN")
	private Long  id;
	
	@ManyToOne (optional=false , fetch=FetchType.LAZY)
	@JoinColumn(name="X_SECTABANUCEN")
	private SeccionTablonAnuncios seccion;
	
//	@Column(name="X_SECTABANUCEN")
//	private Integer idSeccion;
	
	@Column(name="X_NOTSOLPUB")
	private Integer idSolicitud;
	
	@Column(name="D_TITULO")
	private String titulo;
	
	@Column(name="T_CUERPO")
	private String cuerpo;
	@JsonFormat(pattern="dd-MM-yyyy",timezone="GMT+2")
	@Column(name="F_PUBLICACION")
	private Date fechaPublicacion;
	
	@Column(name="F_FIN_VIGENCIA")
	private Date fechaFinVigencia;
	
	@Column(name="L_NOTENVWEB")
	private String enviadaWeb;

	@Column(name="L_DOCUMENTO")
	private String documento;
	
	
	
	
	
	

}
