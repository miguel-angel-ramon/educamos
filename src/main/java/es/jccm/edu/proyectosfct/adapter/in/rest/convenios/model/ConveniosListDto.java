package es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios", description = "Descripcion para el modelo de Convenios para la pantalla multirregistros")
public class ConveniosListDto {

	@Schema(description = "Id del convenio")
	private Long id;
	
	@Schema(description = "Número de convenio")
	private String numeroConvenio;
	
	@Schema(description = "Centro")
	private String centro;
	
	@Schema(description = "Fecha de la firma del convenio")
	private String fechaFirma;
	
	@Schema(description = "Id de la empresa")
	private Long IdEmpresa;
	
	@Schema(description = "Nombre completo de la empresa")
	private String nombreEmpresa;
	
	@Schema(description = "Número identificacion de la empresa")
	private String numIde;
	
	@Schema(description = "Fecha caducidad convenio")
	private String fechaFinVigencia;	
	
	@Schema(description = "Fecha inicio convenio")
	private String fechaInicio;
	
	@Schema(description = "Fecha prorroga convenio")
	private String fechaProrroga;
	
	@Schema(description = "Fecha firma prorroga convenio")
	private String fechaFirmaProrroga;
	
	@Schema(description = "Id sede empresa convenio")
	private Long idSedeEmp;
	
	@Schema(description = "Lista de familias del convenio")
	private String listFamilias;
	
	@Schema(description = "Numero de programas asociados al convenio")
	private Integer numeroProgramas;
	
	@Schema(description = "Identificador del archivo Rodal del convenio firmado")
	private String idConfirRodal;
	
	@Schema(description = "Nombre del archivo Rodal convenio firmado")
	private String txConfirFichero;
	
	@Schema(description = "Valor lógico que nos indica si el documento debe firmarse digitalmente")
	private Integer lconvante;
	
	@Schema(description = "Estado")
	private String estado;
	
	@Schema(description = "Lista de tutores del convenio")
	private String listTutores;
	
	@Schema(description = "Lista del alumnado del convenio")
	private String listAlumnado;

	@Schema(description = "Valor lógico para saber si el convenio es de la nueva ley lofp")
	private Integer lgLofp;

}
