package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumnado NSS Dto", description = "Descripcion para el modelo del alumnado NSS")
public class AlumnadoNSSDto implements Serializable  {	

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Tipo documento")
	@JsonProperty("IPF")
	private String ipf;
	
	@Schema(description = "Provincia")
	@JsonProperty("PROV")
	private String prov;
	
	@Schema(description = "Primer apellido")
	@JsonProperty("AP1")
	private String ap1;
	
	@Schema(description = "Segundo apellido")
	@JsonProperty("AP2")
	private String ap2;
	
	@Schema(description = "Nombre")
	@JsonProperty("NOM")
	private String nom;
	
	@Schema(description = "Fecha nacimiento")
	@JsonProperty("FNAC")
	private String fnac;
	
	@Schema(description = "Nacionalidad")
	@JsonProperty("NCAIONALIDAD")
	private String nacionalidad;
	
	@Schema(description = "Nombre")
	@JsonProperty("NOMBREPADRE")
	private String nombrepadre;
	
	@Schema(description = "Nombre madre")
	@JsonProperty("NOMBREMADRE")
	private String nombremadre;
	
	@Schema(description = "Prefijo móvil")
	@JsonProperty("MOVILPRE")
	private String movilpre;
	
	@Schema(description = "Móvil")
	@JsonProperty("MOVIL")
	private String movil;	

    

}
