package es.jccm.edu.proyectosfct.adapter.in.rest.convenios.model;

import java.io.Serializable;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.EmpresaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.SedeEmpresaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.CentroDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model.EmpleadoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Empresa trabajador", description = "Descripcion para el modelo de Trabajadores de una empresa")
public class ConveniosFctDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del convenio")
	private Long id;

	@Schema(description = "Numero del convenio")
	private String numeroConvenio;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha firma del convenio")
	private Date fechaFirma;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha vigencia del convenio")
	private Date fechaFinVigencia;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha inicio del convenio")
	private Date fechaInicio;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha de prorroga del convenio")
	private Date fechaProrroga;
	
	@Schema(description = "Si esta el convenio activo o no")
	private Boolean convenioFctActivo;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha de firma de prorroga del convenio")
	@JsonFormat (pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFirmaProrroga;
	
	@Schema(description = "Identificador de ID Rodal del fichero del convenio firmado")
	private String idConfirRodal;
	
	@Schema(description = "Nombre del fichero firmado del convenio")
	private String txConfirFichero;
	
	@Schema(description = "Identificador de ID Rodal del fichero del convenio prorrogado")
	private String idConproRodal;
	
	@Schema(description = "Nombre del fichero prorrogado del convenio")
	private String txConproFichero;
	
	@Schema(description = "Valor lógico para saber si necesita ser firmado digitalmente o no")
	private Integer lconvante;
	
	@Schema(description = "Valor lógico para saber si el convenio es de la nueva ley lofp")
	private Integer lglofp;
	
	// ---------- Relationships -----------
	
	@Schema(description = "Empres asociada al convenio")
	private EmpresaDto empresa;
	
	@Schema(description = "Representante de la empresa que firma el convenio")
	private EmpresaTrabajadorDto trabajador;
	
	@Schema(description = "Centro al que se asocia el convenio")
	private CentroDto centro;
	
	@Schema(description = "Director del centro que firma el convenio")
	private EmpleadoDto empleado;
	
	@Schema(description = "Sede de la empresa")
	private SedeEmpresaDto id_sede;
	
}
