package es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model;

import java.io.Serializable;

import java.util.List;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Empresa", description = "Descripcion para el modelo de Empresa")
public class EmpresaDto extends BaseAuditedDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la empresa")
	private Long id;
	
	@Schema(description = "Tipo de identificador (D=DNI, C=CIF, etc.)")
	private String tipoIdentificador;

	@Schema(description = "Identificador de la empresa o de la persona")
	private String numIde;

	@Schema(description = "Nombre de la empresa o de la persona")
	private String nombreEmpresa;

	@Schema(description = "Indica si la empresa está activa o no")
	private String empresaActiva;
	
	@Schema(description = "Números de teléfono de la persona de contacto")
	private String telefonoContacto;
	
	@Schema(description = "Observaciones")
	private String observaciones;

	@Schema(description = "Identificador del sector productivo")
	private Long idSectorProductivo;
	
	@Schema(description = "Web")
	private String paginaWeb;
	
	@Schema(description = "Indicador si es empresa pública(S) o no (N)")
	private String empresaPublica;
	
	@Schema(description = "Indicador si es sescam(S) o no (N)")
	private String sesCam;
	
	@Schema(description = "Indicador de seguridad social (S) o no (N)")
	private String ssCen;
	
	// ---------- Relationships -----------	

	private List<TipoEmpresaDto> tipos; 

}
