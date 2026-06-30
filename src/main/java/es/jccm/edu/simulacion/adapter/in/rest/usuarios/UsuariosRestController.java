package es.jccm.edu.simulacion.adapter.in.rest.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.simulacion.adapter.in.rest.usuarios.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import es.jccm.edu.simulacion.application.domain.usuarios.CentroUsuarioList;
import es.jccm.edu.simulacion.application.domain.usuarios.PerfilUsuarioList;
import es.jccm.edu.simulacion.application.domain.usuarios.UsuarioList;
import es.jccm.edu.simulacion.application.ports.in.usuarios.IUsuariosService;
import es.jccm.edu.simulacion.application.ports.in.usuarios.UsuarioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import es.jccm.edu.simulacion.application.domain.usuarios.projection.UsuarioSimuSh;
@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/simulacion")
@Tag(name = "Servicio Usuarios Escritorio", description = "Servicio de usuarios para el módulo de simulación de usuarios del escritorio")
public class UsuariosRestController {

	@Autowired
	private IUsuariosService usuariosService;

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Value("${enable.simulation}")
	private Boolean simulationEnabled;


	/**
	 * Conectamos con la BBDD de Comunica y rescatamos un listado de usuarios del
	 * código del centro y perfil introducidos.
	 *
	 * @param codCentro
	 * @param codPerfil
	 * @return List<UsuarioDto>
	 */
	@Operation(summary = "Devuelve un listado de usuarios de un centro y perfil concretos", description = "Este metodo devuelve un objeto con los datos de un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/listadoUsuarios")
	public ResponseEntity<List<UsuarioDto>> getListadoUsuarios(@RequestParam("codCentro") Long codCentro,
			@RequestParam("codPerfil") Long codPerfil) {

		List<UsuarioDto> usuariosList = usuariosService.getListadoUsuarios(codCentro, codPerfil);

		return new ResponseEntity<>(usuariosList, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Comunica y rescatamos un listado de usuarios del
	 * código del centro y perfil introducidos.
	 *
	 * @param codCentro
	 * @param codPerfil
	 * @return List<UsuarioDto>
	 */
	@Operation(summary = "Devuelve un listado de usuarios de un centro y perfil concretos de la bbdd de Delphos", description = "Este metodo devuelve un objeto con los datos de un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/listadoUsuariosDelphos")
	public ResponseEntity<List<UsuarioDto>> getListadoUsuariosDelphos(@RequestParam("codCentro") Long codCentro,
			@RequestParam("codPerfil") Long codPerfil) {

		List<UsuarioList> usuariosList = usuariosService.getListadoUsuariosDelphos(codCentro, codPerfil);

		List<UsuarioDto> usuariosOut = usuariosList.stream().map(x -> modelMapper.map(x, UsuarioDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(usuariosOut, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Comunica y rescatamos los datos del usuario con
	 * idUsuario introducido.
	 *
	 * @param jwt
	 * @return DatosUsuarioDto
	 */
	@Operation(summary = "Recupera los datos de un usuario", description = "Este metodo devuelve un objeto con los datos de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/datosUsuario")
	public ResponseEntity<DatosUsuarioDto> getUsuario(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		DatosUsuarioDto usuarioOut = modelMapper.map(usuariosService.getDatosUsuario(datosUsuario.getUsuarioDelphos(), datosUsuario.getUsuarioComunica(), datosUsuario.getIdEmpleadoDelphos()), DatosUsuarioDto.class);

		usuarioOut.setOid(datosUsuario.getOid());

		return new ResponseEntity<>(usuarioOut, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Comunica y rescatamos los perfiles del usuario con
	 * idUsuario introducido.
	 *
	 * @param jwt
	 * @return List<PerfilUsuarioDto>
	 */
	@Operation(summary = "Recupera los perfiles de un usuario", description = "Este metodo devuelve una lista con todos los perfiles de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/perfilesUsuario")
	public ResponseEntity<List<PerfilUsuarioDto>> getPerfilesUsuario(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<PerfilUsuarioList> perfilesList = usuariosService.getPerfilesUsuario(datosUsuario.getUsuarioComunica());

		List<PerfilUsuarioDto> perfilesOut = perfilesList.stream().map(x -> modelMapper.map(x, PerfilUsuarioDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(perfilesOut, HttpStatus.OK);
	}
	
	/**
	 * Conectamos con la BBDD de Delphos y rescatamos los datos del usuario con
	 * idUsuario introducido.
	 *
	 * @param jwt
	 * @return DatosUsuarioDto
	 */
	@Operation(summary = "Recupera los datos de un usuario", description = "Este metodo devuelve un objeto con los datos de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/datosUsuarioDelphos") 
	
	public ResponseEntity<DatosUsuarioDto> getUsuarioDelphos(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
        DatosUsuarioDto usuarioOut = modelMapper.map(usuariosService.getDatosUsuarioDelphos(datosUsuario.getOid()), DatosUsuarioDto.class);

		return new ResponseEntity<>(usuarioOut, HttpStatus.OK);
	}
	
	@Operation(summary = "Recupera los perfiles de Delphos de un usuario FCT/DGC", description = "Este metodo devuelve una lista con todos los perfiles de delphos de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/perfilesDelphosUsuarioFCTDGC")
	public ResponseEntity<List<PerfilUsuarioDto>> getPerfilesDelphosUsuarioModuloFCTDGC(
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<PerfilUsuarioList> perfilesList = usuariosService.getPerfilesDelphosUsuarioFCTDGC(datosUsuario.getUsuarioComunica());

		List<PerfilUsuarioDto> perfilesOut = perfilesList.stream().map(x -> modelMapper.map(x, PerfilUsuarioDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(perfilesOut, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Delhpos y rescatamos los perfiles del usuario con
	 * idUsuario introducido.
	 *
	 * @param jwt
	 * @return List<PerfilUsuarioDto>
	 */
	@Operation(summary = "Recupera los perfiles de Delphos de un usuario", description = "Este metodo devuelve una lista con todos los perfiles de delphos de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/perfilesDelphosUsuario")
	public ResponseEntity<List<PerfilUsuarioDto>> getPerfilesDelphosUsuario(
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<PerfilUsuarioList> perfilesList = usuariosService.getPerfilesDelphosUsuario(datosUsuario.getUsuarioDelphos());

		List<PerfilUsuarioDto> perfilesOut = perfilesList.stream().map(x -> modelMapper.map(x, PerfilUsuarioDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(perfilesOut, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Comunica y rescatamos los centros del usuario con
	 * idUsuario introducido.
	 *
	 * @param jwt
	 * @return List<CentroUsuarioDto>
	 */
	@Operation(summary = "Recupera los centros de un usuario", description = "Este metodo devuelve una lista con todos los perfiles de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/centrosUsuario")
	public ResponseEntity<List<CentroUsuarioDto>> getCentrosUsuario(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<CentroUsuarioList> centrosList = usuariosService.getCentrosUsuario(datosUsuario.getUsuarioComunica());

		List<CentroUsuarioDto> centrosOut = centrosList.stream().map(x -> modelMapper.map(x, CentroUsuarioDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(centrosOut, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Comunica y rescatamos los centros del usuario con
	 * idUsuario introducido.
	 *
	 * @param jwt
	 * @return List<CentroUsuarioDto>
	 */
	@Operation(summary = "Recupera los centros de un usuario", description = "Este metodo devuelve una lista con todos los perfiles de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/centrosDelphosUsuario")
	public ResponseEntity<List<CentroUsuarioDto>> getCentrosDelphosUsuario(
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<CentroUsuarioList> centrosList = usuariosService.getCentrosDelphosUsuario(datosUsuario.getUsuarioDelphos());

		List<CentroUsuarioDto> centrosOut = centrosList.stream().map(x -> modelMapper.map(x, CentroUsuarioDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(centrosOut, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Comunica y rescatamos los datos personales de un
	 * usuario.
	 *
	 * @param jwt
	 * @return DatosPersonalesUsuarioDto
	 */
	@Operation(summary = "Devuelve un listado de usuarios de un centro y perfil concretos", description = "Este metodo devuelve un objeto con los datos de un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/datosPersonales")
	public ResponseEntity<DatosPersonalesUsuarioDto> getDatosPersonalesUsuario(
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		DatosPersonalesUsuarioDto datosPersonalesOut = modelMapper
                .map(usuariosService.getDatosPersonalesUsuario(datosUsuario.getXUsuarioComunica(),datosUsuario.getOid() ), DatosPersonalesUsuarioDto.class);

		return new ResponseEntity<>(datosPersonalesOut, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Delphos y rescatamos el año del curso académico
	 * actual.
	 *
	 * @return CursoAcademico
	 */
	@Operation(summary = "Devuelve el año del curso académico actual.", description = "Este método devuelve un numero con el año actual", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/annoActual")
	public ResponseEntity<CursoAcademicoDto> getAnnoActual() {

		CursoAcademicoDto cursoAcademico = modelMapper.map(usuariosService.getAnnoActual(), CursoAcademicoDto.class);

		return new ResponseEntity<>(cursoAcademico, HttpStatus.OK);
	}

	/**
	 * Conectamos con la BBDD de Delphos y rescatamos el año del curso académico
	 * actual.
	 *
	 * @return CursoAcademico
	 */
	@Operation(summary = "Devuelve el año del curso académico actual hasta el 1 de julio", description = "Este método devuelve un numero con el año actual", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/annoAcademico")
	public ResponseEntity<Long> getAnnoAcademico() {
		try {
			return new ResponseEntity<>(usuariosService.getAnnoAacademico(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Conectamos con la BBDD de Delphos y rescatamos los datos del usuario con
	 * idUsuario introducido.
	 *
	 * @param xPerfil
	 * @return DatosUsuarioDto
	 */
	@Operation(summary = "Recupera los datos de un usuario", description = "Este metodo devuelve un objeto con los datos de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })


	@GetMapping("/datosUsuarioModAcc")
	public ResponseEntity<DatosUsuarioDto> getUsuarioModAcc(@RequestParam("xPerfil") String xPerfil,
															@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		DatosUsuarioDto usuarioOut = modelMapper.map(usuariosService.getDatosUsuarioModAcc(xPerfil,datosUsuario.getOid()), DatosUsuarioDto.class);

		return new ResponseEntity<>(usuarioOut, HttpStatus.OK);
	}
	
	@Operation(summary = "Recupera los perfiles de Delphos de un usuario FCT/DGC", description = "Este metodo devuelve una lista con todos los perfiles de delphos de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })

	@GetMapping("/perfilesModAccUsuarioFCTDGC")
	public ResponseEntity<List<PerfilUsuarioDto>> getPerfilesModAccUsuarioModuloFCTDGC(			
			@RequestParam("xPerfil") String xPerfil,
			@RequestParam("xCentro") String xCentro,
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<PerfilUsuarioList> perfilesList = usuariosService.getPerfilesModAccUsuarioFCTDGC(xPerfil,xCentro,datosUsuario.getOid());

		List<PerfilUsuarioDto> perfilesOut = perfilesList.stream().map(x -> modelMapper.map(x, PerfilUsuarioDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(perfilesOut, HttpStatus.OK);
	}
	
	@Operation(summary = "Recupera un json con la posición de los componentes del usuario", description = "Este metodo devuelve un json con la posición de los componentes de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/componentes")
	public ResponseEntity<String> getPosicionComponentesEscritorio(
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
	
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		String posicionComponentes = usuariosService.getPosicionComponentesEscritorio(datosUsuario.getNif()); 

		return new ResponseEntity<>(posicionComponentes, HttpStatus.OK);
	}
	
	@Operation(summary = "Recupera un json con la posición de los componentes del usuario", description = "Este metodo devuelve un json con la posición de los componentes de un usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setComponentes")
	public ResponseEntity<String> setPosicionComponentesEscritorio(
			@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestBody String posicion) {


		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		usuariosService.setPosicionComponentesEscritorio(datosUsuario.getNif(), posicion);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "registrar centro y perfil por defecto", description = "Este metodo registra el perfil y centro preferentes del usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setConfigUserDefault")
	public ResponseEntity<String> setConfigUsuario(
			@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestBody String options) {
	
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		usuariosService.setConfigUsuario(datosUsuario.getNif(), options); 

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Operation(summary = "update tour", description = "Este metodo cambia el valor de 'tour' si se ha realizado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setTourUser")
	public ResponseEntity<String> setTourUsuario(
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
	
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		usuariosService.setTourUsuario(datosUsuario.getNif()); 

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Operation(summary = "update tour", description = "Este metodo cambia el valor de 'tourEvaluacion' si se ha realizado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setTourEvaluacionUser")
	public ResponseEntity<String> setTourEvaluacionUsuario(
			@RequestHeader(Constants.AUTHORIZATION) String jwt) {
	
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		usuariosService.setTourEvaluacionUsuario(datosUsuario.getNif()); 

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista de docentes a los que sustituye o que le sustituyen
	 *
	 * @return the response entity
	 */
	@Operation(summary = "Lista de docentes a los que sustituye o que le sustituyen", description = "Este método devuelve la lista de docentes a los que sustituye o que le sustituyen", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@GetMapping("/getDocentesSustitutos")
	public ResponseEntity<DocenteSustitutoListDTO> getDocentesSustitutos(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Integer anno) {
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			DocenteSustitutoListDTO docentesList = new DocenteSustitutoListDTO();
			List<DocenteSustitutoDTO> docentes = new ArrayList<DocenteSustitutoDTO>();
			
			docentesList.setSustituto(usuariosService.isDocenteSustituto(datosUsuario.getIdEmpleadoDelphos(), anno));
			
			
			if (docentesList.getSustituto()) {
				docentes = usuariosService.getDocentesSustituye(datosUsuario.getIdEmpleadoDelphos(), anno);
			} else {
				docentes = usuariosService.getDocentesSustitutos(datosUsuario.getIdEmpleadoDelphos(), anno);
			}
			
			docentesList.setDocentes(docentes);

			return new ResponseEntity<>(docentesList, HttpStatus.OK);
		} catch (Exception ex) {
			// log.error("Se ha producido un error al obtener el listado de criterios de evaluación de la actividad", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Conectamos con la BBDD de Comunica y rescatamos los perfiles del usuario con
	 * idUsuario introducido.
	 *
	 * @param jwt
	 * @return List<PerfilUsuarioDto>
	 */
	@Operation(summary = "Recupera el perfil por defecto de un usuario", description = "Este metodo devuelve los perfiles por defecto del usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/perfilPorDefecto")
	public ResponseEntity<PerfilDefectoUsuarioDTO> getPerfilDefecto(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		PerfilDefectoUsuarioDTO perfilDefecto = usuariosService.getPerfilDefecto(datosUsuario.getOid());

		return new ResponseEntity<>(perfilDefecto, HttpStatus.OK);
	}

	/**
	 * resetear centro y perfil por defecto
	 * 
	 * @param jwt
	 * @return
	 */
	@Operation(summary = "resetear centro y perfil por defecto", description = "Este metodo resetea el perfil y centro preferentes del usuario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/resetConfigUserDefault")
	public ResponseEntity<String> resetConfigUsuario(@RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		usuariosService.resetConfigUsuario(datosUsuario.getOid());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Indica si es coordindor de ciclo o no
	 *
	 * @param jwt
	 * @return
	 */
	@Operation(summary = "Indica si el profesor es coordinador de ciclo", description = "Este metodo indica si el profesor es coordinador de ciclo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/isCoordinadorCicloOrFP")
	public ResponseEntity<Boolean> isCoordinadorCicloOrFP(@RequestHeader(Constants.AUTHORIZATION) String jwt,
													  @RequestParam("fechaTomaPosesion") String fechaTomaPosesion) {

		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			Boolean coordinador = usuariosService.isCoordinadorCicloOrFP(datosUsuario.getIdEmpleadoDelphos(), fechaTomaPosesion);

			return new ResponseEntity<>(coordinador,HttpStatus.OK);
		} catch (Exception ex) {
			// log.error("Se ha producido un error", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Indica si es jefe de departamento o no
	 *
	 * @param jwt
	 * @return
	 */
	@Operation(summary = "Indica si el profesor es jefe de departamento", description = "Este metodo indica si el profesor es coordinador de ciclo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/isJefeDepartamento")
	public ResponseEntity<Boolean> isJefeDepartamento(@RequestHeader(Constants.AUTHORIZATION) String jwt,
													  @RequestParam("fechaTomaPosesion") String fechaTomaPosesion) {

		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			Boolean coordinador = usuariosService.isJefeDepartamento(datosUsuario.getIdEmpleadoDelphos(), fechaTomaPosesion);

			return new ResponseEntity<>(coordinador,HttpStatus.OK);
		} catch (Exception ex) {
			// log.error("Se ha producido un error", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Indica si es jefe de departamento o no
	 *
	 * @param jwt
	 * @return
	 */
	@Operation(summary = "Indica si es Inspector", description = "Este metodo indica si es Inspector", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/isInspector")
	public ResponseEntity<Long> isInspector(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			Long inspector = usuariosService.isInspector(datosUsuario.getIdEmpleadoDelphos());
			return new ResponseEntity<>(inspector,HttpStatus.OK);
		} catch (Exception ex) {
			// log.error("Se ha producido un error", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Comprueba si el usuario logado tiene acceso a la Shell mediante la tabla DELPHOS_MODACC.ROLES_USUARIOS
	 *
	 * @param jwt
	 * @return
	 */
	@Operation(summary = "Comprueba si el usuario logado tiene acceso a la Shell", description = "Este metodo comprueba si el usuario logado tiene acceso a la Shell mediante la tabla DELPHOS_MODACC.ROLES_USUARIOS", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAccesoShellProfesorYDirector")
	public ResponseEntity<Boolean> getAccesoShellProfesorYDirector(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			return new ResponseEntity<>(usuariosService.getAccesoShellProfesorYDirector(datosUsuario.getOid()),HttpStatus.OK);
		} catch (Exception ex) {
			// log.error("Se ha producido un error", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Indica si es jefe de departamento o no
	 *
	 * @param idCentro
	 * @return
	 */
	@Operation(summary = "Indica si es Inspector", description = "Este metodo indica si es Inspector", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDirectorReport")
	public ResponseEntity<Long> getDirectorReport(@RequestParam("idCentro") Long idCentro) {
		try {

			Long inspector = usuariosService.getDirectorReport(idCentro);
			return new ResponseEntity<>(inspector,HttpStatus.OK);
		} catch (Exception ex) {
			// log.error("Se ha producido un error", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Conectamos con la BBDD de Comunica y rescatamos un listado de usuarios del
	 * código del centro y perfil introducidos.
	 *
	 * @param codCentro
	 * @param codPerfil
	 * @return List<UsuarioDto>
	 */
	@Operation(summary = "Devuelve un listado de usuarios de un centro y perfil concretos", description = "Este metodo devuelve un objeto con los datos de un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/listadoUsuariosSimulacion")
	public ResponseEntity<List<UsuarioSimuSh>> getListadoUsuariosSimulacion(@RequestParam("codCentro") Long codCentro,
			@RequestParam("codRol") String codRol
		) {

		List<UsuarioSimuSh> usuariosList = usuariosService.getListadoUsuariosSimulacionSh(codCentro,codRol);

		return new ResponseEntity<>(usuariosList, HttpStatus.OK);
	}

	/**
	 * Indica si es jefe de departamento o no
	 *
	 * @param jwt
	 * @return
	 */
	@Operation(summary = "Indica si es Inspector", description = "Este metodo indica si es Inspector", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/centrosAnteriores")
	public ResponseEntity<List<Long>> centrosAnteriores(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			List<Long> centros = usuariosService.centrosAnteriores(datosUsuario.getIdEmpleadoDelphos());
			return new ResponseEntity<>(centros,HttpStatus.OK);
		} catch (Exception ex) {
			// log.error("Se ha producido un error", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Indica si es jefe de departamento o no
	 *
	 * @param jwt
	 * @return
	 */
	@Operation(summary = "Indica si es Inspector", description = "Este metodo indica si es Inspector", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/AnyosAnteriores")
	public ResponseEntity<List<Long>> AnyosAnteriores(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			List<Long> anyos = usuariosService.anyosAnteriores();
			return new ResponseEntity<>(anyos,HttpStatus.OK);
		} catch (Exception ex) {
			// log.error("Se ha producido un error", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}