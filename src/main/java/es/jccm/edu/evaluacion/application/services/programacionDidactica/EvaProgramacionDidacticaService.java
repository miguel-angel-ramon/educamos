package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CompetenciaEspecificaDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaProgramacionAulaRepository;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.*;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaProgramacionAula;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.*;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.*;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.HistorialResponsableProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.HistoricoProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.ProgramacionDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadProgramacionDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.ponderacion.PonderacionRepository;
import es.jccm.edu.evaluacion.application.domain.ponderacion.Ponderacion;
import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.CriteriosComEspProjection;
import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.PonderacionProjection;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaProgramacionDidacticaService;
import es.jccm.edu.evaluacion.application.services.ponderacion.PonderacionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaProgramacionDidacticaService implements IEvaProgramacionDidacticaService {

    @Autowired
    private EvaProgramacionDidacticaRepository programacionDidacticaRepository;

    @Autowired
    private PonderacionRepository ponderacionRepository;

    @Autowired
    private EvaRelacionProgramacionDidacticaPonderacionRepository progPondRepository;

    @Autowired
    private EvaCentroRepository centroRepository;

    @Autowired
    private PonderacionService ponderacionService;

    @Autowired
    private EvaRelacionUnidadProgramacionCriteriosEvaluacionRepository relacionUnidadProgramacionCriteriosEvaluacionRepository;

    @Autowired
    private EvaRelacionProgramacionDidacticaUnidadProgramacionRepository relacionProgramacionDidacticaUnidadProgramacionRepository;

    @Autowired
    private EvaRelacionPonderacionCriteriosEvaluacionRepository relacionPonderacionCriteriosEvaluacionRepository;

    @Autowired
    private EvaRelacionPonderacionCompetenciaEspecificaRepository relacionPonderacionCompetenciaEspecificaRepository;

    @Autowired
    private EvaValoracionCompetenciaEspecificaAlumnoRepository valoracionCompetenciaEspecificaAlumnoRepository;

    @Autowired
    private EvaValoracionCriterioEvaluacionAlumnoRepository valoracionCriterioEvaluacionAlumnoRepository;

    @Autowired
    private EvaUnidadProgramacionRepository unidadProgramacionRepository;

    @Autowired
    private EvaCriterioEvaluacionRepository criterioEvaluacionRepository;

    @Autowired
    private EvaUnidadProgramacionService programacionService;

    @Autowired
    private EvaProgramacionAulaRepository programacionAulaRepository;

    @Autowired
    private EvaOfertaMatriculaGenericoRepository ofertaMatriculaGenericoRepository;

    @Autowired
    private EvaMateriasCentroRepository materiasCentroRepository;

    @Autowired
    private EvaPerfilEmpleadoEditorProgramacionDidacticaRepository empleadoEditorProgramacionDidacticaRepository;

    @Autowired
    private EvaRelacionUnidadProgramacionSaberBasicoRepository relacionUnidadProgramacionSaberBasicoRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public ProgramacionDidacticaDTO getProgramacionDidacticaByMateriaAndCursoAndCodigoCentroAndAnyo(Long idMateria, Long idOfertamatrig, Long codigoCentro, Integer anyo) throws Exception {
        EvaCentro centro = centroRepository.findByCodigo(codigoCentro);
        if (centro != null) {
            EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findByMateriaomgAndOfertamatrigAndCentroAndAnnoAndAcneae(idMateria, idOfertamatrig, centro.getId(), anyo, 0);
            if(programacionDidactica == null) {
                //TODO revisar este log para registros repetidos
                //LOG.info("No se ha encontrado la programación didáctica.");
                return null;
            }
            EvaRelacionProgramacionDidacticaPonderacion relProgPond = progPondRepository.findByProgramacionDidacticaId(programacionDidactica.getId());
            if(relProgPond == null) {
                crearPonderacion(programacionDidactica, idMateria, null);
                relProgPond = progPondRepository.findByProgramacionDidacticaId(programacionDidactica.getId());
            }
            Ponderacion ponderacionF = null;
            PonderacionProjection ponderacionProjection = ponderacionRepository.getPonderacionesById(relProgPond.getPonderacion().getId());
            if (ponderacionProjection != null) {
                ponderacionF = modelMapper.map(ponderacionProjection, Ponderacion.class);
                ponderacionF = ponderacionService.getCompetencias(ponderacionF);
            }
            ProgramacionDidacticaDTO programacionDidacticaOut = modelMapper.map(programacionDidactica, ProgramacionDidacticaDTO.class);
            programacionDidacticaOut.setPonderacion(modelMapper.map(ponderacionF, es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.PonderacionDto.class));
            EvaMateriasCentroProjection materiaOmg = materiasCentroRepository.findMateriaById(idMateria);
            if (materiaOmg != null) {
                programacionDidacticaOut.setNombreMateria(materiaOmg.getNombreMateria());
            }
            return programacionDidacticaOut;

        } else {
            throw new Exception("No se ha encontrado el centro.");
        }

    }

    @Transactional
    public void insertProgramacionDidactica(ProgramacionDidacticaDTO programacionDidacticaDto, Long idMateria) {
        EvaProgramacionDidactica programacionDidactica = modelMapper.map(programacionDidacticaDto, EvaProgramacionDidactica.class);

        programacionDidacticaRepository.save(programacionDidactica);

        crearPonderacion(programacionDidactica, idMateria, null);
    }

    public Integer puedeCerrarProgramacion(Long idProgramacionDidactica, Long idOfertaMatrig) {
        List<UnidadProgramacionDTO> unidadesProgramacion = programacionService.getUnidadesProgramacion(idProgramacionDidactica);
        List<CriterioEvaluacionDTO> criteriosEvaluacionPonderacion = criterioEvaluacionRepository.findAllByIdProgramacionDidactica(idProgramacionDidactica).stream().map(criterioEvaluacion -> modelMapper.map(criterioEvaluacion, CriterioEvaluacionDTO.class)).collect(Collectors.toList());
        List<Long> criteriosEvaluacionUnidad = new ArrayList<>();
        Integer result = 0;

        if (unidadesProgramacion.isEmpty()) {
            return 0;
        }

        for (UnidadProgramacionDTO unidad : unidadesProgramacion) {
            if (unidad.getConvCentroOmc() == null || unidad.getCriteriosEvaluacionIds().isEmpty()) {
                return 0;
            }
            for(Long criEva : unidad.getCriteriosEvaluacionIds()) {
                if (!criteriosEvaluacionUnidad.contains(criEva)) {
                    criteriosEvaluacionUnidad.add(criEva);
                }
            }
        }

        if(criteriosEvaluacionPonderacion.size() == criteriosEvaluacionUnidad.size()) {
            result = 1;
        } else {
            List<Long> idOfertaMatrigs = programacionDidacticaRepository.getIdOfertamatrigCiclos();
            if(idOfertaMatrigs.contains(idOfertaMatrig)) {
                result = 2;
            } else {
                result = 0;
            }
        }

        return result;
    }

    public boolean puedeAbrirProgramacion(Long idProgramacionDidactica) {
        EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findById(idProgramacionDidactica).orElse(null);
        if(programacionDidactica != null) {
            List<EvaProgramacionAula>  programacionesAula = programacionAulaRepository.findAllByProgramacionDidactica(programacionDidactica);
            if(programacionesAula.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Transactional
    public void cerrarProgramacionDidactica(Long idProgramacionDidactica, Integer cerrar) throws Exception {
        EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findById(idProgramacionDidactica).orElse(null);

        if (programacionDidactica != null) {
            programacionDidactica.setCerrada(cerrar);
            programacionDidacticaRepository.save(programacionDidactica);

        } else {
            throw new Exception("No se ha encontrado la programación didáctica");
        }
    }

    @Transactional
    public void updateProgramacionDidactica(ProgramacionDidacticaDTO programacionDidacticaDto) {
        EvaProgramacionDidactica programacionDidactica = modelMapper.map(programacionDidacticaDto, EvaProgramacionDidactica.class);

        if (programacionDidactica != null && programacionDidacticaRepository.existsById(programacionDidactica.getId())) {
            programacionDidacticaRepository.save(programacionDidactica);
        }
    }

    @Transactional
    public Boolean deleteProgramacionDidactica(Long idProgramacionDidactica) {
        // Busco si existe la Unidad de programación mediante el id recibido y la rescato
        EvaProgramacionDidactica borrarProgramacionDidactica = programacionDidacticaRepository.findById(idProgramacionDidactica).orElse(null);

        if(borrarProgramacionDidactica != null) {
            // Rescato todas las relaciones con las unidades de programación
            List<EvaRelacionProgramacionDidacticaUnidadProgramacion> relProgDidacUnidadProg = relacionProgramacionDidacticaUnidadProgramacionRepository.findByProgramacionDidactica(borrarProgramacionDidactica);

            // Recorro todas las relaciones y rescato la unidad de programación
            for (EvaRelacionProgramacionDidacticaUnidadProgramacion progDidacUnidadProg : relProgDidacUnidadProg) {
                EvaUnidadProgramacion unidadProgramacion = progDidacUnidadProg.getUnidadProgramacion();

                // Borro la relación con la unidad de programación
                relacionProgramacionDidacticaUnidadProgramacionRepository.deleteById(progDidacUnidadProg.getId());

                // Borro todas las relaciones con los criterios
                relacionUnidadProgramacionCriteriosEvaluacionRepository.deleteAllByUnidadProgramacion(unidadProgramacion);

                // Borro todas las relaciones con los saberes básicos
                relacionUnidadProgramacionSaberBasicoRepository.deleteAllByUnidadProgramacion(unidadProgramacion);

                // Borro la unidad de programación
                unidadProgramacionRepository.deleteById(unidadProgramacion.getId());
            }

            // Rescato la relación con la ponderación
            EvaRelacionProgramacionDidacticaPonderacion relprogdidacpond = progPondRepository.findByProgramacionDidacticaId(borrarProgramacionDidactica.getId());

            if (relprogdidacpond != null) {
                EvaPonderacion ponderacion = relprogdidacpond.getPonderacion();

                if (ponderacion != null) {
                    // Borro las valoraciones de las criterios de evaluacion de los alumnos
                    valoracionCriterioEvaluacionAlumnoRepository.deleteAllByPonderacion(ponderacion);

                    // Borro las valoraciones de las competencias específicas de los alumnos
                    valoracionCompetenciaEspecificaAlumnoRepository.deleteAllByPonderacion(ponderacion);

                    // Borro las relaciones de la ponderación con todos los criterios
                    relacionPonderacionCriteriosEvaluacionRepository.deleteAllByPonderacion(ponderacion);

                    // Borro las relaciones de la ponderación con las competencias específicas
                    relacionPonderacionCompetenciaEspecificaRepository.deleteAllByPonderacion(ponderacion);

                    // Borro la relación de la programación didáctica con la ponderación
                    progPondRepository.deleteById(relprogdidacpond.getId());

                    // Borro la ponderación
                    ponderacionRepository.deleteById(ponderacion.getId());
                }
            }

            // Borro la programación didáctica
            programacionDidacticaRepository.deleteById(borrarProgramacionDidactica.getId());

            log.info("Se ha borrado exitosamente la programación didáctica con id= " + idProgramacionDidactica);

            return true;
        } else {
            log.error("Error al borrar la programación didáctica con id= " + idProgramacionDidactica);

            return false;
        }
    }

    private int contarDuplicados(Long idCriterio, List<CriteriosComEspProjection> comesp) {
        int repetidos = 0;
        CriteriosComEspProjection valueComesp = null;

        for (CriteriosComEspProjection cricom : comesp) {
            if (cricom.getIdCriterio().equals(idCriterio)) {
                valueComesp = cricom;
            }
        }

        for (CriteriosComEspProjection cricom : comesp) {
            if (valueComesp != null && cricom.getIdPonderacion().equals(valueComesp.getIdPonderacion())) {
                repetidos++;
            }
        }
        return repetidos;
    }

    public void crearPonderacion(EvaProgramacionDidactica programacionDidactica, Long idMateria, List<Long> criterios) {
        EvaPonderacion ponderacion = new EvaPonderacion();
        EvaRelacionProgramacionDidacticaPonderacion evaRelProgDidacPond = new EvaRelacionProgramacionDidacticaPonderacion();

        ponderacion.setIdMateria(idMateria);

        ponderacionRepository.save(ponderacion);

        evaRelProgDidacPond.setProgramacionDidactica(programacionDidactica);
        evaRelProgDidacPond.setPonderacion(ponderacion);

        progPondRepository.save(evaRelProgDidacPond);

        List<Long> idCriterios = null;
        List<Long> idCompetencias = null;

        //si es o no acneae
        if(criterios == null) {
            idCompetencias = ponderacionRepository.getCompetenciasByMateria(ponderacion.getIdMateria());
            for (Long idCompetencia : idCompetencias) {
                ponderacionRepository.insertCompetencias(ponderacion.getId(), idCompetencia, (float) 100 / idCompetencias.size());
            }

            idCriterios = ponderacionRepository.getCriteriosByPonderacion(ponderacion.getId());
        } else {
            idCriterios = criterios;

            idCompetencias = ponderacionRepository.getCompetenciasByCriterios(idCriterios);

            for (Long idCompetencia : idCompetencias) {
                ponderacionRepository.insertCompetencias(ponderacion.getId(), idCompetencia, (float) 100 / idCompetencias.size());
            }
        }

        List<CriteriosComEspProjection> idCriteriosPorcen = ponderacionRepository.getCriteriosCompEsp(idCriterios);
        // calcular porcentaje en funcion de criterio y comespecifica
        for(Long idCriterio: idCriterios) {
            int repetidos = contarDuplicados(idCriterio, idCriteriosPorcen);
            if(repetidos != 0) {
                ponderacionRepository.insertCriterios(ponderacion.getId(), idCriterio, (float) 100 / repetidos);
            }
        }
    }

    @Override
    public EvaCompetenciaEspecificaDidacticaProjection comprobarDuplicarProgramacionDidactica(Long idMateriaOmg, Long idOfermatrig, Long idCentro, Integer anno) {

        return programacionDidacticaRepository.comprobarDuplicarProgramacionDidactica(idMateriaOmg, idOfermatrig, idCentro, anno);
    }

    @Transactional
    public EvaProgramacionDidactica duplicarProgramacionDidactica(Long idProgramacionDidactica, Long idMateriaOmg) {

        EvaProgramacionDidactica nuevaProgramacionDidactica = new EvaProgramacionDidactica();

        // Rescato la programación didáctica a duplicar
        EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findById(idProgramacionDidactica).orElse(null);

        if(programacionDidactica != null) {
            //Creo la nueva programación didáctica con los datos de la otra
            nuevaProgramacionDidactica.setMateriaomg(idMateriaOmg);
            nuevaProgramacionDidactica.setOfertamatrig(programacionDidactica.getOfertamatrig());
            nuevaProgramacionDidactica.setCentro(programacionDidactica.getCentro());
            nuevaProgramacionDidactica.setAnno(programacionDidactica.getAnno());

            programacionDidacticaRepository.save(nuevaProgramacionDidactica);

            duplicarPonderacion(nuevaProgramacionDidactica, idProgramacionDidactica);

            duplicarUnidadesProgramacion(nuevaProgramacionDidactica, programacionDidactica);


        }

        return nuevaProgramacionDidactica;
    }

    @Transactional
    public EvaProgramacionDidactica duplicarProgramacionDidactica(Long idProgramacionDidactica, Integer anyo) {
        EvaProgramacionDidactica nuevaProgramacionDidactica = new EvaProgramacionDidactica();

        // Rescato la programación didáctica a duplicar
        EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findById(idProgramacionDidactica).orElse(null);

        if(programacionDidactica != null) {
            //Creo la nueva programación didáctica con los datos de la otra
            nuevaProgramacionDidactica.setMateriaomg(programacionDidactica.getMateriaomg());
            nuevaProgramacionDidactica.setOfertamatrig(programacionDidactica.getOfertamatrig());
            nuevaProgramacionDidactica.setCentro(programacionDidactica.getCentro());
            nuevaProgramacionDidactica.setAnno(anyo);

            programacionDidacticaRepository.save(nuevaProgramacionDidactica);

            duplicarPonderacion(nuevaProgramacionDidactica, idProgramacionDidactica);

            duplicarUnidadesProgramacion(nuevaProgramacionDidactica, programacionDidactica);
        }

        return nuevaProgramacionDidactica;
    }

    @Transactional
    public EvaProgramacionDidactica duplicarProgramacionDidactica(Long idProgramacionDidactica, Long idMateriaOmg, Integer anyo) {
        EvaProgramacionDidactica nuevaProgramacionDidactica = new EvaProgramacionDidactica();

        // Rescato la programación didáctica a duplicar
        EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findById(idProgramacionDidactica).orElse(null);

        if(programacionDidactica != null) {
            //Creo la nueva programación didáctica con los datos de la otra
            nuevaProgramacionDidactica.setMateriaomg(idMateriaOmg);
            nuevaProgramacionDidactica.setOfertamatrig(programacionDidactica.getOfertamatrig());
            nuevaProgramacionDidactica.setCentro(programacionDidactica.getCentro());
            nuevaProgramacionDidactica.setAnno(anyo);

            programacionDidacticaRepository.save(nuevaProgramacionDidactica);

            duplicarPonderacion(nuevaProgramacionDidactica, idProgramacionDidactica);

            duplicarUnidadesProgramacion(nuevaProgramacionDidactica, programacionDidactica);
        }

        return nuevaProgramacionDidactica;
    }

    @Transactional
    public void duplicarPonderacion(EvaProgramacionDidactica nuevaProgramacionDidactica, Long idProgramacionDidactica) {
        // Rescato la relación de la Programación Didáctica recibida con la ponderación
        EvaRelacionProgramacionDidacticaPonderacion relprogdidacpond = progPondRepository.findByProgramacionDidacticaId(idProgramacionDidactica);

        // Rescato la ponderación
        EvaPonderacion ponderacion = relprogdidacpond.getPonderacion();

        if(ponderacion != null) {
            EvaPonderacion nuevaPonderacion = new EvaPonderacion();
            EvaRelacionProgramacionDidacticaPonderacion nuevaRelprogdidacpond = new EvaRelacionProgramacionDidacticaPonderacion();

            // Creo la nueva ponderación
            nuevaPonderacion.setDescripcion(ponderacion.getDescripcion());
            nuevaPonderacion.setIdMateria(ponderacion.getIdMateria());

            ponderacionRepository.save(nuevaPonderacion);

            // Asocio la nueva ponderación a la nueva programación didáctica
            nuevaRelprogdidacpond.setProgramacionDidactica(nuevaProgramacionDidactica);
            nuevaRelprogdidacpond.setPonderacion(nuevaPonderacion);

            progPondRepository.save(nuevaRelprogdidacpond);

            // Rescato la relación de la ponderación con los criterios de evaluación con sus pesos y porcentajes y las duplico para la nueva ponderación
            List<EvaRelacionPonderacionCriteriosEvaluacion> criteriosEvaluacionPonderacion =
                    relacionPonderacionCriteriosEvaluacionRepository.getAllByPonderacion(ponderacion);

            for(EvaRelacionPonderacionCriteriosEvaluacion criterioEvaluacionPonderacion : criteriosEvaluacionPonderacion) {
                EvaRelacionPonderacionCriteriosEvaluacion nuevoCriterioEvaluacionPonderacion = new EvaRelacionPonderacionCriteriosEvaluacion();

                nuevoCriterioEvaluacionPonderacion.setPeso(criterioEvaluacionPonderacion.getPeso());
                nuevoCriterioEvaluacionPonderacion.setPorcentaje(criterioEvaluacionPonderacion.getPorcentaje());
                nuevoCriterioEvaluacionPonderacion.setIdOpeCalCriEva(criterioEvaluacionPonderacion.getIdOpeCalCriEva());
                nuevoCriterioEvaluacionPonderacion.setPonderacion(nuevaPonderacion);
                nuevoCriterioEvaluacionPonderacion.setCriteriosEvaluacion(criterioEvaluacionPonderacion.getCriteriosEvaluacion());

                relacionPonderacionCriteriosEvaluacionRepository.save(nuevoCriterioEvaluacionPonderacion);
            }

            // Rescato la relación de la ponderación con las compentencias específicas con sus pesos y porcentajes y las duplico para la nueva ponderación
            List<EvaRelacionPonderacionCompetenciaEspecifica> compentenciasEspecificasPonderacion =
                    relacionPonderacionCompetenciaEspecificaRepository.getAllByPonderacion(ponderacion);

            for(EvaRelacionPonderacionCompetenciaEspecifica compentenciaEspecificasPonderacion : compentenciasEspecificasPonderacion) {
                EvaRelacionPonderacionCompetenciaEspecifica nuevaCompentenciaEspecificasPonderacion = new EvaRelacionPonderacionCompetenciaEspecifica();

                nuevaCompentenciaEspecificasPonderacion.setPeso(compentenciaEspecificasPonderacion.getPeso());
                nuevaCompentenciaEspecificasPonderacion.setPorcentaje(compentenciaEspecificasPonderacion.getPorcentaje());
                nuevaCompentenciaEspecificasPonderacion.setIdCompetenciaEspecifica(compentenciaEspecificasPonderacion.getIdCompetenciaEspecifica());
                nuevaCompentenciaEspecificasPonderacion.setPonderacion(nuevaPonderacion);

                relacionPonderacionCompetenciaEspecificaRepository.save(nuevaCompentenciaEspecificasPonderacion);
            }
        }
    }

    public void duplicarUnidadesProgramacion(EvaProgramacionDidactica nuevaProgramacionDidactica, EvaProgramacionDidactica programacionDidactica) {
        // Rescato la relación de la programación didáctica base con las unidades de programación y las duplico para la programación didáctica
        List<EvaRelacionProgramacionDidacticaUnidadProgramacion> relsProgDidacUnidadProg =
                relacionProgramacionDidacticaUnidadProgramacionRepository.findByProgramacionDidactica(programacionDidactica);

        for(EvaRelacionProgramacionDidacticaUnidadProgramacion relProgDidacUnidadProg : relsProgDidacUnidadProg) {
            EvaRelacionProgramacionDidacticaUnidadProgramacion nuevaRelProgDidacUnidadProg = new EvaRelacionProgramacionDidacticaUnidadProgramacion();
            EvaUnidadProgramacion nuevaUnidadProgramacion = new EvaUnidadProgramacion();

            EvaUnidadProgramacion unidadProgramacion = unidadProgramacionRepository.findById(relProgDidacUnidadProg.getUnidadProgramacion().getId()).orElse(null);

            // Rescato y creo la nueva unidad de programación con los datos de la antigua
            if(nuevaUnidadProgramacion != null) {
                assert unidadProgramacion != null;
                nuevaUnidadProgramacion.setNombre(unidadProgramacion.getNombre());
                nuevaUnidadProgramacion.setAbreviatura(unidadProgramacion.getAbreviatura());
                nuevaUnidadProgramacion.setDescripcion(unidadProgramacion.getDescripcion());
                nuevaUnidadProgramacion.setOrden(unidadProgramacion.getOrden());
                nuevaUnidadProgramacion.setConvCentroOmc(programacionService.obtenerConvocatoriaCentroOMC(unidadProgramacion.getConvCentroOmc(), programacionDidactica.getAnno(), nuevaProgramacionDidactica.getAnno()));

                unidadProgramacionRepository.save(nuevaUnidadProgramacion);

                // Creo la relación entre la nueva unidad de programación y la programación didáctica
                nuevaRelProgDidacUnidadProg.setProgramacionDidactica(nuevaProgramacionDidactica);
                nuevaRelProgDidacUnidadProg.setUnidadProgramacion(nuevaUnidadProgramacion);

                relacionProgramacionDidacticaUnidadProgramacionRepository.save(nuevaRelProgDidacUnidadProg);

                // Rescato la relación de la unidad de programación con los criterios de evaluación y los duplico para la unidad de programación
                List<EvaRelacionUnidadProgramacionCriteriosEvaluacion> criteriosEvaluacion =
                        relacionUnidadProgramacionCriteriosEvaluacionRepository.findAllByUnidadProgramacion(unidadProgramacion);

                for(EvaRelacionUnidadProgramacionCriteriosEvaluacion criterioEvaluacion : criteriosEvaluacion) {
                    EvaRelacionUnidadProgramacionCriteriosEvaluacion nuevoCriterioEvaluacion = new EvaRelacionUnidadProgramacionCriteriosEvaluacion();

                    nuevoCriterioEvaluacion.setUnidadProgramacion(nuevaUnidadProgramacion);
                    nuevoCriterioEvaluacion.setCriterioEvaluacion(criterioEvaluacion.getCriterioEvaluacion());

                    relacionUnidadProgramacionCriteriosEvaluacionRepository.save(nuevoCriterioEvaluacion);
                }

                //Rescato la relación de la unidad de programación con los saberes básicos y los duplico para la nueva unidad de programación
                List<EvaRelacionUnidadProgramacionSaberBasico> saberesBasicos =
                        relacionUnidadProgramacionSaberBasicoRepository.findAllByUnidadProgramacion(unidadProgramacion);

                for(EvaRelacionUnidadProgramacionSaberBasico saberBasico : saberesBasicos) {
                    EvaRelacionUnidadProgramacionSaberBasico nuevoSaberBasico = new EvaRelacionUnidadProgramacionSaberBasico();

                    nuevoSaberBasico.setUnidadProgramacion(unidadProgramacion);
                    nuevoSaberBasico.setSaberBasico(saberBasico.getSaberBasico());

                    relacionUnidadProgramacionSaberBasicoRepository.save(nuevoSaberBasico);
                }
            }

        }
    }

    @Transactional
    public void insertProgramacionDidacticaACNEAE(ProgramacionDidacticaDTO programacionDidacticaDto, Long idMateriaOmgNivelCurricular, List<Long> criterios) {
        EvaProgramacionDidactica programacionDidactica = modelMapper.map(programacionDidacticaDto, EvaProgramacionDidactica.class);

        if (programacionDidactica.getAcneae() == null) {
            programacionDidactica.setAcneae(1);
        }

        if (programacionDidactica.getCerrada() == null) {
            programacionDidactica.setCerrada(0);
        }

        programacionDidacticaRepository.save(programacionDidactica);

        crearPonderacion(programacionDidactica, idMateriaOmgNivelCurricular, criterios);
    }

    @Override
    @Transactional
    public ProgramacionDidacticaDTO getProgramacionDidacticaByMateriaAndCursoAndCodigoCentroAndAnyoAndAcneaeAndNivelCurricular(Long idMateria, Long idOfertamatrig, Long codigoCentro, Integer anyo, Long idNivelCurricular) throws Exception {

        EvaCentro centro = centroRepository.findByCodigo(codigoCentro);
        Integer acneae = null;

        if (centro != null) {
            EvaProgramacionDidactica programacionDidactica;


            if(idNivelCurricular == -1) {
                acneae = 0;
            } else {
                acneae = 1;
            }

            if (acneae == 1) {
                programacionDidactica = programacionDidacticaRepository.findByMateriaomgAndOfertamatrigAndCentroAndAnnoAndAcneaeAndNiveadap(idMateria, idOfertamatrig, centro.getId(), anyo, acneae, idNivelCurricular);
            } else {
                programacionDidactica = programacionDidacticaRepository.findByMateriaomgAndOfertamatrigAndCentroAndAnnoAndAcneae(idMateria, idOfertamatrig, centro.getId(), anyo, 0);
            }

            if(programacionDidactica == null) {
                //TODO revisar este log para registros repetidos
                //LOG.info("No se ha encontrado la programación didáctica.");
                return null;
            }
            EvaRelacionProgramacionDidacticaPonderacion relProgPond = progPondRepository.findByProgramacionDidacticaId(programacionDidactica.getId());

            if(relProgPond == null) {
                Long idMateriaPonderacion;

                if (acneae == 1) {
                    idMateriaPonderacion = programacionDidacticaRepository.getIdMateriaOmgACNEAE(idMateria, idNivelCurricular);
                } else {
                    idMateriaPonderacion = idMateria;
                }

                crearPonderacion(programacionDidactica, idMateriaPonderacion, null);

                relProgPond = progPondRepository.findByProgramacionDidacticaId(programacionDidactica.getId());
            }

            Ponderacion ponderacionF = null;

            PonderacionProjection ponderacionProjection = ponderacionRepository.getPonderacionesById(relProgPond.getPonderacion().getId());
            if (ponderacionProjection != null) {
                ponderacionF = modelMapper.map(ponderacionProjection, Ponderacion.class);
                ponderacionF = ponderacionService.getCompetencias(ponderacionF);
            }

            ProgramacionDidacticaDTO programacionDidacticaOut = modelMapper.map(programacionDidactica, ProgramacionDidacticaDTO.class);

            EvaMateriasCentroProjection materiaOmg = materiasCentroRepository.findMateriaById(idMateria);
            if (materiaOmg != null) {
                programacionDidacticaOut.setNombreMateria(materiaOmg.getNombreMateria());
            }

            programacionDidacticaOut.setPonderacion(modelMapper.map(ponderacionF, es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.PonderacionDto.class));
            if (acneae == 1) {
                EvaMateriasCentroProjection materiaOmgAcnee = materiasCentroRepository.findMateriaById(programacionDidacticaOut.getIdMateriaOmgAdap());
                EvaOfertaMatriculaGenerico ofertaMatrigAcneae = ofertaMatriculaGenericoRepository.findById(idNivelCurricular).orElse(null);
                if(ofertaMatrigAcneae != null) {
                    programacionDidacticaOut.setNombreNivelCurricular(materiaOmgAcnee.getNivelCurricular() + " - " + ofertaMatrigAcneae.getDescripcion());
                }
            }

            EvaInformeRodalProjection datosInforme = programacionDidacticaRepository.datosInformeRodal(programacionDidacticaOut.getId());
            if (datosInforme != null) {
                programacionDidacticaOut.setIdRodal(datosInforme.getIdRodal());
                programacionDidacticaOut.setNombreFichero(datosInforme.getNombreFichero());
            }

            programacionDidacticaOut.setListaEditores((getEditores(idOfertamatrig, idMateria, codigoCentro, anyo, idNivelCurricular, -1L)));

            EvaDepartamentoCentroProjection depart =  programacionDidacticaRepository.getDepartamentosProgramacionDidactica(centro.getId(), anyo, idMateria);

            if(depart != null){
                programacionDidacticaOut.setDepartamento(modelMapper.map(depart, EvaDepartamento.class));

            }

            return programacionDidacticaOut;

        } else {
            throw new Exception("No se ha encontrado el centro.");
        }

    }

    public List<CompetenciaEspecificaDidacticaDTO> competenciasCriteriosAcneae(Long idMateriaOmg, Long idNivelCurricular) throws Exception {
        Long idMateriaOmgAcneae = programacionDidacticaRepository.getIdMateriaOmgACNEAE(idMateriaOmg, idNivelCurricular);
        List<CompetenciaEspecificaDidacticaDTO> competencias = new ArrayList<>();

        if(idMateriaOmgAcneae != null) {
            competencias = programacionService.getCompetenciasEspecificas(idMateriaOmgAcneae);

            if(competencias == null || competencias.isEmpty()) {
                throw new Exception("error 1");
            }
        } else {
            throw new Exception("error 2");
        }

        return competencias;
    }

    public Boolean deleteDuplicatesProgramacionDidacticaByMateriaAndCursoAndCentroAndAnyo(Long idMateriaOmg, Long idOfertamatrig, Long idCentro, Integer anyo) {
        List<EvaProgramacionDidactica> programacionesDidacticas = programacionDidacticaRepository.getProgramacionesDidacticasByMateriaomgAndOfertamatrigAndCentroAndAnno(idMateriaOmg, idOfertamatrig, idCentro, anyo);

        return borrarDuplicadosProgramacionDidactica(programacionesDidacticas);
    }

    @Override
    public Boolean deleteDuplicatesProgramacionDidacticaByMateriaAndCursoAndCentroAndAnyoAndNivelCurricular(Long idMateriaOmg, Long idOfertamatrig, Long idCentro, Integer anyo, Long idNivelCurricular, Long idMateriaOmgNivelCurricular) {
        List<EvaProgramacionDidactica> programacionesDidacticas = programacionDidacticaRepository.findAllByMateriaomgAndOfertamatrigAndCentroAndAnnoAndAcneaeAndNiveadapAndIdMateriaOmgAdap(idMateriaOmg, idOfertamatrig, idCentro, anyo, 1, idNivelCurricular, idMateriaOmgNivelCurricular);

        return borrarDuplicadosProgramacionDidactica(programacionesDidacticas);
    }

    private Boolean borrarDuplicadosProgramacionDidactica(List<EvaProgramacionDidactica> programacionesDidacticas) {
        if (!programacionesDidacticas.isEmpty() && programacionesDidacticas.size() > 1) {
            // Se rescata el original, el cual habitualmente es el primero que se ha creado de todos
            EvaProgramacionDidactica programacionDidactica = programacionesDidacticas.remove(0);

            Boolean borrado = true;

            for (EvaProgramacionDidactica progDidac : programacionesDidacticas) {
                borrado &= deleteProgramacionDidactica(progDidac.getId());
            }

            return borrado;
        } else {
            return false;
        }
    }

    @Override
    public Boolean borrarTodosDuplicadosProgramacionesDidacticas() {
        List<EvaProgramacionDidacticaDuplicadaProjection> programacionesDidacticasDuplicadasProjection = programacionDidacticaRepository.getProgramacionesDidacticasDuplicadas();

        Boolean borrado = true;

        for(EvaProgramacionDidacticaDuplicadaProjection progDidacDuplProj : programacionesDidacticasDuplicadasProjection) {
            borrado &= deleteDuplicatesProgramacionDidacticaByMateriaAndCursoAndCentroAndAnyo(progDidacDuplProj.getIdMateriaOmg(), progDidacDuplProj.getIdOfertaMatrig(), progDidacDuplProj.getIdCentro(), progDidacDuplProj.getAnyo());
        }

        return borrado;
    }

    @Transactional
    public void appointResponsableProgramacionDidactica(Long idEmpleado, Long idOfertaMatrig, Long idMateriaOmg, Long idCentro, Integer anyo, Long idNivelCurricular, Long idMateriaOmgAdap){
        Date fechaActual = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        EvaPerfilEmpleadoEditorProgramacionDidactica responsableActual = empleadoEditorProgramacionDidacticaRepository.findByIdOfertaMatrigAndIdMateriaOmgAndIdCentroAndAnnoAndNiveAdapAndIdMateriaOmgAdapAndActivo(idOfertaMatrig, idMateriaOmg, idCentro, anyo, idNivelCurricular, idMateriaOmgAdap, 1);
        //Si es empleado nuevo:
        if (responsableActual == null || !Objects.equals(idEmpleado, responsableActual.getIdEmpleado())) {
            if(responsableActual != null){
                //Desactivamos el responsable anterior:
                responsableActual.setFechaFin(fechaActual);
                responsableActual.setActivo(0);
                empleadoEditorProgramacionDidacticaRepository.save(responsableActual);
            }
            //Asignamos nuevo:
            EvaPerfilEmpleadoEditorProgramacionDidactica perfil = new EvaPerfilEmpleadoEditorProgramacionDidactica();
            perfil.setIdEmpleado(idEmpleado);
            perfil.setIdCentro(idCentro);
            perfil.setIdMateriaOmg(idMateriaOmg);
            perfil.setIdOfertaMatrig(idOfertaMatrig);
            perfil.setAnno(anyo);
            perfil.setNiveAdap(idNivelCurricular);
            perfil.setIdMateriaOmgAdap(idMateriaOmgAdap);
            perfil.setFechaInicio(fechaActual);
            empleadoEditorProgramacionDidacticaRepository.save(perfil);
        }
    }

    @Override
    public List<HistorialResponsableProgramacionDidacticaDTO> getHistorialResponsablesProgramacionDidactica(Long idOfertaMatrig, Long idMateriaOmg,
                                                                                                            Long idCentro, Integer anyo, Long idNivelCurricular, Long idMateriaOmgAdap) {



        return empleadoEditorProgramacionDidacticaRepository.getHistorialResponsablesProgramacionDidactica(idOfertaMatrig, idMateriaOmg, idCentro, anyo, idNivelCurricular, idMateriaOmgAdap).stream().map(empEditProgDidac -> modelMapper.map(empEditProgDidac, HistorialResponsableProgramacionDidacticaDTO.class)).collect(Collectors.toList());
    }


    @Override
    public List<HistorialResponsableProgramacionDidacticaDTO> getProfesoresDepartamentoProgramacionDidactica(Long idCentro, Integer anyo, Long idEmpleado) {
        return empleadoEditorProgramacionDidacticaRepository.getProfesoresDepartamentoProgramacionDidactica(idCentro,anyo, idEmpleado).stream().map(empEditProgDidac -> modelMapper.map(empEditProgDidac, HistorialResponsableProgramacionDidacticaDTO.class)).collect(Collectors.toList());
    }


    @Transactional

    public List<HistorialResponsableProgramacionDidacticaDTO> getEditores(Long idOfertaMatrig, Long idMateriaOmg,
                                                                          Long idCentro, Integer anyo, Long idNivelCurricular, Long idMateriaOmgAdapm) {
        return empleadoEditorProgramacionDidacticaRepository.getEmpleadosEditores(idOfertaMatrig, idMateriaOmg, idCentro, anyo, idNivelCurricular, idMateriaOmgAdapm).stream().map(empEditProgDidac -> modelMapper.map(empEditProgDidac, HistorialResponsableProgramacionDidacticaDTO.class)).collect(Collectors.toList());
    }

    @Transactional

    public void revokeResponsableProgramacionDidactica(Long idOfertaMatrig, Long idMateriaOmg, Long idCentro,
                                                       Integer anyo, Long idNivelCurricular, Long idMateriaOmgAdap) {
        EvaPerfilEmpleadoEditorProgramacionDidactica responsableActual = empleadoEditorProgramacionDidacticaRepository.findByIdOfertaMatrigAndIdMateriaOmgAndIdCentroAndAnnoAndNiveAdapAndIdMateriaOmgAdapAndActivo(idOfertaMatrig, idMateriaOmg, idCentro, anyo, idNivelCurricular, idMateriaOmgAdap, 1);
        if (responsableActual != null) {
            Date fechaActual = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            responsableActual.setFechaFin(fechaActual);
            responsableActual.setActivo(0);
            empleadoEditorProgramacionDidacticaRepository.save(responsableActual);
        }
    }

    public List<EvaDepartamento>getDepartamentosCentro(Long idCentro, Long anyo){
        return empleadoEditorProgramacionDidacticaRepository.getDepartamentosCentro(idCentro, anyo).stream().map(x -> modelMapper.map(x, EvaDepartamento.class)).collect(Collectors.toList());
    }

    @Override
    public List<HistoricoProgramacionDidacticaDTO> getProgramacionesDidacticasAnyosAnteriores(Long codigoCentro, Long idMateriaOmg, Long idOfertamatrig) {
        return programacionDidacticaRepository.getProgramacionesDidacticasAnyosAnteriores(codigoCentro, idMateriaOmg, idOfertamatrig).stream().map(x -> modelMapper.map(x, HistoricoProgramacionDidacticaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<HistoricoProgramacionDidacticaDTO> getProgramacionesDidacticasACNEEAnyosAnteriores(Long codigoCentro, Long idMateriaOmg, Long idOfertamatrig, Long idNivelCurricular) {
        return programacionDidacticaRepository.getProgramacionesDidacticasACNEEAnyosAnteriores(codigoCentro, idMateriaOmg, idOfertamatrig, idNivelCurricular).stream().map(x -> modelMapper.map(x, HistoricoProgramacionDidacticaDTO.class)).collect(Collectors.toList());
    }

    public Integer hasProgramacionDidacticaAnnoAcademicoActual(Long codigoCentro, Long idMateriaOmg, Long idOfertamatrig){
        return programacionDidacticaRepository.hasProgramacionDidacticaAnnoAcademicoActual(codigoCentro,idMateriaOmg,idOfertamatrig);
    }

    public Integer hasProgramacionDidacticaAnnoAcademicoActualAcnee(Long codigoCentro, Long idMateriaOmg, Long idOfertamatrig, Long aLong){
        return programacionDidacticaRepository.hasProgramacionDidacticaAnnoAcademicoActualAcnee(codigoCentro,idMateriaOmg,idOfertamatrig,aLong);
    }

    @Transactional
    public EvaProgramacionDidactica duplicarProgramacionDidactica(Long idProgramacionDidactica, Long idMateriaOmg, Integer anyo, Long idMateriaOmgAdaptacion) {
        EvaProgramacionDidactica nuevaProgramacionDidactica = new EvaProgramacionDidactica();

        // Rescato la programación didáctica a duplicar
        EvaProgramacionDidactica programacionDidactica = programacionDidacticaRepository.findById(idProgramacionDidactica).orElse(null);

        if(programacionDidactica != null) {
            //Creo la nueva programación didáctica con los datos de la otra
            nuevaProgramacionDidactica.setMateriaomg(idMateriaOmg);
            nuevaProgramacionDidactica.setOfertamatrig(programacionDidactica.getOfertamatrig());
            nuevaProgramacionDidactica.setCentro(programacionDidactica.getCentro());
            nuevaProgramacionDidactica.setAnno(anyo);
            nuevaProgramacionDidactica.setAcneae(programacionDidactica.getAcneae());
            nuevaProgramacionDidactica.setNiveadap(programacionDidactica.getNiveadap());
            nuevaProgramacionDidactica.setIdMateriaOmgAdap(idMateriaOmgAdaptacion);

            programacionDidacticaRepository.save(nuevaProgramacionDidactica);

            duplicarPonderacion(nuevaProgramacionDidactica, idProgramacionDidactica);

            duplicarUnidadesProgramacion(nuevaProgramacionDidactica, programacionDidactica);
        }

        return nuevaProgramacionDidactica;
    }

}