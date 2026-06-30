package es.jccm.edu.proyectosfct.application.ports.in.gastos;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.TicketAlumnadoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.TicketTutorDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;

import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAlumnadoHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAnexo;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAnexoHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoEstadoFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoTutor;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoTutorHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.HistoricoFlujoGastos;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ImpuestoTipoServicio;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoGastoAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoGastoAnexo;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.ListadoGastoTutores;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.PeriodoGasto;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketTutor;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import net.sf.jasperreports.engine.JRException;

public interface IGastosService {

	GastoAnexo createGastoAnexo(Long idUsuario, GastoAnexo gastoAnexoIn) throws Exception;

	GastoTutor createGastoTutor(Long idUsuario, Long idPerfil, GastoTutor gastoTutorIn) throws Exception;

	GastoAlumnado createGastoAlumnado(Long idUsuario, Long idPerfil, GastoAlumnado gastoAlumnoIn) throws Exception;

	List<GastoEstadoFlujoSiguiente> getSiguienteGastoFlujo(Long idPerfil, Long idGasto, String abrevTipoGasto);

	List<ListadoGastoTutores> getListadoGastoTutores(Integer annoPeriodo, 
													 Long idCentro, 
													 Long idTutor,
													 Long idPeriodoGasto,
													 Long idPerfil,
													 Long idEstado);

	List<ListadoGastoAlumnado> getListadoGastoAlumnado(Integer annoPeriodo, 
													   Long idCentro, 
													   Long idTutor,
													   Long idPeriodoGasto,
													   Long idPerfil, 
													   Long idCurso,
													   Long idUnidad,										   
													   Long idUsuarioComunica);

	List<PeriodoGasto> getPeriodosGasto(Integer annoPeriodo, 
										Long idCentro, 
										Long idPerfil, 
										Long idTutor,
										Long idUsuarioComunica);

	List<ListadoGastoAnexo> getListadoGastoAnexo(Integer annoPeriodo,
												 Long idCentro, 
												 String abrevTipoGasto,
												 Long idPeriodoGasto,
												 Long idTutor,
												 Long idCurso,
												 Long idUnidad,
												 Long idPerfil,
												 Long idProvinciaDelegacion,
												 Long idUsuario);
	
	List<ListadoGastoTutores> getComboTutores(Integer annoPeriodo, 
											  Long idCentro,
											  Long idUsuario);

	GastoTutor getGastoTutor(Long idGastoTutor);
	
	GastoAlumnado getGastoAlumnado(Long idGastoAlumnado);

	PeriodoGasto getPeriodoGastoById(Long idGastoPeriodo);

	List<HistoricoFlujoGastos> getHistoricoFlujoGastos(Long id, String abrevTipoGasto);

	GastoAnexoHistorial getAnexoHistoriaById(Long idEntidad);

	void updateAnexoHistorial(GastoAnexoHistorial anexohistorial);

	GastoTutorHistorial createSiguienteEstadoFlujoTutor(Long idUsuario, 
														GastoTutorHistorial gastoTutorHistorialIn, 
														Long idPerfil,
														String abrevTipoGasto)
			throws Exception;
	
	GastoAlumnadoHistorial createSiguienteEstadoFlujoAlumnado(Long idUsuario, 
														      GastoAlumnadoHistorial gastoAlumnadoHistorialIn, 
														      Long idPerfil,
														      String abrevTipoGasto,
														      Long idTutor,
														      Long idCurso,
														      Long idUnidad)
			throws Exception;

	void updateFirmaAnexo(Long idHistorial, 
						  Long xUsuario, 
						  Integer posFirma,
						  Long idPerfil);

	List<ElementoSelect> getComboCursosGastos(Integer annoPeriodo, 
											  Long idCentro, 
											  String abrevTipoGasto,
											  Long idPeriodoGasto, 
											  Long idTutor,
											  Long idUsuarioComunica,
											  Long idPerfil);

	List<ElementoSelect> getComboUnidadGastos(Integer annoPeriodo, 
											  Long idCentro, 
											  String abrevTipoGasto,
											  Long idPeriodoGasto, 
											  Long idTutor,
											  Long idCurso,
											  Long idUsuarioComunica,
											  Long idPerfil);

	List<ElementoSelect> getComboTutoresGastosAlumno(Integer annoPeriodo, 
			                                         Long idCentro, 
			                                         String abrevTipoGasto,
			                                         Long idPeriodoGasto,
			                                         Long idUsuarioComunica,
			                                         Long idPerfil);

//	byte[] exportReportAnexoGastoTipo(Integer cAnno, Long idCentro, Long idPeriodo, Long idTipo, Long idTutor,
//			Long idFamilia, Long idCurso, Long idModalidad, Long idProvincia) throws IOException, JRException;

	void generarAnexoVIII(Long idPerfil, Long idCentro, Long xUsuarioDelphos, Long idPeriodoGasto,
			String abrevTipoGasto, Integer cAnno) throws Exception;
	
	void generarAnexosVI(Long idPerfil, 
						 Long idCentro, 
						 Long xUsuarioDelphos, 
						 Long idPeriodoGasto,
						 String abrevTipoGasto, 
						 Integer cAnno,
						 Long idTutor,
						 Long idCurso,
						 Long idUnidad) throws Exception;

	byte[] exportReportAnexoGastoTipo(Integer cAnno, Long idCentro, Long idPeriodo, Long idTipo, Long idTutor,
			Long idCurso, Long idUnidad, Long idProvincia, Long idUsuario, Long idPerfil)
			throws IOException, JRException;

	
	
	ImpuestoTipoServicio getImpuestoTipoServicio(Integer cAnno,String tipServicio );
	


	List<TicketAlumnado> getTicketsAlumno(Long idGastoAlumnado);

	List<TicketTutor> getTicketsTutor(Long idGastoTutor);

	List<TicketTutor> createTicketTutor(Long idGasto, 
									    Long cAnno,
									    Long idCentro,
										List<MultipartFile> files, 
										Long idUsuario) throws RodalExceptionService, InsertarDocFault, IOException;

	void updateTicketTutor(TicketTutor ticketUpdate);

	TicketAlumnado getTicketAlumno(Long idTicketTutor);

	TicketTutor getTicketTutor(Long idTicketAlumno);

	void updateTicketAlumno(TicketAlumnado ticketUpdate);

	void deleteTicketTutor(List<TicketTutorDto> listTicketsTutorDto) throws RodalExceptionService;

	void deleteTicketAlumno(List<TicketAlumnadoDto> listTicketsAlumnoDto) throws RodalExceptionService;

	List<TicketAlumnado> createTicketAlumno(Long idGasto,
											Long cAnno,
										    Long idCentro,
											List<MultipartFile> files, 
											Long xUsuarioDelphos) throws RodalExceptionService, InsertarDocFault, IOException;

	TutorFctDual getNombreTutorById(Long idTutorFct);

	Alumno getDatosAlumnoGastoNuevo(Integer annoPeriodo, Long idCentro, Long idUsuarioComunica);

	List<ElementoSelect> getEstadosGastosDirector();

	GastoAnexo getGastoAnexoById(Long idAnexo);

	GastoAnexoHistorial createSiguienteEstadoFlujoAnexo(GastoAnexoHistorial gastoHistorialIn, Long xUsuarioDelphos) throws Exception;

	void deleteGastoTutor(Long idGastoTutor) throws RodalExceptionService;

	void deleteGastoAlumno(Long idGastoAlumno);

	Integer getNoModificableAnexoVI(Long idCentro, Long idPeriodo, Long idTutor);

	Integer getNoModificableAnexoVIbis(Long idCentro, Long idPeriodo);

	List<ElementoSelect> getComboGastosAlumnos(Integer anno, Long idCentro, Long idTutor, Long idCurso, Long idUnidad , Long idUsuario, Long idPeriodo);

	String getEstadoAnexoGasto(Long idCentro, Long idPeriodoGasto, Long idTipoGasto, Long idTutorFct, Long idCurso, Long idUnidad);
}
