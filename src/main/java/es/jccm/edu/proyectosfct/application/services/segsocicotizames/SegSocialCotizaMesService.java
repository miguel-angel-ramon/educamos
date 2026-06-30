package es.jccm.edu.proyectosfct.application.services.segsocicotizames;

import es.jccm.edu.proyectosfct.adapter.in.rest.segsocialcotizames.model.ListadoHistoricoMesDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma.AlumnoProgramaRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.enviosgestorames.EnviosCotizaMesGestoraLogRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.historicomesprog.HistoricoMesProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.historicomesproy.HistoricoMesProyRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos.ConvProyAlumnoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocialcotizames.SegSocialCotizaMesRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesprog.SegSocialCotizaMesProgRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.segsocicotizamesproy.SegSocialCotizaMesProyRepository;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.AlumnoPrograma;
import es.jccm.edu.proyectosfct.application.domain.datosgestora.entities.DatosGestora;
import es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities.EnviosCotizaMesGestoraLog;
import es.jccm.edu.proyectosfct.application.domain.historicomesprogramas.entities.HistoricoMesProg;
import es.jccm.edu.proyectosfct.application.domain.historicomesproyectos.entities.HistoricoMesProy;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.ElementoSelectProjection;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAlumno;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.entities.ListadoSegSocialCotizaMes;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection.ListadoHistoricoCotizaMesProjection;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection.ListadoSegSocialCotizaMesProjection;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesprogramas.entities.CotizaMesProgramas;
import es.jccm.edu.proyectosfct.application.domain.segsocicotizamesproyectos.entities.CotizaMesProyectos;
import es.jccm.edu.proyectosfct.application.ports.in.segsocicotizames.ISegSociCotizaMesService;
import es.jccm.edu.proyectosfct.application.services.datosgestora.DatosGestoraService;
import es.jccm.edu.proyectosfct.application.services.datosgestorames.DatosGestoraMesService;
import es.jccm.edu.shared.adapter.in.rest.segsocial.model.RegisterDaysContributedDto;
import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterDaysContributed;
import es.jccm.edu.shared.application.ports.out.resttemplate.segsocial.IClientSegSocial;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SegSocialCotizaMesService implements ISegSociCotizaMesService {

    private static final Long ID_PERFIL_GESTOR = 11207L;
    private static final Long ID_PERFIL_CONSEJERIA_FCT = 13207L;
    private static final Long ID_PERFIL_CONSEJERIA_FCT_1 = 15207L;
    private static final Long ID_PERFIL_CENTRO = 161L;
    //private static Integer MAX_DIA_LIQUIDACIONES;

    @Autowired
    private SegSocialCotizaMesRepository segSocialCotizaMesRepository;

    @Autowired
    private SegSocialCotizaMesProyRepository segSocialCotizaMesProyRepository;

    @Autowired
    private SegSocialCotizaMesProgRepository segSocialCotizaMesProgRepository;

    @Autowired
    private AlumnoProgramaRepository alumnoProgramaRepository;

    @Autowired
    private ConvProyAlumnoRepository convProyAlumnoRepository;

    @Autowired
    private HistoricoMesProgRepository historicoMesProgRepository;

    @Autowired
    private HistoricoMesProyRepository historicoMesProyRepository;

    @Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	IClientSegSocial clientSegSocial;

    @Autowired
    private EnviosCotizaMesGestoraLogRepository enviosCotizaMesGestoraLogRepository;

    @Autowired
    private DatosGestoraService datosGestoraService;

    @Autowired
    private DatosGestoraMesService datosGestoraMesService;


    @Override
    public List<ListadoSegSocialCotizaMes> getListadoCotizaMes(Long idTutorfctdual,
                                                                Long idCentro,
                                                                Integer cAnno,
                                                                Integer tipoEmpresa,
                                                                Long idEmpresa,
                                                                Long idOfertamatrig,
                                                                Long idUnidad,
                                                                Long idPerfil,
                                                                Long idCentroCombo,
                                                                Long idProvincia,
                                                                Integer idEstado,
                                                                Integer nuMes,
                                                                Long idUsuario,
                                                                Integer idTipo) {

        List<ListadoSegSocialCotizaMesProjection> listadoAlumnosTutor = null;


        if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {

            if (ID_PERFIL_GESTOR.equals(idPerfil)) {           	
            	
            	//listadoAlumnosTutor = getCotizacionesDelegacion(idTutorfctdual, idCentro, cAnno, tipoEmpresa, idEmpresa,
				//		idOfertamatrig, idUnidad, idPerfil, idCentroCombo, idEstado, nuMes, idUsuario, idTipo);    
            	
            	listadoAlumnosTutor = segSocialCotizaMesRepository.findListadoCotizaMesDelegacion(idTutorfctdual,
    		            idCentro,
    		            cAnno,
    		            tipoEmpresa,
    		            idEmpresa,
    		            idOfertamatrig,
    		            idUnidad,
    		            idPerfil,
    		            idCentroCombo,
    		            idUsuario,
    		            idEstado,
    		            nuMes,
    		            idTipo);  

            } else {

                listadoAlumnosTutor = segSocialCotizaMesRepository.findListadoCotizaMesDelegacionProvincias(idTutorfctdual,
                        idCentro,
                        cAnno,
                        tipoEmpresa,
                        idEmpresa,
                        idOfertamatrig,
                        idUnidad,
                        idCentroCombo,
                        idProvincia,
                        idEstado,
                        nuMes,
                        idTipo);
            }

        } else {

            if (ID_PERFIL_CENTRO.equals(idPerfil)) {

                listadoAlumnosTutor = segSocialCotizaMesRepository.findListadoCotizaMesCen(idTutorfctdual,
                        idCentro,
                        cAnno,
                        tipoEmpresa,
                        idEmpresa,
                        idOfertamatrig,
                        idUnidad,
                        idEstado,
                        nuMes,
                        idTipo);

            } else {

                listadoAlumnosTutor = segSocialCotizaMesRepository.findListadoCotizaMes(idTutorfctdual,
                        idCentro,
                        cAnno,
                        tipoEmpresa,
                        idEmpresa,
                        idOfertamatrig,
                        idUnidad,
                        idEstado,
                        nuMes,
                        idTipo,
                        idUsuario);

            }
        }

        //Se revisa si al alumno se le muestran o no avisos por falta de cotizaciones mensuales en el mes anterior o si cumple los requisitos para NO estar bloqueado
        return revisarListadoAvisosBloqueo(listadoAlumnosTutor, nuMes, idPerfil);

    }

	private List<ListadoSegSocialCotizaMesProjection> getCotizacionesDelegacion(Long idTutorfctdual, 
			                                                                    Long idCentro,
			                                                                    Integer cAnno, 
			                                                                    Integer tipoEmpresa, 
			                                                                    Long idEmpresa, 
			                                                                    Long idOfertamatrig, 
			                                                                    Long idUnidad, 
			                                                                    Long idPerfil,
			                                                                    Long idCentroCombo, 
			                                                                    Integer idEstado, 
			                                                                    Integer nuMes, 
			                                                                    Long idUsuario, 
			                                                                    Integer idTipo) {
		
		List<ListadoSegSocialCotizaMesProjection> listadoAlumnosTutor;		
		
		if (idCentroCombo.equals(Long.valueOf(-1)) && idEstado.equals(1)) {
			
		    listadoAlumnosTutor = segSocialCotizaMesRepository.findListadoCotizaMesDelegacionSinValidar(idTutorfctdual,
		            idCentro,
		            cAnno,
		            tipoEmpresa,
		            idEmpresa,
		            idOfertamatrig,
		            idUnidad,
		            idPerfil,
		            idCentroCombo,
		            idUsuario, 
		            nuMes,
		            idTipo);            		
			
		} else if (idCentroCombo.equals(Long.valueOf(-1)) && idEstado.equals(7))  {
			
		    listadoAlumnosTutor = segSocialCotizaMesRepository.findListadoCotizaMesDelegacionValidadasSinEnviar(idTutorfctdual,
		            idCentro,
		            cAnno,
		            tipoEmpresa,
		            idEmpresa,
		            idOfertamatrig,
		            idUnidad,
		            idPerfil,
		            idCentroCombo,
		            idUsuario,
		            nuMes,
		            idTipo);     		
			
		} else {
			
		    listadoAlumnosTutor = segSocialCotizaMesRepository.findListadoCotizaMesDelegacion(idTutorfctdual,
		            idCentro,
		            cAnno,
		            tipoEmpresa,
		            idEmpresa,
		            idOfertamatrig,
		            idUnidad,
		            idPerfil,
		            idCentroCombo,
		            idUsuario,
		            idEstado,
		            nuMes,
		            idTipo);            		
		}
		return listadoAlumnosTutor;
	}

    @Override
    public void createUpdateListadoCotizaMes(List<ListadoSegSocialCotizaMes> listadoSegSocialCotizaMesIn, Long xUsuarioDelphos) {

        for (ListadoSegSocialCotizaMes alumnoCotizaMes: listadoSegSocialCotizaMesIn){

            if(alumnoCotizaMes.getId() == -1){ //Si el ID es -1 creamos un nuevo registro

                createAltaSeguridadSocialMes(xUsuarioDelphos, alumnoCotizaMes);

            }else{ //Si el ID es no es -1 actualizamos el registro existente

                updateAltaSeguridadSocialMes(alumnoCotizaMes);

            }

        }

    }

    private void updateAltaSeguridadSocialMes(ListadoSegSocialCotizaMes alumnoCotizaMes) {

        if("FCT".equals(alumnoCotizaMes.getTipoEmpresa())){
            updateAltaSegSociMesPrograma(alumnoCotizaMes);

        }  else if("FP Dual".equals(alumnoCotizaMes.getTipoEmpresa())){
            updateSegSociMesProyecto(alumnoCotizaMes);
        }
    }

    private void updateSegSociMesProyecto(ListadoSegSocialCotizaMes alumnoCotizaMes) {
        CotizaMesProyectos cotizaMesProy = null;
        //Recuperamos de la tabla FCT_COTIZAMES_PROY
        cotizaMesProy = segSocialCotizaMesProyRepository.findById(alumnoCotizaMes.getId()).orElse(null);

        if(cotizaMesProy != null){

            //Asignamos los valores que recibimos
            cotizaMesProy.setNuMes(alumnoCotizaMes.getNuMes());
            cotizaMesProy.setNuDiasNacu(alumnoCotizaMes.getNuDiasNacu());
            cotizaMesProy.setNuDiasInte(alumnoCotizaMes.getNuDiasInte());
            cotizaMesProy.setNuDiasReal(alumnoCotizaMes.getNuDiasReal());
            cotizaMesProy.setLgValTut("Sí".equals(alumnoCotizaMes.getValTut()) ? 1 : 0);
            cotizaMesProy.setLgValCen("Sí".equals(alumnoCotizaMes.getValCen()) ? 1 : 0);
            cotizaMesProy.setLgValDel("Sí".equals(alumnoCotizaMes.getValDel()) ? 1 : 0);
            cotizaMesProy.setNuDiasInteEra(alumnoCotizaMes.getDiasInteEra());

            segSocialCotizaMesProyRepository.save(cotizaMesProy);

        }
    }

    private void updateAltaSegSociMesPrograma(ListadoSegSocialCotizaMes alumnoCotizaMes) {
        CotizaMesProgramas cotizaMesProg = null;
        //Recuperamos de la tabla FCT_COTIZAMES_PROG
        cotizaMesProg = segSocialCotizaMesProgRepository.findById(alumnoCotizaMes.getId()).orElse(null);

        if(cotizaMesProg != null){

            //Asignamos los valores que recibimos
            cotizaMesProg.setNuMes(alumnoCotizaMes.getNuMes());
            cotizaMesProg.setNuDiasNacu(alumnoCotizaMes.getNuDiasNacu());
            cotizaMesProg.setNuDiasInte(alumnoCotizaMes.getNuDiasInte());
            cotizaMesProg.setNuDiasReal(alumnoCotizaMes.getNuDiasReal());
            cotizaMesProg.setLgValTut("Sí".equals(alumnoCotizaMes.getValTut()) ? 1 : 0);
            cotizaMesProg.setLgValCen("Sí".equals(alumnoCotizaMes.getValCen()) ? 1 : 0);
            cotizaMesProg.setLgValDel("Sí".equals(alumnoCotizaMes.getValDel()) ? 1 : 0);
            cotizaMesProg.setNuDiasInteEra(alumnoCotizaMes.getDiasInteEra());

            segSocialCotizaMesProgRepository.save(cotizaMesProg);
        }
    }

    private void createAltaSeguridadSocialMes(Long xUsuarioDelphos, ListadoSegSocialCotizaMes alumnoCotizaMes) {

        if("FCT".equals(alumnoCotizaMes.getTipoEmpresa())){ //Lo creamos en la tabla FCT_COTIZAMES_PROG

            createAltaSegSociMesPrograma(xUsuarioDelphos, alumnoCotizaMes);

        }else if("FP Dual".equals(alumnoCotizaMes.getTipoEmpresa())){ //Lo creamos en la tabla FCT_COTIZAMES_PROY

            createAltaSegSociMesProyecto(xUsuarioDelphos, alumnoCotizaMes);

        }
    }

    private void createAltaSegSociMesProyecto(Long xUsuarioDelphos, ListadoSegSocialCotizaMes alumnoCotizaMes) {
        CotizaMesProyectos cotizaMesProyectos = new CotizaMesProyectos();

        cotizaMesProyectos.setNuMes(alumnoCotizaMes.getNuMes());
        cotizaMesProyectos.setNuDiasNacu(alumnoCotizaMes.getNuDiasNacu());
        cotizaMesProyectos.setNuDiasInte(alumnoCotizaMes.getNuDiasInte());
        cotizaMesProyectos.setNuDiasReal(alumnoCotizaMes.getNuDiasReal());
        cotizaMesProyectos.setLgValTut("Sí".equals(alumnoCotizaMes.getValTut()) ? 1 : 0);
        cotizaMesProyectos.setLgValCen("Sí".equals(alumnoCotizaMes.getValCen()) ? 1 : 0);
        cotizaMesProyectos.setLgValDel("Sí".equals(alumnoCotizaMes.getValDel()) ? 1 : 0);
        cotizaMesProyectos.setNuDiasInteEra(alumnoCotizaMes.getDiasInteEra());
        ConveniosProyectoAlumno aluProy = convProyAlumnoRepository.findById(alumnoCotizaMes.getIdAluCon()).orElse(null);
        cotizaMesProyectos.setIdConvProyAlu(alumnoCotizaMes.getIdAluCon());
        cotizaMesProyectos.setXusuarioUlt(xUsuarioDelphos);
        if(aluProy != null && aluProy.getIdMatricula() != null){
            cotizaMesProyectos.setXMatricula(aluProy.getIdMatricula());
        }

        segSocialCotizaMesProyRepository.save(cotizaMesProyectos);
    }

    private void createAltaSegSociMesPrograma(Long xUsuarioDelphos, ListadoSegSocialCotizaMes alumnoCotizaMes) {
        CotizaMesProgramas cotizaMesProgramas = new CotizaMesProgramas();

        cotizaMesProgramas.setNuMes(alumnoCotizaMes.getNuMes());
        cotizaMesProgramas.setNuDiasNacu(alumnoCotizaMes.getNuDiasNacu());
        cotizaMesProgramas.setNuDiasInte(alumnoCotizaMes.getNuDiasInte());
        cotizaMesProgramas.setNuDiasReal(alumnoCotizaMes.getNuDiasReal());
        cotizaMesProgramas.setLgValTut("Sí".equals(alumnoCotizaMes.getValTut()) ? 1 : 0);
        cotizaMesProgramas.setLgValCen("Sí".equals(alumnoCotizaMes.getValCen()) ? 1 : 0);
        cotizaMesProgramas.setLgValDel("Sí".equals(alumnoCotizaMes.getValDel()) ? 1 : 0);
        cotizaMesProgramas.setNuDiasInteEra(alumnoCotizaMes.getDiasInteEra());
        AlumnoPrograma aluProg = alumnoProgramaRepository.findById(alumnoCotizaMes.getIdAluCon()).orElse(null);
        cotizaMesProgramas.setIdConvProgAlu(alumnoCotizaMes.getIdAluCon());
        cotizaMesProgramas.setXusuarioUlt(xUsuarioDelphos);
        if(aluProg != null && aluProg.getIdMatricula() != null){
            cotizaMesProgramas.setXMatricula(aluProg.getIdMatricula());
        }

        segSocialCotizaMesRepository.save(cotizaMesProgramas);
    }

    @Override
    public List<ElementoSelect> getEstadosSS(Long idPerfil) {

        List<ElementoSelectProjection> cursos = segSocialCotizaMesRepository.getEstadosSS(idPerfil);

        return cursos.stream().map(entity -> modelMapper.map(entity, ElementoSelect.class)).collect(Collectors.toList());
    }

    @Override
    public void resetEstadoValidarMes(Long id, Long idPerfil, String tipoEmpresa){

        if("FCT".equals(tipoEmpresa)){

            resetEstadoProgramaMes(id, idPerfil);

        }else {

            resetEstadoProyectoMes(id, idPerfil);

        }

    }
    
    private void resetEstadoProyectoMes(Long id, Long idPerfil) {
    	CotizaMesProyectos altasSegSociProy = segSocialCotizaMesProyRepository.findById(id).orElse(null);

        if(altasSegSociProy != null){

            if(ID_PERFIL_GESTOR.equals(idPerfil))
            {
            	altasSegSociProy.setLgValDel(0);
                altasSegSociProy.setLgValCen(0);
            } else if (ID_PERFIL_CENTRO.equals(idPerfil)) {            	
            	altasSegSociProy.setLgValCen(0);
            }

            altasSegSociProy.setLgValTut(0);
            segSocialCotizaMesProyRepository.save(altasSegSociProy);

        }
    }

    private void resetEstadoProgramaMes(Long id, Long idPerfil) {
    	CotizaMesProgramas cotizaMesProgramas = segSocialCotizaMesProgRepository.findById(id).orElse(null);

        if(cotizaMesProgramas != null){

        	if(ID_PERFIL_GESTOR.equals(idPerfil)){
        		cotizaMesProgramas.setLgValDel(0);
        		cotizaMesProgramas.setLgValCen(0);
            } else if (ID_PERFIL_CENTRO.equals(idPerfil)) {
            	cotizaMesProgramas.setLgValCen(0);            	
            }

        	cotizaMesProgramas.setLgValTut(0);
        	segSocialCotizaMesProgRepository.save(cotizaMesProgramas);
        }
    }

    private void registrarCotizaMesLog(RegisterDaysContributed data, String tipoEnvio) {

        EnviosCotizaMesGestoraLog log = modelMapper.map(data, EnviosCotizaMesGestoraLog.class);

        log.setFechaEnvio(new Date());
        log.setTipoEnvio(tipoEnvio);

        enviosCotizaMesGestoraLogRepository.save(log);
    }

    /*
	@Override
	public Integer envioCotizacionesMensuales(List<RegisterDaysContributedDto> registerDaysContributedDtoList,
			Long xUsuarioDelphos) {

        Integer envio = 0 ;
        List<RegisterDaysContributed> listaRegisterDaysContributed = new ArrayList<>();

        for (RegisterDaysContributedDto dto : registerDaysContributedDtoList) {
            try {

                datosGestoraService.actualizarDatosGestoraDeAlumno(dto.getNif(), dto.getIdCentro(), xUsuarioDelphos);

                List<DatosGestora> datos = datosGestoraService.getDatosGestoraByAlumno(
                        dto.getNif(), dto.getIdAlu(), dto.getTipoEmpresa(), -1L);

                boolean tieneAltaCorrecta = datos.stream()
                        .filter(d -> "Alta".equalsIgnoreCase(d.getDsTipo()))
                        .filter(d -> "Correcto".equalsIgnoreCase(d.getDsEstado()))
                        .anyMatch(d -> rangoCubreMes(d.getFechaAlta(), d.getFechaBaja(), dto.getMonth(), dto.getYear()));


                if (!tieneAltaCorrecta) {
                    // No se envía, no tiene un alta válida para el mes y el año
                    continue;
                }

                RegisterDaysContributed registerDayContributed = modelMapper.map(dto, RegisterDaysContributed.class);
                listaRegisterDaysContributed.add(registerDayContributed);

                envio  += clientSegSocial.envioCotizacionesMensuales(listaRegisterDaysContributed, xUsuarioDelphos);

                if (envio > 0 ) {
                    for (RegisterDaysContributed registroCotizacion : listaRegisterDaysContributed) {
                        updateEnvioFecha(registroCotizacion);
                    }
                }

                datosGestoraMesService.actualizarDatosGestoraMesDeAlumno(dto.getNif(), dto.getIdCentro(), dto.getYear(), dto.getMonth(), xUsuarioDelphos);

                listaRegisterDaysContributed.clear();

            } catch (Exception e) {
                System.err.println("Error al procesar el registro: " + dto + " - " + e.getMessage());
            }
        }

        return envio;
	}
	*/


    @Override
    public Integer envioCotizacionesMensuales(List<RegisterDaysContributed> registerDaysContributed,
                                              Long xUsuarioDelphos) {

        Integer envio = 0 ;
        List<RegisterDaysContributed> listaRegisterDaysContributed = new ArrayList<>();

        for(RegisterDaysContributed registerDayContributed: registerDaysContributed){
            try {
                listaRegisterDaysContributed.add(registerDayContributed);

                envio  += clientSegSocial.envioCotizacionesMensuales(listaRegisterDaysContributed, xUsuarioDelphos);

                if (envio > 0 ) {
                    for (RegisterDaysContributed mes : listaRegisterDaysContributed) {
                        updateEnvioFecha(mes);
                    }
                }

                listaRegisterDaysContributed.clear();
            } catch (Exception e) {
                System.err.println("Error al procesar el registro: " + registerDayContributed + " - " + e.getMessage());
            }
        }

        return envio;
    }

    private boolean rangoCubreMes(Date fechaAlta, Date fechaBaja, int mes, int anno) {
        if (fechaAlta == null || fechaBaja == null) return false;

        Calendar inicioMes = Calendar.getInstance();
        inicioMes.set(Calendar.YEAR, anno);
        inicioMes.set(Calendar.MONTH, mes - 1); // Enero = 0
        inicioMes.set(Calendar.DAY_OF_MONTH, 1);

        Calendar finMes = Calendar.getInstance();
        finMes.set(Calendar.YEAR, anno);
        finMes.set(Calendar.MONTH, mes - 1);
        finMes.set(Calendar.DAY_OF_MONTH, finMes.getActualMaximum(Calendar.DAY_OF_MONTH));

        return !fechaBaja.before(inicioMes.getTime()) && !fechaAlta.after(finMes.getTime());
    }

    private void updateEnvioFecha(RegisterDaysContributed registroCotizacion) {

        String tipoEnvio = "ENVIO";

    	if (registroCotizacion.getTipoEmpresa().equals("FCT")) {

                Optional<CotizaMesProgramas> cotizaMesProgramas = segSocialCotizaMesProgRepository.findById(registroCotizacion.getIdAlu());

                   if (cotizaMesProgramas.isPresent() && registroCotizacion.getIdAlu()!=-1) {
                	   cotizaMesProgramas.get().setFechaEnvio(new Date());
                	   cotizaMesProgramas.get().setLgEnvioEmp(1);
                       cotizaMesProgramas.get().setWarnings(registroCotizacion.getWarnings());
                	   segSocialCotizaMesProgRepository.save(cotizaMesProgramas.get());

                       if (!historicoMesProgRepository.findByIdCotizaMesProg(registroCotizacion.getIdAlu()).isEmpty()) {
                           tipoEnvio = "MODIFICACION";
                       }
                   }

        } else {
                Optional<CotizaMesProyectos> cotizaMesProyectos = segSocialCotizaMesProyRepository.findById(registroCotizacion.getIdAlu());

                if (cotizaMesProyectos.isPresent() && registroCotizacion.getIdAlu()!=-1) {
                	cotizaMesProyectos.get().setFechaEnvio(new Date());
                	cotizaMesProyectos.get().setLgEnvioEmp(1);
                    cotizaMesProyectos.get().setWarnings(registroCotizacion.getWarnings());
                    segSocialCotizaMesProyRepository.save(cotizaMesProyectos.get());

                    if (!historicoMesProyRepository.findByIdCotizaMesProy(registroCotizacion.getIdAlu()).isEmpty()) {
                        tipoEnvio = "MODIFICACION";
                    }
                }
        }
        registrarCotizaMesLog(registroCotizacion, tipoEnvio);
    }

    @Override
    public List<ListadoHistoricoCotizaMesProjection> getListadoHistoricoMes(Long idCotizaMes, String tipo) {

        List<ListadoHistoricoCotizaMesProjection> listadoHistoricoCotizaMesProjectionList = null;

        if("FCT".equals(tipo)){
            listadoHistoricoCotizaMesProjectionList = segSocialCotizaMesProgRepository.getListadoHistoricoMes(idCotizaMes);
        }else {
            listadoHistoricoCotizaMesProjectionList = segSocialCotizaMesProyRepository.getListadoHistoricoMes(idCotizaMes);
        }

        return listadoHistoricoCotizaMesProjectionList;

    }

    @Override
    public void modifyHistoricoMes(ListadoHistoricoMesDto listadoHistoricoMesDtoIn, Long xUsuarioDelphos) {

        if("FCT".equals(listadoHistoricoMesDtoIn.getTipo())){
            updateSegSociCotizaMesProg(listadoHistoricoMesDtoIn, xUsuarioDelphos);
        }else{
            updateSegSociCotizaMesProy(listadoHistoricoMesDtoIn, xUsuarioDelphos);
        }

    }


    private void updateSegSociCotizaMesProg(ListadoHistoricoMesDto listadoHistoricoMesDtoIn, Long xUsuarioDelphos) {
        CotizaMesProgramas cotizaMesProgramas = segSocialCotizaMesProgRepository.findById(listadoHistoricoMesDtoIn.getIdCotizaMes()).orElse(null);
        if(cotizaMesProgramas != null){
            saveHistoricoMesProg(cotizaMesProgramas, xUsuarioDelphos);
            saveSegSociCotizaMesProg(listadoHistoricoMesDtoIn, cotizaMesProgramas);
        }
    }

    private void updateSegSociCotizaMesProy(ListadoHistoricoMesDto listadoHistoricoMesDtoIn, Long xUsuarioDelphos) {
        CotizaMesProyectos cotizaMesProyectos = segSocialCotizaMesProyRepository.findById(listadoHistoricoMesDtoIn.getIdCotizaMes()).orElse(null);
        if(cotizaMesProyectos != null){
            saveHistoricoMesProy(cotizaMesProyectos, xUsuarioDelphos);
            saveSegSociCotizaMesProy(listadoHistoricoMesDtoIn, cotizaMesProyectos);
        }
    }

    private void saveHistoricoMesProg(CotizaMesProgramas cotizaMesProgramas, Long xUsuarioDelphos) {
        List<HistoricoMesProg> listadoHistoricoMesProg = historicoMesProgRepository.findByIdCotizaMesProg(cotizaMesProgramas.getIdCotizaMes());
        HistoricoMesProg historicoMesProg = new HistoricoMesProg();
        historicoMesProg.setIdCotizaMesProg(cotizaMesProgramas.getIdCotizaMes());
        historicoMesProg.setNuMesProg(cotizaMesProgramas.getNuMes());
        historicoMesProg.setNuDiasRealMesProg(cotizaMesProgramas.getNuDiasReal());
        historicoMesProg.setNuDiasInteMesProg(cotizaMesProgramas.getNuDiasInte());
        historicoMesProg.setNuDiasNacuMesProg(cotizaMesProgramas.getNuDiasNacu());
        historicoMesProg.setNuDiasInteEraMesProg(cotizaMesProgramas.getNuDiasInteEra());
        historicoMesProg.setLgValTutMesProg(cotizaMesProgramas.getLgValTut());
        historicoMesProg.setLgValCenMesProg(cotizaMesProgramas.getLgValCen());
        historicoMesProg.setLgValDelMesProg(cotizaMesProgramas.getLgValDel());
        historicoMesProg.setFechaEnvioMesProg(cotizaMesProgramas.getFechaEnvio());
        historicoMesProg.setDsWarningsMesProg(cotizaMesProgramas.getWarnings());
        historicoMesProg.setIdUsuarioUltMesProg(xUsuarioDelphos);
        if(listadoHistoricoMesProg == null){
            historicoMesProg.setNuPeticionMesProg(1);
        }else{
            historicoMesProg.setNuPeticionMesProg(listadoHistoricoMesProg.size() + 1);
        }

        historicoMesProgRepository.save(historicoMesProg);
    }

    private void saveHistoricoMesProy(CotizaMesProyectos cotizaMesProyectos, Long xUsuarioDelphos) {
        List<HistoricoMesProg> listadoHistoricoMesProy = historicoMesProgRepository.findByIdCotizaMesProg(cotizaMesProyectos.getIdCotizaMes());
        HistoricoMesProy historicoMesProy = new HistoricoMesProy();
        historicoMesProy.setIdCotizaMesProy(cotizaMesProyectos.getIdCotizaMes());
        historicoMesProy.setNuMesProy(cotizaMesProyectos.getNuMes());
        historicoMesProy.setNuDiasRealMesProy(cotizaMesProyectos.getNuDiasReal());
        historicoMesProy.setNuDiasInteMesProy(cotizaMesProyectos.getNuDiasInte());
        historicoMesProy.setNuDiasNacuMesProy(cotizaMesProyectos.getNuDiasNacu());
        historicoMesProy.setNuDiasInteEraMesProy(cotizaMesProyectos.getNuDiasInteEra());
        historicoMesProy.setLgValTutMesProy(cotizaMesProyectos.getLgValTut());
        historicoMesProy.setLgValCenMesProy(cotizaMesProyectos.getLgValCen());
        historicoMesProy.setLgValDelMesProy(cotizaMesProyectos.getLgValDel());
        historicoMesProy.setFechaEnvioMesProy(cotizaMesProyectos.getFechaEnvio());
        historicoMesProy.setDsWarningsMesProy(cotizaMesProyectos.getWarnings());
        historicoMesProy.setIdUsuarioUltMesProy(xUsuarioDelphos);
        if(listadoHistoricoMesProy == null){
            historicoMesProy.setNuPeticionMesProy(1);
        }else{
            historicoMesProy.setNuPeticionMesProy(listadoHistoricoMesProy.size() + 1);
        }

        historicoMesProyRepository.save(historicoMesProy);
    }

    private void saveSegSociCotizaMesProg(ListadoHistoricoMesDto listadoHistoricoMesDto, CotizaMesProgramas cotizaMesProgramas) {
        cotizaMesProgramas.setLgValTut(0);
        cotizaMesProgramas.setLgValCen(0);
        cotizaMesProgramas.setLgValDel(0);
        cotizaMesProgramas.setFechaEnvio(null);
        cotizaMesProgramas.setLgEnvioEmp(0);
        cotizaMesProgramas.setNuDiasInte(listadoHistoricoMesDto.getNuDiasInteMesProg());
        cotizaMesProgramas.setNuDiasInteEra(listadoHistoricoMesDto.getNuDiasInteEraMesProg());
        cotizaMesProgramas.setNuDiasNacu(listadoHistoricoMesDto.getNuDiasNacuMesProg());
        cotizaMesProgramas.setNuDiasReal(listadoHistoricoMesDto.getNuDiasRealMesProg());
        cotizaMesProgramas.setWarnings(listadoHistoricoMesDto.getDsWarnings());

        segSocialCotizaMesProgRepository.save(cotizaMesProgramas);
    }

    private void saveSegSociCotizaMesProy(ListadoHistoricoMesDto listadoHistoricoMesDto, CotizaMesProyectos cotizaMesProyectos) {
        cotizaMesProyectos.setLgValTut(0);
        cotizaMesProyectos.setLgValCen(0);
        cotizaMesProyectos.setLgValDel(0);
        cotizaMesProyectos.setFechaEnvio(null);
        cotizaMesProyectos.setLgEnvioEmp(0);
        cotizaMesProyectos.setNuDiasInte(listadoHistoricoMesDto.getNuDiasInteMesProg());
        cotizaMesProyectos.setNuDiasInteEra(listadoHistoricoMesDto.getNuDiasInteEraMesProg());
        cotizaMesProyectos.setNuDiasNacu(listadoHistoricoMesDto.getNuDiasNacuMesProg());
        cotizaMesProyectos.setNuDiasReal(listadoHistoricoMesDto.getNuDiasRealMesProg());
        cotizaMesProyectos.setWarnings(listadoHistoricoMesDto.getDsWarnings());

        segSocialCotizaMesProyRepository.save(cotizaMesProyectos);
    }


    @Override
    public void revertUltimoEstado(Long idCotizaMes, String nuPeticion) {

        HistoricoMesProg historicoMesProg = null;
        HistoricoMesProy historicoMesProy = null;

        historicoMesProg = historicoMesProgRepository.findByIdCotizaMesProgAndNuPeticionMesProg(idCotizaMes, Integer.parseInt(nuPeticion) - 1);

        if(historicoMesProg != null){
            updateCotizaMenProgAndHistMesProg(idCotizaMes, historicoMesProg);
        }else{
            historicoMesProy = historicoMesProyRepository.findByIdCotizaMesProyAndNuPeticionMesProy(idCotizaMes, Integer.parseInt(nuPeticion) - 1);
            updateCotizaMenProyAndHistMesProy(idCotizaMes, historicoMesProy);
        }

    }

    private void updateCotizaMenProyAndHistMesProy(Long idCotizaMes, HistoricoMesProy historicoMesProy) {

        CotizaMesProyectos cotizaMesProyectos = segSocialCotizaMesProyRepository.findById(idCotizaMes).orElse(null);

        if(cotizaMesProyectos != null){
            //Seteamos los valores del histórico al registro obtenido de la tabla FCT_COTIZAMES_PROY
            cotizaMesProyectos.setNuDiasReal(historicoMesProy.getNuDiasRealMesProy());
            cotizaMesProyectos.setNuDiasInteEra(historicoMesProy.getNuDiasInteEraMesProy());
            cotizaMesProyectos.setNuDiasNacu(historicoMesProy.getNuDiasNacuMesProy());
            cotizaMesProyectos.setNuDiasInte(historicoMesProy.getNuDiasInteMesProy());
            cotizaMesProyectos.setLgValDel(historicoMesProy.getLgValDelMesProy());
            cotizaMesProyectos.setLgValCen(historicoMesProy.getLgValCenMesProy());
            cotizaMesProyectos.setLgValTut(historicoMesProy.getLgValTutMesProy());
            cotizaMesProyectos.setNuMes(historicoMesProy.getNuMesProy());
            cotizaMesProyectos.setFechaEnvio(historicoMesProy.getFechaEnvioMesProy());
            cotizaMesProyectos.setWarnings(historicoMesProy.getDsWarningsMesProy());

            //Guardamos el registro con los valores del histórico
            segSocialCotizaMesProyRepository.save(cotizaMesProyectos);
            //Eliminamos el registro del histórico
            historicoMesProyRepository.delete(historicoMesProy);
        }

    }

    private void updateCotizaMenProgAndHistMesProg(Long idCotizaMes, HistoricoMesProg historicoMesProg) {

        CotizaMesProgramas cotizaMesProgramas = segSocialCotizaMesProgRepository.findById(idCotizaMes).orElse(null);

        if(cotizaMesProgramas != null){
            //Seteamos los valores del histórico al registro obtenido de la tabla FCT_COTIZAMES_PROG
            cotizaMesProgramas.setNuDiasReal(historicoMesProg.getNuDiasRealMesProg());
            cotizaMesProgramas.setNuDiasInteEra(historicoMesProg.getNuDiasInteEraMesProg());
            cotizaMesProgramas.setNuDiasNacu(historicoMesProg.getNuDiasNacuMesProg());
            cotizaMesProgramas.setNuDiasInte(historicoMesProg.getNuDiasInteMesProg());
            cotizaMesProgramas.setLgValDel(historicoMesProg.getLgValDelMesProg());
            cotizaMesProgramas.setLgValCen(historicoMesProg.getLgValCenMesProg());
            cotizaMesProgramas.setLgValTut(historicoMesProg.getLgValTutMesProg());
            cotizaMesProgramas.setNuMes(historicoMesProg.getNuMesProg());
            cotizaMesProgramas.setFechaEnvio(historicoMesProg.getFechaEnvioMesProg());
            cotizaMesProgramas.setWarnings(historicoMesProg.getDsWarningsMesProg());

            //Guardamos el registro con los valores del histórico
            segSocialCotizaMesRepository.save(cotizaMesProgramas);
            //Eliminamos el registro del histórico
            historicoMesProgRepository.delete(historicoMesProg);
        }
    }

    @Override
    public int getConsistenciaMesGestora(Long id, String tipo, Integer nuMes, Integer nuDiasReal, Integer nuDiasNacu, Integer nuDiasInte, Integer nuDiasInteEra) {

        if("FCT".equals(tipo)){
            return segSocialCotizaMesProgRepository.getConsistenciaMesGestora(id, nuMes, nuDiasReal, nuDiasNacu, nuDiasInte, nuDiasInteEra);
        } else {
            return segSocialCotizaMesProyRepository.getConsistenciaMesGestora(id, nuMes, nuDiasReal, nuDiasNacu, nuDiasInte, nuDiasInteEra);
        }

    }

    @Override
    public Integer getMaxDiaLiquidacionMensual(){
        return segSocialCotizaMesProyRepository.getMaxDiaLiquidacionMensual();
    }

    private List<ListadoSegSocialCotizaMes> revisarListadoAvisosBloqueo(List<ListadoSegSocialCotizaMesProjection>listadoAlumnosTutor, Integer nuMes, Long idPerfil){
        //Parsear la entidad para modificar a los alumnos en caso necesario
        List<ListadoSegSocialCotizaMes> listadoParsed = listadoAlumnosTutor.stream()
                .map(entity -> modelMapper.map(entity, ListadoSegSocialCotizaMes.class))
                .collect(Collectors.toList());

        //Si el mes seleccionado NO es posterior (dentro del año académico, es decir, de Octubre en adelante) al actual que haga las comprobaciones
        if (esMesComprobable(nuMes)) {
            listadoParsed = gestionarMensajeAviso(listadoParsed, nuMes);
            listadoParsed = gestionarBloqueos(listadoParsed, nuMes);

        } else { //En caso estar seleccionado el mes actual o posterior se quita el bloqueo de todos los alumnos
            listadoParsed = desbloquearTodosLosAlumnos(listadoParsed);
        }

        return listadoParsed;
    }

    private List<ListadoSegSocialCotizaMes> gestionarMensajeAviso(List<ListadoSegSocialCotizaMes> listadoParsed, int nuMes){
        if(comprobarDiasMesCorrecto(nuMes)) {
            for(ListadoSegSocialCotizaMes alumno : listadoParsed){
                boolean haCotizado;

                if (alumno.getTipoEmpresa().equals("FCT")) {
                    haCotizado = segSocialCotizaMesProgRepository.findByIdConvProgAluAndNuMes(alumno.getIdAluCon(), nuMes) != null;
                } else {
                    haCotizado = segSocialCotizaMesProyRepository.findByIdConvProyAluAndNuMes(alumno.getIdAluCon(), nuMes) != null;
                }

                // Si no hay registros de que haya cotizado en el mes anterior, se pone el aviso
                if (!haCotizado) {
                    alumno.setAvisoMes(1);
                }
            }
        }
        return listadoParsed;
    }

    /*
       Comprobar si la fecha actual está entre el día 1 y el día límite (obtenido por bd) del mes posterior al seleccionado.
     */
    private boolean comprobarDiasMesCorrecto(Integer nuMes) {
        LocalDate hoy = LocalDate.now();
        int mesAnterior = (hoy.getMonthValue() == 1) ? 12 : hoy.getMonthValue() - 1; //En el caso de enero, ponemos que el anterior sea diciembre

        //MAX_DIA_LIQUIDACIONES = segSocialCotizaMesProgRepository.getMaxDiaLiquidacionMensual();
        //return mesAnterior == nuMes && hoy.getDayOfMonth() <= MAX_DIA_LIQUIDACIONES;
        return mesAnterior == nuMes && hoy.getDayOfMonth() <= segSocialCotizaMesProyRepository.getMaxDiaLiquidacionMensual();
    }


    private List<ListadoSegSocialCotizaMes> gestionarBloqueos(List<ListadoSegSocialCotizaMes> listadoParsed, int nuMes) {
        int mesActual = LocalDate.now().getMonthValue();
        int mesAnterior = (mesActual == 1) ? 12 : mesActual - 1; //En el caso de enero, ponemos que el anterior sea diciembre

        // Si el mes seleccionado es el mes anterior al actual y la fecha actual es del 1 al día límite de liquidaciones
        if ((nuMes == mesAnterior && comprobarDiasMesCorrecto(nuMes))) {
            listadoParsed = desbloquearAlumnos(listadoParsed, 1);
        }

        int trimestreActual = calcularTrimestreAcademico(mesActual);
        int trimestreMesSeleccionado = calcularTrimestreAcademico(nuMes);

        // Si el mes seleccionado está en el mismo trimestre que el actual
        if (trimestreMesSeleccionado == trimestreActual) {
            listadoParsed = desbloquearAlumnos(listadoParsed, 0);
        } // Si el mes seleccionado pertenece al trimestre anterior y estamos al inicio del trimestre actual (del 1 al día límite)
        else if (trimestreMesSeleccionado == (trimestreActual - 1) && comprobarEsInicioTrimestreActual(mesActual)) {
            listadoParsed = desbloquearAlumnos(listadoParsed, 0);
        }

        return listadoParsed;
    }

    /*
       Desbloquea a todos los alumnos independientemente de si tiene beca o no
     */
    private List<ListadoSegSocialCotizaMes> desbloquearTodosLosAlumnos(List<ListadoSegSocialCotizaMes> listadoParsed){
        for (ListadoSegSocialCotizaMes alumno : listadoParsed) {
            alumno.setBloqueoMes(0);
        }

        return listadoParsed;
    }

    /*
       Desbloquea SOLO a los alumnos según si son con beca erasmus o no
     */
    private List<ListadoSegSocialCotizaMes> desbloquearAlumnos(List<ListadoSegSocialCotizaMes> listadoParsed, int esErasmusConBeca){
            for (ListadoSegSocialCotizaMes alumno : listadoParsed) {
                if (alumno.getLgErasBec() != null && alumno.getLgErasBec() == esErasmusConBeca) {
                    alumno.setBloqueoMes(0);
                }
            }
            return listadoParsed;
        }

    private int calcularTrimestreAcademico(int mes) {
        if (mes >= 10 && mes <= 12) {  // De octubre a diciembre
            return 1;
        } else if (mes >= 1 && mes <= 3) {  // De enero a marzo
            return 2;
        } else if (mes >= 4 && mes <= 6) {  // De abril a junio
            return 3;
        } else {  // De julio a septiembre
            return 4;
        }
    }

    /*
       Comprobar si es el primer mes del trimestre académico
     */
    private boolean comprobarEsInicioTrimestreActual(int mesActual) {
        // Array con el mes de inicio de cada trimestre (1. Octubre - 2. Enero - 3. Abril - 4. Julio)
        List<Integer> mesesInicioTrimestre = Arrays.asList(10, 1, 4, 7);

        int hoy = LocalDate.now().getDayOfMonth();

     // Comprobar si es el primer mes de un trimestre y si estamos entre el día 1 y el día límite
        //return mesesInicioTrimestre.contains(mesActual) && hoy <= MAX_DIA_LIQUIDACIONES;
        return mesesInicioTrimestre.contains(mesActual) && hoy <= segSocialCotizaMesProyRepository.getMaxDiaLiquidacionMensual();
    }

    /*
      Método que devuelve si el mes es anterior al actual (en base al año académico)
     */
    private boolean esMesComprobable(int nuMes) {
        int mesActual = LocalDate.now().getMonthValue();
        if(nuMes == mesActual){
          return false;
        } 
        if (nuMes >= 10) {
            return mesActual > nuMes || mesActual <= 9;
        } else {
            return mesActual > nuMes && mesActual <= 9;
        }
    }

}