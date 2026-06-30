package es.jccm.edu.alumnos.adapter.in.rest;

public class BasePath {
	public static final String AlumnosBasePath="${spring.data.rest.base-path:/api}" + "/alumnos";
	
	@Deprecated
	public static final String FixmeAlumnosTutoresBasePath="${spring.data.rest.base-path:/api}" + "/tutores";

	@Deprecated
	public static final String FixmeAlumnosEvaluacionBasePath="${spring.data.rest.base-path:/api}" + "/evaluacion";
	
	@Deprecated
	public static final String FixmeAlumnosFaltasBasePath="${spring.data.rest.base-path:/api}" + "/faltas";
}
	                                           
