package es.jccm.edu.alumnos.application.domain.conductasContrarias.dto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CondContrariaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date fecha;
	private String nombreCompleto;
	private String incidente;
	private List <String> conductas;
	private List <String> correcciones;
	private Long idAlumnoCContraria;
	private Long idColectivoCContraria;
	private Long idMatricula;
	
	public CondContrariaDTO(Date fecha,String nombre,String apellido1, String apellido2,String incidente,Long id, 
			Long idColectivoCContraria,Long idMatricula) {
		this.fecha=fecha;
		this.nombreCompleto=nombre+" "+apellido1+" "+apellido2;
		this.incidente=incidente;
		this.idAlumnoCContraria=id;
		this.idColectivoCContraria=idColectivoCContraria;
		this.idMatricula=idMatricula;
				
		
	}
	
}
