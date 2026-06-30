package es.jccm.edu.incidencias.adapter.out;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import es.jccm.edu.incidencias.application.domain.ActualizarFormulario;
import es.jccm.edu.incidencias.application.domain.Formulario;
@Slf4j
@Component
public class FreshServiceClient {

	 	private final RestTemplate restTemplate;
	    private  String baseUrl;
	    private  String apiKey;

	    public FreshServiceClient(
	    	      @Value("${freshservice.url}") String baseUrl,
	              @Value("${freshservice.apiKey}") String apiKey) {
	        this.restTemplate = new RestTemplate();
	        this.baseUrl = baseUrl;
	        this.apiKey = apiKey;
	    }
	    
	    public ResponseEntity<String> enviarDatos(Formulario formulario) {

	        // 🔑 Construcción del header Authorization: Basic base64(apiKey + ":x")
	        String auth = apiKey + ":x";
	        String encodedAuth = Base64.getEncoder()
	                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));
	        String authHeader = "Basic " + encodedAuth;

	        HttpHeaders headers = new HttpHeaders();
	        //headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Authorization", authHeader);
            headers.set("Origin", "https://educamosclm.castillalamancha.es");
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("email", formulario.getEmail());
            form.add("phone", formulario.getPhone());
            form.add("category", formulario.getCategory());
            form.add("sub_category", formulario.getSubCategory());
            form.add("item_category", formulario.getItemCategory());
            form.add("subject", formulario.getSubject());
            form.add("priority", String.valueOf(formulario.getPriority())); // ojo: como String
            form.add("status",   String.valueOf(formulario.getStatus()));
            form.add("source",   String.valueOf(formulario.getSource()));
            form.add("description", formulario.getDescription());
            
            if (formulario.getCustomFields() != null) {
                for (Map.Entry<String, String> entry : formulario.getCustomFields().entrySet()) {
                    String key = String.format("custom_fields[%s]", entry.getKey());
                    form.add(key, entry.getValue());
                }
            }
            
            if (formulario.getAttachaments() != null && !formulario.getAttachaments().isEmpty()) {

                List<Object> files = new ArrayList<>();

                for (MultipartFile file : formulario.getAttachaments()) {
                    ByteArrayResource resource;
                    try {
                        resource = new ByteArrayResource(file.getBytes()) {
                            @Override
                            public String getFilename() {
                                return file.getOriginalFilename();
                            }
                        };
                    } catch (IOException e) {
                        throw new RuntimeException("Error leyendo archivo", e);
                    }

                    HttpHeaders fileHeaders = new HttpHeaders();
                    fileHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                    HttpEntity<ByteArrayResource> filePart =
                            new HttpEntity<>(resource, fileHeaders);

                    files.add(filePart);
                }

                // 👇 clave: attachments[]
                form.put("attachments[]", files);
            }




            
            ObjectMapper mapper = new ObjectMapper();
            try {
                log.info("Calling: {}", baseUrl);
                log.info("headers: {}", mapper.writeValueAsString(headers));
                log.info("Form data: {}", mapper.writeValueAsString(form));

            } catch (JsonProcessingException e) {
                log.info(e.getMessage());
            }

	        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(form, headers);

	        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, request, String.class);

	        if (!response.getStatusCode().is2xxSuccessful()) {
	            throw new RuntimeException("Error al enviar formulario: " + response.getStatusCode());
	        }
	        
	        return ResponseEntity
	                .status(response.getStatusCode())
	                .body(response.getBody());
	    }
	    
	    
	    public ResponseEntity<String> actualizarFormulario(ActualizarFormulario formulario, Long requesterId) {
	    	  String auth = apiKey + ":x";
		        String encodedAuth = Base64.getEncoder()
		                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));
		        String authHeader = "Basic " + encodedAuth;

		        HttpHeaders headers = new HttpHeaders();
		        //headers.setContentType(MediaType.APPLICATION_JSON);
		        headers.set("Authorization", authHeader);
	            headers.set("Origin", "https://educamosclm.castillalamancha.es");
	            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

	            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

	            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();	            
	            form.add("first_name", formulario.getFirstName());
	            form.add("last_name", formulario.getLastName());
	            form.add("language", formulario.getLanguage());
	            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(form, headers);
	            ResponseEntity<String> response = restTemplate.exchange(
	                    baseUrl + "/requesters/"+requesterId,
	                    HttpMethod.PUT,
	                    request,
	                    String.class
	            );
	            if (!response.getStatusCode().is2xxSuccessful()) {
		            throw new RuntimeException("Error al enviar formulario: " + response.getStatusCode());
		        }
		        
	            return ResponseEntity
	                    .status(response.getStatusCode())
	                    .body(Optional.ofNullable(response.getBody()).orElse(null));

	    }
	    
}