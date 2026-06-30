package es.jccm.edu.proyectosfct.application.ports.in.alumnoprograma;

import java.util.Date;
import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.ConvProgAluHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.CentroAlumnos;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.UnidadCurso;

public interface IAlumnoProgramaService {
	
	// Create
	
	List<AlumnoPrograma> createAlumnoPrograma(Long idConvProg, List<AlumnoPrograma> alumnosProgramaIn);
	
	// Read
	
	List<Alumno> getAlumnosPrograma(Long idCentro, Long idOfertamatrig, int cAnno, Long idConvProg);
	
	List<Alumno> getAlumnosSeleccionados(Long idCentro, Long idOfertamatrig, int cAnno, Long idConvProg);

	List<UnidadCurso> getUnidades(Long idCentro, Long idOfertamatrig, int cAnno);
	
	CentroAlumnos getNombreCentro(Long idCentro);

	List<AlumnoPrograma> getAlumnosConveniosProgramas(Long idConvenio);

	void deleteAlumnoPrograma(Long convProg);

	Integer countByconvenioProgramaId(Long idConvProg);

	AlumnoPrograma getAlumnoProgramaById(Long idConvProgAlu);

	AlumnoPrograma updateAlumnoConvenioPrograma(AlumnoPrograma alumnosProyectoIn);
	
	AlumnoPrograma getAlumnoProgramaByIdRodal(String idRodal);

	List<ConvProgAluHorPeriodoFctDto> createConvenioProgramaPeriodosHorariosAlumno(
			List<ConvProgAluHorPeriodoFctDto> listConvProgAluHorPeriodoFctDto);

    void updateAlumSeleccionados(List<AlumnoDto> alumSeleccionados);

	List<UnidadCurso> getUnidadesModalidad(Long idCentro, Long idModalidad, int cAnno, Long idTutor);

	Date obtenerFechaFinAltaSS(Long idConvProg, Long idMatricula);
}
