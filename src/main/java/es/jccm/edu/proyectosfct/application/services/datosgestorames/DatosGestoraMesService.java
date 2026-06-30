package es.jccm.edu.proyectosfct.application.services.datosgestorames;

import com.fasterxml.jackson.databind.JsonNode;
import es.jccm.edu.proyectosfct.adapter.out.repositories.datosgestora.DatosGestoraRepository;
import es.jccm.edu.proyectosfct.adapter.out.repositories.datosgestorames.DatosGestoraMesRepository;
import es.jccm.edu.proyectosfct.application.domain.datosgestorames.entities.DatosGestoraMes;
import es.jccm.edu.proyectosfct.application.domain.datosgestorames.projection.ListadoDatosGestoraMesProjection;
import es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities.EnviosGestoraMes;
import es.jccm.edu.proyectosfct.application.ports.in.datosgestorames.IDatosGestoraMesService;
import es.jccm.edu.proyectosfct.application.ports.in.enviosgestorames.IEnvioGestoraMesService;
import es.jccm.edu.proyectosfct.application.ports.in.segsocicotizames.ISegSociCotizaMesService;
import es.jccm.edu.proyectosfct.application.services.datosgestora.DatosGestoraService;
import es.jccm.edu.shared.application.ports.out.resttemplate.segsocial.IClientSegSocial;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatosGestoraMesService implements IDatosGestoraMesService {

    private static final Logger LOG = LogManager.getLogger(DatosGestoraService.class);

    private static final String ABSENT_ERASMUS = "absent_erasmus";
    private static final String ABSENT_SON = "absent_by_son";
    private static final String ABSENT_IT = "absent_it";
    private static final String ERASMUS = "erasmus_scholarship";

    private static final Long ID_PERFIL_GESTOR = 11207L;
    private static final Long ID_PERFIL_CONSEJERIA_FCT = 13207L;
    private static final Long ID_PERFIL_CONSEJERIA_FCT_1 = 15207L;
    private static final Long ID_PERFIL_CENTRO = 161L;

    private static final String WORKER_NIF = "worker_nif";

    @Autowired
    private DatosGestoraMesRepository datosGestoraMesRepository;

    @Autowired
    private DatosGestoraRepository datosGestoraRepository;

    @Autowired
    private ISegSociCotizaMesService segSociCotizaMesService;

    @Autowired
    IClientSegSocial clientSegSocial;

    @Autowired
    IEnvioGestoraMesService envioGestoraMesService;


    @Override
    public List<ListadoDatosGestoraMesProjection> getListadoDatosGestoraMes(Long idTutorfctdual, 
    																		Long idCentro, 
    																		Integer cAnno, 
    																		Long idOfertamatrig, 
    																		Long idUnidad, 
    																		Long idPerfil, 
    																		Long idCentroCombo, 
    																		Long idProvincia, 
    																		Long xUsuarioDelphos, 
    																		String idEstado,
    																		Integer mes) {

        List<ListadoDatosGestoraMesProjection> listadoDatosGestoraMesProjections = null;


        if (ID_PERFIL_GESTOR.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT.equals(idPerfil) || ID_PERFIL_CONSEJERIA_FCT_1.equals(idPerfil)) {

            if (ID_PERFIL_GESTOR.equals(idPerfil)) {

                listadoDatosGestoraMesProjections = datosGestoraMesRepository.findListadoDatosGestoraDelegacion(idTutorfctdual,
                        idCentro,
                        cAnno,
                        idOfertamatrig,
                        idUnidad,
                        idPerfil,
                        idCentroCombo,
                        xUsuarioDelphos,
                        idEstado,
                        mes);

            } else {

                listadoDatosGestoraMesProjections = datosGestoraMesRepository.findListadoDatosGestoraDelegacionProvincias(idTutorfctdual,
                        cAnno,
                        idOfertamatrig,
                        idUnidad,
                        idCentroCombo,
                        idProvincia,
                        idEstado,
                        mes);
            }

        } else {

            if (ID_PERFIL_CENTRO.equals(idPerfil)) {

                listadoDatosGestoraMesProjections = datosGestoraMesRepository.findListadoDatosGestoraCentro(idTutorfctdual,
                        idCentro,
                        cAnno,
                        idOfertamatrig,
                        idUnidad,
                        idEstado,
                        mes);

            } else {

                listadoDatosGestoraMesProjections = datosGestoraMesRepository.findListadoDatosGestoraProf(idTutorfctdual,
                        idCentro,
                        cAnno,
                        idOfertamatrig,
                        idUnidad,
                        idEstado,
                        mes);

            }

        }

        return listadoDatosGestoraMesProjections;
    }

    @Override
    public void actualizarDatosGestoraMesDeAlumno(String nif, Long idCentro, Integer anno, Integer mes, Long xUsuarioDelphos) {
        JsonNode data = clientSegSocial.getDatosSegSocialByTipoAndMes("nif", "", mes, anno, nif);

        if (data == null || data.isEmpty()) return;

        Long idMatricula = datosGestoraRepository.findByXMatricula(nif, idCentro);
        if (idMatricula == null) {
            return;
        }

        List<DatosGestoraMes> registrosAnteriores = datosGestoraMesRepository.findAllByMatriculaAndNuAnnoAndNuMes(idMatricula, anno, mes);

        for (DatosGestoraMes registro : registrosAnteriores) {
            datosGestoraMesRepository.deleteById(registro.getIdDatosGestoraMes());
        }

        for (int i = 0; i < data.size(); i++) {
            // if (fila.hasNonNull("group_id")) continue;

            DatosGestoraMes datosGestoraMes = construirDatosGestoraMes(data, i, idCentro, anno, mes, xUsuarioDelphos);

            salvarGestoraMes(data, idCentro, i, datosGestoraMes, idMatricula);
        }
    }

    private DatosGestoraMes construirDatosGestoraMes(JsonNode nodo, int index, Long idCentro, Integer anno, Integer mes, Long xUsuarioDelphos) {
        DatosGestoraMes datosGestoraMes = new DatosGestoraMes();

        datosGestoraMes.setDsEstado(nodo.get(index).get("status").asText());
        datosGestoraMes.setCdError("null".equals(nodo.get(index).get("error").asText()) ? null : nodo.get(index).get("error").asText());
        datosGestoraMes.setCdCnumide(nodo.get(index).get(WORKER_NIF).asText());
        datosGestoraMes.setIdGestora(convertStringToInteger(nodo.get(index).get("id").asText()));
        datosGestoraMes.setNuDiasReal(convertStringToInteger(nodo.get(index).get("days").asText()));
        datosGestoraMes.setNuDiasNacu(convertStringToInteger(nodo.get(index).get(ABSENT_SON).asText()));
        datosGestoraMes.setNuDiasInte(convertStringToInteger(nodo.get(index).get(ABSENT_IT).asText()));

        if ("0".equals(nodo.get(index).get(ABSENT_ERASMUS).asText()) || "0".equals(nodo.get(index).get(ERASMUS).asText())) {
            datosGestoraMes.setNuDiasInteEra(null);
        } else {
            datosGestoraMes.setNuDiasInteEra(convertStringToInteger(nodo.get(index).get(ABSENT_ERASMUS).asText()));
        }

        datosGestoraMes.setIdUsuarioUlt(xUsuarioDelphos);
        datosGestoraMes.setIdCentro(idCentro);
        datosGestoraMes.setNuAnno(anno);
        datosGestoraMes.setNuMes(mes);

        return datosGestoraMes;
    }

    @Override
    public List<DatosGestoraMes> saveDatosMesSegSocial(Long idCentro, Integer anno, Integer nuMes, Long xUsuarioDelphos) {
        List<DatosGestoraMes> listadoDatosGestora = new ArrayList<>();

        //Obtenemos el cif a partir del idCentro que nos viene por parámetro
        String cif = datosGestoraRepository.getCif(idCentro);
        String resultado = "";

        //Creamos un registro en la tabla FCT_ENVIOS_GESTORA_MES para controlar el nuevo acceso a este servicio
        EnviosGestoraMes envio = envioGestoraMesService.saveEnviosGestoraMesEntity(idCentro, anno, xUsuarioDelphos, resultado, true, null, nuMes);

        //Sacamos la lista de alumnos de cada centro pasandole el cif del centro
        //List<VWorkerIntegraLoopAction> listaWorkerIntegraLoopAction = clientSegSocial.getDatosSegSocial(cif, xUsuarioDelphos);

        JsonNode data = clientSegSocial.getDatosSegSocialByTipoAndMes("cif", cif, nuMes, anno, "");

        if (data != null){

            // Realizamos el chequeo siempre para eliminar datos antiguos si existen. Se eliminan incluso si la consulta es correcta y vacía
            checkIfDatosGestoraMesExists(idCentro, anno, nuMes);

            if (!data.isEmpty()) {
                // Recorremos la lista recibida y creamos entidades para cada item
                createAndSaveDatosGestoraMesEntity(data, anno, idCentro, xUsuarioDelphos, nuMes);
            }
            resultado = "OK";

        }else{
            resultado = "Se ha producido un error";
        }

        envioGestoraMesService.saveEnviosGestoraMesEntity(idCentro, anno, xUsuarioDelphos, resultado, false, envio.getIdEnviosGestoraMes(), nuMes);

        return listadoDatosGestora;
    }

    private void checkIfDatosGestoraMesExists(Long idCentro, Integer anno, Integer nuMes){

        List<DatosGestoraMes> listadoDatosGestoraMes = datosGestoraMesRepository.findAllByIdCentroAndNuAnnoAndNuMes(idCentro, anno, nuMes);

        if(!listadoDatosGestoraMes.isEmpty()){
            listadoDatosGestoraMes.forEach(datosGestoraMes -> datosGestoraMesRepository.deleteById(datosGestoraMes.getIdDatosGestoraMes()));
        }
    }

    private void createAndSaveDatosGestoraMesEntity(JsonNode data, Integer anno, Long idCentro, Long xUsuarioDelphos, Integer nuMes){

        for (int i = 0; i < data.size(); i++) {

            DatosGestoraMes datosGestoraMes = construirDatosGestoraMes(data, i, idCentro, anno, nuMes, xUsuarioDelphos);
            
            Long idMatricula = null;

            if (data.get(i).has("worker_id_ext")) {
            	idMatricula = datosGestoraRepository.findByXMatriculaAndIdInterno(data.get(i).get("worker_id_ext").asLong(), idCentro);	
            }
            

            if(idMatricula == null && data.get(i).has(WORKER_NIF)){
                idMatricula = datosGestoraRepository.findByXMatricula(data.get(i).get(WORKER_NIF).asText(), idCentro);
            }
            
            salvarGestoraMes(data, idCentro, i, datosGestoraMes, idMatricula);

        }
    }

	private void salvarGestoraMes(JsonNode data, Long idCentro, int i, DatosGestoraMes datosGestoraMes,
			Long idMatricula) {
		Integer exists = 0;
		
		if(idMatricula != null){
		        exists = datosGestoraRepository.findByExistsDatosGestora(idMatricula,
																		 datosGestoraMes.getNuAnno(),
																		 datosGestoraMes.getNuMes(),            		
				                                                         datosGestoraMes.getDsEstado());
		}

		if(idMatricula != null && exists == 0){
		    datosGestoraMes.setXMatricula(idMatricula);
		    datosGestoraMesRepository.save(datosGestoraMes);
		} else {

		    LOG.info("Matrícula no encontrada CNUMIDE, CENTRO:  ", data.get(i).get(WORKER_NIF).asText() + " " + idCentro);
		}
	}

    private static Integer convertStringToInteger(String valor){
        try{
            return Integer.parseInt(valor);
        }catch(NumberFormatException e){
            return 0;
        }
    }


    @Override
    public List<DatosGestoraMes> getDatosGestoraMesByAlumno(String nif, Integer nuMes, Integer anno, Long id, String tipo) {

        List<DatosGestoraMes> listadoDatosGestoraMes = new ArrayList<>();

        JsonNode gestoraData = clientSegSocial.getDatosSegSocialByTipoAndMes("nif", "", nuMes, anno, nif);

        for (int i = 0; i < gestoraData.size(); i++) {
            DatosGestoraMes datosGestoraMes = new DatosGestoraMes();
            updateDatosGestoraMesValues(id, datosGestoraMes, gestoraData, i, tipo);
            listadoDatosGestoraMes.add(datosGestoraMes);
        }

        return listadoDatosGestoraMes;
    }

    private void updateDatosGestoraMesValues(Long id, DatosGestoraMes datosGestoraMes, JsonNode gestoraData, int i, String tipo){

        switch(gestoraData.get(i).get("status").asText()){
            case "P":
                datosGestoraMes.setDsEstado("Pendiente");
                break;
            case "F":
                datosGestoraMes.setDsEstado("Finalizado");
                break;
            case "E":
                datosGestoraMes.setDsEstado("Error");
                break;
            default:
                datosGestoraMes.setDsEstado(null);
                break;
        }

        datosGestoraMes.setLgErasmus(convertStringToInteger(gestoraData.get(i).get(ERASMUS).asText()));
        datosGestoraMes.setNuMes(convertStringToInteger(gestoraData.get(i).get("month").asText()));
        datosGestoraMes.setNuAnno(convertStringToInteger(gestoraData.get(i).get("year").asText()));
        datosGestoraMes.setNuDiasNacu(convertStringToInteger(gestoraData.get(i).get(ABSENT_SON).asText()));
        datosGestoraMes.setNuDiasInte(convertStringToInteger(gestoraData.get(i).get(ABSENT_IT).asText()));
        datosGestoraMes.setNuDiasReal(convertStringToInteger(gestoraData.get(i).get("days").asText()));
        datosGestoraMes.setNuDiasInteEra(convertStringToInteger(gestoraData.get(i).get(ABSENT_ERASMUS).asText()));
        datosGestoraMes.setCdCnumide(gestoraData.get(i).get(WORKER_NIF).asText());
        datosGestoraMes.setCdNuss(gestoraData.get(i).get("afiliation_number").asText());
        datosGestoraMes.setIdGestora(convertStringToInteger(gestoraData.get(i).get("id").asText()));
        datosGestoraMes.setNombreCompleto(datosGestoraMesRepository.getNombreCompleto(datosGestoraMes.getCdCnumide()));

        datosGestoraMes.setConsistencia(setRestrictionsConsistenciaMes(id, datosGestoraMes, tipo));
    }



    private boolean setRestrictionsConsistenciaMes(Long id, DatosGestoraMes datosGestoraMes, String tipo){
        int consist = segSociCotizaMesService.getConsistenciaMesGestora(id,
                                                                        tipo,
                                                                        datosGestoraMes.getNuMes(),
                                                                        datosGestoraMes.getNuDiasReal(),
                                                                        datosGestoraMes.getNuDiasNacu(),
                                                                        datosGestoraMes.getNuDiasInte(),
                                                                        datosGestoraMes.getNuDiasInteEra());
        return consist != 0;
    }
}
