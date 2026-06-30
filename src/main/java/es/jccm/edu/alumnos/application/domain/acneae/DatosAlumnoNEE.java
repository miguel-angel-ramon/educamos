package es.jccm.edu.alumnos.application.domain.acneae;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;



@Data
@Entity
public class DatosAlumnoNEE implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	private Long idFamilia;
	
	private String numeroEscolar;
	
		
	private String apellido1;
	
	private String apellido2;
	
	private String nombre;
	
	private String nIdentificacion;
	
	private String tipoIdentificacion;
	
	@JsonFormat(pattern="dd-MM-yyyy" ,locale="es-ES" , timezone="CET")
	private Date  fechaNacimiento;
	
	private String emancipado;
	
	private String espanol;
	
	private  int codProvinciaResidencia;
	
	private int codMunicipioResidencia;
	
	private Long codLocalidadResidencia;
	
	private String c_postal;
	
	private String t_telefono;
	
	private String t_correo_e;
	
	private String descMunicipio;
	
	private String l_sexo;
	
	private String d_provincia;
	
	private String d_localidad;
	
	private String tipoVia;
	
	private String nombreVia;
	
	private String numeroCalle;
	
	private String escalera;
	
	private String piso;
	
	private String letra;
	
	
	

	
	
	
	
	

}
