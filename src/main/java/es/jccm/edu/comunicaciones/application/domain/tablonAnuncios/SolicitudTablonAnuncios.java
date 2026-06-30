package es.jccm.edu.comunicaciones.application.domain.tablonAnuncios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name="TLNOTSOLPUB",schema="DELPHOS_SEGEDU")
public class SolicitudTablonAnuncios implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="X_NOTSOLPUB")
	private Long id;
	
	@Column(name="X_USUARIO_SOLICITA")
	private Long idUsuario;
	
	@JsonFormat(pattern="dd-MM-yyyy",timezone="GMT+2")
	@Column(name="F_SOLICITUD")
	private Date fechaSolicitud;
	
	@Column(name="T_MOTIVO")
	private String motivo;
	
	@Column (name="T_MOTIVO_DENEGACION")
	private String motivoDenegacion;
	
	@Column(name="L_PUBLICADA")
	private String publicada;
	
	

}
