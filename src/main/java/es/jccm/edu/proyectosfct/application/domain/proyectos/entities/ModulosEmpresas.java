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
@Table(name="FCT_MODULOS_EMPRESAS")
public class ModulosEmpresas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_MODULO_EMPRESA")
	private Long id;
	
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
	@JoinColumn(name="ID_MODULO_CURSO")
	private ModulosCurso moduloCurso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONV_PROY")
	private ConveniosProyecto convenioProyecto;
	
	
	

}
