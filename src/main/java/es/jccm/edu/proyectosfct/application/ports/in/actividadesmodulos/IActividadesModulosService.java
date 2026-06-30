package es.jccm.edu.proyectosfct.application.ports.in.actividadesmodulos;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model.EsActividadRepetidaDTO;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model.InfoPardiaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model.ListadoResultadosAsociadosPlanRelacionadosDto;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ActividadesModulos;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ListadoActividadesModulos;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IActividadesModulosService {


    ActividadesModulos crearActividad(Long idModulo, String nombreActividad, Long cUsuCreacion, List<ListadoResultadosAsociadosPlanRelacionadosDto> resultadosAsociadosDtoList, String txAbrev, Integer nuOrden);

    List<ListadoActividadesModulos> getListadoActividadesPlan(Long idModulo);

    ActividadesModulos actualizarActividad(Long idActividad, String nombreActividad, Long xUsuarioDelphos, List<ListadoResultadosAsociadosPlanRelacionadosDto> resultadosAsociadosDtoList,String txAbrev, Integer nuOrden);

    Optional<ActividadesModulos> buscarPorId(Long idActividad);

    boolean borrarActividad(Long idActividad);

    boolean esNombreActividadRepetido(Long idModuloCurso, String nombreActividad, Long idActividad);

    boolean esTxAbrevActividadRepetido(Long idModuloCurso, String txAbrev, Long idActividad);

    boolean esNuOrdenActividadRepetido(Long idModuloCurso, Integer nuOrden, Long idActividad);

    ActividadesModulos obtenerNumeroMaximoActividad(Long idModuloCurso);

    Integer getOrdenActividad(Long idModuloCurso);

    EsActividadRepetidaDTO contarActividadesRepetidas(Long idModuloCurso, String nombreActividad, Long idActividad, String txAbrev, Integer nuOrden);

    List<ListadoActividadesModulos> getListadoActividadesPlanByConvProyAlu(Long idConvProyAlu, String fechaDia);

    void guardarActividadSeguimientoPlan(Long idConvProyAlu, String fechaAct, String fechaInicio, String observaciones, Integer horas, List<Long> actividades);

    @Transactional
    void borrarActividadSeguimientoPlan(Long idConvProyAlu, String fechaActString, String fechaInicioSemString);

    InfoPardiaDto getHorasAndObservacionesPardia(Long idConvProyAlu, String fechaDia);
}
