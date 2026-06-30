package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TLEMPLEADOS" , schema = "DELPHOS")
@EqualsAndHashCode
public class EmpleadoDelphos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "X_EMPLEADO")
	private Long xempleado;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "APELLIDO1")
	private String apellido1;
	
	@Column(name = "APELLIDO2")
	private String apellido2;
	
	@Column(name = "C_NUMIDE")
	private String documento;
	
	@Column(name = "L_SEXO")
	private String lsexo;
	
	@Column(name = "F_NACIMIENTO")
	private Date fechaNacimiento;
	
	@Column(name = "T_CORREO_LDAP")
	private String correoLdap;
	
	@Column(name = "")
	private boolean equipoDirectivo;
	
	@Column(name = "")
	private String listaCargos;
	
	@Column(name = "")
	private String tipoPersonalEmpleado;
	
	@Column(name = "")
	private boolean tutorUnidad;
	
	@Column(name = "")
	private String cursoTutorUnidad;
	
	@Column(name = "")
	private String departamento;
	
	@Column(name = "")
	private String unidadOrganizativa;
	
	@Column(name = "")
	private String ptotraemp;
	
	@Column(name = "")
	private boolean comisioncoordinacionpedagojica;
	
}
