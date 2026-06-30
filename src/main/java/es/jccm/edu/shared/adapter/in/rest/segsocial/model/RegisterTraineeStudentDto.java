package es.jccm.edu.shared.adapter.in.rest.segsocial.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;

@Data
@Schema(name = "RegisterTraineeStudentDto", description = "Descripcion para el modelo envio datos a empresa")
public class RegisterTraineeStudentDto implements Serializable  {
	
    private static final long serialVersionUID = 1L;
    

    @Schema(name = "idAlu")
    @JsonProperty("idAlu")
    private Long idAlu;

    @Schema(name = "nif")
    @JsonProperty("nif")
    private String nif;
    
    @Schema(name = "nuss")
    @JsonProperty("nuss")
    private String afiliation_number;  
    
    @Schema(name = "finiempresa")
    @JsonProperty("finiempresa")
    @JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
    private String real_register_date;

    @Schema(name = "finiempresa")
    @JsonProperty("ffinempresa")
    @JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
    private String endDateCompletion;

    @Schema(name = "erasmus")
    @JsonProperty("erasmus")
    private Integer erasmusfpdualScholarship;
    
    @Schema(name = "cif")
    @JsonProperty("cif")
    private String company_cif; 
    
    @Schema(name = "regime")
    private String regime = "0111";
    
    @Schema(name = "contract_type")
    private String contract_type;   
    
    @Schema(name = "ccodigo")    
    private String ccodigo;  
    
    @Schema(name = "contribution_group")
    private String contribution_group = "07";  
    
    @Schema(name = "cn_occupation")
    private String cn_occupation; 
    
    @Schema(name = "tipoEmpresa")
    @JsonProperty("tipoEmpresa")
    private String tipoEmpresa; 
    
    @Schema(name = "idGestora")
    private Long idGestora;
    
    @Schema(name = "anulado")
    @JsonProperty("anulado")
    private Integer cancel_registration; 
    
    @Schema(name = "inicioHist")
    @JsonProperty("inicioHist")
    private String inicioHist; 
    
    @Schema(name = "finHist")
    @JsonProperty("finHist")
    private String finHist; 
    
    @Schema(name = "id")
    private Long id;

    @Schema(name = "worker_id_ext")
    private Long worker_id_ext;

    @Schema(name = "name")
    private String name;

    @Schema(name = "birth_date")
    private String birth_date;

    @Schema(name = "gender")
    private String gender;

}
