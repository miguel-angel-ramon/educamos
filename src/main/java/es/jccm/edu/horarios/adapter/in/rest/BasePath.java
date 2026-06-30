package es.jccm.edu.horarios.adapter.in.rest;

public class BasePath {
	
	public static final String HorariosBasePath="${spring.data.rest.base-path:/api}" + "/horarios"; 

	@Deprecated
	public static final String FixmeHorariosCalendarioPfcBasePath="${spring.data.rest.base-path:/api}" + "/proyectosfct"; 
	
	@Deprecated
	public static final String FixmeHorariosDependenciasBasePath="${spring.data.rest.base-path:/api}" + "/dependencias";
}
	                                           
