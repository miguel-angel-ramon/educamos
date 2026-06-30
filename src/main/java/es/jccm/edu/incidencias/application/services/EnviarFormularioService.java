package es.jccm.edu.incidencias.application.services;



import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.jccm.edu.incidencias.adapter.out.FreshServiceClient;
import es.jccm.edu.incidencias.application.domain.ActualizarFormulario;
import es.jccm.edu.incidencias.application.domain.Formulario;

@Service
public class EnviarFormularioService {

    private final FreshServiceClient sistemaExternoClient;

    public EnviarFormularioService(FreshServiceClient sistemaExternoClient) {
        this.sistemaExternoClient = sistemaExternoClient;
    }

    public ResponseEntity<String> enviarFormulario(Formulario formulario) {
      return   sistemaExternoClient.enviarDatos(formulario);
    }
    
    public ResponseEntity<String> actualizarFormulario(ActualizarFormulario formulario, Long requesterId) {
        return   sistemaExternoClient.actualizarFormulario(formulario,requesterId);
      }
      
    
}