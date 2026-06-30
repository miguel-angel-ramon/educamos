package es.jccm.edu.simulacion.adapter.in.rest;

public class BasePath {
	
	public static final String SimulacionBasePath="${spring.data.rest.base-path:/api}" + "/simulacion";
	
	@Deprecated
	public static final String FixmeSimulacionJwtUtilBasePath="${spring.data.rest.base-path:/api}" + "/jwtUtils";
	
	@Deprecated
	public static final String FixmeSimulacionVeriftotpBasePath="${spring.data.rest.base-path:/api}" + "/verificationtotp";
}
	                                           
