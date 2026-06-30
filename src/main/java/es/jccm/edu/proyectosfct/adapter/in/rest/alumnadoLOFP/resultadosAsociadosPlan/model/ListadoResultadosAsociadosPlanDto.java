package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Schema(name = "ListadoResultadosAsociadosPlanDto", description = "Listado de resultados asociados a módulos con información adicional")
public class ListadoResultadosAsociadosPlanDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="x_comesp")
	private Long x_comesp;

	@Column(name="abreviatura")
	private String abreviatura;
	
	@Column(name="lg_centro")
	private Integer lg_centro;
	
	@Column(name="actividades")
	private Integer actividades;
	
	@Column(name="lg_empresa")
	private Integer lg_empresa;
	
	@Column(name="descripcion")
	private String descripcion;


}