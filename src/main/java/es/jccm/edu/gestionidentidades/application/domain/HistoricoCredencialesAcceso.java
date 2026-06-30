package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "HISTORICO_CREDENCIALES_ACCESO" , schema = "DELPHOS_MODACC")
public class HistoricoCredencialesAcceso {

	
	@Id
    @Column(name = "OID")
	private Long id;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OID_USUARIO", nullable = false)
	private Usuario idUsuario;
	
	
	@Column(name = "T_LOGIN", nullable = false)
	private String login;
	
	@Column(name = "T_CLAVE", nullable = false)
	private String clave;
	
	@Column(name = "F_BAJA", nullable = false)
	private Date fechaBaja;
	
	@Column(name = "C_USUCREACION")
	private Long usuCreacion;
	
	@Column(name = "F_CREACION")
	private Date fechaCreacion;
	
	@Column(name = "C_USUACTUALIZA")
	private Long usuActualiza;
	
	@Column(name = "F_ACTUALIZA")
	private Date fechaActualiza;
}
