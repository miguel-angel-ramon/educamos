package es.jccm.edu.proyectosfct.application.services.convenios;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosProgramasFctRepository;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConvenioListFct;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.EmpresaTrabajador;
import es.jccm.edu.proyectosfct.application.domain.convenios.projection.ConvenioProjection;
import es.jccm.edu.proyectosfct.application.domain.convenios.projection.DatosCentroConvenioProjection;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasFct;
import es.jccm.edu.proyectosfct.application.domain.empresas.Empresa;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.FamiliaProjection;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Empleado;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IConveniosFctService;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IEmpresaTrabajadorService;
import es.jccm.edu.proyectosfct.application.ports.in.empresas.IEmpresasService;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.ICentroService;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.IEmpleadoService;
import es.jccm.edu.proyectosfct.application.services.Utiles;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class ConveniosFctService implements IConveniosFctService {
	
	
	private static final Long ID_PERFIL_DELEGACION = 13207L;

	private static final Long ID_PERFIL_DELEGACION_1 = 15207L;

	@Autowired
	private ConveniosFctRepository conveniosFctRepository;
	
	@Autowired
	private ConveniosProgramasFctRepository conveniosProgramasFctRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ICentroService centroService;
	
	@Autowired
	private IEmpleadoService empleadoService;
	
	@Autowired
	private IEmpresasService empresaService;
	
	@Autowired
	private IEmpresaTrabajadorService empresaTrabajadorService;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	// Create
	
	public ConveniosFct createConvenio(ConveniosFct conveniosFct) {
		
		//String NewNumeroConvenio = conveniosFctRepository.getMaxNconvenio(conveniosFct.getCentro().getId()); 		
		
		//conveniosFct.setNumeroConvenio(NewNumeroConvenio);		
		
		return conveniosFctRepository.save(conveniosFct);
	}

	// Read
	
	@Override
	public List<ElementoSelect> getCentrosDelegacion(Long idUsuario, Long idPerfil, Long idCentro, Long idProvincia) {
		
		List<ElementoSelectProjection> centroProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil)) {
			
			centroProjection = conveniosFctRepository.allCentroDelegacionProvincias(idProvincia);
			
		} else {
			
			centroProjection = conveniosFctRepository.allCentroDelegacion(idUsuario,idPerfil,idCentro);
			
		}		

		
		return centroProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}
	
	public List<ConveniosFct> findConveniosByCentroIdAndAnno(Long idCentro, Integer cAnno) {		
		
		List<ConveniosFct> convenios = conveniosFctRepository.findConveniosByCentroIdAndAnno(idCentro, cAnno);
		
		//List<ConveniosFct> convenios = conveniosFctRepository.findConveniosByCentroId(idCentro);
		
		return convenios;	
	}
	
	public List<ConveniosFct> findConveniosDelegacionByCentroIdAndAnno(Long xUsuarioDelphos, Long idPerfil, Long idCentroProvincia, Long idCentro, Integer cAnno) {		
		
		List<ConveniosFct> convenios = conveniosFctRepository.findConveniosDelegacionByCentroIdAndAnno(xUsuarioDelphos, idPerfil, idCentroProvincia, idCentro, cAnno);
		
		return convenios;	
	}
	
	public List<ConvenioListFct> getAllConvenios() {		
		
		List<ConvenioProjection> conveniosProjection = conveniosFctRepository.getAllCovenios(); 
		
		List<ConvenioListFct> conveniosListFct = conveniosProjection.stream()
							.map(entity -> modelMapper.map(entity, ConvenioListFct.class)).collect(Collectors.toList());
		
		
		for (ConvenioListFct convenio : conveniosListFct) {
			 
			List<FamiliaProjection> familiaProjection = conveniosFctRepository.allFamiliasConvenio(convenio.getId()); 
				
			List<Familia> familias = familiaProjection.stream()
							.map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());			 
			 
			
			if (familias.size() > 0) {				
				
				StringBuilder listDescripcionFamilia = new StringBuilder(); 
				 
				 for (Familia familia : familias) {
					 
					 listDescripcionFamilia.append(familia.getDescripcionLarga());
					 listDescripcionFamilia.append("/ ");
					 
				 }
				 
				 String SFamilias = listDescripcionFamilia.toString();
				 
				 convenio.setListFamilias(SFamilias.substring(0, SFamilias.length()-2));			 
			}	 
		 }	
		
		return conveniosListFct;
		
	}
	
	
	public List<ConvenioListFct> getAllConveniosCentroAnno(Long idCentro, 
														   Integer cAnno, 
														   Long idEmpresa,			 
														   Long idEstado, 
														   Long idFamilia, 
														   Long idTutor, 
														   Long idMatricula,
														   Long idTipo) {
		
		List<ConvenioProjection> conveniosProjection = conveniosFctRepository.getAllCoveniosCentroAnno(idCentro, 
																									   cAnno, 
																									   idEmpresa,																									
																									   idEstado, 
																									   idFamilia, 
																									   idTutor,
																									   idMatricula,
																									   idTipo);
		
		List<ConvenioListFct> conveniosListFct = conveniosProjection.stream().map(entity -> modelMapper.map(entity, ConvenioListFct.class)).collect(Collectors.toList());	 
		 
		return conveniosListFct;
	}
	
	@Override
	public List<ConvenioListFct> getAllConveniosActivos(Long idCentro, Integer lglofp) {
		List<ConvenioProjection> conveniosProjection = conveniosFctRepository.getAllCoveniosActivos(idCentro,lglofp); 
		
		List<ConvenioListFct> conveniosListFct = conveniosProjection.stream()
							.map(entity -> modelMapper.map(entity, ConvenioListFct.class)).collect(Collectors.toList());
		
		 for (ConvenioListFct convenio : conveniosListFct) {
			 
			List<FamiliaProjection> familiaProjection = conveniosFctRepository.allFamiliasConvenio(convenio.getId()); 
				
			List<Familia> familias = familiaProjection.stream()
							.map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());			 
			 
			
			if (familias.size() > 0) {				
				
				StringBuilder listDescripcionFamilia = new StringBuilder(); 
				 
				 for (Familia familia : familias) {
					 
					 listDescripcionFamilia.append(familia.getDescripcionLarga());
					 listDescripcionFamilia.append("/ ");
					 
				 }
				 
				 String SFamilias = listDescripcionFamilia.toString();
				 
				 convenio.setListFamilias(SFamilias.substring(0, SFamilias.length()-2));			 
			}	 
		 }		 
		 
		return conveniosListFct;
	}	
	
	public ConveniosFct getConvenioById(Long idConvenio) {
		Optional<ConveniosFct> res = conveniosFctRepository.findById(idConvenio);
		
		return res.isPresent() ? res.get() : null;
	}
	
	public ConveniosFct getConvenioByIdConfirRodal(String idConfirRodal) {
		Optional<ConveniosFct> res = conveniosFctRepository.findByIdConfirRodal(idConfirRodal);
		
		return res.isPresent() ? res.get() : null;
	}
	
	public ConveniosFct getConvenioByIdConproRodal(String idConproRodal) {
		Optional<ConveniosFct> res = conveniosFctRepository.findByIdConproRodal(idConproRodal);
		
		return res.isPresent() ? res.get() : null;
	}	

	public DatosCentroConvenioProjection getDatosCentro(Long idCentro) {
		return conveniosFctRepository.findDatosCentro(idCentro);
	}
	
	// Update
	
	public ConveniosFct updateConvenio(ConveniosFct conveniosFct) {
		
		ConveniosFct convenioOrigen = getConvenioById(conveniosFct.getId());
		
		if (convenioOrigen != null) {
			BeanUtils.copyProperties(conveniosFct, convenioOrigen, Utiles.getNullPropertyNames(conveniosFct));
			
			if(conveniosFct.getCentro() != null && conveniosFct.getCentro().getId() != null) {
				Centro centro = centroService.getCentroById(conveniosFct.getCentro().getId());
				convenioOrigen.setCentro(centro);
			}
			
			if(conveniosFct.getEmpleado() != null && conveniosFct.getEmpleado().getId() != null) {
				Empleado empleado = empleadoService.getEmpleadoById(conveniosFct.getEmpleado().getId());
				convenioOrigen.setEmpleado(empleado);
			}
			
			if(conveniosFct.getEmpresa() != null && conveniosFct.getEmpresa().getId() != null) {
				Empresa empresa = empresaService.getEmpresaById(conveniosFct.getEmpresa().getId());
				convenioOrigen.setEmpresa(modelMapper.map(empresa, Empresa.class));
			}
			
			if(conveniosFct.getTrabajador() != null && conveniosFct.getTrabajador().getId() != null) {
				EmpresaTrabajador trabajador = empresaTrabajadorService.getTrabajadorById(conveniosFct.getTrabajador().getId());
				convenioOrigen.setTrabajador(trabajador);
			}
			
			convenioOrigen = conveniosFctRepository.save(convenioOrigen);
		}

		return convenioOrigen;
		
	}
	
	// Delete
	
	@Transactional
	public void deleteConvenio(Long idConvenio) {
		conveniosFctRepository.deleteById(idConvenio);
	}
	
	// Other methods
	
	@Override
	public List<Familia> getAllFamiliasConvenio(Long idConvenio) {
		
		List<FamiliaProjection> familiaProjection = conveniosFctRepository.allFamiliasConvenio(idConvenio); 
		
		return familiaProjection.stream()
				.map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());

	}
	

	
	@Override
	public List<Familia> getAllFamiliasListadoConvenio(Long idCentro, Integer cAnno, Long idEmpresa, Long idEstado) {
		
		List<FamiliaProjection> familiaProjection = conveniosFctRepository.getAllFamiliasListadoConvenio(idCentro, cAnno, idEmpresa, idEstado); 
		
		return familiaProjection.stream()
				.map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());

	}
	
	@Override
	public List<ElementoSelect> getAllTutoresListadoConvenio(Long idCentro, 
													  Integer cAnno, 
													  Long idEmpresa,													   
													  Long idEstado,
													  Long idFamilia) {
		
		List<ElementoSelectProjection> tutorProjection = conveniosFctRepository.getAllTutoresListadoConvenio(idCentro, 
																											 cAnno, 
																											 idEmpresa,																											 
																											 idEstado,
																											 idFamilia); 
		
		return tutorProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());

	}
	
	
	@Override
	public String getFamiliaConvenioNivelEducativo(Long idOfertamatrig) {
		return conveniosFctRepository.getFamiliaConvenioNivelEducativo(idOfertamatrig); 
	}
	
	//get convenios jasper pdf
	public byte[] exportReport(Long id) throws JRException, IOException {

        byte[] output = null; 
        Map<String, Object> parameters = new HashMap<>();
        Optional<ConveniosFct> res = conveniosFctRepository.findById(id);
        String familia = "";
        String yearVigencia = "0";
        String isSuperior = "";
        //File file = new File("");
        InputStream dbAsStream = null; 
        if(res.isPresent()) {
        	ConveniosFct convenio = res.get();	
        	List<ConveniosFct> conveniol = new ArrayList<ConveniosFct>();
        	conveniol.add(convenio);
        	
        	if (convenio.getLgLofp() == 1) 
        	{
          		Resource resource = resourceLoader.getResource("classpath:reports/anexo0C.jrxml");
                dbAsStream = resource.getInputStream(); 
                
        	} else {
        		
        		if(convenio.getEmpresa().getSesCam().equals("S") ) {
            		
                    if(convenio.getFechaInicio() != null && convenio.getFechaFinVigencia() != null) {
          	        	Integer fInicioYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(convenio.getFechaInicio()));
          	  	        Integer fFinVigYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(convenio.getFechaFinVigencia()));
          	  	        yearVigencia = Integer.toString(fFinVigYear - fInicioYear);
          	        }
          	        
          	        List<FamiliaProjection> familiaProjection = conveniosFctRepository.allFamiliasConvenio(convenio.getId()); 
        			List<Familia> familias = familiaProjection.stream().map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());
        			
        			if(!familias.isEmpty()) {
        				familia = familias.get(0).getDescripcionLarga();
        			}
        			
        			List<ConveniosProgramasFct> convProgList = conveniosProgramasFctRepository.findAllByConvenioId(convenio.getId());
        			if(!convProgList.isEmpty()) {				
        				isSuperior = getFamiliaConvenioNivelEducativo(convProgList.get(0).getPrograma().getOfertaMatriculaGenerico().getId()).equals("C.F.G.M.")?"N":"S";
        			}
        			
        			Resource resource = resourceLoader.getResource("classpath:reports/anexo0B.jrxml");
                    dbAsStream = resource.getInputStream();
        	      
            	}else if(convenio.getEmpresa().getEmpresaPublica().equals("S")) {
            		//file = ResourceUtils.getFile("classpath:anexo02.jrxml");

					Resource resource = resourceLoader.getResource("classpath:reports/anexo02.jrxml");
					dbAsStream = resource.getInputStream();

				}else {
            		//file = ResourceUtils.getFile("classpath:anexo0.jrxml");
            		Resource resource = resourceLoader.getResource("classpath:reports/anexo0.jrxml");        		
                    dbAsStream = resource.getInputStream(); 
            	}        		
        	}        	
        	
        	  //JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        	JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);
  	        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(conveniol);
        	    

  	        
        	DatosCentroConvenioProjection datosProyeccion = getDatosCentro(convenio.getCentro().getId());
        	if(datosProyeccion != null) {
        		Long codigoCentro = datosProyeccion.getCodigocentro();
            	String municipio = datosProyeccion.getMunicipio();
            	String provincia = datosProyeccion.getProvincia();
            	String calle = datosProyeccion.getDomicilio();
            	String codigoPostal = datosProyeccion.getCodigoPostal();
            	String cifCentro = datosProyeccion.getCifCentro();
            	String correo = datosProyeccion.getCorreo();
            	String telefono = datosProyeccion.getTelefono();
            	String nombreCentro = datosProyeccion.getNombreCentro();
            	String nombreCentroCompleto = datosProyeccion.getNombreCentroCompleto();
            	String localidad = datosProyeccion.getLocalidad();
            	String ssCentro = convenio.getEmpresa().getSsCen();
            	String direccionSedeEmpresa;
				String nombreDirector = datosProyeccion.getNombre();
				String apellido1Director = datosProyeccion.getApellido1();
				String apellido2Director = datosProyeccion.getApellido2();
				String cifDirector = datosProyeccion.getCifDirector();


            	if(conveniol.get(0).getId_sede().getDomicilio()!=null) { 
            		direccionSedeEmpresa = conveniol.get(0).getId_sede().getDomicilio() +
            				(conveniol.get(0).getId_sede().getNumero()!=null? " " + conveniol.get(0).getId_sede().getNumero():"") +
            				(conveniol.get(0).getId_sede().getEscalera()!=null? " " + conveniol.get(0).getId_sede().getEscalera():"") +
            				(conveniol.get(0).getId_sede().getPiso()!=null? " " + conveniol.get(0).getId_sede().getPiso():"") + 
            				(conveniol.get(0).getId_sede().getLetra()!= null? " " + conveniol.get(0).getId_sede().getLetra():"");
            	} else {
            		direccionSedeEmpresa = "__________________________________";
            	}	
            	

            	String sFirma = conveniosFctRepository.getFechaHoy();
            	
            	String descripcionFirma =  "En " + localidad  + " " + sFirma; 
            	String descripcionFirmaLOPF = "En " + localidad + " ," + provincia  + " " + sFirma;
            	
    	        parameters.put("municipioP", municipio);
    	        parameters.put("codigoCentroP", codigoCentro);
    	        parameters.put("provinciaP", provincia);
    	        parameters.put("calleP", calle);
    	        parameters.put("codigoPostalP", codigoPostal);
    	        parameters.put("cifCentroP", cifCentro);
    	        parameters.put("correoP", correo);
    	        parameters.put("telefonoP", telefono);
    	        parameters.put("nombreCentro", nombreCentro);
    	        parameters.put("nombreCentroCompleto", nombreCentroCompleto);
    	        parameters.put("yearVigencia", yearVigencia);
    	        parameters.put("familia", familia);
    	        parameters.put("isCicloSuperior", isSuperior);
    	        parameters.put("descripcionFirma", descripcionFirma);
    	        parameters.put("ssCentro", ssCentro);
    	        parameters.put("direccionSedeEmpresaP", direccionSedeEmpresa);
    	        parameters.put("descripcionFirmaLOFP", descripcionFirmaLOPF);
    	        parameters.put("nombreDirectorP", nombreDirector);
    	        parameters.put("apellido1DirectorP", apellido1Director);
    	        parameters.put("apellido2DirectorP", apellido2Director);
    	        parameters.put("cifDirectorP", cifDirector);

    	       // parameters.put("cabeceraP",cabecera);
        	}
        		        
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
	        
	        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        JRExporter exporter = new JRPdfExporter();
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
	        exporter.exportReport();
	        output = outputStream.toByteArray();       
        }
        return output;
    }
	
	public byte[] exportReportRenovar(Long id) throws JRException, IOException {

        byte[] output = null; 
        Map<String, Object> parameters = new HashMap<>();
        Optional<ConveniosFct> res = conveniosFctRepository.findById(id);
        if(res.isPresent()) {
        	ConveniosFct convenio = res.get();	
        	List<ConveniosFct> conveniol = new ArrayList<ConveniosFct>();
        	conveniol.add(convenio);
        	
        	//File file = ResourceUtils.getFile("classpath:anexo16.jrxml");
        	//JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        	
        	Resource resource = resourceLoader.getResource("classpath:reports/anexo16.jrxml");
        	InputStream dbAsStream = resource.getInputStream(); 
        	JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);
        	
        	
  	        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(conveniol);
        	
        	      	
        	DatosCentroConvenioProjection datosProyeccion = getDatosCentro(convenio.getCentro().getId());
        	if(datosProyeccion != null) {
        		Long codigoCentro = datosProyeccion.getCodigocentro();
            	String municipio = datosProyeccion.getMunicipio();
            	String provincia = datosProyeccion.getProvincia();
            	String calle = datosProyeccion.getDomicilio();
            	String codigoPostal = datosProyeccion.getCodigoPostal();
            	String cifCentro = datosProyeccion.getCifCentro();
            	String correo = datosProyeccion.getCorreo();
            	String telefono = datosProyeccion.getTelefono();
            	String nombreCentro = datosProyeccion.getNombreCentro();
            	String nombreCentroCompleto = datosProyeccion.getNombreCentroCompleto();
            	String localidad = datosProyeccion.getLocalidad();
            	
            	Calendar fechaProrrogada = Calendar.getInstance();
            	fechaProrrogada.setTime(convenio.getFechaFinVigencia());
            	fechaProrrogada.add(Calendar.YEAR, +2);
            	
            	
            	SimpleDateFormat formatMes= new SimpleDateFormat("MMMMM");
            	SimpleDateFormat formatMesN= new SimpleDateFormat("MM");
            	SimpleDateFormat formatDia= new SimpleDateFormat("dd");
            	SimpleDateFormat formatAno= new SimpleDateFormat("yyyy");
            	String mesFechaInicio = formatMes.format(convenio.getFechaFirma());
            	String mesFechaInicioN = formatMesN.format(convenio.getFechaFirma());
            	String diaFechaInicio= formatDia.format(convenio.getFechaFirma());
            	String anoFechaInicio= formatAno.format(convenio.getFechaFirma());
            	
            	SimpleDateFormat formatMesFinalizacion= new SimpleDateFormat("MMMMM");
            	SimpleDateFormat formatMesFinalizacionN= new SimpleDateFormat("MM");
            	SimpleDateFormat formatDiaFinalizacion= new SimpleDateFormat("dd");
            	SimpleDateFormat formatAnoFinalizacion= new SimpleDateFormat("yyyy");
            	SimpleDateFormat formatAnoFinalizacionN= new SimpleDateFormat("yyyy");
            	String mesFechaFinalizacion = formatMesFinalizacion.format(fechaProrrogada.getTime());
            	String mesFechaFinalizacionN = formatMesFinalizacionN.format(convenio.getFechaFinVigencia());
            	String diaFechaFinalizacion= formatDiaFinalizacion.format(fechaProrrogada.getTime());
            	String anoFechaFinalizacion= formatAnoFinalizacion.format(fechaProrrogada.getTime());
            	String anoFechaFinalizacionN= formatAnoFinalizacionN.format(convenio.getFechaFinVigencia());
            	
            	String fechaInicio = diaFechaInicio + "/" + mesFechaInicioN+ "/"+ anoFechaInicio;
            	String fechaVigencia = diaFechaFinalizacion + "/" + mesFechaFinalizacionN+ "/"+ anoFechaFinalizacionN;
            	
            	String sProrroga = "Se prorroga hasta el " + diaFechaFinalizacion + " de " + mesFechaFinalizacion + " de " + anoFechaFinalizacion +
            			           " la vigencia del Convenio/Acuerdo de colaboracion, suscrito el "  + diaFechaInicio + " de " + mesFechaInicio + 
            			           " de " + anoFechaInicio + ", entre " + nombreCentroCompleto + " y " + convenio.getEmpresa().getNombreEmpresa() +
            			           ", con el objeto de desarrollo del módulo profesional de formación en centros de trabajo de los ciclos formativos, cursos de especialización o prácticas formativas de otras enseñanzas. ";
            			                       	
     
            	String sFirma = conveniosFctRepository.getFechaInicioConvenio(convenio.getId());
            	
            	String descripcionFirma =  "En " + localidad  + " " + sFirma; 
            	
    	        parameters.put("municipioP", municipio);
    	        parameters.put("codigoCentroP", codigoCentro);
    	        parameters.put("provinciaP", provincia);
    	        parameters.put("calleP", calle);
    	        parameters.put("codigoPostalP", codigoPostal);
    	        parameters.put("cifCentroP", cifCentro);
    	        parameters.put("correoP", correo);
    	        parameters.put("telefonoP", telefono);
    	        parameters.put("nombreCentro", nombreCentro);
    	        parameters.put("nombreCentroCompleto", nombreCentroCompleto);
    	        parameters.put("diaFechaInicio", diaFechaInicio);
    	        parameters.put("anoFechaInicio", anoFechaInicio);
    	        parameters.put("mesFechaInicio", mesFechaInicio);
    	        parameters.put("diaFechaFinalizacion", diaFechaFinalizacion);
    	        parameters.put("anoFechaFinalizacion", anoFechaFinalizacion);
    	        parameters.put("mesFechaFinalizacion", mesFechaFinalizacion);
    	        parameters.put("fechaInicio", fechaInicio );
    	        parameters.put("fechaVigencia", fechaVigencia );
    	        parameters.put("sProrroga", sProrroga );
    	        parameters.put("descripcionFirma", descripcionFirma);
    	        
        	}
        		        
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
	        
	        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        JRExporter exporter = new JRPdfExporter();
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
	        exporter.exportReport();
	        output = outputStream.toByteArray();       
        }
        return output;
    }
	
	@Override
	public ConveniosFct firmarConvenio(String fechaFirma, Long idConvenio, String entidad) {
		
		ConveniosFct convenioUpdate = getConvenioById(idConvenio);
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fechaFirma);
               
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaDate);
            
            
            // Incrementar 4 años
            calendar.add(Calendar.YEAR, 4);
            calendar.add(Calendar.HOUR_OF_DAY, 2);
            Date fechaFinConvenio = calendar.getTime();
            
            if (entidad.equals("CF_"))  {
            	convenioUpdate.setFechaFirma(fechaDate);
            	convenioUpdate.setFechaFinVigencia(fechaFinConvenio);
            } else {
            	convenioUpdate.setFechaFirmaProrroga(fechaDate);             
            }
            conveniosFctRepository.save(convenioUpdate);            
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
		
		
		return convenioUpdate;
		
		
	
	}

	@Override
	public String getNumeroConvenio(Long idCentro) {
		return conveniosFctRepository.getNumeroConvenio(idCentro);
	}

	@Override
	public List<ElementoSelect> getEmpresasDelegacion(Long idUsuario, 
													  Long idPerfil, 
													  Long idCentro,
													  Long idCentroProvincia, 
													  Long cAnno,
													  Long idProvincia,
													  Long idEstado) {
		
		List<ElementoSelectProjection> cursosProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil)) {
			
			cursosProjection = conveniosFctRepository.allEmpresasDelegacionProvincial(idCentro,
																				      idProvincia,
																				      idEstado); 
			
		} else {
			
			cursosProjection = conveniosFctRepository.allEmpresasDelegacion(idUsuario,
																	       idPerfil,
																	       idCentro,
																	       idCentroProvincia,
																	       idEstado													      
																	       ); 
	    }
		
		


 		return cursosProjection.stream()
			.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ElementoSelect> getAllFamiliasDelegacionListadoConvenio(Long idUsuario, 
																	    Long idPerfil, 
																	    Long idCentro, 
																	    Long idCentroProvincia,																	    
																	    Integer cAnno, 
																	    Long idEmpresa,																	    
																	    Long idEstado,
																	    Long idProvincia) {
		
		List<ElementoSelectProjection> familiaProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			
			 familiaProjection = conveniosFctRepository.getAllFamiliasDelegacionListadoConvenioProvincias(idCentro, 
																											idEmpresa,																											
																											idEstado,
																											idProvincia); 
			
		} else {
			
			 familiaProjection = conveniosFctRepository.getAllFamiliasDelegacionListadoConvenio(idUsuario, 
																							  idPerfil, 
																							  idCentro, 
																							  idCentroProvincia,																														 
																							  idEmpresa,																							  
																							  idEstado); 
			
		}
		

		
		return familiaProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());

	}
	
	@Override
	public List<ConvenioListFct> getAllCoveniosDelegacionCentroAnno(Long idUsuario, 
																    Long idPerfil, 
																    Long idCentro, 
																    Long idCentroProvincia, 
																    Integer cAnno, 
																    Long idEmpresa, 		
																    Long idEstado, 
																    Long idFamilia,
																    Long idProvincia,
																    Long idTutor,
																    Long idMatricula,
																	Long idTipo)
	{		
		
		
		List<ConvenioProjection> conveniosProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			
			 conveniosProjection = conveniosFctRepository.getAllCoveniosDelegacionCentroAnnoProvincias(idCentro,	
																									   cAnno, 
																									   idEmpresa,
																									   idEstado, 
																									   idFamilia,
																									   idProvincia,
																									   idTutor,
																									   idMatricula,
					 																				   idTipo);
			
		} else {
			
			 conveniosProjection = conveniosFctRepository.getAllCoveniosDelegacionCentroAnno(idUsuario, 
																							 idPerfil, 
																							 idCentro, 
																							 idCentroProvincia, 
																							 cAnno, 
																							 idEmpresa,
																							 idEstado, 
																							 idFamilia,
																							 idTutor,
																							 idMatricula,
					 																		 idTipo);
			
			
		}
		

		
		List<ConvenioListFct> conveniosListFct = conveniosProjection.stream().map(entity -> modelMapper.map(entity, ConvenioListFct.class)).collect(Collectors.toList());	 
		 
		return conveniosListFct;
	}

	@Override
	public String getEsDirector(Long idCentro,Long idUsuario) {
		return conveniosFctRepository.getEsDirector(idCentro, idUsuario);
	}

	@Override
	public List<ElementoSelect> getAllTutoresDelegacionListadoConvenio(Long idUsuario, 
																	   Long idPerfil,
																	   Long idCentro, 
																	   Long idCentroProvincia, 
																	   Integer cAnno, 
																	   Long idEmpresa,																	   
																	   Long idEstado,
																	   Long idProvincia, 
																	   Long idFamilia) {
		List<ElementoSelectProjection> tutorProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			
			tutorProjection = conveniosFctRepository.getAllTutoresDelegacionListadoConvenioProvincias(idCentro, 
																									  idEmpresa,																									  
																									  idEstado,
																									  idProvincia,
																									  idFamilia); 
			
		} else {
			
			tutorProjection = conveniosFctRepository.getAllTutoresDelegacionListadoConvenio(idUsuario, 
																						    idPerfil, 
																							idCentro, 
																							idCentroProvincia,																														 
																							idEmpresa,																							
																							idEstado,
																							idFamilia); 
			
		}
		

		
		return tutorProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ElementoSelect> getAllAlumnadoListadoConvenio(Long idCentro, 
															  Integer cAnno, 
															  Long idEmpresa,															  
															  Long idEstado, 
															  Long idFamilia, 
															  Long idTutor) {
		
		List<ElementoSelectProjection> alumnadoProjection = conveniosFctRepository.getAllAlumnadoListadoConvenio(idCentro, 
																												 cAnno, 
																												 idEmpresa,																												 
																												 idEstado,
																												 idFamilia,
																												 idTutor); 

		return alumnadoProjection.stream()
					.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ElementoSelect> getAllAlumnadoDelegacionListadoConvenio(Long idUsuario, 
																	    Long idPerfil, 
																	    Long idCentro,
																	    Long idCentroProvincia, 
																	    Integer cAnno, 
																	    Long idEmpresa,																	    
																	    Long idEstado,
																	    Long idProvincia, 
																	    Long idFamilia, 
																	    Long idTutor) {
		
		List<ElementoSelectProjection> alumnadoProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil))
		{
			
			alumnadoProjection = conveniosFctRepository.getAllAlumnadoDelegacionListadoConvenioProvincias(idCentro, 
																									  idEmpresa,																									   
																									  idEstado,
																									  idProvincia,
																									  idFamilia,
																									  idTutor,
																									  cAnno); 
			
		} else {
			
			alumnadoProjection = conveniosFctRepository.getAllAlumnadoDelegacionListadoConvenio(idUsuario, 
																						    idPerfil, 
																							idCentro, 
																							idCentroProvincia,																														 
																							idEmpresa,																							
																							idEstado,
																							idFamilia,
																							idTutor,
																							cAnno); 
			
		}
		

		
		return alumnadoProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}
    @Override
	public String getConveniosVigentes(Integer tipoConvenio, String cif, Long xCentro, Date fhIni, Date fhFin) {
	return conveniosFctRepository.getConveniosProgVigentes(tipoConvenio,cif,xCentro,fhIni,fhFin);
	}

	@Override
	public Integer bajaConvenio(Long id) {
		
		ZoneId zonaMadrid = ZoneId.of("Europe/Madrid");
		LocalDate ayer = LocalDate.now(zonaMadrid).minusDays(1);
		Date ayerDate = java.sql.Date.valueOf(ayer);

		
		ConveniosFct convenio = getConvenioById(id);
			
		convenio.setFechaFinVigencia(ayerDate);
		
		if (convenio.getFechaProrroga() != null) {
			convenio.setFechaProrroga(ayerDate);
			
		}
		conveniosFctRepository.save(convenio);
		
		return 0;
		
	}

}
