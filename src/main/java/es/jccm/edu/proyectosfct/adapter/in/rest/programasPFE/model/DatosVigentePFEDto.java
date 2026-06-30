package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="Vigente Dto ", description = "Dto para los Programas formacion vigente")
public class DatosVigentePFEDto implements Serializable {
	
	 private static final long serialVersionUID = 1L;

	    @Schema(name = "id")
	    private Long id;

	    @Schema(name = "dsCurso")
	    private String dsCurso;
	    
	    @Schema(name = "dsAlcance")
	    private String dsAlcance;
	    
	    @Schema(name = "dsModalidad")
	    private String dsModalidad;
	    
	    @Schema(name = "dsAnnoIni")
	    private String dsAnnoIni;
	    
	    @Schema(name = "dsAnnoFin")
	    private String dsAnnoFin;
	    
	    @Schema(name = "txAutor")
	    private String txAutor;
	    
	    @Schema(name = "idRodal")
	    private String idRodal;
	    
	    @Schema(name = "fichero")
	    private String fichero;
	
	

}
