package es.jccm.edu.shared.application.ports.out.resttemplate.segsocial;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterDaysContributed;
import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterTraineeStudent;

public interface IClientSegSocial {

	String getToken();
	
	void getCompanias();
	
	void envioAltasSSEmpresa(List<RegisterTraineeStudent> registerTraineeStudents, Long xUsuarioDelphos, String type);

	void envioAltasSSEmpresaCancelled(RegisterTraineeStudent registerTraineeStudent, Long xUsuarioDelphos);

	Integer envioCotizacionesMensuales(List<RegisterDaysContributed> registerDaysContributed, Long xUsuarioDelphos);

	JsonNode getDatosSegSocialByTipo(String doc, String filtro, String fechaInicio, String fechaFin, String tipo, String status);

	JsonNode getDatosSegSocialByTipoAndMes(String filtro, String cif, Integer nuMes, Integer anno, String nif);
}

