package es.jccm.edu.proyectosfct.application.ports.in.proyectos;

import java.io.IOException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoVinculacionEmpresasDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ProyectosDto;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosCabeceraAnexo2PlanProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import net.sf.jasperreports.engine.JRException;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConvProyAluHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConvProyHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.ConveniosProyectosHorarioAlumnoFctDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.modalidades.Modalidad;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;

public interface IProyectosService {
	
//	List<ListadoProyectos> getAllProyectos(Long idCentro, Integer cAnno);
	
	// Create

	Proyectos createProyecto(Proyectos proyectoIn);
	
	Proyectos getProyectoId(Long idProyecto);
	
	List<ConveniosProyecto> conveniosAproyecto(List<ConveniosProyecto> convprogFct, Long idProyecto);
	
	List<ModulosCurso> modulosAproyecto(List<ModulosCurso> modulos, Long idProyecto);
	
	List<ConveniosProyecto> getEmpresasProyecto(Long idProyecto);
	
	List<ConveniosProyecto> getEmpresasProyectoByAnno(Long idProyecto, Integer cAnno);

	List<CursoModalidad> getCursosModalidad(Long idModalidad, Integer cAnno, Integer idCentro, String idTipoMod);

	List<ModuloProyecto> getModulosProyecto(Long idProyecto);

	List<ModuloModalidad> getModulosModalidad(Long idOfertamatrig, Long idModalidad, Integer cAnno, Integer idCentro);

	List<Familia> getAllFamiliasCentro(Long idCentro, int cAnno, Long idTipo);
	
	List<Familia> getAllFamiliasCentroTutor(Long idCentro, int cAnno, Long idTipo, Long idTutor);

	ConveniosProyecto getConvenioProyecto(Long idConvProy);

	List<ConveniosProyectoAlumno> createAlumnoProyecto(Long idConvProy, List<ConveniosProyectoAlumno> alumnosProyectoIn);

	List<Alumno> getAlumnosSeleccionados(Long idCentro, int cAnno, Long idConvProy);

	Proyectos updateProyecto(Proyectos proyectoIn);

	ModulosCurso getModuloCurso(Long idModCurso);

	List<ModulosActividad> createModuloActividad(List<ModulosActividad> moduloActividad, Long idModuloCurso);

	ModulosActividad getModuloActividad(Long idModCurso, Long idDatProy);

	Boolean getIsCheckedModuloActividad(Long idModCurso, Long idDatProy);
	
	void deleteProyecto(Long idProyecto) throws RodalExceptionService;

	Integer countEmpresasProyecto(Long idProyecto);

	Integer countModulosProyecto(Long idProyecto);

	ConveniosProyectoAlumno getConvenioProyectoAlumno(Long idConvProyAlu);

	ConveniosProyectoAlumno updateAlumnoConvenioProyecto(ConveniosProyectoAlumno alumnosProyectoIn);
	
	ConveniosProyectoAlumno getConvenioProyectoAlumnoByIdRodal(String idConproRodal);

	byte[] exportReport(Long idConvProy,Long idMatricula) throws IOException, JRException;

	byte[] exportReportAnexoI(Long idConvProy) throws IOException, JRException;

	List<ListadoProyectos> getAllProyectos(Long idCentro, Integer cAnno, Long idTutorActual, Long idTipoProyecto, Long idTutor,
										   Long idFamilia, Long idModalidad, Long idCurso);

	List<TipoProyecto> getTiposListadoProyectos(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor,
												Long idFamilia, Long idModalidad);

	List<TutorFctDual> getTutoresListadoProyectos(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor,
			Long idFamilia, Long idModalidad);

	List<Familia> getFamiliaListadoProyectos(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor,
			Long idFamilia, Long idModalidad);

	List<Modalidad> getModalidadListadoProyectos(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor,
			Long idFamilia, Long idModalidad);

	List<ElementoSelect> getCentrosDelegacion(Long idUsuario, 
											  Long idPerfil, 
											  Long idCentro,
											  Long idProvincia);

	List<ElementoSelect> getTutoresDelegacion(Long idUsuario, 		
											  Long idPerfil, 
											  Long idCentro,
											  Long idCentroProvincia,
											  Integer idAnno,
											  Long idTipo,
											  Long idProvincia);

	List<ElementoSelect> getFamiliasProyectosDelegacion(Long idUsuario, 
														Long idPerfil, 
														Long idCentro,
														Long idCentroProvincia, 
														Integer idAnno,
														Long idTipo,
														Long idTutor,
														Long idProvincia);

	List<ElementoSelect> getModalidadesDelegacion(Long idUsuario, 
												  Long idPerfil, 
												  Long idCentro,
												  Long idCentroProvincia, 
												  Integer idAnno,
												  Long idTipo,
												  Long idTutor, 
												  Long idFamilia,
												  Long idProvincia);

	List<ListadoProyectos> getAllProyectosDelegacion(Long idUsuario, 
													 Long idPerfil, 
													 Long idCentro,
													 Long idCentroProvincia, 
													 Integer idAnno, 
													 Long idTipo, 
													 Long idTutor, 
													 Long idFamilia, 
													 Long idModalidad,
													 Long idProvincia,
													 Long idCurso);

	List<ConveniosProyecto> createConvenioProyectoHorarioAlumno(List<ConveniosProyectosHorarioAlumnoFctDto> proconvDto);

	void deleteConvenioProyectosHorarioAlumno(List<ConveniosProyectosHorarioAlumnoFctDto> listConveniosProyectosHorAluFctDto);

	List<ConvProyHorPeriodoFctDto> getConvenioProyectoPeriodosHorarios(Long idConvProy);

	List<ConvProyHorPeriodoFctDto> createConvenioProyectoPeriodosHorarios(List<ConvProyHorPeriodoFctDto> listConvProyHorPeriodoFctDto);


	List<ConvProyAluHorPeriodoFctDto> getConvenioProyectoPeriodosHorariosAlumno(Long idConvProy, Long idAlumno);

	List<ConvProyAluHorPeriodoFctDto> createConvenioProyectoPeriodosHorariosAlumno(
			List<ConvProyAluHorPeriodoFctDto> listConvProyAluHorPeriodoFctDto);

	Integer countByConvenioProyectoId(Long idConvProy);

	Integer getAnnoConvenioProyecto(Long idConvProy);

	void uploadFicherosAnexo(Long idConvenio, String tipo, List<MultipartFile> files) throws RodalExceptionService, InsertarDocFault, IOException;

	void updateFicherosAnexo(ConveniosProyecto convProy);

	List<ConveniosProyectoAnexos> getAnexoII(Long idConvProy);

	List<ParteSemanalAnexosProyecto> uploadFilesParteSemanal(Long idConvProyAlu, List<MultipartFile> files) throws RodalExceptionService, IOException, InsertarDocFault;

	List<ParteSemanalAnexosProyecto> getFilesParteSemanal(Long idConvProyAlu);

	List<Alumno> getAlumnosProyectos(Long idCentro, Long idOfertamatrig, int cAnno, Long idConvProy);

	void updateAlumSeleccionados(List<AlumnoDto> alumSeleccionados);

	Integer checkOverlappingDates(Long idConvenio, Long idProyecto, String fechaInicio, String fechaFin, Long idResponsable) throws ParseException;

    void updateProyectDates(Long idConvProy, String fechaInicio, String fechaFin, Integer newHora) throws ParseException;

	Integer getProyectoUsado(Long id);

	List<ElementoSelect> getCursosCentroAnnoTutorFamiliaModalidad(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad);

	List<ModulosCurso> createModuloPlan(List<ModulosCurso> modulos, Long idProyecto);

	List<AlumnoVinculacionEmpresasDto>  getAlumnosProyectosModalidad(Long idCentro, Long idModalidad, int cAnno, Long idConvProy);

	InfoAdicionalPlan updateInfoAdicionalPlan(InfoAdicionalPlan infoAdicionalPlan);

	InfoAdicionalPlan createInfoAdicionalPlan(InfoAdicionalPlan infoAdicionalPlan);

	InfoAdicionalPlan getInfoAdicionalPlanByIdProyecto(Long idProyecto);

	byte[] exportReportPlan(Long idConvProy, Long idMatricula) throws IOException, JRException;


	byte[] exportMecanismoCoordinacionSeguimientoHtmlToPdf(Long idMatricula);

	byte[] exportReportAnexoIPlan(Long idConvProy) throws IOException, JRException;

    byte[] exportParteSemanalReportPlan(Long idParsemAluPlan) throws JRException, IOException;

    Date obtenerFechaFinAltaSSDual(Long idConvProyAlu, Long idMatricula);

	ProyectosDto copiarPlan(Long idProyectoOriginal, ProyectosDto proyectoDto);
}
