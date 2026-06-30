package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ActividadDTO;
import lombok.Data;

@Data
public class CriteriosActividadesFilter{

	private AulaVirtualListDTO aulaVirtual;
	
	private List<ActividadDTO> actividades;
	
	private Long idUnidadProgramacion;
}