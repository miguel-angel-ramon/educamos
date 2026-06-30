package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import es.jccm.edu.proyectosfct.adapter.in.rest.modalidades.model.ModalidadDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.FamiliaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.CentroDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.TutorFctDualDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ProyectosDto", description = "Descripcion para el modelo de proyectos dto")
public class ProyectosDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del proyecto")
	private Long id;
	
	@Schema(description = "Nombre del programa")
	private String ds_proyecto;
	
	@Schema(description = "año desde")
	private Integer c_anno_desde;
	
	@Schema(description = "año hasta")
	private Integer c_anno_hasta;
	
	@Schema(description = "número horas")
	private Integer nu_horas;
	
	@Schema(description = "numero alumnos")
	private Integer nu_alumnos;

	@Schema(description = "Indicador de LOFP")
	private Integer lgLofp;

	@Schema(description = "Id de curso")
	private Long idOfertaMatrig;

	@Schema(description = "Flag de intervalo diario")
	private Integer lg_idiario;

	@Schema(description = "Flag de intervalo semanal")
	private Integer lg_isemanal;

	@Schema(description = "Flag de intervalo mensual")
	private Integer lg_imensual;

	@Schema(description = "Flag de otros intervalos")
	private Integer lg_iotros;

	@Schema(description = "Flag de varias empresas")
	private Integer lg_ivarias_empresas;

	@Schema(description = "Flag de regimen")
	private Integer lg_regimen;

	
	// ----------- Relationships ------------
	
	@Schema(description = "Id del tipo Proyecto")
	private TipoProyectosDto tipo;
	
	@Schema(description = "Tutor del proyecto")
	private TutorFctDualDto tutor;
	
	@Schema(description = "Centro del proyecto")
	private CentroDto centro;
	
	@Schema(description = "Familia profesional del proyecto")
	private FamiliaDto familia;
	
	@Schema(description = "Modalidad del proyecto")
	private ModalidadDto modalidad;
	

}
