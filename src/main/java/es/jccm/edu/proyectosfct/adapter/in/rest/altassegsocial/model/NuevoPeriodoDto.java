package es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "NuevoPeriodoDto", description = "Nuevo periodo de altas en la seguridad social")
public class NuevoPeriodoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Schema(name = "idAltaSS")
    private Long idAltaSS;
    
    @Schema(name = "fechaFin")
    private Date fechaFin;
    
    @Schema(name = "fechaInicio")
    private Date fechaInicio;
    
    @Schema(name = "tipoEmpresa")
    private String tipoEmpresa;

}
