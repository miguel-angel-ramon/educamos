package es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model;

import java.io.Serializable;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.CodigoPais;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Localidad;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Municipio;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.TipoVia;
import es.jccm.edu.proyectosfct.application.domain.empresas.Empresa;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Sede Empresa", description = "Descripcion para el modelo de Sede Empresa")
public class SedeEmpresaDto extends BaseAuditedDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la sede")
	private Long id;

	@Schema(description = "Domicilio de la empresa")
	private String domicilio;

	@Schema(description = "Numero de la empresa")
	private String numero;

	@Schema(description = "Escalera de la empresa")
	private String escalera;

	@Schema(description = "Piso de la empresa")
	private String piso;
	
	@Schema(description = "Codigo postal de la empresa")
	private String codigoPostal;
	
	@Schema(description = "Numero telefono de la empresa")
	private Long telefono;
	
	@Schema(description = "Segundo numero telefono de la empresa")
	private Integer otroTelefono;
	
	@Schema(description = "FAX de la empresa")
	private Integer fax;
	
	@Schema(description = "Correo de la empresa")
	private String correo;
	
	@Schema(description = "Indica si la empresa esta activa")
	private Boolean lgPrincipal;
	
	// ---------- Relationships -----------

	@Schema(description = "Id de la empresa")
	private Empresa empresa;
	
	@Schema(description = "Tipo de via")
	private TipoVia tipoVia;

	@Schema(description = "Localidad de la empresa")
	private Localidad localidad;

	@Schema(description = "Municipio de la empresa")
	private Municipio municipio;

	@Schema(description = "Pais de la empresa")
	private CodigoPais codigoPais;

}
