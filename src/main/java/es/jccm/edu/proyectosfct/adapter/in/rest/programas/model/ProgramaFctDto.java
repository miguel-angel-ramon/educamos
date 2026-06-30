package es.jccm.edu.proyectosfct.adapter.in.rest.programas.model;
import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.CentroDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.OfertaMatriculaGenericoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.TutorFctDualDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ProgramaFct", description = "Descripcion para el modelo de Programas FCT")
public class ProgramaFctDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del programa")
	private long id;
	
	@Schema(description = "Descripción del programa")
	private String descripcion;

	private Long idUsuarioCreacion;
	
	private Date fechaCreacion;

	private Long idUsuarioModificacion;

	private Date fechaModificacion;
	
	// ---------- Relationships -----------
	
	private TutorFctDualDto tutor;
	
	private CentroDto centro;
	
	private FamiliaDto familia;
	
	private OfertaMatriculaGenericoDto ofertaMatriculaGenerico;	
	
	// -----------------------
	
	private Integer c_anno_desde;
	
	private Integer c_anno_hasta;
	
	private Integer nu_horas;	
	
	
}
