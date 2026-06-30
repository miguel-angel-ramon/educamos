package es.jccm.edu.proyectosfct.application.services.programas;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosProgramasFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.datosprograma.DatosProgramaFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.programas.ProgramaFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual.TutoresFctDualRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.DatosCabeceraEvaluacionProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.convenios.projection.DatosCentroConvenioProjection;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasFct;
import es.jccm.edu.proyectosfct.application.domain.datosprograma.DatosProgramaFct;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ListadoAnexoIAlumnadoFct;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ListadoProgramaFct;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ProgramaFct;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.DatosCabeceraAnexoIAlumnadoProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.EmpleadoProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.FamiliaProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ListadoAnexoIAlumnadoProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ListadoProgramaFctProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.OfertaMatriculaGenericaProjection;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Empleado;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.OfertaMatriculaGenerico;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.ports.in.alumnado.IAlumnadoService;
import es.jccm.edu.proyectosfct.application.ports.in.datosprograma.IDatosProgramaFctService;
import es.jccm.edu.proyectosfct.application.ports.in.programas.IProgramasFctService;
import es.jccm.edu.proyectosfct.application.services.Utiles;
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
public class ProgramaFctService implements IProgramasFctService  {
	
	private static final Long ID_PERFIL_DELEGACION = 13207L;

	private static final Long ID_PERFIL_DELEGACION_1 = 15207L;
	
	@Autowired
	private ProgramaFctRepository programaFctRepository;	
	
	@Autowired
	private ConveniosProgramasFctRepository convProgRepository;
	
	@Autowired
	private TutoresFctDualRepository tutoresFctDualRepository;	
	
	@Autowired
	private DatosProgramaFctRepository datosProgramaFctRepository;
	
	@Autowired IDatosProgramaFctService datosProgramasService;
	
	@Autowired
	private ConveniosFctRepository conveniosFctRepository;
	
	@Autowired
	private IAlumnadoService alumnado;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Override
	public Empleado getDatosTutor(Long idCentro, Long idEmpleado) {
		
		EmpleadoProjection empleadoProjection = programaFctRepository.getDatosTutor(idCentro,idEmpleado);
		
		return modelMapper.map(empleadoProjection, Empleado.class);
		
	}
	
	
	@Override
	public List<Familia> getAllFamiliasCentro(Long idCentro, int cAnno) {		
		
		List<FamiliaProjection> familiaProjection = programaFctRepository.allFamiliasCentro(idCentro,cAnno); 
		
		return familiaProjection.stream()
				.map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());		

	}
	
	
	@Override
	public List<Familia> getAllFamiliasCentroTutor( int cAnno, Long idTutor, Long idCentro) {
		
		List<FamiliaProjection> familiaProjection = programaFctRepository.allFamiliasCentroTutor(cAnno, idTutor, idCentro); 
		
		return familiaProjection.stream()
				.map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<Familia> getAllFamiliasDelegacion(Long idUsuario, Long idPerfil, Long idCentroProvincia, int cAnno) {

        List<FamiliaProjection> familiaProjection = programaFctRepository.allFamiliasDelegacion(idUsuario,idPerfil,idCentroProvincia,cAnno); 
		
		return familiaProjection.stream()
				.map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());	
	}
	
	
	@Override
	public List<OfertaMatriculaGenerico> getAllCursosFamiliaCentro(Long idCentro, int cAnno, Long idFamilia) {
		
		List<OfertaMatriculaGenericaProjection> ofertaProjection = programaFctRepository.allCursosFamiliaCentro(idCentro,cAnno,idFamilia);
		
		return ofertaProjection.stream()
				.map(entity -> modelMapper.map(entity, OfertaMatriculaGenerico.class)).collect(Collectors.toList());
		
	}
	
	@Override
	public List<OfertaMatriculaGenerico> getAllCursosFamiliaTutor(Long idTutor, int cAnno, Long idFamilia, Long idCentro) {
		
		List<OfertaMatriculaGenericaProjection> ofertaProjection = programaFctRepository.allCursosFamiliaTutor(idTutor,cAnno,idFamilia, idCentro);
		
		return ofertaProjection.stream()
				.map(entity -> modelMapper.map(entity, OfertaMatriculaGenerico.class)).collect(Collectors.toList());
		
	}
	
	@Override
	public List<Empleado> getTutoresCentro(Long idCentro) {
		
		List<EmpleadoProjection> res = programaFctRepository.getTutoresCentro(idCentro);
		
		return res.stream()
				.map(entity -> modelMapper.map(entity, Empleado.class)).collect(Collectors.toList());
		
	}
	
	@Override
	public List<ListadoProgramaFct> getAllProgramasCentroTutor(Long idCentro, 
			                                                   Long idTutor, 
			                                                   Long idFamilia, 
			                                                   Long idOferta, 
			                                                   Long idConvenio,
			                                                   Long idAnno) {
		
		List<ListadoProgramaFctProjection> programaProjection = programaFctRepository.getAllCentroTutorFamiliaOferta(idCentro, 
																												     idTutor, 
																												     idFamilia, 
																												     idOferta, 
																												     idConvenio,
																												     idAnno); 
		
		return programaProjection.stream().map(entity -> modelMapper.map(entity, ListadoProgramaFct.class)).collect(Collectors.toList());
	}	
	
	@Override
	public List<ProgramaFct> getAllProgramasDtoCentroTutor(Long idCentro, Integer cAnno, Long idTutor) {
		
		List<ProgramaFct> programaFct = null; 			
		
		if (idTutor == -1L) programaFct =  programaFctRepository.getProgramasCentro(idCentro,cAnno);
		else programaFct = programaFctRepository.getProgramasCentroTutor(idCentro,idTutor,cAnno);
		
//		return programaFct.stream()
//					.map(entity -> modelMapper.map(entity, ProgramaFct.class)).collect(Collectors.toList());
		return programaFct;
		
	}
	
	
	
	@Override
	public Page<ProgramaFct> getAllProgramasCentroTutorName(Long idCentro, String sName, int page, Long idTutor) {

		Pageable paging = PageRequest.of(page, 10, Sort.by("ds_programa"));		
		
		if (idTutor == null) return  programaFctRepository.getBuscaNombre(idCentro, sName, paging);
		else return  programaFctRepository.getBuscaNombreTutor(idCentro, idTutor, sName,paging);		
		
	}
	
	
	@Override
	public ProgramaFct getProgramaId(Long id) {
				
		Optional<ProgramaFct> programaFct = programaFctRepository.findById(id);		
		
		return programaFct.isPresent() ? modelMapper.map(programaFct.get(), ProgramaFct.class) : null;
		
	}
	
	@Override
	public OfertaMatriculaGenerico getByOfertaMatriculaGenericoId(Long idOfertaMatriculaGenerico) {
		
        Optional<OfertaMatriculaGenericaProjection> oferta = programaFctRepository.getByOfertaMatriculaGenericoId(idOfertaMatriculaGenerico);
		
        return oferta.isPresent() ? modelMapper.map(oferta.get(), OfertaMatriculaGenerico.class) : null;    
  	
	}
	
	@Override
	public Familia getByFamiliaId(Long idFamilia) {		
		
        Optional<FamiliaProjection> familia = programaFctRepository.getByFamiliaId(idFamilia);
        
        return familia.isPresent() ? modelMapper.map(familia.get(), Familia.class) : null;       
        
	}
	
	public ProgramaFct createPrograma(ProgramaFct programaFctIn) {	
		ProgramaFct programaSave = new ProgramaFct();
		
		Optional<ProgramaFct> programa = programaFctRepository.findById(programaFctIn.getId());
		
		if(programa.isPresent()){			
			Optional<TutorFctDual> tutor = tutoresFctDualRepository.findById(programaFctIn.getTutor().getId());
			
			programa.get().setDescripcion(programaFctIn.getDescripcion());
			programa.get().setC_anno_desde(programaFctIn.getC_anno_desde());
			programa.get().setC_anno_hasta(programaFctIn.getC_anno_hasta());
			programa.get().setTutor(tutor.orElse(null));
			programa.get().setFamilia(programaFctIn.getFamilia());
			programa.get().setOfertaMatriculaGenerico(programaFctIn.getOfertaMatriculaGenerico());
			
			programaSave = programaFctRepository.save(programa.get());
		}else {
			programaSave = programaFctRepository.save(programaFctIn);
		}
		
		return programaSave;	
	}	
	
	public ProgramaFct updatePrograma(ProgramaFct programaFct) {		
		
		
		Optional<ProgramaFct> ProgramaFct = programaFctRepository.findById(programaFct.getId());
		
		ProgramaFct updatedPrograma = new ProgramaFct();		
		
		if(!ProgramaFct.isEmpty()) {			
			
			ProgramaFct programaAux = ProgramaFct.get();
			BeanUtils.copyProperties(programaFct, programaAux, Utiles.getNullPropertyNames(programaFct));
			updatedPrograma = programaFctRepository.save(programaAux); 	
		
	    }
		
		return modelMapper.map(updatedPrograma, ProgramaFct.class);			

	}
	
	public void deletePrograma(Long idPrograma) {
		
		
		List<DatosProgramaFct> datosProgramas = datosProgramasService.getActividadesPrograma(idPrograma);
		
		for (DatosProgramaFct dato : datosProgramas){
			datosProgramasService.deleteDatosPrograma(dato.getId());
	    }
		
		programaFctRepository.deleteById(idPrograma);		
		
	}


	@Override
	public List<ProgramaFct> getProgramasTutor(Long idTutor) {
		
		List<ProgramaFct> programaFct = programaFctRepository.findAllByTutorId(idTutor);		
		
		return programaFct.stream()
				.map(entity -> modelMapper.map(entity, ProgramaFct.class)).collect(Collectors.toList());	
		
	}


	@Override
	public byte[] exportReport(Long idConvProg, Long idMatricula) throws IOException, JRException {
		 
		byte[] output = null; 
	    InputStream dbAsStream = null; 
	     
	    Resource resource = resourceLoader.getResource("classpath:reports/AnexoII_ProgramaFormativo.jrxml");
        dbAsStream = resource.getInputStream();     
        
        Map<String, Object> parameters = new HashMap<>();   
        DatosCabeceraEvaluacionProjection parametros = null;  
        List<DatosProgramaFct> datosProgramasDataSource = null;
        Optional<ConveniosProgramasFct> convProg = convProgRepository.findById(idConvProg);
        
        Alumno alumno = alumnado.getAlumno(idMatricula);
       	 
       	parametros = programaFctRepository.getDatosCabeceraFCT(convProg.get().getId(), idMatricula);     
       	 
       	datosProgramasDataSource = datosProgramaFctRepository.findAllByProgramaIdOrderByOrden(convProg.get().getPrograma().getId());
        
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(datosProgramasDataSource);

		Integer horasConvenio = convProg.map(ConveniosProgramasFct::getNuHorasTotales).orElse(0);

		Double horasTutoria  = alumnado.getHoras(idConvProg,idMatricula);
		
		String horasS =  (horasConvenio == null || horasConvenio == 0)?String.valueOf(horasTutoria):String.valueOf(horasConvenio);;
		
        if(parametros != null) {        
        	String tituloCabecera ="MÓDULO FORMATIVO DE FORMACIÓN EN CENTROS DE TRABAJO " + "<center>" + "<u>" + "PROGRAMA FORMATIVO" + "</u>"  + "</center>" ;
        	String centro = parametros.getCentro();
		   	String codigo_centro = parametros.getCodigo_centro();
		   	String nombre_tutor = parametros.getNombre_tutor();
		   	String centro_trabajo = parametros.getCentro_trabajo();
		   	String responsable = parametros.getResponsable();
		   	String periodo = parametros.getPeriodo();
		   	String familia = parametros.getFamilia();
		   	String curso = parametros.getCurso();
		   	String area = parametros.getArea();
		   	String localidad = parametros.getLocalidad();	
		   	
		   	//String sFirma = conveniosFctRepository.getFechaInicioConvenio(parametros.getIdconvenio());	
		   	String sFirma = conveniosFctRepository.getFechaActual();
			String nombre_alumno = alumno.getNombre();
			
//		   	String nombre_alumno = parametros.getNombre_alumno();		   	
//		   	String evaluacion = parametros.getEvaluacion();
//		   	String orientaciones = parametros.getOrientaciones();
		   	
		   	String descripcionFirma =  "En " + localidad  + " " + sFirma; 
		   	
		   	parameters.put("nombre_alumnado", nombre_alumno);
		   	parameters.put("horas", horasS);
		   	parameters.put("centro", centro);
		   	parameters.put("codigo_centro", codigo_centro);
		   	parameters.put("nombre_tutor", nombre_tutor);
		   	parameters.put("familia", familia);
		   	parameters.put("curso", curso);
		   	parameters.put("centro_trabajo", centro_trabajo);
		   	parameters.put("responsable", responsable);
		   	parameters.put("area", area);
		   	parameters.put("periodo", periodo);
		   	parameters.put("descripcionFirma", descripcionFirma);
		   	parameters.put(("tituloCabeceraP"),tituloCabecera);
		   	parameters.put("horasConvenio", horasConvenio);
		   	parameters.put("ds", datasource);    
        } 

        
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
	public byte[] exportReportAnexoI(Long idConvProg) throws IOException, JRException {
		 
		byte[] output = null; 
	    InputStream dbAsStream = null; 
	     
	    Resource resource = resourceLoader.getResource("classpath:reports/anexoI_Alumnado.jrxml");
        dbAsStream = resource.getInputStream();     
        
        Map<String, Object> parameters = new HashMap<>();   
        DatosCabeceraAnexoIAlumnadoProjection parametros = null;  
        List<ListadoAnexoIAlumnadoProjection> listadoAnexoIAlumnadoProjection = null;
        Optional<ConveniosProgramasFct> convProg = convProgRepository.findById(idConvProg);
        
       	parametros = programaFctRepository.getDatosCabeceraAnexoIFCT(convProg.get().getId());
       	
       	listadoAnexoIAlumnadoProjection = programaFctRepository.getListadoAlumnadoAnexoI(idConvProg);
       	
        List<ListadoAnexoIAlumnadoFct> listadoAnexoIAlumnado = listadoAnexoIAlumnadoProjection.stream()
				.map(entity -> modelMapper.map(entity, ListadoAnexoIAlumnadoFct.class)).collect(Collectors.toList());
        
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(listadoAnexoIAlumnado);
        
        if(parametros != null) {        
        	//String numConvenio = parametros.getNumconv();
        	//String dia = parametros.getDia();
        	//String mes = parametros.getMes();
        	//String anno = parametros.getAnno();
        	//String centro = parametros.getCentro();
        	//String empresa = parametros.getEmpresa();
        	//String direccion = parametros.getDireccion();
        	String curso = parametros.getCurso();
        	String cursoAcademico = parametros.getCursoAcademico();
        	//String cursoAcademico = parametros.getAnno() + "/" + (Integer.parseInt(parametros.getAnno())+1);
        	String tutor = parametros.getTutor();
        	String representante = parametros.getRepresentante();
        	String localidad = parametros.getLocalidad();
        	String responsable = parametros.getResponsable();

			String cabecera = "Relación de alumnos y alumnas acogidos al CONVENIO/ACUERDO específico número " +
					"<u>" + Optional.ofNullable(parametros.getNumconv()).orElse("".repeat(15)) + "</u>" +
					" suscrito con fecha a " + "<u>" + Optional.ofNullable(parametros.getDia()).orElse("".repeat(3)) + "</u>" +
					" de " + "<u>" + Optional.ofNullable(parametros.getMes()).orElse("".repeat(11)) + "</u>" +
					" de " + "<u>" + Optional.ofNullable(parametros.getAnno()).orElse("".repeat(6)) + "</u>" +
					" entre el centro educativo " + "<u>" + Optional.ofNullable(parametros.getCentro()).orElse("".repeat(15)) + "</u>" +
					" y la empresa o entidad " + "<u>" + Optional.ofNullable(parametros.getEmpresa()).orElse("".repeat(15)) + "</u>" +
					", con Centro de Trabajo ubicado en " + "<u>" + Optional.ofNullable(parametros.getDireccion()).orElse("".repeat(20)) + "</u>" +
					" que realizarán Formación ubicado en Centros de Trabajo (F.C.T.) durante el período abajo indicado.";
        	
		   	//String sFirma = conveniosFctRepository.getFechaInicioConvenio(parametros.getIdconvenio());
        	String sFirma = conveniosFctRepository.getFechaHoy();
		   	
		   	String descripcionFirma =  "En " + localidad  + " " + sFirma;  
		   	 
		   	DatosCentroConvenioProjection datosProyeccion = conveniosFctRepository.findDatosCentro(parametros.getIdcentro());
		   	 
		   	parameters.put("cabecera", cabecera);
		   	/*parameters.put("diaP", dia);
		   	parameters.put("mesP", mes);
		   	parameters.put("annoP", anno);
		   	parameters.put("centroP", centro);
		   	parameters.put("empresaP", empresa);
		   	parameters.put("direccionP", direccion); */
		   	parameters.put("cursoP", curso);
		   	parameters.put("cursoAcademicoP", cursoAcademico);   
		   	parameters.put("tutorP", tutor);
		   	parameters.put("representanteP", representante);
		   	parameters.put("responsableP", responsable);
		   	parameters.put("directorP", datosProyeccion.getNombreCompleto());
		   	parameters.put("descripcionFirma", descripcionFirma);
		   	parameters.put("ds", datasource);   
        } 

        
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
	public List<TutorFctDual> getAllTutoresListadoProgramas(Long idCentro) {
		List<ProgramaFct> programas = programaFctRepository.findAllByCentroId(idCentro);
		
		return programas.stream().map(ProgramaFct::getTutor).sorted(Comparator.comparing(p -> p.getPuestoTrabajoEmpleado().getEmpleado().getApellido1())).distinct().collect(Collectors.toList());
	}
	
	@Override
	public List<Familia> getAllFamiliasListadoProgramas(Long idCentro, Long idTutor) {
		List<ProgramaFct> programas = new ArrayList<>();
		
		if(idTutor == -1) {
			 programas = programaFctRepository.findAllByCentroId(idCentro);
		}else {
			 programas = programaFctRepository.findAllByCentroIdAndTutorId(idCentro, idTutor);
		}
		
		return programas.stream().map(ProgramaFct::getFamilia).sorted(Comparator.comparing(p -> p.getDescripcionLarga())).distinct().collect(Collectors.toList());
	}
	
	@Override
	public List<OfertaMatriculaGenerico> getAllOfertaGenericaListadoProgramas(Long idCentro, Long idTutor, Long idFamilia) {		
		List<ProgramaFct> programas = new ArrayList<>();
		
		if(idTutor == -1 && idFamilia == -1) {
			 programas = programaFctRepository.findAllByCentroId(idCentro);
		}else if(idTutor != -1 && idFamilia == -1) {
			programas = programaFctRepository.findAllByCentroIdAndTutorId(idCentro, idTutor);
		}else if(idTutor == -1 && idFamilia != -1) {
			programas = programaFctRepository.findAllByCentroIdAndFamiliaId(idCentro, idFamilia);
		}else {
			programas = programaFctRepository.findAllByCentroIdAndTutorIdAndFamiliaId(idCentro, idTutor, idFamilia);
		}
		
		return programas.stream().map(ProgramaFct::getOfertaMatriculaGenerico).sorted(Comparator.comparing(p -> p.getDescripcionOfertaMatricula())).distinct().collect(Collectors.toList());
	}
	
	@Override
	public List<ElementoSelect> getCentrosDelegacion(Long idUsuario, Long idPerfil, Long idCentro, Long idProvincia) {
		
		List<ElementoSelectProjection> centroProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			centroProjection = programaFctRepository.allCentroDelegacionProvincias(idProvincia); 
			
		} else {
			
			centroProjection = programaFctRepository.allCentroDelegacion(idUsuario,idPerfil,idCentro); 
		}
		
		
		
		return centroProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}


	@Override
	public List<ElementoSelect> getTutoresDelegacion(Long idUsuario, 
													 Long idPerfil, 
													 Long idCentro,
													 Long idCentroProvincia,
													 Long idProvincia) {
		
		List<ElementoSelectProjection> tutoresProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			tutoresProjection = programaFctRepository.allTutoresDelegacionProvincias(idCentro,
																					 idProvincia); 
			
		} else {
			
			 tutoresProjection = programaFctRepository.allTutoresDelegacion(idUsuario,
																			idPerfil,
																			idCentro,
																			idCentroProvincia); 
			
		}
	
		
		return tutoresProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}


	@Override
	public List<ElementoSelect> getFamiliasDelegacion(Long idUsuario, 
													  Long idPerfil, 
													  Long idCentro,
													  Long idCentroProvincia, 
													  Long idTutor,
													  Long idProvincia) {
		
		List<ElementoSelectProjection> familiasProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			
			 familiasProjection = programaFctRepository.allFamiliasDelegacionProvincias(idCentro,	
																					    idTutor,
																					    idProvincia); 
			
		} else {

			 familiasProjection = programaFctRepository.allFamiliasDelegacion(idUsuario,
																			  idPerfil,
																			  idCentro,
																			  idCentroProvincia,
																			  idTutor); 
			
		}	


		return familiasProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}


	@Override
	public List<ElementoSelect> getCursosDelegacion(Long idUsuario, 
												    Long idPerfil, 
												    Long idCentro,
												    Long idCentroProvincia, 
												    Long idTutor, 
												    Long idFamilia,
												    Long idProvincia) {
		
		List<ElementoSelectProjection> cursosProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			
			 cursosProjection = programaFctRepository.allCursosDelegacionProvincias(idCentro,					
																				    idTutor,
																				    idFamilia,
																				    idProvincia); 
			
		} else {
			
			 cursosProjection = programaFctRepository.allCursosDelegacion(idUsuario,
																		  idPerfil,
																		  idCentro,
																		  idCentroProvincia,
																		  idTutor,
																		  idFamilia);			
			
		}


		return cursosProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}


	@Override
	public List<ListadoProgramaFct> getAllProgramasDelegacion(Long idUsuario, 
															  Long idPerfil, 
															  Long idCentro,
															  Long idCentroProvincia, 
															  Long idTutor, 
															  Long idFamilia, 
															  Long idCurso,
															  Long idProvincia,
															  Long idConvenio,
															  Integer cAnno) {
		
		List<ListadoProgramaFctProjection> programaProjection = null;

		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			 programaProjection = programaFctRepository.getAllCentroTutorFamiliaOfertaProvincias(idCentro,																								 
																								 idTutor, 
																								 idFamilia, 
																								 idCurso,
																								 idProvincia,
																								 idConvenio,
																								 cAnno);
			
		} else {
			
			 programaProjection = programaFctRepository.getAllCentroTutorFamiliaOferta(idUsuario,
																					   idPerfil,
																					   idCentro, 
																					   idCentroProvincia,
																					   idTutor, 
																					   idFamilia, 
																					   idCurso,
																					   idConvenio,
																					   cAnno);
			
			
		}
 
		
		return programaProjection.stream().map(entity -> modelMapper.map(entity, ListadoProgramaFct.class)).collect(Collectors.toList());
	}


	@Override
	public List<ElementoSelect> getAllProgramasConvenios(Long idCentro, 
														 Long idTutor, 
														 Long idFamilia, 
														 Long idOferta) {
		
		List<ElementoSelectProjection> convenioProjection = conveniosFctRepository.getAllProgramasConvenios(idCentro, 
																												idTutor, 
																												idFamilia, 
																												idOferta); 

		return convenioProjection.stream()
									.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}


	@Override
	public List<ElementoSelect> getConveniosDelegacion(Long idUsuario, 
													   Long idPerfil, 
													   Long idCentro,
													   Long idCentroProvincia, 
													   Long idTutor, 
													   Long idFamilia, 
													   Long idProvincia, 
													   Long idCurso) {
		
		List<ElementoSelectProjection> conveniosProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			
			conveniosProjection = programaFctRepository.allConveniosDelegacionProvincias(idCentro,					
																				    idTutor,
																				    idFamilia,
																				    idProvincia,
																				    idCurso); 
			
		} else {
			
			conveniosProjection = programaFctRepository.allConveniosDelegacion(idUsuario,
																		  idPerfil,
																		  idCentro,
																		  idCentroProvincia,
																		  idTutor,
																		  idFamilia,
																		  idCurso);			
			
		}


		return conveniosProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}


	
	
	@Override
	public Integer getProgramaUsado(Long id) {

		return programaFctRepository.getProgramaUsado(id);
	}
	
}
