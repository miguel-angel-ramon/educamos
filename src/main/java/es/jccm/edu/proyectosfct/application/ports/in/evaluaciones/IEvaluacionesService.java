package es.jccm.edu.proyectosfct.application.ports.in.evaluaciones;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model.AlumnoEvaluacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model.DatosGeneralesCabeceraEvaluacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model.CalificacionEvaluacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model.EvalProyAnexoDto;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.EvalProyAnexo;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.ListadoTablaAnexoIX;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.ModuloEvaluacion;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEvaluacionesService {

    List<AlumnoEvaluacionDto> getListadoAlumnosEvaluacionLOFP(Long idTutorfctdual, Long idCentro, Integer cAnno);

    List<ModuloEvaluacion> getListadoModulosEvaluacionLOFP(Long xMatricula, Long xEmpresa);

    List<DatosGeneralesCabeceraEvaluacionDto> getListadoCabecerasEvaluacion(Long xMatricula);

    Long guardarEvaluacionLOFP(Long idEvaluacion, Long xEmpresa, Long idResultado, Long idCalificacion, Long idModuloMatricula, String motivacion);

    List<CalificacionEvaluacionDto> getListadoCalificacionesEvaluacionLOFP();

    EvalProyAnexoDto procesarEvalProyAnexo(EvalProyAnexoDto anexoDto, MultipartFile file, Integer cAnno, Long xCentro, DatosUsuarioJwt datosUsuario) throws Exception;

    EvalProyAnexo getEvalProyAnexoById(Long id);

    EvalProyAnexoDto getEvalProyAnexoByMatriculaAndEmpresa(Long xMatricula, Long xEmpresa);

    EvalProyAnexo updateEvalProyAnexo(EvalProyAnexo evalProyAnexo);

    Integer actualizarEstadoAnexo(Long xMatricula, Long xEmpresa, Integer lgActualizado);

    byte[] downloadAnexoIXEval(Long xEmpresa, Long idMatricula);

    List<ListadoTablaAnexoIX> getTableHeaderAnexoIXEvaluacion(Long xEmpresa, Long idMatricula);

    boolean borrarDocumento(String idEvaFirRodal);

	Integer updateHoras(Long xEmpresa, Long idMatricula, Integer horas);
}
