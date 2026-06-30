package es.jccm.edu.gestionidentidades.adapter.in.rest.interfazUsuario;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.gestionidentidades.application.ports.in.IInterfazUsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "", description = "")
@CrossOrigin
@Transactional
@RequestMapping("${spring.data.rest.base-path:/api}" + "/interfazUsuario")
public class InterfazUsuarioRestController {

    @Autowired
    IInterfazUsuarioService interfazUsuarioService;
    
    //No utilizar puede borrar perfiles en uso de la bbdd a usuarios
    @GetMapping("/lanzarInterfazUsuario")
    public void lanzarInterfazUsuario() {

    	interfazUsuarioService.lanza();

    }
    
    @GetMapping("/lanzarInterfazUsuarioProcedureCompleto")
    public void lanzarInterfazUsuarioProcedureCompleto() {

    	interfazUsuarioService.lanzaProcedureCompleto();

    }
    
    @GetMapping("/lanzarInterfazUsuarioProcedurePorFunciones")
    public void LanzarInterfazUsuarioProcedurePorFunciones() {

    	interfazUsuarioService.lanzaProcedurePorFunciones();

    }
    
    @GetMapping("/lanzarInterfazAsync")
    public CompletableFuture<String> ejemploAsync() {
        CompletableFuture<Integer> bloque1 = interfazUsuarioService.bloque1();
        CompletableFuture<Integer> bloque2 = interfazUsuarioService.bloque2();

        // Esperar a que ambos bloques de código asíncrono finalicen
        return CompletableFuture.allOf(bloque1, bloque2)
                .thenApplyAsync(v -> "Ambos bloques asíncronos han finalizado");
    }

}
