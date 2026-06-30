package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Localidad;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Municipio;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Provincia;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.TipoVia;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Denominacion;
import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Centro", description = "Descripcion para el modelo de centro")
public class DatosCentroDto extends BaseAuditedDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del centro")
	private Long id;

	@Schema(description = "Fecha de inicio de vigencia")
	private Date fechaInicioVigencia;

	@Schema(description = "Descripciones largas del centro")
	private String descripcionLargaCentro;
	
	@Schema(description = "¿Vigente? Si o No")
	private String esVigente;
	
	@Schema(description = "Denominacion del centro que debe aparecer en el titulo. Formato e")
	private String denominacionCentroTitulo;
	
	@Schema(description = "Fecha de fin de vigencia")
	private Date fechaFinVigencia;
	
	@Schema(description = "Descripciones largas del domicilio del centro")
	private String descripcionLargaDomicilioCentro;
	
	@Schema(description = "Código postal")
	private String codigoPostal;
	
	@Schema(description = "Número de telefono")
	private String telefono;
	
	@Schema(description = "Número de Fax.")
	private String fax;
	
	@Schema(description = "Dirección de correo electrónico")
	private String correoElectronico;
	
	@Schema(description = "Identificador del usuario de última modificación del registro")
	private Long idUsuarioModificacion;

	@Schema(description = "Identificador del usuario de creación del registro")
	private Long idUsuarioCreacion;

	@Schema(description = "Fecha de última modificación del registro")
	private Date fechaModificacion;

	@Schema(description = "Fecha de creación del registro")
	private Date fechaCreacion;
	
	@Schema(description = "Es ¿publico?")
	@NotBlank
	private String esPublico;
	
	@Schema(description = "Descripción del centro en el Registro de centros (en el momento en que se realizó la última carga)")
	private String descripcionRegistroCentro;
	
	@Schema(description = "Tipo de centro para los resultados de las elecciones a Consejo Escolar.")
	private String tipoCentroResultadoElecciones;
	
	@Schema(description = "Indica si el centro entra en la admision de la fecha indicada en el parametro general F_PROXIMAADMISION.")
	private String esAdmisible;
	
	@Schema(description = "Nif del centro")
	private String nifCentro;
	
	
	
	
	// ---------- Relationships -----------
	
	
	@Schema(description = "Identificador de centro")
	private Centro idCentro;
	
	@Schema(description = "Identificador de tipo de vía")
	private TipoVia idTipoVia;
	
	@Schema(description = "Código de municipio.")
	private Municipio codigoMunicipio;
	
	@Schema(description = "Código de provincia")
	private Provincia codigoProvincia;
	
	@Schema(description = "Denominación")
	private Denominacion denominacion;
	
	@Schema(description = "Identificador de localidad")
	private Localidad localidad;

}
