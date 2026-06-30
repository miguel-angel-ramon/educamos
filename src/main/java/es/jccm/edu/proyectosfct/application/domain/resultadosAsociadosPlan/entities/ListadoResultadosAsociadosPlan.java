package es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
public class ListadoResultadosAsociadosPlan implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="x_comesp")
	private Long x_comesp;

	@Column(name="abreviatura")
	private String abreviatura;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="lg_centro")
	private Integer lg_centro;

	@Column(name="lg_empresa")
	private Integer lg_empresa;

	@Column(name="actividades")
	private Integer actividades;
}