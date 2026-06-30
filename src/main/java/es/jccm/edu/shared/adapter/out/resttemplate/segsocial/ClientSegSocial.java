package es.jccm.edu.shared.adapter.out.resttemplate.segsocial;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterDaysContributed;
import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterTraineeStudent;
import es.jccm.edu.shared.application.ports.out.resttemplate.segsocial.IClientSegSocial;
import es.jccm.edu.shared.configuration.common.Constants;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Qualifier("clientSegSocialRestTemplate")
public class ClientSegSocial implements IClientSegSocial {

	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static final String WARNINGS = "warnings";
	
	public static final String WORKER_NIF = "worker_nif";
	
	public static final String SUBSCRIPTION_KEY = "SUBSCRIPTION_KEY";
	public static final String TOKEN = "token";
	@Value("${segsocial.biloop.url}")
	private String urlBiloop;

	@Value("${segsocial.biloop.username}")
	private String usernameBiloop;

	@Value("${segsocial.biloop.password}")
	private String passwordBiloop;

	@Value("${segsocial.biloop.apiToken}")
	private String apiTokenBiloop;
	
	private String tokenLoged;

    private final RestTemplate restTemplate;
    
	public ClientSegSocial(@Qualifier("restTemplateSegSocial") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String getToken() {
		//System.out.println("Entra sevicio getToken");
		String accessTokenUrl = urlBiloop + Constants.GET_TOKEN_BILOOP;
		String token = "";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(SUBSCRIPTION_KEY, apiTokenBiloop);
		headers.set("USER", usernameBiloop);
		headers.set("PASSWORD", passwordBiloop);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(accessTokenUrl, HttpMethod.GET, entity,
				String.class);

		String responseBody = responseEntity.getBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		try {
			node = mapper.readTree(responseBody);
			token = node.path("data").get(TOKEN).asText();
			this.setTokenLoged(token);
		} catch (JsonProcessingException ignored) {
		}
		return token;
		
	}
	
	@Override
	public void getCompanias() {
		String accessTokenUrlCompanias = urlBiloop + Constants.GET_BILOOP_COMPANIES;
		String tokenCompanias =this.getTokenLoged();
		HttpHeaders headersCompanias = new HttpHeaders();
		headersCompanias.setContentType(MediaType.APPLICATION_JSON);
		headersCompanias.set(SUBSCRIPTION_KEY, apiTokenBiloop);
		headersCompanias.set(TOKEN, tokenCompanias);

		
		HttpEntity<MultiValueMap<String, String>> entityCompanias = new HttpEntity<>(headersCompanias);

		RestTemplate restTemplateCompanias = new RestTemplate();
		ResponseEntity<String> responseEntityCompanias = restTemplateCompanias.exchange(accessTokenUrlCompanias, HttpMethod.GET, entityCompanias,
				String.class);

		String responseBodyCompanias = responseEntityCompanias.getBody();
		ObjectMapper mapperCompanias = new ObjectMapper();
		JsonNode nodeCompanias;
		try {
			nodeCompanias = mapperCompanias.readTree(responseBodyCompanias);
			System.out.println("REspuesta empresas: "+nodeCompanias.toPrettyString());
		} catch (JsonProcessingException ignored) {
		}
	}

	public String getTokenLoged() {
		return tokenLoged;
	}

	public void setTokenLoged(String tokenLoged) {
		this.tokenLoged = tokenLoged;
	}
	
	@Override
	public void envioAltasSSEmpresa(List<RegisterTraineeStudent> registerTraineeStudents, Long xUsuarioDelphos, String type) {
		
		String accessTokenUrlEnvio = "";

		accessTokenUrlEnvio = (type.equals("INSERT"))?urlBiloop + Constants.POST_BILOOP_REGISTERTRAINEESTUDENTS: urlBiloop + Constants.POST_BILOOP_ALTERTRAINEESTUDENTS;

		String tokenEnvio =this.getToken();
		HttpHeaders headersEnvio = new HttpHeaders();
		headersEnvio.setContentType(MediaType.APPLICATION_JSON);
		headersEnvio.set(SUBSCRIPTION_KEY, apiTokenBiloop);
		headersEnvio.set(TOKEN, tokenEnvio);

		
		HttpEntity<List<RegisterTraineeStudent>> studentsEnvio = new HttpEntity<>(registerTraineeStudents, headersEnvio);
		RestTemplate restTemplateEnvio = new RestTemplate();
		ResponseEntity<String> responseEntityEnvio = restTemplateEnvio.exchange(accessTokenUrlEnvio, HttpMethod.POST, studentsEnvio,
				String.class);
		
		String responseBodyEnvio = responseEntityEnvio.getBody();
		ObjectMapper mapperEnvio = new ObjectMapper();
		JsonNode nodeEnvio;
		try {
			nodeEnvio = mapperEnvio.readTree(responseBodyEnvio);
			String statusEnvio = nodeEnvio.get(STATUS).asText();

            //Obtenemos el listado de objetos que tienen un error
            JsonNode errorsEnvio = nodeEnvio.get(MESSAGE).get("app_errors");

            extraErrors(registerTraineeStudents, errorsEnvio);

			if (responseEntityEnvio.getStatusCode().is2xxSuccessful() && statusEnvio.equals("OK")) {
				JsonNode warningsEnvio = nodeEnvio.get(MESSAGE).get(WARNINGS);				
				//List<String> messages = new ArrayList<>();
				if (type.equals("UPDATE")) {
					extraMessage(registerTraineeStudents, nodeEnvio);					
				}

				if((warningsEnvio != null && !warningsEnvio.isEmpty())){
					registerWarnings(registerTraineeStudents, warningsEnvio);
				}	
				
            } else {
            	updateKO(registerTraineeStudents,type);
            }

		} catch (JsonProcessingException ignored) {
	  }		
		
	}

	private void extraErrors(List<RegisterTraineeStudent> registerTraineeStudents, JsonNode errorsEnvio) {
		if(errorsEnvio != null && !errorsEnvio.isEmpty()){
		    registerErrors(registerTraineeStudents, errorsEnvio);
		} else {            	
			registerErrorsEnvio(registerTraineeStudents);
		}
	}
	
	
	private void extraMessage(List<RegisterTraineeStudent> registerTraineeStudents, JsonNode nodeEnvio) {
		String message = nodeEnvio.get(MESSAGE).asText();
		int indexOfFirstPeriod = message.indexOf('.');					
		if (indexOfFirstPeriod != -1) {
			registerTraineeStudents.get(0).setWarnings(message.substring(indexOfFirstPeriod + 1).trim());
		} else {
			registerTraineeStudents.get(0).setWarnings(null);
		}
	}
	
	public void updateKO(List<RegisterTraineeStudent> registerTraineeStudents, String type){
		
		if (type.equals("UPDATE")) 
		{ 
			for (RegisterTraineeStudent registerTraineeStudent : registerTraineeStudents){
				
				registerTraineeStudent.setId(null);
			}
		}
		
	}
	

	@Override
	public void envioAltasSSEmpresaCancelled(RegisterTraineeStudent registerTraineeStudent, Long xUsuarioDelphos){

		String accessTokenUrlCancel = "";
		List<RegisterTraineeStudent> registerTraineeStudentsCancel = new ArrayList<>();

		registerTraineeStudentsCancel.add(registerTraineeStudent);
		accessTokenUrlCancel = urlBiloop + Constants.POST_BILOOP_CANCELEDTRAINEESTUDENTS;

		String tokenCancel =this.getToken();
		HttpHeaders headersCancel = new HttpHeaders();
		headersCancel.setContentType(MediaType.APPLICATION_JSON);
		headersCancel.set(SUBSCRIPTION_KEY, apiTokenBiloop);
		headersCancel.set(TOKEN, tokenCancel);

        HttpEntity<MultiValueMap<String, String>> entityCancel = new HttpEntity<>(headersCancel);

        String urlTemplateCancel = UriComponentsBuilder.fromHttpUrl(accessTokenUrlCancel)
               .queryParam("id", registerTraineeStudent.getId())
			   .encode()
			   .toUriString();

		RestTemplate restTemplateCancel = new RestTemplate();
		ResponseEntity<String> responseEntityCancel = restTemplateCancel.exchange(urlTemplateCancel, HttpMethod.POST, entityCancel,
				String.class);

		String responseBodyCancel = responseEntityCancel.getBody();
		ObjectMapper mapperCancel = new ObjectMapper();
		JsonNode nodeCancel;
		try {
			nodeCancel = mapperCancel.readTree(responseBodyCancel);
			String statusCancel = nodeCancel.get(STATUS).asText();

			//Obtenemos el listado de objetos que tienen un error
			JsonNode errorsCancel = nodeCancel.get(MESSAGE).get("app_errors");

			extraErrors(registerTraineeStudentsCancel, errorsCancel);

			if (responseEntityCancel.getStatusCode().is2xxSuccessful() && statusCancel.equals("OK")) {
				JsonNode warningsCancel = nodeCancel.get(MESSAGE).get(WARNINGS);
				//List<String> messages = new ArrayList<>();

				if(warningsCancel != null && !warningsCancel.isEmpty()){
					registerWarnings(registerTraineeStudentsCancel, warningsCancel);
				}
			}

		} catch (JsonProcessingException ignored) {
		}


	}

    private static void registerErrors(List<RegisterTraineeStudent> registerTraineeStudents, JsonNode errors) {

    	if (!errors.isEmpty()) {
			for (int i = 0; i < errors.size(); i++){
				JsonNode errorNif = errors.get(i).get("error_status_KO_nif");
				JsonNode errorDates = errors.get(i).get("error_status_KO_dates");
				String errorMessage = "";
				if(errorNif != null){
					errorMessage += errorNif.asText() + "%n";
				}
				if(errorDates != null){
					errorMessage += errorDates.asText() + "%n";
				}
				registerTraineeStudents.get(i).setErrors(errorMessage);
			} 
    	} 
    }
    
    private static void registerErrorsEnvio(List<RegisterTraineeStudent> registerTraineeStudents) {

    	for (int i = 0; i < registerTraineeStudents.size(); i++){			 
    		registerMyErrores(registerTraineeStudents, i);  				
    	} 
    	
    }

	private static void registerMyErrores(List<RegisterTraineeStudent> registerTraineeStudents, int i) {
		String errores = "";
		if (registerTraineeStudents.get(i).getNif() == null) {
			errores = "No se ha mandado DNI, el DNI es obligatorio";
		}
		if  (registerTraineeStudents.get(i).getReal_register_date() == null) {
			errores = (errores.equals(""))?"No se ha mandado fecha de alta, la fecha de alta es obligatoria":"%nNo se ha mandado fecha de alta, la fecha de alta es obligatoria";
		}
		if  (registerTraineeStudents.get(i).getEndDateCompletion() == null) {
			errores = (errores.equals(""))?"No se ha mandado fecha de baja, la fecha de baja es obligatoria":"%nNo se ha mandado fecha de baja, la fecha de baja es obligatoria";
		}
		if (!errores.equals("")) registerTraineeStudents.get(i).setErrors(errores);
	}

    
    
    private void registerWarnings(List<RegisterTraineeStudent> registerTraineeStudents, JsonNode warnings) {
		Iterator<String> listKeys = warnings.fieldNames();
		while (listKeys.hasNext()) {
			String key = listKeys.next();
			JsonNode message = warnings.get(key);
			String messageText = message.asText();
			messageText = messageText.replace("|", "%n");
			messageText = String.format(messageText);

			for (int i = 0; i < warnings.size(); i++) {
				if(registerTraineeStudents.get(i).getNif().equals(key)){
					registerTraineeStudents.get(i).setWarnings(messageText);
				}
			}
		}
	}
	
	@Override
	public Integer envioCotizacionesMensuales(List<RegisterDaysContributed> registerDaysContributed, Long xUsuarioDelphos) {
		String accessTokenUrlMensual = urlBiloop + Constants.POST_BILOOP_REGISTERDAYSCONTRIBUTED;
		String tokenMensual =this.getToken();
		HttpHeaders headersMensual = new HttpHeaders();
		headersMensual.setContentType(MediaType.APPLICATION_JSON);
		headersMensual.set(SUBSCRIPTION_KEY, apiTokenBiloop);
		headersMensual.set(TOKEN, tokenMensual);
		
		HttpEntity<List<RegisterDaysContributed>> studentsMensual = new HttpEntity<>(registerDaysContributed, headersMensual);
		RestTemplate restTemplateMensual = new RestTemplate();
		ResponseEntity<String> responseEntityMensual = restTemplateMensual.exchange(accessTokenUrlMensual, HttpMethod.POST, studentsMensual,
				String.class);
		
		String responseBodyMensual = responseEntityMensual.getBody();
		ObjectMapper mapperMensual = new ObjectMapper();
		JsonNode nodeMensual;
		try {
			nodeMensual = mapperMensual.readTree(responseBodyMensual);
			String status = nodeMensual.get(STATUS).asText();

			JsonNode warningsMensual = nodeMensual.get(MESSAGE).get(WARNINGS);

			if(warningsMensual !=null && !warningsMensual.isEmpty()) {
				//Mostramos el warning por consola
				System.out.println("Status: " + status + "- Mensaje: "  + nodeMensual.get(MESSAGE).get(WARNINGS));
			}
			if (responseEntityMensual.getStatusCode().is2xxSuccessful() && status.equals("OK")) {
				//List<String> messages = new ArrayList<>();

				if(warningsMensual != null && !warningsMensual.isEmpty()){
					registerWarningsDays(registerDaysContributed, warningsMensual);
				}
				    return registerDaysContributed.size();
		        } else {
		        	return 0;
		        }
			
		} catch (JsonProcessingException ignored) {
		}
		return 1;
		
	}

	
	
	
	
	
	@Override
	public JsonNode getDatosSegSocialByTipo(String doc, String filtro, String fechaInicio, String fechaFin, String tipo, String statuss) {

		
		JsonNode dataByTipo = null;
		String accessTokenUrlByTipo = urlBiloop + Constants.GET_BILOOP_GETSTATUSREGISTERSWORKERS;
		String tokenByTipo =this.getToken();
		HttpHeaders headersByTipo = new HttpHeaders();
		headersByTipo.setContentType(MediaType.APPLICATION_JSON);
		headersByTipo.set(SUBSCRIPTION_KEY, apiTokenBiloop);
		headersByTipo.set(TOKEN, tokenByTipo);

		HttpEntity<MultiValueMap<String, String>> entityByTipo = new HttpEntity<>(headersByTipo);

		String urlTemplate = null;
		
		if (filtro.equals("cif")) {
			urlTemplate = UriComponentsBuilder.fromHttpUrl(accessTokenUrlByTipo)
											.queryParam("cif", doc)
											.encode()
											.toUriString();
		} else if (filtro.equals("nif")) {
			urlTemplate = UriComponentsBuilder.fromHttpUrl(accessTokenUrlByTipo)
											.queryParam(WORKER_NIF, doc)
											.encode()
											.toUriString();
		} else if(filtro.equals("nifFechas")){
			urlTemplate = UriComponentsBuilder.fromHttpUrl(accessTokenUrlByTipo)
											.queryParam(WORKER_NIF, doc)
											.queryParam("register_date_start", fechaInicio)
											.queryParam("register_date_end", fechaInicio)
											.queryParam("discharge_date_start", fechaFin)
											.queryParam("discharge_date_end", fechaFin)
											.queryParam("type", tipo)
											//.queryParam("status", statuss)
											.encode()
											.toUriString();
		}else if(filtro.equals("worker_id_ext")){
            urlTemplate = UriComponentsBuilder.fromHttpUrl(accessTokenUrlByTipo)
                                            .queryParam("worker_id_ext", doc)
                                            .encode()
                                            .toUriString();
        }else{
            urlTemplate = UriComponentsBuilder.fromHttpUrl(accessTokenUrlByTipo)
                    .queryParam("id", doc)
                    .encode()
                    .toUriString();
        }


		RestTemplate restTemplateByTipo = new RestTemplate();
		ResponseEntity<String> responseEntityByTipo = restTemplateByTipo.exchange(urlTemplate, HttpMethod.GET, entityByTipo,
				String.class);

		String responseBodyBytipo = responseEntityByTipo.getBody();
		ObjectMapper mapperByTipo = new ObjectMapper();
		JsonNode nodeByTipo;
		try {
			nodeByTipo = mapperByTipo.readTree(responseBodyBytipo);
			String status = nodeByTipo.get(STATUS).asText();

			if(status.equals("OK")){
				dataByTipo = nodeByTipo.get("data");
			} else {
				
				throw new IllegalArgumentException("El estado no es OK");
			}

		} catch (JsonProcessingException ignored) {
		}

		return dataByTipo;
	}

	@Override
	public JsonNode getDatosSegSocialByTipoAndMes(String filtro, String cif, Integer nuMes, Integer anno, String nif) {
		JsonNode dataByTipoAndMes = null;
		String accessTokenUrlByTipoAndMes = urlBiloop + Constants.GET_BILOOP_GETSTATUSCONTRIBUTIONDAYS;
		String tokenByTipoAndMes =this.getToken();
		HttpHeaders headersByTipoAndMes = new HttpHeaders();
		headersByTipoAndMes.setContentType(MediaType.APPLICATION_JSON);
		headersByTipoAndMes.set(SUBSCRIPTION_KEY, apiTokenBiloop);
		headersByTipoAndMes.set(TOKEN, tokenByTipoAndMes);

		HttpEntity<MultiValueMap<String, String>> entityByTipo = new HttpEntity<>(headersByTipoAndMes);

		String urlTemplate = null;

		if(filtro.equals("cif")){
			urlTemplate = UriComponentsBuilder.fromHttpUrl(accessTokenUrlByTipoAndMes)
					.queryParam("company_cif", cif)
					.queryParam("month", nuMes)
					.queryParam("year", anno)
					.encode()
					.toUriString();
		} else if(filtro.equals("nif")){
			urlTemplate = UriComponentsBuilder.fromHttpUrl(accessTokenUrlByTipoAndMes)
					.queryParam(WORKER_NIF, nif)
					.queryParam("month", nuMes)
					.queryParam("year", anno)
					.encode()
					.toUriString();
		}

		RestTemplate restTemplateByTipoAndMes = new RestTemplate();
		ResponseEntity<String> responseEntityByTipoAndMes = restTemplateByTipoAndMes.exchange(urlTemplate, HttpMethod.GET, entityByTipo,
				String.class);

		String responseBodyBytipoAndMes = responseEntityByTipoAndMes.getBody();
		ObjectMapper mapperByTipoAndMes = new ObjectMapper();
		JsonNode nodeByTipoAndMes;
		try {
			nodeByTipoAndMes = mapperByTipoAndMes.readTree(responseBodyBytipoAndMes);
			String status = nodeByTipoAndMes.get(STATUS).asText();

			if(status.equals("OK")){
				dataByTipoAndMes = nodeByTipoAndMes.get("data");
			} else {				
				throw new IllegalArgumentException("El estado no es OK");
			}

		} catch (JsonProcessingException ignored) {
		}

		return dataByTipoAndMes;
	}


	private void registerWarningsDays(List<RegisterDaysContributed> registerDaysContributed, JsonNode warnings) {
		Iterator<String> listKeys = warnings.fieldNames();
		while (listKeys.hasNext()) {
			String key = listKeys.next();
			JsonNode message = warnings.get(key);
			String messageText = message.asText();
			messageText = messageText.replace("|", "%n");
			messageText = String.format(messageText);

			for (int i = 0; i < warnings.size(); i++) {
				
				if(registerDaysContributed.get(i).getAfiliation_number() == null){
					registerDaysContributed.get(i).setWarnings(messageText);
				} 
				else {
				 if (registerDaysContributed.get(i).getAfiliation_number().equals(key)){
					registerDaysContributed.get(i).setWarnings(messageText);
				 }
				}
			}

		}
	}

}
