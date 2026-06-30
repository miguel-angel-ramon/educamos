package es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model;

import java.io.Serializable;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Empresa", description = "Descripcion para el modelo de Empresa para la pantalla multirregistros")
public class EmpresaListDto extends BaseAuditedDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la empresa")
	private Long id;
	
	@Schema(description = "Tipo de identificador (D=DNI, C=CIF, etc.)")
	private String tipoIdentificador;
	
	@Schema(description = "Escalera")
	private String escalera;
	
	@Schema(description = "Otro número de teléfono")
	private Integer otroTelefono;
	
	@Schema(description = "Empresa pública")
	private String empresaPublica;

	@Schema(description = "Identificador de la empresa o de la persona")
	private String numIde;
	
	@Schema(description = "Piso")
	private String piso;
	
	@Schema(description = "Nombre del municipio")
	private String dMunicipio;

	@Schema(description = "Nombre de la empresa o de la persona")
	private String nombreEmpresa;

	@Schema(description = "Domicilio de la empresa")
	private String domicilio;
	
	@Schema(description = "Observaciones")
	private String observaciones;

	@Schema(description = "Número")
	private String numero;
	
	@Schema(description = "Código postal")
	private String codigoPostal;
	
	@Schema(description = "Número de teléfono")
	private Long telefono;
	
	@Schema(description = "Fax")
	private Integer fax;
	
	@Schema(description = "Indica si la empresa está activa o no")
	private String empresaActiva;
	
	@Schema(description = "Correo electrónico")
	private String correo;
	
	@Schema(description = "Números de teléfono de la persona de contacto")
	private String telefonoContacto;
	
	@Schema(description = "Tipo de empresa: T=Transporte, C=Comedor, F=FCT")
	private String tipoEmpresa;
	
	@Schema(description = "Letra")
	private String letra;
	
	@Schema(description = "Nombre del pais")
	private String dPais;
	
	@Schema(description = "Identificador del sector productivo")
	private Long idSectorProductivo;
	
	@Schema(description = "Web")
	private String paginaWeb;
	
	@Schema(description = "Localidad extranjera")
	private String localidadExtranjera;
	
	@Schema(description = "Familia profesional")
	private String familiaProfesional;
	
	@Schema(description = "Descripcion del tipo de la via")
	private String dTipoVia;
	
	@Schema(description = "Nombre de la localidad")
	private String dLocalidad;



	@Schema(description = "Nombre de la Provincia")
	private String dProvincia;

	@Schema(description = "Nombre del pais")
	private String sesCam;

}
