package es.jccm.edu.proyectosfct.application.services.extraordinario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.AutorizacionDesplazamientoRepository;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.projections.AdjuntoAnexoXIProjection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model.AutorizacionExtraordinarioDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.extraordinario.model.AutorizacionExtraordinarioHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model.BASE64DecodedMultipartFile;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.AutorizacionAnexoHistorialRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.AutorizacionFlujoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento.TipoAutorizacionRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.extraordinario.AutorizacionExtraordinarioAnexoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.extraordinario.AutorizacionExtraordinarioHistorialRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.extraordinario.AutorizacionExtraordinarioRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoAux;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujo;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexos;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexosHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.TipoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.AutorizacionExtraordinario;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.AutorizacionExtraordinarioHistorial;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.entities.ListadoAutorizacionExtraordinario;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.projections.DatosCabeceraProjection;
import es.jccm.edu.proyectosfct.application.domain.extraordinario.projections.ListadoAutorizacionExtraordinarioProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.ports.in.extraordinario.IExtraordinarioService;
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
public class ExtraordinarioService implements IExtraordinarioService {
	

	private static final Long ANEXO_XI = 4L;
	private static final Long ID_PERFIL_PROFESORADO = 2L;
	
	@Autowired
	private AutorizacionExtraordinarioAnexoRepository autorizacionExtraordinarioAnexoRepository;
	
	@Autowired
	private AutorizacionExtraordinarioRepository autorizacionExtraordinarioRepository;
	
	@Autowired
	private AutorizacionAnexoHistorialRepository autorizacionAnexoHistorialRepository;
	
	@Autowired
	private AutorizacionFlujoRepository autorizacionFlujoRepository;
	
	@Autowired
	IGastosService gastosService;
	
    @Autowired
    private IRodalClient rodalClient;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ModelMapper modelMapper; 
	

	@Autowired
	private AlumnadoRepository alumnadoRepository;
	
	@Autowired
	private TipoAutorizacionRepository tipoAutorizacionRepository;
	
	@Autowired
	private AutorizacionExtraordinarioHistorialRepository autorizacionExtraordinarioHistorialRepository;

	@Autowired
	private AutorizacionDesplazamientoRepository autorizacionDesplazamientoRepository;

	@Override
	public List<AutorizacionExtraordinario> createAlumnoExtraordinario(List<AutorizacionExtraordinarioDto> listAutorizacionExtraordinarioDto, Long idPerfil, Long idUsuario){
		List<AutorizacionExtraordinario> listAutDesOut = new ArrayList<>();

		for (int i = 0; i < listAutorizacionExtraordinarioDto.size(); i++) {
			AutorizacionExtraordinario autExtIn = modelMapper.map(listAutorizacionExtraordinarioDto.get(i), AutorizacionExtraordinario.class);
			Optional<AutorizacionExtraordinario> autExtUpdate = autorizacionExtraordinarioRepository.findById(autExtIn.getId());
			
			if(autExtUpdate.isPresent()) {//Modo update
				
				autExtUpdate.get().setLgfindeext(autExtIn.getLgfindeext());
				autExtUpdate.get().setLgvacacext(autExtIn.getLgvacacext());
				autExtUpdate.get().setFhIniCalenExt(autExtIn.getFhIniCalenExt());
				autExtUpdate.get().setFhFinCalenExt(autExtIn.getFhFinCalenExt());
				autExtUpdate.get().setFhIniCalenFue(autExtIn.getFhIniCalenFue());
				autExtUpdate.get().setFhFinCalenFue(autExtIn.getFhFinCalenFue());
				autExtUpdate.get().setTxhorarext(autExtIn.getTxhorarext());
				autExtUpdate.get().setTxhorarfue(autExtIn.getTxhorarfue());
				//autExtUpdate.get().setNuPeticion(autExtIn.getNuPeticion());
							
				listAutDesOut.add(autorizacionExtraordinarioRepository.save(autExtUpdate.get()));
			}else {//Modo nuevo 
				
				//Creamos la autorización en bbdd
				autExtIn = autorizacionExtraordinarioRepository.save(autExtIn);
				 
				//Con la primera consulta buscamos en la tabla FCT_AUTORIZACIONES_FLUJO que autorizacion tiene si estado en nulo, un perfil y un tipo de autorizacion en concreto
				//Para saber que tipo de autorizacion estams buscando hacemos otra consulta, en este caso buscamos la que corresponda a AUT_EXT que sería un 2
				AutorizacionFlujo flujo = autorizacionFlujoRepository.findByIdEstadoOrigenIsNullAndIdPerfilAndTipoAutorizacionId(idPerfil, tipoAutorizacionRepository.findByAbreviatura("AUT_EXT").getId());
				
				//Creamos el objeto historial para relacionar la autorizacion creada con el flujo que tiene actualmente
				AutorizacionExtraordinarioHistorial historial = new AutorizacionExtraordinarioHistorial();
				historial.setFechaRegistro(new Date());
				historial.setFlujo(flujo);
				historial.setAutorizacionExtraordinario(autExtIn);
				historial.setIdUsuario(idUsuario);
				
				//Creamos la relacion en la tabla FCT_AUTEXTFUE_HISTORIAL
				autorizacionExtraordinarioHistorialRepository.save(historial);
				
				listAutDesOut.add(autExtIn);
			}
			
		}
		
		return listAutDesOut;
	}
	
	

	@Override
	public List<ElementoSelect> getComboAlumnos(Integer anno, Long idCentro, Long idTutor, Long idCurso, Long idUnidad, Integer nuPeticion, Long idUsuario) {
//		List<ElementoSelect> elements = new ArrayList<>();
		List<ElementoSelectProjection> otros = null;
		
		otros = autorizacionExtraordinarioRepository.getElementsAlumnos(anno, idCentro, idTutor, idCurso, idUnidad, nuPeticion ,idUsuario);
		
		return otros.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}
	


	@Override
	public AutorizacionExtraordinario getAutorizacionExtraordinario(Long idAutExt) {
		return autorizacionExtraordinarioRepository.findById(idAutExt).orElse(null);
	}
	
	

	@Override
	public Alumno getDatosAlumnoExtra(Long idMatricula) {
		return modelMapper.map(alumnadoRepository.findAlumnoByIdMatricula(idMatricula), Alumno.class);
	}
	

	@Override
	public AlumnoAux getDateScheduleAlumnoExtra(Long idMatricula) {
		
		AlumnoAux aux =  modelMapper.map(alumnadoRepository.getDateAndSchedule(idMatricula), AlumnoAux.class);
		
		return aux;
	}

	
	@Override
	public List<ElementoSelect> getCombosAutorizacionesExtraordinario(String cbName, Integer anno, 
																	  Long idCentro, Long idTutor, Long idFamilia, 
																	  Long idCurso, Long idUnidad, Long idPerfil, Long idUsuario) throws Exception {
		List<ElementoSelect> elements = new ArrayList<>();
		List<ElementoSelectProjection> otros = null;
		
		switch (cbName) {
		  /*case "Familia":
				otros = autorizacionDesplazamientoRepository.getElementsFamilias(anno, idCentro, idTutor);
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
				return elements; */
			case "Tutor":
				otros = autorizacionExtraordinarioRepository.getElementsTutores(anno, idCentro, idUsuario);	
				break;
			case "Curso":
				otros =  autorizacionExtraordinarioRepository.getElementsCursos(anno, idCentro, idTutor, idUsuario);
				break;
			case "Unidad":
				otros =  autorizacionExtraordinarioRepository.getElementsUnidades(anno, idCentro, idTutor, idCurso, idUsuario);
				break;
			case "Peticion":
				otros = autorizacionExtraordinarioRepository.getElementsPeticiones(anno, idCentro, idTutor, idCurso, idUnidad);
				break;
			default:
				throw new Exception("El nombre del combo especificado no existe.");
		}
		return otros.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ListadoAutorizacionExtraordinario> getListadoAutorizacionesExtraordinario(Integer anno,
			Long idCentro, Long idTutor, Long idCurso, Long Unidad, Long idPerfil, Integer nuPeticion, Long idUsuario) {
		
		List<ListadoAutorizacionExtraordinarioProjection> listProjection =  autorizacionExtraordinarioRepository.getListadoAutorizacionesExtraordinario(
																			anno, 
																			idCentro,
																			idTutor,
																			idCurso,
																			Unidad,
																			ID_PERFIL_PROFESORADO.equals(idPerfil)?0:1,
																			nuPeticion,
																			idUsuario);


		return listProjection.stream().map(entity -> modelMapper.map(entity, ListadoAutorizacionExtraordinario.class)).collect(Collectors.toList());
	}


	@Override
	public AutorizacionExtraordinarioHistorial createSiguienteEstadoFlujoHistorial(AutorizacionExtraordinarioHistorialDto autorizacionExtraordinarioDto, 
																				  Long idPerfil, 
																				  String abrev,
																				  Long xUsuarioDelphos) {
		AutorizacionExtraordinarioHistorial historial = new AutorizacionExtraordinarioHistorial();
		historial.setAutorizacionExtraordinario(modelMapper.map(autorizacionExtraordinarioDto, AutorizacionExtraordinario.class));
		historial.setAutorizacionExtraordinario(autorizacionExtraordinarioDto.getAutorizacionExtraordinario());
		historial.setFechaRegistro(new Date());
		historial.setFlujo(autorizacionExtraordinarioDto.getFlujo());
		historial.setIdUsuario(xUsuarioDelphos);
		historial.setTxtObservaciones(autorizacionExtraordinarioDto.getTxtObservaciones()); 
		
		return autorizacionExtraordinarioHistorialRepository.save(historial);
	}
	
	
	@Override
	public void generarAnexosXI(Long idPerfil, 
								 Long idCentro, 
								 Long xUsuarioDelphos,
								 String abrev, 
								 Integer annoAnexo, 
								 Long idTutor,
								 Long idCurso,
								 Long idUnidad,
								 Integer nuPeticion,
								 String justificacion, 
								 String control, 
								 String costes) throws Exception {
		
		Centro centro = new Centro();
		centro.setId(idCentro);
		
		generaAnexo(xUsuarioDelphos, 
					idPerfil, 
					abrev, 
					annoAnexo,
					idTutor,
					idCurso,
					idUnidad,
					nuPeticion,
					centro, 
					justificacion,  
					control,
					costes);
		
	}
	
	private void generaAnexo (Long xUsuarioDelphos, 
							  Long idPerfil, 
							  String abrev,
							  Integer annoAnexo,
							  Long idTutor, 
							  Long idCurso, 
							  Long idUnidad,
							  Integer nuPeticion,
							  Centro centro,
							  String justificacion,
							  String control,
							  String costes) throws Exception  
	
	{
			
		byte[]  fileAnexo = exportReportAnexoAutorizacion(annoAnexo, 
				  									      centro.getId(), 
				  									      idTutor,
				  									      idCurso,
				  									      idUnidad,
													      ANEXO_XI,
													      justificacion,
													      control,
													      costes,
													      xUsuarioDelphos,
										                  nuPeticion);
		
	    Optional<TipoAutorizacion> tipo = tipoAutorizacionRepository.findById(ANEXO_XI);
	    
	    if (tipo.isPresent()) {
	    	
	    	AutorizacionesAnexos anexo = null;
	    	AutorizacionesAnexosHistorial historial = null;	


			if (nuPeticion == null  || nuPeticion == -1) {
				nuPeticion = 1;
			}

			anexo = autorizacionExtraordinarioAnexoRepository
					.findByIdAnnoAndTipoIdAndIdCentroAndIdTutorFctAndIdCursoAndIdUnidadAndNuPeticion(
							annoAnexo, tipo.get().getId(), centro.getId(), idTutor, idCurso, idUnidad, nuPeticion);
	    	
	    	 if (anexo == null) {
	    	    	anexo = new AutorizacionesAnexos();
	    	    	anexo.setIdAnno(annoAnexo);
			    	anexo.setIdCentro(centro.getId());
			    	anexo.setIdTutorFct(idTutor);
			    	anexo.setIdCurso(idCurso);
			    	anexo.setIdUnidad(idUnidad);
			    	anexo.setTxtJustificacion(justificacion);
			    	anexo.setTxtControl(control);
			    	anexo.setTxtCostes(costes);
			    	anexo.setTipo(tipo.get());
				 	anexo.setNuPeticion(nuPeticion);
			    	autorizacionExtraordinarioAnexoRepository.save(anexo);

					// si la peticion no es la primera y el anexo es null: creacion de nuevo anexo desde el listado sin solicitudes
				 	if (nuPeticion > 1 ) {
					 	return;
				 	}


	            } else {
	            	anexo.setTxtJustificacion(justificacion);
			    	anexo.setTxtControl(control);
			    	anexo.setTxtCostes(costes);
	            	autorizacionExtraordinarioAnexoRepository.save(anexo);	            	
	            }	    	
		    	
		    	AutorizacionFlujo flujo = autorizacionFlujoRepository.findByIdEstadoOrigenIsNullAndIdPerfilAndTipoAutorizacionId(idPerfil, ANEXO_XI);
		    	
		    	historial = new AutorizacionesAnexosHistorial();
		    	historial.setFechaRegistro(new Date());
		    	historial.setIdUsuario(xUsuarioDelphos);
		    	historial.setAnexo(anexo);
		    	historial.setFlujo(flujo);
		    	historial.setNombreFichero("AnexoXI.pdf");
		    	autorizacionAnexoHistorialRepository.save(historial);
	    	//}    	
	    	
	    	
	    	BASE64DecodedMultipartFile file = new BASE64DecodedMultipartFile(fileAnexo,"AnexoXI.pdf");
	    	
			RespuestaInsertarDoc respuestaRodal = null ;
//			Boolean actualizado = false;
			String entidad = "ANE_XI_";
	    	
			if (historial.getIdAneHisRodal() == null) {
				
				respuestaRodal = rodalClient.insertaDoc(file, 
										                "MFCT", 
										                entidad, 
										                historial.getId(),
										                annoAnexo.longValue(),
										                centro.getId(),
										                -1L,"-1",-1L,-1L);
			} else {
				
				rodalClient.actualizaDoc(file, 
			               "MFCT", 
			               entidad, 
			               historial.getId(),"-1",-1L,-1L);
			}   	
	    	
	    }
	}
	
	
	private byte[] exportReportAnexoAutorizacion(Integer cAnno, 
												 Long idCentro, 
												 Long idTutor,
												 Long idCurso,
												 Long idUnidad,
												 Long idAnexo,
												 String justificacion, 
												 String control, 
												 String costes,
												 Long idUsuario,
												 Integer nuPeticion) throws IOException, JRException

	{
		String esAnexoLOFP = getEsAnexoLOFP(idCurso);

		byte[] output = null;
		InputStream dbAsStream = null;
		Resource resource = null;
		String centro = "";
		String localidad = "";
		String tutor = "";
		String curso = "";				

		resource = resourceLoader.getResource("classpath:reports/"+ (esAnexoLOFP.equals("S")?"anexoXIPlan":"anexoXI")+".jrxml");
		dbAsStream = resource.getInputStream();

		Map<String, Object> parameters = new HashMap<>();
		List<DatosCabeceraProjection> cabecera = autorizacionExtraordinarioAnexoRepository.getDatosCabecera(idCentro,idTutor,idCurso,idUnidad);
		
		if (cabecera !=null && cabecera.size()>0 ) {			
			centro = cabecera.get(0).getCentro();
			localidad = cabecera.get(0).getLocalidad();
			tutor = cabecera.get(0).getTutor();
			curso = cabecera.get(0).getCurso();			
		}
		//String localidad = autorizacionExtraordinarioAnexoRepository.getDatosCabeceraLocalidad(idCentro);
		String director = autorizacionExtraordinarioAnexoRepository.getDirector(idCentro);

		List<ListadoAutorizacionExtraordinarioProjection> listado = autorizacionExtraordinarioRepository
				.getListadoAutorizacionesExtraordinario(cAnno, idCentro, idTutor, idCurso, idUnidad, -1, nuPeticion, idUsuario);

		JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(listado);
		
		String firma = autorizacionExtraordinarioAnexoRepository.getFirma(idCentro); 

		parameters.put("centro", centro);
		parameters.put("localidad", localidad);
		parameters.put("tutor", tutor);
		parameters.put("curso", curso);
		parameters.put("justificacion", justificacion);
		parameters.put("control", control);
		parameters.put("director", director);	
		parameters.put("costes", costes);
		parameters.put("descripcionFirma", firma);
		parameters.put("ds", datasource);

		JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		output = outputStream.toByteArray();

		return output;

	}
	
	@Override
	@Transactional
	public void deleteAutorizacionDesplazamiento(Long idAutExtPro) {
		autorizacionExtraordinarioRepository.findById(idAutExtPro).ifPresent(autExt -> {
		autorizacionExtraordinarioHistorialRepository.deleteByAutorizacionExtraordinario(autExt);
		autorizacionExtraordinarioRepository.deleteById(idAutExtPro);	
		});
	}


	@Override
	public void uploadAdjunto(Long id, MultipartFile file, boolean esAuto) throws RodalExceptionService, InsertarDocFault, IOException {
		Optional<AutorizacionesAnexosHistorial> autorizacionO = autorizacionAnexoHistorialRepository.findById(id);
		AutorizacionesAnexosHistorial autorizacion = autorizacionO.get();
		autorizacion.setLgVisitado(0);
		
		Date date = new Date();

		if (esAuto) {
			// fichero de autorizacion automatico para centros en anexo XI
			registroSinFichero(id, file, autorizacion, date);
		} else {
			// adjunto
			registroConFichero(id, file, autorizacion, date);
		}

		autorizacionAnexoHistorialRepository.save(autorizacion);
	}



	private void registroConFichero(Long id, MultipartFile file, AutorizacionesAnexosHistorial autorizacion, Date date)
			throws RodalExceptionService, InsertarDocFault, IOException {
		if (file == null) {
			// limpiamos relacionados
			autorizacion.setIdAneFctRodal(null);
			autorizacion.setNombreAnexo(null);
		} else {
			if (autorizacion.getIdAneFctRodal() == null) {
				RespuestaInsertarDoc respuesta = rodalClient.insertaDoc(
						file, "MFCT", "ANE_FCT_", id, -1L,
						autorizacion.getAnexo().getIdCentro(), -1L, "-1", -1L, -1L
				);

				autorizacion.setIdAneFctRodal(respuesta.getIdDoc());
				autorizacion.setNombreAnexo(file.getOriginalFilename());
				autorizacion.setFechaRegistroAnexo(date);
			} else {
				rodalClient.actualizaDoc(file, autorizacion.getIdAneFctRodal());
				autorizacion.setNombreAnexo(file.getOriginalFilename());
				autorizacion.setFechaRegistroAnexo(date);
			}
		}
	}



	private void registroSinFichero(Long id, MultipartFile file, AutorizacionesAnexosHistorial autorizacion, Date date)
			throws RodalExceptionService, InsertarDocFault, IOException {
		if (file == null) {
			// limpiamos relacionados
			autorizacion.setIdAnefctRodalAuto(null);
			autorizacion.setTxAnefctFicheroAuto(null);
		} else {
			if (autorizacion.getIdAnefctRodalAuto() == null) {
				RespuestaInsertarDoc respuesta = rodalClient.insertaDoc(
						file, "MFCT", "ANE_FCT_", id, -1L,
						autorizacion.getAnexo().getIdCentro(), -1L, "-1", -1L, -1L
				);

				autorizacion.setIdAnefctRodalAuto(respuesta.getIdDoc());
				autorizacion.setTxAnefctFicheroAuto(file.getOriginalFilename());
				autorizacion.setFhRegistroAnexoAuto(date);
			} else {
				rodalClient.actualizaDoc(file, autorizacion.getIdAnefctRodalAuto());
				autorizacion.setTxAnefctFicheroAuto(file.getOriginalFilename());
				autorizacion.setFhRegistroAnexoAuto(date);
			}
		}
	}


	public String getEsAnexoLOFP(Long idCurso) {
		String esAnexoLofp = autorizacionDesplazamientoRepository.getEsLOFP(idCurso);

		return esAnexoLofp;
	}

	public byte[] generarAutorizacionCentroAnexoXi(Long idAutAnexo, String abrevEstadoSiguiente) {
		try {
			byte[] output = null;
			InputStream dbAsStream = null;

			Resource resouce = resourceLoader.getResource("classpath:reports/adjuntoAnexoXI.jrxml");
			dbAsStream = resouce.getInputStream();

			Map<String, Object> parameters = new HashMap<>();
			AdjuntoAnexoXIProjection datosAdjuntoAnexoXI = getDatosAdjuntoAnexoXI(idAutAnexo);

			if (datosAdjuntoAnexoXI != null) {
				String nombreDelProv = null;
				switch (datosAdjuntoAnexoXI.getProvincia()) {
					case "Albacete":
						nombreDelProv = "VºBº Diego Pérez Gonzaléz";
						break;
					case "Ciudad Real":
						nombreDelProv = "VºBº José Jesús Caro Sierra";
						break;
					case "Cuenca":
						nombreDelProv = "VºBº Gustavo Martínez Morales";
						break;
					case "Guadalajara":
						nombreDelProv = "VºBº Angel Francisco Fernández-Montes Gonzalez";
						break;
					case "Toledo":
						nombreDelProv = "VºBº José Gutierréz Muñoz";
						break;
					default:
						nombreDelProv = "VºBº _____________________";	
				}
				parameters.put("centro", datosAdjuntoAnexoXI.getCentro().toUpperCase());
				parameters.put("provincia", datosAdjuntoAnexoXI.getProvincia());
				parameters.put("nombreCurso", datosAdjuntoAnexoXI.getNombreCurso().toUpperCase());
				parameters.put("fecha", datosAdjuntoAnexoXI.getFecha());
				parameters.put("listadoAlumnos", datosAdjuntoAnexoXI.getAlumnos() + ".");
				parameters.put("estadoSolicitud", (abrevEstadoSiguiente.equals("VCF")
						? "Se autoriza que puedan realizar Formación en Centros de Trabajo (F.C.T) /"
						: "NO se autoriza que puedan realizar Formación en Centros de Trabajo (F.C.T.) /")
						+ "PERIODO DE FORMACIÓN EN EMPRESA");
				parameters.put("nombreDelProv", nombreDelProv);
			}

			JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();
			output = outputStream.toByteArray();

			return output;



		} catch (IOException e) {
			throw new RuntimeException("Error al cargar el recurso JRXML", e);
		} catch (JRException e) {
			throw new RuntimeException("Error al compilar o generar el reporte", e);
		}
	}

	AdjuntoAnexoXIProjection getDatosAdjuntoAnexoXI(Long idAutAnexo) {
		return	autorizacionExtraordinarioAnexoRepository.findDatosAdjuntoAnexo(idAutAnexo);
	}

}
