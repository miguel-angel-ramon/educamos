package es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos.model.IncrementoIntentosFallidosDto;
import es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos.model.UsuarioCerberosDto;
import es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos.model.VUsuarioCerberosDto;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioRequest;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioRequestModel;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.domain.VUsuarioCerberos;
import es.jccm.edu.gestionidentidades.application.ports.in.IApiCerberosService;
import es.jccm.edu.gestionidentidades.application.ports.in.IUsuarioService;
import es.jccm.edu.gestionidentidades.application.ports.in.IVUsuarioCerberosService;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.AltaUsuarioException;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.InvalidNifException;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.ResultSetException;
import es.jccm.edu.gestionidentidades.application.services.VUsuarioCerberosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
//@RequestMapping("${spring.data.rest.base-path:/api}" + "/gestionidentidades/apicerberos")
@RequestMapping(BasePath.ApiCerberisV1BasePath)
@Tag(name = "Servicio Alta Usuario e incremento de fallos", description = "Servicio con las operaciones sobre alta de usuario e incremento de fallos")
public class ApiCerberosRestController {

	@Value("${numIntentos.numMaxIntentos}")
	private int numMaxIntentos;
	
    @Autowired
    IUsuarioService usuarioService;
    
    @Autowired
    IVUsuarioCerberosService vUsuarioCerberosService;
    
    @Autowired
    IApiCerberosService apiCerberosService;
    
    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "Incrementa los intentos fallidos", description = "Nuevo servicio REST Intentos Fallidos para ser consumido desde Cerberos",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PutMapping("/incrementaIntentosFallidos/{oidUsuario}")
    public ResponseEntity<?> incrementaIntentosFallidos(@PathVariable Long oidUsuario) {

        Optional<Usuario> optionalUsuario = usuarioService.findById(oidUsuario);

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Usuario usuario = optionalUsuario.get();

        usuario.setIntentosFallidosAcceso(usuario.getIntentosFallidosAcceso() + 1);

        if (usuario.getIntentosFallidosAcceso() > numMaxIntentos) {
            usuario.setBloqueado("S");
        }

        usuarioService.save(usuario);

        return ResponseEntity.ok(
                new IncrementoIntentosFallidosDto(usuario.getBloqueado(), usuario.getIntentosFallidosAcceso())
        );

    }

    @Operation(summary = "Se da de alta un nuevo usuario o se actualiza el existente", description = "Nuevo servicio REST alta usuario por nif",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/altaUsuario")
    public ResponseEntity<?> altaUsuario(@RequestBody AltaUsuarioRequestModel usuario) {

    	try {
			AltaUsuarioRequest request = toAltaUsuarioPapasRequest(usuario);
			Usuario user = apiCerberosService.altaUsuarioP(request);
			return ResponseEntity.ok(new UsuarioCerberosDto(user.getId(), user.getLogin()));		
		} catch (AltaUsuarioException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Formato de fecha incorrecto",HttpStatus.INTERNAL_SERVER_ERROR);		}

    }

    @Operation(summary = "Se da de alta un nuevo usuario o se actualiza el existente", description = "Nuevo servicio REST alta usuario por uid Ldap",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/altaUsuarioConLdap")
    public ResponseEntity<?> altaUsuarioConLdap(@RequestBody AltaUsuarioRequestModel usuario, @RequestParam String uidLdapUsuario) {

    	try {
			AltaUsuarioRequest request = toAltaUsuarioPapasRequest(usuario);
			Usuario user;
			try {
				user = apiCerberosService.altaUsuarioLdap(request, uidLdapUsuario);
				return ResponseEntity.ok(new UsuarioCerberosDto(user.getId(), user.getLogin()));
			} catch (AltaUsuarioException e) {
				e.printStackTrace();
				return new ResponseEntity<String>("Error al dar de alta al usuario",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Formato de fecha incorrecto",HttpStatus.INTERNAL_SERVER_ERROR);		}

    }
    
    @Operation(summary = "Se obtienen los datos del usuario por login y jwt", description = "Nuevo servicio REST para obtener los datos del usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getDatosUsuarioByQuerydsl")
    public ResponseEntity<?> getDatosUsuarioByQuerydsl(@QuerydslPredicate(root = VUsuarioCerberos.class) Predicate predicate, @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<VUsuarioCerberosDto> datosUsuarioPage = vUsuarioCerberosService.getDatosUsuarioByQuerydsl(predicate, pageable);
            if(datosUsuarioPage.hasContent()) {
                return new ResponseEntity<>(datosUsuarioPage, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("No se encontraron registros" , HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error al recuperar los datos del usuario",HttpStatus.INTERNAL_SERVER_ERROR);    
        }
    }
    
    @Operation(summary = "Se obtienen los datos del usuario por login y jwt", description = "Nuevo servicio REST para obtener los datos del usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getDatosUsuarioByLogin")
    public ResponseEntity<?> getDatosUsuarioByLogin(@RequestParam String login) {
    	try {
    		return new ResponseEntity<VUsuarioCerberosDto>(vUsuarioCerberosService.getDatosUsuarioByLogin(login),HttpStatus.OK);
    	}catch (ResultSetException e) {	
    		e.printStackTrace();
			return new ResponseEntity<String>("Error, se ha encontrado más de un registro para el login: " + login,HttpStatus.BAD_REQUEST);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error al recuperar los datos del usuario",HttpStatus.INTERNAL_SERVER_ERROR);	
		}
    }
    
    @Operation(summary = "Se obtienen los datos del usuario por UidLdap y jwt", description = "Nuevo servicio REST para obtener los datos del usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getDatosUsuarioByUidLdap")
    public ResponseEntity<?> getDatosUsuarioByUidLdap(@RequestParam String uidLdap) {
    	try {
    		return new ResponseEntity<VUsuarioCerberosDto>(vUsuarioCerberosService.getDatosUsuarioByUidLdap(uidLdap),HttpStatus.OK);
    	}catch (ResultSetException e) {	
    		e.printStackTrace();
			return new ResponseEntity<String>("Error, se ha encontrado más de un registro para el UidLdap: " + uidLdap,HttpStatus.BAD_REQUEST);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error al recuperar los datos del usuario",HttpStatus.INTERNAL_SERVER_ERROR);	
		}
    }
    
    @Operation(summary = "Se obtienen los datos del usuario por Nif y jwt", description = "Nuevo servicio REST para obtener los datos del usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado"),
                @ApiResponse(responseCode = "400", description = "Error de registros")})
    @GetMapping("/getDatosUsuarioByNif")
    public ResponseEntity<?> getDatosUsuarioByNif(@RequestParam String nif) {
    	try {
    		return new ResponseEntity<VUsuarioCerberosDto>(vUsuarioCerberosService.getDatosUsuarioByNif(nif),HttpStatus.OK);
    	}catch(ResultSetException e) {
    		e.printStackTrace();
    		return new ResponseEntity<String>("Error, se ha encontrado más de un registro para el nif: " + nif,HttpStatus.BAD_REQUEST);	
    	}catch (InvalidNifException e){
    		e.printStackTrace();
    		return new ResponseEntity<String>("Error, el nif no es válido: " + nif,HttpStatus.BAD_REQUEST);
    	}catch (Exception e) {
    		e.printStackTrace();
			return new ResponseEntity<String>("Error al recuperar los datos del usuario",HttpStatus.INTERNAL_SERVER_ERROR);		
    	}			
    }
    
    private AltaUsuarioRequest toAltaUsuarioPapasRequest(AltaUsuarioRequestModel model) throws ParseException {
		return AltaUsuarioRequest.builder()
		.nombre(model.getNombre())
		.apellido1(model.getApellido1())
		.apellido2(model.getApellido2())
		.nif(model.getNif())
		.fechaNacimiento(parseFechaNacimiento(model.getStrFechaNacimiento()))
		.build();
	}
    
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    
    private Date parseFechaNacimiento(String strFecha) throws ParseException {
		if(strFecha==null) {
			return null;
		}
		
		return sdf.parse(strFecha);
	}
    
    private VUsuarioCerberosDto convertToDto(VUsuarioCerberos vUsuarioCerberos) {
    	VUsuarioCerberosDto vUsuarioCerberoDTO = modelMapper.map(vUsuarioCerberos, VUsuarioCerberosDto.class);
        return vUsuarioCerberoDTO;
    }

}
