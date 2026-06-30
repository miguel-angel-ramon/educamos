package es.jccm.edu.shared.configuration.swagger;

import org.springdoc.core.customizers.OpenApiCustomiser;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;

@AllArgsConstructor 
public class BasePathOpenApiCustomiser implements OpenApiCustomiser {

	private String basePath;

	@Override
	public void customise(OpenAPI oApi) {
		Paths paths = oApi.getPaths();
		// To avoid ConcurrentModificationException
		String[] keySet = paths.keySet().toArray(new String[0]);
		for (String key : keySet) {
			PathItem pathItem = paths.get(key);
			// Remove the basePath from the individual operation
			paths.put(key.replace(basePath, ""), pathItem);
			paths.remove(key);
		}

		Server server = oApi.getServers().get(0);
		// Add the basePath to the server entry
		server.setUrl(server.getUrl() + basePath);
	}
}