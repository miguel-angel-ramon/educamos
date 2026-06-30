package es.jccm.edu.proyectosfct.application.services.desplazamiento;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionDesplazamientoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionDesplazamientoHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionesAnexosHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.DatosAutorizacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.BASE64DecodedMultipartFile;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.AutorizacionAnexoHistorialRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.ConvProgAluHorPeriodoFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.ConvProgAluHorTramoFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.AutorizacionDesplazamientoAnexoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.AutorizacionDesplazamientoHistorialRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.AutorizacionDesplazamientoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.AutorizacionFlujoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.TipoAutorizacionRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.ConvProgAluHorPeriodoFct;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.ConvProgAluHorTramoFct;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamiento;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionDesplazamientoHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujo;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexos;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexosHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.HistoricoFlujoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.AutorizacionFlujoSiguienteProjection;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.DatosAutorizacionProjection;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.DatosCabeceraAnexoVIIProjection;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.HistoricoFlujoAutorizacionProjection;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.ListadoAutorizacionDesplazamiento;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.ListadoAutorizacionesAnexos;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.TipoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.ListadoAutorizacionDesplazamientoProjection;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection.ListadoAutorizacionesAnexosProjection;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.PeriodoGasto;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.ports.in.desplazamiento.IDesplazamientoService;
import es.jccm.edu.proyectosfct.application.ports.in.gastos.IGastosService;
import es.jccm.edu.shared.application.ports.in.rodal.IRodalClient;
import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@Service
public class DesplazamientoService implements IDesplazamientoService {
	
	private static final Long ANEXO_VII = 3L;
	private static final Long ANEXO_XII = 4L;
	private static final String ABREV_DES = "AUT_DES";
	private static final String ABREV_EXT = "AUT_EXT";
	private static final Long ID_PERFIL_PROFESORADO = 2L;
	
	@Autowired
	private AutorizacionDesplazamientoRepository autorizacionDesplazamientoRepository;
	
	@Autowired
	private AutorizacionFlujoRepository autorizacionFlujoRepository;
	
	@Autowired
	private AutorizacionDesplazamientoHistorialRepository autorizacionDesplazamientoHistorialRepository;
	
	@Autowired
	private TipoAutorizacionRepository tipoAutorizacionRepository;
	
	@Autowired
	private AutorizacionDesplazamientoAnexoRepository autorizacionDesplazamientoAnexoRepository;
	
	@Autowired
	private AutorizacionAnexoHistorialRepository autorizacionAnexoHistorialRepository;

	@Autowired
	private AlumnadoRepository alumnadoRepository;
	
	@Autowired
	private ConvProgAluHorPeriodoFctRepository convProgAluHorPeriodoFctRepository;
	
	@Autowired
	private ConvProgAluHorTramoFctRepository convProgAluHorTramoFctRepository;
	
	@Autowired
	IGastosService gastosService;
	
    @Autowired
    private IRodalClient rodalClient;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<AutorizacionDesplazamiento> createAutorizacionDesplazamiento(List<AutorizacionDesplazamientoDto> listAutorizacionDesplazamientoDto, Long idPerfil, Long idUsuario){
		List<AutorizacionDesplazamiento> listAutDesOut = new ArrayList<>();

		for (int i = 0; i < listAutorizacionDesplazamientoDto.size(); i++) {
			AutorizacionDesplazamiento autDesIn = modelMapper.map(listAutorizacionDesplazamientoDto.get(i), AutorizacionDesplazamiento.class);
			Optional<AutorizacionDesplazamiento> autDesUpdate = autorizacionDesplazamientoRepository.findById(autDesIn.getId());
			
			if(autDesUpdate.isPresent()) {//Modo update
				
				autDesUpdate.get().setNumeroDias(autDesIn.getNumeroDias());
				autDesUpdate.get().setTxtMatricula(autDesIn.getTxtMatricula());
				autDesUpdate.get().setTxtItinerario(autDesIn.getTxtItinerario());
				autDesUpdate.get().setPorcetanjeKmDia(autDesIn.getPorcetanjeKmDia());
				autDesUpdate.get().setPorcetanjeTotalKm(autDesIn.getPorcetanjeTotalKm());
				autDesUpdate.get().setPeriodoGasto(autDesIn.getPeriodoGasto());

				autDesUpdate.get().setNuAut(autDesIn.getNuAut());

				if(autDesIn.getNuAut() == 2 || autDesIn.getNuAut() == 3){
					autDesUpdate.get().setNumeroDias2(autDesIn.getNumeroDias2());
					autDesUpdate.get().setTxtMatricula2(autDesIn.getTxtMatricula2());
					autDesUpdate.get().setTxtItinerario2(autDesIn.getTxtItinerario2());
					autDesUpdate.get().setPorcetanjeKmDia2(autDesIn.getPorcetanjeKmDia2());
					autDesUpdate.get().setPorcetanjeTotalKm2(autDesIn.getPorcetanjeTotalKm2());
				}

				if(autDesIn.getNuAut() == 3) {
					autDesUpdate.get().setNumeroDias3(autDesIn.getNumeroDias3());
					autDesUpdate.get().setTxtMatricula3(autDesIn.getTxtMatricula3());
					autDesUpdate.get().setTxtItinerario3(autDesIn.getTxtItinerario3());
					autDesUpdate.get().setPorcetanjeKmDia3(autDesIn.getPorcetanjeKmDia3());
					autDesUpdate.get().setPorcetanjeTotalKm3(autDesIn.getPorcetanjeTotalKm3());
				}
				
				listAutDesOut.add(autorizacionDesplazamientoRepository.save(autDesUpdate.get()));
			}else {//Modo nuevo 
				autDesIn = autorizacionDesplazamientoRepository.save(autDesIn);
				 
				AutorizacionFlujo flujo = autorizacionFlujoRepository.findByIdEstadoOrigenIsNullAndIdPerfilAndTipoAutorizacionId(idPerfil, tipoAutorizacionRepository.findByAbreviatura("AUT_DES").getId());
				
				AutorizacionDesplazamientoHistorial historial = new AutorizacionDesplazamientoHistorial();
				historial.setFechaRegistro(new Date());
				historial.setFlujo(flujo);
				historial.setAutorizacionDesplazamiento(autDesIn);
				historial.setIdUsuario(idUsuario);
				
				autorizacionDesplazamientoHistorialRepository.save(historial);
				
				listAutDesOut.add(autDesIn);
			}
			
		}
		
		return listAutDesOut;
	}
	
	@Override
	public AutorizacionDesplazamientoHistorial createSiguienteEstadoFlujoHistorial(AutorizacionDesplazamientoHistorialDto autDesDto, 
																				   Long idPerfil, 
																				   String abrev, 
																				   Long idUsuario){
		
		AutorizacionDesplazamientoHistorial historial = new AutorizacionDesplazamientoHistorial();
		historial.setAutorizacionDesplazamiento(modelMapper.map(autDesDto, AutorizacionDesplazamiento.class));
		historial.setAutorizacionDesplazamiento(autDesDto.getAutorizacionDesplazamiento());
		historial.setFechaRegistro(new Date());
		historial.setFlujo(autDesDto.getFlujo());
		historial.setIdUsuario(idUsuario);
		historial.setTxtObservaciones(autDesDto.getTxtObservaciones()); 
		
		return autorizacionDesplazamientoHistorialRepository.save(historial); 
		
	}

	@Override
	public void uploadFicheroAutorizacionDesplazamiento(Long idAutDes, Long idCentro, String idRodal, List<MultipartFile> files, Long xUsuarioDelphos) 
																		  throws RodalExceptionService, InsertarDocFault, IOException {
		for (MultipartFile file : files) {
			
			if (idRodal.equals("-1")) {
				rodalClient.insertaDoc(file, "MFCT", "AUTDES_", idAutDes, -1L, idCentro, -1L,"-1",-1L,-1L);
			} else {
				rodalClient.actualizaDoc(file, "MFCT", "AUTDES_", idAutDes,"-1",-1L,-1L);
			}			
		}
	}
	
	@Override
	public void deleteFicheroAutorizacionDesplazamiento(List<AutorizacionDesplazamientoDto> listAutorizacionDesplazamientoDto) throws RodalExceptionService {
		for (AutorizacionDesplazamientoDto autorizacionDesplazamiento : listAutorizacionDesplazamientoDto) {
			rodalClient.borrarDocumento(autorizacionDesplazamiento.getIdAutTutRodal(), "MFCT", "AUTDES_");
		}
	}

	@Override
	public AutorizacionDesplazamiento getAutorizacionDesplazamiento(Long idAutDes) {
		return autorizacionDesplazamientoRepository.findById(idAutDes).orElse(null);
	}
	
	@Override
	public AutorizacionDesplazamiento getAutorizacionDesplazamientoByIdRodal(String idRodal) {
		return autorizacionDesplazamientoRepository.findByIdAutTutRodal(idRodal).orElse(null);
	}

	@Override
	public void updateAutorizacionDesplazamiento(AutorizacionDesplazamiento autDesUpdate) {
		autorizacionDesplazamientoRepository.save(autDesUpdate);
	}

	@Override
	public List<AutorizacionFlujoSiguiente> getSiguienteFlujoAutorizacionDesplazamiento(Long idPerfil, Long idAut, String abrev) {		
		
		Long idTipo = tipoAutorizacionRepository.findByAbreviatura(abrev).getId();
		
		List<AutorizacionFlujoSiguienteProjection> estados = autorizacionFlujoRepository.findSiguienteAutorizacionFlujo(idPerfil, 
				                                                                                                       idAut, 
				                                                                                                       idTipo);
		
		return estados.stream().map(entity -> modelMapper.map(entity, AutorizacionFlujoSiguiente.class)).collect(Collectors.toList());
		
		//AutorizacionFlujo flujo = new AutorizacionFlujo();
		/*if(idEstadoOrigen == -1L) {
			flujo = autorizacionFlujoRepository.findByIdEstadoOrigenIsNullAndIdPerfilAndTipoAutorizacionId(idPerfil, idTipoAutorizacion);
		}else {
			flujo = autorizacionFlujoRepository.findByIdEstadoOrigenAndIdPerfilAndTipoAutorizacionId(idEstadoOrigen, idPerfil, idTipoAutorizacion);
		} */		
		
		//return flujo;
	}

	@Override
	public List<ElementoSelect> getCombosAutorizacionesDesplazamiento(String cbName, Integer anno, Long idPeriodo, 
																	  Long idCentro, Long idTutor, Long idFamilia, 
																	  Long idCurso, Long idPerfil, Long idUsuario) throws Exception {
		List<ElementoSelect> elements = new ArrayList<>();
		List<ElementoSelectProjection> otros = null;
		
		switch (cbName) {
			case "Familia":
				otros = autorizacionDesplazamientoRepository.getElementsFamilias(anno, idPeriodo, idCentro, idTutor, idUsuario);
				break;
			case "Periodo":
				List<PeriodoGasto> periodos = gastosService.getPeriodosGasto(anno, idCentro, idPerfil, idTutor, idUsuario);
				elements = periodos.stream().map( x -> {
					ElementoSelect e = new ElementoSelect();					
					LocalDate fIni = x.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate fFin = x.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();					
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");				
					e.setDescripcion(fIni.format(dtf)+" - "+fFin.format(dtf));
					e.setId(x.getId());
					return e;					
				}).collect(Collectors.toList());
				return elements;
			case "Tutor":
				otros = autorizacionDesplazamientoRepository.getElementsTutores(anno, idPeriodo, idCentro, idTutor, idUsuario);	
				break;
			case "Curso":
				otros =  autorizacionDesplazamientoRepository.getElementsCursos(anno, idPeriodo, idCentro, idTutor, idFamilia, idUsuario);
				break;
			case "Unidad":
				otros =  autorizacionDesplazamientoRepository.getElementsUnidades(anno, idPeriodo, idCentro, idTutor, idFamilia, idCurso, idUsuario);
				break;
			default:
				throw new Exception("El nombre del combo especificado no existe.");
		}
		return otros.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());		
	}

	@Override
	public List<ListadoAutorizacionDesplazamiento> getListadoAutorizacionesDesplazamiento(Integer anno, Long idPeriodo,
			Long idCentro, Long idTutor, Long idFamilia, Long idCurso, Long idUnidad, Long idPerfil, Long idUsuario) {		
		
		List<ListadoAutorizacionDesplazamientoProjection> listadp = autorizacionDesplazamientoRepository.getListadoAutorizacionesDesplazamiento(anno,
																																				idPeriodo,
																																				idCentro,
																																				idTutor,
																																				idFamilia,
																																				idCurso,
																																				idUnidad,
																																				ID_PERFIL_PROFESORADO.equals(idPerfil)? 0 : 1,
																																				idUsuario		);
		
		return listadp.stream().map(entity -> modelMapper.map(entity, ListadoAutorizacionDesplazamiento.class)).collect(Collectors.toList());		
		
	}
	
	@Override
	public Integer getEmancipado(Long idMatricula) {

		return autorizacionDesplazamientoRepository.getEmancipado(idMatricula);
	}
	
	@Override
	public List<Integer> getTutoresMatricula(Long idMatricula) {
		
		return autorizacionDesplazamientoRepository.getTutoresMatricula(idMatricula);
	}

	@Override
	public byte[] getAnexosXII(Long idTutor, Long idMatricula, Long idAutDes) throws FileNotFoundException, JRException, IOException {
		
		byte[] output = null;

       	 Resource resource = resourceLoader.getResource("classpath:reports/AnexoXII_Autorizacion.jrxml");
	     InputStream dbAsStream = resource.getInputStream(); 
	     JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);	     
	        	
	     String datosTutor = autorizacionDesplazamientoRepository.getCabeceraAnexoXII(idTutor,idMatricula,idAutDes);
	     
	     List<DatosAutorizacionProjection> datosProyeccion = autorizacionDesplazamientoRepository.getDatosAutDesplazamiento(idMatricula,idAutDes);
    		
	     List<DatosAutorizacionDto> datos = datosProyeccion.stream().map(entity -> modelMapper.map(entity, DatosAutorizacionDto.class)).collect(Collectors.toList());
	       
	     String firma = autorizacionDesplazamientoRepository.getFirma(idMatricula); 
	    		 
	     String nombretutor = autorizacionDesplazamientoRepository.getNombre(idTutor);  		 
	     
	     JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);      	  
	    	 
	     Map<String, Object> parameters = new HashMap<>();
	    	 
	     JasperPrint jasperPrint = new JasperPrint();
	    	 
	     parameters.put("cabecera", datosTutor);
	     parameters.put("descripcionFirma", firma);
	     parameters.put("nombre", nombretutor);
	     parameters.put("ds", dataSource);
	    	 
	     jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
	    	 
		 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		 JRExporter exporter = new JRPdfExporter();
		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		 exporter.exportReport();
		 output = outputStream.toByteArray();       
	
	     return output;			
	}

	@Override
	public List<ListadoAutorizacionesAnexos> getListadoAutorizacionesAnexos(Integer anno, 
																			String abrev,
																			Long idPeriodo, 
																			Long idCentro,
																		    Long idTutor, 
																		    Long idFamilia, 
																		    Long idCurso, 
																		    Long idUnidad,
																		    Long idUsuario,
																		    Long idPerfil,
																			Long idEstado,
																			Integer nuPeticion)
	{
	
		Long idTipo = tipoAutorizacionRepository.findByAbreviatura(abrev).getId();
		

		List<ListadoAutorizacionesAnexosProjection> listado = null;
		
		if (ANEXO_VII == idTipo) {
			
			listado = autorizacionDesplazamientoAnexoRepository.getListadoGastoAnexoVII(anno, 																												
																				       idPeriodo, 
																				       idTipo,
																				       idCentro,
																				       idTutor, 
																					   idFamilia, 
																					   idCurso, 
																					   idUnidad,
																					   idUsuario,
																					   idPerfil);			
		} else {
			
			listado = autorizacionDesplazamientoAnexoRepository.getListadoGastoAnexoXI(anno, 																												
																				       idTipo,
																				       idCentro,
																				       idTutor,
																					   idCurso, 
																					   idUnidad,
																					   idUsuario,
																					   idPerfil,
																					   idEstado,
			                                                                           nuPeticion);
																			
			
		}


		return listado.stream().map(entity -> modelMapper.map(entity, ListadoAutorizacionesAnexos.class)).collect(Collectors.toList());	
		
	}

	@Override
	public List<HistoricoFlujoAutorizacion> getHistoricoFlujo(Long id, String abrev) {
		
		Long idTipo = tipoAutorizacionRepository.findByAbreviatura(abrev).getId();
		
		List<HistoricoFlujoAutorizacionProjection> listadohistorico = null;
		
		if (ABREV_DES.equals(abrev)) {
			
			listadohistorico = autorizacionDesplazamientoHistorialRepository.getHistoricoFlujoAutorizacion(id, idTipo);
			
		} else if (ABREV_EXT.equals(abrev))  {
			
			listadohistorico = autorizacionDesplazamientoHistorialRepository.getHistoricoFlujoAutorizacionExtraordinario(id, idTipo);
			
		} else {
		
			listadohistorico = autorizacionDesplazamientoHistorialRepository.getHistoricoFlujoAutorizacionAnexos(id, idTipo);
		
		}		 
		
		return listadohistorico.stream().map(entity -> modelMapper.map(entity, HistoricoFlujoAutorizacion.class)).collect(Collectors.toList());
	}

	@Override
	public void generarAnexosVII(Long idPerfil, 
								 Long idCentro, 
								 Long xUsuarioDelphos, 
								 Long idPeriodoGasto,
								 String abrev, 
								 Integer annoAnexo, 
								 Long idTutor, 
								 Long idFamilia, 
								 Long idCurso, 
								 Long idUnidad) throws Exception {
		
		Centro centro = new Centro();
		centro.setId(idCentro);
		PeriodoGasto periodo = new PeriodoGasto();
		periodo.setId(idPeriodoGasto);
		
		generaAnexo(xUsuarioDelphos, 
					idPerfil, 
					abrev, 
					annoAnexo, 
					centro, 
					periodo, 
					idTutor,  
					idFamilia,
					idCurso, 
					idUnidad);
		
	}
	
	private void generaAnexo (Long xUsuarioDelphos, 
							  Long idPerfil, 
							  String abrev,
							  Integer annoAnexo,
							  Centro centro,
							  PeriodoGasto periodo,
							  Long idTutor,
							  Long idFamilia,
							  Long idCurso,
							  Long idUnidad) throws Exception  
	
	{
		String esLOFP = getEsLOFP(idCurso);

		byte[]  fileAnexo = exportReportAnexoAutorizacion(annoAnexo, 
				  									      centro.getId(), 
													      periodo.getId(), 
													      ANEXO_VII,
													      idTutor,
													      idFamilia,
													      idCurso,
													      idUnidad,
													      xUsuarioDelphos,
				                                          esLOFP);
		
	    Optional<TipoAutorizacion> tipo = tipoAutorizacionRepository.findById(ANEXO_VII);
	    
	    if (tipo.isPresent()) {
	    	
	    	AutorizacionesAnexos anexo = null;
	    	AutorizacionesAnexosHistorial historial = null;	
	    	
	    	anexo = autorizacionDesplazamientoAnexoRepository.findByPeriodoGastoIdAndIdAnnoAndTipoIdAndIdCentroAndIdTutorFctAndIdFamiliaAndIdCursoAndIdUnidad(periodo.getId(),
																																				    		  annoAnexo,
																																		    		          tipo.get().getId(),
																																				    		  centro.getId(),
																																				    		  idTutor,
																																				    		  idFamilia,
																																				    		  idCurso,
																																				    		  idUnidad);
	    	//if (anexo != null)  historial = autorizacionAnexoHistorialRepository.findByAnexoId(anexo.getId());
	    	//else anexo = new AutorizacionesAnexos();
	    	
	    	//if (historial.getId() == null)  {
	    		
	    	    if (anexo == null) {
	    	    	anexo = new AutorizacionesAnexos();
			    	anexo.setIdAnno(annoAnexo);
			    	anexo.setIdCentro(centro.getId());
			    	anexo.setIdCurso(idCurso);
			    	anexo.setIdFamilia(idFamilia);
			    	anexo.setIdUnidad(idUnidad);
			    	anexo.setIdTutorFct(idTutor);
			    	anexo.setTipo(tipo.get());
			    	anexo.setPeriodoGasto(periodo);
			    	anexo.setNuPeticion(1);
			    	autorizacionDesplazamientoAnexoRepository.save(anexo);     
	            }
		    	
		    	AutorizacionFlujo flujo = autorizacionFlujoRepository.findByIdEstadoOrigenIsNullAndIdPerfilAndTipoAutorizacionId(idPerfil, ANEXO_VII);
		    	
		    	historial = new AutorizacionesAnexosHistorial();
		    	historial.setFechaRegistro(new Date());
		    	historial.setIdUsuario(xUsuarioDelphos);
		    	historial.setAnexo(anexo);
		    	historial.setFlujo(flujo);
		    	historial.setNombreFichero((esLOFP.equals("S")?"AnexoXPlan":"AnexoVII") + ".pdf");
		    	autorizacionAnexoHistorialRepository.save(historial);
	    	//}    	
	    	
	    	
	    	BASE64DecodedMultipartFile file = new BASE64DecodedMultipartFile(fileAnexo,(esLOFP.equals("S")?"AnexoXPlan":"AnexoVII") + ".pdf");
	    	
			RespuestaInsertarDoc respuestaRodal = null ;
			Boolean actualizado = false;
			String entidad = "ANE_VII_";
	    	
			if (historial.getIdAneHisRodal() == null) {
				
				respuestaRodal = rodalClient.insertaDoc(file, 
										                "MFCT", 
										                entidad, 
										                historial.getId(),
										                annoAnexo.longValue(),
										                centro.getId(),
										                -1L,"-1L",-1L,-1L);
			} else {
				
				actualizado = rodalClient.actualizaDoc(file, 
										               "MFCT", 
										               entidad, 
										               historial.getId(),"-1",-1L,-1L);
			}   	
	    	
	    }
	}

	private String getEsLOFP(Long idCurso) {
    String esLOFP = autorizacionDesplazamientoRepository.getEsLOFP(idCurso);

	return esLOFP;
	}


	private byte[] exportReportAnexoAutorizacion (Integer cAnno,
												  Long idCentro,
												  Long idPeriodo,
												  Long idAnexo,
												  Long idTutor,
												  Long idFamilia,
												  Long idCurso,
												  Long idUnidad,
												  Long idUsuario,
												  String esLOFP) throws IOException, JRException

	{
		byte[] output = null; 
	    InputStream dbAsStream = null; 
	    Resource resource = null;
	    
//	    resource = resourceLoader.getResource("classpath:reports/anexoVII.jrxml");
		resource = resourceLoader.getResource( "classpath:reports/"+ (esLOFP.equals("S")?"anexoXPlan":"anexoVII")+".jrxml");
	    dbAsStream = resource.getInputStream();

	    Map<String, Object> parameters = new HashMap<>();  
	    DatosCabeceraAnexoVIIProjection parametros = autorizacionDesplazamientoAnexoRepository.getDatosCabecera(cAnno,
	    																										idCentro,
	    																										idPeriodo,
	    																										idTutor,
	    																										idFamilia,
	    																										idCurso,
	    																										idUnidad);
	    
	    List<ListadoAutorizacionDesplazamientoProjection> listado = autorizacionDesplazamientoRepository.getListadoAutorizacionesDesplazamientoAnexo(cAnno, 
		    																																		idPeriodo, 
		    																																		idCentro, 
		    																																		idTutor, 
		    																																		idFamilia, 
		    																																		idCurso, 
		    																																		idUnidad,
		    																																		idUsuario);
	    JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(listado);
	    
	    String autorizacion = (esLOFP.equals("S")?"D./Dña.":"D./Dª. ") + parametros.getDirector() + ", Director o Directora del " +
				(esLOFP.equals("S")?"Centro Docente ": "centro docente ") + parametros.getCentro() + " de " + parametros.getMunicipio() +
				(esLOFP.equals("S")?" provincia":" Provincia") + " de " + parametros.getProvincia() +
				", autoriza al alumnado que se relaciona a continuación a desplazarse en vehículo propio para la realización del " +
				(esLOFP.equals("S")?"Periodo de formación en empresa":"Módulo de Formación en Centros de Trabajo.");
	    
	    
     	parameters.put("centro", parametros.getCentro());
    	parameters.put("localidad", parametros.getLocalidad());  
    	parameters.put("provincia", parametros.getProvincia());  
    	parameters.put("anno", parametros.getAnno());
    	parameters.put("codigo", parametros.getCodigo());
    	parameters.put("periodo", parametros.getPeriodo());
    	parameters.put("director", parametros.getDirector());		
    	parameters.put("autorizacion", autorizacion);
    	parameters.put("tutor", parametros.getTutor());  	
    	parameters.put("familiaProfesional", parametros.getFamilia());
    	parameters.put("cicloFormativo", parametros.getCurso());
    	parameters.put("descripcionFirma", parametros.getFechaFirma());
		parameters.put("horas",listado.get(0).getNhoras());
        parameters.put("ds", datasource);        
        
        JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);      	 
    	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
        
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.exportReport();
        output = outputStream.toByteArray();  
       
        return output;	
	
	}
	
	@Override
	public AutorizacionesAnexosHistorial getAutorizacionAnexoHistorial(Long idEntidad, Integer per) {
		
		AutorizacionesAnexosHistorial anexo = autorizacionAnexoHistorialRepository.findById(idEntidad).orElse(null);
		
		if (anexo != null && per == 161) {
			anexo.setLgVisitado(1);		
			autorizacionAnexoHistorialRepository.save(anexo);
		}
		
		return anexo;	
	}

	@Override
	public void updateAutorizacionAnexoHistorial(AutorizacionesAnexosHistorial autDesUpdate) {
		autorizacionAnexoHistorialRepository.save(autDesUpdate);		
	}
	
	@Override
	public Alumno getDatosAlumno(Long idMatricula) {
		return modelMapper.map(alumnadoRepository.findAlumnoByIdMatricula(idMatricula), Alumno.class);
	}

	@Override
	public Integer getNumeroDiasPeriodoAlumno(Long idMatricula) {
		Integer numDias = 0;
		
		List<ConvProgAluHorPeriodoFct> periodos = convProgAluHorPeriodoFctRepository.findAllByIdMatricula(idMatricula);
		for (int i = 0; i < periodos.size(); i++) {
			List<ConvProgAluHorTramoFct> listTramos = convProgAluHorTramoFctRepository.findAllByPeriodoAlumnoHorarioId(periodos.get(i).getId());
			
			LocalDate fInicio = periodos.get(i).getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fFinPeriodo = periodos.get(i).getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
			
			for (int j = 0; j < listTramos.size(); j++) {
				while(fInicio.compareTo(fFinPeriodo)>=0) {
					if(fInicio.getDayOfWeek().getValue() == listTramos.get(j).getDiaSemana()){
						numDias++;
					}
				
					fInicio = fInicio.plusDays(1);
				}
			}
		}
		
		return numDias;
	}

	@Override
	public void updateFirmaAnexo(Long idHistorial, Long xUsuarioDelphos, Long idPerfil, String abrev) {
		Optional<AutorizacionesAnexosHistorial> anexo = autorizacionAnexoHistorialRepository.findById(idHistorial);
		
		if(anexo.isPresent()) 
		{
			AutorizacionesAnexosHistorial historial = anexo.get();
						
			String abrevD = "";
			
			if (abrev.equals("ANE_VII_" )) {
				abrevD = "ANE_VII";				
			} else if (abrev.equals("ANE_XI_" )) {
			    abrevD = "ANE_XI";
			} else if (abrev.equals("ANE_VIII_" )) {
			    abrevD = "ANE_VIII"; 
			} else {
				abrevD = abrev;				
			}
 			
			Long idTipo = tipoAutorizacionRepository.findByAbreviatura(abrevD).getId();
			
			List<AutorizacionFlujoSiguienteProjection> estados = autorizacionFlujoRepository.findSiguienteAutorizacionFlujo(idPerfil, 
															  															  historial.getAnexo().getId(), 															  															
																									                      idTipo);
			Optional<AutorizacionFlujo> flujo = autorizacionFlujoRepository.findById(estados.get(0).getId());			
			
			AutorizacionesAnexosHistorial historialNew = new AutorizacionesAnexosHistorial(); 
			
			historialNew.setAnexo(historial.getAnexo());
			historialNew.setFlujo(flujo.get());
			historialNew.setIdUsuario(xUsuarioDelphos);
			historialNew.setFechaRegistro(new Date());
			autorizacionAnexoHistorialRepository.save(historialNew);
			
		}
		
	}

	@Override
	public AutorizacionesAnexos getDetalleAnexo(Long idAnexo) {
		Optional<AutorizacionesAnexos> anexo =  autorizacionDesplazamientoAnexoRepository.findById(idAnexo);
		
		return anexo.orElse(new AutorizacionesAnexos());	
	}

	@Override
	public AutorizacionesAnexosHistorial createSiguienteEstadoFlujoHistorialAnexo(AutorizacionesAnexosHistorialDto historialAnexoDto, 
																				  Long idPerfil, 
																				  String abrev, 
																				  Long xUsuarioDelphos) {
		
		AutorizacionesAnexosHistorial historial = new AutorizacionesAnexosHistorial();
		historial.setAnexo(modelMapper.map(historialAnexoDto, AutorizacionesAnexos.class));
		historial.setAnexo(historialAnexoDto.getAutorizacionAnexo());
		historial.setFechaRegistro(new Date());
		historial.setFlujo(historialAnexoDto.getFlujo());
		historial.setIdUsuario(xUsuarioDelphos);
		historial.setObservaciones(historialAnexoDto.getObservaciones()); 
		
		return autorizacionAnexoHistorialRepository.save(historial); 
	}
	
	@Override
	@Transactional
	public void deleteAutorizacionDesplazamiento(Long idAutDes) {	
		autorizacionDesplazamientoRepository.findById(idAutDes).ifPresent(autDes -> {
		autorizacionDesplazamientoHistorialRepository.deleteByAutorizacionDesplazamiento(autDes);
		autorizacionDesplazamientoRepository.deleteById(idAutDes);
		});
	}

	@Override
	public List<String> getCountAnexosPendientesFirmaDirector(Integer cAnno, 
															  String abrev,	
															  Long idCentro,
															  Long idTutor,													 
															  Long idCurso, 
															  Long idUnidad) {
		
		Long idTipo = tipoAutorizacionRepository.findByAbreviatura(abrev).getId();
		
		return autorizacionDesplazamientoRepository.getCountAnexosPendientesFirmaDirector(cAnno,
																				         idCentro,
																				         idTutor,
																				         idCurso,
																				         idUnidad,
																				         idTipo);
	}

	@Override
	public List<ElementoSelect> getListadoCentrosAnexoXIFirmado(Integer anno,
																String abrev,
																Long idCentro,
																Long idUsuario,
																Long idPerfil)
	{

		Long idTipo = tipoAutorizacionRepository.findByAbreviatura(abrev).getId();

		List<ElementoSelectProjection> listado = null;

			listado = autorizacionDesplazamientoAnexoRepository.getListadoCentrosAnexoXIFirmado(anno,
					idTipo,
					idCentro,
					idUsuario,
					idPerfil);

		return listado.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());

	}

	@Override
	public String getEstadoAnexoAutorizacion(Integer anno, Long idCentro, Long idTutor, Long idUnidad, Long idCurso, Long idTipo, Long idPeriodo , Integer nuPeticion){
		String estado = autorizacionDesplazamientoAnexoRepository.getEstadoAnexoAutorizacion(anno, idCentro, idTutor, idUnidad, idCurso, idTipo, idPeriodo, nuPeticion);

		if(estado == null){
			estado = "Sin iniciar";
		}

		return estado;
	}


}
