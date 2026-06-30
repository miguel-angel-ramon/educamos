package es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.ConfiguracionUsuarioDto;
import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.DocumentacionesDto;
import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.UsuarioDto;
import es.jccm.edu.gestionidentidades.application.domain.NuevoUsuarioPlataforma;
import es.jccm.edu.gestionidentidades.application.domain.NuevoUsuarioPlataformaDesdeDelphos;
import es.jccm.edu.gestionidentidades.application.domain.ReestablecerClaveFamiliasRequest;
import es.jccm.edu.gestionidentidades.application.domain.RegistroUsuarioPlataformaResponse;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.in.GenerarSolicitudRecuperacionClaveUseCase;
import es.jccm.edu.gestionidentidades.application.ports.in.IConfiguracionUsuarioService;
import es.jccm.edu.gestionidentidades.application.ports.in.IDocumentacionesService;
import es.jccm.edu.gestionidentidades.application.ports.in.IRegistroUsuarioPlataformaService;
import es.jccm.edu.gestionidentidades.application.ports.in.IUsuarioService;
import es.jccm.edu.gestionidentidades.application.ports.in.MandarMailRecuperacionClaveUseCase;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@Tag(name = "Sincronización de usuarios", description = "Servicio destinado para la sincronización de usuarios")
@RequestMapping("${spring.data.rest.base-path:api}" + "/sincronizausuariogesidi")
@Transactional
public class SincronizaUsuarioMARestController {

    @Autowired
    IRegistroUsuarioPlataformaService sincronizaUsuarioMAService;

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    IDocumentacionesService documentacionesService;
    
    @Autowired
    IDatosUsuarioJwtService usuarioJwtService;
    
    @Autowired
    IConfiguracionUsuarioService configuracionUsuarioService;
    
    @Autowired
   	private MandarMailRecuperacionClaveUseCase mandarMailRecuperacionClaveUseCase;
    
    @Autowired
    private GenerarSolicitudRecuperacionClaveUseCase generarSolicitudRecuperacionClaveUseCase;

    @PostMapping("/darAltaUsuario")
    public RegistroUsuarioPlataformaResponse registroUsuarioPlataforma(@RequestBody NuevoUsuarioPlataforma usuario) {

        sincronizaUsuarioMAService.recibeUsuario(usuario);

        return null;
    }

    @PostMapping("/altaUsuarioDesdeDelphos")
    public RegistroUsuarioPlataformaResponse registroUsuarioPlataformaDesdeDelphos(@RequestBody NuevoUsuarioPlataformaDesdeDelphos usuario) {

        sincronizaUsuarioMAService.recibeUsuarioDesdeDelphos(usuario);

        return null;
    }

    @PostMapping("/modificarUsuario")
    public ResponseEntity<UsuarioDto> modificarUsuario(@RequestBody UsuarioDto usuarioDto) {

        return ResponseEntity.ok(usuarioService.modificarUsuario(usuarioDto));
    }

    @PostMapping("/modificarLoginClaveUsuario")
    public ResponseEntity<Usuario> modificarLoginClaveUsuario(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam String login, @RequestParam String clave) {

        return ResponseEntity.of(usuarioService.modificarLoginClaveUsuario(usuarioJwtService.getDatosUsuarioByJwt(jwt).getOid(), login, clave));
    }

    @PostMapping("/modificarDocumentacion")
    public ResponseEntity<DocumentacionesDto> modificarDocumentacion(@RequestBody DocumentacionesDto documentacion) {

        return ResponseEntity.ok(documentacionesService.modificarDocumentacion(documentacion));
    }

    @PostMapping("/aceptarLopd")
    public ResponseEntity<Void> aceptarLopd(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

        if (usuarioService.aceptarLdap(usuarioJwtService.getDatosUsuarioByJwt(jwt).getOid())) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/completardatospersonales")
    public ResponseEntity<Void> completarDatosPersonales(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam String sexo, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento) {

        if (usuarioService.completarDatosPersonales(usuarioJwtService.getDatosUsuarioByJwt(jwt).getOid(), sexo, fechaNacimiento)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/solicitudRecuperacionClave")
    @ResponseBody
    public void generarSolicitudRecuperacionClave(@RequestBody ReestablecerClaveFamiliasRequest oid) {
        //no es necesario retornar la solicitud de recuperación de clave
        generarSolicitudRecuperacionClaveUseCase.newSolicitudRecuperacionClave(oid.getOid());
    }
    
	@PostMapping("/mandarMailRecuperacionClave")
	@ResponseBody
	public ResponseEntity<String> mandarMailRecuperacionClave(@RequestBody ReestablecerClaveFamiliasRequest datos) {
		String error=mandarMailRecuperacionClaveUseCase.mandarEmailRecuperacionClave(datos.getOid(), datos.getEmail());
		if(error==null){
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PostMapping("/guardarConfiguracionUsuario")
    public ResponseEntity<ConfiguracionUsuarioDto> saveUpdate(@RequestBody ConfiguracionUsuarioDto configuracionUsuarioDto) {

        return ResponseEntity.ok(configuracionUsuarioService.saveUpdate(configuracionUsuarioDto));
    }

}
