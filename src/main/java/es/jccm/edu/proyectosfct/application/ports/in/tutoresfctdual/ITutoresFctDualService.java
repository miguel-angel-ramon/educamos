package es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual;

import java.util.List;
import org.springframework.data.domain.Page;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.DatosSustituto;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.ListadoTutoresFctDual;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;

public interface ITutoresFctDualService {
	
	// Create
	
	TutorFctDual createTutorFctDual(TutorFctDual tutor);
	
	// Read
	
	List<TutorFctDual> getAllTutoresFctDual(); 
	
	List<ListadoTutoresFctDual> getAllTutoresFctDualCentro(Long idCentro); 
	
	List<ListadoTutoresFctDual> getAllTutoresDelegacion(Long xUsuario, Long idPerfil, Long idCentro, Long idCentroCombo, Long idProvincia);
	
	List<ListadoTutoresFctDual> getAllTutoresIdFctDual(Long idCentro, Long idTutor);
	
	List<TutorFctDual> getAllTutorFctDualCentro(Long idCentro); 
	
	TutorFctDual getById(Long idTutorFctDual);
	
	Page<TutorFctDual> getTutoresFctDualByName(String name, Integer page);
	
	// Update
	
	TutorFctDual updateTutorFctDual(TutorFctDual tutor);
	
	// Delete
	
	void deleteTutorFctDual(Long idTutorFctDual);
	
	Boolean getTieneDependenciasTutorById(Long idTutorFctDual);

	TutorFctDual getTutorByEmpleado(Long xEmpleado, String fTomapos);
	
	DatosSustituto getIdTutorSustituido(Long xEmpleado, String fTomapos);

	DatosSustituto getTutorSustituto(Long xEmpleado, String fTomapos);

	String getCodigoPerfil(Long idPerfil);



}
