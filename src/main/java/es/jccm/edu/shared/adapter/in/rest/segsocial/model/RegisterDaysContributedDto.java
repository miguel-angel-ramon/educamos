package es.jccm.edu.shared.adapter.in.rest.segsocial.model;


import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RegisterDaysContributed", description = "Descripcion para el modelo envio cotizaciones mensuales")
public class RegisterDaysContributedDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Schema(name = "id")
    @JsonProperty("id")
    private Long idAlu;
    
    @Schema(name = "nuss")
    @JsonProperty("nuss")
    private String afiliation_number; 
    
    @Schema(name = "nuMes")
    @JsonProperty("nuMes")
    private Integer month; 
    
    @Schema(name = "anno")
    @JsonProperty("anno")
    private Integer year;
    
    @Schema(name = "nuDiasReal")
    @JsonProperty("nuDiasReal")
    private Integer days; 
    
    @Schema(name = "nuDiasNacu")
    @JsonProperty("nuDiasNacu")
    private Integer absent_by_son; 
    
    @Schema(name = "nuDiasInte")
    @JsonProperty("nuDiasInte")
    private Integer absent_it; 
    
    @Schema(name = "diasInteEra")
    @JsonProperty("diasInteEra")
    private Integer absent_erasmus;     
    
    @Schema(name = "tipoEmpresa")
    @JsonProperty("tipoEmpresa")
    private String tipoEmpresa; 
    
    @Schema(name = "lgErasBec")
    @JsonProperty("lgErasBec")
    private Integer erasmusScholarship;

    @Schema(name = "nif")
    @JsonProperty("nif")
    private String nif;

    @Schema(name = "idCentro")
    @JsonProperty("idCentro")
    private Long idCentro;
    
}
