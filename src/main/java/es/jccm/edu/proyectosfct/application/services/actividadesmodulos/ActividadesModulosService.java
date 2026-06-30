package es.jccm.edu.proyectosfct.application.services.actividadesmodulos;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model.EsActividadRepetidaDTO;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model.InfoPardiaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model.ListadoResultadosAsociadosPlanRelacionadosDto;

import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.ActividadesModulosRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.ResultadosAprendizajeActividadesRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.PardiaAluplanActmodRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.PardiaAluplanRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.ParsemAluplanRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.ConvProyAlumnoRepository;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ActividadesModulos;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ListadoActividadesModulos;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ResultadosAprendizajeActividades;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.projection.ListadoActividadesModulosProjection;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplan;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplanActmod;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.ParsemAluplan;
import es.jccm.edu.proyectosfct.application.ports.in.actividadesmodulos.IActividadesModulosService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActividadesModulosService implements IActividadesModulosService {

    @Autowired
    private ActividadesModulosRepository actividadesModulosRepository;

    @Autowired
    private ResultadosAprendizajeActividadesRepository resultadosAprendizajeActividadesRepository;

    @Autowired
    private ParsemAluplanRepository parsemAluplanRepository;

    @Autowired
    private ConvProyAlumnoRepository convProyAlumnoRepository;

    @Autowired
    private PardiaAluplanRepository pardiaAluplanRepository;

    @Autowired
    private PardiaAluplanActmodRepository pardiaAluplanActmodRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ActividadesModulos crearActividad(Long idModuloCurso, String nombreActividad, Long usuarioCreacion, List<ListadoResultadosAsociadosPlanRelacionadosDto> resultadosAsociadosDtoList, String txAbrev, Integer nuOrden) {

        ActividadesModulos nuevaActividad = new ActividadesModulos();
        nuevaActividad.setIdModuloCurso(idModuloCurso);
        nuevaActividad.setTxNombre(nombreActividad);
        nuevaActividad.setCUsuCreacion(usuarioCreacion);
        nuevaActividad.setFCreacion(new Date());
        nuevaActividad.setTxAbrev(txAbrev);
        nuevaActividad.setNuOrden(nuOrden);

        ActividadesModulos actividadGuardada = actividadesModulosRepository.save(nuevaActividad);

        resultadosAsociadosDtoList.stream()
                .filter(dto -> dto.getLgres() != null && dto.getLgres() == 1)
                .forEach(dto -> {
                    ResultadosAprendizajeActividades resultadoActividad = new ResultadosAprendizajeActividades();
                    resultadoActividad.setIdActividadModulo(actividadGuardada.getIdActividadModulo());
                    resultadoActividad.setIdResultadoaModulo(dto.getId_resultadoa_modulo());
                    resultadoActividad.setCUsuCreacion(usuarioCreacion);
                    resultadoActividad.setFCreacion(new Date());

                    resultadosAprendizajeActividadesRepository.save(resultadoActividad);
                });



        return actividadGuardada;
    }

    @Override
    @Transactional
    public ActividadesModulos actualizarActividad(Long idActividad, String nombreActividad, Long usuarioActualizacion, List<ListadoResultadosAsociadosPlanRelacionadosDto> resultadosAsociadosDtoList, String txAbrev, Integer nuOrden) {

        ActividadesModulos actividadExistente = actividadesModulosRepository.findById(idActividad)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con el ID: " + idActividad));

        actividadExistente.setTxNombre(nombreActividad);
        actividadExistente.setCUsuActualiza(usuarioActualizacion);
        actividadExistente.setFActualiza(new Date());
        actividadExistente.setTxAbrev(txAbrev);
        actividadExistente.setNuOrden(nuOrden);

        ActividadesModulos actividadActualizada = actividadesModulosRepository.save(actividadExistente);

        resultadosAprendizajeActividadesRepository.deleteByIdActividadModulo(idActividad);


        resultadosAsociadosDtoList.stream()
                .filter(dto -> dto.getLgres() != null && dto.getLgres() == 1)
                .forEach(dto -> {
                    ResultadosAprendizajeActividades nuevoResultado = new ResultadosAprendizajeActividades();
                    nuevoResultado.setIdActividadModulo(idActividad);
                    nuevoResultado.setIdResultadoaModulo(dto.getId_resultadoa_modulo());
                    nuevoResultado.setCUsuCreacion(usuarioActualizacion);
                    nuevoResultado.setFCreacion(new Date());
                    resultadosAprendizajeActividadesRepository.save(nuevoResultado);
                });

        return actividadActualizada;
    }


    @Override
    public Optional<ActividadesModulos> buscarPorId(Long idActividad) {
        return actividadesModulosRepository.findById(idActividad);
    }

    @Override
    @Transactional
    public boolean borrarActividad(Long idActividad) {
        
        Optional<ActividadesModulos> actividad = actividadesModulosRepository.findById(idActividad);

        if (actividad.isPresent()) {
            resultadosAprendizajeActividadesRepository.deleteByIdActividadModulo(idActividad);
            actividadesModulosRepository.deleteById(idActividad);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ListadoActividadesModulos> getListadoActividadesPlan(Long idModulo) {
        List<ListadoActividadesModulosProjection> actividades = actividadesModulosRepository.findActividadesPlanByModulo(idModulo);

        return actividades.stream().map(entity -> modelMapper.map(entity, ListadoActividadesModulos.class)).collect(Collectors.toList());
    }

    @Override
    public boolean esNombreActividadRepetido(Long idModuloCurso, String nombreActividad, Long idActividad) {
        return actividadesModulosRepository.existsByTxNombreAndIdModuloCursoAndIdActividadModuloNot(nombreActividad, idModuloCurso, idActividad);
    }

    @Override
    public boolean esTxAbrevActividadRepetido(Long idModuloCurso, String txAbrev, Long idActividad) {
        return actividadesModulosRepository.existsByTxAbrevAndIdModuloCursoAndIdActividadModuloNot(txAbrev, idModuloCurso, idActividad);
    }

    @Override
    public boolean esNuOrdenActividadRepetido(Long idModuloCurso, Integer nuOrden, Long idActividad) {
        return actividadesModulosRepository.existsByNuOrdenAndIdModuloCursoAndIdActividadModuloNot(nuOrden, idModuloCurso, idActividad);
    }

    @Override
    public Integer getOrdenActividad(Long idModuloCurso) {
        Integer numeroActividad = null;

        ActividadesModulos actividadModulo = new ActividadesModulos();
        actividadModulo = obtenerNumeroMaximoActividad(idModuloCurso);

        if (actividadModulo != null) {
            if (actividadModulo.getNuOrden() != null) {
                numeroActividad = actividadModulo.getNuOrden() + 1;
            }
        } else {
            numeroActividad = 1;
        }

        return numeroActividad;
    }

    @Override
    public ActividadesModulos obtenerNumeroMaximoActividad(Long idModuloCurso) {
        return actividadesModulosRepository.findFirstByIdModuloCursoOrderByNuOrdenDesc(idModuloCurso);
    }

    @Override
    public EsActividadRepetidaDTO contarActividadesRepetidas(Long idModuloCurso, String nombreActividad, Long idActividad, String txAbrev, Integer nuOrden) {

        EsActividadRepetidaDTO esActividadRepetidaDTO = new EsActividadRepetidaDTO();
        esActividadRepetidaDTO.setEsNombre(esNombreActividadRepetido(idModuloCurso,nombreActividad, idActividad)?"si":"no");
        esActividadRepetidaDTO.setEsNuOrden(esNuOrdenActividadRepetido(idModuloCurso,nuOrden,idActividad)?"si":"no");
        esActividadRepetidaDTO.setEsTxAbrev(esTxAbrevActividadRepetido(idModuloCurso,txAbrev,idActividad)?"si":"no");

        return esActividadRepetidaDTO;
    }

    @Override
    public List<ListadoActividadesModulos> getListadoActividadesPlanByConvProyAlu(Long idConvProyAlu, String fechaDia) {
        List<ListadoActividadesModulosProjection> actividades = actividadesModulosRepository.findActividadesPlanByIdConvProyAluAndDia(idConvProyAlu, fechaDia);

        return actividades.stream().map(entity -> modelMapper.map(entity, ListadoActividadesModulos.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void guardarActividadSeguimientoPlan(Long idConvProyAlu, String fechaActString, String fechaInicioSemString, String observaciones, Integer horas, List<Long> actividades) {

        ParsemAluplan partePlan;
        try {
            SimpleDateFormat formatoSemana = new SimpleDateFormat("dd/MM/yyyy");

            Date fechaAct = formatoSemana.parse(fechaActString.replace("_", "/"));
            Date fechaInicioSem = formatoSemana.parse(fechaInicioSemString.replace("_", "/"));

            boolean hayCambios = false;

            //Flag que indica si el parte es nuevo o no, se usa en la validación para saber si marcar como actualizado o no el parte
            boolean esParteSemanalNuevo = false;

            //Recuperar el parte semanal, en caso de que no exista lo creamos
            partePlan = parsemAluplanRepository.findByIdConvProyAluAndFechaInicioSem(idConvProyAlu, fechaInicioSem);
            if (partePlan == null) {
                ParsemAluplan parteCrear = new ParsemAluplan();
                parteCrear.setFInisem(fechaInicioSem);
                parteCrear.setIdConvproyAlu(idConvProyAlu);
                parteCrear.setLgActualizado(1);
                esParteSemanalNuevo = true;
                partePlan = parsemAluplanRepository.save(parteCrear);
            }

            //Recuperar el parte diario, en caso de que no exista lo creamos
            PardiaAluplan parteDiario = pardiaAluplanRepository.findByIdConvProyAluAndDia(idConvProyAlu, fechaActString);
            if (parteDiario == null) {
                //Crear parte diario
                PardiaAluplan parteDiarioCrear = new PardiaAluplan();
                parteDiarioCrear.setIdConvProyAlu(idConvProyAlu);
                parteDiarioCrear.setFDia(fechaAct);
                parteDiarioCrear.setParsemAluplan(partePlan);

                parteDiario = guardarDatosParte(parteDiarioCrear, horas, observaciones);

            } else {
                //Actualizamos las horas y las observaciones en caso de que hayan cambiado.
                if(!Objects.equals(parteDiario.getNuHoras(), horas) || !Objects.equals(parteDiario.getTxObservaciones(), observaciones)){
                  hayCambios = true;
                  guardarDatosParte(parteDiario, horas, observaciones);
                }
            }

            List<PardiaAluplanActmod> actividadesRegistradas = pardiaAluplanActmodRepository.findPartesByIdConvProyAluAndDia(idConvProyAlu, fechaAct);

            // Extraer los id de las actividades ya existentes
            List<Long> actividadesRegistradasIds = actividadesRegistradas.stream()
                    .map(actividad -> actividad.getActividadModulo().getIdActividadModulo())
                    .collect(Collectors.toList());

            hayCambios = delAccActividades(actividades, hayCambios, parteDiario, actividadesRegistradas,
					actividadesRegistradasIds);

            //Actualizar el parte semanal para marcar que no está actualizado en caso de que sea un parte nuevo haya cambios
            if(!esParteSemanalNuevo && hayCambios) {
                partePlan.setLgActualizado(0);
                parsemAluplanRepository.save(partePlan);
            }

        } catch (ParseException e) {
        	throw new IllegalStateException();
        }
    }

	private boolean delAccActividades(List<Long> actividades, boolean hayCambios, PardiaAluplan parteDiario,
			List<PardiaAluplanActmod> actividadesRegistradas, List<Long> actividadesRegistradasIds) {
		// Eliminar las actividades que ya no están
		for (PardiaAluplanActmod actividadExistente : actividadesRegistradas) {
		    if (!actividades.contains(actividadExistente.getActividadModulo().getIdActividadModulo())) {
		        hayCambios = true;
		        pardiaAluplanActmodRepository.delete(actividadExistente);
		    }
		}

		// Añadir las actividades que sean nuevas
		for (Long idActividad : actividades) {
		    if (!actividadesRegistradasIds.contains(idActividad)) {
		        Optional<ActividadesModulos> actividadModuloOpt = actividadesModulosRepository.findById(idActividad);
		        if (actividadModuloOpt.isPresent()) {
		            hayCambios = true;
		            PardiaAluplanActmod nuevoParteActividad = new PardiaAluplanActmod();
		            nuevoParteActividad.setPardiaAluplan(parteDiario);
		            nuevoParteActividad.setActividadModulo(actividadModuloOpt.get());
		            pardiaAluplanActmodRepository.save(nuevoParteActividad);
		        }
		    }
		}
		return hayCambios;
	}

    @Override
    @Transactional
    public void borrarActividadSeguimientoPlan(Long idConvProyAlu, String fechaActString, String fechaInicioSemString) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaAct = formatoFecha.parse(fechaActString.replace("_", "/"));
            Date fechaInicioSem = formatoFecha.parse(fechaInicioSemString.replace("_", "/"));

            ParsemAluplan parteSemanal = parsemAluplanRepository.findByIdConvProyAluAndFechaInicioSem(idConvProyAlu, fechaInicioSem);
            if (parteSemanal == null) {
                return;
            }

            PardiaAluplan parteDiario = pardiaAluplanRepository.findByIdConvProyAluAndDia(idConvProyAlu, fechaActString);
            if (parteDiario == null) {
                return;
            }

            List<PardiaAluplanActmod> actividades = pardiaAluplanActmodRepository.findPartesByIdConvProyAluAndDia(idConvProyAlu, fechaAct);
            for (PardiaAluplanActmod actividad : actividades) {
                pardiaAluplanActmodRepository.delete(actividad);
            }

            pardiaAluplanRepository.delete(parteDiario);


            int totalPartesSemana = pardiaAluplanRepository.countPartesDiariosBySemana(parteSemanal.getIdParsemAluplan());

            if (totalPartesSemana == 0 && parteSemanal.getIdParsemRodal() == null) {
                parsemAluplanRepository.delete(parteSemanal);
            } else {
                parteSemanal.setLgActualizado(0);
                parsemAluplanRepository.save(parteSemanal);
            }

        } catch (ParseException e) {
            throw new IllegalStateException();
        }
    }


    @Override
    public InfoPardiaDto getHorasAndObservacionesPardia(Long idConvProyAlu, String fechaDia) {

        PardiaAluplan parte = pardiaAluplanRepository.findByIdConvProyAluAndDia(idConvProyAlu, fechaDia);

        InfoPardiaDto infoParte = new InfoPardiaDto();
        infoParte.setHoras(parte.getNuHoras());
        infoParte.setObservaciones(parte.getTxObservaciones());

        return infoParte;
    }

    private PardiaAluplan guardarDatosParte(PardiaAluplan parteDiario, Integer horas, String observaciones){
            parteDiario.setNuHoras(horas);
            if (observaciones.equals("null")) {
                observaciones = null;
            }
            parteDiario.setTxObservaciones(observaciones);

            return pardiaAluplanRepository.save(parteDiario);
        }
}