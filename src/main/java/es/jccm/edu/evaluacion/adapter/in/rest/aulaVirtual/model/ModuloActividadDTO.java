package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ModuloActividadDTO", description = "DTO Modulo Actividad")
public class ModuloActividadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
	private String url;
	private String name;
	private Integer instance;
	private Integer contextid;
	private String description;
	private Integer visible;
	private boolean uservisible;
	private Integer visibleoncoursepage;
	private String modicon;
	private String modname;
	private String modplural;
	private String availability;
	private Integer indent;
	private String onclick;
	private String afterlink;
	private String customdata;
	private boolean noviewlink;
	private Integer completion;
	private DatoFinalizacionModuloAulaVirtualDTO completiondata;
	private Integer downloadcontent;
	private List<FechaModuloActividadDTO> dates;
	

}