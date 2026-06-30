package es.jccm.edu.proyectosfct.application.domain.proyectos.entities;

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
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="FCT_INFORMACION_PROYECTOS")
public class InformacionProyectos {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_INFORMACION")
	private Long id;
	
	@Column(name="TX_INTRODUCCION")
	private String tx_introduccion;
	
	@Column(name="TX_OBJETIVOS")
	private String tx_objetivos;
	
	@Column(name="TX_RECURSOS")
	private String tx_recursos;
	
	@Column(name="TX_METODOLOGIA")
	private String tx_metodologia;
	
	@Column(name="TX_EVALUACION")
	private String tx_evaluacion;
	
	@Column(name="LB_COORDINACION")
	private String lb_coordinacion;
	
	@Column(name="TX_ADAPTACION")
	private String tx_adaptacion;
	
	@Column(name="TX_OTROS")
	private String tx_otros;
	
	@Column(name="C_USUCREACION")
	private Long idUsuarioCreacion;
	
	@Column(name="F_CREACION")
	private Date fechaCreacion;
	
	@Column(name="C_USUACTUALIZA")
	private Long idUsuarioModificacion;
	
	@Column(name="F_ACTUALIZA")
	private Date fechaModificacion;

	@Column(name="LB_OBJETIVOS")
	private String lb_objetivos;

    @Column(name="LB_EVALUACION")
    private String lb_evaluacion;

	@Transient
	private SecuenciacionProyectos secuenciacionProy;
	
	// ----------- Relationships ------------
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROYECTO")
	private Proyectos proyecto;
	
	

}
