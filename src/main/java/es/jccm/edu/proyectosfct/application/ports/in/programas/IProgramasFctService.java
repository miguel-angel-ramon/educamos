package es.jccm.edu.proyectosfct.application.ports.in.programas;

import java.io.IOException;

import java.util.List;
import org.springframework.data.domain.Page;

import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ListadoProgramaFct;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ProgramaFct;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Empleado;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.OfertaMatriculaGenerico;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import net.sf.jasperreports.engine.JRException;

public interface IProgramasFctService {

	Empleado getDatosTutor(Long idCentro, Long idEmpleado);
	
	List<Familia> getAllFamiliasCentro(Long idCentro, int cAnno);
	
	List<Familia> getAllFamiliasCentroTutor(int cAnno,
											Long idTutor,
											Long idCentro);	
	
	List<Familia> getAllFamiliasDelegacion(Long idUsuario, Long idPerfil, Long idCentroProvincia, int cAnno);
	
	List<OfertaMatriculaGenerico> getAllCursosFamiliaCentro(Long idCentro, int cAnno, Long idFamilia);
	
	List<Empleado> getTutoresCentro(Long idCentro);
	
	List<ListadoProgramaFct> getAllProgramasCentroTutor(Long idCentro, Long idTutor, Long idFamilia, Long idOferta, Long idConvenio, Long idAnno);	
	
	List<ProgramaFct> getAllProgramasDtoCentroTutor(Long idCentro, Integer cAnno, Long idTutor);
	
	Page<ProgramaFct> getAllProgramasCentroTutorName(Long idCentro, String sName, int page, Long idTutor);
	
	OfertaMatriculaGenerico getByOfertaMatriculaGenericoId(Long idOfertaMatriculaGenerico);
	
	Familia getByFamiliaId(Long idFamilia);	
	
	ProgramaFct getProgramaId(Long idPrograma);
	
	ProgramaFct createPrograma(ProgramaFct programaFctDto);
	
	ProgramaFct updatePrograma(ProgramaFct programaFctDto);
	
	void deletePrograma(Long idPrograma);
	
	List<ProgramaFct> getProgramasTutor(Long idTutor);

	byte[] exportReport(Long idConvProg,Long idMatricula) throws IOException, JRException;

	byte[] exportReportAnexoI(Long idConvProg) throws IOException, JRException;

	List<Familia> getAllFamiliasListadoProgramas(Long idCentro, Long idTutor);

	List<OfertaMatriculaGenerico> getAllOfertaGenericaListadoProgramas(Long idCentro, Long idTutor, Long idFamilia);

	List<TutorFctDual> getAllTutoresListadoProgramas(Long idCentro);

	List<ElementoSelect> getCentrosDelegacion(Long idUsuario, 
											  Long idPerfil, 
											  Long idCentro,
											  Long idProvincia);

	List<ElementoSelect> getTutoresDelegacion(Long idUsuario, 
											  Long idPerfil, 
											  Long idCentro, 
											  Long idCentroProvincia,
											  Long idProvincia);

	List<ElementoSelect> getFamiliasDelegacion(Long idUsuario, 
											   Long idPerfil, 
											   Long idCentro,
											   Long idCentroProvincia, 
											   Long idTutor,
											   Long idProvincia);

	List<ElementoSelect> getCursosDelegacion(Long idUsuario, 
											 Long idPerfil, 
											 Long idCentro, 
											 Long idCentroProvincia,
											 Long idTutor,
											 Long idFamilia,
											 Long idProvincia);

	List<ListadoProgramaFct> getAllProgramasDelegacion(Long xUsuarioDelphos, 
													   Long idPerfil, 
													   Long idCentro,
													   Long idCentroProvincia, 
													   Long idTutor, 
													   Long idFamilia, 
													   Long idOferta,
													   Long idProvincia,
													   Long idConvenio,
													   Integer cAnno);
	
	List<OfertaMatriculaGenerico> getAllCursosFamiliaTutor(Long idTutor, int cAnno, Long idFamilia, Long idCentro);

	List<ElementoSelect> getAllProgramasConvenios(Long idCentro, Long idTutor, Long idFamilia, Long idOferta);

	List<ElementoSelect> getConveniosDelegacion(Long xUsuarioDelphos, 
											    Long idPerfil, 
											    Long idCentro,
											    Long idCentroProvincia, 
											    Long idTutor, 
											    Long idFamilia, 
											    Long idProvincia, 
											    Long idCurso);

	Integer getProgramaUsado(Long id);

}
