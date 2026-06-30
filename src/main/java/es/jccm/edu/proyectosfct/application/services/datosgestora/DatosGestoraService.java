package es.jccm.edu.proyectosfct.application.services.datosgestora;
import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.ListadoHistoricoAltasDto;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.AlumnadoRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado.CorreccionesNUSSRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.datosgestora.ControlTareasSincGestoraRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.datosgestora.DatosGestoraRepository;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection.ListadoHistoricoAltasProjection;
import es.jccm.edu.proyectosfct.application.domain.altassegsociprogramas.entities.AltasSegSociProg;
import es.jccm.edu.proyectosfct.application.domain.altassegsociproyectos.entities.AltasSegSociProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.Alumnado;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.CorreccionesAlumnado;
import es.jccm.edu.proyectosfct.application.domain.datosgestora.entities.DatosGestora;
import es.jccm.edu.proyectosfct.application.domain.datosgestora.projection.ListadoDatosGestoraProjection;
import es.jccm.edu.proyectosfct.application.domain.datosgestorames.entities.DatosGestoraMes;
import es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities.EnviosGestora;
import es.jccm.edu.proyectosfct.application.ports.in.altassegsocial.IAltasSegSocialService;
import es.jccm.edu.proyectosfct.application.ports.in.datosgestora.IDatosGestoraService;
import es.jccm.edu.proyectosfct.application.ports.in.datosgestorames.IDatosGestoraMesService;
import es.jccm.edu.proyectosfct.application.ports.in.enviosgestora.IEnviosGestoraService;
import es.jccm.edu.shared.application.ports.out.resttemplate.segsocial.IClientSegSocial;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatosGestoraService implements IDatosGestoraService {

	private static final Logger LOG = LogManager.getLogger(DatosGestoraService.class);
	
	private static final String WORKER_NIF = "worker_nif";
	private static final String PROCEDURE_FILE = "procedure_file";
	private static final String DATA_FILE = "data_file";
	private static final String SIGN_STATUS = "sign_status";	
	private static final String SIN_ESTADO = null;	
	private static final String ERROR = "error";
	private static final String WORKER_ID = "worker_id_ext";
	private static final String REASON = "reason_archived_error";	
	private static final String NUSS = "affiliation_number";
	private static final String TIPO = "type";
	private static final String ERROR_REGISTER = "registering_error";
	private static final String GROUP_ID = "group_id";
	
	
    private static final Long ID_PERFIL_GESTOR = 11207L;
    private static final Long ID_PERFIL_CONSEJERIA_FCT = 13207L;
    private static final Long ID_PERFIL_CONSEJERIA_FCT_1 = 15207L;
    private static final Long ID_PERFIL_CENTRO = 161L;

    @Autowired
    private DatosGestoraRepository datosGestoraRepository;

    @Autowired
    private IDatosGestoraMesService datosGestoraMesService;

//    @Autowired
//    private EnviosGestoraRepository enviosGestoraRepository;

    @Autowired
    private IEnviosGestoraService enviosGestoraService;


    @Autowired
    private IAltasSegSocialService altasSegSocialService;
    
    @Autowired
    private AlumnadoRepository alumnadoRepository;

    @Autowired
    private CorreccionesNUSSRepository correcionesNUSSRepository;
    
    @Autowired
    IClientSegSocial clientSegSocial;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ControlTareasSincGestoraRepository controlTareasSincGestoraRepository;


    @Override
    public List<DatosGestora> saveDatosSegSocial(Long idCentro, Integer anno, Long xUsuarioDelphos) {

        List<DatosGestora> listadoDatosGestora = new ArrayList<>();

        //Obtenemos el cif a partir del idCentro que nos viene por parámetro
        String cif = datosGestoraRepository.getCif(idCentro);
        String resultado = "";

        //Creamos un registro en la tabla FCT_ENVIOSS_GESTORA para controlar el nuevo acceso a este servicio
        EnviosGestora envio = enviosGestoraService.saveEnviosGestoraEntity(idCentro, anno, xUsuarioDelphos, resultado, true, null);

        //Sacamos la lista de alumnos de cada centro pasandole el cif del centro
        //List<VWorkerIntegraLoopAction> listaWorkerIntegraLoopAction = clientSegSocial.getDatosSegSocial(cif, xUsuarioDelphos);

        JsonNode data = clientSegSocial.getDatosSegSocialByTipo(cif, "cif", "", "", "", "");
//        JsonNode data = clientSegSocial.getDatosSegSocialByTipo(String.valueOf(idInterno), "worker_id_ext", "", "", "", "");
        
        if (data != null && !data.isEmpty()){
        //if(data != null){

            //Comprobamos si ya hay datos para ese centor y ese año
            checkIfDatosGestoraExists(idCentro, anno);

            //Recorremos la lista que recibimos y creamos por cada item una entidad nuestra
            //Recorremos la lista que recibimos y creamos por cada item una entidad nuestra
            createAndSaveDatosGestoraEntity(data, anno, idCentro, xUsuarioDelphos);
            resultado = "OK";

        }else{
            resultado = "OK";
        }

        enviosGestoraService.saveEnviosGestoraEntity(idCentro, anno, xUsuarioDelphos, resultado, false, envio.getIdEnviosGestora());

        return listadoDatosGestora;
    }

    private void checkIfDatosGestoraExists(Long idCentro, Integer anno) {
        List<DatosGestora> listadoDatosGestoraCheck = datosGestoraRepository.findAllByXCentroAndNuAnno(idCentro, anno);

        if(!listadoDatosGestoraCheck.isEmpty()){
            for (DatosGestora datosGestora: listadoDatosGestoraCheck){
                datosGestoraRepository.deleteById(datosGestora.getIdDatosGestora());
            }
        }
    }

    private void createAndSaveDatosGestoraEntity(JsonNode data, Integer anno, Long idCentro, Long xUsuarioDelphos) {

    	for(int i = 0; i < data.size(); i++){

            // Saltar registros de otros años (si group_id no es null)
            if (data.get(i).get(GROUP_ID) != null && !data.get(i).get(GROUP_ID).isNull()) {
                continue;
            }

            DatosGestora datosGestora = new DatosGestora();

            getDatosGestora(data, anno, idCentro, xUsuarioDelphos, i, datosGestora);

            Long idMatricula = datosGestoraRepository.findByXMatriculaAndIdInterno(data.get(i).get(WORKER_ID).asLong(), idCentro);

            if(idMatricula == null){
               idMatricula = datosGestoraRepository.findByXMatricula(data.get(i).get(WORKER_NIF).asText(), idCentro);
            }
            
            if (idMatricula != null) {     
            	datosGestora.setXMatricula(idMatricula);

                // ELIMINAR CUANDO PRUEBAS OK: Simulación para la matrícula 9281608
                //if (idMatricula == 9281608L && "Alta".equals(datosGestora.getDsTipo())) {
                //    datosGestora.setDsEstado("E");
                //    datosGestora.setCdError("Error simulado con 3688 ó 7934");
                //}
                correccionNUSS(idMatricula, 
                		       data.get(i).get(NUSS).asText(),
                		       data.get(i).get(TIPO).asText(),
                		       data.get(i).get(SIGN_STATUS).asText());
                
            	datosGestoraRepository.save(datosGestora);            	
            	refreshEnvios(data, i, idMatricula);            	
            } else {
            	
            	LOG.info("Matrícula no encontrada CNUMIDE, CENTRO:  ", data.get(i).get(WORKER_NIF).asText() + " " + idCentro);
            }
        }
    }
    
    
    

	private void correccionNUSS(Long idMatricula, String nussGestora, String tipo, String estado) 
	{
		if ("Alta".equals(tipo) && "S".equals(estado)) 
		{
		
		List<Alumnado> alumno = alumnadoRepository.getNUSSTLalumnos(idMatricula);		
	
			if (alumno != null && alumno.size()>0 && !alumno.get(0).getTnuss().equals(nussGestora) ) 
			{
				
				Optional<Alumnado> aluModified = alumnadoRepository.findById(alumno.get(0).getId());
				
				if (aluModified.isPresent()) {		
					
					CorreccionesAlumnado correccion = new CorreccionesAlumnado(); 
					correccion.setXMatricula(idMatricula);
					correccion.setNussOld(alumno.get(0).getTnuss());
					correccion.setNussNew(nussGestora);
					correccion.setFregistro(new Date());
					correcionesNUSSRepository.save(correccion);
					
					aluModified.get().setTnuss(nussGestora);
					alumnadoRepository.save(aluModified.get());
				}		
			}		
		}
	}

	private void getDatosGestora(JsonNode data, Integer anno, Long idCentro, Long xUsuarioDelphos, int i,
			DatosGestora datosGestora) {
		datosGestora.setDsTipo(data.get(i).get("type").asText());
		datosGestora.setDsEstado(data.get(i).get(SIGN_STATUS).asText());
		datosGestora.setCdError(data.get(i).get(ERROR).asText().equals("null")?null:data.get(i).get(ERROR).asText());
		datosGestora.setCdEmpresa(data.get(i).get("company_id").asText());

		datosGestora.setFechaAlta(convertStringToDate(data.get(i).get("register_date").asText()));
		datosGestora.setFechaBaja(convertStringToDate(data.get(i).get("discharge_date").asText()));
		datosGestora.setFechaRecepcion(convertStringToDate(data.get(i).get("date_receipt_data").asText()));
//            dateConverter(data.get(i).get("register_date").asText(),
//            		      data.get(i).get("discharge_date").asText(),
//            		      data.get(i).get("date_receipt_data").asText(),
//            		      datosGestora);

		datosGestora.setLgErasmusSb(convertStringToInteger(data.get(i).get("fp_dual_scholarship").asText()));
		datosGestora.setLgErasmusCb(convertStringToInteger(data.get(i).get("erasmus_fp_dual_scholarship").asText()));
		datosGestora.setNuDiasPracticas(convertStringToInteger(data.get(i).get("days_practicals").asText()));
		datosGestora.setLgErasmus(convertStringToInteger(data.get(i).get("erasmus").asText()));
		datosGestora.setIdGestora(convertStringToInteger(data.get(i).get("id").asText()));
		datosGestora.setLgFile(convertStringToInteger(data.get(i).get(PROCEDURE_FILE).asText()));
		datosGestora.setLgData(convertStringToInteger(data.get(i).get(DATA_FILE).asText()));
//            tryNumberFormat(data.get(i).get("fp_dual_scholarship").asText(),
//            		        data.get(i).get("erasmus_fp_dual_scholarship").asText(),
//            		        data.get(i).get("days_practicals").asText(),
//            		        data.get(i).get("erasmus").asText(),
//            		        data.get(i).get("id").asText(),
//            		        data.get(i).get(PROCEDURE_FILE).asText(),
//            		        data.get(i).get(DATA_FILE).asText(),
//            		        datosGestora);
		
		datosGestora.setDsErrorAlta(data.get(i).get(ERROR_REGISTER).asText().equals("null")?null:data.get(i).get(ERROR_REGISTER).asText());
		datosGestora.setDsTipoContrato(data.get(i).get("contract_type").asText().equals("null")?null:data.get(i).get("contract_type").asText());
		datosGestora.setCdCnumide(data.get(i).get(WORKER_NIF).asText());
		datosGestora.setCdNuss(data.get(i).get(NUSS).asText());
		datosGestora.setCdCuentaNuss(data.get(i).get("contribution_account").asText());
		datosGestora.setCdGrupoNuss(data.get(i).get("contribution_group").asText());
		datosGestora.setDsOcupacion(data.get(i).get("occupation").asText().equals("null")?null:data.get(i).get("occupation").asText());
		datosGestora.setDsSituacionBaja(data.get(i).get("status").asText().equals("null")?null:data.get(i).get("status").asText());
		datosGestora.setCdCifEmpresa(data.get(i).get("company_cif").asText());
		datosGestora.setNuAnno(anno);
		datosGestora.setXCentro(idCentro);
		datosGestora.setIdUsuarioUlt(xUsuarioDelphos);
		datosGestora.setIdInternoAlta(data.get(i).get(WORKER_ID).asLong());
        datosGestora.setDsMotivoErrorArchivado(data.get(i).get(REASON).asText().equals("null") ? null : data.get(i).get(REASON).asText());
	}


    private void refreshEnvios(JsonNode data, int i, Long idMatricula) {
        String tipo = data.get(i).get("type").asText();
        String estado = data.get(i).get(SIGN_STATUS).asText();
        String error = data.get(i).get(ERROR).asText().equals("null") ? null : data.get(i).get(ERROR).asText();

        if (tipo.equals("Alta") && estado.equals("S")) {

            // ELIMINAR CUANDO PRUEBAS OK: Simulación para la matrícula 9281608
            //refreshEnviosProg(idMatricula, idMatricula == 9281608L ? "Error simulado con 3688 ó 7934" : null);
            //refreshEnviosProy(idMatricula, idMatricula == 9281608L ? "Error simulado con 3688 ó 7934" : null);

            //VERSION CON PRUEBAS OK
            refreshEnviosProg(idMatricula, null);
            refreshEnviosProy(idMatricula, null);

        } else if (tipo.equals("Error Archivado") && estado.equals("EA")) {
            String  errorArchivado= data.get(i).get(REASON).asText();
            refreshEnviosProg(idMatricula,errorArchivado );
            refreshEnviosProy(idMatricula, errorArchivado);
        }
    }

    private void refreshEnviosProg(Long idMatricula, String error) {
        List<AltasSegSociProg> listAltasProg = altasSegSocialService.getAltaSegSociProgByMatricula(idMatricula);

        if (listAltasProg != null && !listAltasProg.isEmpty()) {
            for (AltasSegSociProg alta : listAltasProg) {
                alta.setWarnings(error);
                alta.setErrors(error);
                altasSegSocialService.saveProg(alta);
            }
        }
    }

    private void refreshEnviosProy(Long idMatricula, String error) {
        List<AltasSegSociProy> listAltasProy = altasSegSocialService.getAltaSegSociProyByMatricula(idMatricula);

        if (listAltasProy != null && !listAltasProy.isEmpty()) {
            for (AltasSegSociProy alta : listAltasProy) {
                alta.setWarnings(error);
                alta.setErrors(error);
                altasSegSocialService.saveProy(alta);
            }
        }
    }

//    private void dateConverter(String fInicio, String fFin, String fRecepcion,DatosGestora datosGestora) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date fechaAlta = null;
//        Date fechaBaja = null;
//        Date fechaRecepcion = null;
//        try{
//            fechaAlta = formatter.parse(fInicio);
//        }catch(ParseException e){
//        }
//
//        try{
//            fechaBaja = formatter.parse(fFin);
//        }catch(ParseException e){
//        }
//
//        try{
//            fechaRecepcion = formatter.parse(fRecepcion);
//        }catch(ParseException e){
//        }
//        datosGestora.setFechaAlta(fechaAlta);
//        datosGestora.setFechaBaja(fechaBaja);
//        datosGestora.setFechaRecepcion(fechaRecepcion);
//    }

    private static Date convertStringToDate(String value){

        SimpleDateFormat mask = new SimpleDateFormat("yyyy-MM-dd");

        try{
            return mask.parse(value);
        }catch(ParseException ignored){
            return null;
        }
    }

    private static Integer convertStringToInteger(String value){

        try {
            return Integer.parseInt(value);
        }catch(NumberFormatException e){
            return 0;
        }

    }

//    private static void tryNumberFormat(String lgErasmusSinBec,
//			String lgErasmusConBec,
//			String nuDiasPracticas,
//			String lgErasmus,
//			String idGestora,
//			String lgFile,
//			String lgData,
//            DatosGestora datosGestora) {
//    	try{
//    		String eraSinbec = lgErasmusSinBec;
//    		datosGestora.setLgErasmusSb(Integer.parseInt(eraSinbec));
//    	}catch(NumberFormatException e){
//    		datosGestora.setLgErasmusSb(0);
//    	}
//
//    	try{
//    		String eraConbec = lgErasmusConBec;
//    		datosGestora.setLgErasmusCb(Integer.parseInt(eraConbec));
//		}catch(NumberFormatException e){
//			datosGestora.setLgErasmusCb(0);
//		}
//
//		try{
//			datosGestora.setNuDiasPracticas(Integer.parseInt(nuDiasPracticas));
//		}catch(NumberFormatException e){
//			datosGestora.setNuDiasPracticas(null);
//		}
//
//		try{
//			String erasmus = lgErasmus;
//			datosGestora.setLgErasmus(Integer.parseInt(erasmus));
//		}catch(NumberFormatException e){
//			datosGestora.setLgErasmus(0);
//		}
//
//		try{
//			String file = lgFile;
//			datosGestora.setLgFile(Integer.parseInt(file));
//		}catch(NumberFormatException e){
//			datosGestora.setLgFile(0);
//		}
//
//		try{
//			String data = lgData;
//			datosGestora.setLgData(Integer.parseInt(data));
//		}catch(NumberFormatException e){
//			datosGestora.setLgData(0);
//		}
//
//
//		try{
//			datosGestora.setIdGestora(Integer.parseInt(idGestora));
//		}catch(NumberFormatException e){
//			datosGestora.setIdGestora(0);
//		}
//}


    @Override
    public List<ListadoDatosGestoraProjection> getListadoDatosGestora(Long idTutorfctdual, 
    																  Long idCentro, 
    																  Integer cAnno, 
    																  Long idOfertamatrig, 
    																  Long idUnidad, 
    																  Long idPerfil, 
    																  Long idCentroCombo,
    																  Long idProvincia, 
    																  Long xUsuarioDelphos, 
    																  String idEstado,
    																  Long idTipo) {
    	
        List<ListadoDatosGestoraProjection> listadoDatosGestoraProjections = null;


        if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {

            if (ID_PERFIL_GESTOR.equals(idPerfil)) {

                listadoDatosGestoraProjections = datosGestoraRepository.findListadoDatosGestoraDelegacion(idTutorfctdual,
                        idCentro,
                        cAnno,
                        idOfertamatrig,
                        idUnidad,
                        idPerfil,
                        idCentroCombo,
                        xUsuarioDelphos,
                        idEstado,
                        idTipo);

            } else {

                listadoDatosGestoraProjections = datosGestoraRepository.findListadoDatosGestoraDelegacionProvincias(idTutorfctdual,
                        cAnno,
                        idOfertamatrig,
                        idUnidad,
                        idCentroCombo,
                        idProvincia,
                        idEstado,
                        idTipo);
            }

        } else {

            if (ID_PERFIL_CENTRO.equals(idPerfil)) {

                listadoDatosGestoraProjections = datosGestoraRepository.findListadoDatosGestoraCentro(idTutorfctdual,
                        idCentro,
                        cAnno,
                        idOfertamatrig,
                        idUnidad,
                        idEstado,
                        idTipo);

            } else {

                listadoDatosGestoraProjections = datosGestoraRepository.findListadoDatosGestoraProf(idTutorfctdual,
                        idCentro,
                        cAnno,
                        idOfertamatrig,
                        idUnidad,
                        idEstado,
                        idTipo);

            }

        }

        return listadoDatosGestoraProjections;
    }

	@Override
	public Integer saveDatosSegSocialBack(Integer anno, Long xUsuarioDelphos) {
	
		List<Long> listCentros = datosGestoraRepository.getCentrosFCT(anno);
		
		for (Long centro : listCentros) {
			
			saveDatosSegSocial(centro,anno,xUsuarioDelphos);
		}

		return 0;		
		
	}

    @Override
    public void actualizarDatosGestoraDeAlumno(String nif, Long idCentro, Long xUsuarioDelphos) {

        JsonNode data = clientSegSocial.getDatosSegSocialByTipo(nif, "nif", "", "", "", "");

        if (data != null && !data.isEmpty()) {

            Long idMatricula = datosGestoraRepository.findByXMatricula(nif, idCentro);

            if (idMatricula != null) {
                List<DatosGestora> registrosAntiguos = datosGestoraRepository.findAllByXMatricula(idMatricula);

                for (DatosGestora antiguo : registrosAntiguos) {
                    datosGestoraRepository.deleteById(antiguo.getIdDatosGestora());
                }

                for (int i = 0; i < data.size(); i++) {


                    if (data.get(i).hasNonNull(GROUP_ID)) continue;

                    DatosGestora nuevo = new DatosGestora();
                    getDatosGestora(data, datosGestoraRepository.getAnnoActual(), idCentro, xUsuarioDelphos, i, nuevo);
                    nuevo.setXMatricula(idMatricula);

                    correccionNUSS(
                            idMatricula,
                            data.get(i).get(NUSS).asText(),
                            data.get(i).get(TIPO).asText(),
                            data.get(i).get(SIGN_STATUS).asText()
                    );

                    datosGestoraRepository.save(nuevo);

                    refreshEnvios(data, i, idMatricula);
                }
            }
        }
    }

    @Override
    public List<DatosGestora> getDatosGestoraByAlumno(String nif, Long id, String tipo, Long idInterno) {

        List<DatosGestora> listadoDatosGestoraByAlumno = new ArrayList<>();

        //Dos servicios, FCT o Dual
        //JsonNode data = clientSegSocial.getDatosSegSocialByTipo(nif, "nif", "", "", "", "");
        JsonNode data = clientSegSocial.getDatosSegSocialByTipo(String.valueOf(idInterno), WORKER_ID, "", "", "", "");

        if (data == null || data.isEmpty()){
            data = clientSegSocial.getDatosSegSocialByTipo(nif, "nif", "", "", "", "");
        }
        for (int i = 0; i < data.size(); i++){
            DatosGestora datosGestora = new DatosGestora();
            updateDatosGestoraValues(id, datosGestora, data, i , tipo);
            listadoDatosGestoraByAlumno.add(datosGestora);
        }

        return listadoDatosGestoraByAlumno;

    }


    @Override
    public byte[] getDatosGestoraById(Integer idGestora, String estado, String tipoDocumento, Long xUsuarioDelphos) {
    	
        JsonNode data = clientSegSocial.getDatosSegSocialByTipo(idGestora + "", "id", "", "", "", "");

        byte[] result = null;

        for (int i = 0; i < data.size(); i++){
            if(data.get(i).get("type").asText().equals(estado)){
                result = selectDocument(tipoDocumento, data, i);
            }
        }

        //byte[] pdfBytes = Base64.getDecoder().decode(result);
        return Base64.getDecoder().decode(result);
    }

    private static byte[] selectDocument(String tipoDocumento, JsonNode data, int i) {
        byte[] result;
        if("tramite".equals(tipoDocumento)){
            result = data.get(i).get(PROCEDURE_FILE).asText().getBytes();
        }else{
            result = data.get(i).get(DATA_FILE).asText().getBytes();
        }
        return result;
    }

    private void updateDatosGestoraValues(Long id, DatosGestora datosGestora, JsonNode data, int i, String tipo) {
        boolean consistencia = true;

        datosGestora.setDsTipo(data.get(i).get("type").asText());

        switch (data.get(i).get(SIGN_STATUS).asText()){
            case "S":
                datosGestora.setDsEstado("Correcto");
                break;
            case "E":
                datosGestora.setDsEstado("Error");
                if ("null".equalsIgnoreCase(datosGestora.getDsTipo())) {
                    datosGestora.setDsTipo("Entrada datos");
                }
			    consistencia = getConsistenciaError(datosGestora, data, i);
                break;
            case "PB":
                datosGestora.setDsEstado("Pendiente Baja");
                break;
            case "PA":
                datosGestora.setDsEstado("Pendiente Alta");
                break;
            case "EA":
                datosGestora.setDsEstado("Error Archivado");
			    consistencia = getConsistenciaErrorArchivado(datosGestora, data, i);
                break;
            default: 
            	datosGestora.setDsEstado(SIN_ESTADO);    
            	break;
        }
        datosGestora.setLgErasmus(convertStringToInteger(data.get(i).get("erasmus").asText()));
//        try{
//            datosGestora.setLgErasmus(Integer.parseInt(data.get(i).get("erasmus").asText()));
//        }catch(NumberFormatException e){
//            datosGestora.setLgErasmus(0);
//        }
        datosGestora.setCdCnumide(data.get(i).get(WORKER_NIF).asText());
        datosGestora.setNombreCompleto(datosGestoraRepository.getNombreCompletoAlu(datosGestora.getCdCnumide()));
        datosGestora.setCdNuss(data.get(i).get(NUSS).asText());
        datosGestora.setLgFile(convertStringToInteger(data.get(i).get(PROCEDURE_FILE).asText()));
        datosGestora.setLgData(convertStringToInteger(data.get(i).get(DATA_FILE).asText()));
//        try{
//            datosGestora.setLgFile(Integer.parseInt(data.get(i).get(PROCEDURE_FILE).asText()));
//        }catch(NumberFormatException e){
//            datosGestora.setLgFile(0);
//        }
//        try{
//            datosGestora.setLgData(Integer.parseInt(data.get(i).get(DATA_FILE).asText()));
//        }catch(NumberFormatException e){
//            datosGestora.setLgData(0);
//        }

        datosGestora.setFechaAlta(convertStringToDate(data.get(i).get("register_date").asText()));
        datosGestora.setFechaBaja(convertStringToDate(data.get(i).get("discharge_date").asText()));
        datosGestora.setFechaRecepcion(convertStringToDate(data.get(i).get("date_receipt_data").asText()));
//        dateConverter(data.get(i).get("register_date").asText(),
//                data.get(i).get("discharge_date").asText(),
//                data.get(i).get("date_receipt_data").asText(),
//                datosGestora);

        //consistencia = setRestrictionsConsistencia(id, datosGestora, consistencia, tipo, datosGestora.getDsEstado());)

        
        
        datosGestora.setConsistencia(consistencia);
        datosGestora.setIdGestora(convertStringToInteger(data.get(i).get("id").asText()));
        datosGestora.setIdInternoAlta(data.get(i).get(WORKER_ID).asLong());
//        try{
//            datosGestora.setIdGestora(Integer.parseInt(data.get(i).get("id").asText()));
//        }catch(NumberFormatException e){
//            datosGestora.setIdGestora(0);
//        }
    }

	private boolean getConsistenciaErrorArchivado(DatosGestora datosGestora, JsonNode data, int i) {
		boolean consistencia;
		String errorArchi = data.get(i).get(REASON).asText().equals("null")?null:data.get(i).get(REASON).asText();
		if (errorArchi == null) {
			errorArchi = data.get(i).get(ERROR).asText().equals("null")?null:data.get(i).get(ERROR).asText();
		}                
		datosGestora.setDsErrorAlta(errorArchi!=null?StringEscapeUtils.unescapeHtml4(errorArchi):null);
		consistencia = false;
		return consistencia;
	}

	private boolean getConsistenciaError(DatosGestora datosGestora, JsonNode data, int i) {
		boolean consistencia;
		String error = data.get(i).get(ERROR_REGISTER).asText().equals("null")?null:data.get(i).get(ERROR_REGISTER).asText();
		if (error == null) {
			error = data.get(i).get(ERROR).asText().equals("null")?null:data.get(i).get(ERROR).asText();
		} 
		datosGestora.setDsErrorAlta(error!=null?StringEscapeUtils.unescapeHtml4(error):null);
		datosGestora.setDsErrorAlta(error);
		consistencia = false;
		return consistencia;
	}

    private boolean setRestrictionsConsistencia(Long id, DatosGestora datosGestora, boolean consistencia, String tipo) {
    	
    	int consis = altasSegSocialService.getConsistenciaGestora(tipo, id, datosGestora.getFechaAlta(),datosGestora.getFechaBaja());  	
    	
    	if (consis == 0)  consistencia = false;
        /*AltasSegSociProg altasSegSociProg;
        AltasSegSociProy altasSegSociProy;
        altasSegSociProg = altasSegSocialService.getAltaSegSociProg(id);
        if(altasSegSociProg != null){
            if(altasSegSociProg.getFechaInicio().compareTo(datosGestora.getFechaAlta()) != 0 || altasSegSociProg.getFechaFin().compareTo(datosGestora.getFechaBaja()) != 0){
                consistencia = false;
            }else if(!(altasSegSociProg.getLgEramsusCb().equals(datosGestora.getLgErasmus())) || !(altasSegSociProg.getLgErasmusSb().equals(datosGestora.getLgErasmus()))){
                consistencia = false;
            }
        }else{
            altasSegSociProy = altasSegSocialService.getAltaSegSociProy(id);
            if(altasSegSociProy.getFechaInicio().compareTo(datosGestora.getFechaAlta()) != 0 || altasSegSociProy.getFechaFin().compareTo(datosGestora.getFechaBaja()) != 0){
                consistencia = false;
            }else if(!(altasSegSociProy.getLgEramsusCb().equals(datosGestora.getLgErasmus())) || !(altasSegSociProy.getLgErasmusSb().equals(datosGestora.getLgErasmus()))){
                consistencia = false;
            }
        } */
        return consistencia;
    }

	@Override
	public Integer programadaDatosGestora() {
		
		Integer cAnno = datosGestoraRepository.getAnnoActual();


        List<Long> lCentros = datosGestoraRepository.getListCentro(cAnno,0);

                Integer nSincronizados = 0;
		
		for (Long centro : lCentros) {
			
			try {
		    
				saveDatosSegSocial(centro,cAnno,1L);
				nSincronizados++;
			} catch (Exception e) {
				
				LOG.error("Error al procesar el centro con ID: " + centro, e);
			}
		}
		
	
		return nSincronizados;
	}

    @Override
    public Integer programadaDatosGestoraCotizacionesMes() {

        Integer cAnno = datosGestoraRepository.getAnnoActual();

        int mesAnterior = LocalDate.now().minusMonths(1).getMonthValue();

        int annoProcesado = (mesAnterior >= 9) ? cAnno : cAnno + 1;

        List<Long> lCentros = datosGestoraRepository.getListCentro(cAnno,0);

        Integer nSincronizados = 0;

        for (Long centro : lCentros) {
            try {
                //List<DatosGestoraMes> listadoDatosGestora = datosGestoraMesService.saveDatosMesSegSocial(centro, 2024, 11, 1L); //Datos de prueba
                 List<DatosGestoraMes> listadoDatosGestora = datosGestoraMesService.saveDatosMesSegSocial(centro, annoProcesado, mesAnterior, 1L);

                if (listadoDatosGestora != null && !listadoDatosGestora.isEmpty()) {
                    nSincronizados++;
                }
            } catch (Exception e) {

                LOG.error("Error al procesar el centro con ID: " + centro, e);
            }
        }

        return nSincronizados;
    }


    @Override
    public List<ListadoHistoricoAltasDto> getListadoHistoricoAltas(List<ListadoHistoricoAltasProjection> listadoHistoricoAltas, String nif, Long id, String tipo, Long idInterno){

        List<ListadoHistoricoAltasDto> listadoHistoricoAltasDto = listadoHistoricoAltas.stream().map(x -> modelMapper.map(x, ListadoHistoricoAltasDto.class)).collect(Collectors.toList());

//        JsonNode data = clientSegSocial.getDatosSegSocialByTipo(nif, "nif", "", "", "", "");
        JsonNode data = clientSegSocial.getDatosSegSocialByTipo(String.valueOf(idInterno), WORKER_ID, "", "", "", "");
        
        if (data == null || data.isEmpty()){
        	data = clientSegSocial.getDatosSegSocialByTipo(String.valueOf(nif), "nif", "", "", "", "");
        }

        for(ListadoHistoricoAltasDto elemento:listadoHistoricoAltasDto){

        	try {        		
                if(data == null){
                    elemento.setEstadoRegistro("-");
                    elemento.setIdGestora(null);
                    elemento.setLgData(0);
                    elemento.setLgFile(0);
                } else {
        		
        		if(elemento.getAccion().equals("Alta")) {

	                getEstados(data, elemento);
	                
                	} else {
                	elemento.setEstadoRegistro(SIN_ESTADO);
                    }
        		
                }
        	} catch (Exception e) {
                                
                elemento.setEstadoRegistro(null);
            }
        }

     return listadoHistoricoAltasDto;
    }

	private void getEstados(JsonNode data, ListadoHistoricoAltasDto elemento) {
		switch (data.get(0).get(SIGN_STATUS).asText()) {
		    case "S":
		        elemento.setEstadoRegistro("Registrado alta");
		        break;
		    case "E":
		        elemento.setEstadoRegistro("---");
		        break;
		    case "PB":
		        elemento.setEstadoRegistro("Pendiente Baja");
		        break;
		    case "PA":
		        elemento.setEstadoRegistro("Pendiente Alta");
		        break;
		    default:
		        elemento.setEstadoRegistro(SIN_ESTADO);
		        break;
		}

		elemento.setIdGestora(convertStringToInteger(data.get(0).get("id").asText()));
		elemento.setLgFile(convertStringToInteger(data.get(0).get(PROCEDURE_FILE).asText()));
		elemento.setLgData(convertStringToInteger(data.get(0).get(DATA_FILE).asText()));
		elemento.setIdInternoAlta(data.get(0).get(WORKER_ID).asLong());
	}


    @Transactional
    public void comenzarTrabajo(String nombreTarea) {
        controlTareasSincGestoraRepository.comenzarTrabajo(nombreTarea);
    }

    @Transactional
    public void finalizarTrabajo(String nombreTarea) {
        controlTareasSincGestoraRepository.finalizarTrabajo(nombreTarea);
    }

    public boolean trabajoEnProgreso(String nombreTarea) {
        Integer enEjecucion = controlTareasSincGestoraRepository.trabajoEnProgreso(nombreTarea);
        return enEjecucion != null && enEjecucion == 1;
    }

}
