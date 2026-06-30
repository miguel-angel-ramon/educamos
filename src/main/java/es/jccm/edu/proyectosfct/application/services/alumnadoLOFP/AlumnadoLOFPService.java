package es.jccm.edu.proyectosfct.application.services.alumnadoLOFP;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model.AlumnadoTutorLofpDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model.DatosTutorYResponsableDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model.EstadoValidacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialprog.AltaSegSocialProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.altasegsocialproy.AltaSegSocialProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.EstadoPlanLOFPRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.ValidacionAlumnoPlanRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesproy.SegSocialCotizaMesProyRepository;
import es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities.AltasSegSociProg;
import es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities.AltasSegSociProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ListadoAlumnadoTutor;
import es.jccm.edu.proyectosfct.application.domain.alumnado.projection.ListadoAlumnadoTutorProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.*;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection.DatosTutorYResponsableProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection.EstadoValidacionProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection.ListadoSeguimientoLOFPProjection;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.UnidadCurso;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection.UnidadCursoProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModuloModalidad;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.ModuloModalidadProjection;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesproyectos.entities.CotizaMesProyectos;
import es.jccm.edu.proyectosfct.application.ports.in.alumnadoLOFP.IAlumnadoLOFPService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnadoLOFP.AlumnadoLOFPRepository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.CursoModalidad;
import es.jccm.edu.proyectosfct.application.domain.proyectos.projection.CursoModalidadProjection;

@Service
public class AlumnadoLOFPService implements IAlumnadoLOFPService {
	
	private static final Long ID_PERFIL_GESTOR = 11207L;
	private static final Long ID_PERFIL_CONSEJERIA_FCT = 13207L;
	private static final Long ID_PERFIL_CONSEJERIA_FCT_1 = 15207L;
	private static final Long ID_PERFIL_ALUMNADO = 5000L;
	
	@Autowired
	private AlumnadoLOFPRepository alumnadoLOFPRepository;

	@Autowired
	private EstadoPlanLOFPRepository estadoPlanLOFPRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ValidacionAlumnoPlanRepository validacionAlumnoPlanRepository;

	@Autowired
	private SegSocialCotizaMesProyRepository segSocialCotizaMesProyRepository;

	@Autowired
	private AltaSegSocialProyRepository altaSegSocialProyRepository;

	@Autowired
	private AlumnadoRepository alumnadoRepository;

	@Override
	public List<CursoModalidad> getCursosEmpleadoCentro(Long idTutorfctdual,
														Long idCentro,
														Integer cAnno,
														Long idPerfil,
														Long idCentroCombo,
														Long idProvincia,
														Long idUsuario,
														Long idEmpleadoComunica) throws org.apache.tomcat.util.json.ParseException {

		List<CursoModalidadProjection> cursosProjection = null;

		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {

			if (ID_PERFIL_GESTOR.equals(idPerfil)) {

				cursosProjection = alumnadoLOFPRepository.findCursosEmpleadoDelegacionLOFP(idTutorfctdual,
						idCentro,
						cAnno,
						idPerfil,
						idCentroCombo,
						idUsuario);

			} else {

				cursosProjection = alumnadoLOFPRepository.findCursosEmpleadoDelegacionProvinciasLOFP(idTutorfctdual,
						idCentro,
						cAnno,
						idProvincia);
			}

		} else {

			if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {

				cursosProjection = alumnadoLOFPRepository.findCursosEmpleadoCentroAlumnadoLOFP(idTutorfctdual, idCentro, cAnno,idEmpleadoComunica);

			} else {

				cursosProjection = alumnadoLOFPRepository.findCursosEmpleadoCentroLOFP(idTutorfctdual, idCentro, cAnno);

			}

		}

		return cursosProjection.stream().map(entity -> modelMapper.map(entity, CursoModalidad.class)).collect(Collectors.toList());
	}

	@Override
	public List<UnidadCurso> getUnidadesLOFP(Long idTutorfctdual,
													   Long idCentro,
													   Integer cAnno,
													   Long idOfertamatrig,
													   Long idPerfil,
													   Long idCentroCombo,
													   Long idProvincia,
													   Long idUsuario,
													   Long idEmpleadoComunica) throws org.apache.tomcat.util.json.ParseException {

		List<UnidadCursoProjection> unidadesProjection = null ;

		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {

			if (ID_PERFIL_GESTOR.equals(idPerfil)) {

				unidadesProjection = alumnadoLOFPRepository.findUnidadesEmpleadoDelegacionLOFP(idTutorfctdual,
						idCentro,
						cAnno,
						idOfertamatrig,
						idPerfil,
						idCentroCombo,
						idUsuario);
			
			} else {

			/*	unidadesProjection = alumnadoLOFPRepository.findUnidadesEmpleadoDelegacionProvinciasLOFP(idTutorfctdual,
						idCentro,
						cAnno,
						idOfertamatrig,
						idCentroCombo,
						idProvincia);
			*/
			}



		} else {

			if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {

			//	unidadesProjection = alumnadoLOFPRepository.findUnidadesEmpleadoCentroAlumnado(idTutorfctdual, idCentro, cAnno,idOfertamatrig, idEmpleadoComunica);

			} else {

				unidadesProjection = alumnadoLOFPRepository.getUnidadesLOFP(idTutorfctdual, idCentro, cAnno, idOfertamatrig);

			}
		}

		return Optional.ofNullable(unidadesProjection)
			    .map(unidades -> unidades.stream()
			        .map(entity -> modelMapper.map(entity, UnidadCurso.class))
			        .collect(Collectors.toList()))
			    .orElse(Collections.emptyList());
	}

	@Override
	public List<AlumnadoTutorLofpDto> getListadoAlumnosLOPD(Long idTutorfctdual,
															Long idCentro,
															Integer cAnno,
															Long idOfertamatrig,
															Long idUnidad,
															Long idPerfil,
															Long idCentroCombo,
															Long idProvincia,
															Long idUsuario,
															Long idEmpleadoComunica,
															Integer esSinPlan,
															Integer idEstado) {
List<ListadoAlumnadoTutorProjection> listadoAlumnosTutor = null;
		
		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {
			
		  if (ID_PERFIL_GESTOR.equals(idPerfil)) {
			  
				listadoAlumnosTutor = alumnadoLOFPRepository.findListadoAlumnosLOFPDelegacion(idTutorfctdual, 
																					       idCentro, 
																					       cAnno,																					        
																					       idOfertamatrig, 
																					       idUnidad,
																					       idPerfil,
																					       idCentroCombo,
																					       idUsuario,
						                                                                   esSinPlan,
																				           idEstado);
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
				
				listadoAlumnosTutor = alumnadoLOFPRepository.findListadoAlumnosLOFP(idTutorfctdual, idCentro, cAnno,  idOfertamatrig, idUnidad,esSinPlan, idEstado, idPerfil);
				
				
			}

			//Se revisa si al alumno se le muestran o no avisos por falta de cotizaciones mensuales en el mes anterior
			//return gestionarMensajeAviso(listadoAlumnosTutor, cAnno);
			
			if (listadoAlumnosTutor == null || cAnno == null) {
			    throw new IllegalArgumentException("Los parámetros no pueden ser null.");
			} else {
			    return gestionarMensajeAviso(listadoAlumnosTutor, cAnno);
			}

			
		}


		return Optional.ofNullable(listadoAlumnosTutor)
				.map(listado -> listado.stream()
						.map(entity -> modelMapper.map(entity, AlumnadoTutorLofpDto.class)) // 🔹 Se cambia al nuevo DTO
						.collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

	@Override
	public List<ElementoSelect> getTutoresAlumnadoLOFP(Long idTutorfctdual, 
			                                           Long idCentro, 
			                                           Integer cAnno, 
			                                           Long idPerfil,
			                                           Long idCentroCombo, 
			                                           Long idProvincia, 
			                                           Long idUsuario, 
			                                           Long idEmpleadoComunica) {
    List<ElementoSelectProjection> tutoresProjection = null;		
		
		if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {
			
			if (ID_PERFIL_GESTOR.equals(idPerfil)) {
				
				tutoresProjection = alumnadoLOFPRepository.findTutoresDelegacionLOFP(idCentro, 
														   cAnno,				
														   idPerfil,
														   idCentroCombo,
														   idUsuario);	 
				
			} else {
				
				/* = alumnadoLOFPRepository.findTutoresDelegacionProvincias(idCentro, 
																					   cAnno, 
																					   tipoEmpresa, 
																					   idEmpresa,						   
																					   idCentroCombo,
																					   idProvincia);*/			
			}
			
		} else {
			
			if (ID_PERFIL_ALUMNADO.equals(idPerfil)) {
				
				// tutoresProjection = alumnadoLOFPRepository.findTutoresCentroAlumnado(idCentro, cAnno,  idEmpleadoComunica);
				
			} else {
				
				tutoresProjection = alumnadoLOFPRepository.findTutoresCentroLOFP(idCentro, cAnno,idTutorfctdual);
				
			}
			
			
			
		}
		
		return Optional.ofNullable(tutoresProjection)
			    .map(tutores -> tutores.stream()
			        .map(entity -> modelMapper.map(entity, ElementoSelect.class))
			        .collect(Collectors.toList()))
			    .orElse(Collections.emptyList());
	}

	@Override
	public List<ElementoSelect> getCentrosDelegacionLOFP(Long idUsuario, Long idPerfil, Long idCentro,
			Long idProvincia, Integer cAnno) {
        
		List<ElementoSelectProjection> centroProjection = null;
		
		if (ID_PERFIL_GESTOR.equals(idPerfil)) {
			
			centroProjection = alumnadoLOFPRepository.allCentroDelegacionLOFP(idUsuario,idPerfil,idCentro,cAnno);
			
		} else {
			
			//centroProjection = alumnadoLOFPRepository.allCentroDelegacionProvincias(idProvincia);
			
		}		

		
		return Optional.ofNullable(centroProjection)
			    .map(centros -> centros.stream()
			        .map(entity -> modelMapper.map(entity, ElementoSelect.class))
			        .collect(Collectors.toList()))
			    .orElse(Collections.emptyList());
	}


	@Override
	public List<ElementoSelect> getFamiliasCentroTutorLOFP(Long idCentro, Integer cAnno, Long idTutor) {

		List<ElementoSelectProjection> familiaProjection = alumnadoLOFPRepository.findFamiliasCentroTutorLOFP(idCentro, cAnno, idTutor);

		return Optional.ofNullable(familiaProjection)
				.map(familias -> familias.stream()
						.map(entity -> modelMapper.map(entity, ElementoSelect.class))
						.collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

	@Override
	public List<ElementoSelect> getModCentroTutorFamiliaLOFP(Long idCentro, Integer cAnno, Long idTutor, Long idFamilia) {

		List<ElementoSelectProjection> modalidadProjection = alumnadoLOFPRepository.findModCentroTutorFamiliaLOFP(idCentro, cAnno, idTutor, idFamilia);

		return Optional.ofNullable(modalidadProjection)
				.map(modalidades -> modalidades.stream()
						.map(entity -> modelMapper.map(entity, ElementoSelect.class))
						.collect(Collectors.toList()))
				.orElse(Collections.emptyList());
}

	@Override
	public List<ElementoSelect> getCursosCentroAnnoTutorFamiliaModalidadLOFP(Long idCentro, Integer cAnno, Long idTutor, Long idFamilia, Long idModalidad) {

		List<ElementoSelectProjection> cursoProjection =
				alumnadoLOFPRepository.findCursosCentroAnnoTutorFamiliaModalidadLOFP(idCentro, cAnno, idTutor, idFamilia, idModalidad);

		return Optional.ofNullable(cursoProjection)
				.map(cursos -> cursos.stream()
						.map(entity -> modelMapper.map(entity, ElementoSelect.class))
						.collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

	@Override
	public List<CursoModalidad> getCursosModalidadLOFP(Long idCurso, Integer idCentro, Integer cAnno ) {

		List<CursoModalidadProjection> cursoProj = alumnadoLOFPRepository.findCursosByModalidadLOFP(idCurso, idCentro, cAnno);

		return cursoProj.stream().map(entity -> modelMapper.map(entity, CursoModalidad.class)).collect(Collectors.toList());
	}

	@Override
	public List<ModuloModalidad> getModulosModalidadLOFP(Long idCurso, Integer idCentro, Integer cAnno) {
		List<ModuloModalidadProjection> modalidadProj = alumnadoLOFPRepository.findModuloByModalidadLOFP(idCurso, idCentro, cAnno);

		return modalidadProj.stream().map(entity -> modelMapper.map(entity, ModuloModalidad.class)).collect(Collectors.toList());
	}

	@Override
    public void postValidarPlanMasivo(List<Long> matriculas, String cPerfil, Long usuarioActual, Long usuarioActualComunica) {
		EstadoPlanLOFP estadoPlan;

		for(Long matricula : matriculas){
			estadoPlan = new EstadoPlanLOFP();
			estadoPlan.setMatricula(matricula);
			estadoPlan.setFechaRegistro(new Date());
			estadoPlan.setUsuario(cPerfil.equals("ALU")?usuarioActualComunica:usuarioActual);
			estadoPlan.setVista(cPerfil);

			if(cPerfil.equals("ALU")){
				estadoPlan.setEstado("VA");
			} else {
				estadoPlan.setEstado("VP");
			}

			Integer ultimoOrden = obtenerUltimoOrdenByMatricula(matricula);
			estadoPlan.setOrden(ultimoOrden + 1);

			estadoPlanLOFPRepository.save(estadoPlan);
		}


    }

	private Integer obtenerUltimoOrdenByMatricula(Long matricula){

		Integer orden = estadoPlanLOFPRepository.findLastOrdenByMatricula(matricula);

		//Si no encuentra nada, se inicializa en 0 como que no hay
		if(orden == null){
			orden = 0;
		}
		return orden;

	}

	public List<PeriodoLOFP> getDatosSeguimientoLOFP(Long xMatricula, Long idEmpresa, Long idProyecto) {
		List<ListadoSeguimientoLOFPProjection> listadoSeguimientoProjection = alumnadoLOFPRepository.findListadoSeguimientoLofp(xMatricula, idEmpresa, idProyecto);

		List<ListadoSeguimientoLOFP> listadoSeguimiento = listadoSeguimientoProjection.stream()
				.map(entity -> modelMapper.map(entity, ListadoSeguimientoLOFP.class)).collect(Collectors.toList());		

		// Mapa para agrupar los periodos por su número
		Map<Integer, PeriodoLOFP> mapaPeriodos = new HashMap<>();

		// Mapa auxiliar para detectar semanas duplicadas
		Map<String, Integer> mapaSemanasAsignadas = new HashMap<>();

		PeriodoLOFP ultimoPeriodo = null;

		for (ListadoSeguimientoLOFP elementoListado : listadoSeguimiento) {

			PeriodoLOFP periodoLofp = mapaPeriodos.get(elementoListado.getPeriodoNumero());
			if (periodoLofp == null && elementoListado.getPeriodoNumero() != null) {
				// Crear un nuevo periodo si no existe
				periodoLofp = new PeriodoLOFP();
				periodoLofp.setPeriodoNumero(elementoListado.getPeriodoNumero());
				periodoLofp.setFechaInicioPeriodo(elementoListado.getFechaInicioPeriodo());
				periodoLofp.setFechaFinPeriodo(elementoListado.getFechaFinPeriodo());
				periodoLofp.setEsPeriodoActual(elementoListado.getEsPeriodoActual());
				periodoLofp.setPeriodoVisible(elementoListado.getPeriodoVisible());
				

				periodoLofp.setMeses(new ArrayList<>());
				mapaPeriodos.put(elementoListado.getPeriodoNumero(), periodoLofp);

				ultimoPeriodo = periodoLofp;
			}

			// Si no tiene periodo, se le pone el último registrado
			if (elementoListado.getPeriodoNumero() == null) {
				periodoLofp = ultimoPeriodo;
			}

			// Generar una clave única para identificar la semana (inicio-fin)
			String claveSemana = elementoListado.getFechaInicioSem() + "-" + elementoListado.getFechaFinSem();

			// Comprobar si la semana ya fue asignada a un periodo diferente
			if (mapaSemanasAsignadas.containsKey(claveSemana)) {
				Integer periodoExistente = mapaSemanasAsignadas.get(claveSemana);
				if (periodoLofp != null && periodoLofp.getPeriodoNumero() < periodoExistente) {
					eliminarSemanaDePeriodo(mapaPeriodos.get(periodoExistente), claveSemana);
				}
			}

			String mesNumero = elementoListado.getMesNumero();
			if (periodoLofp != null && mesNumero != null && elementoListado != null) {
				MesLOFP mesLofp = findOrCreateMes(periodoLofp, mesNumero, elementoListado);
	
				SemanaLOFP semanaLofp = createSemanaLOFP(elementoListado);
	
				mesLofp.getSemanas().add(semanaLofp);
	
				// Registrar que esta semana ha sido asignada a un periodo
				mapaSemanasAsignadas.put(claveSemana, periodoLofp.getPeriodoNumero());
			}
		}

		// Convertimos los periodos en una lista y ordenamos por fecha de inicio descendente
		List<PeriodoLOFP> listaPeriodos = new ArrayList<>(mapaPeriodos.values());
		listaPeriodos.sort((p1, p2) -> p2.getFechaInicioPeriodo().compareTo(p1.getFechaInicioPeriodo()));

		return listaPeriodos;
	}

	@Override
	public DatosTutorYResponsableDto getTutorYResponsable(Long idConvProyAlu) {
		DatosTutorYResponsableProjection datosEncargados = alumnadoLOFPRepository.findTutorYResponsable(idConvProyAlu);
		return modelMapper.map(datosEncargados, DatosTutorYResponsableDto.class);
	}

	private MesLOFP findOrCreateMes(PeriodoLOFP periodoLofp, String mesNumero, ListadoSeguimientoLOFP elementoListado) {
		// Buscar si el mes ya existe en el periodo
		MesLOFP mesLofp = periodoLofp.getMeses().stream()
				.filter(m -> m.getMesNumero().equals(mesNumero))
				.findFirst()
				.orElse(null);

		if (mesLofp == null) {
			mesLofp = new MesLOFP();
			mesLofp.setMesNumero(mesNumero);
			mesLofp.setMesNombre(elementoListado.getMesNombre());
			mesLofp.setMesVisible(elementoListado.getMesVisible());
			mesLofp.setSemanas(new ArrayList<>());
			// Añadir el mes al periodo
			periodoLofp.getMeses().add(mesLofp);
		}

		return mesLofp;
	}

	private SemanaLOFP createSemanaLOFP(ListadoSeguimientoLOFP elementoListado) {
		SemanaLOFP semanaLofp = new SemanaLOFP();
		semanaLofp.setSemanaNumero(elementoListado.getSemanaNumero());
		semanaLofp.setEsSemanaActual(elementoListado.getEsSemanaActual());
		semanaLofp.setSemanaVisible(elementoListado.getSemanaVisible());
		semanaLofp.setFechaInicioSem(elementoListado.getFechaInicioSem());
		semanaLofp.setFechaFinSem(elementoListado.getFechaFinSem());
		semanaLofp.setNumActividades(elementoListado.getNumActividades());
		semanaLofp.setEstadoParte(elementoListado.getEstadoParte());
		semanaLofp.setIdRodal(elementoListado.getIdRodal());
		semanaLofp.setFichero(elementoListado.getFichero());
		semanaLofp.setParteActualizado(elementoListado.getParteActualizado());
		return semanaLofp;
	}

	/**
	 * Método para eliminar una semana duplicada de un periodo.
	 */
	private void eliminarSemanaDePeriodo(PeriodoLOFP periodo, String claveSemana) {
		if (periodo == null) return;

		for (MesLOFP mes : periodo.getMeses()) {
			mes.getSemanas().removeIf(semana ->
					(semana.getFechaInicioSem() + "-" + semana.getFechaFinSem()).equals(claveSemana));
		}
	}
	public List<EstadoValidacionDto> getValidacionesPorMatricula(Long matricula) {
		List<EstadoValidacionProjection> proyecciones = validacionAlumnoPlanRepository.findValidacionesByMatricula(matricula);

		return proyecciones.stream()
				.map(projection -> modelMapper.map(projection, EstadoValidacionDto.class))
				.collect(Collectors.toList());
	}

	private List<AlumnadoTutorLofpDto> gestionarMensajeAviso(List<ListadoAlumnadoTutorProjection> listadoAlumnosTutor, Integer cAnno){
		List<AlumnadoTutorLofpDto> listadoAlumnoParsed = listadoAlumnosTutor.stream().map(entity -> modelMapper.map(entity, AlumnadoTutorLofpDto.class)).collect(Collectors.toList());

		LocalDate hoy = LocalDate.now();
		Integer mesAnterior = (hoy.getMonthValue() == 1) ? 12 : hoy.getMonthValue() - 1; //En el caso de enero, ponemos que el anterior sea diciembre

		if(hoy.getDayOfMonth() <= getMaxDiaLiquidacionMensual()) {

			for(AlumnadoTutorLofpDto alumno : listadoAlumnoParsed){

				if(tienePeriodoActivo(alumno, cAnno, mesAnterior)) {
					CotizaMesProyectos haCotizado = segSocialCotizaMesProyRepository.findByXmatriculaAndNuMes(alumno.getXMatricula(), mesAnterior);

					// Si no hay registros de que haya cotizado en el mes anterior, se pone el aviso
					if (haCotizado == null) {
						alumno.setAvisoMes(1);
					}
				}
			}
		}

		return listadoAlumnoParsed;
	}

	/*
	   Comprobar si tiene algún periodo de alta en activo en el mes anterior al actual
	 */
	private boolean tienePeriodoActivo(AlumnadoTutorLofpDto alumno, Integer cAnno, Integer nuMesAnterior) {
		List<AltasSegSociProy> altasAlumnoProy = altaSegSocialProyRepository.getListForMatricula(alumno.getXMatricula());
		return verificarPeriodoActivo(altasAlumnoProy, cAnno, nuMesAnterior);
	}

	/*
       Verifica si un período está activo en el mes anterior del curso académico.
     */
	private boolean verificarPeriodoActivo(List<AltasSegSociProy> altasAlumno, Integer cAnno, Integer nuMesAnterior) {
		YearMonth mesComprobar = calcularMesAnteriorAcademico(cAnno, nuMesAnterior);

		for (AltasSegSociProy periodo : altasAlumno) {
			Date fechaInicio;
			Date fechaFin;

			// Obtener las fechas de inicio y fin
			fechaInicio = periodo.getFechaInicio();
			fechaFin = periodo.getFechaFin();

			// Convertir Date a LocalDate para comparar
			LocalDate inicioProy = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate finProy = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (!(inicioProy.isAfter(mesComprobar.atEndOfMonth()) || finProy.isBefore(mesComprobar.atDay(1)))) {
				return true; // Si alguna parte del período está dentro del mes
			}

		}
		return false; // Si ningún período cubre el mes anterior
	}

	private YearMonth calcularMesAnteriorAcademico(int cAnno, int nuMesAnterior) {
		int annoCalculadoMes = (nuMesAnterior >= 10) ? cAnno : cAnno + 1;

		return YearMonth.of(annoCalculadoMes, nuMesAnterior);
	}

	private Integer getMaxDiaLiquidacionMensual(){
		return segSocialCotizaMesProyRepository.getMaxDiaLiquidacionMensual();
	}

}
