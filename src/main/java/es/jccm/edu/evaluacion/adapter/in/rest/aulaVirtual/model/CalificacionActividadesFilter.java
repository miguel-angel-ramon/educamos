package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnosPorMateriaDTO;
import lombok.Data;

import java.util.List;

@Data
public class CalificacionActividadesFilter {

	private List<AlumnosPorMateriaDTO> alumnos;

	private List<ActividadDTO> actividades;
}