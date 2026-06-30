package es.jccm.edu.proyectosfct.application.services.proyectos;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoVinculacionEmpresasDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model.*;
import es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialproy.AltaSegSocialProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.ActividadesModulosRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.EstadoPlanLOFPRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.ResultadosAprendizajeActividadesRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.AlumnoProgramaRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.historicoaltasproy.HistoricoAltasProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.PardiaAluplanActmodRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.PardiaAluplanRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.partealumnoplan.ParsemAluplanRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.*;
import es.jccm.edu.proyectosfct.adapter.out.repositories.sedeempresa.SedeEmpresaRepository;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ActividadesModulos;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ResultadosAprendizajeActividades;
import es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities.AltasSegSociProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.*;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.*;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.EstadoPlanLOFP;
import es.jccm.edu.proyectosfct.application.domain.empresas.SedeEmpresa;
import es.jccm.edu.proyectosfct.application.domain.historicoaltasproyectos.entities.HistoricoAltasProy;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplan;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.PardiaAluplanActmod;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.ParsemAluplan;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.*;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.*;
import es.jccm.edu.proyectosfct.application.services.actividadesmodulos.ActividadesModulosService;
import es.jccm.edu.proyectosfct.application.services.resultadosAsociadosPlan.ResultadosAsociadosPlanService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;
import com.google.common.base.Objects;
import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.ResultadosAsociadosPlanRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.convenios.ConveniosFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.datosproyectos.DatosProyectosFctRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesproy.SegSocialCotizaMesProyRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.AlumnoProjection;
import es.jccm.edu.proyectosfct.application.domain.convenios.projection.DatosCentroConvenioProjection;
import es.jccm.edu.proyectosfct.application.domain.datosproyecto.DatosProyectosFct;
import es.jccm.edu.proyectosfct.application.domain.modalidades.Modalidad;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.Familia;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ListadoAnexoIAlumnadoFct;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.CursoModalidadProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ListadoAnioCentroProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ListadoProyectosProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ModuloModalidadProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ModuloProyectosProjection;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ResultadosAsociadosPlan;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesproyectos.entities.CotizaMesProyectos;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.TutorFctDual;
import es.jccm.edu.proyectosfct.application.ports.in.alumnado.IAlumnadoService;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IInformacionProyectosService;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IProyectosService;
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
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;

@Service
public class ProyectosService implements IProyectosService {

	private static final Logger LOG = LogManager.getLogger(ProyectosService.class);
	
	private static final Long ID_PERFIL_DELEGACION = 13207L;

	private static final Long ID_PERFIL_DELEGACION_1 = 15207L;

	public static final String RELLENO = "___________";
	public static final String FAMILIA = "familia";
	public static final String FIRMA = "descripcionFirma";
	public static final String DNI = " con D.N.I. ";
	public static final String CENTRO = "centro";
	

	@Autowired
	private AltaSegSocialProyRepository altaSegSocialProyRepository;
	
	@Autowired
	private SegSocialCotizaMesProyRepository segSocialCotizaMesProyRepository;

	@Autowired
	private ProyectosRepository proyectosRepository;

	@Autowired
	private SecuenciacionProyectosRepository secuenciacionProyectosRepository;
	
	@Autowired
	private ConveniosProyectoRepository conveniosProyectoRepository;
	
	@Autowired
	private CursoProyectoRepository cursoProyectoRepository;
	
	@Autowired
	private ConvProyAlumnoRepository convProyAlumnoRepository;
	
	@Autowired
	private ConvProyAluHorPeriodoFctRepository convProyAluHorPeriodoFctRepository;
	
	@Autowired
	private ConvProyAluHorTramoFctRepository convProyAluHorTramoFctRepository;
	
	@Autowired
	private ModulosCursoRepository modulosCursoRepository;
	
	@Autowired
	private ModulosActividadRepository modulosActividadRepository;
	
	@Autowired
	private IInformacionProyectosService informacionProyectosService;
	
	@Autowired
	private DatosProyectosFctRepository datosProyectosFctRepository;
	
	@Autowired
	private ConvProyHorPeriodoFctRepository convProyHorPeriodoFctRepository;
	
	@Autowired
	private ConvProyHorTramoFctRepository convProyHorTramoFctRepository;
	
	@Autowired
	private ConveniosFctRepository conveniosFctRepository;
	
	@Autowired
	private ConveniosProyectoAnexosRepository conveniosProyectosAnexosRepository;

	@Autowired
	private ParteSemanalAnexosProyectoRepository parteSemanalAnexosProyectoRepository;

	@Autowired
	private AlumnadoRepository alumnadoRepository;

	@Autowired
	private PardiaAluplanRepository pardiaAluplanRepository;

	@Autowired
	private PardiaAluplanActmodRepository pardiaAluplanActmodRepository;

	@Autowired
	private SedeEmpresaRepository sedeEmpresaRepository;
	
	@Autowired
	private IAlumnadoService alumnado;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
    @Autowired
    private IRodalClient rodalClient;

	@Autowired
	private HistoricoAltasProyRepository historicoAltasProyRepository;

	@Autowired
	private TiposProyectosRepository tiposProyectosRepository;
	
	@Autowired
	private ResultadosAsociadosPlanRepository resultadosAsociadosPlanRepository;

	@Autowired
	private ActividadesModulosRepository actividadesModulosRepository;

	@Autowired
	private ResultadosAprendizajeActividadesRepository resultadosAprendizajeActividadesRepository;

	@Autowired
	private InfoAdicionalPlanRepository infoAdicionalPlanRepository;

	@Autowired
	private ModulosEmpresasRepository modulosEmpresasRepository;

	@Autowired
	private AlumnoProgramaRepository alumnoProgramaRepository;

	@Autowired
	private ParsemAluplanRepository parsemAluplanRepository;


    @Autowired
    private ActividadesModulosService actividadesModulosService;

	@Autowired
	private ResultadosAsociadosPlanService resultadosAsociadosPlanService;

	@Autowired
	private EstadoPlanLOFPRepository estadoPlanLOFPRepository;


	@Override
	public List<ListadoProyectos> getAllProyectos(Long idCentro, Integer cAnno, Long idTutorActual, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad, Long idCurso) {
				
        List<ListadoProyectosProjection> proyectosProjection = proyectosRepository.getAllProyectos(idCentro, cAnno, idTutorActual, idTipoProyecto, idTutor, idFamilia, idModalidad,idCurso);
		
		return proyectosProjection.stream().map(entity -> modelMapper.map(entity, ListadoProyectos.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<TipoProyecto> getTiposListadoProyectos(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad) {
        List<Proyectos> proyectos = proyectosRepository.getAllProyectosByIdCentroAndCanno(idCentro, cAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);
		
		return proyectos.stream().map(Proyectos::getTipo).sorted(Comparator.comparing(p->p.getDs_nombre())).distinct().collect(Collectors.toList());
	}
	
	@Override
	public List<TutorFctDual> getTutoresListadoProyectos(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad) {
		List<Proyectos> proyectos = proyectosRepository.getAllProyectosByIdCentroAndCanno(idCentro, cAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);
		
		return proyectos.stream().map(Proyectos::getTutor).sorted(Comparator.comparing(p->p.getPuestoTrabajoEmpleado().getEmpleado().getApellido1())).distinct().collect(Collectors.toList());
	}
	
	@Override
	public List<Familia> getFamiliaListadoProyectos(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad) {
		List<Proyectos> proyectos = proyectosRepository.getAllProyectosByIdCentroAndCanno(idCentro, cAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);
		
		return proyectos.stream().map(Proyectos::getFamilia).sorted(Comparator.comparing(p->p.getDescripcionLarga())).distinct().collect(Collectors.toList());
	}
	
	@Override
	public List<Modalidad> getModalidadListadoProyectos(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad) {
		List<Proyectos> proyectos = proyectosRepository.getAllProyectosByIdCentroAndCanno(idCentro, cAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);
		
		return proyectos.stream().map(Proyectos::getModalidad).sorted(Comparator.comparing(p->p.getDescripcionLarga())).distinct().collect(Collectors.toList());
	}

	@Override
	public Proyectos createProyecto(Proyectos proyecto) {
		if (Integer.valueOf(1).equals(proyecto.getLgLofp())) {
			TipoProyecto tipoLofp = new TipoProyecto();
			tipoLofp.setId(1L);
			proyecto.setTipo(tipoLofp);
		}		
		return proyectosRepository.save(proyecto);
	}
	
	@Override
	public Proyectos updateProyecto(Proyectos proyecto) {
		
		Optional<Proyectos> proyectoUpdate = proyectosRepository.findById(proyecto.getId());
		
		if(proyectoUpdate.isPresent()) {
			proyectoUpdate.get().setDs_proyecto(proyecto.getDs_proyecto());
			proyectoUpdate.get().setC_anno_desde(proyecto.getC_anno_desde());
			proyectoUpdate.get().setC_anno_hasta(proyecto.getC_anno_hasta());
			proyectoUpdate.get().setNu_horas(proyecto.getNu_horas());
			proyectoUpdate.get().setTipo(proyecto.getTipo());
			proyectoUpdate.get().setTutor(proyecto.getTutor());
			proyectoUpdate.get().setFamilia(proyecto.getFamilia());
			proyectoUpdate.get().setModalidad(proyecto.getModalidad());
			proyectoUpdate.get().setLg_idiario(proyecto.getLg_idiario());
			proyectoUpdate.get().setLg_isemanal(proyecto.getLg_isemanal());
			proyectoUpdate.get().setLg_imensual(proyecto.getLg_imensual());
			proyectoUpdate.get().setLg_iotros(proyecto.getLg_iotros());
			proyectoUpdate.get().setLg_ivarias_empresas(proyecto.getLg_ivarias_empresas());
			proyectoUpdate.get().setLg_regimen(proyecto.getLg_regimen());


			proyectosRepository.save(proyectoUpdate.get());
		}else {
			String mes = crearMessage(Proyectos.class.getSimpleName(), proyecto.getId().toString());
			throw new NotFoundException(mes);
		}
		
		return proyectoUpdate.get();
	}

	@Override
	public Proyectos getProyectoId(Long idProyecto) {
		Optional<Proyectos> proyecto = proyectosRepository.findById(idProyecto);		
		
		return proyecto.isPresent() ? modelMapper.map(proyecto.get(), Proyectos.class) : null;
	}

	@Override
	public List<ConveniosProyecto> conveniosAproyecto(List<ConveniosProyecto> convproy, Long idProyecto) {
		
        // Cuando a los proyectos se puedan incluir alumnos se tendrá que modificar el métódo mirar  ConveniosProgramasService.convenioAprogramas
				
		// borramos todos los convenios del proyecto
		
		Optional<Proyectos> proyecto = proyectosRepository.findById(idProyecto);
		
		if (proyecto.isPresent()) deleteConveniosProyecto(idProyecto);
		
		convproy.forEach( elem -> {			
			conveniosProyectoRepository.save(elem);
		});		
		
		return convproy;
	}
	
	@Override
	public List<ModulosCurso> modulosAproyecto(List<ModulosCurso> modulos, Long idProyecto) {
		Proyectos proyecto = getProyectoId(idProyecto);

		for (ModulosCurso modulo : modulos) {
			if (modulo.getEsDelete() == 1 && modulo.getEsInsert() == 0) {
				eliminarModulo(modulo, proyecto.getId());
			} else if (modulo.getEsInsert() == 1 && modulo.getEsDelete() == 0) {
				anadirModulo(modulo, proyecto);
			}
		}

		return modulos;
	}
	
	private void guardarModulosCurso(ModulosCurso modulo, CursoProyecto cursoProyecto) {
		modulo.setCursoProyecto(cursoProyecto);
		modulosCursoRepository.save(modulo);
	}
	
	private CursoProyecto guardarCursoProyecto(CursoProyecto curso, Proyectos proyecto) {
		curso.setProyecto(proyecto);		
		return cursoProyectoRepository.save(curso);
	}	

	private void deleteResultadosAprendizaje(Long idCursoProyecto) {
		List<ResultadosAsociadosPlan> deleteResultadosList = resultadosAsociadosPlanRepository.findAllByModulosCursoId(idCursoProyecto);
		
		if(deleteResultadosList != null && !deleteResultadosList.isEmpty()) {
			resultadosAsociadosPlanRepository.deleteAll(deleteResultadosList);
		}	
	}
	
//	@Transactional
	public void deleteConveniosProyecto(Long idProyecto) {
		List<ConveniosProyecto> conveniosP = conveniosProyectoRepository.findByProyectoId(idProyecto);
		
		if (conveniosP.size()>0) {
			conveniosProyectoRepository.deleteAll(conveniosP);
		}	
	}

	@Override
	public List<ConveniosProyecto> getEmpresasProyecto(Long idProyecto) {
		return conveniosProyectoRepository.findByProyectoId(idProyecto);
	}
	
	@Override
	public List<ConveniosProyecto> getEmpresasProyectoByAnno(Long idProyecto, Integer cAnno) {
		
		List<ConveniosProyecto> conveniosProy = conveniosProyectoRepository.getByProyectoIdAnno(idProyecto,cAnno); 
		
		conveniosProy.forEach( convP -> {
			
			Integer numAnexo = conveniosProyectoRepository.getCountAnexos(convP.getId());
			convP.setNuAnexosII(numAnexo);		
			
			if (convP.getNuHorasTotales() == 0) {
				convP.setNuHorasTotales(conveniosProyectoRepository.getHorasProyecto(convP.getId()));				
			}
		});
		
		return conveniosProyectoRepository.getByProyectoIdAnno(idProyecto,cAnno);
	}

	@Override
	public List<CursoModalidad> getCursosModalidad(Long idModalidad, Integer cAnno, Integer idCentro, String idTipoMod ) {
		
		List<CursoModalidadProjection> cursoProjection = cursoProyectoRepository.findCursosByModalidad(idModalidad, cAnno, idCentro, idTipoMod);
		
		return cursoProjection.stream().map(entity -> modelMapper.map(entity, CursoModalidad.class)).collect(Collectors.toList());
	}

	@Override
	public List<ModuloModalidad> getModulosModalidad(Long idOfertamatrig, Long idModalidad, Integer cAnno, Integer idCentro) {
		List<ModuloModalidadProjection> modalidadProjection = modulosCursoRepository.findModuloByModalidad(idOfertamatrig, idModalidad, cAnno, idCentro);
		
		return modalidadProjection.stream().map(entity -> modelMapper.map(entity, ModuloModalidad.class)).collect(Collectors.toList());
	}

	@Override
	public List<ModuloProyecto> getModulosProyecto(Long idProyecto) {
		List<ModuloProyectosProjection> modalidadProjection = proyectosRepository.getModulosProyecto(idProyecto);
		
		return modalidadProjection.stream().map(entity -> modelMapper.map(entity, ModuloProyecto.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<Familia> getAllFamiliasCentro(Long idCentro, int cAnno, Long idTipo) {		
		
		List<FamiliaProjection> familiaProjection = proyectosRepository.allFamiliasCentro(idCentro,cAnno,idTipo); 
		
		return familiaProjection.stream()
				.map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());		

	}
	
	@Override
	public List<Familia> getAllFamiliasCentroTutor(Long idCentro, int cAnno, Long idTipo, Long idTutor) {
		List<FamiliaProjection> familiaProjection = proyectosRepository.allFamiliasCentroTutor(idCentro,cAnno,idTipo,idTutor); 
		
		return familiaProjection.stream()
				.map(entity -> modelMapper.map(entity, Familia.class)).collect(Collectors.toList());
	}

	@Override
	public ConveniosProyecto getConvenioProyecto(Long idConvProy) {
		Optional<ConveniosProyecto> convProyecto = conveniosProyectoRepository.findById(idConvProy);	
		
		return convProyecto.isPresent() ? modelMapper.map(convProyecto.get(), ConveniosProyecto.class) : null;
	}
	
	@Override
	public ConveniosProyectoAlumno getConvenioProyectoAlumno(Long idConvProyAlu) {
		
		
		Optional<ConveniosProyectoAlumno> convProyectoAlu = convProyAlumnoRepository.findById(idConvProyAlu);
		
		if(convProyectoAlu.isPresent()) {
			String  periodoEval= convProyAlumnoRepository.getPeriodoEval(convProyectoAlu.get().getConvenioProyecto().getId());
			if(convProyectoAlu.get().getNuHorasEva() == null || convProyectoAlu.get().getNuHorasEva() == 0 ) {
				convProyectoAlu.get().setNuHorasEva(convProyectoAlu.get().getConvenioProyecto().getNuHorasTotales()) ;
			}
			convProyectoAlu.ifPresent(convenioProyectoAlumno -> convenioProyectoAlumno.setPeriodo(periodoEval));
			return modelMapper.map(convProyectoAlu.get(), ConveniosProyectoAlumno.class);
		} else {
			throw new NotFoundException("No se ha encontrado el objeto relacionado con " + ConveniosProyectoAlumno.class.getSimpleName());
		}	
	}	
	
	@Override
	@Transactional
	public List<ConveniosProyectoAlumno> createAlumnoProyecto(Long idConvProy,
															  List<ConveniosProyectoAlumno> alumnosProyecto) {



		
		if (alumnosProyecto !=null && !alumnosProyecto.isEmpty()) {

			List<ConvProyHorPeriodoFctDto> listHorariosGenerales = getConvenioProyectoPeriodosHorarios(idConvProy);
			ConveniosProyecto convenioOrigen = getConvenioProyecto(idConvProy);

			for (ConveniosProyectoAlumno convProyAlu: alumnosProyecto) {

				if((idConvProy != null) && (idConvProy != 0) && (convProyAlu.getIdMatricula() != null)){

					switch(convProyAlu.getAccion()){
						case "C":
							createConvenioAlumnoProyecto(convProyAlu, convenioOrigen, listHorariosGenerales);
							break;
						case "U":
							updateConvenioAlumnoProyecto(convProyAlu);
							break;
						case "D":
							deleteConvenioAlumnoProyecto(idConvProy, convProyAlu);
							break;
						default:
							break;
					}
				}
			}
		}
		
		return alumnosProyecto;	
	}

	private void deleteConvenioAlumnoProyecto(Long idConvProy, ConveniosProyectoAlumno convProyAlu) {

		eliminarPartesDiariosYRelaciones(convProyAlu.getId());

		deleteAlta(convProyAlu);

		borrarPeriodos(idConvProy, convProyAlu.getIdMatricula());
		convProyAlumnoRepository.delete(convProyAlu);
	}

	private void deleteAlta(ConveniosProyectoAlumno convProyAlu) {
		List<AltasSegSociProy> aluSegSoci = altaSegSocialProyRepository.findByIdConvProyAlu(convProyAlu.getId());
		
//		if(aluSegSoci != null && (aluSegSoci.getFechaEnvio() == null || aluSegSoci.getLgAnulado() == 1)){
		if (aluSegSoci != null) {
			for(AltasSegSociProy altas: aluSegSoci) {

				trasladaIdentificadorSS(convProyAlu, altas);

				if(altas.getFechaEnvio() == null || Integer.valueOf(1).equals(altas.getLgAnulado())) {
					List<HistoricoAltasProy> listadoHistoricoAltasProy = historicoAltasProyRepository.findByIdAltassProy(altas.getId());
					List<CotizaMesProyectos> cotis = segSocialCotizaMesProyRepository.findByIdConvProyAlu(convProyAlu.getId());
					if (cotis.size() > 0) segSocialCotizaMesProyRepository.deleteAll(cotis);
					if (listadoHistoricoAltasProy.size() > 0)
						historicoAltasProyRepository.deleteAll(listadoHistoricoAltasProy);
					altaSegSocialProyRepository.deleteById(altas.getId());
				}
			}
		}
	}

	private void trasladaIdentificadorSS(ConveniosProyectoAlumno convProyAlu, AltasSegSociProy altas) {
		List<ConveniosProyectoAlumno> otrosConvProyAlu = obtenerOtrosConvenios(convProyAlu);

		if (!otrosConvProyAlu.isEmpty()) {
			// Actualizamos el idConvProyAlu en las altas y obtenemos el nuevo idConvProyAlu máximo
			Long nuevoIdConvProyAlu = actualizarIdConvProyAluAltas(altas, otrosConvProyAlu);

			// Ahora obtenemos las cotizaciones asociadas al convProyAlu actual
			List<CotizaMesProyectos> cotis = segSocialCotizaMesProyRepository.findByIdConvProyAlu(convProyAlu.getId());

			// Actualizamos el idConvProyAlu en las altas
			for (CotizaMesProyectos cotiza : cotis) {
				actualizarIdConvProyAluCotizacion(cotiza, nuevoIdConvProyAlu);
			}
		}
	}

	private List<ConveniosProyectoAlumno> obtenerOtrosConvenios(ConveniosProyectoAlumno convProyAlu) {
		List<ConveniosProyectoAlumno> otrosConvProyAlu = convProyAlumnoRepository.findByMatricula(convProyAlu.getIdMatricula());
		otrosConvProyAlu.removeIf(cp -> cp.getId().equals(convProyAlu.getId()));
		return otrosConvProyAlu;
	}

	private Long actualizarIdConvProyAluAltas(AltasSegSociProy altas, List<ConveniosProyectoAlumno> otrosConvProyAlu) {
		Long nuevoIdConvProyAlu = otrosConvProyAlu.stream()
				.mapToLong(ConveniosProyectoAlumno::getId)
				.max()
				.orElse(altas.getIdConvProyAlu());

		altas.setIdConvProyAlu(nuevoIdConvProyAlu);
		altaSegSocialProyRepository.save(altas);

		return nuevoIdConvProyAlu; // Retornamos el nuevo idConvProyAlu máximo
	}

	private void actualizarIdConvProyAluCotizacion(CotizaMesProyectos cotiza, Long nuevoIdConvProyAlu) {
		cotiza.setIdConvProyAlu(nuevoIdConvProyAlu);
		segSocialCotizaMesProyRepository.save(cotiza);
	}

	private void eliminarPartesDiariosYRelaciones(Long idConvProyAlu) {
		// Obtener los partes diarios asociados
		List<PardiaAluplan> partesDiarios = pardiaAluplanRepository.findPartesByIdConvProyAlu(idConvProyAlu);
		if (!partesDiarios.isEmpty()) {
			for (PardiaAluplan parte : partesDiarios) {
				// Obtener y eliminar las relaciones entre parte de día y actividades
				List<PardiaAluplanActmod> relaciones = pardiaAluplanActmodRepository.findByPardiaAluplanId(parte.getIdPardiaAluplan());
				if (!relaciones.isEmpty()) {
					for (PardiaAluplanActmod relacion : relaciones) {
						pardiaAluplanActmodRepository.delete(relacion);
					}
				}
				// Eliminar parte semanal asociado, si existe
				if (parte.getParsemAluplan() != null) {
					parsemAluplanRepository.deleteById(parte.getParsemAluplan().getIdParsemAluplan());
				}
			}
			// Eliminar los partes de día
			pardiaAluplanRepository.deleteAll(partesDiarios);
		}
	}


	private void updateConvenioAlumnoProyecto(ConveniosProyectoAlumno convProyAlu) {

		ConveniosProyectoAlumno newConvProyAlu = null;

		newConvProyAlu = convProyAlumnoRepository.findById(convProyAlu.getId()).orElse(null);

		if(newConvProyAlu != null) {

			newConvProyAlu.setLgCotiza(convProyAlu.getLgCotiza());
			newConvProyAlu.setLgErasmus(convProyAlu.getLgErasmus());
			//Actualiza el número de la Seguridad Social del alumno y actualiza el campo lgnuss de la tabla FCT_CONVPROY_ALU
			updateSegSocialAlumno(convProyAlu, newConvProyAlu);

			convProyAlumnoRepository.save(newConvProyAlu);
		}
	}

	private void createConvenioAlumnoProyecto(ConveniosProyectoAlumno convProyAlu, ConveniosProyecto convenioOrigen, List<ConvProyHorPeriodoFctDto> listHorariosGenerales) {

		ConveniosProyectoAlumno newConvProyAlu = null;

		newConvProyAlu = new ConveniosProyectoAlumno();
		newConvProyAlu.setOrientaciones(convProyAlu.getOrientaciones()); //campo necesario?
		newConvProyAlu.setConvenioProyecto(convenioOrigen);
		newConvProyAlu.setIdMatricula(convProyAlu.getIdMatricula());
		newConvProyAlu.setIdEvaluacion(convProyAlu.getIdEvaluacion()); //campo necesario?
		newConvProyAlu.setLgNuss(0);
		newConvProyAlu.setLgCotiza(convProyAlu.getLgCotiza());
		newConvProyAlu.setLgErasmus(convProyAlu.getLgErasmus());
		//Actualiza el número de la Seguridad Social del alumno y actualiza el campo lgnuss de la tabla FCT_CONVPROY_ALU
		updateSegSocialAlumno(convProyAlu, newConvProyAlu);

		convProyAlumnoRepository.save(newConvProyAlu);	

		//Se crean los períodos y tramos para ese alumno		
		anadirPeriodosGenerales(listHorariosGenerales, convProyAlu.getIdMatricula(), convenioOrigen.getId());	
	}

	private void updateSegSocialAlumno(ConveniosProyectoAlumno convProyAlu, ConveniosProyectoAlumno aluProy) {
		AlumnoProjection aluProj = alumnadoRepository.findAlumnoByIdMatricula(convProyAlu.getIdMatricula());

		Alumnado alum = alumnadoRepository.findById(aluProj.getId()).orElse(null);

		if (alum == null) {
			throw new NotFoundException("No se ha encontrado el alumno " + Alumno.class.getSimpleName());
		}

		if (!Objects.equal(alum.getTnuss(), convProyAlu.getTnuss())) {
			alum.setTnuss(convProyAlu.getTnuss());
			alumnadoRepository.save(alum);
			if (convProyAlu.getLgNuss() == 0) aluProy.setLgNuss(1);
		}
	}

	private void anadirPeriodosGenerales(List<ConvProyHorPeriodoFctDto> listHorariosGenerales, Long idMatricula, Long idConvProy){
		
		Optional<ConveniosProyecto> convenioProyecto = conveniosProyectoRepository.findById(idConvProy);	
		
		if (convenioProyecto.isPresent()) {
			
			listHorariosGenerales.forEach(periodoGeneral ->{
				
				Optional<ConvProyHorPeriodoFct> periodoGeneralFind = convProyHorPeriodoFctRepository.findById(periodoGeneral.getId());			
				
				if (periodoGeneralFind.isPresent()) {
					
					ConvProyAluHorPeriodoFct periodoAlumno = new ConvProyAluHorPeriodoFct();
					periodoAlumno.setIdMatricula(idMatricula);	
					periodoAlumno.setNhoras(periodoGeneralFind.get().getNhoras());
					periodoAlumno.setFechaInicio(periodoGeneralFind.get().getFechaInicio());
					periodoAlumno.setFechaFin(periodoGeneralFind.get().getFechaFin());
					periodoAlumno.setAnnoPeriodo(periodoGeneralFind.get().getAnnoPeriodo());
			        periodoAlumno.setConvenioProyecto(convenioProyecto.get());				    
			        ConvProyAluHorPeriodoFct periodoSave = convProyAluHorPeriodoFctRepository.save(periodoAlumno);
			        
			        List<ConvProyHorTramoFct> tramosGeneral = convProyHorTramoFctRepository.findAllByPeriodoHorarioId(periodoGeneralFind.get().getId());		        
			       
			        tramosGeneral.forEach(tramo -> {			        	
			        	ConvProyAluHorTramoFct tramoAlumno = new ConvProyAluHorTramoFct(); 			        	
			        	tramoAlumno.setHoraInicio(tramo.getHoraInicio());
			        	tramoAlumno.setHoraFin(tramo.getHoraFin());
			        	tramoAlumno.setDiaSemana(tramo.getDiaSemana());
			        	tramoAlumno.setOrdenTramo(tramo.getOrdenTramo());		
			        	tramoAlumno.setPeriodoAlumnoHorario(periodoSave);     
			        	convProyAluHorTramoFctRepository.save(tramoAlumno);	        	
			        });     
				    
				}						
				/*ConvProyAluHorPeriodoFct periodoAlumno = modelMapper.map(periodoGeneral, ConvProyAluHorPeriodoFct.class);
				periodoAlumno.setIdMatricula(idMatricula);
				periodoAlumno.setNhoras(periodoGeneral.getNHoras());
				
				ConvProyAluHorPeriodoFct periodoSave = convProyAluHorPeriodoFctRepository.save(periodoAlumno);
				for (int i = 0; i < periodoGeneral.getTramosHorarios().size(); i++) {
					ConvProyAluHorTramoFct tramoAlumno = modelMapper.map(periodoGeneral.getTramosHorarios().get(i), ConvProyAluHorTramoFct.class);
					tramoAlumno.setPeriodoAlumnoHorario(periodoSave);
					convProyAluHorTramoFctRepository.save(tramoAlumno);
				}; */				
			});			
		}		
	}
	
	
	
	
	private void borrarPeriodos(Long idConvProy, Long idMatricula){
		List<ConvProyAluHorPeriodoFctDto> listHorariosGenerales = getConvenioProyectoPeriodosHorariosAlumno(idConvProy, idMatricula);
		
		for (int i = 0; i < listHorariosGenerales.size(); i++) {
			
			ConvProyAluHorPeriodoFctDto periodoHor = listHorariosGenerales.get(i);
			
			for (int j = 0; j < periodoHor.getTramosHorarios().size(); j++) {
				convProyAluHorTramoFctRepository.deleteById(periodoHor.getTramosHorarios().get(j).getId());				
			}

			convProyAluHorPeriodoFctRepository.deleteById(periodoHor.getId());			
		}
		
		/*listHorariosGenerales.forEach( periodoHor -> {
			periodoHor.getTramosHorarios().forEach(tramosHor ->{
				ConvProyAluHorTramoFct tramo = modelMapper.map(tramosHor, ConvProyAluHorTramoFct.class);
				convProyAluHorTramoFctRepository.delete(tramo);
			});	
			ConvProyAluHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProyAluHorPeriodoFct.class);
			convProyAluHorPeriodoFctRepository.delete(periodo);
		}); */
		
		
	}
	
//	@Transactional
	public void deleteAlumnoProyecto(Long idConvProy) {
		List<ConveniosProyectoAlumno> alumnosPrograma = convProyAlumnoRepository.findAllByConvenioProyectoId(idConvProy);
		
		if (alumnosPrograma.size()>0) {
			Long idConvenioPrograma = alumnosPrograma.get(0).getConvenioProyecto().getId();	
			for (int i = 0; i < alumnosPrograma.size(); i++) {
				Long idMatricula = alumnosPrograma.get(i).getIdMatricula(); 
				borrarPeriodos(idConvenioPrograma, idMatricula);				
			}
			/*alumnosPrograma.forEach( aluProg -> {
				borrarPeriodos(aluProg.getConvenioProyecto().getId(), aluProg.getIdMatricula());
			}); */
			
			convProyAlumnoRepository.deleteAll(alumnosPrograma);
		}	
	}
	
	@Override
	public ConveniosProyectoAlumno updateAlumnoConvenioProyecto(ConveniosProyectoAlumno alumnoProyecto) {
		
		if ((alumnoProyecto.getConvenioProyecto() != null) && (alumnoProyecto.getConvenioProyecto().getId() != 0) && (alumnoProyecto.getIdMatricula() != null)) {
			
			Optional<ConveniosProyectoAlumno> aluProy = convProyAlumnoRepository.findById(alumnoProyecto.getId());
			
			ConveniosProyecto convenioOrigen = getConvenioProyecto(alumnoProyecto.getConvenioProyecto().getId());
			
			aluProy.get().setOrientaciones(alumnoProyecto.getOrientaciones());
			aluProy.get().setConvenioProyecto(convenioOrigen);
			aluProy.get().setIdMatricula(alumnoProyecto.getIdMatricula());
			aluProy.get().setIdEvaluacion(alumnoProyecto.getIdEvaluacion());
			aluProy.get().setIdEvaRodal(alumnoProyecto.getIdEvaRodal());
			aluProy.get().setTxEvafirFichero(alumnoProyecto.getTxEvafirFichero());			
			convProyAlumnoRepository.save(aluProy.get());
		}
		
		return alumnoProyecto;	
	}
	
	@Override
	public List<Alumno> getAlumnosSeleccionados(Long idCentro, int cAnno, Long idConvProg) {
		List<AlumnoProjection> alumnoProjection = convProyAlumnoRepository.getAlumnosConvenioSeleccionados(idConvProg); 
		List<Alumno> alumnos = alumnoProjection.stream().map(entity -> modelMapper.map(entity, Alumno.class)).collect(Collectors.toList());

		for (Alumno alumno: alumnos){
			alumno.setCotizaIntermitente(1);
			checkFechaEnvioAltasSSProy(alumno);
		}

		return alumnos;
	}

	private void checkFechaEnvioAltasSSProy(Alumno alumno) {
		List<AltasSegSociProy> altasSegSociProy = altaSegSocialProyRepository.findByIdConvProyAlu(alumno.getIdConvAlu());

		if(altasSegSociProy != null){
			for(AltasSegSociProy altas: altasSegSociProy) {
				if (altas.getFechaEnvio() == null) {
					checkFechaEnvioHistoricoProy(alumno, altas);
				} else {
					alumno.setCotizaIntermitente(0);
				}
			}
		}
	}

	private void checkFechaEnvioHistoricoProy(Alumno alumno, AltasSegSociProy altasSegSociProy) {
		List<HistoricoAltasProy> historicoAltasProyList = historicoAltasProyRepository.findByIdAltassProy(altasSegSociProy.getId());
		if(historicoAltasProyList != null){
			for (HistoricoAltasProy historicoAltasProy: historicoAltasProyList){
				if(historicoAltasProy.getFechaEnvio() != null){
					alumno.setCotizaIntermitente(0);
					break;
				}
			}
		}
	}
	
	@Override
	public ModulosCurso getModuloCurso(Long idModCurso) {
		Optional<ModulosCurso> moduloCurso = modulosCursoRepository.findById(idModCurso);
		
		if(!moduloCurso.isPresent()) {
			String mes = crearMessage(ModulosCurso.class.getSimpleName(), idModCurso.toString());
			throw new NotFoundException(mes);
		}
		
		return moduloCurso.isPresent() ? modelMapper.map(moduloCurso.get(), ModulosCurso.class) : null;
	}
	
	@Override
	public List<ModulosActividad> createModuloActividad(List<ModulosActividad> moduloActividad, Long idModuloCurso) {
		
		deleteModulosActividades(idModuloCurso);
		
		return (List<ModulosActividad>) modulosActividadRepository.saveAll(moduloActividad);
	}
	
	private void deleteModulosActividades(Long idModuloCurso) {
		List<ModulosActividad> modulosActividades = modulosActividadRepository.findAllByModuloCursoId(idModuloCurso);
		
		if(modulosActividades.size() > 0) {
			modulosActividadRepository.deleteAll(modulosActividades);
		}
	}
	
	@Override
	public ModulosActividad getModuloActividad(Long idModCurso, Long idDatProy) {
		Optional<ModulosActividad> moduloActividad = modulosActividadRepository.findByModuloCursoIdAndDatoProyectoId(idModCurso, idDatProy);
		
		if(!moduloActividad.isPresent()) {
			String mes = crearMessage(ModulosActividad.class.getSimpleName(), idModCurso.toString());
			throw new NotFoundException(mes);
		}
		
		return moduloActividad.isPresent() ? modelMapper.map(moduloActividad.get(), ModulosActividad.class) : null;
	}
	
	@Override
	public Boolean getIsCheckedModuloActividad(Long idModCurso, Long idDatProy) {
		Optional<ModulosActividad> moduloActividad = modulosActividadRepository.findByModuloCursoIdAndDatoProyectoId(idModCurso, idDatProy);
		
		return  moduloActividad.isPresent() ? true : false;
	}
	
	// Other methods
	private String crearMessage(String nameClass, String id) {
		return "No se ha encontrado el objeto relacionado con " + nameClass + " para el parámetro (" + id + ")\"";
	}

	@Transactional
	public void deleteProyecto(Long idProyecto) throws RodalExceptionService {
		
		
		Optional<InformacionProyectos> datosInformacionProyectos = informacionProyectosService.getInfoProyectoId(idProyecto);
		SecuenciacionProyectos secProy = secuenciacionProyectosRepository.findByProyectoId(idProyecto).orElse(null);

		if(datosInformacionProyectos.isPresent() && secProy != null) {

			if(secProy.getIdSecficRodal() != null && secProy.getTxSecficFichero() != null){

				rodalClient.borrarDocumento(secProy.getIdSecficRodal());

			}

			secuenciacionProyectosRepository.deleteById(secProy.getId());
			informacionProyectosService.deleteInformacionProyecto(datosInformacionProyectos.get().getId());

		}

		borrarProyectoSiPlan(idProyecto);

		proyectosRepository.deleteById(idProyecto);

		
	}

	@Transactional
	public void borrarProyectoSiPlan(Long idProyecto) {
		Optional<Proyectos> proyectoOptional = proyectosRepository.findById(idProyecto);

		if (proyectoOptional.isPresent()) {
			Proyectos proyecto = proyectoOptional.get();

			if (proyecto.getLgLofp() != null && proyecto.getLgLofp() == 1) {

				borradoCascadaPlan(idProyecto);
			}
		}
	}
	
	@Override
	public Integer countEmpresasProyecto(Long idProyecto) {
		return conveniosProyectoRepository.countByProyectoId(idProyecto);
	}

	@Override
	public Integer countModulosProyecto(Long idProyecto) {
		return modulosCursoRepository.countByCursoProyectoProyectoId(idProyecto);
	}

	@Override
	public byte[] exportReport(Long idConvProy, Long idMatricula) throws IOException, JRException  {
		 
		byte[] output = null; 
		InputStream dbAsStream = null; 
		 
		Resource resource = resourceLoader.getResource("classpath:reports/AnexoII_ProgramaFormativo.jrxml");
		dbAsStream = resource.getInputStream();     
		   
		Map<String, Object> parameters = new HashMap<>();   
		DatosCabeceraEvaluacionProjection parametros = null;  
		List<DatosProyectosFct> datosProyectoDataSource = null;
		Optional<ConveniosProyecto> convProy = conveniosProyectoRepository.findById(idConvProy);
		
		Alumno alumno = alumnado.getAlumno(idMatricula);		  	 
		
		if (convProy.isPresent()) {
		    parametros = proyectosRepository.getDatosCabeceraFCT(convProy.get().getId(), idMatricula);     
		  	 
		    datosProyectoDataSource = datosProyectosFctRepository.findAllByProyectoIdOrderByOrden(convProy.get().getProyecto().getId());
		}
		   
		JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(datosProyectoDataSource);
		
		Double horasTutoria  = alumnado.getHorasProyecto(idConvProy,idMatricula);		
			
		Integer horasConvenio = getNuHorasTotalesByidConvProy(idConvProy);
		
		String horasS =  (horasConvenio == 0)?String.valueOf(horasTutoria):String.valueOf(horasConvenio);;
		   
		if(parametros != null) {  
			String tituloCabecera = "<u>" + "PROGRAMA FORMATIVO DE FP DUAL" + "</u>";
		   	String centro = parametros.getCentro();
		   	String responsable = parametros.getResponsable();
		   	String nombre_tutor = parametros.getNombre_tutor();		   	
		   	String familia = parametros.getFamilia();
		   	String curso = parametros.getCurso();		   	
		   	String localidad = parametros.getLocalidad();		   	
		   	String centro_trabajo = parametros.getCentro_trabajo();
		   	String area = parametros.getArea();
//			String horasS = horas % 1 == 0 ? horas.toString().substring(0, horas.toString().indexOf('.')) : horas.toString();
		   	String nombre_alumno = alumno.getNombre();
		   	String periodo = parametros.getPeriodo();
		   	String codigo_centro = parametros.getCodigo_centro();
		   	
		   	//String sFirma = conveniosProyectoRepository.getFechaInicioConvenio(parametros.getIdconvenio());	
		   	String sFirma = conveniosProyectoRepository.getFechaActual();
		   	String descripcionFirma =  "En " + localidad  + " " + sFirma; 
		   	
//		   	String nombre_alumno = parametros.getNombre_alumno();		   	
//		   	String evaluacion = parametros.getEvaluacion();
//		   	String orientaciones = parametros.getOrientaciones();
		   	 
		   	parameters.put("nombre_alumnado", nombre_alumno);
		   	parameters.put("horas", horasS);
		   	parameters.put(CENTRO, centro);
		   	parameters.put("codigo_centro", codigo_centro);
		   	parameters.put("nombre_tutor", nombre_tutor);
		   	parameters.put(FAMILIA, familia);
		   	parameters.put("curso", curso);
		   	parameters.put("centro_trabajo", centro_trabajo);
		   	parameters.put("responsable", responsable);
		   	parameters.put("area", area);
		   	parameters.put("periodo", periodo);
		   	parameters.put(FIRMA, descripcionFirma);
		   	parameters.put("tituloCabeceraP", tituloCabecera);
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

	private Integer getNuHorasTotalesByidConvProy(Long idConvProy) {
		ConveniosProyecto convProy = conveniosProyectoRepository.findNuHorasTotalesById(idConvProy);
		return convProy.getNuHorasTotales();
	}

	@Override
	public byte[] exportReportAnexoI(Long idConvProy) throws IOException, JRException {

		byte[] outputAneI = null; 
	    InputStream dbAsStreamAneI = null; 
	     
	    Resource resourceAneI = resourceLoader.getResource("classpath:reports/anexoI_Alumnado_Dual.jrxml");
	    dbAsStreamAneI = resourceAneI.getInputStream();     
        
        Map<String, Object> parametersAneI = new HashMap<>();   
        DatosCabeceraAnexoIAlumnadoProjection parametrosAneI = null;  
        List<ListadoAnexoIAlumnadoProjection> listadoAnexoIAlumnadoProjection = null;
        Optional<ConveniosProyecto> convProy = conveniosProyectoRepository.findById(idConvProy);
        
        if (convProy.isPresent()) {
        	parametrosAneI = proyectosRepository.getDatosCabeceraAnexoIFCT(convProy.get().getId());
        } else {
            // Manejo del caso donde convProy está vacío
            throw new IllegalStateException("convProy no está presente");
        }
       	
       	listadoAnexoIAlumnadoProjection = proyectosRepository.getListadoAlumnadoAnexoI(idConvProy);
       	
        List<ListadoAnexoIAlumnadoFct> listadoAnexoIAlumnado = listadoAnexoIAlumnadoProjection.stream()
				.map(entity -> modelMapper.map(entity, ListadoAnexoIAlumnadoFct.class)).collect(Collectors.toList());
        
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(listadoAnexoIAlumnado);
        
        if(parametrosAneI != null) {
        	
        	String curso = getCurso(parametrosAneI);
        	String cursoAcademico = getAnno(parametrosAneI);
        	String tutor = getTutor(parametrosAneI);
        	String representante = getRepresentante(parametrosAneI);
        	String localidad = parametrosAneI.getLocalidad().toUpperCase();
        	String responsable = Objects.equal(null,parametrosAneI.getResponsable()) ? "          " : parametrosAneI.getResponsable().toUpperCase();
        	String familia = Objects.equal(null,parametrosAneI.getFamilia()) ? "_______________________________________" : parametrosAneI.getFamilia();
         	String primero = parametrosAneI.getPrimero();
         	String dniTutor = Objects.equal(null,parametrosAneI.getDniTutor()) ? RELLENO : parametrosAneI.getDniTutor();
         	String dniRep	= Objects.equal(null,parametrosAneI.getDireccion()) ? RELLENO : parametrosAneI.getDniTutor();

			String cabecera = "Relación del alumnado acogido al CONVENIO/ACUERDO de colaboración, número " + Optional.ofNullable(parametrosAneI.getNumconv()).orElse("_".repeat(15)) +
					" suscrito con fecha de " +	Optional.ofNullable(parametrosAneI.getDia()).orElse("_".repeat(3)) + " de " + Optional.ofNullable(parametrosAneI.getMes())
					.orElse("_".repeat(11)) + " de " + Optional.ofNullable(parametrosAneI.getAnno()).orElse("_".repeat(6)) + " entre el centro educativo " +
					Optional.ofNullable(parametrosAneI.getCentro()).map(String::toUpperCase).orElse("_".repeat(15)) + " y la empresa, entidad u organismo público o privado " +
					Optional.ofNullable(parametrosAneI.getEmpresa()).orElse("_".repeat(15)) + ", con Centro de Trabajo ubicado en " + Optional.ofNullable(parametrosAneI.getDireccion())
					.map(String::toUpperCase).orElse("_".repeat(20)) + " C.I.F " + Optional.ofNullable(parametrosAneI.getCif()).orElse("_".repeat(11)) +" correo electrónico " +
					Optional.ofNullable(parametrosAneI.getEmail()).orElse("_".repeat(11)) + " que desarrollarán el proyecto de Formación Profesional Dual con Código: " +
					Optional.ofNullable(parametrosAneI.getCodigo()).orElse("_".repeat(6));

			String bodyAneI = "En el cumplimiento de la cláusula tercera del Convenio/Acuerdo de colaboración, se procede a " +
					      "designar al tutor/a del centro educativo, que será D./Dña. " + parametrosAneI.getTutor() +
						  DNI + parametrosAneI.getDniTutor() + " , y el/la tutor/a de la empresa/organismo " +
						  "público o privado, que será D./Dña. " + parametrosAneI.getResponsable() + DNI + parametrosAneI.getDniRep();

		   	//String sFirma = conveniosFctRepository.getFechaInicioConvenio(parametros.getIdconvenio());
        	
        	String sFirmaAneI = conveniosFctRepository.getFechaHoy();
        	
		   	String descripcionFirma =  "En " + localidad  + " " + sFirmaAneI;  
		   	 
		   	DatosCentroConvenioProjection datosProyeccionAneI = conveniosFctRepository.findDatosCentro(parametrosAneI.getIdcentro());
		   	 
		   	parametersAneI.put("cabecera", cabecera);
		   	parametersAneI.put("body", bodyAneI);
		   	//parameters.put("diaP", dia);
		   	//parameters.put("mesP", mes);
		   	//parameters.put("annoP", anno);
		   	//parameters.put("centroP", centro);
		   	//parameters.put("empresaP", empresa);
		   	//parameters.put("direccionP", direccion);
		   	parametersAneI.put("dniTutorP",dniTutor);
		   	parametersAneI.put("dniRepP",dniRep);
		   	parametersAneI.put("cursoP", curso);
		   	parametersAneI.put("cursoAcademicoP", cursoAcademico);   
		   	parametersAneI.put("tutorP", tutor);
		   	parametersAneI.put("representanteP", representante);
		   	parametersAneI.put("responsableP", responsable);
		   	parametersAneI.put("directorP", Objects.equal(null,datosProyeccionAneI.getNombreCompleto()) ? "_____________" : datosProyeccionAneI.getNombreCompleto().toUpperCase());
		   	parametersAneI.put(FIRMA, descripcionFirma);
		   	parametersAneI.put(FAMILIA,familia);
		   	parametersAneI.put("primeroP", primero);
		   	parametersAneI.put("ds", datasource);   
        } 

        
        JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStreamAneI);      	 
    	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametersAneI,new JREmptyDataSource());
        
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.exportReport();
        outputAneI = outputStream.toByteArray();  
       
        return outputAneI;
	}
	
	@Override
	public List<ElementoSelect> getCentrosDelegacion(Long idUsuario, Long idPerfil, Long idCentro, Long idProvincia) {
		
		List<ElementoSelectProjection> centroProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil)) {
			
			centroProjection = proyectosRepository.allCentroDelegacionProvincias(idProvincia); 
			
		} else {
			centroProjection = proyectosRepository.allCentroDelegacion(idUsuario,idPerfil,idCentro); 
			
		}	
		
		return centroProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override

	public ConveniosProyectoAlumno getConvenioProyectoAlumnoByIdRodal(String idEvaRodal) {
		Optional<ConveniosProyectoAlumno> res = convProyAlumnoRepository.findByIdEvaRodal(idEvaRodal); 
		
		return res.isPresent() ? res.get() : null;
	}

	public List<ElementoSelect> getTutoresDelegacion(Long idUsuario, 
													 Long idPerfil, 
													 Long idCentro,
													 Long idCentroProvincia,
													 Integer idAnno,
													 Long idTipo,
													 Long idProvincia) {
		
		List<ElementoSelectProjection> tutoresProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil)) {
			
			 tutoresProjection = proyectosRepository.allTutoresDelegacionProvincias(idCentro,
																				    idAnno,
																				    idTipo,
																				    idProvincia); 
			
		} else {
			
			 tutoresProjection = proyectosRepository.allTutoresDelegacion(idUsuario,
																		  idPerfil,
																		  idCentro,
																		  idCentroProvincia,
																		  idAnno,
																		  idTipo); 
			
			
		}
		

		
		return tutoresProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ElementoSelect> getFamiliasProyectosDelegacion(Long idUsuario, 
															   Long idPerfil, 
															   Long idCentro,
															   Long idCentroProvincia, 
															   Integer idAnno,
															   Long idTipo,
															   Long idTutor,
															   Long idProvincia) {
		
		List<ElementoSelectProjection> familiasProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil)) {
			
			 familiasProjection = proyectosRepository.allFamiliasDelegacionProvincias(idCentro,																					
																					  idAnno,
																					  idTipo,
																					  idTutor,
																					  idProvincia); 
			
		} else {
			
			 familiasProjection = proyectosRepository.allFamiliasDelegacion(idUsuario,
					  idPerfil,
					  idCentro,
					  idCentroProvincia,
					  idAnno,
					  idTipo,
					  idTutor); 
			
			
		}
		
	
		
		

		return familiasProjection.stream()
		.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ElementoSelect> getModalidadesDelegacion(Long idUsuario, 
													     Long idPerfil, 
													     Long idCentro,
													     Long idCentroProvincia,
													     Integer idAnno,
													     Long idTipo, 
													     Long idTutor, 
													     Long idFamilia,
													     Long idProvincia) {
		
		List<ElementoSelectProjection> modalidadesProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil)) {
			
			 modalidadesProjection = proyectosRepository.allModalidadesDelegacionProvincias(idCentro,																		                    
																		                    idAnno,
																		                    idTipo,
																		                    idTutor,
																		                    idFamilia,
																		                    idProvincia);
			
		} else {
			
			 modalidadesProjection = proyectosRepository.allModalidadesDelegacion(idUsuario,
																                  idPerfil,
																                  idCentro,
																                  idCentroProvincia,
																                  idAnno,
																                  idTipo,
																                  idTutor,
																                  idFamilia);
			
		}
		
 

		return modalidadesProjection.stream()
				.map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
	}

	@Override
	public List<ListadoProyectos> getAllProyectosDelegacion(Long idUsuario, 
															Long idPerfil, 
															Long idCentro,
															Long idCentroProvincia, 
															Integer idAnno, 
															Long idTipo, 
															Long idTutor, 
															Long idFamilia, 
															Long idModalidad, 
															Long idProvincia,
															Long idCurso) {
		
		List<ListadoProyectosProjection> proyectosProjection = null;
		
		if (ID_PERFIL_DELEGACION.equals(idPerfil) || ID_PERFIL_DELEGACION_1.equals(idPerfil)) {
			
			proyectosProjection = proyectosRepository.getAllProyectosDelegacionProvincias(idCentro,																				 
																						  idAnno, 
																						  idTipo, 
																						  idTutor, 
																						  idFamilia, 
																						  idModalidad,
																						  idProvincia,
																						  idCurso);
			
		} else {
			
			proyectosProjection = proyectosRepository.getAllProyectosDelegacion(idUsuario,
																				idPerfil,
																				idCentro, 
																				idCentroProvincia, 
																				idAnno, 
																				idTipo, 
																				idTutor, 
																				idFamilia, 
																				idModalidad,
																				idCurso);
			
		}
		

		
		return proyectosProjection.stream().map(entity -> modelMapper.map(entity, ListadoProyectos.class)).collect(Collectors.toList());
	}

	@Override
	public List<ConveniosProyecto> createConvenioProyectoHorarioAlumno(
			List<ConveniosProyectosHorarioAlumnoFctDto> proconvDto) {
		
	   List<ConveniosProyecto> conveniosProgramasFctListIn = new ArrayList<>();
		
	   proconvDto.forEach( convProgHorAlu -> {
			ConveniosProyecto convProg = modelMapper.map(convProgHorAlu, ConveniosProyecto.class);
			convProg.setNuHorasTotales(convProgHorAlu.getNuHorasTotales());
			ConveniosProyecto convProgSave = conveniosProyectoRepository.save(convProg);

			if(convProgSave.getSedeResp() == null){
				SedeEmpresa sedeResp = sedeEmpresaRepository.findByIdTrabajador(convProgSave.getTrabajador().getId());
				if(sedeResp == null){
					sedeResp = sedeEmpresaRepository.findByIdConvenio(convProgSave.getConvenio().getId());
				}
				convProgSave.setSedeResp(sedeResp);
			}

			conveniosProgramasFctListIn.add(convProgSave);
			
			convProgHorAlu.getHorarioAlumno().forEach( periodoHor -> {
				ConvProyHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProyHorPeriodoFct.class);
				periodo.getConvenioProyecto().setId(convProgSave.getId());
				ConvProyHorPeriodoFct periodoSave = convProyHorPeriodoFctRepository.save(periodo);
				
				periodoHor.getTramosHorarios().forEach(tramosHor ->{
					ConvProyHorTramoFct tramo = modelMapper.map(tramosHor, ConvProyHorTramoFct.class);
					tramo.getPeriodoHorario().setId(periodoSave.getId());
					convProyHorTramoFctRepository.save(tramo);
				});				
			});
		});
		
		return conveniosProgramasFctListIn;

	}

	@Override
	public void deleteConvenioProyectosHorarioAlumno(
			List<ConveniosProyectosHorarioAlumnoFctDto> listConveniosProyectosHorAluFctDto) {
		listConveniosProyectosHorAluFctDto.forEach( convProyHorAlu -> {

            try {
                borrarAnexosConvenio(convProyHorAlu.getId());
			} catch (RodalExceptionService e) {
				throw new RuntimeException("Error al borrar los anexos del convenio con ID: " + convProyHorAlu.getId(), e);
			}

			borrarModulosEmpresa(convProyHorAlu.getId());
			
            convProyHorAlu.getHorarioAlumno().forEach( periodoHor -> {
				periodoHor.getTramosHorarios().forEach(tramosHor ->{
					ConvProyHorTramoFct tramo = modelMapper.map(tramosHor, ConvProyHorTramoFct.class);
					convProyHorTramoFctRepository.delete(tramo);
				});	
				ConvProyHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProyHorPeriodoFct.class);
				convProyHorPeriodoFctRepository.delete(periodo);
			});
			ConveniosProyecto convProg = modelMapper.map(convProyHorAlu, ConveniosProyecto.class);
			conveniosProyectoRepository.delete(convProg);
		});
		
	}

	private void borrarAnexosConvenio(Long idConvProy) throws RodalExceptionService {

		List<ConveniosProyectoAnexos> listConvProyAnex = conveniosProyectosAnexosRepository.findAllByIdConvProy(idConvProy);

		for (ConveniosProyectoAnexos anexo : listConvProyAnex) {
			rodalClient.borrarDocumento(anexo.getIdAnexoRodal());

			conveniosProyectosAnexosRepository.deleteById(anexo.getId());
		}
	}

	private void borrarModulosEmpresa(Long idConvProy) {
		List<ModulosEmpresas> modulosEmpresa = modulosEmpresasRepository.findAllByConvenioProyectoId(idConvProy);

		if (!modulosEmpresa.isEmpty()) {
			modulosEmpresasRepository.deleteAll(modulosEmpresa);
		}
	}
	
	@Override
	public List<ConvProyAluHorPeriodoFctDto> getConvenioProyectoPeriodosHorariosAlumno(Long idConvProy, Long idMatricula) {
		List<ConvProyAluHorPeriodoFctDto> listPeriodosDto = new ArrayList<>();
		
		List<ConvProyAluHorPeriodoFct> periodos = convProyAluHorPeriodoFctRepository.findAllByConvenioProyectoIdAndIdMatricula(idConvProy, idMatricula);
		
		periodos.forEach(periodo -> {
			List<ConvProyAluHorTramoFctDto> listTramosDto = convProyAluHorTramoFctRepository.findAllByPeriodoAlumnoHorarioId(periodo.getId())
					.stream().map(x -> modelMapper.map(x, ConvProyAluHorTramoFctDto.class)).collect(Collectors.toList());
			
			ConvProyAluHorPeriodoFctDto periodoDto = modelMapper.map(periodo, ConvProyAluHorPeriodoFctDto.class);
			periodoDto.setTramosHorarios(listTramosDto);
			listPeriodosDto.add(periodoDto);
		});

		return listPeriodosDto;
	}

	@Override
	public List<ConvProyHorPeriodoFctDto> getConvenioProyectoPeriodosHorarios(Long idConvProy) {
		List<ConvProyHorPeriodoFctDto> listPeriodosDto = new ArrayList<>();
		
		List<ConvProyHorPeriodoFct> periodos = convProyHorPeriodoFctRepository.findAllByConvenioProyectoId(idConvProy);
		
		periodos.forEach(periodo -> {
			List<ConvProyHorTramoFctDto> listTramosDto = convProyHorTramoFctRepository.findAllByPeriodoHorarioId(periodo.getId())
					.stream().map(x -> modelMapper.map(x, ConvProyHorTramoFctDto.class)).collect(Collectors.toList());
			
			ConvProyHorPeriodoFctDto periodoDto = modelMapper.map(periodo, ConvProyHorPeriodoFctDto.class);
			periodoDto.setTramosHorarios(listTramosDto);
			listPeriodosDto.add(periodoDto);
		});

		return listPeriodosDto;
	}

	@Override
	public List<ConvProyHorPeriodoFctDto> createConvenioProyectoPeriodosHorarios(List<ConvProyHorPeriodoFctDto> listConvProyHorPeriodoFctDto) {
		
		List<ConvProyHorPeriodoFctDto> listConvProyHorPeriodoFctOut = new ArrayList<>();
		
		if(listConvProyHorPeriodoFctDto.get(0) != null) {
			listConvProyHorPeriodoFctOut = getConvenioProyectoPeriodosHorarios(listConvProyHorPeriodoFctDto.get(0).getConvenioProyecto().getId());
			
			listConvProyHorPeriodoFctOut.forEach( periodoHor -> {
				periodoHor.getTramosHorarios().forEach(tramosHor ->{
					ConvProyHorTramoFct tramo = modelMapper.map(tramosHor, ConvProyHorTramoFct.class);
					convProyHorTramoFctRepository.delete(tramo);
				});	
				ConvProyHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProyHorPeriodoFct.class);
				convProyHorPeriodoFctRepository.delete(periodo);
			});
		}		
		
		listConvProyHorPeriodoFctDto.forEach( periodoHor -> {
			ConvProyHorPeriodoFct periodo = modelMapper.map(periodoHor, ConvProyHorPeriodoFct.class);
			ConvProyHorPeriodoFct periodoSave = convProyHorPeriodoFctRepository.save(periodo);
			
			periodoHor.getTramosHorarios().forEach(tramosHor ->{
				ConvProyHorTramoFct tramo = modelMapper.map(tramosHor, ConvProyHorTramoFct.class);
				tramo.getPeriodoHorario().setId(periodoSave.getId());
				convProyHorTramoFctRepository.save(tramo);
			});				
		});
		
		return listConvProyHorPeriodoFctOut;
	}

	
	@Override
	public List<ConvProyAluHorPeriodoFctDto> createConvenioProyectoPeriodosHorariosAlumno(List<ConvProyAluHorPeriodoFctDto> listConvProyHorPeriodoFctDto) {
		List<ConvProyAluHorPeriodoFctDto> listConvProyHorPeriodoFctOut = new ArrayList<>();
		if(!listConvProyHorPeriodoFctDto.isEmpty()) {
			borrarPeriodos(listConvProyHorPeriodoFctDto.get(0).getConvenioProyecto().getId(), listConvProyHorPeriodoFctDto.get(0).getIdMatricula());
			anadirPeriodos(listConvProyHorPeriodoFctDto);
			actualizaProyectoEmpresa(listConvProyHorPeriodoFctDto);
		}		
		return listConvProyHorPeriodoFctOut;
	}
	
     private void actualizaProyectoEmpresa(List<ConvProyAluHorPeriodoFctDto> listConvProyHorPeriodoFctDto){
		
		Date maxDateProy = getMaxDateAlumnoProy(listConvProyHorPeriodoFctDto);
		if (maxDateProy != null) {			
			ConveniosProyecto convProy= this.getConvenioProyecto(listConvProyHorPeriodoFctDto.get(0).getConvenioProyecto().getId());						
			if (convProy.getFechaFin().before(maxDateProy)) {				
				convProy.setFechaFin(maxDateProy);
				conveniosProyectoRepository.save(convProy);
				
			}
		}
	}
     
     private Date getMaxDateAlumnoProy(List<ConvProyAluHorPeriodoFctDto> listConvProyHorPeriodoFctDto){
 		
     	Optional<Date> maxFechaFinProy = listConvProyHorPeriodoFctDto.stream()
                 .map(ConvProyAluHorPeriodoFctDto::getFechaFin) // Obtenemos las fechas de fin
                 .filter(fecha -> fecha != null) // Filtramos para evitar nulos
                 .max(Date::compareTo); // Obtenemos la fecha máxima
         
         // Si no hay elementos o todos son nulos, devolverá null
         return maxFechaFinProy.orElse(null);
 	}      
     
	
	private void anadirPeriodos(List<ConvProyAluHorPeriodoFctDto> listPeriodosHorarios){
		
		listPeriodosHorarios.forEach(periodoGeneral ->{
			ConvProyAluHorPeriodoFct periodoAlumno = modelMapper.map(periodoGeneral, ConvProyAluHorPeriodoFct.class);
			
			ConvProyAluHorPeriodoFct periodoSave = convProyAluHorPeriodoFctRepository.save(periodoAlumno);			
			for (int i = 0; i < periodoGeneral.getTramosHorarios().size(); i++) {
				ConvProyAluHorTramoFct tramoAlumno = modelMapper.map(periodoGeneral.getTramosHorarios().get(i), ConvProyAluHorTramoFct.class);
				tramoAlumno.setPeriodoAlumnoHorario(periodoSave);
				convProyAluHorTramoFctRepository.save(tramoAlumno);
			}
		});
	}
	
	@Override
	public Integer countByConvenioProyectoId(Long idConvProy) {
		return convProyAlumnoRepository.countByConvenioProyectoId(idConvProy);
	}

	@Override
	public Integer getAnnoConvenioProyecto(Long idConvProy) {
		return conveniosProyectoRepository.getAnnoConvenioProyecto(idConvProy);
	}
	
	@Override
	public void updateFicherosAnexo(ConveniosProyecto convProy) {
		conveniosProyectoRepository.save(convProy);
	}

	@Override
	public void uploadFicherosAnexo(Long idConvProy, String tipo, List<MultipartFile> files)
			throws RodalExceptionService, InsertarDocFault, IOException {
		
		ConveniosProyecto convProy = conveniosProyectoRepository.findById(idConvProy).get();
		
		Long idCentro = conveniosProyectoRepository.getIdCentro(idConvProy);

		if("ANEXOI".equals(tipo)) {
			anexo1Upload(idConvProy, idCentro, convProy,  files.get(0));
		}else if("ANEXOII".equals(tipo)) {
			anexoIIUpload(idConvProy,idCentro, files);
		}
		
	}
	
	private void anexo1Upload(Long idConvProy, Long idCentro, ConveniosProyecto convProy, MultipartFile archivo) throws RodalExceptionService, IOException, InsertarDocFault {
		
    	if(convProy.getIdAneiRodal() == null) {
    		
			RespuestaInsertarDoc respuesta = rodalClient.insertaDoc(archivo, "MFCT", "CONV_PROY_", idConvProy, -1L, idCentro, -1L,"-1",-1L,-1L);
			
			convProy.setIdAneiRodal(respuesta.getIdDoc());
			convProy.setTxAneiFichero(archivo.getOriginalFilename());	
			conveniosProyectoRepository.save(convProy);				
				
	    }else {
			rodalClient.actualizaDoc(archivo, convProy.getIdAneiRodal());
				
			convProy.setTxAneiFichero(archivo.getOriginalFilename());
			conveniosProyectoRepository.save(convProy);
		}
	}
	
	private void anexoIIUpload(Long idConvProy, Long idCentro, List<MultipartFile> files) throws RodalExceptionService, IOException, InsertarDocFault {
		
		//Obtenemos la lista de documentos para el convenio que recibimos
		List<ConveniosProyectoAnexos> listConvProyAnex = conveniosProyectosAnexosRepository.findAllByIdConvProy(idConvProy);

		for (int i = 0; i < listConvProyAnex.size(); i++) {
			//eliminamos los registros de los documentos asociados al convenio que recibimos
			rodalClient.borrarDocumento(listConvProyAnex.get(i).getIdAnexoRodal());
			conveniosProyectosAnexosRepository.deleteById(listConvProyAnex.get(i).getId());
		}
		
		for (int i = 0; i < files.size(); i++) {
			
			ConveniosProyectoAnexos anexos = new ConveniosProyectoAnexos();
			anexos.setIdConvProy(idConvProy);
			conveniosProyectosAnexosRepository.save(anexos);
			
			try {				
				
				RespuestaInsertarDoc respuesta = rodalClient.insertaDoc(files.get(i), "MFCT", "CONV_PROY_ANEXO_", anexos.getId(), -1L, idCentro, -1L,"-1",-1L,-1L);
	 			
				anexos.setIdAnexoRodal(respuesta.getIdDoc());
				anexos.setNombreFichero(files.get(i).getOriginalFilename());
				conveniosProyectosAnexosRepository.save(anexos);
			
			 } catch (RodalExceptionService e) {					

				 rodalClient.borrarDocumento(listConvProyAnex.get(i).getIdAnexoRodal());
					conveniosProyectosAnexosRepository.deleteById(listConvProyAnex.get(i).getId());
					
           		throw new RodalExceptionService(e);			    
			} 
		}	
	}
	@Override
	public List<ConveniosProyectoAnexos> getAnexoII(Long idConvProy) {
		return conveniosProyectosAnexosRepository.findAllByIdConvProy(idConvProy);
	}

	@Override
	public List<ParteSemanalAnexosProyecto> uploadFilesParteSemanal(Long idConvProyAlu, List<MultipartFile> files) throws RodalExceptionService, IOException, InsertarDocFault {

		ListadoAnioCentroProjection anioCentro = parteSemanalAnexosProyectoRepository.getAnioAndIdCentro(idConvProyAlu);

		ListadoAnioCentro anioCentroE = modelMapper.map(anioCentro, ListadoAnioCentro.class);

		List<ParteSemanalAnexosProyecto> listParsemProyAlu = parteSemanalAnexosProyectoRepository.findAllByIdConvProyAlu(idConvProyAlu);

        for (ParteSemanalAnexosProyecto parteSemanalAnexosProyecto : listParsemProyAlu) {
            rodalClient.borrarDocumento(parteSemanalAnexosProyecto.getIdAnexoRodal());
            parteSemanalAnexosProyectoRepository.deleteById(parteSemanalAnexosProyecto.getId());
        }

        for (MultipartFile file : files) {

            ParteSemanalAnexosProyecto parsem = new ParteSemanalAnexosProyecto();
            parsem.setIdConvProyAlu(idConvProyAlu);
			parteSemanalAnexosProyectoRepository.save(parsem);

            RespuestaInsertarDoc respuesta = rodalClient.insertaDoc(file, "MFCT", "PARSEM_ANEX_PROY_", parsem.getId(), anioCentroE.getAnio(), anioCentroE.getIdCentro(), -1L,"-1",-1L,-1L);

			parsem.setIdAnexoRodal(respuesta.getIdDoc());
			parsem.setTxAnexoFichero(file.getOriginalFilename());
			parteSemanalAnexosProyectoRepository.save(parsem);

        }

		return parteSemanalAnexosProyectoRepository.findAllByIdConvProyAlu(idConvProyAlu);

	}

	@Override
	public List<ParteSemanalAnexosProyecto> getFilesParteSemanal(Long idConvProyAlu) {
		return parteSemanalAnexosProyectoRepository.findAllByIdConvProyAlu(idConvProyAlu);
	}

	@Override
	public List<Alumno> getAlumnosProyectos(Long idCentro, Long idOfertamatrig, int cAnno, Long idConvProy) {
		
		List<AlumnoProjection> alumnoProjection = conveniosProyectoRepository.getAlumnosConvenio(idCentro, idOfertamatrig, cAnno, idConvProy); 
		
		return alumnoProjection.stream().map(entity -> modelMapper.map(entity, Alumno.class)).collect(Collectors.toList());
	}

	@Override
	public void updateAlumSeleccionados(List<AlumnoDto> alumSeleccionados) {

		//Recorrer la lista
		for (AlumnoDto alum: alumSeleccionados){
			//Actualizar lgCotiza de la tabla FCT_CONVPROY_ALU mediante el idConvProyAlu
            ConveniosProyectoAlumno convProyAlu =  convProyAlumnoRepository.findById(alum.getIdConvAlu()).orElse(null);

			if(convProyAlu != null){
				convProyAlu.setLgCotiza(alum.getLgCotiza());
				convProyAlu.setLgErasmus(alum.getLgErasmus()); 
				//Obtener el objeto Alumno
				Alumnado alu = alumnadoRepository.findById(alum.getId()).orElse(null);				
				
				if(alu != null)
				{
					//Comprobar si es necesario actualizar el TNUSS					
					if (!Objects.equal(alum.getTnuss(), alu.getTnuss())) {
						alu.setTnuss(alum.getTnuss());
						alumnadoRepository.save(alu);						
						if (convProyAlu.getLgNuss() == 0) convProyAlu.setLgNuss(1);
					}
					
					compruebaAltasSegSoc(alum, convProyAlu);

					//Guardamos el objeto convenioProgramaAlumno
					convProyAlumnoRepository.save(convProyAlu);								 
				}			
				
			}

        }

	}

	private void compruebaAltasSegSoc(AlumnoDto alum, ConveniosProyectoAlumno convProyAlu) {
		//Obtenemos el objeto de altas en la seg social de un alumno para un proyecto
		List<AltasSegSociProy> altasSegSociProy = altaSegSocialProyRepository.findByIdConvProyAlu(convProyAlu.getId());
		//Cmprobamos si existe registro, si tiene no tiene fecha de envío y si el LgCotiza que recibimos es un 0 (deja de cotizar)
//		if(altasSegSociProy != null && altasSegSociProy.getFechaEnvio() == null && alum.getLgCotiza() == 0){
		if(altasSegSociProy != null) {
			for(AltasSegSociProy altas: altasSegSociProy) {
				if (altas.getFechaEnvio() == null && alum.getLgCotiza() == 0) {
					//Comprobamos si no tiene histórico asociados al registro de la tabla de altasSS
					List<HistoricoAltasProy> listadoHistoricoAltasProy = historicoAltasProyRepository.findByIdAltassProy(altas.getId());
					//Si no existe eliminamos de la tabla de altaSS
					if (listadoHistoricoAltasProy.isEmpty()) {
						altaSegSocialProyRepository.delete(altas);
					}
				}
			}
		}
	}

	@Override
	public Integer checkOverlappingDates(Long idConvenio, Long idProyecto, String fechaInicio, String fechaFin, Long idResponsable) throws ParseException {

		//Obtengo el listado de Proyecto que pertenecen a un convenio
		List<ConveniosProyecto> conveniosProyectos = conveniosProyectoRepository.findByConvenioIdAndProyectoIdAndTrabajadorId(idConvenio, idProyecto, idResponsable);
		Integer puedeCrear = 1; //0 no puede crear, 1 si puede crear

		//Compruebo si la lista no está vacía
		if(!conveniosProyectos.isEmpty()){
			//Recorro la lista de proyecto
			puedeCrear = checkEachProgram(fechaInicio, fechaFin, conveniosProyectos, puedeCrear);
		}

		return puedeCrear;

	}

	private static Integer checkEachProgram(String fechaInicio, String fechaFin, List<ConveniosProyecto> conveniosProyectos, Integer puedeCrear) throws ParseException {
		for (ConveniosProyecto conveniosProyecto: conveniosProyectos){

			puedeCrear = checkDatesOfEachProgram(fechaInicio, fechaFin, conveniosProyecto);
		}
		return puedeCrear;
	}

	private static Integer checkDatesOfEachProgram(String fechaInicio, String fechaFin, ConveniosProyecto conveniosProyecto) throws ParseException {
		int puedeCrear;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaIni = dateFormat.parse(fechaInicio);
		Date fechaFi = dateFormat.parse(fechaFin);

		//Compruebo si las fechas del nuevo programa se solapan con las fechas de cada programa
		if(fechaIni.before(conveniosProyecto.getFechaFin()) && conveniosProyecto.getFechaIni().before(fechaFi)){
			puedeCrear = 0; //Las fechas se solapan
		}else{
			puedeCrear = 1; //Las fechas no se solapan
		}
		return puedeCrear;
	}

	@Override
	public void updateProyectDates(Long idConvProy, String fechaInicio, String fechaFin,Integer newHoras) throws ParseException {

		ConveniosProyecto proyecto = conveniosProyectoRepository.findById(idConvProy).orElse(null);

		if(proyecto != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaIni = dateFormat.parse(fechaInicio);
			Date fechaFi = dateFormat.parse(fechaFin);
			proyecto.setFechaIni(fechaIni);
			proyecto.setFechaFin(fechaFi);
			proyecto.setNuHorasTotales(newHoras);
			conveniosProyectoRepository.save(proyecto);
		}
	}

	@Override
	public Integer getProyectoUsado(Long id) {
		return proyectosRepository.getProyectoUsado(id);
	}

	@Override
	public List<ElementoSelect> getCursosCentroAnnoTutorFamiliaModalidad(Long idCentro, Integer cAnno, Long idTipoProyecto, Long idTutor, Long idFamilia, Long idModalidad) {
		List<ElementoSelectProjection> cursoProjection =
				proyectosRepository.findCursosCentroAnnoTutorFamiliaModalidad(idCentro, cAnno, idTipoProyecto, idTutor, idFamilia, idModalidad);

		return Optional.ofNullable(cursoProjection)
				.map(cursos -> cursos.stream()
						.map(entity -> modelMapper.map(entity, ElementoSelect.class))
						.collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

	@Transactional
	@Override
	public List<ModulosCurso> createModuloPlan(List<ModulosCurso> modulos, Long idProyecto) {
		Proyectos proyecto = getProyectoId(idProyecto);

		CursoProyecto cursoProyecto = new CursoProyecto();

		for (int i = 0; i < modulos.size(); i++) {

			if (modulos.get(i).getEsDelete() == 1 && modulos.get(i).getEsInsert() == 0) {

				Long idCurso = modulosCursoRepository.findById(modulos.get(i).getId())
						.map(modulo -> modulo.getCursoProyecto().getId())
						.orElseThrow(() -> new NoSuchElementException("No se encontró el módulo con ID: "));
				deleteModuloCurso(modulos.get(i).getId());

				boolean existenModulos = modulosCursoRepository.existsByCursoProyectoId(idCurso);

				if (!existenModulos) {
					List<CursoProyecto> deleteCursoProyectoList = cursoProyectoRepository.findAllByProyectoId(idProyecto);
					cursoProyectoRepository.deleteAll(deleteCursoProyectoList);
				}
			} else if (modulos.get(i).getEsInsert() == 1 && modulos.get(i).getEsDelete() == 0) {

				if (!omgExiste(modulos.get(i).getCursoProyecto().getIdOfertamatrig().getId(), idProyecto)) {
					guardarCursoProyecto(modulos.get(i).getCursoProyecto(), proyecto);
				}

				cursoProyecto = cursoProyectoRepository.findByIdOfertamatrigIdAndProyectoId(modulos.get(i).getCursoProyecto().getIdOfertamatrig().getId(), idProyecto);

				guardarModulosCurso(modulos.get(i), cursoProyecto);
			}
		}

		return modulos;
	}

	private void deleteModuloCurso(Long idModulo) {
		deleteActividadesYResultados(idModulo);
		deleteResultadosAprendizaje(idModulo);
		modulosCursoRepository.deleteById(idModulo);
	}

	private void deleteActividadesYResultados(Long idModulo) {
		// Listado de Actividades relacionadas con el módulo
		List<ActividadesModulos> actividades = actividadesModulosRepository.findAllByIdModuloCurso(idModulo);

		// Eliminar cada actividad y sus resultados de aprendizaje asociados
		for (ActividadesModulos actividad : actividades) {
			resultadosAprendizajeActividadesRepository.deleteByIdActividadModulo(actividad.getIdActividadModulo());

			actividadesModulosRepository.delete(actividad);
		}
	}

	@Transactional
	public void borradoCascadaPlan(Long idProyecto) {
		// Obtener los módulos asociados al proyecto
		List<ModulosCurso> modulosCurso = modulosCursoRepository.findAllModuloCursoByProyectoId(idProyecto);

		// Borrar actividades y resultados de aprendizaje para cada módulo
		for (ModulosCurso modulo : modulosCurso) {
			deleteActividadesYResultados(modulo.getId());
		}

		// Borrar resultados de aprendizaje directamente asociados a los módulos
		for (ModulosCurso modulo : modulosCurso) {
			deleteResultadosAprendizaje(modulo.getId());
		}

		// Borrar los módulos
		modulosCursoRepository.deleteAll(modulosCurso);

		// Borrar los cursos asociados al proyecto
		List<CursoProyecto> cursosProyecto = cursoProyectoRepository.findAllByProyectoId(idProyecto);
		cursoProyectoRepository.deleteAll(cursosProyecto);

		InfoAdicionalPlan infoAdicionalPlan = infoAdicionalPlanRepository.findByIdProyecto(idProyecto);
		if (infoAdicionalPlan != null) {
			infoAdicionalPlanRepository.delete(infoAdicionalPlan);
		}
	}

	@Transactional
	public ProyectosDto copiarPlan(Long idProyectoOriginal, ProyectosDto proyectoDto) {
		try {
			
			
			if (proyectosRepository.getDescripcionUsada(proyectoDto.getCentro().getId(),
					                                    proyectoDto.getDs_proyecto()) > 0) 
			{
			    throw new ResponseStatusException(HttpStatus.CONFLICT,
			    		"Ya existe un plan individualizado con la misma descripción.");					                                                    	   
            }			
			
			// Obtener el proyecto original Fix sonnar
			//proyectosRepository.findById(idProyectoOriginal)
			//		.orElseThrow(() -> new RuntimeException("No se encontró el proyecto con ID: " + idProyectoOriginal));
			
			if (!proyectosRepository.existsById(idProyectoOriginal)) {
			    throw new RuntimeException("No se encontró el proyecto con ID: " + idProyectoOriginal);
			}

			// Crear nuevo proyecto
			Proyectos nuevoProyecto = crearNuevoProyecto(proyectoDto);

			// Convertir a DTO
			ProyectosDto nuevoProyectoDto = modelMapper.map(nuevoProyecto, ProyectosDto.class);

			// Copiar información adicional
			copiarInformacionAdicional(idProyectoOriginal, nuevoProyecto.getId());

			// Copiar módulos, actividades y resultados
			copiarModulosYActividades(idProyectoOriginal, nuevoProyecto);

			return nuevoProyectoDto;
			
			
		} catch (Exception e) {
			throw new RuntimeException("Error al copiar el plan", e);
		}
	}

	private Proyectos crearNuevoProyecto(ProyectosDto proyectoDto) {
		Proyectos nuevoProyecto = new Proyectos();
		modelMapper.map(proyectoDto, nuevoProyecto);
		nuevoProyecto.setId(null);
		return proyectosRepository.save(nuevoProyecto);
	}

	private void copiarInformacionAdicional(Long idProyectoOriginal, Long idNuevoProyecto) {
		InfoAdicionalPlan infoAdicionalPlan = infoAdicionalPlanRepository.findByIdProyecto(idProyectoOriginal);
		if (infoAdicionalPlan != null) {
			InfoAdicionalPlan nuevaInfoAdicional = new InfoAdicionalPlan();
			modelMapper.map(infoAdicionalPlan, nuevaInfoAdicional);
			nuevaInfoAdicional.setId(null);
			nuevaInfoAdicional.setIdProyecto(idNuevoProyecto);
			infoAdicionalPlanRepository.save(nuevaInfoAdicional);
		}
	}

	private void copiarModulosYActividades(Long idProyectoOriginal, Proyectos nuevoProyecto) {
		Map<Long, Long> mapaResultadosCopiados = new HashMap<>();

		List<ModulosCurso> modulosCursoOriginales = modulosCursoRepository.findAllModuloCursoByProyectoId(idProyectoOriginal);

		for (ModulosCurso moduloOriginal : modulosCursoOriginales) {
			// Copiar módulo
			ModulosCurso nuevoModulo = copiarModulo(moduloOriginal, nuevoProyecto);

			// Copiar resultados de aprendizaje del módulo
			copiarResultadosModulo(moduloOriginal, nuevoModulo, mapaResultadosCopiados);

			// Copiar actividades y sus resultados
			copiarActividadesYResultados(moduloOriginal, nuevoModulo, mapaResultadosCopiados);
		}
	}

	private ModulosCurso copiarModulo(ModulosCurso moduloOriginal, Proyectos nuevoProyecto) {
		ModulosCurso nuevoModulo = new ModulosCurso();
		modelMapper.map(moduloOriginal, nuevoModulo);
		nuevoModulo.setId(null);

		if (moduloOriginal.getCursoProyecto() != null) {
			CursoProyecto nuevoCursoProyecto = new CursoProyecto();
			modelMapper.map(moduloOriginal.getCursoProyecto(), nuevoCursoProyecto);
			nuevoCursoProyecto.setId(null);
			nuevoModulo.setCursoProyecto(nuevoCursoProyecto);
		}

		anadirModulo(nuevoModulo, nuevoProyecto);
		return nuevoModulo;
	}

	private void copiarResultadosModulo(ModulosCurso moduloOriginal, ModulosCurso nuevoModulo, Map<Long, Long> mapaResultadosCopiados) {
		List<ResultadosAsociadosPlan> resultadosModulo = resultadosAsociadosPlanRepository.findAllByModulosCursoId(moduloOriginal.getId());

		for (ResultadosAsociadosPlan resultadoOriginal : resultadosModulo) {
			ResultadosAsociadosPlan nuevoResultadoModulo = new ResultadosAsociadosPlan();
			modelMapper.map(resultadoOriginal, nuevoResultadoModulo);
			nuevoResultadoModulo.setIdResultadoaModulo(null);
			nuevoResultadoModulo.setModulosCurso(nuevoModulo);

			ResultadosAsociadosPlan resultadoGuardado = resultadosAsociadosPlanRepository.save(nuevoResultadoModulo);
			mapaResultadosCopiados.put(resultadoOriginal.getIdResultadoaModulo(), resultadoGuardado.getIdResultadoaModulo());
		}
	}

	private void copiarActividadesYResultados(ModulosCurso moduloOriginal, ModulosCurso nuevoModulo, Map<Long, Long> mapaResultadosCopiados) {
		List<ActividadesModulos> actividadesOriginales = actividadesModulosRepository.findAllByIdModuloCurso(moduloOriginal.getId());

		for (ActividadesModulos actividadOriginal : actividadesOriginales) {
			ActividadesModulos nuevaActividad = new ActividadesModulos();
			modelMapper.map(actividadOriginal, nuevaActividad);
			nuevaActividad.setIdActividadModulo(null);
			nuevaActividad.setIdModuloCurso(nuevoModulo.getId());

			ActividadesModulos actividadGuardada = actividadesModulosRepository.save(nuevaActividad);

			copiarResultadosActividades(actividadOriginal, actividadGuardada, mapaResultadosCopiados);
		}
	}

	private void copiarResultadosActividades(ActividadesModulos actividadOriginal, ActividadesModulos actividadGuardada, Map<Long, Long> mapaResultadosCopiados) {
		List<ResultadosAprendizajeActividades> resultadosActividad =
				resultadosAprendizajeActividadesRepository.findByIdActividadModulo(actividadOriginal.getIdActividadModulo());

		for (ResultadosAprendizajeActividades resultadoOriginal : resultadosActividad) {
			if (mapaResultadosCopiados.containsKey(resultadoOriginal.getIdResultadoaModulo())) {
				ResultadosAprendizajeActividades nuevoResultado = new ResultadosAprendizajeActividades();
				nuevoResultado.setIdResultadoaActividad(null);
				nuevoResultado.setIdActividadModulo(actividadGuardada.getIdActividadModulo());
				nuevoResultado.setIdResultadoaModulo(mapaResultadosCopiados.get(resultadoOriginal.getIdResultadoaModulo()));

				resultadosAprendizajeActividadesRepository.save(nuevoResultado);
			}
		}
	}



	private Boolean omgExiste(Long idOfertaMatrig, Long idProyecto){
		CursoProyecto curso = cursoProyectoRepository.findByIdOfertamatrigIdAndProyectoId(idOfertaMatrig, idProyecto);
		return curso!=null;
	}
	
	@Override
	public List<AlumnoVinculacionEmpresasDto> getAlumnosProyectosModalidad(Long idCentro, Long idModalidad, int cAnno, Long idConvProy) {
		
		List<AlumnoProjection> alumnoProjection = conveniosProyectoRepository.getAlumnosConvenioModalidad(idCentro, idModalidad, cAnno, idConvProy); 
		
		return alumnoProjection.stream().map(entity -> modelMapper.map(entity, AlumnoVinculacionEmpresasDto.class)).collect(Collectors.toList());
	}

	@Override
	public InfoAdicionalPlan createInfoAdicionalPlan(InfoAdicionalPlan infoAdicionalPlan) {
		Long idProyecto = infoAdicionalPlan.getIdProyecto();
		if (!proyectosRepository.existsById(idProyecto)) {
			return null;
		}
		return infoAdicionalPlanRepository.save(infoAdicionalPlan);
	}

	@Override
	public InfoAdicionalPlan updateInfoAdicionalPlan(InfoAdicionalPlan infoAdicionalPlan) {
		if (!infoAdicionalPlanRepository.existsById(infoAdicionalPlan.getId())) {
			return null;
		}
		return infoAdicionalPlanRepository.save(infoAdicionalPlan);
	}

	@Override
	public InfoAdicionalPlan getInfoAdicionalPlanByIdProyecto(Long idProyecto) {
		return infoAdicionalPlanRepository.findByIdProyecto(idProyecto);
	}

	@Override
	public byte[] exportReportPlan(Long idConvProy, Long idMatricula) throws IOException, JRException {

		byte[] output = null;
		InputStream dbAsStream = null;

		Resource resource = resourceLoader.getResource("classpath:reports/AnexoII_LOFP.jrxml");
		dbAsStream = resource.getInputStream();


		DatosCabeceraAnexo2PlanProjection parametrosPlan = null;

		parametrosPlan = obtenerDatosCabeceraAnexo2Plan(idConvProy,idMatricula);

		JRBeanCollectionDataSource dataSetTablaEmpresas = obtenerDatosTablaEmpresas(idConvProy,idMatricula);

     	JRBeanCollectionDataSource dataSetTablaPeriodos = obtenerDatosTablaPeriodos(idConvProy, idMatricula);

		DatosTablaFormEspAnexo2PlanProjection formacionesEspecificas = obtenerDatosTablaFormacionEspecificaAnexo2Plan(idMatricula);
		List<DatosTablaHorariosAnexo2PlanProjection> listaSumaHoras = (List<DatosTablaHorariosAnexo2PlanProjection>) dataSetTablaPeriodos.getData();

		Double totalHoras = listaSumaHoras.stream()
				.mapToDouble(DatosTablaHorariosAnexo2PlanProjection::getHoras)
				.sum();

		List<DatosTablaModulosAnexo2LOFP> dataSetTablaModulos = obtenerDatosTablaModulos(idConvProy,idMatricula);

		Resource resourceSubReport = resourceLoader.getResource("classpath:reports/SubReportAnexoII_LOFP.jrxml");
		JasperReport subReport = JasperCompileManager.compileReport(resourceSubReport.getInputStream());


		List<DatosTablaCoorAnexo2LOFP>  tablaCoordinacion = exportarMecanismoCoordinacionSeguimientoHtmlToTxtPlano(idMatricula);


		Date fechaValidacion = getFechaPlanValidado(idMatricula,"VA");

		Resource resourceSubReportFormaciones = resourceLoader.getResource(tablaCoordinacion.get(0).getHeaders()==null?"classpath:reports/SubReportAnexoIIStringFormaciones.jrxml":"classpath:reports/SubReportAnexoIITablaFormaciones.jrxml");
		JasperReport subReportFormaciones = JasperCompileManager.compileReport(resourceSubReportFormaciones.getInputStream());

		Map<String, Object> parametersPlan = new HashMap<>();
		if(parametrosPlan != null) {
			parametersPlan.put("regimen", parametrosPlan.getRegimen());
			parametersPlan.put("fecha", parametrosPlan.getFecha());
			parametersPlan.put("academico", parametrosPlan.getAcademico());
			parametersPlan.put("orden", parametrosPlan.getOrden());
			parametersPlan.put("ciclo", parametrosPlan.getCiclo());
			parametersPlan.put("grupo", parametrosPlan.getGrupo());
			parametersPlan.put("nombre", parametrosPlan.getNombre());
			parametersPlan.put("dni", parametrosPlan.getDni());
			parametersPlan.put("nuss", parametrosPlan.getNuss());
			parametersPlan.put("email", parametrosPlan.getEmail());
			parametersPlan.put("telefono", parametrosPlan.getTelefono());
			parametersPlan.put("nacimiento", parametrosPlan.getNacimiento());
			parametersPlan.put("basico", parametrosPlan.getBasico());
			parametersPlan.put("certificacion", parametrosPlan.getCertificacion());
			parametersPlan.put("especificar", parametrosPlan.getEspecificar());
			parametersPlan.put(CENTRO, parametrosPlan.getCentro());
			parametersPlan.put("mailcen", parametrosPlan.getMailcen());
			parametersPlan.put("codigo", parametrosPlan.getCodigo());
			parametersPlan.put("tutor", parametrosPlan.getTutor());
			parametersPlan.put("mailtut", parametrosPlan.getMailtut());
			parametersPlan.put("tfntut", parametrosPlan.getTfntut());
			parametersPlan.put("adaptaciones", parametrosPlan.getAdaptaciones());
			parametersPlan.put("dsadaptaciones", parametrosPlan.getDsadaptaciones());
			parametersPlan.put("autorizaciones", parametrosPlan.getAutorizaciones());
			parametersPlan.put("dsautorizaciones", parametrosPlan.getDsautorizaciones());
			parametersPlan.put("observaciones", parametrosPlan.getObservaciones());
			parametersPlan.put("diario", parametrosPlan.getDiario());
			parametersPlan.put("semanal", parametrosPlan.getSemanal());
			parametersPlan.put("mensual", parametrosPlan.getMensual());
			parametersPlan.put("otros", parametrosPlan.getOtros());
			parametersPlan.put("varias", parametrosPlan.getVarias());
			parametersPlan.put("subReport",subReport);
			parametersPlan.put("datosTablaEmpresas", dataSetTablaEmpresas);
			parametersPlan.put("datosTablaPeriodos", dataSetTablaPeriodos);
			parametersPlan.put("listaTablaModulos", dataSetTablaModulos);
			String dsDescricion = "";
			String dsCalendario = "";
			String dsResultados = "";
			String dsActividades = "";
			String dsContenidos = "";			
			if (formacionesEspecificas != null) {
				dsDescricion = formacionesEspecificas.getDescripcion();
				dsCalendario = formacionesEspecificas.getCalendario();
				dsResultados = formacionesEspecificas.getResultadoPrevisto();
				dsActividades = formacionesEspecificas.getActividades();
				dsContenidos =  formacionesEspecificas.getContenidos();				
			}
			parametersPlan.put("descripcion",dsDescricion);
			parametersPlan.put("calendario",dsCalendario);
			parametersPlan.put("resultadoPrevisto",dsResultados);
			parametersPlan.put("actividades",dsActividades);
			parametersPlan.put("contenidos",dsContenidos);
			parametersPlan.put("tablaCoordinacion",tablaCoordinacion);
			parametersPlan.put("idConvProy",idConvProy);
			parametersPlan.put("totalHoras", totalHoras);
			parametersPlan.put("subReportFormaciones", subReportFormaciones);
			parametersPlan.put("horasConvenio",parametrosPlan.getHorasConvenio());
			parametersPlan.put("fechaValidacion", fechaValidacion);
		}



		JasperReport jasperReport = JasperCompileManager.compileReport(dbAsStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametersPlan,new JREmptyDataSource());

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		output = outputStream.toByteArray();

		return output;
	}

	private Date getFechaPlanValidado(Long idMatricula, String estado) {
		return estadoPlanLOFPRepository.findFirstByMatriculaAndEstadoOrderByFechaRegistroDesc(idMatricula, estado)
			.map(EstadoPlanLOFP::getFechaRegistro).orElse(null);
	}

	private List<DatosTablaModulosAnexo2LOFP> obtenerDatosTablaModulos(Long idConvProy, Long idMatricula) {

		List<DatosTablaModulosAnexo2LOFP> listaTablaModulos = new ArrayList<>();
		List<DatosListaModuloAnexo2PlanProjection> datosListaModulo = proyectosRepository.getDatosListaModuloAnexoIIPlan(idConvProy,idMatricula);
		if (!datosListaModulo.isEmpty()) {
			for(DatosListaModuloAnexo2PlanProjection datosModulo : datosListaModulo) {
				DatosTablaModulosAnexo2LOFP tablaModulos = new DatosTablaModulosAnexo2LOFP();
				tablaModulos.setModuloProfesional(datosModulo.getModulo());
				tablaModulos.setCodigo(datosModulo.getCodigo());
				tablaModulos.setImparteCen(datosModulo.getColaboracion()==1?0:1);

				List<DatosListaResultadosAprendizajeAnexo2PlanProjection> listaAprendizaje = proyectosRepository.getDatosListaResultadosAprendizajeAnexo2Plan(datosModulo.getIdModuloCurso(), idMatricula);
				List<DatosListaActividadesAnexo2PlanProjection> listaActividades = proyectosRepository.getDatosListaActividadesAnexo2Plan(datosModulo.getIdModuloCurso());


				List<String> listaResultadosAprendizaje = listaAprendizaje.stream()
						.map(DatosListaResultadosAprendizajeAnexo2PlanProjection::getAprendizaje)
						.collect(Collectors.toList());
				List<Integer> listaDesarrolloCentro = listaAprendizaje.stream()
						.map(DatosListaResultadosAprendizajeAnexo2PlanProjection::getCentro)
						.collect(Collectors.toList());
				List<Integer> listaDesarrolloEmpresa = listaAprendizaje.stream()
						.map(DatosListaResultadosAprendizajeAnexo2PlanProjection::getEmpresa)
						.collect(Collectors.toList());
				List<String> listaNombreEmpresa = listaAprendizaje.stream()
						.map(DatosListaResultadosAprendizajeAnexo2PlanProjection::getDempresa)
						.collect(Collectors.toList());

				List<String> listaResultadoActividades = listaActividades.stream()
						.map(DatosListaActividadesAnexo2PlanProjection::getActividades)
						.collect(Collectors.toList());

				tablaModulos.setResultadosAprendizaje(listaResultadosAprendizaje);
				tablaModulos.setDesarrolladoCen(listaDesarrolloCentro);
				tablaModulos.setDesarrolladoEmp(listaDesarrolloEmpresa);
				tablaModulos.setEmpresa(listaNombreEmpresa);
				tablaModulos.setActividades(listaResultadoActividades);

				listaTablaModulos.add(tablaModulos);
			}

			}
		return listaTablaModulos;
	}


	public JRBeanCollectionDataSource obtenerDatosTablaPeriodos(Long idConvProy, Long idMatricula) {

		List<DatosTablaHorariosAnexo2PlanProjection> listaHorarios = proyectosRepository.getDatosTablaPeriodosPlan(idConvProy,idMatricula);
		proyectosRepository.getDatosTablaPeriodosPlan(idConvProy,idMatricula);
		return new JRBeanCollectionDataSource(listaHorarios);
	}

	public JRBeanCollectionDataSource obtenerDatosTablaEmpresas(Long idConvProy, Long idMatricula) {
		List<DatosTablaEmpresasAnexo2PlanProjection> listaEmpresas = proyectosRepository.getDatosTablaEmpresasPlan(idConvProy,idMatricula);
		return new JRBeanCollectionDataSource(listaEmpresas);
	}

	public byte[] exportMecanismoCoordinacionSeguimientoHtmlToPdf(Long idMatricula) {

		Long idProyecto = proyectosRepository.findProyectoIdByMatricula(idMatricula);
		if (idProyecto == null) {
			return null; // Si no se encuentra un proyecto, devuelve null
		}

		// Obtener la información adicional del plan asociada al proyecto
		InfoAdicionalPlan infoAdicionalPlan = getInfoAdicionalPlanByIdProyecto(idProyecto);
		if (infoAdicionalPlan == null || infoAdicionalPlan.getMecanismoCoordinacionSeguimiento() == null) {
			return null;
		}
		PDDocument document = null;
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			// Procesar el HTML utilizando el método de limpieza
			String cleanedHtml = procesarStringEditor(infoAdicionalPlan.getMecanismoCoordinacionSeguimiento());

			// Crear el ITextRenderer y configurar el HTML
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(cleanedHtml);
			renderer.layout();

			// Generar el PDF y escribirlo en el OutputStream
			renderer.createPDF(outputStream);

			// Obtener el PDF como InputStream
			InputStream pdfInputStream = new ByteArrayInputStream(outputStream.toByteArray());
			document = PDDocument.load(pdfInputStream);
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			// Renderizamos la primera página del PDF
			BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 92);


			// Guardamos la imagen en un ByteArrayOutputStream
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "PNG", baos);

			// Convertir el contenido del OutputStream a un array de bytes
			return baos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Error al generar el PDF para el mecanismo de coordinación y seguimiento", e);
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					LOG.error("Error document.close ", e);
				}
			}
		}
	}

	private String procesarStringEditor(String htmlContent) {
		// Reemplazar   por espacios normales en todo el contenido
		String cleanedHtml = htmlContent.replace(" ", " ");

		// Asegurarnos de que las tablas tengan borde simple y estilo colapsado, y ocupar todo el ancho
		cleanedHtml = cleanedHtml.replaceAll("(?i)<table([^>]*)(?<!\\bborder=\\\"\\d+\\\")>", "<table$1 border=\"1\" style=\"border-collapse: collapse; width: 100%;\">");

		// Reemplazar párrafos vacíos por saltos de línea (<br/>)
		cleanedHtml = cleanedHtml.replaceAll("(?i)<p>\\s+</p>", "<br/>");

		// Asegurar que las etiquetas <hr> estén bien cerradas, eliminando el posible ">" adicional
		cleanedHtml = cleanedHtml.replaceAll("(?i)<hr(\\s[^>]*)?>", "<hr$1/>");

		// Asegurar que las etiquetas <br> estén bien cerradas, corrigiendo cualquier error con >
		cleanedHtml = cleanedHtml.replaceAll("(?i)<br\\s+>", "<br/>");


		if (!cleanedHtml.trim().startsWith("<html>")) {
			cleanedHtml = "<html><head><title></title><style>"
					+ "@page { margin: 8px 5px 0px 5px; }"
					+ "body {  margin: 0 ; padding: 0; font-size: 10px; }"
					+ "table { width: 95%; border-collapse: collapse; }"
					+ "</style></head><body>"
					+ "<div style='width: 100%; margin: 0; padding: 0;'>" // Añadir el div contenedor
					+ cleanedHtml
					+ "</div></body></html>";
		}

		return cleanedHtml;
	}
	public DatosCabeceraAnexo2PlanProjection obtenerDatosCabeceraAnexo2Plan(Long idConvProy, Long idMatricula) {
		return proyectosRepository.getDatosCabeceraPlan(idConvProy,idMatricula);
	}

	public DatosTablaFormEspAnexo2PlanProjection obtenerDatosTablaFormacionEspecificaAnexo2Plan(Long idMatricula) {
		return proyectosRepository.getDatosListaFormEspAnexo2Plan(idMatricula);
	}

	@Override
	public byte[] exportReportAnexoIPlan(Long idConvProy) throws IOException, JRException {

		byte[] output = null;
		InputStream dbAsStream = null;

		Resource resource = resourceLoader.getResource("classpath:reports/anexoI_Alumnado_Plan.jrxml");
		dbAsStream = resource.getInputStream();

		Map<String, Object> parameters = new HashMap<>();
		DatosCabeceraAnexoIAlumnadoProjection parametros = null;
		List<ListadoAnexoIAlumnadoProjection> listadoAnexoIAlumnadoProjection = null;
		Optional<ConveniosProyecto> convProy = conveniosProyectoRepository.findById(idConvProy);

		if (convProy.isPresent()) {
		    parametros = proyectosRepository.getDatosCabeceraAnexoIFCT(convProy.get().getId());
		} else {
		    // Manejo del caso en que convProy está vacío
		    throw new IllegalStateException("convProy no está presente");
		}

		listadoAnexoIAlumnadoProjection = proyectosRepository.getListadoAlumnadoAnexoI(idConvProy);

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
			String curso = getCurso(parametros);
			String cursoAcademico = getAnno(parametros);
			String tutor = getTutor(parametros);
			String representante = getRepresentante(parametros);
			String localidad = parametros.getLocalidad().toUpperCase();
			String responsable = Objects.equal(null,parametros.getResponsable()) ? "          " : parametros.getResponsable().toUpperCase();
			String familia = Objects.equal(null,parametros.getFamilia()) ? "_______________________________________" : parametros.getFamilia();
			String primero = parametros.getPrimero();
			String dniTutor = Objects.equal(null,parametros.getDniTutor()) ? RELLENO : parametros.getDniTutor();
			String dniRep	= Objects.equal(null,parametros.getDireccion()) ? RELLENO : parametros.getDniTutor();

			String cabecera = "Relación nominal del alumnado acogido al Convenio número " + Optional.ofNullable(parametros.getNumconv()).orElse("_".repeat(15)) +
					" suscrito con fecha " + Optional.ofNullable(parametros.getDia()).orElse("_".repeat(3)) + " de " + Optional.ofNullable(parametros.getMes()).orElse("_".repeat(11)) + " de " +
					Optional.ofNullable(parametros.getAnno()).orElse("_".repeat(6)) + " entre el centro educativo " +
					Optional.ofNullable(parametros.getCentro()).map(String::toUpperCase).orElse("_".repeat(15)) + 	" y la empresa u organismo equiparado " +
					Optional.ofNullable(parametros.getEmpresa()).orElse("_".repeat(15)) + 	", con centro de trabajo ubicado en " +
					Optional.ofNullable(parametros.getDireccion()).map(String::toUpperCase).orElse("_".repeat(20)) + ", C.I.F " +
					Optional.ofNullable(parametros.getCif()).orElse("_".repeat(11)) + ", teléfono: " + Optional.ofNullable(parametros.getTelefono()).orElse("_".repeat(10)) +
					" y correo electrónico " + Optional.ofNullable(parametros.getEmail()).orElse("_".repeat(11));

			String body = "En el cumplimiento de la cláusula tercera del Convenio/Acuerdo de colaboración, se procede a designar al tutor/a dual del " +
					"centro educativo, que será D./Dña. " + parametros.getTutor() + " con " +
					"D.N.I. " + parametros.getDniTutor() + ", y el/la tutor/a dual de la empresa u organismo equiparado que será D./Dña. "
					+ parametros.getResponsable() + DNI + parametros.getDniRep();

			//String sFirma = conveniosFctRepository.getFechaInicioConvenio(parametros.getIdconvenio());

			String sFirma = conveniosFctRepository.getFechaHoy();

			String descripcionFirma =  "En " + localidad  + " " + sFirma;

			DatosCentroConvenioProjection datosProyeccion = conveniosFctRepository.findDatosCentro(parametros.getIdcentro());

			parameters.put("cabecera", cabecera);
			parameters.put("body", body);
			//parameters.put("diaP", dia);
			//parameters.put("mesP", mes);
			//parameters.put("annoP", anno);
			//parameters.put("centroP", centro);
			//parameters.put("empresaP", empresa);
			//parameters.put("direccionP", direccion);
			parameters.put("dniTutorP",dniTutor);
			parameters.put("dniRepP",dniRep);
			parameters.put("cursoP", curso);
			parameters.put("cursoAcademicoP", cursoAcademico);
			parameters.put("tutorP", tutor);
			parameters.put("representanteP", representante);
			parameters.put("responsableP", responsable);
			parameters.put("directorP", Objects.equal(null,datosProyeccion.getNombreCompleto()) ? "_____________" : datosProyeccion.getNombreCompleto().toUpperCase());
			parameters.put(FIRMA, descripcionFirma);
			parameters.put(FAMILIA,familia);
			parameters.put("primeroP", primero);
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

	private String getRepresentante(DatosCabeceraAnexoIAlumnadoProjection parametros) {
		String representante = Objects.equal(null,parametros.getRepresentante()) ? RELLENO :  parametros.getRepresentante().toUpperCase();
		return representante;
	}

	private String getTutor(DatosCabeceraAnexoIAlumnadoProjection parametros) {
		String tutor = Objects.equal(null,parametros.getTutor()) ? RELLENO : parametros.getTutor().toUpperCase();
		return tutor;
	}

	private String getAnno(DatosCabeceraAnexoIAlumnadoProjection parametros) {
		String cursoAcademico = Objects.equal(null,parametros.getCursoAcademico()) ? RELLENO : parametros.getCursoAcademico();
		return cursoAcademico;
	}

	private String getCurso(DatosCabeceraAnexoIAlumnadoProjection parametros) {
		String curso = Objects.equal(null,parametros.getCurso()) ? RELLENO : parametros.getCurso();
		return curso;
	}
	public List<DatosTablaCoorAnexo2LOFP> exportarMecanismoCoordinacionSeguimientoHtmlToTxtPlano(Long idMatricula) {

		Long idProyecto = proyectosRepository.findProyectoIdByMatricula(idMatricula);
		if (idProyecto == null) {
			return crearDatosVacios(); // Si no se encuentra un proyecto, devuelve null
		}

		// Obtener la información adicional del plan asociada al proyecto
		InfoAdicionalPlan infoAdicionalPlan = getInfoAdicionalPlanByIdProyecto(idProyecto);
		if (infoAdicionalPlan == null || infoAdicionalPlan.getMecanismoCoordinacionSeguimiento() == null) {
			return crearDatosVacios();
		}

		try {
			// Procesar el HTML utilizando el método de limpieza

			String html = infoAdicionalPlan.getMecanismoCoordinacionSeguimiento();
			Jsoup.clean(html, Safelist.basic());

			// Parsear el HTML con Jsoup
			org.jsoup.nodes.Document doc = Jsoup.parse(html);

			// Listas para almacenar los datos
			List<String> headers = new ArrayList<>();
			List<String> values = new ArrayList<>();

			// Seleccionar filas de la tabla
			Elements rows = doc.select("table tbody tr");

			return getDatosHTML(html, headers, values, rows);
			
		} catch (Exception e) {
			throw new RuntimeException("Error al generar el PDF para el mecanismo de coordinación y seguimiento", e);
		}
	}

	private List<DatosTablaCoorAnexo2LOFP> getDatosHTML(String html, List<String> headers, List<String> values,
			Elements rows) {
		if (rows.isEmpty()) {
		   return htmlPlano(html);
		} else {
			
			
		
			// Iterar sobre las filas
			for (org.jsoup.nodes.Element row : rows) {
				// Obtener el encabezado (th) y el valor (td)
				org.jsoup.nodes.Element th = row.select("th").first();
				org.jsoup.nodes.Element td = row.select("td").first();


//				org.jsoup.nodes.Document doc = Jsoup.parse(cleanedHtml);


				if (th != null) {
					String headerText = th.text().trim();
					headers.add(headerText);
				}

				if (td != null) {
					String valuesText = td.text().trim();
					values.add(valuesText);
				}
			}
			// Crear y devolver el objeto DatosTablaCoorAnexo2LOFP
			List<DatosTablaCoorAnexo2LOFP> datosTabla = new ArrayList<>();

			for (int i=0;i < headers.size(); i++) {
				DatosTablaCoorAnexo2LOFP datosTablaCoorAnexo2LOFP = new DatosTablaCoorAnexo2LOFP();
				datosTablaCoorAnexo2LOFP.setHeaders(headers.get(i));
				datosTablaCoorAnexo2LOFP.setValues(values.get(i));
				datosTabla.add(datosTablaCoorAnexo2LOFP);
			}
			return datosTabla;
		}
	}

	private List<DatosTablaCoorAnexo2LOFP> htmlPlano(String html) {
		List<DatosTablaCoorAnexo2LOFP> datosTabla = new ArrayList<>();
		
		DatosTablaCoorAnexo2LOFP datosTablaCoorAnexo2LOFP = new DatosTablaCoorAnexo2LOFP();
		datosTablaCoorAnexo2LOFP.setHeaders(null);
		datosTablaCoorAnexo2LOFP.setValues(Jsoup.parse(html).text());
		datosTabla.add(datosTablaCoorAnexo2LOFP);
		return datosTabla;
	}

	private List<DatosTablaCoorAnexo2LOFP> crearDatosVacios() {
		List<DatosTablaCoorAnexo2LOFP> listaVacia = new ArrayList<>();
		DatosTablaCoorAnexo2LOFP datosVacios=new DatosTablaCoorAnexo2LOFP();
		datosVacios.setHeaders("");
		datosVacios.setValues("");
		listaVacia.add(datosVacios);
		return listaVacia;
	}
        public byte[] exportParteSemanalReportPlan(Long idParsemAluPlan) throws JRException, IOException {

		byte[] output = null;
		InputStream dbAsStream = null;

		Resource resource = resourceLoader.getResource("classpath:reports/InformeParteSemanalPlan.jrxml");
		dbAsStream = resource.getInputStream();

		Map<String, Object> parameters = new HashMap<>();
		DatosCabeceraEvaluacionProjection parametros = null;
		List<DatosHojaSemanal> hojaSemanalDataSource = null;

		Long idConvProyAlu = getIdConvProyAlu(idParsemAluPlan);

		parametros = alumnoProgramaRepository.getDatosCabeceraDUAL(idConvProyAlu);

		hojaSemanalDataSource = getDatosTablaParteSemanal(idParsemAluPlan);

		Double nHorasTotales = 0.0;

		nHorasTotales = alumnoProgramaRepository.getHorasTotalesDUAL(idConvProyAlu);

		if (nHorasTotales == null) nHorasTotales = 0.0;
		nHorasTotales = (double) Math.round(nHorasTotales * 100);
		nHorasTotales = nHorasTotales / 100;


		JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(hojaSemanalDataSource);

		if(parametros != null) {

			String centro = parametros.getCentro();
			String codigo_centro = parametros.getCodigo_centro();
			String nombre_alumno = parametros.getNombre_alumno();
			String nombre_tutor = parametros.getNombre_tutor();
			String familia = parametros.getFamilia();
			String centro_trabajo = parametros.getCentro_trabajo();
			String curso = parametros.getCurso();
			String responsable = parametros.getResponsable();
			String area = parametros.getArea();
			String periodo = parametros.getPeriodo();
			String evaluacion = parametros.getEvaluacion();
			String orientaciones = parametros.getOrientaciones();
			String firma = conveniosFctRepository.getFechaActual().substring(1).toUpperCase();

			parameters.put(CENTRO, centro);
			parameters.put("codigo_centro", codigo_centro);
			parameters.put("nombre_alumno", nombre_alumno);
			parameters.put("nombre_tutor", nombre_tutor);
			parameters.put(FAMILIA, familia);
			parameters.put("curso", curso);
			parameters.put("centro_trabajo", centro_trabajo);
			parameters.put("responsable", responsable);
			parameters.put("area", area);
			parameters.put("periodo", periodo);
			parameters.put("evaluacion", evaluacion);
			parameters.put("orientaciones", orientaciones);
			parameters.put("ds", datasource);
			parameters.put("hTotales", nHorasTotales % 1 == 0 ? nHorasTotales.toString().subSequence(0, nHorasTotales.toString().indexOf('.')): nHorasTotales.toString());
			parameters.put("firmaP", firma);
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

	private Long getIdConvProyAlu(Long idParsemAluPlan) {
		ParsemAluplan parsemAluplan = parsemAluplanRepository.findFirstByIdParsemAluplan(idParsemAluPlan);

		return parsemAluplan.getIdConvproyAlu();
	}

	private List<DatosHojaSemanal> getDatosTablaParteSemanal(Long idParsemAluPlan) {

		List<DatosHojaSemanalProjection> listaProyeccion = parsemAluplanRepository.getDatosTablaParteSemanal(idParsemAluPlan);

		return listaProyeccion.stream()
				.map(projection -> modelMapper.map(projection, DatosHojaSemanal.class))
				.collect(Collectors.toList());
	}
	@Override
	public Date obtenerFechaFinAltaSSDual(Long idConvProyAlu, Long idMatricula) {
		Date diaFinAltaSS = altaSegSocialProyRepository.findFechaFinAltaSSDual(idConvProyAlu, idMatricula);
		if (diaFinAltaSS == null) {
			diaFinAltaSS = new Date(Long.MAX_VALUE);
		}
		return diaFinAltaSS;
	}

	private void anadirModulo(ModulosCurso moduloNuevo, Proyectos proyecto) {
		//Buscar cursoProyecto, si no lo encuentra lo crea
		CursoProyecto cursoProyecto = cursoProyectoRepository.findByIdOfertamatrigIdAndProyectoId(moduloNuevo.getCursoProyecto().getIdOfertamatrig().getId(), proyecto.getId());
		if (cursoProyecto == null) {
			cursoProyecto = guardarCursoProyecto(moduloNuevo.getCursoProyecto(), proyecto);
		}

		moduloNuevo.setCursoProyecto(cursoProyecto);
		modulosCursoRepository.save(moduloNuevo);
	}

	private void eliminarModulo(ModulosCurso modulo, Long idProyecto) {
		modulosCursoRepository.delete(modulo);

		//Comprobamos si el curso al que pertenecía tiene más módulos asociados, si no tiene se elimina
		CursoProyecto cursoProyecto = cursoProyectoRepository.findByIdOfertamatrigIdAndProyectoId(modulo.getIdMateriaomg().getId(), idProyecto);
		if (modulosCursoRepository.existsByCursoProyectoId(cursoProyecto.getId())) {
			List<CursoProyecto> deleteCursoProyectoList = cursoProyectoRepository.findAllByProyectoId(idProyecto);
			cursoProyectoRepository.deleteAll(deleteCursoProyectoList);
		}

	}

}

