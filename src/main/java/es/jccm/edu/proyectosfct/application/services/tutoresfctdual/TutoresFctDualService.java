package es.jccm.edu.proyectosfct.application.services.tutoresfctdual;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual.TutoresFctDualRepository;
import es.jccm.edu.proyectosfct.application.domain.eventos.EventoDocumentacion;
import es.jccm.edu.proyectosfct.application.domain.eventos.Eventos;
import es.jccm.edu.proyectosfct.application.domain.eventos.TipoMessage;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.DatosSustituto;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.ListadoTutoresFctDual;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.PuestoTrabajoEmpleado;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.DatosSustitutoProjection;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.EventoTutorFctDtoProjection;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.ListadoTutoresFctDualProjection;
import es.jccm.edu.proyectosfct.application.ports.in.eventos.IEventosService;
import es.jccm.edu.proyectosfct.application.ports.in.programas.IProgramasFctService;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.IPuestoTrabajoEmpleadoService;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.ITutoresFctDualService;
import es.jccm.edu.proyectosfct.application.services.Utiles;
import java.util.UUID;

@Service
public class TutoresFctDualService implements ITutoresFctDualService {
	
	private static final Long ID_PERFIL_DELEGACION_FCT = 13207L;

	private static final Long ID_PERFIL_DELEGACION_FCT_1 = 15207L;
	
	private static final String TOPIC = "es.jccm.edu.cuenta.application.domain.event.cuentaevent.CuentaEvent";

	@Autowired
	private TutoresFctDualRepository tutoresFctDualRepository;
	
	@Autowired
	private IPuestoTrabajoEmpleadoService puestoTrabajoEmpleadoService;
	
	@Autowired
	private IProgramasFctService programasFctDual;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private IEventosService eventos;
	
	// Create

	@Override
	public TutorFctDual createTutorFctDual(TutorFctDual tutor) {
		
		TutorFctDual tutorNew = tutoresFctDualRepository.save(tutor); 
		
		this.generarEvento(tutorNew);
		
		return tutorNew;
	}
	
	// Read

	@Override
	public List<TutorFctDual> getAllTutoresFctDual() {
		
		return (List<TutorFctDual>) tutoresFctDualRepository.findAll();
	}
	
	@Override
	public List<ListadoTutoresFctDual> getAllTutoresFctDualCentro(Long idCentro) {
		
        //List<PuestoTrabajoEmpleado> listPuestos = puestoTrabajoEmpleadoService.getEmpleadosByCentro(idCentro);		
		
		//return (List<TutorFctDual>) tutoresFctDualRepository.findByPuestoTrabajoEmpleadoIn(listPuestos);
		
		List<ListadoTutoresFctDualProjection> tutoresProjection = tutoresFctDualRepository.getListaTutoresCentro(idCentro); 		
		
		return tutoresProjection.stream().map(entity -> modelMapper.map(entity, ListadoTutoresFctDual.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ListadoTutoresFctDual> getAllTutoresDelegacion(Long xUsuario, Long idPerfil, Long idCentro, Long idCentroCombo, Long idProvincia) {
		
		List<ListadoTutoresFctDualProjection> tutoresProjection = null;
		
		if (ID_PERFIL_DELEGACION_FCT.equals(idPerfil) || ID_PERFIL_DELEGACION_FCT_1.equals(idPerfil)) {
			
			tutoresProjection = tutoresFctDualRepository.getListaTutoresDelegacionProvincias(idProvincia);
																			
			
		} else {
			
			tutoresProjection = tutoresFctDualRepository.getListaTutoresDelegacion(xUsuario,
															    				 idPerfil,
																				 idCentro,
																				 idCentroCombo);
			
		}
		
		  		
		
		return tutoresProjection.stream().map(entity -> modelMapper.map(entity, ListadoTutoresFctDual.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ListadoTutoresFctDual> getAllTutoresIdFctDual(Long idCentro, Long idTutor) {
		
		List<ListadoTutoresFctDualProjection> tutoresProjection = tutoresFctDualRepository.getListaTutoresIdCentro(idCentro,idTutor); 		
		
		return tutoresProjection.stream().map(entity -> modelMapper.map(entity, ListadoTutoresFctDual.class)).collect(Collectors.toList());
	}
	
	
	@Override
	public List<TutorFctDual> getAllTutorFctDualCentro(Long idCentro) {
		
        //List<PuestoTrabajoEmpleado> listPuestos = puestoTrabajoEmpleadoService.getEmpleadosByCentro(idCentro);		
		
		//return (List<TutorFctDual>) tutoresFctDualRepository.findByPuestoTrabajoEmpleadoIn(listPuestos);
		
		//List<TutorFctDual> tutoresProjection = tutoresFctDualRepository.getListaTutorCentro(idCentro);
		
		List<TutorFctDual> tutoresProjection = tutoresFctDualRepository.findByPuestoTrabajoEmpleadoCentroId(idCentro);
		
		return tutoresProjection.stream().map(entity -> modelMapper.map(entity, TutorFctDual.class)).collect(Collectors.toList());
	}
	
	@Override
	public TutorFctDual getById(Long idTutorFctDual) {
		Optional<TutorFctDual> res = tutoresFctDualRepository.findById(idTutorFctDual);
		
		return res.isPresent() ? res.get() : null;
	}

	@Override
	public Page<TutorFctDual> getTutoresFctDualByName(String name, Integer page) {
		Pageable paging = PageRequest.of(page, 10);
		return tutoresFctDualRepository.findByPuestoTrabajoEmpleadoEmpleadoNombreContainingIgnoreCase(name, paging);
	}

	// Update
	
	public TutorFctDual updateTutorFctDual(TutorFctDual tutor) {
		
		TutorFctDual tutorOrigen = getById(tutor.getId());
		
		if (tutorOrigen != null) {
			BeanUtils.copyProperties(tutor, tutorOrigen, Utiles.getNullPropertyNames(tutor));
			
			if(tutor.getPuestoTrabajoEmpleado() != null && tutor.getPuestoTrabajoEmpleado().getId().getEmpleado() != null && tutor.getPuestoTrabajoEmpleado().getId().getFechaTomaPosesion() != null) {
				PuestoTrabajoEmpleado puestoTrabajo = puestoTrabajoEmpleadoService.getPuestoTrabajoEmpleadoByIdAndTomaPos(tutor.getPuestoTrabajoEmpleado().getId().getEmpleado(), tutor.getPuestoTrabajoEmpleado().getId().getFechaTomaPosesion());
				tutorOrigen.setPuestoTrabajoEmpleado(puestoTrabajo);
				

					tutorOrigen.setFechaBaja(tutor.getFechaBaja());
			}
			
			tutorOrigen = tutoresFctDualRepository.save(tutorOrigen);
			
			if (tutorOrigen != null) {
				
				this.generarEvento(tutorOrigen);
			}
			
		}

		return tutorOrigen;
	}
	
	
	public void generarEvento(TutorFctDual tutorOrigen)  {		
				
	   Eventos evento = new Eventos();
	   Integer order = eventos.generaOrden(); 
	   
	   EventoTutorFctDtoProjection event = tutoresFctDualRepository.getDatosEventos(tutorOrigen.getId());	   
	   
	   try {

		   if (event != null) {
			  evento.setId(UUID.randomUUID().toString());
			  evento.setOrder(order+1);
			  evento.setTime(new Date());
			  evento.setTopic(TOPIC);
			  
			  EventoDocumentacion documentacion = new EventoDocumentacion();
			  documentacion.setDocumentacion(event.getNif());
			  documentacion.setTipoDocumento(event.getTipide());
			  
			  List<EventoDocumentacion> documentaciones = new ArrayList<>();
			  documentaciones.add(documentacion);
			  
			  TipoMessage message  = new TipoMessage(); 
			  message.setDocumentaciones(documentaciones);
					  
			  evento.setMessage(objectMapper.writeValueAsString(message));
			  
			  eventos.generarEvento(evento);   
		   }
		   
	   } catch (JsonProcessingException e) {
			
	   }	
		
	}
	
	
	
	// Delete
	
	@Transactional
	public void deleteTutorFctDual(Long id) {
		
		TutorFctDual tutorOrigen = getById(id);
		
		this.generarEvento(tutorOrigen);
		
		tutoresFctDualRepository.deleteById(id);	
		
	}

	@Override
	public Boolean getTieneDependenciasTutorById(Long idTutorFctDual) {
	
		return programasFctDual.getProgramasTutor(idTutorFctDual).size()>0?true:false;
	
	}

	@Override
	public TutorFctDual getTutorByEmpleado(Long xEmpleado, String fTomapos) {
		
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			Date d_fTomapos = formato.parse(fTomapos);
			
			PuestoTrabajoEmpleado puestoTrabajo = puestoTrabajoEmpleadoService.getPuestoTrabajoEmpleadoByIdAndTomaPos(xEmpleado, d_fTomapos);
			
			Optional<TutorFctDual> res = tutoresFctDualRepository.findByPuestoTrabajoEmpleado(puestoTrabajo);
			
			return res.isPresent() ? res.get() : null;			
			
		} catch (ParseException e) {

			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public DatosSustituto getIdTutorSustituido(Long xEmpleado, String fTomapos) {
		
		DatosSustituto datos = new DatosSustituto();
		datos.setId(-1L);
		datos.setNombre("");
		datos.setIdTutorFctDual(-1L);
		
		Optional<DatosSustitutoProjection> res = tutoresFctDualRepository.getIdTutorSustituido(xEmpleado,fTomapos);	
		
		return  res.isEmpty() ? datos :  modelMapper.map(res.get(), DatosSustituto.class);	
	}

	@Override
	public DatosSustituto getTutorSustituto(Long xEmpleado, String fTomapos) {
		DatosSustituto datos = new DatosSustituto();
		
		Optional<DatosSustitutoProjection> res = tutoresFctDualRepository.getTutorSustituto(xEmpleado,fTomapos);		
		
		return  res.isPresent() ?  modelMapper.map(res.get(), DatosSustituto.class) : datos;
	}

	@Override
	public String getCodigoPerfil(Long idPerfil) {
		 return tutoresFctDualRepository.getCodigoPerfil(idPerfil);
	}	

}
