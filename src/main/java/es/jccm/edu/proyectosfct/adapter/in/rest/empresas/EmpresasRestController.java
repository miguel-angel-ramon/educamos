package es.jccm.edu.proyectosfct.adapter.in.rest.empresas;

import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.proyectosfct.adapter.in.rest.datosterritoriales.model.CodigoPaisDto;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.CodigoPais;
import es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales.ICodigoPaisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.EmpresaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.EmpresaEmpleadosListDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.EmpresaListDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.empresas.model.TipoEmpresaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.FamiliaDto;
import es.jccm.edu.proyectosfct.application.domain.empresas.Empresa;
import es.jccm.edu.proyectosfct.application.domain.empresas.EmpresaEmpleadosList;
import es.jccm.edu.proyectosfct.application.domain.empresas.EmpresaList;
import es.jccm.edu.proyectosfct.application.domain.empresas.TipoEmpresa;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import es.jccm.edu.proyectosfct.application.ports.in.empresas.IEmpresasService;
import es.jccm.edu.proyectosfct.application.ports.in.usuarios.IParamsFCTService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Empresas", description = "Servicio con las operaciones sobre Empresas")
public class EmpresasRestController {
	
	@Autowired
	private IEmpresasService empresasService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	IParamsFCTService iParamsFCTService;

	@Autowired
	ICodigoPaisService codigoPaisService;
	
	// Create
	
	/**
	 * Creación de los Datos de una Empresa.
	 *
	 * @param empresaDto Datos de la empresa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de los datos de una Empresa", description = "Este metodo crea los datos de una Empresa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createEmpresa")
	public ResponseEntity<EmpresaDto> createEmpresa(
			@Parameter(description = "Datos del Convenio", required = true) @RequestBody final EmpresaDto empresaDto) {
		
		Empresa empresaIn = modelMapper.map(empresaDto, Empresa.class);
		
		empresaIn = empresasService.createEmpresa(empresaIn);
		
		EmpresaDto empresaOut = modelMapper.map(empresaIn, EmpresaDto.class);
		
		return new ResponseEntity<EmpresaDto>(empresaOut, HttpStatus.OK);
	}
	
	
	// Read
	
	/**
	 * Devuelve un listado de empresas by empleado centro
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT','ALU')")
	@Operation(summary = "Lista de Empresas", description = "Este metodo devuelve una lista con todas las empresas filtradas por empleado y centro",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoEmpresasByEmpleadoCentro/{id_tutorfctdual}/{idCentro}/{cAnno}/{tipoEmpresa}/{idPerfil}/{idCentroCombo}/{idProvincia}")
	public ResponseEntity<List<EmpresaListDto>> getListadoEmpresasByEmpleadoCentro(@PathVariable("id_tutorfctdual") Long id_tutorfctdual,
																			@PathVariable("idCentro") Long idCentro,
																			@PathVariable("cAnno") Integer cAnno,
																			@PathVariable("tipoEmpresa") Integer tipoEmpresa,
																			@PathVariable("idPerfil") Long idPerfil,
																			@PathVariable("idCentroCombo") Long idCentroCombo,
																			@PathVariable("idProvincia") Long idProvincia,
																			@RequestHeader(Constants.AUTHORIZATION) String jwt){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());
		
		List<EmpresaList> empresasList = empresasService.getListadoEmpresasByEmpleadoCentro(id_tutorfctdual, 
																							idCentro, 
																							cAnno, 
																							tipoEmpresa,
																							idPerfil,
																							idCentroCombo,
																							idProvincia,
																							datosUsuario.getXUsuarioDelphos(),
																							xEmpleadoComunica);
		
		List<EmpresaListDto> empresasOut = empresasList.stream().map(x -> modelMapper.map(x, EmpresaListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<EmpresaListDto>>(empresasOut, HttpStatus.OK);
	}
	
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Empresas", description = "Este metodo devuelve una lista con el nombre y la id de todas las empresas para ser utilizados en los combos",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoEmpresasCombo")
	public ResponseEntity<List<EmpresaListDto>> getListadoEmpresas(){
		List<EmpresaList> empresasList = empresasService.getListadoEmpresas();
		List<EmpresaListDto> empresasOut = empresasList.stream().map(x -> modelMapper.map(x, EmpresaListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<EmpresaListDto>>(empresasOut,HttpStatus.OK);
	}	

	/**
	 * Devuelve un listado de empresas paginado
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Empresas", description = "Este metodo devuelve una lista con todas las empresas paginado",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllEmpresas/{page}/{numItems}")
	public ResponseEntity<Page<EmpresaDto>> getAllEmpresas(@PathVariable("page") int page, @PathVariable("numItems") int numItems){
		
		Page<Empresa> empresasList = empresasService.getAllEmpresas(page, numItems);
		
		List<EmpresaDto> empresasOut = empresasList.getContent().stream().map(x -> modelMapper.map(x, EmpresaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<Page<EmpresaDto>>(new PageImpl<EmpresaDto>(empresasOut, empresasList.getPageable(), empresasList.getTotalElements()), HttpStatus.OK);
	}
	
	/**
	 * Devuelve un listado de empresas
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Empresas", description = "Este metodo devuelve una lista con todas las empresas",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllEmpresas")
	public ResponseEntity<List<EmpresaDto>> getAllEmpresas(){
		
		List<Empresa> empresasList = empresasService.getAllEmpresas();
		
		List<EmpresaDto> empresasOut = empresasList.stream().map(x -> modelMapper.map(x, EmpresaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<EmpresaDto>>(empresasOut, HttpStatus.OK);
	}
	
	
	/**
	 * Devuelve un listado de empresas
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Empresas", description = "Este metodo devuelve una lista con todas las empresas para la pantalla listado de empresas",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoEmpresas")
	public ResponseEntity<List<EmpresaListDto>> getEmpresas(){
		
		List<EmpresaList> empresasList = empresasService.getEmpresas();
		
		List<EmpresaListDto> empresasOut = empresasList.stream().map(x -> modelMapper.map(x, EmpresaListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<EmpresaListDto>>(empresasOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve un listado de empresas
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Empresas", description = "Este metodo devuelve una lista con todas las empresas filtradas por Familia y Provincia",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoEmpresasFamiliaProvincia/{idFamilia}/{dPais}/{idProvincia}/{conConvenio}")
	public ResponseEntity<List<EmpresaListDto>> getEmpresasFamiliaProvincia(@PathVariable("idFamilia") Long idFamilia,
																			@PathVariable("dPais") String dPais,
																			@PathVariable("idProvincia") Long idProvincia,
                                                                            @PathVariable("conConvenio") Integer conConvenio){

		
		List<EmpresaList> empresasList = empresasService.getEmpresasFamiliaProvincia(idFamilia,idProvincia,dPais,conConvenio);

		List<EmpresaListDto> empresasOut = empresasList.stream().map(x -> modelMapper.map(x, EmpresaListDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<EmpresaListDto>>(empresasOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve un page de empresas filtrado por nombre y ordenador por nombre
	 *
	 * @param name nombre de la empresa
	 * @param page pagina deseada
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Lista de Empresas filtrada", description = "Este metodo devuelve una lista con todas las empresas filtradas, páginadas y ordenadas",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEmpresasByName/{name}/{page}")
	public ResponseEntity<Page<EmpresaDto>> getEmpresasByName(@PathVariable("name") String name, @PathVariable("page") int page){
		
		Page<Empresa> empresasList = empresasService.getPageEmpresasByName(name, page);
		
		List<EmpresaDto> empresasDtoList = empresasList.getContent().stream().map(x -> modelMapper.map(x, EmpresaDto.class)).collect(Collectors.toList());

		return new ResponseEntity<Page<EmpresaDto>>(new PageImpl<EmpresaDto>(empresasDtoList, empresasList.getPageable(), empresasList.getTotalElements()), HttpStatus.OK);
	}
	
	/**
	 * Devuelve el número de empresas activas
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Contador de empresas", description = "Este metodo devuelve el numero de empresas activas en el sistema",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCountEmpresas")
	public ResponseEntity<Long> getCountEmpresas(){
		return new ResponseEntity<Long>(empresasService.countEmpresas(), HttpStatus.OK);
		
	}
	
	
	
	/**
	 * Devuelve las familias profesionales de una empresa
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Familias profesional empresas", description = "Este metodo devuelve las familias profesionales de una empresa",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllFamiliasEmpresaById/{idEmpresa}")
	public ResponseEntity<List<FamiliaDto>> getFamiliasEmpresa(@PathVariable("idEmpresa") Long idEmpresa){

		List<Familia> familias = empresasService.getFamiliasEmpresa(idEmpresa);
		
		List<FamiliaDto> familiasOut = familias.stream().map(x -> modelMapper.map(x, FamiliaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<FamiliaDto>>(familiasOut, HttpStatus.OK);
	}
	
	
	
	/**
	 * Datos de una Empresa.
	 *
	 * @param id Id de la Empresa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener datos de una Empresa", description = "Este metodo devuelve los datos de una Empresa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEmpresaById/{idEmpresa}")
	public ResponseEntity<EmpresaDto> getEmpresaById(
			@Parameter(description = "Identificador de la Empresa", required = true) @PathVariable("idEmpresa") Long idEmpresa) {
		
		EmpresaDto empresaOut = modelMapper.map(empresasService.getEmpresaById(idEmpresa), EmpresaDto.class);
		
		List<TipoEmpresa> tipoEmpresaP = empresasService.getTiposEmpresa(idEmpresa);
		
		List<TipoEmpresaDto> tipoEmpresaO = tipoEmpresaP.stream().map(x -> modelMapper.map(x, TipoEmpresaDto.class)).collect(Collectors.toList());
		
		empresaOut.setTipos(tipoEmpresaO);
		
		return new ResponseEntity<EmpresaDto>(empresaOut, HttpStatus.OK);
	}
	
	// Update
	
	/**
	 * Actualización de los Datos de una Empresa.
	 *
	 * @param empresaDto Datos de la Empresa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Actualización de los datos de una Empresa", description = "Este metodo actualiza los datos de una Empresa",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateEmpresa")
	public ResponseEntity<EmpresaDto> updateEmpresa(
			@Parameter(description = "Datos de la Empresa", required = true) @RequestBody final EmpresaDto empresaDto) {
		
		Empresa empresaIn = modelMapper.map(empresaDto, Empresa.class);
		
		empresaIn = empresasService.updateEmpresa(empresaIn);
		
		EmpresaDto empresaOut = modelMapper.map(empresaIn, EmpresaDto.class);
		
		return new ResponseEntity<EmpresaDto>(empresaOut, HttpStatus.OK);
	}
	
	
	/**
	 * Borrado de una Empresa.
	 *
	 * @param idEmpresa Id de la Empresa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Borrado de una Empresa", description = "Este metodo borra los datos de una Empresa",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteEmpresa/{idEmpresa}")
	public ResponseEntity<HttpStatus> deleteEmpresa(
			@Parameter(description = "Identificador de la Empresa", required = true) @PathVariable("idEmpresa")Long id) {
		try {
			empresasService.deleteEmpresa(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Devuelve un listado de empresas
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de Empleados de la una Empresa", description = "Este metodo devuelve una lista con todos los empleados de una empresa",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEmpleadosEmpresa/{idEmpresa}")
	public ResponseEntity<List<EmpresaEmpleadosListDto>> getEmpleadosEmpresa(@PathVariable("idEmpresa") Long idEmpresa){
		
		List<EmpresaEmpleadosList> empresasEmpleadosList = empresasService.getEmpleadosEmpresa(idEmpresa);
		
		List<EmpresaEmpleadosListDto> empresasEmpleadosListOut = empresasEmpleadosList.stream().map(x -> modelMapper.map(x, EmpresaEmpleadosListDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<EmpresaEmpleadosListDto>>(empresasEmpleadosListOut, HttpStatus.OK);
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener provincias", description = "Este metodo devuelve el listado de provincias manchegas",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoPaises")
	public ResponseEntity<List<CodigoPaisDto>> getListadoPaises() {

		List<CodigoPais> paises = codigoPaisService.findAll();

		List<CodigoPaisDto> codigoPaisDto = paises.stream().map(x -> modelMapper.map(x, CodigoPaisDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<CodigoPaisDto>>(codigoPaisDto, HttpStatus.OK);
	}

}
