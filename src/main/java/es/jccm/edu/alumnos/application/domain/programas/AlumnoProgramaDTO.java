package es.jccm.edu.alumnos.application.domain.programas;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class AlumnoProgramaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idMatricula;
	private String alumno;
	private String unidad;
	private String estado;
	private int resultado;
	private List<MateriaPrograma> materias;
	
	public AlumnoProgramaDTO (Long id, Long idMatricula,String nombre, String apellido1, String apellido2, String unidad, String estado, int resultado) {
		this.id=id;
		this.idMatricula=idMatricula;
		if (apellido2  != null) {
			this.alumno=apellido1 + " " +apellido2 + " " +nombre;
		}else {
			this.alumno=apellido1 + " " +nombre;
		}
		this.unidad=unidad;
		this.estado=estado;
		this.resultado=resultado;
	}

	public void setMaterias(List<MateriaPrograma> materias) {
		this.materias=materias;
		
	}
	
	
	

	
}
