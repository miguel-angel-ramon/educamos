package es.jccm.edu.shared.configuration.common;

import java.io.Serializable;

public final class Constants implements Serializable {

	/**
	 * Serial version Id
	 */
	private static final long serialVersionUID = -8779788131446718968L;

	/** Constante BEARER. */
	public static final String BEARER = "Bearer";
	
	/** Constante AUTHORIZATION. */
	public static final String AUTHORIZATION = "Authorization";

	/** Constante AUTHORIZATION. */
	public static final String TOKEN = "token";
	
	/** Constante 2FA. */
	public static final String TOTP2FA = "Totp_2Fa";
	
	/** Constante GRANT TYPE*/
	public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
	
	/** Constante ACCESO TOKEN URL*/
	public static final String ACCESO_TOKEN_URL = "/protocol/openid-connect/token";
	
	/** Constante PERFIL 161*/
	public static final int PERFIL_161 = 161;
	
	/*
	 * QUARTZ
	 */
	public static final String QUARTZ_SHEDULER_CONFIGURACIONES_DESCRIPTION = "Planificador Quartz para la entidad [Configuraciones - %d].";

	/*
	 * ERRORES
	 */
	public static final String ERROR_MESSAGE_GENERAL = "Error inesperado en la ejecución del Api Backend de EducamosCLM.";
	public static final String ERROR_MESSAGE_GENERAL_QUARTZ = "Error inesperado en la ejecucion del planificador Quartz.";

	/*
	 * BILOOP SEG.SOCIAL
	 */
	public static final String GET_TOKEN_BILOOP = "api-global/v1/token";
	public static final String GET_BILOOP_COMPANIES = "api-global/v1/getCompanies";
	public static final String POST_BILOOP_REGISTERTRAINEESTUDENTS = "api-global/v1/requests/labor/registerTraineeStudents";
	public static final String POST_BILOOP_ALTERTRAINEESTUDENTS = "api-global/v1/requests/labor/alterTraineeStudents";
	public static final String POST_BILOOP_CANCELEDTRAINEESTUDENTS = "api-global/v1/requests/labor/cancelledTraineeStudents";	
	public static final String POST_BILOOP_REGISTERDAYSCONTRIBUTED = "api-global/v1/requests/labor/registerDaysContributed";
	public static final String GET_BILOOP_GETSTATUSREGISTERSWORKERS = "api-global/v1/requests/labor/getStatusRegistersWorkers";
	public static final String GET_BILOOP_GETSTATUSCONTRIBUTIONDAYS = "/api-global/v1/requests/labor/getStatusContributionDays";
	


}
