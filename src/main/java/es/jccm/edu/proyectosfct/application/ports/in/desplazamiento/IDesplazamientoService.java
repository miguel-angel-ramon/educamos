package es.jccm.edu.proyectosfct.application.ports.in.desplazamiento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionDesplazamientoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionDesplazamientoHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionesAnexosHistorialDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamiento;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamientoHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexos;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexosHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.HistoricoFlujoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.ListadoAutorizacionDesplazamiento;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.ListadoAutorizacionesAnexos;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import net.sf.jasperreports.engine.JRException;

public interface IDesplazamientoService {

//	List<AutorizacionDesplazamiento> createAutorizacionDesplazamiento(
//			List<AutorizacionDesplazamientoDto> listAutorizacionDesplazamientoDto);

	void uploadFicheroAutorizacionDesplazamiento(Long idAutDes, Long idCentro, String idRodal, List<MultipartFile> files, Long xUsuarioDelphos) throws RodalExceptionService, InsertarDocFault, IOException;

	void deleteFicheroAutorizacionDesplazamiento(List<AutorizacionDesplazamientoDto> listAutorizacionDesplazamientoDto) throws RodalExceptionService;

	AutorizacionDesplazamiento getAutorizacionDesplazamiento(Long idAutDes);

	void updateAutorizacionDesplazamiento(AutorizacionDesplazamiento autDesUpdate);

	AutorizacionDesplazamiento getAutorizacionDesplazamientoByIdRodal(String idRodal);

	List<AutorizacionFlujoSiguiente> getSiguienteFlujoAutorizacionDesplazamiento(Long idPerfil, 
																		         Long idAut,
																		         String abrev);

	AutorizacionDesplazamientoHistorial createSiguienteEstadoFlujoHistorial(AutorizacionDesplazamientoHistorialDto autDesDto,
																		    Long idPerfil, 
																		    String abrev, 
																		    Long idUsuario);
	
    Integer getEmancipado(Long idMatricula);
	
	List<Integer> getTutoresMatricula(Long idMatricula);
	
	byte[] getAnexosXII(Long idTutor, Long idMatricula, Long idAutDes) throws FileNotFoundException, JRException, IOException;

	List<ElementoSelect> getCombosAutorizacionesDesplazamiento(String cbName, Integer anno, Long idPeriodo, Long idCentro, Long idTutor, Long idFamilia, Long idCurso, Long idPerfil, Long idUsuario) throws Exception;

	List<ListadoAutorizacionDesplazamiento> getListadoAutorizacionesDesplazamiento(Integer anno, Long idPeriodo,
			Long idCentro, Long idTutor, Long idFamilia, Long idCurso, Long idUnidad, Long idPerfil, Long idUsuario);

	List<ListadoAutorizacionesAnexos> getListadoAutorizacionesAnexos(Integer anno, 	
																	 String abrev,
																	 Long idPeriodo, 
																	 Long idCentro,
																	 Long idTutor, 
																	 Long idFamilia, 
																	 Long idCurso, 
																	 Long idUnidad,
																	 Long idUsuario,
																	 Long idPerfil,
																	 Long idEstado,
																	 Integer nuPeticion);

	List<HistoricoFlujoAutorizacion> getHistoricoFlujo(Long id, String abrev);

	void generarAnexosVII(Long idPerfil, 
						  Long idCentro, 
						  Long xUsuarioDelphos, 
						  Long idPeriodoGasto,
						  String abrev, 
						  Integer annoAnexo, 
						  Long idTutor, 
						  Long idFamilia, 
						  Long idCurso, 
						  Long idUnidad) throws Exception;

	AutorizacionesAnexosHistorial getAutorizacionAnexoHistorial(Long idEntidad, Integer per);

	void updateAutorizacionAnexoHistorial(AutorizacionesAnexosHistorial autDesUpdate);

	Alumno getDatosAlumno(Long idMatricula);

	Integer getNumeroDiasPeriodoAlumno(Long idMatricula);

	List<AutorizacionDesplazamiento> createAutorizacionDesplazamiento(
			List<AutorizacionDesplazamientoDto> listAutorizacionDesplazamientoDto, Long idPerfil, Long idUsuario);

	void updateFirmaAnexo(Long idHistorial, 
						  Long xUsuarioDelphos, 
						  Long idPerfil,
						  String abrev);

	AutorizacionesAnexos getDetalleAnexo(Long idAnexo);

	AutorizacionesAnexosHistorial createSiguienteEstadoFlujoHistorialAnexo(AutorizacionesAnexosHistorialDto historialAnexoDto, 
																		   Long idPerfil, 
																		   String abrev, 
																		   Long xUsuarioDelphos);

	void deleteAutorizacionDesplazamiento(Long idAutDes);

	List<String> getCountAnexosPendientesFirmaDirector(Integer cAnno, 
													   String abrev, 
													   Long idCentro,
													   Long idTutor,										
													   Long idCurso, 
													   Long idUnidad);

	List<ElementoSelect> getListadoCentrosAnexoXIFirmado(Integer anno,
														 String abrev,
														 Long idCentro,
														 Long idUsuario,
														 Long idPerfil);

	String getEstadoAnexoAutorizacion(Integer anno, Long idCentro, Long idTutor, Long idUnidad, Long idCurso, Long idTipo, Long idPeriodo, Integer nuPeticion);

}
