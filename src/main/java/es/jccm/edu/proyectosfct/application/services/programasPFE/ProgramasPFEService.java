package es.jccm.edu.proyectosfct.application.services.programasPFE;

import es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.ProgramasPFERepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.historialPFERepository.HistorialPFERepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programasPFE.modulosPFE.ModulosFPERepository;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexosHistorial;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.HistorialProgramaPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.CombosProgramasPFEProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.CursosProgramasPFEProjection;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.projection.ListadoPFEProjection;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.IProgramasPFEService;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.DetallePFE.IProgamaPFE;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model.ListadoPFEDto;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.anexosPFE.IAnexosFPE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.InfoAnexos;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ModulosProgPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ProgramasPFE;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramasPFEService implements IProgramasPFEService {

    private static final Long ID_PERFIL_GESTOR = 11207L;
    private static final Long ID_PERFIL_CONSEJERIA_FCT = 13207L;
    private static final Long ID_PERFIL_CONSEJERIA_FCT_1 = 15207L;
    private static final Long ID_PERFIL_ALUMNADO = 5000L;

    @Autowired
    private ProgramasPFERepository programasPFERepository;
    
	@Autowired
	private HistorialPFERepository  historialPFERepository;
	
    @Autowired
    private IAnexosFPE iAnexosFPE;
    
    @Autowired
    private ModulosFPERepository modulosFPERepository;
    
	@Autowired
	private IRodalClient rodalClient;
    
	@Autowired
	private ModelMapper modelMapper;
	
    @Autowired
    private IProgamaPFE iProgamaPFE;


    @Override
    public List<CombosProgramasPFEProjection> getCombosProgramasPFE(String cbNombre,
                                                                    Long idCentro,
                                                                    Integer cAnno,
                                                                    Long idPerfil,
                                                                    Long idUsuario,
                                                                    Long idCentroCombo,
                                                                    Integer idProvincia,
                                                                    Long xUsuario,
                                                                    Long idCodigo) {

        List<CombosProgramasPFEProjection> listadoCombos;

        switch (cbNombre) {

            case "codigo":
                listadoCombos = getListadoCodigo(idCentro,cAnno,idPerfil,xUsuario);
                break;

            case "centro":
                listadoCombos = getListadoCentro(idCentro,cAnno,idPerfil,xUsuario, idCodigo);
                break;

            case "familias":
                listadoCombos = getListadoFamilias(idCentroCombo, xUsuario, idPerfil,idCentro,idProvincia);
                break;
            default:
                listadoCombos=null;
        }



        return listadoCombos;
    }



    @Override
    public List<CursosProgramasPFEProjection> getCursosProgramasPFE(Long idCentroCombo, Long idFamilia, Integer idProvincia, Long idUsuario, Long idPerfil, Long idCentro) {

        List<CursosProgramasPFEProjection> cursosProjection = null;


        if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {

            if (ID_PERFIL_GESTOR.equals(idPerfil)) {

                cursosProjection = programasPFERepository.getCursosProgramasPFEGestor(idCentroCombo, idFamilia,idUsuario, idPerfil,idCentro);



            } else {

                cursosProjection = programasPFERepository.getCursosProgramasPFEDelegacion(idCentroCombo, idFamilia, idProvincia);
            }

        } else {

            if (!ID_PERFIL_ALUMNADO.equals(idPerfil)) {

                cursosProjection = programasPFERepository.getCursosProgramasPFEDirectorTutor(idCentro, idFamilia);

            }


        }

        return cursosProjection;
    }

    public List<CombosProgramasPFEProjection> getListadoCodigo(Long idCentro,Integer cAnno, Long idPerfil,Long idUsuario) {
        return programasPFERepository.getCodigoProgramasPFE(idCentro,cAnno,idPerfil,idUsuario);
    }

    public List<CombosProgramasPFEProjection> getListadoCentro(Long idCentro,Integer cAnno,Long idPerfil,Long xUsuario, Long idCodigo) {
        return programasPFERepository.getCentrosProgramasPFE(idCentro,cAnno,idPerfil,xUsuario,idCodigo);
    }

    private List<CombosProgramasPFEProjection> getListadoFamilias(Long idCentroCombo, Long idUsuario, Long idPerfil, Long idCentro, Integer idProvincia) {


        List<CombosProgramasPFEProjection> listadoCombos = null;

        if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {

            if (ID_PERFIL_GESTOR.equals(idPerfil)) {


                listadoCombos = programasPFERepository.getFamiliasProgramasPFEGestor(idCentroCombo, idUsuario, idPerfil, idCentro);


            } else {

                listadoCombos = programasPFERepository.getFamiliasProgramasPFEDelegacion(idCentroCombo, idProvincia);
            }

        } else {

                listadoCombos = programasPFERepository.getFamiliasProgramasPFEDirectorTutor(idCentro);

            

        }

        return listadoCombos;
    }



	@Override
	public List<ElementoSelectProjection> getListadoCreadoresProgramasPFE(Long idCentroCombo, Long idFamilia, Integer idProvincia,
			Long idUsuario, Long idPerfil, Long idCentro, Long idCurso, Long idModalidad, Long xUsuario) {
		
		 List<ElementoSelectProjection> cursosProjection = null;


	        if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {

	            if (ID_PERFIL_GESTOR.equals(idPerfil)) {

	                //cursosProjection = programasPFERepository.getListadoCreadoresProgramasPFEgetCursosProgramasPFEGestor(idCentroCombo, idFamilia,idUsuario, idPerfil,idCentro);
	            	cursosProjection = programasPFERepository.getListadoCreadoresProgramasPFEGestor(idCentroCombo, xUsuario, idPerfil, idCentro, idModalidad, idCurso);



	            } else {

	                //cursosProjection = programasPFERepository.getListadoCreadoresProgramasPFEDelegacion(idCentroCombo, idFamilia, idProvincia);
	            }

	        } else {

	            if (!ID_PERFIL_ALUMNADO.equals(idPerfil)) {

	                cursosProjection = programasPFERepository.getListadoCreadoresProgramasPFEDirectorTutor(idCentro, idCurso, idModalidad);

	            }


	        }

	        return cursosProjection;
	}



	@Override
	public List<ListadoPFEDto> getListadoPFE(Long idCentro, Integer cAnno, Long idCurso, Long idPerfil,
			Long idCentroCombo, Long idProvincia, Long xUsuarioDelphos, Long idUsuCrea,
			Long idModalidad, Long idFamilia, Long idCodigo,Integer lgVigente, Integer esDirector, Integer idEstado,Integer reqAutorizacion) {
		
		
        List<ListadoPFEProjection> listadoAlumnosTutor = null;
		
		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {
			
		  if (ID_PERFIL_GESTOR.equals(idPerfil)) {
			  
			  listadoAlumnosTutor = programasPFERepository.getListadoPFEDelegacion(idCentro, cAnno,  idCurso, idPerfil, idCentroCombo, idUsuCrea, idModalidad, idFamilia, xUsuarioDelphos, idCodigo, lgVigente, idEstado,reqAutorizacion);
	
		 
			  
		  } else {
			 /* listadoAlumnosTutor = alumnadoLOFPRepository.findListadoAlumnosLOFPDelegacionProvincias(idTutorfctdual, 
																							       idCentro, 
																							       cAnno, 
																							       tipoEmpresa, 
																							       idEmpresa, 
																							       idOfertamatrig, 
																							       idUnidad,																							  
																							       idCentroCombo,
																							       idProvincia);*/
			  
		  }

			
		} else {
			
			if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {
			
				//listadoAlumnosTutor = alumnadoLOFPRepository.findListadoAlumnosLOFPAlumnado(idTutorfctdual, idCentro, cAnno, tipoEmpresa, idEmpresa, idOfertamatrig, idUnidad,idEmpleadoComunica);
			
			} else {
				
				listadoAlumnosTutor = programasPFERepository.getListadoPFE(idCentro, cAnno,  idCurso, idUsuCrea, idModalidad, idFamilia, idPerfil, xUsuarioDelphos,lgVigente,esDirector, idEstado, reqAutorizacion);
				
				
			}		
		}


		return Optional.ofNullable(listadoAlumnosTutor)
				.map(listado -> listado.stream()
						.map(entity -> modelMapper.map(entity, ListadoPFEDto.class)) // 🔹 Se cambia al nuevo DTO
						.collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}



	@Override
	public void deletePrograma(Long id)  throws RodalExceptionService, InsertarDocFault, IOException {
		
		deleteHistorial(id);		
		deleteAnexos(id);		
		deleteModulos(id);		
		deleteProgramaV(id);		
	}



	private void deleteProgramaV(Long id) {		
		Optional<ProgramasPFE> programa = programasPFERepository.findById(id);	
		
		if (programa.isPresent()) {
	        programasPFERepository.delete(programa.get());
	    } else {
	        // Handle not-found: throw exception or return a status
	        throw new ResourceNotFoundException("ProgramaPFE not found with id " + id);
	    }
		
	}



	private void deleteModulos(Long id) {
		List<ModulosProgPFE> modulos = modulosFPERepository.findByIdProgramaFPE(id);
		
		modulosFPERepository.deleteAll(modulos);
	}



	private void deleteAnexos(Long id) throws RodalExceptionService, InsertarDocFault, IOException {
		List<InfoAnexos> anexos = iAnexosFPE.getInfoAnexosAut(id);
		
		for (InfoAnexos anexo : anexos) {
			
			iAnexosFPE.deleteAnexo(anexo.getId());
			
		}
	}



	private void deleteHistorial(Long id) throws RodalExceptionService {		
		
		List<HistorialProgramaPFE> historico =  historialPFERepository.findByIdProgramaFPE(id);
		
		List<HistorialProgramaPFE> historicoRodal =  historialPFERepository.findByIdProgramaFPEAndIdAneRodalIsNotNull(id);
		
		if (historico != null && historico.size()>0) 
		{
			
			if (historicoRodal != null && historicoRodal.size()>0  && historicoRodal.get(0).getIdAneRodal() != null) {
				rodalClient.borrarDocumento(historicoRodal.get(0).getIdAneRodal());
		    }		
			
			historialPFERepository.deleteAll(historico);			
			
		}
	}

	public ProgramasPFE getPfeById(Long idProgPerFor) {
		return programasPFERepository.findById(idProgPerFor).orElse(null);
	}



	@Override
	public void postVistoBuenoPFE(List<Long> lista, Long xUsuarioDelphos,String cPerfil) {

		List<AutorizacionFlujoSiguiente> idFlujo = null;


		if ("FCT".equals(cPerfil)) {
		    idFlujo = iProgamaPFE.getSiguienteEstadoFlujoPFEDelegacion();
		} else {
			idFlujo = iProgamaPFE.getSiguienteEstadoFlujoPFE(Long.valueOf(161L), lista.get(0), Integer.valueOf(0), xUsuarioDelphos, Integer.valueOf(1), true);
		}


		for(Long registro : lista){			
			
			iProgamaPFE.createSiguienteEstadoFlujoPfePrograma(registro,idFlujo.get(0).getId(),xUsuarioDelphos);			
		
		}
		
	}



	@Override
	public void uploadAdjuntoPFE(Long xCentro, Long id, MultipartFile file)
			throws RodalExceptionService, InsertarDocFault, IOException {
		
		Optional<HistorialProgramaPFE> historialO = historialPFERepository.findById(id);
		
		HistorialProgramaPFE historial = historialO.orElse(null);
				
		Date date = new Date();

		registroConFicheroPFE(xCentro, id, file, historial, date);

		historialPFERepository.save(historial);
		
	}
	
	
	private void registroConFicheroPFE(Long xCentro, Long id, MultipartFile file, HistorialProgramaPFE historial, Date date)
			throws RodalExceptionService, InsertarDocFault, IOException {
		if (file == null) {
			// limpiamos relacionados
			historial.setIdAneIns(null);
			historial.setTxAneIns(null);
		} else {
			if (historial.getIdAneIns() == null) {
				RespuestaInsertarDoc respuesta = rodalClient.insertaDoc(
						file, "MFCT", "ANE_FCT_", id, -1L,
						xCentro, -1L, "-1", -1L, -1L
				);

				historial.setIdAneIns(respuesta.getIdDoc());
				historial.setTxAneIns(file.getOriginalFilename());
				historial.setFRegistroIns(date);
			} else {
				rodalClient.actualizaDoc(file, historial.getIdAneIns());
				historial.setTxAneIns(file.getOriginalFilename());
				historial.setFRegistroIns(date);
			}
		}
	}



	@Override
	public HistorialProgramaPFE getAdjuntoAnexoHistorial(Long idHistorial) {
		
		return historialPFERepository.findById(idHistorial).orElse(null);
		
	}

}

