package es.jccm.edu.alumnos.application.domain.conductasContrarias.dto;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class AlumnadoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nombreCompleto;
	private Long matricula;
	private Long conductasLeves;
	private Long conductasGraves;
	private Long conductasAutoridad;
	private Long conductasAutoridadGraves;
	
	public AlumnadoDTO(String nombre,String apellido1, String apellido2, Long matricula,Long conductasLeves,
			Long conductasGraves, Long conductasAutoridad,Long conductasAutoridadGraves) {
		this.nombreCompleto=nombre+" "+apellido1+" "+apellido2;
		this.matricula=matricula;
		this.conductasLeves=conductasLeves;
		this.conductasGraves=conductasGraves;
		this.conductasAutoridad=conductasAutoridad;
		this.conductasAutoridadGraves=conductasAutoridadGraves;
	}
	
	

}
