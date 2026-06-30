package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="FCT_SECUENCIACION_PROYECTOS")
public class SecuenciacionProyectos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_SECUENCIACION")
	private Long id;
	
	@Column(name="TX_SECUECNCIACION")
	private String txProyecto;
	
	@Column(name="ID_SECFIC_RODAL")
	private String idSecficRodal;
	
	@Column(name="TX_SECFIC_FICHERO")
	private String txSecficFichero;
	
	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;
	
	@Column(name="F_CREACION")
	private Date fechaCreacion;
	
	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;
	
	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;
	
	
	// ----------- Relationships ------------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROYECTO")
	private Proyectos proyecto;
	
	
	
	

}
