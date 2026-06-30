package es.jccm.edu.shared.application.domain.datosUsuarioJwt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DatosUsuarioJwt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long xUsuarioDelphos;

	private Long xUsuarioComunica;

	private String usuarioDelphos;

	private String usuarioComunica;

	private Long idEmpleadoDelphos;

	private Long idEmpleadoComunica;

	private String nif;

	private Long oid;

	private String email;
	private String nombre;
	private String apellidos;
	private Date fNacimiento;
}
