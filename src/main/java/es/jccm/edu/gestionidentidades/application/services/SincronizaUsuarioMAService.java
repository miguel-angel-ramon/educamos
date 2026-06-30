package es.jccm.edu.gestionidentidades.application.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.in.rest.SincronizarUsuarioMA.model.UsuarioDto;
import es.jccm.edu.gestionidentidades.adapter.out.repository.DocumentacionesRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.MotivoBloqueoRegistroPersonaRepository;
import es.jccm.edu.gestionidentidades.adapter.out.repository.TlpersonaRepository;
import es.jccm.edu.gestionidentidades.application.domain.AltaLdapRequest;
import es.jccm.edu.gestionidentidades.application.domain.AltaLdapResponse;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalResponse;
import es.jccm.edu.gestionidentidades.application.domain.Aplicacion;
import es.jccm.edu.gestionidentidades.application.domain.AplicacionesAccesiblesUsuario;
import es.jccm.edu.gestionidentidades.application.domain.AsignacionNuevasCredenciales;
import es.jccm.edu.gestionidentidades.application.domain.Documentaciones;
import es.jccm.edu.gestionidentidades.application.domain.EmpleadoDelphos;
import es.jccm.edu.gestionidentidades.application.domain.EstadoRegistro;
import es.jccm.edu.gestionidentidades.application.domain.MotivoBloqueoRegistroPersona;
import es.jccm.edu.gestionidentidades.application.domain.NuevoUsuarioPlataforma;
import es.jccm.edu.gestionidentidades.application.domain.NuevoUsuarioPlataformaDesdeDelphos;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.domain.PersonaId;
import es.jccm.edu.gestionidentidades.application.domain.RegistroUsuarioPlataformaRequest;
import es.jccm.edu.gestionidentidades.application.domain.RegistroUsuarioPlataformaResponse;
import es.jccm.edu.gestionidentidades.application.domain.TipoDocumento;
import es.jccm.edu.gestionidentidades.application.domain.TipoPersonal;
import es.jccm.edu.gestionidentidades.application.domain.Tlpersona;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.domain.Usuariot;
import es.jccm.edu.gestionidentidades.application.ports.in.GenerarNuevasCredencialesUseCase;
import es.jccm.edu.gestionidentidades.application.ports.in.IAplicacionService;
import es.jccm.edu.gestionidentidades.application.ports.in.IAplicacionesAccesiblesUsuarioService;
import es.jccm.edu.gestionidentidades.application.ports.in.IDocumentacionesService;
import es.jccm.edu.gestionidentidades.application.ports.in.IEmpleadoDelphosService;
import es.jccm.edu.gestionidentidades.application.ports.in.IPersonaService;
import es.jccm.edu.gestionidentidades.application.ports.in.IRegistroUsuarioPlataformaService;
import es.jccm.edu.gestionidentidades.application.ports.in.IUsuarioService;
import es.jccm.edu.gestionidentidades.application.ports.in.IUsuariotService;
import es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos.RegistroUsuarioPlataformaException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
public class SincronizaUsuarioMAService implements IRegistroUsuarioPlataformaService{
	
	@Autowired
	AltaLdapService altaLdapService;
	
	@Autowired
	CredencialesService credencialesService;
	
	@Autowired
	IAplicacionService aplicacionService;
	
	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	IDocumentacionesService documentacionesService;
	
	@Autowired
	DocumentacionesRepository documentacionesRepository;
	
	@Autowired
	TlpersonaRepository tlpersonaRepository;
	
	@Autowired
	MotivoBloqueoRegistroPersonaRepository motivoBloqueoRegistroPersonaRepository;
	
	@Autowired
	IUsuariotService usuariotService;
	
	@Autowired
	IPersonaService personasService;
	
	@Autowired
	IEmpleadoDelphosService empleadoDelphosService;
	
	@Autowired
	IAplicacionesAccesiblesUsuarioService aplicacionesAccesiblesUsuarioService;
	
	@Autowired
	GenerarNuevasCredencialesUseCase generarNuevasCredencialesUseCase;
	
	private static final String SUFIJO_EMAIL_DEFECTO_LDAP = "jccm.es";
	private static final String SUFIJO_EMAIL_DOCENTE_LDAP = "edu.jccm.es";

	@Override
	public EstadoRegistro registroUsuarioPlataforma(RegistroUsuarioPlataformaRequest request)
			throws RegistroUsuarioPlataformaException {
		
		EstadoRegistro ret = null;
		Integer xCentro = request.getXCentro();
		Date fechaTomaPos = request.getFechaTomaPos();		
		
			try {
				
				TipoPersonal tipoPersonal = request.getTipoPersonal();
				List<Aplicacion> codAplicaciones = aplicacionService.getAplicacionesPorDefectoSegunTipoPersonal( tipoPersonal);
							
				AltaUsuarioGlobalRequest altaUsuarioGlobalRequest=AltaUsuarioGlobalRequest.builder()
				.persona(getPersona(request))
				.identificacion(request.getIdentificacion())
				.tipide(request.getTipide())
				.codAplicaciones(codAplicaciones)
				.modo("SE-SAI")
				.correoRecuperacion(request.getCorreoExterno())
				.identificacion(request.getIdentificacion())
				.tipide(request.getTipide())
				.build();
				
				AltaUsuarioGlobalResponse ugc= altaUsuarioGlobal(altaUsuarioGlobalRequest, tipoPersonal, xCentro, fechaTomaPos);					
				
				ret = ugc.getAsignacion()!=null?EstadoRegistro.USUARIO_NUEVO:EstadoRegistro.USUARIO_EXISTENTE;	

			} catch (Exception e) {
				log.error("ocurrió un error inesperado al sincronizar empleado delphos en modulo de acceso");
				throw new RegistroUsuarioPlataformaException(e);
			}
		

		return ret;
	}
	
	@Override
	public RegistroUsuarioPlataformaResponse recibeUsuario(NuevoUsuarioPlataforma usuario) {
		
		try {

			return this.resgistroUsuarioReq(
					new RegistroUsuarioPlataformaRequest(
							TipoPersonal.findById(usuario.getTipoPersonal()),
							PersonaId.builder()
									.documentacion(usuario.getDni())
									.tipoDocumento(TipoDocumento.getByCodPersona(usuario.getTipide()))
									.build()
							, trataCorreoExterno(usuario.getCorreoExterno()), usuario.getNombre(), usuario.getApellido1(), usuario.getApellido2(), usuario.getSexo(), usuario.getFechaNacimiento(), usuario.getDni(), usuario.getTipide()));

		} catch (es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos.RegistroUsuarioPlataformaException e) {
			log.error("Error al sincronizar con el modulo de acceso  dni[" + usuario.getDni() + "]" , e);
		}
		
		return null;
	}
	
	@Override
	public RegistroUsuarioPlataformaResponse recibeUsuarioDesdeDelphos(NuevoUsuarioPlataformaDesdeDelphos usuarioDelphos) {
		
		try {

			return this.resgistroUsuarioReq(
					new RegistroUsuarioPlataformaRequest(
							TipoPersonal.findById(usuarioDelphos.getTipoPersonal()),
							PersonaId.builder()
									.documentacion(usuarioDelphos.getDni())
									.tipoDocumento(TipoDocumento.getByCodPersona(usuarioDelphos.getTipide()))
									.build()
							, trataCorreoExterno(usuarioDelphos.getCorreoExterno()), usuarioDelphos.getNombre(), usuarioDelphos.getApellido1(), usuarioDelphos.getApellido2(), usuarioDelphos.getSexo(), usuarioDelphos.getFechaNacimiento(), usuarioDelphos.getDni(), usuarioDelphos.getTipide(), usuarioDelphos.getXCentro(), usuarioDelphos.getFechaTomaPos()));

		} catch (es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos.RegistroUsuarioPlataformaException e) {
			log.error("Error al sincronizar con el modulo de acceso  dni[" + usuarioDelphos.getDni() + "]" , e);
		}
		
		return null;
	}
	
	private RegistroUsuarioPlataformaResponse resgistroUsuarioReq(RegistroUsuarioPlataformaRequest request) {
		
		RegistroUsuarioPlataformaResponse exit = new RegistroUsuarioPlataformaResponse(null, null, null);
		
		EstadoRegistro estadoRegistro = registroUsuarioPlataforma(request);
		
		return exit;

	}
	
	@Override
	public AltaUsuarioGlobalResponse altaUsuarioGlobal(AltaUsuarioGlobalRequest request, TipoPersonal tipoPersonal, Integer xCentro, Date fechaTomaPos) {
		
		
		AplicacionesAccesiblesUsuario aplicacionesAccesiblesUsuario = new AplicacionesAccesiblesUsuario();
		AltaUsuarioGlobalResponse altaUsuarioGlobalResponse = new AltaUsuarioGlobalResponse(null, null);
		UsuarioDto usuarioGlobal=null;
		Documentaciones documentacion = new Documentaciones();
		AsignacionNuevasCredenciales asignacionNuevasCredenciales;
		Usuario user = new Usuario();
		Usuariot usuariot = new Usuariot(); 
		

		//TODO Enganchar creación de credenciales con políticas de credenciales
		usuarioGlobal = credencialesService.findFirst(request.getIdentificacion(), request.getTipide());
		
		if (usuarioGlobal == null) {	
			
			Tlpersona tlpersonaByIdentificacion = tlpersonaRepository.findPersonaByIdentificacion(request.getIdentificacion());

			Persona person = request.getPersona();
			
			if(tlpersonaByIdentificacion != null && !"".equals(tlpersonaByIdentificacion)) {
				//Se crea la persona
				
				person.setXPersona(tlpersonaByIdentificacion.getXPersona());
				personasService.createPersona(request.getPersona());
			}else {
				Tlpersona tlpersona = new Tlpersona();
				tlpersona.setNombre(request.getPersona().getNombre());	
				tlpersona.setApellido1(request.getPersona().getApellido1());		
				tlpersona.setApellido2(request.getPersona().getApellido2());
				tlpersona.setFechaNacimiento(request.getPersona().getFechaNacimiento());
				tlpersona.setTipIde(request.getTipide());	
				tlpersona.setGenero(request.getPersona().getSexo());
				tlpersona.setNumIde(request.getIdentificacion());	
				tlpersona.setActprodatper('N');			
				Tlpersona tlpersonaCreada = tlpersonaRepository.save(tlpersona);
				
				List<MotivoBloqueoRegistroPersona> bloqueo = motivoBloqueoRegistroPersonaRepository.findMotivoBloqueoRegistroPersonaByXPersona(tlpersonaCreada.getXPersona());
				
				if(bloqueo == null || "".equals(bloqueo) || bloqueo.isEmpty()){
					throw new RuntimeException("El registro de la persona se ha parado debido a un bloqueo en la misma.");
				}else {
					person.setXPersona(tlpersonaCreada.getXPersona());
					personasService.createPersona(request.getPersona());
				}
				
			}
			

			documentacion = documentacionesService.construirDocumentacion(request);

			//Se crea la documentación
			documentacionesService.save(documentacion);

			asignacionNuevasCredenciales = credencialesService.generarNuevasCredenciales(user, request);

			user = usuarioService.construirUsuario(request, asignacionNuevasCredenciales);
			
			if (request.getCorreoRecuperacion() != null) {
				user.setCorreoE(request.getCorreoRecuperacion());
			}
			
			//Se crea el Usuario
			user = usuarioService.save(user);
			
			altaUsuarioGlobalResponse.setUsuarioGlobal(user);
			altaUsuarioGlobalResponse.setAsignacion(asignacionNuevasCredenciales);

			for (Aplicacion app : request.getCodAplicaciones()) {
				aplicacionesAccesiblesUsuario = aplicacionesAccesiblesUsuarioService.construirAplicacionesAccesiblesUsuario(app, user);
				aplicacionesAccesiblesUsuarioService.save(aplicacionesAccesiblesUsuario);
			}
			
			//Se da de alta en el LDAP
			AltaLdapResponse altaLdapResponse = altaLdap(request, tipoPersonal);
			
			//Se actualiza el correo en Delphos
			empleadoDelphosService.actualizaCorreoEnDelphos(altaLdapResponse, request);
			
			//Se crea el Usuariot
			if(xCentro == null || fechaTomaPos == null) {

				usuariot = usuariotService.construirUsuariot(person, user);

			} else {

				List <EmpleadoDelphos> el = empleadoDelphosService.findEmpleadoDelphosByDocumentoExtended(request.getIdentificacion(), xCentro, fechaTomaPos);
				EmpleadoDelphos es = el.get(0);
				usuariot = usuariotService.construirUsuariotDesdeDelphos(person, user, es, xCentro);
			}
	
			Usuariot usuariotCreado = usuariotService.crearUsuariot(usuariot);
			
		}		
		return altaUsuarioGlobalResponse;
	}
	
	private String trataCorreoExterno(String correoExterno) {
		if ("-1".equals(correoExterno)) {
			return null;
		}
		return correoExterno;
	}
	
	private Persona getPersona(RegistroUsuarioPlataformaRequest request) {
		List<PersonaId> listpersonaId= new ArrayList<PersonaId>();
		listpersonaId.add(request.getPersonaId());
		
		Persona persona = Persona.builder()
				          .nombre(request.getNombre())
				          .apellido1(request.getApellido1())
				          .apellido2(request.getApellido2())
				          .sexo(request.getSexo())
				          .fechaNacimiento(request.getFechaNacimiento()).build();
		return persona;
	}
	
	public void borrarUsuario(Long idUsuario, Long idPersona) {
		
		documentacionesRepository.deleteByOidPersona(idPersona);
		aplicacionesAccesiblesUsuarioService.deleteByIdUsuario(idUsuario);
		usuarioService.limpiaBorrar(idUsuario);
		usuarioService.borrarUsuarioByIdPersona(idPersona);
		personasService.deletePersona(idPersona);
	}
	
	@Override
	public AltaLdapResponse altaLdap(AltaUsuarioGlobalRequest request, TipoPersonal tipoPersonal) {
		String sufijoEmail = TipoPersonal.PROFESOR.equals(tipoPersonal) ? SUFIJO_EMAIL_DOCENTE_LDAP : SUFIJO_EMAIL_DEFECTO_LDAP;

		AltaLdapRequest altaLdapRequest=
		AltaLdapRequest.builder()
		.documento(request.getIdentificacion())
		.nombre(request.getPersona().getNombre())
		.apellido1(request.getPersona().getApellido1())
		.apellido2(request.getPersona().getApellido2())
		.correoRecuperacion(request.getCorreoRecuperacion())
		.sufijoEmail(sufijoEmail)
		.tipoPersonal(tipoPersonal)
		.build();
		
		AltaLdapResponse response=altaLdapService.altaLDAP(altaLdapRequest);
		
		return response;
	}
	
}
