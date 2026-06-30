package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model;



import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "PeriodoDto", description = "Informacion de los periodos de empresa")
public class PeriodoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public PeriodoDto(String per) {
        this.per = per;
    }
	
	@Schema(description = "Periodo")
	private String per;

}
