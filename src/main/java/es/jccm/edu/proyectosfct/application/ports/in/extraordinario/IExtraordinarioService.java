package es.jccm.edu.proyectosfct.application.ports.in.extraordinario;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model.AutorizacionExtraordinarioDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model.AutorizacionExtraordinarioHistorialDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoAux;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.AutorizacionExtraordinario;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.AutorizacionExtraordinarioHistorial;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.ListadoAutorizacionExtraordinario;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;

public interface IExtraordinarioService {

	List<ElementoSelect> getCombosAutorizacionesExtraordinario(String cbName, Integer anno, Long idCentro, Long idTutor, Long idFamilia, Long idCurso, Long idPeticion, Long idPerfil, Long idUsuario) throws Exception;

	List<ListadoAutorizacionExtraordinario> getListadoAutorizacionesExtraordinario(Integer anno,Long idCentro, Long idTutor, Long idCurso, Long idUnidad,Long idPerfil, Integer nuPeticion, Long idUsuario);

	
	List<ElementoSelect> getComboAlumnos(Integer anno, Long idCentro, Long idTutor, Long idCurso, Long idUnidad, Integer nuPeticion, Long idUsuario);


	List<AutorizacionExtraordinario> createAlumnoExtraordinario (List<AutorizacionExtraordinarioDto> listAutorizacionExtraordinarioDto, Long idPerfil, Long idUsuario);

	
	AutorizacionExtraordinario getAutorizacionExtraordinario(Long idAutExt);
	

	Alumno getDatosAlumnoExtra(Long idMatricula);

	
	AlumnoAux getDateScheduleAlumnoExtra(Long idMatricula);

	AutorizacionExtraordinarioHistorial createSiguienteEstadoFlujoHistorial(AutorizacionExtraordinarioHistorialDto autorizacionExtraordinarioDto, 
																			Long idPerfil, 
																			String abrev,
																			Long xUsuarioDelphos);
	
	void generarAnexosXI(Long idPerfil, 
						 Long idCentro, 
						 Long xUsuarioDelphos,
						 String abrev, 
						 Integer annoAnexo, 
						 Long idTutor,
						 Long idCurso,
						 Long idUnidad,
						 Integer nuPeticion,
						 String justificacion, 
						 String control, 
						 String costes) throws Exception;

	void deleteAutorizacionDesplazamiento(Long idAutExtPro);

	void uploadAdjunto(Long id, MultipartFile file, boolean esAuto) throws RodalExceptionService, InsertarDocFault, IOException;

	byte[] generarAutorizacionCentroAnexoXi(Long idAutAnexo, String abrevEstadoSiguiente);
}
