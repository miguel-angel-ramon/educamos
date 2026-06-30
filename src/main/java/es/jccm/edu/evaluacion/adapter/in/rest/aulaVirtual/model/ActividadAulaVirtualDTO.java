package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ActividadAulaVirtualDTO", description = "DTO Actividad Aula Virtual")
public class ActividadAulaVirtualDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
	private String name;
	private Integer visible;
	private String summary;
	private String summaryformat;
	private Integer section;
	private Integer hiddenbynumsections;
	private boolean uservisible;
	private List<ModuloActividadDTO> modules;
}