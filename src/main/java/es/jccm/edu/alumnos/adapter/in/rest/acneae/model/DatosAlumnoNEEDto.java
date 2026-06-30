package es.jccm.edu.alumnos.adapter.in.rest.acneae.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="Alumnos",description="Entidad para rescatar los datos  de un alumno con NEE")
public class DatosAlumnoNEEDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idFamilia;
	
	private Long id;
	
	private String t_correo_e;
	
	private String l_sexo;
	
	private String d_localidad;
	
	private String nombreVia;
	
	private String escalera;

	private String descMunicipio;
	
	private String letra;
	
	private String t_telefono;
	
	private String c_postal;
	
	private Long codLocalidadResidencia;
	
	private int codMunicipioResidencia;
	
	private  int codProvinciaResidencia;
	
	private String d_provincia;
	
	private String espanol;
	
	private String emancipado;
	
	@JsonFormat(pattern="dd-MM-yyyy" , locale="es-ES" , timezone="CET")
	private Date fechaNacimiento;
	private String tipoVia;
	
	private String tipoIdentificacion;
	
	private String nIdentificacion;
	
	private String apellido2;
	
	private String numeroCalle;
	
	private String apellido1;
	
	private String nombre;
	
	private String piso;
	
	private String numeroEscolar;
	
	

}
