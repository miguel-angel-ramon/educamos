package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DatoFinalizacionModuloAulaVirtualDTO", description = "DTO Dato Finalizacion Modulo Aula Virtual")
public class DatoFinalizacionModuloAulaVirtualDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
	private Integer state;
	private Integer timecompleted;
	private String overrideby;
	private boolean valueused;
	private boolean hascompletion;
	private boolean isautomatic;
	private boolean istrackeduser;
	private boolean uservisible;
	
	
}