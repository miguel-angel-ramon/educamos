package es.jccm.edu.aulasVirtuales.application.services.aulasVirtuales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.jccm.edu.aulasVirtuales.adapter.out.repository.aulasVirtuales.AulaVirtualRepository;
import es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.AulaVirtualList;
import es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.UsuarioAulaVirtual;
import es.jccm.edu.aulasVirtuales.application.domain.aulasVirtuales.projection.AulaVirtualProjection;
import es.jccm.edu.aulasVirtuales.application.ports.in.aulasVirtuales.IAulaVirtualService;

@Service
public class AulaVirtualService implements IAulaVirtualService {

	private static final Map<String, String> servidoresAulasVirtuales = new HashMap<>();
	
	//Rutas de los WebServices
	public static final String BASE_TOKEN = "/webservice/rest/server.php?wstoken=";
	public static final String BASE_WEBSERVICE = "&wsfunction=";
	public static final String MEDIA_TYPE_JSON = "&moodlewsrestformat=json";
	public static final String READ_USER_IDNUMBER = "core_user_get_users_by_idnumber";

//	@Autowired
//	private ClientComunicaRestTemplatePortOut clientComunicaRestTemplatePortOut;
	
	@Autowired
	private AulaVirtualRepository aulaVirtualRepository;

	@Autowired
	private ModelMapper modelMapper;

	@PostConstruct
	private void postConstruct() {
		
		String anno = aulaVirtualRepository.getAnnoAcademicoActual().substring(2,4);
	
		anno += (Integer.parseInt(anno)+1);
		
		servidoresAulasVirtuales.put("1", "https://aulasfp" + anno + ".castillalamancha.es");
		servidoresAulasVirtuales.put("2", "https://aulaseparegesp" + anno + ".castillalamancha.es");
		servidoresAulasVirtuales.put("3", "https://aulasprimaria" + anno + ".castillalamancha.es");
		servidoresAulasVirtuales.put("4", "https://aulasesoabgu" + anno + ".castillalamancha.es");
		servidoresAulasVirtuales.put("5", "https://aulasesocrcu" + anno + ".castillalamancha.es");
		servidoresAulasVirtuales.put("6", "https://aulasesoto" + anno + ".castillalamancha.es");
		servidoresAulasVirtuales.put("7", "https://aulasbach" + anno + ".castillalamancha.es");
		servidoresAulasVirtuales.put("8", "https://aulasciclos" + anno + ".castillalamancha.es");
	}

	@Override
	public List<AulaVirtualList> getAulasVirtualesByProfesor(Long idEmpleado, Integer anno) {

		List<AulaVirtualProjection> aulasVirtualesProjection = aulaVirtualRepository
				.findAulasVirtualesByEmpleado(idEmpleado, anno);

		List<AulaVirtualList> aulasVirtuales = aulasVirtualesProjection.stream()
				.map(aulaVirtual -> modelMapper.map(aulaVirtual, AulaVirtualList.class)).collect(Collectors.toList());

		var x = 0;

		for (AulaVirtualProjection aulaProjection : aulasVirtualesProjection) {

			if (aulaProjection.getIdPlataforma() != null && aulaProjection.getIdMoodle() != null
					&& aulaProjection.getIdPlataforma().matches("MOODLE_\\d*_\\d*_\\d*")) {

				aulasVirtuales.get(x)
						.setUrlAula(servidoresAulasVirtuales.get(aulaProjection.getIdPlataforma().split("_")[2])
								.concat("/course/view.php?id=").concat(aulaProjection.getIdMoodle().toString()));

			}

			x++;

		}

		return aulasVirtuales;
	}
	
	@Override
	public List<AulaVirtualList> getAulasVirtuales(Long idEmpleado, Long oid, Integer anno) {

		List<AulaVirtualList> aulasVirtuales = null;
		
		try {
			//Obtengo la información de las Aulas Virtuales del profesor
			List<AulaVirtualProjection> aulasVirtualesProjection = aulaVirtualRepository
					.findAulasVirtualesByEmpleadoAnno(idEmpleado, anno);
			
			//Transformo la projección a entidad para poder trabajar con sus datos
			aulasVirtuales = aulasVirtualesProjection.stream()
					.map(aulaVirtual -> modelMapper.map(aulaVirtual, AulaVirtualList.class)).collect(Collectors.toList());
	
			// Creo una instancia de RestTemplate
	        RestTemplate restTemplate = new RestTemplate();
			
			// Establece las cabeceras de la solicitud
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	
	        // Crea una entidad de solicitud con las cabeceras
	        HttpEntity<String> entity = new HttpEntity<>(headers);
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			//Llamo a los segmentos para rescatar la información del usuario en cada idPlataforma
			for(AulaVirtualList aulaVirtual : aulasVirtuales) {
				//Construyo la url
				String url = servidoresAulasVirtuales.get(aulaVirtual.getIdPlataforma()) + this.BASE_TOKEN +
						aulaVirtual.getTokenPlataforma() + this.BASE_WEBSERVICE + this.READ_USER_IDNUMBER + this.MEDIA_TYPE_JSON + "&userids[0]=" + oid;
						
				// Realiza la llamada
		        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
				
		        String respuesta = response.getBody();
		        
		        UsuarioAulaVirtual usuarioAulaVirtual = objectMapper.readValue(respuesta, UsuarioAulaVirtual[].class)[0];
				
		        aulaVirtual.setUsuarioAulaVirtual(usuarioAulaVirtual);
			}
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	
      return aulasVirtuales;
	}
	
}
