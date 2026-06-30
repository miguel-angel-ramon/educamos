package es.jccm.edu.proyectosfct.application.ports.in.alumnadoLOFP;

import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.ListadoAlumnadoTutorDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model.AlumnadoTutorLofpDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model.DatosTutorYResponsableDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model.EstadoValidacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ListadoAlumnadoTutor;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.MesLOFP;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.PeriodoLOFP;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.UnidadCurso;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModuloModalidad;
import org.apache.tomcat.util.json.ParseException;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.CursoModalidad;

public interface IAlumnadoLOFPService {

	List<CursoModalidad> getCursosEmpleadoCentro(Long idTutorfctdual,
												 Long idCentro,
												 Integer cAnno,
												 Long idPerfil,
												 Long idCentroCombo,
												 Long idProvincia,
												 Long idUsuario,
												 Long idEmpleadoComunica) throws ParseException;

	List<UnidadCurso> getUnidadesLOFP(Long idTutorfctdual,
												Long idCentro,
												Integer cAnno,
												Long idOfertamatrig,
												Long idPerfil,
												Long idCentroCombo,
												Long idProvincia,
												Long idUsuario,
												Long idEmpleadoComunica
	) throws ParseException;

	List<AlumnadoTutorLofpDto> getListadoAlumnosLOPD(Long idTutorfctdual,
													 Long idCentro,
													 Integer cAnno,
													 Long idOfertamatrig,
													 Long idUnidad,
													 Long idPerfil,
													 Long idCentroCombo,
													 Long idProvincia,
													 Long xUsuarioDelphos,
													 Long idEmpleadoComunica,
													 Integer tienePlan,
													 Integer idEstado);

	List<ElementoSelect> getTutoresAlumnadoLOFP(Long idTutorfctdual, 
			                                    Long idCentro, 
			                                    Integer cAnno, 
			                                    Long idPerfil,
			                                    Long idCentroCombo, 
			                                    Long idProvincia, 
			                                    Long xUsuarioDelphos, 
			                                    Long idEmpleadoComunica);

	List<ElementoSelect> getCentrosDelegacionLOFP(Long xUsuarioDelphos, 
			                                      Long idPerfil, 
			                                      Long idCentro, 
			                                      Long idProvincia,
			                                      Integer cAnno);

	List<ElementoSelect> getFamiliasCentroTutorLOFP(Long idCentro,
													Integer cAnno,
													Long idTutor);

	List<ElementoSelect> getModCentroTutorFamiliaLOFP(Long idCentro,
													  Integer cAnno,
													  Long idTutor,
													  Long idFamilia);

	List<ElementoSelect> getCursosCentroAnnoTutorFamiliaModalidadLOFP(Long idCentro,
																	  Integer cAnno,
																	  Long idTutor,
																	  Long idFamilia,
																	  Long idModalidad);


	List<CursoModalidad> getCursosModalidadLOFP(Long idCurso, Integer idCentro, Integer cAnno);

	List<ModuloModalidad> getModulosModalidadLOFP(Long idCurso,
												  Integer idCentro,
												  Integer cAnno);

	List<PeriodoLOFP> getDatosSeguimientoLOFP(Long xMatricula, Long idEmpresa, Long idProyecto );

	void postValidarPlanMasivo(List<Long> matriculas, String cPerfil, Long usuarioActual, Long usuarioActualComunica);

	DatosTutorYResponsableDto getTutorYResponsable(Long idConvProyAlu);

	List<EstadoValidacionDto> getValidacionesPorMatricula(Long matricula);
}