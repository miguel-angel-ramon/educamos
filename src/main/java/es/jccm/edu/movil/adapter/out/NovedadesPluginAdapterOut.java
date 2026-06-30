package es.jccm.edu.movil.adapter.out;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.jccm.edu.movil.adapter.in.rest.novedades.model.FieldNoticiaImagenDTO;
import es.jccm.edu.movil.adapter.in.rest.novedades.model.NovedadesPublicoDTO;
import es.jccm.edu.movil.application.ports.out.INovedadesPlugin;

@Service
public class NovedadesPluginAdapterOut implements INovedadesPlugin {

	@Value("${portal.url}")
	private String URL; 
	
	@Value("${front.base-path}")
	public String BASE_PATH_FRONT;
	
	private static final String FIELD_ENLACES_INTERES = "field_enlaces_interes";

	private static final String TITLE = "title";

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<NovedadesPublicoDTO> obtenerNovedades() {
        List<NovedadesPublicoDTO> novedades = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root;

        try {
            String response = restTemplate.getForObject(URL, String.class);
            root = objectMapper.readTree(response);
        } catch (RestClientException e) {
            // Manejar excepciones relacionadas con la llamada HTTP
            System.err.println("Error al realizar la llamada HTTP: " + e.getMessage());
            return novedades;
        } catch (JsonProcessingException e) {
            // Manejar excepciones relacionadas con el procesamiento del JSON
            System.err.println("Error al procesar el JSON: " + e.getMessage());
            return novedades;
        }

        for (JsonNode node : root) {
            try {
                NovedadesPublicoDTO novedad = objectMapper.treeToValue(node, NovedadesPublicoDTO.class);

                List<FieldNoticiaImagenDTO> listaUrls = novedad.getField_noticia_imagen();
                for (FieldNoticiaImagenDTO lurl : listaUrls) {
					String url = lurl.getUrl();
					
					int index = url.indexOf("portal/");
					String result = url;
					 
					if (index != -1) {
					    result = url.substring(index); 
					}
					lurl.setUrl(BASE_PATH_FRONT+result);
				}                
                novedad.setField_noticia_imagen(listaUrls);
                novedades.add(novedad);
            } catch (JsonProcessingException e) {
                // Manejar excepciones relacionadas con la conversión de JSON a objeto
                System.err.println("Error al convertir el JSON a objeto: " + e.getMessage());
            }
        }
        return novedades;
    }
    
	@Value("${portal.url-novedadesFiltradas}")
	private String URLNOVEDADESFILTRADAS; 
	
	@Override
	public List<NovedadesPublicoDTO> obtenerNovedadesFiltradas() {
	    List<NovedadesPublicoDTO> noticias = obtenerNovedades();
	    List<NovedadesPublicoDTO> novedades = fetchAndParseNovedades();
	    List<NovedadesPublicoDTO> elementosbanner = filtrarNovedadesPorTitulo(novedades, noticias);
	    return elementosbanner.isEmpty() ? noticias : elementosbanner;
	}

	private List<NovedadesPublicoDTO> fetchAndParseNovedades() {
	    List<NovedadesPublicoDTO> novedades = new ArrayList<>();
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode root;

	    try {
	        String response = restTemplate.getForObject(URLNOVEDADESFILTRADAS, String.class);
	        root = objectMapper.readTree(response);
	    } catch (RestClientException | JsonProcessingException e) {
	        System.err.println("Error al obtener o procesar JSON: " + e.getMessage());
	        return novedades;
	    }

	    for (JsonNode node : root) {
	        try {
	            NovedadesPublicoDTO novedad = procesarNodoNovedad((ObjectNode) node, objectMapper);
	            if (novedad != null) {
	                actualizarUrlsImagen(novedad);
	                novedades.add(novedad);
	            }
	        } catch (JsonProcessingException e) {
	            System.err.println("Error al convertir el JSON a objeto: " + e.getMessage());
	        }
	    }

	    return novedades;
	}

	private NovedadesPublicoDTO procesarNodoNovedad(ObjectNode nodeObject, ObjectMapper objectMapper) throws JsonProcessingException {
	    normalizeTextList(nodeObject, TITLE, objectMapper);
	    normalizeTextList(nodeObject, "body", objectMapper);
	    normalizeTextList(nodeObject, "field_entradilla", objectMapper);
	    normalizeTextList(nodeObject, "field_fecha_publicacion_noticia", objectMapper);

	    normalizeObjectList(nodeObject, "field_noticia_imagen", objectMapper, "url");
	    normalizeObjectList(nodeObject, "field_archivos_relacionados", objectMapper, "url");
	    normalizeObjectList(nodeObject, "nid", objectMapper, "value");

	    normalizeEnlacesInteres(nodeObject, objectMapper);

	    return objectMapper.treeToValue(nodeObject, NovedadesPublicoDTO.class);
	}

	private void actualizarUrlsImagen(NovedadesPublicoDTO novedad) {
	    List<FieldNoticiaImagenDTO> listaUrls = novedad.getField_noticia_imagen();
	    if (listaUrls == null) return;

	    for (FieldNoticiaImagenDTO lurl : listaUrls) {
	        String url = lurl.getUrl();
	        if (url != null) {
	            int index = url.indexOf("portal/");
	            String result = index != -1 ? url.substring(index) : url;
	            lurl.setUrl(BASE_PATH_FRONT + result);
	        }
	    }
	}

	private List<NovedadesPublicoDTO> filtrarNovedadesPorTitulo(List<NovedadesPublicoDTO> novedades, List<NovedadesPublicoDTO> noticias) {
	    List<NovedadesPublicoDTO> elementosFiltrados = new ArrayList<>();

	    for (NovedadesPublicoDTO novedadFiltrada : novedades) {
	        String tituloFiltrado = obtenerTituloSeguro(novedadFiltrada);
	        for (NovedadesPublicoDTO noticia : noticias) {
	            String tituloNoticia = obtenerTituloSeguro(noticia);
	            if (tituloFiltrado != null && tituloFiltrado.equalsIgnoreCase(tituloNoticia)) {
	                elementosFiltrados.add(noticia);
	                break;
	            }
	        }
	    }

	    return elementosFiltrados;
	}

	private String obtenerTituloSeguro(NovedadesPublicoDTO dto) {
	    try {
	        return dto.getTitle().get(0).getValue().trim();
	    } catch (Exception e) {
	        return null;
	    }
	}
    
    private void normalizeTextList(ObjectNode nodeObject, String fieldName, ObjectMapper mapper) {
        JsonNode field = nodeObject.get(fieldName);
        if (field == null || field.isBoolean() || field.isNull()) {
            nodeObject.set(fieldName, mapper.createArrayNode());
        } else if (field.isTextual()) {
            ArrayNode arrayNode = mapper.createArrayNode();
            ObjectNode valueNode = mapper.createObjectNode();
            valueNode.put("value", field.asText());
            arrayNode.add(valueNode);
            nodeObject.set(fieldName, arrayNode);
        }
    }

    private void normalizeObjectList(ObjectNode nodeObject, String fieldName, ObjectMapper mapper, String keyName) {
        JsonNode field = nodeObject.get(fieldName);
        if (field == null || field.isArray()) {
            return;
        }

        ArrayNode arrayNode = mapper.createArrayNode();

        if (field.isTextual()) {
            String text = field.asText();
            if (!text.isBlank()) {
                ObjectNode obj = mapper.createObjectNode();
                obj.put(keyName, text);
                arrayNode.add(obj);
            }
        } else if (field.isObject()) {
            arrayNode.add(field);
        }

        nodeObject.set(fieldName, arrayNode);
    }

    private void normalizeEnlacesInteres(ObjectNode nodeObject, ObjectMapper mapper) {
        JsonNode field = nodeObject.get(FIELD_ENLACES_INTERES);

        ArrayNode arrayNode = mapper.createArrayNode();

        if (field == null || field.isNull()) {
            nodeObject.set(FIELD_ENLACES_INTERES, arrayNode);
            return;
        }

        if (field.isArray()) {
            for (JsonNode item : field) {
                if (item.isTextual()) {
                    String uri = item.asText();
                    ObjectNode enlaceNode = mapper.createObjectNode();
                    enlaceNode.put("uri", uri);
                    enlaceNode.put(TITLE, (String) null); // o valor por defecto
                    arrayNode.add(enlaceNode);
                } else if (item.isObject()) {
                    arrayNode.add(item);
                }
            }
        } else if (field.isTextual()) {
            ObjectNode enlaceNode = mapper.createObjectNode();
            enlaceNode.put("uri", field.asText());
            enlaceNode.put(TITLE, (String) null);
            arrayNode.add(enlaceNode);
        }

        nodeObject.set(FIELD_ENLACES_INTERES, arrayNode);
    }

}
