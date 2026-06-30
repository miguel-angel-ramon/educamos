package es.jccm.edu.alumnos.application.domain.historicoDomiciliosAlumno;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoricoDomicilioAlumnoDTO implements Serializable {
	private static final long serialVersionUID=1L;
	
	private Long id;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date fechaDomicilio;
	private String tutores;
	private String domicilio;
	
	public HistoricoDomicilioAlumnoDTO (Long id,Date fechaDomicilio, String nombreT1,String apellido1T1,String apellido2T1,
			String nombreT2,String apellido1T2, String apellido2T2, String tipoVia,String calle,String numero, String escalera, String piso,String letra,
			String cPostal, String localidad, String municipio, String provincia) {
		
		this.id=id;
		this.fechaDomicilio=fechaDomicilio;
		this.tutores=apellido1T1+" "+apellido2T1+", "+nombreT1+"  y  "+apellido1T2+" "+apellido2T2	+", "+ nombreT2;
		
		String direccion=tipoVia+" "+ calle + " Num: "+numero;
		if(escalera !=null) direccion=direccion+" Esc: " + escalera;
		if(piso !=null)direccion=direccion+ " Piso: " + piso;
		if(letra !=null) direccion=direccion+ " Letra: " + letra;
		this.domicilio=direccion+" C.P.: "+cPostal + " "+localidad+ " , " + municipio + " , "+provincia;
		
	}


}
