package es.jccm.edu.incidencias.adapter.in.rest;


import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import es.jccm.edu.incidencias.application.domain.ActualizarFormulario;
import es.jccm.edu.incidencias.application.domain.Formulario;
import es.jccm.edu.incidencias.application.services.EnviarFormularioService;



@RestController
@RequestMapping("/publico/formularioFS")
public class FormularioController {

    private final EnviarFormularioService enviarFormularioService;

    public FormularioController(EnviarFormularioService enviarFormularioService) {
        this.enviarFormularioService = enviarFormularioService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> recibirFormulario(
            @RequestPart("formulario") Formulario formulario,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments) {

      

        formulario.setAttachaments(attachments);
        return enviarFormularioService.enviarFormulario(formulario);
    }

    
    @PutMapping("/requesters/{requesterId}")
    public ResponseEntity<String> actualizarFormulario(@PathVariable Long requesterId, @RequestBody ActualizarFormulario formulario) {
        return enviarFormularioService.actualizarFormulario(formulario,requesterId);    
    }

}