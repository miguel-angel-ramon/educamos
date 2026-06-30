package es.jccm.edu.proyectosfct.application.domain.empresas.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Docente", description = "Descripcion para el modelo de docentes de un centro")
public interface EmpresaProjection {
	
	@Schema(description = "Id de la empresa")
	Long getId();
	
	@Schema(description = "tipo identificador")
	String getTipoIdentificador();

	@Schema(description = "Numero Identificacion")
	String getNumIde();

	@Schema(description = "Nombre empresa")
	String getNombreEmpresa();
	
	@Schema(description = "Domicilio")
	String getDomicilio();
	
	@Schema(description = "Numero")
	String getNumero();
	
	@Schema(description = "Escalera")
	String getEscalera();
	
	@Schema(description = "Piso")
	String getPiso();
	
	@Schema(description = "Codigo postal")
	String getCodigoPostal();
	
	@Schema(description = "Telefono")
	Long getTelefono();
	
	@Schema(description = "Otro telefono")
	Integer getOtroTelefono();
	
	@Schema(description = "Fax")
	Integer getFax();
	
	@Schema(description = "Correo")
	String getCorreo();
	
	@Schema(description = "Empresa activa")
	String getEmpresaActiva();
	
	@Schema(description = "Telefono contacto")
	String getTelefonoContacto();
	
	@Schema(description = "Observaciones")
	String getObservaciones();
	
	@Schema(description = "Tipo empresa")
	String getTipoEmpresa();
	
	@Schema(description = "Letra")
	String getLetra();
	
	@Schema(description = "Id del sector productivo")
	Long getIdSectorProductivo();

	@Schema(description = "Pagina web")
	String getPaginaWeb();
	
	@Schema(description = "Localidad Extrangera")
	String getLocalidadExtranjera();
	
	@Schema(description = "Empresa pública")
	String getEmpresaPublica();
	
	@Schema(description = "Familia profesional")
	String getFamiliaProfesional();
	
	@Schema(description = "Tipo via")
	String getDTipoVia();
	
	@Schema(description = "localidad")
	String getDLocalidad();
	
	@Schema(description = "Municipio")
	String getDMunicipio();
	
	@Schema(description = "Provincia")
	String getDProvincia();
	
	@Schema(description = "Pais")
	String getDPais();
	
	@Schema(description = "SesCam")
	String getSesCam();
	
}

