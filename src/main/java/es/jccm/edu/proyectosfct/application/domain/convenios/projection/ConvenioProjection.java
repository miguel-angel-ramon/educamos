package es.jccm.edu.proyectosfct.application.domain.convenios.projection;


import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ConvenioProjection {

	@Schema(description = "Identificador del convenio")
	Long getId();
	
	@Schema(description = "Número de convenio")
	String getNumeroConvenio();
	
	@Schema(description = "Centro")
	String getCentro();
	
	@Schema(description = "Fecha de la firma del convenio")
	Date getFechaFirma();
	
	@Schema(description = "Id empresa")
	Long getIdEmpresa();
	
	@Schema(description = "Nombre empresa")
	String getNombreEmpresa();
	
	@Schema(description = "Número de identificacion de la empresa")
	String getNumIde();	
	
	@Schema(description = "Fecha caducidad convenio")
	String getFechaFinVigencia();	
	
	@Schema(description = "Fecha inicio convenio")
	Date getFechaInicio();	
	
	@Schema(description = "Fecha prórroga convenio")
	Date getFechaProrroga();	
	
	@Schema(description = "Fecha firma prórroga convenio")
	Date getFechaFirmaProrroga();	
	
	@Schema(description = "Fecha firma prórroga convenio")
	String getIdSedEmp();	
	
	@Schema(description = "Lista de familias del convenio")
	String getListFamilias();		
	
	@Schema(description = "Numero de programas asociados al convenio")
	Integer getNumeroProgramas();
	
	@Schema(description = "Identificador del archivo Rodal del convenio firmado")
	String getIdConfirRodal();
	
	@Schema(description = "Nombre del archivo Rodal convenio firmado")
	String getTxConfirFichero();
	
	@Schema(description = "Valor lógico que nos indica si el documento debe firmarse digitalmente")
	Integer getLconvante();
	
	@Schema(description = "Estado")
	String getEstado();
	
	@Schema(description = "Lista de tutores del convenio")
	String getListTutores();	
	
	@Schema(description = "Lista del alumando del convenio")
	String getListAlumnado();

	@Schema(description = "Valor lógico para saber si el convenio es de la nueva ley lofp")
	Integer getLgLofp();

}
