package es.jccm.edu.evaluacion.application.services.ponderacion;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.PonderacionDto;
import es.jccm.edu.evaluacion.adapter.out.repositories.ponderacion.PonderacionRepository;
import es.jccm.edu.evaluacion.application.domain.ponderacion.CompetenciasEspecificas;
import es.jccm.edu.evaluacion.application.domain.ponderacion.CriterioList;
import es.jccm.edu.evaluacion.application.domain.ponderacion.DocentePonderacion;
import es.jccm.edu.evaluacion.application.domain.ponderacion.MateriasUnidad;
import es.jccm.edu.evaluacion.application.domain.ponderacion.Ponderacion;
import es.jccm.edu.evaluacion.application.domain.ponderacion.enums.SaveType;
import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.CriteriosComEspProjection;
import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.DocentePonderacionProjection;
import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.MateriasUnidadProjection;
import es.jccm.edu.evaluacion.application.domain.ponderacion.projection.PonderacionProjection;
import es.jccm.edu.evaluacion.application.ports.in.ponderacion.IPonderacionService;

@Service
public class PonderacionService implements IPonderacionService {

    private static final Logger LOG = LogManager.getLogger(PonderacionService.class);

    @Autowired
    private PonderacionRepository ponderacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MateriasUnidad> getMateriasUnidad(Long idEmpleado, Integer anno, Long codigoCentro) {
        LOG.debug("Obteniendo materias del usuario ", idEmpleado);

        List<MateriasUnidadProjection> materiasProjection = ponderacionRepository.findAllMaterias(idEmpleado, anno, codigoCentro);
        List<MateriasUnidad> materias = materiasProjection.stream().map(materia -> modelMapper.map(materia, MateriasUnidad.class)).collect(Collectors.toList());
        
        for(MateriasUnidad materia: materias) {
        	PonderacionProjection ponderacionProjection = ponderacionRepository.getPonderacionesByCentro(materia.getIdMateria(), idEmpleado, codigoCentro);
			materia.setEstaPonderada(ponderacionProjection != null);
			
			//Comprobamos si existen competecias para saber si la pondeación tiene curriculum asociado en BBDD y bloquear el botón de detalle de la ponderación en la pestaña
			//de Ponderación de la aplicación de Evaluación
			materia.setExistenCompetencias(this.existenCompetencias(materia.getIdMateria()));
		}

        return materias;
    }

    @Override
    @Transactional
    public Ponderacion getPonderaciones(Long idMateria, Long idEmpleado) {
        LOG.debug("Obteniendo las ponderaciones de la materia: ", idMateria);

        PonderacionProjection ponderacionProjection = ponderacionRepository.getPonderaciones(idMateria, idEmpleado);

        if (ponderacionProjection == null) {
            try {
                insertPonderacion(idMateria, idEmpleado);
                ponderacionProjection = ponderacionRepository.getPonderaciones(idMateria, idEmpleado);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Ponderacion ponderacion = modelMapper.map(ponderacionProjection, Ponderacion.class);

        ponderacion = getCompetencias(ponderacion);

        return ponderacion;
    }

    @Transactional
    public void insertPonderacion(Long idMateria, Long idEmpleado) {
        Long idPonderacion = ponderacionRepository.getNewIdPonderacion();

        ponderacionRepository.insertPonderacion(idPonderacion, idMateria, idEmpleado);
        List<Long> idCompetencias = ponderacionRepository.getCompetenciasByMateria(idMateria);

        for(Long idCompetencia: idCompetencias) {
            ponderacionRepository.insertCompetencias(idPonderacion, idCompetencia,(float) 100/idCompetencias.size());
        }

        List<Long> idCriterios = ponderacionRepository.getCriteriosByPonderacion(idPonderacion);
        List<CriteriosComEspProjection> idCriteriosPorcen = ponderacionRepository.getCriteriosCompEsp(idCriterios);
        // calcular porcentaje en funcion de criterio y comespecifica
        for(Long idCriterio: idCriterios) {
        	int repetidos = contarDuplicados(idCriterio, idCriteriosPorcen);
            if(repetidos != 0) {
                ponderacionRepository.insertCriterios(idPonderacion, idCriterio, (float) 100 / repetidos);
            }
        }
    }

    public Ponderacion getCompetencias(Ponderacion ponderacion) {
        List<CompetenciasEspecificas> competencias = ponderacionRepository.getCompetenciasEspecificas(ponderacion.getIdPonderacion()).stream().map(competencia -> modelMapper.map(competencia, CompetenciasEspecificas.class)).collect(Collectors.toList());

        for (CompetenciasEspecificas competencia : competencias) {
            List<CriterioList> criterios = ponderacionRepository.getCriteriosEvaluacion(ponderacion.getIdPonderacion(), competencia.getIdCompetencia()).stream().map(criterio -> modelMapper.map(criterio, CriterioList.class)).collect(Collectors.toList());

            competencia.setCriterios(criterios);
        }
        ponderacion.setCompetencias(competencias);

        return ponderacion;
    }

    @Transactional
    public void savePonderacion(PonderacionDto ponderacionDTO, Integer guardado) {
    	Ponderacion ponderacion = modelMapper.map(ponderacionDTO, Ponderacion.class);
        if (SaveType.TODO.value.equals(guardado)) {
            updateCompetencias(ponderacion);
            updateCriterios(ponderacion);
        } else if (SaveType.COMPETENCIAS.value.equals(guardado)) {
            updateCompetencias(ponderacion);
        } else if (SaveType.CRITERIOS.value.equals(guardado)) {
            updateCriterios(ponderacion);
        }
    }

    @Transactional
    public void updateCompetencias(Ponderacion ponderacion) {
        for (CompetenciasEspecificas competencia : ponderacion.getCompetencias()) {
            ponderacionRepository.updateCompetencias(competencia.getIdRelacionCompe(), competencia.getPesoCompe(), competencia.getPorcentajeCompe());
        }
    }

    @Transactional
    public void updateCriterios(Ponderacion ponderacion) {
        for (CompetenciasEspecificas competencia : ponderacion.getCompetencias()) {
            for (CriterioList criterio : competencia.getCriterios()) {
            	if(criterio.getIdTipoOperacion() != null) {
            		ponderacionRepository.updateCriterios(criterio.getIdRelacionCri(), criterio.getPesoCri(), criterio.getPorcentajeCri(), criterio.getIdTipoOperacion());
            	} else {
            		ponderacionRepository.updateCriterios(criterio.getIdRelacionCri(), criterio.getPesoCri(), criterio.getPorcentajeCri());
            	}
            }
        }
    }

    public List<DocentePonderacion> getDocentesPonderacion(Long codCentro, Long idMateria, Long idEmpleado) {
        LOG.debug("Obteniendo listado de docentes ponderaciones con materia ", idMateria);

        List<DocentePonderacionProjection> docentesProjection = ponderacionRepository.getDocentesPonderacion(codCentro, idMateria, idEmpleado);
        return docentesProjection.stream().map(docente -> modelMapper.map(docente, DocentePonderacion.class)).collect(Collectors.toList());
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
		
    @Transactional
    public Boolean compruebaPonderacionEditable(Long idPonderacion) {
    	Boolean actualizado = false;
    	
    	//Rescato el número de valoraciones de criterios asociadas a una ponderación
    	Integer numValoraciones = ponderacionRepository.getValoracionesCriteriosAsociadasPonderacion(idPonderacion);
    	
    	String mensaje = String.format("El número de valoraciones de criterios asociados a la ponderacion con identificador: %d, es de %d", 
				idPonderacion, numValoraciones);
		LOG.info(mensaje);
    	
    	//Si no existen valoraciones de criterios asociadas a la ponderación, hago que esta sea editable poniendole el campo L_EDITABLE a 'S'
    	if(numValoraciones == 0) {
    		ponderacionRepository.setPonderacionEditable(idPonderacion);
    		
    		actualizado = true;
    		
    		mensaje = String.format("Se ha modificado el campo L_EDITABLE de la ponderacion con identificador: %d, con el valor 'S'", 
    				idPonderacion, numValoraciones);
    		LOG.info(mensaje);
    	}
    	
    	return actualizado;
    }
    
    private Boolean existenCompetencias(Long idMateria) {
    	
    	//Intento rescatar las competencias de una materia, si existen entonces devolveré un true, en caso contrario un false
    	return ponderacionRepository.getCompetenciasByMateria(idMateria).isEmpty() ? false : true;
    	
    }
    

}
