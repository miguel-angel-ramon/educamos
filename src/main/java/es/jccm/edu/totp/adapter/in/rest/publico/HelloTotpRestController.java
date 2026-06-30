package es.jccm.edu.totp.adapter.in.rest.publico;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.totp.adapter.in.rest.BasePath;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.TotpBasePath)
@Tag(name = "Hello World", description = "Hello world servicio de prueba")
public class HelloTotpRestController {
		
	@GetMapping("/publico/hello/{quien}")
	@ResponseBody
	public String helloworld(
			@PathVariable(value="quien",required=true) String quien) {
		return "hola "+quien;
	}
}


