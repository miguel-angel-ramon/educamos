package es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado autorizacion desplazamiento", description = "Descripcion para el modelo del listado autorizacion desplazamiento")
public interface ListadoAutorizacionDesplazamientoProjection {
	
	@Schema(description = "Id")
	Long getId();
	
	@Schema(description = "Nombre alumno")
	String getNombreAlumno();
	
	@Schema(description = "Fecha inicio")
	Date getFInicio();
	
	@Schema(description = "Fecha fin")
	Date getFFin();
	
	@Schema(description = "Dias")
	String getDias();
	
	@Schema(description = "Km dias")
	String getKmdia();
	
	@Schema(description = "Matricula")
	String getMatricula();
	
	@Schema(description = "Itinerario")
	String getItinerario();
	
	@Schema(description = "Total kilometros")
	String getTotalkm();
	
	@Schema(description = "Fecha de la última creación/modificación solicitud")
	String getFultgen();
	
	@Schema(description = "Estado")
	String getEstado();
	
	@Schema(description = "Id matricula")
	Long getIdMatricula();
	
	@Schema(description = "Editar estado")
	Integer getEditarestado();
	
	@Schema(description = "Habilitar boton de borrado")
	String getPuedeBorrar();

	@Schema(description = "Número total de horas.")
	String getNhoras();

}

