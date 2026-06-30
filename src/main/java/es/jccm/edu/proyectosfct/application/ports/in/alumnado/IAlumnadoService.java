package es.jccm.edu.proyectosfct.application.ports.in.alumnado;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.json.ParseException;

import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AlumnadoAltasBajas;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AlumnadoAltas;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AlumnadoNSS;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.AptoEvaluacionAlumno;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ComunicacionDiasPracticas;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.EvaAluActProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.EvaAluActProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.FechaSemana;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ListadoAlumnadoTutor;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemActProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemActProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemAluProy;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.UnidadCurso;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.CursoModalidad;
import net.sf.jasperreports.engine.JRException;

public interface IAlumnadoService {

	ParsemAluProg createParsemAluProg(ParsemAluProg parsemAluProgIn, String fechaIni, Integer nuDias);

	List<ParsemActProg> createParsemActProg(List<ParsemActProg> parsemActProgIn, Long idParsemAluProg);

	Alumno getAlumno(Long idMatricula);
	
	Double getHoras(Long idConvProg, Long idMatricula);
	
	Double getHorasProyecto(Long idConvProy, Long idMatricula);

	List<FechaSemana> getFechasSemana(Long idParsemAluProg);

	List<ParsemActProg> getAllParsemActProg(Long idParsemAluProg);

	ParsemAluProg getParsemAluProgByIdParsemAluProg(Long idParsemAluProg);

	ParsemAluProg getParsemAluProgByIdConvProgAlu(Long idConvProgAlu, String fechaIni);

	List<ListadoAlumnadoTutor> getListadoAlumnosTutor(Long idTutorfctdual, 
													  Long idCentro, 
													  Integer cAnno, 
													  Integer tipoEmpresa, 
													  Long idEmpresa, 
													  Long idOfertamatrig, 
													  Long idUnidad,
													  Long idPerfil,
													  Long idCentroCombo,
													  Long idProvincia,
													  Long idUsuario, 
													  Long idEmpleadoComunica) throws ParseException;

	List<UnidadCurso> getUnidadesEmpleadoCentro(Long idTutorfctdual, 
												Long idCentro, 
												Integer cAnno,
												Integer tipoEmpresa, 
												Long idEmpresa, 
												Long idOfertamatrig,
												Long idPerfil,
												Long idCentroCombo,
												Long idProvincia,
												Long idUsuario, 
												Long idEmpleadoComunica
												) throws ParseException;

	List<CursoModalidad> getCursosEmpleadoCentro(Long idTutorfctdual, 
												 Long idCentro, Integer cAnno,
												 Integer tipoEmpresa, 
												 Long idEmpresa,
												 Long idPerfil,
												 Long idCentroCombo,
												 Long idProvincia,
												 Integer sTodos,
												 Long idUsuario, 
												 Long idEmpleadoComunica
												 ) throws ParseException;
	
	List<ElementoSelect> getTutoresAlumnado(Long idTutorfctdual, 
											Long idCentro, Integer cAnno,
											Integer tipoEmpresa, 
											Long idEmpresa,
											Long idPerfil,
											Long idCentroCombo,
											Long idProvincia,
											Integer sTodos,
											Long idUsuario,
											Long idEmpleadoComunica) throws ParseException;

	ParsemAluProy createParsemAluProy(ParsemAluProy parsemAluProyIn, String fechaIni, Integer nuDias);

	List<ParsemActProy> createParsemActProy(List<ParsemActProy> parsemActProyIn, Long idParsemAluProy);

	ParsemAluProy getParsemAluProyByIdConvProyAlu(Long idConvProyAlu, String fechaIni);

	List<ParsemActProy> getAllParsemActProy(Long idParsemAluProy);

	ParsemAluProy getParsemAluProyByIdParsemAluProy(Long idParsemAluProy);

	List<FechaSemana> getFechasSemanaProy(Long idConvProyAlu);

	List<EvaAluActProg> getAllEvaAluActProg(Long idConvProgAlu);

	List<EvaAluActProy> getAllEvaAluActProy(Long idConvProyAlu);

	List<EvaAluActProg> createEvaAluActProg(List<EvaAluActProg> evaAluActProgListIn, Long idConvProgAlu);

	List<EvaAluActProy> createEvaAluActProy(List<EvaAluActProy> evaAluActProyListIn, Long idConvProyAlu);

	List<AptoEvaluacionAlumno> getAptoEvaluacionAlumno();

	byte[] exportReport(Long id,String tipo) throws FileNotFoundException, JRException, IOException;

	byte[] exportParteSemanalReport(Long id, String tipo, Long idParsem) throws FileNotFoundException, JRException, IOException;

	Boolean firmarEvaluacionAlumno(Long idEntidad, String entidad);

	List<AlumnadoNSS> getAlumnadoNSS(Integer cAnno);

	List<AlumnadoAltasBajas> getAlumnadoAltasBajas(Integer cAnno);

	List<AlumnadoAltas> getAlumnadoAltas(Integer cAnno, Long idCentro);

	List<ComunicacionDiasPracticas> getComunicacionDiasPracticas(Integer cAnno, Long idCentro, Integer nMes);

	Integer getNumeroProgProy(Integer cAnno, Long xCentro, Long idEmpleadoComunica);

	void deleteAlumnoParteSemanal(Long idConvProAlu, String tipo);

	void updateNuHorasEval(Long idAluCon, String tipo, Integer nuHorasEva);
}
